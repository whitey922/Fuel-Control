package UI;

import Auto.Moto;
import Auto.Storage;
import Auto.Trip;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


public class AddTripForm {

    public ComboBox motoObj;
    public ComboBox driver;
    public DatePicker date;
    public TextField beginOdometr;
    public TextField finishOdometr;
    public TextField countFuel;

    public TextField timeBegin;
    public TextField timeFinish;
    public Button addTrip;
    MainController mainCtrl;
    private Trip trip;
    private Moto moto;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage, MainController mainCtrl) throws SQLException {
        this.mainCtrl = mainCtrl;

        this.dialogStage = dialogStage;

        /*Заполняем ComboBox названиями мотоциклов */
        for (int i = 0; i < mainCtrl.dataAccessor.getMotoList().size(); i++) {
            motoObj.getItems().addAll(mainCtrl.dataAccessor.getMotoList().get(i).getName());
        }

        /*Заполняем ComboBox фамилиями водителей */
        for (int i = 0; i < mainCtrl.dataAccessor.getDriversList().size(); i++) {
            driver.getItems().addAll(mainCtrl.dataAccessor.getDriversList().get(i).getSurname());
        }
    }

    @FXML
    private void initialize() throws SQLException {


    }


    public void addTrip() throws IOException, ClassNotFoundException, SQLException {

        mainCtrl.dataAccessor.addTrip(motoObj.getSelectionModel().getSelectedItem().toString(),
                driver.getSelectionModel().getSelectedItem().toString(),
                Double.parseDouble(countFuel.getText()),
                Integer.parseInt(finishOdometr.getText()),
                LocalDate.now(),
                Integer.parseInt(beginOdometr.getText()),
                timeBegin.getText(),
                LocalTime.now().getHour()+":"+LocalTime.now().getMinute(),
                mainCtrl.getUser());
        mainCtrl.fillTable(mainCtrl.dataAccessor.getTripList());

        Storage.getInstance().actionsJerrycan(Double.parseDouble(countFuel.getText()));

        mainCtrl.fillStorageTable();
        dialogStage.close();
    }
}
