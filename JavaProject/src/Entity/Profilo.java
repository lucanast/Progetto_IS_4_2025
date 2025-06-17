package Entity;

import DAO.ProfiloDAO;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class Profilo {
    private int id;
    private String biografia;
    private String immagine;
    private String dati;
    private Autore autore;  // relazione 1-1 con Autore

    public Profilo() {
    }

    public Profilo(int id, Autore autore) {
        this.id = id;
        this.autore = autore;
    }

    public Profilo(String biografia, String immagine, String dati, Autore autore, int id) {
        this.biografia = biografia;
        this.immagine = immagine;
        this.dati = dati;
        this.id = id;
        this.autore = autore;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    public String getDati() {
        return dati;
    }

    public void setDati(String dati) {
        this.dati = dati;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    @Override
    public String toString() {
        return "Profilo{" +
                "id=" + id +
                ", biografia='" + biografia + '\'' +
                ", immagine='" + immagine + '\'' +
                ", dati='" + dati + '\'' +
                ", autore=" + (autore != null ? autore.getEmail() : "null") +
                '}';
    }

    public void scriviSuDB(int id){
        ProfiloDAO p=new ProfiloDAO();
        p.setImmagine(this.immagine);
        p.setId(id);
        p.setBiografia(this.biografia);
        p.setDati(this.dati);
        p.scriviSuDB();
    }
}
