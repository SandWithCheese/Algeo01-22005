package matrix;

import matrix.Matrix;
import matrix.spl.GaussJordan;
import java.util.Scanner;
import java.io.File;


public class InterpolasiPolinom {
    /**
     * Mengembalikan solusi SPL dengan metode Gauss-Jordan
     */
    public Matrix SPLInterpol(Matrix A){
        Matrix lanjar = new Matrix(A.getRow(), A.getRow()+1);

        for (int i = 0; i < lanjar.getRow(); i++){
            double k = 1;
            for (int j = 0; j < lanjar.getCol()-1; j++){
                lanjar.setElement(i, j, k);
                k *= (A.getElement(i, 0));
            }
        }

        for (int i = 0; i < lanjar.getRow(); i++){
            lanjar.setElement(i, lanjar.getCol()-1, A.getElement(i, A.getCol()-1));
        }

        lanjar = new GaussJordan().gaussJordan(lanjar);


        return lanjar;
    }


    /**
     * Mengembalikan jumlah titik uji interpolasi dari file
     */
    public int countTest(String pathToFile){
        try {
            int nUji = 0;
            File file1 = new File(pathToFile);
            Scanner scanner1 = new Scanner(file1);
            while (scanner1.hasNextLine()) {
                String data = scanner1.nextLine();
                String[] array = data.split(" ");
                if (array.length == 1){
                    nUji++;
                }
            }
            scanner1.close();

            return nUji;
        } catch (Exception e) {
            if (e.toString().contains("FileNotFoundException")) {
                System.out.println("File tidak ditemukan");
            } else {
                System.out.println("Input titik tidak valid");
            }
            return 0;
        }
    }
}
