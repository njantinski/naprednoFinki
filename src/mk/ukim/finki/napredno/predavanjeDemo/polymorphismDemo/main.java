package mk.ukim.finki.napredno.predavanjeDemo.polymorphismDemo;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        List<Animal> animals = new ArrayList<>();

        Dog maltese = new Dog("Maltese", 4, true);
        Dog pitBull = new Dog("Pitbull", 4 , false);

        Monkey orangutan = new Monkey("Orangutan", 2, 2, true);
        Monkey Chimpanzee = new Monkey("Chimpanzee", 2, 2, true);

        animals.add(maltese);
        animals.add(pitBull);
        animals.add(orangutan);
        animals.add(Chimpanzee);

        for (Animal m: animals) {
            System.out.println(m.toString());
        }

    }
}
