package interfacciaUtente;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.SystemColor;

epublic class SezioneCategorie {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SezioneCategorie window = new SezioneCategorie();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SezioneCategorie() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(new Color(255, 0, 0));
		frame.getContentPane().setBackground(new Color(30, 144, 255));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
		
		JTextPane txtpnSezioneCategorie = new JTextPane();
		txtpnSezioneCategorie.setForeground(new Color(255, 0, 0));
		txtpnSezioneCategorie.setBackground(new Color(127, 255, 212));
		txtpnSezioneCategorie.setText("SEZIONE CATEGORIE:");
		frame.getContentPane().add(txtpnSezioneCategorie);
		
		JButton btnPartitedicalcio = new JButton("PartiteDiCalcio");
		btnPartitedicalcio.setBackground(SystemColor.desktop);
		frame.getContentPane().add(btnPartitedicalcio);
	}
}
