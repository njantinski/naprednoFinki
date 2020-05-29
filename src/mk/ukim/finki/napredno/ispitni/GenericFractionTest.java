package mk.ukim.finki.napredno.ispitni;

import java.util.Scanner;

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде
class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException(){
        super("Denominator cannot be zero");
    }
}

class GenericFraction<T extends Number, U extends Number> {
    private T numerator;
    private U denominator;

    public GenericFraction(T numerator, U denominator) throws ZeroDenominatorException {
        this.numerator = numerator;
        if (denominator.doubleValue() == 0.0)
            throw new ZeroDenominatorException();
        this.denominator = denominator;
    }

    public T getNumerator() {
        return numerator;
    }

    public U getDenominator() {
        return denominator;
    }

    public GenericFraction<Double, Double> add(GenericFraction<? extends Number, ? extends Number> gf) throws ZeroDenominatorException {
        int newDenominator = findLcm(denominator.intValue(), gf.getDenominator().intValue());
        GenericFraction<Double, Double> firstWithSameDenom = getNewGen(this, newDenominator);
        GenericFraction<Double, Double> secondWithSameDenom = getNewGen(gf, newDenominator);
        double newNumerator = firstWithSameDenom.getNumerator() + secondWithSameDenom.getNumerator();
        return new GenericFraction<>(newNumerator, (double) newDenominator);
    }

    private GenericFraction<Double, Double> getNewGen(GenericFraction<? extends Number, ? extends Number> tuGenericFraction, int newDenominator) throws ZeroDenominatorException {
        int numToMultiply = newDenominator / tuGenericFraction.getDenominator().intValue();
        return new GenericFraction<Double, Double>(tuGenericFraction.getNumerator().doubleValue() * numToMultiply, (double) newDenominator);
    }

    private int findLcm(int doubleValue, int doubleValue1) {
        if (doubleValue < doubleValue1) {
            int tmp = doubleValue1;
            doubleValue1 = doubleValue;
            doubleValue = tmp;
        }
        int lcm = doubleValue;
        while (lcm % doubleValue1 != 0) {
            lcm += doubleValue;
        }
        return lcm;
    }

    public double toDouble() {
        return numerator.doubleValue() / denominator.doubleValue();
    }

    public String toString() {
        boolean optimized = false;
        int gcdNum = findGcd(numerator.intValue(),denominator.intValue());
        if ( gcdNum > 1) {
            GenericFraction<? extends Number, ? extends Number> optimizedFraction = null;
            try {
                optimizedFraction = optimize(this,gcdNum);
            } catch (ZeroDenominatorException e) {
                e.printStackTrace();
            }
            return String.format("%.2f / %.2f", optimizedFraction.getNumerator().doubleValue(), optimizedFraction.getDenominator().doubleValue());
        }
        else {
            return String.format("%.2f / %.2f", numerator.doubleValue(), denominator.doubleValue());
        }
        }

    private GenericFraction<? extends Number, ? extends Number> optimize(GenericFraction<T, U> tuGenericFraction,int gcd) throws ZeroDenominatorException {
        return new GenericFraction<>(tuGenericFraction.numerator.intValue()/gcd, tuGenericFraction.denominator.intValue()/gcd);
    }

    int findGcd(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        else{
            return findGcd(n2, n1%n2);
        }
    }
}









