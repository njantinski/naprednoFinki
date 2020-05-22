package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo;

public class Monkey extends Animal {
    protected int arms;
    protected String race;

    public Monkey(String race, int arms,int legs, boolean tail) {
        super(legs, tail);
        this.race = race;
        this.arms = arms;
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
        return "Monkey{" +
                "arms=" + arms +
                ", race='" + race + '\'' +
                ", legs=" + legs +
                ", tail=" + tail +
                '}';
    }
}
