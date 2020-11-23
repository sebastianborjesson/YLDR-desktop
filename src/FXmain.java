
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.FloatStringConverter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class FXmain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    YLDR yldr = new YLDR();
    YLDRStore yldrStore = new YLDRStore();


    @Override
    public void start(Stage primaryStage) throws Exception, SQLException {

        SalesInfo salesInfo = new SalesInfo();

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.TOP_CENTER);
        Label welcome = new Label("Välkommen till YLDR Automatron");
        Button expOrders = new Button("Exportera orders till Excel");
        DatePicker datePickerStart = new DatePicker();
        DatePicker datePickerEnd = new DatePicker();

        Button removeButton = new Button("Ta bort produkt från beställning");


        Button viewDB = new Button("Översikt");

        HBox tabeller = new HBox();

        Button customers = new Button("Customers");
        customers.setPrefWidth(75);

        Button ordercontent = new Button("Ordercontent");
        ordercontent.setPrefWidth(75);

        Button purchaseorder = new Button("Purchaseorder");
        purchaseorder.setPrefWidth(75);

        Button products = new Button("Products");
        products.setPrefWidth(75);
        tabeller.getChildren().addAll(customers, ordercontent, purchaseorder, products);

        tabeller.setVisible(false);
        tabeller.managedProperty().bind(tabeller.visibleProperty());


        TextField ordernumberField = new TextField();
        ordernumberField.setMaxWidth(200);
        ordernumberField.setVisible(false);

        Button showSelectedOrder = new Button("Visa order");
        showSelectedOrder.setVisible(false);
        showSelectedOrder.managedProperty().bind(showSelectedOrder.visibleProperty());

        Button expToLogistics= new Button("Exportera till logistik");
        expToLogistics.setPrefWidth(75);


        ComboBox hoursStart = new ComboBox();
        ComboBox minutesStart = new ComboBox();
        ComboBox hoursEnd = new ComboBox();
        ComboBox minutesEnd = new ComboBox();

        hoursStart.setMinWidth(60);
        hoursEnd.setMinWidth(60);
        minutesStart.setMinWidth(60);
        minutesEnd.setMinWidth(60);

        VBox vBox = new VBox();
        Button exp = new Button();

        exp.setText("Exportera");
        exp.setStyle("-fx-background-color: #008000;-fx-text-fill: antiquewhite");

        TextField kickback= new TextField();
        //kickback.setVisible(false);
        kickback.setPromptText("kickbackprocent");


     /*   ObservableList<String> ol= FXCollections.observableArrayList(yldrStore.getManufacturer());
        ListView <String> listView= new ListView<>(ol);*/

        ComboBox tillverkare = new ComboBox();
        tillverkare.setMinWidth(80);
        tillverkare.getItems().addAll(yldrStore.getManufacturer());
        DatePicker datePickerExpStart = new DatePicker();
        DatePicker datePickerExpEnd = new DatePicker();
        ComboBox shops = new ComboBox();
        shops.getItems().addAll(yldr.getShoppar());
        Button executeExportQuarts = new Button("Exportera");
        executeExportQuarts.setStyle("-fx-background-color: #008000;-fx-text-fill: antiquewhite");

        vBox.getChildren().addAll(exp);
        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(datePickerStart, hoursStart, minutesStart, tillverkare);
        hBox1.setVisible(false);
        hBox1.managedProperty().bind(hBox1.visibleProperty());
        hBox1.setAlignment(Pos.TOP_CENTER);


        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(datePickerEnd, hoursEnd, minutesEnd);
        hBox2.setVisible(false);
        hBox2.managedProperty().bind(hBox1.visibleProperty());
        hBox2.setAlignment(Pos.TOP_CENTER);

        vBox.setVisible(false);
        vBox.managedProperty().bind(vBox.visibleProperty());
        vBox.setAlignment(Pos.TOP_CENTER);

        HBox hBox3 = new HBox();
        hBox3.setVisible(false);
        hBox3.managedProperty().bind(hBox3.visibleProperty());
        hBox3.getChildren().addAll(datePickerExpStart, datePickerExpEnd);
        hBox3.setAlignment(Pos.TOP_CENTER);
        HBox hBox4 = new HBox();
        hBox4.setVisible(false);
        hBox4.managedProperty().bind(hBox4.visibleProperty());
        hBox4.getChildren().addAll(shops,kickback);
        hBox4.setAlignment(Pos.TOP_CENTER);
        VBox vBox2 = new VBox();
        vBox2.setVisible(false);
        vBox2.managedProperty().bind(vBox2.visibleProperty());
        vBox2.setAlignment(Pos.TOP_CENTER);
        vBox2.getChildren().addAll(executeExportQuarts);

        expOrders.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                getTimes(hoursStart, hoursEnd, minutesStart, minutesEnd);
                if (hBox1.isVisible() && hBox2.isVisible() && vBox.isVisible()) {
                    hBox1.setVisible(false);
                    hBox2.setVisible(false);
                    vBox.setVisible(false);
                } else {
                    hBox1.setVisible(true);
                    hBox2.setVisible(true);
                    vBox.setVisible(true);
                }


                exp.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Timestamp startDatum = Timestamp.valueOf(datePickerStart.getValue().toString() + " " + hoursStart.getValue().toString() + ":" + minutesStart.getValue().toString() + ":00");
                            Timestamp slutDatum = Timestamp.valueOf(datePickerEnd.getValue().toString() + " " + hoursEnd.getValue().toString() + ":" + minutesEnd.getValue().toString() + ":00");
                            String formatedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDatum);
                            String formatedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(slutDatum);


                            try {
                                if (yldr.checkDateIntervals(formatedDateStart, formatedDateEnd)) {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setContentText("Tryck OK för att exportera existerande fil. \nTryck cancel för att ändra datumintervall");
                                    alert.setHeaderText("Robin!! Nu ligger det ordrar utanför valt datum intervall. ");
                                    Optional<ButtonType> result = alert.showAndWait();
                                    if (result.get() == ButtonType.OK) {

                                        export(datePickerStart, hoursStart, minutesStart, datePickerEnd, hoursEnd, minutesEnd, tillverkare.getValue().toString());
                                    }
                                } else {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setHeaderText("Är du säker?");
                                    alert.setContentText("Tryck ok för att exportera. \nTryck cancel för att avbryta");
                                    Optional<ButtonType> result = alert.showAndWait();

                                    if (result.get() == ButtonType.OK) {

                                        export(datePickerStart, hoursStart, minutesStart, datePickerEnd, hoursEnd, minutesEnd, tillverkare.getValue().toString());

                                    }
                                }

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } catch (NullPointerException np) {
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setContentText("Fyll i alla rutor");
                            alert1.showAndWait();
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
                Label prisLabel = new Label("Pris");
                TextField SKU = new TextField();
                SKU.setPromptText("Skriv in SKU");
                TextField productName = new TextField();
                productName.setPromptText("Skriv in produktnamnet");
                TextField tillverkare = new TextField();
                tillverkare.setPromptText("Skriv in tillverkaren");
                TextField pris = new TextField();
                pris.setPromptText("Skriv in priset");
                SKU.setMaxWidth(200);
                tillverkare.setMaxWidth(200);
                productName.setMaxWidth(200);
                pane.setAlignment(Pos.TOP_CENTER);
                Button addProduct = new Button("Klicka för att lägga till produkten i databasen");

                pane.getChildren().addAll(SKUlabel, SKU, produktnamnLabel, productName, tillverkarLabel, tillverkare, prisLabel, pris, addProduct);

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
                        if (SKU.getText().isEmpty() || productName.getText().isEmpty() || tillverkare.getText().isEmpty() || pris.getText().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Robin! Nu har du glömt någon av rutorna!");
                            alert.showAndWait();
                        } else if (count == yldrStore.products.size()) {
                            yldr.laggTillProdukt(SKU.getText(), productName.getText(), tillverkare.getText(), Float.parseFloat(pris.getText()));
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


        //datePickerExpStart.setVisible(false);


        expQuart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                getTimes(hoursStart, hoursEnd, minutesStart, minutesEnd);
                if (hBox3.isVisible() && hBox4.isVisible() && vBox2.isVisible()) {
                    hBox3.setVisible(false);
                    hBox4.setVisible(false);
                    vBox2.setVisible(false);
                } else {
                    hBox3.setVisible(true);
                    hBox4.setVisible(true);
                    vBox2.setVisible(true);

                }


                executeExportQuarts.setOnAction(actionEvent1 -> {
                    try {
                        Timestamp startDatum = Timestamp.valueOf(datePickerExpStart.getValue().toString() + " 00:00:01");
                        Timestamp slutDatum = Timestamp.valueOf(datePickerExpEnd.getValue().toString() + " 23:59:59");
                        String formatedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDatum);
                        String formatedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(slutDatum);

                        String s=kickback.getText();
                        float svar=Float.parseFloat(s);
                        svar=svar/100;
                        try {
                            yldr.createQuartillyExcel(formatedDateStart,formatedDateEnd,shops.getValue().toString(),svar);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    } catch (NullPointerException e) {

                    }

                });


            }
        });


        Button quit = new Button("Avsluta");

        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        ordernumberField.managedProperty().bind(ordernumberField.visibleProperty());


        removeButton.setOnAction(new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent actionEvent) {

                ordernumberField.setVisible(true);
                showSelectedOrder.setVisible(true);


                TableColumn<Order, String> ordernumber = new TableColumn<>("orderNumber");
                ordernumber.setMinWidth(100);
                ordernumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

                TableColumn<Order, String> articlenumber = new TableColumn<>("articleNumber");
                articlenumber.setMinWidth(100);
                articlenumber.setCellValueFactory(new PropertyValueFactory<>("articleNumber"));

                TableColumn<Order, String> size = new TableColumn<>("size");
                size.setMinWidth(100);
                size.setCellValueFactory(new PropertyValueFactory<>("size"));

                TableColumn<Order, String> nickName = new TableColumn<>("nickname");
                nickName.setMinWidth(100);
                nickName.setCellValueFactory(new PropertyValueFactory<>("nickname"));

                TableColumn<Order, String> realName = new TableColumn<>("realName");
                realName.setMinWidth(100);
                realName.setCellValueFactory(new PropertyValueFactory<>("realName"));

                TableColumn<Order, String> countryFlag = new TableColumn<>("countryFlag");
                countryFlag.setMinWidth(100);
                countryFlag.setCellValueFactory(new PropertyValueFactory<>("countryFlag"));

                TableColumn<Order, String> rang = new TableColumn<>("rang");
                rang.setMinWidth(100);
                rang.setCellValueFactory(new PropertyValueFactory<>("rang"));

                TableColumn<Order, String> squadNumber = new TableColumn<>("squadNumber");
                squadNumber.setMinWidth(100);
                squadNumber.setCellValueFactory(new PropertyValueFactory<>("squadNumber"));

                TableColumn<Order, String> custom1 = new TableColumn<>("custom1");
                custom1.setMinWidth(100);
                custom1.setCellValueFactory(new PropertyValueFactory<>("custom1"));


                TableColumn<Order, String> custom2 = new TableColumn<>("custom2");
                custom2.setMinWidth(100);
                custom2.setCellValueFactory(new PropertyValueFactory<>("custom2"));


                TableColumn<Order, String> custom3 = new TableColumn<>("custom3");
                custom3.setMinWidth(100);
                custom3.setCellValueFactory(new PropertyValueFactory<>("custom3"));


                showSelectedOrder.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        VBox listOfProducts = new VBox();
                        TableView<Order> produkterIOrdercontent = new TableView<>();

                        ObservableList<Order> list = FXCollections.observableArrayList();

                        try {
                            for (Order o : yldr.getProductsFromOrdercontent(ordernumberField.getText())) {
                                list.add(o);
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                        produkterIOrdercontent.getColumns().clear();

                        produkterIOrdercontent.getColumns().addAll(ordernumber, articlenumber, size, nickName, realName, countryFlag, rang, squadNumber, custom1, custom2, custom3);

                        AnchorPane box = new AnchorPane();

                        Button delete = new Button("Delete");
                        Button removeTestOrder = new Button("Ta bort testorder");

                        AnchorPane.setLeftAnchor(delete, 1.0);
                        AnchorPane.setRightAnchor(removeTestOrder, 1.0);


                        box.getChildren().addAll(delete, removeTestOrder);


                        listOfProducts.getChildren().clear();
                        listOfProducts.getChildren().addAll(produkterIOrdercontent, box);

                        produkterIOrdercontent.getItems().clear();

                        produkterIOrdercontent.setItems(list);
              /*  for (String o:list     yldr.getProductsFromOrdercontent("5f99db354d1c1a7c8d5f2c58")) {

                }*/

                        //produkterIOrdercontent.getItems().addAll(list);


                        delete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Order order = produkterIOrdercontent.getSelectionModel().getSelectedItem();
                                System.out.println(order.getOrderNumber() + " " + order.getArticleNumber() + " " + order.getSize() + " " + order.getNickname() + " " + order.getCountryFlag() + " " + order.getRealName());
                                try {
                                    yldr.removeFromDB(order.getOrderNumber(), order.getArticleNumber(), order.getSize(), order.getNickname(), order.getCountryFlag(), order.getRealName(), order.getRang(), order.getSquadNumber(), order.getCustom1(), order.getCustom2(), order.getCustom3());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                                produkterIOrdercontent.getItems().clear();

                                try {
                                    for (Order o : yldr.getProductsFromOrdercontent(ordernumberField.getText())) {
                                        list.add(o);
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                produkterIOrdercontent.setItems(list);

                            }
                        });

                        removeTestOrder.setOnAction(e -> {


                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Robin, du är äkta guld, självklart får du ta bort en order!");
                            alert.setContentText("Är du säker?");
                            Optional<ButtonType> result = alert.showAndWait();

                            if (result.get() == ButtonType.OK) {
                                try {
                                    yldr.removeTestOrder(ordernumberField.getText());

                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                                produkterIOrdercontent.getItems().clear();
                            }
                            produkterIOrdercontent.getItems().clear();

                            try {
                                for (Order o : yldr.getProductsFromOrdercontent(ordernumberField.getText())) {
                                    list.add(o);
                                }
                            } catch (SQLException es) {
                                es.printStackTrace();
                            }
                            produkterIOrdercontent.setItems(list);
                        });

                        Scene scene = new Scene(listOfProducts);
                        Stage stage = new Stage();
                        // scene.setRoot(listOfProducts);

                        stage.setScene(scene);
                        stage.show();


                    }
                });

            }
        });



        viewDB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                tabeller.setAlignment(Pos.CENTER);

                tabeller.setVisible(true);

                customers.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        TableView<Customer> customerTV = new TableView<>();
                        //customerTV.setEditable(true);
                        customerTV.sort();

                        ObservableList<Customer> list = FXCollections.observableArrayList();


                        TableColumn<Customer, String> email = new TableColumn<>("Email");
                        email.setMinWidth(100);
                        email.setCellValueFactory(new PropertyValueFactory<>("email"));
                        //email.setEditable(true);
                        //email.setCellFactory(TextFieldTableCell.forTableColumn());


                        TableColumn<Customer, String> firstName = new TableColumn<>("firstname");
                        firstName.setMinWidth(100);
                        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

                        TableColumn<Customer, String> lastName = new TableColumn<>("lastname");
                        lastName.setMinWidth(100);
                        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

                        TableColumn<Customer, String> streetAdress = new TableColumn<>("streetadress");
                        streetAdress.setMinWidth(100);
                        streetAdress.setCellValueFactory(new PropertyValueFactory<>("adress"));

                        TableColumn<Customer, String> city = new TableColumn<>("city");
                        city.setMinWidth(100);
                        city.setCellValueFactory(new PropertyValueFactory<>("city"));

                        TableColumn<Customer, String> zipcode = new TableColumn<>("zipcode");
                        zipcode.setMinWidth(100);
                        zipcode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));

                        TableColumn<Customer, String> state = new TableColumn<>("state");
                        state.setMinWidth(100);
                        state.setCellValueFactory(new PropertyValueFactory<>("state"));

                        TableColumn<Customer, String> country = new TableColumn<>("country");
                        country.setMinWidth(100);
                        country.setCellValueFactory(new PropertyValueFactory<>("country"));

                        TableColumn<Customer, String> phonenumber = new TableColumn<>("phonenumber");
                        phonenumber.setMinWidth(100);
                        phonenumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

                        customerTV.getColumns().clear();


                        customerTV.getItems().clear();


                        customerTV.getColumns().addAll(email, firstName, lastName, streetAdress, city, zipcode, state, country, phonenumber);


                        //list.addAll(Arrays.asList(yldrStore.getCustomerFromDatabase()));


                        for (Customer c : yldrStore.getCustomerFromDatabase()) {
                            list.add(c);
                        }

                        customerTV.setItems(list);

                        Scene scene = new Scene(customerTV);

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();


                    }
                });

                products.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        TableView<Product> productTV = new TableView<>();
                        productTV.setEditable(true);
                        productTV.sort();
                        productTV.getColumns().clear();

                        productTV.getItems().clear();

                        ObservableList<Product> list = FXCollections.observableArrayList();


                        TableColumn<Product, String> articleNumber = new TableColumn<>("SKU");
                        articleNumber.setMinWidth(100);
                        articleNumber.setCellValueFactory(new PropertyValueFactory<>("SKU"));
                        articleNumber.setEditable(true);
                        articleNumber.setCellFactory(TextFieldTableCell.forTableColumn());

                        TableColumn<Product, String> productName = new TableColumn<>("Produktnamn");
                        productName.setMinWidth(100);
                        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
                        productName.setEditable(true);
                        productName.setCellFactory(TextFieldTableCell.forTableColumn());

                        TableColumn<Product, String> manufacturer = new TableColumn<>("Tillverkare");
                        manufacturer.setMinWidth(100);
                        manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
                        manufacturer.setEditable(true);
                        manufacturer.setCellFactory(TextFieldTableCell.forTableColumn());

                        TableColumn<Product, Float> price = new TableColumn<>("Pris");
                        price.setMinWidth(100);
                        price.setCellValueFactory(new PropertyValueFactory<>("price"));
                        price.setEditable(true);
                        price.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));


                        productTV.getColumns().clear();

                        productTV.getItems().clear();

                        list.removeAll();

                        productTV.getColumns().addAll(articleNumber, productName, manufacturer, price);


                        try {
                            for (Product c : yldrStore.getEverythingFromProduct()) {
                                list.add(c);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        productTV.setItems(list);


                        articleNumber.setOnEditCommit(event -> {
                            Product product = event.getRowValue();
                            // product.setSKU(event.getNewValue());
                            System.out.println(product.getSKU());
                            try {
                                yldrStore.updateProduct(event.getNewValue(), product.getSKU(), "articleNumber");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });


                        productName.setOnEditCommit(event -> {
                            Product product = event.getRowValue();
                            product.setName(event.getNewValue());
                            System.out.println(product.getName());
                            System.out.println(event.getTableColumn().toString());
                            try {
                                yldrStore.updateProduct(event.getNewValue(), product.getSKU(), "productName");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        manufacturer.setOnEditCommit(event -> {
                            Product product = event.getRowValue();
                            product.setManufacturer(event.getNewValue());
                            System.out.println(product.getManufacturer());
                            try {
                                yldrStore.updateProduct(event.getNewValue(), product.getSKU(), "manufacturer");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });

                        price.setOnEditCommit(event -> {
                            Product product = event.getRowValue();
                            product.setPrice(event.getNewValue());
                            System.out.println(product.getPrice());
                            try {
                                yldrStore.updateProduct(event.getNewValue().toString(), product.getSKU(), "price");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });


                        Scene scene = new Scene(productTV);

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                });

                purchaseorder.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        TableView<PurchaseOrder> purchaseOrderTV = new TableView<>();
                        //purchaseOrderTV.setEditable(true);
                        purchaseOrderTV.sort();

                        ObservableList<PurchaseOrder> list = FXCollections.observableArrayList();

                        TableColumn<PurchaseOrder, String> orderNumber = new TableColumn<>("Ordernummer");
                        orderNumber.setMinWidth(100);
                        orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

                        TableColumn<PurchaseOrder, String> email = new TableColumn<>("email");
                        email.setMinWidth(100);
                        email.setCellValueFactory(new PropertyValueFactory<>("email"));


                        TableColumn<PurchaseOrder, Float> totalamount = new TableColumn<>("totalamount");
                        totalamount.setMinWidth(100);
                        totalamount.setCellValueFactory(new PropertyValueFactory<>("summa"));

                        TableColumn<PurchaseOrder, String> currency = new TableColumn<>("currency");
                        currency.setMinWidth(100);
                        currency.setCellValueFactory(new PropertyValueFactory<>("valuta"));

                        TableColumn<PurchaseOrder, Date> date = new TableColumn<>("date");
                        date.setMinWidth(100);
                        date.setCellValueFactory(new PropertyValueFactory<>("date"));

                        TableColumn<PurchaseOrder, String> shop = new TableColumn<>("shop");
                        shop.setMinWidth(100);
                        shop.setCellValueFactory(new PropertyValueFactory<>("shop"));

                        TableColumn<PurchaseOrder, Boolean> isSent = new TableColumn<>("isEmailSent");
                        isSent.setMinWidth(100);
                        isSent.setCellValueFactory(new PropertyValueFactory<>("isEmailSent"));


                        TableColumn<PurchaseOrder, Float> discountPercentage = new TableColumn<>("discountPercentage");
                        discountPercentage.setMinWidth(100);
                        discountPercentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));


                        TableColumn<PurchaseOrder, Float> tax = new TableColumn<>("tax");
                        tax.setMinWidth(100);
                        tax.setCellValueFactory(new PropertyValueFactory<>("tax"));


                        TableColumn<PurchaseOrder, Float> shippingSum = new TableColumn<>("shippingSum");
                        shippingSum.setMinWidth(100);
                        shippingSum.setCellValueFactory(new PropertyValueFactory<>("shippingSum"));


                        purchaseOrderTV.getColumns().clear();

                        purchaseOrderTV.getItems().clear();

                        purchaseOrderTV.getColumns().addAll(orderNumber, email, totalamount, currency, date, shop, isSent, discountPercentage, tax, shippingSum);


                        for (PurchaseOrder c : yldrStore.getPurchaseInfoFromDatabase()) {
                            list.add(c);
                        }

                        purchaseOrderTV.setItems(list);


                        Scene scene = new Scene(purchaseOrderTV);

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();
                    }
                });
                ordercontent.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {


                        TableView<Order> orderTV = new TableView<>();
                        orderTV.setEditable(true);

                        orderTV.sort();


                        ObservableList<Order> list = FXCollections.observableArrayList();

                        TableColumn<Order, String> orderNumber = new TableColumn<>("orderNumber");
                        orderNumber.setMinWidth(100);
                        orderNumber.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));

                        TableColumn<Order, String> articleNumber = new TableColumn<>("articleNumber");
                        articleNumber.setMinWidth(100);
                        articleNumber.setCellValueFactory(new PropertyValueFactory<>("articleNumber"));

                        TableColumn<Order, String> size = new TableColumn<>("size");
                        size.setMinWidth(100);
                        size.setCellValueFactory(new PropertyValueFactory<>("size"));

                        TableColumn<Order, String> nickname = new TableColumn<>("nickname");
                        nickname.setMinWidth(100);
                        nickname.setCellValueFactory(new PropertyValueFactory<>("nickname"));

                        TableColumn<Order, String> countryFlag = new TableColumn<>("countryFlag");
                        countryFlag.setMinWidth(100);
                        countryFlag.setCellValueFactory(new PropertyValueFactory<>("countryFlag"));

                        TableColumn<Order, String> realName = new TableColumn<>("realName");
                        realName.setMinWidth(100);
                        realName.setCellValueFactory(new PropertyValueFactory<>("realName"));

                        TableColumn<Order, String> rang = new TableColumn<>("rang");
                        rang.setMinWidth(100);
                        rang.setCellValueFactory(new PropertyValueFactory<>("rang"));

                        TableColumn<Order, String> squadNumber = new TableColumn<>("squadNumber");
                        squadNumber.setMinWidth(100);
                        squadNumber.setCellValueFactory(new PropertyValueFactory<>("squadNumber"));

                        TableColumn<Order, String> custom1 = new TableColumn<>("custom1");
                        custom1.setMinWidth(100);
                        custom1.setCellValueFactory(new PropertyValueFactory<>("custom1"));

                        TableColumn<Order, String> custom2 = new TableColumn<>("custom2");
                        custom2.setMinWidth(100);
                        custom2.setCellValueFactory(new PropertyValueFactory<>("custom2"));

                        TableColumn<Order, String> custom3 = new TableColumn<>("custom3");
                        custom3.setMinWidth(100);
                        custom3.setCellValueFactory(new PropertyValueFactory<>("custom3"));

                        TableColumn<Order, Boolean> isSent = new TableColumn<>("isSent");
                        isSent.setMinWidth(100);
                        isSent.setCellValueFactory(new PropertyValueFactory<>("isSent"));
                        isSent.setEditable(true);
                        isSent.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));

                        TableColumn<Order, Boolean> isLogisticsSent = new TableColumn<>("isLogisticsSent");
                        isLogisticsSent.setMinWidth(100);
                        isLogisticsSent.setCellValueFactory(new PropertyValueFactory<>("isLogisticsSent"));
                        isLogisticsSent.setEditable(true);
                        isLogisticsSent.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));


                        orderTV.getColumns().clear();

                        orderTV.getItems().clear();

                        orderTV.getColumns().addAll(orderNumber,articleNumber,size,nickname,countryFlag,realName,rang,squadNumber, custom1,custom2,custom3,isSent, isLogisticsSent);

                        try {
                            for (Order c : yldrStore.getOC()) {
                                list.add(c);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        orderTV.setItems(list);

                        isLogisticsSent.setOnEditCommit(event -> {
                            Order order = event.getRowValue();
                            order.setLogisticsSent(event.getNewValue());
                            yldrStore.updateISSenttoLogistics(event.getNewValue(),"isSentToLogistics", order.getOrderNumber(), order.getArticleNumber(), order.getSize(), order.getNickname(), order.getRealName(), order.getCountryFlag(), order.getRang(), order.getSquadNumber(), order.getCustom1(), order.getCustom2(), order.getCustom3());
                        });

                        isSent.setOnEditCommit(event -> {
                            Order order = event.getRowValue();
                            order.setIsSent(event.getNewValue());
                            yldrStore.updateISSenttoLogistics(event.getNewValue(),"isSent", order.getOrderNumber(), order.getArticleNumber(), order.getSize(), order.getNickname(), order.getRealName(), order.getCountryFlag(), order.getRang(), order.getSquadNumber(), order.getCustom1(), order.getCustom2(), order.getCustom3());
                        });


                        Scene scene = new Scene(orderTV);

                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.show();

                    }


                });

            }
        });
        VBox vBox3= new VBox();
        vBox3.setVisible(false);
        Button expLogs= new Button("Exportera");
      /*  expLogs.setVisible(false);
        expLogs.managedProperty().bind(expLogs.visibleProperty());*/
        vBox3.getChildren().addAll(expLogs);

        HBox hBox5= new HBox();
        hBox5.getChildren().addAll(datePickerStart, hoursStart, minutesStart);
        hBox5.setVisible(false);
        hBox5.managedProperty().bind(hBox5.visibleProperty());
        hBox5.setAlignment(Pos.TOP_CENTER);


        HBox hBox6= new HBox();
        hBox6.getChildren().addAll(datePickerEnd, hoursEnd, minutesEnd);
        hBox6.setVisible(false);
        hBox6.managedProperty().bind(hBox6.visibleProperty());
        hBox6.setAlignment(Pos.TOP_CENTER);



        vBox3.managedProperty().bind(vBox3.visibleProperty());
        vBox3.setAlignment(Pos.TOP_CENTER);


        expToLogistics.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                getTimes(hoursStart, hoursEnd, minutesStart, minutesEnd);
                if (hBox5.isVisible() && hBox6.isVisible() && vBox3.isVisible()) {
                    hBox5.setVisible(false);
                    hBox6.setVisible(false);
                    vBox3.setVisible(false);
                } else {
                    hBox5.setVisible(true);
                    hBox6.setVisible(true);
                    vBox3.setVisible(true);
                }

                expLogs.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            Timestamp startDatum = Timestamp.valueOf(datePickerStart.getValue().toString() + " " + hoursStart.getValue().toString() + ":" + minutesStart.getValue().toString() + ":00");
                            Timestamp slutDatum = Timestamp.valueOf(datePickerEnd.getValue().toString() + " " + hoursEnd.getValue().toString() + ":" + minutesEnd.getValue().toString() + ":00");

                                        exportLogistics(datePickerStart, hoursStart, minutesStart, datePickerEnd, hoursEnd, minutesEnd);

                        }catch (NullPointerException e) {

                        }



                    }
                });


            }
        });


        layout.getChildren().addAll(welcome, expOrders, hBox1, hBox2, vBox, addProduct, expQuart, hBox3, hBox4, vBox2, removeButton, ordernumberField, showSelectedOrder, viewDB, tabeller, expToLogistics,hBox5,hBox6,vBox3, quit);
        Scene primaryScene = new Scene(layout, 600, 600);

        primaryStage.setTitle("YLDR Automatron");
        primaryScene.setRoot(layout);
        System.out.println(primaryScene.getRoot());
        primaryStage.setScene(primaryScene);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }


    public void getTimes(ComboBox hoursStart, ComboBox hoursEnd, ComboBox minutesStart, ComboBox minutesEnd) {
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

    public void export(DatePicker datePickerStart, ComboBox hoursStart, ComboBox minutesStart, DatePicker datePickerEnd, ComboBox hoursEnd, ComboBox minutesEnd, String manufacturer) {
        Timestamp startDatum = Timestamp.valueOf(datePickerStart.getValue().toString() + " " + hoursStart.getValue().toString() + ":" + minutesStart.getValue().toString() + ":00");
        Timestamp slutDatum = Timestamp.valueOf(datePickerEnd.getValue().toString() + " " + hoursEnd.getValue().toString() + ":" + minutesEnd.getValue().toString() + ":00");
        String formatedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDatum);
        String formatedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(slutDatum);
        try {
            yldr.createWB(formatedDateStart, formatedDateEnd, manufacturer);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void exportLogistics(DatePicker datePickerStart, ComboBox hoursStart, ComboBox minutesStart, DatePicker datePickerEnd, ComboBox hoursEnd, ComboBox minutesEnd) {
        Timestamp startDatum = Timestamp.valueOf(datePickerStart.getValue().toString() + " " + hoursStart.getValue().toString() + ":" + minutesStart.getValue().toString() + ":00");
        Timestamp slutDatum = Timestamp.valueOf(datePickerEnd.getValue().toString() + " " + hoursEnd.getValue().toString() + ":" + minutesEnd.getValue().toString() + ":00");
        String formatedDateStart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDatum);
        String formatedDateEnd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(slutDatum);
        try {
            yldr.createLogisticExcel(formatedDateStart, formatedDateEnd);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
