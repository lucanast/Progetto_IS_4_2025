package Entity;

import DAO.CommentoDAO;

import java.util.Date;

public class Commento {
    private int id;
    private String testo;
    private String dataPubblicazione;
    private Autore autore;
    private Ricetta ricetta;

    public Commento() {
    }

    public Commento(int id, Autore autore) {
        this.id = id;
        this.autore = autore;
    }

    public Commento(String testo, String dataPubblicazione, Autore autore, int id) {
        this.testo = testo;
        this.dataPubblicazione = dataPubblicazione;
        this.autore = autore;
        this.id = id;
    }

    // Getter e setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getDataPubblicazione() {
        return dataPubblicazione;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public Autore getAutore() {
        return autore;
    }

    public void setRicetta(Ricetta ricetta) {
        this.ricetta = ricetta;
    }

    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    public  Ricetta getRicetta() {
        return ricetta;
    }
    @Override
    public String toString() {
        return "Commento{" +
                "id=" + id +
                ", testo='" + testo + '\'' +
                ", dataPubblicazione=" + dataPubblicazione +
                ", autore=" + autore +
                '}';
    }
   /* public void scriviSuDB(int id){
        CommentoDAO c = new CommentoDAO();
        c.setTesto(this.testo);
        c.setData_pubblicazione(this.dataPubblicazione);
        c.setId(this.id);
        c.scriviSuDB();

    }*/
}
