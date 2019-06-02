package database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cervello.Campo;
import cervello.Categoria;
import cervello.Evento;
import cervello.Notifica;
import cervello.PartitaCalcioCat;
import cervello.PartitaCalcioEvento;
import cervello.Utente;

public class LeggiXML {

	/**
	 * legge da file gli utenti
	 * @param username
	 * @return il nodo utente letto oppure null
	 */
	public Element leggiUtente(String username) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder=factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_UTENTI.getNome()));


		} catch (SAXException sax) {
			sax.getStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		NodeList lista =doc.getElementsByTagName(NomiDB.TAG_UTENTE.getNome());
		for(int i=0; i<lista.getLength(); i++) {
			Element node = (Element) lista.item(i);
			if (node.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent().equals(username)) {
				return node;
			}

		}


		return null;
	}


	public PartitaCalcioCat leggiPartitaCalcioCat() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		PartitaCalcioCat ritorno;
		try {
			builder=factory.newDocumentBuilder();
			doc=builder.parse(new File(NomiDB.FILE_PARTITA_CALCIO.getNome()));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Element categoria = (Element) doc.getElementsByTagName(NomiDB.TAG_CATEGORIA.getNome()).item(0);
		String nome = categoria.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
		String descrizione = categoria.getElementsByTagName(NomiDB.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
		ArrayList<PartitaCalcioEvento> bacheca = new ArrayList<PartitaCalcioEvento>(leggiListaPartiteCalcio(doc));
		ritorno = new PartitaCalcioCat<>(nome, descrizione, bacheca);
		return ritorno;
	}

	public ArrayList<PartitaCalcioEvento> leggiListaPartiteCalcio(Document doc) {
		Element lista = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		ArrayList<PartitaCalcioEvento> ritorno = new ArrayList<PartitaCalcioEvento>();
		for(int i=0; i<lista.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).getLength(); i++) {
			Element newEvento = (Element) lista.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).item(i);
			ritorno.add(leggiPartitaCalcioEvento(newEvento));

		}

		return ritorno;
	}

	public PartitaCalcioEvento leggiPartitaCalcioEvento(Element evento) {
		int id = Integer.parseInt(evento.getAttribute("id"));
		Campo<String> titolo = leggiCampo(evento, String.class, NomiDB.CAMPO_TITOLO);
		Campo<Integer> nPartecipanti = leggiCampo(evento, Integer.class, NomiDB.CAMPO_PARTECIPANTI_MAX);
		Campo<GregorianCalendar> termineUltimo = leggiCampo(evento, GregorianCalendar.class, NomiDB.CAMPO_TERMINE_ULTIMO);
		Campo<String> luogo = leggiCampo(evento, String.class, NomiDB.CAMPO_LUOGO);
		Campo<GregorianCalendar> dataInizio= leggiCampo(evento, GregorianCalendar.class, NomiDB.CAMPO_DATA_INIZIO);
		Campo<Integer> durata = leggiCampo(evento, Integer.class, NomiDB.CAMPO_DURATA);
		Campo<GregorianCalendar> dataFine= leggiCampo(evento, GregorianCalendar.class, NomiDB.CAMPO_DATA_FINE);
		Campo<Integer> quotaIndividuale = leggiCampo(evento, Integer.class, NomiDB.CAMPO_QUOTA_IND);
		Campo<String> compresoQuota = leggiCampo(evento, String.class, NomiDB.CAMPO_COMPRESO_QUOTA);
		Campo<String> nota = leggiCampo(evento, String.class, NomiDB.CAMPO_NOTE);
		Campo<String> sesso = leggiCampo(evento, String.class, NomiDB.CAMPO_SESSO);
		Campo<Integer> eta = leggiCampo(evento, Integer.class, NomiDB.CAMPO_ETA);
		LinkedList<String> partecipanti = leggiPartecipanti(evento);


		PartitaCalcioEvento ritorno = new PartitaCalcioEvento(id, titolo, nPartecipanti, partecipanti, termineUltimo, luogo, dataInizio,
				durata, quotaIndividuale, compresoQuota, dataFine, nota, sesso, eta);
		return ritorno;
	}

	/**
	 * legge la partita 
	 * @param id unico
	 * @return
	 */
	public PartitaCalcioEvento leggiPartitaCalcioEvento(int id) {
		PartitaCalcioCat<PartitaCalcioEvento> cat = leggiPartitaCalcioCat();
		ArrayList<PartitaCalcioEvento> list = new ArrayList<PartitaCalcioEvento>(cat.getBacheca());
		
		for(PartitaCalcioEvento pce : list) {
			if (pce.getIdEvento()==id)
				return pce;
		}
		return null;
	}
	
	public Campo leggiCampoDaNodo(Element campo, Class classeValore) {
		String nome = campo.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
		String descrizione = campo.getElementsByTagName(NomiDB.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
		String valoreToString = campo.getElementsByTagName(NomiDB.TAG_VALORE.getNome()).item(0).getTextContent();

		Campo ritorno = null;
		if(classeValore.equals(String.class)) {
			ritorno = new Campo<String>(nome, descrizione, valoreToString, true);
		}
		else if(classeValore.equals(Integer.class)) {
			ritorno = new Campo<Integer>(nome, descrizione, Integer.parseInt(valoreToString), true);
		}
		else if(classeValore.equals(GregorianCalendar.class)) {
			GregorianCalendar data = new GregorianCalendar();
			Element valore = (Element) campo.getElementsByTagName(NomiDB.TAG_VALORE.getNome()).item(0);
			int anno = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_ANNO.getNome()).item(0).getTextContent());
			int mese = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_MESE.getNome()).item(0).getTextContent());
			int giorno = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_GIORNO.getNome()).item(0).getTextContent());
			int ora = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_ORA.getNome()).item(0).getTextContent());

			data.set(anno, mese, giorno, ora, 0);

			ritorno = new Campo<GregorianCalendar>(nome, descrizione, data, true); 
		}


		return ritorno;
	}

	public LinkedList<String> leggiPartecipanti(Element evento){
		LinkedList<String> lista = new LinkedList<String>();
		Element nodoLista = (Element) evento.getElementsByTagName(NomiDB.CAMPO_PARTECIPANTI.getNome()).item(0);
		for(int i=0; i<nodoLista.getElementsByTagName(NomiDB.TAG_NOME.getNome()).getLength(); i++) {
			String username = nodoLista.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(i).getTextContent();
			lista.add(username);
		}

		return lista;

	}

	public Campo leggiCampo(Element evento, Class classeValore, NomiDB nomeCampo) {
		NodeList campiLista = evento.getElementsByTagName(NomiDB.TAG_CAMPO.getNome());
		Campo ritorno=null;
		for(int i=0; i<campiLista.getLength(); i++) {
			Element campo= ((Element) campiLista.item(i));
			String testo =campo.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
			if(nomeCampo.getNome().equals(testo)) {
				ritorno = leggiCampoDaNodo(campo, classeValore);
				break;
			}

		}

		return ritorno;

	}

	
	
	public LinkedList<Notifica> leggiNotifiche(Element listaNotifiche){
		LinkedList<Notifica> ritorno = new LinkedList<Notifica>();
		NodeList lista = listaNotifiche.getElementsByTagName(NomiDB.TAG_NOTIFICA.getNome());
		for(int i=0; i<lista.getLength(); i++) {
			Element nodoNotifica = (Element) lista.item(i);
			Element nodoMessaggio = (Element) nodoNotifica.getElementsByTagName(NomiDB.TAG_DESCRIZIONE.getNome()).item(0);
			Element nodoID = (Element) nodoNotifica.getElementsByTagName(NomiDB.TAG_ID.getNome()).item(0);
			Element nodoLetto = (Element) nodoNotifica.getElementsByTagName(NomiDB.TAG_LETTO.getNome()).item(0);
			
			String messaggio = nodoMessaggio.getTextContent();
			Evento evento = leggiPartitaCalcioEvento(Integer.parseInt(nodoID.getTextContent()));
			boolean letto = Boolean.valueOf(nodoLetto.getTextContent());
			ritorno.add(new Notifica(evento, messaggio, letto));
			
		}
		
		return ritorno;
	}

	/**
	 * torna l'oggetto utente
	 * @param id
	 * @return
	 */
	public Utente caricaUtente(String id) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;

		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_UTENTI.getNome()));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element elenco = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		NodeList lista = elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome());
		Element utente=null;
		for(int i=0; i<lista.getLength(); i++) {
			if(id.equals(((Element) lista.item(i)).getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent()))
				utente = (Element) lista.item(i);
		}
		
		String nome = utente.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
		byte[] hash = utente.getElementsByTagName(NomiDB.TAG_HASH.getNome()).item(0).getTextContent().getBytes();
		Element notifiche = (Element) utente.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		Utente ritorno = new Utente(nome, hash, leggiNotifiche(notifiche));
		return ritorno;
	}




}















