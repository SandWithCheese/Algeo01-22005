import java.util.Scanner;

import matrix.Matrix;
import matrix.balikan.*;
import matrix.determinan.*;
import matrix.spl.*;
import matrix.BicubicSpline;
import matrix.InterpolasiPolinom;
import matrix.RegresiLinearBerganda;
import java.text.DecimalFormat;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
import java.time.LocalDate;

public class Main {
    private static void welcome() {
        System.out.println("Selamat Datang di Program Tugas Besar I\n"
                + "Mata Kuliah IF2123 Aljabar Linier dan Geometri Tahun 2023/2024");

    }

    private static void Menu() {
        System.out.println("\nMENU:");
        System.out.println("1. Sistem Persamaan Linier\n"
                + "2. Determinan\n"
                + "3. Matriks Balikan\n"
                + "4. Interpolasi Polinom\n"
                + "5. Interpolasi Bicubic Spline\n"
                + "6. Regresi Linier Berganda\n"
                + "7. Keluar\n");
        System.out.printf("Masukkan menu pilihan Anda > ");
    }

    private static void subMenu1() {
        boolean exit = false;
        int row, col;
        DecimalFormat df = new DecimalFormat("#.####");

        while (exit == false) {
            System.out.println("\nPenyelesaian Sistem Persamaan Linier");
            System.out.println("1. Metode Eliminasi Gauss\n"
                    + "2. Metode Eliminasi Gauss-Jordan\n"
                    + "3. Metode Matriks Balikan\n"
                    + "4. Metode Kaidah Cramer\n"
                    + "5. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu1 = new Scanner(System.in);
            int pilSubMenu1 = scanSubMenu1.nextInt();

            if (pilSubMenu1 == 1 || pilSubMenu1 == 2 || pilSubMenu1 == 3 || pilSubMenu1 == 4 || pilSubMenu1 == 5) {
                String output = "";
                String save = "";

                switch (pilSubMenu1) {
                    case 5:
                        exit = true;
                        break;
                    case 1:
                        boolean isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        String input = scanSubMenu1.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        Matrix M = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            M = M.readMatrixFromFile(pathToFile);

                            if (M == null) {
                                break;
                            }

                            row = M.getRow();
                            col = M.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu1.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu1.nextInt();
                            M = new Matrix(row, col);
                            M.readMatrix(scanSubMenu1);
                        }

                        System.out.println("");

                        Matrix resultM = new Gauss().gauss(M);
                        Matrix lastRow = resultM.getRowElmt(row - 1);
                        boolean isSolvable = false;

                        for (int i = 0; i < col - 1; i++) {
                            if (lastRow.getElement(0, i) != 0) {
                                isSolvable = true;
                                break;
                            }
                        }

                        if (!isSolvable && lastRow.getElement(0, col - 1) == 0) {
                            isSolvable = true;
                        }

                        resultM.displayMatrix();
                        System.out.println("");
                        df = new DecimalFormat("#.####");

                        output = "";
                        if (isSolvable) {
                            boolean isZeroMatrix = true;
                            for (int i = 0; i < col - 1; i++) {
                                if (lastRow.getElement(0, i) != 0) {
                                    isZeroMatrix = false;
                                    break;
                                }
                            }

                            if (!isZeroMatrix && lastRow.getElement(0, col - 1) == 0) {
                                isZeroMatrix = true;
                            }

                            if (isZeroMatrix) {
                                output += "-> SPL memiliki banyak solusi\n";
                                System.out.println("-> SPL memiliki banyak solusi\n");
                                for (int j = 0; j < row; j++) {
                                    int idx = 0;
                                    for (int i = 0; i < col - 1; i++) {
                                        if (i == col - 2) {
                                            output += String
                                                    .valueOf(Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                    + String.format("(x_%d)", idx + 1);
                                            System.out.print(String
                                                    .valueOf(Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                    + String.format("(x_%d)", idx + 1));
                                        } else {
                                            output += String
                                                    .valueOf(Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                    + String.format("(x_%d)", idx + 1)
                                                    + " + ";
                                            System.out
                                                    .print(String.valueOf(
                                                            Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                            + String.format("(x_%d)", idx + 1)
                                                            + " + ");
                                        }
                                        idx++;
                                    }
                                    output += " = "
                                            + String.valueOf(
                                                    Double.parseDouble(df.format(resultM.getElement(j, col - 1))))
                                            + "\n";
                                    System.out.print(" = " + String
                                            .valueOf(Double.parseDouble(df.format(resultM.getElement(j, col - 1)))));
                                    System.out.println("");
                                }
                            } else {
                                if (col - row >= 2) {
                                    output += "-> SPL memiliki banyak solusi\n";
                                    System.out.println("-> SPL memiliki banyak solusi\n");
                                    for (int j = 0; j < row; j++) {
                                        int idx = 0;
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == col - 2) {
                                                output += String.valueOf(
                                                        Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                        + String.format("(x_%d)", idx + 1);
                                                System.out.print(String.valueOf(
                                                        Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                        + String.format("(x_%d)", idx + 1));
                                            } else {
                                                output += String.valueOf(
                                                        Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                        + String.format("(x_%d)", idx + 1)
                                                        + " + ";
                                                System.out
                                                        .print(String.valueOf(
                                                                Double.parseDouble(df.format(resultM.getElement(j, i))))
                                                                + String.format("(x_%d)", idx + 1)
                                                                + " + ");
                                            }
                                            idx++;
                                        }
                                        output += " = "
                                                + String.valueOf(
                                                        Double.parseDouble(df.format(resultM.getElement(j, col - 1))))
                                                + "\n";
                                        System.out.print(" = " + String.valueOf(
                                                Double.parseDouble(df.format(resultM.getElement(j, col - 1)))));
                                        System.out.println("");
                                    }
                                } else {
                                    output += "-> SPL memiliki solusi unik\n";
                                    System.out.println("-> SPL memiliki solusi unik\n");
                                    resultM = new GaussJordan().gaussJordan(resultM);
                                    for (int j = 0; j < row; j++) {
                                        int idx = 0;
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == j) {
                                                output += String.format("x_%d", idx + 1);
                                                System.out.print(String.format("x_%d", idx + 1));
                                                break;
                                            }
                                            idx++;
                                        }
                                        output += " = " + String.valueOf(df.format(resultM.getElement(j, col - 1)))
                                                + "\n";
                                        System.out.print(
                                                " = " + String.valueOf(df.format(resultM.getElement(j, col - 1))));
                                        System.out.println("");
                                    }
                                }
                            }
                        } else {
                            output += "-> SPL tidak memiliki solusi\n";
                            System.out.println("-> SPL tidak memiliki solusi\n");
                        }

                        System.out.println("Simpan ke dalam file? (Y/N)");
                        save = scanSubMenu1.next().toUpperCase();
                        if (save.equals("Y")) {
                            System.out.println("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            if (pathToFile.equals("")) {
                                LocalDate date = LocalDate.now();
                                pathToFile = "SPL_" + date.toString() + ".txt";
                            }
                            try {
                                FileWriter myWriter = new FileWriter(pathToFile);
                                myWriter.write(output);
                                myWriter.close();
                                System.out.println("Berhasil menyimpan ke dalam file");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan dalam menyimpan file");
                            }
                        }

                        break;
                    case 2:
                        isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        input = scanSubMenu1.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        M = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            M = M.readMatrixFromFile(pathToFile);

                            if (M == null) {
                                break;
                            }

                            row = M.getRow();
                            col = M.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu1.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu1.nextInt();
                            M = new Matrix(row, col);
                            M.readMatrix(scanSubMenu1);
                        }

                        System.out.println("");

                        resultM = new GaussJordan().gaussJordan(M);
                        lastRow = resultM.getRowElmt(row - 1);
                        isSolvable = false;

                        for (int i = 0; i < col - 1; i++) {
                            if (lastRow.getElement(0, i) != 0) {
                                isSolvable = true;
                                break;
                            }
                        }

                        if (!isSolvable && lastRow.getElement(0, col - 1) == 0) {
                            isSolvable = true;
                        }

                        resultM.displayMatrix();
                        System.out.println("");

                        output = "";
                        if (isSolvable) {
                            boolean isZeroMatrix = true;
                            for (int i = 0; i < col - 1; i++) {
                                if (lastRow.getElement(0, i) != 0) {
                                    isZeroMatrix = false;
                                    break;
                                }
                            }

                            if (!isZeroMatrix && lastRow.getElement(0, col - 1) == 0) {
                                isZeroMatrix = true;
                            }

                            if (isZeroMatrix) {
                                output += "-> SPL memiliki banyak solusi\n";
                                System.out.println("-> SPL memiliki banyak solusi\n");
                                for (int j = 0; j < row; j++) {
                                    int idx = 0;
                                    for (int i = 0; i < col - 1; i++) {
                                        if (i == col - 2) {
                                            output += String.valueOf(df.format(resultM.getElement(j, i)))
                                                    + String.format("(x_%d)", idx + 1);
                                            System.out.print(String.valueOf(df.format(resultM.getElement(j, i)))
                                                    + String.format("(x_%d)", idx + 1));
                                        } else {
                                            output += String.valueOf(df.format(resultM.getElement(j, i)))
                                                    + String.format("(x_%d)", idx + 1)
                                                    + " + ";
                                            System.out
                                                    .print(String.valueOf(df.format(resultM.getElement(j, i)))
                                                            + String.format("(x_%d)", idx + 1)
                                                            + " + ");
                                        }
                                        idx++;
                                    }
                                    output += " = " + String.valueOf(df.format(resultM.getElement(j, col - 1))) + "\n";
                                    System.out.print(" = " + String.valueOf(df.format(resultM.getElement(j, col - 1))));
                                    System.out.println("");
                                }
                            } else {
                                if (col - row >= 2) {
                                    output += "-> SPL memiliki banyak solusi\n";
                                    System.out.println("-> SPL memiliki banyak solusi\n");
                                    for (int j = 0; j < row; j++) {
                                        int idx = 0;
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == col - 2) {
                                                output += String.valueOf(df.format(resultM.getElement(j, i)))
                                                        + String.format("(x_%d)", idx + 1);
                                                System.out.print(String.valueOf(df.format(resultM.getElement(j, i)))
                                                        + String.format("(x_%d)", idx + 1));
                                            } else {
                                                output += String.valueOf(df.format(resultM.getElement(j, i)))
                                                        + String.format("(x_%d)", idx + 1)
                                                        + " + ";
                                                System.out
                                                        .print(String.valueOf(df.format(resultM.getElement(j, i)))
                                                                + String.format("(x_%d)", idx + 1)
                                                                + " + ");
                                            }
                                            idx++;
                                        }
                                        output += " = " + String.valueOf(df.format(resultM.getElement(j, col - 1)))
                                                + "\n";
                                        System.out.print(
                                                " = " + String.valueOf(df.format(resultM.getElement(j, col - 1))));
                                        System.out.println("");
                                    }
                                } else {
                                    output += "-> SPL memiliki solusi unik\n";
                                    System.out.println("-> SPL memiliki solusi unik\n");
                                    for (int j = 0; j < row; j++) {
                                        int idx = 0;
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == j) {
                                                output += String.format("x_%d", idx + 1);
                                                System.out.print(String.format("x_%d", idx + 1));
                                                break;
                                            }
                                            idx++;
                                        }
                                        output += " = " + String.valueOf(df.format(resultM.getElement(j, col - 1)))
                                                + "\n";
                                        System.out.print(
                                                " = " + String.valueOf(df.format(resultM.getElement(j, col - 1))));
                                        System.out.println("");
                                    }
                                }
                            }
                        } else {
                            output += "-> SPL tidak memiliki solusi\n";
                            System.out.println("-> SPL tidak memiliki solusi\n");
                        }

                        System.out.println("Simpan ke dalam file? (Y/N)");
                        save = scanSubMenu1.next().toUpperCase();
                        if (save.equals("Y")) {
                            System.out.println("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            if (pathToFile.equals("")) {
                                LocalDate date = LocalDate.now();
                                pathToFile = "SPL_" + date.toString() + ".txt";
                            }
                            try {
                                FileWriter myWriter = new FileWriter(pathToFile);
                                myWriter.write(output);
                                myWriter.close();
                                System.out.println("Berhasil menyimpan ke dalam file");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan dalam menyimpan file");
                            }
                        }

                        break;
                    case 3:
                        isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        input = scanSubMenu1.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        M = new Matrix(0, 0);
                        Matrix A = new Matrix(0, 0);
                        Matrix B = new Matrix(0, 0);

                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            M = M.readMatrixFromFile(pathToFile);

                            if (M == null) {
                                break;
                            }

                            row = M.getRow();
                            col = M.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu1.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu1.nextInt();
                            M = new Matrix(row, col);
                            M.readMatrix(scanSubMenu1);
                        }

                        A = new Matrix(row, col - 1);
                        for (int i = 0; i < row; i++) {
                            for (int j = 0; j < A.getCol(); j++) {
                                A.setElement(i, j, M.getElement(i, j));
                            }
                        }

                        B = new Matrix(row, 1);
                        for (int i = 0; i < B.getRow(); i++) {
                            B.setElement(i, 0, M.getElement(i, M.getCol() - 1));
                        }

                        System.out.println("");

                        output = "";
                        Matrix result = new MatriksBalikan().SPLInverse(A, B);
                        if (result == null) {
                            output += "-> Gagal menghitung solusi! Matriks A tak memiliki balikan\n";
                            System.out.println("-> Gagal menghitung solusi! Matriks A tak memiliki balikan");
                        } else {
                            output += "-> Solusi SPL:\n";
                            System.out.println("-> Solusi SPL:");
                            for (int i = 0; i < row; i++) {
                                output += String.format(
                                        "x_" + (i + 1) + " = " + Double.parseDouble(df.format(result.getElement(i, 0))))
                                        + "\n";
                                System.out.printf("x_" + (i + 1) + " = "
                                        + Double.parseDouble(df.format(result.getElement(i, 0))));
                                System.out.println("");
                            }
                        }

                        System.out.println("Simpan ke dalam file? (Y/N)");
                        save = scanSubMenu1.next().toUpperCase();
                        if (save.equals("Y")) {
                            System.out.println("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            if (pathToFile.equals("")) {
                                LocalDate date = LocalDate.now();
                                pathToFile = "SPL_" + date.toString() + ".txt";
                            }
                            try {
                                FileWriter myWriter = new FileWriter(pathToFile);
                                myWriter.write(output);
                                myWriter.close();
                                System.out.println("Berhasil menyimpan ke dalam file");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan dalam menyimpan file");
                            }
                        }
                        break;
                    case 4:
                        isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        input = scanSubMenu1.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        Matrix M1 = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            M1 = M1.readMatrixFromFile(pathToFile);

                            if (M1 == null) {
                                break;
                            }

                            row = M1.getRow();
                            col = M1.getCol();

                            if (col - row != 1) {
                                System.out.println("-> Tidak bisa dihitung menggunakan metode Cramer\n");
                                break;
                            }
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu1.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu1.nextInt();

                            if (col - row != 1) {
                                System.out.println("-> Tidak bisa dihitung menggunakan metode Cramer\n");
                                break;
                            }

                            M1 = new Matrix(row, col);
                            M1.readMatrix(scanSubMenu1);
                        }

                        System.out.println("");

                        double[] cramer = new Cramer().cramer(M1);

                        output = "";
                        if (cramer != null) {
                            output += "-> Solusi SPL:\n";
                            System.out.println("-> Solusi SPL:");
                            for (int i = 0; i < row; i++) {
                                output += String.format("x_%d = %s", i + 1, df.format(cramer[i])) + "\n";
                                System.out.printf("x_%d = %s", i + 1, df.format(cramer[i]));
                                System.out.println("");
                            }
                        } else {
                            output += "-> Gagal menghitung solusi! Matriks A tak memiliki balikan\n";
                            System.out.println("-> Gagal menghitung solusi! Matriks A tak memiliki balikan");
                        }

                        System.out.println("Simpan ke dalam file? (Y/N)");
                        save = scanSubMenu1.next().toUpperCase();
                        if (save.equals("Y")) {
                            System.out.println("Masukkan nama file: ");
                            String pathToFile = scanSubMenu1.next();

                            if (pathToFile.equals("")) {
                                LocalDate date = LocalDate.now();
                                pathToFile = "SPL_" + date.toString() + ".txt";
                            }
                            try {
                                FileWriter myWriter = new FileWriter(pathToFile);
                                myWriter.write(output);
                                myWriter.close();
                                System.out.println("Berhasil menyimpan ke dalam file");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan dalam menyimpan file");
                            }
                        }
                        break;
                }
            } else {
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }

    private static void subMenu2() {
        boolean exit = false;
        int row, col;
        DecimalFormat df = new DecimalFormat("#.####");

        while (exit == false) {
            System.out.println("\nMenghitung Determinan Matriks Persegi");
            System.out.println("1. Metode Eliminasi Kofaktor\n"
                    + "2. Metode Reduksi Baris\n"
                    + "3. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu2 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu2.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3) {
                switch (pilSubMenu2) {
                    case 3:
                        exit = true;
                        break;
                    default:
                        boolean isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        String input = scanSubMenu2.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        Matrix A = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu2.next();

                            A = A.readMatrixFromFile(pathToFile);

                            if (A == null) {
                                break;
                            }

                            row = A.getRow();
                            col = A.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu2.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu2.nextInt();

                            A = new Matrix(row, col);
                            A.readMatrix(scanSubMenu2);
                        }

                        System.out.println("");

                        String output = "";
                        if (A.isSquareMatrix()) {
                            if (pilSubMenu2 == 1) {
                                double result = new Kofaktor().detKofaktor(A);
                                A.displayMatrix();
                                output += "-> Determinan: " + Double.parseDouble(df.format(result)) + "\n";
                                System.out.println("-> Determinan: " + Double.parseDouble(df.format(result)) + "\n");
                            } else {
                                double result = ReduksiBaris.determinan(A);
                                A.displayMatrix();
                                output += "-> Determinan: " + Double.parseDouble(df.format(result)) + "\n";
                                System.out.println("-> Determinan: " + Double.parseDouble(df.format(result)) + "\n");
                            }
                        } else {
                            output += "Matriks masukan BUKANLAH MATRIKS PERSEGI\n";
                            System.out.printf("Gagal!\nMatriks masukan BUKANLAH MATRIKS PERSEGI\n");
                        }

                        System.out.println("Simpan ke dalam file? (Y/N)");
                        String save = scanSubMenu2.next().toUpperCase();
                        if (save.equals("Y")) {
                            System.out.println("Masukkan nama file: ");
                            String pathToFile = scanSubMenu2.next();

                            if (pathToFile.equals("")) {
                                LocalDate date = LocalDate.now();
                                pathToFile = "Determinan_" + date.toString() + ".txt";
                            }
                            try {
                                FileWriter myWriter = new FileWriter(pathToFile);
                                myWriter.write(output);
                                myWriter.close();
                                System.out.println("Berhasil menyimpan ke dalam file");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan dalam menyimpan file");
                            }
                        }
                }
            } else {
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }

    private static void subMenu3() {
        boolean exit = false;
        int row, col;
        DecimalFormat df = new DecimalFormat("#.####");

        while (exit == false) {
            System.out.println("\nMenghitung Balikan Matriks Persegi");
            System.out.println("1. Metode Adjoin\n"
                    + "2. Metode Gauss Jordan\n"
                    + "3. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu3 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu3.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3) {
                switch (pilSubMenu2) {
                    case 3:
                        exit = true;
                        break;
                    default:
                        boolean isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        String input = scanSubMenu3.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        Matrix A = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu3.next();

                            A = A.readMatrixFromFile(pathToFile);

                            if (A == null) {
                                break;
                            }

                            row = A.getRow();
                            col = A.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu3.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu3.nextInt();

                            A = new Matrix(row, col);
                            A.readMatrix(scanSubMenu3);
                        }

                        System.out.println("");

                        String output = "";
                        df = new DecimalFormat("#.####");
                        if (A.isSquareMatrix()) {
                            if (pilSubMenu2 == 1) {
                                Matrix result = new Adjoin().inverseAdjoin(A);
                                if (result == null) {
                                    output += "-> Tidak Memiliki Matriks Balikan\n";
                                    System.out.println("-> Tidak Memiliki Matriks Balikan\n");
                                } else {
                                    output += "-> Hasil Matriks Balikan:\n";
                                    for (int i = 0; i < result.getRow(); i++) {
                                        for (int j = 0; j < result.getCol(); j++) {
                                            output += String
                                                    .valueOf(Double.parseDouble(df.format(result.getElement(i, j))));
                                            if (j != result.getCol() - 1) {
                                                output += " ";
                                            }
                                        }
                                        output += "\n";
                                    }
                                    System.out.println("-> Hasil Matriks Balikan:");
                                    result.displayMatrix();
                                }
                            } else {
                                Matrix result = new BalikanGaussJordan().balikanGaussJordan(A);
                                if (result == null) {
                                    output += "-> Tidak Memiliki Matriks Balikan\n";
                                    System.out.println("-> Tidak Memiliki Matriks Balikan\n");
                                } else {
                                    output += "-> Hasil Matriks Balikan:\n";
                                    for (int i = 0; i < result.getRow(); i++) {
                                        for (int j = 0; j < result.getCol(); j++) {
                                            output += String
                                                    .valueOf(Double.parseDouble(df.format(result.getElement(i, j))));
                                            if (j != result.getCol() - 1) {
                                                output += " ";
                                            }
                                        }
                                        output += "\n";
                                    }
                                    System.out.println("-> Hasil Matriks Balikan:");
                                    result.displayMatrix();
                                }
                            }
                        } else {
                            output += "Matriks masukan BUKANLAH MATRIKS PERSEGI\n";
                            System.out.printf("Matriks Masukan BUKANLAH MATRIKS PERSEGI\n");
                        }

                        System.out.println("Simpan ke dalam file? (Y/N)");
                        String save = scanSubMenu3.next().toUpperCase();
                        if (save.equals("Y")) {
                            System.out.println("Masukkan nama file: ");
                            String pathToFile = scanSubMenu3.next();

                            if (pathToFile.equals("")) {
                                LocalDate date = LocalDate.now();
                                pathToFile = "Balikan_" + date.toString() + ".txt";
                            }
                            try {
                                FileWriter myWriter = new FileWriter(pathToFile);
                                myWriter.write(output);
                                myWriter.close();
                                System.out.println("Berhasil menyimpan ke dalam file");
                            } catch (IOException e) {
                                System.out.println("Terjadi kesalahan dalam menyimpan file");
                            }
                        }
                }
            } else {
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }

    private static void runInterpol() {
        while (true) {
            int row, col;
            double est;
            boolean next = true;
            DecimalFormat df = new DecimalFormat("#.####");

            Scanner scanInterpol = new Scanner(System.in);

            System.out.println("\nMenginterpolasi Titik");

            boolean isFile = false;

            System.out.printf("Masukan dari terminal (T) atau file (F) > ");
            String input = scanInterpol.next().toUpperCase();

            if (input.equals("F")) {
                isFile = true;
            } else if (!input.equals("F") && !input.equals("T")) {
                System.out.println("Masukan TIDAK VALID\n");
                break;
            }

            Matrix A = new Matrix(0, 0);
            Matrix uji = new Matrix(0, 0);
            if (isFile) {
                System.out.printf("Masukkan nama file: ");
                String pathToFile = scanInterpol.next();

                A = A.readInterpolMatrixFromFile(pathToFile);

                if (A == null) {
                    break;
                }

                row = A.getRow();

                int nUji = new InterpolasiPolinom().countTest(pathToFile);

                if (nUji != 0) {
                    uji = uji.readInterpolTestFromFile(nUji, pathToFile);
                } else {
                    next = false;
                }
            } else {
                System.out.print("Masukkan jumlah titik: ");
                row = scanInterpol.nextInt();

                A = new Matrix(row, 2);
                A.readMatrix(scanInterpol);
            }

            System.out.println("");

            Matrix result = new InterpolasiPolinom().SPLInterpol(A);
            for (int i = 0; i < result.getRow(); i++) {
                result.setElement(i, result.getCol() - 1,
                        Double.parseDouble(df.format(result.getElement(i, result.getCol() - 1))));
            }

            String output = "p" + String.format("%d", row - 1) + "(x) = ";
            System.out.print("p" + (row - 1) + "(x) = ");
            for (int i = 0; i < result.getRow(); i++) {
                if (i == 0) {
                    output += String.valueOf(result.getElement(i, result.getCol() - 1));
                    System.out.print(result.getElement(i, result.getCol() - 1));
                } else if (i == 1) {
                    output += String.valueOf(result.getElement(i, result.getCol() - 1)) + "(x)";
                    System.out.print(result.getElement(i, result.getCol() - 1) + "(x)");
                } else {
                    output += String.valueOf(result.getElement(i, result.getCol() - 1)) + "(x^" + i + ")";
                    System.out.print(result.getElement(i, result.getCol() - 1) + "(x^" + i + ")");
                }

                if (i != result.getRow() - 1) {
                    output += " + ";
                    System.out.print(" + ");
                }
            }
            output += "\n";
            System.out.println("");

            while (next) {
                if (isFile == true) {
                    for (int i = 0; i < uji.getRow(); i++) {
                        est = 0.0;
                        for (int j = 0; j < result.getRow(); j++) {
                            est = est + result.getElement(j, result.getCol() - 1) * (Math.pow(uji.getElement(i, 0), j));
                        }
                        output += "p" + String.format("%d", row - 1) + "(" + uji.getElement(i, 0) + ") = "
                                + Double.parseDouble(df.format(est)) + "\n";
                        System.out.println("p" + (row - 1) + "(" + uji.getElement(i, 0) + ") = "
                                + Double.parseDouble(df.format(est)));
                    }
                    next = false;
                } else {
                    System.out.printf("\nPrediksi nilai titik Ya (Y) atau Tidak (T) > ");
                    String ujiInterpol = scanInterpol.next().toUpperCase();

                    if (!input.equals("Y") && !input.equals("T")) {
                        output += "Masukan TIDAK VALID\n";
                        System.out.println("Masukan TIDAK VALID\n");
                        break;
                    }

                    if (ujiInterpol.equals("Y")) {
                        est = 0.0;

                        System.out.print("\nMasukkan titik (x) yang ingin diestimasi: ");
                        Double x = scanInterpol.nextDouble();

                        for (int i = 0; i < result.getRow(); i++) {
                            est = est + result.getElement(i, result.getCol() - 1) * (Math.pow(x, i));
                        }

                        output += "p" + String.format("%d", row - 1) + "(" + x + ") = "
                                + Double.parseDouble(df.format(est)) + "\n";
                        System.out.println("p" + (row - 1) + "(" + x + ") = " + Double.parseDouble(df.format(est)));
                    } else {
                        break;
                    }
                }
            }

            System.out.println("Simpan ke dalam file? (Y/N)");
            String save = scanInterpol.next().toUpperCase();
            if (save.equals("Y")) {
                System.out.println("Masukkan nama file: ");
                String pathToFile = scanInterpol.next();

                if (pathToFile.equals("")) {
                    LocalDate date = LocalDate.now();
                    pathToFile = "InterpolasiPolinom_" + date.toString() + ".txt";
                }
                try {
                    FileWriter myWriter = new FileWriter(pathToFile);
                    myWriter.write(output);
                    myWriter.close();
                    System.out.println("Berhasil menyimpan ke dalam file");
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan dalam menyimpan file");
                }
            }

            break;
        }
    }

    private static void runBicubic() {
        while (true) {
            BicubicSpline bicubic = new BicubicSpline();
            Matrix X = bicubic.bicubicSpline();
            boolean isFile = false;
            DecimalFormat df = new DecimalFormat("#.####");

            Scanner scanBicubic = new Scanner(System.in);

            System.out.printf("Masukan dari terminal (T) atau file (F) > ");
            String input = scanBicubic.next().toUpperCase();

            if (input.equals("F")) {
                isFile = true;
            } else if (!input.equals("F") && !input.equals("T")) {
                System.out.println("Masukan TIDAK VALID");
                break;
            }

            Matrix A = new Matrix(0, 0);
            Matrix y = new Matrix(16, 1);
            double x1 = 0;
            double y1 = 0;
            if (isFile) {
                System.out.printf("Masukkan nama file: ");
                String pathToFile = scanBicubic.next();

                try {
                    int row, col;
                    File file1 = new File(pathToFile);
                    Scanner scanner1 = new Scanner(file1);
                    row = 0;
                    col = 0;
                    while (row < 4) {
                        String data = scanner1.nextLine();
                        String[] array = data.split(" ");
                        col = array.length;
                        row += 1;
                    }
                    String data_xy = scanner1.nextLine();
                    String[] array_xy = data_xy.split(" ");
                    x1 = Double.parseDouble(array_xy[0]);
                    y1 = Double.parseDouble(array_xy[1]);
                    scanner1.close();

                    Matrix matrix = new Matrix(row, col);
                    File file2 = new File(pathToFile);
                    Scanner scanner2 = new Scanner(file2);
                    int mrow = 0;
                    while (mrow < row) {
                        String data = scanner2.nextLine();
                        String[] array = data.split(" ");
                        for (int i = 0; i < array.length; i++) {
                            matrix.setElement(mrow, i, Double.parseDouble(array[i]));
                        }
                        mrow += 1;
                    }

                    scanner2.close();

                    A = matrix;
                } catch (Exception e) {
                    if (e.toString().contains("FileNotFoundException")) {
                        System.out.println("File tidak ditemukan");
                    } else {
                        System.out.println("Input matriks tidak valid");
                    }
                    A = null;
                }

                if (A == null) {
                    scanBicubic.close();
                    break;
                }

                int row = A.getRow();
                int col = A.getCol();
                int idx = 0;
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        y.setElement(idx, 0, A.getElement(i, j));
                        idx++;
                    }
                }
            } else {
                y.readMatrix(scanBicubic);
                x1 = scanBicubic.nextDouble();
                y1 = scanBicubic.nextDouble();
                System.out.println("");
            }

            Matrix inv_A = new BalikanGaussJordan().balikanGaussJordan(X);
            MatriksBalikan mb = new MatriksBalikan();
            Matrix a = mb.multiplyMatrix(inv_A, y);
            double z = bicubic.f(x1, y1, a);
            System.out.println("z = " + df.format(z));

            System.out.println("Simpan ke dalam file? (Y/N)");
            String save = scanBicubic.next().toUpperCase();
            if (save.equals("Y")) {
                System.out.println("Masukkan nama file: ");
                String pathToFile = scanBicubic.next();

                if (pathToFile.equals("")) {
                    LocalDate date = LocalDate.now();
                    pathToFile = "BicubicSpline_" + date.toString() + ".txt";
                }
                try {
                    FileWriter myWriter = new FileWriter(pathToFile);
                    myWriter.write("z = " + df.format(z));
                    myWriter.close();
                    System.out.println("Berhasil menyimpan ke dalam file");
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan dalam menyimpan file");
                }
            }
            break;
        }
    }

    private static void runRegresi() {
        while (true) {
            int row;
            int col;
            double x;
            double hasil;
            boolean next = true;

            DecimalFormat df = new DecimalFormat("#.####");

            System.out.println("\nMeregresi Linear Berganda Sampel");
            System.out.println("");

            Scanner scanRegresi = new Scanner(System.in);

            boolean isFile = false;

            System.out.printf("Masukan dari terminal (T) atau file (F) > ");
            String input = scanRegresi.next().toUpperCase();

            if (input.equals("F")) {
                isFile = true;
            } else if (!input.equals("F") && !input.equals("T")) {
                System.out.println("Masukan TIDAK VALID");
                break;
            }

            Matrix A = new Matrix(0, 0);
            if (isFile) {
                System.out.printf("Masukkan nama file: ");
                String pathToFile = scanRegresi.next();

                A = A.readMatrixFromFile(pathToFile);

                if (A == null) {
                    break;
                }

                row = A.getRow();
                col = A.getCol();
            } else {
                System.out.print("Masukkan jumlah peubah x: ");
                col = scanRegresi.nextInt();
                col++;
                System.out.print("");
                System.out.print("Masukkan jumlah sampel: ");
                row = scanRegresi.nextInt();

                A = new Matrix(row, col);
                A.readMatrix(scanRegresi);
            }

            System.out.println("");

            Matrix result = new RegresiLinearBerganda().regresiLinear(A);
            for (int i = 0; i < result.getRow(); i++) {
                result.setElement(i, result.getCol() - 1,
                        Double.parseDouble(df.format(result.getElement(i, result.getCol() - 1))));
            }

            String output = "f(x) = ";
            System.out.print("f(x) = ");
            for (int i = 0; i < result.getRow(); i++) {
                if (i == 0) {
                    output += result.getElement(i, result.getCol() - 1);
                    System.out.print(result.getElement(i, result.getCol() - 1));
                } else if (i == result.getRow() - 1) {
                    output += result.getElement(i, result.getCol() - 1) + "(x_" + i + ")";
                    System.out.print(result.getElement(i, result.getCol() - 1) + "(x_" + i + ")");
                } else {
                    output += result.getElement(i, result.getCol() - 1) + "(x_" + i + ")";
                    System.out.print(result.getElement(i, result.getCol() - 1) + "(x_" + i + ")");
                }

                if (i != result.getRow() - 1) {
                    output += " + ";
                    System.out.print(" + ");
                }
            }
            output += "\n";
            System.out.println("");

            while (next) {
                System.out.printf("\nPrediksi nilai titik Ya (Y) atau Tidak (T) > ");
                String ujiRegresi = scanRegresi.next().toUpperCase();

                if (!ujiRegresi.equals("Y") && !ujiRegresi.equals("T")) {
                    output += "Masukan TIDAK VALID\n";
                    System.out.println("Masukan TIDAK VALID");
                    break;
                }

                if (ujiRegresi.equals("Y")) {
                    hasil = result.getElement(0, result.getCol() - 1);
                    for (int i = 1; i < result.getCol() - 1; i++) {
                        System.out.print("Masukkan nilai x_" + i + ": ");
                        x = scanRegresi.nextDouble();
                        x *= result.getElement(i, result.getCol() - 1);
                        hasil += x;
                    }
                    output += "f(x) = " + Double.parseDouble(df.format(hasil)) + "\n";
                    System.out.println("f(x) = " + Double.parseDouble(df.format(hasil)));
                } else {
                    break;
                }
            }

            System.out.println("Simpan ke dalam file? (Y/N)");
            String save = scanRegresi.next().toUpperCase();
            if (save.equals("Y")) {
                System.out.println("Masukkan nama file: ");
                String pathToFile = scanRegresi.next();

                if (pathToFile.equals("")) {
                    LocalDate date = LocalDate.now();
                    pathToFile = "Regresi_" + date.toString() + ".txt";
                }
                try {
                    FileWriter myWriter = new FileWriter(pathToFile);
                    myWriter.write(output);
                    myWriter.close();
                    System.out.println("Berhasil menyimpan ke dalam file");
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan dalam menyimpan file");
                }
            }
            break;
        }
    }

    private static void credit() {
        System.out.println("\n+---------------------------------------+\n"
                + "Dari kami yang pemula:\n"
                + "1) Ahmad Naufal Ramadan        - 13522005\n"
                + "2) Sa'ad Abdul Hakim           - 13522092\n"
                + "3) Muhammad Dava Fathurrahman  - 13522114\n"
                + "+---------------------------------------+");
    }

    public static void main(String[] args) {
        boolean exit = false;
        welcome();

        while (exit == false) {
            Menu();

            Scanner scanMenu = new Scanner(System.in);
            int pilMenu = scanMenu.nextInt();

            switch (pilMenu) {
                case 1:
                    subMenu1();
                    break;
                case 2:
                    subMenu2();
                    break;
                case 3:
                    subMenu3();
                    break;
                case 4:
                    runInterpol();
                    break;
                case 5:
                    runBicubic();
                    break;
                case 6:
                    runRegresi();
                    break;
                case 7:
                    exit = true;
                    scanMenu.close();
                    break;
                default:
                    System.out.printf("Masukan TIDAK VALID\n");
            }
        }
        credit();
    }
}
