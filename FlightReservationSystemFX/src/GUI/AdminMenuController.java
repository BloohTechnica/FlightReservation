package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {


    @FXML
    private Button AirlinesButton;

    @FXML
    private Button AirportsButton;

    @FXML
    private Button FlightsButton;

    @FXML
    private Button UsersButton;
    @FXML
    private Button logOutButton;

    @FXML
    private FontAwesomeIcon menuButton;
    @FXML
    private BorderPane borderPane;
    boolean checkIfPress = false;
    @FXML
    private AnchorPane menuContainer;



    @FXML
    void openMenu(MouseEvent event) {
        /*ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setNode(menuContainer);
        if (!checkIfPress){
            checkIfPress = true;


            scaleTransition.setByX(500);
            scaleTransition.play();
        } else if (checkIfPress) {
            checkIfPress = false;
            scaleTransition.setByX(-500);
            scaleTransition.play();
        }*/

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(menuContainer);
        if (!checkIfPress && menuContainer.getLayoutX() == -330){
            checkIfPress = true;
            translateTransition.setByX(330);
            translateTransition.play();
        }

    }
    @FXML
    void back(){
        if (checkIfPress){
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(menuContainer);
            checkIfPress = false;
            translateTransition.setByX(-330);
            translateTransition.play();
        }

    }
    @FXML
    void back2(){
        if (checkIfPress){
            TranslateTransition translateTransition = new TranslateTransition();
            translateTransition.setNode(menuContainer);
            checkIfPress = false;
            translateTransition.setByX(-330);
            translateTransition.play();
        }
    }

    @FXML
    void AirlineManagement(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("AirlinesManagementScene.fxml"));
        borderPane.setCenter(anchorPane);
    }

    @FXML
    void FlightsManagement(ActionEvent event) throws IOException {
        loadFlights();
    }
    @FXML
    void AirportsManagement() throws IOException{
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("AirportsManagementScene.fxml"));
        borderPane.setCenter(anchorPane);
    }
    @FXML
    void userManagement(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("UserManagementScene.fxml"));
        borderPane.setCenter(anchorPane);
    }
    private void loadFlights() throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("FlightManagementScene.fxml"));
        borderPane.setCenter(anchorPane);
    }


    //buton focus

    @FXML
    void flightsFocus(MouseEvent event) {

    }

    @FXML
    void airlinesFocus(MouseEvent event) {

    }

    @FXML
    void airportsFocus(MouseEvent event) {

    }


    @FXML
    void userFocus(MouseEvent event) {

        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Choose the alert type
        alert.setTitle("User Focused");
        alert.setHeaderText(null); // You can set a header text or leave it as null
        alert.setContentText("The user has focused on this element."); // The main message to display

        alert.showAndWait(); // Display the alert and wait for the user to close it
        */
        /*

        */


    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation to Log out");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to log out?");

        Optional<ButtonType> result = alert.showAndWait();

        // Check the user's response
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User chose OK
            System.out.println("User chose OK");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInForAdmin.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) logOutButton.getScene().getWindow();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            LogInForAdmin logInForAdmin = loader.getController();
            logInForAdmin.setStage(stage);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();
            currentStage.close();

        } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
            // User chose Cancel or closed the dialog
            System.out.println("User chose Cancel or closed the dialog");
        }
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadFlights();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menuContainer.setLayoutX(-330);
    }
}
