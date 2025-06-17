package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaccoltaDAO {
    private String titolo;
    private String descrizione;
    private boolean isPrivate;
    private int id;
    public String AutoreEmail;
    public RaccoltaDAO(int id,String titolo, String descrizione, boolean isPrivate, String AutoreEmail) {
        this.id = id;
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.isPrivate = isPrivate;

        this.AutoreEmail = AutoreEmail;
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
    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
    public boolean isPrivate() {
        return isPrivate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAutoreEmail() {
        return AutoreEmail;
    }
    public void setAutoreEmail(String AutoreEmail) {
        this.AutoreEmail = AutoreEmail;
    }



    public void caricaDaDB() {

        String query = "SELECT * FROM raccolte WHERE id = ?";
        System.out.println(query);

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1, this.id);

            ResultSet rs = DBManager.selectQuery(statement);

            if(rs.next()) {

                titolo = rs.getString("titolo");
                descrizione = rs.getString("descrizione");
                isPrivate= rs.getBoolean("IsPrivate");
                AutoreEmail = rs.getString("AutoreEmail");

            }

            rs.close();
            DBManager.closeConnection();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void scriviSuDB() {

        String query = "INSERT INTO Raccolte(id,titolo,descrizione,isPrivate,Autori_email)VALUES(?, ?, ?, ?, ?)";
        System.out.println(query);

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1, this.id);
            statement.setString(2, this.titolo);
            statement.setString(3, this.descrizione);
            statement.setBoolean(4, this.isPrivate);
            statement.setString(5, this.AutoreEmail);



            int rs = DBManager.updateQuery(statement);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public RaccoltaDAO(){
        super();
    }




    public int trovaIdPerTitoloEAutore(String titolo, String emailAutore) {
        String query = "SELECT id FROM raccolte WHERE titolo = ? AND Autori_email = ?";
        try {
            PreparedStatement stmt = DBManager.getPreparedStatement(query);
            stmt.setString(1, titolo);
            stmt.setString(2, emailAutore);
            ResultSet rs = DBManager.selectQuery(stmt);
            if (rs.next()) {
                return rs.getInt("id");
            }
            rs.close();
            DBManager.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // nessuna raccolta trovata per quell'autore
    }





}
