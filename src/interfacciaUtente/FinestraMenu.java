package interfacciaUtente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.FlowLayout;
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

//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FinestraMenu window = new FinestraMenu();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public FinestraMenu() {
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
		});
		btnAreapersonale.setBackground(SystemColor.textHighlight);
		btnAreapersonale.setForeground(SystemColor.text);
		btnAreapersonale.setBounds(0, 0, 125, 23);
		panel.add(btnAreapersonale);
		
		JButton btnVistacategorie = new JButton("VistaCategorie");
		btnVistacategorie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.getContentPane().remove(panelInfo);
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
						
						frame.getContentPane().remove(panelCategorie);
						frame.revalidate();
						frame.repaint();
						bachecaPDC = new JPanel();
						bachecaPDC.setBackground(new Color(224, 255, 255));
						bachecaPDC.setBounds(0, 23, 673, 385);
						frame.getContentPane().add(bachecaPDC);
						bachecaPDC.setLayout(null);
						
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
}
