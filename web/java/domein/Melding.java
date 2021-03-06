/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;
/**
 *
 * @author Jorn
 */
public class Melding 
{
    private int MeldingID;
    private String Stad;
    private String Straat;
    private int Straatnummer;
    private String Omschrijving;
    private String Soort;
    private double Latitude;
    private double Longitude;
    private String Gebruikernaam;
    //private Gebruiker gebruiker;
    
    public Melding()
    {
        
    }
    public Melding(int ID ,String so, String straat , String stad , int straatnummer , String omschrijving ,double la , double lo , String g /*, Gebruiker gebruiker*/)
    {
        setMeldingID(ID);
        setSoort(so);
        setStraat(straat);
        setStad(stad);      
        setStraatnummer(straatnummer);        
        setOmschrijving(omschrijving);
        setLatitude(la);
        setLongitude(lo);
        setGebruikernaam(g);
        //setGebruiker(gebruiker);
    }
    
    public String getSoort() {
        return Soort;
    }

    public int getMeldingID() {
        return MeldingID;
    }

    public String getStad() {
        return Stad;
    }

    public String getStraat() {
        return Straat;
    }

    public int getStraatnummer() {
        return Straatnummer;
    }

    public String getOmschrijving() {
        return Omschrijving;
    }
    
    public double getLatitude()
    {
        return Latitude;
    }
    
    public double getLongitude()
    {
        return Longitude;
    }

    public String getGebruikernaam()
    {
        return Gebruikernaam;
    }
    /*public Gebruiker getGebruiker()
    {
        return gebruiker;
    }*/
    /*
    public void setMeldingID(int MeldingID) 
    {
       if (MeldingID > 0) 
       {
            this.MeldingID = MeldingID;
       }
       else
       {
           throw new IllegalArgumentException("ID moet groter zijn dan 0");
       }
       
    }
    */
    public void setMeldingID(int MeldingID)
    {
        this.MeldingID = MeldingID;
    }
    public void setStad(String Stad) 
    {
       if (Stad != null && !Stad.trim().equals(""))
       {
           this.Stad = Stad;
       }
       else
       {
           throw new IllegalArgumentException("Stad mag niet leeg zijn");
       }
           
    }

    public void setStraat(String Straat)
    {
       if (Straat != null && !Straat.trim().equals(""))
       {
           this.Straat = Straat;
       }
       else
       {
           throw new IllegalArgumentException("Straat mag niet leeg zijn");
       }
    }

    public void setStraatnummer(int Straatnummer) 
    {
        if (Straatnummer > 0)
        {
            this.Straatnummer = Straatnummer;
        }
        else
        {
            throw new IllegalArgumentException("Straatnummer moet groter zijn dan 0");
        }
    }

    public void setOmschrijving(String Omschrijving) 
    {
       if (Omschrijving != null && !Omschrijving.trim().equals(""))
       {
           this.Omschrijving = Omschrijving;
       }
       else
       {
           throw new IllegalArgumentException("Omschrijving mag niet leeg zijn");
       }
    }
    
    
    public void setSoort(String Soort) {
       if (Soort != null && !Soort.trim().equals(""))
       {
           this.Soort = Soort;
       }
       else
       {
           throw new IllegalArgumentException("Soort mag niet leeg zijn");
       }
    }
    
    public void setLatitude(double Latitude)
    {
        this.Latitude = Latitude;
    }
    
    public void setLongitude(double Longitude)
    {
        this.Longitude = Longitude;
    }
    
    public void setGebruikernaam(String Gebruikernaam)
    {
       if (Gebruikernaam != null && !Gebruikernaam.trim().equals(""))
       {
           this.Gebruikernaam = Gebruikernaam;
       }
       else
       {
           throw new IllegalArgumentException("Omschrijving mag niet leeg zijn");
       }
    }
    /*public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }*/
    
    

}