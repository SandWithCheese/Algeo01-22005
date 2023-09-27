import java.util.Scanner;

import matrix.Matrix;
import matrix.determinan.Kofaktor;
import matrix.balikan.Adjoin;
import matrix.spl.MatriksBalikan;

public class Main {
    public static void main(String[] args) {
        int row, col;

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Masukkan jumlah baris: ");
            row = scanner.nextInt();
            System.out.print("Masukkan jumlah kolom: ");
            col = scanner.nextInt();

            Matrix A = new Matrix(row, col);
            A.readMatrix(scanner); // Pass the scanner as a parameter
            A.displayMatrix();
            System.out.println("");

            Matrix B = new Matrix(row, 1);
            B.readMatrix(scanner); // Pass the scanner as a parameter
            B.displayMatrix();
            System.out.println("");

            System.out.println("Hasil Matriks Inverse: ");

            Matrix result = new MatriksBalikan().SPLInverse(A, B);
            result.displayMatrix();
        } finally {
            scanner.close();
        }
    }
}
