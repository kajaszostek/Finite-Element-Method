import Jama.Matrix;

public class Temperature {

    double[][] globalH;
    double[][] globalC;

    double[] t0;
    double[] vectorP;
    double[] stepVectorP;

    double simulationTime;
    double step;
    double initialTemperature;

    Grid grid;

    Temperature(Grid grid) {

        this.grid = grid;
        globalC = grid.agregationC;
        globalH = grid.sum;
        vectorP = grid.globalVectorP;

        simulationTime = grid.readTxt.getSimulationTime();
        step = grid.readTxt.getStepTime();
        initialTemperature = grid.readTxt.getInitialTemperature();

        t0 = new double[vectorP.length];
        stepVectorP = new double[vectorP.length];


        simulation();
    }

    void simulation() {

       double[] temporaryTemperature = new double[t0.length];
        calculateTemperature(initialTemperature, temporaryTemperature);

        stepVectorP = countStep(step, globalC, temporaryTemperature, vectorP);

        for (int i = 0; i < (simulationTime / step); ++i) {

            double[] temp = countEquation(globalH, stepVectorP);

            stepVectorP = countStep(step, globalC, temp, vectorP);

            for (int j = 0; j < temp.length; ++j) {
                grid.arrayOfNodesForGrid[j].setTemperature(temp[j], i);
                temporaryTemperature[j]=temp[j];
            }

            minMax(temp, (i + 1) * step);
        }
    }

    double[] countEquation(double[][] matrixH, double[] vecP) {

        double[] finalTemperaturesVector = new double[vectorP.length];

        Matrix matrix = new Matrix(matrixH);
        Matrix reverseMatrix = matrix.inverse();

        double[][] inverseMatrixH = reverseMatrix.getArray();

        for (int i = 0; i < inverseMatrixH.length; ++i) {
            for (int j = 0; j < inverseMatrixH[0].length; ++j) {
                finalTemperaturesVector[i] += (inverseMatrixH[i][j] * vecP[j]); }
        }
        return finalTemperaturesVector;
    }

    double[] countStep(double step, double[][] globalC, double[] temporaryTemperature, double[] vectorP) {

        double[] temporaryP = new double[this.vectorP.length];

        for (int i = 0; i < this.vectorP.length; i++) {
            for (int j = 0; j < this.vectorP.length; j++) {
                temporaryP[i] += (-1)*(globalC[i][j] / step) * temporaryTemperature[j];
            }
            temporaryP[i] += vectorP[i];
            temporaryP[i] *=(-1);
        }
        return temporaryP;
    }

    void calculateTemperature(double initialTemperature, double[] t) {

        for (int i = 0; i < t0.length; i++) {
            t[i] = initialTemperature;
            //System.out.println(t[i]);
        }
    }

    void minMax(double[] temperatures, double step) {

        double min = temperatures[0];
        double max = temperatures[0];

        for (int i = 0; i < temperatures.length; i++) {
            if (temperatures[i] < min) min = temperatures[i];
            if (temperatures[i] > max) max = temperatures[i];
        }

        System.out.println(step + " Min: " + min + " Max: " + max);
    }

}
