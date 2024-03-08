package Model;


import java.sql.*;

import java.util.*;
import Data.DataBaseConnexion;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;


public class Student  {
            /**Attributs pour initialiser un étudiant **/
    private SimpleIntegerProperty numID;
    private SimpleStringProperty lastname;
    private SimpleStringProperty firstname;
    private SimpleStringProperty formaName;
    private SimpleStringProperty promotion;
    

            /**Getters et Setters **/
    // Getters
    public int getNumID() {return numID.get();}
    public String getLastname() {return lastname.get();}
    public String getFirstname() {return firstname.get();}
    public String getFormaName() {return formaName.get();}
    public String getPromotion() {return promotion.get();}
    
    // Setters
    public void setNumID(int numID) {this.numID.set(numID);}
    public void setLastname(String lastname) {this.lastname.set(lastname);}
    public void setFirstname(String firstname) {this.firstname.set(firstname);}
    public void setFormaName(String formaName) {this.formaName.set(formaName);}
    public void setPromotion(String promotion) {this.promotion.set(promotion);}
    
            /**Fonction pour intéraction avec JavaFX**/
    public SimpleIntegerProperty numIDProperty() {return numID;}
    public SimpleStringProperty lastnameProperty() {return lastname;}
    public SimpleStringProperty firstnameProperty() {return firstname;}
    public SimpleStringProperty formaNameProperty() {return formaName;}
    public SimpleStringProperty promotionProperty() {return promotion;}
    
    /** Constructeur */
    public Student(int numID, String lastname, String firstname, String formaName, String promotion) {
        this.numID = new SimpleIntegerProperty(numID);
        this.lastname = new SimpleStringProperty(lastname);
        this.firstname = new SimpleStringProperty(firstname);
        this.formaName = new SimpleStringProperty(formaName);
        this.promotion = new SimpleStringProperty(promotion);
    }
    
    /** Méthode permettant d'afficher tous les étudiants avec les formations qu'ils suivent 
     * dans tableModel */
    public static void AfficherTStudent(TableView<Student> studentTable) {
        String requete = "SELECT Student.numID, Student.lastname, Student.firstname, Formation.formaName, Formation.promotion FROM Student JOIN Formation ON Student.formation_id = Formation.numID";
        ObservableList<Student> students = FXCollections.observableArrayList();
        try (Connection conn = DataBaseConnexion.getConnection(); // Assurez-vous que DataBaseConnexion est correctement configurée
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(requete)) {

            while (rs.next()) {
                students.add(new Student(
                    rs.getInt("numID"),
                    rs.getString("lastname"),
                    rs.getString("firstname"),
                    rs.getString("formaName"),
                    rs.getString("promotion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        studentTable.setItems(students);
    }
    
    /** Méthode qui modifie un étudiant deja dans la base */
    public static int modifyStudent(int studentId, String newName, String newFirstName, String newFormation, String newPromotion) {
        // basée sur son nom, et similairement pour la promotion
        int formationId = Formation.getFormationId(newFormation,newPromotion);
        int affectedRows = -1;
        final String updateQuery = "UPDATE Student SET lastname = ?, firstname = ?, formation_id = ? WHERE numID = ?";
        // Vérifiez si les IDs de formation et promotion sont valides
        if (formationId == -1) {
            return affectedRows;
        }
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newFirstName);
            pstmt.setInt(3, formationId);
            pstmt.setInt(4, studentId);
            affectedRows = pstmt.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
    
    /** Méthode qui compte le nombre de formations dans la BDD */
    public static int countStudent() {
        String countQuery = "SELECT COUNT(*) FROM Student";
        int count = 0;

        try (Connection conn = DataBaseConnexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(countQuery);
            ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /** Méthode qui verifie l'existence d'un certain étudiant */ 
    public static boolean studentExists(String lastname, String firstname) {
        String checkStudent = "SELECT COUNT(*) AS count FROM Student WHERE lastname = ? AND firstname = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkStudent)) {
            pstmt.setString(1, lastname);
            pstmt.setString(2, firstname);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Méthode qui permet d'insérer un élève dans la table Student */
    public static int addStudent(String lastname, String firstname, int formation_id) {
        int affectedRows = -1;
        if (studentExists(lastname, firstname)) {
            return affectedRows;
        }
        String insertStudent = "INSERT INTO Student (lastname, firstname, formation_id) VALUES (?,?,?)";
        //Pour vérifier si l'étudiant a bien été supprimé
        
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertStudent)) {
            pstmt.setString(1, lastname);
            pstmt.setString(2, firstname);
            pstmt.setInt(3, formation_id);
            affectedRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
    
    /**Méthode qui permet de supprimer un étudiant de la table Student */
    public static int deleteStudent(int studentId) {
        String deleteStudent = "DELETE FROM Student WHERE numID = ?";
        String checkTableQuery = "SELECT COUNT(*) FROM Student";//Pour avoir le nb d'etudiant
        String resetAutoIncrement = "ALTER TABLE Student AUTO_INCREMENT = 1";
        String sqlResetAutoIncrement = "ALTER TABLE Grades AUTO_INCREMENT = 1"; 
        //Pour vérifier si l'étudiant a bien été supprimé
        int affectedRows = -1;
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteStudent);
             Statement checkStmt = conn.createStatement()) {
            pstmt.setInt(1, studentId);
            affectedRows = pstmt.executeUpdate();
            // On vérifie si la table Student est maintenant vide afin de remettre à 1 l'auto incrémentation
            ResultSet rs = checkStmt.executeQuery(checkTableQuery);
            if (rs.next() && rs.getInt(1) == 0) {
                // Si la table est vide, on réinitialise l'auto-incrémentation
                checkStmt.executeUpdate(resetAutoIncrement);
                checkStmt.executeUpdate(sqlResetAutoIncrement);
            }
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /** Méthode qui permet de récupérer l'ensemble des noms des étudiants */
    public static String[] getStudentNames() {
        ArrayList<String> namesList = new ArrayList<>();
        final String query = "SELECT lastname, firstname FROM Student ORDER BY lastname, firstname";
        
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String fullName = rs.getString("lastname") + ", " + rs.getString("firstname");
                namesList.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }

        // Conversion de l'ArrayList en un tableau de String
        return namesList.toArray(new String[0]);
    }

    /** Méthode permettant d'obtenir la formation d'un certain étudiant */
    public static String getCurrentFormation(int studentId) {
        final String query = "SELECT f.formaName FROM Formation f " +
                             "JOIN Student s ON f.numID = s.formation_id " +
                             "WHERE s.numID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("formaName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // On retourne null si la formation n'est pas trouvée ou en cas d'erreur
    }
   
    /** Méthode permettant d'obtenir la promotion d'un certain étudiant */
    public static String getCurrentPromotion(int studentId) {
        final String query = "SELECT f.promotion FROM Formation f " +
                             "JOIN Student s ON f.numID = s.formation_id " +
                             "WHERE s.numID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("promotion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // On retourne null si la promotion n'est pas trouvée ou en cas d'erreur
    }

    /** Méthode permettant de récuperer les eleves par cours */
    public static String[] getElevesParCours(String selectedCours) {
        List<String> eleves = new ArrayList<>();
        String query = "SELECT DISTINCT Student.lastname, Student.firstname FROM Student "
        + "JOIN Formation ON Student.formation_id = Formation.numID "
        + "JOIN Project ON Formation.numID = Project.formation_id "
        + "LEFT JOIN Grades ON Student.numID = Grades.studentID AND Project.numID = Grades.projectID "
        + "WHERE Project.coursName = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, selectedCours);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String fullName = rs.getString("lastname") + ", " + rs.getString("firstname");
                    eleves.add(fullName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eleves.toArray(new String[0]);
    }

    /** Méthode pour obtenir l'ID de l'étudiant basé sur son prénom et son nom 
     * On retourne -1 si les entrées sont vides ou nulles ou si l'étudiants n'est pas trouvé (ou autre erreur) */
    public static int getStudentId(String lastName, String firstName) {
        if (lastName == null || firstName == null || lastName.trim().isEmpty() || firstName.trim().isEmpty()) {
            return -1;
        }

        final String query = "SELECT numID FROM Student WHERE LOWER(lastname) = LOWER(?) AND LOWER(firstname) = LOWER(?)";
    
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, lastName.trim());
            pstmt.setString(2, firstName.trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("numID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }

    /** Méthode pour obtenir le nom de l'étudiant basé sur son ID */
    public static String[] getStudentName(int studentId) {
        final String query = "SELECT firstname, lastname FROM Student WHERE numID = ?";
        String[] name = new String[2]; 
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, studentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    name[0] = rs.getString("lastname"); // Nom
                    name[1] = rs.getString("firstname");  // Prénom
                    return name;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
}