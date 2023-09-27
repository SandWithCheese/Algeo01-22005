import java.util.Scanner;

import matrix.Matrix;
import matrix.determinan.Kofaktor;

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

            Kofaktor myDet = new Kofaktor();
            System.out.println("Hasil Determinan by Kofaktor: ");

            double result = myDet.detKofaktor(M1);
            System.out.println(result);
        } finally {
            scanner.close();
        }

    }
}
