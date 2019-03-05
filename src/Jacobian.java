public class Jacobian {

    double [] jacobian = new double[4];
    double [] jacobianTransformation = new double[4];
    double determinant;
    double [] jacobianTransformationWithDeterminant = new double[4];

    Jacobian(Element element, int id){
        jacobian[0]=element.derivativeCoordinates[id].dXdKsi;
        jacobian[1]=element.derivativeCoordinates[id].dYdKsi;
        jacobian[2]=element.derivativeCoordinates[id].dXdEta;
        jacobian[3]=element.derivativeCoordinates[id].dYdEta;

        //System.out.println(element.derivativeCoordinates[id].dXdKsi);


        determinant=jacobian[0]*jacobian[3]-jacobian[1]*jacobian[2];

        jacobianTransformation[0]=jacobian[3];
        jacobianTransformation[1]=(-1)*jacobian[1];
        jacobianTransformation[2]=(-1)*jacobian[2];
        jacobianTransformation[3]=jacobian[0];

        for(int i=0;i<4;i++)
        {
            jacobianTransformationWithDeterminant[i]=jacobianTransformation[i]/determinant;
        }

    }

    void showJacobian(Element element) {
        System.out.println("\t***Function - Show Jacobian***\n");

        for (int i = 0; i < 4; i++) {
            System.out.println("Point " + element.integrationPoints[i].id + "\n");
            System.out.format("Determinant: %.7f\n",element.jacobian[i].determinant);
            System.out.println("Transformed jacobian: ");

            for (int j = 0; j < 4; j++) {

                System.out.format("\t%.4f\n" ,element.jacobian[i].jacobianTransformationWithDeterminant[j]);
            }
        }
    }
}
