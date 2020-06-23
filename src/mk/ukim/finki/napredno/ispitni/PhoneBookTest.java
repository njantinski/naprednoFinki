package mk.ukim.finki.napredno.ispitni;


import java.util.*;

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

class Contact{
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" ").append(phoneNumber);
        return sb.toString();
    }

    public static Contact createNewContact(String name, String num){
        return new Contact(name,num);
    }
}

class PhoneBook{
    Set<Contact> contacts;
    Map<String, Set<Contact>> byNumber;
    Map<String,Set<Contact>> byName;
    private static final Comparator<Contact> comparatorByNumber = Comparator.comparing(Contact::getPhoneNumber);
    private static final Comparator<Contact> byNameComparator = Comparator.comparing(Contact::getName).thenComparing(Contact::getPhoneNumber);

    public PhoneBook() {
        contacts = new TreeSet<>(Comparator.comparing(Contact::getPhoneNumber));
        byName = new HashMap<>();
        byNumber = new HashMap<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact newContact = Contact.createNewContact(name, number);
        if (contacts.contains(newContact)) {
            throw new DuplicateNumberException(newContact.getPhoneNumber());
        }

        contacts.add(newContact);
        Set<Contact> sameName = byName.computeIfAbsent(newContact.getName(), value -> new TreeSet<>(comparatorByNumber));
        sameName.add(newContact);

        putByNumber(newContact);
    }



    private void putByNumber(Contact newContact) {
        for(int i = 3; i <= newContact.getPhoneNumber().length(); i++){
            for(int j = 0; j <= newContact.getPhoneNumber().length() - i; j++){
                String key = newContact.getPhoneNumber().substring(j,i+j);
                Set<Contact> byNumbers = byNumber.computeIfAbsent(key, value -> new TreeSet<>(byNameComparator));
                byNumbers.add(newContact);
            }
        }
    }

    public void contactsByName(String name){
        if(!byName.containsKey(name)){
            System.out.println("NOT FOUND");
        }
        else {
            Set<Contact> newByName = new TreeSet<>(comparatorByNumber);
            newByName.addAll(byName.get(name));
            byName.put(name,newByName);
            byName.get(name).forEach(System.out::println);
        }
    }

    public void contactsByNumber(String number) {
        if (!byNumber.containsKey(number)) {
            System.out.println("NOT FOUND");
        } else {
            byNumber.get(number).forEach(System.out::println);
        }
    }
}

class DuplicateNumberException extends Exception{
    DuplicateNumberException(String number){
        super(String.format("Duplicate number: %d",number));
    }
}