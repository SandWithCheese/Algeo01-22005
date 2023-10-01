package matrix.determinan;

import matrix.Matrix;

/**
 * Membuat kelas bentukan Pair untuk menyimpan dua nilai
 */
class Pair<X, Y> {
    private final X k;
    private final Y v;

    public Pair(X k, Y v) {
        this.k = k;
        this.v = v;
    }

    public X retrieveKey() {
        return this.k;
    }

    public Y retrieveVal() {
        return this.v;
    }
}

public class ReduksiBaris {
    /**
     * Menukar 2 baris pada matriks M
     */
    private static Matrix swapRow(Matrix M, int row1, int row2) {
        Matrix result = M.copyMatrix();

        for (int i = 0; i < M.getCol(); i++) {
            result.setElement(row1, i, M.getElement(row2, i));
            result.setElement(row2, i, M.getElement(row1, i));
        }

        return result;
    }

    /**
     * Menambahkan baris row2 pada baris row1 dengan multiplier
     */
    private static Matrix addRow(Matrix M, int row1, int row2, double multiplier) {
        Matrix result = M.copyMatrix();

        for (int i = 0; i < M.getCol(); i++) {
            result.setElement(row1, i, M.getElement(row1, i) + M.getElement(row2, i) * multiplier);
        }

        return result;
    }

    /**
     * Mengembalikan matriks segitiga atas dari matriks M
     */
    private static Pair<Matrix, Integer> reduceToTriangle(Matrix M) {
        Matrix result = M.copyMatrix();
        Integer count = 0;

        for (int i = 0; i < M.getRow(); i++) {
            if (result.getElement(i, i) == 0) {
                for (int j = i + 1; j < M.getRow(); j++) {
                    if (result.getElement(j, i) != 0) {
                        result = swapRow(result, i, j);
                        count++;
                        break;
                    }
                }
            }

            if (result.getElement(i, i) == 0) {
                continue;
            }

            for (int j = i + 1; j < M.getRow(); j++) {
                result = addRow(result, j, i, -result.getElement(j, i) / result.getElement(i, i));
            }
        }

        return new Pair<Matrix, Integer>(result, count);
    }

    /**
     * Mengembalikan determinan dari matriks M
     */
    public static Double determinan(Matrix M) {
        if (!M.isSquareMatrix()) {
            return null;
        }

        Pair<Matrix, Integer> p = reduceToTriangle(M);
        Matrix M1 = p.retrieveKey();
        Integer count = p.retrieveVal();

        double det = 1;
        for (int i = 0; i < M1.getRow(); i++) {
            det *= M1.getElement(i, i);
        }

        return det * Math.pow(-1, count);
    }
}
