package Model;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

import Data.DataBaseConnexion;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class Formation {
            /** Attributs pour initialiser une formation **/
    private SimpleIntegerProperty numID;
    private SimpleStringProperty formaName;
    private SimpleStringProperty promotion;

            /**Getters et Setters **/
    // Getters
    public int getNumID() {return numID.get();}
    public String getFormaName() {return formaName.get();}
    public String getPromotion() {return promotion.get();}
    
    // Setters
    public void setNumID(int numID) {this.numID.set(numID);}
    public void setFormaName(String formaName) {this.formaName.set(formaName);}
    public void setPromotion(String promotion) {this.promotion.set(promotion);}
    
            /**Fonction pour intéraction avec JavaFX**/
    public SimpleIntegerProperty numIDProperty() {return numID;}
    public SimpleStringProperty formaNameProperty() {return formaName;}
    public SimpleStringProperty promotionProperty() {return promotion;}
    
    /**Constructeur*/
    public Formation(int numID, String formaName, String promotion){
        this.numID = new SimpleIntegerProperty(numID);
        this.formaName = new SimpleStringProperty(formaName);
        this.promotion = new SimpleStringProperty(promotion);
    }; 
    
    /** Méthode qui permet de renvoyer un tableau avec toutes les formations sans doublons */
    public static String[] RecupereFormations() {
        // Requêtes pour récupérer le nom de toutes les formations sans doublons
        String requetes = "SELECT DISTINCT formaName FROM Formation";
        // On utilise un type set pour éviter qu'il y ait de duplication
        Set<String> formations = new LinkedHashSet<>();
        try (Connection conn = DataBaseConnexion.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(requetes);
            ResultSet rs = pstmt.executeQuery()) {
            // Boucle sur les résultats de la requête qui ajoute les formations au Set
            while (rs.next()) {
                formations.add(rs.getString("formaName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Conversion du Set en tableau de String 
        String[] formationsArray = formations.toArray(new String[0]);
        return formationsArray;
    }

    /** Permet de renvoyer un tableau avec toutes les promotions sans doublons */
    public static String[] RecuperePromotion() {
        // Requête pour récupérer les promotions uniques
        String requete = "SELECT DISTINCT promotion FROM Formation";
        // On utilise un type set pour éviter qu'il y ait de duplication
        Set<String> promotions = new LinkedHashSet<>();
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(requete);
             ResultSet rs = pstmt.executeQuery()) {
            
            // Boucle sur les résultats de la requête qui ajoute les promotions au Set
            while (rs.next()) {
                promotions.add(rs.getString("promotion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Conversion du Set en tableau de String
        String[] promotionsArray = promotions.toArray(new String[0]);
        return promotionsArray;
    }
    
    /** Méthode qui permet de récupérer les différentes promotion par formation */
    public static String[] recuperePromotionsParFormation(String formaName) {
        Set<String> promotions = new LinkedHashSet<>();
        String query = "SELECT promotion FROM Formation WHERE formaName = ?";
        try (Connection conn = DataBaseConnexion.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, formaName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    promotions.add(rs.getString("promotion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return promotions.toArray(new String[0]);
    }

    /** Méthode qui permet de vérifier qu'une certaine Formation existe dans la Base de Donnée */
    private static boolean formationExists(String formaName, String promotion) {
        String checkFormation = "SELECT COUNT(*) AS count FROM Formation WHERE formaName = ? AND promotion = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkFormation)) {
            pstmt.setString(1, formaName);
            pstmt.setString(2, promotion);
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

    /** Méthode qui ajoute une formation de la BDD */
    public static int addFormation(String formaName, String promotion) {
        // Si la formation n'existe pas, l'ajouter
        String insertFormation = "INSERT INTO Formation (formaName, promotion) VALUES (?, ?)";
        int affectedRows = -1;
        if (formationExists(formaName, promotion)) {
            return affectedRows;
        }
        // Vérifier d'abord si la formation existe déjà
        if (formationExists(formaName, promotion)) {
            
            return affectedRows;
        }
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertFormation)) {
            pstmt.setString(1, formaName);
            pstmt.setString(2, promotion);
            affectedRows= pstmt.executeUpdate();
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affectedRows;
    }

    /** Méthode qui supprime une formation de la BDD */
    public static boolean delFormation(String formaName, String promotion) {
        // La requête SQL pour supprimer une formation
        String deleteQuery = "DELETE FROM Formation WHERE formaName = ? AND promotion = ?";
        // La requête pour vérifier si la table est vide
        String checkTableQuery = "SELECT COUNT(*) FROM Formation";
        // La requête pour réinitialiser l'auto-incrémentation si la table est vide
        String resetAutoIncrement = "ALTER TABLE Formation AUTO_INCREMENT = 1";
        int affectedRows = -1;
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
             Statement checkStmt = conn.createStatement()) {
            pstmt.setString(1, formaName);
            pstmt.setString(2, promotion);
            affectedRows = pstmt.executeUpdate();
            // On vérifie si la table Formation est maintenant vide afin de remettre à 1 l'auto incrémentation
            ResultSet rs = checkStmt.executeQuery(checkTableQuery);
            if (rs.next() && rs.getInt(1) == 0) {
                // Si la table est vide, On réinitialise l'auto-incrémentation
                checkStmt.executeUpdate(resetAutoIncrement);
            }
            return affectedRows>0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /** Méthode qui compte le nombre de formations dans la BDD */
    public static int countFormations() {
        String countQuery = "SELECT COUNT(*) FROM Formation";
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

    /** Méthode qui montre les eleves de chaque formation */
    public static void showAllStudent(String Nomformation){
        String afficheQuery = "SELECT S.numID, S.lastname, S.firstname, F.formaName, F.promotion FROM Student S INNER JOIN Formation F ON S.formation_id = F.numID WHERE F.formaName = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(afficheQuery)) {
                pstmt.setString(1, Nomformation); // Le nom de la formation doit etre selectionné d'une liste déroulante, on n'a donc pas besoin de vérifier d'elle existe 
                try (ResultSet rs = pstmt.executeQuery()) {
                    //Permet de voir s'il y a des élèves ou non dans la formation 
                    boolean found = false;
                    while (rs.next()) {
                        found = true;
                        int studentNumID = rs.getInt("numID");
                        String lastName = rs.getString("lastname");
                        String firstName = rs.getString("firstname");
                        String promotion = rs.getString("promotion");
                        System.out.println("ID: " + studentNumID + ", Nom: " + lastName + ", Prénom: " + firstName + ", Formation: " + Nomformation + ", Promotion: " + promotion);
                    }
                    if (!found) {
                        System.out.println("Aucun étudiant trouvé pour la formation " + Nomformation); 
                    }
                } 
        }catch (SQLException e) {
        e.printStackTrace();
        }
    }
    
    /** Méthode qui permet de renvoyer La formation et Promotion à partir de l'id */
    public static String[] getFormaPromo(int formationID) {
        String query = "SELECT formaName, promotion FROM Formation WHERE numID = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setInt(1, formationID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String formaName = rs.getString("formaName");
                    String promotion = rs.getString("promotion");
                    return new String[] { formaName, promotion };
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si la formation n'est pas trouvée ou en cas d'erreur
    }

    /** Méthode renvoyant le nombre d'élève par formation */
    public void showNbStudent() throws Exception{
        String NbQuery = "SELECT F.formaName, F.promotion,COUNT(S.numID) AS NumberOfStudents FROM Formation F LEFT JOIN Student S ON F.numID = S.formation_id GROUP BY F.formaName, F.promotion";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(NbQuery);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String formaName = rs.getString("formaName");
                String promotion = rs.getString("promotion");
                int numberOfStudents = rs.getInt("NumberOfStudents");
                System.out.println("Formation: " + formaName + " - Promotion: " + promotion + " - Nombre d'étudiants: " + numberOfStudents);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }       
    
    /** Méthode permettant de réucpérer l'ID d'une formation */
    public static int getFormationId(String formationName, String promo) {
        // La requête pour obtenir l'ID basé sur le nom de la formation
        String query = "SELECT numID FROM Formation WHERE (formaName, promotion) = (?,?)";
        int formationId = -1; // on met une valeur par défaut si l'id est pas trouvé
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, formationName);
            pstmt.setString(2,promo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    formationId = rs.getInt("numID");
                } else {
                    
                }
            }
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return formationId;
    }

    /** Méthode permettant de mettre à jour la table de formation
     * Elle renvoie true si la mise à jour a pu se faire, false sinon */
    public static boolean updateFormationPromotion(int formationId, String newFormation, String newPromotion) {
        boolean updateSuccess = false;    
        // La requête pour mettre à jour la formation et la promotion
        String updateSql = "UPDATE Formation SET formaName = ?, promotion = ? WHERE numID = ?";
    
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            pstmt.setString(1, newFormation);
            pstmt.setString(2, newPromotion);
            pstmt.setInt(3, formationId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows>0){
                return updateSuccess = true;
            } else{
                return updateSuccess;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateSuccess;
    }

    /** Méthode qui permet d'afficher l'ensemble des formations dans tableModel */
    public static void AfficherTFormation(TableView<Formation> formationTable) {
        // Requête SQL pour récupérer les données souhaitées
        String query = "SELECT numID ,formaName, promotion FROM Formation";
        ObservableList<Formation> formation = FXCollections.observableArrayList();

        try (Connection conn = DataBaseConnexion.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            // Parcours des résultats de la requête et ajout de celles-ci au modèle de table
            while (rs.next()) {
                formation.add(new Formation(
                    rs.getInt("numID"),
                    rs.getString("formaName"),
                    rs.getString("promotion")
                ));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        formationTable.setItems(formation);
    }
}


