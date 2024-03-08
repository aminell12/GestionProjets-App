package Main;

import java.sql.SQLException;

import Data.DataBaseConnexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Chargement du fichier FXML
            Parent root = FXMLLoader.load(getClass().getResource("/InterfaceGraphique/Accueil.fxml"));
            // Création de la scène avec le root FXML et définition de sa taille
            Scene scene = new Scene(root);
            // Configuration du stage (fenêtre)
            primaryStage.setTitle("MyProject");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); //Permet de ne pas changer la taille de la fenêtere
            
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Pour lancer l'application
    public static void main(String[] args) throws SQLException {
        launch(args);
        DataBaseConnexion.getConnection();
    }
}
