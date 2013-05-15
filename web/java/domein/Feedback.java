/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author Jorn
 */
public class Feedback 
{
    private int feedbackID;
    private String feedback;
    private String onderwerp;
    private Gebruiker gebruiker;
    
    public Feedback()
    {
        
    }
    
    public Feedback(int fID , String f , String o , Gebruiker g)
    {
        setFeedbackID(fID);
        setFeedback(f);
        setOnderwerp(o);
        setGebruiker(g);
    }

    public int getFeedbackID() 
    {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) 
    {
        this.feedbackID = feedbackID;
    }

    public String getFeedback() 
    {
        return feedback;
    }

    public void setFeedback(String feedback) 
    {
       if (feedback != null && !feedback.trim().equals(""))
       {
           this.feedback = feedback;
       }
       else
       {
           throw new IllegalArgumentException("Feedback mag niet leeg zijn");
       }
    }

    public String getOnderwerp() 
    {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) 
    {
       if (onderwerp != null && !onderwerp.trim().equals(""))
       {
           this.onderwerp = onderwerp;
       }
       else
       {
           throw new IllegalArgumentException("Onderwerp mag niet leeg zijn");
       }
    }

    public Gebruiker getGebruiker() 
    {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) 
    {
        this.gebruiker = gebruiker;
    }
    
    
}
