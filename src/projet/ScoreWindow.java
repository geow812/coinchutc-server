package projet;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import jade.core.AID;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ScoreWindow extends JFrame implements PropertyChangeListener {
	
	private JPanel pane1;
	private JPanel pane2;
	private JLabel lab1;
	private JLabel lab2;
	private JLabel lab3;
	private JLabel lab4;
	//private JLabel lab5;
	//private JLabel lab6;
	private JTextPane sc1;
	private JTextPane sc2;
	
	public ScoreWindow(AID[] eq1, AID[] eq2) 
	{
		sc1 = new JTextPane();
		sc2 = new JTextPane();
		sc1.setDisabledTextColor(Color.DARK_GRAY);
		sc2.setDisabledTextColor(Color.DARK_GRAY);
		sc1.setOpaque(true);
		sc2.setOpaque(true);
		
		Font font = new Font("arial", Font.PLAIN, 48);
		Font font2 = new Font("arial", Font.PLAIN, 48);
		sc1.setFont(font);
		sc2.setFont(font2);
		
		pane1 = new JPanel();
		pane2 = new JPanel();
		
		ImageIcon source = new ImageIcon("res/drawable/"+eq1[0].getLocalName()+".jpg");
		ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		JLabel lab1 = new JLabel(resultat);
		
		 source = new ImageIcon("res/drawable/"+eq1[1].getLocalName()+".jpg");
		 resultat = new ImageIcon(source.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		 JLabel lab2 = new JLabel(resultat);
		 
		 source = new ImageIcon("res/drawable/"+eq2[0].getLocalName()+".jpg");
		 resultat = new ImageIcon(source.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		 JLabel lab3 = new JLabel(resultat);
		 
		 source = new ImageIcon("res/drawable/"+eq2[1].getLocalName()+".jpg");
		 resultat = new ImageIcon(source.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
		 JLabel lab4 = new JLabel(resultat);
		
		 JLabel lab5 = new JLabel();
		 lab5.setText("Score �uipe 1:");
		 
		 JLabel lab6 = new JLabel();
		 lab6.setText("Score �uipe 2:");
		 
		 pane1.setLayout(new GridLayout(2,2));
		 pane2.setLayout(new GridLayout(2,2));
		 
		 pane1.add(lab1);
		 pane1.add(lab2);
		 pane1.add(lab5);
		 pane1.add(sc1);
		 
		 pane1.setBorder(BorderFactory.createLineBorder(Color.black));
		 pane2.setBorder(BorderFactory.createLineBorder(Color.black));
		 
		 pane2.add(lab3);
		 pane2.add(lab4);
		 pane2.add(lab6);
		 pane2.add(sc2);
		 
		 this.setLayout(new GridLayout(1,2));
		 this.add(pane1);
		 this.add(pane2);
		 this.pack();
		 this.setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("je suis dans le PCL :"+arg0.getPropertyName()+" "+arg0.getNewValue()+" "+arg0.getOldValue());
		sc1.setText(arg0.getNewValue().toString());
		sc2.setText(arg0.getOldValue().toString());
	}
	
}
