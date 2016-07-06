package UI;

import Auto.Driver;
import Auto.Moto;
import Auto.Trip;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Created by Yegorov on 20.05.2016.
 */
public class EditTripControler {
    public ComboBox motoObj;
    public ComboBox driver;
    public DatePicker date;
    public TextField beginOdometr;
    public TextField finishOdometr;
    public TextField countFuel;

    public TextField timeBegin;
    public TextField timeFinish;

    MainController mainCtrl;
    private Trip trip;
    private Moto moto;
    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage, Trip trip, MainController mainCtrl) throws SQLException {
        this.mainCtrl = mainCtrl;

        this.trip = trip;

        this.dialogStage = dialogStage;

        driver.setValue(trip.getDriver());
        motoObj.setValue(trip.getMoto());
        date.setValue((LocalDate) trip.getDate());
        beginOdometr.setText(String.valueOf(trip.getOdometrBegin()));
        finishOdometr.setText(String.valueOf(trip.getOdometrFinish()));
        timeBegin.setText(String.valueOf(trip.getTimeBegin()));
        timeFinish.setText(String.valueOf(trip.getTimeFinish()));
        countFuel.setText(String.valueOf(trip.getFuel()));

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
    private void initialize() {

    }



    public void changeTripData() throws IOException, ClassNotFoundException, SQLException {
        mainCtrl.dataAccessor.editTrip(trip.getId(), motoObj.getSelectionModel().getSelectedItem().toString(),
                driver.getSelectionModel().getSelectedItem().toString(),
                Double.parseDouble(countFuel.getText()),
                Integer.parseInt(finishOdometr.getText()),
                date.getValue(),
                Integer.parseInt(beginOdometr.getText()),
                timeBegin.getText(),
                timeFinish.getText());

        mainCtrl.fillTable(mainCtrl.dataAccessor.getTripList());
        mainCtrl.tableAllAuto.refresh();
        dialogStage.close();
    }
}
