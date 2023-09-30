package matrix;

import matrix.Matrix;
import matrix.spl.GaussJordan;


public class InterpolasiPolinom {
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
}
