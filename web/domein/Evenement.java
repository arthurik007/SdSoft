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
    private String Straat;
    private String Stad;
    private int Straatnummer;
    private String Omschrijving;
    private Gebruiker gebruiker;
    
    public Evenement()
    {
        
    }
    public Evenement(int eID ,String str ,String sta , int strnr , String o , Gebruiker gebruiker)
    {
        setEvenementenID(eID);      
        setStraat(str);
        setStad(sta);
        setStraatnummer(strnr);
        setOmschrijving(o);
        setGebruiker(gebruiker);      
    }

    public int getEvenementenID() {
        return EvenementenID;
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
    
    public Gebruiker getGebruiker()
    {
        return gebruiker;
    }
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
    
    public void setGebruiker(Gebruiker gebruiker)
    {
        this.gebruiker = gebruiker;
    }
    
    
    
    
}
