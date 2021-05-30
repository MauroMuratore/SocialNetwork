package test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lib.core.Notifica;
import lib.core.PartitaCalcioEvento;
import lib.util.Log;
import lib.util.Nomi;
import server.SocialNetwork;

class TestInviti {
	
	String invitante = "invitante";
	String usernameNN = "usernameNN";
	String usernameIN = "usernameIN";
	String usernameNP1 = "usernameNP1";
	String usernameNP2 = "usernameNP2";
	String usernameIP1 = "usernameIP1";
	String usernameIP2 = "usernameIP2";
	String password ="123123123";
	int etaMin = 5;
	SocialNetwork sn = SocialNetwork.getInstance();
	String[] cat = new String[1];
	String[] vuoto = new String[0];
	PartitaCalcioEvento eventoTest = new PartitaCalcioEvento();
	PartitaCalcioEvento eventoPassato = new PartitaCalcioEvento();
	LinkedList<String> invitati = new LinkedList<String>();
	
	@BeforeEach
	void setUp()  {
		Log.setVerbose(0);
		cat[0] = Nomi.CAT_PARTITA_CALCIO.getNome(); 
		sn.registrazione(usernameNN, password, password, etaMin, etaMin+1, vuoto);
		sn.logout();
		sn.registrazione(usernameIN, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(usernameNP1, password, password, etaMin, etaMin+1, vuoto);
		sn.logout();
		sn.registrazione(usernameNP2, password, password, etaMin, etaMin+1, vuoto);
		sn.logout();
		sn.registrazione(usernameIP1, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(usernameIP2, password, password, etaMin, etaMin+1, cat);
		sn.logout();
		sn.registrazione(invitante, password, password, etaMin, etaMin+5, cat);
		
		eventoTest.setTitolo("Test Invito");
		eventoTest.setCompresoQuota("niente");
		eventoTest.setDataFine("29/10/2022", "15:00");
		eventoTest.setDataInizio("29/10/2022", "15:00");
		eventoTest.setDurata("1");
		eventoTest.setLuogo("Brescia");
		eventoTest.setNote("niente");
		eventoTest.setPartecipantiNecessari("10");
		eventoTest.setProprietario(invitante);
		eventoTest.setQuotaIndividuale("1");
		eventoTest.setTermineUltimo("20/10/2022");
		eventoTest.setTermineUltimoRitiro("19/10/2022");
		eventoTest.setTitolo("Test inviti");
		eventoTest.setTolleranzaPartecipanti("1");
		eventoTest.setSesso("Maschio");
		eventoTest.setEta("12");
		eventoPassato.setTitolo("Evento passato");
		eventoPassato.setCompresoQuota("niente");
		eventoPassato.setDataFine("29/10/2022", "15:00");
		eventoPassato.setDataInizio("29/10/2022", "15:00");
		eventoPassato.setDurata("1");
		eventoPassato.setLuogo("Brescia");
		eventoPassato.setNote("niente");
		eventoPassato.setPartecipantiNecessari("10");
		eventoPassato.setProprietario(invitante);
		eventoPassato.setQuotaIndividuale("1");
		eventoPassato.setTermineUltimo("20/10/2022");
		eventoPassato.setTermineUltimoRitiro("19/10/2022");
		eventoPassato.setTolleranzaPartecipanti("1");
		eventoPassato.setSesso("Maschio");
		eventoPassato.setEta("12");
		eventoPassato.iscrizione(usernameNP1);
		eventoPassato.iscrizione(usernameNP2);
		eventoPassato.iscrizione(usernameIP1);
		eventoPassato.iscrizione(usernameIP2);
		sn.creaEvento(eventoPassato, new LinkedList<String>());
	}
	

	@Test
	void invitabili() {
		sn.creaEvento(eventoTest, new LinkedList<String>());
		LinkedList<String> invitabili = new LinkedList<String>();
		invitabili.add(usernameNP1);
		invitabili.add(usernameNP2);
		invitabili.add(usernameIP1);
		invitabili.add(usernameIP2);
		
		assertEquals(invitabili, sn.getPersoneInvitabili(Nomi.CAT_PARTITA_CALCIO.getNome()));
		sn.logout();
	}
	
	@Test
	void interessati() {
		sn.creaEvento(eventoTest, new LinkedList<String>());
		sn.logout();
		sn.login(usernameIN, password);
		boolean isPresent = false;
		for(Notifica not: sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.NUOVO_EVENTO_APERTO) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString()))
				isPresent=true;
		}
		assertTrue(isPresent);
		sn.logout();
		sn.login(usernameIP1, password);
		isPresent = false;
		for(Notifica not: sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.NUOVO_EVENTO_APERTO) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString()))
				isPresent=true;
		}
		assertTrue(isPresent);
		sn.logout();
		sn.login(usernameIP2, password);
		isPresent = false;
		for(Notifica not: sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.NUOVO_EVENTO_APERTO) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString()))
				isPresent=true;
		}
		assertTrue(isPresent);
		sn.logout();
	}
	
	@Test
	void invitati() {
		LinkedList<String> invitati = new LinkedList<String>();
		invitati.add(usernameIP1);
		invitati.add(usernameNP1);
		sn.creaEvento(eventoTest, invitati);
		sn.logout();
		sn.login(usernameIP1, password);
		boolean isPresent= false;
		for(Notifica not :  sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.INVITO) && not.getMessaggio().contains(invitante) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString() ))
				isPresent = true;
		}
		assertTrue(isPresent);
		sn.logout();
		sn.login(usernameNP1, password);
		isPresent= false;
		for(Notifica not :  sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.INVITO) && not.getMessaggio().contains(invitante) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString() ))
				isPresent = true;
		}
		assertTrue(isPresent);
		sn.logout();
		sn.login(usernameIP2, password);
		isPresent= false;
		for(Notifica not :  sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.INVITO) && not.getMessaggio().contains(invitante) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString() ))
				isPresent = true;
		}
		assertFalse(isPresent);
		sn.logout();
		sn.login(usernameNP2, password);
		isPresent= false;
		for(Notifica not :  sn.getUtente().getNotifiche()) {
			if(not.getMessaggio().contains(Notifica.INVITO) && not.getMessaggio().contains(invitante) &&
					not.getMessaggio().contains(eventoTest.getTitolo().getValoreString() ))
				isPresent = true;
		}
		assertFalse(isPresent);
		sn.logout();
	}

	@AfterEach
	void tearDown(){
		sn.eliminaEvento(eventoPassato);
		sn.eliminaEvento(eventoTest);
		sn.eliminaUtente(invitante);
		sn.eliminaUtente(usernameIN);
		sn.eliminaUtente(usernameIP1);
		sn.eliminaUtente(usernameIP2);
		sn.eliminaUtente(usernameNN);
		sn.eliminaUtente(usernameNP1);
		sn.eliminaUtente(usernameNP2);
	}


}
