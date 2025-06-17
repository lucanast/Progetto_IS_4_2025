package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entity.Autore;
import Entity.Commento;


public class CommentoDAO {
    private String testo;
    private  String data_pubblicazione;
    int id;
    private int ricettaId;
    private String autoreEmail;
    private String ricettaAutoreEmail;

    //per carica da db
    public CommentoDAO(int id) {

        this.id = id;

    }
    //per scrivi su db

    public CommentoDAO(int id,String testo, String data_pubblicazione,String autoreEmail,int ricettaId,String ricettaAutoreEmail) {
        this.id = id;
        this.testo = testo;
        this.data_pubblicazione = data_pubblicazione;
        this.autoreEmail = autoreEmail;
        this.ricettaId = ricettaId;
        this.ricettaAutoreEmail = ricettaAutoreEmail;


    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }
    public String getTesto() {
        return testo;
    }
    public void setData_pubblicazione(String data_pubblicazione) {
        this.data_pubblicazione = data_pubblicazione;
    }
    public String getData_pubblicazione() {
        return data_pubblicazione;
    }
    public void setAutoreEmail(String autoreEmail) {
        this.autoreEmail = autoreEmail;
    }
    public String getAutoreEmail() {
        return autoreEmail;
    }
    public void setRicettaId(int ricettaId) {
        this.ricettaId = ricettaId;
    }
    public int getRicettaId() {
        return ricettaId;
    }
    public void setRicettaAutoreEmail(String ricettaAutoreEmail) {
        this.ricettaAutoreEmail = ricettaAutoreEmail;
    }
    public String getRicettaAutoreEmail() {
        return ricettaAutoreEmail;
    }


    public void caricaDaDB() {

        String query = "SELECT * FROM Commenti WHERE Id = " + this.id;

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1, this.id);

            ResultSet rs = DBManager.selectQuery(statement);

            if(rs.next()) {

                this.testo = rs.getString("testo");
                this.data_pubblicazione = rs.getString("data_pubblicazione");
                this.ricettaId = rs.getInt("Ricetta_Id");
                this.autoreEmail = rs.getString("Autori_email");
                this.ricettaAutoreEmail = rs.getString("Ricetta_Autori_Email");

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void scriviSuDB() {

        String query = "INSERT INTO Commenti(id,testo, data_pubblicazione,Autori_email,Ricette_id,Ricette_Autori_email)  VALUES (?, ?, ?, ?, ?, ?) ";

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1, this.id);
            statement.setString(2, this.testo);
            statement.setString(3, this.data_pubblicazione);
            statement.setString(4, this.autoreEmail);
            statement.setInt(5, this.ricettaId);
            statement.setString(6, this.ricettaAutoreEmail);



            this.id = DBManager.updateQueryReturnGeneratedKey(statement);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }




    public CommentoDAO(){
        super();
    }


    public ArrayList<Commento> caricaUltimiCommentiPerRicetta(int idRicetta, int limite) {
        ArrayList<Commento> commenti = new ArrayList<>();
        String query = "SELECT * FROM commenti WHERE Ricette_id = ? ORDER BY id DESC LIMIT ?";

        try {
            PreparedStatement stmt = DBManager.getPreparedStatement(query);
            stmt.setInt(1, idRicetta);
            stmt.setInt(2, limite);
            ResultSet rs = DBManager.selectQuery(stmt);

            while (rs.next()) {
                Commento c = new Commento();
                c.setTesto(rs.getString("testo"));
                Autore autore = new Autore();
                autore.setEmail(rs.getString("Autori_email")); // nome campo tabella
                c.setAutore(autore);

                commenti.add(c);


            }

            rs.close();
            DBManager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return commenti;
    }



}
