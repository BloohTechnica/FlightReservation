package DB;

import java.sql.*;

public class Config {
    String URL = "jdbc:mysql://localhost:3306/";
    String URL_WITH_DB = "jdbc:mysql://localhost:3306/flight_reservation_system";
    String USER_NAME = "root";
    String PASS = "";

    public Config(){
        createDataBase();
        createTables();
    }
    private void createDataBase() {
        String dbName = "flight_reservation_system";
        String query = "CREATE DATABASE IF NOT EXISTS " + dbName;

        try (Connection connection = DriverManager.getConnection(URL, USER_NAME, PASS);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(query);
            System.out.println("Database created successfully!");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createTables(){
        String tableForPassenger = "CREATE TABLE IF NOT EXISTS passenger (" +
                "PassengerID int(11) PRIMARY KEY AUTO_INCREMENT, " +
                "FirstName varchar(50) NOT NULL, " +
                "LastName varchar(50) NOT NULL, " +
                "Email varchar(100) NOT NULL UNIQUE, " +
                "Password varchar(255) NOT NULL" +
                ")";
        String tableForAirline = "CREATE TABLE IF NOT EXISTS airline (" +
                "AirlineID int(11) PRIMARY KEY AUTO_INCREMENT, " +
                "AirlineName varchar(100) NOT NULL, " +
                "ContactNumber varchar(20) NOT NULL, " +
                "OperatingRegion varchar(100) NOT NULL" +
                ")";
        String tableForFlights = "CREATE TABLE IF NOT EXISTS flights(" +
                "FlightID int(11) PRIMARY KEY," +
                "FlightNumber int(11) NOT NULL," +
                "DepartureDateTime DATETIME NOT NULL," +
                "ArrivalDateTime DATETIME NOT NULL," +
                "OriginAirportCode varchar(255) NOT NULL," +
                "DestinationAirportCode varchar(255) NOT NULL," +
                "AvailableSeats int(11) NOT NULL" +
                ")";
        String tableForAirports = "CREATE TABLE IF NOT EXISTS airport(" +
                "AirportCode int(11) PRIMARY KEY NOT NULL," +
                "AirportName varchar(255) NOT NULL," +
                "Location varchar(255) NOT NULL" +
                ")";
        Create(tableForPassenger);
        Create(tableForAirline);
        Create(tableForFlights);
        Create(tableForAirports);
    }

    private void Create(String query){
        try (Connection connection = DriverManager.getConnection(URL_WITH_DB,USER_NAME,PASS);
            Statement statement = connection.createStatement()){
            statement.executeUpdate(query);
            System.out.println("Table created success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Config();
    }
}
