package database;

import java.io.File;
import java.io.IOException;

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
		newChild.setAttribute(NomiDB.TAG_NOME.getNome(), username); //come attributo l'username
		newChild.setTextContent(hashChar);

		root.appendChild(newChild);
		
		//effetivament per scrivere
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(NomiDB.FILE_LOGIN.getNome()));
			transformer.transform(source, result);


		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Done");
		
		
		System.out.println("Scrittura completata");
	}
	

	
	public <E extends Evento> void scriviEvento(E evento) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(NomiDB.FILE_EVENTI.getNome())); //da pensare dove scriverli
			
		}catch (SAXException sax) {
			sax.printStackTrace();
		}catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}
		
		Element newEvento = doc.createElement(NomiDB.TAG_EVENTO.getNome());
		newEvento.setAttribute("id", String.valueOf(evento.getIdEvento())); 
		scriviCampo(evento.getTitolo(), newEvento, doc);
		scriviCampo(evento.getPartecipanti(), newEvento, doc);
		scriviCampo(evento.getTermineUltimo(), newEvento, doc);
		scriviCampo(evento.getLuogo(), newEvento, doc);
		scriviCampo(evento.getDataInizio(), newEvento, doc);
		scriviCampo(evento.getOrarioInizio(), newEvento, doc);
		scriviCampo(evento.getDurata(), newEvento, doc);
		scriviCampo(evento.getDataFine(), newEvento, doc);
		scriviCampo(evento.getOrarioFine(), newEvento, doc);
		scriviCampo(evento.getQuotaIndividuale(), newEvento, doc);
		scriviCampo(evento.getCompresoQuota(), newEvento, doc);
		scriviCampo(evento.getNote(), newEvento, doc);
		

	}

	public void scriviCampo(Campo campo, Element evento, Document doc) {
		Element newEl = doc.createElement(NomiDB.TAG_CAMPO.getNome());
		newEl.setAttribute(NomiDB.TAG_NOME.getNome(), campo.getNome());
		newEl.setAttribute(NomiDB.TAG_DESCRIZIONE.getNome(), campo.getDescrizione());
		newEl.setAttribute(NomiDB.TAG_VALORE.getNome(), campo.toString());
		newEl.setAttribute(NomiDB.TAG_OBBL.getNome(), String.valueOf(campo.isObbligatorio()));
		
		evento.appendChild(newEl);

	}
}




































