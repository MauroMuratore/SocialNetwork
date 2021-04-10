package interfacciaUtente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import server.core.SocialNetwork;


public class Login {

	private JFrame frame;
	private JTextField txtuser;
	private boolean isLog =false;
	private String username;
	private String pw;
	private boolean rispostaUI;
	private JPasswordField passwordField;
	private JTextPane textPane;
	private UserInterface userInt;
	
	public boolean isLog() {
		return isLog;
	}

	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public boolean isRispostaUI() {
		return rispostaUI;
	}

	public void setRispostaUI(boolean rispostaUI) {
		this.rispostaUI = rispostaUI;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JTextField getTxtuser() {
		return txtuser;
	}

	public void setTxtuser(JTextField txtuser) {
		this.txtuser = txtuser;
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JTextPane getTextPane() {
		return textPane;
	}

	public void setTextPane(JTextPane textPane) {
		this.textPane = textPane;
	}

	private boolean isLogOut=false;

	public Login(UserInterface userInt) {
		initialize();
	}
	public void setFalse(){
		isLog=false;
	}
	private void initialize() {
		frame = new JFrame("SocialNetwork M&M (login) ");
		frame.setMinimumSize(new Dimension(450, 285));
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(600, 300, 450, 283);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblLogin = new JLabel("SEZIONE LOGIN:");
		lblLogin.setBounds(10, 11, 95, 23);
		lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblLogin.setOpaque(true);
		lblLogin.setBackground(new Color(0, 255, 255));
		lblLogin.setForeground(SystemColor.textHighlight);
		frame.getContentPane().add(lblLogin);

		txtuser = new JTextField();
		txtuser.setBounds(10, 70, 95, 20);
		frame.getContentPane().add(txtuser);
		txtuser.setColumns(10);

		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(11, 101, 80, 14);
		frame.getContentPane().add(lblPassword);

		JButton btnNewButton = new JButton("login");
		btnNewButton.setBounds(10, 163, 95, 23);
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
			public void actionPerformed(ActionEvent e) {
				isLog=true;
				username = txtuser.getText();
				pw = passwordField.getText();

			}
		});
		btnNewButton.setBackground(SystemColor.desktop);
		frame.getContentPane().add(btnNewButton);

		JLabel lblNonTiSei = new JLabel("Non ti sei ancora registrato? ");
		lblNonTiSei.setBounds(198, 113, 184, 14);
		lblNonTiSei.setForeground(SystemColor.textHighlight);
		frame.getContentPane().add(lblNonTiSei);

		JLabel lblPassaAllaraAlla = new JLabel("passa allora alla sezione di registrazione");
		lblPassaAllaraAlla.setBounds(198, 138, 236, 14);
		lblPassaAllaraAlla.setForeground(SystemColor.textHighlight);
		frame.getContentPane().add(lblPassaAllaraAlla);

		JButton btnRegistrazione = new JButton("registrazione");
		btnRegistrazione.setBounds(198, 163, 113, 23);
		btnRegistrazione.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRegistrazione.setBackground(Color.green);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRegistrazione.setBackground(SystemColor.desktop);
			}
		});
		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				userInt.createFinestraReg();
//				finestraReg = new SezioneRegistrazione(SN);
//				finestraReg.getFrame().setVisible(true);
			}
		});
		btnRegistrazione.setBackground(SystemColor.desktop);
		frame.getContentPane().add(btnRegistrazione);

		passwordField = new JPasswordField();
		passwordField.setBounds(10, 126, 95, 20);
		frame.getContentPane().add(passwordField);

		textPane = new JTextPane();
		textPane.setBounds(10, 197, 414, 20);
		textPane.setForeground(new Color(255, 0, 0));
		textPane.setBackground(new Color(224, 255, 255));
		frame.getContentPane().add(textPane);	

		JLabel txtpnprogettoIngegneriaDel = new JLabel();
		txtpnprogettoIngegneriaDel.setBounds(0, 228, 424, 20);
		txtpnprogettoIngegneriaDel.setFont(new Font("Sitka Subheading", Font.PLAIN, 10));
		txtpnprogettoIngegneriaDel.setText("#Progetto Ingegneria del software (parte 1) 2018-2019");
		txtpnprogettoIngegneriaDel.setBackground(new Color(224, 255, 255));
		frame.getContentPane().add(txtpnprogettoIngegneriaDel);
		
		JLabel lblUsername = new JLabel("username");
		lblUsername.setBounds(10, 45, 80, 14);
		frame.getContentPane().add(lblUsername);
	
	}

}
