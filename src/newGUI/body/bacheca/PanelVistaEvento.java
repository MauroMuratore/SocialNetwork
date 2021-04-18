package newGUI.body.bacheca;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.border.Border;

import java.awt.BorderLayout;

public class PanelVistaEvento extends JPanel {

	private JButton btnIscrizione;
	private JButton btnRevoca;
	private JButton btnCancella;
	private JButton btnIndietro;

	/**
	 * Create the panel.
	 */
	public PanelVistaEvento(Evento evento, ActionListener al) {

		setLayout(new BorderLayout(0, 0));

		JPanel panelLabel = new JPanel();
		add(panelLabel, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();


		scrollPane.add(new JLabel(evento.getTitolo().toString()));

		scrollPane.add(new JLabel(evento.getPartecipantiNecessari().toString()));

		if(evento.getTolleranzaPartecipanti()!=null)
			scrollPane.add(new JLabel(evento.getTolleranzaPartecipanti().toString()));

		scrollPane.add(new JLabel(evento.getTermineUltimo().toString()));

		if(evento.getTermineUltimoRitiro()!=null)
			scrollPane.add(new JLabel(evento.getTermineUltimoRitiro().toString()));


		scrollPane.add(new JLabel(evento.getLuogo().toString()));

		scrollPane.add(new JLabel(evento.getDataInizio().toString()));

		if(evento.getDataFine()!=null)
			scrollPane.add(new JLabel(evento.getDataFine().toString()));

		if(evento.getDurata()!=null) 
			scrollPane.add(new JLabel(evento.getDurata().toString()));

		scrollPane.add(new JLabel(evento.getQuotaIndividuale().toString()));

		if(evento.getCompresoQuota()!=null)
			scrollPane.add(new JLabel(evento.getCompresoQuota().toString()));

		if(evento.getNote()!=null)
			scrollPane.add(new JLabel(evento.getNote().toString()));

		if(evento.getClass().equals(PartitaCalcioEvento.class)) {

			scrollPane.add(new JLabel(((PartitaCalcioEvento)evento).getSesso().toString()));

			if(((PartitaCalcioEvento)evento).getEta()!=null) 
				scrollPane.add(new JLabel(((PartitaCalcioEvento)evento).getEta().toString()));
		}
		
		else if(evento.getClass().equals(EscursioneMontagnaEvento.class)) {
			
			if(((EscursioneMontagnaEvento)evento).getAttrezzatura()!=null)
				scrollPane.add(new JLabel(((EscursioneMontagnaEvento)evento).getAttrezzatura().toString()));
			
			if(((EscursioneMontagnaEvento)evento).getIstruttore()!=null)
				scrollPane.add(new JLabel(((EscursioneMontagnaEvento)evento).getIstruttore().toString()));
		}
		scrollPane.add(new JLabel(evento.getStato().toString()));

		scrollPane.add(new JLabel("Proprietario " + evento.getProprietario()));

		String partecipanti = "Partecipanti: ";

		for(String partecipante: evento.getPartecipanti()) {
			partecipanti.concat(partecipante + " ");
		}

		scrollPane.add(new JLabel(partecipanti));


		panelLabel.setLayout(new BorderLayout(0, 0));

		panelLabel.add(scrollPane);

		JPanel panelButton = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelButton.getLayout();
		flowLayout.setHgap(0);
		flowLayout.setVgap(0);
		add(panelButton, BorderLayout.SOUTH);

		btnIscrizione = new JButton("Iscrizione");
		btnIscrizione.addActionListener(al);
		btnIscrizione.setActionCommand("Iscrizione");
		btnRevoca = new JButton("Discrizione");
		btnRevoca.addActionListener(al);
		btnRevoca.setActionCommand("Discrizione");
		btnCancella = new JButton("Cancella Evento");
		btnCancella.addActionListener(al);
		btnCancella.setActionCommand("Cancella Evento");
		btnIndietro = new JButton("Indietro");
		btnIndietro.addActionListener(al);
		btnIndietro.setActionCommand("Indietro");

		panelButton.add(btnIscrizione);
		panelButton.add(btnRevoca);
		panelButton.add(btnCancella);
		panelButton.add(btnIndietro);

	}

}
