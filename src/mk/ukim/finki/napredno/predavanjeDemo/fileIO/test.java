package mk.ukim.finki.napredno.predavanjeDemo.fileIO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
            File zivotni = new File("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\predavanjeDemo\\fileIO\\zivotni.txt");
        List<Zivotno> zivotincinja = new ArrayList<>();
        zivotincinja.add( new Zivotno("Dog", 4,true));
        zivotincinja.add(new Zivotno("Ulicna Macka", 4,false));
        zivotincinja.add( new Zivotno("Ulicen maltezer", 3 ,false));

        BufferedWriter br = new BufferedWriter(new PrintWriter(new FileWriter(zivotni,true)));
        //PrintWriter pw = new PrintWriter(new FileWriter(zivotni));
        for(Zivotno z : zivotincinja){
            br.write(z.toString());
            br.newLine();
        }
      br.close();
        System.out.println();
        System.out.println(zivotni.toString());

        BufferedReader buffer= new BufferedReader(new FileReader(zivotni));
        String line;
        while((line =buffer.readLine()) != null){
            System.out.println(line);
        }

    }

}

class Zivotno{
    private String name;
    private int legs;
    private boolean tail;

    public Zivotno(String name, int legs, boolean tail) {
        this.name = name;
        this.legs = legs;
        this.tail = tail;
    }

    @Override
    public String toString() {
        return "Zivotno: " + this.name + " has: " + this.legs + " legs and " + ((this.tail) ? "has a tail" : "doesn't have a tail");
    }
}
