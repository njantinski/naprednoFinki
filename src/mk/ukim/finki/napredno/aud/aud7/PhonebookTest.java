/*
package mk.ukim.finki.napredno.aud.aud7;

import java.util.*;

public class PhonebookTest {
}

class PhoneBook{
    Set<String> allNumbers;
    Map<String,Set<Contact>> byNumberParts;
    Map<String, Set<Contact>> byName;

    static Comparator<Contact> comparator = Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber);

    public PhoneBook(){
        byNumberParts = new TreeMap<>();
        byName = new HashMap<>();
        allNumbers = new HashSet<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if(allNumbers.contains(number))
            throw new DuplicateNumberException(number);

        Contact contact = new Contact(name,number);
        Set<Contact> contactsByName = byName.computeIfAbsent(name, key -> new TreeSet<>(comparator));
        contactsByName.add(contact);

        List<String> keys = getKeys(number,3);
        for(String key : keys){
            Set<Contact> contactsByNumber = byNumberParts.computeIfAbsent(key, k -> new TreeSet<>(comparator));
            contactsByNumber.add(contact);
        }
    }

    private List<String> getKeys(String number, int minLen) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i <= number.length() - minLen; ++i ){
            for(int len = minLen; len <= (number.length() - i); ++len){
                String k = number.substring(i, i + len);
                result.add(k);
            }
        }
        return result;
    }

    public void contactsByName(String name){
        if(byName.containsKey(name)){
            byName.get(name).forEach(System.out::println);
        }
    }

    public void contactsByNumber(String number){
        if(byNumberParts.containsKey(number)){
            byNumberParts.get(number).forEach(System.out::println);
        }
    }
}


class Contact{
    private String name;
    private String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(number, contact.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String number) {
        super(String.format("Duplicate number: %s", number));
    }
}*/
