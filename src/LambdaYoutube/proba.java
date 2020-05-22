package LambdaYoutube;
interface kurac{
    public void printaj(String s);
}

interface presmetaj{
    public int calculate(int a, int b);
}
public class proba {
    public static void main(String[] args) {
        int a = 5;
        int b = 3;
        kurac aKurac = s -> System.out.println("Niko e zmaj, " + s);
        kurac bKurac = s -> System.out.println("Niko e zverka, " + s);
        String najjak = "najjak";
        aKurac.printaj(najjak);
        bKurac.printaj(najjak);

        presmetaj soberi = (zbir1,zbir2) -> zbir1 + zbir2;
        System.out.println(soberi.calculate(3,3));


    }
}
