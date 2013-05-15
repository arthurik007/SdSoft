/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;
/**
 *
 * @author Jorn
 */
public class Evenement 
{
    private int EvenementenID;
    private String Soort;
    private String Straat;
    private String Stad;
    private int Straatnummer;
    private String Omschrijving;
    private double Latitude;
    private double Longitude;
    private String Gebruikernaam;
    //private Gebruiker gebruiker;
    
    public Evenement()
    {
        
    }
    public Evenement(int eID ,String so , String str ,String sta , int strnr , String o ,double la , double lo ,String g/* ,Gebruiker gebruiker*/)
    {
        setEvenementenID(eID);      
        setSoort(so);
        setStraat(str);
        setStad(sta);
        setStraatnummer(strnr);
        setOmschrijving(o);
        setLatitude(la);
        setLongitude(lo);
        setGebruikernaam(g);
        //setGebruiker(gebruiker); 
        
    }

    public int getEvenementenID() {
        return EvenementenID;
    }   
    
    public String getSoort()
    {
        return Soort;
    }

    public String getStraat() {
        return Straat;
    }

    public String getStad() {
        return Stad;
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
   /* public Gebruiker getGebruiker()
    {
        return gebruiker;
    }*/
    
    /*
    public void setEvenementenID(int EvenementenID) {
       if (EvenementenID > 0) 
       {
            this.EvenementenID = EvenementenID;
       }
       else
       {
           throw new IllegalArgumentException("ID moet groter zijn dan 0");
       }
    }
    */
    public void setEvenementenID(int EvenementenID)
    {
        this.EvenementenID = EvenementenID;
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

    public void setStraat(String Straat) {
       if (Straat != null && !Straat.trim().equals(""))
       {
           this.Straat = Straat;
       }
       else
       {
           throw new IllegalArgumentException("Straat mag niet leeg zijn");
       }
    }

    public void setStad(String Stad) {
       if (Stad != null && !Stad.trim().equals(""))
       {
           this.Stad = Stad;
       }
       else
       {
           throw new IllegalArgumentException("Stad mag niet leeg zijn");
       }
    }

    public void setStraatnummer(int Straatnummer) {
       if (Straatnummer > 0) 
       {
            this.Straatnummer = Straatnummer;
       }
       else
       {
           throw new IllegalArgumentException("Straatnummer moet groter zijn dan 0");
       }
    }

    public void setOmschrijving(String Omschrijving) {
       if (Omschrijving != null && !Omschrijving.trim().equals(""))
       {
           this.Omschrijving = Omschrijving;
       }
       else
       {
           throw new IllegalArgumentException("Omschrijving mag niet leeg zijn");
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
           throw new IllegalArgumentException("Gebruikernaam mag niet leeg zijn");
       }
    }
    /*public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }*/
    
    
    
    
}
