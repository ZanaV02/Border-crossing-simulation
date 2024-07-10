package Main.Vehicles;

import java.io.Serializable;
import java.util.Random;

public class Baggage implements Serializable
{
    private boolean containsProhibitedItems = false;
    public Baggage()
    {
        Random random = new Random();
        int num = random.nextInt(1,101);
        if(num<=10)
        {
            this.containsProhibitedItems=true;
        }
    }
    public boolean ContainsProhibitedItems()
    {
        return this.containsProhibitedItems;
    }
}
