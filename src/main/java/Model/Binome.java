package Model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import Data.DataBaseConnexion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class Binome{
            /** Attributs pour initialiser un binome **/
    private SimpleIntegerProperty binomeID;
    private SimpleIntegerProperty projectID;
    private SimpleIntegerProperty student1ID;
    private SimpleIntegerProperty student2ID;
    private SimpleObjectProperty<Double> gradeRapport;
    private SimpleObjectProperty<LocalDate> dateRemEff;

    // Propriétés supplémentaires pour les noms et sujets
    private SimpleStringProperty student1Name = new SimpleStringProperty();
    private SimpleStringProperty student2Name = new SimpleStringProperty();
    private SimpleStringProperty sujet = new SimpleStringProperty();

    /** Constructeur Binome */
    public Binome(int binomeID, int projectID, int student1ID, int student2ID, Double gradeRapport, LocalDate dateRemEff) {
        this.binomeID = new SimpleIntegerProperty(binomeID);
        this.projectID = new SimpleIntegerProperty(projectID);
        this.student1ID = new SimpleIntegerProperty(student1ID);
        this.student2ID = new SimpleIntegerProperty(student2ID);
        this.gradeRapport = new SimpleObjectProperty<>(gradeRapport);
        this.dateRemEff = new SimpleObjectProperty<>(dateRemEff);
    }
    
            /**Getters et Setters **/
    // Getters
    public Integer getBinomeID() { return binomeID.get(); }
    public Integer getProjectID() { return projectID.get(); }
    public Integer getStudent1ID() { return student1ID.get(); }
    public Integer getStudent2ID() { return student2ID.get(); }
    public Double getGradeRapport() { return gradeRapport.get(); }
    public LocalDate getDateRemEff() { return dateRemEff.get(); }
    public String getStudent1Name() {return student1Name.get();}
    public String getStudent2Name() {return student2Name.get();}
    public String getSujet() {return sujet.get();}

    // Setters
    public void setBinomeID(int binomeID) { this.binomeID.set(binomeID); }
    public void setProjectID(int projectID) { this.projectID.set(projectID); }
    public void setStudent1ID(int student1ID) { this.student1ID.set(student1ID); }
    public void setStudent2ID(int student2ID) { this.student2ID.set(student2ID); }
    public void setGradeRapport(Double gradeRapport) { this.gradeRapport.set(gradeRapport); }
    public void setDateRemEff(LocalDate dateRemEff) { this.dateRemEff.set(dateRemEff); }
    
    // Setters pour les propriétés supplémentaires
    public void setStudent1Name(String value) { student1Name.set(value);}
    public void setStudent2Name(String value) {student2Name.set(value);}
    public void setSujet(String value) {sujet.set(value);}

    /** Fonction pour intéraction avec JavaFX*/
    public SimpleIntegerProperty binomeIDProperty() { return binomeID; }
    public SimpleIntegerProperty projectIDProperty() { return projectID; }
    public SimpleIntegerProperty student1IDProperty() { return student1ID; }
    public SimpleIntegerProperty student2IDProperty() { return student2ID; }
    public SimpleObjectProperty<Double> gradeRapportProperty() { return gradeRapport; }
    public SimpleObjectProperty<LocalDate> dateRemEffProperty() { return dateRemEff; }
    
    /** Méthode d'ajout d'un nouveau binome dans la base de donnée*/
    public static boolean addBinome(int student1Id, int student2Id, int projectId) {
        String insertQuery = "INSERT INTO Binome (projectID, student1ID, student2ID) VALUES (?, ?, ?)";
        if(student2Id == student1Id){
            return false;
        }
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            
            // Définit les valeurs des paramètres
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, student1Id);
            pstmt.setInt(3, student2Id);

            // Exécute la requête d'insertion
            int affectedRows = pstmt.executeUpdate();
            
            // Vérifie si l'insertion a réussi
            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion du binôme : " + e.getMessage());
        }
        return false;
    }

    /** Méthode de suppression d'un binome de la base de données */
    public static boolean delBinome(int idProj, int s1, int s2) {
        // La requête SQL pour supprimer une formation
        String deleteQuery = "DELETE FROM Binome WHERE (projectID,student1ID,student2ID) = (?,?,?)"; 
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {
                pstmt.setInt(1, idProj); 
                pstmt.setInt(2, s1);
                pstmt.setInt(3, s2);
            int affectedRows = pstmt.executeUpdate();//ExecuteUpdate permet de changer la base de donnees sans recevoir de resultat
            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Méthode pour marquer une note de rapport d'un binome comme "NonNoté" pour un projet donné */
    public static boolean deleteGradeRapport(int projectId, int studentID1) {
        // Mettre à jour la note de rapport à NULL pour le binôme spécifié dans le projet
        String sql = "UPDATE Binome SET gradeRapport = NULL WHERE projectID = ? AND (student1ID = ? OR student2ID = ?)";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, projectId);
            pstmt.setInt(2, studentID1);
            pstmt.setInt(3, studentID1);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Méthode d'affichage de la table Binome */
    public static void AfficherTBinome(TableView<Binome> binomeTable) {
        String query = "SELECT binomeID, projectID, student1ID, student2ID, gradeRapport, dateRemEff FROM Binome";
        ObservableList<Binome> binomes = FXCollections.observableArrayList();
        try (Connection conn = DataBaseConnexion.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                LocalDate dateRemPrev = null;
                Date date = rs.getDate("dateRemEff");
                if (date != null) {
                    dateRemPrev = date.toLocalDate();
                }
                Binome binome = new Binome(
                    rs.getInt("binomeID"),
                    rs.getInt("projectID"),
                    rs.getInt("student1ID"),
                    rs.getInt("student2ID"),
                    rs.getDouble("gradeRapport"),
                    dateRemPrev
                );
                binome.setNamesAndSubjectFromIds();
                binomes.add(binome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        binomeTable.setItems(binomes);
    }
    
    /** Méthode intermédiaire pour définir les propriétés de noms à partir des identifiants */
    public void setNamesAndSubjectFromIds() {
        this.setStudent1Name(Student.getStudentName(this.getStudent1ID())[0] + ", " + Student.getStudentName(this.getStudent1ID())[1]);
        int student2Id = this.getStudent2ID();
        if (student2Id > 0) { // Supposons que les ID valides sont > 0
            this.setStudent2Name(Student.getStudentName(student2Id)[0] + ", " +Student.getStudentName(student2Id)[1]);
        } else {
            this.setStudent2Name("Non attribué");
        }
        this.setSujet(Project.getProjetsByID(this.getProjectID()));
    }

    /** Méthode qui récupère les Noms des étudiants par cours */
    public static String[] getStudentNamesByCours(String coursName) {
        ArrayList<String> namesList = new ArrayList<>();
        final String query = "SELECT DISTINCT CONCAT(lastname, ', ', firstname) AS fullName " +
                             "FROM Student s " +
                             "INNER JOIN Project p ON s.numID = p.student_id " +
                             "WHERE p.coursName = ? " +
                             "ORDER BY lastname, firstname";
    
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, coursName);
            ResultSet rs = pstmt.executeQuery();
    
            while (rs.next()) {
                String fullName = rs.getString("fullName");
                namesList.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    
        // Conversion de l'ArrayList en un tableau de String
        return namesList.toArray(new String[0]);
    }

    /** Méthode de mise à jour de la base de données */
    public static boolean updateBinome(Integer projectId, Integer student1Id , int student2Id) {
        // Vérifie que tous les identifiants ont été correctement récupérés
        if (projectId == -1 || student1Id == -1 || student2Id == -1) {
            return false; // L'un des éléments n'a pas été trouvé
        }
        String sql = "UPDATE Binome SET student1ID = ?, student2ID = ? WHERE projectID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Définit les valeurs des paramètres pour la mise à jour
            pstmt.setInt(1, student1Id);
            pstmt.setInt(2, student2Id);
            pstmt.setInt(3, projectId);
            // Exécute la requête de mise à jour
            int rowsAffected = pstmt.executeUpdate();
            // Vérifie si la mise à jour a réussi en vérifiant le nombre de lignes affectées
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /** Méthode qui compte le nombre de binome par cours dans la BDD */
    public static int countBinomeByCours(int coursID) {
        String countQuery = "SELECT COUNT(*) FROM Binome WHERE projectID IN (SELECT numID FROM Project WHERE formation_id = 1)";
        int count = 0;

        try (Connection conn = DataBaseConnexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(countQuery)) {

            pstmt.setInt(1, coursID); // Définit le coursID comme paramètre de la requête

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    /** Méthode qui permet de compter le nb de binome total */
    public static int countBinome() {
        String countQuery = "SELECT COUNT(*) FROM Binome";
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
    
    /** Méthode qui retourne les notes de Rapports */
    public static Double getGradeRapport(int projectID) {
        Double gradeRapport = null;
        String query = "SELECT AVG(gradeRapport) as averageGrade FROM Binome WHERE projectID = ?";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectID);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    gradeRapport = rs.getDouble("averageGrade");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gradeRapport;
    }

    /** Méthode qui renvoie le nombre de binome en retard */
    public static int countLateBinomes() {
        String query = "SELECT COUNT(*) AS lateCount " +
                       "FROM Binome b " +
                       "JOIN Project p ON b.projectID = p.numID " +
                       "WHERE b.dateRemEff > p.dateRemPrev";
    
        int lateBinomesCount = 0;
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
    
            if (rs.next()) {
                lateBinomesCount = rs.getInt("lateCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lateBinomesCount;
    }
}
