/*
package mk.ukim.finki.napredno.ispitni;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

class NonExistingItemException extends Exception{
    public NonExistingItemException(int id){
        super(String.format("Item with id %d doesn't exist",id));
    }
}



interface dateArchive{
    void setDate(Date dateArchived);
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

    public LockedArchive(int id, Date dateToOpen) {
        super(id);
        this.dateToOpen = dateToOpen;
    }

    public Date getDateToOpen() {
        return dateToOpen;
    }


}

class SpecialArchive extends Archive{
    private int maxOpen;

    public SpecialArchive(int id, int maxOpen) {
        super(id);
        this.maxOpen = maxOpen;
    }

    public int getMaxOpen() {
        return maxOpen;
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

    public void openItem(int id, Date date) throws NonExistingItemException {
        if(findArchiveById(id) == null){
            throw new NonExistingItemException(id);
        }
        messages.add(openItemMessage(id,date));
    }

    private String openItemMessage(int id, Date date) {


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
