public class DerivativeCoordinates {

    double dXdKsi = 0, dYdKsi = 0, dXdEta = 0, dYdEta = 0;
    double [] derivativeCoordinates = new double [4];

    DerivativeCoordinates(Element element, int i) {

        for (int j = 0; j < 4; j++) {
            dXdKsi += element.nodes[j].x * element.derivativeShapeFunctions[i].dNdKsi[j];
            dYdKsi += element.nodes[j].y * element.derivativeShapeFunctions[i].dNdKsi[j];
            dXdEta += element.nodes[j].x * element.derivativeShapeFunctions[i].dNdEta[j];
            dYdEta += element.nodes[j].y * element.derivativeShapeFunctions[i].dNdEta[j];

        }

        derivativeCoordinates[0]=dXdKsi;
        derivativeCoordinates[1]=dYdKsi;
        derivativeCoordinates[2]=dXdEta;
        derivativeCoordinates[3]=dYdEta;

    }


}
