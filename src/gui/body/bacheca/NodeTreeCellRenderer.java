package gui.body.bacheca;

import java.awt.Component;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import lib.util.Nomi;

public class NodeTreeCellRenderer implements TreeCellRenderer  {

	private JLabel label;
	private File football;
	private File mountain;
	private File event;
	private File newicon;
	
	public NodeTreeCellRenderer() {
		label = new JLabel();
		String os = System.getProperty("os.name");
		
		if(os.startsWith("Windows")){
			football = new File(Nomi.FILE_ICON_FOOTBALL_WIN.getNome());
			if(!football.exists()) {
				football = new File("..\\" + Nomi.FILE_ICON_FOOTBALL_WIN.getNome());
				mountain = new File("..\\" + Nomi.FILE_ICON_MOUNTAIN_WIN.getNome());
				event = new File("..\\" + Nomi.FILE_ICON_EVENT_WIN.getNome());
				newicon = new File("..\\" + Nomi.FILE_ICON_NEW_WIN.getNome());
			}
			else {
				mountain = new File( Nomi.FILE_ICON_MOUNTAIN_WIN.getNome());
				event = new File(Nomi.FILE_ICON_EVENT_WIN.getNome());
				newicon = new File( Nomi.FILE_ICON_NEW_WIN.getNome());
			}
		}
		else {
			football = new File(Nomi.FILE_ICON_FOOTBALL.getNome());
			if(!football.exists()) {
				football = new File("../" + Nomi.FILE_ICON_FOOTBALL.getNome());
				mountain = new File("../" + Nomi.FILE_ICON_MOUNTAIN.getNome());
				event = new File("../" + Nomi.FILE_ICON_EVENT.getNome());
				newicon = new File("../" + Nomi.FILE_ICON_NEW.getNome());
			}
			else {
				mountain = new File( Nomi.FILE_ICON_MOUNTAIN.getNome());
				event = new File(Nomi.FILE_ICON_EVENT.getNome());
				newicon = new File( Nomi.FILE_ICON_NEW.getNome());
			}
		}
	}
	
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object nodo, boolean selected,
			boolean expanded, boolean leaf,	int row, boolean focus) {
				
		if(((DefaultMutableTreeNode)nodo).getUserObject().equals(Nomi.CAT_PARTITA_CALCIO.getNome())) {
			label.setIcon(new ImageIcon(football.getPath()));
			label.setText(Nomi.CAT_PARTITA_CALCIO.getNome());
		}
		else if(((DefaultMutableTreeNode)nodo).getUserObject().equals(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome())) {
			label.setIcon(new ImageIcon(mountain.getPath()));
			label.setText(Nomi.CAT_ESCURSIOME_MONTAGNA.getNome());
		}
		else if(((DefaultMutableTreeNode)nodo).getUserObject().equals(Nomi.AZIONE_CREA_EVENTO.getNome())) {
			label.setIcon(new ImageIcon(newicon.getPath()));
			label.setText(Nomi.AZIONE_CREA_EVENTO.getNome());
		}
		else {
			label.setIcon(new ImageIcon(event.getPath()));
			label.setText((String)((DefaultMutableTreeNode)nodo).getUserObject());
		}
		
		
		
		
		return label;
	}
	
	

}
