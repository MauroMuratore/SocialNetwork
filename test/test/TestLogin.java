package test;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lib.core.Utente;
import lib.util.Nomi;
import server.SocialNetwork;
import server.database.ConsultaDB;

public class TestLogin {

	Utente utest;
	SocialNetwork sn;
	ConsultaDB cdb;
	
	@Before
	public void setUp() {
		sn = SocialNetwork.getInstance();
		utest = new Utente ("utenteTest", "utenteTest", 1, 10);
		cdb = ConsultaDB.getInstance();
		cdb.salvaUtente(utest);		
	}
	
	@Test
	public void testLoginPWSbagliata() {
		//test con password sbagliata
		String ritorno = sn.login(utest.getUsername(), "utenteTes"); 
		assertEquals(Nomi.SN_PW_SBAGLIATA.getNome(), ritorno);
		
	}
	
	@Test
	public void testLoginPWVuota() {
		//test con password vuota
		String ritorno = sn.login(utest.getUsername(), "");
		assertEquals(Nomi.SN_PW_SBAGLIATA.getNome(), ritorno);
	}
	
	@Test
	public void testLoginNomeSbagliato() {
		//test con nome sbagliato
		String ritorno = sn.login("utente", utest.getPassword());
		assertEquals(Nomi.SN_ID_INESISTENTE.getNome(), ritorno);
	}
	
	@Test
	public void testLoginNomeVuoto(){
		//test con nome vuote
		String ritorno = sn.login("", utest.getPassword());
		assertEquals(Nomi.SN_ID_INESISTENTE.getNome(), ritorno);
	
	}
	
	@Test
	public void testLoginCorretto() {
		String ritorno = sn.login(utest.getUsername(), utest.getPassword());
		assertEquals(Nomi.SN_BENVENUTO.getNome(), ritorno);
		assertEquals(utest.getUsername(), sn.getUtente().getUsername());
	}
		
	@After
	public void reset() {
		cdb.eliminaUtente(utest.getUsername());
	}
	

}
