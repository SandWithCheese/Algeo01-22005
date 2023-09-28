import java.util.Scanner;

import matrix.Matrix;
import matrix.balikan.*;
import matrix.determinan.*;
import matrix.spl.*;


public class Main {
    private static void welcome(){
        System.out.println("Selamat Datang di Program Tugas Besar I\n"
            + "Mata Kuliah IF2123 Aljabar Linier dan Geometri Tahun 2023/2024\n");

    }


    private static void Menu(){
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

        while (exit == false){
            System.out.println("\nPenyelesaian Sistem Persamaan Linier");
            System.out.println("1. Metode Eliminasi Gauss\n"
                                + "2. Metode Eliminasi Gauss-Jordan\n"
                                + "3. Metode Matriks Balikan\n"
                                + "4. Metode Kaidah Cramer\n"
                                + "5. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu2 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu2.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3 || pilSubMenu2 == 4 || pilSubMenu2 == 5){
                switch (pilSubMenu2){
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
                        if (result == null){
                            System.out.println("-> Gagal Menghitung Solusi! Matriks A Tidak Memiliki Balikan\n");
                        }
                        else{
                            System.out.println("-> Solusi SPL:");
                            for (int i = 0; i < row; i++){
                                System.out.printf("x%d = %f", i+1, result.getElement(i, 0));
                                System.out.println("");
                            }
                        }                        
                    case 5:
                        exit = true;
                        break;
                }
            }
            else{
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }


    private static void subMenu2() {
        boolean exit = false;
        int row, col;

        while (exit == false){
            System.out.println("\nMenghitung Determinan Matriks Persegi");
            System.out.println("1. Metode Eliminasi Kofaktor\n"
                                + "2. Metode Reduksi Baris\n"
                                + "3. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu2 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu2.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3){
                if (pilSubMenu2 == 3){
                    exit = true;
                }
                else{
                    System.out.print("Masukkan jumlah baris: ");
                    row = scanSubMenu2.nextInt();
                    System.out.print("Masukkan jumlah kolom: ");
                    col = scanSubMenu2.nextInt();

                    Matrix A = new Matrix(row, col);
                    A.readMatrix(scanSubMenu2);
                    System.out.println("");

                    if (A.isSquareMatrix()){
                        if (pilSubMenu2 == 1){
                            double result = new Kofaktor().detKofaktor(A);
                            A.displayMatrix();
                            System.out.println("-> Determinan: " + result + "\n");
                        }
                        else{
                            double result = new Kofaktor().detKofaktor(A);
                            A.displayMatrix();
                            System.out.println("-> Determinan: " + result + "\n");
                        }
                    }
                    else{
                        System.out.printf("Matriks Masukan BUKANLAH MATRIKS PERSEGI\n");
                    }
                }
            }
            else{
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }


    private static void subMenu3() {
        boolean exit = false;
        int row, col;

        while (exit == false){
            System.out.println("\nMenghitung Balikan Matriks Persegi");
            System.out.println("1. Metode Adjoin\n"
                                + "2. Metode Gauss Jordan\n"
                                + "3. Kembali\n");
            System.out.printf("Masukkan metode pilihan Anda > ");

            Scanner scanSubMenu3 = new Scanner(System.in);
            int pilSubMenu2 = scanSubMenu3.nextInt();

            if (pilSubMenu2 == 1 || pilSubMenu2 == 2 || pilSubMenu2 == 3){
                if (pilSubMenu2 == 3){
                    exit = true;
                }
                else{
                    System.out.print("Masukkan jumlah baris: ");
                    row = scanSubMenu3.nextInt();
                    System.out.print("Masukkan jumlah kolom: ");
                    col = scanSubMenu3.nextInt();

                    Matrix A = new Matrix(row, col);
                    A.readMatrix(scanSubMenu3);
                    System.out.println("");

                    if (A.isSquareMatrix()){
                        if (pilSubMenu2 == 1){
                            Matrix result = new Adjoin().inverseAdjoin(A);
                            if (result == null){
                                 System.out.println("-> Tidak Memiliki Matriks Balikan\n");
                            }
                            else{
                                System.out.println("-> Hasil Matriks Balikan:");
                                result.displayMatrix();
                            }
                        }
                        else{
                            double result = new Kofaktor().detKofaktor(A);
                            A.displayMatrix();
                            System.out.println("-> Determinan: " + result + "\n");
                        }
                    }
                    else{
                        System.out.printf("Matriks Masukan BUKANLAH MATRIKS PERSEGI\n");
                    }
                }
            }
            else{
                System.out.printf("Masukan TIDAK VALID\n");
            }
        }
    }

    private static void runInterpol() {
        System.out.println("\nMenghitung Balikan Matriks Persegi");
        System.out.println("1. Metode Adjoin\n"
                            + "2. Metode Gauss Jordan\n"
                            + "3. Kembali\n");
        System.out.printf("Masukkan metode pilihan Anda > ");
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

    private static void credit(){
        System.out.println("\n+---------------------------------------+\n"
            + "Dari kami yang pemula:\n"
            + "1) Ahmad Naufal Ramadan        - 13522005\n"
            + "2) Sa'ad Abdul Hakim           - 13522092\n"
            + "3) Muhammad Dava Fathurrahman  - 13522114\n"
            + "+---------------------------------------+");
    }

    public static void main(String[] args){
        boolean exit = false;
        welcome();

        while (exit == false){
            Menu();

            Scanner scanMenu = new Scanner(System.in);
            int pilMenu = scanMenu.nextInt();

            switch (pilMenu){
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
                    break;
                default:
                    System.out.printf("Masukan TIDAK VALID\n");
            }
        }
        credit();
    }
}
