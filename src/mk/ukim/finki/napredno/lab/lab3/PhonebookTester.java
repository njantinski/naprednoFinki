
package mk.ukim.finki.napredno.lab.lab3;

// za invalidnameexception e.name
// za read text files
// za streamot getContactNumbers(String prefix)

// konsultacii petok od 15:00


import javax.naming.InvalidNameException;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class PhonebookTester {

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch( line ) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while ( jin.hasNextLine() )
            phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook,text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if ( ! pb.equals(phonebook) ) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while ( jin.hasNextLine() ) {
            String command = jin.nextLine();
            switch ( command ) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(),jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook.toString());
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while ( jin.hasNextLine() ) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        }
        catch (InvalidNameException e ) {
            System.out.println(e.getMessage()   );
            exception_thrown = true;
        }
        catch ( Exception e ) {}
        if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw InvalidNameException");

/*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */

    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String names_to_test[] = { "And\nrej","asd","AAAAAAAAAAAAAAAAAAAAAA","Ð�Ð½Ð´Ñ€ÐµÑ˜A123213","Andrej#","Andrej<3"};
        for ( String name : names_to_test ) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String numbers_to_test[] = { "+071718028","number","078asdasdasd","070asdqwe","070a56798","07045678a","123456789","074456798","073456798","079456798" };
        for ( String number : numbers_to_test ) {
            try {
                new Contact("Andrej",number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String nums[] = new String[10];
        for ( int i = 0 ; i < nums.length ; ++i ) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej",nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if ( ! exception_thrown ) System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej",getRandomLegitNumber(rnd),getRandomLegitNumber(rnd),getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact.toString());
    }

    static String[] legit_prefixes = {"070","071","072","075","076","077","078"};
    static Random rnd = new Random();

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for ( int i = 3 ; i < 9 ; ++i )
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }

}


class MaximumSizeExceddedException extends Exception{
    public MaximumSizeExceddedException(){
        super("Max size reached!");
    }
}

class InvalidNumberException extends Exception{
    public InvalidNumberException(String phone){
        super(String.format("The phone number: %s is invalid! ", phone));
    }
}

class Contact{
    private String name;
    private List<String> phoneNumbers;

    public Contact(String name, String... phoneNumber) throws InvalidNameException, MaximumSizeExceddedException, InvalidNumberException {
        if(!checkName(name)){
            throw new InvalidNameException(name);
        }
        this.name = name;

        phoneNumbers =new ArrayList<>();

        if(phoneNumber.length > 5)
            throw new MaximumSizeExceddedException();
        for(String phone : phoneNumber){
            if(!checkPhoneNum(phone))
                throw new InvalidNumberException(phone);
            else
                phoneNumbers.add(phone);
        }
        phoneNumbers.sort(String::compareTo);
    }

    public Contact(){}

    public String getName(){
        return this.name;
    }

    public String[] getNumbers(){
          return phoneNumbers.toArray(new String[phoneNumbers.size()]);

    }

    public void addNumber(String phone) throws MaximumSizeExceddedException, InvalidNumberException {
        if(phoneNumbers.size() == 5){
            throw new MaximumSizeExceddedException();
        }
        else if(!checkPhoneNum(phone)){
            throw new InvalidNumberException(phone);
        }
        if(phone != null){
            phoneNumbers.add(phone);
             phoneNumbers.sort(String::compareTo);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.name).append("\n").append(phoneNumbers.size()).append("\n");
        for(String phone : phoneNumbers){
            sb.append(phone).append("\n");
        }
        return sb.toString();
    }

    public static Contact valueOf(String s) throws InvalidNumberException, InvalidNameException, MaximumSizeExceddedException {
        String[] contact = s.split("\\R");
        Contact newValue = new Contact(contact[0]);
        for(int i = 2; i < contact.length;i++){
            newValue.addNumber(contact[i]);
        }
        return newValue;
    }

    private boolean checkPhoneNum(String phone) {
        if(phone.length() == 9){
            for(int i = 0; i < 9; i++){
                if(!Character.isDigit(phone.charAt(i)))
                    return false;
            }
            if(phone.charAt(0) != '0' || phone.charAt(1) != '7' )
                return false;
            if(checkOperator(phone.charAt(2))){
                return true;
            }
        }
        return false;
    }

    private boolean checkOperator(char num) {

        if(num == '3' || num == '9' || num == '4') {
            return false;
        }
        return true;
    }

    private boolean checkName(String name) {
        if (!(name.length() > 4 && name.length() < 10))
            return false;

        for (char c : name.toCharArray()) {
                if(!Character.isLetterOrDigit(c))
                    return false;
            }
            return true;


    }
}

class PhoneBook{
    List<Contact> contacts;

    public PhoneBook(){
        contacts = new ArrayList<>();
    }

    public void addContact(Contact newContact) throws MaximumSizeExceddedException, InvalidNameException {
        if(contacts.size()==250)
            throw new MaximumSizeExceddedException();
        Contact equalsContact = contacts.stream()
                .filter(contact -> contact.getName().equals(newContact.getName()))
                .findFirst().orElse(null);
        if(equalsContact != null){
            throw new InvalidNameException(newContact.getName());
        }
        if(newContact != null)
            contacts.add(newContact);
    }

    public Contact getContactForName(String name){
        return contacts.stream().filter(ph -> ph.getName().equals(name)).findFirst().orElse(null);
    }
    public int numberOfContacts(){
        return contacts.size();
    }

    public Contact[] getContacts(){
        Comparator<Contact> compareContacts = (c1,c2) -> c1.getName().compareTo(c2.getName());
        contacts.sort(compareContacts);

       return contacts.toArray(new Contact[contacts.size()]);
    }
    public boolean removeContact(String name){
        Contact contactToDelete = getContactForName(name);
        if(contactToDelete == null){
            return false;
        }
        contacts.remove(contactToDelete);
        return true;
    }

    @Override
    public String toString(){
        StringBuilder stringToReturn = new StringBuilder();
        contacts.stream().forEach(c-> stringToReturn.append(c.toString()).append("\n"));
        return stringToReturn.toString();
    }

    public static boolean saveAsTextFile(PhoneBook phonebook, String path){
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
        if(!f.isDirectory()){
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(path));
            bw.write(phonebook.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
    public static PhoneBook loadFromTextFile(String path) throws FileNotFoundException, InvalidNumberException, MaximumSizeExceddedException, InvalidNameException {
        PhoneBook phoneBookToReturn = new PhoneBook();
        Scanner sc = new Scanner((new InputStreamReader(new FileInputStream(path))));

        while(sc.hasNext()){
            String name = sc.nextLine();
            int numsOfContacts = sc.nextInt();
            List<String> listOfContacts = new ArrayList<>();


            for(int i = 0; i < numsOfContacts;i++){
                listOfContacts.add(sc.nextLine());
            }
            Contact c = new Contact(name,listOfContacts.toArray(new String[listOfContacts.size()]));

            phoneBookToReturn.addContact(c);
        }
        return phoneBookToReturn;
    }
    Contact[] getContactsForNumber(String number_prefix){
        List<Contact> contactsToReturn =  contacts.stream()
                .filter(contact ->
                        Arrays.stream(contact.getNumbers())
                                .anyMatch(number -> number.contains(number_prefix))).collect(Collectors.toList());

        return contactsToReturn.toArray(new Contact[contactsToReturn.size()]);

    }
}




