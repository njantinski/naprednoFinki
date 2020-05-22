package mk.ukim.finki.napredno.aud.aud5;

import java.util.ArrayList;
import java.util.List;

public class PrioritiQueueTester {
    public static void main(String[] args) {
        PriorityQueue<String> q = new PriorityQueue<>();
        q.add("X",10);
        q.add("Y", 1);
        q.add("Z",3);


        System.out.println(q.peek());
        System.out.println(q.remove());
        System.out.println(q.remove());
        System.out.println(q.remove());
        System.out.println(q.remove());
    }
}

class PriorityQueue<T>{
    private List<T> items;
    private List<Integer> priorities;

    public PriorityQueue(){
        items= new ArrayList<>();
        priorities = new ArrayList<>();
    }

    public void add(T item, int priority){
        items.add(item);
        priorities.add(priority);
    }


    public T remove(){
        int indexToRemove = findNextIndex();
        if(indexToRemove == -1){
            return null;
        }
        else{
            priorities.remove(indexToRemove);
            return items.remove(indexToRemove);
        }
    }
    public T peek(){
        return items.get(findNextIndex());
    }

    private int findNextIndex() {
        int maxElement = priorities.stream().max(Integer::compare).orElse(-1);
        if(maxElement == -1)
            return -1;
        return priorities.indexOf(maxElement);
    }
}
