package mk.ukim.finki.napredno.aud.aud1;

public class StringPrefix {
    public static boolean isPrefix(String str, String pref){
        if(pref.length() > str.length())
            return false;
        if(str.substring(0,pref.length()).equals(pref))
            return true;
        return false;
    }

    public static void main(String[] args) {
        String str = "Matematika";
        String pref = "Mat";
        System.out.println(isPrefix(str,pref));
    }
}
