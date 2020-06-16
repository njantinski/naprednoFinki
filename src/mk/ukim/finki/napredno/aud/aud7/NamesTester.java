package mk.ukim.finki.napredno.aud.aud7;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class NamesTester {
    public static void main(String[] args) {
        Names names = new Names();
        try{
            names.readNames("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\aud\\aud7\\boynames.txt",SEX.MALE);
            names.readNames("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\aud\\aud7\\girlnames.txt",SEX.FEMALE);

            names.printDuplicates(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

enum SEX{
    MALE,
    FEMALE
}

class Name{
    String name;
    Integer maleCounts;
    Integer femaleCounts;

    public Name(String name){
        this.name = name;
    }

    public void setMaleCounts(Integer maleCounts) {
        this.maleCounts = maleCounts;
    }

    public void setFemaleCounts(Integer femaleCounts) {
        this.femaleCounts = femaleCounts;
    }

    public String getName() {
        return name;
    }

    public Integer getMaleCounts() {
        return maleCounts;
    }

    public Integer getFemaleCounts() {
        return femaleCounts;
    }


    public int getTotalCount(){
        return maleCounts + femaleCounts;
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                ", maleCounts=" + maleCounts +
                ", femaleCounts=" + femaleCounts +
                '}';
    }
}

class Names{
        Map<String,Name> nameMap;
        Names(){
            nameMap = new HashMap<>();
        }


        public void readNames(String fileLocation, SEX sex) throws FileNotFoundException {
            File file = new File(fileLocation);
            BufferedReader br = new BufferedReader(new FileReader(file));

            br.lines().forEach(line ->{
                String[] parts  = line.split("\\s+");
                String name = parts[0];
                int frequency = Integer.parseInt(parts[1]);

                nameMap.putIfAbsent(name, new Name(name));
                if(sex.equals(SEX.MALE)){
                    nameMap.get(name).setMaleCounts(frequency);
                }
                else{
                    nameMap.get(name).setFemaleCounts(frequency);
                }
            });
        }

        public void printDuplicates(OutputStream os){
            PrintWriter pw = new PrintWriter(os);
            Predicate<Name> checkBothPredicate = n -> n.getFemaleCounts() != null && n.getMaleCounts() != null;

            nameMap.values().stream().filter(checkBothPredicate).sorted(Comparator.comparingInt(Name::getTotalCount).reversed())
                    .forEach(pw::println);
            pw.flush();
        }
}
