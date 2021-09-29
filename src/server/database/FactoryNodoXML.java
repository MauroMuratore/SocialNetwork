package server.database;

import java.io.UnsupportedEncodingException;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lib.core.Campo;
import lib.core.Categoria;
import lib.core.Evento;
import lib.core.Invito;
import lib.core.Notifica;
import lib.core.StatoEvento;
import lib.core.Utente;
import lib.util.Log;
import lib.util.Nomi;

public class FactoryNodoXML {

	public Element creaNodoUtente(Document doc, Element elenco, Utente utente) {
		NodeList lista = elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome());
		Element nodoUtente = null;
		for(int i=0; i<lista.getLength(); i++) {
			String nome =((Element)lista.item(i)).getElementsByTagName(Nomi.TAG_NOME.getNome()).item(0).getTextContent();
			if(nome.equals(utente.getUsername())) {
				nodoUtente = (Element)lista.item(i);
			}
		}

		if(nodoUtente==null) {
			nodoUtente = doc.createElement(Nomi.TAG_UTENTE.getNome());
			Element childName=doc.createElement(Nomi.TAG_NOME.getNome());
			childName.setTextContent(utente.getUsername());
			Element childPW = doc.createElement(Nomi.TAG_HASH.getNome());
			try {
				childPW.setTextContent(utente.getPassword());
			} catch (DOMException e) {
				Log.writeErrorLog(this.getClass(), "errore nella creazione nodo utente");
				e.printStackTrace();
			}
			Element childNotifiche = doc.createElement(Nomi.TAG_ELENCO.getNome());

			nodoUtente.appendChild(childName);
			nodoUtente.appendChild(childPW);
			nodoUtente.appendChild(childNotifiche);
		}

		Element notifiche = creaNodoElencoNotifiche(doc, utente.getNotifiche(), nodoUtente);
		nodoUtente.appendChild(notifiche);

		//eventi creati dall'utente
		Element eventiCreati=(Element) nodoUtente.getElementsByTagName(Nomi.ATT_EVENTI_CREATI.getNome()).item(0);
		if(eventiCreati==null)
			eventiCreati=doc.createElement(Nomi.ATT_EVENTI_CREATI.getNome());

		for(int id: utente.getIdEventi()) {
			boolean notPresent = true;
			for(int i=0; i<eventiCreati.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).getLength(); i++) {
				Element idEventoNodo = (Element) eventiCreati.getElementsByTagName(Nomi.TAG_EVENTO.getNome()).item(i);
				int idEvento = Integer.parseInt(idEventoNodo.getTextContent());
				if(id==idEvento) {
					notPresent = false;
					break;
				}
			}
			Element idEventoNodo = null;
			if(notPresent) {
				idEventoNodo = doc.createElement(Nomi.TAG_EVENTO.getNome());
				idEventoNodo.setTextContent(String.valueOf(id));
				eventiCreati.appendChild(idEventoNodo);
			}
		}
		nodoUtente.appendChild(eventiCreati);

		//eta minima
		Element etaMin = (Element) nodoUtente.getElementsByTagName(Nomi.ATT_ETA_MIN.getNome()).item(0);
		if(etaMin == null)
			etaMin = doc.createElement(Nomi.ATT_ETA_MIN.getNome());
		etaMin.setTextContent(String.valueOf(utente.getEtaMin()));
		nodoUtente.appendChild(etaMin);

		//eta massima
		Element etaMax = (Element) nodoUtente.getElementsByTagName(Nomi.ATT_ETA_MAX.getNome()).item(0);
		if(etaMax == null)
			etaMax = doc.createElement(Nomi.ATT_ETA_MAX.getNome());
		etaMax.setTextContent(String.valueOf(utente.getEtaMax()));
		nodoUtente.appendChild(etaMax);

		//interessi
		Element interessi = (Element) nodoUtente.getElementsByTagName(Nomi.ATT_INTERESSI.getNome()).item(0);
		if(interessi==null)
			interessi = doc.createElement(Nomi.ATT_INTERESSI.getNome());

		for(String cat: utente.getInteressi()) {
			boolean notPresent = true;
			for(int i=0; i<interessi.getElementsByTagName(Nomi.TAG_NOME.getNome()).getLength(); i++) {
				Element categoriaNodo = (Element) interessi.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(i);
				String categoria = categoriaNodo.getTextContent();
				if(categoria.equals(cat)) {
					notPresent = false;
					break;
				}
			}
			Element categoriaNodo =null;
			if(notPresent) {
				categoriaNodo = doc.createElement(Nomi.TAG_NOME.getNome());
				categoriaNodo.setTextContent(cat);
				interessi.appendChild(categoriaNodo);
			}
		}
		nodoUtente.appendChild(interessi);

		return nodoUtente;
	}

	public Element creaNodoElencoNotifiche(Document doc, LinkedList<Notifica> listaNotifiche, Element nodoUtente) {
		Element elencoNotifiche = (Element) nodoUtente.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		if(elencoNotifiche==null)
			elencoNotifiche=doc.createElement(Nomi.ATT_EVENTI_CREATI.getNome());
		int lunghezzaListaElenco = elencoNotifiche.getElementsByTagName(Nomi.TAG_NOTIFICA.getNome()).getLength();
		for(int index=lunghezzaListaElenco; index<listaNotifiche.size(); index++) {
			Notifica n = listaNotifiche.get(index);
			Element nodoNotifica = creaNodoNotifica(doc, n);
			elencoNotifiche.appendChild(nodoNotifica);
		}
		return elencoNotifiche;
	}

	public Element creaNodoCategoria(Document doc, Categoria cat) {
		Element categoria = (Element) doc.getElementsByTagName(Nomi.TAG_CATEGORIA.getNome()).item(0);

		//controllo se è presente in cat ma non nel doc
		for(String personaInteressata: (LinkedList<String>) cat.getPersoneInteressate()) {
			boolean notPresentInDoc = true;
			for(int i=0; i<categoria.getElementsByTagName(Nomi.PERSONE_INTERESSATE.getNome()).getLength(); i++) {
				Element personaNodo = (Element) categoria.getElementsByTagName(Nomi.PERSONE_INTERESSATE.getNome()).item(i);
				String persona = personaNodo.getTextContent();
				if(persona.equals(personaInteressata)) {
					notPresentInDoc = false;
					break;
				}
			}
			Element personaNodo = null;
			if(notPresentInDoc) {
				personaNodo = doc.createElement(Nomi.PERSONE_INTERESSATE.getNome());
				personaNodo.setTextContent(personaInteressata);
				categoria.appendChild(personaNodo);
			}
		}
		//controllo se è presente in doc ma non in cat
		for(int i=0; i<categoria.getElementsByTagName(Nomi.PERSONE_INTERESSATE.getNome()).getLength(); i++) {
			boolean presentInCat = false;
			Element personaNodo = (Element) categoria.getElementsByTagName(Nomi.PERSONE_INTERESSATE.getNome()).item(i);
			String persona = personaNodo.getTextContent();
			for(String personaInteressata: (LinkedList<String>) cat.getPersoneInteressate()) {
				if(persona.equals(personaInteressata)) {
					presentInCat=true;
					break;
				}				
			}
			if(!presentInCat) {
				categoria.removeChild(personaNodo);
			}
		}
		return categoria;
	}

	public Element creaNodoEvento(Document doc, Evento evento) {
		Element newEvento = doc.createElement(Nomi.TAG_EVENTO.getNome());
		newEvento.setAttribute("id", String.valueOf(evento.getIdEvento())); 
		Element proprietario = doc.createElement(Nomi.CAMPO_PROPRIETARIO.getNome());
		proprietario.setTextContent(evento.getProprietario());
		newEvento.appendChild(proprietario);
		return newEvento;
	}

	public Element creaNodoData( Document doc, GregorianCalendar data, Element nodoData) {
		Element annoNodo = doc.createElement(Nomi.TAG_ANNO.getNome());
		int annoInt =data.get(GregorianCalendar.YEAR);
		annoNodo.setTextContent( String.valueOf(annoInt) );

		Element meseNodo = doc.createElement(Nomi.TAG_MESE.getNome());
		int meseInt = data.get(GregorianCalendar.MONTH);
		meseNodo.setTextContent(String.valueOf(meseInt));

		Element giornoNodo = doc.createElement(Nomi.TAG_GIORNO.getNome());
		int giornoInt = data.get(GregorianCalendar.DAY_OF_MONTH);
		giornoNodo.setTextContent(String.valueOf(giornoInt));

		Element oraNodo = doc.createElement(Nomi.TAG_ORA.getNome());
		int oraInt = data.get(GregorianCalendar.HOUR_OF_DAY);
		oraNodo.setTextContent(String.valueOf(oraInt));

		nodoData.appendChild(annoNodo);
		nodoData.appendChild(meseNodo);
		nodoData.appendChild(giornoNodo);
		nodoData.appendChild(oraNodo);

		return nodoData;
	}

	public Element creaNodoCampo(Document doc, Campo campo) {
		Element nodoCampo = doc.createElement(Nomi.TAG_CAMPO.getNome());
		Element nome = doc.createElement(Nomi.TAG_NOME.getNome());
		nome.setTextContent(campo.getNome());
		nodoCampo.appendChild(nome);
		Element descrizione = doc.createElement(Nomi.TAG_DESCRIZIONE.getNome());
		descrizione.setTextContent(campo.getDescrizione());
		nodoCampo.appendChild(descrizione);
		Element valore = doc.createElement(Nomi.TAG_VALORE.getNome());
		if(campo.getValore()==null) {
			//non fare niente
		}
		else if(campo.getClasseValore().equals(GregorianCalendar.class)) { //scrittura della data
			GregorianCalendar data =((GregorianCalendar)campo.getValore());
			creaNodoData(doc, data, valore);
		}
		else //scrittura di qualsiasi altro campo
			valore.setTextContent(campo.getValoreString());
		nodoCampo.appendChild(valore);
		Element obbl = doc.createElement(Nomi.TAG_OBBL.getNome());
		obbl.setTextContent(Boolean.toString(campo.isObbligatorio()));
		nodoCampo.appendChild(obbl);

		return nodoCampo;
	}

	public Element creaNodoStatoEvento(Document doc, StatoEvento statoEvento) {
		Element newStato = doc.createElement(Nomi.STATO_EVENTO.getNome());
		if(statoEvento.equals(StatoEvento.APERTO)) {
			newStato.setTextContent(Nomi.STATO_EVENTO_APERTO.getNome());
		}

		else if(statoEvento.equals(StatoEvento.CHIUSO)) {
			newStato.setTextContent(Nomi.STATO_EVENTO_CHIUSO.getNome());

		}
		else if(statoEvento.equals(StatoEvento.FALLITO)) {
			newStato.setTextContent(Nomi.STATO_EVENTO_FALLITO.getNome());

		}
		else if(statoEvento.equals(StatoEvento.CONCLUSO)) {
			newStato.setTextContent(Nomi.STATO_EVENTO_CONCLUSO.getNome());
		}
		else if(statoEvento.equals(StatoEvento.CANCELLATO)) {
			newStato.setTextContent(Nomi.STATO_EVENTO_CANCELLATO.getNome());
		}
		return newStato;
	}

	public Element creaNodoPartecipanti(Document doc, LinkedList<String> lista, Element nodoEvento, String tagListaPartecipanti) {
		Element nodoLista = null;
		if(nodoEvento.getElementsByTagName(tagListaPartecipanti).item(0)==null) {
			nodoLista = doc.createElement(tagListaPartecipanti);
			Log.writeRoutineLog(this.getClass(),"creo il nodo lista di partecipanti " + tagListaPartecipanti, Log.LOW_PRIORITY);
		}
		else
			nodoLista = (Element) nodoEvento.getElementsByTagName(tagListaPartecipanti).item(0);
		if(lista.isEmpty()) {
			for (int index=0; index<nodoLista.getElementsByTagName(Nomi.TAG_NOME.getNome()).getLength(); index++) {
				Element nodoPartecipante = (Element) nodoLista.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(index);
				nodoLista.removeChild(nodoPartecipante);
			}
		}
		else for(int i=0; i<lista.size(); i++) {
			boolean isPresent=false;

			for (int index=0; index<nodoLista.getElementsByTagName(Nomi.TAG_NOME.getNome()).getLength(); index++) {
				Element nodoPartecipante = (Element) nodoLista.getElementsByTagName(Nomi.TAG_NOME.getNome()).item(index);
				if(nodoPartecipante.getTextContent().equals(lista.get(i)))
					isPresent=true;
				else {
					
					nodoLista.removeChild(nodoPartecipante);
				}
				Log.writeRoutineLog(this.getClass(), i + " " + isPresent, Log.LOW_PRIORITY );
				
			}
			Element nodoNome=null;
			if(!isPresent) {
				nodoNome = doc.createElement(Nomi.TAG_NOME.getNome());
				nodoNome.setTextContent(lista.get(i));
				nodoLista.appendChild(nodoNome);
			}

		}
		return nodoLista;
	}

	public Element creaNodoNotifichePendenti(Map<String, LinkedList<Notifica>> notifichePendenti, Document doc) {
		Element elenco = (Element) doc.getElementsByTagName(Nomi.TAG_ELENCO.getNome()).item(0);
		for(String key : notifichePendenti.keySet()) {
			boolean notPresent=true;
			for(int i=0; i<elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome()).getLength(); i++) {
				Element utente = (Element) elenco.getElementsByTagName(Nomi.TAG_UTENTE.getNome()).item(i);
				if(key.equals(utente.getTextContent())) {
					notPresent=false;
					for(int j=utente.getElementsByTagName(Nomi.TAG_NOTIFICA.getNome()).getLength();
							j<notifichePendenti.get(key).size(); j++) {
						Notifica not = notifichePendenti.get(key).get(j);
						Element nodoNotifica =creaNodoNotifica(doc, not);
						utente.appendChild(nodoNotifica);
					}
					break;
				}				
			}
			if(!notPresent) {
				Element utente= doc.createElement(Nomi.TAG_UTENTE.getNome());
				for(Notifica not: notifichePendenti.get(key)) {
					Element nodoNotifica = creaNodoNotifica(doc, not);
					utente.appendChild(nodoNotifica);
				}
				elenco.appendChild(utente);
			}


		}
		return elenco;
	}

	public Element creaNodoNotifica(Document doc, Notifica not) {
		Element nodoNotifica = doc.createElement(Nomi.TAG_NOTIFICA.getNome());
		Element nodoMessaggio = doc.createElement(Nomi.TAG_DESCRIZIONE.getNome());
		Element nodoEvento = doc.createElement(Nomi.TAG_ID.getNome());
		Element nodoLetto = doc.createElement(Nomi.TAG_LETTO.getNome());

		nodoMessaggio.setTextContent(not.getMessaggio());
		nodoEvento.setTextContent(String.valueOf(not.getEvento().getIdEvento()));
		nodoLetto.setTextContent(String.valueOf(not.getLetta()));


		if(not.getMessaggio().contains(Notifica.INVITO)) {
			GregorianCalendar data = ((Invito) not).getDataInvito();
			Element nodoDataInvito = doc.createElement(Nomi.TAG_DATA.getNome());
			creaNodoData(doc, data, nodoDataInvito);
			nodoNotifica.appendChild(nodoDataInvito);
		}

		nodoNotifica.appendChild(nodoMessaggio);
		nodoNotifica.appendChild(nodoEvento);
		nodoNotifica.appendChild(nodoLetto);

		return nodoNotifica;
	}


























}
