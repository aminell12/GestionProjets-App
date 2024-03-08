package InterfaceGraphique;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import Model.*;
import javafx.util.Duration;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StudentController implements Initializable{
    @FXML
    private AnchorPane PaneHautContenaire1;

    @FXML
    private AnchorPane PaneHautContenaire;

    @FXML
    private Label PH_Label_Nomprojet;

    @FXML
    private Button DeconnecteBtn;

    @FXML
    private Button Menu;

    @FXML
    private AnchorPane PaneLeft;

    @FXML
    private VBox MenuVbox;

    @FXML
    private Button MenuSlide_btn_P;

    @FXML
    private Button MenuSlide_btn_N;

    @FXML
    private AnchorPane PC_Note;

    @FXML
    private TableView<User> Note_Table;

    @FXML
    private TableColumn<User, String> Note_Table_col_Cours;

    @FXML
    private TableColumn<User, String> Note_Table_col_Sujet;

    @FXML
    private TableColumn<User, Double> Note_Table_col_NR;

    @FXML
    private TableColumn<User, Double> Note_Table_col_NS;

    @FXML
    private TableColumn<User, Double> Note_Table_col_MP;

    @FXML
    private TextField Grades_search;

    @FXML
    private AnchorPane PC_Projet;

    @FXML
    private TableView<User> Projet_Table;

    @FXML
    private TableColumn<User,String> Projet_Table_col_Cours;

    @FXML
    private TableColumn<User,String> Projet_Table_col_Sujet;

    @FXML
    private TableColumn<User,Double> Projet_Table_col_NotesRapport;

    @FXML
    private TableColumn<User,Double> Projet_Table_col_NotesS;

    @FXML
    private TableColumn<User,String> Projet_Table_col_Status;

    @FXML
    private TextField Grades_search1;

    @FXML
    private Label Grades_Label_Sujet;

    @FXML
    private ComboBox<String> Projet_CB_Cours;

    @FXML
    private Label Grades_Label_Sujet1;

    @FXML
    private ComboBox<String> Projet_CB_sujet;

    @FXML
    private DatePicker Projet_Date;

    @FXML
    private Label Grades_Label_Sujet11;

    @FXML
    private Button Projet_remis;
    @FXML
    private Label Nom_Student;


                                            /*Fonction générale */

    @Override
    public void initialize(URL location, ResourceBundle resources){
        Nom_Student.setText(User.getNom()+ " " + User.getPrenom());
        refreshDataProjetUser();
        initializeTableNoteUser();
    }

    /** Méthode permettant l'affichage d'une boite de dialogue */
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /** Cette méthode remplit le ComboBox avec les données fournies par la méthode de récupération de données */
    private void populateComboBox(ComboBox<String> comboBox, String[] items) {
        comboBox.setItems(FXCollections.observableArrayList(items));
    }

    /** Méthode qui crée le menu affichant les différentes tables */
    public void MenuBarSlide() {
        // Gestionnaire unique pour le clic sur le menu
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setNode(MenuVbox);
        // Vérification de  la visibilité du MenuVbox pour déterminer son état
        if (!MenuVbox.isVisible()) {
            // Si MenuVbox n'est pas visible, le rendre visible et le glisser à 0
            MenuVbox.toFront();
            MenuVbox.setVisible(true);
            transition.setToX(0); // Déplacer MenuVbox pour qu'il soit visible
        } else {
            // Si MenuVbox est visible, le glisser hors de l'écran et le rendre non visible après la transition
            transition.setToX(-MenuVbox.getWidth()); // Déplacer MenuVbox hors de l'écran
            transition.setOnFinished(e -> {MenuVbox.setVisible(false);MenuVbox.toBack();}); // Rendre MenuVbox non visible après qu'il est hors de l'écran
        }
        transition.play();
    }

    /** Méthode qui permet de revenir à l'accueil */
    public void backtoLogin() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) DeconnecteBtn.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /* Permet de switcher entre les différentes page de l'interface Sutendt */
    public void switchpane(ActionEvent event){
        // Cas pour MenuSlide_btn_Ele
        if(event.getSource() == MenuSlide_btn_P){
            PC_Note.setVisible(false);
            PC_Projet.setVisible(true);
            //Actualise lkes données
            refreshDataProjetUser(); 
        }
        // Cas pour MenuSlide_btn_For
        else if(event.getSource() == MenuSlide_btn_N){
            PC_Note.setVisible(true);
            PC_Projet.setVisible(false);
            //initializeTableNote();
        }
    }

    
                                    /*Projet*/

    /** Méthode qui permet de initialiser la Table de Projet */
    public void initializeTableProjetUser() {
        //On ajoute les valeurs à chaque cellule
        Projet_Table_col_Cours.setCellValueFactory(new PropertyValueFactory<>("coursName"));
        Projet_Table_col_Sujet.setCellValueFactory(new PropertyValueFactory<>("sujet")); 
        Projet_Table_col_NotesRapport.setCellValueFactory(new PropertyValueFactory<>("gradeRapport")); 
        Projet_Table_col_NotesS.setCellValueFactory(new PropertyValueFactory<>("gradeSoutenance"));
        Projet_Table_col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));
        //On ajoute les elements au tableau
        User.AfficherTUser(Projet_Table);
    }

    /** Permet de mettre à jour la liste des sujets et des elèves en fonction du cours choisi */
    @FXML
    private void updateSujetAndEleveParCoursNote(){
        String selectedCours = Projet_CB_Cours.getValue();
        ObservableList<String> sujet = FXCollections.observableArrayList(Project.getProjetsParCours(selectedCours));
        Projet_CB_sujet.setItems(sujet);
        ObservableList<String> eleves = FXCollections.observableArrayList(Student.getElevesParCours(selectedCours));
        Projet_CB_sujet.setItems(eleves);
    }
    
    /** Méthode qui permet l'insertion de la date de remise */
    @FXML
    private void handleRemiseProjet(){
        // Récupérer les valeurs des champs de texte
        String coursName = Projet_CB_Cours.getValue();
        String sujet = Projet_CB_sujet.getValue();
        LocalDate dateRem = Projet_Date.getValue();
        if (coursName== null|| sujet == null || dateRem == null)  {
            showAlert(AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }
        try {
            boolean affectedRows = User.Remise(coursName, sujet,dateRem);
            if (affectedRows ) {
                showAlert(AlertType.INFORMATION, "Succès", "Projet "+ Projet_CB_sujet.getValue() + " avec succès.");
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Aucune Projet remis. Le projet est déjà rendu.");
            }
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de la remise du projet.");
            ex.printStackTrace();
        }
        refreshDataProjetUser();
    }
    
    /** Actualise les données de Projet */
    @FXML
    private void refreshDataProjetUser() {
        initializeTableProjetUser();
        populateComboBox(Projet_CB_Cours,Project.RecupereCours());
        populateComboBox(Projet_CB_sujet,Project.RecupereProjet());
        Projet_CB_Cours.setValue(null);
        Projet_CB_sujet.setValue(null);
        Projet_Date.setValue(null);
    }
    
    /** Méthode qui récupère les infos d'un projet selectionné du tableau */
    public void getSelectedProjetsTable(){
        User projetSelectionne = Projet_Table.getSelectionModel().getSelectedItem();
        if (projetSelectionne != null) {
            // Mise à jour des champs de texte avec les propriétés de l'objet Student sélectionné
            Projet_Date.setValue(null);
            Projet_CB_Cours.setValue(projetSelectionne.getCoursName());
            Projet_CB_sujet.setValue(projetSelectionne.getSujet());
        }
    }


                                    /*Note*/
    /** Méthode qui permet de initialiser la Table de Projet */
    public void initializeTableNoteUser() {
        //On ajoute les valeurs à chaque cellule
        Note_Table_col_Cours.setCellValueFactory(new PropertyValueFactory<>("coursName"));
        Note_Table_col_Sujet.setCellValueFactory(new PropertyValueFactory<>("sujet")); 
        Note_Table_col_NR.setCellValueFactory(new PropertyValueFactory<>("gradeRapport")); 
        Note_Table_col_NS.setCellValueFactory(new PropertyValueFactory<>("gradeSoutenance"));
        Note_Table_col_MP.setCellValueFactory(new PropertyValueFactory<>("average"));
        //On ajoute les elements au tableau
        User.AfficherTUserNote(Note_Table);
    }
}

