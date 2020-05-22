package mk.ukim.finki.napredno.predavanjeDemo.klasaB;

import mk.ukim.finki.napredno.predavanjeDemo.klasaA.KlasaA;

public class KlasaB extends KlasaA {
    public void printN(){
        System.out.println(A);
    }
    public static void print(){
        System.out.println("Klasa b");
    }

    public void printNonStatic(){
        System.out.println("Klasa b");
    }
    public static void main(String[] args) {
        KlasaA a = new KlasaA();
        KlasaB b = new KlasaB();

        a = b;
        a.printNonStatic();
    }
}
