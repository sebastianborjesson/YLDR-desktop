package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_CENTER);

        Label welcome = new Label("Välkommen till YLDR Automatron"); {

        }

        Button expOrders = new Button("Exportera orders till Excel");

        Button addProduct = new Button("Lägg till produkt");

        Button expQuart = new Button ("Generera kvartalsrapport");

        Button quit = new Button ("Avsluta");



        layout.getChildren().addAll(welcome, expOrders, addProduct, expQuart, quit);

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("YLDR Automatron");
        primaryStage.setScene(new Scene(layout, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
