package matrix.spl;

import matrix.Matrix;

public class Gauss {
    private Matrix swapRow(Matrix M, int row1, int row2) {
        Matrix result = M.copyMatrix();

        for (int i = 0; i < M.getCol(); i++) {
            result.setElement(row1, i, M.getElement(row2, i));
            result.setElement(row2, i, M.getElement(row1, i));
        }

        return result;
    }

    private Matrix multiplyRow(Matrix M, int row, double multiplier) {
        Matrix result = M.copyMatrix();

        for (int i = 0; i < M.getCol(); i++) {
            result.setElement(row, i, M.getElement(row, i) * multiplier);
        }

        return result;
    }

    private Matrix addRow(Matrix M, int row1, int row2, double multiplier) {
        Matrix result = M.copyMatrix();

        for (int i = 0; i < M.getCol(); i++) {
            result.setElement(row1, i, M.getElement(row1, i) + M.getElement(row2, i) * multiplier);
        }

        return result;
    }

    private boolean isAnyNaN(Matrix M) {
        for (int i = 0; i < M.getRow(); i++) {
            for (int j = 0; j < M.getCol(); j++) {
                if (Double.isNaN(M.getElement(i, j))) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isAnyInf(Matrix M) {
        for (int i = 0; i < M.getRow(); i++) {
            for (int j = 0; j < M.getCol(); j++) {
                if (Double.isInfinite(M.getElement(i, j))) {
                    return true;
                }
            }
        }

        return false;
    }

    public Matrix gauss(Matrix m) {
        Matrix result = m.copyMatrix();
        Matrix temp;

        for (int i = 0; i < m.getRow(); i++) {
            if (result.getElement(i, i) == 0) {
                for (int j = i + 1; j < m.getRow(); j++) {
                    if (result.getElement(j, i) != 0) {
                        result = this.swapRow(result, i, j);
                        break;
                    }
                }
            }

            temp = result.copyMatrix();

            result = this.multiplyRow(result, i, 1 / result.getElement(i, i));

            if (this.isAnyNaN(result) || this.isAnyInf(result)) {
                return temp;
            }

            for (int j = i+1; j < m.getRow(); j++) {
                result = this.addRow(result, j, i, -result.getElement(j, i));
            }
        }

        return result;
    }
}
