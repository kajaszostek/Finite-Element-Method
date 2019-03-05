public class BorderConditionPoints {

    double ksi;
    double eta;
    double id;
    double [] ksiEta = new double [2];
    ShapeFunctions borderConditionPointsShapeFunctions;

    BorderConditionPoints(int id)
    {
        this.id = id;

        switch(id)
        {
//                borderConditionPointsShapeFunctions = new ShapeFunctions(1,1);
//                borderConditionPointsShapeFunctions.shapeFunctions[0]=borderConditionPointsShapeFunctions.N1=0;
//                borderConditionPointsShapeFunctions.shapeFunctions[1]=borderConditionPointsShapeFunctions.N2=0;
//                borderConditionPointsShapeFunctions.shapeFunctions[2]=borderConditionPointsShapeFunctions.N3=0;
//                borderConditionPointsShapeFunctions.shapeFunctions[3]=borderConditionPointsShapeFunctions.N4=0;
            case 1:
                ksiEta[0]=ksi = -1/1.73;
                ksiEta[1]=eta = -1;
                break;
            case 2:
                ksiEta[0]=ksi = 1/1.73;
                ksiEta[1]=eta = -1;
                break;
            case 3:
                ksiEta[0]=ksi = 1;
                ksiEta[1]=eta = -1/1.73;
                break;
            case 4:
                ksiEta[0]=ksi = 1;
                ksiEta[1]=eta = 1/1.73;
                break;
            case 5:
                ksiEta[0]=ksi = 1/1.73;
                ksiEta[1]=eta = 1;
                break;
            case 6:
                ksiEta[0]=ksi = -1/1.73;
                ksiEta[1]=eta = 1;
                break;
            case 7:
                ksiEta[0]=ksi = -1;
                ksiEta[1]=eta = 1/1.73;
                break;
            case 8:
                ksiEta[0]=ksi = -1;
                ksiEta[1]=eta = -1/1.73;
            default:
                break;
        }
            borderConditionPointsShapeFunctions = new ShapeFunctions(ksi, eta);

    }

}
