package Entity;

import DAO.RicettaDAO;

import java.util.ArrayList;
import java.util.Date;

public class Ricetta {
    private int id;
    private String titolo;
    private String descrizione;
    private int tempo;
    private String dataPubblicazione; // meglio usare Date per la data
    private int numeroLike;
    private int numeroCommenti;
    private String tag;

    private Autore autore; // relazione 1-N (molte ricette a 1 autore)
    private Raccolta raccolta; // contenimento lasco

    private ArrayList<Commento> commenti; // contenimento lasco
    private ArrayList<Ingrediente> ingredienti; // contenimento lasco

    public Ricetta() {
        commenti = new ArrayList<>();
        ingredienti = new ArrayList<>();
    }

    public Ricetta(int id) {
        this();
        this.id = id;
    }

    public Ricetta(String titolo, String descrizione, int tempo, Raccolta raccolta, int numeroLike, int numeroCommenti, String tag) {
        this();
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tempo = tempo;
        this.raccolta = raccolta;
        this.numeroLike = numeroLike;
        this.numeroCommenti = numeroCommenti;
        this.tag = tag;
    }

    // Getters e setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public int getTempo() { return tempo; }
    public void setTempo(int tempo) { this.tempo = tempo; }

    public String getDataPubblicazione() { return dataPubblicazione; }
    public void setDataPubblicazione(String dataPubblicazione) { this.dataPubblicazione = dataPubblicazione; }

    public int getNumeroLike() { return numeroLike; }
    public void setNumeroLike(int numeroLike) { this.numeroLike = numeroLike; }

    public int getNumeroCommenti() { return numeroCommenti; }
    public void setNumeroCommenti(int numeroCommenti) { this.numeroCommenti = numeroCommenti; }

    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    public Autore getAutore() { return autore; }
    public void setAutore(Autore autore) { this.autore = autore; }

    public Raccolta getRaccolta() { return raccolta; }
    public void setRaccolta(Raccolta raccolta) { this.raccolta = raccolta; }

    public ArrayList<Commento> getCommenti() { return commenti; }
    public void setCommenti(ArrayList<Commento> commenti) { this.commenti = commenti; }

    public ArrayList<Ingrediente> getIngredienti() { return ingredienti; }
    public void setIngredienti(ArrayList<Ingrediente> ingredienti) { this.ingredienti = ingredienti; }


    // Metodo per aggiungere singoli elementi ai contenitori laschi

    public void aggiungiCommento(Commento commento) {
        this.commenti.add(commento);
    }

    public void aggiungiIngrediente(Ingrediente ingrediente) {
        this.ingredienti.add(ingrediente);
    }


    public void scriviSuDB(int id){
        RicettaDAO r=new RicettaDAO();
        r.setDescrizione(this.descrizione);
        r.setTempo(this.tempo);
        r.setNumero_like(this.numeroLike);
        r.setNumeroCommenti(this.numeroCommenti);
        r.setTag(this.tag);
        r.setDataPubblicazione(this.dataPubblicazione);
        r.setTitolo(this.titolo);
        r.scriviSuDB();

    }

}
