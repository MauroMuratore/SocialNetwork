import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.core.SocialNetwork;
import server.core.Utente;
import server.database.ConsultaDB;
import util.Nomi;

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
	public void testLogin() {
		//test con password sbagliata
		String ritorno = sn.login(utest.getUsername(), "utenteTes"); 
		assertEquals(Nomi.SN_PW_SBAGLIATA.getNome(), ritorno);
		
		//test con password vuota
		ritorno = sn.login(utest.getUsername(), "");
		assertEquals(Nomi.SN_PW_SBAGLIATA.getNome(), ritorno);
		
		//test con nome sbagliato
		ritorno = sn.login("utente", utest.getPassword());
		assertEquals(Nomi.SN_ID_INESISTENTE.getNome(), ritorno);
		
		//test con nome vuote
		ritorno = sn.login("", utest.getPassword());
		assertEquals(Nomi.SN_ID_INESISTENTE.getNome(), ritorno);
		
		//test con nome e password corretti
		ritorno = sn.login(utest.getUsername(), utest.getPassword());
		assertEquals(Nomi.SN_BENVENUTO.getNome(), ritorno);
		assertEquals(utest.getUsername(), sn.getUtente().getUsername());
	}
		
	@After
	public void reset() {
		cdb.cancellaUtente(utest.getUsername());
	}
	

}
