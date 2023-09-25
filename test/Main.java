import matrix.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix M1 = new Matrix(2, 3);
        M1.readMatrix();
        M1.displayMatrix();

        Matrix M2 = M1.copyMatrix();

        M2.transpose().displayMatrix();
    }
}
