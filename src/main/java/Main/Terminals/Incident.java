package Main.Terminals;

import Main.Vehicles.Passenger;
import Main.Vehicles.Vehicle;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Main.*;

public class Incident implements Serializable
{
    private Vehicle vehicle;
    private ArrayList<Passenger> passengers;

    public Incident(Vehicle vehicle, ArrayList<Passenger> passengers)
    {
        this.vehicle = vehicle;
        this.passengers = passengers;
    }
    public Vehicle getVehicle()
    {
        return this.vehicle;
    }
    public ArrayList<Passenger> getPassengers()
    {
        return this.passengers;
    }

    public void writeToTextFile()
    {
        try
        {
            PrintWriter writer = new PrintWriter(new FileWriter(Main.pathToTxt,true));
            writer.print(this.vehicle.getName() + "#");
            if(this.passengers != null)
            {
                for(int i=0;i<this.passengers.size();i++)
                {
                    writer.print(this.passengers.get(i).getName() + "#");
                }
            }
            writer.println();
            writer.close();
        }
        catch (IOException e)
        {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }
    public void writeToBinaryFile()
    {
        File file = new File(Main.pathToBinary);
        try
        {
            if(!file.exists())
            {
                    ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(Main.pathToBinary, true));
                    writer.writeObject(this);
                    writer.close();
            }
            else
            {
                    ObjectOutputStream2 writer = new ObjectOutputStream2(new FileOutputStream(Main.pathToBinary, true));
                    writer.writeObject(this);
                    writer.close();
            }

        }
        catch (IOException e)
        {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }
}
