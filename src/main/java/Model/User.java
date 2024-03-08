package Model;

import java.sql.*;

import java.time.LocalDate;

import Data.DataBaseConnexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleObjectProperty;

public class User {
            /**Attributs de la classe user**/
    private SimpleStringProperty coursName ;
    private SimpleStringProperty sujet ;
    private SimpleObjectProperty<Double> gradeRapport = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Double> gradeSoutenance = new SimpleObjectProperty<>();
    private SimpleObjectProperty<Double> average = new SimpleObjectProperty<>();
    private SimpleStringProperty status = new SimpleStringProperty();
    private static String nom;
    private static String prenom;
    private static Integer StudentID;
    
            /**Getters et Setters **/
    // Getters 
    public String getCoursName() { return coursName.get(); }
    public String getSujet() { return sujet.get(); }
    public Double getGradeRapport() { return gradeRapport.get(); }
    public Double getGradeSoutenance() { return gradeSoutenance.get(); }
    public Double getAverage() { return average.get(); }
    public String getStatus() { return status.get(); }

    //Setters
    public void setCoursName(String coursName) { this.coursName.set(coursName); }
    public void setSujet(String sujet) { this.sujet.set(sujet); }
    public void setGradeRapport(Double gradeRapport) { this.gradeRapport.set(gradeRapport); }
    public void setGradeSoutenance(Double gradeSoutenance) { this.gradeSoutenance.set(gradeSoutenance); }
    public void setAverage(Double average) { this.average.set(average); }
    public void setStatus(String status) { this.status.set(status); }

            /** Fonctions pour intéraction avec JavaFX **/
    public SimpleObjectProperty<Double> averageProperty() { return average; }
    public SimpleObjectProperty<Double> gradeSoutenanceProperty() { return gradeSoutenance; }
    public SimpleObjectProperty<Double> gradeRapportProperty() { return gradeRapport; }
    public SimpleStringProperty sujetProperty() { return sujet; }
    public SimpleStringProperty coursNameProperty() { return coursName; }
    public SimpleStringProperty statusProperty() { return status; }

            /** Fonctions pour connaitre l'utilisateur connecté **/
    public static String getNom() {return nom;}
    public static String getPrenom() {return prenom; }
    
    /** Constructeur */
    public User(String coursName, String sujet, Double gradeRapport, Double gradeSoutenance, Double average, String status) {
        this.coursName = new SimpleStringProperty(coursName);
        this.sujet=  new SimpleStringProperty(sujet);
        this.gradeRapport =  new SimpleObjectProperty<>(gradeRapport);
        this.gradeSoutenance = new SimpleObjectProperty<>(gradeSoutenance) ;
        this.average= new SimpleObjectProperty<>(average);
        this.status=  new SimpleStringProperty(status);
    }

    /*Méthode pour initialiser l'élève */
    public static void setCurrentUser(String nom, String prenom) {
        User.nom = nom;
        User.prenom = prenom;
        User.StudentID = Student.getStudentId(User.nom, User.prenom);
    }
    
    /** Méthode permettant d'afficher tous les cours,sujets, notesrapport et notesSoutenance et lme status du projet dans le tableau */
    public static void AfficherTUser(TableView<User> userTable) {
        ObservableList<User> users = FXCollections.observableArrayList();
        String requete = "SELECT p.coursName, p.sujet, b.gradeRapport, g.gradeSoutenance, g.average, " +
                         "CASE WHEN b.dateRemEff > p.dateRemPrev THEN 'Retard' ELSE 'Rendu' END as status " +
                         "FROM Project p " +
                         "JOIN Binome b ON p.numID = b.projectID " +
                         "JOIN Grades g ON p.numID = g.projectID " +
                         "WHERE b.student1ID = ? OR b.student2ID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(requete)) {
            pstmt.setInt(1, User.StudentID);
            pstmt.setInt(2, StudentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                users.add(new User(
                    rs.getString("coursName"),
                    rs.getString("sujet"),
                    rs.getDouble("gradeRapport"),
                    rs.getDouble("gradeSoutenance"),
                    rs.getDouble("average"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userTable.setItems(users);
    }
    
    /** Méthode qui permet d'ajouter la date de remise effective du binome */
    public static boolean Remise( String coursName, String sujet, LocalDate dateRem) {
        // Requête pour trouver le projectID basé sur coursName, sujet et studentID
        String findProjectIdQuery = "SELECT p.numID FROM Project p " +
                                    "JOIN Binome b ON p.numID = b.projectID " +
                                    "WHERE p.coursName = ? AND p.sujet = ? AND (b.student1ID = ? OR b.student2ID = ?)";

        // Requête pour mettre à jour la date de remise effective dans la table Binome
        String updateBinomeQuery = "UPDATE Binome SET dateRemEff = ? WHERE projectID = ? AND (student1ID = ? OR student2ID = ?)";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement findStmt = conn.prepareStatement(findProjectIdQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateBinomeQuery)) {

            // Paramètres pour trouver le projectID
            findStmt.setString(1, coursName);
            findStmt.setString(2, sujet);
            findStmt.setInt(3, StudentID);
            findStmt.setInt(4, StudentID);
            ResultSet rs = findStmt.executeQuery();

            // Vérification si le projet a été trouvé
            if (rs.next()) {
                int projectID = rs.getInt("numID");
                // Mise à jour de la date de remise effective pour l'étudiant et son binôme
                updateStmt.setDate(1, java.sql.Date.valueOf(dateRem));
                updateStmt.setInt(2, projectID);
                updateStmt.setInt(3, StudentID);
                updateStmt.setInt(4, StudentID);
                int affectedRows = updateStmt.executeUpdate();
                return affectedRows>0;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Méthode qui permet d'afficher le cours,sujet,noteRapport,NooteSoutenance et la moyenne de l'élmève utilisateur */
    public static void AfficherTUserNote(TableView<User> noteTable) {
        String requete = "SELECT p.coursName, p.sujet, b.gradeRapport, g.gradeSoutenance, g.average " +
                         "FROM Project p " +
                         "JOIN Binome b ON p.numID = b.projectID " +
                         "JOIN Grades g ON p.numID = g.projectID " +
                         "WHERE b.student1ID = ? OR b.student2ID = ?";
        ObservableList<User> notes = FXCollections.observableArrayList();
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(requete)) {
            pstmt.setInt(1, StudentID);
            pstmt.setInt(2, StudentID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                notes.add(new User(
                    rs.getString("coursName"),
                    rs.getString("sujet"),
                    rs.getDouble("gradeRapport"),
                    rs.getDouble("gradeSoutenance"),
                    rs.getDouble("average"),
                    null 
                ));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        noteTable.setItems(notes);
    }

}
