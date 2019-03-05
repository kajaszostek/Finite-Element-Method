public class MatrixHBC {

    double alpha;
    double L2;
    double[][] finalHBC = new double[4][4];
    double[][][] NNT = new double[8][4][4];

    MatrixHBC(Element element, ReadTxt read) {

        alpha = read.getAlpha();
        int doesExists;
        double[][] tmpArray = new double[8][4];

        int counter = -1;

        for (int i = 0; i < 8; i++) // array of {N}
        {
            for (int j = 0; j < 4; j++) {
                tmpArray[i][j] = element.borderConditions.borderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[j];
            }
        }

        for (int i = 0; i < 8; i++) // array of {N}*{N}t
        {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    NNT[i][j][k] = tmpArray[i][k] * tmpArray[i][j];
                }
            }
        }

        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                finalHBC[i][j]=0;
            }
        }

        for (int i = 0; i < 4; i++) {  //walls

            doesExists = element.doBorderConditionsExist[i]; // 1 or 0

            if (i == 0 || i == 2) L2 = element.dX / 2;
            else L2 = element.dY / 2;

            for (int j = 0; j < 2; j++) // 2 points on wall
            {
                counter++;

                for (int k=0;k<4;k++)
                {
                    for (int n=0;n<4;n++)
                    {
                        finalHBC[k][n]+=NNT[counter][k][n]*doesExists*L2*alpha;
                    }
                }
            }
        }

    }


void showFinalHBC()
{
    System.out.println("\nShow final HBC \n");

    for (int i=0;i<4;i++)
    {
        for (int j=0;j<4; j++)
        {
            System.out.println(finalHBC[i][j]+"\t");
        }
        System.out.println("\n");
    }
}

}