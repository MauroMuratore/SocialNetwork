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

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					SezioneRegistrazione window = new SezioneRegistrazione();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public SezioneRegistrazione() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setBounds(100, 100, 535, 369);
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
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(20, 243, 154, 23);
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
		textField_2.setBounds(20, 201, 153, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		lblRipetiPassword = new JLabel("ripeti password");
		lblRipetiPassword.setBounds(20, 176, 153, 14);
		frame.getContentPane().add(lblRipetiPassword);
		
		lblNoteaDopoLa = new JLabel("Nota: dopo la registrazione l'accesso al social network verr\u00E0 eseguito automaticamente");
		lblNoteaDopoLa.setForeground(SystemColor.textHighlight);
		lblNoteaDopoLa.setBounds(20, 267, 499, 14);
		frame.getContentPane().add(lblNoteaDopoLa);
	}

}
