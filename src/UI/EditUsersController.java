package UI;

import Auto.User;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

/**
 * Created by Yegorov on 31.05.2016.
 */
public class EditUsersController {
    public TextField login;
    public TextField password;
    MainController mainCtrl;
    private Stage dialogStage;

    User user;

    public void setDialogStage(Stage dialogStage,User user, MainController mainCtrl) {
        this.dialogStage = dialogStage;
        this.mainCtrl = mainCtrl;

        this.user = user;

        login.setText(user.getLogin());
        password.setText(user.getPassword());
    }

    public void editUser() throws SQLException {

        mainCtrl.dataAccessor.editUser(user.getID(), login.getText(), password.getText());

        mainCtrl.fillTableUsers();
        dialogStage.close();
    }
}
