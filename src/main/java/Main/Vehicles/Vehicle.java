package Main.Vehicles;

import Main.Main;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static Main.Main.*;

public abstract class Vehicle implements Runnable
{
    protected static int IDcounter;

    protected int vehicleID;
    protected String name;
    protected List<Passenger> passengers;
    protected boolean hasPoliceProblem = false;
    protected boolean hasCustomProblem = false;
    transient protected ImageView vehicleImage;
    public transient int x;
    public transient int y;
    public void setPoliceProblem(boolean policeProblem)
    {
        this.hasPoliceProblem = policeProblem;
    }

    public void setCustomsProblem(boolean customProblem)
    {
        this.hasCustomProblem = customProblem;
    }

    public boolean hasPoliceProblem()
    {
        return this.hasPoliceProblem;
    }

    public boolean hasCustomsProblem()
    {
        return this.hasCustomProblem;
    }
    public ImageView getVehicleImage()
    {
        return this.vehicleImage;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public String getName()
    {
        return this.name;
    }


    public int getVehicleID() {
        return this.vehicleID;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj != null && obj.getClass() == this.getClass()) {
            return this.vehicleID == ((Vehicle) obj).vehicleID;
        }
        return false;
    }
    public List<Passenger> getPassengers()
    {
        return this.passengers;
    }

    public abstract void generateNumberOfPassengers();
    public void processVehicle()
    {
        boolean processed = false;
        boolean processed2 = false;
        while (!processed)
        {
            try
            {
                if (isPaused)
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
            Vehicle firstVehicle = Main.vehicleQueue.peek();
            if(firstVehicle != null && firstVehicle.equals(this))
            {
                if (Main.policeTerminal1.isFree() && Main.policeTerminal1.isWorking())
                {
                    synchronized (Main.lock3)
                    {
                        Platform.runLater(() ->
                        {
                            if (this.getX() == startX && this.getY() == startY)
                            {
                                layout1.getChildren().remove(this.getVehicleImage());
                                layout1.add(this.getVehicleImage(), Main.policeTerminal1.getCoordinates().getX(), Main.policeTerminal1.getCoordinates().getY());
                                this.setX(Main.policeTerminal1.getCoordinates().getX());
                                this.setY(Main.policeTerminal1.getCoordinates().getY());
                                Main.vehicleList.remove(0);
                                drawVehicle();
                            }
                        });
                        Main.policeTerminal1.setFree(false);
                        System.out.println("Policijski terminal 1 je zauzet.");
                        Main.vehicleQueue.poll();
                        Main.policeTerminal1.processVehicle(this);
                        processed = true;
                        if (!this.hasPoliceProblem() && processed)
                        {
                            Main.onCustomTerminal.add(this);
                        }
                    }
                    while (!processed2 && !this.hasPoliceProblem() && processed)
                    {
                          if(Main.customsTerminal.isFree() && Main.customsTerminal.isWorking() && Main.onCustomTerminal.peek().equals(this))
                          {
                            System.out.println("Nije imao problem sa policijom.");
                            Main.customsTerminal.setFree(false);
                            Main.policeTerminal1.setFree(true);
                            Main.onCustomTerminal.poll();
                            Platform.runLater(() -> {
                                moveToCustom(this.getVehicleImage());
                            });
                            Main.customsTerminal.processVehicle(this);
                            System.out.println(this.getName() + " se obradio na carini.");
                            Main.customsTerminal.setFree(true);
                            processed2 = true;
                        }
                    }
                    //System.out.println("processed is true");
                    if (this.hasPoliceProblem() && processed)
                    {
                        System.out.println("Imao problem sa policijom");
                        Platform.runLater(() -> {
                            layout1.getChildren().remove(this.getVehicleImage());
                            moveToIncidentScene(this);
                        });
                        Main.policeTerminal1.setFree(true);
                    }
                    processed2 = false;

                }
                else if (Main.policeTerminal2.isFree() && Main.policeTerminal2.isWorking())
                {
                    synchronized (Main.lock4)
                    {
                        Platform.runLater(() -> {
                            if (this.getX() == startX && this.getY() == startY) {
                                layout1.getChildren().remove(this.getVehicleImage());
                                layout1.add(this.getVehicleImage(), Main.policeTerminal2.getCoordinates().getX(), Main.policeTerminal2.getCoordinates().getY());
                                this.setX(Main.policeTerminal2.getCoordinates().getX());
                                this.setY(Main.policeTerminal2.getCoordinates().getY());
                                Main.vehicleList.remove(0);
                                drawVehicle();
                            }
                        });
                    }
                    Main.policeTerminal2.setFree(false);
                    System.out.println("Policijski terminal 2 je zauzet.");
                    Main.vehicleQueue.poll();
                    Main.policeTerminal2.processVehicle(this);
                    processed = true;
                    if (!this.hasPoliceProblem() && processed)
                        Main.onCustomTerminal.add(this);
                    while (!processed2 && !this.hasPoliceProblem() && processed)
                    {
                          if(Main.customsTerminal.isFree() && Main.customsTerminal.isWorking() && Main.onCustomTerminal.peek().equals(this))
                          {
                            System.out.println("Nije imao problem sa policijom.");
                            Main.customsTerminal.setFree(false);
                            Main.policeTerminal2.setFree(true);
                            Main.onCustomTerminal.poll();
                            Platform.runLater(() -> {
                                moveToCustom(this.getVehicleImage());
                            });
                            Main.customsTerminal.processVehicle(this);
                            System.out.println(this.getName() + " se obradio na carini.");
                            Main.customsTerminal.setFree(true);
                            processed2 = true;
                        }
                    }
                    if (this.hasPoliceProblem() && processed)
                    {
                        System.out.println("Imao problem sa policijom");
                        Platform.runLater(() -> {
                            layout1.getChildren().remove(this.getVehicleImage());
                            moveToIncidentScene(this);
                        });
                        Main.policeTerminal2.setFree(true);
                    }
                    processed2 = false;
                    Platform.runLater(() -> {
                        layout1.getChildren().remove(this.getVehicleImage());
                    });
                    System.out.println("processed is true");
                }
            }
        }
    }
    public void run()
    {
        this.processVehicle();
    }
    public String toString()
    {
        return this.name + " ima " + this.passengers.size() + " putnika.";
    }
    public String getInformation()
    {
        return this  + "\n" + Main.readTxt(this) +  Main.readBinary(this);
    }

}
