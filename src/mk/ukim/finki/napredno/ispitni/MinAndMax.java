package mk.ukim.finki.napredno.ispitni;

import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}

class MinMax<T extends Comparable<T>>{
    private T min;
    private T max;
    private int updateCount;

    public MinMax() {
        min = null;
        max = null;
        updateCount = 0;
    }

    public T max(){
        return max;
    }
    public T min(){
        return min;
    }

    public void update(T newElem){
       if(min == null && max == null){
           min = newElem;
           max = newElem;
       }
       else{
           if(checkMin(newElem)){
               min = newElem;
               updateCount++;
               return;
           }
           else if(checkMax(newElem)){
               max = newElem;
               updateCount++;
               return;
           }
       }

    }

    private boolean checkMax(T newElem) {
        return max.compareTo(newElem) < 0;

    }

    private boolean checkMin(T newElem) {
        return min.compareTo(newElem) > 0;
    }

    @Override
    public String toString(){
        return min.toString() + " " + max.toString() + " " + updateCount + "\n";
    }


}