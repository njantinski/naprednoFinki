package mk.ukim.finki.napredno.lab.lab3;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

enum PizzaType{
    Standard , Pepperoni , Vegetarian
}

enum ExtraType{
    Coke, Ketchup
}

class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException(String type){
        super(String.format("Invalid extra type: " + type));
    }
}

class InvalidPizzaTypeException extends Exception{
    public InvalidPizzaTypeException(String type){
        super(String.format("Invalid pizza type: " + type));
    }
}

class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item){
        super(String.format("ItemOutOfStockException"));
    }
}
class OrderLockedException extends Exception{
    public OrderLockedException(){
        super("OrderLockedException");
    }
}
class EmptyOrder extends Exception{
    public EmptyOrder(){
        super("Empty order");
    }
}


interface Item{
    public int getPrice();
    public String getType();

}



class ExtraItem implements Item{
    private String type;

    public ExtraItem(String type) throws InvalidExtraTypeException {
        if(Arrays.stream(ExtraType.values()).noneMatch(f -> f.toString().equals(type)))
            throw new InvalidExtraTypeException(type);

        this.type = type;
    }

    public int getPrice(){
        if(type.equals("Coke"))
            return 5;
        else return 3;
    }

    public String getType(){
        return this.type;
    }

}

class PizzaItem implements Item{
    private String type;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if(Arrays.stream(PizzaType.values()).noneMatch(f -> f.toString().equals(type)))
            throw new InvalidPizzaTypeException(type);

        this.type = type;
    }

    public int getPrice(){
        if(type.equals("Standard"))
            return 10;
        else if(type.equals("Pepperoni"))
            return 12;
        return 8;
    }
    public String getType(){
        return this.type;
    }
}

class Order{
    private int[] amount;
    private Item[] items;
    private boolean locked;

    public Order(){
        amount = new int[0];
        items = new Item[0];
        locked = false;
    }

    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {

        if(count > 10)
            throw new ItemOutOfStockException(item);
        if(!locked) {
            int num = IntStream.range(0, items.length)
                    .filter(f -> items[f].getPrice() == item.getPrice()).findFirst().orElse(-1);

            if (num != -1)
                amount[num] = count;
            else {
                setNewAmountItems(count);
                setNewItems(item);
            }
        }
        else
            throw new OrderLockedException();

    }


    public int getPrice(){
        return IntStream.range(0, items.length)
                .reduce(0,(sum,num) -> sum += items[num].getPrice() * amount[num]);
    }

    public void removeItem(int index) throws OrderLockedException {
        if(!locked) {
            index = index - 1;
            if (index > items.length - 1)
                throw new ArrayIndexOutOfBoundsException(index);

            Item[] newItems = new Item[this.items.length - 1];
            int[] newAmount = new int[this.amount.length - 1];

            for(int i = 0 ; i < index ; i++){
               newItems[i] = items[i];
               newAmount[i] = amount[i];
           }
            for(int i = index+1; i < items.length;i++){
                newItems[i-1] = items[i];
                newAmount[i-1] = amount[i];
            }
            items = newItems;
            amount = newAmount;
            }

        else
            throw new OrderLockedException();
        }





    public void lock() throws EmptyOrder {
        if(items.length == 0)
            throw new EmptyOrder();

        this.locked = true;
    }

    public void displayOrder(){
        IntStream.range(0,items.length).forEach(f ->{
            String num = String.format("%3d.", f+1);
            String name = String.format("%-15s", items[f].getType());
            String numAmount = "x" + String.format("%2d",amount[f]);
            String price = String.format("%5d", items[f].getPrice() * amount[f]) + "$";
            System.out.println(num + name + numAmount + price);
        });
        String total = String.format("%-22s", "Total:") + String.format("%5d$", getPrice());
        System.out.println(total);
    }
    private void setNewAmountItems(int count){
        int[] newArray = new int[amount.length + 1];
        int i;
        for(i = 0; i < amount.length;i++)
            newArray[i] = amount[i];
        newArray[i] = count;
        amount = newArray;
    }

    private void setNewItems(Item item){
        Item[] newItemArr = new Item[items.length + 1];
        int i;
        for(i = 0; i < items.length;i++){
            newItemArr[i] = items[i];
        }
        newItemArr[i] = item;
        items = newItemArr;
    }
}



public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}
