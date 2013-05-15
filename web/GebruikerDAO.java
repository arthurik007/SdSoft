/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */


//import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GebruikerDAO {
    
    private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/projectdb?user=root&password=jornie";
    
  
    public boolean addGebruiker(Gebruiker p) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO gebruikers VALUES (?,?,?,?,?,?,?,?,?)");
            stat.setInt(1, p.getGebruikerId());
            stat.setString(2, p.getNaam());
            stat.setString(3, p.getAchternaam());
            stat.setString(4, p.getStraat());
            stat.setString(5, p.getGemeente());
            stat.setInt(6,p.getHuisnummer());
            stat.setInt(7,p.getPunten());            
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    
   
    public boolean addGebruiker(Gebruiker... gebruikers) {
        boolean success = true;
        for (Gebruiker p : gebruikers) {
            success = success && addGebruiker(p);
        }
        return success;
    }
    
    
    public Gebruiker getGebruiker(int id) {
        Gebruiker p = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM gebruikers WHERE GebruikerID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int ID = rs.getInt("GebruikerID");
                    String stad = rs.getString("Stad");
                    String achternaam = rs.getString("Achternaam");
                    String straat = rs.getString("Straat");
                    String gemeente = rs.getString("Gemeente");
                    int huisnummer = rs.getInt("Huisnummer");
                    int punten = rs.getInt("Punten");
                                   
                  
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return p;
    }
    
    
    public List<Gebruiker> getGebruikers(int... ids) {
        List<Gebruiker> gebruikers = new ArrayList<>();
        
        for (int id : ids) {
            Gebruiker p = getGebruiker(id);
            if (p != null) {
                gebruikers.add(p);
            }
        }
        
        return gebruikers;
    }
    
   
    public List<Gebruiker> getAllGebruikers() {
        List<Gebruiker> gebruikers = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruikers");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int ID = rs.getInt("GebruikerID");
                    String stad = rs.getString("Stad");
                    String achternaam = rs.getString("Achternaam");
                    String straat = rs.getString("Straat");
                    String gemeente = rs.getString("Gemeente");
                    int huisnummer = rs.getInt("Huisnummer");
                    int punten = rs.getInt("Punten");
                    
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return gebruikers;
    }
    
  
    public boolean updateGebruiker(Gebruiker p) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat =
                    conn.prepareStatement("UPDATE gebruikers SET GebruikerID = ?, Stad = ? , Achternaam = ? , Straat = ?, Gemeente = ? , Huisnummer = ? , Punten = ? WHERE GebruikerID = ?");
            stat.setInt(1, p.getGebruikerId());
            stat.setString(2, p.getNaam());
            stat.setString(3, p.getAchternaam());
            stat.setString(4, p.getStraat());
            stat.setString(5, p.getGemeente());
            stat.setInt(6,p.getHuisnummer());
            stat.setInt(7,p.getPunten());       
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    
   
    public boolean updateGebruikers(Gebruiker... gebruikers) {
        boolean success = true;
        for (Gebruiker p : gebruikers) {
            success = success && updateGebruiker(p);
        }
        return success;
    }
    
  
    public boolean deleteGebruiker(Gebruiker p) {
        return deleteGebruiker(p.getGebruikerId());
    }
    
    public boolean deleteGebruiker(int id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM gebruikers WHERE GebruikerID = ?");
            stat.setInt(1, id);
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    
    
    public boolean deleteGebruikers(Gebruiker... gebruikers) {
        boolean success = true;
        for (Gebruiker p : gebruikers) {
            success = success && deleteGebruiker(p.getGebruikerId());
        }
        return success;
    }
    
  
    public boolean deleteGebruikers(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteGebruiker(id);
        }
        return success;
    }
    
    
}

