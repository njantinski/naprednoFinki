package mk.ukim.finki.napredno.aud.aud1.wthStreams;

import java.util.stream.IntStream;

public class StringPrefixStreamTester {
    public static boolean isPrefixStream(String str1, String str2){
        if(str1.length() > str2.length())
            return false;

        return IntStream.range(0,str1.length()).allMatch(i -> str1.charAt(i) ==str2.charAt(i));
    }
}
