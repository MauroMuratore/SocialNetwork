package interfacciaUtente;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.UIManager;

import server.core.SocialNetwork;
import server.database.ConsultaDB;
import util.Nomi;

import java.awt.SystemColor;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import java.awt.Dimension;

public class UserInterface {

	private boolean rispostaUI;
	private SezioneRegistrazione finestraReg;
	private FinestraMenu finestraMenu;
	private SocialNetwork SN;
	private Login finestraLog;
	private boolean isLogOut=false;
	
	public void logOut(){
		setFalse();
		SN.logout();
		rispostaUI=false;
		isLogOut=true;
	}

	public boolean isLogOut() {
		return isLogOut;
	}

	public void setLogOut(boolean isLogOut) {
		this.isLogOut = isLogOut;
	}

	public void sezioneMenu(){
				
		finestraLog.getFrame().setVisible(false);
		if(finestraReg!=null)finestraReg.getFrame().dispose();
		finestraMenu= new FinestraMenu(this);
		finestraMenu.getFrame().setVisible(true);
		
		
	}
	public void setFalse(){
		if(finestraLog!= null)
		finestraLog.setFalse();
		
		if(finestraReg!= null)
			finestraReg.setFalse();
		
	}
	public byte[] getConfermaPWREG(){
		return finestraReg.getConfermaPWREG();
	}
	public byte[] getPWREG(){
		return finestraReg.getPWREG();
	}
	public String getIDREG(){
		return finestraReg.getIDREG();
	}
	public boolean isReg(){
		if(finestraReg!= null)
			return finestraReg.isReg();
		else return false;
	}
	public UserInterface() {
		SN= SocialNetwork.getInstance();
		initialize();
	}
	public boolean isLog()
	{
		return finestraLog.isLog();	
	}
	public String getUS(){
		return finestraLog.getUsername();
	}
	public byte[] getPASS(){
		byte[] pass= finestraLog.getPw().getBytes();
		return pass;
	}
	public JFrame getFrame() {
		return finestraLog.getFrame();
	}

	public boolean isRegistrazione(){
		if(finestraReg!=null) 
		{		
			return finestraReg.isReg();
		}

		else return false;
	}
	public boolean riceviStringa(String rispostaSN){
			/*switch(rispostaSN)
			{
			case Nomi.SN_BENVENUTO.getNome() : 
				rispostaUI=true;
				finestraLog.getTextPane().setText("");
				return rispostaUI;
			case Nomi.SN_ID_INESISTENTE.getNome() :
				rispostaUI=false;
				finestraLog.getTextPane().setText(Nomi.SN_ID_INESISTENTE.getNome());
				return rispostaUI;
			case Nomi.SN_PW_SBAGLIATA.getNome() : 
				rispostaUI=false;
				finestraLog.getTextPane().setText(Nomi.SN_PW_SBAGLIATA.getNome());
				return rispostaUI;
			case Nomi.SN_ID_IN_USO.getNome() : 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(Nomi.SN_ID_IN_USO.getNome());
				return rispostaUI;
			case Nomi.SN_PW_DIVERSE.getNome(): 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(Nomi.SN_PW_DIVERSE.getNome());
				return rispostaUI;
			case Nomi.SN_PW_CORTA.getNome(): 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(Nomi.SN_PW_CORTA.getNome());
				return rispostaUI;
			case Nomi.SN_ID_CORTO.getNome(): 
				rispostaUI=false;
				finestraReg.getTextPane_1().setText(Nomi.SN_ID_CORTO.getNome());
				return rispostaUI;
			default : 
				rispostaUI=false;
				return rispostaUI;	
			}*/
		return false;
		}	

	private void initialize(){	
		finestraLog = new Login(this);
		finestraLog.getFrame().setVisible(true);
		}
	public String getMin(){
		return finestraReg.getMin();
	}
	public String getMax(){
		return finestraReg.getMax();
	}
	public String[] getCategoriePreferite(){
		return finestraReg.getCategoriePreferite();
	}
	public void createFinestraReg(){
		finestraReg = new SezioneRegistrazione();
		finestraReg.getFrame().setVisible(true);
	}
	}

