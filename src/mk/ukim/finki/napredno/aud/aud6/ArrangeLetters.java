package mk.ukim.finki.napredno.aud.aud6;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrangeLetters {
    public String arrange(String input){
        String[] parts = input.split("\\s+");
        IntStream.range(0,parts.length).mapToObj(i ->{
            char[] partsOf = parts[i].toCharArray();
            Arrays.sort(partsOf);
            return new String(partsOf);
        }).sorted();
        return Arrays.stream(parts).sorted().collect(Collectors.joining(" "));
    }
}
