package newGUI.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class ViewLogin extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordFieldLog;
	private JTextField textUsername;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewLogin frame = new ViewLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewLogin() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 462, 386);
		setResizable(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel panelLog = new JPanel();
		panelLog.setBorder(new EmptyBorder(10, 10, 10, 50));
		contentPane.add(panelLog, "name_9283346758503");
		panelLog.setLayout(null);
		
		JLabel lblUsernameLog = new JLabel("Username");
		lblUsernameLog.setBounds(10, 86, 72, 15);
		panelLog.add(lblUsernameLog);
		
		textUsername = new JTextField();
		textUsername.setBounds(100, 84, 280, 19);
		panelLog.add(textUsername);
		textUsername.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 156, 72, 15);
		lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
		panelLog.add(lblPassword);
		
		passwordFieldLog = new JPasswordField();
		passwordFieldLog.setBounds(100, 154, 280, 19);
		panelLog.add(passwordFieldLog);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 183, 342, 25);
		panel.setBorder(new EmptyBorder(0, 15, 0, 15));
		panelLog.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 15, 0, 15));
		panel.add(panel_1);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConferma.setBounds(56, 246, 104, 25);
		panelLog.add(btnConferma);
		
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.setBounds(265, 246, 104, 25);
		panelLog.add(btnRegistrati);
		
		JPanel panelReg = new JPanel();
		panelReg.setBorder(new EmptyBorder(0, 20, 0, 0));
		contentPane.add(panelReg, "name_10834180881615");
		panelReg.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(12, 22, 88, 15);
		panelReg.add(lblUsername);
		
		textField = new JTextField();
		textField.setBounds(109, 20, 216, 19);
		panelReg.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setBounds(12, 49, 70, 15);
		panelReg.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setText("");
		passwordField.setBounds(109, 47, 216, 19);
		panelReg.add(passwordField);
		
		JLabel lblConferma = new JLabel("Conferma");
		lblConferma.setBounds(12, 76, 70, 15);
		panelReg.add(lblConferma);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(109, 74, 216, 19);
		panelReg.add(passwordField_1);
		
		JLabel lblEtaMinima = new JLabel("Eta Minima");
		lblEtaMinima.setBounds(12, 120, 88, 15);
		panelReg.add(lblEtaMinima);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(109, 118, 40, 20);
		panelReg.add(spinner);
		
		JLabel lblEtMassima = new JLabel("Età Massima");
		lblEtMassima.setBounds(184, 120, 106, 15);
		panelReg.add(lblEtMassima);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(296, 118, 40, 20);
		panelReg.add(spinner_1);
		
		JButton btnConferma_1 = new JButton("Conferma");
		btnConferma_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnConferma_1.setBounds(23, 296, 117, 25);
		panelReg.add(btnConferma_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(214, 296, 117, 25);
		panelReg.add(btnLogin);
		

		Vector<String> listaParametri = new Vector<String>();
		listaParametri.add("cosa1");
		listaParametri.add("cosa2");
		listaParametri.add("cosa3");
		listaParametri.add("cosa4");
		
		JList list = new JList<String>(listaParametri);
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(40, 262, 262, -80);
		panelReg.add(scrollPane);
		
	}
}
