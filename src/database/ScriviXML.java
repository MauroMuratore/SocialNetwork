package database;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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
		
		Element newChild = doc.createElement(NomiDB.TAG_UTENTE.getNome());
		newChild.setAttribute("id", username); //come attributo l'username
		
		Element hashNode = doc.createElement(NomiDB.TAG_HASH.getNome()); //ha un nodo figlio 
		hashNode.setTextContent(hashChar);
		
		newChild.appendChild(hashNode);

		doc.appendChild(newChild);

	}

}
