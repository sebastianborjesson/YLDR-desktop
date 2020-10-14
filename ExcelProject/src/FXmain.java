import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class FXmain extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    YLDR yldr = new YLDR();


    @Override
    public void start(Stage primaryStage) throws Exception, SQLException {

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_CENTER);
        Label welcome = new Label("Välkommen till YLDR Automatron");
        Button expOrders = new Button("Exportera orders till Excel");
        DatePicker datePickerStart = new DatePicker();
        DatePicker datePickerEnd = new DatePicker();


        ComboBox hoursStart = new ComboBox();
        ComboBox minutesStart = new ComboBox();
        ComboBox hoursEnd = new ComboBox();
        ComboBox minutesEnd = new ComboBox();
        VBox vBox = new VBox();
        Button exp = new Button();

        exp.setText("Exportera");
        exp.setStyle("-fx-background-color: #008000;-fx-text-fill: antiquewhite");

        vBox.getChildren().addAll(exp);
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(datePickerStart, hoursStart, minutesStart);
        hBox1.setVisible(false);
        hBox1.managedProperty().bind(hBox1.visibleProperty());
        hBox1.setAlignment(Pos.TOP_CENTER);

        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(datePickerEnd, hoursEnd, minutesEnd);
        hBox2.setVisible(false);
        hBox2.managedProperty().bind(hBox1.visibleProperty());
        hBox2.setAlignment(Pos.TOP_CENTER);

        vBox.setVisible(false);
        vBox.managedProperty().bind(hBox1.visibleProperty());
        vBox.setAlignment(Pos.TOP_CENTER);





        expOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               getTimes(hoursStart,hoursEnd,minutesStart,minutesEnd);
                hBox1.setVisible(true);
                hBox2.setVisible(true);
                vBox.setVisible(true);

                exp.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Timestamp startDatum = Timestamp.valueOf(datePickerStart.getValue().toString() + " " + hoursStart.getValue().toString() + ":" + minutesStart.getValue().toString() + ":00");
                        Timestamp slutDatum = Timestamp.valueOf(datePickerEnd.getValue().toString() + " " + hoursEnd.getValue().toString() + ":" + minutesEnd.getValue().toString() + ":00");
                        String formatedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDatum);
                        String formatedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(slutDatum);

                        try {
                            if (yldr.checkDateIntervals(formatedDateStart,formatedDateEnd)){
                                Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setContentText("Tryck OK för att exportera existerande fil. \nTryck cancel för att ändra datumintervall");
                                alert.setHeaderText("Robin!! Nu ligger det ordrar utanför valt datum intervall. ");
                                Optional<ButtonType> result=alert.showAndWait();
                                if (result.get()==ButtonType.OK) {
                                    export(datePickerStart, hoursStart, minutesStart, datePickerEnd, hoursEnd, minutesEnd);
                                }
                            }else {
                                Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText("Är du säker?");
                                alert.setContentText("Tryck ok för att exportera. \nTryck cancel för att avbryta");
                                Optional<ButtonType> result=alert.showAndWait();
                                if (result.get()==ButtonType.OK) {
                                    export(datePickerStart, hoursStart, minutesStart, datePickerEnd, hoursEnd, minutesEnd);
                                }
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });



        Button addProduct = new Button("Lägg till produkt");
        addProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                VBox pane = new VBox();
                Label SKUlabel = new Label("SKU");
                Label produktnamnLabel = new Label("Produktnamn");
                Label tillverkarLabel = new Label("Tillverkare");
                TextField SKU = new TextField();
                SKU.setPromptText("Skriv in SKU");
                TextField productName = new TextField();
                productName.setPromptText("Skriv in produktnamnet");
                TextField tillverkare = new TextField();
                tillverkare.setPromptText("Skriv in tillverkaren");
                SKU.setMaxWidth(200);
                tillverkare.setMaxWidth(200);
                productName.setMaxWidth(200);
                pane.setAlignment(Pos.TOP_CENTER);
                Button addProduct = new Button("Klicka för att lägga till produkten i databasen");

                pane.getChildren().addAll(SKUlabel, SKU, produktnamnLabel, productName, tillverkarLabel, tillverkare, addProduct);

                addProduct.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        YLDRStore yldrStore = new YLDRStore();
                        YLDR yldr = new YLDR();
                        int count = 0;
                        for (String s : yldrStore.getProducts()) {
                            if (!s.equals(SKU.getText())) {
                                count++;
                            }
                        }
                        if (SKU.getText().isEmpty() || productName.getText().isEmpty() || tillverkare.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Robin! Nu har du glömt någon av rutorna!");
                            alert.showAndWait();
                        } else if (count == yldrStore.products.size()) {
                            yldr.laggTillProdukt(SKU.getText(), productName.getText(), tillverkare.getText());
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Du la till " + SKU.getText() + " i databasen.");
                            alert.showAndWait();
                        } else {
                            System.out.println("Produkten finns redan inlagd");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Robin! Vafan håller du på med! Produkten är redan inlagd");
                            alert.showAndWait();
                        }
                    }
                });

                Scene scene = new Scene(pane, 300, 300);
                Stage stageForProdukt = new Stage();
                stageForProdukt.setScene(scene);
                stageForProdukt.show();
            }
        });


        Button expQuart = new Button("Generera kvartalsrapport");
        Button quit = new Button("Avsluta");

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });


        layout.getChildren().addAll(welcome, expOrders, hBox1, hBox2, vBox, addProduct, expQuart, quit);
        primaryStage.setTitle("YLDR Automatron");
        primaryStage.setScene(new Scene(layout, 500, 500));
        primaryStage.show();
    }



    public void getTimes(ComboBox hoursStart, ComboBox hoursEnd, ComboBox minutesStart, ComboBox minutesEnd){
        hoursStart.getItems().removeAll(hoursStart.getItems());
        hoursEnd.getItems().removeAll(hoursEnd.getItems());
        minutesStart.getItems().removeAll(minutesStart.getItems());
        minutesEnd.getItems().removeAll(minutesEnd.getItems());
        for (int i = 0; i <= 23; i++) {
            if (i < 10) {
                hoursStart.getItems().add("0" + i);
                hoursEnd.getItems().addAll("0" + i);
            } else {
                hoursStart.getItems().add(i);
                hoursEnd.getItems().add(i);
            }
        }
        minutesStart.getItems().addAll("00", "15", "30", "45");
        minutesEnd.getItems().addAll("00", "15", "30", "45");
    }
    public void export(DatePicker datePickerStart, ComboBox hoursStart, ComboBox minutesStart, DatePicker datePickerEnd, ComboBox hoursEnd, ComboBox minutesEnd){
        Timestamp startDatum = Timestamp.valueOf(datePickerStart.getValue().toString() + " " + hoursStart.getValue().toString() + ":" + minutesStart.getValue().toString() + ":00");
        Timestamp slutDatum = Timestamp.valueOf(datePickerEnd.getValue().toString() + " " + hoursEnd.getValue().toString() + ":" + minutesEnd.getValue().toString() + ":00");
        String formatedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDatum);
        String formatedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(slutDatum);
        try {
            yldr.createWB(formatedDateStart, formatedDateEnd);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
