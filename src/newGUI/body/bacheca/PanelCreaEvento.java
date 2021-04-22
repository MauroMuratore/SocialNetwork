package newGUI.body.bacheca;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;

import lib.util.Nomi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import java.awt.ScrollPane;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

public class PanelCreaEvento extends JPanel {

	private JPanel panelCentrale;
	private JPanel panelButton;
	private JButton btnCreaEvento;
	private JButton btnIndietro;
	private ScrollPane scrollPane;
	private JTextField textTitolo;
	private JTextField textPartecipanti;
	private JTextField textTolleranzaPartecipanti;
	private JTextField textTermineUltimo;
	private JTextField textTermineUltimoRitiro;
	private JTextField textLuogo;
	private JTextField textDataInizio;
	private JTextField textOraInizio;
	private JTextField textDataFine;
	private JTextField textOraFine;
	private JTextField textDurata;
	private JTextField textQuota;
	private JTextField textCompresoQuota;
	private JTextField textNote;
	private JTextField textSesso;
	private JTextField textEta;
	private JTextField textAttrezzatura;
	private JTextField textIstruttore;
	private JList invitabili;

	public PanelCreaEvento(String categoria, List<String> personeInteressate, ActionListener al) {
		setLayout(new BorderLayout(0, 0));
		panelCentrale = new JPanel();
		panelButton = new JPanel();

		add(panelCentrale, BorderLayout.CENTER);
		panelCentrale.setLayout(new BorderLayout(5, 5));

		JPanel panelInterno = new JPanel();
		panelInterno.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weightx=1;
		c.weighty=1;

		c.gridx = 0;
		c.gridy = 0;
		JLabel lblTitolo = new JLabel("Titolo* ");
		lblTitolo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblTitolo, c);
		textTitolo = new JTextField(25);
		textTitolo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 0;
		panelInterno.add(textTitolo, c);

		c.gridx = 0;
		c.gridy = 1;
		JLabel lblPart = new JLabel("Partecipanti Necessari* ");
		lblPart.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblPart, c);
		textPartecipanti = new JTextField(25);
		textPartecipanti.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 1;
		panelInterno.add(textPartecipanti, c);

		c.gridx = 0;
		c.gridy = 2;
		JLabel lblTol = new JLabel("Tolleranza Partecipanti ");
		lblTol.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblTol, c);
		textTolleranzaPartecipanti = new JTextField(25);
		textTolleranzaPartecipanti.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 2;
		panelInterno.add(textTolleranzaPartecipanti, c);

		c.gridx = 0;
		c.gridy = 3;
		JLabel lblTUI = new JLabel("Termine Ultimo Iscrizione* ");
		lblTUI.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblTUI, c);
		textTermineUltimo = new JTextField(25);
		textTermineUltimo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 3;
		panelInterno.add(textTermineUltimo, c);

		c.gridx = 0;
		c.gridy = 4;
		JLabel lblTUR = new JLabel("Termine Ultimo Ritiro Iscrizione ");
		lblTUR.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblTUR, c);
		textTermineUltimoRitiro = new JTextField(25);
		textTermineUltimoRitiro.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 4;
		panelInterno.add(textTermineUltimoRitiro, c);

		c.gridx = 0;
		c.gridy = 5;
		JLabel lblLuogo = new JLabel("Luogo* ");
		lblLuogo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblLuogo, c);
		textLuogo = new JTextField(25);
		textLuogo.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 5;
		panelInterno.add(textLuogo, c);

		c.gridx = 0;
		c.gridy = 6;
		JLabel lblDataInizio = new JLabel("Data Inizio* ");
		lblDataInizio.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblDataInizio, c);
		textDataInizio = new JTextField(25);
		textDataInizio.setText("GG/MM/AAAA");
		textDataInizio.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 6;
		panelInterno.add(textDataInizio, c);
		textOraInizio = new JTextField(25);
		textOraInizio.setText("HH:MM");
		textOraInizio.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 2;
		c.gridy = 6;
		panelInterno.add(textOraInizio, c);

		c.gridx = 0;
		c.gridy = 7;
		JLabel lblDataFine = new JLabel("Data Fine ");
		lblDataFine.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblDataFine, c);
		textDataFine = new JTextField(25);
		textDataFine.setText("GG/MM/AAAA");
		textDataFine.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 7;
		panelInterno.add(textDataFine, c);
		textOraFine = new JTextField(25);
		textOraFine.setText("HH:MM");
		
		textOraFine.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 2;
		c.gridy = 7;
		panelInterno.add(textOraFine, c);

		c.gridx = 0;
		c.gridy = 8;
		JLabel lblDurata = new JLabel("Durata ");
		lblDurata.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblDurata, c);
		textDurata = new JTextField(25);
		textDurata.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 8;
		panelInterno.add(textDurata, c);

		c.gridx = 0;
		c.gridy = 9;
		JLabel lblQuota = new JLabel("Quota* ");
		lblQuota.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblQuota, c);
		textQuota = new JTextField(25);
		textQuota.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 9;
		panelInterno.add(textQuota, c);

		c.gridx = 0;
		c.gridy = 10;
		JLabel lblCompreso = new JLabel("Compreso nella quota");
		lblCompreso.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblCompreso, c);
		textCompresoQuota = new JTextField(25);
		textCompresoQuota.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 10;
		panelInterno.add(textCompresoQuota, c);

		c.gridx = 0;
		c.gridy = 11;
		JLabel lblNote = new JLabel("Note ");
		lblNote.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		panelInterno.add(lblNote, c);
		textNote = new JTextField(25);
		textNote.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx = 1;
		c.gridy = 11;
		panelInterno.add(textNote, c);

		if (categoria.equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			c.gridx = 0;
			c.gridy = 12;
			JLabel lblSesso = new JLabel("Sesso* ");
			lblSesso.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelInterno.add(lblSesso, c);
			textSesso = new JTextField(25);
			textSesso.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			c.gridx = 1;
			c.gridy = 12;
			panelInterno.add(textSesso, c);

			c.gridx = 0;
			c.gridy = 13;
			JLabel lblEta = new JLabel("Eta ");
			lblEta.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelInterno.add(lblEta, c);
			textEta = new JTextField(25);
			textEta.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			c.gridx = 1;
			c.gridy = 13;
			panelInterno.add(textEta, c);
		} else if (categoria.equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			c.gridx = 0;
			c.gridy = 12;
			JLabel lblAtt = new JLabel("Attrezzatura ");
			lblAtt.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelInterno.add(lblAtt, c);
			textAttrezzatura = new JTextField(25);
			textAttrezzatura.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			c.gridx = 1;
			c.gridy = 12;
			panelInterno.add(textAttrezzatura, c);

			c.gridx = 0;
			c.gridy = 13;
			JLabel lblIstr = new JLabel("Istruttore ");
			lblIstr.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			panelInterno.add(lblIstr, c);
			textIstruttore = new JTextField(25);
			textIstruttore.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
			c.gridx = 1;
			c.gridy = 13;
			panelInterno.add(textIstruttore, c);
		}
		

		JLabel lblInvitabili = new JLabel("Lista persone invitabili");
		lblInvitabili.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx=0;
		c.gridy=14;
		panelInterno.add(lblInvitabili, c);
		
		DefaultListModel<String> listaModel = new DefaultListModel<>();
		for(String p: personeInteressate) {
			listaModel.addElement(p);
		}
		
		invitabili = new JList(listaModel);
		invitabili.setFont(new Font(getFont().getFontName(), Font.PLAIN, 24));
		c.gridx=1;
		c.gridy=14;
		c.gridwidth=2;
		panelInterno.add(invitabili, c);
		

		scrollPane = new ScrollPane();
		panelCentrale.add(scrollPane);

		scrollPane.add(panelInterno);
		
		btnCreaEvento = new JButton(Nomi.AZIONE_CREA_EVENTO.getNome());
		btnCreaEvento.addActionListener(al);
		btnCreaEvento.setActionCommand(Nomi.AZIONE_CREA_EVENTO.getNome());
		btnIndietro = new JButton("Indietro");
		btnIndietro.addActionListener(al);
		btnIndietro.setActionCommand("Indietro");

		panelButton.add(btnCreaEvento);
		panelButton.add(btnIndietro);
		add(panelButton, BorderLayout.SOUTH);

	}

	public String getTitolo() {
		return textTitolo.getText();
	}

	public String getPartecipanti() {
		return textPartecipanti.getText();
	}

	public String getTolleranzaPartecipanti() {
		return textTolleranzaPartecipanti.getText();
	}

	public String getTermineUltimo() {
		return textTermineUltimo.getText();
	}

	public String getTermineUltimoRitiro() {
		return textTermineUltimoRitiro.getText();
	}

	public String getLuogo() {
		return textLuogo.getText();
	}

	public String getDataInizio() {
		return textDataInizio.getText();
	}

	public String getOraInizio() {
		return textOraInizio.getText();
	}

	public String getDataFine() {
		return textDataFine.getText();
	}

	public String getOraFine() {
		return textOraFine.getText();
	}

	public String getDurata() {
		return textDurata.getText();
	}

	public String getQuota() {
		return textQuota.getText();
	}

	public String getCompresoQuota() {
		return textCompresoQuota.getText();
	}

	public String getNote() {
		return textNote.getText();
	}

	public String getSesso() {
		return textSesso.getText();
	}

	public String getTextEta() {
		return textEta.getText();
	}

	public String getTextAttrezzatura() {
		return textAttrezzatura.getText();
	}

	public String getIstruttore() {
		return textIstruttore.getText();
	}

	public List<String> getInvitabili() {
		return invitabili.getSelectedValuesList();
	}
	
	
	
	
	

}
