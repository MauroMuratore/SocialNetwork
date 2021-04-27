package server.database;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import lib.util.Log;
import lib.util.Nomi;

public class FactoryDocXML {
	
	public Document creaDocument(Nomi fileName) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc=null;
		try {
			builder = factory.newDocumentBuilder();
			File file = new File(fileName.getNome());
			if(!file.exists()) {
				String os = System.getProperty("os.name");
				if(os.startsWith("Windows")){
					file = new File("..\\" + fileName.getNome());
				}
				else
					file = new File("../" + fileName.getNome());
			}
			doc = builder.parse(file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			Log.writeErrorLog(this.getClass(), "errore nella creazione del docXML");
			e.printStackTrace();
		}
		
		//Log.writeRoutineLog(this.getClass(),"apertura doc " + file.getNome());
		return doc;
	}

}
