package UI;

import Auto.Driver;
import Auto.User;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Yegorov on 31.05.2016.
 */
public class EditDriversController {
    public TextField surname;
    public TextField name;
    MainController mainCtrl;
    private Stage dialogStage;

    Driver driver;

    public void setDialogStage(Stage dialogStage,Driver driver, MainController mainCtrl) {
        this.dialogStage = dialogStage;
        this.mainCtrl = mainCtrl;

        this.driver = driver;

        surname.setText(driver.getSurname());
        name.setText(driver.getName());
    }

    public void editUser() throws SQLException {

        mainCtrl.dataAccessor.editDriver(driver.getID(), surname.getText(), name.getText());

        mainCtrl.fillDriversTable();
        dialogStage.close();
    }
}
