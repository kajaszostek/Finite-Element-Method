public class Grid {

    ReadTxt readTxt = new ReadTxt();

    double high = 0;
    double width = 0;

    double maxHigh;
    double maxWidth;

    double deltaHigh;
    double deltaWidth;

    int numberOfNodes;
    int numberOfElements;

    int numberOfNodesInColumns;

    Element[] arrayOfElementsForGrid;
    Node[] arrayOfNodesForGrid;

    double[][] agregationH;
    double[][] agregationC;
    double[] vectorP;
    double [] globalVectorP;

    Temperature temperature;

    double [][] sum;

    Grid() {

        readTxt.read();

        this.maxHigh = readTxt.getH();
        this.maxWidth = readTxt.getL();
        this.deltaHigh = maxHigh / 3;
        this.deltaWidth = maxWidth / 3;
        this.numberOfNodes = readTxt.getNumberOfPoints();
        this.numberOfElements = readTxt.getNumberOfElements();
        this.numberOfNodesInColumns = readTxt.getNumberOfPointsInColumn();

        arrayOfElementsForGrid = new Element[numberOfElements];
        arrayOfNodesForGrid = new Node[numberOfNodes];

        agregationH = new double[numberOfNodes][numberOfNodes];
        agregationC = new double[numberOfNodes][numberOfNodes];
        vectorP = new double[numberOfNodes];
        globalVectorP = new double[numberOfNodes];

        this.setArrayOfNodesForGrid();

        this.setArrayOfElementsForGrid();

        this.setAgregationH();

        //showAgregationH();

        this.setAgregationC();

        //showAgregationC();

        this.setVectorP();

        showVectorP();

        partOfFinalOperations();

        //showSum();

        temperature = new Temperature(this);

    }

    public void showSum() {

        for (int i=0;i<numberOfNodes;i++)
        {
            for (int j=0;j<numberOfNodes;j++)
                System.out.println(sum[i][j]);
        }
    }

    public void setArrayOfNodesForGrid() {

        int tmp = numberOfNodesInColumns;
        int nodesId = 0;

        for (int k = 0; k < numberOfNodes; k++) {

            arrayOfNodesForGrid[k] = new Node(nodesId, width, high, readTxt);
            nodesId++;

            if (nodesId == tmp) {
                high = 0;
                width += deltaWidth;
                tmp += numberOfNodesInColumns;
            } else {
                high += deltaHigh;
            }

        }
    }

    public void setArrayOfElementsForGrid() {

        Node[] temporaryArrayOfNodes = new Node[4];

        int begin = 0;
        int end = numberOfNodesInColumns - 1;
        int counter = 1;

        for (int m = 0; m < numberOfElements; m++) {

            temporaryArrayOfNodes[0] = arrayOfNodesForGrid[begin];
            temporaryArrayOfNodes[1] = arrayOfNodesForGrid[begin + 4];
            temporaryArrayOfNodes[2] = arrayOfNodesForGrid[begin + 5];
            temporaryArrayOfNodes[3] = arrayOfNodesForGrid[begin + 1];

            begin++;

            Element element = new Element(temporaryArrayOfNodes, counter);

            arrayOfElementsForGrid[counter - 1] = element;
            counter++;

            if (begin == end) {
                end += numberOfNodesInColumns;
                begin++;
            }

        }

    }

    public void setAgregationH() {
        for (int i = 0; i < numberOfNodes; i++)
            for (int j = 0; j < numberOfNodes; j++)
                agregationH[i][j] = 0;

        for (int k = 0; k < numberOfElements; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    agregationH[arrayOfElementsForGrid[k].nodes[i].id][arrayOfElementsForGrid[k].nodes[j].id] += arrayOfElementsForGrid[k].finalMatrixH[i][j];
                }
            }
        }
    }

    public void setAgregationC() {
        for (int i = 0; i < numberOfNodes; i++)
            for (int j = 0; j < numberOfNodes; j++)
                agregationC[i][j] = 0;

        for (int k = 0; k < numberOfElements; k++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    agregationC[arrayOfElementsForGrid[k].nodes[i].id][arrayOfElementsForGrid[k].nodes[j].id] += arrayOfElementsForGrid[k].matrixC.matrixC[i][j];
                }
            }
        }
    }

    public void setVectorP() {
        for (int i = 0; i < numberOfElements; i++) {
            for (int j = 0; j < vectorP.length; j++) {
                for (int k = 0; k < 4; k++) {

                    if(arrayOfElementsForGrid[i].nodes[k].id == j)
                    globalVectorP[j] += arrayOfElementsForGrid[i].vectorP.vectorP[k];
                }

            }
        }
    }

    public void partOfFinalOperations()
    {
        int nH = readTxt.getnH();
        int nL  = readTxt.getnL();
        double [][] c_dT = new double[nH*nL][nH*nL];
        double stepTime = readTxt.getStepTime();

        sum = new double[nH*nL][nH*nL];

        for(int i=0; i<nH*nL; ++i){
            for(int j=0; j<nH*nL; ++j){
                sum[i][j] = agregationH[i][j];
            }
        }

        for(int i=0; i< nH*nL; ++i){
            for(int j=0; j<nH*nL; ++j){
                c_dT[i][j] = agregationC[i][j]/stepTime;
                sum[i][j]+=c_dT[i][j];
            }}
    }

    void showElements() {
        for (int i = 0; i < numberOfElements; i++) {
            System.out.println("Element " + arrayOfElementsForGrid[i].id + "\n");

            System.out.println("Nodes\n");

            System.out.println(arrayOfElementsForGrid[i].nodes[0].id + " Coordinates: " + arrayOfElementsForGrid[i].nodes[0].x + "," + arrayOfElementsForGrid[i].nodes[0].y);
            System.out.println(arrayOfElementsForGrid[i].nodes[1].id + " Coordinates: " + arrayOfElementsForGrid[i].nodes[1].x + "," + arrayOfElementsForGrid[i].nodes[1].y);
            System.out.println(arrayOfElementsForGrid[i].nodes[2].id + " Coordinates: " + arrayOfElementsForGrid[i].nodes[2].x + "," + arrayOfElementsForGrid[i].nodes[2].y);
            System.out.println(arrayOfElementsForGrid[i].nodes[3].id + " Coordinates: " + arrayOfElementsForGrid[i].nodes[3].x + "," + arrayOfElementsForGrid[i].nodes[3].y);
            System.out.println("\n");


        }

       /* arrayOfElementsForGrid[0].showShapeFunctions();
//        arrayOfElementsForGrid[0].showNodes();
//        arrayOfElementsForGrid[0].showDerivativeShapeFunctions();
*/
    }

    void showAgregationH() {

        System.out.println("\n Agregacja H \n");

        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                System.out.println(agregationH[j][i]);
            }

            System.out.println("\n");
        }

    }

    void showAgregationC() {
        System.out.println("\n Agregacja C \n");

        for (int i = 0; i < numberOfNodes; i++) {
            for (int j = 0; j < numberOfNodes; j++) {
                System.out.println(agregationC[j][i]);
            }

            System.out.println("\n");
        }
    }

    void showVectorP()
    {
        System.out.println("\n***VectorP***\n");

        for (int i=0;i<vectorP.length;i++)
        {
            System.out.println(globalVectorP[i]);
        }
    }
}
