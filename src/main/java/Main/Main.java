package Main;

import Main.Terminals.CustomsTerminal;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import Main.Vehicles.*;
import Main.Terminals.*;

import java.io.*;

import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.ObjectInputStream;
import java.io.FileInputStream;


public class Main extends Application {
    public static List<Vehicle> vehicleList;
    public static ConcurrentLinkedQueue<Vehicle> vehicleQueue = new ConcurrentLinkedQueue<>();
    public static ArrayBlockingQueue<Vehicle> onCustomTerminal = new ArrayBlockingQueue<>(2);

    public static Object lock = new Object();
    public static Object lock2 = new Object();
    public static Object lock3 = new Object();
    public static Object lock4 = new Object();
    public static Object lock5 = new Object();
    public static Object lock6 = new Object();
    public static Object lock7 = new Object();

    public static CustomsTerminal customsTerminal = new CustomsTerminal();
    public static CustomsTerminal customsTerminalTruck = new CustomsTerminal();

    public static PoliceTerminal policeTerminal1 = new PoliceTerminal();
    public static PoliceTerminal policeTerminal2 = new PoliceTerminal();
    public static PoliceTerminal policeTerminalTruck = new PoliceTerminal();

    public static String pathToTxt = "";
    public static String pathToBinary = "";
    public static ImageView openedPoliceTerminal1 = new ImageView(new Image("file:" + "Pictures" + File.separator + "openedPoliceTerminal.png"));
    public static ImageView closedPoliceTerminal1 = new ImageView(new Image("file:" + "Pictures" + File.separator + "closedPoliceTerminal.png"));
    public static ImageView openedPoliceTerminal2 = new ImageView(new Image("file:" + "Pictures" + File.separator + "openedPoliceTerminal.png"));
    public static ImageView closedPoliceTerminal2 = new ImageView(new Image("file:" + "Pictures" + File.separator + "closedPoliceTerminal.png"));
    public static ImageView openedPoliceTerminalTruck = new ImageView(new Image("file:" + "Pictures" + File.separator + "openedPoliceTerminal.png"));
    public static ImageView closedPoliceTerminalTruck = new ImageView(new Image("file:" + "Pictures" + File.separator + "closedPoliceTerminal.png"));
    public static ImageView openedCustomTerminal = new ImageView(new Image("file:" + "Pictures" + File.separator + "openedCustomTerminal.png"));
    public static ImageView closedCustomTerminal = new ImageView(new Image("file:" + "Pictures" + File.separator + "closedCustomTerminal.png"));
    public static ImageView openedCustomTerminalTruck = new ImageView(new Image("file:" + "Pictures" + File.separator + "openedCustomTerminal.png"));
    public static ImageView closedCustomTerminalTruck = new ImageView(new Image("file:" + "Pictures" + File.separator + "closedCustomTerminal.png"));
    public static ImageView background = new ImageView(new Image("file:" + "Pictures" + File.separator + "background1.jpg"));
    public static ImageView background2 = new ImageView(new Image("file:" + "Pictures" + File.separator + "background2.jpg"));


    public static Handler handler;
    static {
        try
        {
            handler = new FileHandler("loger.log");
            Logger.getLogger("").addHandler(handler);
        }
        catch(IOException e)
        {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
    }
    public static int vehicleCounter = 0;
    public static int vehicleCounterI3 = 1;
    //terminals:
    public static int startX = 7;
    public static int startY = 14;
    public static int startX2 = 3;
    public static int startY2 = 4;
    public static int startYi2 = startY2;
    public static int startXi2 = startX2;

    public static GridPane layout1 = new GridPane();
    public static GridPane layout2 = new GridPane();
    public static GridPane layout3 = new GridPane();

    public static Label timeLabel = new Label();
    public static int durationOfSimulation = 0;
    public static Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        durationOfSimulation++;
        updateTimeLabel(timeLabel, durationOfSimulation);
    }));
    public static boolean isPaused = false;
    public static void updateTimeLabel(Label label, int seconds)
    {
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;
        String timeString = String.format("%02d:%02d", minutes, remainingSeconds);
        timeLabel.setText("Duration of simulation: " + timeString);
        timeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-background-color: rgba(176, 162, 130, 1.0); -fx-text-fill: rgb(74, 68, 55); -fx-padding: 5;");
    }
    public static void moveToCustom(ImageView vehicleImage)
    {
        layout1.getChildren().remove(vehicleImage);
        layout1.add(vehicleImage, Main.customsTerminal.getCoordinates().getX(), Main.customsTerminal.getCoordinates().getY());
    }
    public static void moveToCustomTruck(ImageView vehicleImage)
    {
        layout1.getChildren().remove(vehicleImage);
        layout1.add(vehicleImage, Main.customsTerminalTruck.getCoordinates().getX(), Main.customsTerminalTruck.getCoordinates().getY());
    }
    public static void drawVehicle()
    {
        int startY1 = startY;
        int startYi2 = startY2;
        int startXi2 = startX2;
        vehicleCounter = 0;
        int vehicleCounterInterface = 0;
        for(Vehicle vehicle:vehicleList)
        {
            layout1.getChildren().remove(vehicle.getVehicleImage());
            layout2.getChildren().remove(vehicle.getVehicleImage());
        }
        for(Vehicle vehicle: vehicleList)
        {
            if(vehicleCounter < 5) {
                vehicle.setX(startX);
                vehicle.setY(startY1);
                layout1.add(vehicle.getVehicleImage(), startX, startY1);
                GridPane.setHalignment(vehicle.getVehicleImage(), javafx.geometry.HPos.CENTER);
                GridPane.setValignment(vehicle.getVehicleImage(), javafx.geometry.VPos.CENTER);
                startY1 += 2;
                vehicleCounter++;
            }
            else
            {
                vehicleCounterInterface++;
                vehicle.setX(startXi2);
                vehicle.setY(startYi2);
                layout2.add(vehicle.getVehicleImage(), startXi2, startYi2);
                GridPane.setHalignment(vehicle.getVehicleImage(), javafx.geometry.HPos.CENTER);
                GridPane.setValignment(vehicle.getVehicleImage(), javafx.geometry.VPos.CENTER);
                startYi2 += 2;
                if(vehicleCounterInterface % 9 == 0)
                {
                    startXi2 += 2;
                    startYi2 = startY2;
                }
            }
        }
    }
    public static void moveToIncidentScene(Vehicle vehicle)
    {
        ImageView vehicleImage = new ImageView(vehicle.getVehicleImage().getImage());
        vehicleImage.setFitHeight(vehicle.getVehicleImage().getFitHeight());
        vehicleImage.setFitWidth(vehicle.getVehicleImage().getFitWidth());

        vehicleImage.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacija o vozilu.");
            alert.setHeaderText(null);
            alert.setContentText(vehicle.getInformation());
            alert.showAndWait();
        });
        if(vehicleCounterI3 % 9 == 0) {
            layout3.add(vehicleImage, startXi2, startYi2);
            GridPane.setHalignment(vehicleImage, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(vehicleImage, javafx.geometry.VPos.CENTER);
            startXi2 += 2;
            startYi2 = startY2;

        }
        else
        {
            layout3.add(vehicleImage, startXi2, startYi2);
            GridPane.setHalignment(vehicleImage, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(vehicleImage, javafx.geometry.VPos.CENTER);
            startYi2 += 2;
        }
        vehicleCounterI3++;
    }
    public static synchronized String readTxt(Vehicle vehicle)
    {
        String text = "";
        try
        {
            File file = new File(pathToTxt);
            if(file.exists())
            {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                String line = bufferedReader.readLine();
                while(line != null)
                {
                    String[] lineSplit = line.split("#");
                    if(lineSplit[0].equals(vehicle.getName()))
                    {
                        if(vehicle instanceof Truck)
                            text +=  lineSplit[0] + " je imao problem sa carinom.\n";
                        else
                        {
                            for (int i = 1; i < lineSplit.length; i++) {
                                text += lineSplit[i] + " je imao problem sa carinom.\n";
                            }
                        }
                    }
                    line = bufferedReader.readLine();
                }
            }
        }
        catch (Exception e)
        {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        return text;
    }
    public static String readBinary(Vehicle vehicle)
    {
        String text = "";
        synchronized (Main.lock7)
        {
            File file = new File(pathToBinary);
            if (!file.exists())
            {
                return "";
            }
            try
            {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
                while (true)
                {
                    Object obj = ois.readObject();
                    Incident incident = null;
                    if (obj instanceof Incident)
                        incident = (Incident) obj;

                    if (incident != null && vehicle.getName().equals(incident.getVehicle().getName()))
                    {
                        for(Passenger pass: incident.getPassengers())
                        {
                            text += pass.getName() + " je imao problem sa policijom.\n";
                        }
                        break;
                    }
                    if(incident == null)
                        break;
                }
            }
            catch(EOFException e)
            {
                text += vehicle.getName() + " je imao problem sa policijom.\n";
            }
            catch (Exception e)
            {
                Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
                e.printStackTrace();
            }
        }
        return text;
    }
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        primaryStage.setResizable(true);
        FileWatcher fileWatcher = new FileWatcher();
        fileWatcher.setDaemon(true);
        fileWatcher.start();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
        pathToTxt += now.format(formatter) + ".txt";
        pathToBinary += now.format(formatter) + ".dat";
        timeline.setCycleCount(Timeline.INDEFINITE);
        for (int i = 0; i < 10; i++)
        {
            vehicleQueue.add(new Truck());
        }
        for (int i = 0; i < 5; i++) {
            vehicleQueue.add(new Bus());
        }
        for (int i = 0; i < 35; i++) {
            vehicleQueue.add(new Automobile());
        }

        vehicleList = new ArrayList<>(vehicleQueue);
        Collections.shuffle(vehicleList);
        vehicleQueue.clear();
        vehicleQueue.addAll(vehicleList);
        policeTerminal1.setCoordinates(new Coordinates(3,8));
        policeTerminal2.setCoordinates(new Coordinates(7, 8));
        policeTerminalTruck.setCoordinates(new Coordinates(12, 8));
        customsTerminal.setCoordinates(new Coordinates(3,2));
        customsTerminalTruck.setCoordinates(new Coordinates(12, 2));

        //layout1
        for(int i = 0; i < 15; i++)
        {
            layout1.getColumnConstraints().add(new ColumnConstraints(80));
        }
        for(int i = 0; i < 25; i++)
        {
            RowConstraints row = new RowConstraints();
            if(i % 2 == 0) {
                row.setPercentHeight(50.0);
            }
            else
            {
                row.setPercentHeight(10.0);
            }
            layout1.getRowConstraints().add(row);
        }
        openedPoliceTerminal1.setFitHeight(80);
        openedPoliceTerminal1.setFitWidth(80);
        layout1.add(openedPoliceTerminal1, 2, 8);
        openedPoliceTerminal2.setFitHeight(80);
        openedPoliceTerminal2.setFitWidth(80);
        layout1.add(openedPoliceTerminal2, 6, 8);
        openedPoliceTerminalTruck.setFitHeight(80);
        openedPoliceTerminalTruck.setFitWidth(80);
        layout1.add(openedPoliceTerminalTruck, 11, 8);
        openedCustomTerminal.setFitHeight(80);
        openedCustomTerminal.setFitWidth(80);
        layout1.add(openedCustomTerminal, 2, 2);
        openedCustomTerminalTruck.setFitHeight(80);
        openedCustomTerminalTruck.setFitWidth(80);
        layout1.add(openedCustomTerminalTruck, 11, 2);
        closedPoliceTerminal1.setFitHeight(80);
        closedPoliceTerminal1.setFitWidth(80);
        layout1.add(closedPoliceTerminal1, 2, 8);
        closedPoliceTerminal2.setFitHeight(80);
        closedPoliceTerminal2.setFitWidth(80);
        layout1.add(closedPoliceTerminal2, 6, 8);
        closedPoliceTerminalTruck.setFitHeight(80);
        closedPoliceTerminalTruck.setFitWidth(80);
        layout1.add(closedPoliceTerminalTruck, 11, 8);
        closedCustomTerminal.setFitHeight(80);
        closedCustomTerminal.setFitWidth(80);
        layout1.add(closedCustomTerminal, 2, 2);
        closedCustomTerminalTruck.setFitHeight(80);
        closedCustomTerminalTruck.setFitWidth(80);
        layout1.add(closedCustomTerminalTruck, 11, 2);

        Scene terminals = new Scene(layout1, 1200, 770);
        Scene queueOfVehicles = new Scene(layout2, 1200, 770);
        Scene incidents = new Scene(layout3, 1200, 770);

        Button waitingVehicles = new Button("Waiting vehicles >");
        waitingVehicles.alignmentProperty().setValue(Pos.CENTER);
        waitingVehicles.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 11;");
        waitingVehicles.setPrefWidth(120);
        waitingVehicles.setPrefHeight(80);
        waitingVehicles.setOnAction(e -> {
            primaryStage.setScene(queueOfVehicles);
        });
        layout1.add(waitingVehicles , 13, 0, 2, 1);
        GridPane.setHalignment(waitingVehicles, HPos.RIGHT);
        layout1.add(timeLabel, 12, 24, 3,1);

        //layout2
        for(int i = 0; i < 15; i++)
        {
            layout2.getColumnConstraints().add(new ColumnConstraints(80));
        }
        for(int i = 0; i < 25; i++)
        {
            RowConstraints row = new RowConstraints();
            if(i % 2 == 0) {
                row.setPercentHeight(50.0);
            }
            else {
                row.setPercentHeight(10.0);
            }
            layout2.getRowConstraints().add(row);
        }

        Button button2 = new Button("< Terminals");
        button2.alignmentProperty().setValue(Pos.CENTER);
        button2.setStyle("  -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 7; -fx-font-weight: bold; -fx-font-size: 13; ");
        button2.setPrefWidth(120);
        button2.setPrefHeight(80);
        button2.setOnAction(e -> {
            primaryStage.setScene(terminals);
        });
        layout2.add(button2 , 0, 0, 2, 1);
        GridPane.setHalignment(button2, HPos.LEFT);

        //layout3
        for(int i = 0; i < 15; i++)
        {
            layout3.getColumnConstraints().add(new ColumnConstraints(80));
        }
        for(int i = 0; i < 25; i++)
        {
            RowConstraints row = new RowConstraints();
            if(i % 2 == 0)
            {
                row.setPercentHeight(50.0);
            }
            else
            {
                row.setPercentHeight(10.0);
            }
            layout3.getRowConstraints().add(row);
        }

        Button incidents1 = new Button("Incidents >");
        incidents1.alignmentProperty().setValue(Pos.CENTER);
        incidents1.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 14; ");
        incidents1.setPrefWidth(120);
        incidents1.setPrefHeight(80);
        incidents1.setOnAction(e -> {
            primaryStage.setScene(incidents);
        });
        layout2.add(incidents1 , 13, 0, 2, 1);
        GridPane.setHalignment(incidents1, HPos.RIGHT);
        Button button4 = new Button("< Waiting vehicles");
        button4.alignmentProperty().setValue(Pos.CENTER);
        button4.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 11;");
        button4.setPrefWidth(120);
        button4.setPrefHeight(80);
        button4.setOnAction(e -> {
            primaryStage.setScene(queueOfVehicles);
        });
        layout3.add(button4 , 0, 0, 2, 1);
        GridPane.setHalignment(button4, HPos.LEFT);

        Button button5 = new Button("<< Terminals");
        button5.alignmentProperty().setValue(Pos.CENTER);
        button5.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 14; ");
        button5.setPrefWidth(120);
        button5.setPrefHeight(80);
        button5.setOnAction(e -> {
            primaryStage.setScene(terminals);
        });
        layout3.add(button5 , 0, 2, 2, 1);
        GridPane.setHalignment(button5, HPos.LEFT);
        Button incidents2 = new Button("Incidents >>");
        incidents2.alignmentProperty().setValue(Pos.CENTER);
        incidents2.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 14;");
        incidents2.setPrefWidth(120);
        incidents2.setPrefHeight(80);
        incidents2.setOnAction(e -> {
            primaryStage.setScene(incidents);
        });
        layout1.add(incidents2 , 13, 2, 2, 1);
        GridPane.setHalignment(incidents2, HPos.RIGHT);
        BackgroundImage backgroundImage = new BackgroundImage(background.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        layout1.setBackground(new Background(backgroundImage));
        BackgroundImage backgroundImage2 = new BackgroundImage(background2.getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        layout2.setBackground(new Background(backgroundImage2));
        layout3.setBackground(new Background(backgroundImage2));
        Button startSimulation1 = new Button("START SIMULATION");
        Button pauseSimulation = new Button("Pause");
        startSimulation1.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-font-weight: bold; -fx-font-size: 20; -fx-background-radius: 5; -fx-border-width: 1.05px;-fx-border-color: rgb(61, 49, 24); -fx-border-radius: 0;");
        startSimulation1.setPrefWidth(400);
        startSimulation1.setPrefHeight(500);
        startSimulation1.setOnAction(e -> {

            startSimulation1.setVisible(false);
            timeline.play();
            pauseSimulation.setDisable(false);
            updateTimeLabel(timeLabel, durationOfSimulation);
            for(Vehicle v: vehicleQueue)
            {
                Thread threadVehicle = new Thread(v);
                threadVehicle.setDaemon(true);
                threadVehicle.start();
            }
        });
        layout1.add(startSimulation1, 5, 4, 8, 8);
        Button exitSimulation = new Button("Exit");
        exitSimulation.alignmentProperty().setValue(Pos.CENTER);
        exitSimulation.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 17;");
        exitSimulation.setPrefWidth(80);
        exitSimulation.setPrefHeight(30);
        exitSimulation.setOnAction(e -> {
            System.exit(0);
        });
        layout1.add(exitSimulation, 1,22, 2, 1);
        pauseSimulation.setDisable(true);
        pauseSimulation.alignmentProperty().setValue(Pos.CENTER);
        pauseSimulation.setStyle(" -fx-text-fill: rgb(74, 68, 55); -fx-background-color:rgba(176, 162, 130, 1.0); -fx-background-radius: 5; -fx-font-weight: bold; -fx-font-size: 14;");
        pauseSimulation.setPrefWidth(80);
        pauseSimulation.setPrefHeight(40);
        pauseSimulation.setOnAction(e -> {
            synchronized (lock5)
            {
                isPaused = !isPaused;
                if(!isPaused)
                {
                    pauseSimulation.setText("Pause");
                    lock5.notifyAll();
                    timeline.play();
                }
                else
                {
                    pauseSimulation.setText("Resume");
                    timeline.pause();
                }
            }
        });
        layout1.add(pauseSimulation, 1,20, 2, 1);
        drawVehicle();
        primaryStage.setTitle("Simulation");
        primaryStage.setScene(terminals);
        primaryStage.show();
    }
}