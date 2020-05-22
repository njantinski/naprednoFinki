package mk.ukim.finki.napredno.ispitni;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

interface Convertable{
    double getAsCelsius();
    double getAsFahrenheit();
}

abstract class Measurement implements Convertable{
    double value;

    public Measurement(double value) {
        this.value = value;
    }
}

class CMeasurement extends Measurement{

    public CMeasurement(double value) {
        super(value);
    }

    @Override
    public double getAsCelsius() {
        return super.value;
    }

    @Override
    public double getAsFahrenheit() {
        return ((super.value * 9) / 5) + 32;
    }
}

class FMeasurement extends Measurement{

    public FMeasurement(double value) {
        super(value);
    }

    @Override
    public double getAsCelsius() {
        return (value - 32) * 5 / 9.0;
    }


    @Override
    public double getAsFahrenheit() {
        return super.value;
    }
}


class DailyMeasurement implements Comparable<DailyMeasurement>{
    int day;
    List<Measurement> measurements;

    public DailyMeasurement(int day, List<Measurement> measurements) {
        this.day = day;
        this.measurements = measurements;
    }

    public static DailyMeasurement createInstance(String input){
        String[] parts = input.split("\\s+");
        int day = Integer.parseInt(parts[0]);
        List<Measurement> measurements = new ArrayList<>();

        measurements = Arrays.stream(parts).skip(1).
                map(in ->{
                    double value = Double.parseDouble(in.substring(0, in.length()-1));
                    char scale = in.charAt(in.length() - 1);

                    if(scale == 'C')
                        return new CMeasurement(value);
                    else
                        return new FMeasurement(value);
                }).collect(Collectors.toList());

        return new DailyMeasurement(day, measurements);
    }


    public String toString(char scale){
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        measurements.stream().forEach(m ->{
            if(scale == 'C')
                doubleSummaryStatistics.accept(m.getAsCelsius());
            else
                doubleSummaryStatistics.accept(m.getAsFahrenheit());
        });

        return String.format("%3d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c",
                day, (int)doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                scale,
                doubleSummaryStatistics.getMax(),
                scale,
                doubleSummaryStatistics.getAverage(),
                scale);
    }




    @Override
    public int compareTo(DailyMeasurement otherDailyMeasurement) {
        return Integer.compare(this.day, otherDailyMeasurement.day);
    }
}

class DailyTemperatures{
    List<DailyMeasurement> measurements;

    public DailyTemperatures(){
        measurements = new ArrayList<>();
    }

    public void readTemperatures(InputStream in){
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        measurements = br.lines().map(DailyMeasurement::createInstance)
                .collect(Collectors.toList());
    }

    public void writeDailyStats(OutputStream out, char scale){
        PrintWriter pw = new PrintWriter(out);

        measurements.sort(null);
        measurements.stream().forEach(m -> pw.println(m.toString(scale)));

        pw.flush();
        pw.close();
    }
}






public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

