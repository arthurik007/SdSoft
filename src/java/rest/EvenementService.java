/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domein.Gebruiker;
import domein.Evenement;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jorn
 */
/*
@Stateless
@Path("evenementen")
public class EvenementService 
{
    @Resource(name = "jdbc/projectdb")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
      public List<Evenement> getAllEvenementen() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen INNER JOIN gebruikers ON evenementen.GebruikerID = gebruikers.GebruikerID")) {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Evenement> results = new ArrayList<>();
                    while (rs.next()) {
                        Evenement m = new Evenement();
                        m.setEvenementenID(rs.getInt("evenementen.EvenementenID"));
                        m.setSoort(rs.getString("Soort"));
                        m.setStraat(rs.getString("Straat"));
                        m.setStad(rs.getString("Stad"));
                        m.setStraatnummer(rs.getInt("Straatnummer"));
                        m.setOmschrijving(rs.getString("Omschrijving"));
                        m.setLatitude(rs.getDouble("Latitude"));
                        m.setLongitude(rs.getDouble("Longitude"));
                        
                        Gebruiker u = new Gebruiker();
                        u.setGebruikerID(rs.getInt("gebruikers.GebruikerID"));
                        u.setVoornaam(rs.getString("Voornaam"));
                        u.setAchternaam(rs.getString("Achternaam"));
                        u.setStraat(rs.getString("Straat"));
                        u.setGemeente(rs.getString("Gemeente"));
                        u.setHuisnummer(rs.getInt("Huisnummer"));
                        u.setPunten(rs.getInt("Punten"));
                        m.setGebruiker(u);
                        
                       
                        
                        results.add(m);
                    }
                    return results;
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvenement(Evenement m) {
        try (Connection conn = source.getConnection()) {
            
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een evenement moet een gebruiker hebben.").build());
            }
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT MAX(EvenementenID) FROM evenementen")) {
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        m.setEvenementenID(rs.getInt(1) + 1);
                    } else {
                        m.setEvenementenID(1);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("INSERT INTO evenementen VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                stat.setInt(1, m.getEvenementenID());
                stat.setString(2, m.getSoort());
                stat.setString(3, m.getStraat());
                stat.setString(4, m.getStad());
                stat.setInt(5, m.getStraatnummer());
                stat.setString(6, m.getOmschrijving());               
                stat.setDouble(7, m.getLatitude());
                stat.setDouble(8, m.getLongitude());
                stat.setInt(9, m.getGebruiker().getGebruikerID());
                
                
                stat.executeUpdate();
            }
            
            return Response.created(URI.create("/" + m.getEvenementenID())).build();
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{EvenementenID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Evenement getEvenement(@PathParam("EvenementenID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen INNER JOIN gebruikers ON evenementen.GebruikerID = gebruikers.GebruikerID WHERE evenementen.EvenementenID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Evenement m = new Evenement();
                        m.setEvenementenID(rs.getInt("evenementen.EvenementenID"));
                        m.setSoort(rs.getString("Soort"));
                        m.setStraat(rs.getString("Straat"));
                        m.setStad(rs.getString("Stad"));
                        m.setStraatnummer(rs.getInt("Straatnummer"));
                        m.setOmschrijving(rs.getString("Omschrijving"));
                        m.setLatitude(rs.getDouble("Latitude"));
                        m.setLongitude(rs.getDouble("Longitude"));
                        
                        Gebruiker u = new Gebruiker();
                        u.setGebruikerID(rs.getInt("gebruikers.GebruikerID"));
                        u.setVoornaam(rs.getString("Voornaam"));
                        u.setAchternaam(rs.getString("Achternaam"));
                        u.setStraat(rs.getString("Straat"));
                        u.setGemeente(rs.getString("Gemeente"));
                        u.setHuisnummer(rs.getInt("Huisnummer"));
                        u.setPunten(rs.getInt("Punten"));
                        m.setGebruiker(u);
                        
                        return m;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{EvenementenID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEvenement(@PathParam("EvenementenID") int id, Evenement m) {
        try (Connection conn = source.getConnection()) {
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een evenement moet een gebruiker hebben.").build());
            }
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("UPDATE evenementen SET Soort = ? ,Straat = ?, Stad = ?, Straatnummer = ?, Omschrijving = ?, Latitude = ? , Longitude = ? , GebruikerID =  ? WHERE EvenementenID = ?")) {
                stat.setString(1, m.getSoort());
                stat.setString(2, m.getStraat());
                stat.setString(3, m.getStad());
                stat.setInt(4, m.getStraatnummer());
                stat.setString(5, m.getOmschrijving());
                stat.setDouble(6, m.getLatitude());
                stat.setDouble(7, m.getLongitude());
                stat.setInt(8, m.getGebruiker().getGebruikerID());
                stat.setInt(9, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{EvenementenID}")
    @DELETE
    public void removeEvenement(@PathParam("EvenementenID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
*/

@Stateless
@Path("evenementen")
public class EvenementService 
{
    @Resource(name = "jdbc/projectdb")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
      public List<Evenement> getAllEvenementen() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen")) {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Evenement> results = new ArrayList<>();
                    while (rs.next()) {
                        Evenement m = new Evenement();
                        m.setEvenementenID(rs.getInt("evenementen.EvenementenID"));
                        m.setSoort(rs.getString("Soort"));
                        m.setStraat(rs.getString("Straat"));
                        m.setStad(rs.getString("Stad"));
                        m.setStraatnummer(rs.getInt("Straatnummer"));
                        m.setOmschrijving(rs.getString("Omschrijving"));
                        m.setLatitude(rs.getDouble("Latitude"));
                        m.setLongitude(rs.getDouble("Longitude"));
                        m.setGebruikernaam(rs.getNString("Gebruikernaam"));
                        
                       
                        
                        results.add(m);
                    }
                    return results;
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
     @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addEvenement(Evenement m) {
        try (Connection conn = source.getConnection()) {
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT MAX(EvenementenID) FROM evenementen")) {
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        m.setEvenementenID(rs.getInt(1) + 1);
                    } else {
                        m.setEvenementenID(1);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("INSERT INTO evenementen VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                stat.setInt(1, m.getEvenementenID());
                stat.setString(2, m.getSoort());
                stat.setString(3, m.getStraat());
                stat.setString(4, m.getStad());
                stat.setInt(5, m.getStraatnummer());
                stat.setString(6, m.getOmschrijving());               
                stat.setDouble(7, m.getLatitude());
                stat.setDouble(8, m.getLongitude());
                stat.setString(9, m.getGebruikernaam());
                
                
                stat.executeUpdate();
            }
            
            return Response.created(URI.create("/" + m.getEvenementenID())).build();
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
     
    @Path("{EvenementenID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Evenement getEvenement(@PathParam("EvenementenID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Evenement m = new Evenement();
                        m.setEvenementenID(rs.getInt("evenementen.EvenementenID"));
                        m.setSoort(rs.getString("Soort"));
                        m.setStraat(rs.getString("Straat"));
                        m.setStad(rs.getString("Stad"));
                        m.setStraatnummer(rs.getInt("Straatnummer"));
                        m.setOmschrijving(rs.getString("Omschrijving"));
                        m.setLatitude(rs.getDouble("Latitude"));
                        m.setLongitude(rs.getDouble("Longitude"));
                        m.setGebruikernaam(rs.getString("Gebruikernaam"));
                        return m;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
     
    @Path("{EvenementenID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateEvenement(@PathParam("EvenementenID") int id, Evenement m) {
        try (Connection conn = source.getConnection()) {
            
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("UPDATE evenementen SET Soort = ? ,Straat = ?, Stad = ?, Straatnummer = ?, Omschrijving = ?, Latitude = ? , Longitude = ? , Gebruikernaam = ? WHERE EvenementenID = ?")) {
                stat.setString(1, m.getSoort());
                stat.setString(2, m.getStraat());
                stat.setString(3, m.getStad());
                stat.setInt(4, m.getStraatnummer());
                stat.setString(5, m.getOmschrijving());
                stat.setDouble(6, m.getLatitude());
                stat.setDouble(7, m.getLongitude());
                stat.setString(8, m.getGebruikernaam());
                stat.setInt(9, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{EvenementenID}")
    @DELETE
    public void removeEvenement(@PathParam("EvenementenID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM evenementen WHERE EvenementenID = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
}