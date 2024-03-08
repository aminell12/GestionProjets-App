package InterfaceGraphique;


import java.io.IOException;




import javafx.fxml.*;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


public class AccueilController {
    //Attributs pour les différentes composantes de la Page Accueil     
    @FXML
    private Label NomProjetAcc;

    @FXML
    private AnchorPane PaneAccueil;

    @FXML
    private AnchorPane PaneDroiteAcc;

    @FXML
    private AnchorPane PaneGaucheAcc;

    @FXML
    private TextFlow TextAccueil;

    @FXML
    private Label TitreBienvenueAcc;

    @FXML
    private Button btnLancer;

    @FXML
    private ImageView logoImageAccueil;

    /** Permet d'afficher le texte à la page d'accueil */
    public void initialize() {
        Text introText = new Text("Nous sommes ravis de vous accueillir sur votre plateforme interactive dédiée à la gestion et au suivi de projets académiques. Notre système est conçu pour faciliter la communication et la collaboration entre professeurs et étudiants, en offrant des fonctionnalités adaptées à chaque groupe.\n\n");
        Text espaceProfesseurTitle = new Text("1. Espace Professeur :\n");
        espaceProfesseurTitle.setStyle("-fx-font-weight: bold");
        Text espaceProfesseurContent = new Text("En tant que professeur, cet espace vous offre une vue d'ensemble complète des projets de vos étudiants. Vous pouvez :\n• Gestion : Des Formations, des projets, des étudiants…\n• Évaluation des Étudiants : Saisir des notes, fournir des retours.\n\n");
        Text espaceEtudiantTitle = new Text("2. Espace Étudiant :\n");
        espaceEtudiantTitle.setStyle("-fx-font-weight: bold");
        Text espaceEtudiantContent = new Text("En tant qu'étudiant, cet espace est votre centre de suivi et de gestion de projet. Vous pouvez :\n• Consulter Vos Notes : Accéder à vos notes, moyennes individuelles et moyennes de projet.\n• Voir les Informations de la Promotion : Découvrir les performances générales et les membres de votre promotion.\n\n");
        Text commencerTitle = new Text("3. Commencer :\n");
        commencerTitle.setStyle("-fx-font-weight: bold");
        Text commencerContent = new Text("• Connexion : Utilisez vos identifiants pour accéder à votre espace personnel (disponible dans le README pour chaque utilisateur).\n• Navigation : Explorez les fonctionnalités et outils disponibles selon votre rôle.\n\n");
        Text closingText = new Text("Nous sommes là pour soutenir votre parcours académique et professionnel. Profitez de toutes les ressources à votre disposition et faites de chaque projet une réussite !\n\nNous vous souhaitons une expérience enrichissante et productive !");
        TextAccueil.getChildren().addAll(introText, espaceProfesseurTitle, espaceProfesseurContent, espaceEtudiantTitle, espaceEtudiantContent, commencerTitle, commencerContent, closingText);
    }
    
    /** Méthode qui permet le lancement de l'application */
    @FXML
    public void LancerApp() throws IOException {
        // Charger le fichier Login.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceGraphique/Login.fxml"));
        Parent loginRoot = loader.load();
        // Obtenir la scène actuelle et la remplacer par la scène de Login
        Stage stage = (Stage) btnLancer.getScene().getWindow();
        stage.setScene(new Scene(loginRoot));
        stage.show();
        stage.setResizable(false);
    }
}
    




