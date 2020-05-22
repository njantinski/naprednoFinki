package mk.ukim.finki.napredno.predavanjeDemo.Lambdas;
interface Run{
    public void run();
}

interface Operation{
    public int operation(int a, int b);
}
class Trcaj{

    public void Trcaj(Run r){
        r.run();
    }
}

interface Print{
    public void print(String message);
}
interface Printa{
   public String printa(String por);
}

class Operacija{
    int a;
    public Operacija(Operation op,int a, int b){
        this.a = op.operation(a,b);
    }
}
class Start{
    public void Start(Runnable r){
        System.out.println("Running");
        r.run();
    }
}

public class nikoProba {
    public static void main(String[] args) {
        Trcaj niko = new Trcaj();
        niko.Trcaj(()-> System.out.println("Krek"));

        Operation mnozi = (a,b) -> a*b;
        Operation deli = (a,b) -> a/b;

        Print Zdravo = (String s) ->System.out.println("Printanata poraka e: " + s);
        System.out.println(mnozi.operation(5,5));

        Zdravo.print("Jedi kurac");

        Printa kreker = ((String porace) -> "mojata poraka sto ja vrakjam e: "
        + porace);

        String oceKurac = kreker.printa("por1");

        Printa oceKurac1 = new Printa(){
            public String printa(String s){
                return s+ "por2";
            }
        };
        String s = oceKurac1.printa("str1 ");
        System.out.println(s);

        Start starting = new Start();
        starting.Start(()-> System.out.println("por3"));
        Operation modul = (a,b) -> a%b;


    }
}
