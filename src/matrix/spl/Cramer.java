package matrix.spl;

import matrix.Matrix;
import matrix.determinan.ReduksiBaris;

public class Cramer {
    /**
     * Mengembalikan matriks A dari matriks augmented M
     */
    private Matrix getA(Matrix M) {
        Matrix A = new Matrix(M.getRow(), M.getCol() - 1);

        for (int i = 0; i < M.getRow(); i++) {
            for (int j = 0; j < M.getCol() - 1; j++) {
                A.setElement(i, j, M.getElement(i, j));
            }
        }

        return A;
    }

    /**
     * Mengembalikan matriks B dari matriks augmented M
     */
    private Matrix getB(Matrix M) {
        Matrix B = new Matrix(M.getRow(), 1);

        for (int i = 0; i < M.getRow(); i++) {
            B.setElement(i, 0, M.getElement(i, M.getCol() - 1));
        }

        return B;
    }

    /**
     * Mengembalikan matriks dengan mengganti kolom ke-i dengan matriks B
     */
    private Matrix changeEntry(Matrix M, int col, Matrix B) {
        Matrix result = M.copyMatrix();

        for (int i = 0; i < M.getRow(); i++) {
            result.setElement(i, col, B.getElement(i, 0));
        }

        return result;
    }

    /**
     * Mengembalikan solusi SPL dengan metode Cramer
     */
    public double[] cramer(Matrix M) {
        double[] result = new double[M.getCol() - 1];

        Matrix A = this.getA(M);
        Matrix B = this.getB(M);

        Double detA = ReduksiBaris.determinan(A);

        if (detA == null || detA == 0) {
            return null;
        }

        for (int i = 0; i < M.getCol() - 1; i++) {
            Matrix Ai = this.changeEntry(A, i, B);
            result[i] = ReduksiBaris.determinan(Ai) / detA;
        }

        return result;
    }
}
