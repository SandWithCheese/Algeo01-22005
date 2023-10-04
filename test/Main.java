import java.util.Scanner;

import matrix.Matrix;
import matrix.balikan.*;
import matrix.determinan.*;
import matrix.spl.*;
import matrix.BicubicSpline;
import matrix.InterpolasiPolinom.*;
import matrix.RegresiLinearBerganda;
import java.text.DecimalFormat;
import java.io.File;
import java.lang.Math;

public class Main {
    private static void welcome() {
        System.out.println("Selamat Datang di Program Tugas Besar I\n"
                + "Mata Kuliah IF2123 Aljabar Linier dan Geometri Tahun 2023/2024\n");

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

        while (exit == false) {
            System.out.println("\nPenyelesaian Sistem Persamaan Linier");
            System.out.println("1. Metode Eliminasi Gauss\n"
                    + "2. Metode Eliminasi Gauss-Jordan\n"
                    + "3. Metode Matriks Balikan\n"
                    + "4. Metode Kaidah Cramer\n"
                    + "5. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu2 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu2.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3 || pilSubMenu2 == 4 || pilSubMenu2 == 5) {
                switch (pilSubMenu2) {
                    case 1:
                        boolean isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        String input = scanSubMenu2.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        Matrix M = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu2.next();

                            M = M.readMatrixFromFile(pathToFile);

                            if (M == null) {
                                break;
                            }

                            row = M.getRow();
                            col = M.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu2.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu2.nextInt();
                            M = new Matrix(row, col);
                            M.readMatrix(scanSubMenu2);
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
                                System.out.println("-> SPL memiliki banyak solusi\n");
                                for (int j = 0; j < row; j++) {
                                    char var = 'a';
                                    for (int i = 0; i < col - 1; i++) {
                                        if (i == col - 2) {
                                            System.out.print(String.valueOf(resultM.getElement(j, i)) + var);
                                        } else {
                                            System.out
                                                    .print(String.valueOf(resultM.getElement(j, i)) + var
                                                            + " + ");
                                        }
                                        var++;
                                    }
                                    System.out.print(" = " + String.valueOf(resultM.getElement(j, col - 1)));
                                    System.out.println("");
                                }
                            } else {
                                if (col - row >= 2) {
                                    System.out.println("-> SPL memiliki banyak solusi\n");
                                    for (int j = 0; j < row; j++) {
                                        char var = 'a';
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == col - 2) {
                                                System.out.print(String.valueOf(resultM.getElement(j, i)) + var);
                                            } else {
                                                System.out
                                                        .print(String.valueOf(resultM.getElement(j, i)) + var
                                                                + " + ");
                                            }
                                            var++;
                                        }
                                        System.out.print(" = " + String.valueOf(resultM.getElement(j, col - 1)));
                                        System.out.println("");
                                    }
                                } else {
                                    System.out.println("-> SPL memiliki solusi unik\n");
                                    for (int j = 0; j < row; j++) {
                                        char var = 'a';
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == j) {
                                                System.out.print(var);
                                                break;
                                            }
                                            var++;
                                        }
                                        System.out.print(" = " + String.valueOf(resultM.getElement(j, col - 1)));
                                        System.out.println("");
                                    }
                                }
                            }
                        } else {
                            System.out.println("-> SPL tidak memiliki solusi\n");
                        }

                        break;
                    case 2:
                        isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        input = scanSubMenu2.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        M = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu2.next();

                            M = M.readMatrixFromFile(pathToFile);

                            if (M == null) {
                                break;
                            }

                            row = M.getRow();
                            col = M.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu2.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu2.nextInt();
                            M = new Matrix(row, col);
                            M.readMatrix(scanSubMenu2);
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
                                System.out.println("-> SPL memiliki banyak solusi\n");
                                for (int j = 0; j < row; j++) {
                                    char var = 'a';
                                    for (int i = 0; i < col - 1; i++) {
                                        if (i == col - 2) {
                                            System.out.print(String.valueOf(resultM.getElement(j, i)) + var);
                                        } else {
                                            System.out
                                                    .print(String.valueOf(resultM.getElement(j, i)) + var
                                                            + " + ");
                                        }
                                        var++;
                                    }
                                    System.out.print(" = " + String.valueOf(resultM.getElement(j, col - 1)));
                                    System.out.println("");
                                }
                            } else {
                                if (col - row >= 2) {
                                    System.out.println("-> SPL memiliki banyak solusi\n");
                                    for (int j = 0; j < row; j++) {
                                        char var = 'a';
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == col - 2) {
                                                System.out.print(String.valueOf(resultM.getElement(j, i)) + var);
                                            } else {
                                                System.out
                                                        .print(String.valueOf(resultM.getElement(j, i)) + var
                                                                + " + ");
                                            }
                                            var++;
                                        }
                                        System.out.print(" = " + String.valueOf(resultM.getElement(j, col - 1)));
                                        System.out.println("");
                                    }
                                } else {
                                    System.out.println("-> SPL memiliki solusi unik\n");
                                    for (int j = 0; j < row; j++) {
                                        char var = 'a';
                                        for (int i = 0; i < col - 1; i++) {
                                            if (i == j) {
                                                System.out.print(var);
                                                break;
                                            }
                                            var++;
                                        }
                                        System.out.print(" = " + String.valueOf(resultM.getElement(j, col - 1)));
                                        System.out.println("");
                                    }
                                }
                            }
                        } else {
                            System.out.println("-> SPL tidak memiliki solusi\n");
                        }

                        break;
                    case 3:
                        isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        input = scanSubMenu2.next().toUpperCase();

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
                            String pathToFile = scanSubMenu2.next();

                            M = M.readMatrixFromFile(pathToFile);

                            if (M == null) {
                                break;
                            }

                            row = M.getRow();
                            col = M.getCol();
                        } else {
                            System.out.print("Masukkan jumlah baris: ");
                            row = scanSubMenu2.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu2.nextInt();
                            M = new Matrix(row, col);
                            M.readMatrix(scanSubMenu2);
                        }
                        A = new Matrix(M.getRow(), M.getCol()-1);
                        for (int i = 0; i < A.getRow(); i++){
                            for (int j = 0; j < A.getCol(); j++){
                                A.setElement(i, j, M.getElement(i, j));
                            }
                        }

                        B = new Matrix(M.getRow(), 1);
                        for (int i = 0; i < B.getRow(); i++){
                            B.setElement(i, 0, M.getElement(i, M.getCol()-1));
                        }

                        System.out.println("");

                        Matrix result = new MatriksBalikan().SPLInverse(A, B);
                        if (result == null) {
                            System.out.println("-> Gagal Menghitung Solusi! Matriks A Tidak Memiliki Balikan\n");
                        } else {
                            System.out.println("-> Solusi SPL:");
                            for (int i = 0; i < row; i++) {
                                System.out.printf("x%d = %f", i + 1, result.getElement(i, 0));
                                System.out.println("");
                            }
                        }
                        break;
                    case 4:
                        isFile = false;

                        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
                        input = scanSubMenu2.next().toUpperCase();

                        if (input.equals("F")) {
                            isFile = true;
                        } else if (!input.equals("F") && !input.equals("T")) {
                            System.out.println("Masukan TIDAK VALID\n");
                            break;
                        }

                        Matrix M1 = new Matrix(0, 0);
                        if (isFile) {
                            System.out.printf("Masukkan nama file: ");
                            String pathToFile = scanSubMenu2.next();

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
                            row = scanSubMenu2.nextInt();
                            System.out.print("Masukkan jumlah kolom: ");
                            col = scanSubMenu2.nextInt();

                            if (col - row != 1) {
                                System.out.println("-> Tidak bisa dihitung menggunakan metode Cramer\n");
                                break;
                            }

                            M1 = new Matrix(row, col);
                            M1.readMatrix(scanSubMenu2);
                        }

                        System.out.println("");

                        double[] cramer = new Cramer().cramer(M1);

                        System.out.println("-> Solusi SPL:");
                        for (int i = 0; i < row; i++) {
                            System.out.printf("x%d = %f", i + 1, cramer[i]);
                            System.out.println("");
                        }

                        break;
                    case 5:
                        exit = true;
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

        while (exit == false) {
            System.out.println("\nMenghitung Determinan Matriks Persegi");
            System.out.println("1. Metode Eliminasi Kofaktor\n"
                    + "2. Metode Reduksi Baris\n"
                    + "3. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu2 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu2.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3) {
                if (pilSubMenu2 == 3) {
                    exit = true;
                }
                else {
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

                    if (A.isSquareMatrix()) {
                        if (pilSubMenu2 == 1) {
                            double result = new Kofaktor().detKofaktor(A);
                            A.displayMatrix();
                            System.out.println("-> Determinan: " + result + "\n");
                        } else {
                            double result = ReduksiBaris.determinan(A);
                            A.displayMatrix();
                            System.out.println("-> Determinan: " + result + "\n");
                        }
                    }
                    else {
                        System.out.printf("Matriks Masukan BUKANLAH MATRIKS PERSEGI\n");
                    }
                }
            }
            else {
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }

    private static void subMenu3() {
        boolean exit = false;
        int row, col;

        while (exit == false) {
            System.out.println("\nMenghitung Balikan Matriks Persegi");
            System.out.println("1. Metode Adjoin\n"
                    + "2. Metode Gauss Jordan\n"
                    + "3. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu3 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu3.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3) {
                if (pilSubMenu2 == 3) {
                    exit = true;
                } else {
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

                    if (A.isSquareMatrix()) {
                        if (pilSubMenu2 == 1) {
                            Matrix result = new Adjoin().inverseAdjoin(A);
                            if (result == null) {
                                System.out.println("-> Tidak Memiliki Matriks Balikan\n");
                            } else {
                                System.out.println("-> Hasil Matriks Balikan:");
                                result.displayMatrix();
                            }
                        }
                        else {
                            Matrix result = new BalikanGaussJordan().balikanGaussJordan(A);
                            if (result == null) {
                                System.out.println("-> Tidak Memiliki Matriks Balikan\n");
                            } else {
                                System.out.println("-> Hasil Matriks Balikan:");
                                result.displayMatrix();
                            }
                        }
                    } else {
                        System.out.printf("Matriks Masukan BUKANLAH MATRIKS PERSEGI\n");
                    }
                }
            } else {
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }

    private static void runInterpol() {
        while (true){
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
            Matrix uji = null;
            if (isFile) {
                System.out.printf("Masukkan nama file: ");
                String pathToFile = scanInterpol.next();

                A = A.readInterpolMatrixFromFile(pathToFile);

                if (A == null) {
                    break;
                }

                row = A.getRow();

                int nUji = new InterpolasiPolinom().countTest(pathToFile);
                System.out.println(nUji);

                if (nUji != 0){
                    uji = uji.readInterpolTestFromFile(nUji, pathToFile);
                }
                else{
                    next = false;
                }
            } else {
                System.out.print("Masukkan jumlah titik: ");
                row = scanInterpol.nextInt();

                A = new Matrix(row, col);
                A.readMatrix(scanInterpol);
                System.out.println("");
            }

            System.out.println("");

            Matrix result = new InterpolasiPolinom().SPLInterpol(A);
            for (int i = 0; i < result.getRow(); i++) {
                result.setElement(i, result.getCol() - 1,
                    Double.parseDouble(df.format(result.getElement(i, result.getCol() - 1))));
            }

            System.out.print("p" + (row - 1) + "(x) = ");
            for (int i = 0; i < result.getRow(); i++) {
                if (i == 0) {
                    System.out.print(result.getElement(i, result.getCol() - 1));
                } else if (i == 1) {
                    System.out.print(result.getElement(i, result.getCol() - 1) + "(x)");
                } else {
                    System.out.print(result.getElement(i, result.getCol() - 1) + "(x" + i + ")");
                }

                if (i != result.getRow() - 1) {
                    System.out.print(" + ");
                }
            }

            while (next) {
                if (isFile == true){
                    uji.displayMatrix();
                    
                    for (int i = 0; i < uji.getRow(); i ++){
                        est = 0.0;
                        for (int j = 0; j < result.getRow(); j++) {
                            est = est + result.getElement(j, result.getCol() - 1) * (Math.pow(uji.getElement(i, 0), i));
                        }

                        System.out.println("p(" + uji.getElement(i, 0) + ") = " + Double.parseDouble(df.format(est)));
                    }
                }
                else{
                    System.out.println("\nMenguji Titik");
                    System.out.println("1. Ya\n"
                            + "2. Tidak");
                    System.out.printf("Masukkan (1 / 2) > ");

                    int ujiInterpol = scanInterpol.nextInt();

                    if (ujiInterpol == 1) {
                        est = 0.0;

                        System.out.print("\nMasukkan titik (x) yang ingin diestimasi: ");
                        Double x = scanInterpol.nextDouble();

                        for (int i = 0; i < result.getRow(); i++) {
                            est = est + result.getElement(i, result.getCol() - 1) * (Math.pow(x, i));
                        }

                        System.out.println("p(" + x + ") = " + Double.parseDouble(df.format(est)));
                    } else {
                        next = false;
                    }
                }
            }
            System.out.println("");
        }
    }

    private static void runBicubic(Scanner scan) {
        BicubicSpline bicubic = new BicubicSpline();
        Matrix X = bicubic.bicubicSpline();

        boolean isFile = false;

        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
        String input = scan.next().toUpperCase();

        if (input.equals("F")) {
            isFile = true;
        } else if (!input.equals("F") && !input.equals("T")) {
            System.out.println("Masukan TIDAK VALID\n");
            scan.close();
            return;
        }

        Matrix A = new Matrix(0, 0);
        Matrix y = new Matrix(16, 1);
        double x1 = 0;
        double y1 = 0;
        if (isFile) {
            System.out.printf("Masukkan nama file: ");
            String pathToFile = scan.next();

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
                scan.close();
                return;
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
            y.readMatrix(scan);
            x1 = scan.nextDouble();
            y1 = scan.nextDouble();
        }

        Matrix inv_A = new BalikanGaussJordan().balikanGaussJordan(X);
        MatriksBalikan mb = new MatriksBalikan();
        Matrix a = mb.multiplyMatrix(inv_A, y);
        double z = bicubic.f(x1, y1, a);
        System.out.println("z = " + z);
    }

    private static void runRegresi() {
        int row;
        int col;
        double x;
        double hasil;
        boolean next = true;

        DecimalFormat df = new DecimalFormat("#.####");

        System.out.println("\nMengregresi Linear Berganda Sampel");
        System.out.println("");

        Scanner scanRegresi = new Scanner(System.in);

        boolean isFile = false;

        System.out.printf("Masukan dari terminal (T) atau file (F) > ");
        String input = scanRegresi.next().toUpperCase();

        if (input.equals("F")) {
            isFile = true;
        } else if (!input.equals("F") && !input.equals("T")) {
            System.out.println("Masukan TIDAK VALID\n");
            scanRegresi.close();
            return;
        }

        Matrix A = new Matrix(0, 0);
        if (isFile) {
            System.out.printf("Masukkan nama file: ");
            String pathToFile = scanRegresi.next();

            A = A.readMatrixFromFile(pathToFile);

            if (A == null) {
                scanRegresi.close();
                return;
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

        System.out.println("f(x) = ");
        for (int i = 0;i < result.getRow();i++){
            if (i == 0){
                System.out.print(result.getElement(i, result.getCol()-1)+" + ");
            } else if (i == result.getRow()-1){
                System.out.print(result.getElement(i, result.getCol()-1)+"x"+i);
            } else {
                System.out.print(result.getElement(i, result.getCol()-1)+"x"+i+" + ");
            }
        }
        
        while (next) {
            System.out.println("\nEstimasi Nilai (y)");
            System.out.println("1. Ya\n"
                    + "2. Tidak");
            System.out.printf("Masukkan (1 / 2) > ");

            int ujiRegresi = scanRegresi.nextInt();

            if (ujiRegresi == 1) {
                hasil = result.getElement(0, result.getCol()-1);
                for (int i = 1; i < result.getCol()-2;i++){
                    System.out.print("\nMasukkan nilai x"+i+": ");
                    x = scanRegresi.nextDouble();
                    hasil += x;
                }
                System.out.println("f(x) = " + Double.parseDouble(df.format(hasil)));
            } else {
                next = false;
            }
        }
        System.out.println("");
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
                    runBicubic(scanMenu);
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
