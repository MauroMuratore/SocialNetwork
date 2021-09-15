package gui.ipconnect;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.JAvviso;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.awt.FlowLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;

public class ViewIPConnect extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnConferma;

	/**
	 * Create the frame.
	 */
	public ViewIPConnect() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("InetAddress");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Inserire indirizzo IP del server:");
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 100));
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(15);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		
		btnConferma = new JButton("Invio");
		panel_1.add(btnConferma);
		
		this.setVisible(true);
	}

	public String getIPAddress() {
		return textField.getText();
	}
	
	public boolean isEmpty() {
		boolean empty = textField.getText().isEmpty();
		if(empty) {
			new JAvviso("indirizzo mancante");
		}
		return empty;
	}
	
	public void addMouseListener(MouseListener ml) {
		btnConferma.addMouseListener(ml);
	}
	
	public void incorrectInet() {
		new JAvviso("indirizzo ip scorretto");
	}

}
