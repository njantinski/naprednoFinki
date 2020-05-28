    package mk.ukim.finki.napredno.ispitni;


    import java.io.*;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.Comparator;
    import java.util.List;


    public class TimesTest {

        public static void main(String[] args) {
            TimeTable timeTable = new TimeTable();
            try {
                timeTable.readTimes(System.in);
            } catch (UnsupportedFormatException e) {
                System.out.println("UnsupportedFormatException: " + e.getMessage());
            } catch (InvalidTimeException e) {
                System.out.println("InvalidTimeException: " + e.getMessage());
            }
            System.out.println("24 HOUR FORMAT");
            timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
            System.out.println("AM/PM FORMAT");
            timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
        }

    }

    enum TimeFormat {
        FORMAT_24, FORMAT_AMPM
    }

    interface Convertable{
        String convertToAMPM();
        String convertTo24();
    }

    class UnsupportedFormatException extends Exception{
        public UnsupportedFormatException(String time){
            super(time);
        }
    }

    class InvalidTimeException extends Exception{
        public InvalidTimeException(String time){
            super(time);
        }
    }


    class TimeOfDay implements Convertable, Comparable<TimeOfDay>{
        private final int hour;
        private final int minutes;

        public TimeOfDay(String time) throws UnsupportedFormatException, InvalidTimeException {
            String[] arrTime = time.split("(:)");
            if(arrTime.length == 1){
                arrTime = time.split("\\.");
            }
            if(arrTime.length == 1){
                throw new UnsupportedFormatException(time);
            }
            int h = Integer.parseInt(arrTime[0]);
            int m = Integer.parseInt(arrTime[1]);
            if(h < 0 || h > 23 || m < 0 || m > 59)
                throw new InvalidTimeException(time);
            this.hour = h;
            this.minutes = m;
        }

        public int getHour() {
            return hour;
        }

        public int getMinutes() {
            return minutes;
        }

        @Override
        public String convertToAMPM() {
            if(hour == 0){
                return String.format("%2d:%02d AM",12,minutes);
            }
            else if(hour < 12){
                return String.format("%2d:%02d AM",hour,minutes);
            }
            else if(hour == 12){
               return String.format("%2d:%02d PM",hour,minutes);
            }
            else{
                return String.format("%2d:%02d PM",hour - 12,minutes);
            }
        }

        @Override
        public String convertTo24() {
            return String.format("%2d:%02d",hour,minutes);
        }

        @Override
        public int compareTo(TimeOfDay timeOfDay) {
            int cmp = Integer.compare(hour, timeOfDay.getHour());
            if (cmp == 0) {
                cmp = Integer.compare(minutes, timeOfDay.getMinutes());
            }
            return cmp;
        }
    }

    class TimeTable{
        private List<TimeOfDay> times;

        public TimeTable(){
            times = new ArrayList<>();
        }

        public void readTimes(InputStream is) throws UnsupportedFormatException, InvalidTimeException {
            try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
                String line;
                while((line = br.readLine()) != null){
                    String[] strTimes = line.split("\\s+");
                    for(String t : strTimes){
                        times.add(new TimeOfDay(t));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void writeTimes(OutputStream out, TimeFormat format){
            try{
                PrintWriter pw = new PrintWriter(out);
                times.sort(Comparator.naturalOrder());
                switch (format) {
                    case FORMAT_24:
                        for(TimeOfDay t : times){
                            pw.println(t.convertTo24());
                            pw.flush();
                        }
                        break;
                    case FORMAT_AMPM:
                        for(TimeOfDay t : times){
                            pw.println(t.convertToAMPM());
                            pw.flush();
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }