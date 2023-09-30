package matrix.balikan;

import matrix.Matrix;
import matrix.spl.GaussJordan;

public class BalikanGaussJordan {
    /**
     * Mengembalikan matriks balikan dari matriks m
     */
    public Matrix balikanGaussJordan(Matrix m) {
        Matrix sementara = new Matrix(m.getRow(), 2 * m.getCol());
        Matrix hasil = new Matrix(m.getRow(), m.getCol());

        for (int i = 0; i < sementara.getRow(); i++) {
            for (int j = 0; j < sementara.getCol(); j++) {
                if (j < m.getCol()) {
                    sementara.setElement(i, j, m.getElement(i, j));
                } else {
                    if (j - m.getCol() == i) {
                        sementara.setElement(i, j, 1);
                    } else {
                        sementara.setElement(i, j, 0);
                    }
                }
            }
        }

        GaussJordan gaussjordan = new GaussJordan();
        sementara = gaussjordan.gaussJordan(sementara);

        int col, row = 0;
        for (int i = 0; i < sementara.getRow(); i++) {
            col = 0;
            for (int j = m.getCol(); j < sementara.getCol(); j++) {
                hasil.setElement(row, col, sementara.getElement(i, j));
                col++;
            }
            row++;
        }

        return hasil;
    }
}
