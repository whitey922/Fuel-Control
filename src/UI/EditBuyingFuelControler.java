package UI;

import Auto.Fuel;
import Auto.Driver;
import Auto.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Yegorov on 20.05.2016.
 */
public class EditBuyingFuelControler {
    public TextField timeTextField;
    public TextField countTextField;
    public TextField priceTextField;
    public DatePicker dateDateicker;
    public ComboBox chooseDriverComboBox;
    private Stage dialogStage;

    Fuel byingFuel;
    MainController mainCtrl;
    @FXML
    private void initialize() {
        /*Заполняем ComboBox фамилиями водителей */
        for (int i = 0; i < Driver.driverList.size(); i++) {
            chooseDriverComboBox.getItems().addAll(Driver.driverList.get(i).getSurname());
        }

    }

    public void setDialogStage(Stage dialogStage, Fuel byingFuel, MainController mainCtrl) {
        this.mainCtrl = mainCtrl;

        this.byingFuel = byingFuel;

        this.dialogStage = dialogStage;

        timeTextField.setText(byingFuel.getTime());
        dateDateicker.setValue(byingFuel.getDate());
        countTextField.setText(String.valueOf(byingFuel.getCountFuel()));
        priceTextField.setText(String.valueOf(byingFuel.getPrice()));
        chooseDriverComboBox.setValue(byingFuel.getSurname());

    }


    public void editBuyingFuel() throws SQLException {
        mainCtrl.dataAccessor.editFuel(byingFuel.getID(),
                timeTextField.getText(),
                dateDateicker.getValue(),
                Double.parseDouble(countTextField.getText()),
                Double.parseDouble(priceTextField.getText()),
                chooseDriverComboBox.getSelectionModel().getSelectedItem().toString());

        mainCtrl.fillFuelInTableView();

        Storage.getInstance().actionsFuel(byingFuel.getCountFuel()-Double.parseDouble(countTextField.getText()));
        mainCtrl.fillStorageTable();
        dialogStage.close();

    }

}
