package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutoreDAO {
    private String nome;
    private String cognome;
    private String email;
    private String password;

    //per carica da db
    public AutoreDAO(String email)
    {

        this.email = email;
        caricaDaDB();

    }

    //per scrivere sul db
    public AutoreDAO(String email,String password,String nome,String cognome){

        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;

    }


    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCognome() {
        return cognome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }




    public boolean caricaDaDB() {
        String query = "SELECT * FROM autori WHERE email = ? AND password = ?";
        System.out.println(query);
        boolean trovato = false;

        try {
            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setString(1, this.email);
            statement.setString(2, this.password);
            ResultSet rs = DBManager.selectQuery(statement);

            if (rs.next()) {
                this.nome = rs.getString("nome");
                this.cognome = rs.getString("cognome");
                trovato = true;
            }

            rs.close();
            DBManager.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return trovato;
    }
    public void scriviSuDB() {



        String query = "INSERT INTO autori(nome, cognome, email, password) VALUES (?, ?, ?, ?)";
        System.out.println(query);

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setString(1, this.nome);
            statement.setString(2, this.cognome);
            statement.setString(3, this.email);
            statement.setString(4, this.password);

            int rs = DBManager.updateQuery(statement);
            System.out.println("📦 Registrazione completata. Righe inserite: " + rs);

        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(" Errore durante scriviSuDB: " + e.getMessage());
            e.printStackTrace();

        }



    }

    public boolean emailEsistente() {
        String query = "SELECT * FROM autori WHERE email = ?";
        boolean trovato = false;

        try {
            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setString(1, this.email); // solo 1 parametro ora

            ResultSet rs = DBManager.selectQuery(statement);

            if (rs.next()) {
                trovato = true;
            }

            rs.close();
            DBManager.closeConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return trovato;
    }


    public AutoreDAO(){
        super();
    }




}