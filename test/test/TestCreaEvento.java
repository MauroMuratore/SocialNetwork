package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;

class TestCreaEvento {
	
	PartitaCalcioEvento pce;
	EscursioneMontagnaEvento eme;
	
	@BeforeEach
	void setUp() {
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
		
	}
	
	@Test
	void testLuogo() {
	}
	
	@Test
	void testPartecipantiNecessari() {
	}
	
	@Test
	void testTermineUltimo() {
	}
	
	@Test
	void testTermineUltimoRitiro() {
		
	}
		
	
	@Test 
	void testPartitaValida() {
		
	}
	
	@Test
	void TestEscursioneValido() {
		
	}

}
