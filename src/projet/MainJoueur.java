package projet;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.TransferHandler;

public class MainJoueur extends JPanel{
	//private ArrayList<Carte> main;
	private Carte[] main;
	private JLabel c0;
	private JLabel c1;
	private JLabel c2;
	private JLabel c3;
	private JLabel c4;
	private JLabel c5;
	private JLabel c6;
	private JLabel c7;
	private int ind;
	private boolean ok=false;
	private Carte carteEnCours;
	private MainWindow mw;
	private PropertyChangeSupport changes;
	
	public MainJoueur(){
		
		//mw=m;
		
		//changes = new PropertyChangeSupport(mw);
		//changes.addPropertyChangeListener(mw);
		
		main = new Carte[8];
		ind=0;
		c0 = new JLabel();
		c1 = new JLabel();
		c2 = new JLabel();
		c3 = new JLabel();
		c4 = new JLabel();
		c5 = new JLabel();
		c6 = new JLabel();
		c7 = new JLabel();

		c0.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(0).getCouleur()+" "+main.get(0).getValeur());
					//getCarte(main.get(0));
					//main.remove(0);
					setCarteEnCours(main[0]);
					changes.firePropertyChange("jeuCarte", main[0], main[0]);
					main[0]=null;
					c0.setIcon(null);
				}
			}
		});
		c1.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(1).getCouleur()+" "+main.get(1).getValeur());  
					//getCarte(main.get(1));
					//main.remove(1);
					setCarteEnCours(main[1]);
					main[1]=null;
					c1.setIcon(null);
				}
			}
		});
		c2.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(2).getCouleur()+" "+main.get(2).getValeur());  
					//getCarte(main.get(2));
					//main.remove(2);
					setCarteEnCours(main[2]);
					main[2]=null;
					c2.setIcon(null);
				}
			}
		});
		c3.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(3).getCouleur()+" "+main.get(3).getValeur());  
					//getCarte(main.get(3));
					//main.remove(3);
					setCarteEnCours(main[3]);
					main[3]=null;
					c3.setIcon(null);
				}
			}
		});
		c4.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(4).getCouleur()+" "+main.get(4).getValeur());  
					//getCarte(main.get(4));
					//main.remove(4);
					setCarteEnCours(main[4]);
					main[4]=null;
					c4.setIcon(null);
				}
			}
		});
		c5.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(5).getCouleur()+" "+main.get(5).getValeur());  
					//getCarte(main.get(5));
					//main.remove(5);
					setCarteEnCours(main[5]);
					main[5]=null;
					c5.setIcon(null);
				}
			}
		});
		c6.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(6).getCouleur()+" "+main.get(6).getValeur());  
					//getCarte(main.get(6));
					//main.remove(6);
					setCarteEnCours(main[6]);
					main[6]=null;
					c6.setIcon(null);
				}
			}
		});
		c7.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					//System.out.println("carte :"+main.get(7).getCouleur()+" "+main.get(7).getValeur());  
					//getCarte(main.get(7));
					//main.remove(7);
					setCarteEnCours(main[7]);
					main[7]=null;
					c7.setIcon(null);
				}
			}
		});
		
		this.add(c0);
		this.add(c1);
		this.add(c2);
		this.add(c3);
		this.add(c4);
		this.add(c5);
		this.add(c6);
		this.add(c7);
		this.setSize(200, 200);
		this.setLayout(new GridLayout(2,4));
		this.setVisible(true);
	}

	public void getCarte(Carte card)
	{
		if (ok==true)
		{

		}
	}
	
	public boolean getOk()
	{
		return ok;
	}

	public void setOk(boolean b)
	{
		ok=b;
	}
	
	public void setCarteEnCours(Carte card)
	{
		carteEnCours = card;
	}

	public Carte getCarteEnCours()
	{
		return carteEnCours;
	}
	
	public void setCarte(Carte card)
	{
		main[ind]=card;
		switch(ind)
		{
		case 0: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c0.setIcon(resultat);

			break;
		}
		case 1: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c1.setIcon(resultat);

			break;
		}
		case 2: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c2.setIcon(resultat);

			break;
		}
		case 3: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c3.setIcon(resultat);

			break;
		}
		case 4: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c4.setIcon(resultat);

			break;
		}
		case 5: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c5.setIcon(resultat);

			break;
		}
		case 6: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c6.setIcon(resultat);

			break;
		}
		case 7: {
			ImageIcon source = new ImageIcon(card.getImage());
			ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
			c7.setIcon(resultat);

			break;
		}
		}
		ind++;
	}
	
	public void setInd()
	{
		ind=0;
	}
}
