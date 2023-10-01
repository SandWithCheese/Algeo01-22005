package matrix.determinan;

import matrix.Matrix;

public class Kofaktor {
    /**
     * Mengembalikan matriks minor dari matriks M
     */
    public Matrix Minor(Matrix M, int row, int col) {
        Matrix result = new Matrix(M.getRow() - 1, M.getCol() - 1);

        int idx_resultRow = 0;

        for (int i = 0; i < M.getRow(); i++) {
            if (i != row) {
                int idx_resultCol = 0;
                for (int j = 0; j < M.getCol(); j++) {
                    if (j != col) {
                        result.setElement(idx_resultRow, idx_resultCol, M.getElement(i, j));
                        idx_resultCol++;
                    }
                }
                idx_resultRow++;
            }
        }

        return result;
    }

    /**
     * Mengembalikan determinan dari matriks M
     */
    public double detKofaktor(Matrix M) {
        double det_value = 0;
        int sign = 1;

        if (M.getRow() == 1) {
            return M.getElement(0, 0);
        }

        for (int i = 0; i < M.getCol(); i++) {
            det_value += sign * M.getElement(0, i) * detKofaktor(Minor(M, 0, i));
            sign *= (-1);
        }
        return det_value;
    }
}
