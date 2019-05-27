package interfacciaUtente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import cervello.Evento;
import cervello.PartitaCalcioCat;
import cervello.PartitaCalcioEvento;
import cervello.SocialNetwork;
import database.ConsultaDB;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinestraMenu {

	private JFrame frame;
	private JPanel panelInfo;
	private JPanel panelCategorie;
	private JButton btnPartitedicalcio;
	private JTextPane txtpnSelezionaLaCategoria;
	private JPanel bachecaPDC ;
	private JPanel panelAP;
	private SocialNetwork SN;
	private PartitaCalcioCat<PartitaCalcioEvento> pdc;
	private JPanel finestraEV;
	private int k=0;//serve per un for(dichiarata qua per questioni di visibilita)

	public FinestraMenu(SocialNetwork sn) {
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
		
		JButton btnAreapersonale = new JButton("AreaPersonale");
		btnAreapersonale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAreapersonale.setBackground(Color.blue);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAreapersonale.setBackground(SystemColor.textHighlight);
			}
			public void mouseClicked(MouseEvent e){
				if (panelInfo != null)
				frame.getContentPane().remove(panelInfo);
				if (bachecaPDC != null)
				frame.getContentPane().remove(bachecaPDC);
				if (panelCategorie != null)
				frame.getContentPane().remove(panelCategorie);
				frame.revalidate();
				frame.repaint();
				panelAP = new JPanel();
				panelAP.setBackground(new Color(224, 255, 255));
				panelAP.setBounds(0, 23, 673, 385);
				frame.getContentPane().add(panelAP);
				panelAP.setLayout(null);	
			}
		});
		btnAreapersonale.setBackground(SystemColor.textHighlight);
		btnAreapersonale.setForeground(SystemColor.text);
		btnAreapersonale.setBounds(0, 0, 125, 23);
		panel.add(btnAreapersonale);
		
		JButton btnVistacategorie = new JButton("VistaCategorie");
		btnVistacategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					
					public void mouseClicked(MouseEvent e) {
						
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
						frame.revalidate();
						frame.repaint();
						costruisciBachecaPDC();

					}
				});
				
				btnPartitedicalcio.setBackground(SystemColor.desktop);
				btnPartitedicalcio.setBounds(10, 59, 290, 23);
				panelCategorie.add(btnPartitedicalcio);
				
				txtpnSelezionaLaCategoria = new JTextPane();
				txtpnSelezionaLaCategoria.setText("Seleziona la categoria di ciu voi visualizzare gli eventi");
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
		
		panelInfo = new JPanel();
		panelInfo.setBackground(new Color(224, 255, 255));
		panelInfo.setBounds(0, 23, 673, 385);
		frame.getContentPane().add(panelInfo);
		panelInfo.setLayout(null);
		
		JTextPane txtpnBenvenutoNelSocial = new JTextPane();
		txtpnBenvenutoNelSocial.setOpaque(false);
		txtpnBenvenutoNelSocial.setText("Benvenuto nel Social network M&M. Scegli una delle voci nella barra navigatrice  ");
		txtpnBenvenutoNelSocial.setBounds(10, 11, 414, 20);
		panelInfo.add(txtpnBenvenutoNelSocial);
		
		JTextPane txtpnAreapersonale = new JTextPane();
		txtpnAreapersonale.setBackground(SystemColor.info);
		txtpnAreapersonale.setText("qui potrai consultare le tue notifiche e visualizzare i tuoi dai personali");
		txtpnAreapersonale.setBounds(128, 42, 390, 20);
		panelInfo.add(txtpnAreapersonale);
		
		JTextPane txtpnVistacategorieQuiPotrai = new JTextPane();
		txtpnVistacategorieQuiPotrai.setBackground(SystemColor.info);
		txtpnVistacategorieQuiPotrai.setText("qui potrai visualizzare l'elenco delle categorie e sceglire quale tra queste visualizzarne gli eventi");
		txtpnVistacategorieQuiPotrai.setBounds(128, 73, 535, 20);
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
		
		JTextPane txtpnQuestiSonoI = new JTextPane();
		txtpnQuestiSonoI.setForeground(SystemColor.textHighlight);
		txtpnQuestiSonoI.setText("Questi sono i titoli degli eventi disponibili( per maggiori informazioni su un evento fai click su uno di esso)");
		txtpnQuestiSonoI.setBackground(SystemColor.info);
		txtpnQuestiSonoI.setBounds(10, 5, 643, 20);
		pannelloSpiegazione.add(txtpnQuestiSonoI);
		
		Panel pannelloTitoliE = new Panel();
		pannelloTitoliE.setBounds(0, 39, 663, 336);
		bachecaPDC.add(pannelloTitoliE);
		pannelloTitoliE.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		pdc = SN.getPdc();
		int size = pdc.getBacheca().size();
		
		for(;k<size;k++)
		{ 
			String titolo= ((Evento) pdc.getBacheca().get(k)).getTitolo().getValoreString();
			JButton btnNewButton = new JButton(titolo);
			btnNewButton.setBackground(SystemColor.desktop);
			btnNewButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					btnNewButton.setBackground(Color.green);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					btnNewButton.setBackground(SystemColor.desktop);
				}
			});
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					costruisciFinestraEvento((Evento) pdc.getBacheca().get(k-1));
				}
			});
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
		txtpnNome.setBackground(SystemColor.info);
		txtpnNome.setText("Titolo: "+ ev.getTitolo().getValore());
		txtpnNome.setBounds(10, 11, 641, 22);
		finestraEV.add(txtpnNome);
		
		JLabel txtpnTermineUltimo = new JLabel();
		txtpnTermineUltimo.setBackground(SystemColor.info);
		txtpnTermineUltimo.setText("Termine Ultimo: "+ ev.getTermineUltimo().getValoreString());
		txtpnTermineUltimo.setBounds(10, 44, 641, 20);
		finestraEV.add(txtpnTermineUltimo);
		
		JLabel txtpnLuogo = new JLabel();
		txtpnLuogo.setBackground(SystemColor.info);
		txtpnLuogo.setText("Luogo: "+ev.getLuogo().getValoreString());
		txtpnLuogo.setBounds(10, 75, 641, 20);
		finestraEV.add(txtpnLuogo);
		
		JLabel txtpnDatainizio = new JLabel();
		txtpnDatainizio.setBackground(SystemColor.info);
		txtpnDatainizio.setText("DataInizio: "+ev.getDataInizio().getValoreString());
		txtpnDatainizio.setBounds(10, 106, 641, 20);
		finestraEV.add(txtpnDatainizio);
		
		JLabel txtpnDatafine = new JLabel();
		txtpnDatafine.setBackground(SystemColor.info);
		txtpnDatafine.setText("DataFine: "+ev.getDataFine().getValoreString());
		txtpnDatafine.setBounds(10, 137, 641, 20);
		finestraEV.add(txtpnDatafine);
		
		JLabel txtpnDurata = new JLabel();
		txtpnDurata.setBackground(SystemColor.info);
		txtpnDurata.setText("Durata: "+ev.getDurata().getValoreString());
		txtpnDurata.setBounds(10, 168, 641, 20);
		finestraEV.add(txtpnDurata);
		
		JLabel txtpnCompresonellaquota = new JLabel();
		txtpnCompresonellaquota.setBackground(SystemColor.info);
		txtpnCompresonellaquota.setText("CompresoNellaQuota: "+ev.getCompresoQuota().getValoreString());
		txtpnCompresonellaquota.setBounds(10, 199, 641, 20);
		finestraEV.add(txtpnCompresonellaquota);
		
		JLabel txtpnNote = new JLabel();
		txtpnNote.setBackground(SystemColor.info);
		txtpnNote.setText("Note: "+ev.getNote().getValoreString());
		txtpnNote.setBounds(10, 230, 641, 20);
		finestraEV.add(txtpnNote);
		
		
		
//		frame.setResizable(false);
//		frame.setBounds(600, 300, 667, 430);
		frame.revalidate();
		frame.repaint();
		
	}
}
