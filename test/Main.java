import java.util.Scanner;

import matrix.Matrix;
import matrix.spl.GaussJordan;
import matrix.spl.Cramer;

public class Main {
    public static void main(String[] args) {
        int row, col;

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Masukkan jumlah baris: ");
            row = scanner.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            col = scanner.nextInt();

            Matrix M1 = new Matrix(row, col);
            M1.readMatrix();
            M1.displayMatrix();
            System.out.println("");

            Cramer cramer = new Cramer();
            System.out.println("Hasil Cramer: ");
            double[] result = cramer.cramer(M1);
            if (result == null) {
                System.out.println("Tidak ada solusi");
            } else {
                for (int i = 0; i < result.length; i++) {
                    System.out.println("x" + (i + 1) + " = " + result[i]);
                }
            }
        } finally {
            scanner.close();
        }

    }
}
