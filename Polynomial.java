public class Polynomial  {
	double[] poly;
	
	public Polynomial() {
		poly=new double [1];
		poly[0]=0;
	}
	
	public Polynomial(double[] coefs) {
		int length=coefs.length;
		poly=new double[length];
		
		for(int i=0;i<length;i++) {
			poly[i]=coefs[i];
		}
	}
	
	public Polynomial add(Polynomial arg) {
		int polylen=poly.length;
		int arglen=arg.poly.length;
		
		if(polylen>arglen) {
			double[] result=new double[polylen];
			for(int i=0;i<arglen;i++) {
			result[i]=poly[i]+arg.poly[i];
			}
			for(int i=arglen; i<polylen;i++) {
				result[i]=poly[i];
			}
			Polynomial res=new Polynomial(result);
			return res;
		}
		
		else {
			double[] result=new double[arglen];
			for(int i=0;i<polylen;i++) {
			result[i]=poly[i]+arg.poly[i];
			}
			for(int i=polylen; i<arglen;i++) {
				result[i]=arg.poly[i];
			}
			Polynomial res=new Polynomial(result);
			return res;
		}
    }

	
	public double evaluate(double x) {
		int len=poly.length;
		double evaluation=poly[len-1];
		
		for(int i=0;i<len;i++) {
			evaluation=evaluation*x+poly[len-1-i];
			}
		return evaluation;
	}
	
	public boolean hasRoot(double root) {
		double eval=this.evaluate(root);
		if (eval==0) return true;
		else return false;
	}
	
}
