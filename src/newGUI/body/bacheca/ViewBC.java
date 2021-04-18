package newGUI.body.bacheca;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lib.core.Categoria;
import lib.core.Evento;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import java.awt.BorderLayout;
;

public class ViewBC extends JPanel {

	private JTree tree;
	private JButton btnAvanti;
	private CardLayout layout;
	private JPanel panelEsterno;

	/**
	 * Create the panel.
	 */
	public ViewBC(List<Categoria> categorie) {
		layout = new CardLayout(0, 0);
		setLayout(layout);

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);

		for(Categoria cat: categorie) {
			DefaultMutableTreeNode catNodo = new DefaultMutableTreeNode (cat.getNome());

			for(Evento evento: (List<Evento>) cat.getBacheca()) {
				catNodo.add(new DefaultMutableTreeNode(evento.getTitolo().getNome()));
			}

			DefaultMutableTreeNode nuovoEventoNodo = new DefaultMutableTreeNode("Crea Evento");
			catNodo.add(nuovoEventoNodo);

			rootNode.add(catNodo);
		}

		JPanel panelCategorie = new JPanel();
		add(panelCategorie, "name_11595670272846");
		panelCategorie.setLayout(new BorderLayout(0, 0));

		tree = new JTree();
		tree.setBounds(0, 0, 438, 257);
		tree.setModel(model);
		tree.setRootVisible(false);
		tree.setFont(new Font(tree.getFont().getFontName(), Font.PLAIN, 24));
		JScrollPane scrollPane = new JScrollPane(tree);
		panelCategorie.add(scrollPane);

		btnAvanti = new JButton("Avanti");
		panelCategorie.add(btnAvanti, BorderLayout.SOUTH);
		btnAvanti.setPreferredSize(getMinimumSize());
		
		repaint();
	}

	public TreePath getSelectionPath() {
		return tree.getSelectionPath();
	}
	
	public void addActionListener(ActionListener al) {
		btnAvanti.addActionListener(al);
		btnAvanti.setActionCommand("Avanti");
	}
	
	public void addPanel(JPanel panel) {
		panelEsterno = panel;
		add(panelEsterno);
		layout.next(this);
	}
	
	public void removePanelEsterno() {
		remove(panelEsterno);
	}
}
