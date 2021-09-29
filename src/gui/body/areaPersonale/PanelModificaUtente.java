package gui.body.areaPersonale;

import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lib.core.utenti.Utente;
import lib.util.Nomi;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JSpinner;
import java.awt.Component;

public class PanelModificaUtente extends JPanel {
	
	private JSpinner spinnerMax;
	private JSpinner spinnerMin;
	private JList<String> listCat;

	/**
	 * Create the panel.
	 */
	public PanelModificaUtente(Utente utente, ActionListener al, ActionListener father) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Box boxUser = Box.createHorizontalBox();
		JLabel lblUsername = new JLabel("Username: " + utente.getUsername());
		lblUsername.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		boxUser.add(lblUsername);
		boxUser.add(Box.createHorizontalGlue());
		add(boxUser);

		String pw = "*";
		for(int i=1; i<utente.getPassword().length(); i++) {
			pw.concat("*");
		}
		
		Box boxPw = Box.createHorizontalBox();
		JLabel lblPw = new JLabel("Password: " + pw);
		lblPw.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		boxPw.add(lblPw);
		boxPw.add(Box.createHorizontalGlue());
		add(boxPw);
		
		JPanel panelEtaMin = new JPanel();
		FlowLayout fl_panelEtaMin = new FlowLayout();
		fl_panelEtaMin.setAlignment(FlowLayout.LEFT);
		panelEtaMin.setLayout(fl_panelEtaMin);
		JLabel lblEtaMin = new JLabel ("Eta min ");
		lblEtaMin.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelEtaMin.add(lblEtaMin);
		add(panelEtaMin);
		
		spinnerMin = new JSpinner();
		spinnerMin.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		spinnerMin.setValue(new Integer(utente.getEtaMin()));
		panelEtaMin.add(spinnerMin);
		
		JPanel panelEtaMax = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelEtaMax.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panelEtaMax);
		
		JLabel lblEtaMax = new JLabel("Eta max ");
		lblEtaMax.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		panelEtaMax.add(lblEtaMax);
		
		spinnerMax = new JSpinner();
		spinnerMax.setFont(new Font(getFont().getFontName(), Font.PLAIN, 18));
		spinnerMax.setValue(utente.getEtaMax());
		panelEtaMax.add(spinnerMax);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		model.addElement(Nomi.CAT_PARTITA_CALCIO.getNome());
		model.addElement(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		
		listCat = new JList<String>(model);
		
		
		JScrollPane scrollPane  = new JScrollPane(listCat);
		
		add(scrollPane);
		
		add(Box.createVerticalGlue());
		
		JButton btnModifica = new JButton(Nomi.AZIONE_MODIFICA_PROFILO.getNome());
		btnModifica.addActionListener(al);
		btnModifica.addActionListener(father);
		btnModifica.setActionCommand(Nomi.AZIONE_MODIFICA_PROFILO.getNome());
		add(btnModifica);
		
	}
	
	public int getEtaMin() {
		return ((Integer) spinnerMin.getValue()).intValue();
	}
	
	public int getEtaMax() {
		return ((Integer) spinnerMax.getValue()).intValue();
	}
	
	public String[] getCatPref() {
		int lenght = listCat.getSelectedValuesList().size();
		String[] ritorno = new String[lenght];
		for(int i=0; i<lenght; i++) {
			ritorno[i]= (String) listCat.getSelectedValuesList().get(i);
		}
		return ritorno;
	}

}
