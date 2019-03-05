public class ShapeFunctions {

    double [] shapeFunctions = new double [4];

    ShapeFunctions(double ksi, double eta)
    {
        shapeFunctions[0]=0.25*(1-ksi)*(1-eta);
        shapeFunctions[1]=0.25*(1+ksi)*(1-eta);
        shapeFunctions[2]=0.25*(1+ksi)*(1+eta);
        shapeFunctions[3]=0.25*(1-ksi)*(1+eta);
    }

    void showShapeFunctions()
    {
        System.out.println("\n\t*** Function - show Shape Functions ***\n");

        System.out.println("\nShape functions\n");

        for (int i = 0; i < 4; i++) {
            System.out.println("N"+(i+1)+"= " + this.shapeFunctions[i] + "\t");
        }
    }

}
