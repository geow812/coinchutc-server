package projet;

import jade.core.AID;

import java.awt.GridLayout;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.codehaus.jackson.map.ObjectMapper;

public class PartyWindow extends JFrame implements PropertyChangeListener{
	/*private JTextArea j0;
	private JTextArea j1;
	private JTextArea j2;
	private JTextArea j3;
	*/
	
	private JPanel p0;
	private JPanel p1;
	private JPanel p2;
	private JPanel p3;
	
	private JLabel j0;
	private JLabel j1;
	private JLabel j2;
	private JLabel j3;
	
	private JLabel i0;
	private JLabel i1;
	private JLabel i2;
	private JLabel i3;
	
	private Partie myAgent;

	public PartyWindow(Partie p, AID[] j)
	{
		myAgent = p;
		
		/*j0 = new JTextArea();
		j1 = new JTextArea();
		j2 = new JTextArea();
		j3 = new JTextArea();*/
		
		this.setTitle("Tapis");
		
		p0 = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		j0 = new JLabel();
		j1 = new JLabel();
		j2 = new JLabel();
		j3 = new JLabel();
		
		i0 = new JLabel();
		i1 = new JLabel();
		i2 = new JLabel();
		i3 = new JLabel();
		
		j0.setName(j[0].getLocalName());
		j1.setName(j[1].getLocalName());
		j2.setName(j[2].getLocalName());
		j3.setName(j[3].getLocalName());

		p0.add(i0);
		p0.add(j0);
		p1.add(i1);
		p1.add(j1);
		p2.add(i2);
		p2.add(j2);
		p3.add(i3);
		p3.add(j3);
		
		this.setLayout(new GridLayout(2,2));
		this.add(p0); 
		this.add(p1);
		this.add(p2);
		this.add(p3);
		this.pack();
		this.setVisible(true);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		System.out.println("je suis dans le partywindow");
		System.out.println(evt.getOldValue());
		if (evt.getPropertyName().equals("finTour"))
		{
			j0.setIcon(null);
			j1.setIcon(null);
			j2.setIcon(null);
			j3.setIcon(null);
		}
		
		else if (j0.getName().equals(evt.getOldValue().toString()))
		{
			String s = evt.getNewValue().toString(); // cha�e JSON
			ObjectMapper mapper = new ObjectMapper();
			try {
				Carte card = mapper.readValue(s, Carte.class);
				
				j0.setIcon(new ImageIcon(card.getImage()));
				ImageIcon source = new ImageIcon("res/drawable/"+evt.getOldValue().toString()+".jpg");
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				i0.setIcon(resultat);

				//j0.setText(card.getValeur()+ " "+card.getCouleur());
			}
			catch(Exception ex) {}
		}
		else if (j1.getName().equals(evt.getOldValue().toString()))
		{
			String s = evt.getNewValue().toString(); // cha�e JSON
			ObjectMapper mapper = new ObjectMapper();
			try {
				Carte card = mapper.readValue(s, Carte.class);
				j1.setIcon(new ImageIcon(card.getImage()));
				ImageIcon source = new ImageIcon("res/drawable/"+evt.getOldValue().toString()+".jpg");
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				i1.setIcon(resultat);
				//j1.setText(card.getValeur()+ " "+card.getCouleur());
			}
			catch(Exception ex) {}
		}
		else if (j2.getName().equals(evt.getOldValue().toString()))
		{
			String s = evt.getNewValue().toString(); // cha�e JSON
			ObjectMapper mapper = new ObjectMapper();
			try {
				Carte card = mapper.readValue(s, Carte.class);
				j2.setIcon(new ImageIcon(card.getImage()));
				ImageIcon source = new ImageIcon("res/drawable/"+evt.getOldValue().toString()+".jpg");
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				i2.setIcon(resultat);
				//j2.setText(card.getValeur()+ " "+card.getCouleur());
			}
			catch(Exception ex) {}
		}
		else if (j3.getName().equals(evt.getOldValue().toString()))
		{
			String s = evt.getNewValue().toString(); // cha�e JSON
			ObjectMapper mapper = new ObjectMapper();
			try {
				Carte card = mapper.readValue(s, Carte.class);
				j3.setIcon(new ImageIcon(card.getImage()));
				ImageIcon source = new ImageIcon("res/drawable/"+evt.getOldValue().toString()+".jpg");
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				i3.setIcon(resultat);
				//j3.setText(card.getValeur()+ " "+card.getCouleur());
			}
			catch(Exception ex) {}
		}
	}
}
