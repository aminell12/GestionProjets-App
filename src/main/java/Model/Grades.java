package Model;

import java.sql.*;
import Data.DataBaseConnexion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class Grades{
            /** Attributs pour intiialiser une note **/
    private SimpleIntegerProperty gradesID;
    private SimpleIntegerProperty projectID;
    private SimpleIntegerProperty studentID;
    private SimpleObjectProperty<Double> gradeSoutenance;
    private SimpleObjectProperty<Double> average;

    // Propriétés supplémentaires pour les noms, prénoms, cours et sujet
    private SimpleStringProperty studentLastName = new SimpleStringProperty();
    private SimpleStringProperty studentFirstName = new SimpleStringProperty();
    private SimpleStringProperty courseName = new SimpleStringProperty();
    private SimpleStringProperty sujet = new SimpleStringProperty();
    private SimpleObjectProperty<Double> gradeRapport = new SimpleObjectProperty<Double>();

    /**Constructeur*/
    public Grades(int gradesID, int projectID, int studentID, Double gradeSoutenance, Double average) {
        this.gradesID = new SimpleIntegerProperty(gradesID);
        this.projectID = new SimpleIntegerProperty(projectID);
        this.studentID = new SimpleIntegerProperty(studentID);
        this.gradeSoutenance = new SimpleObjectProperty<>(gradeSoutenance);
        this.average = new SimpleObjectProperty<>(average);
    }
    /** Méthode pour définir les attributs supplémentaires*/
    public void setStudentAndProjectDetails(String lastName, String firstName, String course, String project, Double gradeRapport) {
        this.studentLastName.set(lastName);
        this.studentFirstName.set(firstName);
        this.courseName.set(course);
        this.sujet.set(project);
        this.gradeRapport.set(gradeRapport);
    }

            /**Getters et Setters **/
    // Getters 
    public int getGradesID() { return gradesID.get(); }
    public int getProjectID() { return projectID.get(); }
    public int getStudentID() { return studentID.get(); }
    public Double getGradeSoutenance() { return gradeSoutenance.get(); }
    public Double getAverage() { return average.get(); }
    public String getStudentLastName() { return studentLastName.get(); }
    public String getStudentFirstName() { return studentFirstName.get(); }
    public String getCourseName() { return courseName.get(); }
    public String getSujet() { return sujet.get(); }
    public Double getGradeRapport() { return gradeRapport.get(); }

    // Setters
    public void setGradesID(int value) { gradesID.set(value); }
    public void setProjectID(int value) { projectID.set(value); }
    public void setStudentID(int value) { studentID.set(value); }
    public void setGradeSoutenance(Double value) { gradeSoutenance.set(value); }
    public void setAverage(Double value) { average.set(value); }
    public void setStudentLastName(String name) { studentLastName.set(name); }
    public void setStudentFirstName(String name) { studentFirstName.set(name); }
    public void setCourseName(String name) { courseName.set(name); }
    public void setSujet(String name) { sujet.set(name); }
    public void setGradeRapport(Double value) { gradeRapport.set(value); }
    

    /** Fonction pour intéraction avec JavaFX */
    public SimpleIntegerProperty gradesIDProperty() { return gradesID; }
    public SimpleIntegerProperty projectIDProperty() { return projectID; }
    public SimpleIntegerProperty studentIDProperty() { return studentID; }
    public SimpleObjectProperty<Double> gradeSoutenanceProperty() { return gradeSoutenance; }
    public SimpleObjectProperty<Double> averageProperty() { return average; }
    public SimpleStringProperty sujetProperty() {return sujet;}
    public SimpleObjectProperty<Double> gradeRapportProperty() { return gradeRapport; }
    
    /** Méthode d'ajout d'une nouvelle note à un étudiant dans la base de données */
    public static boolean addGrades(int projectID, int studentID, double gradeSoutenance, double gradeRapport) {
        // Requête pour obtenir la date de remise prévue et effective
        String dateQuery = "SELECT p.dateRemPrev, b.dateRemEff FROM Project p JOIN Binome b ON p.numID = b.projectID WHERE p.numID = ?";
        // Insertion/mise à jour des notes dans la base de données
        String insertGradesQuery = "INSERT INTO Grades (projectID, studentID, gradeSoutenance, average) VALUES (?, ?, ?, ?)";
        String insertBinomeQuery = "INSERT INTO Binome (projectID, student1ID, gradeRapport) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE gradeRapport = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmtDate = conn.prepareStatement(dateQuery);
             PreparedStatement pstmtGrades = conn.prepareStatement(insertGradesQuery);
             PreparedStatement pstmtBinome = conn.prepareStatement(insertBinomeQuery)) {
    
            // Vérifier si le projet est en retard
            pstmtDate.setInt(1, projectID);
            ResultSet rsDate = pstmtDate.executeQuery();
            if (rsDate.next()) {
                Date dateRemPrev = rsDate.getDate("dateRemPrev");
                Date dateRemEff = rsDate.getDate("dateRemEff");
                // Si la date de remise effective est après la date prévue, ajuster la note de rapport
                if (dateRemEff != null && dateRemPrev != null && dateRemEff.after(dateRemPrev)) {
                    gradeRapport -= 0.5;
                }
            }
            // Insertion dans la table Grades
            pstmtGrades.setInt(1, projectID);
            pstmtGrades.setInt(2, studentID);
            pstmtGrades.setDouble(3, gradeSoutenance);
            pstmtGrades.setDouble(4, (gradeSoutenance + Math.max(gradeRapport, 0)) / 2); // S'assurer que la note n'est pas négative
            int gradesAffected = pstmtGrades.executeUpdate();
            // Insertion/mise à jour dans la table Binome
            pstmtBinome.setInt(1, projectID);
            pstmtBinome.setInt(2, studentID);
            pstmtBinome.setDouble(3, Math.max(gradeRapport, 0)); // S'assurer que la note n'est pas négative
            pstmtBinome.setDouble(4, Math.max(gradeRapport, 0)); 
            int binomeAffected = pstmtBinome.executeUpdate();
            return gradesAffected > 0 && binomeAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Méthode pour afficher toutes les notes dans le DefaultTableModel */
    public static void AfficherTGrades(TableView<Grades> gradesTable) {
        // ObservableList pour stocker les objets Grades
        ObservableList<Grades> gradesList = FXCollections.observableArrayList();
        // Requête SQL pour obtenir les gradesID, studentID, projectID, gradeSoutenance et average
        String query = "SELECT g.gradesID, g.studentID, g.projectID, g.gradeSoutenance, g.average FROM Grades g";
        // Exécution de la requête et remplissage de la liste
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Grades grade = new Grades(
                    rs.getInt("gradesID"), 
                    rs.getInt("projectID"), 
                    rs.getInt("studentID"), 
                    rs.getDouble("gradeSoutenance"), 
                    rs.getDouble("average")
                );
                grade.setNamesAndSubjectFromIds();
                gradesList.add(grade); 
            }  
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        // Mise à jour des données de la TableView
        gradesTable.setItems(gradesList);
    }
    
    /** Méthode dans la classe Grades pour définir les noms et le sujet à partir des ID */
    public void setNamesAndSubjectFromIds() {
        String[] studentName = Student.getStudentName(this.getStudentID());
        this.setStudentLastName(studentName[0]);
        this.setStudentFirstName(studentName[1]);
        this.setCourseName(Project.getCoursByProjet(this.getProjectID()));
        this.setSujet(Project.getProjetsByID(this.getProjectID()));
        this.setGradeRapport(Binome.getGradeRapport(this.getProjectID()));
    }
    
    /** Méthode pour marquer une note de soutenance d'un étudiant comme "NonNoté" pour un projet donné */
    public static boolean deleteGradeSoutenance(int projectId, int studentId) {
        
        String sql = "UPDATE Grades SET gradeSoutenance = NULL WHERE projectID = ? AND studentID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, studentId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Méthode mettant à jour la table de Grades dans la base de données
     * Elle renvoie true si la mise à jour a pu s'effectuer, false sinon */
    public static boolean updateGrades(int projectId, int studentId, Double newNoteRapport, Double newNoteSoutenance) {
        String updateGradesQuery = "UPDATE Grades SET gradeSoutenance = ?, average = ? WHERE projectID = ? AND studentID = ?";
        String updateBinomeQuery = "UPDATE Binome SET gradeRapport = ? WHERE projectID = ? AND (student1ID = ? OR student2ID = ?)";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmtGrades = conn.prepareStatement(updateGradesQuery);
             PreparedStatement pstmtBinome = conn.prepareStatement(updateBinomeQuery)) {
            // Mise à jour dans la table Grades
            pstmtGrades.setDouble(1, newNoteSoutenance);
            pstmtGrades.setDouble(2, (newNoteRapport + newNoteSoutenance) / 2);
            pstmtGrades.setInt(3, projectId);
            pstmtGrades.setInt(4, studentId);
            int gradesAffected = pstmtGrades.executeUpdate();
            // Mise à jour dans la table Binome
            pstmtBinome.setDouble(1, newNoteRapport);
            pstmtBinome.setInt(2, projectId);
            pstmtBinome.setInt(3, studentId);
            pstmtBinome.setInt(4, studentId);
            int binomeAffected = pstmtBinome.executeUpdate();
            return gradesAffected > 0 && binomeAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Méthode qui renvoie la moyenne par cours */
    public static Double averageByCourse(String coursName) {
        String sql = "SELECT AVG(g.average) as courseAverage FROM Grades g " +
                     "JOIN Project p ON g.projectID = p.numID " +
                     "WHERE p.coursName = ?";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, coursName);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("courseAverage");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}