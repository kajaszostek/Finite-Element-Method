public class VectorP {

    double[] vectorP;
    double alpha;
    double temp;

    //-alpha*{N}*temp*L/2

    VectorP(Element element, ReadTxt read) {

        vectorP = new double[4];
        alpha = read.getAlpha();
        temp = read.getAmbientTemperature();

        double[] tmpArrayP = new double[4];

        for (int i = 0; i < 4; i++) {
            tmpArrayP[i] = 0;
        }

        int counter = -1;
        int doesBCExist;
        double L2;

        for (int i = 0; i < 4; i++) {
            doesBCExist = element.doBorderConditionsExist[i];

            if (i == 0 || i == 2) L2 = element.dX / 2;
            else L2 = element.dY / 2;

            for (int j = 0; j < 2; j++) {
                counter++;
                for (int k = 0; k < 4; k++) {
                    tmpArrayP[k] += (element.borderConditions.borderConditionPoints[counter].borderConditionPointsShapeFunctions.shapeFunctions[k] * doesBCExist * L2);
                }
            }
        }

        for (int n = 0; n < 4; n++) {
            vectorP[n]=tmpArrayP[n]*(-1)*alpha*temp;
        }

    }

    void showVectorP(Element element)
    {
        System.out.println("\nElement: "+element.id+"\n");
        System.out.println("Vector P: \n");

        for (int i=0;i<4;i++)
        {
            System.out.println(this.vectorP[i]);
        }

        System.out.println("\n");
    }

}
