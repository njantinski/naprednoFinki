package mk.ukim.finki.napredno.aud.aud4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayListTester {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        System.out.println(integerList.size());
        integerList.add(5);
        integerList.add(6);
        System.out.println(integerList.toString());
        integerList.add(1,10);// adding to specific pos
        System.out.println(integerList.toString());
        integerList.addAll(Arrays.asList(5,6,7,8,9,19));
        System.out.println(integerList);
        System.out.println(integerList.size());
    }
}
