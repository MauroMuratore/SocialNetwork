package database;

import java.io.File;
import java.io.IOException;
import java.util.GregorianCalendar;
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
import cervello.PartitaCalcioEvento;
import cervello.Utente;

public class ScriviXML {

	public static final String UTENTE = "utente";
	public static final String USERNAME = "username";
	public static final String HASH = "hash";

	/**
	 * scrive alla fine del file l'utente
	 * @param username
	 * @param hash
	 */
	public void scriviUtente(String username, byte[] hash) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		String hashChar = null;
		try {
			builder = factory.newDocumentBuilder();

			doc = builder.parse(new File(NomiDB.FILE_LOGIN.getNome()));
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
		scriviSuFile(doc, NomiDB.FILE_LOGIN);


		System.out.println("Scrittura completata");
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
		
		for(int i=0; i< elenco.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).getLength(); i++) {
			Element nodo = (Element) elenco.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).item(i);
			int idNodo = Integer.parseInt(nodo.getAttribute("id"));
			if(evento.getIdEvento()==idNodo) {
				scriviCampo(evento.getTitolo(), nodo, doc);
				scriviCampo(evento.getPartecipantiMax(), nodo, doc);
				scriviCampo(evento.getTermineUltimo(), nodo, doc);
				scriviCampo(evento.getLuogo(), nodo, doc);
				scriviCampo(evento.getDataInizio(), nodo, doc);
				//scriviCampo(evento.getOrarioInizio(), newEvento, doc);
				scriviCampo(evento.getDurata(), nodo, doc);
				scriviCampo(evento.getDataFine(), nodo, doc);
				scriviCampo(evento.getQuotaIndividuale(), nodo, doc);
				scriviCampo(evento.getCompresoQuota(), nodo, doc);
				scriviCampo(evento.getNote(), nodo, doc);
				scriviCampo(evento.getSesso(), nodo, doc);
				scriviCampo(evento.getEta(), nodo, doc);
				scriviPartecipanti(evento.getPartecipanti(), nodo, doc);
				return;
			}
		}

		Element newEvento = doc.createElement(NomiDB.TAG_EVENTO.getNome());
		newEvento.setAttribute("id", String.valueOf(evento.getIdEvento())); 
		scriviCampo(evento.getTitolo(), newEvento, doc);
		scriviCampo(evento.getPartecipantiMax(), newEvento, doc);
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

		if(campo.getClasseValore().equals(GregorianCalendar.class)) { //scrittura della data
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

	public void scriviPartecipanti(LinkedList<String> lista, Element evento, Document doc) {
		Element nodoLista = doc.createElement(NomiDB.CAMPO_PARTECIPANTI.getNome());
		for(int i=0; i<lista.size(); i++) {
			Element nodoNome = doc.createElement(NomiDB.TAG_NOME.getNome());
			nodoNome.setTextContent(lista.get(i));
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
}




































