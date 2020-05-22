package LambdaYoutube;


// problemi vo oop
// vo oop se e objekt, ne moze funkcionalnost
// da postoi vo izolacija, a da ne e del
// od nekoj objekt

/* primer */

class Greeter{

    public void greeting(Greet Greet){
        Greet.perform();
    }

    public static void main(String[] args) {

        // ova e pred java 7
        Greeter greeter = new Greeter();
        HelloWorldGreeting hello = new HelloWorldGreeting();
        greeter.greeting(hello);
        // ne davame odnesuvanje tuku davame objekt
        // koj go diktira odnesuvanjeto
        // kako da ja preneseme samo akcijata

        // preku lambda


    }
}
public class Lambda {


    // lambda kreira entiteti koi ne se metodi
    // tuku samo se funkcii koi postojat
    // vo izolacija i mozat da se tretiraat
    // kako vrednosti

    // da dademe na varijabla del od kod
    // koj ke se izvrsuva
    // metodot se dodeluva na varijablata










}
