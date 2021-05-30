package gui.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ViewLogin extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordFieldLog;
	private JTextField textUsernameLog;
	private JTextField textUsernameReg;
	private JPasswordField passwordFieldReg;
	private JPasswordField passwordFieldConferma;
	
	private String username;
	private String password;
	private String confermaPW;
	private int etaMin;
	private int etaMax;
	private String[] categoriePref;
	private JSpinner spinnerEtaMin;
	private JSpinner spinnerEtaMax;
	private JList list;
	private JButton btnConfermaReg;
	private JButton btnConfermaLog;
	
	/**
	 * Create the frame.
	 */
	public ViewLogin(String[] cat) {
		setTitle("Login");
		setBounds(100, 100, 402, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		CardLayout cl = new CardLayout(0, 0);
		setResizable(false);
		contentPane.setLayout(cl);
		
		JPanel panelLog = new JPanel();
		panelLog.setBorder(new EmptyBorder(10, 10, 10, 50));
		contentPane.add(panelLog, "name_9283346758503");
		panelLog.setLayout(null);
		
		JLabel lblUsernameLog = new JLabel("Username");
		lblUsernameLog.setBounds(10, 86, 72, 15);
		panelLog.add(lblUsernameLog);
		
		textUsernameLog = new JTextField();
		textUsernameLog.setBounds(100, 84, 232, 19);
		panelLog.add(textUsernameLog);
		textUsernameLog.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 156, 72, 15);
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		panelLog.add(lblPassword);
		
		passwordFieldLog = new JPasswordField();
		passwordFieldLog.setBounds(100, 154, 232, 19);
		panelLog.add(passwordFieldLog);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 183, 342, 25);
		panel.setBorder(new EmptyBorder(0, 15, 0, 15));
		panelLog.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 15, 0, 15));
		panel.add(panel_1);
		
		btnConfermaLog = new JButton("Conferma");

		btnConfermaLog.setBounds(41, 246, 104, 25);
		panelLog.add(btnConfermaLog);
		
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.setBounds(215, 246, 104, 25);
		btnRegistrati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.next(contentPane);
			}
		});
		panelLog.add(btnRegistrati);
		
		
		JPanel panelReg = new JPanel();
		panelReg.setBorder(new EmptyBorder(0, 20, 0, 0));
		contentPane.add(panelReg, "name_10834180881615");
		panelReg.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(22, 22, 88, 15);
		panelReg.add(lblUsername);
		
		textUsernameReg = new JTextField();
		textUsernameReg.setBounds(109, 20, 227, 19);
		panelReg.add(textUsernameReg);
		textUsernameReg.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(22, 49, 70, 15);
		panelReg.add(lblNewLabel);
		
		passwordFieldReg = new JPasswordField();
		passwordFieldReg.setText("");
		passwordFieldReg.setBounds(109, 47, 227, 19);
		panelReg.add(passwordFieldReg);
		
		JLabel lblConferma = new JLabel("Conferma");
		lblConferma.setBounds(22, 76, 70, 15);
		panelReg.add(lblConferma);
		
		passwordFieldConferma = new JPasswordField();
		passwordFieldConferma.setBounds(109, 74, 227, 19);
		panelReg.add(passwordFieldConferma);
		
		JLabel lblEtaMinima = new JLabel("Eta Minima");
		lblEtaMinima.setBounds(23, 120, 88, 15);
		panelReg.add(lblEtaMinima);
		
		spinnerEtaMin = new JSpinner();
		spinnerEtaMin.setBounds(109, 118, 40, 20);
		panelReg.add(spinnerEtaMin);
		
		JLabel lblEtMassima = new JLabel("Età Massima");
		lblEtMassima.setBounds(184, 120, 106, 15);
		panelReg.add(lblEtMassima);
		
		spinnerEtaMax = new JSpinner();
		spinnerEtaMax.setBounds(296, 118, 40, 20);
		panelReg.add(spinnerEtaMax);
			
		JLabel lblNewLabel_1 = new JLabel("Seleziona le categorie di tuo interesse");
		lblNewLabel_1.setBounds(23, 159, 313, 15);
		panelReg.add(lblNewLabel_1);
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(219, 296, 117, 25);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.previous(contentPane);
			}
		});
		panelReg.add(btnLogin);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(22, 186, 314, 98);
		scrollPane.setVisible(true);
		panelReg.add(scrollPane);
		
		DefaultListModel<String> listaModel = new DefaultListModel();
		for(int i=0; i<cat.length; i++) {
			listaModel.addElement(cat[i]);
		}
		list = new JList(listaModel);
		scrollPane.setViewportView(list);
		list.setVisible(true);
		
		btnConfermaReg = new JButton("Conferma");

		btnConfermaReg.setBounds(22, 296, 117, 25);
		panelReg.add(btnConfermaReg);	
		
		this.setVisible(true);
	}
	
	

	public void addActionListener(ActionListener al) {
		btnConfermaLog.addActionListener(al);
		btnConfermaLog.setActionCommand("Login");
		btnConfermaReg.addActionListener(al);
		btnConfermaReg.setActionCommand("Registrazione");
	}

	public String getUsernameLog() {
		return textUsernameLog.getText();
	}
	
	public String getUsernameReg() {
		return textUsernameReg.getText();
	}

	public String getPasswordLog() {
		return new String (passwordFieldLog.getPassword());
	}
	
	public String getPasswordReg() {
		return new String (passwordFieldReg.getPassword());
	}

	public String getConfermaPW() {
		return new String (passwordFieldConferma.getPassword());
	}

	public int getEtaMin() {
		return ((Integer) spinnerEtaMin.getValue() ).intValue();
	}

	public int getEtaMax() {
		return ((Integer) spinnerEtaMax.getValue() ).intValue();
	}

	public String[] getCategoriePref() {
		int lenght = list.getSelectedValuesList().size();
		String[] ritorno = new String[lenght];
		for(int i=0; i<lenght; i++) {
			ritorno[i]= (String) list.getSelectedValuesList().get(i);
		}
		return ritorno;
	}
	
	
	
}
