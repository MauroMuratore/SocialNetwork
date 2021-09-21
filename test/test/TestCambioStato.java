package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lib.core.PartitaCalcioEvento;
import lib.core.StatoEvento;
import lib.util.Log;
import lib.util.Nomi;
import server.GestoreServizi;

class TestCambioStato{
	
	PartitaCalcioEvento pce = new PartitaCalcioEvento();
	String creatore = "creatore";
	String username1 = "username1";
	String username2 = "username2";
	String username3 = "username3";
	String username4 = "username4";
	String username5 = "username5";
	String password ="123123123";
	int etaMin = 5;
	String[] cat = new String[1];
	GestoreServizi sn = GestoreServizi.getInstance();
	
	@BeforeEach
	void setUp() throws Exception {
		Log.setVerbose(0);
		cat[0] = Nomi.CAT_PARTITA_CALCIO.getNome(); 
		sn.registrazione(username1, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(username2, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(username3, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(username4, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(username5, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(creatore, password, password, etaMin, etaMin+1, cat);
		pce.setTitolo("titolo");
		pce.setPartecipantiNecessari("3");
		pce.setTolleranzaPartecipanti("1");
		pce.setDataInizio("12/1/2022", "15:00");
		pce.setTermineUltimo("10/1/2022");
		pce.setTermineUltimoRitiro("8/1/2022");
		pce.setQuotaIndividuale("10");
		pce.setLuogo("qui");
		pce.setSesso("maschio");
		sn.creaEvento(pce, new LinkedList<String>());
		
	}

	@AfterEach
	void tearDown() throws Exception {
		sn.logout();
		sn.eliminaEvento(pce);
		sn.eliminaUtente(username1);
		sn.eliminaUtente(username2);
		sn.eliminaUtente(username3);
		sn.eliminaUtente(username4);
		sn.eliminaUtente(username5);
		sn.eliminaUtente(creatore);
	}

	@Test
	void testApertoFallito() {
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.set(2022, 3, 12);
		pce.testCambioStato(oggi);
		assertEquals(StatoEvento.FALLITO, pce.getStato());
		
	}
	
	@Test
	void testApertoCancellato() {
		sn.cancellaEvento(pce);
		assertEquals(StatoEvento.CANCELLATO, pce.getStato());
	}
	
	@Test
	void testApertoAperto() {
		sn.logout();
		sn.login(username1, password);
		sn.iscrizione(pce);
		assertEquals(StatoEvento.APERTO, pce.getStato());
	}
	
	@Test 
	void testApertoChiusoNoMaxPartecipanti(){
		sn.logout();
		sn.login(username1, password);
		sn.iscrizione(pce);
		sn.logout();
		sn.login(username2, password);
		sn.iscrizione(pce);
		assertEquals(StatoEvento.APERTO, pce.getStato());
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.set(2022, 3, 12);
		pce.testCambioStato(oggi);
		assertEquals(StatoEvento.CHIUSO, pce.getStato());
	}
	
	@Test
	void testApertoChiusoMaxPartecipanti() {
		sn.logout();
		sn.login(username1, password);
		sn.iscrizione(pce);
		sn.logout();
		sn.login(username2, password);
		sn.iscrizione(pce);
		sn.logout();
		sn.login(username3, password);
		sn.iscrizione(pce);
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.set(2022, 1, 9);
		pce.testCambioStato(oggi);
		assertEquals(StatoEvento.CHIUSO, pce.getStato());
	}
	
	@Test
	void testChiusoConcluso() {
		sn.logout();
		sn.login(username1, password);
		sn.iscrizione(pce);
		sn.logout();
		sn.login(username2, password);
		sn.iscrizione(pce);
		sn.logout();
		sn.login(username3, password);
		sn.iscrizione(pce);
		GregorianCalendar oggi = new GregorianCalendar();
		oggi.set(2022, 1, 9);
		pce.testCambioStato(oggi);
		assertEquals(StatoEvento.CHIUSO, pce.getStato());
		oggi.set(2022, 3, 15);
		pce.testCambioStato(oggi);
		assertEquals(StatoEvento.CONCLUSO, pce.getStato());
	}
		

}
