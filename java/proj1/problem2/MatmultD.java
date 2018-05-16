import java.util.*;
import java.lang.*;

// command-line execution example) java MatmultD 6 < mat500.txt
// 6 means the number of threads to use
// < mat500.txt means the file that contains two matrices is given as standard input
//
// In eclipse, set the argument value and file input by using the menu [Run]->[Run Configurations]->{[Arguments], [Common->Input File]}.

// Original JAVA source code: http://stackoverflow.com/questions/21547462/how-to-multiply-2-dimensional-arrays-matrix-multiplication
class Matrix {
    public int from;
    public int to;
    public int[][] matrice = null;

    Matrix(int from, int to, int[][] matrice) {
        this.from = from;
        this.to = to;
        this.matrice = matrice;
    }
}

class MyClass extends Thread {
    private int[][] aMatrice;
    private int[][] bMatrice;
    private int from;
    private int to;
    private int id;
    public Matrix resMatrice = null;

    MyClass(int[][] aMatrice, int[][] bMatrice, int from, int to, int id) {
        this.aMatrice = aMatrice;
        this.bMatrice = bMatrice;
        this.from = from;
        this.to = to;
        this.id = id;
    }

    private Matrix multMatrix() {
        if(this.aMatrice.length == 0) return null;
        if(this.aMatrice[0].length != this.bMatrice.length) return null; // invalid dims

        int n = this.aMatrice[0].length;
        int p = this.bMatrice[0].length;
        int ans[][] = new int[this.aMatrice.length][p];

        for(int i = this.from; i < this.to; i++) {
            for(int j = 0; j < p; j++) {
                for(int k = 0; k < n; k++) {
                    ans[i][j] += this.aMatrice[i][k] * this.bMatrice[k][j]; // calc the matrice
                }
            }
        }
        return new Matrix(this.from, this.to, ans);
    }

    public void run() {
        long startTime = System.currentTimeMillis();

        this.resMatrice = this.multMatrix();

        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;

        System.out.println("Execution Time in thread " + this.id + " : " + timeDiff + "ms");
    }
}

public class MatmultD
{
    private static Scanner sc = new Scanner(System.in);

    public static void main(String [] args)
    {
        int thread_no = 0;
        if (args.length == 1) thread_no = Integer.valueOf(args[0]);
        else thread_no = 1;

        int a[][] = readMatrix();
        int b[][] = readMatrix();
        int res[][] = new int[a.length][a[0].length];

        // start calc time
        long startTime = System.currentTimeMillis();

        MyClass[] myClasses = new MyClass[thread_no];
        for (int i = 0; i < thread_no; ++i) {
            if (i == thread_no - 1) {
                myClasses[i] = new MyClass(a, b, (a.length / thread_no) * i, (a.length / thread_no) * (i + 1) + (a.length % thread_no), i);
            } else {
                myClasses[i] = new MyClass(a, b, (a.length / thread_no) * i, (a.length / thread_no) * (i + 1), i);
            }
            myClasses[i].start();
        }
        for (int i = 0; i < thread_no; ++i) {
            try {
                myClasses[i].join();
                // Recreate matrice
                Matrix c = myClasses[i].resMatrice;
                for (int x = c.from; x < c.to; x++) {
                    for (int y = 0; y < c.matrice[0].length; y++) {
                        res[x][y] = c.matrice[x][y];
                    }
                }
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }

        long endTime = System.currentTimeMillis();

        printMatrix(res);

        System.out.printf("[thread_no]:%2d , [Time]:%4d ms\n", thread_no, endTime-startTime);
    }

    public static int[][] readMatrix() {
        int rows = sc.nextInt();
        int cols = sc.nextInt();
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = sc.nextInt();
            }
        }
        return result;
    }

    public static void printMatrix(int[][] mat) {
        if (mat == null) {
            System.out.println("The matrice cannot be print.");
            return;
        }
        // System.out.println("Matrix[" + mat.length + "][" + mat[0].length + "]");
        int rows = mat.length;
        int columns = mat[0].length;
        int sum = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                // System.out.printf("%4d " , mat[i][j]);
                sum+=mat[i][j];
            }
             // System.out.println();
        }
        System.out.println();
        System.out.println("Matrix Sum = " + sum + "\n");
    }
}
