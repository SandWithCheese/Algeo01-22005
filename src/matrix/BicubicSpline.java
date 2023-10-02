package matrix;

import matrix.Matrix;

public class BicubicSpline {
    public double f(double x, double y, Matrix aij) {
        int idx = 0;
        double z = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                z += aij.getElement(idx, 0) * Math.pow(x, i) * Math.pow(y, j);
                idx++;
            }
        }

        return z;
    }

    public Matrix bicubicSpline() {
        Matrix M = new Matrix(16, 16);
        int curRow = 0;

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int curCol = 0;
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        M.setElement(curRow, curCol, Math.pow(x, i) * Math.pow(y, j));
                        curCol++;
                    }
                }
                curRow++;
            }
        }

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int curCol = 0;
                for (int j = 0; j < 4; j++) {
                    M.setElement(curRow, curCol, 0);
                    curCol++;
                    for (int i = 1; i < 4; i++) {
                        M.setElement(curRow, curCol, i * Math.pow(x, i - 1) * Math.pow(y, j));
                        curCol++;
                    }
                }
                curRow++;
            }
        }

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int curCol = 0;
                M.setElement(curRow, curCol, 0);
                curCol++;
                M.setElement(curRow, curCol, 0);
                curCol++;
                M.setElement(curRow, curCol, 0);
                curCol++;
                M.setElement(curRow, curCol, 0);
                curCol++;
                for (int j = 1; j < 4; j++) {
                    for (int i = 0; i < 4; i++) {
                        M.setElement(curRow, curCol, j * Math.pow(x, i) * Math.pow(y, j - 1));
                        curCol++;
                    }
                }
                curRow++;
            }
        }

        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                int curCol = 0;
                M.setElement(curRow, curCol, 0);
                curCol++;
                M.setElement(curRow, curCol, 0);
                curCol++;
                M.setElement(curRow, curCol, 0);
                curCol++;
                M.setElement(curRow, curCol, 0);
                curCol++;
                for (int j = 1; j < 4; j++) {
                    M.setElement(curRow, curCol, 0);
                    curCol++;
                    for (int i = 1; i < 4; i++) {
                        M.setElement(curRow, curCol, i * j * Math.pow(x, i - 1) * Math.pow(y, j - 1));
                        curCol++;
                    }
                }
                curRow++;
            }
        }

        return M;
    }
}
