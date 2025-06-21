package DAO;
import Entity.Autore;
import Entity.Ricetta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RicettaDAO {
    private String titolo;
    private String descrizione;
    private int tempo;
    public String dataPubblicazione;
    public int numero_like;
    public int numero_commenti;
    private String tag;
    private ArrayList<IngredienteDAO> ingredienti;
    private int id;
    private String autoreEmail;
    private int idRaccolta;
    public RicettaDAO(ArrayList<IngredienteDAO> ingredienti) {

        this.ingredienti = ingredienti;
    }
    public RicettaDAO(int id) {
        this.id = id;
    }
    public RicettaDAO(String titolo, String descrizione,int tempo ,int numero_like, int numero_commenti,String tag,String autoreEmail,int idRaccolta) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.tempo = tempo;
        this.numero_like = numero_like;
        this.numero_commenti = numero_commenti;
        this.tag=tag;
        this.autoreEmail = autoreEmail;
        this.idRaccolta = idRaccolta;

    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public String getTitolo() {
        return titolo;
    }
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getDescrizione() {
        return descrizione;
    }
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }
    public int getTempo() {
        return tempo;
    }
    public void setNumero_like(int numero_like) {
        this.numero_like = numero_like;
    }
    public int getNumero_like() {
        return numero_like;
    }
    public void setNumeroCommenti(int numero_commenti) {
        this.numero_commenti = numero_commenti;
    }
    public int getNumeroCommenti() {
        return numero_commenti;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDataPubblicazione(String dataPubblicazione) {
        this.dataPubblicazione = dataPubblicazione;
    }

    public ArrayList<IngredienteDAO> getIngredienti() {
        return ingredienti;
    }

    public String getAutoreEmail() {
        return autoreEmail;
    }
    public void setAutoreEmail(String autoreEmail) {
        this.autoreEmail = autoreEmail;
    }
    public int getIdRaccolta() {
        return idRaccolta;
    }
    public void setIdRaccolta(int idRaccolta) {
        this.idRaccolta = idRaccolta;
    }

    public void caricaDaDB() {

        String query =  "SELECT * FROM ricette WHERE id = ?";
        System.out.println(query);

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1, this.id);

            ResultSet rs = DBManager.selectQuery(statement);

            if(rs.next()) {
                id=rs.getInt("id");
                descrizione = rs.getString("descrizione");
                tempo=rs.getInt("tempo");
                tag = rs.getString("tag");
                dataPubblicazione=rs.getString("data_pubblicazione");
                numero_like = rs.getInt("numero_like");
                numero_commenti = rs.getInt("numero_commenti");
                autoreEmail = rs.getString("Autori_email");
                idRaccolta = rs.getInt("Raccolte_id");
                titolo = rs.getString("titolo");
            }

            rs.close();
            DBManager.closeConnection();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }


    public void scriviSuDB() {
        String query = """
            INSERT INTO ricette 
            (id,titolo, descrizione, tempo, tag, data_pubblicazione, numero_like, numero_commenti, Autori_email, Raccolte_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try {
            PreparedStatement statement = DBManager.getPreparedStatement(query);

            statement.setInt(1, this.id);
            statement.setString(2, this.titolo);
            statement.setString(3, this.descrizione);
            statement.setInt(4, this.tempo);
            statement.setString(5, this.tag);
            statement.setString(6, this.dataPubblicazione);
            statement.setInt(7, this.numero_like);
            statement.setInt(8, this.numero_commenti);
            statement.setString(9, this.autoreEmail);
            statement.setInt(10, this.idRaccolta);

            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1); // 🎉 ora hai un vero ID assegnato dal DB
            }
            rs.close();
            DBManager.closeConnection();



        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void aggiornaNumeroCommenti() throws SQLException, ClassNotFoundException {
        String query = "UPDATE ricette SET numero_commenti = ? WHERE id = ?";
        PreparedStatement statement = DBManager.getPreparedStatement(query);
        statement.setInt(1, this.numero_commenti);
        statement.setInt(2, this.id);
        DBManager.updateQuery(statement);

    }
    public int trovaIdPerTitolo(String titolo) {
        String query = "SELECT id FROM ricette WHERE LOWER(titolo) = LOWER(?)";
        try {
            PreparedStatement stmt = DBManager.getPreparedStatement(query);
            stmt.setString(1, titolo);
            ResultSet rs = DBManager.selectQuery(stmt);
            if (rs.next()) return rs.getInt("id");
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // non trovata
    }



    public ArrayList<Ricetta> caricaRicetteDiAltriAutori(String emailUtente, int limite) {
        ArrayList<Ricetta> lista = new ArrayList<>();
        String query = "SELECT * FROM ricette WHERE Autori_email <> ? ORDER BY id DESC LIMIT ?";

        try {
            PreparedStatement stmt = DBManager.getPreparedStatement(query);
            stmt.setString(1, emailUtente);
            stmt.setInt(2, limite);
            ResultSet rs = DBManager.selectQuery(stmt);

            while (rs.next()) {
                Ricetta r = new Ricetta();
                r.setId(rs.getInt("id"));
                r.setTitolo(rs.getString("titolo"));
                r.setDescrizione(rs.getString("descrizione"));
                r.setTempo(rs.getInt("tempo"));
                r.setTag(rs.getString("tag"));
                Autore autore = new Autore(); // 🆕 crea l’autore e imposta la sua email
                autore.setEmail(rs.getString("Autori_email"));
                r.setAutore(autore);
                lista.add(r);
            }

            rs.close();
            DBManager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }


    public void incrementaNumeroCommenti(int idRicetta) {
        String query = "UPDATE ricette SET numero_commenti = numero_commenti + 1 WHERE id = ?";

        try {
            PreparedStatement stmt = DBManager.getPreparedStatement(query);
            stmt.setInt(1, idRicetta);
            stmt.executeUpdate();
            DBManager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Ricetta> caricaRicetteDiAutore(String emailAutore) {
        ArrayList<Ricetta> lista = new ArrayList<>();
        String query = "SELECT * FROM ricette WHERE Autori_email = ? ORDER BY id DESC";

        try {
            PreparedStatement stmt = DBManager.getPreparedStatement(query);
            stmt.setString(1, emailAutore);
            ResultSet rs = DBManager.selectQuery(stmt);

            while (rs.next()) {
                Ricetta r = new Ricetta();
                r.setId(rs.getInt("id"));
                r.setTitolo(rs.getString("titolo"));
                r.setDescrizione(rs.getString("descrizione"));
                r.setTempo(rs.getInt("tempo"));
                r.setTag(rs.getString("tag"));
                r.setNumeroCommenti(rs.getInt("numero_commenti"));

                Autore autore = new Autore();
                autore.setEmail(emailAutore);
                r.setAutore(autore);

                lista.add(r);
            }

            rs.close();
            DBManager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }







    public RicettaDAO(){
        super();
    }

}
