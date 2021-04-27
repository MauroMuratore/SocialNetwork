package newGUI.body.areaPersonale;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;

import javax.swing.JSplitPane;

import lib.core.Utente;
import lib.util.Nomi;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class ViewAP extends JPanel {

	private JPanel infoPanel;
	private JPanel notifichePanel;
	private JButton btnModifica;
	
	public ViewAP(Utente utente, ActionListener al) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.33);
		add(splitPane);
		infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		notifichePanel = new JPanel();
		notifichePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		splitPane.setLeftComponent(infoPanel);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		
		JLabel lblInfo = new JLabel("Dati Personali");
		lblInfo.setFont(new Font(getFont().getFontName(), Font.BOLD, 18));
		infoPanel.add(lblInfo);
		
		
		JPanel panelInterno = new JPanel();
		panelInterno.setLayout(new BoxLayout(panelInterno, BoxLayout.Y_AXIS));
		
		JLabel lblUsername = new JLabel("Username: " + utente.getUsername());
		lblUsername.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInterno.add(lblUsername);
		
		String pw = "*";
		for(int i=1; i<utente.getPassword().length(); i++) {
			pw.concat("*");
		}
		
		JLabel lblPw = new JLabel("Password: " + pw);
		lblPw.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInterno.add(lblPw);
		
		JLabel lblEtaMin = new JLabel("Eta' minima: " + utente.getEtaMin());
		lblEtaMin.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInterno.add(lblEtaMin);
		
		JLabel lblEtaMax = new JLabel("Eta' massima: " + utente.getEtaMax() );
		lblEtaMax.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInterno.add(lblEtaMax);
		
		JLabel lblCatPref = new JLabel("Categorie preferite: ");
		lblCatPref.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInterno.add(lblCatPref);
		
		for(String cat: utente.getInteressi()) {
			JLabel lblCategoria = new JLabel(cat);
			lblCategoria.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
			panelInterno.add(lblCategoria);
		}
				
		JScrollPane scrollPane = new JScrollPane(panelInterno);
		infoPanel.add(scrollPane);
		
		btnModifica = new JButton(Nomi.AZIONE_MODIFICA_PROFILO.getNome());
		btnModifica.addActionListener(al);
		btnModifica.setActionCommand(Nomi.AZIONE_MODIFICA_PROFILO.getNome());
		infoPanel.add(btnModifica);
		
		splitPane.setRightComponent(notifichePanel);
		
		
	}

}
