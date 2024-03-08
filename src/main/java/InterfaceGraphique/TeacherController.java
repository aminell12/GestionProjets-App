package InterfaceGraphique;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TeacherController implements Initializable {
    
    @FXML
    private TableView<Project> Projet_Table;
    @FXML
    private TableColumn<Project, String> Projet_Table_col_Cours;
    @FXML
    private TableColumn<Project, LocalDate> Projet_Table_col_DateRemise;
    @FXML
    private TableColumn<Project, Number> Projet_Table_col_Formation;
    @FXML
    private TableColumn<Project, Number> Projet_Table_col_NumID;
    @FXML
    private TableColumn<Project, String> Projet_Table_col_Promo;
    @FXML
    private TableColumn<Project, String> Projet_Table_col_Sujet;

    @FXML
    private Label Projet_Label_PG_NbAffStat;
    @FXML
    private AnchorPane BInomePaneG_AffStats;
    @FXML
    private Label BInome_Label_Inb;
    @FXML
    private Label BInome_Label_nbBIn;
    @FXML
    private AnchorPane BInome_PG_BtnINsert;
    @FXML
    private CheckBox Binome_CheckB_1;
    @FXML
    private CheckBox Binome_CheckB_2;
    @FXML
    private ComboBox<String> Binome_Combo_El1;
    @FXML
    private ComboBox<String> Binome_Combo_El2;
    @FXML
    private ComboBox<String> Binome_Combo_Sujet;
    @FXML
    private ComboBox<String> Binome_Combo_cours;
    @FXML
    private Label Binome_Label_El2;
    @FXML
    private Label Binome_Label_cours;
    @FXML
    private Label Binome_Label_nb;
    @FXML
    private Label Binome_Label_sujet;
    @FXML
    private Label Binome_label_El1;
    @FXML
    private AnchorPane Binome_pane_table;
    @FXML
    private TableView<Binome> Binome_table;
    @FXML
    private TableColumn<Binome, String> Binome_table_col_el1;
    @FXML
    private TableColumn<Binome, String> Binome_table_col_el2;
    @FXML
    private TableColumn<Binome, Number> Binome_table_col_numID;
    @FXML
    private TableColumn<Binome, String> Binome_table_col_sujet;
    @FXML
    private TableColumn<Binome, LocalDate> Binome_table_col_DateRem;
    @FXML
    private TextField Binome_tsearch;
    @FXML
    private BorderPane BorderPaneCont;
    @FXML
    private PieChart DashBoard_Diagramme;
    @FXML
    private Label Dashboard_Label;
    @FXML
    private Label Dashboard_LabelT_BinRAffStat;
    @FXML
    private Label Dashboard_Label_BinRAffStat;
    @FXML
    private Label Dashboard_Label_PRC;
    @FXML
    private Label Dashboard_Label_PRCAffichstat;
    @FXML
    private Label Dashboard_Label_date;
    @FXML
    private AnchorPane Dashboard_PaneG_PRC;
    @FXML
    private AnchorPane Dashboard_Pane_AffStat;
    @FXML
    private AnchorPane Dashboard_Table_BinRAffStat;
    @FXML
    private Button DeconnecteBtn;
    @FXML
    private AnchorPane Fenetre;
    @FXML
    private Button Formation_Btn_Actua;
    @FXML
    private Button Formation_Btn_Insert;
    @FXML
    private Button Formation_Btn_Mod;
    @FXML
    private Button Formation_Btn_Supp;
    @FXML
    private ComboBox<String> Formation_Combo_promo;
    @FXML
    private Label Formation_Label_forma;
    @FXML
    private Label Formation_Label_nbForma;
    @FXML
    private Label Formation_Label_promo;
    @FXML
    private Label Formation_Label_txt;
    @FXML
    private AnchorPane Formation_PG_Affstat;
    @FXML
    private AnchorPane Formation_PG_BtnTable;
    @FXML
    private TextField Formation_TF_Forma;
    @FXML
    private TableView<Formation> Formation_Table;
    @FXML
    private TableColumn<Formation, String> Formation_Table_col_forma;
    @FXML
    private TableColumn<Formation, Number> Formation_Table_col_numID;
    @FXML
    private TableColumn<Formation, String> Formation_Table_col_promo;
    @FXML
    private TextField Formation_search;
    @FXML
    private ComboBox<String> Grades_Box_student;
    @FXML
    private ComboBox<String> Grades_CB_Cours;
    @FXML
    private ComboBox<String> Grades_CB_Sujet;
    @FXML
    private Label Grades_Label_GradesR;
    @FXML
    private Label Grades_Label_GradesS;
    @FXML
    private Label Grades_Label_Sujet;
    @FXML
    private Label Grades_Label_student;
    @FXML
    private TextField Grades_TF_GradesR;
    @FXML
    private TextField Grades_TF_GradesS;
    @FXML
    private TableView<Grades> Grades_Table;
    @FXML
    private TableColumn<Grades, String> Grades_Table_col_Cours;
    @FXML
    private TableColumn<Grades, Number> Grades_Table_col_ID;
    @FXML
    private TableColumn<Grades, Number> Grades_Table_col_Moy;
    @FXML
    private TableColumn<Grades, Number> Grades_Table_col_NR;
    @FXML
    private TableColumn<Grades, Number> Grades_Table_col_NS;
    @FXML
    private TableColumn<Grades, String> Grades_Table_col_Nom;
    @FXML
    private TableColumn<Grades, String> Grades_Table_col_Prenom;
    @FXML
    private TableColumn<Grades, String> Grades_Table_col_Sujet;
    @FXML
    private AnchorPane Grades_pane_table;
    @FXML
    private TextField Grades_search;
    @FXML
    private Label Notes_Label_PG_1;
    @FXML
    private Button Menu;
    @FXML
    private VBox MenuVbox;
    @FXML
    private Button Notes_Btn_Actu;
    @FXML
    private Button Notes_Btn_Insert;
    @FXML
    private Button Notes_Btn_Mod;
    @FXML
    private Button Notes_Btn_Supp;
    @FXML
    private Label Notes_Label_PG_;
    @FXML
    private Label Notes_labelPG_MPC;
    @FXML
    private AnchorPane Notes_paneG_affichage;
    @FXML
    private AnchorPane PGInsertionNotes;
    @FXML
    private AnchorPane PGInsertionBinome;
    @FXML
    private AnchorPane PGInsertionFormation;
    @FXML
    private AnchorPane PGInsertionProjet;
    @FXML
    private AnchorPane PGInsertionStudent;
    @FXML
    private AnchorPane PGTableauBord;
    @FXML
    private AnchorPane PaneCBinome;
    @FXML
    private AnchorPane PaneCDB;
    @FXML
    private AnchorPane PaneCFormation;
    @FXML
    private AnchorPane PaneCNotes;
    @FXML
    private AnchorPane PaneCProjet;
    @FXML
    private AnchorPane PaneCStudent;
    @FXML
    private ComboBox<String> Projet_Combo_forma;
    @FXML
    private ComboBox<String> Projet_Combo_promo;
    @FXML
    private Label Projet_Label_Cours;
    @FXML
    private Label Projet_Label_date;
    @FXML
    private Label Projet_Label_forma;
    @FXML
    private Label Projet_Label_promo;
    @FXML
    private Label Projet_Label_sujet;
    @FXML
    private TextField Projet_Tf_Cours;
    @FXML
    private TextField Projet_Tf_sujet;
    @FXML
    private DatePicker Projet_date;
    @FXML
    private AnchorPane Projet_pane_table;
    @FXML
    private Button Student_Btn_Actua;
    @FXML
    private Button Student_Btn_Insert;
    @FXML
    private Button Student_Btn_Modif;
    @FXML
    private Button Student_Btn_Supp;
    @FXML
    private ComboBox<String> Student_Combo_forma;
    @FXML
    private ComboBox<String> Student_Combo_promo;
    @FXML
    private Label Student_Label_Nom;
    @FXML
    private Label Student_Label_forma;
    @FXML
    private Label Student_Label_prenom;
    @FXML
    private Label Student_Label_promo;
    @FXML
    private Label Student_PGL_AffnbEl;
    @FXML
    private Label Student_PGL_nbel;
    @FXML
    private AnchorPane Student_PG_AffichStat;
    @FXML
    private AnchorPane Student_PG_BtnTable;
    @FXML
    private AnchorPane Student_PC_Table;
    @FXML
    private TextField Student_TF_nom;
    @FXML
    private TextField Student_TF_prenom;
    @FXML
    private TableView<Student> Student_Table;
    @FXML
    private TableColumn<Student, String> Student_Table_col_forma;
    @FXML
    private TableColumn<Student, Number> Student_Table_col_numID;
    @FXML
    private TableColumn<Student, String> Student_Table_col_promo;
    @FXML
    private TableColumn<Student, String> Student_Table_col_Nom,Student_Table_col_Prenom;
    @FXML
    private TextField Student_search;

    @FXML
    private Button MenuSlide_btn_TB,MenuSlide_btn_P,MenuSlide_btn_N,MenuSlide_btn_Ele,MenuSlide_btn_Bin,MenuSlide_btn_For;

    private final String [] PROMOTION = {"Initiale","Alternance","Continue"};


                                    /**Partie Générale */

    @Override
    public void initialize(URL location, ResourceBundle resources){
        Dashboard_Label_BinRAffStat.setText(String.valueOf(Binome.countLateBinomes()));
        Dashboard_Label_PRCAffichstat.setText(String.valueOf(Project.countInProgressProjects()));
        refreshDataStudent();
        refreshDataFormation();
        refreshDataProjets();
        refreshDataBinome();
        refreshDataNote();
    }
    
    /** Méthode permettant l'affichage d'une boite de dialogue */
    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /** Cette méthode remplit le ComboBox avec les données fournies par la méthode de récupération de données**/
    private void populateComboBox(ComboBox<String> comboBox, String[] items) {
        comboBox.setItems(FXCollections.observableArrayList(items));
    }
    
    /** Méthode qui crée le menu affichant les différentes tables */
    public void MenuBarSlide() {
        // Gestionnaire unique pour le clic sur le menu
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setNode(MenuVbox);
        // Vérification de la visibilité de MenuVbox pour déterminer son état
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
    
    /** Permet de switcher entre les différentes page de l'interface Professeur */
    public void switchpane(ActionEvent event){
        // Cas pour MenuSlide_btn_TB
        if (event.getSource() == MenuSlide_btn_TB){
            PGTableauBord.setVisible(true);
            PaneCDB.setVisible(true);
            PaneCStudent.setVisible(false);
            PGInsertionStudent.setVisible(false);
            PaneCFormation.setVisible(false);
            PGInsertionFormation.setVisible(false);
            PaneCBinome.setVisible(false);
            PGInsertionBinome.setVisible(false);
            PaneCProjet.setVisible(false);
            PGInsertionProjet.setVisible(false);
            PaneCNotes.setVisible(false);
            PGInsertionNotes.setVisible(false);
            MenuBarSlide();
            //Mise à jour de la page à chaque fois qu'on y vient
            Dashboard_Label_BinRAffStat.setText(String.valueOf(Binome.countLateBinomes()));
            Dashboard_Label_PRCAffichstat.setText(String.valueOf(Project.countInProgressProjects()));
        }

        // Cas pour MenuSlide_btn_Ele
        else if(event.getSource() == MenuSlide_btn_Ele){
            PGTableauBord.setVisible(false);
            PaneCDB.setVisible(false);
            PaneCStudent.setVisible(true);
            PGInsertionStudent.setVisible(true);
            PaneCFormation.setVisible(false);
            PGInsertionFormation.setVisible(false);
            PaneCBinome.setVisible(false);
            PGInsertionBinome.setVisible(false);
            PaneCProjet.setVisible(false);
            PGInsertionProjet.setVisible(false);
            PaneCNotes.setVisible(false);
            PGInsertionNotes.setVisible(false);
            MenuBarSlide();
            //Mise à jour de la page à chaque fois qu'on y vient
            refreshDataStudent();
        }

        // Cas pour MenuSlide_btn_For
        else if(event.getSource() == MenuSlide_btn_For){
            PGTableauBord.setVisible(false);
            PaneCDB.setVisible(false);
            PaneCStudent.setVisible(false);
            PaneCFormation.setVisible(true);
            PGInsertionFormation.setVisible(true);
            PaneCBinome.setVisible(false);
            PGInsertionBinome.setVisible(false);
            PaneCProjet.setVisible(false);
            PGInsertionProjet.setVisible(false);
            PaneCNotes.setVisible(false);
            PGInsertionNotes.setVisible(false);
            MenuBarSlide();
            //Mise à jour de la page à chaque fois qu'on y vient
            refreshDataFormation();
        }

        // Cas pour MenuSlide_btn_Bin
        else if(event.getSource() == MenuSlide_btn_Bin){
            PGTableauBord.setVisible(false);
            PaneCDB.setVisible(false);
            PaneCStudent.setVisible(false);
            PaneCFormation.setVisible(false);
            PaneCBinome.setVisible(true);
            PGInsertionBinome.setVisible(true);
            PaneCProjet.setVisible(false);
            PGInsertionProjet.setVisible(false);
            PaneCNotes.setVisible(false);
            PGInsertionNotes.setVisible(false);
            MenuBarSlide();
            //Mise à jour de la page à chaque fois qu'on y vient
            refreshDataBinome();
        }

        // Cas pour MenuSlide_btn_P
        else if(event.getSource() == MenuSlide_btn_P){
            PGTableauBord.setVisible(false);
            PaneCDB.setVisible(false);
            PaneCStudent.setVisible(false);
            PGInsertionStudent.setVisible(false);
            PaneCFormation.setVisible(false);
            PGInsertionFormation.setVisible(false);
            PaneCBinome.setVisible(false);
            PGInsertionBinome.setVisible(false);
            PaneCProjet.setVisible(true);
            PGInsertionProjet.setVisible(true);
            PaneCNotes.setVisible(false);
            PGInsertionNotes.setVisible(false);
            MenuBarSlide();
            //Mise à jour de la page à chaque fois qu'on y vient
            refreshDataProjets();
        }

        // Cas pour MenuSlide_btn_N
        else if(event.getSource() == MenuSlide_btn_N){
            PGTableauBord.setVisible(false);
            PaneCDB.setVisible(false);
            PaneCStudent.setVisible(false);
            PGInsertionStudent.setVisible(false);
            PaneCFormation.setVisible(false);
            PGInsertionFormation.setVisible(false);
            PaneCBinome.setVisible(false);
            PGInsertionBinome.setVisible(false);
            PaneCProjet.setVisible(false);
            PGInsertionProjet.setVisible(false);
            PaneCNotes.setVisible(true);
            PGInsertionNotes.setVisible(true);
            MenuBarSlide();
            //Mise à jour de la page à chaque fois qu'on y vient
            refreshDataNote();
        }
    }
                                        
                                    /***Partie Student ***/

    /** Méthode qui permet de initialiser la Table des étudiants */
    public void initializeTableStudent() {
        //On ajoute les valeurs à chaque cellule
        Student_Table_col_numID.setCellValueFactory(new PropertyValueFactory<>("numID"));
        Student_Table_col_Nom.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        Student_Table_col_Prenom.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        Student_Table_col_forma.setCellValueFactory(new PropertyValueFactory<>("formaName"));
        Student_Table_col_promo.setCellValueFactory(new PropertyValueFactory<>("promotion"));
        //On ajoute les elements au tableau
        Student.AfficherTStudent(Student_Table);
    }
    
    /** Méthode qui permet d'insérer un Étudiant à la BDD */
    @FXML
    private void handleInsertActionStudent() {
        // Récupération des valeurs des champs TextField et ComboBox
        String nom = Student_TF_nom.getText();
        String prenom = Student_TF_prenom.getText();
        String formation = Student_Combo_forma.getValue();
        String promo = Student_Combo_promo.getValue();
        // Vérification si les champs nom et prénom sont vides
        if (nom.trim().isEmpty() || prenom.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }
        try {
            int formationId = Formation.getFormationId(formation, promo); 
            int affectedRows = Student.addStudent(nom, prenom, formationId); 
            if (affectedRows > 0) {
                showAlert(AlertType.INFORMATION, "Succès", "Étudiant inséré avec succès");
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Étudiant déjà existant dans la base de données");
            }
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de l'insertion de l'étudiant");
            ex.printStackTrace();
        }
        refreshDataStudent();
    }
    
    @FXML
    /** Méthode qui va permettre de modifier une personne selectionnée du tableau grâce à une méthode intermédiaire */
    private void handleModificationStudent(){
        Student etudiantSelectionne = Student_Table.getSelectionModel().getSelectedItem();
        if (etudiantSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un élève doit être selectionné.");
            return;
        }
        // Récupération des valeurs des champs TextField et ComboBox
        String nom = Student_TF_nom.getText();
        String prenom = Student_TF_prenom.getText();
        String formation = Student_Combo_forma.getValue();
        String promo = Student_Combo_promo.getValue();
        int StudentID = etudiantSelectionne.getNumID();
        // Applcation des modifications à la base de données
        int affectedrows = Student.modifyStudent(StudentID,nom, prenom,formation,promo);
        
        //Bloc if pour voir si l'eleve a bien été modifié
        if (affectedrows > 0) {
            showAlert(AlertType.INFORMATION,"Succès", "Élève modifié avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur", "Modifictation échouée.");
        }
        refreshDataStudent();
    }
    
    /** Méthode qui va permettre de suppprimer une formation selectionnée du tableau */
    @FXML
    private void handleSuppressionActionStudent(){
        Student etudiantSelectionne = Student_Table.getSelectionModel().getSelectedItem();
        if (etudiantSelectionne == null ) {
            showAlert(AlertType.ERROR, "Erreur", "Un élève doit être selectionné.");
            return;
        }
        // Récupération des valeurs des champs TextField et ComboBox
        String nom = etudiantSelectionne.getLastname();
        String prenom = etudiantSelectionne.getFirstname();
        int studentID = etudiantSelectionne.getNumID();
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Suppression de l'élève");
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer l'élève " + nom + " " + prenom + " ?");
        // Attente de la réponse de l'utilisateur
        Optional<ButtonType> response = confirmationAlert.showAndWait();
        if (response.isPresent() && response.get() == ButtonType.OK) {
            // L'utilisateur a confirmé la suppression
            if (studentID == -1) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Étudiant introuvable.");
                return;
            }
            int affectedRows = Student.deleteStudent(studentID); 
            if (affectedRows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Élève supprimé avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "L'élève n'a pas pu être supprimé.");
            }
        }
        refreshDataStudent();
    }
    
    /** Méthode qui récupère les infos d'un student selectionné du tableau */
    public void getSelectedStudentTable() {
        Student etudiantSelectionne = Student_Table.getSelectionModel().getSelectedItem();
        if (etudiantSelectionne != null) {
            // Mise à jour des champs de texte avec les propriétés de l'objet Student sélectionné
            String firstname = etudiantSelectionne.getFirstname();
            String lastname = String.valueOf(etudiantSelectionne.getLastname());
            int studentId = etudiantSelectionne.getNumID();
            Student_TF_prenom.setText(firstname);
            Student_TF_nom.setText(lastname);
            // Récupération de la formation et de la promotion actuelle de l'étudiant
            String currentFormation = Student.getCurrentFormation(studentId);
            String currentPromotion = Student.getCurrentPromotion(studentId);
            Student_Combo_forma.setValue(currentFormation);
            Student_Combo_promo.setValue(currentPromotion);
        }
    }
    
    /** Méthode qui permet de rafraichir les données de la table Élève */
    @FXML
    private void refreshDataStudent() {
        initializeTableStudent();
        populateComboBox(Student_Combo_forma,Formation.RecupereFormations());
        populateComboBox(Student_Combo_promo,PROMOTION);
        Student_TF_prenom.setText("");
        Student_TF_nom.setText("");
        Student_Combo_forma.setValue(null);
        Student_PGL_AffnbEl.setText((String.valueOf(Student.countStudent())));
    }
    
    /** Permet de mettre à jour la liste des promotions en fonction de la formation choisie */
    @FXML
    private void updatePromotionParFormationStudent(){
        String selectedForma = Student_Combo_forma.getValue();
        ObservableList<String> promotions = FXCollections.observableArrayList(Formation.recuperePromotionsParFormation(selectedForma));
        Student_Combo_promo.setItems(promotions);
    }
    

                                        /**Partie Formation **/

    /** Méthode qui permet de initialiser la Table des Formations */
    public void initializeTableFormation() {
        //On ajoute les valeurs à chaque cellule
        Formation_Table_col_numID.setCellValueFactory(new PropertyValueFactory<>("numID"));
        Formation_Table_col_forma.setCellValueFactory(new PropertyValueFactory<>("formaName"));
        Formation_Table_col_promo.setCellValueFactory(new PropertyValueFactory<>("promotion"));
        //On ajoute les elements au tableau
        Formation.AfficherTFormation(Formation_Table);
    }
    
     /**Méthode qui permet d'insérer une Formation à la BDD */
   @FXML
    private void handleInsertActionFormation() {
        // Récupération des valeurs des champs TextField et ComboBox
        String formaName = Formation_TF_Forma.getText();
        String promo = Formation_Combo_promo.getValue();
        // Vérification si les champs formation
        if (formaName.trim().isEmpty()|| promo == null || promo.trim().isEmpty())  {
            showAlert(AlertType.ERROR, "Erreur", "Les champs Formation et Promotion ne doivent pas être vides.");
            return;
        }
        try {
            int affectedRows = Formation.addFormation(formaName, promo);
            if (affectedRows > 0) {
                showAlert(AlertType.INFORMATION, "Succès", "Formation et Promotion insérées avec succès.");
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Aucune ligne ajoutée. La formation pourrait déjà exister.");
            }
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de l'insertion de la formation et de la promotion.");
            ex.printStackTrace();
        }
        refreshDataFormation();
    }
    
     /** Méthode qui va permettre de modifier une formation selectionnée du tableau */
   @FXML
    private void handleModificationActionFormation(){
        Formation formationSelectionne = Formation_Table.getSelectionModel().getSelectedItem();
        // Récupération des valeurs des champs TextField et ComboBox
        String formaName = Formation_TF_Forma.getText();
        String promo = Formation_Combo_promo.getValue();
        if (formationSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un formation doit être selectionnée.");
            return;
        }
        int formationID =  formationSelectionne.getNumID();
        // Application des modifications à la base de données 
        boolean affectedrows = Formation.updateFormationPromotion(formationID,formaName ,promo);
        //Bloc if pour voir si l'eleve a bien été modifié
        if (affectedrows) {
            showAlert(AlertType.INFORMATION,"Succès", "Formation et/ou Promotion  modifiée(s) avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur", "Modifictation échouée.");
        }
        refreshDataFormation();
    }
    
    /** Méthode qui va permettre de suppprimer une formation selectionnée du tableau */
    @FXML
    private void handleSuppressionActionFormation(){
        // Récupération des valeurs des champs TextField et ComboBox
        String formaName = Formation_TF_Forma.getText();
        String promo = Formation_Combo_promo.getValue();
        if (formaName.trim().isEmpty()|| promo == null || promo.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une Formation et une Promotion.");
            return;
        }
        // Application des modifications à la base de données
        boolean affectedrows = Formation.delFormation(formaName ,promo);
        //Bloc if pour voir si l'eleve a bien été modifié
        if (affectedrows) {
            showAlert(AlertType.INFORMATION,"Suppression réussie", "Formation et Promotion supprimées avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur lors de la suppression.", "Suppression échouée.");
        }
        refreshDataFormation();
    }

    /** Méthode qui récupère les infos d'une Formaiton selectionnée du tableau **/  
    @FXML
    private void getSelectedFormationTable(){
        Formation FormationSelectionne = Formation_Table.getSelectionModel().getSelectedItem();
        if (FormationSelectionne != null) {
            // Mise à jour des champs de texte avec les propriétés de l'objet Student sélectionné
            String formaName = FormationSelectionne.getFormaName();
            String promo = String.valueOf(FormationSelectionne.getPromotion());
            Formation_TF_Forma.setText(formaName);
            Formation_Combo_promo.setValue(promo);
        }
    }
    
    /** Acutalise les données de Formation */
    @FXML
    private void refreshDataFormation() {
        initializeTableFormation();
        populateComboBox(Formation_Combo_promo,PROMOTION);
        Formation_TF_Forma.setText("");
        Formation_Combo_promo.setValue(null);
        Formation_Label_nbForma.setText((String.valueOf(Formation.countFormations())));
    }
    

                                        /**Partie Projet  **/

    /** Méthode qui permet de initialiser la Table des Projets */
    public void initializeTableProjets() {
        //On ajoute les valeurs à chaque cellule
        Projet_Table_col_NumID.setCellValueFactory(new PropertyValueFactory<>("numID"));
        Projet_Table_col_Cours.setCellValueFactory(new PropertyValueFactory<>("coursName"));
        Projet_Table_col_Sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        Projet_Table_col_DateRemise.setCellValueFactory(new PropertyValueFactory<>("dateRemPrev"));
        Projet_Table_col_Formation.setCellValueFactory(new PropertyValueFactory<>("formation_id"));
        //On ajoute les elements au tableau
        Project.AfficherTProject(Projet_Table);
    }
    
    /** Méthode qui permet d'insérer un Projet à la BDD */
    @FXML
    private void handleInsertActionProjets() {
        // Récupération des valeurs des champs de texte
        String coursName = Projet_Tf_Cours.getText();
        String sujet = Projet_Tf_sujet.getText();
        LocalDate dateRem = Projet_date.getValue();
        String formation = Projet_Combo_forma.getValue();
        String promo = Projet_Combo_promo.getValue();
        // Vérification si les champs formation ne sont pas vides ou null
        if (coursName.trim().isEmpty()|| promo == null || promo.trim().isEmpty())  {
            showAlert(AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }
        try {
            boolean affectedRows = Project.addProject(coursName, sujet,dateRem,Formation.getFormationId(formation,promo));
            if (affectedRows ) {
                showAlert(AlertType.INFORMATION, "Succès", "Formation et Promotion insérées avec succès.");
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Aucune ligne ajoutée. Le projet pourrait déjà exister.");
            }
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de l'insertion du projet.");
            ex.printStackTrace();
        }
        refreshDataProjets();
    }
    
    /** Méthode qui va permettre de modifier un projet selectionnée du tableau */
    @FXML
    private void handleModificationActionProjets(){
        Project projetSelectionne = Projet_Table.getSelectionModel().getSelectedItem();
        if (projetSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un projet doit être selectionné.");
            return;
        }
        // Récupération des valeurs des champs TextField et ComboBox
        String newSujet = Projet_Tf_sujet.getText();
        String newCoursName = Projet_Tf_Cours.getText();
        LocalDate newDate = Projet_date.getValue();
        if (newSujet.trim().isEmpty() || newCoursName.trim().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }
        int projetID =  projetSelectionne.getNumID();
        // Application des modifications à la base de données 
        boolean updateSuccess = Project.updateProject(projetID, newCoursName, newSujet, newDate);
        //Bloc if pour voir si l'eleve a bien été modifié
        if (updateSuccess) {
            showAlert(AlertType.INFORMATION,"Succès", "Mise à jour réussie.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur", "Erreur lors de la mise à jour.");
        }
        refreshDataProjets();
    }
    
    /** Méthode qui va permettre de suppprimer un projet selectionné du tableau */
    @FXML
    private void handleSuppressionActionProjets(){
        Project projetSelectionne = Projet_Table.getSelectionModel().getSelectedItem();
        if (projetSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un projet doit être selectionné.");
            return;
        }
        // Application des modifications à la base de données 
        boolean affectedrows = Project.delProject(projetSelectionne.getCoursName(), projetSelectionne.getSujet());
        //Bloc if pour voir si l'eleve a bien été modifié
        if (affectedrows) {
            showAlert(AlertType.INFORMATION,"Suppression réussie", "Projet supprimé avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur lors de la suppression.", "Suppression échouée.");
        }
        refreshDataProjets();
    }
   
    /** Méthode qui récupère les infos d'un projet selectionné du tableau */
    public void getSelectedProjetsTable(){
        Project projetSelectionne = Projet_Table.getSelectionModel().getSelectedItem();
        if (projetSelectionne != null) {
            // Mise à jour des champs de texte avec les propriétés de l'objet Student sélectionné
            int formaID = projetSelectionne.getFormation_id();
            String [] parts= Formation.getFormaPromo(formaID);
            String formation =parts[0];
            String promo = parts[1];
            Projet_Tf_sujet.setText(projetSelectionne.getSujet());
            Projet_Tf_Cours.setText(projetSelectionne.getCoursName());
            Projet_date.setValue(projetSelectionne.getDateRemPrev());
            Projet_Combo_forma.setValue(formation);
            Projet_Combo_promo.setValue(promo);
        }
    }
    
    /** Acutalise les données de Projet */
    @FXML
    private void refreshDataProjets() {
        initializeTableProjets();
        populateComboBox(Projet_Combo_forma,Formation.RecupereFormations());
        populateComboBox(Projet_Combo_promo,PROMOTION);
        Projet_Tf_Cours.setText("");
        Projet_Tf_sujet.setText("");
        Projet_date.setValue(null);
        Projet_Combo_forma.setValue(null);
        Projet_Combo_promo.setValue(null);
        Projet_Label_PG_NbAffStat.setText((String.valueOf(Project.countProjets())));
    }

    /** Permet de mettre à jour la liste des promotions en fonction de la formation choisie */
    @FXML
    private void updatePromotionParFormationProjet(){
        String selectedForma = Projet_Combo_forma.getValue();
        ObservableList<String> promotions = FXCollections.observableArrayList(Formation.recuperePromotionsParFormation(selectedForma));
        Projet_Combo_promo.setItems(promotions);
    }

                                            
                                        /** Partie Binome  **/

    /** Méthode qui permet de initialiser la Table des Binome */
    public void initializeTableBinome() {
        //On ajoute les valeurs à chaque cellule
        Binome_table_col_numID.setCellValueFactory(new PropertyValueFactory<>("binomeID"));
        Binome_table_col_el1.setCellValueFactory(new PropertyValueFactory<>("student1Name")); 
        Binome_table_col_el2.setCellValueFactory(new PropertyValueFactory<>("student2Name")); 
        Binome_table_col_sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        Binome_table_col_DateRem.setCellValueFactory(new PropertyValueFactory<>("dateRemEff"));
        //On ajoute les elements au tableau
        Binome.AfficherTBinome(Binome_table);
    }
    
    /** Méthode qui permet d'insérer un binome à la BDD */
    @FXML
    private void handleInsertActionBinome() {
        // Récupération des valeurs des champs de texte
        Integer projectID = Project.getProjectId(Binome_Combo_Sujet.getValue());
        Integer Student1ID = Student.getStudentId(Binome_Combo_El1.getValue().split(", ")[0],Binome_Combo_El1.getValue().split(", ")[1]);
        Integer Student2ID = Student.getStudentId(Binome_Combo_El2.getValue().split(", ")[0],Binome_Combo_El2.getValue().split(", ")[1]);
        // Vérification si les champs formation
        if (Student1ID == null|| projectID == null ||Student1ID == null)  {
            showAlert(AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }
        try {
            boolean affectedRows = Binome.addBinome(Student1ID, Student2ID,projectID);
            if (affectedRows ) {
                showAlert(AlertType.INFORMATION, "Succès", "Le binôme insérées avec succès.");
            } else {
                showAlert(AlertType.ERROR, "Erreur", "Aucune ligne ajoutée. Le binôme pourrait déjà exister.");
            }
        } catch (Exception ex) {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de l'insertion du binôme.");
            ex.printStackTrace();
        }
        refreshDataBinome();
    }
    
     /**Méthode qui va permettre de modifier un binome selectionnée du tableau */
   @FXML
    private void handleModificationActionBinome(){
        Binome binomeSelectionne = Binome_table.getSelectionModel().getSelectedItem();
        if (binomeSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un binôme doit être selectionné.");
            return;
        }
        // Récupération des valeurs des champs de texte
        Integer projectID = Project.getProjectId(Binome_Combo_Sujet.getValue());
        Integer Student1ID = Student.getStudentId(Binome_Combo_El1.getValue().split(", ")[0],Binome_Combo_El1.getValue().split(", ")[1]);
        Integer Student2ID;
        if (Binome_Combo_El2.getValue()!= null){
            Student2ID = Student.getStudentId(Binome_Combo_El2.getValue().split(", ")[0],Binome_Combo_El2.getValue().split(", ")[1]);
        }else{
            Student2ID = null;
        }
        // Vérification si les champs formation
        if (Student1ID == null|| projectID == null ||Student1ID == null)  {
            showAlert(AlertType.ERROR, "Erreur", "Les champs ne doivent pas être vides.");
            return;
        }
        // Application des modifications à la base de données 
        boolean updateSuccess = Binome.updateBinome(projectID, Student1ID, Student2ID);
        //Bloc if pour voir si l'eleve a bien été modifié
        if (updateSuccess) {
            showAlert(AlertType.INFORMATION,"Succès", "Mise à jour réussie.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur", "Erreur lors de la mise à jour.");
        }
        refreshDataBinome();
    }
    
    /** Méthode qui va permettre de suppprimer un binome selectionnée du tableau */
    @FXML
    private void handleSuppressionActionBinome(){
        Binome binomeSelectionne = Binome_table.getSelectionModel().getSelectedItem();
        if (binomeSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un binôme doit être selectionné.");
            return;
        }
        // Application des modifications à la base de données ici
        boolean affectedrows = Binome.delBinome(binomeSelectionne.getProjectID(), binomeSelectionne.getStudent1ID(),binomeSelectionne.getStudent2ID());
        //Bloc if pour voir si l'eleve a bien été modifié
        if (affectedrows) {
            showAlert(AlertType.INFORMATION,"Suppression réussie", "Binome supprimé avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur lors de la suppression.", "Supression échouée.");
        }
        refreshDataBinome();
    }
    
    /** Méthode qui récupère les infos d'un Binome selectionné du tableau */
    public void getSelectedBinomeTable(){
        Binome binomeSelectionne = Binome_table.getSelectionModel().getSelectedItem();
        if (binomeSelectionne != null) {
            // Mise à jour des champs de texte avec les propriétés de l'objet Binome sélectionné
            int projetID = binomeSelectionne.getProjectID();
            int student1ID = binomeSelectionne.getStudent1ID();
            String [] fullnameBin1 = Student.getStudentName(student1ID);
            Binome_Combo_El1.setValue(fullnameBin1[0] +", " +fullnameBin1[1]);
            //Voir si le groupe est composé d'une personne ou non
            if (binomeSelectionne.getStudent2ID() != null){
                int student2ID = binomeSelectionne.getStudent2ID();
                String [] fullnameBin2 = Student.getStudentName(student2ID);
                Binome_Combo_El2.setValue(fullnameBin2[0] +", " +fullnameBin2[1]);
                Binome_CheckB_1.setSelected(false);
                Binome_CheckB_2.setSelected(true);
            }else{
                Binome_Combo_El2.setValue(null);
                Binome_CheckB_1.setSelected(true);
                Binome_CheckB_2.setSelected(false);
            }
            Binome_Combo_cours.setValue(Project.getCoursByProjet(projetID));
            Binome_Combo_Sujet.setValue(Project.getProjetsByID(projetID));
        }
    }
    
    /** Acutalise les données de Binome */
    @FXML
    private void refreshDataBinome() {
        initializeTableBinome();
        populateComboBox(Binome_Combo_cours,Project.RecupereCours());
        populateComboBox(Binome_Combo_Sujet,Project.RecupereProjet());
        populateComboBox(Binome_Combo_El1,Student.getStudentNames());
        populateComboBox(Binome_Combo_El2,Student.getStudentNames());
        Binome_Combo_El1.setValue(null);
        Binome_Combo_El2.setValue(null);
        Binome_Combo_cours.setValue(null);
        Binome_Combo_Sujet.setValue(null);
        Binome_CheckB_1.setSelected(false);
        Binome_CheckB_2.setSelected(false);
        Binome_Combo_El2.setVisible(true);
        Binome_Label_El2.setVisible(true);
        BInome_Label_Inb.setText(String.valueOf(Binome.countBinome())); 
    }
    
    /** Permet de mettre à jour la liste des sujets et des elèves en fonction du cours choisi */
    @FXML
    private void updateSujetParCoursBinome(){
        String selectedCours = Binome_Combo_cours.getValue();
        ObservableList<String> sujet = FXCollections.observableArrayList(Project.getProjetsParCours(selectedCours));
        Binome_Combo_Sujet.setItems(sujet);
        ObservableList<String> eleves = FXCollections.observableArrayList(Student.getElevesParCours(selectedCours));
        Binome_Combo_El1.setItems(eleves);
        Binome_Combo_El2.setItems(eleves);
        BInome_Label_Inb.setText((String.valueOf(Binome.countBinomeByCours(Project.getCoursByStrCours(selectedCours)))));
    }
    
    /** Méthode action qui permet de supprimer le deuxième étudiant si un étudiant a choisi de faire un projet seul
     * (Cas où l'étudiant a rempli la case du premier étudiant) */
    @FXML
    private void checkStudent1(){
        // Si le premier CheckBox est sélectionné, désélectionnez le second et ajustez la visibilité
        if (Binome_CheckB_1.isSelected()) {
            Binome_CheckB_2.setSelected(false);
            Binome_Combo_El2.setVisible(false);
            Binome_Label_El2.setVisible(false);
            Binome_Combo_El2.setValue(null);
        }else{
            Binome_Combo_El2.setVisible(true);
            Binome_Label_El2.setVisible(true);
        }
    }
    /** Méthode action qui permet de supprimer le premier étudiant si un étudiant a choisi de faire un projet seul
     * (Cas où l'étudiant a rempli la case du second étudiant) */    
    @FXML
    private void checkStudent2(){
        // Si le second CheckBox est sélectionné, désélectionnez le premier et ajustez la visibilité
        if (Binome_CheckB_2.isSelected()) {
            Binome_CheckB_1.setSelected(false);
            Binome_Combo_El2.setVisible(true);
            Binome_Label_El2.setVisible(true);
        }
    }


                                /**Partie Notes  **/

    /** Méthode qui permet de initialiser la Table des Notes */
    public void initializeTableNote() {
        //On ajoute les valeurs à chaque cellule
        Grades_Table_col_ID.setCellValueFactory(new PropertyValueFactory<>("gradesID"));
        Grades_Table_col_Nom.setCellValueFactory(new PropertyValueFactory<>("studentLastName")); 
        Grades_Table_col_Prenom.setCellValueFactory(new PropertyValueFactory<>("studentFirstName")); 
        Grades_Table_col_Cours.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        Grades_Table_col_Sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        Grades_Table_col_NR.setCellValueFactory(new PropertyValueFactory<>("gradeRapport"));
        Grades_Table_col_NS.setCellValueFactory(new PropertyValueFactory<>("gradeSoutenance")); 
        Grades_Table_col_Moy.setCellValueFactory(new PropertyValueFactory<>("average"));
        //On ajoute les elements au tableau
        Grades.AfficherTGrades(Grades_Table);
    }
    
    /*Méthode qui permet d'insérer une note à la BDD*/
    @FXML
    private void handleInsertActionNote() {
        // Récupérer les valeurs des champs de texte
        Integer projectID = Project.getProjectId(Grades_CB_Sujet.getValue());
        Double NoteRapport;
        Double NoteSoutenance;
        Integer StudentID = Student.getStudentId(Grades_Box_student.getValue().split(", ")[0],Grades_Box_student.getValue().split(", ")[1]);
        try {
            NoteRapport = Double.parseDouble(Grades_TF_GradesR.getText());
            NoteSoutenance = Double.parseDouble(Grades_TF_GradesS.getText());
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez entrer des nombres valides pour les notes.");
            return;
        }
        if (Grades_TF_GradesR.getText().isEmpty() || Grades_TF_GradesS.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Les champs de note ne doivent pas être vides.");
            return;
        }
        // Vérification si les notes sont sur 20
        if ((NoteSoutenance < 0 || NoteSoutenance > 20) || (NoteRapport < 0 || NoteRapport > 20)) {
            showAlert(AlertType.ERROR, "Erreur", "Les notes doivent être comprises entre 0 et 20.");
            return;
        }
        boolean affectedRows = Grades.addGrades(projectID, StudentID, NoteSoutenance,NoteRapport);
        if (affectedRows) {
            showAlert(AlertType.INFORMATION, "Succès", "Notes insérées avec succès.");
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Erreur lors de l'insertion des notes.");
        }
        refreshDataNote();
    }
    
    /**Méthode qui va permettre de modifier une note d'un étudiant selectionné du tableau**/
    @FXML
    private void handleModificationActionNote(){
        Grades gradeSelectionne = Grades_Table.getSelectionModel().getSelectedItem();
        if (gradeSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un elève doit être selectionné.");
            return;
        }
        // Récupérer les valeurs des champs de texte
        Integer projectID = Project.getProjectId(Grades_CB_Sujet.getValue());
        Double NoteRapport;
        Double NoteSoutenance;
        Integer StudentID = Student.getStudentId(Grades_Box_student.getValue().split(", ")[0],Grades_Box_student.getValue().split(", ")[1]);
        try {
            NoteRapport = Double.parseDouble(Grades_TF_GradesR.getText());
            NoteSoutenance = Double.parseDouble(Grades_TF_GradesS.getText());
            
        } catch (NumberFormatException e) {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez entrer des nombres valides pour les notes.");
            return;
        }
        System.out.println(NoteRapport);
        // Vérification si les champs formation
        if (Grades_TF_GradesR.getText().isEmpty() || Grades_TF_GradesS.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Les champs de note ne doivent pas être vides.");
            return;
        }
        // Appliquez les modifications à la base de données ici
        boolean updateSuccess = Grades.updateGrades(projectID, StudentID, NoteRapport,NoteSoutenance);
        //Bloc if pour voir si l'eleve a bien été modifié
        if (updateSuccess) {
            showAlert(AlertType.INFORMATION,"Succès", "Mise à jour réussie.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur", "Erreur lors de la mise à jour.");
        }
        refreshDataNote();
    }
    
    /**Méthode qui va permettre de suppprimer une note selectionnée du tableau**/
    @FXML
    private void handleSuppressionActionNote(){
        Grades gradeSelectionne = Grades_Table.getSelectionModel().getSelectedItem();
        if (gradeSelectionne == null) {
            showAlert(AlertType.ERROR, "Erreur", "Un étudiant et un projet doivent être selectionnés.");
            return;
        }
        // Récupérer les valeurs des champs de texte
        Integer projectID = Project.getProjectId(Grades_CB_Sujet.getValue());
        Integer StudentID = Student.getStudentId(gradeSelectionne.getStudentLastName(),Grades_Box_student.getValue().split(", ")[1]);
        
        // Appliquez les modifications à la base de données ici
        boolean affectedrows = Grades.deleteGradeSoutenance(projectID,StudentID);
        boolean affectedrows2 = Binome.deleteGradeRapport(projectID,StudentID); 
        //Bloc if pour voir si la note de l'élève a bien été modifiée
        if (affectedrows) {
            showAlert(AlertType.INFORMATION,"Suppression réussie", "La note de Soutenance a été supprimée avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur lors de la suppression.", "Supression note de Soutenance échouée.");
        }
        if (affectedrows2) {
            showAlert(AlertType.INFORMATION,"Suppression réussie", "La note de Rapport a été supprimée avec succès.");
        } else {
            showAlert(AlertType.INFORMATION,"Erreur lors de la suppression.", "Supression note de Rapport échouée.");
        }
        refreshDataNote();
    }
    
    /** Méthode qui récupère les infos d'une Formaiton selectionnée du tableau */
    public void getSelectedNote(){
        Grades gradeSelectionne = Grades_Table.getSelectionModel().getSelectedItem();
        if (gradeSelectionne != null) {
            //Mise à jour des champs de texte avec les propriétés de l'objet grade sélectionné
            Grades_Box_student.setValue(Student.getStudentName(gradeSelectionne.getStudentID())[0] +
            ", " +Student.getStudentName(gradeSelectionne.getStudentID())[1]);
            Grades_CB_Cours.setValue(Project.getCoursByProjet(gradeSelectionne.getProjectID()));
            Grades_CB_Sujet.setValue(Project.getProjetsByID(gradeSelectionne.getProjectID()));
            Grades_TF_GradesR.setText(String.valueOf(gradeSelectionne.getGradeRapport()));
            Grades_TF_GradesS.setText(String.valueOf(gradeSelectionne.getGradeSoutenance()));            
        }
    }
    
    /** Acutalise les données de Binome */
    @FXML
    private void refreshDataNote() {
        initializeTableNote();
        populateComboBox(Grades_CB_Cours,Project.RecupereCours());
        populateComboBox(Grades_CB_Sujet,Project.RecupereProjet());
        populateComboBox(Grades_Box_student,Student.getStudentNames());
        Grades_CB_Cours.setValue(null);
        Grades_CB_Sujet.setValue(null);
        Grades_Box_student.setValue(null);
        Grades_TF_GradesR.setText("");
        Grades_TF_GradesS.setText("");
        
    }
    
    /** Permet de mettre à jour la liste des sujets et des elèves en fonction du cours choisi */
    @FXML
    private void updateSujetAndEleveParCoursNote(){
        String selectedCours = Grades_CB_Cours.getValue();
        ObservableList<String> sujet = FXCollections.observableArrayList(Project.getProjetsParCours(selectedCours));
        Grades_CB_Sujet.setItems(sujet);
        ObservableList<String> eleves = FXCollections.observableArrayList(Student.getElevesParCours(selectedCours));
        Grades_Box_student.setItems(eleves);
        Notes_Label_PG_1.setText(String.valueOf(Grades.averageByCourse(selectedCours)));
    }
}
