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

    public void readMatrix(Scanner scanner) {
        System.out.println("Masukkan nilai matriks: ");
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.data[i][j] = scanner.nextDouble();
            }
        }
    }

    public void displayMatrix() {
        for (int i = 0; i < this.row; i++) {
            System.out.print("| ");
            for (int j = 0; j < this.col; j++) {
                System.out.print(String.format("%20s", data[i][j]));
            }
            System.out.println(String.format("%20s", "|"));
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public double getElement(int row, int col) {
        return this.data[row][col];
    }

    public void setElement(int row, int col, double value) {
        this.data[row][col] = value;
    }

    public boolean isMatrixIdxValid(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }

    public Matrix copyMatrix() {
        Matrix copy = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.data[i], 0, copy.data[i], 0, this.col);
        }

        return copy;
    }

    public boolean isSquareMatrix() {
        return this.row == this.col;
    }

    public boolean isIdentity() {
        if (!this.isSquareMatrix()) {
            return false;
        }

        for (int i = 0; i < this.row; i++) {
            if (this.data[i][i] != 1) {
                return false;
            }

            for (int j = 0; j < this.col; j++) {
                if (i != j && this.data[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public Matrix transpose() {
        Matrix transpose = new Matrix(this.col, this.row);

        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.row; j++) {
                transpose.data[i][j] = this.data[j][i];
            }
        }

        return transpose;
    }
}
