package Main.Terminals;

import Main.Main;
import Main.Vehicles.Automobile;
import Main.Vehicles.*;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Main.Main.*;


public class CustomsTerminal
{
    private Coordinates coordinates;
    volatile private boolean isWorking = true;
    volatile private boolean isFree = true;

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
    public void processVehicle(Vehicle vehicle)
    {
        if(vehicle instanceof Truck)
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
            System.out.println("Carinska obrada kamiona");
            try
            {
                Thread.sleep(500);
            }
            catch (Exception e)
            {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
            if(((Truck) vehicle).getRealWeight() > ((Truck) vehicle).getDeclaredWeight())
            {
                vehicle.setCustomsProblem(true);
            }
            if(vehicle.hasCustomsProblem())
            {
                Incident incident = new Incident(vehicle,null);
                synchronized (Main.lock6)
                {
                    incident.writeToTextFile();
                    Platform.runLater(() -> {
                        moveToIncidentScene(vehicle);
                    });
                }
                System.out.println("Kamion je dodat u listu losih vozila");
            }
            System.out.println("Obradjeno");
            Platform.runLater(() -> {
                layout1.getChildren().remove(vehicle.getVehicleImage());
            });
        }
        else if(vehicle instanceof Automobile)
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
            System.out.println("Carinska obrada auta");
            try
            {
                Thread.sleep(2000);
            }
            catch (Exception e)
            {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
            System.out.println("Obradjeno");
            Platform.runLater(() -> {
                layout1.getChildren().remove(vehicle.getVehicleImage());
            });
        }
        else if(vehicle instanceof Bus)
        {
            System.out.println("Carinska obrada busa");
            ArrayList<Passenger> badPassengers = new ArrayList<Passenger>();
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
                if(!(vehicle.getPassengers().get(i).getBaggage() == null))
                {
                    if(vehicle.getPassengers().get(i).getBaggage().ContainsProhibitedItems())
                    {
                        badPassengers.add(vehicle.getPassengers().get(i));
                        if(i==0)
                        {
                            vehicle.setCustomsProblem(true);
                        }
                        else
                        {
                            vehicle.getPassengers().remove(i);
                            vehicle.setCustomsProblem(true);
                        }

                    }
                }
            }
            if(vehicle.hasCustomsProblem())
            {
                Incident incident = new Incident(vehicle,badPassengers);
                synchronized (Main.lock6)
                {
                    incident.writeToTextFile();
                    Platform.runLater(() -> {
                        moveToIncidentScene(vehicle);
                    });
                }
                System.out.println("Bus je dodat u listu losih vozila");
            }
            System.out.println("Obradjeno");
            Platform.runLater(() -> {
                layout1.getChildren().remove(vehicle.getVehicleImage());
            });
        }
    }
}
