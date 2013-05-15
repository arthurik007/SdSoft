/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domein.Gebruiker;
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

@Stateless
@Path("gebruikers")
public class GebruikerService {
    
    @Resource(name="jdbc/projectdb")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Gebruiker> getAllGebruikers() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruikers")) {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Gebruiker> results = new ArrayList<>();
                    while (rs.next()) {
                        Gebruiker u = new Gebruiker();
                        u.setGebruikerID(rs.getInt("GebruikerID"));
                        u.setVoornaam(rs.getString("Voornaam"));
                        u.setAchternaam(rs.getString("Achternaam"));
                        u.setStraat(rs.getString("Straat"));
                        u.setGemeente(rs.getString("Gemeente"));
                        u.setHuisnummer(rs.getInt("Huisnummer"));
                        u.setPunten(rs.getInt("Punten"));
                        results.add(u);
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
    public Response addGebruiker(Gebruiker u) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT MAX(GebruikerID) FROM gebruikers")) {
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        u.setGebruikerID(rs.getInt(1) + 1);
                    } else {
                        u.setGebruikerID(1);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("INSERT INTO gebruikers VALUES(?, ? , ? , ? , ? , ? , ?)")) {
                stat.setInt(1, u.getGebruikerID());
                stat.setString(2, u.getVoornaam());
                stat.setString(3, u.getAchternaam());
                stat.setString(4, u.getStraat());
                stat.setString(5, u.getGemeente());
                stat.setInt(6, u.getHuisnummer());
                stat.setInt(7, u.getPunten());
                stat.executeUpdate();
            }
            
            return Response.created(URI.create("/" + u.getGebruikerID())).build();
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{GebruikerID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Gebruiker getGebruiker(@PathParam("GebruikerID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruikers WHERE GebruikerID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Gebruiker u = new Gebruiker();
                        u.setGebruikerID(rs.getInt("GebruikerID"));
                        u.setVoornaam(rs.getString("Voornaam"));
                        u.setAchternaam(rs.getString("Achternaam"));
                        u.setStraat(rs.getString("Straat"));
                        u.setGemeente(rs.getString("Gemeente"));
                        u.setHuisnummer(rs.getInt("Huisnummer"));
                        u.setPunten(rs.getInt("Punten"));
                        return u;
                    } else {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
     
    @Path("{GebruikerID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateGebruiker(@PathParam("GebruikerID") int id, Gebruiker u) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruikers WHERE GebruikerID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("UPDATE gebruikers SET Voornaam = ? , Achternaam = ? , Straat = ? , Gemeente = ? , Huisnummer = ? , Punten = ? WHERE GebruikerID = ?")) {
                stat.setString(1, u.getVoornaam());
                stat.setString(2, u.getAchternaam());
                stat.setString(3, u.getStraat());
                stat.setString(4, u.getGemeente());
                stat.setInt(5, u.getHuisnummer());
                stat.setInt(6, u.getPunten());
                stat.setInt(7, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{GebruikerID}")
    @DELETE
    public void removeGebruiker(@PathParam("GebruikerID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM gebruikers WHERE GebruikerID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM gebruikers WHERE GebruikerID = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    
}
