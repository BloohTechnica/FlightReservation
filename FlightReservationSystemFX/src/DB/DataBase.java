package DB;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.*;
import java.util.Optional;

public class DataBase {
    String URL = "jdbc:mysql://localhost:3306/";
    String URL_WITH_DB = "jdbc:mysql://localhost:3306/flight_reservation_system";
    String USER_NAME = "root";
    String PASS = "";


    public boolean checkIfUserExists(String email, String password) {
        String query = "SELECT * FROM passenger WHERE Email = ? AND Password = ?";
        try (Connection connection = DriverManager.getConnection(URL_WITH_DB, USER_NAME, PASS);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return true; // User exists
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Server is unavailable!");
            alert.showAndWait();
        }
        return false; // User does not exist
    }
    //register checkers variables

    public boolean userAlreadyExists = false;
    public boolean addNewUser(String firstName, String lastName, String Email, String Password, String ConfirmPassword){


            if (!checkIfUserAlreadyExists(firstName,lastName,Email)){
                String query = "INSERT INTO passenger(FirstName,LastName,Email,Password) VALUES(?,?,?,?)";
                try (Connection connection = DriverManager.getConnection(URL_WITH_DB,USER_NAME,PASS);
                     PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
                    preparedStatement.setString(1,firstName);
                    preparedStatement.setString(2,lastName);
                    preparedStatement.setString(3,Email);
                    preparedStatement.setString(4,Password);
                    preparedStatement.executeUpdate();
                    return true;
                }catch (Exception e){
                    System.out.println(e);
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Server is unavailable!");
                    alert.showAndWait();
                }
            } else {
                userAlreadyExists = true;
            }


        return false;
    }
    //this method will check if user already exists in the database to avoid data redundancy
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

}
