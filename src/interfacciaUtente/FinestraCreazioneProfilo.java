package interfacciaUtente;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.List;

import javax.swing.JTextField;

import cervello.SocialNetwork;

import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FinestraCreazioneProfilo {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private SocialNetwork sn;
	private UserInterface ui;
	private JLabel messaggioSistema;
	private static final String VUOTO = "ATTENZIONE: il campo � vuoto";
	private static final String OK = "OK";
	private static final String FORMATO_SBAGLIATO = "ATTENZIONE: il formato � errato";

	public FinestraCreazioneProfilo(SocialNetwork _sn, UserInterface _ui) {
		ui=_ui;
		sn=_sn;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("SocialNetwork M&M (CreazioneProfilo)");
		frame.setBounds(600, 300, 712, 294);//+30
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.getContentPane().setLayout(null);
		creazionePanel();
		
	}

	public String settaCampiProfilo(String nomignolo,String min ,String max,String[] catPref){
		String messaggio;
		messaggio=sn.setNomignolo(nomignolo);
		if(!messaggio.equals(OK))return messaggio+" titolo";
		messaggio=sn.setFasciaDiEta(min,max);
		if(!messaggio.equals(OK))return messaggio+" titolo";
		messaggio=sn.setCategorieDiInteresse(catPref);
		if(!messaggio.equals(OK))return messaggio+" titolo";
		
		return messaggio;
		
	}
	public void creazionePanel(){
		
		Panel panel = new Panel();
		panel.setBackground(new Color(224, 255, 255));
		panel.setBounds(0, 0, 707, 265);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Questo \u00E8 il tuo primo accesso al social network, per questo ti chiediamo di darci alcune informazioni su di te e sulle tue preferenze.");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(SystemColor.desktop);
		lblNewLabel.setBounds(10, 11, 685, 30);
		panel.add(lblNewLabel);

		JLabel lblCompilaISegueinti = new JLabel("Compila i segueinti campi (Nota: FasciaDiEta e CategorieDiInteresse potranno essere cambiate in qualsirsi momento all interno del social)-->");
		lblCompilaISegueinti.setOpaque(true);
		lblCompilaISegueinti.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblCompilaISegueinti.setBackground(SystemColor.info);
		lblCompilaISegueinti.setBounds(10, 52, 685, 14);
		panel.add(lblCompilaISegueinti);

		JLabel lblNomignolo = new JLabel("Nomignolo :");
		lblNomignolo.setBounds(10, 77, 85, 21);
		panel.add(lblNomignolo);

		textField = new JTextField();
		textField.setBounds(94, 77, 119, 20);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblFasciadet = new JLabel("FasciaDiet\u00E0 :");
		lblFasciadet.setBounds(10, 109, 85, 21);
		panel.add(lblFasciadet);

		JLabel lblMin = new JLabel("min:");
		lblMin.setBounds(94, 109, 34, 21);
		panel.add(lblMin);

		JLabel lblMax = new JLabel("max:");
		lblMax.setBounds(198, 108, 34, 21);
		panel.add(lblMax);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(138, 108, 50, 20);
		panel.add(textField_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(242, 109, 50, 20);
		panel.add(textField_2);
		
		messaggioSistema = new JLabel("");
		messaggioSistema.setForeground(new Color(255, 0, 0));
		messaggioSistema.setBounds(10, 173, 685, 21);
		panel.add(messaggioSistema);

		JLabel lblCategoriediinteresse = new JLabel("CategorieDiInteresse:");
		lblCategoriediinteresse.setBounds(10, 141, 152, 21);
		panel.add(lblCategoriediinteresse);

		List list = new List();
		list.setBounds(138, 173, 196, -34);
		for(int i =0;i<sn.titoliCategorie().size();i++)
			list.add(sn.titoliCategorie().get(i));
		panel.add(list);

		JButton btnConferma = new JButton("Conferma");
		btnConferma.setBackground(SystemColor.desktop);
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nomignolo = textField.getText();
				String min=textField_1.getText();
				String max=textField_2.getText();
				String[] categoriePreferite= ((String [])list.getSelectedItems());
				messaggioSistema.setText(settaCampiProfilo(nomignolo,min,max,categoriePreferite));
				if(messaggioSistema.getText().equals(OK)){
					frame.dispose();
					ui.sezioneMenu();
				}
				else creazionePanel();		
			}
		});
		btnConferma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				btnConferma.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnConferma.setBackground(SystemColor.desktop);
			}
		});
		btnConferma.setBounds(309, 196, 89, 23);
		panel.add(btnConferma);
		
		
		
		
		
	}

	public JFrame getFrame() {
		// TODO Auto-generated method stub
		return f;
	}





}
