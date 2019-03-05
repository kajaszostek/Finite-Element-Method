public class IntegrationPoints {

    int id;
    double ksi;
    double eta;

    IntegrationPoints(int ID)
    {
        id=ID;

        switch (id)
        {
            case 1:
            {
                ksi = -1.0/1.73;
                eta = -1.0/1.73;
                break;
            }
            case 2:
            {
                ksi=1.0/1.73;
                eta=-1.0/1.73;
                break;
            }
            case 3:
            {
                ksi=1.0/1.73;
                eta=1.0/1.73;
                break;
            }
            case 4:
            {
                ksi=-1.0/1.73;
                eta=1.0/1.73;
                break;
            }
            default:
                break;
        }
    }


}
