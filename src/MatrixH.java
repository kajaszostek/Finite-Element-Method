public class MatrixH {

    double [][] matrixH = new double[4][4];

    MatrixH(double lastOperations[][][])
    {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int n = 0; n < 4; n++) {
                    matrixH[i][j] += lastOperations[n][i][j];
                }
            }
        }
    }

    void showMatrixH() {
        System.out.println("\n\t***Function - show Matrix H***\n");

        for (int i = 0; i < 4; i++) {
            System.out.println("\n\t***New column***\n");
            for (int j = 0; j < 4; j++) {

                System.out.println(matrixH[i][j]+"\t");

            }

        }
    }

}
