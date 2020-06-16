package mk.ukim.finki.napredno.aud.aud6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Finalists {
    public static void main(String[] args) {
        System.out.println("Vnesete broj na finalisti:");
        Scanner sc = new Scanner(System.in);
        int brFinalisti = sc.nextInt();
        RandomPicker finalsti = new RandomPicker(brFinalisti);


        System.out.println("Trojcata finalisti se: ");
        finalsti.pick(3).forEach(System.out::println);


    }
}


class RandomPicker{
    // se vnesuva brojot na igraci od koi se bira
    private final int n;

    RandomPicker(int n) {
        this.n = n;
    }

    public List<Integer> pick(int x){
        //se inicijalizira ranodm brojac
        Random random = new Random();
        List<Integer> picked = new ArrayList<>();
        // se biraat x finalisti
        while(picked.size() != x){
            // ako ne e izbran se dodava vo nizata
            // problem: mnogu povtoruvanja, idendicni odbiranja
            int pick = random.nextInt(n) + 1;
            if(!picked.contains(pick)){
                picked.add(pick);
            }
        }
        return picked;
    }
}