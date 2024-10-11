package GUI;

import DB.DataBase;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    String URL = "jdbc:mysql://localhost:3306/";
    String URL_WITH_DB = "jdbc:mysql://localhost:3306/flight_reservation_system";
    String USER_NAME = "root";
    String PASS = "";

    private Stage stage;
    @FXML
    private FontAwesomeIcon CloseButton;
    @FXML
    private FontAwesomeIcon BackToLogInPage;

    @FXML
    private Button LogInButton;
//dadawd
    @FXML
    private AnchorPane LogInPane;
    @FXML
    private AnchorPane RegisterPane;
    @FXML
    private AnchorPane coverPane;

    @FXML
    private Label invalidLabel;
    @FXML
    private Label invalidLabelForRegister;


    @FXML
    private Button RegisterButton;
    @FXML
    private Button registerButton2;

    @FXML
    private PasswordField confirmPassRegister;

    @FXML
    private TextField emailField;

    @FXML
    private TextField emailFieldRegister;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordFieldRegister;

    @FXML
    void LogInAction(ActionEvent event) throws IOException {
        DataBase dataBase = new DataBase();
        if(!dataBase.checkIfUserExists(emailField.getText(),passwordField.getText())){
            invalidLabel.setStyle("-fx-text-fill: red");
            invalidLabel.setText("*Invalid credentials");
        } else {
            invalidLabel.setStyle("-fx-text-fill: lightgreen");
            invalidLabel.setText("Log In success");

            TranslateTransition transitionForLogIn = new TranslateTransition();
            transitionForLogIn.setNode(LogInPane);
            transitionForLogIn.setDuration(Duration.seconds(1));
            transitionForLogIn.setByX(-350);
            transitionForLogIn.play();

            new Thread(() -> {
                try {
                    Thread.sleep(1000); // 1 second delay
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Platform.runLater(() -> {
                    try {
                        openSceneForUserMenu();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }).start();

        }

    }

    private void openSceneForUserMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuUser.fxml"));
        Parent root = loader.load();

        Stage currentStage = (Stage) LogInButton.getScene().getWindow();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));
        newStage.show();
        currentStage.close();
    }
    @FXML
    void RegisterButtonAction(ActionEvent event){
        DataBase dataBase = new DataBase();
        if(firstNameField.getText().equals("")) invalidLabelForRegister.setText("*Enter you first name");
        else if (lastNameField.getText().equals("")) invalidLabelForRegister.setText("*Enter you last name");
        else if (emailFieldRegister.getText().equals("")) invalidLabelForRegister.setText("*Enter your email address");
        else if (passwordFieldRegister.getText().equals("")) invalidLabelForRegister.setText("*Enter your password");
        else if (!confirmPassRegister.getText().equals(passwordFieldRegister.getText())) invalidLabelForRegister.setText("*Password did not match");
        else if (checkIfUserAlreadyExists(firstNameField.getText(),lastNameField.getText(),emailFieldRegister.getText())) invalidLabelForRegister.setText("*Looks like user already exists.");
        else if (checkIfEmailAlreadyExists()) invalidLabelForRegister.setText("*Email already use by other user. ");
        else {
            if(dataBase.addNewUser(firstNameField.getText(),lastNameField.getText(),emailFieldRegister.getText(),passwordFieldRegister.getText(),confirmPassRegister.getText())){
                firstNameField.clear();
                lastNameField.clear();
                emailFieldRegister.clear();
                passwordFieldRegister.clear();
                confirmPassRegister.clear();
                invalidLabelForRegister.setStyle("-fx-text-fill: lightgreen");
                invalidLabelForRegister.setText("Registered successfully!");
                System.out.println("New user!!");

            }
        }
    }
    private boolean  checkIfUserAlreadyExists(String firstName, String lastName, String email){
        String query = "SELECT * FROM passenger WHERE Email = ? AND FirstName = ? AND LastName = ?";
        try (Connection connection = DriverManager.getConnection(URL_WITH_DB, USER_NAME, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true; // User exists
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }
    private boolean checkIfEmailAlreadyExists(){
        String query = "SELECT * FROM passenger WHERE Email = ?";
        try (Connection connection = DriverManager.getConnection(URL_WITH_DB,USER_NAME, PASS);
            PreparedStatement preparedStatement = connection.prepareStatement(query))   {
            preparedStatement.setString(1, emailFieldRegister.getText());

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()) return true;
            }catch (Exception e){
                System.out.println(e);
            }
        }catch (Exception e){
            System.out.println(e);
        }
        return false;
    }
    @FXML
    void close(MouseEvent event) {
        Stage stage1 = (Stage) CloseButton.getScene().getWindow();
        stage1.close();
    }

    @FXML
    void rotateAndScale(MouseEvent event) {
        RotateTransition translateTransition = new RotateTransition();
        translateTransition.setNode(CloseButton);
        translateTransition.setDuration(Duration.millis(100));
        translateTransition.setByAngle(90);
        translateTransition.play();


    }
    @FXML
    void rotateBack(){

    }
    @FXML
    void backToLogIn(){
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(coverPane);
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setByX(-410);
        translateTransition.play();

        TranslateTransition transitionForLogIn = new TranslateTransition();
        transitionForLogIn.setNode(LogInPane);
        transitionForLogIn.setByX(20);
        transitionForLogIn.play();

        TranslateTransition transitionForRegisterPane = new TranslateTransition();
        transitionForRegisterPane.setNode(RegisterPane);
        transitionForRegisterPane.setByX(85);
        transitionForRegisterPane.play();

        firstNameField.clear();
        lastNameField.clear();
        emailFieldRegister.clear();
        passwordFieldRegister.clear();
        confirmPassRegister.clear();

        invalidLabelForRegister.setText("");
    }

    @FXML
    void goToRegisterPane(){
        invalidLabel.setText("");
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(coverPane);
        translateTransition.setDuration(Duration.millis(400));
        translateTransition.setByX(410);
        translateTransition.play();

        TranslateTransition transitionForLogIn = new TranslateTransition();
        transitionForLogIn.setNode(LogInPane);
        transitionForLogIn.setByX(-20);
        transitionForLogIn.play();

        TranslateTransition transitionForRegisterPane = new TranslateTransition();
        transitionForRegisterPane.setNode(RegisterPane);
        //transitionForRegisterPane.setDuration(Duration.millis(200));
        transitionForRegisterPane.setByX(-85);
        transitionForRegisterPane.play();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
    private double x;
    private double y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        coverPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        coverPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }


}
