package gui.body;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import javax.swing.JTree;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.tree.DefaultTreeModel;

import gui.body.areaPersonale.ViewAP;
import gui.body.bacheca.ViewBC;
import lib.core.categorie.Categoria;
import lib.core.eventi.Evento;
import lib.util.Nomi;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ViewBody extends JFrame {

	private JPanel contentPane;
	private CardLayout cl_panelViste;
	private JPanel panelViste;
	private JPanel paneVuoto = new JPanel();


	/**
	 * Create the frame.
	 */
	public ViewBody(ViewBC viewBC, ViewAP viewAP, ActionListener al) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("UNIBS Social Network");
		setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 30));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panelButton = new JPanel();
		contentPane.add(panelButton);
		panelButton.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.Y_AXIS));
		
		JButton btnAreaPersonale = new JButton("Area Personale");
		panelButton.add(btnAreaPersonale);
		btnAreaPersonale.addActionListener(al);
		btnAreaPersonale.setActionCommand("Area Personale");
		
		panelButton.add(Box.createVerticalStrut(5));
		
		JButton btnBacheca = new JButton("Vista Bacheca");
		panelButton.add(btnBacheca);
		btnBacheca.addActionListener(al);
		btnBacheca.setActionCommand("Vista Bacheca");
		
		panelButton.add(Box.createVerticalGlue());
		
		JButton btnLogout = new JButton(Nomi.AZIONE_LOGOUT.getNome());
		btnLogout.addActionListener(al);
		btnLogout.setActionCommand(Nomi.AZIONE_LOGOUT.getNome());
		panelButton.add(btnLogout);
		
		
		panelViste = new JPanel();
		contentPane.add(panelViste);
		cl_panelViste = new CardLayout(0, 0);
		panelViste.setLayout(cl_panelViste);
		panelViste.add(paneVuoto);
		
		JPanel panelBC = new JPanel();
		panelViste.add(panelBC, "bacheca");
		panelBC.setLayout(new BorderLayout(15, 15));
		
		JLabel labelBC = new JLabel("Elenco delle categorie e degli eventi");
		labelBC.setFont(new Font(labelBC.getFont().getFontName(), Font.PLAIN, 32));
		panelBC.add(labelBC, BorderLayout.NORTH);
		
		panelBC.add(viewBC, BorderLayout.CENTER);
		
		
		JPanel panelAP = new JPanel();
		panelViste.add(panelAP, "areaPersonale");
		panelAP.setLayout(new BorderLayout(15,15));
		
		JLabel labelAP = new JLabel("Area Personale");
		labelAP.setFont(new Font(labelBC.getFont().getFontName(), Font.PLAIN, 32));
		panelAP.add(labelAP, BorderLayout.NORTH);
		
		panelAP.add(viewAP, BorderLayout.CENTER);
		
		
		cl_panelViste.first(panelViste);
		
		setVisible(true);
		repaint();

	}
	
	public void showView(int i) {
		String view = null;
		switch(i) {
		case 0: view = "bacheca";
		break;
		case 1: view = "areaPersonale";
		break;
		}
		cl_panelViste.show(panelViste, view);
		
		revalidate();
		repaint();
	}
	
	public void updateGUI() {
		revalidate();
		repaint();
	}
}
