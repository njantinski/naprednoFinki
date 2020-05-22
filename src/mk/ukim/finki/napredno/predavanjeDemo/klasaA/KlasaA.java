package mk.ukim.finki.napredno.predavanjeDemo.klasaA;

public class KlasaA {

    protected int A = 5;

    public KlasaA(int a) {
        A = a;
    }
    public KlasaA(){}
    public static void print(){
        System.out.println("Klasa a");
    }
    public void printNonStatic(){
        System.out.println("Klasa a");
    }
}
