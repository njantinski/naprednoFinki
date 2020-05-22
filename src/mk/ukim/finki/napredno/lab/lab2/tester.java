package mk.ukim.finki.napredno.lab.lab2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class tester {
    public static void main(String[] args) {
        int a = 4;
        int b = 2;

        int[] n = { 8,6,0,7,0,3,5,6};
        int start = n.length - (a * b);

        int matrix[][] = new int[a][b];

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                matrix[i][j] = n[start + (i * b) + j];
            }


        }
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++)
                System.out.print(matrix[i][j] + "\t");

            System.out.println();
        }

    }
}
