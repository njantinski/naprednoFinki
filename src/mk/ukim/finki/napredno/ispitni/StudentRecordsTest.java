package mk.ukim.finki.napredno.ispitni;

import java.io.*;
import java.nio.Buffer;
import java.util.*;
import java.util.stream.IntStream;

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

// your code here

class Student{
    private String code;
    private String major;
    private List<Integer> grades;
    private double average;
    private int[] numGrades;

    public Student(String input) {
        readStudentInput(input);
        DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
        grades.stream().forEach(dss::accept);
        average = dss.getAverage();
        this.numGrades = new int[5];
        for(int i = 0; i < 5; i++){
            numGrades[i] = 0;
        }
        processGrades();
    }

    private void processGrades() {
        grades.stream().forEach(i ->{
            switch(i){
                case 6:
                    numGrades[0]++;
                    break;
                case 7:
                    numGrades[1]++;
                    break;
                case 8:
                    numGrades[2]++;
                    break;
                case 9:
                    numGrades[3]++;
                    break;
                case 10:
                    numGrades[4]++;
                    break;
                default:
                    break;
            }
        });
    }

    public String getCode(){
        return this.code;
    }


    public int[] getStudentGradesDistribution(){
        return numGrades;
    }

    private void readStudentInput(String input) {
        String[] inputs = input.split("\\s+");
        this.code = inputs[0];
        this.major = inputs[1];
        this.grades = new ArrayList<>();
        IntStream.range(2,inputs.length).forEach(i -> grades.add(Integer.parseInt(inputs[i])));
    }
    public double getAverage(){
        return this.average;
    }

    public String getMajor(){
        return this.major;
    }
    public String toString(){
        return String.format("%s %.2f",code,average);
    }

}
class Major implements Comparable<Major>{
    private String code;
    private int[] gradesDistribution;

    public Major(String code) {
        this.code = code;
        gradesDistribution = new int[5];
        for(int i = 0; i < 5; i++){
            gradesDistribution[i] = 0;
        }
    }

    public void updateGrades(int[] studentGrades){
        for(int i = 0; i < 5; i++){
            this.gradesDistribution[i] += studentGrades[i];
        }
    }

    public int getTens(){
        return gradesDistribution[4];
    }

    public int compareTo(Major m){
        if(m.getTens() < getTens()){
            return -1;
        }
        else if(m.getTens() == getTens()){
            return 0;
        }
        return 1;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(code).append("\n");
        for(int i = 0; i < 5; i++){
            sb.append(String.format("%2d",i + 6)).append(" | ");
            int tmp = gradesDistribution[i];
            while(tmp > 0){
                sb.append("*");
                tmp-=10;
            }
            sb.append("(").append(gradesDistribution[i]).append(")").append("\n");
        }
        return sb.toString();
    }
}

class StudentRecords{
    // kluc e nasokata, a vrednosta e mnozestvo na studenti
    // od taa nasoka
    Map<String, Set<Student>> byMajor;
    Set<Major> distribution;
    Map<String, Major> majorsByCode;

    public StudentRecords(){
        byMajor = new TreeMap<>();
        distribution = new TreeSet<>();
        majorsByCode = new HashMap<>();
    }

    public int readRecords(InputStream is){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String input;
            int numOutput = 0;
            while((input = br.readLine() )!= null){
                Student newStudent = new Student(input);
                Set<Student> byMajorSet = byMajor
                        .computeIfAbsent(newStudent.getMajor(), value -> new TreeSet<>(Comparator
                                .comparing(Student::getAverage).reversed().thenComparing(Student::getCode)));
                byMajorSet.add(newStudent);
                numOutput++;
                Major m = majorsByCode.computeIfAbsent(newStudent.getMajor(), value -> new Major(newStudent.getMajor()));
                m.updateGrades(newStudent.getStudentGradesDistribution());
            }
            return numOutput;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void writeTable(OutputStream outputStream){
        Set<String> keys = byMajor.keySet();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream));
        keys.stream().forEach(k ->{
            pw.println(k);
            byMajor.get(k).stream().forEach(student -> pw.println(student.toString()));
        });
        pw.flush();
    }
    //
    public void writeDistribution(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream));
        distribution.addAll(majorsByCode.values());
        distribution.stream().forEach(pw::print);
        pw.flush();

    }



}