package Entity;

import DAO.IngredienteDAO;

public class Ingrediente {
    private int id;
    private String nome;

    public Ingrediente() {
    }

    public Ingrediente(int id) {
        this.id = id;
    }

    public Ingrediente(String nome, int id) {
        this.nome = nome;
        this.id = id;
    }

    // Getter e Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

    public void scriviSuDB(int id) {
        IngredienteDAO i = new IngredienteDAO();
        i.setNome(this.nome);
        i.setId(this.id);
        i.scriviSuDB();
    }
}
