package Entity;


import DAO.AutoreDAO;

import java.util.ArrayList;

public class Autore {
    private String nome;
    private String cognome;
    private String email;
    private String password;

    private Profilo profilo;
    private ArrayList<Raccolta> raccolte;
    private ArrayList<Commento> commenti;
    private ArrayList<Ricetta> ricette;

    public Autore() {
        this.profilo = new Profilo();
        this.raccolte = new ArrayList<>();
        this.commenti = new ArrayList<>();
        this.ricette = new ArrayList<>();
    }

    public Autore(String email) {
        this();
        this.email = email;
    }

    public Autore(String email, String password, String nome, String cognome) {
        this(email);
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }

    // Getters & Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Profilo getProfilo() { return profilo; }
    public void setProfilo(Profilo profilo) { this.profilo = profilo; }

    public ArrayList<Raccolta> getRaccolte() { return raccolte; }
    public void setRaccolte(ArrayList<Raccolta> raccolte) { this.raccolte = raccolte; }

    public ArrayList<Commento> getCommenti() { return commenti; }
    public void setCommenti(ArrayList<Commento> commenti) { this.commenti = commenti; }

    public ArrayList<Ricetta> getRicette() { return ricette; }
    public void setRicette(ArrayList<Ricetta> ricette) { this.ricette = ricette; }

    public void scriviSuDB(String email){
        AutoreDAO a=new AutoreDAO();
        a.setNome(this.nome);
        a.setCognome(this.cognome);
        a.setPassword(this.password);
        a.setEmail(this.email);
        a.scriviSuDB();
    }
}
