package Entity;

import java.util.ArrayList;

public class ElencoRaccolte {
    private ArrayList<Raccolta> raccolte;

    public ElencoRaccolte() {
        raccolte = new ArrayList<>();
    }

    public ArrayList<Raccolta> getRaccolte() {
        return raccolte;
    }

    public void setRaccolte(ArrayList<Raccolta> raccolte) {
        this.raccolte = raccolte;
    }

    public void aggiungiRaccolta(Raccolta r) {
        raccolte.add(r);
    }

    public void rimuoviRaccolta(Raccolta r) {
        raccolte.remove(r);
    }

    @Override
    public String toString() {
        return "ElencoRaccolte{" +
                "raccolte=" + raccolte.size() +
                '}';
    }
}
