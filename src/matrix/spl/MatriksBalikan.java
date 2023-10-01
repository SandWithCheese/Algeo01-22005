package matrix.spl;

import matrix.Matrix;
import matrix.balikan.BalikanGaussJordan;

public class MatriksBalikan {
    /**
     * Mengembalikan hasil perkalian matriks m1 dan m2
     */
    public Matrix multiplyMatrix(Matrix m1, Matrix m2) {
        Matrix Mout = new Matrix(m1.getRow(), m2.getCol());

        for (int i = 0; i < m1.getRow(); i++) {
            for (int j = 0; j < m2.getCol(); j++) {
                Mout.setElement(i, j, 0);

                for (int k = 0; k < m1.getCol(); k++) {
                    double valMout = Mout.getElement(i, j);
                    double valM1 = m1.getElement(i, k);
                    double valM2 = m2.getElement(k, j);

                    Mout.setElement(i, j, valMout + valM1 * valM2);
                }
            }
        }

        return Mout;
    }

    /**
     * Mengembalikan solusi SPL dengan matriks balikan
     */
    public Matrix SPLInverse(Matrix A, Matrix B) {
        Matrix Ai = new BalikanGaussJordan().balikanGaussJordan(A);

        if (Ai != null) {
            Matrix result = multiplyMatrix(Ai, B);
            return result;
        } else {
            return null;
        }
    }
}
