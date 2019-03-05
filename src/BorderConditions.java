public class BorderConditions {

    BorderConditionPoints [] borderConditionPoints = new BorderConditionPoints[8];
    BorderConditionPoints [] finalBorderConditionPoints = new BorderConditionPoints[4];
    double [][] transformedShapeFunctionsMatrix = new double[4][4];

    int numberOfBorderPoints;


    int alpha = 300;


    BorderConditions(Element element) {

        for (int i = 0; i < 8; i++) {
            borderConditionPoints[i] = new BorderConditionPoints(i + 1);
        }
        int counter=0;

        if(element.doBorderConditionsExist[0] == 1)
        {
            finalBorderConditionPoints[counter] = borderConditionPoints[0];
            finalBorderConditionPoints[counter+1] = borderConditionPoints[1];
            counter+=2;
        }

        if(element.doBorderConditionsExist[1] == 1)
        {
            finalBorderConditionPoints[counter] = borderConditionPoints[2];
            finalBorderConditionPoints[counter+1] = borderConditionPoints[3];
            counter+=2;
        }

        if(element.doBorderConditionsExist[2] == 1)
        {
            finalBorderConditionPoints[counter] = borderConditionPoints[4];
            finalBorderConditionPoints[counter+1] = borderConditionPoints[5];
            counter+=2;
        }

        if (element.doBorderConditionsExist[3] == 1)
        {
            finalBorderConditionPoints[counter] = borderConditionPoints[6];
            finalBorderConditionPoints[counter+1] = borderConditionPoints[7];
            counter+=2;
        }

        numberOfBorderPoints=counter;

        if(counter == 2)
        {
            finalBorderConditionPoints[2] = borderConditionPoints[0];
            finalBorderConditionPoints[3] = borderConditionPoints[0];

        }

        if(element.doBorderConditionsExist[0] == 0 && element.doBorderConditionsExist[1] == 0 && element.doBorderConditionsExist[2] == 0 && element.doBorderConditionsExist[3] == 0)
        {
            for (int i=0;i<4;i++)
            {
                finalBorderConditionPoints[i]= borderConditionPoints[0];

                finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[0] = 0;
                finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[1] = 0;
                finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[2] = 0;
                finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[3] = 0;

            }
        }

        transformShapeFunctionsMatrix();

    }

     void showTransformedShapeFunctionsMatrix() {

         System.out.println("\n\t*** Function - show Shape Functions Transformed Matrix ***\n");

         System.out.println("\nShape functions\n");

         for (int i = 0; i < 4; i++) {
             System.out.println("Point " + finalBorderConditionPoints[i].id + "\n");
             System.out.println("N1 = " + finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[i] + "\t");
             System.out.println("N2 = " + finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[i] + "\t");
             System.out.println("N3 = " + finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[i] + "\t");
             System.out.println("N4 = " + finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[i] + "\t");
         }

    }

    void showDoBorderConditionsExist(Element element)
    {
        System.out.println("\n\t***Function - show do Border Conditions exist\n");
        {
            for (int i=0;i<4;i++)
            {
                System.out.println(element.doBorderConditionsExist[i]+"\t");
            }
        }
    }

    void transformShapeFunctionsMatrix()
    {
        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                transformedShapeFunctionsMatrix[i][j]+= (finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[j]*finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.shapeFunctions[i])*alpha;
            }
        }
    }

    void showFinalBCP()
    {
        System.out.println("Final BC POINTS: \n");
        for (int i=0;i<4;i++)
        {
            System.out.println(finalBorderConditionPoints[i].ksi +" " +finalBorderConditionPoints[i].eta);
        }
        System.out.println("\n");
    }

    void showBC()
    {
        System.out.println("Final BC POINT: \n");
        for (int i=0;i<4;i++)
        {
            System.out.println(finalBorderConditionPoints[i].ksi +" " +finalBorderConditionPoints[i].eta);

                finalBorderConditionPoints[i].borderConditionPointsShapeFunctions.showShapeFunctions();

        }
        System.out.println("\n");
    }
}
