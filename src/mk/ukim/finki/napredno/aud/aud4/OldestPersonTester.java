package mk.ukim.finki.napredno.aud.aud4;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class OldestPersonTester {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\aud\\aud4\\persons.txt"));
        String line;
        List<Person> lista = new ArrayList<>();
        while((line = br.readLine()) != null){
            String[] covek = line.split("\\s+");
            lista.add(new Person(covek[0],Integer.parseInt(covek[1])));
        }

        System.out.println(najstar(lista).toString());
    }

    public static Person najstar(List<Person> lista){
        Person first = lista.get(0);

        for(int i = 1 ; i < lista.size(); i++){
            if(first.compareTo(lista.get(i)) == -1){
                first = lista.get(i);
            }
        }
        return first;
    }
}


class Person implements Comparable<Person>{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Person o) {
        if(getAge() > o.getAge())
            return 1;
        else if(getAge() < o.getAge())
            return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}