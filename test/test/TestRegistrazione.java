package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lib.core.Utente;
import lib.util.Log;
import lib.util.Nomi;
import server.database.ConsultaDB;
import server.gestori.GestoreServizi;

public class TestRegistrazione {

	Utente utest;
	GestoreServizi sn;
	ConsultaDB cdb;
	String username, stringaCorta, stringaVuota;
	int etaMin, etaMax;
	String[] categorie;
	String pw, conferma;

	String ritorno;

	@Before
	public void setUp() {
		Log.setVerbose(0);
		utest = new Utente ("utenteRegistrato", "utenteRegistrato", 1, 10);
		sn = GestoreServizi.getInstance();
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
	public void testUserVuoto() {
		//test con username vuoto
		ritorno = sn.registrazione(stringaVuota, pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_ID_CORTO.getNome(), ritorno);

	}

	@Test
	public void testUserCorto() {
		//test con username.lenght=6
		ritorno = sn.registrazione(stringaCorta, pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_ID_CORTO.getNome(), ritorno);
	}

	@Test
	public void testUserInUso() {
		//test con username gia' in uso
		ritorno = sn.registrazione(utest.getUsername(), pw, conferma, etaMin, etaMax, categorie);

		assertEquals(Nomi.SN_ID_IN_USO.getNome(), ritorno);
	}

	@Test
	public void testPWCorta() {
		//test con pw corta (<7)
		ritorno = sn.registrazione(username, stringaCorta, conferma, etaMin, etaMax, categorie);
		System.out.println("pw corta" +ritorno);
		assertEquals(Nomi.SN_PW_CORTA.getNome(), ritorno);
	}

	@Test
	public void testPWVuota() {
		//test con pw vuota
		ritorno = sn.registrazione(username, stringaVuota, conferma, etaMin, etaMax, categorie);

		assertEquals(Nomi.SN_PW_CORTA.getNome(), ritorno);

	}

	@Test
	public void testConfermaVuota() {
		//test con conferma vuota
		ritorno = sn.registrazione(username, pw, stringaVuota, etaMin, etaMax, categorie);

		assertEquals(Nomi.SN_PW_DIVERSE.getNome(), ritorno);
	}

	@Test
	public void testPWConfermaVuoti() {
		//test con pw vuota e conferma vuota
		ritorno = sn.registrazione(username, stringaVuota, stringaVuota, etaMin, etaMax, categorie);

		assertEquals(Nomi.SN_PW_CORTA.getNome(), ritorno);

	}

	@Test
	public void testPWConfermaDiversi() {
		//test con conferma diversa
		ritorno = sn.registrazione(username, pw, "passwordTes", etaMin, etaMax, categorie);

		assertEquals(Nomi.SN_PW_DIVERSE.getNome(), ritorno);
	}

	@Test
	public void testEtaNegative() {
		//test con input negativi per etaMin e etaMax
		ritorno = sn.registrazione(username, pw, conferma, -5, -2 , categorie);

		assertEquals(Nomi.SN_NUMERO_NEGATIVO.getNome(), ritorno);

	}

	@Test
	public void testEtaMinMaggioreEtaMax() {
		//test con etaMin e etaMax validi, etaMin > etaMax
		ritorno = sn.registrazione(username, pw, conferma, 1, 0, categorie);

		assertEquals(Nomi.SN_ETAMIN_MAGG_ETAMAX.getNome(), ritorno);
	}

	@Test
	public void testRegCorretta() {
		ritorno = sn.registrazione(username, pw, conferma, etaMin, etaMax, categorie);
		assertEquals(Nomi.SN_BENVENUTO.getNome(), ritorno);
		assertEquals(username, sn.getUtente().getUsername());
		cdb.eliminaUtente(username);
	}


	@After
	public void tearDown() {
		cdb.eliminaUtente(utest.getUsername());
		
	}
























































}
