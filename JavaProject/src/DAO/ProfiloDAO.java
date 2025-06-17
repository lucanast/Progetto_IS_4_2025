// DAO/ProfiloDAO.java
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class ProfiloDAO {
    private int id;
    private String immagine;
    private String biografia;
    private String dati;

    private String autoreEmail;
    private AutoreDAO autoreDao;

    /**
     * Costruttore per associare un autore già inserito (profilo nuovo)
     */
    public ProfiloDAO(int id, String immagine, String biografia, String dati, String autoreEmail) {

        this.id=id;
        this.immagine = "";
        this.biografia = "";
        this.dati = "";
        this.autoreEmail = autoreEmail;
    }





    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getImmagine() { return immagine; }
    public void setImmagine(String immagine) { this.immagine = immagine; }
    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }
    public String getDati() { return dati; }
    public void setDati(String dati) { this.dati = dati; }
   public String getAutoreEmail() { return autoreEmail; }
    public void setAutoreEmail(String autoreEmail) { this.autoreEmail = autoreEmail; }


    public boolean caricaDaDB() throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM profili WHERE id = ?";
        System.out.println(query);
        PreparedStatement ps = DBManager.getPreparedStatement(query);
        ps.setInt(1, this.id);
        ResultSet rs = DBManager.selectQuery(ps);
        boolean trovato = false;
        if (rs.next()) {
            this.biografia = rs.getString("biografia");
            this.immagine  = rs.getString("immagine");
            this.dati      = rs.getString("dati");
            trovato = true;
        }
        rs.close();
        DBManager.close(ps);
        return trovato;
    }


    public void scriviSuDB()  {

        String query = "INSERT INTO profili (id, biografia, immagine, dati, Autori_email) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE biografia = VALUES(biografia), immagine = VALUES(immagine), dati = VALUES(dati)";
        System.out.println(query);
        try{
            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1,this.id);
            statement.setString(2,this.biografia);
            statement.setString(3,this.immagine);
            statement.setString(4,this.dati);
            statement.setString(5,this.autoreEmail);

            int rs=DBManager.updateQuery(statement);
        }catch(ClassNotFoundException|SQLException e){
            e.printStackTrace();
        }

    }



    public void caricaIdDalDB() throws ClassNotFoundException, SQLException {
        if (this.autoreEmail == null || this.autoreEmail.isEmpty()) {
            throw new IllegalStateException(" autoreEmail non è stato impostato!");
        }

        String query = "SELECT id FROM profili WHERE Autori_email = ?";
        PreparedStatement ps = DBManager.getPreparedStatement(query);
        ps.setString(1, this.autoreEmail);

        ResultSet rs = DBManager.selectQuery(ps);
        if (rs.next()) {
            this.id = rs.getInt("id");
            System.out.println("✅ ID profilo trovato: " + this.id);
        } else {
            throw new SQLException(" Nessun profilo trovato per email: " + this.autoreEmail);
        }

        rs.close();
        DBManager.close(ps);
    }


    public ProfiloDAO(){
        super();
    }
    public ProfiloDAO(AutoreDAO autoreDao) {
        this.autoreDao = autoreDao;
    }

    public int generaProfiloId() throws ClassNotFoundException, SQLException {
        String query = "SELECT MAX(id) AS max_id FROM profili";
        PreparedStatement ps = DBManager.getPreparedStatement(query);
        ResultSet rs = DBManager.selectQuery(ps);

        int nuovoId = 1; // valore iniziale predefinito, se la tabella è vuota
        if (rs.next()) {
            int maxId = rs.getInt("max_id");
            if (!rs.wasNull()) {
                nuovoId = maxId + 1;
            }
        }

        rs.close();
        DBManager.close(ps);
        return nuovoId;
    }


}
