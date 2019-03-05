public class Element {

    ReadTxt readTxt = new ReadTxt();

    int id;

    Node[] nodes = new Node[4];
    IntegrationPoints[] integrationPoints = new IntegrationPoints[4];
    ShapeFunctions[] shapeFunctions = new ShapeFunctions[4];
    DerivativeShapeFunctions[] derivativeShapeFunctions = new DerivativeShapeFunctions[4];
    DerivativeCoordinates[] derivativeCoordinates = new DerivativeCoordinates[4];  //every node has 4 values so we need to sum up them
    Jacobian[] jacobian = new Jacobian[4];
    int[] doBorderConditionsExist = new int[4];

    int borders;

    BorderConditions borderConditions;

    double[][] derivativesDxDshapeFunctions = new double[4][4];
    double[][] derivativesDyDshapeFunctions = new double[4][4];

    double[][][] transposedArrayOfX = new double[4][4][4];
    double[][][] transposedArrayOfY = new double[4][4][4];

    double[][][] lastOperations = new double[4][4][4];

    double k; //conductivity
    double c;  //specific heat
    double ro;  //density
    double dX;
    double dY;

    MatrixH matrixH;
    MatrixHBC matrixHBC;
    double [][] finalMatrixH = new double [4][4];


    double[][][] transposedForMatrixC = new double[4][4][4];
    MatrixC matrixC;

    //ShapeFunctions[] shapeFunctionsForBorderConditions = new ShapeFunctions[2];

    VectorP vectorP;


    Element(Node[] arrayOfNodes, int ID) {

        readTxt.read();

        k = readTxt.getConductivity();
        c = readTxt.getC();
        ro = readTxt.getRo();

        this.id = ID;

        //System.out.println("\n"+this.id+"\n");


        for (int j = 0; j < 4; j++)
            doBorderConditionsExist[j] = 0;

        for (int i = 0; i < 4; i++) {
            this.nodes[i] = arrayOfNodes[i];

            if (nodes[i].y == 0)
                doBorderConditionsExist[0] = 1;
            if (nodes[i].x == 0)
                doBorderConditionsExist[3] = 1;
            if (nodes[i].x == 0.1)
                doBorderConditionsExist[1] = 1;
            if (nodes[i].y == 0.1)
                doBorderConditionsExist[2] = 1;
        }

        for (int i = 0; i < 4; i++) {
            if (doBorderConditionsExist[i] == 1)
                borders++;
        }

        dX = nodes[1].x-nodes[0].x;
        dY = nodes[2].y-nodes[1].y;

        borderConditions = new BorderConditions(this);

        for (int i = 0; i < 4; i++) {
            integrationPoints[i] = new IntegrationPoints(i + 1);
            shapeFunctions[i] = new ShapeFunctions(integrationPoints[i].ksi, integrationPoints[i].eta);
            derivativeShapeFunctions[i] = new DerivativeShapeFunctions(this.integrationPoints[i].ksi, this.integrationPoints[i].eta);
            derivativeCoordinates[i] = new DerivativeCoordinates(this, i);
            jacobian[i] = new Jacobian(this, i);
        }

        this.setDerivativesDxDshapeFunctions();
        this.setDerivativesDyDshapeFunctions();
        this.transposing();

        this.setLastOperations();

        this.matrixH = new MatrixH(lastOperations);
        this.matrixHBC = new MatrixHBC(this, readTxt);
        setFinalMatrixH();

        this.transposingForMatrixC();

        this.matrixC = new MatrixC(transposedForMatrixC);

        vectorP = new VectorP(this, this.readTxt);

    }

    public void transposing() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    transposedArrayOfX[i][k][j] = derivativesDxDshapeFunctions[i][j] * derivativesDxDshapeFunctions[i][k];
                    transposedArrayOfY[i][k][j] = derivativesDyDshapeFunctions[i][j] * derivativesDyDshapeFunctions[i][k];

                    transposedArrayOfY[i][k][j] *= this.jacobian[i].determinant;
                    transposedArrayOfX[i][k][j] *= this.jacobian[i].determinant;
                }
            }
        }
    }

    public void setLastOperations() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int m = 0; m < 4; m++) {
                    this.lastOperations[i][j][m] = k * (transposedArrayOfY[i][j][m] + transposedArrayOfX[i][j][m]);
                }
            }
        }
    }

    public void setDerivativesDxDshapeFunctions() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                derivativesDxDshapeFunctions[i][j] = this.jacobian[i].jacobianTransformationWithDeterminant[0] * this.derivativeShapeFunctions[i].dNdKsi[j] + this.jacobian[i].jacobianTransformationWithDeterminant[1] * this.derivativeShapeFunctions[i].dNdEta[j];
            }
        }
    }

    public void setDerivativesDyDshapeFunctions() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                derivativesDyDshapeFunctions[i][j] = this.jacobian[i].jacobianTransformationWithDeterminant[2] * this.derivativeShapeFunctions[i].dNdKsi[j] + this.jacobian[i].jacobianTransformationWithDeterminant[3] * this.derivativeShapeFunctions[i].dNdEta[j];
            }
        }
    }

    public void setFinalMatrixH() {

        for (int i=0;i<4;i++)
        {
            for (int j=0;j<4;j++)
            {
                finalMatrixH[i][j]+=matrixH.matrixH[i][j]+matrixHBC.finalHBC[i][j];
            }
        }

    }

    void transposingForMatrixC() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    transposedForMatrixC[i][k][j] = shapeFunctions[i].shapeFunctions[j] * shapeFunctions[i].shapeFunctions[k] * this.jacobian[i].determinant * c * ro;
                }
            }
        }
    }

    void showdXdYdN() {
        for (int i = 0; i < 4; i++) {
            System.out.println("\n\t***Function - show dNdX and dNdY\n");
            System.out.println("Element - " + this.id + " \n");
            System.out.println("Point - " + (i + 1) + " dN/dX");
            for (int j = 0; j < 4; j++) {
                System.out.println(this.derivativesDxDshapeFunctions[i][j] + "\t");
            }

            System.out.println("\nPoint - " + (i + 1) + " dN/dY");
            for (int j = 0; j < 4; j++) {
                System.out.println(this.derivativesDyDshapeFunctions[i][j] + "\t");
            }
        }
    }

    void showTranspose() {
        System.out.println("\n\t***Function - show transpose***\n");

        for (int i = 0; i < 4; i++) {
            System.out.println("PC " + (i + 1) + "\n");

            for (int j = 0; j < 4; j++) {
                System.out.println("PC main " + (j + 1) + "\n");

                for (int k = 0; k < 4; k++) {
                    System.out.println(transposedArrayOfY[i][k][j]);
                }
                System.out.println("\n");
            }
        }
    }

    void showLastOperations() {

        System.out.println("\n\t***Function - show last operations***\n");
        for (int i = 0; i < 4; i++) {
            System.out.println("\nNew point\n");
            for (int j = 0; j < 4; j++) {
                System.out.println("\nNew column\n");
                for (int m = 0; m < 4; m++) {
                    System.out.println(this.lastOperations[i][j][m] + "\t");
                }
            }
        }
    }

    void showTransposedForMatrixC() {

        System.out.println("\n\t***Function - show transposed for Matrix C***\n");

        for (int i = 0; i < 4; i++) {
            System.out.println("\nNew point " + (i + 1) + "\n");
            for (int j = 0; j < 4; j++) {
                System.out.println("\nNew column\n");
                for (int n = 0; n < 4; n++) {
                    System.out.format("%.4f\n", transposedForMatrixC[i][j][n]);
                }
            }
        }
    }

    void showDoBorderConditionsExist() {
        System.out.println("\n\tElement " + this.id + "\n");
        System.out.println("\n\t***Function - show do Border Conditions exist\n");
        {
            for (int i = 0; i < 4; i++) {
                System.out.println(this.doBorderConditionsExist[i] + "\t");
            }
        }
    }

    void showFinalMatrixH()
    {
        System.out.println("\nSHOW FINAL MATRIX H\n");

        for (int i=0;i<4;i++)
        {
            System.out.println("\n");

            for (int j=0;j<4;j++)
            {
                System.out.println(finalMatrixH[i][j]);
            }
        }
    }

}

