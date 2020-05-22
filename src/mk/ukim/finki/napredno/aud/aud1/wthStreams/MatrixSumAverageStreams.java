package mk.ukim.finki.napredno.aud.aud1.wthStreams;

import java.util.Arrays;

public class MatrixSumAverageStreams {
    public static double sum(double[][] a){
            return Arrays.stream(a).mapToDouble(row -> Arrays.stream(row).sum()).sum();
    }
}
