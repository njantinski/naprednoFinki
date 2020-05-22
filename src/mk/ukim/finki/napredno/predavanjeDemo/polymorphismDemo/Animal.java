package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo;

public abstract class Animal {
    protected int legs;
    protected boolean tail;

    public Animal(int legs, boolean tail) {
        this.legs = legs;
        this.tail = tail;
    }

    public abstract int getLegs();
    public abstract boolean tail();
}
