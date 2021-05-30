package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JAvviso extends JDialog implements MouseListener {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JLabel lblNewLabel;

	
	/**
	 * Create the dialog.
	 */
	public JAvviso(String error) {
		setBounds(100, 100, 450, 300);
		setTitle("Avviso");
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(50, 50, 30, 50));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			okButton = new JButton("OK");
			okButton.setPreferredSize(okButton.getMinimumSize());
			okButton.addMouseListener(this);
			contentPanel.setLayout(new GridLayout(3, 1, 0, 50));
			{
				lblNewLabel = new JLabel(error);
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				contentPanel.add(lblNewLabel);
			}
			contentPanel.add(okButton);
		}
		this.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.dispose();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
