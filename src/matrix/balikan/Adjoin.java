package matrix.balikan;

import matrix.Matrix;
import matrix.determinan.Kofaktor;

public class Adjoin {
    public Matrix MtxKofaktor(Matrix M, int row, int col){
        Matrix result = new Matrix(row, col);

        Kofaktor Adj = new Kofaktor();
        int sign = 1;

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                result.setElement(i, j, sign * Adj.detKofaktor(Adj.Minor(M, i, j)));
                sign *= (-1);
            }
        }

        return result;
    }

    public Matrix MtxAdjoin(Matrix M, int row, int col){
        Matrix result = new Adjoin().MtxKofaktor(M, row, col);

        return result.transpose();        
    }

    public Matrix inverseAdjoin(Matrix M, int row, int col){
        Matrix result = new Adjoin().MtxAdjoin(M, row, col);
        double det = new Kofaktor().detKofaktor(M);

        for (int i = 0; i < row; i++){
            for (int j = 0; j < col; j++){
                result.setElement(i, j, result.getElement(i, j) * ((float)1 / det));
            }
        }

        return result;
    }
}
