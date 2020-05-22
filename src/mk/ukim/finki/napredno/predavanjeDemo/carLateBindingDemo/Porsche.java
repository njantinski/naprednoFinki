package mk.ukim.finki.napredno.predavanjeDemo.carLateBindingDemo;

public class Porsche extends car{
    private String model;

    public Porsche(String type, int horsePower, int doors, String model) {
        super(type, horsePower, doors);
        this.model = model;
    }

    @Override
    public String toString() {
        return "Porsche{" +
                "model='" + model + '\'' +
                '}' + "car{" +
                "type='" + type + '\'' +
                ", horsePower=" + horsePower +
                ", doors=" + doors +
                '}';
    }
}
