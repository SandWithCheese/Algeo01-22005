package matrix.balikan;

import matrix.Matrix;
import matrix.determinan.Kofaktor;

public class Adjoin {
    public Matrix MtxKofaktor(Matrix M, int row, int col){
        Matrix resultCofactor = new Matrix(row, col);

        Kofaktor Adj = new Kofaktor();
        int sign;

        for (int i = 0; i < row; i++){
            if (i % 2 == 0){
                sign = 1;
            }
            else {
                sign = -1;
            }

            for (int j = 0; j < col; j++){
                resultCofactor.setElement(i, j, sign * Adj.detKofaktor(Adj.Minor(M, i, j)));
                sign *= (-1);
            }
        }

        return resultCofactor;
    }

    public Matrix MtxAdjoin(Matrix M, int row, int col){
        Matrix resultAdjoin = new Adjoin().MtxKofaktor(M, row, col);

        return resultAdjoin.transpose();        
    }

    public Matrix inverseAdjoin(Matrix M, int row, int col){
        Matrix result = new Adjoin().MtxAdjoin(M, row, col);
        double det = new Kofaktor().detKofaktor(M);

        if (det == 0){
            return null;
        }
        else{
            for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    result.setElement(i, j, result.getElement(i, j) * ((double)1 / det));
                }
            }
            return result;
        }
    }
}
