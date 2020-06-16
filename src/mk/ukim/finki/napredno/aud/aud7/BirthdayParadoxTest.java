package mk.ukim.finki.napredno.aud.aud7;

import java.util.*;

public class BirthdayParadoxTest {
}


class BirthdayParadox{
    static final int NUM_TRIALS = 5000;

    private final int maxPeople;

    public BirthdayParadox(int maxPeople){
        this.maxPeople = maxPeople;
    }

    public Map<Integer, Double> simulate(int numTrials){
        Random random = new Random();
        Map<Integer, Double> probabilities = new TreeMap<>();
        for(int numPeople = 2; numPeople <= maxPeople; ++numPeople){
            double probability = simulation(numPeople, numTrials, random);
            probabilities.put(numPeople,probability);
        }
        return probabilities;
    }

    private double simulation(int numPeople, int numTrials, Random random) {
        int positiveEvents = 0;
        for(int i =0; i < numTrials; ++i){
            if(singleExperiment(numPeople, random)){
                ++positiveEvents;
            }
        }
        return positiveEvents *1.0/ NUM_TRIALS;
    }

    private boolean singleExperiment(int numPeople, Random random) {
        Set<Integer> room = new HashSet<>();
        for(int i = 0; i < numPeople; ++i){
            int birthday = random.nextInt(365 ) +1 ;
            if(!room.add(birthday))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        BirthdayParadox birthdays= new BirthdayParadox(50);
        Map<Integer,Double>probabilities=birthdays.simulate(NUM_TRIALS);
        probabilities.forEach((key,value)->System.out.printf("For %d people, the probability of two birthdays isabout %.3f\n",key,value));
    }
}
