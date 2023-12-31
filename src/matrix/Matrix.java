package matrix;

import java.util.Scanner;
import java.io.File;
import java.text.DecimalFormat;

public class Matrix {
    private final int row;
    private final int col;
    private final double[][] data;

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        data = new double[row][col];
    }

    /**
     * Membaca matriks dari input user
     */
    public void readMatrix(Scanner scanner) {
        System.out.println("Masukkan nilai matriks: ");
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                this.data[i][j] = scanner.nextDouble();
            }
        }
    }

    /**
     * Membaca matriks dari file
     */
    public Matrix readMatrixFromFile(String pathToFile) {
        try {
            int row, col;
            File file1 = new File(pathToFile);
            Scanner scanner1 = new Scanner(file1);
            row = 0;
            col = 0;
            while (scanner1.hasNextLine()) {
                String data = scanner1.nextLine();
                String[] array = data.split(" ");
                col = array.length;
                row += 1;
            }
            scanner1.close();

            Matrix matrix = new Matrix(row, col);
            File file2 = new File(pathToFile);
            Scanner scanner2 = new Scanner(file2);
            int mrow = 0;
            while (scanner2.hasNextLine()) {
                String data = scanner2.nextLine();
                String[] array = data.split(" ");
                for (int i = 0; i < array.length; i++) {
                    matrix.setElement(mrow, i, Double.parseDouble(array[i]));
                }
                mrow += 1;
            }

            matrix.displayMatrix();
            scanner2.close();

            return matrix;
        } catch (Exception e) {
            if (e.toString().contains("FileNotFoundException")) {
                System.out.println("File tidak ditemukan");
            } else {
                System.out.println("Input matriks tidak valid");
            }
            return null;
        }
    }

    /**
     * Membaca titik interpolasi dari file
     */
    public Matrix readInterpolMatrixFromFile(String pathToFile) {
        try {
            int row, col;
            File file1 = new File(pathToFile);
            Scanner scanner1 = new Scanner(file1);
            row = 0;
            col = 2;
            while (scanner1.hasNextLine()) {
                String data = scanner1.nextLine();
                String[] array = data.split(" ");
                if (array.length == 2) {
                    row++;
                }
            }
            scanner1.close();

            Matrix matrix = new Matrix(row, 2);
            File file2 = new File(pathToFile);
            Scanner scanner2 = new Scanner(file2);
            int mrow = 0;
            while (scanner2.hasNextLine()) {
                String data = scanner2.nextLine();
                String[] array = data.split(" ");
                if (array.length == 2) {
                    for (int i = 0; i < array.length; i++) {
                        matrix.setElement(mrow, i, Double.parseDouble(array[i]));
                    }
                    mrow++;
                }
            }

            matrix.displayMatrix();
            scanner2.close();

            return matrix;
        } catch (Exception e) {
            if (e.toString().contains("FileNotFoundException")) {
                System.out.println("File tidak ditemukan");
            } else {
                System.out.println("Input matriks tidak valid");
            }
            return null;
        }
    }

    /**
     * Membaca titik uji interpolasi dari file
     */
    public Matrix readInterpolTestFromFile(int n, String pathToFile) {
        try {
            Matrix pointTest = new Matrix(n, 1);
            File file2 = new File(pathToFile);
            Scanner scanner1 = new Scanner(file2);
            int mrow = 0;
            while (scanner1.hasNextLine()) {
                String data = scanner1.nextLine();
                String[] array = data.split(" ");
                if (array.length == 1) {
                    pointTest.setElement(mrow, 0, Double.parseDouble(array[0]));
                    mrow++;
                }
            }
            pointTest.displayMatrix();
            scanner1.close();

            return pointTest;
        } catch (Exception e) {
            if (e.toString().contains("FileNotFoundException")) {
                System.out.println("File tidak ditemukan");
            } else {
                System.out.println("Input titik uji tidak valid");
            }
            return null;
        }
    }

    /**
     * Menampilkan matriks pada terminal
     */
    public void displayMatrix() {
        DecimalFormat df = new DecimalFormat("#.####");
        for (int i = 0; i < this.row; i++) {
            System.out.print("[");
            for (int j = 0; j < this.col; j++) {
                System.out.print(Double.parseDouble(df.format(data[i][j])));
                if (j != this.col - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }

    /**
     * Mengembalikan jumlah baris matriks
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Mengembalikan jumlah kolom matriks
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Mengembalikan elemen baris ke-i dari matriks
     */
    public Matrix getRowElmt(int row) {
        Matrix rowElmt = new Matrix(1, this.col);

        for (int i = 0; i < this.col; i++) {
            rowElmt.data[0][i] = this.data[row][i];
        }

        return rowElmt;
    }

    /**
     * Mengembalikan elemen kolom ke-i dari matriks
     */
    public Matrix getColElmt(int col) {
        Matrix colElmt = new Matrix(this.row, 1);

        for (int i = 0; i < this.row; i++) {
            colElmt.data[i][0] = this.data[i][col];
        }

        return colElmt;
    }

    /**
     * Mengembalikan elemen matriks pada baris dan kolom tertentu
     */
    public double getElement(int row, int col) {
        return this.data[row][col];
    }

    /**
     * Mengubah elemen matriks pada baris dan kolom tertentu
     */
    public void setElement(int row, int col, double value) {
        this.data[row][col] = value;
    }

    /**
     * Mengecek apakah indeks baris dan kolom valid
     */
    public boolean isMatrixIdxValid(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col;
    }

    /**
     * Men-copy matriks ke matriks baru
     */
    public Matrix copyMatrix() {
        Matrix copy = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.data[i], 0, copy.data[i], 0, this.col);
        }

        return copy;
    }

    /**
     * Mengecek apakah matriks merupakan matriks persegi
     */
    public boolean isSquareMatrix() {
        return this.row == this.col;
    }

    /**
     * Mengecek apakah matriks merupakan matriks identitas
     */
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

    /**
     * Mengembalikan transpose dari matriks
     */
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
