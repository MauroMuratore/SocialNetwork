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
import cervello.Categoria;
import cervello.Evento;
import cervello.Invito;
import cervello.Notifica;
import cervello.PartitaCalcioEvento;
import cervello.StatoEvento;
import cervello.Utente;

public class ScriviXML {


	/**
	 * scrive alla fine del file le credenziali dell'utente
	 * @param username
	 * @param hash
	 * @param categoriePref 
	 * @param etaMax 
	 * @param etaMin 
	 */
	public void aggiungiUtente(String username, byte[] hash, int etaMin, int etaMax, String[] categoriePref) {
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

		Element nodoUtente = doc.createElement(NomiDB.TAG_UTENTE.getNome());
		Element childName=doc.createElement(NomiDB.TAG_NOME.getNome());
		childName.setTextContent(username);
		Element childPW = doc.createElement(NomiDB.TAG_HASH.getNome());
		childPW.setTextContent(hashChar);
		Element childNotifiche = doc.createElement(NomiDB.TAG_ELENCO.getNome());

		nodoUtente.appendChild(childName);
		nodoUtente.appendChild(childPW);
		nodoUtente.appendChild(childNotifiche);

		//eta min
		Element etaMinNodo = doc.createElement(NomiDB.ATT_ETA_MIN.getNome());
		etaMinNodo.setTextContent(String.valueOf(etaMin));
		nodoUtente.appendChild(etaMinNodo);

		//eta massima
		Element etaMaxNodo = doc.createElement(NomiDB.ATT_ETA_MAX.getNome());
		etaMaxNodo.setTextContent(String.valueOf(etaMax));
		nodoUtente.appendChild(etaMaxNodo);

		//interessi
		Element interessi = doc.createElement(NomiDB.ATT_INTERESSI.getNome());

		for(String cat: categoriePref) {
			Element categoriaNodo =null;
			categoriaNodo = doc.createElement(NomiDB.TAG_NOME.getNome());
			categoriaNodo.setTextContent(cat);
			interessi.appendChild(categoriaNodo);

		}
		nodoUtente.appendChild(interessi);
		
		Element eventicreati = doc.createElement(NomiDB.ATT_EVENTI_CREATI.getNome());
		nodoUtente.appendChild(eventicreati);
		root.appendChild(nodoUtente);

		//effetivament per scrivere
		scriviSuFile(doc, NomiDB.FILE_UTENTI);


		System.out.println("aggiunto utente " + username + " eta min " + etaMin + " hashChar " +hashChar );
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

		//eventi creati dall'utente
		Element eventiCreati=(Element) nodoUtente.getElementsByTagName(NomiDB.ATT_EVENTI_CREATI.getNome()).item(0);
		if(eventiCreati==null)
			eventiCreati=doc.createElement(NomiDB.ATT_EVENTI_CREATI.getNome());

		for(int id: utente.getIdEventi()) {
			boolean notPresent = true;
			for(int i=0; i<eventiCreati.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).getLength(); i++) {
				Element idEventoNodo = (Element) eventiCreati.getElementsByTagName(NomiDB.TAG_EVENTO.getNome()).item(i);
				int idEvento = Integer.parseInt(idEventoNodo.getTextContent());
				if(id==idEvento) {
					notPresent = false;
					break;
				}
			}
			Element idEventoNodo = null;
			if(notPresent) {
				idEventoNodo = doc.createElement(NomiDB.TAG_EVENTO.getNome());
				idEventoNodo.setTextContent(String.valueOf(id));
				eventiCreati.appendChild(idEventoNodo);
			}
		}
		nodoUtente.appendChild(eventiCreati);

		//eta minima
		Element etaMin = (Element) nodoUtente.getElementsByTagName(NomiDB.ATT_ETA_MIN.getNome()).item(0);
		if(etaMin == null)
			etaMin = doc.createElement(NomiDB.ATT_ETA_MIN.getNome());
		etaMin.setTextContent(String.valueOf(utente.getEtaMin()));
		nodoUtente.appendChild(etaMin);

		//eta massima
		Element etaMax = (Element) nodoUtente.getElementsByTagName(NomiDB.ATT_ETA_MAX.getNome()).item(0);
		if(etaMax == null)
			etaMax = doc.createElement(NomiDB.ATT_ETA_MAX.getNome());
		etaMax.setTextContent(String.valueOf(utente.getEtaMax()));
		nodoUtente.appendChild(etaMax);

		//interessi
		Element interessi = (Element) nodoUtente.getElementsByTagName(NomiDB.ATT_INTERESSI.getNome()).item(0);
		if(interessi==null)
			interessi = doc.createElement(NomiDB.ATT_INTERESSI.getNome());

		for(String cat: utente.getInteressi()) {
			boolean notPresent = true;
			for(int i=0; i<interessi.getElementsByTagName(NomiDB.TAG_NOME.getNome()).getLength(); i++) {
				Element categoriaNodo = (Element) interessi.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(i);
				String categoria = categoriaNodo.getTextContent();
				if(categoria.equals(cat)) {
					notPresent = false;
					break;
				}
			}
			Element categoriaNodo =null;
			if(notPresent) {
				categoriaNodo = doc.createElement(NomiDB.TAG_NOME.getNome());
				categoriaNodo.setTextContent(cat);
				interessi.appendChild(categoriaNodo);
			}
		}
		nodoUtente.appendChild(interessi);

		System.out.println("salvo utente " + utente.getUsername() + " eta min " +utente.getEtaMin());
		scriviSuFile(doc, NomiDB.FILE_UTENTI);


	}

	public void scriviCategoriaPartitaCalcio(Categoria cat) {
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

		Element categoria = (Element) doc.getElementsByTagName(NomiDB.TAG_CATEGORIA.getNome()).item(0);

		for(String personaInteressata: (LinkedList<String>) cat.getPersoneInteressate()) {
			boolean notPresent = true;
			for(int i=0; i<categoria.getElementsByTagName(NomiDB.PERSONE_INTERESSATE.getNome()).getLength(); i++) {
				Element personaNodo = (Element) categoria.getElementsByTagName(NomiDB.PERSONE_INTERESSATE.getNome()).item(i);
				String persona = personaNodo.getTextContent();
				if(persona.equals(personaInteressata)) {
					notPresent = false;
					break;
				}
			}
			Element personaNodo = null;
			if(notPresent) {
				personaNodo = doc.createElement(NomiDB.PERSONE_INTERESSATE.getNome());
				personaNodo.setTextContent(personaInteressata);
				categoria.appendChild(personaNodo);
			}
		}

		scriviSuFile(doc, NomiDB.FILE_PARTITA_CALCIO);

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
				sovrascriviCampo(evento.getTolleranzaPartecipanti(), nodo, doc);
				sovrascriviCampo(evento.getTermineUltimoRitiro(), nodo, doc);
				scriviPartecipanti(evento.getPartecipanti(), nodo, doc);
				scriviStato(nodo, evento.getStato(), doc);
				scriviSuFile(doc, NomiDB.FILE_PARTITA_CALCIO);
				return;
			}
		}

		Element newEvento = doc.createElement(NomiDB.TAG_EVENTO.getNome());
		newEvento.setAttribute("id", String.valueOf(evento.getIdEvento())); 
		Element proprietario = doc.createElement(NomiDB.CAMPO_PROPRIETARIO.getNome());
		proprietario.setTextContent(evento.getProprietario());
		newEvento.appendChild(proprietario);
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
		scriviCampo(evento.getTolleranzaPartecipanti(), newEvento, doc);
		scriviCampo(evento.getTermineUltimoRitiro(), newEvento, doc);
		scriviPartecipanti(evento.getPartecipanti(), newEvento, doc);


		Element statoEvento = doc.createElement(NomiDB.CAMPO_STATO_EVENTO.getNome());
		Element stato = doc.createElement(NomiDB.STATO_EVENTO.getNome());
		if(evento.getStato().equals(StatoEvento.CHIUSO))
			stato.setTextContent(NomiDB.STATO_EVENTO_CHIUSO.getNome());
		else if(evento.getStato().equals(StatoEvento.CONCLUSO))
			stato.setTextContent(NomiDB.STATO_EVENTO_CONCLUSO.getNome());
		else if(evento.getStato().equals(StatoEvento.APERTO))
			stato.setTextContent(NomiDB.STATO_EVENTO_APERTO.getNome());
		else if(evento.getStato().equals(StatoEvento.FALLITO))
			stato.setTextContent(NomiDB.STATO_EVENTO_FALLITO.getNome());
		statoEvento.appendChild(stato);
		newEvento.appendChild(statoEvento);
		elenco.appendChild(newEvento);

		scriviSuFile(doc, NomiDB.FILE_PARTITA_CALCIO);

	}

	public void scriviStato(Element nodoEvento, StatoEvento statoEvento, Document doc) {
		System.out.println("scrivo stato evento");
		Element nodoStatoEvento = (Element) nodoEvento.getElementsByTagName(NomiDB.CAMPO_STATO_EVENTO.getNome()).item(0);
		int lastIndex = nodoStatoEvento.getElementsByTagName(NomiDB.STATO_EVENTO.getNome()).getLength()-1;
		Element lastChild = (Element) nodoStatoEvento.getElementsByTagName(NomiDB.STATO_EVENTO.getNome()).item(lastIndex);
		String oldStatoString = lastChild.getTextContent();
		StatoEvento oldStato=null;
		if(oldStatoString.equals(NomiDB.STATO_EVENTO_CHIUSO.getNome())) {
			oldStato=StatoEvento.CHIUSO;
		}
		else if(oldStatoString.equals(NomiDB.STATO_EVENTO_APERTO.getNome())) {
			oldStato=StatoEvento.APERTO;
		}
		else if(oldStatoString.equals(NomiDB.STATO_EVENTO_FALLITO.getNome())) {
			oldStato=StatoEvento.FALLITO;
		}
		else if(oldStatoString.equals(NomiDB.STATO_EVENTO_CONCLUSO.getNome())) {
			oldStato=StatoEvento.CONCLUSO;
		}

		if(!oldStato.equals(statoEvento)) {
			System.out.println("stato evento cambiato");
			Element newStato = doc.createElement(NomiDB.STATO_EVENTO.getNome());
			if(statoEvento.equals(StatoEvento.APERTO)) {
				newStato.setTextContent(NomiDB.STATO_EVENTO_APERTO.getNome());
			}

			else if(statoEvento.equals(StatoEvento.CHIUSO)) {
				newStato.setTextContent(NomiDB.STATO_EVENTO_CHIUSO.getNome());

			}
			else if(statoEvento.equals(StatoEvento.FALLITO)) {
				newStato.setTextContent(NomiDB.STATO_EVENTO_FALLITO.getNome());

			}
			else if(statoEvento.equals(StatoEvento.CONCLUSO)) {
				newStato.setTextContent(NomiDB.STATO_EVENTO_CONCLUSO.getNome());
			}
			else if(statoEvento.equals(StatoEvento.CANCELLATO)) {
				newStato.setTextContent(NomiDB.STATO_EVENTO_CANCELLATO.getNome());
			}


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

		Element nodoLista = null;
		if(evento.getElementsByTagName(NomiDB.CAMPO_PARTECIPANTI.getNome()).item(0)==null)
			nodoLista = doc.createElement(NomiDB.CAMPO_PARTECIPANTI.getNome());
		else
			nodoLista = (Element) evento.getElementsByTagName(NomiDB.CAMPO_PARTECIPANTI.getNome()).item(0);

		for(int i=0; i<lista.size(); i++) {
			boolean isPresent=false;

			for (int index=0; index<nodoLista.getElementsByTagName(NomiDB.TAG_NOME.getNome()).getLength(); index++) {
				Element nodoPartecipante = (Element) nodoLista.getElementsByTagName(NomiDB.TAG_NOME.getNome()).item(index);
				if(nodoPartecipante.getTextContent().equals(lista.get(i)))
					isPresent=true;
				else
					nodoLista.removeChild(nodoPartecipante);

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
		int lunghezzaListaElenco = elenco.getElementsByTagName(NomiDB.TAG_NOTIFICA.getNome()).getLength();
		System.out.println("notifiche gia presenti " + lunghezzaListaElenco + " notifiche utente " + listaNotifiche.size());
		for(int index=lunghezzaListaElenco; index<listaNotifiche.size(); index++) {
			Notifica n = listaNotifiche.get(index);
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
			if(n.getMessaggio().contains(Notifica.INVITO)) {
				GregorianCalendar data = ((Invito) n).getDataInvito();
				Element nodoDataInvito = doc.createElement(NomiDB.TAG_DATA.getNome());
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
				
				nodoDataInvito.appendChild(annoNodo);
				nodoDataInvito.appendChild(meseNodo);
				nodoDataInvito.appendChild(giornoNodo);
				nodoDataInvito.appendChild(oraNodo);
				
				nodoNotifica.appendChild(nodoDataInvito);
			}

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
			boolean notPresent=true;
			for(int i=0; i<elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome()).getLength(); i++) {
				Element utente = (Element) elenco.getElementsByTagName(NomiDB.TAG_UTENTE.getNome()).item(i);
				if(key.equals(utente.getTextContent())) {
					notPresent=false;
					for(int j=utente.getElementsByTagName(NomiDB.TAG_NOTIFICA.getNome()).getLength();
							j<notifichePendenti.get(key).size(); j++) {
						Notifica not = notifichePendenti.get(key).get(j);
						Element nodoNotifica = doc.createElement(NomiDB.TAG_NOTIFICA.getNome());
						Element nodoMessaggio = doc.createElement(NomiDB.TAG_DESCRIZIONE.getNome());
						Element nodoEvento = doc.createElement(NomiDB.TAG_ID.getNome());
						Element nodoLetto = doc.createElement(NomiDB.TAG_LETTO.getNome());

						nodoMessaggio.setTextContent(not.getMessaggio());
						nodoEvento.setTextContent(String.valueOf(not.getEvento().getIdEvento()));
						nodoLetto.setTextContent(String.valueOf(not.getLetta()));

						if(not.getMessaggio().contains(Notifica.INVITO)) {
							GregorianCalendar data = ((Invito) not).getDataInvito();
							Element nodoDataInvito = doc.createElement(NomiDB.TAG_DATA.getNome());
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
							
							nodoDataInvito.appendChild(annoNodo);
							nodoDataInvito.appendChild(meseNodo);
							nodoDataInvito.appendChild(giornoNodo);
							nodoDataInvito.appendChild(oraNodo);
							
							nodoNotifica.appendChild(nodoDataInvito);
						}

						
						nodoNotifica.appendChild(nodoMessaggio);
						nodoNotifica.appendChild(nodoEvento);
						nodoNotifica.appendChild(nodoLetto);
						utente.appendChild(nodoNotifica);
					}
					break;
				}				
			}
			if(!notPresent) {
				Element utente= doc.createElement(NomiDB.TAG_UTENTE.getNome());
				for(Notifica not: notifichePendenti.get(key)) {
					Element nodoNotifica = doc.createElement(NomiDB.TAG_NOTIFICA.getNome());
					Element nodoMessaggio = doc.createElement(NomiDB.TAG_DESCRIZIONE.getNome());
					Element nodoEvento = doc.createElement(NomiDB.TAG_ID.getNome());
					Element nodoLetto = doc.createElement(NomiDB.TAG_LETTO.getNome());

					nodoMessaggio.setTextContent(not.getMessaggio());
					nodoEvento.setTextContent(String.valueOf(not.getEvento().getIdEvento()));
					nodoLetto.setTextContent(String.valueOf(not.getLetta()));

					
					if(not.getMessaggio().contains(Notifica.INVITO)) {
						GregorianCalendar data = ((Invito) not).getDataInvito();
						Element nodoDataInvito = doc.createElement(NomiDB.TAG_DATA.getNome());
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
						
						nodoDataInvito.appendChild(annoNodo);
						nodoDataInvito.appendChild(meseNodo);
						nodoDataInvito.appendChild(giornoNodo);
						nodoDataInvito.appendChild(oraNodo);
						
						nodoNotifica.appendChild(nodoDataInvito);
					}

					nodoNotifica.appendChild(nodoMessaggio);
					nodoNotifica.appendChild(nodoEvento);
					nodoNotifica.appendChild(nodoLetto);
					utente.appendChild(nodoNotifica);
				}
				elenco.appendChild(utente);
			}


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
			if(descrizione.equals(notifica.getMessaggio()) && intId == notifica.getEvento().getIdEvento()) {
				System.out.println("cancellaNotifica if condition " + descrizione);
				listaNotifiche.removeChild(nodoNotifica);
				break;
			}
		}

		scriviSuFile(doc, NomiDB.FILE_UTENTI);

	}



}




































