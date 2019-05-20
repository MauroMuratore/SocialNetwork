package interfacciaUtente;

	
	import java.awt.EventQueue;

	import javax.swing.JFrame;
	import javax.swing.JTextField;
	import javax.swing.JLabel;
	import java.awt.Color;
	import javax.swing.JButton;
	import javax.swing.UIManager;

import com.sun.glass.ui.Window;
import com.sun.java.swing.plaf.windows.resources.windows;

import java.awt.SystemColor;
	import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

	public class UserInterface {

		public JFrame frame;
		private JTextField txtuser;
		private boolean isLog =false;
		private boolean isReg =false;
		String username;
		String pw;
		private JPasswordField passwordField;
		

		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						UserInterface window = new UserInterface();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		/**
		 * Create the application.
		 */
		public UserInterface() {
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
		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame("SocialNetwork M&M ");
			frame.setResizable(false);
			frame.getContentPane().setBackground(new Color(224, 255, 255));
			frame.setBounds(100, 100, 450, 248);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			
			JLabel lblLogin = new JLabel("SEZIONE LOGIN:");
			lblLogin.setOpaque(true);
			lblLogin.setBounds(10, 20, 95, 14);
			lblLogin.setBackground(SystemColor.infoText);
			lblLogin.setForeground(SystemColor.textHighlight);
			frame.getContentPane().add(lblLogin);
			
			JLabel lblUsername = new JLabel("username");
			lblUsername.setBounds(10, 45, 95, 14);
			frame.getContentPane().add(lblUsername);
			
			txtuser = new JTextField();
			txtuser.setBounds(10, 70, 95, 20);
			frame.getContentPane().add(txtuser);
			txtuser.setColumns(10);
			
			JLabel lblPassword = new JLabel("password");
			lblPassword.setBounds(11, 101, 80, 14);
			frame.getContentPane().add(lblPassword);
			
			JButton btnNewButton = new JButton("login");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isLog=true;
					username = txtuser.getText();
					pw = passwordField.getText();
					
				}
			});
			btnNewButton.setBounds(10, 163, 95, 23);
			btnNewButton.setBackground(SystemColor.desktop);
			frame.getContentPane().add(btnNewButton);
			
			JLabel lblNonTiSei = new JLabel("Non ti sei ancora registrato? ");
			lblNonTiSei.setBounds(188, 113, 184, 14);
			lblNonTiSei.setForeground(SystemColor.textHighlight);
			frame.getContentPane().add(lblNonTiSei);
			
			JLabel lblPassaAllaraAlla = new JLabel("passa allora alla sezione di registrazione");
			lblPassaAllaraAlla.setBounds(188, 138, 236, 14);
			lblPassaAllaraAlla.setForeground(SystemColor.textHighlight);
			frame.getContentPane().add(lblPassaAllaraAlla);
			
			JButton btnRegistrazione = new JButton("registrazione");
			btnRegistrazione.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					isReg=true;
					frame.setVisible(false);
					SezioneRegistrazione finestraReg = new SezioneRegistrazione();
					finestraReg.getFrame().setVisible(true);
				}
			});
			btnRegistrazione.setBounds(188, 163, 113, 23);
			btnRegistrazione.setBackground(SystemColor.desktop);
			frame.getContentPane().add(btnRegistrazione);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(10, 126, 95, 20);
			frame.getContentPane().add(passwordField);
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public boolean isLogin() {
		return false;
	}
	
	//potremmo fare che se non è login allora è registrazione e "risparmiamo" un metodo
	public boolean isRegistrazione() {
		return false;
	}

	public String getID() {
		return null;
	}

	public byte[] getPW() {
		return null;
	}

	public byte[] getConfermaPW() {
		return null;
	}

	public boolean riceviStringa(String risposta) {
		return false;
	}
}
