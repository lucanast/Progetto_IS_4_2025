package Control;

import DAO.AutoreDAO;
import DAO.CommentoDAO;
import DAO.RaccoltaDAO;
import DAO.RicettaDAO;
import DAO.ProfiloDAO;
import Entity.Autore;
import Entity.Commento;
import Entity.Raccolta;
import Entity.Ricetta;
import Entity.Profilo;
import exception.ErroreCredenziali;

import java.util.ArrayList;

public class ControlAutore {

    public ControlAutore() {}

    public Autore login(String email, String passwordInput) throws ErroreCredenziali {
        AutoreDAO dao = new AutoreDAO(email,passwordInput,"","");
        boolean trovato = dao.caricaDaDB();
        if (!trovato) {
            throw new ErroreCredenziali("Email non registrata");
        }
        if (!passwordInput.equals(dao.getPassword())) {
            throw new ErroreCredenziali("Password errata");
        }
        Autore a = new Autore();
        a.setEmail(dao.getEmail());
        a.setNome(dao.getNome());
        a.setCognome(dao.getCognome());
        a.setPassword(dao.getPassword());
        try{
            ProfiloDAO profiloDAO = new ProfiloDAO();
            profiloDAO.setAutoreEmail(a.getEmail());
            profiloDAO.caricaIdDalDB();
            profiloDAO.caricaDaDB();
        }catch(Exception e){
            System.err.println("Nessun profilo collegato trovato per l’autore.");
        }
        return a;
    }
    public void creaRicetta(Ricetta nuovaRicetta) {
        try {
            String titolo = nuovaRicetta.getTitolo();
            if (titolo == null || titolo.trim().isEmpty() || titolo.length() > 100) {
                throw new IllegalArgumentException("titolo non valido");
            }


            //  Validazione descrizione
            String descrizione = nuovaRicetta.getDescrizione();


            if ( descrizione==null ||descrizione.isEmpty()||descrizione.length() > 800) {
                throw new IllegalArgumentException("");
            }


            int tempo = nuovaRicetta.getTempo();
            if (tempo <= 0) {
                throw new IllegalArgumentException("Il tempo deve essere un numero positivo.");
            }

            String tag = nuovaRicetta.getTag();
            if (tag == null || tag.trim().isEmpty()) {
                throw new IllegalArgumentException("Il Tag non può essere vuoto.");
            }
            String titoloRaccolta = nuovaRicetta.getRaccolta().getTitolo();
            String descrizioneRaccolta=nuovaRicetta.getRaccolta().getDescrizione();
            String emailAutore = nuovaRicetta.getAutore().getEmail();

            // 1. Verifica se la raccolta esiste già
            RaccoltaDAO daoRac = new RaccoltaDAO();
            int idRaccolta = daoRac.trovaIdPerTitoloEAutore(titoloRaccolta,emailAutore); // metodo che cerca per titolo e per autore

            if (idRaccolta == 0) {
                // 2. Se non trovata, la creo
                Raccolta nuova = new Raccolta();
                nuova.setTitolo(titoloRaccolta);
                nuova.setDescrizione(descrizioneRaccolta); // puoi chiedere descrizione all’utente se vuoi
                nuova.setPrivate(false);
                nuova.setAutore(nuovaRicetta.getAutore());

                Raccolta inserita = creaRaccolta(nuova); // salva e restituisce con ID
                idRaccolta = inserita.getId();
            }
            // 3. Preparazione oggetto DAO Ricetta con foreign key coerenti
            int idRicetta = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
            RicettaDAO daoRic = new RicettaDAO();
            daoRic.setId(idRicetta);
            daoRic.setTitolo(nuovaRicetta.getTitolo());
            daoRic.setDescrizione(nuovaRicetta.getDescrizione());
            daoRic.setTempo(nuovaRicetta.getTempo());
            daoRic.setTag(nuovaRicetta.getTag());
            daoRic.setDataPubblicazione(java.time.LocalDate.now().toString());
            daoRic.setNumero_like(0);
            daoRic.setNumeroCommenti(0);
            daoRic.setAutoreEmail(emailAutore);
            daoRic.setIdRaccolta(idRaccolta);

            daoRic.scriviSuDB();

        } catch (Exception e) {
            throw new RuntimeException("Errore DB durante creaRicetta: " + e.getMessage(), e);
        }
    }



    public void modificaProfilo(Profilo profiloAggiornato, Autore autore) {
        try {

            ProfiloDAO loader = new ProfiloDAO();
            loader.setAutoreEmail(autore.getEmail());
            loader.caricaIdDalDB(); // Assicurati che questo metodo sia implementato
            int idProfilo = loader.getId();


            ProfiloDAO dao = new ProfiloDAO();
            dao.setId(idProfilo);
            dao.setBiografia(profiloAggiornato.getBiografia());
            dao.setImmagine(profiloAggiornato.getImmagine());
            dao.setDati(profiloAggiornato.getDati());
            dao.setAutoreEmail(autore.getEmail());

            dao.scriviSuDB();

        } catch (Exception e) {
            throw new RuntimeException("Errore DB durante modificaProfilo: " + e.getMessage(), e);
        }
    }




    public Raccolta creaRaccolta(Raccolta nuovaRaccolta) {
        try {

            String titolo = nuovaRaccolta.getTitolo();
            if (titolo == null || titolo.trim().isEmpty()) {
                throw new IllegalArgumentException("Il titolo della raccolta non può essere vuoto.");
            }
            if (titolo.length() > 100) {
                throw new IllegalArgumentException("Il titolo della raccolta non può superare 100 caratteri.");
            }


            String descrizione = nuovaRaccolta.getDescrizione();
            if (descrizione == null || descrizione.trim().isEmpty() || descrizione.length() > 100) {
                throw new IllegalArgumentException("La descrizione non è valida.");
            }



            RaccoltaDAO daoCheck = new RaccoltaDAO();
            int idEsistente = daoCheck.trovaIdPerTitoloEAutore(titolo.trim(), nuovaRaccolta.getAutore().getEmail());

            if (idEsistente != 0) {
                throw new IllegalArgumentException("Hai già una raccolta con questo titolo.");
            }


            int nuovoId = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
            RaccoltaDAO daoNuova = new RaccoltaDAO(
                    nuovoId,
                    titolo,
                    descrizione,
                    nuovaRaccolta.isPrivate(),
                    nuovaRaccolta.getAutore().getEmail()
            );
            daoNuova.scriviSuDB();

            Raccolta inDB = new Raccolta();
            inDB.setId(nuovoId);
            inDB.setTitolo(titolo);
            inDB.setDescrizione(descrizione);
            inDB.setPrivate(nuovaRaccolta.isPrivate());

            return inDB;

        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Errore DB durante creaRaccolta: " + e.getMessage(), e);
        }
    }


    public ArrayList<Ricetta> mostraFeedDaAltriAutori(String emailUtente) {
       RicettaDAO dao = new RicettaDAO();
       return dao.caricaRicetteDiAltriAutori(emailUtente, 5); //  massimo 5
   }
    public ArrayList<Commento> getCommentiPerRicetta(int idRicetta) {
        CommentoDAO dao = new CommentoDAO();
        return dao.caricaUltimiCommentiPerRicetta(idRicetta, 3);
    }

    public void aggiungiCommento(Commento commento) throws Exception {
        int idCommento = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        RicettaDAO daoRic = new RicettaDAO();
        daoRic.setId(commento.getRicetta().getId());
        daoRic.caricaDaDB(); // 👈 fondamentale!

        CommentoDAO daoComm = new CommentoDAO(
                idCommento,
                commento.getTesto(),
                commento.getDataPubblicazione(),
                commento.getAutore().getEmail(),
                daoRic.getId(),
                daoRic.getAutoreEmail()
        );
        daoComm.scriviSuDB();

        daoRic.setNumeroCommenti(daoRic.getNumeroCommenti() + 1);
        daoRic.aggiornaNumeroCommenti();

    }

    public ArrayList<Ricetta> getRicetteDiAutore(String emailAutore) {
        RicettaDAO dao = new RicettaDAO();
        return dao.caricaRicetteDiAutore(emailAutore);
    }





}