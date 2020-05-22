package mk.ukim.finki.napredno.aud.aud4;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OcenkiTester {


    public static void main(String[] args) throws IOException {
        Students listOfStudents = new Students(readStudents("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\aud\\aud4\\readGrades.txt"));
        listOfStudents.printStudents(new File("C:\\Users\\Nikolaj\\Desktop\\JavaPrograms\\napredno\\src\\mk\\ukim\\finki\\napredno\\aud\\aud4\\RezultatiOdIspit.txt"));
    }




    public static List<Student> readStudents(String path) throws IOException {
        File f = validateFile(path);
        BufferedReader br = null;
        List<Student> studentsList;
        try{
            br = new BufferedReader(new FileReader(f));
            String line;

            studentsList = br.lines().map(Student::createNewStudent).collect(Collectors.toList());
            return studentsList;
        }
        finally{
            if(br != null)
                br.close();
        }
    }
    private static File validateFile(String path) throws FileNotFoundException {
        File f = new File(path);
        if(!f.exists())
            throw new FileNotFoundException("File doesn't exists");

        return f;
    }
}


class Student implements Comparable<Student>{
    private String firstName;
    private String lastName;
    private int exam1,exam2,exam3;
    private char grade;
    private int finalScore;

    public Student(String lastName, String firstName, int exam1, int exam2, int exam3) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
        this.finalScore = (int)(exam1 * 0.25 + exam2 * 0.3 + exam3 * 0.45);
        this.grade = setGrade(exam1,exam2,exam3);
    }

    private char setGrade(int exam1, int exam2, int exam3) {

        if(finalScore < 60)
            return 'F';
        else if(finalScore < 70)
            return 'D';
        else if(finalScore < 80)
            return 'C';
        else if (finalScore < 90)
            return 'B';
        else return 'A';
    }

    public static Student createNewStudent(String line){
        String[] studentArg = line.split("(:)");
        Student newStudent = new Student(studentArg[0],studentArg[1],Integer.parseInt(studentArg[2]),
                Integer.parseInt(studentArg[3]),Integer.parseInt(studentArg[4]));
        return newStudent;
    }

    public String toString(){
        return this.lastName + " " + this.firstName + " " +this.grade;
    }

    public String fileToString(){
        return this.lastName + " " + this.firstName + " " + exam1 +" "+ exam2 +" " + exam3 +" "+ finalScore +" " +this.grade;
    }

    public int getFinalScore(){
        return this.finalScore;
    }

    public char getGrade(){
        return this.grade;
    }
    @Override
    public int compareTo(Student otherStudent){
        return Double.compare(getFinalScore(),otherStudent.getFinalScore());
    }

}

class Students{
    private List<Student> listStudents;

    public Students(List<Student> students) {
        listStudents = new ArrayList<>(students);
        listStudents.sort(Comparator.reverseOrder());
    }

    public void printStudents(File file) throws IOException {
        printToConsole();
        printToFile(file);
    }

    private void printToConsole(){
        listStudents.stream().forEach(System.out::println);
    }

    private void printToFile(File f) throws IOException {
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new FileWriter(f));
            PrintWriter finalPw = pw;
            listStudents.forEach(s -> finalPw.println(s.fileToString()));

            int count;
            count = (int) listStudents.stream().filter(s -> s.getGrade() == 'A').count();
            pw.println("A " + count);
            count = (int) listStudents.stream().filter(s -> s.getGrade() == 'B').count();
            pw.println("B " + count);
            count = (int) listStudents.stream().filter(s -> s.getGrade() == 'C').count();
            pw.println("C " + count);
            count = (int) listStudents.stream().filter(s -> s.getGrade() == 'D').count();
            pw.println("D " + count);
            count = (int) listStudents.stream().filter(s -> s.getGrade() == 'F').count();
            pw.println("F " + count);
            finalPw.flush();
            finalPw.close();
        }
        finally{
            if (pw != null)
                pw.close();
        }
    }





}