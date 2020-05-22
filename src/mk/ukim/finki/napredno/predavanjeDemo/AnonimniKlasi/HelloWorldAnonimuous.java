package mk.ukim.finki.napredno.predavanjeDemo.AnonimniKlasi;

public class HelloWorldAnonimuous {
    interface HelloWorld{
        public void greet();
        public void greetSomeone(String someone);
    }
    public void sayHello(){
        class EngGreeting implements HelloWorld{
            String name = "world";
            public void greet(){
                greetSomeone("world");
            }
            public void greetSomeone(String someone){
                name = someone;
                System.out.println("Hello " + name);
            }
        }
        HelloWorld MkGreeting = new HelloWorld() {
            String name = "Decki";
            @Override
            public void greet() {
                greetSomeone(name);
            }

            @Override
            public void greetSomeone(String someone) {
                name = someone;
                System.out.println("Zdravo " + name);
            }
        };
        HelloWorld EngGreet = new EngGreeting();
        EngGreet.greet();
        MkGreeting.greet();
    }

    public static void main(String[] args) {
        HelloWorldAnonimuous niko = new HelloWorldAnonimuous();
        niko.sayHello();
    }
}
