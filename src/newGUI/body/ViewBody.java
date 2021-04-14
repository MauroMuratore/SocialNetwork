package newGUI.body;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import newGUI.body.bacheca.ViewBC;
import java.awt.CardLayout;
import javax.swing.JTree;
import javax.swing.LayoutFocusTraversalPolicy;
import javax.swing.tree.DefaultTreeModel;

import lib.core.Categoria;
import lib.core.Evento;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ViewBody extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public ViewBody(List<Categoria> categorie) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("UNIBS Social Network");
		setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 30));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel panelBC = new JPanel();
		contentPane.add(panelBC, "name_3362780215051");
		panelBC.setLayout(new BorderLayout(15, 15));
		
		JLabel labelBC = new JLabel("Elenco delle categorie e degli eventi");
		labelBC.setFont(new Font(labelBC.getFont().getFontName(), Font.PLAIN, 32));
		panelBC.add(labelBC, BorderLayout.NORTH);
		
		JButton btnAreaPersonale = new JButton("Area Personale");
		JPanel panelBtnAP = new JPanel();
		panelBtnAP.setLayout(new FlowLayout());
		panelBtnAP.add(btnAreaPersonale);
		btnAreaPersonale.setPreferredSize(btnAreaPersonale.getMinimumSize());
		panelBC.add(panelBtnAP, BorderLayout.WEST);
		
		ViewBC viewBC = new ViewBC(categorie);
		panelBC.add(viewBC, BorderLayout.CENTER);
		
		setVisible(true);
		repaint();

	}
}
