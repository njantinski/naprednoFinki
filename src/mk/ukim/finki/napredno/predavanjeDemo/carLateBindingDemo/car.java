package mk.ukim.finki.napredno.predavanjeDemo.carLateBindingDemo;

public class car {
    protected String type;
    protected int horsePower;
    protected int doors;

    public car(String type, int horsePower, int doors) {
        this.type = type;
        this.horsePower = horsePower;
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "car{" +
                "type='" + type + '\'' +
                ", horsePower=" + horsePower +
                ", doors=" + doors +
                '}';
    }
}
