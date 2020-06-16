package mk.ukim.finki.napredno.aud.aud6;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BenfordLawProcessor{
    List<Integer> firstDigits;
    double[] countingArray = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};

    BenfordLawProcessor(){
        firstDigits = new ArrayList<>();
    }

    public void readData(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        firstDigits = br.lines().map(line -> {
            String[] parts = line.split("\\s+");
            return Integer.parseInt(String.valueOf(parts[2].charAt(0)));
        }).collect(Collectors.toList());

        firstDigits.forEach(digit -> countingArray[digit] += 1.0);
    }

    public void printPercentage(OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        IntStream.range(1,10).forEach(i ->{
            countingArray[i]/=firstDigits.size();
            pw.println(String.format("%d\t%.2f%%", i, countingArray[i]*100));
        });
        pw.flush();
    }

}

public class BenfordLawTester {
    public static void main(String[] args) throws FileNotFoundException {
        BenfordLawProcessor bl = new BenfordLawProcessor();
        bl.readData(new FileInputStream("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\aud\\aud6\\randomNums.txt"));
        bl.printPercentage(System.out);
    }
}
