package matrix;

import java.util.Scanner;

public class Matrix {
    private final int row;
    private final int col;
    private final double[][] data;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        data = new double[row][col];
    }

    public void readMatrix() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Masukkan nilai matriks: ");
            for (int i = 0; i < this.row; i++) {
                for (int j = 0; j < this.col; j++) {
                    this.data[i][j] = scanner.nextDouble();
                }
            }
        } finally {
            scanner.close();
        }
    }

    public void displayMatrix() {
        for (int i = 0; i < this.row; i++) {
            System.out.print("| ");
            for (int j = 0; j < this.col; j++) {
                System.out.print(String.format("%8s", data[i][j]));
            }
            System.out.println(String.format("%8s", "|"));
        }
    }
}
