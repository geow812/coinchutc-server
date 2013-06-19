package projet;

import jade.gui.GuiEvent;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import org.codehaus.jackson.map.ObjectMapper;

public class MainWindow extends JFrame implements PropertyChangeListener{
	private JSplitPane jsp;
	private JSplitPane jsp2;
	private JSplitPane jsp3;
	private Joueur myAgent;
	private JComboBox pts;
	private JComboBox col;
	private JButton annonce;
	private JComboBox mainJ;
	private JButton jouer;
	private JLabel nom;
	private JButton rejoindre;
	private JTextArea chat;
	private JButton envoyer;
	private JTextArea recu;
	private JLabel img;
	private JPanel pane1;
	private DragDrop dd;
	private JLabel espace;
	//private MainJoueur m;
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
	private JPanel paneCartes;
	private JPanel annoncePanel;
	
	private JPanel tapis;
	
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
	
	private JPanel flagPanel;
	
	@SuppressWarnings("deprecation")
	public MainWindow(Joueur j)
	{
		myAgent = j;
		annonce = new JButton("Annoncer");
		col = new JComboBox();
		pane1=new JPanel();
		espace = new JLabel("");
		tapis=new JPanel();
		tapis.setSize(1000, 1000);
		
		flagPanel = new JPanel();
		flagPanel.add(espace);
		
		p0 = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		j0 = new JLabel();
		j0.setName("slancelo");
		j1 = new JLabel();
		j1.setName("devauxbr");
		j2 = new JLabel();
		j2.setName("wangyiou");
		j3 = new JLabel();
		j3.setName("rclermon");
		
		i0 = new JLabel();
		i1 = new JLabel();
		i2 = new JLabel();
		i3 = new JLabel();
		
		p0.add(i0);
		p0.add(j0);
		p1.add(i1);
		p1.add(j1);
		p2.add(i2);
		p2.add(j2);
		p3.add(i3);
		p3.add(j3);
		
		tapis.setLayout(new GridLayout(2,2));
		tapis.add(p0); 
		tapis.add(p1);
		tapis.add(p2);
		tapis.add(p3);
		
		tapis.setBorder(BorderFactory.createLineBorder(Color.black));
		
		ImageIcon source = new ImageIcon(myAgent.getImage());
		ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

		img = new JLabel(resultat);

		chat = new JTextArea("Ecrire message ici...");
		recu = new JTextArea("Messages reçus");

		jouer = new JButton("Jouer");
		envoyer = new JButton("Envoyer");
		rejoindre = new JButton("Rejoindre Partie");
		mainJ = new JComboBox();
		//nom = new JLabel(myAgent.getLocalName());
		pts = new JComboBox();
		annoncePanel = new JPanel();
		paneCartes = new JPanel();
		c0 = new JLabel();
		c1 = new JLabel();
		c2 = new JLabel();
		c3 = new JLabel();
		c4 = new JLabel();
		c5 = new JLabel();
		c6 = new JLabel();
		c7 = new JLabel();

		paneCartes.add(c0);
		paneCartes.add(c1);
		paneCartes.add(c2);
		paneCartes.add(c3);
		paneCartes.add(c4);
		paneCartes.add(c5);
		paneCartes.add(c6);
		paneCartes.add(c7);
		paneCartes.setSize(400, 200);
		paneCartes.setLayout(new GridLayout(2,4));

		annoncePanel.setSize(400,50);
		annoncePanel.setLayout(new GridLayout(1,3));
		annoncePanel.add(pts);
		annoncePanel.add(col);
		annoncePanel.add(annonce);
		
		ind=0;
		
		main = new Carte[8];

		c0.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[0];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[0]=null;
					c0.setIcon(null);
					ok=false;
				}
			}
		});

		c1.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[1];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[1]=null;
					c1.setIcon(null);
					ok=false;
				}
			}
		});

		c2.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[2];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[2]=null;
					c2.setIcon(null);
					ok=false;
				}
			}
		});

		c3.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[3];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[3]=null;
					c3.setIcon(null);
					ok=false;
				}
			}
		});

		c4.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[4];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[4]=null;
					c4.setIcon(null);
					ok=false;
				}
			}
		});

		c5.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[5];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[5]=null;
					c5.setIcon(null);
					ok=false;
				}
			}
		});

		c6.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[6];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[6]=null;
					c6.setIcon(null);
					ok=false;
				}
			}
		});

		c7.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (ok==true)
				{
					Carte card = main[7];
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
						ev.addParameter(s);
						myAgent.postGuiEvent(ev);
					}
					catch(Exception ex) {}

					main[7]=null;
					c7.setIcon(null);
					ok=false;
				}
			}
		});

		//m = new MainJoueur();
		//m.setSize(400, 200);
		dd = new DragDrop();

		pts.addItem(0);
		pts.addItem(80);
		pts.addItem(90);
		pts.addItem(100);
		pts.addItem(110);
		pts.addItem(120);
		pts.addItem(250);

		//pane1.add(nom);
		//pane1.add(img);

		col.addItem("Pique");
		col.addItem("Trefle");
		col.addItem("Carreau");
		col.addItem("Coeur");
		col.addItem("Tout-Atout");
		col.addItem("Sans-Atout");

		rejoindre.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				rejoindre.setEnabled(false);
				//remove(rejoindre);
				//envoyer.setEnabled(false);
				//chat.setEnabled(false);

				GuiEvent ev = new GuiEvent(this,Joueur.REJOINDRE_EVENT);
				ev.addParameter(pts.getSelectedItem());
				ev.addParameter(col.getSelectedItem());
				myAgent.postGuiEvent(ev);
			}

		});

		envoyer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				GuiEvent ev = new GuiEvent(this,Joueur.CHAT_EVENT);
				ev.addParameter(chat.getText());
				myAgent.postGuiEvent(ev);
			}

		});

		annonce.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println(myAgent.getLocalName()+" annonce : "+pts.getSelectedItem()+" "+col.getSelectedItem());
				GuiEvent ev = new GuiEvent(this,Joueur.ANNONCE_EVENT);
				ev.addParameter(pts.getSelectedItem());
				ev.addParameter(col.getSelectedItem());
				myAgent.postGuiEvent(ev);
			}

		});



		jouer.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {

				// TODO Auto-generated method stub
				System.out.println(myAgent.getLocalName()+" joue : "+mainJ.getSelectedItem());
				Carte card = parserCarte((String) mainJ.getSelectedItem());
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				try {
					mapper.writeValue(sw, card);
					String s = sw.toString();
					GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
					ev.addParameter(s);
					myAgent.postGuiEvent(ev);
				}
				catch(Exception ex) {}


			}

		});
		
		//jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,annoncePanel,paneCartes);
		//jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,paneCartes,tapis);
		
		jouer.setVisible(true);
		jouer.setEnabled(false);

		//this.setLayout(new GridLayout(11,1));
		this.setLayout(new GridLayout(4,1));
		this.setTitle(myAgent.getLocalName());
		//this.add(nom);
		this.add(chat);
		this.add(recu);
		this.add(envoyer);
		this.add(rejoindre);
		/*this.add(pts); 
		this.add(col);
		this.add(annonce);
		this.add(mainJ);
		this.add(jouer);*/
		//this.add(dd);
		this.pack();
		this.setVisible(true);
	}

	public Carte parserCarte(String s){
		Carte card = new Carte();

		String[] s1 = s.split(" ");
		int val = Integer.parseInt(s1[0]);
		card.setValeur(val);
		card.setCouleur(s1[1]);
		card.setImage(s1[2]);
		return card;
	}


	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getPropertyName().equals("new card"))
		{
			mainJ.addItem(((Carte)arg0.getNewValue()).getValeur()+" "+((Carte)arg0.getNewValue()).getCouleur()+" "+((Carte)arg0.getNewValue()).getImage());
			main[ind]=(Carte) arg0.getNewValue();
			switch(ind)
			{
			case 0: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c0.setIcon(resultat);

				break;
			}
			case 1: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c1.setIcon(resultat);

				break;
			}
			case 2: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c2.setIcon(resultat);

				break;
			}
			case 3: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c3.setIcon(resultat);

				break;
			}
			case 4: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c4.setIcon(resultat);

				break;
			}
			case 5: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c5.setIcon(resultat);

				break;
			}
			case 6: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c6.setIcon(resultat);

				break;
			}
			case 7: {
				ImageIcon source = new ImageIcon(((Carte)arg0.getNewValue()).getImage());
				ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
				c7.setIcon(resultat);

				break;
			}
			}
			ind++;
			//m.setCarte((Carte)arg0.getNewValue());

		}
		else if (arg0.getPropertyName().equals("cartesDispo"))
		{
			/*ArrayList<Carte> cartesDispo = (ArrayList<Carte>) arg0.getNewValue();
			for (int k=0;k<cartesDispo.size();k++)
			{
				System.out.println("carte : "+cartesDispo.get(k).getValeur()+" "+cartesDispo.get(k).getCouleur());
			}
			boolean trouve=false;

			for (int i=0; i<mainJ.getItemCount();i++)
			{
				Carte card = parserCarte(mainJ.getItemAt(i).toString());
				for (int j=0;j<cartesDispo.size();j++)
				{
					if (cartesDispo.get(j).getCouleur().equals(card.getCouleur()) && cartesDispo.get(j).getValeur()==card.getValeur())
					{
						trouve = true;
						break;
					}
				}

				if (trouve==false && cartesDispo.size()!=0)
					mainJ.removeItemAt(i);

				trouve=false;
			}*/
		}
		else if (arg0.getPropertyName().equals("jouer"))
		{
			//System.out.println("je suis l�"+myAgent.getLocalName());
			//m.setOk(true);
			//paneCartes.setEnabled(true);
			espace.setText("A toi de jouer !");
			ok=true;
			//jouer.setEnabled(true);
			//jouer.setVisible(true);
		}
		else if (arg0.getPropertyName().equals("annonce"))
		{
			annonce.setEnabled(true);	
		}
		else if (arg0.getPropertyName().equals("finAnnonce"))
		{
			annonce.setEnabled(false);	
			ind=0;
		}
		else if (arg0.getPropertyName().equals("finJeu"))
		{
			//jouer.setVisible(false);
			//mainJ.removeItem(mainJ.getSelectedItem());
			//m.setOk(false);
			espace.setText("");
			paneCartes.setEnabled(false);
			jouer.setEnabled(false);
		}
		else if (arg0.getPropertyName().equals("chat"))
		{
			recu.setText(recu.getText()+"\n"+arg0.getOldValue()+": "+arg0.getNewValue());
		}
		else if (arg0.getPropertyName().equals("envoi"))
		{
			recu.setText(recu.getText()+"\nMoi: "+arg0.getNewValue());
		}
		else if (arg0.getPropertyName().equals("finChat"))
		{
			this.remove(chat);
			this.remove(recu);
			this.remove(envoyer);
			this.remove(rejoindre);

			this.setLayout(new GridLayout(1,1));
			
			//this.setLayout(new BorderLayout());
			//this.add(pts); 
			//this.add(col);
			//this.add(annonce);
			//this.add(mainJ);
			annonce.setEnabled(false);
			annoncePanel.setSize(400, 50);
			paneCartes.setSize(400,400);
			
			//jsp.setResizeWeight(0.25);
			//this.add(annoncePanel);
			//flagPanel.setLayout(new GridLayout(1,1));
			//this.add(flagPanel);
			//this.add(paneCartes);
			jsp3 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,flagPanel,paneCartes);
			jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,annoncePanel,jsp3);
			this.add(jsp);
			this.pack();
			//this.add(m);
			
			//this.add(m);
			//m.setInd();
			//m.setEnabled(false);
			
		}
		else if (arg0.getPropertyName().equals("fini"))
		{
			//this.remove(pts);
			//this.remove(col);
			//this.remove(annonce);
			//m.setEnabled(true);
			//this.remove(annoncePanel);
			//this.setLayout(new GridLayout(3,1));
			//espace.setSize(400,50);
			//this.add(espace);
			this.remove(jsp);
			//this.add(tapis);
			jsp2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT,jsp3,tapis);
			this.add(jsp2);
			//this.add(jouer);
			this.pack();

		}
		else if (arg0.getPropertyName().equals("newManche"))
		{
			//this.remove(jouer);
			this.remove(jsp2);
			this.setLayout(new GridLayout(1,1));
			//this.add(pts);
			//this.add(col);
			//this.add(annonce);
			//this.add(annoncePanel);
			//this.add(paneCartes);
			//this.add(espace);
			jsp = new JSplitPane(JSplitPane.VERTICAL_SPLIT,annoncePanel,jsp3);
			//this.add(tapis);
			//m.setEnabled(true);
			this.add(jsp);
			this.pack();
			//this.add(jouer);

		}

		else if (arg0.getPropertyName().equals("jeuCarte"))
		{
			System.out.println(myAgent.getLocalName()+" joue : "+mainJ.getSelectedItem());
			Carte card = (Carte) arg0.getNewValue();
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {
				mapper.writeValue(sw, card);
				String s = sw.toString();
				GuiEvent ev = new GuiEvent(this,Joueur.JEU_EVENT);
				ev.addParameter(s);
				myAgent.postGuiEvent(ev);
			}
			catch(Exception ex) {}
		}
		
		else if (arg0.getPropertyName().equals("carteTapis"))
		{
			
			if (j0.getName().equals(arg0.getOldValue().toString()))
			{
				//System.out.println("je suis l� joueur :"+arg0.getOldValue());
				Carte card = (Carte) arg0.getNewValue(); 
				try {
					ImageIcon source = new ImageIcon(card.getImage());
					ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					j0.setIcon(resultat);
					ImageIcon source2 = new ImageIcon("res/drawable/"+arg0.getOldValue().toString()+".jpg");
					ImageIcon resultat2 = new ImageIcon(source2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					i0.setIcon(resultat2);

					//j0.setText(card.getValeur()+ " "+card.getCouleur());
				}
				catch(Exception ex) {}
			}
			else if (j1.getName().equals(arg0.getOldValue().toString()))
			{
				//System.out.println("je suis l� joueur :"+arg0.getOldValue());
				Carte card = (Carte) arg0.getNewValue(); 
				try {
					ImageIcon source = new ImageIcon(card.getImage());
					ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					j1.setIcon(resultat);
					ImageIcon source2 = new ImageIcon("res/drawable/"+arg0.getOldValue().toString()+".jpg");
					ImageIcon resultat2 = new ImageIcon(source2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					i1.setIcon(resultat2);
					//j1.setText(card.getValeur()+ " "+card.getCouleur());
				}
				catch(Exception ex) {}
			}
			else if (j2.getName().equals(arg0.getOldValue().toString()))
			{
				//System.out.println("je suis l� joueur :"+arg0.getOldValue());
				Carte card = (Carte) arg0.getNewValue(); 
				try {
					ImageIcon source = new ImageIcon(card.getImage());
					ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					j2.setIcon(resultat);
					ImageIcon source2 = new ImageIcon("res/drawable/"+arg0.getOldValue().toString()+".jpg");
					ImageIcon resultat2 = new ImageIcon(source2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					i2.setIcon(resultat2);
					//j2.setText(card.getValeur()+ " "+card.getCouleur());
				}
				catch(Exception ex) {}
			}
			else if (j3.getName().equals(arg0.getOldValue().toString()))
			{
				//System.out.println("je suis l� joueur :"+arg0.getOldValue());
				//String s = arg0.getNewValue().toString(); // cha�e JSON
				//ObjectMapper mapper = new ObjectMapper();
				Carte card = (Carte) arg0.getNewValue(); 
				try {
					//Carte card = mapper.readValue(s, Carte.class);
					ImageIcon source = new ImageIcon(card.getImage());
					ImageIcon resultat = new ImageIcon(source.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					j3.setIcon(resultat);
					ImageIcon source2 = new ImageIcon("res/drawable/"+arg0.getOldValue().toString()+".jpg");
					ImageIcon resultat2 = new ImageIcon(source2.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
					i3.setIcon(resultat2);
					//j3.setText(card.getValeur()+ " "+card.getCouleur());
				}
				catch(Exception ex) {}
			}
		}

		else
		{
			annonce.setVisible(false);
			//jouer.setVisible(true);
		}

	}

}
