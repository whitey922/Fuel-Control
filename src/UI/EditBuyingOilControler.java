package UI;

import Auto.Oil;
import Auto.Storage;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Yegorov on 21.05.2016.
 */
public class EditBuyingOilControler {
    public TextField timeTextField;
    public TextField countTextField;
    public TextField priceTextField;
    public DatePicker dateDatePicker;
    Oil buyingOil;
    MainController mainCtrl;
    private Stage dialogStage;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage, Oil buyingOil, MainController mainCtrl) {
        this.mainCtrl = mainCtrl;

        this.buyingOil = buyingOil;

        this.dialogStage = dialogStage;

        timeTextField.setText(buyingOil.getTime());
        countTextField.setText(String.valueOf(buyingOil.getCountOil()));
        priceTextField.setText(String.valueOf(buyingOil.getPrice()));
        dateDatePicker.setValue(buyingOil.getDate());
    }

    public void editBuyingOil() throws IOException, ClassNotFoundException, SQLException {


        mainCtrl.dataAccessor.editOil(buyingOil.getID(),
                timeTextField.getText(),
                dateDatePicker.getValue(),
                Double.parseDouble(countTextField.getText()),
                Double.parseDouble(priceTextField.getText()));

        mainCtrl.fillOilInTableView();

        Storage.getInstance().actionsOil(buyingOil.getCountOil()-Double.parseDouble(countTextField.getText()));
        mainCtrl.fillStorageTable();

        dialogStage.close();

    }

}
