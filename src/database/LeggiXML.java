package database;

import java.io.File;
import java.io.IOException;
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

import cervello.Campo;
import cervello.Categoria;
import cervello.EscursioneMontagnaCat;
import cervello.EscursioneMontagnaEvento;
import cervello.Evento;
import cervello.Invito;
import cervello.Notifica;
import cervello.PartitaCalcioCat;
import cervello.PartitaCalcioEvento;
import cervello.StatoEvento;
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


	public Categoria leggiCategoria(String categoria) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		Categoria ritorno=null;
		try {
			builder=factory.newDocumentBuilder();
			if(categoria.equals(NomiDB.CAT_PARTITA_CALCIO.getNome())) 
				doc=builder.parse(new File(NomiDB.FILE_PARTITA_CALCIO.getNome()));
			else if(categoria.equals(NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome()))
				doc=builder.parse(new File(NomiDB.FILE_ESCURSIONE_MONTAGNA.getNome()));

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


		Element categoriaNodo = (Element) doc.getElementsByTagName(NomiDB.TAG_CATEGORIA.getNome()).item(0);
		String nome = categoriaNodo.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
		String descrizione = categoriaNodo.getElementsByTagName(NomiDB.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
		LinkedList<String> personeInteressate = new LinkedList<String>();
		for(int i=0; i<categoriaNodo.getElementsByTagName(NomiDB.PERSONE_INTERESSATE.getNome()).getLength(); i++) {
			Element utente = (Element) categoriaNodo.getElementsByTagName(NomiDB.PERSONE_INTERESSATE.getNome()).item(i);
			String nomeUtente = utente.getTextContent();
			personeInteressate.add(nomeUtente);
		}
		ArrayList<Evento> bacheca = new ArrayList<Evento>(leggiListaEventi(doc, categoria));

		if(categoria.equals(NomiDB.CAT_PARTITA_CALCIO.getNome())) 			
			ritorno = new PartitaCalcioCat<>(nome, descrizione, bacheca, personeInteressate);
		else if(categoria.equals(NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome()))
			ritorno = new EscursioneMontagnaCat<>(nome, descrizione, bacheca, personeInteressate);

		return ritorno;
	}

	public ArrayList<Evento> leggiListaEventi(Document doc, String categoria) {
		Element lista = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		ArrayList<Evento> ritorno = new ArrayList<Evento>();
		for(int i=0; i<lista.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).getLength(); i++) {
			Element newEvento = (Element) lista.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).item(i);
			ritorno.add(leggiEvento(newEvento, categoria));

		}

		return ritorno;
	}


	public Evento leggiEvento(Element evento, String categoria) {
		Evento ritorno = null;
		//PARTE COMUNE A TUTTI GLI EVENTI
		int id = Integer.parseInt(evento.getAttribute("id"));
		String proprietario = evento.getElementsByTagName(NomiDB.CAMPO_PROPRIETARIO.getNome()).item(0).getTextContent();
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
		Campo<GregorianCalendar> termineUltimoRitiro = leggiCampo(evento, GregorianCalendar.class, NomiDB.CAMPO_TERMINE_ULTIMO_RITIRO);
		Campo<Integer> tolleranza = leggiCampo(evento, Integer.class, NomiDB.CAMPO_TOLLERANZA);
		LinkedList<String> partecipanti = leggiPartecipanti(evento, NomiDB.CAMPO_PARTECIPANTI.getNome());

		Element nodoStato = (Element) evento.getElementsByTagName(NomiDB.CAMPO_STATO_EVENTO.getNome()).item(0);
		int lastIndex = nodoStato.getElementsByTagName(NomiDB.STATO_EVENTO.getNome()).getLength()-1;
		String stato = nodoStato.getElementsByTagName(NomiDB.STATO_EVENTO.getNome()).item(lastIndex).getTextContent();
		StatoEvento statoEvento = StatoEvento.APERTO;
		if(stato.equals(NomiDB.STATO_EVENTO_CHIUSO.getNome()))
			statoEvento=StatoEvento.CHIUSO;
		else if(stato.equals(NomiDB.STATO_EVENTO_CONCLUSO.getNome()))
			statoEvento=StatoEvento.CONCLUSO;
		else if(stato.equals(NomiDB.STATO_EVENTO_FALLITO.getNome()))
			statoEvento=StatoEvento.FALLITO;		
		else if(stato.equals(NomiDB.STATO_EVENTO_CANCELLATO.getNome()))
			statoEvento=StatoEvento.CANCELLATO;

		//SOLO PARTITE DI CALCIO
		if(categoria.equals(NomiDB.CAT_PARTITA_CALCIO.getNome())) {
			Campo<String> sesso = leggiCampo(evento, String.class, NomiDB.CAMPO_SESSO);
			Campo<Integer> eta = leggiCampo(evento, Integer.class, NomiDB.CAMPO_ETA);
			ritorno = new PartitaCalcioEvento(id, titolo, nPartecipanti, partecipanti,proprietario, termineUltimo, termineUltimoRitiro, luogo, dataInizio,
					durata, quotaIndividuale, compresoQuota, dataFine, nota, tolleranza, sesso, eta, statoEvento);
		}

		if(categoria.equals(NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			Campo<Integer> istruttore = leggiCampo(evento, Integer.class, NomiDB.CAMPO_ISTRUTTORE);
			Campo<Integer> attrezzatura = leggiCampo(evento, Integer.class, NomiDB.CAMPO_ATTREZZATURA);
			LinkedList<String> listaIstr = leggiPartecipanti(evento, NomiDB.LISTA_ISTRUTTORE.getNome());
			LinkedList<String> listaAttr = leggiPartecipanti(evento, NomiDB.LISTA_ATTREZZATURE.getNome());
			ritorno = new EscursioneMontagnaEvento(id, titolo, nPartecipanti, partecipanti, proprietario, termineUltimo, termineUltimoRitiro, luogo, dataInizio,
					durata, quotaIndividuale, compresoQuota, dataFine, nota, tolleranza, istruttore, attrezzatura, listaIstr, listaAttr, statoEvento);
			
		}
		return ritorno;
	}

	/**
	 * legge la partita 
	 * @param id unico
	 * @return
	 */
	public Evento leggiEvento(int id, String categoria) {
		Categoria<Evento> cat = leggiCategoria(categoria);
		for(Evento pce :cat.getBacheca()) {
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
			if(valoreToString.equals(""))
				ritorno = new Campo<Integer>(nome, descrizione, null, true);
			else 
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

	public LinkedList<String> leggiPartecipanti(Element evento, String nomeNodo){
		LinkedList<String> lista = new LinkedList<String>();
		Element nodoLista = (Element) evento.getElementsByTagName(nomeNodo).item(0);
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
			Evento evento = leggiEvento(Integer.parseInt(nodoID.getTextContent()), NomiDB.CAT_PARTITA_CALCIO.getNome());
			if(evento==null)
				evento = leggiEvento(Integer.parseInt(nodoID.getTextContent()), NomiDB.CAT_ESCURSIOME_MONTAGNA.getNome());
			boolean letto = Boolean.valueOf(nodoLetto.getTextContent());
			if(messaggio.contains(Notifica.INVITO)) {
				GregorianCalendar data = new GregorianCalendar();
				Element valore = (Element) nodoNotifica.getElementsByTagName(NomiDB.TAG_DATA.getNome()).item(0);
				int anno = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_ANNO.getNome()).item(0).getTextContent());
				int mese = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_MESE.getNome()).item(0).getTextContent());
				int giorno = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_GIORNO.getNome()).item(0).getTextContent());
				int ora = Integer.parseInt(valore.getElementsByTagName(NomiDB.TAG_ORA.getNome()).item(0).getTextContent());

				data.set(anno, mese, giorno, ora, 0);
				ritorno.add(new Invito(evento, messaggio, letto, data));
			}else
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


		int etaMin = Integer.parseInt(utente.getElementsByTagName(NomiDB.ATT_ETA_MIN.getNome()).item(0).getTextContent());
		int etaMax = Integer.parseInt(utente.getElementsByTagName(NomiDB.ATT_ETA_MAX.getNome()).item(0).getTextContent());

		ArrayList<Integer> eventiCreati = new ArrayList<Integer>();
		Element eventiCreatiNodo = (Element) utente.getElementsByTagName(NomiDB.ATT_EVENTI_CREATI.getNome()).item(0);
		for(int i=0; i<eventiCreatiNodo.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).getLength(); i++) {
			int idEvento = Integer.parseInt(eventiCreatiNodo.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).item(i).getTextContent());
			eventiCreati.add(idEvento);
		}

		LinkedList<String> interessi = new LinkedList<String> ();
		Element interessiNodo = (Element) utente.getElementsByTagName(NomiDB.ATT_INTERESSI.getNome()).item(0);
		for(int i=0; i<interessiNodo.getElementsByTagName(NomiDB.TAG_NOME.getNome()).getLength(); i++) {
			String categoria = interessiNodo.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(i).getTextContent();
			interessi.add(categoria);
		}


		Utente ritorno = new Utente(nome, hash, leggiNotifiche(notifiche), interessi, eventiCreati ,etaMin, etaMax);
		return ritorno;
	}

	public Hashtable<String, LinkedList<Notifica>> leggiNotifichePendenti() {
		Hashtable<String, LinkedList<Notifica>> ritorno = new Hashtable<String, LinkedList<Notifica>>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;

		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_NOTIFICHE_PENDENTI.getNome()));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element elenco = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);

		for(int i=0; i<elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome()).getLength(); i++) {
			Element utente = (Element) elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome()).item(i);
			String nomeUtente = utente.getTextContent();
			ritorno.put(nomeUtente, leggiNotifiche(utente));

		}


		return ritorno;
	}




}















