package server.database;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lib.core.categorie.Categoria;
import lib.core.categorie.EscursioneMontagnaCat;
import lib.core.categorie.PartitaCalcioCat;
import lib.core.eventi.Campo;
import lib.core.eventi.EscursioneMontagnaEvento;
import lib.core.eventi.Evento;
import lib.core.eventi.PartitaCalcioEvento;
import lib.core.eventi.StatoEvento;
import lib.core.utenti.Invito;
import lib.core.utenti.Notifica;
import lib.core.utenti.Utente;
import lib.util.Log;
import lib.util.Nomi;

public class LeggiXML {

	private FactoryDocXML fdx;
	private String os;
	
	public LeggiXML() {
		fdx = new FactoryDocXML();
		os = System.getProperty("os.name");
	}

	/**
	 * legge da file gli utenti
	 * @param username
	 * @return il nodo utente letto oppure null
	 */
	public boolean controllaUtente(String username) {
		Document doc = null;
		if(os.startsWith("Windows"))
			doc= fdx.creaDocument(Nomi.FILE_UTENTI_WIN);
		else
			doc = fdx.creaDocument(Nomi.FILE_UTENTI);
		if(getUtenteNodo(doc, username)==null)
			return false;

		return true;
	}

	public boolean controllaPW(String pw, String username) {
		Document doc = null;
		if(os.startsWith("Windows"))
			doc= fdx.creaDocument(Nomi.FILE_UTENTI_WIN);
		else
			doc = fdx.creaDocument(Nomi.FILE_UTENTI);Element nodoUtente = getUtenteNodo(doc, username);
		Element hashNode = (Element) nodoUtente.getElementsByTagName(Nomi.TAG_HASH.getNome()).item(0);
		String conferma = hashNode.getTextContent();
		if(pw.equals(conferma)) {
			return true;
		}
		return false;
	}

	private Element getUtenteNodo(Document doc, String username ) {
		NodeList lista =doc.getElementsByTagName(Nomi.TAG_UTENTE.getNome());
		Element ritorno=null;
		for(int i=0; i<lista.getLength(); i++) {
			Element node = (Element) lista.item(i);
			if (node.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent().equals(username)) {
				ritorno = node;
				return ritorno;
			}

		}
		return ritorno;
	}


	public Categoria leggiCategoria(String categoria) {
		Nomi nomeFile=null;
		if(categoria.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_NOTIFICHE_PENDENTI_WIN;
			else 
				nomeFile = Nomi.FILE_PARTITA_CALCIO;
		}
		else if(categoria.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA_WIN;
			else 
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA;
		}
		Document doc = fdx.creaDocument(nomeFile);
		Categoria ritorno = null;

		Element categoriaNodo = (Element) doc.getElementsByTagName(Nomi.TAG_CATEGORIA.getNome()).item(0);
		String nome = categoriaNodo.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
		String descrizione = categoriaNodo.getElementsByTagName(Nomi.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
		LinkedList<String> personeInteressate = new LinkedList<String>();
		for(int i=0; i<categoriaNodo.getElementsByTagName(Nomi.PERSONE_INTERESSATE.getNome()).getLength(); i++) {
			Element utente = (Element) categoriaNodo.getElementsByTagName(Nomi.PERSONE_INTERESSATE.getNome()).item(i);
			String nomeUtente = utente.getTextContent();
			personeInteressate.add(nomeUtente);
		}
		ArrayList<Evento> bacheca = new ArrayList<Evento>(leggiListaEventi(doc, categoria));

		if(categoria.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) 			
			ritorno = new PartitaCalcioCat<>(nome, descrizione, bacheca, personeInteressate);
		else if(categoria.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()))
			ritorno = new EscursioneMontagnaCat<>(nome, descrizione, bacheca, personeInteressate);
		
		Log.writeRoutineLog(getClass(), "Lettura categoria", Log.MEDIUM_PRIORITY);
		return ritorno;
	}

	public ArrayList<Evento> leggiListaEventi(Document doc, String categoria) {
		Element lista = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		ArrayList<Evento> ritorno = new ArrayList<Evento>();
		for(int i=0; i<lista.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).getLength(); i++) {
			Element newEvento = (Element) lista.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).item(i);
			ritorno.add(leggiEvento(newEvento, categoria));

		}

		return ritorno;
	}


	public Evento leggiEvento(Element evento, String categoria) {
		Evento ritorno = null;
		//PARTE COMUNE A TUTTI GLI EVENTI
		int id = Integer.parseInt(evento.getAttribute("id"));
		String proprietario = evento.getElementsByTagName(Nomi.CAMPO_PROPRIETARIO.getNome()).item(0).getTextContent();
		Campo<String> titolo = leggiCampo(evento, String.class, Nomi.CAMPO_TITOLO);
		Campo<Integer> nPartecipanti = leggiCampo(evento, Integer.class, Nomi.CAMPO_PARTECIPANTI_MIN);
		Campo<GregorianCalendar> termineUltimo = leggiCampo(evento, GregorianCalendar.class, Nomi.CAMPO_TERMINE_ULTIMO);
		Campo<String> luogo = leggiCampo(evento, String.class, Nomi.CAMPO_LUOGO);
		Campo<GregorianCalendar> dataInizio= leggiCampo(evento, GregorianCalendar.class, Nomi.CAMPO_DATA_INIZIO);
		Campo<Integer> durata = leggiCampo(evento, Integer.class, Nomi.CAMPO_DURATA);
		Campo<GregorianCalendar> dataFine= leggiCampo(evento, GregorianCalendar.class, Nomi.CAMPO_DATA_FINE);
		Campo<Integer> quotaIndividuale = leggiCampo(evento, Integer.class, Nomi.CAMPO_QUOTA_IND);
		Campo<String> compresoQuota = leggiCampo(evento, String.class, Nomi.CAMPO_COMPRESO_QUOTA);
		Campo<String> nota = leggiCampo(evento, String.class, Nomi.CAMPO_NOTE);
		Campo<GregorianCalendar> termineUltimoRitiro = leggiCampo(evento, GregorianCalendar.class, Nomi.CAMPO_TERMINE_ULTIMO_RITIRO);
		Campo<Integer> tolleranza = leggiCampo(evento, Integer.class, Nomi.CAMPO_TOLLERANZA);
		LinkedList<String> partecipanti = leggiPartecipanti(evento, Nomi.CAMPO_PARTECIPANTI.getNome());

		StatoEvento statoEvento = leggiStatoEvento(evento);

		//SOLO PARTITE DI CALCIO
		if(categoria.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			Campo<String> sesso = leggiCampo(evento, String.class, Nomi.CAMPO_SESSO);
			Campo<Integer> eta = leggiCampo(evento, Integer.class, Nomi.CAMPO_ETA);
			ritorno = new PartitaCalcioEvento(id, titolo, nPartecipanti, partecipanti,
					proprietario, termineUltimo, termineUltimoRitiro, luogo, dataInizio,
					durata, quotaIndividuale, compresoQuota, dataFine, nota, tolleranza, 
					sesso, eta, statoEvento);
		}

		else if(categoria.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			Campo<Integer> istruttore = leggiCampo(evento, Integer.class, Nomi.CAMPO_ISTRUTTORE);
			Campo<Integer> attrezzatura = leggiCampo(evento, Integer.class, Nomi.CAMPO_ATTREZZATURA);
			LinkedList<String> listaIstr = leggiPartecipanti(evento, Nomi.LISTA_ISTRUTTORE.getNome());
			LinkedList<String> listaAttr = leggiPartecipanti(evento, Nomi.LISTA_ATTREZZATURE.getNome());
			ritorno = new EscursioneMontagnaEvento(id, titolo, nPartecipanti, partecipanti, 
					proprietario, termineUltimo, termineUltimoRitiro, luogo, dataInizio,
					durata, quotaIndividuale, compresoQuota, dataFine, nota, tolleranza, 
					istruttore, attrezzatura, listaIstr, listaAttr, statoEvento);

		}

		return ritorno;
	}

	public StatoEvento leggiStatoEvento(Element evento) {
		Element nodoStato = (Element) evento.getElementsByTagName(Nomi.CAMPO_STATO_EVENTO.getNome()).item(0);
		int lastIndex = nodoStato.getElementsByTagName(Nomi.STATO_EVENTO.getNome()).getLength()-1;
		String stato = nodoStato.getElementsByTagName(Nomi.STATO_EVENTO.getNome()).item(lastIndex).getTextContent();
		StatoEvento statoEvento = StatoEvento.APERTO;
		if(stato.equals(Nomi.STATO_EVENTO_CHIUSO.getNome()))
			statoEvento=StatoEvento.CHIUSO;
		else if(stato.equals(Nomi.STATO_EVENTO_CONCLUSO.getNome()))
			statoEvento=StatoEvento.CONCLUSO;
		else if(stato.equals(Nomi.STATO_EVENTO_FALLITO.getNome()))
			statoEvento=StatoEvento.FALLITO;		
		else if(stato.equals(Nomi.STATO_EVENTO_CANCELLATO.getNome()))
			statoEvento=StatoEvento.CANCELLATO;

		return statoEvento;
	}

	/**
	 * legge la partita 
	 * @param id unico
	 * @return
	 */
	public Evento cercaEvento(int id, String categoria) {
		Categoria<Evento> cat = leggiCategoria(categoria);
		for(Evento e :cat.getBacheca()) {
			if (e.getIdEvento()==id)
				return e;
		}
		return null;
	}

	/**
	 * dal nodo campo legge tutti i valori e torna il campo
	 * @param campo
	 * @param classeValore
	 * @return
	 */
	public Campo leggiCampoDaNodo(Element campo, Class classeValore) {
		String nome = campo.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
		String descrizione = campo.getElementsByTagName(Nomi.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
		String valoreToString = campo.getElementsByTagName(Nomi.TAG_VALORE.getNome()).item(0).getTextContent();

		Campo ritorno = null;
		if(classeValore.equals(String.class)) {
			ritorno = new Campo<String>(nome, descrizione, valoreToString, true);
		}
		else if(classeValore.equals(Integer.class)) {
			if(valoreToString.equals(""))
				ritorno = new Campo<Integer>(nome, descrizione, null, true);
			else 
				ritorno = new Campo<Integer>(nome, descrizione, Integer.parseInt(valoreToString), true);
		}
		else if(classeValore.equals(GregorianCalendar.class)) {
			GregorianCalendar data =leggiData(campo);
			ritorno = new Campo<GregorianCalendar>(nome, descrizione, data, true); 
		}

		return ritorno;
	}


	/**
	 * trova il nodo campo nella lista dell'evento e torna il campo
	 * @param evento
	 * @param classeValore
	 * @param nomeCampo
	 * @return
	 */
	public Campo leggiCampo(Element evento, Class classeValore, Nomi nomeCampo) {
		NodeList campiLista = evento.getElementsByTagName(Nomi.TAG_CAMPO.getNome());
		Campo ritorno=null;
		for(int i=0; i<campiLista.getLength(); i++) {
			Element campo= ((Element) campiLista.item(i));
			String testo =campo.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
			if(nomeCampo.getNome().equals(testo)) {
				ritorno = leggiCampoDaNodo(campo, classeValore);
				break;
			}

		}

		return ritorno;

	}


	public LinkedList<String> leggiPartecipanti(Element evento, String nomeNodo){
		LinkedList<String> lista = new LinkedList<String>();
		Element nodoLista = (Element) evento.getElementsByTagName(nomeNodo).item(0);
		for(int i=0; i<nodoLista.getElementsByTagName(Nomi.TAG_NOME.getNome()).getLength(); i++) {
			String username = nodoLista.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(i).getTextContent();
			lista.add(username);
		}

		return lista;

	}



	public LinkedList<Notifica> leggiNotifiche(Element listaNotifiche){
		LinkedList<Notifica> ritorno = new LinkedList<Notifica>();
		NodeList lista = listaNotifiche.getElementsByTagName(Nomi.TAG_NOTIFICA.getNome());
		for(int i=0; i<lista.getLength(); i++) {
			Element nodoNotifica = (Element) lista.item(i);
			Element nodoMessaggio = (Element) nodoNotifica.getElementsByTagName(Nomi.TAG_DESCRIZIONE.getNome()).item(0);
			Element nodoID = (Element) nodoNotifica.getElementsByTagName(Nomi.TAG_ID.getNome()).item(0);
			Element nodoLetto = (Element) nodoNotifica.getElementsByTagName(Nomi.TAG_LETTO.getNome()).item(0);

			String messaggio = nodoMessaggio.getTextContent();
			Evento evento = cercaEvento(Integer.parseInt(nodoID.getTextContent()), Nomi.CAT_PARTITA_CALCIO.getNome());
			if(evento==null)
				evento = cercaEvento(Integer.parseInt(nodoID.getTextContent()), Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
			boolean letto = Boolean.valueOf(nodoLetto.getTextContent());
			if(messaggio.contains(Notifica.INVITO)) {
				GregorianCalendar data = leggiData(nodoNotifica);
				ritorno.add(new Invito(evento, messaggio, letto, data));
			}else
				ritorno.add(new Notifica(evento, messaggio, letto));

		}
		
		Log.writeRoutineLog(getClass(), "lettura notifiche", Log.MEDIUM_PRIORITY);

		return ritorno;
	}


	/**
	 * torna l'oggetto utente
	 * @param id
	 * @return
	 */
	public Utente caricaUtente(String id) {
		Document doc=null;
		if(os.startsWith("Windows")){
			doc = fdx.creaDocument(Nomi.FILE_UTENTI_WIN);
		} else {
			doc = fdx.creaDocument(Nomi.FILE_UTENTI);
		}

		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		NodeList lista = elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome());
		Element utente=null;
		for(int i=0; i<lista.getLength(); i++) {
			if(id.equals(((Element) lista.item(i)).getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent()))
				utente = (Element) lista.item(i);
		}

		String nome = utente.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
		String hash = utente.getElementsByTagName(Nomi.TAG_HASH.getNome()).item(0).getTextContent();
		Element notifiche = (Element) utente.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);


		int etaMin = Integer.parseInt(utente.getElementsByTagName(Nomi.ATT_ETA_MIN.getNome()).item(0).getTextContent());
		int etaMax = Integer.parseInt(utente.getElementsByTagName(Nomi.ATT_ETA_MAX.getNome()).item(0).getTextContent());

		ArrayList<Integer> eventiCreati = new ArrayList<Integer>();
		Element eventiCreatiNodo = (Element) utente.getElementsByTagName(Nomi.ATT_EVENTI_CREATI.getNome()).item(0);
		for(int i=0; i<eventiCreatiNodo.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).getLength(); i++) {
			int idEvento = Integer.parseInt(eventiCreatiNodo.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).item(i).getTextContent());
			eventiCreati.add(idEvento);
		}

		LinkedList<String> interessi = new LinkedList<String> ();
		Element interessiNodo = (Element) utente.getElementsByTagName(Nomi.ATT_INTERESSI.getNome()).item(0);
		for(int i=0; i<interessiNodo.getElementsByTagName(Nomi.TAG_NOME.getNome()).getLength(); i++) {
			String categoria = interessiNodo.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(i).getTextContent();
			interessi.add(categoria);
		}


		Utente ritorno = new Utente(nome, hash, leggiNotifiche(notifiche), interessi, eventiCreati ,etaMin, etaMax);
		Log.writeRoutineLog(this.getClass(), "leggi utente " + ritorno.getUsername(), Log.HIGH_PRIORITY);
		return ritorno;
	}

	public Hashtable<String, LinkedList<Notifica>> leggiNotifichePendenti() {
		Hashtable<String, LinkedList<Notifica>> ritorno = new Hashtable<String, LinkedList<Notifica>>();
		Document doc = fdx.creaDocument(Nomi.FILE_NOTIFICHE_PENDENTI);

		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);

		for(int i=0; i<elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome()).getLength(); i++) {
			Element utente = (Element) elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome()).item(i);
			String nomeUtente = utente.getTextContent();
			ritorno.put(nomeUtente, leggiNotifiche(utente));

		}
		
		Log.writeRoutineLog(getClass(), "Lettura notifiche pendenti", Log.MEDIUM_PRIORITY);


		return ritorno;
	}

	public GregorianCalendar leggiData(Element nodo) {
		GregorianCalendar data = new GregorianCalendar();
		Element valore = (Element) nodo.getElementsByTagName(Nomi.TAG_VALORE.getNome()).item(0);
		int anno=0, mese=0, giorno=0, ora=0;
		if(valore.getElementsByTagName(Nomi.TAG_ANNO.getNome()).getLength()==0) {
			return null;
		}
		if(valore.getElementsByTagName(Nomi.TAG_ANNO.getNome()).item(0).getTextContent()!=null) {
			anno = Integer.parseInt(valore.getElementsByTagName(Nomi.TAG_ANNO.getNome()).item(0).getTextContent());
			
		}
		if(valore.getElementsByTagName(Nomi.TAG_MESE.getNome()).item(0).getTextContent()!=null) {
			mese = Integer.parseInt(valore.getElementsByTagName(Nomi.TAG_MESE.getNome()).item(0).getTextContent());
			
		}
		if(valore.getElementsByTagName(Nomi.TAG_GIORNO.getNome()).item(0).getTextContent()!=null) {
			giorno = Integer.parseInt(valore.getElementsByTagName(Nomi.TAG_GIORNO.getNome()).item(0).getTextContent());
			
		}
		ora = Integer.parseInt(valore.getElementsByTagName(Nomi.TAG_ORA.getNome()).item(0).getTextContent());

		data.set(anno, mese, giorno, ora, 0);
		return data;

	}


	

}















