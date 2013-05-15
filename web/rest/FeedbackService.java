/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import domein.Feedback;
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
@Path("feedback")
public class FeedbackService 
{
    @Resource(name = "jdbc/projectdb")
    private DataSource source;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Feedback> getAllFeedback() {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback INNER JOIN gebruikers ON feedback.GebruikerID = gebruiker.GebruikerID")) {
                try (ResultSet rs = stat.executeQuery()) {
                    List<Feedback> results = new ArrayList<>();
                    while (rs.next()) {
                        Feedback m = new Feedback();
                        m.setFeedbackID(rs.getInt("feedback.FeedbackID"));
                        m.setFeedback(rs.getString("Feedback"));
                        m.setOnderwerp(rs.getString("Onderwerp"));
                        
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
    public Response addFeedback(Feedback m) {
        try (Connection conn = source.getConnection()) {
            
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een feedback moet een gebruiker hebben.").build());
            }
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT MAX(FeedbackID) FROM feedback")) {
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        m.setFeedbackID(rs.getInt(1) + 1);
                    } else {
                        m.setFeedbackID(1);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("INSERT INTO feedback VALUES(?, ?, ?, ?)")) {
                stat.setInt(1, m.getFeedbackID());
                stat.setString(2, m.getFeedback());
                stat.setString(3, m.getOnderwerp());              
                stat.setInt(4, m.getGebruiker().getGebruikerID());
                stat.executeUpdate();
            }
            
            return Response.created(URI.create("/" + m.getFeedbackID())).build();
            
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{FeedbackID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Feedback getFeedback(@PathParam("FeedbackID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback INNER JOIN gebruiker ON feedback.GebruikerID = gebruikers.GebruikerID WHERE feedback.FeedbackID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        Feedback m = new Feedback();
                        m.setFeedbackID(rs.getInt("feedback.FeedbackID"));
                        m.setFeedback(rs.getString("Feedback"));
                        m.setOnderwerp(rs.getString("Onderwerp"));
                        
                        Gebruiker u = new Gebruiker();
                        u.setGebruikerID(rs.getInt("gebruikers.GebruikerID"));
                        u.setVoornaam(rs.getString("Voornaam"));
                        u.setAchternaam(rs.getString("Achternaam"));
                        u.setStraat(rs.getString("Straat"));
                        u.setGemeente(rs.getString("Gemeente"));
                        u.setHuisnummer(rs.getInt("Huisnummer"));
                        u.setPunten(rs.getInt("Punten"));
                        m.setGebruiker(u);
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
    
    @Path("{FeedbackID}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateFeedback(@PathParam("FeedbackID") int id, Feedback m) {
        try (Connection conn = source.getConnection()) {
            if (m.getGebruiker() == null) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Een feedback moet een gebruiker hebben.").build());
            }
            
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE FeedbackID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("UPDATE feedback SET Feedback = ?, Onderwerp = ?, GebruikerID =  ? WHERE FeedbackID = ?")) {
                stat.setString(1, m.getFeedback());
                stat.setString(2, m.getOnderwerp());               
                stat.setInt(3, m.getGebruiker().getGebruikerID());
                stat.setInt(4, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
    
    @Path("{FeedbackID}")
    @DELETE
    public void removeFeedback(@PathParam("FeedbackID") int id) {
        try (Connection conn = source.getConnection()) {
            try (PreparedStatement stat = conn.prepareStatement("SELECT * FROM feedback WHERE FeedbackID = ?")) {
                stat.setInt(1, id);
                try (ResultSet rs = stat.executeQuery()) {
                    if (!rs.next()) {
                        throw new WebApplicationException(Response.Status.NOT_FOUND);
                    }
                }
            }
            
            try (PreparedStatement stat = conn.prepareStatement("DELETE FROM feedback WHERE FeedbackID = ?")) {
                stat.setInt(1, id);
                stat.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
