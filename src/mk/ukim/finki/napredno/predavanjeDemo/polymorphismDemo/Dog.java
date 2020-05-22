package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo;

public class Dog extends Animal {
    protected String race;

    public Dog(String race, int legs, boolean tail) {
        super(legs, tail);
        this.race = race;
    }

    @Override
    public int getLegs() {
        return super.legs;
    }

    @Override
    public boolean tail() {
        return super.tail;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "race='" + race + '\'' +
                ", legs=" + legs +
                ", tail=" + tail +
                '}';
    }
}
