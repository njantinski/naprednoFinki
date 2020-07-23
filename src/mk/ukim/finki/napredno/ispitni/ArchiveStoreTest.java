/*
package mk.ukim.finki.napredno.ispitni;//package mk.ukim.finki.command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        Date date = new Date(113, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            Date dateToOpen = new Date(date.getTime() + (days * 24 * 60
                    * 60 * 1000));
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; ++i) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while(scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch(NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }
}

// вашиот код овде

enum Type{
    LockedArchive,
    SpecialArchive
}



interface dateArchive{
    void setDate(Date dateArchived);
    Type getType();
}



class NonExistingItemException extends Exception{
    public NonExistingItemException(int id){
        super(String.format("Item with id %d doesn't exist",id));
    }
}


class Convert{
    public static ZonedDateTime getZonedDateTime(Date date){
        ZonedDateTime zdt = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return zdt;
    }
}


class GetDateFormat{
    public static DateTimeFormatter getFormat(){
        return  DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy");
    }
}



abstract class Archive implements dateArchive{
    private int id;
    private Date dateArchived;

    public Archive(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    @Override
    public void setDate(Date dateArchived) {
        this.dateArchived = dateArchived;
    }
}

class LockedArchive extends Archive{
    private Date dateToOpen;
    private Type type;

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
        type = Type.LockedArchive;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }

    public Type getType(){
        return type;
    }


}

class SpecialArchive extends Archive{
    private int maxOpen;
    private Type type;
    private int timesOppened;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
        this.type = Type.SpecialArchive;
        this.timesOppened = 0;
    }


    public int getMaxOpen() {
        return maxOpen;
    }
    public int getTimesOppened(){
        return this.timesOppened;
    }
    public void increment(){
        timesOppened++;
    }

    public Type getType(){
        return this.type;
    }
}

class ArchiveStore{
    private List<Archive> archiveList;
    private List<String> messages;

    public ArchiveStore(){
        archiveList = new ArrayList<>();
        messages = new ArrayList<>();
    }


    public void archiveItem(Archive item, Date date){
        archiveList.add(item);
        item.setDate(date);
        messages.add(archiveItemMessage(item,date));
    }



    private String archiveItemMessage(Archive item, Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append("Item ").append(item.getId()).append(" archived at ").append(getDateString(date));
        return sb.toString();
    }

    private String getDateString(Date date) {
        ZonedDateTime zdt = Convert.getZonedDateTime(date);
        zdt.format(GetDateFormat.getFormat());
        return GetDateFormat.getFormat().format(zdt).replaceAll("\\+0000", "UTC");
    }

    public void openItem(int id, Date date) throws NonExistingItemException {
        if(findArchiveById(id) == null){
            throw new NonExistingItemException(id);
        }
        messages.add(openItemMessage(id,date));
    }


    private String openItemMessage(int id, Date date) {
        Archive item = archiveList.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
        Type type = item.getType();
        String message = "";

        switch(type){
            case LockedArchive:
                message = lockedArchiveMessage((LockedArchive) item,date);
                break;
            case SpecialArchive:
                message = speacialArchiveMessage((SpecialArchive) item);
                break;
        }

        return message;

    }

    private String speacialArchiveMessage(SpecialArchive item) {
        if(item.getMaxOpen() == item.getTimesOppened()){
            return String.format("Item %d cannot be opened more than %d times",item.getId(),item.getMaxOpen());
        }
        else{
            item.increment();
            return getStringDate(item.getId(), item.getDateArchived(), item);
        }
    }

    private String lockedArchiveMessage(LockedArchive item, Date date) {
        if(item.getDateToOpen().compareTo(date) > -1){
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("Item %d cannot be opened before ", item.getId()));
            ZonedDateTime zdt = Convert.getZonedDateTime(item.getDateToOpen());

            sb.append(GetDateFormat.getFormat().format(zdt).replaceAll("\\+0000", "UTC"));
            return sb.toString();
        }
        else{
            return getStringDate(item.getId(), item.getDateArchived(), (Archive)item);

        }

    }

    private String getStringDate(int id, Date dateArchived, Archive item) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Item %d opened at ", id));
        ZonedDateTime zdt = Convert.getZonedDateTime(dateArchived);
        //zdt.format(GetDateFormat.getFormat());
        sb.append(GetDateFormat.getFormat().format(zdt).replaceAll("\\+0000", "UTC"));
        return sb.toString();
    }

    private Archive findArchiveById(int id){
        return archiveList.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    public String getLog(){
        StringBuilder sb = new StringBuilder();
        messages.forEach(m -> sb.append(m).append("\n"));
        return sb.toString();
    }
}

*/
