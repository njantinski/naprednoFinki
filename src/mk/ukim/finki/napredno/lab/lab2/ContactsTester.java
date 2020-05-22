/*
package mk.ukim.finki.napredno.lab.lab2;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

enum Type{
    VIP, TMOBILE,ONE
}

abstract class Contact{
    private int year;
    private int month;
    private int day;

    public Contact(String date){
        this.year = Integer.parseInt(date.substring(0,4));
        this.month = Integer.parseInt(date.substring(5,7));
        this.day = Integer.parseInt(date.substring(8,10));
    }

    public Contact(Contact c){
        this.year = c.getYear();
        this.month = c.getMonth();
        this.day = c.getDay();
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean isNewerThan(Contact c){
        if(getYear() > c.getYear())
            return true;
        else if(getYear() == c.getYear() && getMonth() > c.getMonth())
            return true;
        else if(getYear() == c.getYear() && c.getMonth() == getMonth() && getDay() > c.getDay())
            return true;

        return false;
    }

    abstract public String getType();
}

class EmailContact extends Contact{
    private String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public EmailContact(EmailContact e){
        this(String.format("%d/%2d-%2d",e.getYear(),e.getMonth(),e.getDay()), e.getEmail());
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return "EmailContact";
    }

    @Override
    public String toString() {
        return "EmailContact{" +
                "email='" + email + '\'' +
                '}';
    }
}

class PhoneContact extends Contact{
    private String phone;
    private Type operator;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
        setOperator(phone);
    }

    public PhoneContact(PhoneContact c){
        this(String.format("%d/%2d-%2d",c.getYear(),c.getMonth(),c.getDay()), c.getPhone());
    }

    private void setOperator(String phone){
        if(phone.charAt(2) == '0' || phone.charAt(2) == '1' || phone.charAt(2) == '2')
            this.operator = Type.TMOBILE;
        if(phone.charAt(2) == '5' || phone.charAt(2) == '6')
            this.operator = Type.ONE;
        if(phone.charAt(2) == '7' || phone.charAt(2) == '8')
            this.operator = Type.VIP;
    }

    public String getPhone() {
        return this.phone;
    }

    public Type getOperator() {
        return operator;
    }
    public String getType(){
        return "PhoneContact";
    }


}

class Student{
    private String firstName;
    private String lastName;
    private String city;
    private int age;
    private long index;
    private List<Contact> contacts;


    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.contacts = new ArrayList<>();
    }
    public Student(Student s){
        this(s.getFirstName(),s.getLastName(),s.getCity(),s.getAge(),s.getIndex());
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public void addEmailContact(String date, String email){
        Contact newContact = new EmailContact(date,email);
        contacts.add(newContact);
    }

    public void addPhoneContact(String date, String phone){
        PhoneContact newContact = new PhoneContact(date,phone);
        contacts.add(newContact);
    }

    public EmailContact[] getEmailContacts(){
        List<Contact> mails;
        int count = (int) contacts.stream().filter(c -> c.getType().equals("EmailContact")).count();
        if (count > 0) {
            mails =  contacts.stream()
                    .filter(c -> c.getType().equals("EmailContact"))
                    .collect(Collectors.toList());
            EmailContact[] EmailContacts = new EmailContact[mails.size()];


            for(int i = 0; i < EmailContacts.length; i++){
                EmailContacts[i] = (EmailContact)mails.get(i);
            }
            return EmailContacts;
        }
        return new EmailContact[0];


    }

    public PhoneContact[] getPhoneContacts(){
        List<Contact> phones;
        int count = (int )contacts.stream().filter(c -> c.getType().equals("PhoneContact")).count();
        if (count > 0) {
            phones =  contacts.stream()
                    .filter(c -> c.getType().equals("PhoneContact"))
                    .collect(Collectors.toList());
            PhoneContact[] Phones = new PhoneContact[phones.size()];
            for(int i = 0; i < Phones.length;i++){
                Phones[i] = (PhoneContact) phones.get(i);
            }
            return Phones;
        }

        return new PhoneContact[0];

    }

    public String getCity(){
        return this.city;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public long getIndex(){
        return this.index;
    }

    public Contact getLatestContact(){
        Contact first = contacts.get(0);
        for(int i = 1; i < contacts.size(); i++){
            if(contacts.get(i).isNewerThan(first))
                first = contacts.get(i);
        }
            return  first;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"ime\"").append(":").append("\"" +getFirstName() + "\"").append(", ")
                .append("\"prezime\"").append(":").append("\"" +getLastName() + "\"").append(", ")
                .append("\"vozrast\"").append(":").append( +getAge() ).append(", ")
                .append("\"grad\"").append(":").append("\"" +getCity() + "\"").append(", ")
                .append("\"indeks\"").append(":").append(getIndex() ).append(", ");
        sb.append("\""+ "telefonskiKontakti"+"\"").append(":[");
        if(Arrays.stream(getPhoneContacts()).count() > 0) {
            sb.append(Arrays.stream(getPhoneContacts())
                    .map(p -> p.getPhone())
                    .collect(Collectors.joining("\", \"", "\"", "\"")));
        }

        sb.append("], ").append("\"").append("emailKontakti").append("\"").append(":[");
        if(Arrays.stream(getEmailContacts()).count() > 0) {
            sb.append(Arrays.stream(getEmailContacts()).map(e -> e.getEmail()).collect(Collectors.joining("\", \"", "\"", "\"")));
        }

        sb.append("]").append("}");
        return sb.toString();
    }

    public int getContactsNum(){
        return contacts.size();
    }
}

class Faculty{
    private String name;
    private Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public Student getStudent(long index){
        for(Student s : students){
            if(s.getIndex() == index)
                return s;
        }
        return null;
    }

    public double getAverageNumberOfContacts(){
        int contactNum = 0;
        for(Student s : students){
            contactNum += s.getContactsNum();
        }
        return (double) contactNum/students.length;
    }

    public Student getStudentWithMostContacts(){
        Student max = students[0];
        for(int i = 1; i < students.length;i++){
            if(max.getContactsNum() < students[i].getContactsNum())
                max = students[i];
            if(max.getContactsNum() == students[i].getContactsNum()){
                max = max.getIndex() > students[i].getIndex() ? max : students[i];
            }

        }
        return max;
    }

    public int countStudentsFromCity(String city){
        return (int) Arrays.stream(students).filter(s -> s.getCity().equals(city)).count();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"").append("fakultet").append("\"").append(":").append("\"" + name + "\"").append(", ").append("\"studenti \":[");
        sb.append(Arrays.stream(students).map(s -> s.toString()).collect(Collectors.joining(", ")));
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}



public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("EmailContact"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("PhoneContact"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
*/
