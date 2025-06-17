package DAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class IngredienteDAO {
    private String nome;
    private int id;
    public IngredienteDAO(int id) {
        this.id = id;
    }
    public IngredienteDAO(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNome() {
        return nome;
    }
    public int getId(){
        return id;
    }

    public boolean caricaDaDB() {

        String query = "SELECT * FROM Ingredienti WHERE Id = ;";
        boolean trovato=false;

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setInt(1, this.id);


            ResultSet rs = DBManager.selectQuery(statement);

            if(rs.next()) {

                this.nome = rs.getString("Nome");
                trovato=true;


            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return trovato;
    }

    public void scriviSuDB() {

        String query = "INSERT INTO Ingredienti(id,nome) VALUES ( \'"+id+"\',\'"+this.nome+"\')";
        System.out.println(query);

        try {

            PreparedStatement statement = DBManager.getPreparedStatement(query);
            statement.setString(2, this.nome);
            statement.setInt(1, this.id);

            this.id = DBManager.updateQueryReturnGeneratedKey(statement);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public IngredienteDAO(){
        super();
    }

    public void setId(int id) {
        this.id = id;
    }
}
