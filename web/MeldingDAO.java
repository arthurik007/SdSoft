
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MeldingDAO {
    
     private static final String JDBC_URL_MYSQL = "jdbc:mysql://localhost:3306/projectdb?user=root&password=jornie";
    
  
    public boolean addMelding(Melding p) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("INSERT INTO meldingen VALUES (?,?,?,?,?,?,?,?)");
            stat.setInt(1, p.getMeldingID());
            stat.setString(6, p.getStad());
            stat.setString(5, p.getStraat());
            stat.setInt(7, p.getStraatnummer());
            stat.setInt(2, p.getGebruikerID());
            stat.setString(8, p.getOmschrijving());
            stat.setString(3, p.getSoort());
            stat.setString(4, p.getBeperking());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    
   
    public boolean addMeldingen(Melding... meldingen) {
        boolean success = true;
        for (Melding p : meldingen) {
            success = success && addMelding(p);
        }
        return success;
    }
    
    
    public Melding getMelding(int id) {
        Melding p = null;
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT FROM meldingen WHERE MeldingID = ?");
            stat.setInt(1, id);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    int meldingID = rs.getInt("MeldingID");
                    int gebruikerID = rs.getInt("GebruikerID");
                    String soort = rs.getString("Soort");
                    String beperking = rs.getString("Beperking");
                    String straat = rs.getString("Straat"); 
                    String stad = rs.getString("Stad");
                    int straatnummer = rs.getInt("Straatnummer");
                    String omschrijving = rs.getString("Omschrijving");
                      
                    p = new Melding(meldingID,gebruikerID,soort,beperking,straat,stad,straatnummer,omschrijving);
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return p;
    }
    
    
    public List<Melding> getMeldingen(int... ids) {
        List<Melding> meldingen = new ArrayList<>();
        
        for (int id : ids) {
            Melding p = getMelding(id);
            if (p != null) {
                meldingen.add(p);
            }
        }
        
        return meldingen;
    }
    

    public List<Melding> getAllMeldingen() {
        List<Melding> meldingen = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("SELECT * FROM meldingen");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    int meldingID = rs.getInt("MeldingID");
                    int gebruikerID = rs.getInt("GebruikerID");
                    String soort = rs.getString("Soort");
                    String beperking = rs.getString("Beperking");
                    String straat = rs.getString("Straat"); 
                    String stad = rs.getString("Stad");
                    int straatnummer = rs.getInt("Straatnummer");
                    String omschrijving = rs.getString("Omschrijving");
                    
                    meldingen.add(new Melding(meldingID,gebruikerID,soort,beperking,straat,stad,straatnummer,omschrijving));
                }
            }
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
        }
        
        return meldingen;
    }
    
   
    public boolean updateMelding(Melding p) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("UPDATE PRODUCT SET GebruikerID = ?, Soort = ? , Beperking = ? , Straat = ? , Stad = ? , Straatnummer = ? , Omschrijving = ? WHERE MeldingID = ?");
            stat.setInt(1, p.getMeldingID());
            stat.setInt(2, p.getGebruikerID());
            stat.setString(3, p.getSoort());
            stat.setString(4, p.getBeperking());
            stat.setString(5, p.getStraat());
            stat.setString(6, p.getStad());
            stat.setInt(7, p.getStraatnummer());
            stat.setString(8, p.getOmschrijving());
            stat.executeUpdate();
            return true;
        } catch (SQLException ex) {
            for (Throwable t : ex) {
                t.printStackTrace();
            }
            return false;
        }
    }
    
    
    public boolean updateMeldingen(Melding... meldingen) {
        boolean success = true;
        for (Melding p : meldingen) {
            success = success && updateMelding(p);
        }
        return success;
    }
    
   
    public boolean deleteMelding(Melding p) {
        return deleteMelding(p.getMeldingID());
    }
    
    
    public boolean deleteMelding(int id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL_MYSQL)) {
            PreparedStatement stat = conn.prepareStatement("DELETE FROM meldingen WHERE MeldingID = ?");
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
    
   
    public boolean deleteMeldingen(Melding... meldingen) {
        boolean success = true;
        for (Melding p : meldingen) {
            success = success && deleteMelding(p.getMeldingID());
        }
        return success;
    }
    
    
    public boolean deleteMeldingen(int... ids) {
        boolean success = true;
        for (int id : ids) {
            success = success && deleteMelding(id);
        }
        return success;
    }
}
