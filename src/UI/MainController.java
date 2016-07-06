package UI;

/*TODO

13. Сделать сброс фильтра по дате
14. Вывод до 10-ых в л/100
15. Просмотреть фильтр для поездок. При выборе
16. Возможность добавить пустые поля
17. Добавляются минуты без нулей
* */

import Auto.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainController {
    private static final String ICON_URL = "file:icon.png";
    public TableView<Trip> tableAllAuto;
    public TableColumn id;
    public TableColumn name;
    public TableColumn driver;
    public TableColumn date;
    public TableColumn beginOdometr;
    public TableColumn finishOdometr;
    public TableColumn countFuel;
    public TableColumn timeShiftFinish;
    public TableColumn timeShiftBegin;
    public TableColumn fuelPerHundredKm;
    public AreaChart numberLineChart;
    public ListView motoListView;
    public DatePicker sinceDatePicker;
    public DatePicker tillDatePicker;
    public TableView<Fuel> tableAllBuyingFuel;
    public TableColumn dateByuingFuel;
    public TableColumn driverByuingFuel;
    public TableColumn timeByuingFuel;
    public TableColumn countFuelByuingFuel;
    public TableColumn priceFuelByuingFuel;
    public ComboBox chooseDriverComboBox;
    public TextField countTextField;
    public TextField priceTextField;
    public DatePicker sinceDatePickerByuingFuel;
    public DatePicker tillDatePickerByuingFuel;
    public TableView<Oil> tableAllBuyingOil;
    public TableColumn dateByuingOil;
    public TableColumn timeByuingOil;
    public TableColumn countFuelByuingOil;
    public TableColumn priceFuelByuingOil;
    public TextField countOilTextField;
    public TextField priceOilTextField;
    public TableView<Storage> tableStorage;
    public TableColumn fuel_oil;
    public TableColumn fuel;
    public TableColumn oil;
    public TableView<Driver> tableDrivers;
    public TableColumn surnameDriver;
    public TableColumn nameDriver;
    public TextField nameTextField;
    public TextField surnameTextField;
    public TableView<User> tableUser;
    public TableColumn loginUser;
    public TableColumn passwordUser;
    public TextField login;
    public TextField password;
    public DatePicker sinceDatePickerBuyingOil;
    public DatePicker tillDatePickerBuyingOil;
    public TabPane upperTab;
    public Label labelUser;
    public AnchorPane paneAllMoto;

    public Label inJerrycans_count;
    public Label fuel_count;
    public  Label oil_count;
    Stage dialogStage;
    List<Trip> tripList = new ArrayList<>();
    DBConnection dataAccessor;
    private String user;
    private ObservableList<Trip> data;
    private ObservableList<User> dataUser;
    private ObservableList<Driver> dataDrivers;
    private ObservableList<Fuel> dataByingFuel;
    private ObservableList<Oil> dataByingOil;
    private ObservableList<Storage> dataStorage;

    public String getUser() {
        return user;
    }

    public void checkLogin(String login) {
        switch (login) {
            case "admin":
                initAmdinRules();
                break;
            default:
                upperTab.getTabs().remove(5);
                labelUser.setText("");
                break;
        }
    }

    public void setDialogStage(Stage dialogStage, String user) {
        this.dialogStage = dialogStage;
        this.user = user;
        checkLogin(user);

        dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    dataAccessor.update();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fillTable(List<Trip> tripList) throws SQLException {


        data = FXCollections.observableArrayList(tripList);
        id.setCellValueFactory(new PropertyValueFactory<Trip, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Trip, String>("moto"));
        driver.setCellValueFactory(new PropertyValueFactory<Trip, String>("driver"));
        date.setCellValueFactory(new PropertyValueFactory<Trip, String>("date"));
        beginOdometr.setCellValueFactory(new PropertyValueFactory<Trip, String>("odometrBegin"));
        timeShiftBegin.setCellValueFactory(new PropertyValueFactory<Trip, String>("timeBegin"));
        timeShiftFinish.setCellValueFactory(new PropertyValueFactory<Trip, String>("timeFinish"));
        finishOdometr.setCellValueFactory(new PropertyValueFactory<Trip, String>("odometrFinish"));
        countFuel.setCellValueFactory(new PropertyValueFactory<Trip, String>("fuel"));
        countFuel.setCellFactory(TextFieldTableCell.<Trip, Double>forTableColumn(new StringConverter<Double>() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();

            {
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
            }

            @Override public String toString(final Double value) {
                return nf.format(value);
            }

            @Override public Double fromString(final String s) {
                // Don't need this, unless table is editable, see DoubleStringConverter if needed
                return null;
            }
        }));
        fuelPerHundredKm.setCellValueFactory(new PropertyValueFactory<Trip, String>("fuelPerHundredKm"));
        fuelPerHundredKm.setCellFactory(TextFieldTableCell.<Trip, Double>forTableColumn(new StringConverter<Double>() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();

            {
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
            }

            @Override public String toString(final Double value) {
                return nf.format(value);
            }

            @Override public Double fromString(final String s) {
                // Don't need this, unless table is editable, see DoubleStringConverter if needed
                return null;
            }
        }));
        tableAllAuto.setItems(data);
        tableAllAuto.sortPolicyProperty().set( new Callback<TableView<Trip>, Boolean>() {
            @Override
            public Boolean call(TableView<Trip> param) {
                Comparator<Trip> comparator = new Comparator<Trip>() {
                    @Override
                    public int compare(Trip r1, Trip r2) {
                        if (r1.getDate().isBefore(r2.getDate())) {
                            return 1;
                        } else if (r1.getDate().isAfter(r2.getDate())) {
                            return -1;
                        } else if (param.getComparator() == null) {
                            return 0;
                        } else {
                            return param.getComparator().compare(r1, r2);
                        }
                    }
                };
                FXCollections.sort(tableAllAuto.getItems(), comparator);
                return true;
            }
        });

    } /*Загружаем форму для добавления поездки*/

    public void addTrip() throws IOException, SQLException { /* Загружаем fxml-файл и создаём новую сцену для всплывающего диалогового окна.*/
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("add-trip.fxml"));
        AnchorPane page = (AnchorPane) loader.load(); /* Создаём диалоговое окно Stage.*/
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Добавить поездку");
        dialogStage.initModality(Modality.WINDOW_MODAL); /*  dialogStage.initOwner(primaryStage);*/
        Scene scene = new Scene(page);
        dialogStage.setScene(scene); /* Передаём адресата в контроллер.*/
        AddTripForm controller = loader.getController();
        controller.setDialogStage(dialogStage, this); /* Отображаем диалоговое окно и ждём, пока пользователь его не закроет*/
        dialogStage.getIcons().add(new Image(ICON_URL));
        dialogStage.show();


    } /*Загружаем форму для добавления профиля водителя*/

    public void fillTableUsers() throws SQLException {

        dataUser = FXCollections.observableArrayList(dataAccessor.getUserList());
        loginUser.setCellValueFactory(new PropertyValueFactory<Trip, String>("login"));
        tableUser.setItems(dataUser);
    } /*Загружаем форму для добавления поездки*/

    public void fillDriversTable() throws SQLException {
        dataDrivers = FXCollections.observableArrayList(dataAccessor.getDriversList());
        surnameDriver.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
        nameDriver.setCellValueFactory(new PropertyValueFactory<User, String>("name"));

        tableDrivers.setItems(dataDrivers);
    }

    public void addDriver() throws IOException, SQLException {
        dataAccessor.addDriver(surnameTextField.getText(), nameTextField.getText());
        fillDriversTable();

        surnameTextField.setText("");
        nameTextField.setText("");
    }

    public void addFuel() throws IOException, SQLException {
        dataAccessor.addFuel(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute(), LocalDate.now(), chooseDriverComboBox.getSelectionModel().getSelectedItem().toString(),
                Double.parseDouble(countTextField.getText()), Double.parseDouble(priceTextField.getText()));
        Storage.getInstance().addFuel(Double.parseDouble(countTextField.getText()));

        fillFuelInTableView();
        fillStorageTable();
    }

    public void addOil() throws IOException, SQLException {
        dataAccessor.addOil(LocalTime.now().getHour() + ":" + LocalTime.now().getMinute(),
                LocalDate.now(),Double.parseDouble(countOilTextField.getText()),
                Double.parseDouble(priceOilTextField.getText()));

        Storage.getInstance().addOil(Double.parseDouble(countOilTextField.getText()));

        fillOilInTableView();
        fillStorageTable();
    }

    public void fillFuelInTableView() throws SQLException {
        dataByingFuel = FXCollections.observableArrayList(dataAccessor.getBuyingFuelList());
        dateByuingFuel.setCellValueFactory(new PropertyValueFactory<Fuel, String>("date"));
        driverByuingFuel.setCellValueFactory(new PropertyValueFactory<Fuel, String>("surname"));
        timeByuingFuel.setCellValueFactory(new PropertyValueFactory<Fuel, String>("time"));
        countFuelByuingFuel.setCellValueFactory(new PropertyValueFactory<Fuel, String>("countFuel"));
        countFuelByuingFuel.setCellFactory(TextFieldTableCell.<Trip, Double>forTableColumn(new StringConverter<Double>() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();

            {
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
            }

            @Override public String toString(final Double value) {
                return nf.format(value);
            }

            @Override public Double fromString(final String s) {
                // Don't need this, unless table is editable, see DoubleStringConverter if needed
                return null;
            }
        }));
        priceFuelByuingFuel.setCellValueFactory(new PropertyValueFactory<Fuel, String>("price"));
        priceFuelByuingFuel.setCellFactory(TextFieldTableCell.<Trip, Double>forTableColumn(new StringConverter<Double>() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();

            {
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
            }

            @Override public String toString(final Double value) {
                return nf.format(value);
            }

            @Override public Double fromString(final String s) {
                // Don't need this, unless table is editable, see DoubleStringConverter if needed
                return null;
            }
        }));
        tableAllBuyingFuel.setItems(dataByingFuel);
        tableAllBuyingFuel.sortPolicyProperty().set( new Callback<TableView<Fuel>, Boolean>() {
            @Override
            public Boolean call(TableView<Fuel> param) {
                Comparator<Fuel> comparator = new Comparator<Fuel>() {
                    @Override
                    public int compare(Fuel r1, Fuel r2) {
                        if (r1.getDate().isBefore(r2.getDate())) {
                            return 1;
                        } else if (r1.getDate().isAfter(r2.getDate())) {
                            return -1;
                        } else if (param.getComparator() == null) {
                            return 0;
                        } else {
                            return param.getComparator().compare(r1, r2);
                        }
                    }
                };
                FXCollections.sort(tableAllBuyingFuel.getItems(), comparator);
                return true;
            }
        });
    }

    public void fillOilInTableView() throws SQLException {
        dataByingOil = FXCollections.observableArrayList(dataAccessor.getBuyingOilList());
        dateByuingOil.setCellValueFactory(new PropertyValueFactory<Oil, String>("date"));
        timeByuingOil.setCellValueFactory(new PropertyValueFactory<Oil, String>("time"));
        countFuelByuingOil.setCellValueFactory(new PropertyValueFactory<Oil, String>("countOil"));
        countFuelByuingOil.setCellFactory(TextFieldTableCell.<Trip, Double>forTableColumn(new StringConverter<Double>() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();

            {
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
            }

            @Override public String toString(final Double value) {
                return nf.format(value);
            }

            @Override public Double fromString(final String s) {
                // Don't need this, unless table is editable, see DoubleStringConverter if needed
                return null;
            }
        }));
        priceFuelByuingOil.setCellValueFactory(new PropertyValueFactory<Oil, String>("price"));
        priceFuelByuingOil.setCellFactory(TextFieldTableCell.<Trip, Double>forTableColumn(new StringConverter<Double>() {
            private final NumberFormat nf = NumberFormat.getNumberInstance();

            {
                nf.setMaximumFractionDigits(2);
                nf.setMinimumFractionDigits(2);
            }

            @Override public String toString(final Double value) {
                return nf.format(value);
            }

            @Override public Double fromString(final String s) {
                // Don't need this, unless table is editable, see DoubleStringConverter if needed
                return null;
            }
        }));
        tableAllBuyingOil.setItems(dataByingOil);

        tableAllBuyingOil.sortPolicyProperty().set( new Callback<TableView<Oil>, Boolean>() {
            @Override
            public Boolean call(TableView<Oil> param) {
                Comparator<Oil> comparator = new Comparator<Oil>() {
                    @Override
                    public int compare(Oil r1, Oil r2) {
                        if (r1.getDate().isBefore(r2.getDate())) {
                            return 1;
                        } else if (r1.getDate().isAfter(r2.getDate())) {
                            return -1;
                        } else if (param.getComparator() == null) {
                            return 0;
                        } else {
                            return param.getComparator().compare(r1, r2);
                        }
                    }
                };
                FXCollections.sort(tableAllBuyingOil.getItems(), comparator);
                return true;
            }
        });
    }


    public void fillStorageTable() throws SQLException {
        /*dataStorage =
                FXCollections.observableArrayList(
                        Storage.getInstance()
                );

        fuel_oil.setCellValueFactory(
                new PropertyValueFactory<Storage, String>("inJerrycans"));
        fuel.setCellValueFactory(
                new PropertyValueFactory<Storage, String>("countFuel"));
        oil.setCellValueFactory(
                new PropertyValueFactory<Storage, String>("countOil"));

        tableStorage.setItems(dataStorage);
        tableStorage.refresh();*/
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        inJerrycans_count.setText(String.valueOf( nf.format(Storage.getInstance().getInJerrycans())));
        fuel_count.setText(String.valueOf( nf.format(Storage.getInstance().getCountFuel())));
        oil_count.setText(String.valueOf( nf.format(Storage.getInstance().getCountOil())));

    }
/*ПРОВЕРКА И УВЕДОМЛЕНИЕ НА КОЛ-ВО МАСЛА И БЕНЗАНА*/
    public void checkCountFuelAndOil() {
        Alert error;
        if(Storage.getInstance().getCountFuel()<=10) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Внимание!");
            error.setContentText("Кол-во литров бензина меньше 10!");
            /*error.setHeaderText("");*/
            error.setHeaderText("Нужно закупить бензин!");
            error.showAndWait();
        }

        if(Storage.getInstance().getCountOil()<=5) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Внимание!");
            error.setContentText("Кол-во литров масла меньше 5!");
            error.setHeaderText("Нужно закупить масло!");
            error.showAndWait();
        }
    }

    public void initAmdinRules() {
        /*****ПОЕЗДКИ*****/
        tableAllAuto.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode().equals(KeyCode.DELETE)) {

                    try {
                        dataAccessor.removeTrip(tableAllAuto.getSelectionModel().getSelectedItem().getId());
                        Storage.getInstance().actionsJerrycan(-tableAllAuto.getSelectionModel().getSelectedItem().getFuel());
                        fillTable(dataAccessor.getTripList());
                        fillStorageTable();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        tableAllAuto.setRowFactory(tv -> {
            TableRow<Trip> row = new TableRow<>();
            row.hoverProperty().addListener((observable) -> {
                final Trip person = row.getItem();

                if (row.isHover() && person != null) {
                    labelUser.setText("Добавлял: "
                            + person.getUser() + " "
                    );
                } else {
                    labelUser.setText("Добавлял: ");
                }
            });
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Trip rowData = tableAllAuto.getSelectionModel().getSelectedItem();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("changeTrip.fxml"));
                    AnchorPane page = null;
                    try {
                        page = (AnchorPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Редактировать поездку");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Передаём адресата в контроллер.
                    EditTripControler controller = loader.getController();
                    try {
                        controller.setDialogStage(dialogStage, rowData, this);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    dialogStage.getIcons().add(new Image(ICON_URL));
                    // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
                    dialogStage.showAndWait();

                }

            });
            return row;
        });

        /****
         * ВОДИТЕЛИ
         *****/
        tableDrivers.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode().equals(KeyCode.DELETE)) {

                    try {
                        dataAccessor.removeDriver(tableDrivers.getSelectionModel().getSelectedItem().getID());
                        fillDriversTable();


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        tableDrivers.setRowFactory(tv -> {
            TableRow<Driver> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Driver rowData = tableDrivers.getSelectionModel().getSelectedItem();
                    // Загружаем fxml-файл и создаём новую сцену
                    // для всплывающего диалогового окна.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("editDrivers.fxml"));
                    AnchorPane page = null;
                    try {
                        page = (AnchorPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
// Создаём диалоговое окно Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Редактировать водителя");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
      /*  dialogStage.initOwner(primaryStage);*/
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Передаём адресата в контроллер.
                    EditDriversController controller = loader.getController();
                    controller.setDialogStage(dialogStage, rowData, this);

                    // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
                    dialogStage.getIcons().add(new Image(ICON_URL));
                    dialogStage.showAndWait();


                }
            });
            return row;
        });


        /****
         * ПОЛЬЗОВАТЕЛИ
         *****/
        tableUser.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode().equals(KeyCode.DELETE)) {

                    try {
                        dataAccessor.removeUser(tableUser.getSelectionModel().getSelectedItem().getID());
                        fillTableUsers();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

        tableUser.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    User rowData = tableUser.getSelectionModel().getSelectedItem();
                    // Загружаем fxml-файл и создаём новую сцену
                    // для всплывающего диалогового окна.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("editUsers.fxml"));
                    AnchorPane page = null;
                    try {
                        page = (AnchorPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
// Создаём диалоговое окно Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Редактировать пользователя");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
      /*  dialogStage.initOwner(primaryStage);*/
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Передаём адресата в контроллер.
                    EditUsersController controller = loader.getController();
                    controller.setDialogStage(dialogStage, rowData, this);

                    // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
                    dialogStage.getIcons().add(new Image(ICON_URL));
                    dialogStage.getIcons().add(new Image(ICON_URL));
                    dialogStage.showAndWait();

                }
            });
            return row;
        });
/****
 * Покупка бензина
 *****/


        tableAllBuyingFuel.setRowFactory(tv -> {
            TableRow<Fuel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Fuel rowData = tableAllBuyingFuel.getSelectionModel().getSelectedItem();
                    // Загружаем fxml-файл и создаём новую сцену
                    // для всплывающего диалогового окна.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("editBuyingFuel.fxml"));
                    AnchorPane page = null;
                    try {
                        page = (AnchorPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Создаём диалоговое окно Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Редактировать покупку бензина");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Передаём адресата в контроллер.
                    EditBuyingFuelControler controller = loader.getController();
                    controller.setDialogStage(dialogStage, rowData, this);

                    // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
                    dialogStage.getIcons().add(new Image(ICON_URL));
                    dialogStage.showAndWait();

                    tableStorage.refresh();
                }
            });
            return row;
        });
        tableAllBuyingFuel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    try {
                        dataAccessor.removeFuel(tableAllBuyingFuel.getSelectionModel().getSelectedItem().getID());
                        Storage.getInstance().actionsFuel(tableAllBuyingFuel.getSelectionModel().getSelectedItem().getCountFuel());
                        fillFuelInTableView();
                        fillStorageTable();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            }
        });

/****
 * Покупка масла
 *****/
        tableAllBuyingOil.setRowFactory(tv -> {
            TableRow<Oil> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Oil rowData = tableAllBuyingOil.getSelectionModel().getSelectedItem();
                    // Загружаем fxml-файл и создаём новую сцену
                    // для всплывающего диалогового окна.
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("editBuyingOil.fxml"));
                    AnchorPane page = null;
                    try {
                        page = (AnchorPane) loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Создаём диалоговое окно Stage.
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Редактировать покупку масла");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    // Передаём адресата в контроллер.
                    EditBuyingOilControler controller = loader.getController();
                    controller.setDialogStage(dialogStage, rowData, this);

                    // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
                    dialogStage.getIcons().add(new Image(ICON_URL));
                    dialogStage.showAndWait();

                    tableStorage.refresh();
                }
            });
            return row;
        });
        tableAllBuyingOil.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    try {
                        dataAccessor.removeOil(tableAllBuyingOil.getSelectionModel().getSelectedItem().getID());
                        Storage.getInstance().actionsOil(tableAllBuyingOil.getSelectionModel().getSelectedItem().getCountOil());
                        fillOilInTableView();
                        fillStorageTable();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    /*tableStorage.refresh();*/
                }


            }
        });
    }

    @FXML
    private void initialize() throws IOException, ClassNotFoundException, SQLException {

        try {
            dataAccessor = new DBConnection(
                    "jdbc:sqlite:identifier.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        dataAccessor.getStorage();


        fillTable(dataAccessor.getTripList());
        fillTableOneAuto();
        fillTableUsers();
        fillDriversTable();
        fillFuelInTableView();
        initTableFuel();
        fillOilInTableView();
        fillStorageTable();
        checkCountFuelAndOil();


    }

    private void initTableFuel() throws SQLException {

       /*Заполняем ComboBox фамилиями водителей */
        for (int i = 0; i < dataAccessor.getDriversList().size(); i++) {
            chooseDriverComboBox.getItems().addAll(dataAccessor.getDriversList().get(i).getSurname());
        }


    }

    /*Заполняем ListView названиями машинам*/
    public void fillTableOneAuto() throws SQLException {
        /*motoListView.getSelectionModel().getSelectedItem();*/
        motoListView.getItems().add("Все");
        for (int i = 0; i < dataAccessor.getMotoList().size(); i++) {
            motoListView.getItems().addAll(dataAccessor.getMotoList().get(i).getName());
        }
    }

    /*Заполняем Table поездками выбранного из списка определенного мотоцикла*/
    public void setAllTripsToTable() throws SQLException {
        tableAllAuto.getItems().clear();
        tripList.clear();

        for (Trip trip : dataAccessor.getTripList()) {
            if (trip.getMoto().equals(motoListView.getSelectionModel().getSelectedItem())) {

                tripList.add(trip);
            }
            if (motoListView.getSelectionModel().
                    getSelectedItem().equals("Все"))
                tripList.add(trip);

        }
        fillTable(tripList);

    }

    /*Заполняем график*/
    public void initChart(List<Trip> tripList) throws SQLException {

        numberLineChart.getData().clear();
        numberLineChart.setTitle("График заправки бензином");
        XYChart.Series series1 = new XYChart.Series();
        ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();

        for (Trip trip : tripList)
            datas.add(new XYChart.Data(trip.getDate().toString(), trip.getFuelPerHundredKm()));

        series1.setData(datas);

        numberLineChart.getData().add(series1);

    }

    /*Вывод поездок по дате*/
    public void getTripOnDate() throws SQLException {
        tableAllAuto.getItems().clear();
        tripList.clear();
        for (Trip trip : dataAccessor.getTripList()
                ) {
            try {
                if (trip.getDate().isAfter(sinceDatePicker.getValue()) &&
                        trip.getDate().isBefore(tillDatePicker.getValue())) {
                    if (trip.getMoto().
                            equals(motoListView.getSelectionModel().getSelectedItem()))
                        tripList.add(trip);
                    if (motoListView.getSelectionModel().getSelectedItem().equals("Все")) {
                        tripList.add(trip);
                    }
                }

            } catch (Exception e) {
            }
        }
        fillTable(tripList);
        initChart(tripList);
    }


    public void dropFilter() throws SQLException {
        /*tillDatePicker.setValue(LocalDate.now());
        sinceDatePicker.setValue(LocalDate.);*/
        sinceDatePicker.getEditor().clear();
        tillDatePicker.getEditor().clear();

        getTripOnDate();
    }

    /*    Вывод списка покупки бензина по дате*/
    /*public void getBuyingFuelOnDate() {
        tableAllBuyingFuel.getItems().clear();
        List<Fuel> byingFuels = new ArrayList<>();
        for (Fuel buyingFuel : Fuel.byingFuelList
                ) {
            if (buyingFuel.getDate().isAfter(sinceDatePickerByuingFuel.getValue()) &&
                    buyingFuel.getDate().isBefore(tillDatePickerByuingFuel.getValue())) {
                byingFuels.add(buyingFuel);


            }


        }

        fillFuelInTableView(byingFuels);
    }*/

    /*    Вывод списка покупки масла по дате*/
    /*public void getBuyingOilOnDate() {
        tableAllBuyingOil.getItems().clear();
        List<Oil> buyingOils = new ArrayList<>();
        for (Oil buyingOil : Oil.buyingOilList
                ) {
            if (buyingOil.getDate().isAfter(sinceDatePickerBuyingOil.getValue()) &&
                    buyingOil.getDate().isBefore(tillDatePickerBuyingOil.getValue())) {
                buyingOils.add(buyingOil);


            }


        }

        fillOilInTableView(buyingOils);
    }
*/

    public void addUser() throws SQLException {
        dataAccessor.addUser(login.getText(), password.getText());
        login.setText("");
        password.setText("");
        fillTableUsers();
    }


}

