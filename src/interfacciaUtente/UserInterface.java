package interfacciaUtente;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.UIManager;

import cervello.SocialNetwork;
import database.ConsultaDB;

import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import java.awt.Dimension;

public class UserInterface {

	private JFrame frame;
	public static final String BENVENUTO = "BENVENUTO";
	public static final String ID_INESISTENTE= "ATTENZIONE! : username inesistente";
	public static final String PW_SBAGLIATA = "ATTENZIONE! : password sbalgiata";
	public static final String ID_IN_USO = "ATTENZIONE! : username in uso";
	public static final String PW_DIVERSE = "ATTENZIONE! : password diverse";
	public static final String PW_CORTA ="ATTENZIONE! : passoword troppo corta";
	private JTextField txtuser;
	private boolean isLog =false;
	private String username;
	private String pw;
	private boolean rispostaUI;
	private SezioneRegistrazione finestraReg;
	private JPasswordField passwordField;
	private JTextPane textPane;
	private FinestraMenu finestraMenu;
	private SocialNetwork SN;

	public void sezioneMenu(){
		
		frame.setVisible(false);
		finestraMenu= new FinestraMenu(SN);
		finestraMenu.getFrame().setVisible(true);
		
		
	}
	public void setFalse(){
		isLog=false;
		if(finestraReg!= null)
			finestraReg.setFalse();
	}
	public byte[] getConfermaPWREG(){
		return finestraReg.getConfermaPWREG();
	}
	public byte[] getPWREG(){
		return finestraReg.getPWREG();
	}
	public String getIDREG(){
		return finestraReg.getIDREG();
	}
	public boolean isReg(){
		if(finestraReg!= null)
			return finestraReg.isReg();
		else return false;
	}
	public UserInterface(SocialNetwork sn) {
		SN=sn;
		initialize();
	}
	public boolean isLog()
	{
		return isLog;	
	}
	public String getUS(){
		return username;
	}
	public byte[] getPASS(){
		byte[] pass= pw.getBytes();
		return pass;
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public boolean isRegistrazione(){
		if(finestraReg!=null) 
		{		
			return finestraReg.isReg();
		}

		else return false;
	}
	public boolean riceviStringa(String rispostaSN){
			switch(rispostaSN)
			{
			case BENVENUTO : 
				rispostaUI=true;
				textPane.setForeground(Color.GREEN);
				textPane.setText(BENVENUTO+" "+username);
				return rispostaUI;
			case ID_INESISTENTE :
				rispostaUI=false;
				textPane.setText(ID_INESISTENTE);
				return rispostaUI;
			case PW_SBAGLIATA : 
				rispostaUI=false;
				textPane.setText(PW_SBAGLIATA);
				return rispostaUI;
			case ID_IN_USO : 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(ID_IN_USO);
				return rispostaUI;
			case PW_DIVERSE: 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(PW_DIVERSE);
				return rispostaUI;
			case PW_CORTA: 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(PW_CORTA);
				return rispostaUI;
			default : 
				rispostaUI=false;
				return rispostaUI;	
			}	
		}	

	private void initialize(){
			
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
					finestraReg = new SezioneRegistrazione();
					finestraReg.getFrame().setVisible(true);
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

			JTextPane txtpnprogettoIngegneriaDel = new JTextPane();
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

