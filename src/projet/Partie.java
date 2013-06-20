package projet;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.beans.PropertyChangeSupport;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.codehaus.jackson.map.ObjectMapper;

@SuppressWarnings("serial")
public class Partie extends GuiAgent{
	private AID[] joueurs;
	private AID[] equipe1;
	private AID[] equipe2;
	private Points[] points;
	public static Carte[] tapis = new Carte[4];
	//private Carte[] jeu;
	private ArrayList<Carte> jeu;
	public static boolean pret=false;
	public static Annonce annonce;
	private PropertyChangeSupport changes;
	private int score1=0;
	private int score2=0;
	private PropertyChangeSupport changes2;

	public class SubscribeBehaviour extends Behaviour
	{
		//ce comportement sera mis dans une autre classe, table d'attente (a voir niveau conception)

		private boolean fini=false;
		private int compteur = 0;
		@Override
		public void action() {
			ACLMessage msg = myAgent.receive();
			if (msg!=null && msg.getPerformative() == ACLMessage.INFORM)
			{
				System.out.println("message recu de : "+ msg.getSender().getLocalName());

				joueurs[compteur] = msg.getSender();

				if (compteur == 0)
					equipe1[0] = msg.getSender();

				else if (compteur == 1)
					equipe2[0] = msg.getSender();

				else if (compteur == 2)
					equipe1[1] = msg.getSender();

				else if (compteur == 3)
					equipe2[1] = msg.getSender();

				compteur++;
			}

			if (compteur==4)
			{
				PartyWindow p = new PartyWindow((Partie) myAgent,joueurs);
				changes = new PropertyChangeSupport(p);
				changes.addPropertyChangeListener(p);

				for (int i=0;i<4;i++)
				{
					ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);
					message.addReceiver(joueurs[i]);
					// Send the order of the four players
					message.setContent(joueurs[0].getLocalName()+","
					+joueurs[1].getLocalName()+","
					+joueurs[2].getLocalName()+","
					+joueurs[3].getLocalName());
					send(message);
				}
				fini = true;
			}
		}

		@Override
		public boolean done() {
			return fini;
		}

	}

	public class DistributionBehaviour extends OneShotBehaviour
	{

		@Override
		public void action() {
			for(int i=0;i<4;i++)
			{

				//v锟�fier pour chaque carte tir锟�al锟�oirement si elle est d锟斤拷utilis锟�
				for (int j=0;j<8;j++)
				{
					Random r = new Random();
					int valeur = 0 + r.nextInt(jeu.size());

					//Carte card = jeu[valeur];
					Carte card = jeu.get(valeur);
					ObjectMapper mapper = new ObjectMapper();
					StringWriter sw = new StringWriter();
					try {
						mapper.writeValue(sw, card);
						String s = sw.toString();
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.setContent(s);
						msg.addReceiver(joueurs[i]);
						send(msg);
					}
					catch(Exception ex) {}
					jeu.remove(valeur);
				}
			}
			pret=true;
		}


	}

	public class AnnonceBehaviour extends Behaviour
	{
		private boolean fini=false;
		private int ind = 0;
		private int compteur=0;
		private boolean maitre=false;
		private int passe=0;
		@Override
		public void action() {
			if (compteur==0)
			{
				System.out.println("j'envoie un message d'annonce a "+joueurs[ind].getLocalName() );
				ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
				msg1.addReceiver(joueurs[ind]);
				msg1.setContent("jouer");
				send(msg1);
				compteur++;
			}
			
			ACLMessage msg = myAgent.receive();
			if(msg!=null && msg.getPerformative()==ACLMessage.CONFIRM)
			{
				if (msg.getSender().equals(joueurs[ind]))
				{
					String s = msg.getContent(); // chaine JSON
					ObjectMapper mapper = new ObjectMapper();
					try {
						Annonce ann = mapper.readValue(s, Annonce.class);

						if (ann.getValeur()==0)
						{
							passe++;
						}

						else if (ann.getValeur()>annonce.getValeur())
						{
							passe=0;
							annonce = ann;
							for (int i=0; i<2;i++)
							{
								if (msg.getSender().equals(equipe1[i]))
								{
									maitre=true;
									annonce.setMaitre(equipe1);
									break;
								}
							}
							if (maitre==false)
								annonce.setMaitre(equipe2);
						}
						if (passe==3)
						{
							for (int i=0; i<4;i++)
							{
								ACLMessage message = new ACLMessage(ACLMessage.CONFIRM);
								String s1 = "" + annonce.getValeur();
								message.setContent(s1);
								message.addReceiver(joueurs[i]);
								send(message);
								System.out.println("confirm final annonce to "+ joueurs[i].getLocalName() );
							}
							System.out.println(annonce.getMaitre()[0]);
							System.out.println(annonce.getMaitre()[1]);
							fini = true;
						}


					}
					catch(Exception ex) {}
					System.out.println("Annonce en cours : "+annonce.getValeur()+" "+annonce.getCouleur());
					ind++;
					System.out.println("ind = "+ ind );
					compteur=0;
					if (ind > 3 && fini==false)
					{
						compteur=0;
						ind=0;
					}
				}
			}

		}

		@Override
		public boolean done() {
			return fini;
		}

	}

	public class TourBehaviour extends Behaviour
	{

		private int ind1=0;
		private int ind=0;
		private int compteur=0;
		private boolean fini=false;

		@Override
		public void action() {
			if(compteur==0)
			{
				System.out.println("j'envoie un message de jouer a "+joueurs[ind].getLocalName() ); 
				ACLMessage msg1 = new ACLMessage(ACLMessage.INFORM);
				msg1.addReceiver(joueurs[ind1]);
				msg1.setContent("jouer");
				send(msg1);
				compteur++;
			}
			ACLMessage msg = myAgent.receive();

			if(msg!=null && msg.getPerformative()==ACLMessage.REQUEST && msg.getSender().equals(joueurs[ind1]))
			{
				System.out.println("message " + msg.getContent()+" recu de : " + msg.getSender().getLocalName());
				String s = msg.getContent(); // chaine JSON
				ObjectMapper mapper = new ObjectMapper();
				try {
					Carte card = mapper.readValue(s, Carte.class);
					tapis[ind]=card;
					System.out.println("carte sur le tapis : "+tapis[ind].getValeur()+ " "+tapis[ind].getCouleur()+" jouee par : "+msg.getSender().getLocalName());
					card.setLogin(msg.getSender().getLocalName());
					ObjectMapper mapper2 = new ObjectMapper();
					StringWriter sw = new StringWriter();
					mapper2.writeValue(sw, card);
					String s2 = sw.toString();

					changes.firePropertyChange("carte", msg.getSender().getLocalName(), s2);

					for (int i=0;i<4;i++)
					{
						ACLMessage rep = new ACLMessage(ACLMessage.REQUEST);
						rep.setContent(s2);
						rep.addReceiver(joueurs[i]);
						send(rep);
					}
					
					
					ind++;
					ind1++;
					compteur=0;
				}

				catch(Exception ex) {}
			}
			if (ind==4)
			{
				AID gagnant = new AID();
				boolean trouve=false;
				int result=0;
				gagnant=quiGagne();
				System.out.println("gagnant : "+gagnant.getLocalName());
				for (int i=0;i<2;i++)
				{
					if (equipe1[i].equals(gagnant))
					{
						trouve=true;
					}
				}
				result=nbPoints();
				System.out.println("nb points : "+result);
				if (trouve==true)
				{
					score1=score1+result;
					System.out.println("score equipe 1 : "+score1);
				}
				else
				{
					score2=score2+result;
					System.out.println("score equipe 2 : "+score2);
				}
				changes.firePropertyChange("finTour", null, null);
				fini=true;
			}
		}

		@Override
		public boolean done() {
			return fini; 
		}

	}

	public int nbPoints()
	{
		int score=0;
		for (int i=0;i<4;i++)
		{
			if (annonce.getCouleur().equals(tapis[i].getCouleur()))
			{
				for (int j=8;j<16;j++)
				{
					if(points[j].getCarte()==tapis[i].getValeur())
					{
						score=score+points[j].getValeur();
					}
				}
			}
			else 
			{
				for (int j=0;j<8;j++)
				{
					if(points[j].getCarte()==tapis[i].getValeur())
					{
						score=score+points[j].getValeur();
					}
				}
			}
		}
		return score;
	}

	public AID quiGagne()
	{
		AID gagnant = new AID();
		boolean coupe=false;
		int ind=0;
		String ann = annonce.getCouleur();
		for (int i=0;i<4;i++)
		{
			if (tapis[i].getCouleur().equals(ann))
			{
				coupe=true;
				ind=i;
			}	
		}
		if (coupe==true)
		{
			gagnant=joueurs[ind];
			if (ind == 1)
			{
				AID jTemp = new AID();

				jTemp=joueurs[0];

				joueurs[0]=joueurs[ind];
				joueurs[ind]=joueurs[2];
				joueurs[2]=joueurs[3];
				joueurs[3]=jTemp;

			}
			else if (ind == 2)
			{
				AID jTemp = new AID();
				AID jTemp2 = new AID();
				jTemp = joueurs[0];

				joueurs[0]=joueurs[ind];
				joueurs[2]=jTemp;
				jTemp2=joueurs[1];
				joueurs[1]=joueurs[3];
				joueurs[3]=jTemp2;
			}
			else if (ind == 3)
			{
				AID jTemp = new AID();
				AID jTemp2 = new AID();
				AID jTemp3 = new AID();

				jTemp = joueurs[0];
				joueurs[0]=joueurs[ind];
				jTemp2=joueurs[1];
				joueurs[1]=jTemp;
				jTemp3=joueurs[2];
				joueurs[2]=jTemp2;
				joueurs[3]=jTemp3;
			}

		}
		else
		{

			int max=0;
			for (int i=0;i<4;i++)
			{
				if (tapis[i].getValeur()>max)
				{
					max=tapis[i].getValeur();
					ind=i;
				}
			}
			gagnant=joueurs[ind];
			if (ind == 1)
			{
				AID jTemp = new AID();

				jTemp=joueurs[0];

				joueurs[0]=joueurs[ind];
				joueurs[ind]=joueurs[2];
				joueurs[2]=joueurs[3];
				joueurs[3]=jTemp;

			}
			else if (ind == 2)
			{
				AID jTemp = new AID();
				AID jTemp2 = new AID();
				jTemp = joueurs[0];

				joueurs[0]=joueurs[ind];
				joueurs[2]=jTemp;
				jTemp2=joueurs[1];
				joueurs[1]=joueurs[3];
				joueurs[3]=jTemp2;
			}
			else if (ind == 3)
			{
				AID jTemp = new AID();
				AID jTemp2 = new AID();
				AID jTemp3 = new AID();

				jTemp = joueurs[0];
				joueurs[0]=joueurs[ind];
				jTemp2=joueurs[1];
				joueurs[1]=jTemp;
				jTemp3=joueurs[2];
				joueurs[2]=jTemp2;
				joueurs[3]=jTemp3;
			}


		}
		return gagnant;
	}

	public void creerJeu()
	{
		@SuppressWarnings("unused")
		int ind = 0;

		for (int i=0; i<8;i++)
		{
			int img = i+7;
			Carte card = new Carte(i+7,"Pique","res/drawable/"+img+"Pique.png");
			//jeu[ind]=card;
			jeu.add(card);
			ind++;
		}
		for (int i=8; i<16;i++)
		{
			int img = i-1;
			Carte card = new Carte(i-1,"Trefle","res/drawable/"+img+"Trefle.png");
			//jeu[ind]=card;
			jeu.add(card);
			ind++;
		}
		for (int i=16; i<24;i++)
		{
			int img = i-9;
			Carte card = new Carte(i-9,"Carreau","res/drawable/"+img+"Carreau.png");
			//jeu[ind]=card;
			jeu.add(card);
			ind++;
		}
		for (int i=24; i<32;i++)
		{
			int img = i-17;
			Carte card = new Carte(i-17,"Coeur","res/drawable/"+img+"Coeur.png");
			//jeu[ind]=card;
			jeu.add(card);
			ind++;
		}

		for (int i=0;i<3;i++)
		{
			Points p = new Points(i+7,0,false);
			points[i]=p;
		}
		Points p1 = new Points(10,10, false);
		points[3]=p1;

		Points p2 = new Points(11,2, false);
		points[4]=p2;

		Points p3 = new Points(12,3, false);
		points[5]=p3;

		Points p4 = new Points(13,4, false);
		points[6]=p4;

		Points p5 = new Points(14,11, false);
		points[7]=p5;

		for (int i=8;i<10;i++)
		{
			Points p6 = new Points(i-1,0,true);
			points[i] = p6;
		}

		Points p7 = new Points(9,14,true);
		points[10] = p7;

		Points p8 = new Points(10,10,true);
		points[11]=p8;

		Points p11 = new Points(11,20,true);
		points[12]=p11;

		Points p12 = new Points(12,2,true);
		points[13]=p12;

		Points p13 = new Points(13,4,true);
		points[14]=p13;

		Points p14 = new Points(14,11,true);
		points[15]=p14;

		for (int i=0;i<16;i++)
		{
			System.out.println("points :"+points[i].getCarte()+" "+points[i].getValeur());
		}

	}

	public class CalculBehaviour extends OneShotBehaviour{

		@Override
		public void action() {
			ScoreWindow sc = new ScoreWindow(equipe1,equipe2);
			changes2 = new PropertyChangeSupport(sc);
			changes2.addPropertyChangeListener(sc);
			// TODO Auto-generated method stub
			AID[] maitres = new AID[2];
			boolean eq1 = false ;
			maitres = annonce.getMaitre();

			for (int i=0;i<2;i++)
			{
				System.out.println(maitres[i].getLocalName());
			}

			if(maitres[0].equals(equipe1[0]))
			{
				eq1 = true;
			}
			System.out.println(eq1);
			if(eq1==true)
			{
				if(score1>=annonce.getValeur())
				{
					System.out.println("Equipe 1 a realise");
					score1=annonce.getValeur();
					score2=0;
					//changes.firePropertyChange("R锟�is锟�, score2, score1);
				}
				else
				{
					System.out.println("Equipe 1 a chute");
					score2=160;
					score1=0;
					//changes.firePropertyChange("Chut锟�, score2, score1);
				}
			}
			else 
			{
				if(score2>=annonce.getValeur())
				{
					System.out.println("Equipe 2 a realise");
					score2=annonce.getValeur();
					score1=0;
					//changes.firePropertyChange("R锟�is锟�, score2, score1);
				}
				else
				{
					System.out.println("Equipe 2 a chute");
					score1=160;
					score2=0;
					//changes.firePropertyChange("Chut锟�, score2, score1);
				}
			}
			changes2.firePropertyChange("score", score2, score1);

			if (!(score1>=1000 || score2>=1000))
			{
				for (int i = 0; i<4; i++)
				{
					ACLMessage msg1 = new ACLMessage(ACLMessage.CONFIRM);
					msg1.setContent("recommencer");
					msg1.addReceiver(joueurs[i]);
					myAgent.send(msg1);
				}
				annonce.setValeur(0);
				annonce.setCouleur("NA");
				tapis = new Carte[4];

				for (int i=0;i<4;i++)
				{
					Carte card = new Carte(0,"NA","NA");
					tapis[i]=card;
				}

				creerJeu();

				SequentialBehaviour comportementSequentiel = new SequentialBehaviour();

				//comportementSequentiel.addSubBehaviour(new SubscribeBehaviour());

				comportementSequentiel.addSubBehaviour(new DistributionBehaviour());

				comportementSequentiel.addSubBehaviour(new AnnonceBehaviour());

				SequentialBehaviour Manche = new SequentialBehaviour();

				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());
				Manche.addSubBehaviour(new TourBehaviour());

				comportementSequentiel.addSubBehaviour(Manche);

				comportementSequentiel.addSubBehaviour(new CalculBehaviour());

				myAgent.addBehaviour(comportementSequentiel);
			}
			else
			{
				JFrame finJeu = new JFrame();
				finJeu.add(new JLabel("Partie terminee!"));
				finJeu.pack();
				finJeu.setVisible(true);
			}
		}


	}

	protected void setup()
	{
		//PartyWindow p = new PartyWindow(this);
		//changes = new PropertyChangeSupport(p);
		//schanges.addPropertyChangeListener(p);

		joueurs = new AID[4];
		equipe1 = new AID[2];
		equipe2 = new AID[2];
		points = new Points[16];
		//jeu = new Carte[32];
		jeu = new ArrayList<Carte>();
		annonce = new Annonce(0,"NA");
		tapis = new Carte[4];

		for (int i=0;i<4;i++)
		{
			Carte card = new Carte(0,"NA","NA");
			tapis[i]=card;
		}

		creerJeu();

		System.out.println("Hello " + this.getAID());

		SequentialBehaviour comportementSequentiel = new SequentialBehaviour();

		comportementSequentiel.addSubBehaviour(new SubscribeBehaviour());

		comportementSequentiel.addSubBehaviour(new DistributionBehaviour());

		comportementSequentiel.addSubBehaviour(new AnnonceBehaviour());

		SequentialBehaviour Manche = new SequentialBehaviour();

		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());
		Manche.addSubBehaviour(new TourBehaviour());

		comportementSequentiel.addSubBehaviour(Manche);

		comportementSequentiel.addSubBehaviour(new CalculBehaviour());

		addBehaviour(comportementSequentiel);

	}

	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub

	}




}
