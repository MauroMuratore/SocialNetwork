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
import java.awt.CardLayout;
import java.awt.Font;
;

public class ViewBC extends JPanel {
	
	/**
	 * Create the panel.
	 */
	public ViewBC(List<Categoria> categorie) {
		setLayout(new CardLayout(0, 0));
		
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

		JTree tree = new JTree();
		tree.setBounds(0, 0, 438, 257);
		tree.setModel(model);
		tree.setRootVisible(false);
		tree.setFont(new Font(tree.getFont().getFontName(), Font.PLAIN, 24));
		JScrollPane scrollPane = new JScrollPane(tree);
		
		add(scrollPane);
		
		
		repaint();
		
		
	}
}
