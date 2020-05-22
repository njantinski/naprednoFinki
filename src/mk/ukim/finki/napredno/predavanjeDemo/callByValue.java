package mk.ukim.finki.napredno.predavanjeDemo;

public class callByValue {
    public static void main(String[] args) {
        int a = 5;
        int b = a;
        System.out.println("A: " + a + ", B: " + b);

        b = 3;
        System.out.println("A: " + a + ", B: " + b);

        System.out.println("A: " + a + ", B: " + updateNumb(a));
        System.out.println("A: " + a + ", B: " + b);
    }
    public static int updateNumb(int a){
        return a + a;
    }
    public void printaj(){
        print();
    }
    public static void print(){
        System.out.println("Printam vo staticki metod");
    }
}
