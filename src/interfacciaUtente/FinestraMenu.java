package interfacciaUtente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import cervello.Categoria;
import cervello.Evento;
import cervello.PartitaCalcioCat;
import cervello.PartitaCalcioEvento;
import cervello.SocialNetwork;
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
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FinestraMenu {

	private JFrame frame;
	private JPanel panelInfo;
	private JPanel panelCategorie;
	private JButton btnPartitedicalcio;
	private JLabel txtpnSelezionaLaCategoria;
	private JPanel bachecaPDC ;
	private JPanel panelAP;
	private SocialNetwork SN;
	private Categoria pdc;
	private JPanel finestraEV;
	private int k=0;//serve per un for(dichiarata qua per questioni di visibilita)
	private UserInterface UI;

	public FinestraMenu(SocialNetwork sn, UserInterface ui) {
		UI = ui;
		SN=sn;
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
		frame.setBounds(600, 300, 689, 447);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
				System.out.println("ci sono");
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
						//frame.revalidate();
						//frame.repaint();
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
		pannelloTitoliE.setBounds(0, 39, 663, 336);
		bachecaPDC.add(pannelloTitoliE);
		pannelloTitoliE.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		pdc = SN.mostraCategoria("Partita calcio");
		System.out.println(pdc.getNome());
		ArrayList<Evento> bacheca = pdc.getBacheca();
		int size = bacheca.size();

		for(k=0;k<size;k++)
		{ 
			String titolo= ((Evento) pdc.getBacheca().get(k)).getTitolo().getValoreString();
			JButton btnNewButton = new JButton(titolo);
			btnNewButton.setBackground(SystemColor.desktop);
			costruisciActionListener(btnNewButton,k);
			pannelloTitoliE.add(btnNewButton);

		}
		frame.getContentPane().add(bachecaPDC);
		frame.setResizable(false);
		frame.setBounds(600, 300, 667, 430);
		frame.revalidate();
		frame.repaint();
	}
	public void costruisciFinestraEvento(Evento ev){

		finestraEV = new JPanel();
		finestraEV.setBackground(new Color(224, 255, 255));
		finestraEV.setBounds(0, 23, 673, 385);
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


		//		frame.setResizable(false);
		//		frame.setBounds(600, 300, 667, 430);
		frame.revalidate();
		frame.repaint();
	}
	public void costruisciActionListener(JButton btn, int k){
		
		System.out.println(k);
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
//				System.out.println(((Evento) pdc.getBacheca().get(k-1)).getTitolo().getValore());
				costruisciFinestraEvento((Evento) pdc.getBacheca().get(k));
			}
		});
	}
	public void costruisciFinestraAP(){
		
		panelAP = new JPanel();
		panelAP.setBackground(new Color(176, 224, 230));
		panelAP.setBounds(0, 23, 673, 385);
		frame.getContentPane().add(panelAP);
		panelAP.setLayout(null);
		
		frame.getContentPane().add(panelAP);
		
		List list = new List();
		list.setForeground(new Color(0, 0, 0));
		list.setBackground(new Color(0, 204, 204));
		list.setMultipleMode(true);
		list.setBounds(204, 38, 459, 60);
		list.add("notifica1");
		list.add("notifica2");
		list.add("notifica3");
		list.add("notifica4");
		list.add("notifica5");
		list.add("notifica6");
		panelAP.add(list);
		
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
		
		TextArea textArea = new TextArea();
		textArea.setBackground(new Color(0, 204, 204));
		textArea.setForeground(new Color(224, 255, 255));
		textArea.setBounds(204, 104, 459, 271);
		panelAP.add(textArea);
	
		frame.revalidate();
		frame.repaint();
		
		
		
	}
}







