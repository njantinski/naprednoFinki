package mk.ukim.finki.napredno.predavanjeDemo;

public class WrapperKlasi {
    /*
    obezbeduvanje na klasen tip za site
    primitivni vrednosti
    ovozmozuva primitivnite vrednosti da se
    odnesuvaat kako klasi

    sodrzat i korisni konstanti i static
    metodi
     */

    public static void main(String[] args) {

        // boxing
        // od primitivna vrednost vo objekt
        // nemaat definicija na konstruktor bez arg

        // ova e preckrtano posto od java 5
        // avtomatski se slucuva ova
        Integer intObject = new Integer(34);

        /*
        unboxing
        od objekt vo primitivna vrednost
         */

        int a = intObject.intValue();

        String stringNum = intObject.toString();
        String br = "443";
        int brOdStr = Integer.valueOf(br);
        System.out.println(brOdStr + 7);
        /*
        string e objekt, odnosno ne e primitiven i zatoa
        se sporeduva so equals
         se sporeduva vrednosta na ascii kodot na stringot
         ako e isti vrakja 1 ako ne vrakja 0
         */
        String proba1 = "Kreker";
        String proba2 = "kreker";
        System.out.println(proba1.equals(proba2));
        /*
        ako sakame leksikogravsko sporeduvanje
        bez razlika na golemi i mali bukvi upotrebuvame
        ignore case
         */
        System.out.println(proba1.equalsIgnoreCase(proba2));

    }
}
