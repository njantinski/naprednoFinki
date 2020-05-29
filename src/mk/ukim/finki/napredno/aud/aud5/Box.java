package mk.ukim.finki.napredno.aud.aud5;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Box<T> {
    List<T> listOfItems;
    Random randomNum;

    public Box(){
        listOfItems = new ArrayList<>();
        randomNum = new Random();
    }

    public void addItem(T item){
        listOfItems.add(item);
    }

    public boolean isEmtpy(){
        return listOfItems.size() == 0;
    }

    public T drawItem(){
        return listOfItems.get(randomNum.nextInt(listOfItems.size()));
    }

    public static void main(String[] args) {
        Box<String>stringBox=new Box<>();
        stringBox.addItem("Dexter");
        stringBox.addItem("Seinfeld");
        stringBox.addItem("Barney");
        stringBox.addItem("Sheldon");
        stringBox.addItem("Costanza");
        stringBox.addItem("Hank");
        System.out.println(stringBox.drawItem());
    }
}
