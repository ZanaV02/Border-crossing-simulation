package Main.Vehicles;

import java.io.Serializable;
import java.util.Random;

public class Passenger implements Serializable
{
    private static int ID;
    private String name;
    private Baggage baggage = null;
    private boolean isValidDocument = true;

    public Passenger()
    {
        this.name = "Passenger " + ++ID;
        Random random = new Random();
        int num = random.nextInt(1,101);
        if(num<=70)
        {
            this.baggage = new Baggage();
        }
        if(random.nextInt(1,101)<=3)
        {
            this.isValidDocument = false;
        }
    }
    public void setValidDocument(boolean validity)
    {
        this.isValidDocument = validity;
    }
    public boolean getValidityStatus()
    {
        return this.isValidDocument;
    }
    public Baggage getBaggage()
    {
        return this.baggage;
    }
    public int getID()
    {
        return this.ID;
    }
    public String getName()
    {
        return this.name;
    }
}
