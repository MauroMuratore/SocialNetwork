package newGUI.body.bacheca;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lib.core.Categoria;
import lib.core.Evento;
import lib.core.StatoEvento;
import lib.util.Nomi;

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
	private JPanel panelCategorie;
	/**
	 * Create the panel.
	 */
	public ViewBC(List<Categoria> cat, ActionListener son) {
		layout = new CardLayout(0, 0);
		setLayout(layout);
		panelCategorie = new JPanel();
		tree = new JTree();
		tree.setBounds(0, 0, 438, 257);
		setCategorie(cat);
		JScrollPane scrollPane = new JScrollPane(tree);
		panelCategorie.add(scrollPane);

		btnAvanti = new JButton("Avanti");
		panelCategorie.add(btnAvanti, BorderLayout.SOUTH);
		btnAvanti.setPreferredSize(getMinimumSize());
		btnAvanti.addActionListener(son);
		btnAvanti.setActionCommand("Avanti");

		repaint();
	}

	public TreePath getSelectionPath() {
		return tree.getSelectionPath();
	}


	public void addPanel(JPanel panel) {
		panelEsterno = panel;
		add(panelEsterno);
		layout.next(this);
		repaint();
	}

	public void removePanelEsterno() {
		layout.first(this);
		remove(panelEsterno);
	}

	public void setCategorie(List<Categoria> categorie) {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
		DefaultTreeModel model = new DefaultTreeModel(rootNode);
		for(Categoria cat: categorie) {
			
			DefaultMutableTreeNode catNodo = new DefaultMutableTreeNode (cat.getNome());

			for(Evento evento: (List<Evento>) cat.getBacheca()) {
				if(!evento.getStato().equals(StatoEvento.CANCELLATO) && !evento.getStato().equals(StatoEvento.CONCLUSO))
						catNodo.add(new DefaultMutableTreeNode(evento.getTitolo().getValore()));
			}

			DefaultMutableTreeNode nuovoEventoNodo = new DefaultMutableTreeNode(Nomi.AZIONE_CREA_EVENTO.getNome());
			catNodo.add(nuovoEventoNodo);

			rootNode.add(catNodo);
		}

		add(panelCategorie, "name_11595670272846");
		panelCategorie.setLayout(new BorderLayout(0, 0));

		tree.setModel(model);
		tree.setRootVisible(false);
		tree.setCellRenderer(new NodeTreeCellRenderer());
		tree.setFont(new Font(tree.getFont().getFontName(), Font.PLAIN, 24));
		
		revalidate();
		repaint();

	}
}
