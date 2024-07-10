package Main.Vehicles;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import  Main.Main;

public class Truck extends Vehicle implements Serializable
{
    private boolean hasDocumentaion = false;
    private double declaredWeight;
    private double realWeight;
    public double getDeclaredWeight()
    {
        return this.declaredWeight;
    }
    public double getRealWeight()
    {
        return this.realWeight;
    }
    public boolean isHasDocumentaion()
    {
        return this.hasDocumentaion;
    }
    private int imageHeight = 45;
    private int imageWidth = 35;
    public Truck()
    {
        this.name = "Truck" + ++IDcounter;
        this.vehicleID = IDcounter;
        this.generateNumberOfPassengers();
        this.vehicleImage = new ImageView(new Image("file:" + "Pictures" + File.separator + "truck.png"));
        this.vehicleImage.setFitHeight(imageHeight);
        this.vehicleImage.setFitWidth(imageWidth);
        Random random = new Random();
        if(random.nextInt(1,101)<=50)
        {
            this.hasDocumentaion = true;
        }
        this.declaredWeight = random.nextDouble(0,100);
        if(random.nextInt(1,101)<=20)
        {
            this.realWeight = this.declaredWeight + random.nextDouble(0,0.3)*this.declaredWeight;
        }
        else
        {
            this.realWeight = this.declaredWeight;
        }
        this.vehicleImage.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacija o kamionu.");
            alert.setHeaderText(null);
            alert.setContentText(this.toString());
            alert.showAndWait();
        });
    }
    @Override
    public void generateNumberOfPassengers()
    {
        Random capacity = new Random();
        this.passengers=new ArrayList<Passenger>();
        for(int i=0;i<capacity.nextInt(3) + 1;i++)
        {
            this.passengers.add(new Passenger());
        }
    }
    @Override
    public void processVehicle()
    {
        boolean processed = false;

        while (!processed)
        {
            try
            {
                if(Main.isPaused)
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
            if(firstVehicle.equals(this) && firstVehicle != null && Main.policeTerminalTruck.isFree() && Main.policeTerminalTruck.isWorking())
            {
                synchronized (Main.lock)
                {
                    Platform.runLater(() ->
                    {
                        if (this.getX() == Main.startX && this.getY() == Main.startY)
                        {
                            Main.layout1.getChildren().remove(this.getVehicleImage());
                            Main.layout1.add(this.getVehicleImage(), Main.policeTerminalTruck.getCoordinates().getX(), Main.policeTerminalTruck.getCoordinates().getY());
                            this.setX(Main.policeTerminalTruck.getCoordinates().getX());
                            this.setY(Main.policeTerminalTruck.getCoordinates().getY());
                            Main.vehicleList.remove(0);
                            Main.drawVehicle();
                        }
                    });
                    Main.policeTerminalTruck.setFree(false);
                    System.out.println("Policijski terminal za kamione zauzet.");
                    Main.vehicleQueue.poll();
                    while(!Main.customsTerminalTruck.isFree() || !Main.customsTerminalTruck.isWorking());
                    processed = true;
                    //System.out.println("processed is true");
                }
                if (!this.hasPoliceProblem() && processed && Main.customsTerminalTruck.isFree() && Main.customsTerminalTruck.isWorking())
                {
                    synchronized (Main.lock2)
                    {
                        Main.policeTerminalTruck.processVehicle(this);
                        System.out.println("Nije imao problem sa policijom");
                        Main.customsTerminalTruck.setFree(false);
                        System.out.println("Carinski terminal za kamione zauzet");
                        Main.policeTerminalTruck.setFree(true);
                        Platform.runLater(()->{Main.moveToCustomTruck(this.getVehicleImage());});
                        System.out.println("Policijski terminal za kamione oslobodjen");
                        Main.customsTerminalTruck.processVehicle(this);
                        System.out.println(this + " se obradio na carini");
                        Main.customsTerminalTruck.setFree(true);
                    }
                }
                else
                {
                    Platform.runLater(() -> {
                        Main.layout1.getChildren().remove(this.getVehicleImage());
                        Main.moveToIncidentScene(this);
                    });
                }
            }
        }
    }
}
