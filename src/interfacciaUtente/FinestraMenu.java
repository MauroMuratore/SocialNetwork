package interfacciaUtente;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import lib.core.Categoria;
import lib.core.EscursioneMontagnaEvento;
import lib.core.Evento;
import lib.core.PartitaCalcioEvento;
import lib.core.StatoEvento;
import lib.core.Utente;
import lib.util.Nomi;
import server.SocialNetwork;

public class FinestraMenu {

	private JFrame frame;
	private JPanel panelInfo;
	private JPanel panelCategorie;
	private JButton btnPartitedicalcio;
	private JButton btnEscursione;
	private JLabel txtpnSelezionaLaCategoria;
	private JPanel bachecaPDC ;
	private JPanel bachecaEIM;
	private JPanel panelAP;
	private SocialNetwork sn;
	private Categoria pdc;
	private Categoria eim;
	private JPanel finestraEV;
	private int k=0;//serve per un for(dichiarata qua per questioni di visibilita)
	private UserInterface UI;
	private JPanel finestraCEVPartita;
	private JPanel finestraCEVEscu;
	private JPanel panelModificaDati;
	private Evento eventoCreato ;
	private TextArea textArea;
	private Label messaggioErr;
	private JPanel panelInvito;
	private JLabel confermaSistema = new JLabel("");
	private Label noteSistema;
	private List listaNot ;
	

	public FinestraMenu( UserInterface ui) {
		UI = ui;
		sn= SocialNetwork.getInstance();
		initialize();
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	private void initialize() {
		rimozioneVariPanel();
		frame = new JFrame("SocialNetwork M&M (menu)");
		frame.setBounds(600, 300, 680, 477);//+30
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.out.println("salvo tutto");
				sn.salvaTutto();
			}
		});
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 673, 23);
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		//nella relaise 2
		JButton btnAreapersonale = new JButton("AreaPersonale");
		btnAreapersonale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				costruisciFinestraAP();
			}
		});
		btnAreapersonale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAreapersonale.setBackground(Color.blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAreapersonale.setBackground(SystemColor.textHighlight);
			}
		});
		btnAreapersonale.setBackground(SystemColor.textHighlight);
		btnAreapersonale.setForeground(SystemColor.text);
		btnAreapersonale.setBounds(0, 0, 125, 23);
		panel.add(btnAreapersonale);

		JButton btnVistacategorie = new JButton("VistaCategorie");
		btnVistacategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				rimozioneVariPanel();
				frame.revalidate();
				frame.repaint();
				panelCategorie = new JPanel();
				panelCategorie.setBackground(new Color(224, 255, 255));
				panelCategorie.setBounds(0, 23, 673, 385);
				frame.getContentPane().add(panelCategorie);
				panelCategorie.setLayout(null);

				btnPartitedicalcio = new JButton("PartiteDiCalcio");
				btnPartitedicalcio.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						btnPartitedicalcio.setBackground(Color.green);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						btnPartitedicalcio.setBackground(SystemColor.desktop);
					}
				});
				btnPartitedicalcio.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						costruisciBachecaPDC();
					}
				});

				btnPartitedicalcio.setBackground(SystemColor.desktop);
				btnPartitedicalcio.setBounds(10, 59, 290, 23);
				panelCategorie.add(btnPartitedicalcio);		
				
				btnEscursione = new JButton("EscursioneInMontagna");
				btnEscursione.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						btnEscursione.setBackground(Color.green);
					}
					@Override
					public void mouseExited(MouseEvent e) {
						btnEscursione.setBackground(SystemColor.desktop);
					}
				});
				btnEscursione.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						costruisciBachecaEIM();
					}
				});

				btnEscursione.setBackground(SystemColor.desktop);
				btnEscursione.setBounds(10, 85, 290, 23);
				panelCategorie.add(btnEscursione);
				
				txtpnSelezionaLaCategoria = new JLabel();
				txtpnSelezionaLaCategoria.setOpaque(true);
				txtpnSelezionaLaCategoria.setText("Seleziona la categoria di ciu voi visualizzare gli eventi ->");
				txtpnSelezionaLaCategoria.setForeground(SystemColor.textText);
				txtpnSelezionaLaCategoria.setBackground(SystemColor.info);
				txtpnSelezionaLaCategoria.setBounds(10, 28, 342, 20);
				panelCategorie.add(txtpnSelezionaLaCategoria);
				//				frame.revalidate();
				//				frame.repaint();

			}
		});
		btnVistacategorie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnVistacategorie.setBackground(Color.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnVistacategorie.setBackground(SystemColor.textHighlight);
			}
		});
		btnVistacategorie.setForeground(SystemColor.text);
		btnVistacategorie.setBackground(SystemColor.textHighlight);
		btnVistacategorie.setBounds(124, 0, 125, 23);
		panel.add(btnVistacategorie);

		JButton btnLogout = new JButton("LogOut");
		btnLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnLogout.setBackground(Color.blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnLogout.setBackground(SystemColor.textHighlight);
			}
		});
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				UI.getFrame().setVisible(true);
				UI.logOut();
			}
		});
		btnLogout.setForeground(Color.WHITE);
		btnLogout.setBackground(SystemColor.textHighlight);
		btnLogout.setBounds(548, 0, 125, 23);
		panel.add(btnLogout);

		panelInfo = new JPanel();
		panelInfo.setBackground(new Color(224, 255, 255));
		panelInfo.setBounds(0, 23, 673, 385);
		frame.getContentPane().add(panelInfo);
		panelInfo.setLayout(null);

		JLabel txtpnBenvenutoNelSocial = new JLabel();
		txtpnBenvenutoNelSocial.setOpaque(false);
		txtpnBenvenutoNelSocial.setText("Ciao "+UI.getUS()+", benvenuto nel Social network M&M. Scegli una delle voci nella barra navigatrice  ");
		txtpnBenvenutoNelSocial.setBounds(10, 11, 653, 20);
		panelInfo.add(txtpnBenvenutoNelSocial);

		JLabel txtpnAreapersonale = new JLabel();
		txtpnAreapersonale.setBackground(SystemColor.info);
		txtpnAreapersonale.setText("qui potrai consultare le tue notifiche e visualizzare i tuoi dai personali");
		txtpnAreapersonale.setBounds(97, 42, 566, 20);
		panelInfo.add(txtpnAreapersonale);

		JLabel txtpnVistacategorieQuiPotrai = new JLabel();
		txtpnVistacategorieQuiPotrai.setBackground(SystemColor.info);
		txtpnVistacategorieQuiPotrai.setText("qui potrai visualizzare l'elenco delle categorie e sceglire quale tra queste visualizzarne gli eventi");
		txtpnVistacategorieQuiPotrai.setBounds(97, 73, 566, 20);
		panelInfo.add(txtpnVistacategorieQuiPotrai);

		JLabel lblNewLabel = new JLabel("AreaPersonale :");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setBounds(10, 42, 108, 20);
		panelInfo.add(lblNewLabel);

		JLabel lblVistacategorie = new JLabel("VistaCategorie :");
		lblVistacategorie.setForeground(Color.BLUE);
		lblVistacategorie.setBounds(10, 73, 108, 20);
		panelInfo.add(lblVistacategorie);
	}

	public void costruisciBachecaPDC(){
		

		rimozioneVariPanel();
		frame.setBounds(600, 300, 680, 477);//+30
		frame.setResizable(false);

		bachecaPDC = new JPanel();
		bachecaPDC.setBackground(new Color(224, 255, 255));
		bachecaPDC.setBounds(0, 23, 673, 385);
		bachecaPDC.setLayout(null);

		Panel pannelloSpiegazione = new Panel();
		pannelloSpiegazione.setBounds(0, 0, 663, 40);
		bachecaPDC.add(pannelloSpiegazione);
		pannelloSpiegazione.setLayout(null);

		JLabel txtpnQuestiSonoI = new JLabel();
		txtpnQuestiSonoI.setOpaque(true);
		txtpnQuestiSonoI.setForeground(SystemColor.BLACK);
		txtpnQuestiSonoI.setText("Questi sono i titoli degli eventi disponibili( per maggiori informazioni su un evento fai click su di esso)");
		txtpnQuestiSonoI.setBackground(SystemColor.info);
		txtpnQuestiSonoI.setBounds(10, 5, 643, 20);
		pannelloSpiegazione.add(txtpnQuestiSonoI);

		Panel pannelloTitoliE = new Panel();
		pannelloTitoliE.setBounds(0, 39, 476, 336);
		bachecaPDC.add(pannelloTitoliE);
		pannelloTitoliE.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		pdc = sn.mostraCategoria(Nomi.CAT_PARTITA_CALCIO.getNome());
		ArrayList<Evento> bacheca = pdc.getBacheca();
		int size = bacheca.size();

		for(k=0;k<size;k++)
		{  
			if(((Evento) pdc.getBacheca().get(k)).getStato().equals(StatoEvento.APERTO)||((Evento) pdc.getBacheca().get(k)).getStato().equals(StatoEvento.CHIUSO))
			{
				String titolo= ((Evento) pdc.getBacheca().get(k)).getTitolo().getValoreString();
				JButton btnNewButton = new JButton(titolo);
				btnNewButton.setBackground(SystemColor.desktop);
				costruisciActionListenerPartita(btnNewButton,k);
				pannelloTitoliE.add(btnNewButton);
			}
		}
		frame.getContentPane().add(bachecaPDC);

		Panel panel = new Panel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setForeground(new Color(51, 255, 255));
		panel.setBounds(476, 39, 187, 336);
		bachecaPDC.add(panel);
		panel.setLayout(null);

		Label label = new Label("Vuoi creare un nuovo evento?");
		label.setForeground(new Color(0, 0, 255));
		label.setBounds(0, 10, 187, 22);
		panel.add(label);

		Label label_1 = new Label("vai alla sezione di creazione");
		label_1.setForeground(new Color(0, 0, 255));
		label_1.setBounds(0, 33, 187, 22);
		panel.add(label_1);

		JButton btnCreaEvento = new JButton("CreaEvento");
		btnCreaEvento.setBounds(0, 61, 100, 23);
		btnCreaEvento.setBackground(SystemColor.desktop);
		panel.add(btnCreaEvento);

		btnCreaEvento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCreaEvento.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCreaEvento.setBackground(SystemColor.desktop);
			}
		});
		btnCreaEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (panelAP!= null)
					frame.getContentPane().remove(panelAP);
				if (panelInfo != null)
					frame.getContentPane().remove(panelInfo);
				if (bachecaPDC != null)
					frame.getContentPane().remove(bachecaPDC);
				if (panelCategorie != null)
					frame.getContentPane().remove(panelCategorie);
				if(finestraEV != null)
					frame.getContentPane().remove(finestraEV);
				if (finestraCEVEscu!= null)
					frame.getContentPane().remove(finestraCEVEscu);
				frame.revalidate();
				frame.repaint();
				costruisciFinestraCreazioneEVPartita();
			}
		});

		frame.revalidate();
		frame.repaint();

	}
	public void costruisciFinestraCreazioneEVPartita(){
		rimozioneVariPanel();
		finestraCEVPartita = new JPanel();
		finestraCEVPartita.setBackground(new Color(224, 255, 255));
		finestraCEVPartita.setBounds(0, 23, 673, 385);
		finestraCEVPartita.setLayout(null);
		frame.getContentPane().add(finestraCEVPartita);

		JTextField textField;
		JTextField textField_1;
		JTextField textField_2;
		JTextField txtGgmmaa_1;
		JTextField txtGgmmaa_2;
		JTextField txtnumeroDiGiorni;
		JTextField textField_6;
		JTextField textField_7;
		JTextField txtGgmmaa;
		JTextField textField_8;
		JTextField txtOraminuti;
		JTextField textfieldEta;
		JTextField textOraminutiFine;


		Label label = new Label("Sezione creazione evento(compila i campi e dai conferma per creare l'evento desiderato):");
		label.setForeground(new Color(0, 0, 255));
		label.setBounds(10, 10, 653, 22);
		finestraCEVPartita.add(label);

		Label label_1 = new Label("Titolo:");
		label_1.setBounds(10, 38, 62, 22);
		finestraCEVPartita.add(label_1);

		Label label_2 = new Label("PartecipantiNec:");
		label_2.setBounds(10, 66, 122, 22);
		finestraCEVPartita.add(label_2);

		Label label_3 = new Label("Luogo:");
		label_3.setBounds(10, 94, 62, 22);
		finestraCEVPartita.add(label_3);

		Label label_4 = new Label("DataInizio:");
		label_4.setBounds(10, 122, 62, 22);
		finestraCEVPartita.add(label_4);

		Label label_5 = new Label("TermineUltimoIsc:\r\n");
		label_5.setBounds(10, 262, 105, 22);
		finestraCEVPartita.add(label_5);

		Label label_6 = new Label("Durata:");
		label_6.setBounds(10, 178, 62, 22);
		finestraCEVPartita.add(label_6);

		Label label_7 = new Label("QuotaIndividuale:");
		label_7.setBounds(10, 206, 122, 22);
		finestraCEVPartita.add(label_7);

		Label label_8 = new Label("CompresoNellaQuota:");
		label_8.setBounds(10, 234, 122, 22);
		finestraCEVPartita.add(label_8);

		Label label_9 = new Label("DataFine:\r\n");
		label_9.setBounds(10, 150, 62, 22);
		finestraCEVPartita.add(label_9);

		Label label_10 = new Label("Note:");
		label_10.setBounds(10, 290, 62, 22);
		finestraCEVPartita.add(label_10);

		textField = new JTextField();
		textField.setBounds(142, 40, 159, 20);
		finestraCEVPartita.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(142, 66, 159, 20);
		finestraCEVPartita.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(142, 96, 159, 20);
		finestraCEVPartita.add(textField_2);

		txtGgmmaa_1 = new JTextField();
		txtGgmmaa_1.setText("GG/MM/AAAA");
		txtGgmmaa_1.setColumns(10);
		txtGgmmaa_1.setBounds(142, 122, 159, 20);
		finestraCEVPartita.add(txtGgmmaa_1);

		txtGgmmaa_2 = new JTextField();
		txtGgmmaa_2.setText("GG/MM/AAAA");
		txtGgmmaa_2.setColumns(10);
		txtGgmmaa_2.setBounds(142, 152, 159, 20);
		finestraCEVPartita.add(txtGgmmaa_2);

		txtnumeroDiGiorni = new JTextField();
		txtnumeroDiGiorni.setText("(NUMERO DI GIORNI)");
		txtnumeroDiGiorni.setColumns(10);
		txtnumeroDiGiorni.setBounds(142, 180, 159, 20);
		finestraCEVPartita.add(txtnumeroDiGiorni);

		textField_6 = new JTextField();
		textField_6.setText("(\u20AC)");
		textField_6.setColumns(10);
		textField_6.setBounds(142, 208, 159, 20);
		finestraCEVPartita.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(142, 236, 159, 20);
		finestraCEVPartita.add(textField_7);

		txtGgmmaa = new JTextField();
		txtGgmmaa.setText("GG/MM/AAAA");
		txtGgmmaa.setColumns(10);
		txtGgmmaa.setBounds(142, 262, 159, 20);
		finestraCEVPartita.add(txtGgmmaa);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(142, 290, 159, 20);
		finestraCEVPartita.add(textField_8);

		messaggioErr= new Label();
		messaggioErr.setForeground(Color.red);
		messaggioErr.setBounds(307, 336, 356, 22);
		finestraCEVPartita.add(messaggioErr);

		Label label_11 = new Label("OraInizio: ");
		label_11.setBounds(307, 150, 62, 22);
		finestraCEVPartita.add(label_11);

		Label label_12 = new Label("OraFine: ");
		label_12.setBounds(307, 178, 62, 22);
		finestraCEVPartita.add(label_12);

		txtOraminuti = new JTextField();
		txtOraminuti.setText("ORA:MINUTI");
		txtOraminuti.setColumns(10);
		txtOraminuti.setBounds(435, 152, 159, 20);
		finestraCEVPartita.add(txtOraminuti);

		textOraminutiFine = new JTextField();
		textOraminutiFine.setText("ORA:MINUTI");
		textOraminutiFine.setColumns(10);
		textOraminutiFine.setBounds(435, 178, 159, 20);
		finestraCEVPartita.add(textOraminutiFine);

		Label label_13 = new Label("Sesso:");
		label_13.setBounds(307, 94, 62, 22);
		finestraCEVPartita.add(label_13);

		textfieldEta = new JTextField();
		textfieldEta.setColumns(10);
		textfieldEta.setBounds(435, 122, 159, 20);
		finestraCEVPartita.add(textfieldEta);

		Label label_14 = new Label("Et\u00E0:");
		label_14.setBounds(307, 122, 62, 22);
		finestraCEVPartita.add(label_14);

		Choice choice = new Choice();
		choice.add("M");
		choice.add("F");
		choice.setBounds(435, 94, 159, 20);
		finestraCEVPartita.add(choice);

		Label label_15 = new Label("ToleranzaPartecipanti: ");
		label_15.setBounds(307, 66, 125, 22);
		finestraCEVPartita.add(label_15);

		JTextField textTolleranza = new JTextField();
		textTolleranza.setColumns(10);
		textTolleranza.setBounds(435, 66, 159, 20);
		finestraCEVPartita.add(textTolleranza);

		Label label_16 = new Label("TermineUltimoRitiro:");
		label_16.setBounds(307, 206, 122, 22);
		finestraCEVPartita.add(label_16);

		JTextField termineultRit = new JTextField();
		termineultRit.setColumns(10);
		termineultRit.setText("GG/MM/AAAA");
		termineultRit.setBounds(435, 206, 159, 20);
		finestraCEVPartita.add(termineultRit);


		frame.revalidate();
		frame.repaint();


		JButton btnNewButton = new JButton("CreaEvento");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setBackground(Color.green);
			}
			public void mouseExited(MouseEvent e){
				btnNewButton.setBackground(SystemColor.desktop);
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String titolo = textField.getText();
				String partNecessari = textField_1.getText();
				String luogo = textField_2.getText();
				String dataInizio = txtGgmmaa_1.getText();
				String dataFine = txtGgmmaa_2.getText();
				String durata = (txtnumeroDiGiorni.getText());
				String quota = (textField_6.getText());
				String compresoQuota = textField_7.getText();
				String termineUltimo = txtGgmmaa_2.getText();
				String note = textField_8.getText();
				String oraMinInizio=txtOraminuti.getText();
				String oraMinFine=textOraminutiFine.getText();
				String sesso= choice.getSelectedItem();
				String eta= textfieldEta.getText();
				String tolleranza= textTolleranza.getText();
				String termineUltRit= termineultRit.getText();
				eventoCreato = new PartitaCalcioEvento();

				messaggioErr.setText(settaCampi(titolo,partNecessari,luogo,dataInizio,dataFine,durata,quota,compresoQuota,termineUltimo,note,oraMinInizio,oraMinFine,sesso,eta,tolleranza,termineUltRit));
				if(messaggioErr.getText().equals(Evento.OK))
				{  
					System.out.println(messaggioErr.getText());
					//if(eventoCreato.valido())
					{
						costruisciPanelInvito(eventoCreato);

					}
				}
				else
				{
					System.out.println(messaggioErr.getText());
					costruisciFinestraCreazioneEVPartita();
				}

			}
		});
		btnNewButton.setBackground(SystemColor.desktop);
		btnNewButton.setBounds(142, 336, 159, 23);
		finestraCEVPartita.add(btnNewButton);


	}
	public String settaCampi(String titolo,String partNec, String luogo, String dataInizio,String dataFine,String durata,String quota,String compresoQuota,String termineUltimo,String note,String oraIni,String oraFine,String sesso,String eta,String tolleranza,String termineUltRit)
	{
		String messaggio;
		messaggio=eventoCreato.setTitolo(titolo);
		if(!messaggio.equals(Evento.OK))return messaggio+" titolo";
		messaggio=eventoCreato.setPartecipantiNecessari(partNec);
		if(!messaggio.equals(Evento.OK))return messaggio+" part necessari";
		messaggio=eventoCreato.setLuogo(luogo);
		if(!messaggio.equals(Evento.OK))return messaggio+" luogo";
		messaggio=eventoCreato.setDataInizio(dataInizio, oraIni);
		if(!messaggio.equals(Evento.OK))return messaggio+" data inizio";
		messaggio=eventoCreato.setDataFine(dataFine, oraFine);
		if(!messaggio.equals(Evento.OK))return messaggio+" data fine";
		messaggio=eventoCreato.setDurata(durata);
		if(!messaggio.equals(Evento.OK))return messaggio+ " durata";
		messaggio=eventoCreato.setQuotaIndividuale(quota);
		if(!messaggio.equals(Evento.OK))return messaggio+" quota";
		messaggio=eventoCreato.setCompresoQuota(compresoQuota);
		if(!messaggio.equals(Evento.OK))return messaggio+" compreso quota";
		messaggio=eventoCreato.setTermineUltimo(termineUltimo);
		if(!messaggio.equals(Evento.OK))return messaggio+" termine ultimo";
		messaggio=eventoCreato.setNote(note);
		if(!messaggio.equals(Evento.OK))return messaggio+" note";
		messaggio=((PartitaCalcioEvento) eventoCreato).setSesso(sesso);
		if(!messaggio.equals(Evento.OK))return messaggio+" sesso";
		messaggio=((PartitaCalcioEvento) eventoCreato).setEta(eta);
		if(!messaggio.equals(Evento.OK))return messaggio+" et�";
		messaggio=eventoCreato.setTolleranzaPartecipanti(tolleranza);
		if(!messaggio.equals(Evento.OK))return messaggio+" tolleranza";
		messaggio=eventoCreato.setTermineUltimoRitiro(termineUltRit);
		if(!messaggio.equals(Evento.OK))return messaggio+"termine ultimo ritiro";
		return Evento.OK;
	}
	public void costruisciFinestraEventoPartita(Evento ev){

		rimozioneVariPanel();
		frame.setBounds(600, 300, 680, 540);
		finestraEV = new JPanel();
		finestraEV.setBackground(new Color(224, 255, 255));
		finestraEV.setBounds(0, 23, 673, 478);
		finestraEV.setLayout(null);
		frame.getContentPane().add(finestraEV);
		frame.getContentPane().remove(bachecaPDC);

		JLabel txtpnNome = new JLabel();
		txtpnNome.setOpaque(true);
		txtpnNome.setBackground(SystemColor.info);
		txtpnNome.setText("Titolo: "+ ev.getTitolo().getValore());
		txtpnNome.setBounds(10, 11, 641, 22);
		finestraEV.add(txtpnNome);

		JLabel txtpnTermineUltimo = new JLabel();
		txtpnTermineUltimo.setOpaque(true);
		txtpnTermineUltimo.setBackground(SystemColor.info);
		txtpnTermineUltimo.setText("Termine Ultimo: "+ ev.getTermineUltimo().getValoreString());
		txtpnTermineUltimo.setBounds(10, 44, 641, 20);
		finestraEV.add(txtpnTermineUltimo);

		JLabel txtpnLuogo = new JLabel();
		txtpnLuogo.setOpaque(true);
		txtpnLuogo.setBackground(SystemColor.info);
		txtpnLuogo.setText("Luogo: "+ev.getLuogo().getValoreString());
		txtpnLuogo.setBounds(10, 75, 641, 20);
		finestraEV.add(txtpnLuogo);

		JLabel txtpnDatainizio = new JLabel();
		txtpnDatainizio.setOpaque(true);
		txtpnDatainizio.setBackground(SystemColor.info);
		txtpnDatainizio.setText("DataInizio: "+ev.getDataInizio().getValoreString());
		txtpnDatainizio.setBounds(10, 106, 641, 20);
		finestraEV.add(txtpnDatainizio);

		JLabel txtpnDatafine = new JLabel();
		txtpnDatafine.setOpaque(true);
		txtpnDatafine.setBackground(SystemColor.info);
		txtpnDatafine.setText("DataFine: "+ev.getDataFine().getValoreString());
		txtpnDatafine.setBounds(10, 137, 641, 20);
		finestraEV.add(txtpnDatafine);

		JLabel txtpnDurata = new JLabel();
		txtpnDurata .setOpaque(true);
		txtpnDurata.setBackground(SystemColor.info);
		txtpnDurata.setText("Durata(giorni): "+ev.getDurata().getValoreString());
		txtpnDurata.setBounds(10, 168, 641, 20);
		finestraEV.add(txtpnDurata);

		JLabel txtpnCompresonellaquota = new JLabel();
		txtpnCompresonellaquota .setOpaque(true);
		txtpnCompresonellaquota.setBackground(SystemColor.info);
		txtpnCompresonellaquota.setText("CompresoNellaQuota: "+ev.getCompresoQuota().getValoreString());
		txtpnCompresonellaquota.setBounds(10, 199, 641, 20);
		finestraEV.add(txtpnCompresonellaquota);

		JLabel txtpnNote = new JLabel();
		txtpnNote.setOpaque(true);
		txtpnNote.setBackground(SystemColor.info);
		txtpnNote.setText("Note: "+ev.getNote().getValoreString());
		txtpnNote.setBounds(10, 323, 641, 20);
		finestraEV.add(txtpnNote);

		JLabel txtQuota = new JLabel();
		txtQuota.setOpaque(true);
		txtQuota.setBackground(SystemColor.info);
		txtQuota.setText("Quota: "+ev.getQuotaIndividuale().getValoreString()+" �");
		txtQuota.setBounds(10, 230, 641, 20);
		finestraEV.add(txtQuota);

		JLabel txtEta = new JLabel();
		txtEta.setOpaque(true);
		txtEta.setBackground(SystemColor.info);
		txtEta.setText("Et�: "+((PartitaCalcioEvento)ev).getEta().getValoreString());
		txtEta.setBounds(10, 292, 641, 20);
		finestraEV.add(txtEta);

		JLabel txtSesso1 = new JLabel();
		txtSesso1.setOpaque(true);
		txtSesso1.setBackground(SystemColor.info);
		txtSesso1.setText("Sesso: "+((PartitaCalcioEvento)ev).getSesso().getValoreString());
		txtSesso1.setBounds(10,261, 641, 20);
		finestraEV.add(txtSesso1);

		Button button = new Button("Iscrizione");
		button.setBounds(10, 446, 76, 22);
		finestraEV.add(button);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(SystemColor.desktop);
			}
		});
		button.setBackground(SystemColor.desktop);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				noteSistema.setText(sn.iscrizione(ev));
			}
		});



		Button disiscrizione = new Button("disiscrzione");
		disiscrizione.setBounds(91, 446, 76, 22);
		finestraEV.add(disiscrizione);
		disiscrizione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				disiscrizione.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				disiscrizione.setBackground(SystemColor.desktop);
			}
		});
		disiscrizione.setBackground(SystemColor.desktop);
		disiscrizione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				noteSistema.setText(sn.revocaIscrizione(ev));
			}
		});


		noteSistema = new Label("\r\n");
		noteSistema.setText("");
		noteSistema.setBounds(169, 446, 405, 22);
		noteSistema.setForeground(Color.green);
		finestraEV.add(noteSistema);

		JLabel txtPartecnec = new JLabel();
		txtPartecnec.setText("ParetecipantiNecessari: "+ev.getPartecipantiNecessari().getValoreString());
		txtPartecnec.setOpaque(true);

		txtPartecnec.setBackground(SystemColor.info);
		txtPartecnec.setBounds(10, 354, 641, 20);
		finestraEV.add(txtPartecnec);

		JLabel statoEvento = new JLabel();
		statoEvento.setText("StatoEvento: "+ev.getStato());
		statoEvento.setOpaque(true);
		statoEvento.setBackground(SystemColor.info);
		statoEvento.setBounds(10, 385, 641, 20);
		finestraEV.add(statoEvento);

		JLabel proprietario = new JLabel();
		proprietario.setText("ProprietarioEvento: "+ev.getProprietario());
		proprietario.setOpaque(true);
		proprietario.setBackground(SystemColor.info);
		proprietario.setBounds(10, 416, 641, 20);
		finestraEV.add(proprietario);

		Button cancellazioneEv = new Button("EliminaEvento");
		cancellazioneEv.setBackground(SystemColor.desktop);
		cancellazioneEv.setBounds(575, 446, 76, 22);
		finestraEV.add(cancellazioneEv);

		cancellazioneEv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cancellazioneEv.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cancellazioneEv.setBackground(SystemColor.desktop);
			}
		});
		cancellazioneEv.setBackground(SystemColor.desktop);
		cancellazioneEv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				if(ev.getProprietario().equals(sn.getUtente().getUsername()))
				{
					sn.cancellaEvento(ev);
					costruisciBachecaPDC();
				}
				else
					noteSistema.setText("Non puoi cancellare un eveto se non sei il proprietario");

			}
		});





		//		frame.setResizable(false);
		//		frame.setBounds(600, 300, 667, 430);
		frame.revalidate();
		frame.repaint();
	}
	public void costruisciActionListenerPartita(JButton btn, int k){

		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBackground(SystemColor.desktop);
			}
		});
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				costruisciFinestraEventoPartita((Evento) pdc.getBacheca().get(k));
			}
		});

		frame.revalidate();
		frame.repaint();
	}
	public void costruisciFinestraAP(){
		rimozioneVariPanel();
		
		frame.revalidate();
		frame.repaint();
		frame.setBounds(600, 300, 680, 477);

		panelAP = new JPanel();
		panelAP.setBackground(new Color(176, 224, 230));
		panelAP.setBounds(0, 23, 673, 385);
		frame.getContentPane().add(panelAP);
		panelAP.setLayout(null);
		frame.getContentPane().add(panelAP);


		Button btnCancNot = new Button("CancellaNotifica");
		btnCancNot.setBounds(204, 101, 111, 22);
		btnCancNot.setBackground(Color.gray);
		panelAP.add(btnCancNot);
		btnCancNot.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnCancNot.setBackground(Color.red);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnCancNot.setBackground(Color.gray);
			}
		});
		btnCancNot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				for(int count=0;count<sn.getUtente().getNotifiche().size();count++)
				{	
					String titolo=sn.getUtente().getNotifiche().get(count).getMessaggio()+" TITOLO: "+sn.getUtente().getNotifiche().get(count).getEvento().getTitolo().getValore();
					if(titolo.equals(listaNot.getSelectedItem())){
						sn.cancellaNotifica(sn.getUtente().getNotifiche().get(count));
						System.out.println(sn.getUtente().getNotifiche().size());
						costruisciFinestraAP();
					}
				}

			}
		});

		listaNot = new List();
		listaNot.setForeground(new Color(0, 0, 0));
		listaNot.setBackground(new Color(0, 204, 204));
		listaNot.setBounds(204, 38, 459, 60);
		listaNot.setMultipleMode(false);
		for(int i =0; i<sn.getUtente().getNotifiche().size();i++)
		{		
			String notifica=sn.getUtente().getNotifiche().get(i).getMessaggio()+" TITOLO: "+sn.getUtente().getNotifiche().get(i).getEvento().getTitolo().getValoreString();
			listaNot.add(notifica);	
		}

		listaNot.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {

				for(int count=0;count<sn.getUtente().getNotifiche().size();count++)
				{

					String titolo=sn.getUtente().getNotifiche().get(count).getMessaggio()+" TITOLO: "+sn.getUtente().getNotifiche().get(count).getEvento().getTitolo().getValore();



					if(titolo.equals(listaNot.getSelectedItem())){

						textArea.setText("Titilo: "+sn.getUtente().getNotifiche().get(count).getEvento().getTitolo().getValoreString()+ "\r\n"+
								"PartecipantiNecessari: "+sn.getUtente().getNotifiche().get(count).getEvento().getPartecipantiNecessari().getValoreString()+ "\r\n"+
								"TolleranzaPartecipanti: "+sn.getUtente().getNotifiche().get(count).getEvento().getTolleranzaPartecipanti().getValoreString()+ "\r\n"+
								"TetmineUltimoIscrizione: "+sn.getUtente().getNotifiche().get(count).getEvento().getTermineUltimo().getValoreString()+ "\r\n"+
								"TetmineUltimoRitiro: "+sn.getUtente().getNotifiche().get(count).getEvento().getTermineUltimoRitiro().getValoreString()+ "\r\n"+
								"Luogo: "+sn.getUtente().getNotifiche().get(count).getEvento().getLuogo().getValoreString()+ "\r\n"+
								"DataInizio: "+sn.getUtente().getNotifiche().get(count).getEvento().getDataInizio().getValoreString()+ "\r\n"+
								"DataFine: "+sn.getUtente().getNotifiche().get(count).getEvento().getDataFine().getValoreString()+ "\r\n"+
								"Durata: "+sn.getUtente().getNotifiche().get(count).getEvento().getDurata().getValoreString()+ "\r\n"+
								"QuotaIndividuale: "+sn.getUtente().getNotifiche().get(count).getEvento().getQuotaIndividuale().getValoreString()+" � "+ "\r\n"+
								"CompresoQuota: "+sn.getUtente().getNotifiche().get(count).getEvento().getCompresoQuota().getValoreString()+ "\r\n"+
								"Note: "+sn.getUtente().getNotifiche().get(count).getEvento().getNote().getValoreString()+ "\r\n"		
								);
						frame.revalidate();
						frame.repaint();


					}
				}
			}
		});

		panelAP.add(listaNot);

		Panel panel = new Panel();
		panel.setBackground(new Color(135, 206, 235));
		panel.setForeground(SystemColor.info);
		panel.setBounds(0, 0, 198, 385);
		panelAP.add(panel);
		panel.setLayout(null);

		Label label = new Label("Dati personali:");
		label.setForeground(new Color(0, 0, 0));
		label.setBounds(0, 10, 198, 22);
		panel.add(label);

		Label label_1 = new Label("username: " + sn.getUtente().getUsername());
		label_1.setBounds(0, 38, 198, 22);
		panel.add(label_1);

		Label label_3 = new Label("password: " + new String(sn.getUtente().getPassword()));
		label_3.setBounds(0, 66, 198, 22);
		panel.add(label_3);

		JButton btnModificadati = new JButton("ModificaDati");
		btnModificadati.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnModificadati.setBackground(new Color(0, 206, 209));

			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnModificadati.setBackground(new Color(127, 255, 212));
			}
		});
		btnModificadati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				confermaSistema.setText("");
				costruisciPanelModificaDati();
			}
		});

		btnModificadati.setBackground(new Color(127, 255, 212));
		btnModificadati.setBounds(10, 351, 178, 23);
		panel.add(btnModificadati);

		Label label_4 = new Label("FasciaDiEta: "+sn.getUtente().getEtaMin()+"-"+sn.getUtente().getEtaMax());
		label_4.setBounds(0, 94, 198, 22);
		panel.add(label_4);

		Label label_5 = new Label("CategorieDiInteresse:");
		label_5.setBounds(0, 122, 198, 22);
		panel.add(label_5);

		List list_1 = new List();
		list_1.setForeground(SystemColor.text);
		list_1.setBackground(new Color(176, 224, 230));
		list_1.setBounds(10, 150, 178, 22);
		for(int i=0;i<sn.getUtente().getInteressi().size();i++)
			list_1.add(sn.getUtente().getInteressi().get(i));
		list_1.setMultipleSelections(false);
		panel.add(list_1);


		Label label_2 = new Label("Seleziona una delle notifiche per visualizzarne il contenuto -->");
		label_2.setBackground(new Color(0, 139, 139));
		label_2.setBounds(204, 10, 459, 22);
		panelAP.add(label_2);

		textArea = new TextArea();
		textArea.setBackground(new Color(0, 204, 204));
		textArea.setForeground(new Color(224, 255, 255));
		textArea.setBounds(204, 129, 459, 256);
		panelAP.add(textArea);

		frame.revalidate();
		frame.repaint();
	}
	public void costruisciPanelModificaDati(){

		rimozioneVariPanel();

		frame.revalidate();
		frame.repaint();

		frame.setBounds(600, 300, 680, 540);
		panelModificaDati = new JPanel();
		panelModificaDati.setBackground(new Color(224, 255, 255));
		panelModificaDati.setBounds(0, 23, 673, 540);
		panelModificaDati.setLayout(null);
		frame.getContentPane().add(panelModificaDati);

		Label label = new Label("(Nota: solo i campi FasciaDiet\u00E0 e CategorieDiInteresse possono essere modificati)");
		label.setBackground(SystemColor.desktop);
		label.setBounds(10, 10, 642, 25);
		panelModificaDati.add(label);
		
		confermaSistema.setBounds(145, 363, 507, 22);
		panelModificaDati.add(confermaSistema);

		JLabel lblFasciadiet = new JLabel("FasciaDiEt\u00E0: ");
		lblFasciadiet.setBounds(10, 79, 74, 14);
		panelModificaDati.add(lblFasciadiet);

		JLabel lblMin = new JLabel("min:");
		lblMin.setBounds(113, 79, 38, 14);
		panelModificaDati.add(lblMin);

		JTextField textField_9 = new JTextField();
		textField_9.setBounds(161, 76, 38, 20);
		panelModificaDati.add(textField_9);
		textField_9.setColumns(10);

		JLabel lblMax = new JLabel("max:");
		lblMax.setBounds(209, 79, 38, 14);
		panelModificaDati.add(lblMax);

		JTextField textField_10 = new JTextField();
		textField_10.setColumns(10);
		textField_10.setBounds(257, 76, 38, 20);
		panelModificaDati.add(textField_10);

		JLabel lblCategoriediinteresse = new JLabel("CategorieDiInteresse: ");
		lblCategoriediinteresse.setBounds(10, 197, 129, 14);
		panelModificaDati.add(lblCategoriediinteresse);
		frame.getContentPane().remove(panelAP);
		
		Label label_5 = new Label("Info: seleziona nella lista le tue categorie di interesse,le precedenti verranno sovrascritte.");
		label_5.setBackground(SystemColor.info);
		label_5.setBounds(145, 225, 507, 22);
		panelModificaDati.add(label_5);



		List list_1 = new List();
		list_1.setMultipleMode(true);
		list_1.setForeground(SystemColor.text);
		list_1.setBackground(new Color(176, 224, 230));
		list_1.setBounds(145, 197, 178, 22);
		for(int i=0;i<sn.titoliCategorie().size();i++)
			list_1.add(sn.titoliCategorie().get(i));
		panelModificaDati.add(list_1);

		Button button = new Button("ConfermaModifica");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String min= textField_9.getText();
				String max= textField_10.getText();
				int[] selezionatiInt=list_1.getSelectedIndexes();
				for(int k=0;k<sn.getUtente().getInteressi().size();k++)
					sn.modificaUtente(Utente.RIMUOVI_INTERESSE, sn.getUtente().getInteressi().get(k));
				for(int k=0;k<selezionatiInt.length;k++) 
					sn.modificaUtente(Utente.AGGIUNGI_INTERESSE, sn.titoliCategorie().get(k));

				String confermaEtaMin =sn.modificaUtente(Utente.ETA_MIN, min);
				String confermaEtaMax =sn.modificaUtente(Utente.ETA_MAX, max);
				if(!confermaEtaMin.equals(Utente.MODIFICA_RIUSCITA)) confermaSistema.setText(confermaEtaMin);
				if(!confermaEtaMax.equals(Utente.MODIFICA_RIUSCITA)) confermaSistema.setText(confermaEtaMax);
				else confermaSistema.setText(Utente.MODIFICA_RIUSCITA);
				if(confermaSistema.getText().equals(Utente.MODIFICA_RIUSCITA))
				{
					System.out.println("modifica ok");
					costruisciFinestraAP();
				}
				else 
				{
					costruisciPanelModificaDati();
				}

			}
		});
		button.setBackground(SystemColor.desktop);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				button.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(SystemColor.desktop);
			}
		});
		button.setBounds(10, 363, 129, 22);
		panelModificaDati.add(button);

		frame.revalidate();
		frame.repaint();
	}
	public void costruisciPanelInvito(Evento eventoCreato){

		rimozioneVariPanel();
		frame.revalidate();
		frame.repaint();

		frame.setBounds(600, 300, 680, 415);
		panelInvito = new JPanel();
		panelInvito.setBackground(new Color(224, 255, 255));
		panelInvito.setBounds(0, 23, 673, 307);
		panelInvito.setLayout(null);

		frame.getContentPane().add(panelInvito);

		Label label = new Label("Seleziona le persone a cui invire un invito per il tuo evento-->");
		label.setBackground(SystemColor.activeCaption);
		label.setBounds(10, 10, 653, 22);
		panelInvito.add(label);

		List list = new List();
		list.setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		list.setBounds(10, 53, 221, 77);
		list.setMultipleMode(true);
		
		if(eventoCreato.getClass()==PartitaCalcioEvento.class)
		{
			
			for(int i =0;i<sn.getPersoneInvitabili(Nomi.CAT_PARTITA_CALCIO.getNome()).size();i++)
				list.addItem(sn.getPersoneInvitabili(Nomi.CAT_PARTITA_CALCIO.getNome()).get(i));
		}
		
		if(eventoCreato.getClass()==EscursioneMontagnaEvento.class)
		{
			
			for(int i =0;i<sn.getPersoneInvitabili(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()).size();i++)
				list.addItem(sn.getPersoneInvitabili(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()).get(i));
		}
		
		panelInvito.add(list);

		JButton btnConferma = new JButton("Conferma");
		btnConferma.setBackground(SystemColor.desktop);
		btnConferma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnConferma.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnConferma.setBackground(SystemColor.desktop);
			}
		});
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				
				int[] invitatiInt=list.getSelectedIndexes();
				LinkedList<String> invitati= new LinkedList<String>();
				
				if(eventoCreato.getClass()==PartitaCalcioEvento.class)
				{
				for(int c=0;c<invitatiInt.length;c++)
					invitati.add(sn.getPersoneInvitabili(Nomi.CAT_PARTITA_CALCIO.getNome()).get(invitatiInt[c]));
				}
				else if(eventoCreato.getClass()==EscursioneMontagnaEvento.class)
				{
					for(int c=0;c<invitatiInt.length;c++)
						invitati.add(sn.getPersoneInvitabili(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome()).get(invitatiInt[c]));

				}
				
				sn.addEvento(eventoCreato,invitati);
				System.out.println("sono qua");
				costruisciFinestraAP();
			}
		});
		btnConferma.setBounds(10, 185, 100, 23);
		panelInvito.add(btnConferma);

	}
	
	public void costruisciBachecaEIM(){
		
		rimozioneVariPanel();


		frame.setBounds(600, 300, 680, 477);//+30
		frame.setResizable(false);

		bachecaEIM = new JPanel();
		bachecaEIM.setBackground(new Color(224, 255, 255));
		bachecaEIM.setBounds(0, 23, 673, 385);
		bachecaEIM.setLayout(null);

		Panel pannelloSpiegazione = new Panel();
		pannelloSpiegazione.setBounds(0, 0, 663, 40);
		bachecaEIM.add(pannelloSpiegazione);
		pannelloSpiegazione.setLayout(null);

		JLabel txtpnQuestiSonoI = new JLabel();
		txtpnQuestiSonoI.setOpaque(true);
		txtpnQuestiSonoI.setForeground(SystemColor.BLACK);
		txtpnQuestiSonoI.setText("Questi sono i titoli degli eventi disponibili( per maggiori informazioni su un evento fai click su di esso)");
		txtpnQuestiSonoI.setBackground(SystemColor.info);
		txtpnQuestiSonoI.setBounds(10, 5, 643, 20);
		pannelloSpiegazione.add(txtpnQuestiSonoI);

		Panel pannelloTitoliE = new Panel();
		pannelloTitoliE.setBounds(0, 39, 476, 336);
		bachecaEIM.add(pannelloTitoliE);
		pannelloTitoliE.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		eim = sn.mostraCategoria(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		ArrayList<Evento> bacheca = eim.getBacheca();
		int size = bacheca.size();

		for(k=0;k<size;k++)
		{  
			if(((Evento) eim.getBacheca().get(k)).getStato().equals(StatoEvento.APERTO)||((Evento) eim.getBacheca().get(k)).getStato().equals(StatoEvento.CHIUSO))
			{
				String titolo= ((Evento) eim.getBacheca().get(k)).getTitolo().getValoreString();
				JButton btnNewButton = new JButton(titolo);
				btnNewButton.setBackground(SystemColor.desktop);
				costruisciActionListenerEscu(btnNewButton,k);
				pannelloTitoliE.add(btnNewButton);
			}
		}
		frame.getContentPane().add(bachecaEIM);

		Panel panel = new Panel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setForeground(new Color(51, 255, 255));
		panel.setBounds(476, 39, 187, 336);
		bachecaEIM.add(panel);
		panel.setLayout(null);

		Label label = new Label("Vuoi creare un nuovo evento?");
		label.setForeground(new Color(0, 0, 255));
		label.setBounds(0, 10, 187, 22);
		panel.add(label);

		Label label_1 = new Label("vai alla sezione di creazione");
		label_1.setForeground(new Color(0, 0, 255));
		label_1.setBounds(0, 33, 187, 22);
		panel.add(label_1);

		JButton btnCreaEvento = new JButton("CreaEvento");
		btnCreaEvento.setBounds(0, 61, 100, 23);
		btnCreaEvento.setBackground(SystemColor.desktop);
		panel.add(btnCreaEvento);

		btnCreaEvento.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCreaEvento.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCreaEvento.setBackground(SystemColor.desktop);
			}
		});
		btnCreaEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rimozioneVariPanel();
	
				frame.revalidate();
				frame.repaint();
				costruisciFinestraCreazioneEVEscursione();
			}
		});

		frame.revalidate();
		frame.repaint();
	}
	
	
	public void costruisciFinestraCreazioneEVEscursione(){
		
		finestraCEVEscu = new JPanel();
		finestraCEVEscu.setBackground(new Color(224, 255, 255));
		finestraCEVEscu.setBounds(0, 23, 673, 458);
		finestraCEVEscu.setLayout(null);
		frame.getContentPane().add(finestraCEVEscu);

		JTextField textField;
		JTextField textField_1;
		JTextField textField_2;
		JTextField txtGgmmaa_1;
		JTextField txtGgmmaa_2;
		JTextField txtnumeroDiGiorni;
		JTextField textField_6;
		JTextField textField_7;
		JTextField txtGgmmaa;
		JTextField textField_8;
		JTextField txtOraminuti;
		JTextField textField_11;
		JTextField textField_12;
		JTextField textOraminutiFine;


		Label label = new Label("Sezione creazione evento(compila i campi e dai conferma per creare l'evento desiderato):");
		label.setForeground(new Color(0, 0, 255));
		label.setBounds(10, 10, 653, 22);
		finestraCEVEscu.add(label);

		Label label_1 = new Label("Titolo:");
		label_1.setBounds(10, 38, 62, 22);
		finestraCEVEscu.add(label_1);

		Label label_2 = new Label("PartecipantiNec:");
		label_2.setBounds(10, 66, 122, 22);
		finestraCEVEscu.add(label_2);

		Label label_3 = new Label("Luogo:");
		label_3.setBounds(10, 94, 62, 22);
		finestraCEVEscu.add(label_3);

		Label label_4 = new Label("DataInizio:");
		label_4.setBounds(10, 122, 62, 22);
		finestraCEVEscu.add(label_4);

		Label label_5 = new Label("TermineUltimoIsc:\r\n");
		label_5.setBounds(10, 262, 105, 22);
		finestraCEVEscu.add(label_5);

		Label label_6 = new Label("Durata:");
		label_6.setBounds(10, 178, 62, 22);
		finestraCEVEscu.add(label_6);

		Label label_7 = new Label("QuotaIndividuale:");
		label_7.setBounds(10, 206, 122, 22);
		finestraCEVEscu.add(label_7);

		Label label_8 = new Label("CompresoNellaQuota:");
		label_8.setBounds(10, 234, 122, 22);
		finestraCEVEscu.add(label_8);

		Label label_9 = new Label("DataFine:\r\n");
		label_9.setBounds(10, 150, 62, 22);
		finestraCEVEscu.add(label_9);

		Label label_10 = new Label("Note:");
		label_10.setBounds(10, 290, 62, 22);
		finestraCEVEscu.add(label_10);

		textField = new JTextField();
		textField.setBounds(142, 40, 159, 20);
		finestraCEVEscu.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(142, 66, 159, 20);
		finestraCEVEscu.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(142, 96, 159, 20);
		finestraCEVEscu.add(textField_2);

		txtGgmmaa_1 = new JTextField();
		txtGgmmaa_1.setText("GG/MM/AAAA");
		txtGgmmaa_1.setColumns(10);
		txtGgmmaa_1.setBounds(142, 122, 159, 20);
		finestraCEVEscu.add(txtGgmmaa_1);

		txtGgmmaa_2 = new JTextField();
		txtGgmmaa_2.setText("GG/MM/AAAA");
		txtGgmmaa_2.setColumns(10);
		txtGgmmaa_2.setBounds(142, 152, 159, 20);
		finestraCEVEscu.add(txtGgmmaa_2);

		txtnumeroDiGiorni = new JTextField();
		txtnumeroDiGiorni.setText("(NUMERO DI GIORNI)");
		txtnumeroDiGiorni.setColumns(10);
		txtnumeroDiGiorni.setBounds(142, 180, 159, 20);
		finestraCEVEscu.add(txtnumeroDiGiorni);

		textField_6 = new JTextField();
		textField_6.setText("(\u20AC)");
		textField_6.setColumns(10);
		textField_6.setBounds(142, 208, 159, 20);
		finestraCEVEscu.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(142, 236, 159, 20);
		finestraCEVEscu.add(textField_7);

		txtGgmmaa = new JTextField();
		txtGgmmaa.setText("GG/MM/AAAA");
		txtGgmmaa.setColumns(10);
		txtGgmmaa.setBounds(142, 262, 159, 20);
		finestraCEVEscu.add(txtGgmmaa);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(142, 290, 159, 20);
		finestraCEVEscu.add(textField_8);

		messaggioErr= new Label();
		messaggioErr.setForeground(Color.red);
		messaggioErr.setBounds(307, 336, 356, 22);
		finestraCEVEscu.add(messaggioErr);

		Label label_11 = new Label("OraInizio: ");
		label_11.setBounds(307, 150, 62, 22);
		finestraCEVEscu.add(label_11);

		Label label_12 = new Label("OraFine: ");
		label_12.setBounds(307, 178, 62, 22);
		finestraCEVEscu.add(label_12);

		txtOraminuti = new JTextField();
		txtOraminuti.setText("ORA:MINUTI");
		txtOraminuti.setColumns(10);
		txtOraminuti.setBounds(435, 152, 159, 20);
		finestraCEVEscu.add(txtOraminuti);

		textOraminutiFine = new JTextField();
		textOraminutiFine.setText("ORA:MINUTI");
		textOraminutiFine.setColumns(10);
		textOraminutiFine.setBounds(435, 178, 159, 20);
		finestraCEVEscu.add(textOraminutiFine);

		Label label_15 = new Label("ToleranzaPartecipanti: ");
		label_15.setBounds(307, 66, 125, 22);
		finestraCEVEscu.add(label_15);

		JTextField textTolleranza = new JTextField();
		textTolleranza.setColumns(10);
		textTolleranza.setBounds(435, 66, 159, 20);
		finestraCEVEscu.add(textTolleranza);

		Label label_16 = new Label("TermineUltimoRitiro:");
		label_16.setBounds(307, 206, 122, 22);
		finestraCEVEscu.add(label_16);

		JTextField termineultRit = new JTextField();
		termineultRit.setColumns(10);
		termineultRit.setText("GG/MM/AAAA");
		termineultRit.setBounds(435, 206, 159, 20);
		finestraCEVEscu.add(termineultRit);
		
		textField_11 = new JTextField();
		textField_11.setText("(\u20AC)");
		textField_11.setColumns(10);
		textField_11.setBounds(435, 234, 159, 20);
		finestraCEVEscu.add(textField_11);
		
		textField_12 = new JTextField();
		textField_12.setText("(\u20AC)");
		textField_12.setColumns(10);
		textField_12.setBounds(435, 262, 159, 20);
		finestraCEVEscu.add(textField_12);



		JButton btnNewButton = new JButton("CreaEvento");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnNewButton.setBackground(Color.green);
			}
			public void mouseExited(MouseEvent e){
				btnNewButton.setBackground(SystemColor.desktop);
			}
		});

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String titolo = textField.getText();
				String partNecessari = textField_1.getText();
				String luogo = textField_2.getText();
				String dataInizio = txtGgmmaa_1.getText();
				String dataFine = txtGgmmaa_2.getText();
				String durata = (txtnumeroDiGiorni.getText());
				String quota = (textField_6.getText());
				String compresoQuota = textField_7.getText();
				String termineUltimo = txtGgmmaa_2.getText();
				String note = textField_8.getText();
				String oraMinInizio=txtOraminuti.getText();
				String oraMinFine=textOraminutiFine.getText();
				String tolleranza= textTolleranza.getText();
				String termineUltRit= termineultRit.getText();
				String quotaIstruttore= textField_11.getText();
				String quotaAttrezzatura= textField_12.getText();
				eventoCreato = new EscursioneMontagnaEvento();

				messaggioErr.setText(settaCampiEscu(titolo,partNecessari,luogo,dataInizio,dataFine,durata,quota,compresoQuota,termineUltimo,note,oraMinInizio,oraMinFine,tolleranza,termineUltRit,quotaIstruttore,quotaAttrezzatura));
				if(messaggioErr.getText().equals(Evento.OK))
				{  
					System.out.println(messaggioErr.getText());
					//if(eventoCreato.valido())
					{
						costruisciPanelInvito(eventoCreato);

					}
				}
				else
				{
					System.out.println(messaggioErr.getText());
					costruisciFinestraCreazioneEVEscursione();
				}

			}
		});
		btnNewButton.setBackground(SystemColor.desktop);
		btnNewButton.setBounds(142, 336, 159, 23);
		finestraCEVEscu.add(btnNewButton);
		
		Label label_17 = new Label("QuotaIstruttorePersonale:");
		label_17.setBounds(307, 234, 122, 22);
		finestraCEVEscu.add(label_17);
		
		Label label_18 = new Label("QuotaAttrezzatura:");
		label_18.setBounds(307, 262, 122, 22);
		finestraCEVEscu.add(label_18);
		
		


		
	}
	public String settaCampiEscu(String titolo,String partNec, String luogo, String dataInizio,String dataFine,String durata,String quota,String compresoQuota,String termineUltimo,String note,String oraIni,String oraFine,String tolleranza,String termineUltRit,String quotaIst,String quotaAtt)
	{
			
		String messaggio;
		messaggio=((EscursioneMontagnaEvento)eventoCreato).setTitolo(titolo);
		if(!messaggio.equals(Evento.OK))return messaggio+" titolo";
		messaggio=eventoCreato.setPartecipantiNecessari(partNec);
		if(!messaggio.equals(Evento.OK))return messaggio+" part necessari";
		messaggio=eventoCreato.setLuogo(luogo);
		if(!messaggio.equals(Evento.OK))return messaggio+" luogo";
		messaggio=eventoCreato.setDataInizio(dataInizio, oraIni);
		if(!messaggio.equals(Evento.OK))return messaggio+" data inizio";
		messaggio=eventoCreato.setDataFine(dataFine, oraFine);
		if(!messaggio.equals(Evento.OK))return messaggio+" data fine";
		messaggio=eventoCreato.setDurata(durata);
		if(!messaggio.equals(Evento.OK))return messaggio+ " durata";
		messaggio=eventoCreato.setQuotaIndividuale(quota);
		if(!messaggio.equals(Evento.OK))return messaggio+" quota";
		messaggio=eventoCreato.setCompresoQuota(compresoQuota);
		if(!messaggio.equals(Evento.OK))return messaggio+" compreso quota";
		messaggio=eventoCreato.setTermineUltimo(termineUltimo);
		if(!messaggio.equals(Evento.OK))return messaggio+" termine ultimo";
		messaggio=eventoCreato.setNote(note);
		if(!messaggio.equals(Evento.OK))return messaggio+" note";
		messaggio=eventoCreato.setTolleranzaPartecipanti(tolleranza);
		if(!messaggio.equals(Evento.OK))return messaggio+" tolleranza";
		messaggio=eventoCreato.setTermineUltimoRitiro(termineUltRit);
		if(!messaggio.equals(Evento.OK))return messaggio+"termine ultimo ritiro";
		messaggio=((EscursioneMontagnaEvento)eventoCreato).setIstruttore(quotaIst);
		if(!messaggio.equals(Evento.OK))return messaggio+"quota istruttore";
		messaggio=((EscursioneMontagnaEvento)eventoCreato).setAttrezzatura(quotaAtt);
		if(!messaggio.equals(Evento.OK))return messaggio+"quota attrezzatura";
		
		return Evento.OK;
		
		
	}
	public void costruisciActionListenerEscu(JButton btn, int k){

		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBackground(SystemColor.desktop);
			}
		});
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				costruisciFinestraEventoEscu((Evento) eim.getBacheca().get(k));
			}
		});

		frame.revalidate();
		frame.repaint();
	
	
	}
	public void costruisciFinestraEventoEscu(Evento ev){
		
		
		frame.setBounds(600, 300, 680, 540);
		finestraEV = new JPanel();
		finestraEV.setBackground(new Color(224, 255, 255));
		finestraEV.setBounds(0, 23, 673, 478);
		finestraEV.setLayout(null);
		
		frame.getContentPane().remove(bachecaEIM);
		frame.getContentPane().add(finestraEV);

		JLabel txtpnNome = new JLabel();
		txtpnNome.setOpaque(true);
		txtpnNome.setBackground(SystemColor.info);
		txtpnNome.setText("Titolo: "+ ev.getTitolo().getValore());
		txtpnNome.setBounds(10, 11, 641, 22);
		finestraEV.add(txtpnNome);

		JLabel txtpnTermineUltimo = new JLabel();
		txtpnTermineUltimo.setOpaque(true);
		txtpnTermineUltimo.setBackground(SystemColor.info);
		txtpnTermineUltimo.setText("Termine Ultimo: "+ ev.getTermineUltimo().getValoreString());
		txtpnTermineUltimo.setBounds(10, 44, 641, 20);
		finestraEV.add(txtpnTermineUltimo);

		JLabel txtpnLuogo = new JLabel();
		txtpnLuogo.setOpaque(true);
		txtpnLuogo.setBackground(SystemColor.info);
		txtpnLuogo.setText("Luogo: "+ev.getLuogo().getValoreString());
		txtpnLuogo.setBounds(10, 75, 641, 20);
		finestraEV.add(txtpnLuogo);

		JLabel txtpnDatainizio = new JLabel();
		txtpnDatainizio.setOpaque(true);
		txtpnDatainizio.setBackground(SystemColor.info);
		txtpnDatainizio.setText("DataInizio: "+ev.getDataInizio().getValoreString());
		txtpnDatainizio.setBounds(10, 106, 641, 20);
		finestraEV.add(txtpnDatainizio);

		JLabel txtpnDatafine = new JLabel();
		txtpnDatafine.setOpaque(true);
		txtpnDatafine.setBackground(SystemColor.info);
		txtpnDatafine.setText("DataFine: "+ev.getDataFine().getValoreString());
		txtpnDatafine.setBounds(10, 137, 641, 20);
		finestraEV.add(txtpnDatafine);

		JLabel txtpnDurata = new JLabel();
		txtpnDurata .setOpaque(true);
		txtpnDurata.setBackground(SystemColor.info);
		txtpnDurata.setText("Durata(giorni): "+ev.getDurata().getValoreString());
		txtpnDurata.setBounds(10, 168, 641, 20);
		finestraEV.add(txtpnDurata);

		JLabel txtpnCompresonellaquota = new JLabel();
		txtpnCompresonellaquota .setOpaque(true);
		txtpnCompresonellaquota.setBackground(SystemColor.info);
		txtpnCompresonellaquota.setText("CompresoNellaQuota: "+ev.getCompresoQuota().getValoreString());
		txtpnCompresonellaquota.setBounds(10, 199, 641, 20);
		finestraEV.add(txtpnCompresonellaquota);

		JLabel txtpnNote = new JLabel();
		txtpnNote.setOpaque(true);
		txtpnNote.setBackground(SystemColor.info);
		txtpnNote.setText("Note: "+ev.getNote().getValoreString());
		txtpnNote.setBounds(10, 323, 641, 20);
		finestraEV.add(txtpnNote);

		JLabel txtQuota = new JLabel();
		txtQuota.setOpaque(true);
		txtQuota.setBackground(SystemColor.info);
		txtQuota.setText("Quota: "+ev.getQuotaIndividuale().getValoreString()+" �");
		txtQuota.setBounds(10, 230, 641, 20);
		finestraEV.add(txtQuota);

		JLabel txtEta = new JLabel();
		txtEta.setOpaque(true);
		txtEta.setBackground(SystemColor.info);
		txtEta.setText("QuotaIstruttore: "+((EscursioneMontagnaEvento)ev).getIstruttore().getValoreString());
		txtEta.setBounds(10,261, 530, 20);
		finestraEV.add(txtEta);

		JLabel txtSesso1 = new JLabel();
		txtSesso1.setOpaque(true);
		txtSesso1.setBackground(SystemColor.info);
		txtSesso1.setText("QuotaAttrezzatura: "+((EscursioneMontagnaEvento)ev).getAttrezzatura().getValoreString());
		txtSesso1.setBounds(10,292, 530, 20);
		finestraEV.add(txtSesso1);
		
		Choice choice = new Choice();
		choice.setBounds(546, 261, 105, 20);
		choice.add("si");
		choice.add("no");
		boolean istruttore;
		if(choice.getSelectedItem().equals("si"))istruttore=true;
		else istruttore=false;
		finestraEV.add(choice);
		
		Choice choice_1 = new Choice();
		choice_1.setBounds(546, 292, 105, 20);
		choice_1.add("si");
		choice_1.add("no");
		boolean attrezzatura;
		if(choice_1.getSelectedItem().equals("si"))attrezzatura=true;
		else attrezzatura=false;
		finestraEV.add(choice_1);
		

		Button button = new Button("Iscrizione");
		button.setBounds(10, 446, 76, 22);
		finestraEV.add(button);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				button.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				button.setBackground(SystemColor.desktop);
			}
		});
		button.setBackground(SystemColor.desktop);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				noteSistema.setText(sn.iscrizione(((EscursioneMontagnaEvento)ev), istruttore, attrezzatura));
			}
		});



		Button disiscrizione = new Button("disiscrzione");
		disiscrizione.setBounds(91, 446, 76, 22);
		finestraEV.add(disiscrizione);
		disiscrizione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				disiscrizione.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				disiscrizione.setBackground(SystemColor.desktop);
			}
		});
		disiscrizione.setBackground(SystemColor.desktop);
		disiscrizione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				noteSistema.setText(sn.revocaIscrizione(ev));
			}
		});


		noteSistema = new Label("\r\n");
		noteSistema.setText("");
		noteSistema.setBounds(169, 446, 405, 22);
		noteSistema.setForeground(Color.green);
		finestraEV.add(noteSistema);

		JLabel txtPartecnec = new JLabel();
		txtPartecnec.setText("ParetecipantiNecessari: "+ev.getPartecipantiNecessari().getValoreString());
		txtPartecnec.setOpaque(true);

		txtPartecnec.setBackground(SystemColor.info);
		txtPartecnec.setBounds(10, 354, 641, 20);
		finestraEV.add(txtPartecnec);

		JLabel statoEvento = new JLabel();
		statoEvento.setText("StatoEvento: "+ev.getStato());
		statoEvento.setOpaque(true);
		statoEvento.setBackground(SystemColor.info);
		statoEvento.setBounds(10, 385, 641, 20);
		finestraEV.add(statoEvento);

		JLabel proprietario = new JLabel();
		proprietario.setText("ProprietarioEvento: "+ev.getProprietario());
		proprietario.setOpaque(true);
		proprietario.setBackground(SystemColor.info);
		proprietario.setBounds(10, 416, 641, 20);
		finestraEV.add(proprietario);

		Button cancellazioneEv = new Button("EliminaEvento");
		cancellazioneEv.setBackground(SystemColor.desktop);
		cancellazioneEv.setBounds(575, 446, 76, 22);
		finestraEV.add(cancellazioneEv);

		cancellazioneEv.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				cancellazioneEv.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				cancellazioneEv.setBackground(SystemColor.desktop);
			}
		});
		cancellazioneEv.setBackground(SystemColor.desktop);
		cancellazioneEv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {		
				if(ev.getProprietario().equals(sn.getUtente().getUsername()))
				{
					sn.cancellaEvento(ev);
					costruisciBachecaPDC();
				}
				else
					noteSistema.setText("Non puoi cancellare un eveto se non sei il proprietario");

			}
		});

		frame.revalidate();
		frame.repaint();
		
		
	}
	public void rimozioneVariPanel(){
		if (bachecaEIM!= null)
			frame.getContentPane().remove(bachecaEIM);
		if (finestraCEVEscu!= null)
			frame.getContentPane().remove(finestraCEVEscu);
		if(finestraCEVPartita!= null)
			frame.getContentPane().remove(finestraCEVPartita);
		if(panelAP != null)
			frame.getContentPane().remove(panelAP);
		if(panelInfo != null)
			frame.getContentPane().remove(panelInfo);
		if(bachecaPDC != null)
			frame.getContentPane().remove(bachecaPDC);
		if(finestraEV != null)
			frame.getContentPane().remove(finestraEV);
		if(panelCategorie!= null)
			frame.getContentPane().remove(panelCategorie);
		if(panelModificaDati!= null)
			frame.getContentPane().remove(panelModificaDati);
		if(panelInvito!= null)
			frame.getContentPane().remove(panelInvito);	
				
	}
}







