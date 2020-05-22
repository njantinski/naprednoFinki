package mk.ukim.finki.napredno.predavanjeDemo.Lambdas;
interface Executable{
    void execute();
}
interface  ExecutableInt{
    int execute();
}

class Runner{
    public void run(Executable e){
        System.out.println("Executing code block... ");
        e.execute();
    }
    public void runInt(ExecutableInt e){
        System.out.println("Executing code block... ");
        int value = e.execute();
        System.out.println("Return value is: " + value);
    }
}


public class LambdaDemo {
    public static void main(String[] args) {
        Runner runner = new Runner();
     /*   runner.run(new Executable() {
                       public void execute() {
                           System.out.println("Hello there");
                       }
                   }
        );
       */
        System.out.println(".................................");

        runner.run(() -> {
            System.out.println("Hello there");
            System.out.println(5+5);
            System.out.println("Lambda expression");
        });


        Runner runner2 = new Runner();
        runner2.runInt(() -> 8*8);

    }

}
