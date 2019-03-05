public class DerivativeShapeFunctions {

    double N1Ksi, N2Ksi, N3Ksi, N4Ksi;
    double N1Eta, N2Eta, N3Eta, N4Eta;

    double[] dNdKsi = new double[4];
    double[] dNdEta = new double[4];

    DerivativeShapeFunctions(double ksi, double eta) {
        for (int i = 0; i < 4; i++) {
            NKsi(eta);
            NEta(ksi);
        }
    }

    void NKsi(double eta) {
        dNdKsi[0] = N1Ksi = -0.25 * (1 - eta);
        dNdKsi[1] = N2Ksi = 0.25 * (1 - eta);
        dNdKsi[2] = N3Ksi = 0.25 * (1 + eta);
        dNdKsi[3] = N4Ksi = -0.25 * (1 + eta);
    }

    void NEta(double ksi) {
        dNdEta[0] = N1Eta = -0.25 * (1 - ksi);
        dNdEta[1] = N2Eta = -0.25 * (1 + ksi);
        dNdEta[2] = N3Eta = 0.25 * (1 + ksi);
        dNdEta[3] = N4Eta = 0.25 * (1 - ksi);
    }

    void showDerivativeShapeFunctions(Element element) {
        System.out.println("\n\t*** Function - show derivative shape functions***\n");
        System.out.println("\nElement " + element.id + "\n");
        for (int i = 0; i < 4; i++) {

            System.out.println("\nPoint " + element.integrationPoints[i].id + "\n");

            System.out.println("\tdN/dKsi\n");
            for (int j = 0; j < 4; j++) {
                System.out.println("N" + (j + 1) + " = " + element.derivativeShapeFunctions[i].dNdKsi[j] + "\t");
            }
            System.out.println("\n\tdN/dEta\n");
            for (int j = 0; j < 4; j++) {
                System.out.println("N" + (j + 1) + " = " + element.derivativeShapeFunctions[i].dNdEta[j] + "\t");
            }

        }


    }
}
