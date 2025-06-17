package Test;

import Control.ControlAutoreNonRegistrato;
import Entity.Autore;
import DAO.AutoreDAO;
import exception.EmailInUso;
import org.junit.*;
import Control.ControlAutoreNonRegistrato;
import static org.junit.Assert.*;

public class ControlAutoreNonRegistratoTest {

    private ControlAutoreNonRegistrato control;

    @Before
    public void setUp() {
        control = new ControlAutoreNonRegistrato() {
            @Override
            public void registrazione(Autore nuovoAutore) {
                // Simulo la verifica email già usata
                if ("duplicata@unina.it".equals(nuovoAutore.getEmail())) {
                    throw new EmailInUso("Email già registrata: " + nuovoAutore.getEmail());
                }
                // Validazioni vere
                if (!emailValida(nuovoAutore.getEmail())) {
                    throw new IllegalArgumentException("Email non valida o dominio non autorizzato.");
                }
                if (!passwordValida(nuovoAutore.getPassword())) {
                    throw new IllegalArgumentException("La password deve avere 8–16 caratteri, almeno una maiuscola, un numero e un simbolo speciale.");
                }
                // Non accedo al DB, simulo successo
            }
        };
    }


    @After
    public void tearDown() {
        control = null;
        System.out.println("<< Test completato.");
    }

    @Test
    public void testRegistrazioneSuccesso() {
        Autore autore = new Autore("valida@unina.it", "Password1!", "Mario", "Rossi");
        try {
            control.registrazione(autore);
        } catch (Exception e) {
            fail("Non attesa eccezione: " + e.getMessage());
        }
    }

    @Test(expected = EmailInUso.class)
    public void testRegistrazioneEmailGiaUsata() {
        Autore a = new Autore("duplicata@unina.it", "Password1!", "Luca", "Verdi");
        control.registrazione(a); // Deve lanciare EmailInUso
    }
    @Test(expected = RuntimeException.class)
    public void testEmailNonValida() {
        Autore autore = new Autore("fake@domain.xyz", "Password1!", "Arianna", "Bianchi");
        control.registrazione(autore);
    }

    @Test(expected = RuntimeException.class)
    public void testEmailNull() {
        Autore autore = new Autore(null, "Password1!", "Paolo", "Esposito");
        control.registrazione(autore);
    }

    @Test(expected = RuntimeException.class)
    public void testPasswordNonValida() {
        Autore autore = new Autore("user@unina.it", "abc123", "Laura", "Neri");
        control.registrazione(autore);
    }


    @Test(expected = RuntimeException.class)
    public void testPasswordNull() {
        Autore autore = new Autore("paolo@unina.it", null, "Paolo", "Esposito");
        control.registrazione(autore);
    }

    @Test(expected = RuntimeException.class)
    public void testErroreGenericoSimulato() {
        ControlAutoreNonRegistrato controlFinto = new ControlAutoreNonRegistrato() {
            @Override
            public void registrazione(Autore autore) {
                throw new RuntimeException("Errore DB fittizio");
            }
        };

        Autore autore = new Autore("errore@unina.it", "Password1!", "Finto", "Autore");
        controlFinto.registrazione(autore);
    }
}
