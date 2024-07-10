package Main.Terminals;

import Main.Vehicles.*;
import Main.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Main.Main.*;

public class PoliceTerminal
{
    private Coordinates coordinates;
    private boolean isWorking = true;
    private boolean isFree = true;
    public boolean isWorking()
    {
        return this.isWorking;
    }
    public boolean isFree()
    {
        return this.isFree;
    }
    public void setWorking(boolean working)
    {
        this.isWorking = working;
    }
    public void setFree(boolean free)
    {
        this.isFree = free;
    }
    public void setCoordinates(Coordinates coordinates)
    {
        this.coordinates = coordinates;
    }
    public Coordinates getCoordinates()
    {
        return this.coordinates;
    }
    public void  processVehicle(Vehicle vehicle)
    {
        ArrayList<Passenger> badPassengers = new ArrayList<>();
        boolean policeProblem = false;
        if(vehicle instanceof Truck)
        {
            System.out.println("----------Policijska obrada kamiona----------");
            System.out.println(vehicle.getName());
            List<Passenger> passengers = vehicle.getPassengers();

            for(int i=0;i<passengers.size();i++)
            {
                try
                {
                    if(isPaused)
                    {
                        synchronized (Main.lock5)
                        {
                            Main.lock5.wait();
                        }
                    }
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                try
                {
                    Thread.sleep(500);
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                if(!passengers.get(0).getValidityStatus() && i==0)
                {
                    badPassengers.add(passengers.get(i));
                    vehicle.getPassengers().remove(i);
                    vehicle.setPoliceProblem(true);
                    policeProblem = true;
                }
                else if(!passengers.get(i).getValidityStatus() && i!=0)
                {
                    badPassengers.add(passengers.get(i));
                    vehicle.getPassengers().remove(i);
                    policeProblem = true;
                }
            }
            if(policeProblem)
            {
                Incident incident = new Incident(vehicle,badPassengers);
                synchronized (Main.lock7)
                {
                    incident.writeToBinaryFile();
                }
                System.out.println("Kamion je dodat u listu losih vozila");
            }
        }
        else if(vehicle instanceof Automobile)
        {
            System.out.println("----------Policijska obrada auta----------");
            System.out.println(vehicle.getName());
            List<Passenger> passengers = vehicle.getPassengers();

            // TODO lista kaznjenih putnika
            for(int i=0;i<passengers.size();i++)
            {
                try
                {
                    if(isPaused)
                    {
                        synchronized (Main.lock5)
                        {
                            Main.lock5.wait();
                        }
                    }
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                try
                {
                    Thread.sleep(500);
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                if(!passengers.get(i).getValidityStatus() && i!=0)
                {
                    badPassengers.add(passengers.get(i));
                    passengers.remove(i);
                    policeProblem = true;
                }
                else if(!passengers.get(0).getValidityStatus() && i==0)
                {
                    badPassengers.add(passengers.get(i));
                    vehicle.getPassengers().remove(i);
                    vehicle.setPoliceProblem(true);
                    policeProblem = true;
                }
            }
            if(policeProblem)
            {
                Incident incident = new Incident(vehicle,badPassengers);
                synchronized (Main.lock7)
                {
                    incident.writeToBinaryFile();
                }
                System.out.println("Auto je dodat u listu losih vozila");
            }
        }
        else if(vehicle instanceof Bus)
        {
            System.out.println("----------Policijska obrada busa----------");
            List<Passenger> passengers = vehicle.getPassengers();
            for(int i=0;i<vehicle.getPassengers().size();i++)
            {
                try
                {
                    if(isPaused)
                    {
                        synchronized (Main.lock5)
                        {
                            Main.lock5.wait();
                        }
                    }
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                try
                {
                    Thread.sleep(100);
                }
                catch (Exception e)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                }
                if(!vehicle.getPassengers().get(i).getValidityStatus() && i!=0)
                {
                    badPassengers.add(passengers.get(i));
                    vehicle.getPassengers().remove(i);
                    policeProblem = true;
                }
                else if(!vehicle.getPassengers().get(0).getValidityStatus() && i==0)
                {
                    badPassengers.add(passengers.get(i));
                    vehicle.getPassengers().remove(i);
                    vehicle.setPoliceProblem(true);
                    policeProblem = true;
                }
            }
            if(policeProblem)
            {
                Incident incident = new Incident(vehicle,badPassengers);
                synchronized (Main.lock7)
                {
                    incident.writeToBinaryFile();
               }
                System.out.println(vehicle.getName() + " je dodat u listu losih vozila");
            }
        }
    }
}
