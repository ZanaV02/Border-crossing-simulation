package Main.Vehicles;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Automobile extends Vehicle implements Serializable
{
    private int imageHeight = 45;
    private int imageWidth = 34;

    public Automobile()
    {
        this.name = "Auto" + ++IDcounter;
        this.vehicleID = IDcounter;
        this.generateNumberOfPassengers();
        this.vehicleImage = new ImageView(new Image("file:" + "Pictures" + java.io.File.separator + "car1.png"));
        this.vehicleImage.setFitHeight(imageHeight);
        this.vehicleImage.setFitWidth(imageWidth);
        this.vehicleImage.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacija o autu");
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
        for(int i=0;i<capacity.nextInt(5) + 1;i++)
        {
            this.passengers.add(new Passenger());
        }
    }
}
