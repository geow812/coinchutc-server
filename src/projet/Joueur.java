package projet;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

import java.beans.PropertyChangeSupport;
import java.io.StringWriter;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;

public class Joueur extends GuiAgent{
	protected static final int ANNONCE_EVENT = 0;
	protected static final int JEU_EVENT = 1;
	protected static final int REJOINDRE_EVENT = 2;
	protected static final int CHAT_EVENT = 3;
	private static final String CHAT_ID = "__chat__";
	private static final String NOTIF = "__notif__";
	private Carte[] main;
	private String main2;
	private String nom;
	private String prenom;
	private PropertyChangeSupport changes;
	private AID[] receivers = new AID[3];
	private boolean rejoindre=false;
	private String image;
	
	private void setReceiver() {
		int ind=0;
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Chat");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			if (result.length > 0) {
				for (int i=0;i<result.length;i++)
				{
					if (!result[i].getName().equals(this.getAID()))
					{
						receivers[ind] = result[i].getName();
						ind++;
					}
				}

			}
			else System.out.println("Erreur lors de la creation des receivers");
		}
		catch(FIPAException fe) {  }
		ind = 0;
	}

	@SuppressWarnings("serial")
	public class ChatBehaviour extends Behaviour{
		private boolean fini = false;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();
			
			if (msg != null && msg.getPerformative() == ACLMessage.INFORM && msg.getConversationId().equalsIgnoreCase(CHAT_ID))
			{
				changes.firePropertyChange("chat", msg.getSender().getLocalName(), msg.getContent());
			}
			else if (msg != null && msg.getPerformative()==ACLMessage.CONFIRM)
			{
				changes.firePropertyChange("finChat",null,null);
				fini=true;
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return fini;
		}
		
	}
	
	@SuppressWarnings("serial")
	public class RecupBehaviour extends Behaviour{
		private boolean fini = false;
		private int ind=0;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();
			if(msg!=null && msg.getPerformative()==ACLMessage.INFORM)
			{
				System.out.println("message re莽u : "+msg.getContent()+" par : "+myAgent.getLocalName());
				//main2 = msg.getContent();
				String s = msg.getContent(); // cha锟� JSON
				ObjectMapper mapper = new ObjectMapper();
				try {
					Carte card = mapper.readValue(s, Carte.class);
					main[ind]=card;

					changes.firePropertyChange("new card", null, card);

					ind++;
				}
				catch(Exception ex) {}
			}
			if (ind==8)
			{
				fini = true;
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return fini;
		}

	}

	@SuppressWarnings("serial")
	public class AnnonceBehaviour extends Behaviour{
		private boolean fini = false;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();

			if(msg!=null && msg.getPerformative()==ACLMessage.CONFIRM)
			{

				changes.firePropertyChange("fini", null, "fini");
				fini = true; 

			}
			else if (msg!=null && msg.getPerformative()==ACLMessage.INFORM)
			{
				changes.firePropertyChange("annonce",null,null);
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return fini;
		}

	}

	@SuppressWarnings("serial")
	public class JouerBehaviour extends Behaviour{
		private boolean fini=false;
		private String annonce;
		private boolean atout=false;
		@Override
		public void action() {
			//System.out.println("je suis dans jouer");
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();
			//System.out.println(msg.getContent());
			ArrayList<Carte> aJouer = new ArrayList<Carte>();

			if (msg!=null && msg.getPerformative()==ACLMessage.INFORM)
			{		
				annonce = Partie.annonce.getCouleur();
				System.out.println("couleur annoncée : "+annonce);
				if (Partie.tapis[0].getValeur()!=0)
				{
					if (Partie.tapis[0].getCouleur().equals(annonce))
						atout=true;
					
					if(atout==true)
					{
						for(int i=0;i<8;i++)
						{
							if (main[i].getCouleur().equals(annonce))
								aJouer.add(main[i]);
						}
						if (aJouer.size()==0)
						{
							for (int i=0;i<8;i++)
								aJouer.add(main[i]);
						}
					}
					else 
					{
						for(int i=0;i<8;i++)
						{
							if (main[i].getCouleur().equals(Partie.tapis[0].getCouleur()))
								aJouer.add(main[i]);
						}
						if (aJouer.size()==0)
						{
							for (int i=0;i<8;i++)
							{
								if(main[i].getCouleur().equals(annonce))
									aJouer.add(main[i]);
							}
						}
						if (aJouer.size()==0)
						{
							for (int i=0;i<8;i++)
							{
								aJouer.add(main[i]);
							}
						}
					}
				}
				changes.firePropertyChange("cartesDispo", null, aJouer);
				changes.firePropertyChange("jouer",null,"go");
				//changes.firePropertyChange("finJeu",null,"fin");
				//fini=true;
			}
			else if(msg!=null && msg.getPerformative()==ACLMessage.CONFIRM)
			{
				SequentialBehaviour comportementSequentiel = new SequentialBehaviour();
				//comportementSequentiel.addSubBehaviour(new ChatBehaviour());
				comportementSequentiel.addSubBehaviour(new RecupBehaviour());
				comportementSequentiel.addSubBehaviour(new AnnonceBehaviour());
				
				SequentialBehaviour jouerTour = new SequentialBehaviour();
				jouerTour.addSubBehaviour(new JouerBehaviour());
				/*jouerTour.addSubBehaviour(new JouerBehaviour());
				jouerTour.addSubBehaviour(new JouerBehaviour());
				jouerTour.addSubBehaviour(new JouerBehaviour());
				jouerTour.addSubBehaviour(new JouerBehaviour());
				jouerTour.addSubBehaviour(new JouerBehaviour());
				jouerTour.addSubBehaviour(new JouerBehaviour());
				jouerTour.addSubBehaviour(new JouerBehaviour());*/
				
				comportementSequentiel.addSubBehaviour(jouerTour);
				
				myAgent.addBehaviour(comportementSequentiel);
				
				changes.firePropertyChange("newManche",null,null);
				
				fini=true;
			}

		}
		@Override
		public boolean done() {
			return fini;
		}
	}

	@SuppressWarnings("serial")
	public class NotifyOthersBehaviour extends OneShotBehaviour {

		@Override
		public void action() {
			setReceiver();
			ACLMessage notif = new ACLMessage(ACLMessage.INFORM);
			notif.setConversationId(NOTIF);
			notif.addReceiver(receivers[0]);
			notif.addReceiver(receivers[1]);
			notif.addReceiver(receivers[2]);
			myAgent.send(notif);
			System.out.println("Notify player join");
		}

	}

	protected void setup()
	{
		image = "res/drawable/"+this.getLocalName()+".jpg";
		main = new Carte[8];
		MainWindow m = new MainWindow(this);
		
		changes = new PropertyChangeSupport(m);
		changes.addPropertyChangeListener(m);

		System.out.println("Hello " + this.getAID());
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Chat");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
			System.out.println(" "+getAID().getName()+" is registered as '"+sd.getType()+"'");
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

		// notify others players especially android players
		addBehaviour(new NotifyOthersBehaviour());
		
		SequentialBehaviour comportementSequentiel = new SequentialBehaviour();
		comportementSequentiel.addSubBehaviour(new ChatBehaviour());
		comportementSequentiel.addSubBehaviour(new RecupBehaviour());
		comportementSequentiel.addSubBehaviour(new AnnonceBehaviour());
		
		SequentialBehaviour jouerTour = new SequentialBehaviour();
		jouerTour.addSubBehaviour(new JouerBehaviour());
		/*jouerTour.addSubBehaviour(new JouerBehaviour());
		jouerTour.addSubBehaviour(new JouerBehaviour());
		jouerTour.addSubBehaviour(new JouerBehaviour());
		jouerTour.addSubBehaviour(new JouerBehaviour());
		jouerTour.addSubBehaviour(new JouerBehaviour());
		jouerTour.addSubBehaviour(new JouerBehaviour());
		jouerTour.addSubBehaviour(new JouerBehaviour());*/
		
		comportementSequentiel.addSubBehaviour(jouerTour);
		
		addBehaviour(comportementSequentiel);
		

	}


	public String getImage(){
		return image;
	}

	public Carte[] getMain() {
		return main;
	}



	public void setMain(Carte[] main) {
		this.main = main;
	}



	public String getNom() {
		return nom;
	}



	public void setNom(String nom) {
		this.nom = nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}



	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getType() == ANNONCE_EVENT) {
			changes.firePropertyChange("finAnnonce",null,null);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {

				int valeur = Integer.parseInt(arg0.getParameter(0).toString());

				Annonce ann = new Annonce(valeur,arg0.getParameter(1).toString());
				mapper.writeValue(sw, ann);
				String s = sw.toString();
				ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
				msg.setContent(s);
				msg.addReceiver(new AID("Partie1", AID.ISLOCALNAME));

				send(msg);

			}
			catch(Exception ex) {}
		}
		else if(arg0.getType()==JEU_EVENT)
		{
			changes.firePropertyChange("finJeu", null, "fin");
			String s = (String) arg0.getParameter(0); // chaine JSON
			
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			try {
				Carte card = mapper.readValue(s, Carte.class);
				System.out.println("image carte : "+card.getImage());
				//System.out.println("carte jouee gui ok : "+card.getValeur()+" "+card.getCouleur());
				mapper.writeValue(sw, card);
				String s1 = sw.toString();
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				msg.setContent(s1);
				msg.addReceiver(new AID("Partie1", AID.ISLOCALNAME));

				send(msg);
				

			}
			catch(Exception ex) {}
		}
		else if(arg0.getType()==REJOINDRE_EVENT)
		{
			//rejoindre = true;
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setContent("subscribe");
			msg.addReceiver(new AID("Partie1", AID.ISLOCALNAME));
			send(msg);
		}
		else if(arg0.getType()==CHAT_EVENT)
		{
			
			setReceiver();
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setConversationId(CHAT_ID);
			msg.setContent((String) arg0.getParameter(0));
			msg.addReceiver(receivers[0]);
			msg.addReceiver(receivers[1]);
			msg.addReceiver(receivers[2]);
			send(msg);
			
			changes.firePropertyChange("envoi", this.getLocalName(), (String) arg0.getParameter(0));
		}
	}
}
