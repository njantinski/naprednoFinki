package mk.ukim.finki.napredno.lab.dateDemo;

import java.time.LocalDate;

public class dateDemo {

    public static void main(String[] args) {
        //System.out.println(LocalDateDemo.localDateOfDemo());
        System.out.println(LocalDateDemo.localDateParseDemo("fda-08-21"));
    }
}


class LocalDateDemo{
    public static LocalDate localDateOfDemo(){
        LocalDate test = LocalDate.of(2015,06,18);
        return test;
    }

    public static LocalDate localDateParseDemo(String date){
        return LocalDate.parse(date);
    }
}