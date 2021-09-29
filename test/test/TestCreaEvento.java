 package test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lib.core.Campo;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;
import lib.util.Log;
import lib.util.Nomi;

class TestCreaEvento {
	
	PartitaCalcioEvento pce;
	EscursioneMontagnaEvento eme;
	
	@BeforeEach
	void setUp() {
		Log.setVerbose(0);
		pce = new PartitaCalcioEvento();
		eme = new EscursioneMontagnaEvento();
	}

	@Test
	void testTitolo() {
		String ritorno = pce.setTitolo("");
		assertEquals(pce.getTitolo().getNome() + Evento.VUOTO, ritorno);
		ritorno = pce.setTitolo("titolo");
		assertEquals(Evento.OK, ritorno);
	}
	
	@Test
	void testDataInizio() {	
		String ritorno = pce.setDataInizio("input", "");
		assertEquals(Nomi.CAMPO_DATA_INIZIO.getNome() + Campo.FORMATO_SBAGLIATO, ritorno);
		ritorno = pce.setDataInizio("input/non/valido", "no:no");
		assertEquals(Nomi.CAMPO_DATA_INIZIO.getNome() + Campo.FORMATO_SBAGLIATO, ritorno);
		ritorno = pce.setDataInizio("10/11/2022", "no:no");
		assertEquals(Nomi.CAMPO_DATA_INIZIO.getNome() + Campo.FORMATO_INTERO_SBAGLIATO, ritorno);
		ritorno= pce.setDataInizio("3/2/1990", "12:00");
		assertEquals(Evento.DATA_PASSATA, ritorno);
		ritorno = pce.setDataInizio("10/11/2022", "12:00");
		assertEquals(Campo.OK, ritorno);
	}
	
	@Test
	void testDataFinale() {
		pce.setDataInizio("10/11/2022", "12:00");
		String ritorno = pce.setDataFine("9/11/2022", "1:00");
		assertEquals(Evento.FINE_PRIMA_INZIO, ritorno);
		ritorno = pce.setDataFine("11/11/2022", "11:00");
		assertEquals(Evento.OK, ritorno);
	}
	
	@Test
	void testLuogo() {
		String ritorno = pce.setLuogo("");
		assertEquals(pce.getLuogo().getNome() + Evento.VUOTO, ritorno);
		ritorno = pce.setTitolo("luogo");
		assertEquals(Evento.OK, ritorno);
	}
	
	@Test
	void testPartecipantiNecessari() {
		String ritorno = pce.setPartecipantiNecessari("ventordici");
		assertEquals(Nomi.CAMPO_PARTECIPANTI_MIN.getNome() + Campo.FORMATO_INTERO_SBAGLIATO, ritorno);
		ritorno=pce.setPartecipantiNecessari("-5");
		assertEquals(Nomi.CAMPO_PARTECIPANTI_MIN.getNome() + Campo.FORMATO_INTERO_SBAGLIATO, ritorno);
		ritorno = pce.setPartecipantiNecessari("1");
		assertEquals(Campo.OK, ritorno);
	}
	
	@Test
	void testTermineUltimo() {
		pce.setDataInizio("10/11/2022", "12:00");
		
		String ritorno = pce.setTermineUltimo("12/12/2022");
		assertEquals(Evento.DATA_ISCRIZIONE_NON_VALIDA, ritorno);
		
		ritorno = pce.setTermineUltimo("9/11/2022");
		assertEquals(Campo.OK, ritorno);
		
	}
	
	@Test
	void testTermineUltimoRitiro() {
		pce.setDataInizio("10/11/2022", "12:00");
		pce.setTermineUltimo("9/11/2022");
		
		assertEquals(pce.getTermineUltimo().getValore(), pce.getTermineUltimoRitiro().getValore());
		
		String ritorno =pce.setTermineUltimoRitiro("12/11/2022");
		assertEquals(Evento.TUR_DOPO_TUI, ritorno);
		
		ritorno = pce.setTermineUltimoRitiro("8/11/2022");
		assertEquals(Evento.OK, ritorno);
	}
	
	@Test 
	void testPartitaValida() {
		pce.setTitolo("titolo");
		assertFalse(pce.valido());
		pce.setPartecipantiNecessari("2");
		assertFalse(pce.valido());
		pce.setDataInizio("12/1/2022", "15:00");
		assertFalse(pce.valido());
		pce.setTermineUltimo("10/1/2022");
		assertFalse(pce.valido());
		pce.setQuotaIndividuale("10");
		assertFalse(pce.valido());
		pce.setLuogo("qui");
		assertFalse(pce.valido());
		pce.setSesso("maschio");
		assertTrue(pce.valido());

		
	}
	
	@Test
	void TestEscursioneValido() {
		eme.setTitolo("titolo");
		assertFalse(eme.valido());
		eme.setPartecipantiNecessari("2");
		assertFalse(eme.valido());
		eme.setDataInizio("12/1/2022", "15:00");
		assertFalse(eme.valido());
		eme.setTermineUltimo("10/1/2022");
		assertFalse(eme.valido());
		eme.setQuotaIndividuale("10");
		assertFalse(eme.valido());
		eme.setLuogo("qui");
		assertFalse(eme.valido());
		eme.setAttrezzatura("1");
		assertFalse(eme.valido());
		eme.setIstruttore("2");
		assertTrue(eme.valido());
		
	}

}
