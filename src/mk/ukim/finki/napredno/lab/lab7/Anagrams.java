package mk.ukim.finki.napredno.lab.lab7;

import java.io.*;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class Anagrams {

    public static void main(String[] args) throws IOException {
        findAll(new FileInputStream(new File("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\lab\\lab7\\prm")));
    }

    public static void findAll(InputStream inputStream) throws IOException {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            Map<String, List<String>> groupAnagrams = new TreeMap<>();
            BiPredicate<String,String> checkKey = (word1, word2) -> checkAnagram(word1,word2);
            String line;

            while((line = br.readLine()) != null){
                String finalLine = line;
                String keyToPut = groupAnagrams.keySet().stream().filter(key -> checkKey.test(key, finalLine)).findFirst().orElse(null);
                if(keyToPut == null){
                    List<String> newList = new ArrayList<>();
                    newList.add(finalLine);
                    groupAnagrams.put(finalLine,newList);
                }
                else{
                    groupAnagrams.get(keyToPut).add(finalLine);
                }
            }
            printAnagrams(groupAnagrams);
        }
    }

    private static void printAnagrams(Map<String, List<String>> groupAnagrams) {
        groupAnagrams.values().stream().filter(list -> list.size() >= 5).forEach(list ->{
            String values = list.stream().collect(Collectors.joining(" "));
            System.out.println(values);
        });
    }

    public static boolean checkAnagram(String word1, String word2){
        char[] sortedWord1 = word1.toCharArray();
        Arrays.sort(sortedWord1);
        char[] sortedWord2 = word2.toCharArray();
        Arrays.sort(sortedWord2);
        return String.valueOf(sortedWord1).equals(String.valueOf(sortedWord2));
    }
}
