package Data;

import java.sql.*;

public class DataBaseConnexion {

    //Constructeur 
    private DataBaseConnexion(){
        throw new AssertionError("Cette classe ne doit pas être instanciée.");
    }
    private static final String URL = "jdbc:mysql://localhost:3306/Project";
    private static final String USER = "sqluser";
    private static final String PASSWORD = "password";
    private static  String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    //Fonction static qui permet de renvoyer la connexion à la base de donner permettant de créer les requêtes dans les autres classes
    public static Connection getConnection() throws SQLException{
        try {
            Class.forName(DRIVER_CLASS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Driver JDBC introuvable", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}
