package database;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
			doc = builder.parse(new File(NomiDB.FILE_LOGIN.getNome()));
			
			
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

}
