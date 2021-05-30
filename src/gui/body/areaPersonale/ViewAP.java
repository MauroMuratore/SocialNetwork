package gui.body.areaPersonale;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JSplitPane;

import lib.core.Notifica;
import lib.core.Utente;
import lib.net.ModelModificaUtente;
import lib.util.Nomi;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.CardLayout;
import javax.swing.ListSelectionModel;

public class ViewAP extends JPanel {

	private JPanel infoPanel;
	private JPanel notifichePanel;
	private JPanel panelVistaOModifica;
	private JButton btnModifica;
	private JButton btnCancella;
	private JList<String> listNotifiche;
	private JLabel lblEtaMin;
	private JLabel lblEtaMax;
	private JLabel catPartita;
	private JLabel catEscursione;
	private JPanel panelInternoInfo;
	private JScrollPane scrollPaneNot;
	private DefaultListModel<String> modelList;
	private PanelModificaUtente panelMU;
	private CardLayout layoutVistaOModifica;
	private JLabel lblNotifiche;

	public ViewAP(Utente utente, ActionListener al, ActionListener father) {

		setLayout(new CardLayout(0, 0));

		JPanel panelPrincipale = new JPanel();
		add(panelPrincipale, "name_3739923649066");
		panelPrincipale.setLayout(new BoxLayout(panelPrincipale, BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		panelPrincipale.add(splitPane);
		splitPane.setResizeWeight(0.33);
		infoPanel = new JPanel();
		infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		notifichePanel = new JPanel();
		notifichePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		splitPane.setLeftComponent(infoPanel);
		splitPane.setRightComponent(notifichePanel);
		notifichePanel.setLayout(new BoxLayout(notifichePanel, BoxLayout.Y_AXIS));

		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

		JLabel lblInfo = new JLabel("Dati Personali");
		lblInfo.setFont(new Font(getFont().getFontName(), Font.BOLD, 18));
		infoPanel.add(lblInfo);

		panelVistaOModifica = new JPanel();
		layoutVistaOModifica = new CardLayout(0,0);
		panelVistaOModifica.setLayout(layoutVistaOModifica);
		infoPanel.add(panelVistaOModifica);

		panelInternoInfo = new JPanel();
		JPanel panelScrollBtn = new JPanel();
		panelScrollBtn.setLayout(new BoxLayout(panelScrollBtn, BoxLayout.Y_AXIS));
		setUtente(utente);

		JScrollPane scrollPaneInfo = new JScrollPane(panelInternoInfo);
		panelScrollBtn.add(scrollPaneInfo);

		btnModifica = new JButton(Nomi.AZIONE_MODIFICA_PROFILO.getNome());
		btnModifica.addActionListener(al);
		btnModifica.addActionListener(father);
		btnModifica.setActionCommand("Inizio Modifica");
		panelScrollBtn.add(btnModifica);
		panelVistaOModifica.add(panelScrollBtn);

		modelList = new DefaultListModel<>();

		for(int i=0; i<utente.getNotifiche().size(); i++) {
			modelList.addElement(i + " " + utente.getNotifiche().get(i).getMessaggio());
		}
		
		lblNotifiche = new JLabel("Elenco notifiche");
		lblNotifiche.setFont(new Font(getFont().getFontName(), Font.BOLD, 18));
		notifichePanel.add(lblNotifiche);
		

		listNotifiche = new JList<String>(modelList);
		listNotifiche.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		scrollPaneNot = new JScrollPane(listNotifiche);

		notifichePanel.add(scrollPaneNot);

		btnCancella = new JButton(Nomi.AZIONE_CANCELLA_NOTIFICA.getNome());
		btnCancella.addActionListener(al);
		btnCancella.addActionListener(father);
		btnCancella.setActionCommand(Nomi.AZIONE_CANCELLA_NOTIFICA.getNome());
		notifichePanel.add(btnCancella);

	}

	public String getNotifica() {
		return listNotifiche.getSelectedValue();
	}
	
	public void setActionListener(ActionListener father) {
		System.out.println("imposto action listener");
		btnCancella.addActionListener(father);
		
	}

	public void aggiornaNotifiche(List<Notifica> notifiche) {
		modelList.removeAllElements();
		for(int i=0; i<notifiche.size(); i++) {
			modelList.addElement(i + " " + notifiche.get(i).getMessaggio());
		}		
		revalidate();
		repaint();
	}

	public void setUtente(Utente utente) {
		panelInternoInfo.setLayout(new BoxLayout(panelInternoInfo, BoxLayout.Y_AXIS));

		JLabel lblUsername = new JLabel("Username: " + utente.getUsername());
		lblUsername.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(lblUsername);

		String pw = "*";
		for(int i=1; i<utente.getPassword().length(); i++) {
			pw.concat("*");
		}

		JLabel lblPw = new JLabel("Password: " + pw);
		lblPw.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(lblPw);

		lblEtaMin = new JLabel("Eta' minima: " + utente.getEtaMin());
		lblEtaMin.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(lblEtaMin);

		lblEtaMax = new JLabel("Eta' massima: " + utente.getEtaMax() );
		lblEtaMax.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(lblEtaMax);

		JLabel lblCatPref = new JLabel("Categorie preferite: ");
		lblCatPref.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(lblCatPref);

		if(utente.getInteressi().contains(Nomi.CAT_PARTITA_CALCIO.getNome())){
			catPartita = new JLabel(Nomi.CAT_PARTITA_CALCIO.getNome());
		} else catPartita = new JLabel("");

		catPartita.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(catPartita);

		if(utente.getInteressi().contains(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())){
			catEscursione = new JLabel(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		} else catEscursione = new JLabel("");
		catEscursione.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelInternoInfo.add(catEscursione);

	}

	public void addPanelModifica(PanelModificaUtente pmu) {
		panelMU = pmu;
		panelVistaOModifica.add(pmu);
		layoutVistaOModifica.last(panelVistaOModifica);
		repaint();
	}

	public void removePanelModifica(PanelModificaUtente pmu) {
		panelVistaOModifica.remove(pmu);
		layoutVistaOModifica.first(panelVistaOModifica);
		repaint();
	}

	public void aggiornaUtente(ModelModificaUtente mmu) {
		lblEtaMax.setText("Eta' massima: " + mmu.getEtaMax());
		lblEtaMin.setText("Eta' minima: " + mmu.getEtaMin());
		if(mmu.isCatPartita()) {
			catPartita.setText(Nomi.CAT_PARTITA_CALCIO.getNome());
		} else catPartita.setText("");

		if(mmu.isCatEscursione()) {
			catEscursione.setText(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		} else catEscursione.setText("");
		repaint();
	}



}
