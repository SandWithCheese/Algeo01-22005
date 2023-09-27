import java.util.Scanner;

import matrix.Matrix;
import matrix.determinan.Kofaktor;
import matrix.balikan.Adjoin;

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

            Adjoin myKofaktor = new Adjoin();
            System.out.println("Hasil Matriks Inverse: ");

            Matrix result = myKofaktor.inverseAdjoin(M1, M1.getRow(), M1.getCol());
            result.displayMatrix();
        } finally {
            scanner.close();
        }

    }
}
