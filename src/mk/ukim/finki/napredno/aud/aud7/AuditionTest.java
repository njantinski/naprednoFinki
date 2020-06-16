package mk.ukim.finki.napredno.aud.aud7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        audition.addParticipant("Skopje","111", "Tomas", 15);
        audition.addParticipant("Skopje","112","Ano",21);
        audition.printParticipantsByCity("Skopje");
    }
    

}




class Audition{
    Map<String, HashSet<Participant>> participants;

    public Audition(){
        participants = new HashMap<>();
    }

    public void addParticipant(String city, String code, String name, int age){
        Participant newParticipant = createParticipant(code,name,age);
        Set<Participant> cityParticipants = participants
                .computeIfAbsent(city, key -> new HashSet<>());
        cityParticipants.add(newParticipant);
    }

    private Participant createParticipant(String code, String name, int age) {
        return new Participant(name,code,age);
    }

    public void printParticipantsByCity(String city){
        if(!participants.containsKey(city))
            System.out.println("Ne postojat kandidati od toj grad");
        else{
            participants.get(city).forEach(System.out::println);
        }
    }
}











class Participant{
    private String name;
    private String code;
    private int age;

    public Participant(String name,String code, int age) {
        this.name = name;

        this.code = code;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", age=" + age +
                '}';
    }
}
