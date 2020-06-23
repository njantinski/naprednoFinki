package mk.ukim.finki.napredno.ispitni;

import java.util.*;

public class BooksTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}

// Вашиот код овде


class Book{
    private String title;
    private String category;
    private float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String toString(){
        return String.format("%s (%s) %.2f",title,category,price);
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }
}

class BookCollection{
    private Map<String, Set<Book>> byCategory;
    Set<Book> allBooks;

    public BookCollection() {
        byCategory = new HashMap<>();
        allBooks = new HashSet<>();
    }

    public void addBook(Book book){
        allBooks.add(book);
        Set<Book> category = byCategory.computeIfAbsent(book.getCategory(), value -> new HashSet<>());
        category.add(book);
    }

    public void printByCategory(String category){
        if(!byCategory.containsKey(category)){
            return;
        }
        else{
            Set<Book> thisCategory = new TreeSet<>(Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice));
            thisCategory.addAll(byCategory.get(category));
            thisCategory.stream().forEach(System.out::println);
        }
    }

    public List<Book> getCheapestN(int n) {
        List<Book> cheapest = new ArrayList<>();
        cheapest.addAll(allBooks);
        cheapest.sort(Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle));
        if(cheapest.size() < n)
            return cheapest;
        else
            return cheapest.subList(0,n);
    }
}