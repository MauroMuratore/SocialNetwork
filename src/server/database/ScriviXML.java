package server.database;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import lib.core.categorie.Categoria;
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

public class ScriviXML {

	private FactoryNodoXML fnx ;
	private FactoryDocXML fdx ;
	private String os;

	public ScriviXML() {
		fnx = new FactoryNodoXML();
		fdx = new FactoryDocXML();
		os = System.getProperty("os.name");
	}

	public void salvaUtente(Utente utente) {
		Document doc=null;
		if(os.startsWith("Windows")){
			doc = fdx.creaDocument(Nomi.FILE_UTENTI_WIN);
		} else {
			doc = fdx.creaDocument(Nomi.FILE_UTENTI);
		}
		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		Element nodoUtente = fnx.creaNodoUtente(doc, elenco, utente);
		elenco.appendChild(nodoUtente);

		Log.writeRoutineLog(this.getClass(), "salvo utente " + utente.getUsername(), Log.HIGH_PRIORITY);
		if(os.startsWith("Windows")){
			scriviSuFile(doc, Nomi.FILE_UTENTI_WIN);
		} else {
			scriviSuFile(doc, Nomi.FILE_UTENTI);
		}


	}

	public void scriviCategoria(Categoria cat) {
		Nomi nomeFile=null;
		if(cat.getNome().equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_PARTITA_CALCIO_WIN;
			else 
				nomeFile = Nomi.FILE_PARTITA_CALCIO;
		}
		else if(cat.getNome().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA_WIN;
			else 
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA;
		}
		Document doc = fdx.creaDocument(nomeFile);


		Element categoria = fnx.creaNodoCategoria(doc, cat);

		scriviSuFile(doc, nomeFile);
	}


	/**
	 * scrittura partita da calcio, se è nuova viene creato un nuovo oggetto, altrimenti viene sovrascritto
	 * @param evento
	 */
	public void scriviPartitaCalcioEvento(PartitaCalcioEvento evento, Element nodoPartita, Document doc, boolean sovrascrivi) {
		if(sovrascrivi) {
			sovrascriviCampo(evento.getSesso(), nodoPartita);
			sovrascriviCampo(evento.getEta(), nodoPartita);
		}else {
			scriviCampo(evento.getSesso(), nodoPartita, doc);
			scriviCampo(evento.getEta(), nodoPartita, doc);
		}
	}

	public void scriviEscursioneMontagnaEvento(EscursioneMontagnaEvento evento, Element nodoEM, Document doc, boolean sovrascrivi) {
		if(sovrascrivi) {
			sovrascriviCampo(evento.getAttrezzatura(), nodoEM);
			sovrascriviCampo(evento.getIstruttore(), nodoEM);
		}else {
			scriviCampo(evento.getAttrezzatura(), nodoEM, doc);
			scriviCampo(evento.getIstruttore(), nodoEM, doc);			
		}
		scriviPartecipanti(evento.getListaPerAttrezzature(), nodoEM, doc, Nomi.LISTA_ATTREZZATURE.getNome());
		scriviPartecipanti(evento.getListaPerIstruttore(), nodoEM, doc, Nomi.LISTA_ISTRUTTORE.getNome());
	}

	public void scriviEvento(Evento evento) {
		Nomi nomeFile=null;
		if(evento.getCategoria().equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_NOTIFICHE_PENDENTI_WIN;
			else 
				nomeFile = Nomi.FILE_PARTITA_CALCIO;
		}
		else if(evento.getCategoria().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA_WIN;
			else 
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA;
		}
		Document doc = fdx.creaDocument(nomeFile);


		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		//evento esistente
		for(int i=0; i< elenco.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).getLength(); i++) {
			Element nodo = (Element) elenco.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).item(i);
			int idNodo = Integer.parseInt(nodo.getAttribute("id"));
			if(evento.getIdEvento()==idNodo) {
				sovrascriviEvento(doc, evento, nodo);
				scriviSuFile(doc, nomeFile);
				return;
			}
		}
		Element newEvento = fnx.creaNodoEvento(doc, evento);
		scriviNuovoEvento(doc, evento, newEvento);
		elenco.appendChild(newEvento);

		Log.writeRoutineLog(this.getClass(), "scrivo evento ", Log.MEDIUM_PRIORITY);
		scriviSuFile(doc, nomeFile);

	}

	public void scriviNuovoEvento(Document doc, Evento evento, Element nodoEvento) {
		scriviCampo(evento.getTitolo(), nodoEvento, doc);
		scriviCampo(evento.getPartecipantiNecessari(), nodoEvento, doc);
		scriviCampo(evento.getTermineUltimo(), nodoEvento, doc);
		scriviCampo(evento.getLuogo(), nodoEvento, doc);
		scriviCampo(evento.getDataInizio(), nodoEvento, doc);
		scriviCampo(evento.getDurata(), nodoEvento, doc);
		scriviCampo(evento.getDataFine(), nodoEvento, doc);
		scriviCampo(evento.getQuotaIndividuale(), nodoEvento, doc);
		scriviCampo(evento.getCompresoQuota(), nodoEvento, doc);
		scriviCampo(evento.getNote(), nodoEvento, doc);
		scriviCampo(evento.getTolleranzaPartecipanti(), nodoEvento, doc);
		scriviCampo(evento.getTermineUltimoRitiro(), nodoEvento, doc);
		scriviPartecipanti(evento.getPartecipanti(), nodoEvento, doc, Nomi.CAMPO_PARTECIPANTI.getNome());

		if(evento.getClass().equals(PartitaCalcioEvento.class)) {
			scriviPartitaCalcioEvento((PartitaCalcioEvento)evento, nodoEvento, doc, false);
		}else if(evento.getClass().equals(EscursioneMontagnaEvento.class)) {
			scriviEscursioneMontagnaEvento((EscursioneMontagnaEvento) evento, nodoEvento, doc, false);
		}
		Element statoEvento = doc.createElement(Nomi.CAMPO_STATO_EVENTO.getNome());
		Element stato = fnx.creaNodoStatoEvento(doc, evento.getStato());
		statoEvento.appendChild(stato);
		nodoEvento.appendChild(statoEvento);
	}

	public void sovrascriviEvento(Document doc, Evento evento, Element nodoEvento) {
		sovrascriviCampo(evento.getTitolo(), nodoEvento);
		sovrascriviCampo(evento.getPartecipantiNecessari(), nodoEvento);
		sovrascriviCampo(evento.getTermineUltimo(), nodoEvento);
		sovrascriviCampo(evento.getLuogo(), nodoEvento);
		sovrascriviCampo(evento.getDataInizio(), nodoEvento);
		sovrascriviCampo(evento.getDurata(), nodoEvento);
		sovrascriviCampo(evento.getDataFine(), nodoEvento);
		sovrascriviCampo(evento.getQuotaIndividuale(), nodoEvento);
		sovrascriviCampo(evento.getCompresoQuota(), nodoEvento);
		sovrascriviCampo(evento.getNote(), nodoEvento);
		sovrascriviCampo(evento.getTolleranzaPartecipanti(), nodoEvento);
		sovrascriviCampo(evento.getTermineUltimoRitiro(), nodoEvento);
		scriviPartecipanti(evento.getPartecipanti(), nodoEvento, doc, Nomi.CAMPO_PARTECIPANTI.getNome());
		scriviStato(nodoEvento, evento.getStato(), doc);
		if(evento.getClass().equals(PartitaCalcioEvento.class)) {
			scriviPartitaCalcioEvento((PartitaCalcioEvento)evento, nodoEvento, doc, true);
		}else if(evento.getClass().equals(EscursioneMontagnaEvento.class)) {
			scriviEscursioneMontagnaEvento((EscursioneMontagnaEvento) evento, nodoEvento, doc, true);
		}
	}




	public void scriviStato(Element nodoEvento, StatoEvento statoEvento, Document doc) {
		Element nodoStatoEvento = (Element) nodoEvento.getElementsByTagName(Nomi.CAMPO_STATO_EVENTO.getNome()).item(0);
		int lastIndex = nodoStatoEvento.getElementsByTagName(Nomi.STATO_EVENTO.getNome()).getLength()-1;
		Element lastChild = (Element) nodoStatoEvento.getElementsByTagName(Nomi.STATO_EVENTO.getNome()).item(lastIndex);
		String oldStatoString = lastChild.getTextContent();
		StatoEvento oldStato=null;
		if(oldStatoString.equals(Nomi.STATO_EVENTO_CHIUSO.getNome())) {
			oldStato=StatoEvento.CHIUSO;
		}
		else if(oldStatoString.equals(Nomi.STATO_EVENTO_APERTO.getNome())) {
			oldStato=StatoEvento.APERTO;
		}
		else if(oldStatoString.equals(Nomi.STATO_EVENTO_FALLITO.getNome())) {
			oldStato=StatoEvento.FALLITO;
		}
		else if(oldStatoString.equals(Nomi.STATO_EVENTO_CONCLUSO.getNome())) {
			oldStato=StatoEvento.CONCLUSO;
		}

		if(!oldStato.equals(statoEvento)) {
			Element newStato = fnx.creaNodoStatoEvento(doc, statoEvento);
			nodoStatoEvento.appendChild(newStato);
		}
	}


	/**
	 * scrittura di un campo di un determinato evento
	 * @param campo
	 * @param evento
	 * @param doc
	 */
	public void scriviCampo(Campo campo, Element evento, Document doc) {

		Element nodoCampo = fnx.creaNodoCampo(doc, campo);
		evento.appendChild(nodoCampo);

	}

	public void sovrascriviCampo(Campo campo, Element evento) {
		NodeList listaCampi = evento.getElementsByTagName(Nomi.TAG_CAMPO.getNome());
		for(int i=0; i<listaCampi.getLength(); i++) {
			Element nodoCampo = (Element) listaCampi.item(i);
			if(nodoCampo.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent()==campo.getNome()) {
				Element nodoValore = (Element) nodoCampo.getElementsByTagName(Nomi.TAG_VALORE.getNome()).item(0);
				if(campo.getClasseValore().equals(GregorianCalendar.class)) {
					GregorianCalendar data =((GregorianCalendar)campo.getValore());

					Element annoNodo = (Element) nodoValore.getElementsByTagName(Nomi.TAG_ANNO.getNome()).item(0);
					int annoInt =data.get(GregorianCalendar.YEAR);
					annoNodo.setTextContent( String.valueOf(annoInt) );

					Element meseNodo = (Element) nodoValore.getElementsByTagName(Nomi.TAG_MESE.getNome()).item(0);
					int meseInt = data.get(GregorianCalendar.MONTH);
					meseNodo.setTextContent(String.valueOf(meseInt));

					Element giornoNodo = (Element) nodoValore.getElementsByTagName(Nomi.TAG_GIORNO.getNome()).item(0);
					int giornoInt = data.get(GregorianCalendar.DAY_OF_MONTH);
					giornoNodo.setTextContent(String.valueOf(giornoInt));

					Element oraNodo = (Element) nodoValore.getElementsByTagName(Nomi.TAG_ORA.getNome()).item(0);
					int oraInt = data.get(GregorianCalendar.HOUR_OF_DAY);
					oraNodo.setTextContent(String.valueOf(oraInt));
				}
				else
					nodoCampo.getElementsByTagName(Nomi.TAG_VALORE.getNome()).item(0).setTextContent(campo.getValoreString());
			}
		}
	}

	public void scriviPartecipanti(LinkedList<String> lista, Element nodoEvento, Document doc, String tagListaPartecipanti) {		
		Element nodoLista = fnx.creaNodoPartecipanti(doc, lista, nodoEvento, tagListaPartecipanti);
		nodoEvento.appendChild(nodoLista);

	}

	/**
	 * scrittura effettiva su file
	 * @param doc
	 * @param file
	 */
	private void scriviSuFile(Document doc, Nomi fileName) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			File file = new File(fileName.getNome());
			if(!file.exists()) {
				String os = System.getProperty("os.name");
				if(os.startsWith("Windows")){
					file = new File("..\\" + fileName.getNome());
				}
				else {
					file = new File("../" + fileName.getNome());
				}
			}
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);

		} catch (TransformerConfigurationException e) {
			Log.writeErrorLog(this.getClass(), "errore nella scrittura su file");
			e.printStackTrace();
		} catch (TransformerException e) {
			Log.writeErrorLog(this.getClass(), "errore nella scrittura su file");
			e.printStackTrace();
		}

		Log.writeRoutineLog(this.getClass(), "Scrittura su file " + fileName.getNome(), Log.LOW_PRIORITY);
	}

	public void scriviNotifichePendenti(Map<String, LinkedList<Notifica>> notifichePendenti) {
		if(notifichePendenti==null)
			return;
		Document doc = fdx.creaDocument(Nomi.FILE_NOTIFICHE_PENDENTI);		
		Element elenco = fnx.creaNodoNotifichePendenti(notifichePendenti, doc);

		scriviSuFile(doc, Nomi.FILE_NOTIFICHE_PENDENTI);


	}

	public void cancellaNotifica(Notifica notifica, Utente utente) {
		Document doc = null;
		if(os.startsWith("Windows"))
			doc= fdx.creaDocument(Nomi.FILE_UTENTI_WIN);
		else
			doc = fdx.creaDocument(Nomi.FILE_UTENTI);
		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		NodeList listaUtenti = elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome());
		Element nodoUtente = null;
		for(int i=0; i<listaUtenti.getLength(); i++) {
			String nome =((Element)listaUtenti.item(i)).getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
			if(nome.equals(utente.getUsername())) {
				nodoUtente = (Element)listaUtenti.item(i);
			}
		}

		Element listaNotifiche =(Element) nodoUtente.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		for(int i=0; i<listaNotifiche.getElementsByTagName(Nomi.TAG_NOTIFICA.getNome()).getLength(); i++) {
			Element nodoNotifica = (Element) listaNotifiche.getElementsByTagName(Nomi.TAG_NOTIFICA.getNome()).item(i);
			String descrizione = nodoNotifica.getElementsByTagName(Nomi.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
			String id = nodoNotifica.getElementsByTagName(Nomi.TAG_ID.getNome()).item(0).getTextContent();
			int intId = Integer.parseInt(id);
			if(descrizione.equals(notifica.getMessaggio()) && intId == notifica.getEvento().getIdEvento()) {
				listaNotifiche.removeChild(nodoNotifica);
				break;
			}
		}
		if(os.startsWith("Windows"))
			scriviSuFile(doc, Nomi.FILE_UTENTI_WIN);
		else
			scriviSuFile(doc, Nomi.FILE_UTENTI);

	}

	public void cancellaUtente(String username) {
		Document doc = null;
		if(os.startsWith("Windows"))
			doc= fdx.creaDocument(Nomi.FILE_UTENTI_WIN);
		else
			doc = fdx.creaDocument(Nomi.FILE_UTENTI);
		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		NodeList lista = elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome());
		Element nodoUtente = null;
		for(int i=0; i<lista.getLength(); i++) {
			String nome =((Element)lista.item(i)).getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
			if(nome.equals(username)) {

				nodoUtente = (Element)lista.item(i);
				Log.writeRoutineLog(this.getClass(), username + " rimosso", Log.HIGH_PRIORITY);
			}
		}
		elenco.removeChild(nodoUtente);

		if(os.startsWith("Windows"))
			scriviSuFile(doc, Nomi.FILE_UTENTI_WIN);
		else
			scriviSuFile(doc, Nomi.FILE_UTENTI);

	}

	public void eliminaEvento(Evento evento) {
		Nomi nomeFile=null;
		if(evento.getCategoria().equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_NOTIFICHE_PENDENTI_WIN;
			else 
				nomeFile = Nomi.FILE_PARTITA_CALCIO;
		}
		else if(evento.getCategoria().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			if(os.startsWith("Windows"))
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA_WIN;
			else 
				nomeFile = Nomi.FILE_ESCURSIONE_MONTAGNA;
		}
		Document doc = fdx.creaDocument(nomeFile);

		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		//evento esistente
		for(int i=0; i< elenco.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).getLength(); i++) {
			Element nodoEvento = (Element) elenco.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).item(i);
			int idNodo = Integer.parseInt(nodoEvento.getAttribute("id"));
			if(evento.getIdEvento()==idNodo) {
				elenco.removeChild(nodoEvento);				
				break;
			}
		}

		Log.writeRoutineLog(this.getClass(), "elimino evento ", Log.MEDIUM_PRIORITY);
		scriviSuFile(doc, nomeFile);
	}



}




































