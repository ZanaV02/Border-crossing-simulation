package Main.Vehicles;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Bus extends Vehicle implements Serializable
{
    private int imageHeight = 50;
    private int imageWidth = 35;
    public Bus() {
        this.name = "Bus" + ++IDcounter;
        this.vehicleID = IDcounter;
        this.generateNumberOfPassengers();
        this.vehicleImage = new ImageView(new Image("file:" + "Pictures" + File.separator + "bus.png"));
        this.vehicleImage.setFitHeight(imageHeight);
        this.vehicleImage.setFitWidth(imageWidth);
        this.vehicleImage.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacija o busu");
            alert.setHeaderText(null);
            alert.setContentText(this.toString());
            alert.showAndWait();
        });
    }

    @Override
    public void generateNumberOfPassengers() {
        Random capacity = new Random();
        this.passengers = new ArrayList<Passenger>();
        for (int i = 0; i < capacity.nextInt(52) + 1; i++) {
            this.passengers.add(new Passenger());
        }
    }

}
