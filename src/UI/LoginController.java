package UI;

import Auto.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Controls the login screen
 */
public class LoginController {

    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;
    @FXML
    private String login = null;

    DBConnection dataAccessor;

    public void initialize() throws SQLException, IOException {

    }
    @FXML
    private void authorize() throws IOException, SQLException {
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

        dataAccessor.getTripList();

        for (User user: dataAccessor.getUserList()
                ) {
            if(this.user.getText().equals(user.getLogin())&&this.password.getText().equals(user.getPassword())) {
                login = user.getLogin();
                showMainStage();
            }

        }

    }

    /*Загружаем форму для добавления поездки*/
    public void showMainStage() throws IOException {
        // Загружаем fxml-файл и создаём новую сцену
        // для всплывающего диалогового окна.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("m.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Контроль бензина");
        dialogStage.initModality(Modality.WINDOW_MODAL);
      /*  dialogStage.initOwner(primaryStage);*/
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add(new Image("file:icon.png"));
        // Передаём адресата в контроллер.
        MainController controller = loader.getController();
        controller.setDialogStage(dialogStage, login);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.show();

    }

}
