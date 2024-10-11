package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LogInForAdmin implements Initializable {

    //Admin credentials
    private String userName  = "Admin";
    private String password = "GamayOten";
    @FXML
    private AnchorPane logInPane;
    @FXML
    private Button logInButtonAdmin;
    @FXML
    private PasswordField passwordAdmin;
    @FXML
    private TextField userNameAdmin;
    @FXML
    private FontAwesomeIcon closeButton;

    @FXML
    private Label invalidLabel;

    private double x;
    private double y;
    private Stage stage;
    public void setStage(Stage stage){
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logInPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        logInPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    @FXML
    void close() throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        OpenUserLogIn();
    }

    private void OpenUserLogIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent root =loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        LogInController logInController = loader.getController();
        logInController.setStage(stage);
        //open the admin log in
        KeyCombination ctrlN = new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_DOWN) ;
        scene.setOnKeyPressed(keyEvent -> {
            if (ctrlN.match(keyEvent)){
                stage.close();
                try {
                    openAdminLogIn();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Log In");
        stage.show();
    }

    private void openAdminLogIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInForAdmin.fxml"));
        Parent root =loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        LogInForAdmin logInForAdmin = loader.getController();
        logInForAdmin.setStage(stage);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Log In as Admin");
        stage.show();

    }

    @FXML
    void logInAsAdmin(ActionEvent event) throws IOException {
        if(userNameAdmin.getText().equals(userName) && passwordAdmin.getText().equals(password)){
            invalidLabel.setStyle("-fx-text-fill: lightgreen");

            invalidLabel.setText("Logging In please wait.....");
            new Thread(() -> {
                try {
                    Thread.sleep(2000); // 1 second delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    try {
                        openAdminMenu();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }).start();

        } else invalidLabel.setText("*Invalid credentials");
    }

    private void openAdminMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMenu.fxml"));
        Parent root = loader.load();

        Stage currentStage = (Stage) logInButtonAdmin.getScene().getWindow();

        Stage AdminMenu = new Stage();
        AdminMenu.setScene(new Scene(root));
        AdminMenu.setTitle("Admin Menu");
        AdminMenu.setResizable(false);
        AdminMenu.show();
        currentStage.close();
    }
}
