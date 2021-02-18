package mk.ukim.finki.napredno.ispitni;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();

    }
}

// Вашиот код овде
class Time{
    int hour;
    int min;
    int sec;
    int ms;

    public Time(int hour, int min, int sec, int ms) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
        this.ms = ms;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public int getMs() {
        return ms;
    }

    public void shift(int ms){
        if(ms > 0){
            shiftPositive(ms);
        }
        else {
            shiftNegative(ms);
        }

    }

    private void shiftPositive(int ms) {
        int newMs = this.ms + ms;
        if(newMs > 1000){
            this.ms = newMs % 1000;
            int newSeconds = this.sec + (newMs / 1000);
            if(newSeconds > 60){
                this.sec =  newSeconds % 60;
                this.hour += this.sec / 60;
            }
            else{
                this.sec = newSeconds;
            }
        }
        else{
            this.ms = newMs;
        }
    }
    private void shiftNegative(int ms){
        if(this.ms > -ms){
            shiftPositive(ms);
        }
        else{
            if(sec== 0){
                this.sec = 60;
                this.min -= 1;
            }
            this.sec -= 1;
            int oldMs = this.ms;
            this.ms = oldMs + 1000 + ms;
        }
    }

    @Override
    public String toString(){
        return String.format("%02d:%02d:%02d,%03d",hour,min,sec,ms);
    }

    public static Time createTime(String line){
        String[] hmc = line.trim().split(":");
        String[] secMs = hmc[2].trim().split(",");
        int hour = Integer.parseInt(hmc[0]);
        int min = Integer.parseInt(hmc[1]);
        int sec = Integer.parseInt(secMs[0]);
        int ms = Integer.parseInt(secMs[1]);
        return new Time(hour,min,sec,ms);
    }
}

class Subtitle{
    int numOrder;
    Time startTime;
    Time endTime;
    String content;

    public Subtitle(int numOrder, Time startTime, Time endTime, String content) {
        this.numOrder = numOrder;
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
    }

    public static Subtitle createSubtitle(int num, String times, String content) {
        String[] timesSplit = times.trim().split("-->");
        Time startTime = Time.createTime(timesSplit[0]);
        Time endTime = Time.createTime(timesSplit[1]);
        return new Subtitle(num,startTime,endTime,content);

    }


    public void shift(int ms){
        startTime.shift(ms);
        endTime.shift(ms);
    }
    @Override
    public String toString(){
       StringBuilder sb = new StringBuilder();
       sb.append(numOrder).append("\n").append(startTime.toString()).append(" --> ").append(endTime.toString()).append("\n")
               .append(content).append("\n");
       return sb.toString();
    }
}

class Subtitles{
    List<Subtitle> subtitles;

    public Subtitles() {
        this.subtitles = new ArrayList<>();
    }

    public int loadSubtitles(InputStream inputStream){
        int numSubtitles = 0;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            while((line = br.readLine()) != null){
                int num =  Integer.parseInt(line);
                String times = br.readLine();
                String content = br.readLine();
                if((line = br.readLine()) != null) {
                    while (!line.equals("")) {
                       content = content.concat("\n" + line);
                        line = br.readLine();
                        if(line == null)
                            break;
                    }
                }
                subtitles.add(Subtitle.createSubtitle(num,times,content));
                numSubtitles++;
            }
        } catch (IOException e) {

        }
        return numSubtitles;
    }

    public void print() {
        subtitles.forEach(System.out::println);
    }

    public void shift(int shift) {
        subtitles.forEach(s -> s.shift(shift));
    }
}