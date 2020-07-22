package mk.ukim.finki.napredno.ispitni;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde
class Discount{
    private int price;
    private int priceBeforeDiscount;
    private int percentDiscount;
    private int totalDiscount;

    public Discount(int price, int priceBeforeDiscount) {
        this.price = price;
        this.priceBeforeDiscount = priceBeforeDiscount;

        this.percentDiscount = (int)Math.floor(100 -((price/ (double)priceBeforeDiscount) * 100));
        this.totalDiscount = priceBeforeDiscount - price;
    }


    public static Discount createNewDiscount(int price, int priceBeforeDiscount) {
        return new Discount(price,priceBeforeDiscount);
    }

    public int getPercentDiscount(){
       return this.percentDiscount;
    }

    public int getTotalDiscount(){
       return this.totalDiscount;
    }


    public String toString(){
        return String.format("%2s%% %d/%d",getPercentDiscount(),price,priceBeforeDiscount);
    }
}

class Store{
    private String name;
    private List<Discount> discounts;


    Comparator<Discount> sortDiscounts = Comparator.comparing(Discount::getPercentDiscount).thenComparing(Discount::getTotalDiscount).reversed();

    public Store(String name) {
        this.name = name;
        discounts = new ArrayList<>();
    }

    public static Store readNewStore(String line) {
        String[] input = line.split("\\s+");
        Store newStore = new Store(input[0]);

        for(int i = 1; i < input.length; i++){
            String[] discount = input[i].split("(:)");
            newStore.addDiscount(Integer.parseInt(discount[0]),Integer.parseInt(discount[1]));
        }

        return newStore;
    }

    public void addDiscount(int price, int priceAfterDiscount){
        discounts.add(Discount.createNewDiscount(price,priceAfterDiscount));
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        discounts.sort(sortDiscounts);
        sb.append(name).append("\n");
        sb.append(average()).append(total());

        for(int i = 0; i < discounts.size(); i++){
            sb.append(discounts.get(i).toString());
                    if(i < discounts.size() - 1){
                        sb.append("\n");
                    }
        }
        return sb.toString();
    }

    private String average() {
        return String.format("Average discount: %.1f%%\n",getAverageDiscount());
    }

    public double getAverageDiscount() {
        return discounts.stream().mapToDouble(d -> d.getPercentDiscount()).sum() / discounts.size();
    }

    public int getTotalDiscount(){
        return discounts.stream().mapToInt(d -> d.getTotalDiscount()).sum();
    }

    private String total() {
        return "Total discount: " + getTotalDiscount() + "\n";
    }
    public String getName(){
        return this.name;
    }
}

class Discounts{
    private List<Store> stores;
    private Comparator<Store> byAverageDiscount = Comparator.comparing(Store::getAverageDiscount).thenComparing(Store::getName).reversed();
    private Comparator<Store> byTotalDiscount = Comparator.comparing(Store::getTotalDiscount).thenComparing(Store::getName);

    public Discounts() {
        stores = new ArrayList<>();
    }


    public int readStores(InputStream inputStream){
        int counter = 0;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            while((line = br.readLine()) != null){
                stores.add(Store.readNewStore(line));
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public List<Store> byAverageDiscount() {
        stores.sort(byAverageDiscount);
        return stores.stream().limit(3).collect(Collectors.toList());
    }
    public List<Store> byTotalDiscount(){
        stores.sort(byTotalDiscount);
        return stores.stream().limit(3).collect(Collectors.toList());
    }
}