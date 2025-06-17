package Entity;

import DAO.RaccoltaDAO;

import java.util.ArrayList;

public class Raccolta {
    private int id;
    private String titolo;
    private String descrizione;
    private boolean isPrivate;
    private ArrayList<Ricetta> ricette;
    private Autore autore;// contenimento lasco di Ricetta

    public Raccolta() {
        ricette = new ArrayList<>();
    }

    public Raccolta(int id) {
        this.id = id;
        ricette = new ArrayList<>();
    }

    public Raccolta(String titolo, String descrizione, Autore autore,boolean isPrivate, int id) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.isPrivate = isPrivate;
        this.id = id;
        this.autore = autore;
        ricette = new ArrayList<>();
    }

    // getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public ArrayList<Ricetta> getRicette() {
        return ricette;
    }

    public void setRicette(ArrayList<Ricetta> ricette) {
        this.ricette = ricette;
    }
    public Autore getAutore() {
        return autore;
    }
    public void setAutore(Autore autore) {
        this.autore = autore;
    }

    @Override
    public String toString() {
        return "Raccolta{" +
                "id=" + id +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", isPrivate=" + isPrivate +
                ", ricette=" + ricette.size() +
                '}';
    }

    public void scriviSuDB(int id){
        RaccoltaDAO r=new RaccoltaDAO();
        r.setTitolo(this.titolo);
        r.setDescrizione(this.descrizione);
        r.setPrivate(this.isPrivate);
        r.setId(this.id);
        r.scriviSuDB();
    }
}
