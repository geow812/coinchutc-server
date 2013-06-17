package projet;

import jade.gui.GuiEvent;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class LogWindow extends JFrame{
	private JTextArea login;
	private JTextArea mdp;
	private JButton conn;
	private LogAgent myAgent;
	
	private JButton creer;
	private JTextArea nom;
	private JTextArea login2;
	private JTextArea mdp2;
	private JButton valider;
	
	public LogWindow(LogAgent log){
		myAgent = log;
		login = new JTextArea("Entrez votre login...");
		mdp = new JTextArea("Entrez votre mot de passe...");
		
		nom = new JTextArea("Entrez votre nom...");
		login2 = new JTextArea("Entrez un login...");
		mdp2 = new JTextArea("Entrez un mot de passe...");
		
		conn = new JButton("Connexion");
		creer = new JButton("Cr¨¦er compte");
		valider = new JButton("Valider");
		
		nom.setEnabled(false);
		login2.setEnabled(false);
		mdp2.setEnabled(false);
		valider.setEnabled(false);
		
		conn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GuiEvent ev = new GuiEvent(this,LogAgent.LOG_EVENT);
				ev.addParameter(login.getText());
				ev.addParameter(mdp.getText());
				myAgent.postGuiEvent(ev);
			}
			
		});
		
		creer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				nom.setEnabled(true);
				login2.setEnabled(true);
				mdp2.setEnabled(true);
				valider.setEnabled(true);
			}
			
		});
		
		valider.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GuiEvent ev = new GuiEvent(this,LogAgent.CREER_EVENT);
				ev.addParameter(nom.getText());
				ev.addParameter(login2.getText());
				ev.addParameter(mdp2.getText());
				myAgent.postGuiEvent(ev);
			}
			
		});
		
		this.setLayout(new GridLayout(8,1));
		this.add(login);
		this.add(mdp);
		this.add(conn);
		this.add(creer);
		this.add(nom);
		this.add(login2);
		this.add(mdp2);
		this.add(valider);
		this.pack();
		this.setVisible(true);
	}
}

