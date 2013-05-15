/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package domein;
import java.util.ArrayList;

/**
 *
 * @author LodeVL
 */
public class Gebruiker 
{
    private int GebruikerID;
    private String voornaam;
    private String achternaam;
    private String straat;
    private String gemeente;
    private int huisnummer;
    private int punten;
    
        public Gebruiker()
        {
            
        }
        public Gebruiker(int ID, String voornaam, String achternaam, String straat, String gemeente, int huisnummer, int punten)
        {
            setGebruikerID(ID);
            setVoornaam(voornaam);
            setAchternaam(achternaam);
            setStraat(straat);
            setGemeente(gemeente);
            setHuisnummer(huisnummer);
            setPunten(punten);
           
            
        }
        /*
        public void setGebruikerID(int gebruikerID)
        {
            if (GebruikerID > 0)
            {
                this.GebruikerID = gebruikerID;
            }
            else
            {
                throw new IllegalArgumentException("ID moet groter zijn dan 0");
            }
        }
        */
        
        public void setGebruikerID(int gebruikerID)
        {
            this.GebruikerID = gebruikerID;
        }
        public int getGebruikerID()
        {
            return GebruikerID;
        }
        
	public String getVoornaam() 
        {
		return voornaam;
	}
	public void setVoornaam(String voornaam) 
        {
            if (voornaam != null && !voornaam.trim().equals(""))
            {
                this.voornaam = voornaam;
            }
            else
            {
                throw new IllegalArgumentException("Naam mag niet leeg zijn");
            }
	}
	public String getAchternaam() 
        {
		return achternaam;
	}
	public void setAchternaam(String achternaam) 
        {
	    if (achternaam != null && !achternaam.trim().equals(""))
            {
                this.achternaam = achternaam;
            }
            else
            {
                throw new IllegalArgumentException("Achternaam mag niet leeg zijn");
            }
	}
	public String getStraat()
        {
		return straat;
	}
	public void setStraat(String straat) 
        {
            if (straat != null && !straat.trim().equals(""))
            {
                this.straat = straat;
            }
            else
            {
                throw new IllegalArgumentException("Straat mag niet leeg zijn");
            }
	}
	public String getGemeente() 
        {
		return gemeente;
	}
	public void setGemeente(String gemeente) 
        {
            if (gemeente != null && !gemeente.trim().equals(""))
            {
                this.gemeente = gemeente;
            }
            else
            {
                throw new IllegalArgumentException("Gemeente mag niet leeg zijn");
            }
	}
	public int getHuisnummer()
        {
		return huisnummer;
	}
	public void setHuisnummer(int huisnummer) 
        {
	    if (huisnummer > 0)
            {
                this.huisnummer = huisnummer;
            }
            else
            {
                throw new IllegalArgumentException("Huisnummer moet groter zijn dan 0");
            }
	}
	public int getPunten() 
        {
		return punten;
	}
	public void setPunten(int punten) 
        {
            if (punten > 0)
            {
                this.punten = punten;
            }
            else
            {
                throw new IllegalArgumentException("Punten moet groter zijn dan 0");
            }
	}
	
	    
    
    
}
