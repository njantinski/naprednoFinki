package mk.ukim.finki.napredno.lab.lab2;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

class InsufficientElementsException extends Exception{
    public InsufficientElementsException(){
        super("Insufficient number of elements");
    }
}

class InvalidRowNumberException extends Exception{
    public InvalidRowNumberException(){
        super("Invalid row number");
    }
}

class InvalidColumnNumberException extends Exception{
    public InvalidColumnNumberException(){
        super("Invalid column number");
    }
}
final class DoubleMatrix{
    private final double[][] matrix;
    private final int m;
    private final int n;

    DoubleMatrix(double[] arr, int m, int n) throws InsufficientElementsException {
        if(arr.length < m * n)
            throw new InsufficientElementsException();

        this.m = m;
        this.n = n;
        this.matrix = new double[m][n];


        if(arr.length > m*n){
            int start = arr.length - (m * n) ;

            for(int i = 0; i < m ;i++){
                for(int j = 0; j < n; j++){
                    matrix[i][j] = arr[start++];
                }
            }
        }
        else{
            int counter=0;
            for(int i=0;i<m;i++) {
                for(int j=0;j<n;j++) {
                    matrix[i][j]=arr[counter];
                    counter++;
                }
            }

        }
    }
    public String getDimensions(){
        return String.format("[%d x %d]",m,n);
    }

    public int rows(){
        return this.m ;
    }

    public int columns(){
        return this.n;
    }

    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if(row > m || row < 1)
            throw new InvalidRowNumberException();

        double max = matrix[row-1][0];
        for(int i = 1; i < n; i++) {
            if (matrix[row-1][i] > max){
                max = matrix[row-1][i];
            }
        }
        return max;
    }
    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if((column < 1) || (column > n))
            throw new InvalidColumnNumberException();

        double max = matrix[0][n - 1];
        for(int i = 1; i < m; i++) {
            if (matrix[i][n-1] > max){
                max = matrix[i][column -1];
            }
        }
        return max;
    }
    public double sum(){
        return Arrays.stream(matrix).map(e -> Arrays.stream(e).sum()).reduce(0.0,(e,s) -> e +=s);
    }

    public double[] toSortedArray(){
        // pomnozi ja celata niza so -1, sortiraj ja vo rastecki redosled, pomnozi ja pak so -1
        // Arrays.sort(a, Collections.reverseOrder()); ne raboti so primitivni tipovi kako int, double..
        double[] array = new double[m*n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                array[( i * n ) + j ] = matrix[i][j];

        for (int i=0; i<array.length; i++){
            array[i] *= -1;
        }
        Arrays.sort(array);
        for (int i=0; i<array.length; i++){
            array[i] *= -1;
        }
        return array;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(matrix);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DoubleMatrix other = (DoubleMatrix)obj;
        if (!Arrays.deepEquals(matrix, other.matrix))
            return false;
        return true;
    }

    public String toString(){
        DecimalFormat format = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(format.format(matrix[i][j]));
                if (j != n - 1) {
                    sb.append("\t");
                } else {
                    sb.append("");
                }
            }
            if(i != m -1)
                sb.append("\n");
        }
        return sb.toString();
    }
}
class MatrixReader{
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner in = new Scanner(input);
        int m = in.nextInt();
        int n = in.nextInt();
        double inputArray[] =new double[m * n];
        int i=0;
        while(in.hasNextDouble()){
            inputArray[i++] = in.nextDouble();
        }

        return new DoubleMatrix(inputArray,m,n);
    }
}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode()&&f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode()&&f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}

