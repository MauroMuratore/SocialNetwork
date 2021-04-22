package newGUI.body.bacheca;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;
import lib.util.Nomi;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.GridLayout;

public class PanelVistaEvento extends JPanel {

	private JButton btnIscrizione;
	private JButton btnRevoca;
	private JButton btnCancella;
	private JButton btnIndietro;
	private JCheckBox checkAttrezzatura;
	private JCheckBox checkIstruttore;


	/**
	 * Create the panel.
	 */
	public PanelVistaEvento(Evento evento, ActionListener al) {


		setLayout(new BorderLayout(0, 0));

		JPanel panelCenter = new JPanel();
		add(panelCenter, BorderLayout.CENTER);

		JPanel panelLbl = new JPanel();
		panelLbl.setLayout(new GridLayout(30, 0, 0, 5));
		panelLbl.setAlignmentX(LEFT_ALIGNMENT);

		JLabel lblTitolo =new JLabel(evento.getTitolo().toString());
		lblTitolo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblTitolo);

		JLabel lblPartecipantiNecessari =new JLabel(evento.getPartecipantiNecessari().toString());
		lblPartecipantiNecessari.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblPartecipantiNecessari);

		if(evento.getTolleranzaPartecipanti()!=null) {
			JLabel lblTolleranza = new JLabel(evento.getTolleranzaPartecipanti().toString());
			lblTolleranza.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblTolleranza);
		}
		JLabel lblTermineUltimo =new JLabel(evento.getTermineUltimo().toString());
		lblTermineUltimo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblTermineUltimo);

		if(evento.getTermineUltimoRitiro()!=null) {
			JLabel lblTermineUltimoRitiro = new JLabel(evento.getTermineUltimoRitiro().toString());
			lblTermineUltimoRitiro.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblTermineUltimoRitiro);

		}

		JLabel lblLuogo = new JLabel(evento.getLuogo().toString());
		lblLuogo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblLuogo);

		JLabel lblDataInizio = new JLabel(evento.getDataInizio().toString());
		lblDataInizio.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblDataInizio);

		if(evento.getDataFine()!=null) {
			JLabel lblDataFine =  new JLabel(evento.getDataFine().toString());
			lblDataFine.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblDataFine);
		}


		if(evento.getDurata()!=null) {
			JLabel lblDurata = new JLabel(evento.getDurata().toString());
			lblDurata.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblDurata);
		}


		JLabel lblQuota = new JLabel(evento.getQuotaIndividuale().toString());
		lblQuota.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblQuota);


		if(evento.getCompresoQuota()!=null) {
			JLabel lblCompresoQuota = new JLabel(evento.getCompresoQuota().toString());
			lblCompresoQuota.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblCompresoQuota);
		}


		if(evento.getNote()!=null) {
			JLabel lblNote = new JLabel(evento.getNote().toString());
			lblNote.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblNote);

		}

		if(evento.getClass().equals(PartitaCalcioEvento.class)) {
			JLabel lblSesso = new JLabel(((PartitaCalcioEvento)evento).getSesso().toString());
			lblSesso.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelLbl.add(lblSesso);

			if(((PartitaCalcioEvento)evento).getEta()!=null) {
				JLabel lblEta = new JLabel(((PartitaCalcioEvento)evento).getEta().toString());
				lblEta.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
				panelLbl.add(lblEta);

			}
		}

		else if(evento.getClass().equals(EscursioneMontagnaEvento.class)) {

			if(((EscursioneMontagnaEvento)evento).getAttrezzatura()!=null) {
				JPanel panelAttr = new JPanel();
				JLabel lblAttrezzatura = new JLabel(((EscursioneMontagnaEvento)evento).getAttrezzatura().toString()); 
				lblAttrezzatura.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
				panelAttr.add(lblAttrezzatura);
				checkAttrezzatura = new JCheckBox();
				checkAttrezzatura.addActionListener(al);
				checkAttrezzatura.setActionCommand("Select Att");
				panelAttr.add(checkAttrezzatura);
				panelAttr.setAlignmentX(LEFT_ALIGNMENT);
				panelLbl.add(panelAttr);

			}

			if(((EscursioneMontagnaEvento)evento).getIstruttore()!=null) {

				JPanel panelIstr = new JPanel();
				JLabel lblIstruttore = new JLabel(((EscursioneMontagnaEvento)evento).getIstruttore().toString());
				lblIstruttore.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
				checkIstruttore = new JCheckBox();
				checkIstruttore.addActionListener(al);
				checkIstruttore.setActionCommand("Select Istr");
				panelIstr.add(lblIstruttore);
				panelIstr.add(checkIstruttore);
				panelIstr.setAlignmentX(LEFT_ALIGNMENT);
				panelLbl.add(panelIstr);
			}

		}
		JLabel lblStato = new JLabel("STATO EVENTO " + evento.getStato().toString());
		lblStato.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblStato);

		JLabel lblProprietario = new JLabel("PROPRIETARIO " + evento.getProprietario());
		lblProprietario.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblProprietario);

		String partecipanti = "PARTECIPANTI: ";

		for(String partecipante: evento.getPartecipanti()) {
			partecipanti = partecipanti.concat(partecipante + " ");
		}

		JLabel lblPartecipanti = new JLabel(partecipanti);
		lblPartecipanti.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelLbl.add(lblPartecipanti);


		panelCenter.setLayout(new BorderLayout(0, 0));



		ScrollPane scrollPane  = new ScrollPane();
		scrollPane.add(panelLbl);
		panelCenter.add(scrollPane);

		JPanel panelButton = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelButton.getLayout();
		flowLayout.setHgap(20);
		flowLayout.setVgap(0);
		add(panelButton, BorderLayout.SOUTH);

		btnIscrizione = new JButton(Nomi.AZIONE_ISCRIZIONE.getNome());
		btnIscrizione.addActionListener(al);
		btnIscrizione.setActionCommand(Nomi.AZIONE_ISCRIZIONE.getNome());
		btnRevoca = new JButton(Nomi.AZIONE_DISISCRIZIONE.getNome());
		btnRevoca.addActionListener(al);
		btnRevoca.setActionCommand(Nomi.AZIONE_DISISCRIZIONE.getNome());
		btnCancella = new JButton(Nomi.AZIONE_CANCELLA_EVENTO.getNome());
		btnCancella.addActionListener(al);
		btnCancella.setActionCommand(Nomi.AZIONE_CANCELLA_EVENTO.getNome());
		btnIndietro = new JButton("Indietro");
		btnIndietro.addActionListener(al);
		btnIndietro.setActionCommand("Indietro");

		panelButton.add(btnIscrizione);
		panelButton.add(btnRevoca);
		panelButton.add(btnCancella);
		panelButton.add(btnIndietro);

	}

}
