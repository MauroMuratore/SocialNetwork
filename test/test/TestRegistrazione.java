package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lib.core.Campo;
import lib.core.Utente;
import lib.util.Nomi;
import server.SocialNetwork;
import server.database.ConsultaDB;

public class TestRegistrazione {

	Utente utest;
	SocialNetwork sn;
	ConsultaDB cdb;
	String username, stringaCorta, stringaVuota;
	int etaMin, etaMax;
	String[] categorie;
	String pw, conferma;

	@Before
	public void setUp() {
		utest = new Utente ("utenteRegistrato", "utenteRegistrato", 1, 10);
		sn = SocialNetwork.getInstance();
		cdb = ConsultaDB.getInstance();
		cdb.salvaUtente(utest);
		username = "utenteT";
		stringaCorta="123456";
		stringaVuota="";
		etaMin = 1;
		etaMax = 10;
		categorie = new String[0];
		pw = conferma = "passwordTest";
	}

	@Test
	public void test() {
		//test con username vuoto
		String ritorno = sn.registrazione(stringaVuota, pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_ID_CORTO.getNome(), ritorno);

		//test con username.lenght=6
		ritorno = sn.registrazione(stringaCorta, pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_ID_CORTO.getNome(), ritorno);

		//test con username gia' in uso
		ritorno = sn.registrazione(utest.getUsername(), pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_ID_IN_USO.getNome(), ritorno);

		//test con pw corta (<7)
		ritorno = sn.registrazione(username, stringaCorta, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_PW_CORTA.getNome(), ritorno);

		//test con pw vuota
		ritorno = sn.registrazione(username, stringaVuota, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_PW_CORTA.getNome(), ritorno);

		//test con conferma vuota
		ritorno = sn.registrazione(username, pw, stringaVuota, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_PW_DIVERSE.getNome(), ritorno);
		
		//test con pw vuota e conferma vuota
		ritorno = sn.registrazione(username, stringaVuota, stringaVuota, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_PW_CORTA.getNome(), ritorno);

		//test con conferma diversa
		ritorno = sn.registrazione(username, pw, "passwordTes", etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_PW_DIVERSE.getNome(), ritorno);
		
		/*
		//test con input vuoti per etaMin e etaMax
		ritorno = sn.registrazione(username, pw, conferma, stringaVuota, stringaVuota, categorie);
		assertEquals(Campo.STRINGA_VUOTA, ritorno);
		
		//test con input non validi per etaMin e etaMax
		ritorno = sn.registrazione(username, pw, conferma, "abcd", "!£$%", categorie);
		assertEquals(Campo.FORMATO_INTERO_SBAGLIATO, ritorno);
		
		//test con input negativi per etaMin e etaMax
		ritorno = sn.registrazione(username, pw, conferma, "-5", "-2" , categorie);
		assertEquals(Campo.FORMATO_INTERO_SBAGLIATO, ritorno);

		//test con etaMin e etaMax validi, etaMin > etaMax
		ritorno = sn.registrazione(username, pw, conferma, "1", "0", categorie);
		assertEquals(SocialNetwork.ETAMIN_MAGG_ETAMAX, ritorno);
		*/
		//test per registrazione corretta
		ritorno = sn.registrazione(username, pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_BENVENUTO.getNome(), ritorno);
		assertEquals(username, sn.getUtente().getUsername());
	}
	
	@After
	public void reset() {
		cdb.cancellaUtente(utest.getUsername());
		cdb.cancellaUtente(username);
	}
























































}
