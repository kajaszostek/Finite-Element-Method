public class Node {

    int id;
    double  x, y;
    double [] temperature;

    Node(int ID, double X, double Y, ReadTxt read)
    {
        this.id=ID;
        this.x=X;
        this.y=Y;

        this.temperature = new double[(int)(read.getSimulationTime()/read.getStepTime())];
    }

    void setTemperature(double temp, int i)
    {
            temperature[i] = temp;
    }

    void showNodes(Element element) {

        System.out.println("\n\t*** Function - show Nodes ***\n");

        System.out.println("\nNodes of " + element.id + " element: \n");

        for (int i = 0; i < 4; i++) {
            System.out.println("Node " + element.nodes[i].id + "\t x = " + element.nodes[i].x + "\t y = " + element.nodes[i].y);
        }

    }

}
