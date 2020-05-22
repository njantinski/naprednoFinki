package mk.ukim.finki.napredno.predavanjeDemo.carLateBindingDemo;

import java.sql.SQLOutput;

public class main {
    public static void main(String[] args) {
        car Kola = new car("SUV", 192, 5);
        Porsche panamera = new Porsche("sedan", 350, 5, "Panamera");

        System.out.println(Kola.toString());
        System.out.println(panamera.toString());


        System.out.println("Late binding test: ");
        Kola = panamera;
        System.out.println("Treba da go printa panamera tostring");
        System.out.println(Kola.toString());
        /*
        downcasting : mora eksplicitno da se navede deka od
        izvedenata klasa se kastira vo osnovnata klaas
         */

        car Kola2 = new car("Hatchback", 109, 5);
        Porsche cayene = new Porsche("SUV", 500, 5, "Cayenne");
        // dava runtime error
        // bidejki ova nema smisla\
        // cayene = (Porsche) Kola2;


    }
}
