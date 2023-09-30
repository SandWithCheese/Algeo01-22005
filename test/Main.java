import java.util.Scanner;

import matrix.Matrix;
import matrix.balikan.*;
import matrix.determinan.*;
import matrix.spl.*;
import matrix.InterpolasiPolinom;
import java.text.DecimalFormat;
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
                    case 2:
                        System.out.print("Masukkan jumlah baris: ");
                        row = scanSubMenu2.nextInt();
                        System.out.print("Masukkan jumlah kolom: ");
                        col = scanSubMenu2.nextInt();

                        Matrix M = new Matrix(row, col);
                        M.readMatrix(scanSubMenu2);
                        System.out.println("");

                        Matrix resultM = new GaussJordan().gaussJordan(M);
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
                    case 3:
                        System.out.println("Penyelesaian x = A'B\n");

                        System.out.println("Matriks A");
                        System.out.print("Masukkan jumlah baris: ");
                        row = scanSubMenu2.nextInt();
                        System.out.print("Masukkan jumlah kolom: ");
                        col = scanSubMenu2.nextInt();

                        Matrix A = new Matrix(row, col);
                        A.readMatrix(scanSubMenu2);
                        System.out.println("");

                        System.out.println("Matriks B");

                        Matrix B = new Matrix(row, 1);
                        B.readMatrix(scanSubMenu2);
                        System.out.println("");

                        Matrix result = new MatriksBalikan().SPLInverse(A, B);
                        A.displayMatrix();
                        System.out.println("");
                        B.displayMatrix();
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
                        System.out.print("Masukkan jumlah baris: ");
                        row = scanSubMenu2.nextInt();
                        System.out.print("Masukkan jumlah kolom: ");
                        col = scanSubMenu2.nextInt();

                        if (col - row != 1) {
                            System.out.println("-> Tidak bisa dihitung menggunakan metode Cramer\n");
                            break;
                        }

                        Matrix M1 = new Matrix(row, col);
                        M1.readMatrix(scanSubMenu2);
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
                } else {
                    System.out.print("Masukkan jumlah baris: ");
                    row = scanSubMenu2.nextInt();
                    System.out.print("Masukkan jumlah kolom: ");
                    col = scanSubMenu2.nextInt();

                    Matrix A = new Matrix(row, col);
                    A.readMatrix(scanSubMenu2);
                    System.out.println("");

                    if (A.isSquareMatrix()) {
                        if (pilSubMenu2 == 1) {
                            double result = new Kofaktor().detKofaktor(A);
                            A.displayMatrix();
                            System.out.println("-> Determinan: " + result + "\n");
                        } else {
                            if (ReduksiBaris.determinan(A) == null) {
                                System.out.println("-> Tidak Memiliki Determinan\n");
                            } else {
                                double result = ReduksiBaris.determinan(A);
                                A.displayMatrix();
                                System.out.println("-> Determinan: " + result + "\n");
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
                    System.out.print("Masukkan jumlah baris: ");
                    row = scanSubMenu3.nextInt();
                    System.out.print("Masukkan jumlah kolom: ");
                    col = scanSubMenu3.nextInt();

                    Matrix A = new Matrix(row, col);
                    A.readMatrix(scanSubMenu3);
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
                        } else {
                            Matrix result = new BalikanGaussJordan().balikanGaussJordan(A);
                            result.displayMatrix();
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
        int row;
        int col = 2;
        double est;
        double x;
        boolean next = true;

        DecimalFormat df = new DecimalFormat("#.####");

        System.out.println("\nMenginterpolasi Titik");

        Scanner scanInterpol = new Scanner(System.in);

        System.out.print("Masukkan jumlah titik: ");
        row = scanInterpol.nextInt();

        Matrix A = new Matrix(row, col);
        A.readMatrix(scanInterpol);
        System.out.println("");


        Matrix result = new InterpolasiPolinom().SPLInterpol(A);
        for (int i = 0; i < result.getRow(); i++){
            result.setElement(i, result.getCol()-1, Double.parseDouble(df.format(result.getElement(i, result.getCol()-1))));
        }

        System.out.print("p" + (row-1) + "(x) = ");
        for (int i = 0; i < result.getRow(); i++){
            if (i == 0){
                System.out.print(result.getElement(i, result.getCol()-1));
            }
            else if (i == 1){
                System.out.print(result.getElement(i, result.getCol()-1) + "(x)");
            }
            else {
                System.out.print(result.getElement(i, result.getCol()-1) + "(x" + i + ")");
            }


            if (i != result.getRow()-1){
                System.out.print(" + ");
            }
        }

        while (next){
            System.out.println("\nMenguji Titik");
            System.out.println("1. Ya\n"
                    + "2. Tidak");
            System.out.printf("Masukkan (1 / 2) > ");

            int ujiInterpol = scanInterpol.nextInt();

            if (ujiInterpol == 1){
                est = 0.0;

                System.out.print("\nMasukkan titik (x) yang ingin diestimasi: ");
                x = scanInterpol.nextDouble();

                for (int i = 0; i < result.getRow(); i++){
                    est = est + result.getElement(i, result.getCol()-1) * (Math.pow(x, i));
                }
                
                System.out.println("p(" + x + ") = " +  Double.parseDouble(df.format(est)));
            }
            else{
                next = false;
            }
        }
        System.out.println("");        
    }

    private static void runBicubic() {
        System.out.println("\nMenghitung Balikan Matriks Persegi");
        System.out.println("1. Metode Adjoin\n"
                + "2. Metode Gauss Jordan\n"
                + "3. Kembali\n");
        System.out.printf("Masukkan metode pilihan Anda > ");
    }

    private static void runRegresi() {
        System.out.println("\nMenghitung Balikan Matriks Persegi");
        System.out.println("1. Metode Adjoin\n"
                + "2. Metode Gauss Jordan\n"
                + "3. Kembali\n");
        System.out.printf("Masukkan metode pilihan Anda > ");
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
