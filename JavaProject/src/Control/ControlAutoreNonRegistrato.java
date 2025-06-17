package Control;

import DAO.AutoreDAO;
import DAO.ProfiloDAO;
import Entity.Autore;
import exception.EmailInUso;

public class ControlAutoreNonRegistrato {

    public ControlAutoreNonRegistrato() {}

    public void registrazione(Autore nuovoAutore) {
        try {
            //  Validazione email e password
            if (!emailValida(nuovoAutore.getEmail())) {
                throw new IllegalArgumentException("Email non valida o dominio non autorizzato.");
            }

            if (!passwordValida(nuovoAutore.getPassword())) {
                throw new IllegalArgumentException("La password deve avere 8–16 caratteri, almeno una maiuscola, un numero e un simbolo speciale.");
            }

            //  Controlla se l'email è già in uso
            AutoreDAO daoCheck = new AutoreDAO(nuovoAutore.getEmail());
            if (daoCheck.emailEsistente()) {
                throw new EmailInUso("Email già registrata: " + nuovoAutore.getEmail());
            }

            // ✍ Crea e salva nuovo autore nel DB
            AutoreDAO daoNuovo = new AutoreDAO(
                    nuovoAutore.getEmail(),
                    nuovoAutore.getPassword(),
                    nuovoAutore.getNome(),
                    nuovoAutore.getCognome()
            );
            daoNuovo.scriviSuDB();

            //  Crea il profilo vuoto associato
            ProfiloDAO nuovoProfiloDAO = new ProfiloDAO();
            int nuovoId = nuovoProfiloDAO.generaProfiloId();

            nuovoProfiloDAO.setId(nuovoId);
            nuovoProfiloDAO.setAutoreEmail(nuovoAutore.getEmail());
            nuovoProfiloDAO.setBiografia("");
            nuovoProfiloDAO.setImmagine("");
            nuovoProfiloDAO.setDati("");
            nuovoProfiloDAO.scriviSuDB();

        } catch (EmailInUso e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Errore generico durante registrazione autore: " + e.getMessage(), e);
        }
    }

    // -------------------------------
    //  Metodi di validazione
    // -------------------------------

    protected boolean emailValida(String email) {
        if (email == null) return false;

        String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regexEmail)) return false;

        String[] dominiAmmessi = {"gmail.com", "libero.it", "outlook.com", "studenti.unina.it", "unina.it"};
        String dominio = email.substring(email.lastIndexOf("@") + 1).toLowerCase();

        for (String d : dominiAmmessi) {
            if (dominio.equals(d)) return true;
        }

        return false;
    }

    protected boolean passwordValida(String password) {
        if (password == null) return false;
        if (password.length() < 8 || password.length() > 16) return false;

        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!?.*()\\-_/]).{8,16}$";
        return password.matches(regex);
    }
}
