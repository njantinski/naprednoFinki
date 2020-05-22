package mk.ukim.finki;

public class Main {

    public static void main(String[] args) {
	// write your code here
        String s = "2014-04-15";
        System.out.println(s.substring(8,10));
        int[] niza = new int[5];
        System.out.println(niza.length + niza[3]);
        System.out.println((char)((int)'A' + 1));
        int[] nizca= new int[0];
        System.out.println(nizca.length > 0);
    }
}

abstract class Animal{
    private int legs;
    private boolean tail;

    public Animal(int legs, boolean tail) {
        this.legs = legs;
        this.tail = tail;
    }
}

class Dog extends Animal{
    private String name;
    public Dog(int legs, boolean tail) {
        super(legs, tail);

    }
}