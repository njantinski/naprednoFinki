package mk.ukim.finki.napredno.ispitni;




import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


// negde mi vadi plus za edna vrednost na vtorata decimala,
// probav so sekakvi formtiranja, nejke nikako ne znam zasto


public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}

enum Type{
    A,B,V
}

class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(int price){
        super(String.format("Receipt with amount %d is not allowed to be scanned",price));
    }
}

class Item{
    private int price;
    private Type type;
    private double ddvReturn;

    public Item(int price, Type type) {
        this.price = price;
        this.type = type;
        this.ddvReturn = calculateDdvReturn();
    }

    private double calculateDdvReturn() {
        double ddv = 0;
        switch(type){
            case A:
                ddv = ((double)price/100) * 18;
                break;
            case B:
                ddv = ((double)price/100) * 5;
                break;
            case V:
                ddv = ((double)price/100) * 0;
                break;

        }
        return (ddv/100)* 15;
    }

    public static Item readItem(int price, Type type){
        return new Item(price,type);
    }

    public int getPrice() {
        return price;
    }

    public Type getType() {
        return type;
    }

    public double getDdvReturn() {
        return ddvReturn;
    }
}

class Receipt{
    private int id;
    private List<Item> items;
    private int totalSum;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public Receipt(int id) {
        this.id = id;
        this.items = new ArrayList<>();
        totalSum = 0;
    }

    public void addItem(Item item){
        items.add(item);
        totalSum+=item.getPrice();
    }

    public static Receipt readReceipt(String line){

        String[] input = line.split("\\s+");
        int id = Integer.parseInt(input[0]);
        Receipt newReceipt = new Receipt(id);

        for(int i = 1; i < input.length;i+=2){
          int price = Integer.parseInt(input[i]);
          Type type = Type.valueOf(input[i+1]);
          newReceipt.addItem(Item.readItem(price,type));
        }

        return newReceipt;
    }

    public int getId() {
        return id;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public double taxReturn(){
        return items.stream().mapToDouble(i -> i.getDdvReturn()).sum();
    }

    public String toString(){

        return String.format("%d %d %.2f",id,totalSum,taxReturn());
    }
}

class MojDDV{
    private List<Receipt> receipts;

    public MojDDV() {
        receipts = new ArrayList<>();
    }

    public void addReceipt(Receipt receipt) throws AmountNotAllowedException {
        if(receipt.getTotalSum() > 30000){
            throw new AmountNotAllowedException(receipt.getTotalSum());
        }
        receipts.add(receipt);
    }

    public void readRecords (InputStream inputStream){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while( (line = br.readLine()) != null ){
                Receipt readReceipt = Receipt.readReceipt(line);
                try{
                    addReceipt(readReceipt);
                } catch (AmountNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printTaxReturns (OutputStream outputStream){
        try(PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream))){
            receipts.stream().forEach(pw::println);
        }
    }
}