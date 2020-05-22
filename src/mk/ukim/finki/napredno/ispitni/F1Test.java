package mk.ukim.finki.napredno.ispitni;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class F1Test {

    public static void main(String[] args) throws IOException {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race{
    // vashiot kod ovde
    private List<Driver> drivers;

    public F1Race(){
        drivers = new ArrayList<>();
    }

    public void readResults(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;

        while((line = br.readLine()) != null){
            String[] driver = line.split("\\s+");
            List<Lap> laps = new ArrayList<>();
            IntStream.range(1,driver.length).forEach(i -> laps.add(new Lap(driver[i])));
            Driver d = new Driver(driver[0], laps);
            drivers.add(d);
        }
        drivers.sort(Comparator.naturalOrder());
    }


    public void printSorted(PrintStream out) {
        IntStream.range(0, drivers.size()).forEach(i -> out.println((i + 1) + ". " + drivers.get(i).toString()));
    }
}

class Driver implements Comparable<Driver>{
    private String name;
    private List<Lap> laps;
    private Lap bestLap;

    public Driver(String name, List<Lap> laps) {
        this.name = name;
        this.laps = laps;
        this.bestLap = computeBestLap();

    }

    public Lap getBestLap(){
        return this.bestLap;
    }
    private Lap computeBestLap(){
        return laps.stream().sorted(Comparator.naturalOrder()).findFirst().orElse(new Lap());
    }
    public String toString(){
        return String.format("%10s%10s",this.name,this.bestLap.toString());
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Driver o) {
        return getBestLap().compareTo(o.getBestLap());
    }
}

class Lap implements Comparable<Lap>{

    private int mins;
    private int secs;
    private int mills;

    public Lap(){}

    public Lap(String lapTime) {
        String[] time = lapTime.split("(:)");
        this.mins = Integer.parseInt(time[0]);
        this.secs = Integer.parseInt(time[1]);
        this.mills = Integer.parseInt(time[2]);
    }

    public int getMins() {
        return mins;
    }

    public int getSecs() {
        return secs;
    }

    public int getMills() {
        return mills;
    }

    @Override
    public int compareTo(Lap o) {
        if(getMins() > o.getMins()){
            return 1;
        }
        else if(getMins() == o.getMins() && getSecs() > o.getSecs()){
            return 1;
        }
        else if(getMins() == o.getMins() && getSecs() == o.getSecs() && getMills() > o.getMills()){
            return 1;
        }
        else if(getMins() == o.getMins() && getSecs() == o.getSecs() && getMills() == o.getMills()){
            return 0;
        }
        else
            return -1;
    }

    public String toString(){
        return String.format("%d:%02d:%03d",mins,secs,mills);
    }
}