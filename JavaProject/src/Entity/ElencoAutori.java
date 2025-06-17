package Entity;

import java.util.ArrayList;

public class ElencoAutori {
    private ArrayList<Autore> autori;

    public ElencoAutori() {
        this.autori = new ArrayList<>();
    }

    public ElencoAutori(ArrayList<Autore> autori) {
        this.autori = autori;
    }

    public ArrayList<Autore> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autore> autori) {
        this.autori = autori;
    }

    public void aggiungiAutore(Autore autore) {
        this.autori.add(autore);
    }

    public boolean rimuoviAutore(Autore autore) {
        return this.autori.remove(autore);
    }

    public int numeroAutori() {
        return this.autori.size();
    }

    @Override
    public String toString() {
        return "ElencoAutori{" +
                "autori=" + autori +
                '}';
    }
}
