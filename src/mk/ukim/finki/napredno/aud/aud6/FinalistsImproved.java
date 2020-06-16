package mk.ukim.finki.napredno.aud.aud6;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FinalistsImproved {
}

class Pick{
    private List<Integer> picked;
    private List<Integer> participants;

    public Pick(int n){
        participants = new ArrayList<>();
        IntStream.range(0,n).forEach(i -> participants.add(i));
    }

    public List<Integer> pickWinners(int finalists){
        Random random = new Random();

        return IntStream.range(0,finalists).mapToObj(prize ->{
           int index = random.nextInt(participants.size());
           Integer pickedValue = participants.get(index);
           participants.remove(pickedValue);
           return pickedValue;
        }).collect(Collectors.toList());
    }
}
