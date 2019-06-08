package interfacciaUtente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import com.sun.glass.events.WindowEvent;

import cervello.Campo;
import cervello.Categoria;
import cervello.Evento;
import cervello.PartitaCalcioCat;
import cervello.PartitaCalcioEvento;
import cervello.SocialNetwork;
import cervello.StatoEvento;
import database.ConsultaDB;
//import javafx.stage.WindowEvent;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;

import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;

import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class FinestraMenu {

	private JFrame frame;
	private JPanel panelInfo;
	private JPanel panelCategorie;
	private JButton btnPartitedicalcio;
	private JLabel txtpnSelezionaLaCategoria;
	private JPanel bachecaPDC ;
	private JPanel panelAP;
	private SocialNetwork sn;
	private Categoria pdc;
	private JPanel finestraEV;
	private int k=0;//serve per un for(dichiarata qua per questioni di visibilita)
	private UserInterface UI;
	private JPanel finestraCEV;
	private Evento eventoCreato ;
	private TextArea textArea;
	private Label messaggioErr;
	private Label noteSistema;
	private List listaNot ;
	private static final String EVENTO_VALIDO = "Evento valido";
	private static final String EVENTO_ESISTENTE = "ATTENZIONE: l'evento è gia esistente";
	private static final String VUOTO = "ATTENZIONE: il campo è vuoto";
	private static final String OK = "OK";
	private static final String FORMATO_SBAGLIATO = "ATTENZIONE: il formato è errato";
	private static final String PARTECIPANTI_NECESSARI_MIN ="partecipanti necessari inconsistenti";

	public FinestraMenu(SocialNetwork _sn, UserInterface ui) {
		UI = ui;
		sn=_sn;
		initialize();
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	private void initialize() {
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
				if(finestraCEV!= null)
					frame.getContentPane().remove(finestraCEV);
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
				frame.revalidate();
				frame.repaint();
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
				if(finestraCEV!= null)
					frame.getContentPane().remove(finestraCEV);
				if(panelCategorie != null)
					frame.getContentPane().remove(panelCategorie);
				if(panelAP != null)
					frame.getContentPane().remove(panelAP);
				if(panelInfo != null)
					frame.getContentPane().remove(panelInfo);
				if(bachecaPDC != null)
					frame.getContentPane().remove(bachecaPDC);
				if(finestraEV != null)
					frame.getContentPane().remove(finestraEV);
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

		if(finestraCEV!= null)
			frame.getContentPane().remove(finestraCEV);
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

		pdc = sn.mostraCategoria("Partita calcio");
		System.out.println(pdc.getNome());
		ArrayList<Evento> bacheca = pdc.getBacheca();
		int size = bacheca.size();

		for(k=0;k<size;k++)
		{  
			if(((Evento) pdc.getBacheca().get(k)).getStato().equals(StatoEvento.APERTO)||((Evento) pdc.getBacheca().get(k)).getStato().equals(StatoEvento.CHIUSO))
			{
				String titolo= ((Evento) pdc.getBacheca().get(k)).getTitolo().getValoreString();
				JButton btnNewButton = new JButton(titolo);
				btnNewButton.setBackground(SystemColor.desktop);
				costruisciActionListener(btnNewButton,k);
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
				frame.revalidate();
				frame.repaint();
				costruisciFinestraCreazioneEV();
			}
		});

		frame.revalidate();
		frame.repaint();

	}
	public void costruisciFinestraCreazioneEV(){

		finestraCEV = new JPanel();
		finestraCEV.setBackground(new Color(224, 255, 255));
		finestraCEV.setBounds(0, 23, 673, 385);
		finestraCEV.setLayout(null);
		frame.getContentPane().add(finestraCEV);

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
		finestraCEV.add(label);

		Label label_1 = new Label("Titolo:");
		label_1.setBounds(10, 38, 62, 22);
		finestraCEV.add(label_1);

		Label label_2 = new Label("PartecipantiNec:");
		label_2.setBounds(10, 66, 122, 22);
		finestraCEV.add(label_2);

		Label label_3 = new Label("Luogo:");
		label_3.setBounds(10, 94, 62, 22);
		finestraCEV.add(label_3);

		Label label_4 = new Label("DataInizio:");
		label_4.setBounds(10, 122, 62, 22);
		finestraCEV.add(label_4);

		Label label_5 = new Label("TermineUltimoIsc:\r\n");
		label_5.setBounds(10, 262, 105, 22);
		finestraCEV.add(label_5);

		Label label_6 = new Label("Durata:");
		label_6.setBounds(10, 178, 62, 22);
		finestraCEV.add(label_6);

		Label label_7 = new Label("QuotaIndividuale:");
		label_7.setBounds(10, 206, 122, 22);
		finestraCEV.add(label_7);

		Label label_8 = new Label("CompresoNellaQuota:");
		label_8.setBounds(10, 234, 122, 22);
		finestraCEV.add(label_8);

		Label label_9 = new Label("DataFine:\r\n");
		label_9.setBounds(10, 150, 62, 22);
		finestraCEV.add(label_9);

		Label label_10 = new Label("Note:");
		label_10.setBounds(10, 290, 62, 22);
		finestraCEV.add(label_10);

		textField = new JTextField();
		textField.setBounds(142, 40, 159, 20);
		finestraCEV.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(142, 66, 159, 20);
		finestraCEV.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(142, 96, 159, 20);
		finestraCEV.add(textField_2);

		txtGgmmaa_1 = new JTextField();
		txtGgmmaa_1.setText("GG/MM/AA");
		txtGgmmaa_1.setColumns(10);
		txtGgmmaa_1.setBounds(142, 122, 159, 20);
		finestraCEV.add(txtGgmmaa_1);

		txtGgmmaa_2 = new JTextField();
		txtGgmmaa_2.setText("GG/MM/AA");
		txtGgmmaa_2.setColumns(10);
		txtGgmmaa_2.setBounds(142, 152, 159, 20);
		finestraCEV.add(txtGgmmaa_2);

		txtnumeroDiGiorni = new JTextField();
		txtnumeroDiGiorni.setText("(NUMERO DI GIORNI)");
		txtnumeroDiGiorni.setColumns(10);
		txtnumeroDiGiorni.setBounds(142, 180, 159, 20);
		finestraCEV.add(txtnumeroDiGiorni);

		textField_6 = new JTextField();
		textField_6.setText("(\u20AC)");
		textField_6.setColumns(10);
		textField_6.setBounds(142, 208, 159, 20);
		finestraCEV.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(142, 236, 159, 20);
		finestraCEV.add(textField_7);

		txtGgmmaa = new JTextField();
		txtGgmmaa.setText("GG/MM/AA");
		txtGgmmaa.setColumns(10);
		txtGgmmaa.setBounds(142, 262, 159, 20);
		finestraCEV.add(txtGgmmaa);

		textField_8 = new JTextField();
		textField_8.setColumns(10);
		textField_8.setBounds(142, 290, 159, 20);
		finestraCEV.add(textField_8);

		messaggioErr= new Label();
		messaggioErr.setForeground(Color.red);
		messaggioErr.setBounds(307, 336, 356, 22);
		finestraCEV.add(messaggioErr);

		Label label_11 = new Label("OraInizio: ");
		label_11.setBounds(307, 150, 62, 22);
		finestraCEV.add(label_11);

		Label label_12 = new Label("OraFine: ");
		label_12.setBounds(307, 178, 62, 22);
		finestraCEV.add(label_12);

		txtOraminuti = new JTextField();
		txtOraminuti.setText("ORA:MINUTI");
		txtOraminuti.setColumns(10);
		txtOraminuti.setBounds(435, 152, 159, 20);
		finestraCEV.add(txtOraminuti);

		textOraminutiFine = new JTextField();
		textOraminutiFine.setText("ORA:MINUTI");
		textOraminutiFine.setColumns(10);
		textOraminutiFine.setBounds(435, 178, 159, 20);
		finestraCEV.add(textOraminutiFine);

		Label label_13 = new Label("Sesso:");
		label_13.setBounds(307, 94, 62, 22);
		finestraCEV.add(label_13);

		textfieldEta = new JTextField();
		textfieldEta.setColumns(10);
		textfieldEta.setBounds(435, 122, 159, 20);
		finestraCEV.add(textfieldEta);

		Label label_14 = new Label("Et\u00E0:");
		label_14.setBounds(307, 122, 62, 22);
		finestraCEV.add(label_14);

		Choice choice = new Choice();
		choice.add("M");
		choice.add("F");
		choice.setBounds(435, 94, 159, 20);
		finestraCEV.add(choice);
		
		Label label_15 = new Label("ToleranzaPartecipanti: ");
		label_15.setBounds(307, 66, 125, 22);
		finestraCEV.add(label_15);
		
		JTextField textTolleranza = new JTextField();
		textTolleranza.setColumns(10);
		textTolleranza.setBounds(435, 66, 159, 20);
		finestraCEV.add(textTolleranza);

		Label label_16 = new Label("TermineUltimoRitiro:");
		label_16.setBounds(307, 206, 122, 22);
		finestraCEV.add(label_16);
		
		JTextField termineultRit = new JTextField();
		termineultRit.setColumns(10);
		termineultRit.setText("GG/MM/AA");
		termineultRit.setBounds(435, 206, 159, 20);
		finestraCEV.add(termineultRit);


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
				if(messaggioErr.getText().equals(OK))
				{  
					System.out.println(messaggioErr.getText());
					//if(eventoCreato.valido())
					{
						sn.addEvento(eventoCreato);
						costruisciBachecaPDC();
					}
				}
				else
				{
					System.out.println(messaggioErr.getText());
					costruisciFinestraCreazioneEV();
				}

			}
		});
		btnNewButton.setBackground(SystemColor.desktop);
		btnNewButton.setBounds(142, 336, 159, 23);
		finestraCEV.add(btnNewButton);


	}
	public String settaCampi(String titolo,String partNec, String luogo, String dataInizio,String dataFine,String durata,String quota,String compresoQuota,String termineUltimo,String note,String oraIni,String oraFine,String sesso,String eta,String tolleranza,String termineUltRit)
	{
		String messaggio;
		messaggio=eventoCreato.setTitolo(titolo);
		if(!messaggio.equals(OK))return messaggio+" titolo";
		messaggio=eventoCreato.setPartecipantiNecessari(partNec);
		if(!messaggio.equals(OK))return messaggio+" part necessari";
		messaggio=eventoCreato.setLuogo(luogo);
		if(!messaggio.equals(OK))return messaggio+" luogo";
		messaggio=eventoCreato.setDataInizio(dataInizio, oraIni);
		if(!messaggio.equals(OK))return messaggio+" data inizio";
		messaggio=eventoCreato.setDataFine(dataFine, oraFine);
		if(!messaggio.equals(OK))return messaggio+" data fine";
		messaggio=eventoCreato.setDurata(durata);
		if(!messaggio.equals(OK))return messaggio+ " durata";
		messaggio=eventoCreato.setQuotaIndividuale(quota);
		if(!messaggio.equals(OK))return messaggio+" quota";
		messaggio=eventoCreato.setCompresoQuota(compresoQuota);
		if(!messaggio.equals(OK))return messaggio+" compreso quota";
		messaggio=eventoCreato.setTermineUltimo(termineUltimo);
		if(!messaggio.equals(OK))return messaggio+" termine ultimo";
		messaggio=eventoCreato.setNote(note);
		if(!messaggio.equals(OK))return messaggio+" note";
		messaggio=((PartitaCalcioEvento) eventoCreato).setSesso(sesso);
		if(!messaggio.equals(OK))return messaggio+" sesso";
		messaggio=((PartitaCalcioEvento) eventoCreato).setEta(eta);
		if(!messaggio.equals(OK))return messaggio+" età";
		messaggio=eventoCreato.setTolleranzaPartecipanti(tolleranza);
		if(!messaggio.equals(OK))return messaggio+" tolleranza";
		messaggio=eventoCreato.setTermineUltimoRitiro(termineUltRit);
		if(!messaggio.equals(OK))return messaggio+"termine ultimo ritiro";
		return OK;
	}
	public void costruisciFinestraEvento(Evento ev){

		
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
		txtQuota.setText("Quota: "+ev.getQuotaIndividuale().getValoreString()+" €");
		txtQuota.setBounds(10, 230, 641, 20);
		finestraEV.add(txtQuota);

		JLabel txtEta = new JLabel();
		txtEta.setOpaque(true);
		txtEta.setBackground(SystemColor.info);
		txtEta.setText("Età: "+((PartitaCalcioEvento)ev).getEta().getValoreString());
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
	public void costruisciActionListener(JButton btn, int k){

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
				costruisciFinestraEvento((Evento) pdc.getBacheca().get(k));
			}
		});

		//		frame.setResizable(false);
		//		frame.setBounds(600, 300, 667, 430);
		frame.revalidate();
		frame.repaint();
	}

	public void costruisciFinestraAP(){

		if(panelAP!=null)frame.remove(panelAP);
		frame.revalidate();
		frame.repaint();
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
		listaNot.setMultipleMode(true);
		listaNot.setBounds(204, 38, 459, 60);
		listaNot.setMultipleMode(false);
		System.out.println(sn.getUtente().getNotifiche().get(0).getEvento().getTitolo());
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
								"QuotaIndividuale: "+sn.getUtente().getNotifiche().get(count).getEvento().getQuotaIndividuale().getValoreString()+" € "+ "\r\n"+
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

		Label label_1 = new Label("username: " + UI.getUS());
		label_1.setBounds(0, 38, 198, 22);
		panel.add(label_1);

		Label label_3 = new Label("password: " + new String(UI.getPASS()));
		label_3.setBounds(0, 66, 198, 22);
		panel.add(label_3);

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
}







