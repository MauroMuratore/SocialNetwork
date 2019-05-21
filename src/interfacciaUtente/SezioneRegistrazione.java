package interfacciaUtente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;

public class SezioneRegistrazione {

	private JFrame frame;
	private JLabel lblSezione;
	private JLabel lblUsername;
	private JButton btnNewButton;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JLabel lblPassword;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblRipetiPassword;
	private JLabel lblNoteaDopoLa;
	private boolean isReg =false;
	private String username;
	private String password;
	private String ripetiPassword;
	private JTextPane textPane;
	private JTextPane textPane_1;

	public JTextPane getTextPane_1() {
		return textPane_1;
	}
	public void setTextPane_1(JTextPane textPane_1) {
		this.textPane_1 = textPane_1;
	}
	public void setFalse(){
		isReg=false;
	}
	public byte[] getConfermaPWREG(){
		byte[] conf = ripetiPassword.getBytes();
		return conf;	
	}
	public String getIDREG(){
		return username;
	}
	public byte[] getPWREG(){
		byte[] ps= password.getBytes();
		return ps;
	}
	public boolean isReg(){
		return isReg;
		
	}
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public SezioneRegistrazione() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame("SocialNetwork M&M (registrazione)");
		frame.setForeground(new Color(255, 0, 0));
		frame.setBackground(new Color(255, 0, 0));
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(600, 300, 560, 389);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblSezione = new JLabel("SEZIONE REGISTRAZIONE:");
		lblSezione.setForeground(SystemColor.textHighlight);
		lblSezione.setOpaque(true);
		lblSezione.setBackground(SystemColor.infoText);
		lblSezione.setBounds(20, 11, 187, 29);
		frame.getContentPane().add(lblSezione);
		
		lblUsername = new JLabel("username");
		lblUsername.setBounds(20, 51, 87, 14);
		frame.getContentPane().add(lblUsername);
		
		btnNewButton = new JButton("Registrati");
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
		btnNewButton.setBackground(SystemColor.desktop);
		btnNewButton.setForeground(SystemColor.infoText);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("isReg modifica");
				isReg=true;
				//if(isReg)System.out.println("isReg modifica");
				username=textField.getText();
				password=textField_1.getText();
				ripetiPassword=textField_2.getText();
				
			}
		});
		btnNewButton.setBounds(20, 264, 154, 23);
		frame.getContentPane().add(btnNewButton);
		
		lblNewLabel = new JLabel("(deve contenere almeno 7 caratteri)");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setBounds(20, 95, 276, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(21, 76, 153, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		lblPassword = new JLabel("password");
		lblPassword.setBounds(20, 120, 154, 14);
		frame.getContentPane().add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setBounds(21, 145, 153, 20);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(21, 215, 153, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		lblRipetiPassword = new JLabel("ripeti password");
		lblRipetiPassword.setBounds(20, 190, 153, 14);
		frame.getContentPane().add(lblRipetiPassword);
		
		lblNoteaDopoLa = new JLabel("(Nota: dopo la registrazione l'accesso al social network verr\u00E0 eseguito automaticamente)");
		lblNoteaDopoLa.setForeground(SystemColor.textHighlight);
		lblNoteaDopoLa.setBounds(20, 286, 524, 14);
		frame.getContentPane().add(lblNoteaDopoLa);
		
		textPane = new JTextPane();
		textPane.setBackground(new Color(224, 255, 255));
		textPane.setForeground(new Color(255, 0, 0));
		textPane.setBounds(20, 309, 499, 20);
		frame.getContentPane().add(textPane);
		
		JLabel label = new JLabel("(deve contenere almeno 7 caratteri)");
		label.setForeground(SystemColor.textHighlight);
		label.setBounds(20, 165, 276, 14);
		frame.getContentPane().add(label);
		
		textPane_1 = new JTextPane();
		textPane_1.setBackground(new Color(224, 255, 255));
		textPane_1.setForeground(new Color(255, 0, 0));
		textPane_1.setBounds(20, 309, 499, 20);
		frame.getContentPane().add(textPane_1);
	}
}
