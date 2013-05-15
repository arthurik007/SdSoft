/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domein.Melding;
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

/**
 *
 * @author Jorn
 */

@Stateless
@Path("meldingen")
public class MeldingService 
{
    @Resource(name = "jdbc/projectdb")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
     public List<Melding> getAllMeldingen() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM meldingen INNER JOIN gebruikers ON meldingen.GebruikerID = gebruikers.GebruikerID")) {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Melding> results = new ArrayList<>();
                    while (rs.next()) {
                        Melding m = new Melding();
                        m.setMeldingID(rs.getInt("meldingen.MeldingID"));
                        m.setSoort(rs.getString("Soort"));
                        m.setBeperking(rs.getString("Beperking"));
                        m.setStraat(rs.getString("Straat"));
                        m.setStad(rs.getString("Stad"));
                        m.setStraatnummer(rs.getInt("Straatnummer"));
                        m.setOmschrijving(rs.getString("Omschrijving"));
                        
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
    public Response addMelding(Melding m) {
        try (Connection conn = source.getConnection()) {
            
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een melding moet een gebruiker hebben.").build());
            }
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT MAX(MeldingID) FROM meldingen")) {
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        m.setMeldingID(rs.getInt(1) + 1);
                    } else {
                        m.setMeldingID(1);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("INSERT INTO TBL_MESSAGE VALUES(?, ?, ?, ?, ?, ?, ?, ?)")) {
                stat.setInt(1, m.getMeldingID());
                stat.setString(2, m.getSoort());
                stat.setString(3, m.getBeperking());
                stat.setString(4, m.getStraat());
                stat.setString(5, m.getStad());
                stat.setInt(6, m.getStraatnummer());
                stat.setString(7, m.getOmschrijving());
                stat.setInt(8, m.getGebruiker().getGebruikerID());
                stat.executeUpdate();
            }
            
            return Response.created(URI.create("/" + m.getMeldingID())).build();
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{MeldingID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Melding getMelding(@PathParam("MeldingID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM meldingen INNER JOIN gebruikers ON meldingen.GebruikerID = gebruikers.GebruikerID WHERE meldingen.MeldingID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Melding m = new Melding();
                        m.setMeldingID(rs.getInt("meldingen.MeldingID"));
                        m.setSoort(rs.getString("Soort"));
                        m.setBeperking(rs.getString("Beperking"));
                        m.setStraat(rs.getString("Straat"));
                        m.setStad(rs.getString("Stad"));
                        m.setStraatnummer(rs.getInt("Straatnummer"));
                        m.setOmschrijving(rs.getString("Omschrijving"));
                        
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
    
    @Path("{MeldingID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateMelding(@PathParam("MeldingID") int id, Melding m) {
        try (Connection conn = source.getConnection()) {
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een melding moet een gebruiker hebben.").build());
            }
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM meldingen WHERE MeldingID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("UPDATE meldingen SET Soort = ?, Beperking = ?, Straat = ?, Stad = ?, Straatnummer = ?, Omschrijving = ?, GebruikerID = ? WHERE MeldingID = ?")) {
                stat.setString(1, m.getSoort());
                stat.setString(2, m.getBeperking());
                stat.setString(3, m.getStraat());
                stat.setString(4, m.getStad());
                stat.setInt(5, m.getStraatnummer());
                stat.setString(6, m.getOmschrijving());
                stat.setInt(7, m.getGebruiker().getGebruikerID());
                stat.setInt(6, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{MeldingID}")
    @DELETE
    public void removeMelding(@PathParam("MeldingID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM meldingen WHERE MeldingID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM meldingen WHERE MeldingID = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
