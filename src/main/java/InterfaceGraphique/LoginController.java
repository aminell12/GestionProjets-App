package InterfaceGraphique;

import Model.*;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
    @FXML
    private TextField TextUserTeacher,TextUserstud,TextPassTeacher,TextPassStud;
    @FXML
    private Button btnConnecterStu,btnConnecterTeacher,BtnAccueil;

    /** Méthode qui permet de revenir à l'accueil */
    @FXML
    public void BacktoAccueil() throws IOException {
        // Charger le fichier Login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
        Parent loginRoot = loader.load();

        // Obtenir la scène actuelle et la remplacer par la scène de Login
        Stage stage = (Stage) BtnAccueil.getScene().getWindow();
        stage.setScene(new Scene(loginRoot));
        stage.show();
    }
    
    /** Méthode qui permet au prof de se connecter */
    @FXML
    public void loginT() {
            // on vérifie si le prof se connecte bien à son profil
        if (adminConn.logAdmin(TextUserTeacher.getText(), TextPassTeacher.getText())){
            // Connexion réussie
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Connexion réussie");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Vous êtes connecté avec succès !");
            successAlert.showAndWait();
            TextUserTeacher.setText("");
            TextPassTeacher.setText("");
            // Charger la scène des professeurs (exemple de chargement)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Teacher.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btnConnecterTeacher.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // Échec de la connexion
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Identifiants incorrects. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
    
    /** Méthode qui permet à l'élève de se connecter */
    @FXML
    public void loginS() {
            // On vérifie si le prof se connecte bien à son profil
        if (adminConn.logAdmin(TextUserstud.getText(), TextPassStud.getText())){
            // Connexion réussie
            // Mise à jour des informations dans la classe User
            User.setCurrentUser(TextUserstud.getText(), TextPassStud.getText());
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Connexion réussie");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Vous êtes connecté avec succès !");
            successAlert.showAndWait();
            TextUserstud.setText("");
            TextPassStud.setText("");
            // Charger la scène des professeurs (exemple de chargement)
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Student.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) btnConnecterStu.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            // Échec de la connexion
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText(null);
            alert.setContentText("Identifiants incorrects. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
}
