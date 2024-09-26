public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial(new double[]{}, new int[]{});
        System.out.println("Should be 0.0, actually: " + p.evaluate(3));
        
        Polynomial p1 = new Polynomial(new double[]{2, 3, 4}, new int[]{0, 2, 3});
        Polynomial p2 = new Polynomial(new double[]{1,4,5}, new int[]{0,1,4});
        Polynomial sum = p1.add(p2);
        Polynomial product = p1.multiple(p2);

        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);
        
        System.out.println("p1(1) = " + p1.evaluate(1));
        if (p1.hasRoot(1)) {
            System.out.println("1 is a root of p1");
        } else {
            System.out.println("1 is not a root of p1");
        }
    }
}
