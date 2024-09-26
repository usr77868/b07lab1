import java.util.*;
import java.io.*;

public class Polynomial  {
	double[] polycoef;
	int[] polyexpo;
	
	public Polynomial() {
		polycoef=new double [1];
		polycoef[0]=0;
		polyexpo=new int[1];
		polyexpo[0]=0;
	}
	
	public Polynomial(double[] coef, int[] expo) {
		int coeflen=coef.length;
		polycoef=new double[coeflen];
		
		for(int i=0;i<coeflen;i++) {
			polycoef[i]=coef[i];
		}
		
		int expolen=expo.length;
		polyexpo=new int[expolen];
		
		for(int i=0;i<expolen;i++) {
			polyexpo[i]=expo[i];
		}
	}
	
    public Polynomial(File file) throws IOException {
        String polynomialString = new BufferedReader(new FileReader(file)).readLine();
        parsePolynomial(polynomialString);
    }
	
    public void normalize() {
        Map<Integer, Double> map = new HashMap<>();
        for (int i = 0; i < polycoef.length; i++) {
            map.put(polyexpo[i], map.getOrDefault(polyexpo[i], 0.0) + polycoef[i]);
        }

        List<Double> coefficients = new ArrayList<>();
        List<Integer> exponents = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            if (entry.getValue() != 0) {
                coefficients.add(entry.getValue());
                exponents.add(entry.getKey());
            }
        }

        polycoef = coefficients.stream().mapToDouble(Double::doubleValue).toArray();
        polyexpo = exponents.stream().mapToInt(Integer::intValue).toArray();
    }
    
    public Polynomial add(Polynomial argument) {
        List<Double> resultCoefficients = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();

        int i = 0, j = 0;
        while (i < polycoef.length && j < argument.polycoef.length) {
            if (polyexpo[i] == argument.polyexpo[j]) {
                resultCoefficients.add(polycoef[i] + argument.polycoef[j]);
                resultExponents.add(polyexpo[i]);
                i++;
                j++;
            } else if (polyexpo[i] < argument.polyexpo[j]) {
                resultCoefficients.add(polycoef[i]);
                resultExponents.add(polyexpo[i]);
                i++;
            } else {
                resultCoefficients.add(argument.polycoef[j]);
                resultExponents.add(argument.polyexpo[j]);
                j++;
            }
        }

        while (i < polycoef.length) {
            resultCoefficients.add(polycoef[i]);
            resultExponents.add(polyexpo[i]);
            i++;
        }

        while (j < argument.polycoef.length) {
            resultCoefficients.add(argument.polycoef[j]);
            resultExponents.add(argument.polyexpo[j]);
            j++;
        }

        return new Polynomial(resultCoefficients.stream().mapToDouble(Double::doubleValue).toArray(),
                              resultExponents.stream().mapToInt(Integer::intValue).toArray());
    }

    public Polynomial multiple(Polynomial argument) {
        List<Double> resultCoefficients = new ArrayList<>();
        List<Integer> resultExponents = new ArrayList<>();

        for (int i = 0; i < polycoef.length; i++) {
            for (int j = 0; j < argument.polycoef.length; j++) {
                double newCoefficient = polycoef[i] * argument.polycoef[j];
                int newExponent = polyexpo[i] + argument.polyexpo[j];

                resultCoefficients.add(newCoefficient);
                resultExponents.add(newExponent);
            }
        }
        Map<Integer, Double> map = new HashMap<>();
        for (int k = 0; k < resultCoefficients.size(); k++) {
            map.put(resultExponents.get(k), map.getOrDefault(resultExponents.get(k), 0.0) + resultCoefficients.get(k));
        }

        List<Double> finalCoefficients = new ArrayList<>();
        List<Integer> finalExponents = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry : map.entrySet()) {
            if (entry.getValue() != 0) {
                finalCoefficients.add(entry.getValue());
                finalExponents.add(entry.getKey());
            }
        }

        return new Polynomial(finalCoefficients.stream().mapToDouble(Double::doubleValue).toArray(),
                              finalExponents.stream().mapToInt(Integer::intValue).toArray());
    }
	
	public double evaluate(double x) {
		double result=0.0;
		for(int i=0;i<this.polycoef.length;i++) {
			result+=this.polycoef[i]*Math.pow(x, this.polyexpo[i]);
		}
		
		return result;
	}
	
	public boolean hasRoot(double root) {
		if(this.evaluate(root)==0) return true;
		else return false;
	}
	
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < polycoef.length; i++) {
            if (i > 0 && polycoef[i] > 0) {
                result.append("+");
            }
            result.append(polycoef[i]);
            if (polyexpo[i] > 0) {
                result.append("x");
                if (polyexpo[i] > 1) {
                    result.append("^").append(polyexpo[i]);
                }
            }
        }
        return result.toString();
    }
    
    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            StringBuilder polynomialString = new StringBuilder();
            for (int i = 0; i < polycoef.length; i++) {
                if (i > 0 && polycoef[i] > 0) {
                    polynomialString.append("+");
                }
                polynomialString.append(polycoef[i]);
                if (polyexpo[i] > 0) {
                    polynomialString.append("x");
                    if (polyexpo[i] > 1) {
                        polynomialString.append("^").append(polyexpo[i]);
                    }
                }
            }
            writer.write(polynomialString.toString());
        }
    }
    
    public void parsePolynomial(String polynomialString) {
        List<Double> coefficients = new ArrayList<>();
        List<Integer> exponents = new ArrayList<>();

        String[] terms = polynomialString.split("(?=[+-])");
        for (String term : terms) {
            term = term.trim();
            if (term.isEmpty()) continue;
            double coefficient = 1.0;
            int exponent = 0;
            if (term.contains("x")) {
                String[] parts = term.split("x");
                if (parts[0].isEmpty() || parts[0].equals("+")) {
                    coefficient = 1.0;
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(parts[0]);
                }
                if (parts.length > 1 && parts[1].startsWith("^")) {
                    exponent = Integer.parseInt(parts[1].substring(1));
                } else {
                    exponent = 1;
                }
            } else {
                coefficient = Double.parseDouble(term);
                exponent = 0;
            }

            coefficients.add(coefficient);
            exponents.add(exponent);
        }
        polycoef = coefficients.stream().mapToDouble(Double::doubleValue).toArray();
        polyexpo = exponents.stream().mapToInt(Integer::intValue).toArray();
        normalize();
    }

}
