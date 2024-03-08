package Model;


import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import Data.DataBaseConnexion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;


public class Project {
            /** Attributs pour initialiser un projet **/
    private SimpleIntegerProperty numID;
    private SimpleStringProperty coursName;
    private SimpleStringProperty sujet;
    private SimpleObjectProperty<LocalDate> dateRemPrev;
    private SimpleIntegerProperty formation_id;

    /** Constructeur */
    public Project(int numID, String coursName, String sujet, LocalDate dateRemPrev, int formation_id) {
        this.numID = new SimpleIntegerProperty(numID);
        this.coursName = new SimpleStringProperty(coursName);
        this.sujet = new SimpleStringProperty(sujet);
        this.dateRemPrev = new SimpleObjectProperty<>(dateRemPrev);
        this.formation_id = new SimpleIntegerProperty(formation_id);
    }

            /**Getters et Setters **/
    // Getters
    public int getNumID() { return numID.get(); }
    public String getCoursName() { return coursName.get(); }
    public String getSujet() { return sujet.get(); }
    public LocalDate getDateRemPrev() { return dateRemPrev.get(); }
    public int getFormation_id() { return formation_id.get(); }

    // Setters
    public void setNumID(int numID) { this.numID.set(numID); }
    public void setCoursName(String coursName) { this.coursName.set(coursName); }
    public void setSujet(String sujet) { this.sujet.set(sujet); }
    public void setDateRemPrev(LocalDate dateRemPrev) { this.dateRemPrev.set(dateRemPrev); }
    public void setFormation_id(int formation_id) { this.formation_id.set(formation_id); }
    
    /**Fonction pour intéraction avec JavaFX**/
    public SimpleIntegerProperty numIDProperty() { return numID; }
    public SimpleStringProperty coursNameProperty() { return coursName; }
    public SimpleStringProperty sujetProperty() { return sujet; }
    public SimpleObjectProperty<LocalDate> dateRemPrevProperty() { return dateRemPrev; }
    public SimpleIntegerProperty formation_idProperty() { return formation_id; }


    /** Méthode qui permet de vérifier qu'un certain projet existe dans la base de données */
    private static boolean projectExists(String coursName, String sujet, LocalDate dateRemPrev) {
        String checkProject = "SELECT COUNT(*) AS count FROM project WHERE (coursName,sujet,dateRemPrev) = (?,?,?)";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkProject)) {
            pstmt.setString(1, coursName);
            pstmt.setString(2, sujet);
            if (dateRemPrev != null) {
                pstmt.setDate(3, java.sql.Date.valueOf(dateRemPrev));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }
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

    /** Méthode qui pemret d'ajouter un nouveau projet à la base de données */
    public static boolean addProject(String coursName, String sujet, LocalDate dateRemPrev, int formation_id) {
        // Requête pour ajouter un projet
        boolean affectedRows = false;
        if (projectExists(coursName, sujet, dateRemPrev)) {
            return affectedRows;
        }
        // Requête pour ajouter un projet
        String insertProject = "INSERT INTO project (coursName, sujet, dateRemPrev, formation_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertProject)) {
            pstmt.setString(1, coursName);
            pstmt.setString(2, sujet);
            if (dateRemPrev != null) {
                pstmt.setDate(3, java.sql.Date.valueOf(dateRemPrev));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE); 
            }
            pstmt.setInt(4, formation_id);
            // Exécution la requête
            affectedRows = pstmt.executeUpdate() > 0;
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }
    
    /** Méthode qui permet de supprimer un projet de la base de données */
    public static boolean delProject(String coursName, String sujet) {
        String deleteSql = "DELETE FROM Project WHERE coursName = ? AND sujet = ?";
        String checkEmptyTableSql = "SELECT COUNT(*) AS rowcount FROM Project";
        String resetAutoIncrementSql = "ALTER TABLE Project AUTO_INCREMENT = 1";
        
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
             Statement stmt = conn.createStatement()) {
            
            // Suppression du projet
            deleteStmt.setString(1, coursName);
            deleteStmt.setString(2, sujet);
            int affectedRows = deleteStmt.executeUpdate();
    
            // On vérifie que la table est bien vide
            ResultSet rs = stmt.executeQuery(checkEmptyTableSql);
            if (rs.next() && rs.getInt("rowcount") == 0) {
                // Si la table est vide, réinitialisez l'auto-incrémentation
                stmt.execute(resetAutoIncrementSql);
            }
    
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Méthode qui permet de récupérer les projets associés à un cours spécifique */
    public static String[] getSubjectsByCourse(String coursName) {
        Set<String> subjects = new LinkedHashSet<>();
        String sql = "SELECT sujet FROM Project WHERE coursName = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, coursName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    subjects.add(rs.getString("sujet"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects.toArray(new String[0]);
    }

    /** Méthode qui affiche les eleves avec un rendu en retard */
    public static void showStudentsWithLateProjects(int projID) {
        String selectLateProjects = "SELECT S.numID, S.lastname, S.firstname, P.coursName, B.dateRemEff, P.dateRemPrev, " +
                "DATEDIFF(B.dateRemEff, P.dateRemPrev) AS joursDeRetard " +
                "FROM Student S " +
                "INNER JOIN Binome B ON S.numID = B.numStud1 OR S.numID = B.numStud2 " +
                "INNER JOIN Project P ON B.numIDproj = P.numID " +
                "WHERE B.dateRemEff > P.dateRemPrev AND B.numIDproj = "+projID;

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectLateProjects);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String lastName = resultSet.getString("lastname");
                String firstName = resultSet.getString("firstname");
                int joursDeRetard = resultSet.getInt("joursDeRetard");

                System.out.println(firstName+" "+lastName+" a rendu son projet en retard de "+ joursDeRetard+ " jours.");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Méthode qui affiche les projets par cours */
    public static void showProjectsByCourse() {
        String selectProjectsByCourse = "SELECT F.formaName, F.promotion, P.coursName, B.dateRemEff " +
                "FROM Formation F " +
                "INNER JOIN Student S ON F.numID = S.formation_id " +
                "INNER JOIN Binome B ON S.numID = B.numStud1 OR S.numID = B.numStud2 " +
                "INNER JOIN Project P ON B.numIDproj = P.numID";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectProjectsByCourse);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String formaName = resultSet.getString("formaName");
                String promotion = resultSet.getString("promotion");
                String coursName = resultSet.getString("coursName");
                java.sql.Date dateRemEff = resultSet.getDate("dateRemEff");

                System.out.println("Formation: " + formaName);
                System.out.println("Promotion: " + promotion);
                System.out.println("Cours: " + coursName);
                System.out.println("Date de rendu effectif: " + dateRemEff);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Méthode qui affiche le nombre de projet par cours */ 
    public static void displayProjectCountsByCourse() {
        String selectProjectCountsByCourse = "SELECT F.formaName, F.promotion, P.coursName, COUNT(*) AS projectCount " +
                "FROM Formation F " +
                "INNER JOIN Student S ON F.numID = S.formation_id " +
                "INNER JOIN Binome B ON S.numID = B.numStud1 OR S.numID = B.numStud2 " +
                "INNER JOIN Project P ON B.numIDproj = P.numID " +
                "GROUP BY F.formaName, F.promotion, P.coursName";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectProjectCountsByCourse);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                String formaName = resultSet.getString("formaName");
                String promotion = resultSet.getString("promotion");
                String coursName = resultSet.getString("coursName");
                int projectCount = resultSet.getInt("projectCount");

                System.out.println("Il y a "+projectCount+" projets dans la matiere "+coursName);
                System.out.println("Formation: " + formaName);
                System.out.println("Promotion: " + promotion);
                System.out.println("Cours: " + coursName);
                System.out.println("Nombre de projets: " + projectCount);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Méthode qui affiche le nombre total de projets */
    public static void showTotalProjectCount() {
        String selectTotalProjectCount = "SELECT COUNT(*) AS totalProjectCount FROM Project";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectTotalProjectCount);
             ResultSet resultSet = pstmt.executeQuery()) {

            if (resultSet.next()) {
                int totalProjectCount = resultSet.getInt("totalProjectCount");

                System.out.println("Nombre total de projets : " + totalProjectCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Méthode qui permet d'afficher les moyennes personnelles et de groupe pour chaque projet */
    public static void calculateOverallAverages() {
        String selectOverallAverages = "SELECT projectID, AVG(gradeGroup) AS overallAverageGroup, AVG(gradePerso) AS overallAveragePerso " +
                "FROM Grades " +
                "GROUP BY projectID";

        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectOverallAverages);
             ResultSet resultSet = pstmt.executeQuery()) {

            while (resultSet.next()) {
                int projectID = resultSet.getInt("projectID");
                double overallAverageGroup = resultSet.getDouble("overallAverageGroup");
                double overallAveragePerso = resultSet.getDouble("overallAveragePerso");

                System.out.println("Projet ID: " + projectID);
                System.out.println("Moyenne générale de groupe: " + overallAverageGroup);
                System.out.println("Moyenne générale personnelle: " + overallAveragePerso);
                System.out.println("--------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Permet de renvoyer un tableau avec toutes les Sujets de projets sans doublons */
    public static String[] RecupereProjet() {
        // Requêtes pour récupérer le nom de toutes les formations sans doublons
        String requetes = "SELECT sujet FROM Project ORDER BY sujet";
        // On utilise un type set pour éviter qu'il y ait de duplication
        Set<String> sujet = new LinkedHashSet<>();
        try (Connection conn = DataBaseConnexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(requetes);
            ResultSet rs = pstmt.executeQuery()) {
            // Boucle sur les résultats de la requête ajout de ceux-ci au Set
            while (rs.next()) {
                sujet.add(rs.getString("sujet"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Conversion du Set en tableau de String 
        String[] sujetArray = sujet.toArray(new String[0]);
        return sujetArray;
    }
    
    /** Permet de renvoyer un tableau avec toutes les cours de projets sans doublons */
    public static String[] RecupereCours() {
        Set<String> Cours = new LinkedHashSet<>();
        final String query = "SELECT DISTINCT coursName FROM Project ORDER BY coursName";
        try (Connection conn = DataBaseConnexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Cours.add(rs.getString("coursName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
        String[] CoursArray = Cours.toArray(new String[0]);
        // Conversion de l'ArrayList en un tableau de String
        return CoursArray;
    }

    /** Méthode qui permet de récupérer tous les projets d'une certaine matière */
    public static String[] getProjetsParCours(String selectedCours) {
        Set<String> projets = new LinkedHashSet<>();
        String query = "SELECT sujet FROM Project WHERE coursName = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, selectedCours);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    projets.add(rs.getString("sujet"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] ProjetArray = projets.toArray(new String[0]);
        return ProjetArray;
    }

    /** Méthode pour récupérer l'ID d'un projet */
    public static int getProjectId(String projet) {
        String query = "SELECT numID FROM Project WHERE sujet = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, projet);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("numID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Retourne -1 si le projet n'est pas trouvé ou en cas d'erreur
    }

    /** Méthode qui permet d'afficher tous les projets dans tableModel */
    public static void AfficherTProject(TableView<Project> projetTable) {
        // Requête SQL pour récupérer les données souhaitées
        String query = "SELECT Project.numID, Project.coursName,Project.sujet,Project.dateRemPrev, Formation.numID as formation_id FROM Project JOIN Formation ON Project.formation_id = Formation.numID";
        ObservableList<Project> projet = FXCollections.observableArrayList();
        try (Connection conn = DataBaseConnexion.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            // Parcours les résultats de la requête et ajout des résultats au modèle de table
            while (rs.next()) {
                LocalDate dateRemPrev = null;
                Date date = rs.getDate("dateRemPrev");
                if (date != null) {
                    dateRemPrev = date.toLocalDate();
                }
                projet.add(new Project(
                    rs.getInt("numID"),
                    rs.getString("coursName"),
                    rs.getString("sujet"),
                    dateRemPrev,
                    rs.getInt("formation_id")
                ));
            
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        projetTable.setItems(projet);
    }

    /** Méthode pour mettre à jour la base de données */
    public static boolean updateProject(int projetID, String newCoursName, String newSujet, LocalDate newDate) {
        // Définir la requête SQL pour la mise à jour
        String sqlUpdateProject = "UPDATE Project SET coursName = ?, sujet = ?, dateRemPrev = ? WHERE numID = ?";
    
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlUpdateProject)) {
    
            // Remplissage des paramètres de la requête préparée
            pstmt.setString(1, newCoursName);
            pstmt.setString(2, newSujet);
            if (newDate != null) {
                pstmt.setDate(3, java.sql.Date.valueOf(newDate));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE); 
            }
            pstmt.setInt(4, projetID);

            // Exécution de la mise à jour
            int rowsAffected = pstmt.executeUpdate();
    
            // Vérifie du succès de la mise à jour
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Méthode qui compte le nombre de Projets dans la BDD */
    public static int countProjets() {
        String countQuery = "SELECT COUNT(*) FROM Project";
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
    
    /** Méthode qui renvoie le cours à partir du projetID */   
    public static String getCoursByProjet(int projetID) {
        final String query = "SELECT coursName FROM Project WHERE numID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projetID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("coursName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    
    /** Méthode qui renvoie le sujet à partir du projetID */   
    public static String getProjetsByID(int projetID) {
        final String query = "SELECT sujet FROM Project WHERE numID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projetID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("sujet");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
     /** Méthode qui renvoie l'ID du cours à partir du coursname */  
    public static int getCoursByStrCours(String selectedCours) {
        String query = "SELECT numID FROM Cours WHERE coursName = ?";
        int courseId = -1;
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, selectedCours); // On définit le nom du cours comme paramètre de la requête

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    courseId = rs.getInt("numID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courseId;
    }
    
    /** Méthode qui renvoie le nombre total de projet en cours */
    public static int countInProgressProjects() {
        String query = "SELECT COUNT(*) AS inProgressCount " +
                       "FROM Project " +
                       "WHERE dateRemPrev > ?";
        int inProgressProjectsCount = 0;
        LocalDate today = LocalDate.now();
    
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setDate(1, java.sql.Date.valueOf(today));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    inProgressProjectsCount = rs.getInt("inProgressCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inProgressProjectsCount;
    }
}



    

