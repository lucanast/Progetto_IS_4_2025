package Test;

import Control.*;
import Entity.*;
import DAO.*;
import exception.ErroreCredenziali;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ControlAutoreTest {

    private ControlAutore control;

    @Before
    public void setUp() {
        control = new ControlAutore();
        System.out.println(">> Inizio test...");
    }

    @After
    public void tearDown() {
        control = null;
        System.out.println("<< Test completato.");
    }

    @Test(expected = ErroreCredenziali.class)
    public void testLoginEmailNonRegistrata() throws ErroreCredenziali {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public Autore login(String email, String passwordInput) throws ErroreCredenziali {
                throw new ErroreCredenziali("Email non registrata");
            }
        };
        controlFake.login("non@esiste.it", "password");
    }

    @Test(expected = ErroreCredenziali.class)
    public void testLoginPasswordErrata() throws ErroreCredenziali {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public Autore login(String email, String passwordInput) throws ErroreCredenziali {
                throw new ErroreCredenziali("Password errata");
            }
        };
        controlFake.login("test@unina.it", "sbagliata");
    }

    @Test
    public void testLoginSuccessoSimulato() throws ErroreCredenziali {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public Autore login(String email, String passwordInput) {
                Autore a = new Autore();
                a.setEmail(email);
                a.setNome("Mario");
                a.setCognome("Rossi");
                a.setPassword(passwordInput);
                return a;
            }
        };
        Autore a = controlFake.login("giusto@unina.it", "Password1@");
        assertEquals("giusto@unina.it", a.getEmail());
    }

    @Test
    public void testCreaRicettaTitoloNull() {
        Ricetta r = new Ricetta();
        r.setTitolo(null);
        try {
            control.creaRicetta(r);
            fail("RuntimeException attesa");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertEquals("titolo non valido", e.getCause().getMessage());
        }
    }

    @Test
    public void testCreaRicettaDescrizioneVuota() {
        Ricetta r = new Ricetta();
        r.setTitolo("Torta");
        r.setDescrizione("");
        try {
            control.creaRicetta(r);
            fail("RuntimeException attesa");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertEquals("La descrizione non può essere vuota e  non può superare 800 caratteri.", e.getCause().getMessage());
        }
    }

    @Test
    public void testCreaRicettaTempoNegativo() {
        Ricetta r = new Ricetta();
        r.setTitolo("Torta");
        r.setDescrizione("Buonissima");
        r.setTempo(-5);
        try {
            control.creaRicetta(r);
            fail("RuntimeException attesa");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertEquals("Il tempo deve essere un numero positivo.", e.getCause().getMessage());
        }
    }

    @Test
    public void testCreaRicettaTagVuoto() {
        Ricetta r = new Ricetta();
        r.setTitolo("Torta");
        r.setDescrizione("Buonissima");
        r.setTempo(10);
        r.setTag("");
        try {
            control.creaRicetta(r);
            fail("RuntimeException attesa");
        } catch (RuntimeException e) {
            assertTrue(e.getCause() instanceof IllegalArgumentException);
            assertEquals("Il Tag non può essere vuoto.", e.getCause().getMessage());
        }
    }


    @Test
    public void testCreaRaccoltaTitoloVuoto() {
        Raccolta r = new Raccolta();
        r.setTitolo("");
        r.setDescrizione("Dessert");
        r.setPrivate(false);
        r.setAutore(new Autore("email@unina.it", "pass", "nome", "cognome"));
        try {
            control.creaRaccolta(r);
            fail("Eccezione IllegalArgumentException attesa");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().toLowerCase().contains("titolo"));
        } catch (RuntimeException e) {
            fail("Attesa IllegalArgumentException, ricevuta RuntimeException");
        }
    }



    @Test
    public void testCreaRaccoltaDescrizioneTroppoLunga() {
        Raccolta r = new Raccolta();
        r.setTitolo("Raccolta1");
        r.setDescrizione(new String(new char[101]).replace('\0', 'a')); // 101 caratteri
        r.setAutore(new Autore("email@unina.it", "pass", "nome", "cognome"));

        try {
            control.creaRaccolta(r);
            fail("Eccezione IllegalArgumentException attesa");
        } catch (IllegalArgumentException e) {
            assertEquals("La descrizione non è valida.", e.getMessage());
        }
    }


    @Test(expected = RuntimeException.class)
    public void testModificaProfiloErroreFinto() {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public void modificaProfilo(Profilo p, Autore a) {
                throw new RuntimeException("Errore DB simulato");
            }
        };
        Profilo p = new Profilo();
        Autore a = new Autore();
        a.setEmail("email@unina.it");
        controlFake.modificaProfilo(p, a);
    }

    @Test
    public void testMostraFeedDaAltriAutoriSimulato() {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public ArrayList<Ricetta> mostraFeedDaAltriAutori(String emailUtente) {
                ArrayList<Ricetta> lista = new ArrayList<>();
                Ricetta r = new Ricetta();
                r.setTitolo("Test Ricetta");
                lista.add(r);
                return lista;
            }
        };
        ArrayList<Ricetta> feed = controlFake.mostraFeedDaAltriAutori("utente@unina.it");
        assertEquals(1, feed.size());
        assertEquals("Test Ricetta", feed.get(0).getTitolo());
    }

    @Test
    public void testGetRicetteDiAutoreSimulato() {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public ArrayList<Ricetta> getRicetteDiAutore(String emailAutore) {
                ArrayList<Ricetta> lista = new ArrayList<>();
                Ricetta r = new Ricetta();
                r.setTitolo("RicettaAutore");
                lista.add(r);
                return lista;
            }
        };
        ArrayList<Ricetta> lista = controlFake.getRicetteDiAutore("autore@unina.it");
        assertFalse(lista.isEmpty());
    }

    @Test
    public void testGetCommentiPerRicettaSimulato() {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public ArrayList<Commento> getCommentiPerRicetta(int idRicetta) {
                ArrayList<Commento> lista = new ArrayList<>();
                Commento c = new Commento();
                c.setTesto("Buona!");
                lista.add(c);
                return lista;
            }
        };
        ArrayList<Commento> commenti = controlFake.getCommentiPerRicetta(123);
        assertEquals(1, commenti.size());
    }

    @Test(expected = RuntimeException.class)
    public void testAggiungiCommentoErroreSimulato() throws Exception {
        ControlAutore controlFake = new ControlAutore() {
            @Override
            public void aggiungiCommento(Commento commento) throws Exception {
                throw new RuntimeException("Errore finto");
            }
        };
        Commento c = new Commento();
        c.setTesto("Ottima!");
        controlFake.aggiungiCommento(c);
    }
}
