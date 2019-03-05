public class MatrixC {

    double [][] matrixC = new double[4][4];

    MatrixC(double transposedForMatrixC [][][])
    {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int n = 0; n < 4; n++) {
                    matrixC[i][j] += transposedForMatrixC[n][i][j];
                }
            }
        }
    }

    void showMatrixC() {
        System.out.println("\n\t***Function - show Matrix C***\n");

        for (int i = 0; i < 4; i++) {
            System.out.println("\n\t***New column***\n");
            for (int j = 0; j < 4; j++) {

                System.out.println(matrixC[i][j]+"\t");

            }

        }
    }

}
