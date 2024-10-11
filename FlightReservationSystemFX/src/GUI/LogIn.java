package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LogIn extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);


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


        scene.getStylesheets().add(getClass().getResource("Style2.css").toExternalForm());
        LogInController logInController = loader.getController();
        logInController.setStage(stage);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);


        //
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
}
