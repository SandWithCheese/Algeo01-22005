package matrix.balikan;

import matrix.Matrix;
import matrix.determinan.Kofaktor;

public class Adjoin {
    public Matrix MtxKofaktor(Matrix M){
        Matrix resultCofactor = new Matrix(M.getRow(), M.getCol());

        Kofaktor Adj = new Kofaktor();
        int sign;

        for (int i = 0; i < M.getRow(); i++){
            if (i % 2 == 0){
                sign = 1;
            }
            else {
                sign = -1;
            }

            for (int j = 0; j < M.getCol(); j++){
                resultCofactor.setElement(i, j, sign * Adj.detKofaktor(Adj.Minor(M, i, j)));
                sign *= (-1);
            }
        }

        return resultCofactor;
    }

    public Matrix MtxAdjoin(Matrix M){
        Matrix resultAdjoin = new Adjoin().MtxKofaktor(M);

        return resultAdjoin.transpose();        
    }

    public Matrix inverseAdjoin(Matrix M){
        Matrix result = new Adjoin().MtxAdjoin(M);
        double det = new Kofaktor().detKofaktor(M);

        if (det == 0){
            return null;
        }
        else{
            for (int i = 0; i < M.getRow(); i++){
                for (int j = 0; j < M.getCol(); j++){
                    result.setElement(i, j, result.getElement(i, j) * ((double)1 / det));
                }
            }
            return result;
        }
    }
}
