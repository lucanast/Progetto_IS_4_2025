package Entity;

import java.util.ArrayList;

public class Piattaforma {
    private ArrayList<Autore> autori;

    public Piattaforma() {
        autori = new ArrayList<>();
    }

    public ArrayList<Autore> getAutori() {
        return autori;
    }

    public void setAutori(ArrayList<Autore> autori) {
        this.autori = autori;
    }

    public void aggiungiAutore(Autore autore) {
        if (autore != null && !autori.contains(autore)) {
            autori.add(autore);
        }
    }

    public void rimuoviAutore(Autore autore) {
        autori.remove(autore);
    }

    @Override
    public String toString() {
        return "Piattaforma{" +
                "numeroAutori=" + autori.size() +
                '}';
    }
}
