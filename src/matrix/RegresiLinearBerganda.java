package matrix;

import matrix.Matrix;
import matrix.spl.GaussJordan;

public class RegresiLinearBerganda {
    public Matrix regresiLinear(Matrix A){
        Matrix lanjar = new Matrix(A.getCol(), A.getCol()+1);
        
        double n = A.getRow();
        double temp;
        lanjar.setElement(0, 0, n);
        for (int i = 1;i<lanjar.getCol();i++){
            temp = 0;
            if (i == lanjar.getCol() - 1){
                for (int j = 0;j < A.getRow();j++){
                    temp += A.getElement(j, A.getCol()-1);
                }
            } else {
                for (int j = 0;j < A.getRow();j++){
                    temp += A.getElement(j, i-1);
                }
            }
            lanjar.setElement(0, i, temp);
        }

        for (int i = 1;i < lanjar.getRow();i++){
            for (int j = 0; j < lanjar.getCol();j++){
                temp = 0;
                if (j==0){
                    for (int k = 0; k < A.getRow();k++){
                        temp += A.getElement(k, i-1);
                    }
                } else if (j==lanjar.getCol()-1){
                    for (int k = 0; k < A.getRow();k++){
                        temp += A.getElement(k, i-1)*A.getElement(k, A.getCol()-1);
                    }
                } else {
                    for (int k = 0; k < A.getRow();k++){
                        temp += A.getElement(k, i-1)*A.getElement(k, j-1);
                    }
                }
                lanjar.setElement(i, j, temp);
            }
        }

        lanjar = new GaussJordan().gaussJordan(lanjar);

        return lanjar;
    }
}
