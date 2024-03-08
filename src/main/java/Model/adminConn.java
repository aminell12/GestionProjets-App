package Model;

import Data.DataBaseConnexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class adminConn {
    public adminConn(){
        throw new AssertionError("Cette classe ne doit pas être instanciée.");
    }
    /*Méthode pour prendre en compte la connexion de l'utilisateur */
    public static boolean logAdmin(String username, String password){
        if (username == null || password == null){
            return false;   
        }
        String sql = "SELECT * FROM Student WHERE lastname = ? AND firstname = ?";
        try (Connection conn = DataBaseConnexion.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
    
            // Définir les valeurs des paramètres
            pstmt.setString(1, username);
            pstmt.setString(2, password);
    
            // Exécuter la requête
            ResultSet rs = pstmt.executeQuery();
    
            // Vérifiez si un enregistrement a été trouvé
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
