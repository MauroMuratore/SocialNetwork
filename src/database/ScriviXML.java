package database;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;

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

import cervello.Campo;
import cervello.Evento;
import cervello.Notifica;
import cervello.PartitaCalcioEvento;
import cervello.StatoEvento;
import cervello.Utente;

public class ScriviXML {


	/**
	 * scrive alla fine del file le credenziali dell'utente
	 * @param username
	 * @param hash
	 */
	public void aggiungiUtente(String username, byte[] hash) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		String hashChar = null;
		try {
			builder = factory.newDocumentBuilder();

			doc = builder.parse(new File(NomiDB.FILE_UTENTI.getNome()));
			hashChar = new String(hash, "UTF-8");
		}catch (SAXException sax) {
			sax.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		Element root = (Element) doc.getFirstChild();

		Element newChild = doc.createElement(NomiDB.TAG_UTENTE.getNome());
		Element childName=doc.createElement(NomiDB.TAG_NOME.getNome());
		childName.setTextContent(username);
		Element childPW = doc.createElement(NomiDB.TAG_HASH.getNome());
		childPW.setTextContent(hashChar);

		newChild.appendChild(childName);
		newChild.appendChild(childPW);

		root.appendChild(newChild);

		//effetivament per scrivere
		scriviSuFile(doc, NomiDB.FILE_UTENTI);


		System.out.println("Scrittura completata");
	}

	public void salvaUtente(Utente utente) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_UTENTI.getNome()));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element elenco = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		NodeList lista = elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome());
		Element nodoUtente = null;
		for(int i=0; i<lista.getLength(); i++) {
			String nome =((Element)lista.item(i)).getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
			if(nome.equals(utente.getUsername())) {
				nodoUtente = (Element)lista.item(i);
			}
		}
		Element notifiche = (Element) nodoUtente.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);

		scriviNotificheUtente(doc, utente.getNotifiche(), notifiche);

		nodoUtente.appendChild(notifiche);

		scriviSuFile(doc, NomiDB.FILE_UTENTI);


	}


	/**
	 * scrittura partita da calcio, se è nuova viene creato un nuovo oggetto, altrimenti viene sovrascritto
	 * @param evento
	 */
	public void scriviPartitaCalcioEvento(PartitaCalcioEvento evento) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_PARTITA_CALCIO.getNome())); 

		}catch (SAXException sax) {
			sax.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		Element elenco = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		//evento esistente
		for(int i=0; i< elenco.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).getLength(); i++) {
			Element nodo = (Element) elenco.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).item(i);
			int idNodo = Integer.parseInt(nodo.getAttribute("id"));
			if(evento.getIdEvento()==idNodo) {
				sovrascriviCampo(evento.getTitolo(), nodo, doc);
				sovrascriviCampo(evento.getPartecipantiNecessari(), nodo, doc);
				sovrascriviCampo(evento.getTermineUltimo(), nodo, doc);
				sovrascriviCampo(evento.getLuogo(), nodo, doc);
				sovrascriviCampo(evento.getDataInizio(), nodo, doc);
				//scriviCampo(evento.getOrarioInizio(), newEvento, doc);
				sovrascriviCampo(evento.getDurata(), nodo, doc);
				sovrascriviCampo(evento.getDataFine(), nodo, doc);
				sovrascriviCampo(evento.getQuotaIndividuale(), nodo, doc);
				sovrascriviCampo(evento.getCompresoQuota(), nodo, doc);
				sovrascriviCampo(evento.getNote(), nodo, doc);
				sovrascriviCampo(evento.getSesso(), nodo, doc);
				sovrascriviCampo(evento.getEta(), nodo, doc);
				scriviPartecipanti(evento.getPartecipanti(), nodo, doc);
				scriviSuFile(doc, NomiDB.FILE_PARTITA_CALCIO);
				return;
			}
		}

		Element newEvento = doc.createElement(NomiDB.TAG_EVENTO.getNome());
		newEvento.setAttribute("id", String.valueOf(evento.getIdEvento())); 
		scriviCampo(evento.getTitolo(), newEvento, doc);
		scriviCampo(evento.getPartecipantiNecessari(), newEvento, doc);
		scriviCampo(evento.getTermineUltimo(), newEvento, doc);
		scriviCampo(evento.getLuogo(), newEvento, doc);
		scriviCampo(evento.getDataInizio(), newEvento, doc);
		//scriviCampo(evento.getOrarioInizio(), newEvento, doc);
		scriviCampo(evento.getDurata(), newEvento, doc);
		scriviCampo(evento.getDataFine(), newEvento, doc);
		scriviCampo(evento.getQuotaIndividuale(), newEvento, doc);
		scriviCampo(evento.getCompresoQuota(), newEvento, doc);
		scriviCampo(evento.getNote(), newEvento, doc);
		scriviCampo(evento.getSesso(), newEvento, doc);
		scriviCampo(evento.getEta(), newEvento, doc);
		scriviPartecipanti(evento.getPartecipanti(), newEvento, doc);
		

		Element statoEvento = doc.createElement(NomiDB.CAMPO_STATO_EVENTO.getNome());
		if(evento.getStato().equals(StatoEvento.CHIUSO))
			statoEvento.setTextContent(NomiDB.STATO_EVENTO_CHIUSO.getNome());
		else if(evento.getStato().equals(StatoEvento.CONCLUSO))
			statoEvento.setTextContent(NomiDB.STATO_EVENTO_CONCLUSO.getNome());
		else if(evento.getStato().equals(StatoEvento.APERTO))
			statoEvento.setTextContent(NomiDB.STATO_EVENTO_APERTO.getNome());
		else if(evento.getStato().equals(StatoEvento.FALLITO))
			statoEvento.setTextContent(NomiDB.STATO_EVENTO_FALLITO.getNome());

		elenco.appendChild(newEvento);

		scriviSuFile(doc, NomiDB.FILE_PARTITA_CALCIO);

	}


	/**
	 * scrittura di un campo di un determinato evento
	 * @param campo
	 * @param evento
	 * @param doc
	 */
	public void scriviCampo(Campo campo, Element evento, Document doc) {
		Element newEl = doc.createElement(NomiDB.TAG_CAMPO.getNome());
		Element nome = doc.createElement(NomiDB.TAG_NOME.getNome());
		nome.setTextContent(campo.getNome());
		newEl.appendChild(nome);
		Element descrizione = doc.createElement(NomiDB.TAG_DESCRIZIONE.getNome());
		descrizione.setTextContent(campo.getDescrizione());
		newEl.appendChild(descrizione);
		Element valore = doc.createElement(NomiDB.TAG_VALORE.getNome());
		if(campo.getValore()==null) {
			//non fare niente
		}
		else if(campo.getClasseValore().equals(GregorianCalendar.class)) { //scrittura della data
			GregorianCalendar data =((GregorianCalendar)campo.getValore());

			Element annoNodo = doc.createElement(NomiDB.TAG_ANNO.getNome());
			int annoInt =data.get(GregorianCalendar.YEAR);
			annoNodo.setTextContent( String.valueOf(annoInt) );

			Element meseNodo = doc.createElement(NomiDB.TAG_MESE.getNome());
			int meseInt = data.get(GregorianCalendar.MONTH);
			meseNodo.setTextContent(String.valueOf(meseInt));

			Element giornoNodo = doc.createElement(NomiDB.TAG_GIORNO.getNome());
			int giornoInt = data.get(GregorianCalendar.DAY_OF_MONTH);
			giornoNodo.setTextContent(String.valueOf(giornoInt));

			Element oraNodo = doc.createElement(NomiDB.TAG_ORA.getNome());
			int oraInt = data.get(GregorianCalendar.HOUR_OF_DAY);
			oraNodo.setTextContent(String.valueOf(oraInt));

			valore.appendChild(annoNodo);
			valore.appendChild(meseNodo);
			valore.appendChild(giornoNodo);
			valore.appendChild(oraNodo);


		}
		else //scrittura di qualsiasi altro campo
			valore.setTextContent(campo.getValoreString());
		newEl.appendChild(valore);
		Element obbl = doc.createElement(NomiDB.TAG_OBBL.getNome());
		obbl.setTextContent(Boolean.toString(campo.isObbligatorio()));
		newEl.appendChild(obbl);
		evento.appendChild(newEl);

	}

	public void sovrascriviCampo(Campo campo, Element evento, Document doc) {
		NodeList listaCampi = evento.getElementsByTagName(NomiDB.TAG_CAMPO.getNome());
		for(int i=0; i<listaCampi.getLength(); i++) {
			Element nodoCampo = (Element) listaCampi.item(i);
			if(nodoCampo.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent()==campo.getNome()) {
				Element nodoValore = (Element) nodoCampo.getElementsByTagName(NomiDB.TAG_VALORE.getNome()).item(0);
				if(campo.getClasseValore().equals(GregorianCalendar.class)) {
					GregorianCalendar data =((GregorianCalendar)campo.getValore());

					Element annoNodo = (Element) nodoValore.getElementsByTagName(NomiDB.TAG_ANNO.getNome()).item(0);
					int annoInt =data.get(GregorianCalendar.YEAR);
					annoNodo.setTextContent( String.valueOf(annoInt) );

					Element meseNodo = (Element) nodoValore.getElementsByTagName(NomiDB.TAG_MESE.getNome()).item(0);
					int meseInt = data.get(GregorianCalendar.MONTH);
					meseNodo.setTextContent(String.valueOf(meseInt));

					Element giornoNodo = (Element) nodoValore.getElementsByTagName(NomiDB.TAG_GIORNO.getNome()).item(0);
					int giornoInt = data.get(GregorianCalendar.DAY_OF_MONTH);
					giornoNodo.setTextContent(String.valueOf(giornoInt));

					Element oraNodo = (Element) nodoValore.getElementsByTagName(NomiDB.TAG_ORA.getNome()).item(0);
					int oraInt = data.get(GregorianCalendar.HOUR_OF_DAY);
					oraNodo.setTextContent(String.valueOf(oraInt));
				}
				else
					nodoCampo.getElementsByTagName(NomiDB.TAG_VALORE.getNome()).item(0).setTextContent(campo.getValoreString());
			}
		}
	}

	public void scriviPartecipanti(LinkedList<String> lista, Element evento, Document doc) {
		Element nodoLista = doc.createElement(NomiDB.CAMPO_PARTECIPANTI.getNome());
		for(int i=0; i<lista.size(); i++) {
			boolean isPresent=false;
			for (int index=0; index<nodoLista.getElementsByTagName(NomiDB.TAG_NOME.getNome()).getLength(); index++) {
				Element nodoPartecipante = (Element) nodoLista.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(index);
				if(nodoPartecipante.getTextContent().equals(lista.get(i)))
					isPresent=true;
			}
			Element nodoNome=null;
			if(!isPresent) {
				nodoNome = doc.createElement(NomiDB.TAG_NOME.getNome());
				nodoNome.setTextContent(lista.get(i));
				nodoLista.appendChild(nodoNome);
			}
		}
		evento.appendChild(nodoLista);

	}


	/**
	 * scrittura effettiva su file
	 * @param doc
	 * @param file
	 */
	private void scriviSuFile(Document doc, NomiDB file) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(file.getNome()));
			transformer.transform(source, result);


		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch blritorno = leggiCampoDaNodo(campo, classeValore);ock
			e.printStackTrace();
		}

		System.out.println("Scrittura su file " + file.getNome());
	}

	public void scriviNotificheUtente(Document doc, LinkedList<Notifica> listaNotifiche, Element elenco) {
		if(elenco.getChildNodes()!=null)
			for(int i=0; i<elenco.getChildNodes().getLength();i++) {

				elenco.removeChild(elenco.getChildNodes().item(i));
			}

		for(Notifica n: listaNotifiche) {
			Element nodoNotifica = doc.createElement(NomiDB.TAG_NOTIFICA.getNome());
			Element nodoMessaggio = doc.createElement(NomiDB.TAG_DESCRIZIONE.getNome());
			Element nodoEvento = doc.createElement(NomiDB.TAG_ID.getNome());
			Element nodoLetto = doc.createElement(NomiDB.TAG_LETTO.getNome());

			nodoMessaggio.setTextContent(n.getMessaggio());
			nodoEvento.setTextContent(String.valueOf(n.getEvento().getIdEvento()));
			nodoLetto.setTextContent(String.valueOf(n.getLetta()));

			nodoNotifica.appendChild(nodoMessaggio);
			nodoNotifica.appendChild(nodoEvento);
			nodoNotifica.appendChild(nodoLetto);
			elenco.appendChild(nodoNotifica);
		}

	}

	public void scriviNotifichePendenti(Hashtable<String, LinkedList<Notifica>> notifichePendenti) {
		if(notifichePendenti==null)
			return;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_NOTIFICHE_PENDENTI.getNome())); 

		}catch (SAXException sax) {
			sax.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		Element elenco = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		for(String key : notifichePendenti.keySet()) {
			scriviNotificheUtente(doc, notifichePendenti.get(key), elenco);

		}

		scriviSuFile(doc, NomiDB.FILE_NOTIFICHE_PENDENTI);


	}

	public void cancellaNotifica(Notifica notifica, Utente utente) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_UTENTI.getNome()));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Element elenco = (Element) doc.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		NodeList lista = elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome());
		Element nodoUtente = null;
		for(int i=0; i<lista.getLength(); i++) {
			String nome =((Element)lista.item(i)).getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(0).getTextContent();
			if(nome.equals(utente.getUsername())) {
				nodoUtente = (Element)lista.item(i);
			}
		}
		Element listaNotifiche =(Element) nodoUtente.getElementsByTagName(NomiDB.TAG_ELENCO.getNome()).item(0);
		for(int i=0; i<listaNotifiche.getElementsByTagName(NomiDB.TAG_NOTIFICA.getNome()).getLength(); i++) {
			Element nodoNotifica = (Element) listaNotifiche.getElementsByTagName(NomiDB.TAG_NOTIFICA.getNome()).item(i);
			String descrizione = nodoNotifica.getElementsByTagName(NomiDB.TAG_DESCRIZIONE.getNome()).item(0).getTextContent();
			String id = nodoNotifica.getElementsByTagName(NomiDB.TAG_ID.getNome()).item(0).getTextContent();
			int intId = Integer.parseInt(id);
			if(descrizione.equals(notifica.getMessaggio()) || intId == notifica.getEvento().getIdEvento()) {
				listaNotifiche.removeChild(nodoNotifica);
			}
		}

		scriviSuFile(doc, NomiDB.FILE_UTENTI);

	}

}




































