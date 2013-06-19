package projet;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class PartieGenerale extends Agent{
	private AID[] joueurs;
	private AID[] equipe1;
	private AID[] equipe2;
	private int score1=0;
	private int score2=0;
		
	public class SubscribeBehaviour extends Behaviour{
		private boolean fini=false;
		private int compteur=0;
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();
			if (msg!=null && msg.getPerformative()==ACLMessage.INFORM)
			{
				System.out.println("message recu de : "+msg.getSender().getLocalName());
				joueurs[compteur] = msg.getSender();

				if (compteur==0)
					equipe1[0]=msg.getSender();
				else if (compteur==1)
					equipe2[0]=msg.getSender();
				else if (compteur==2)
					equipe1[1]=msg.getSender();
				else if (compteur==3)
					equipe2[1]=msg.getSender();

				compteur++;
			}
			if (compteur==4)
			{
				for (int i=0;i<4;i++)
				{
					ACLMessage message = new ACLMessage(ACLMessage.INFORM);
					message.addReceiver(new AID("Partie1", AID.ISLOCALNAME));
					try {
						message.setContentObject(joueurs[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					send(message);
				}
				fini = true;
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return fini;
		}

	}
	
	public class MancheBehaviour extends Behaviour{
		
		private boolean fini = false ;
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			ACLMessage msg = myAgent.receive();
			if (msg!=null && msg.getPerformative()==ACLMessage.INFORM)
			{
				String[] s = msg.getContent().split("=");
				if (s[0].equals("score1"))
				{
					int val = Integer.parseInt(s[1]);
					score1=val;
				}
				else
				{
					int val = Integer.parseInt(s[1]);
					score2=val;
				}
				if (score1>=1000 || score2>=1000)
				{
					fini = true;
				}
				Runtime rt = Runtime.instance();
				ProfileImpl p = new ProfileImpl();
				p.setParameter("gui", "true");
				p.setParameter("main", "false");
				ContainerController cc = rt.createAgentContainer(p);
				AgentController dummy = null;
				try {
					dummy = cc.createNewAgent("Partie1",
							"projet.Partie", null);
					dummy.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i=0;i<4;i++)
				{
					ACLMessage message = new ACLMessage(ACLMessage.INFORM);
					message.addReceiver(new AID("Partie1", AID.ISLOCALNAME));
					try {
						message.setContentObject(joueurs[i]);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					send(message);
				}
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return fini;
		}
		
	}
	
	protected void setup(){
		System.out.println("Hello "+this.getAID().getLocalName());
		
		joueurs = new AID[4];
		equipe1 = new AID[2];
		equipe2 = new AID[2];
		
		Runtime rt = Runtime.instance();
		ProfileImpl p = new ProfileImpl();
		p.setParameter("gui", "true");
		p.setParameter("main", "false");
		ContainerController cc = rt.createAgentContainer(p);
		AgentController dummy = null;
		try {
			dummy = cc.createNewAgent("Partie1",
					"projet.Partie", null);
			dummy.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SequentialBehaviour deroulementPartie = new SequentialBehaviour();
		deroulementPartie.addSubBehaviour(new SubscribeBehaviour());
		deroulementPartie.addSubBehaviour(new MancheBehaviour());
		
		addBehaviour(deroulementPartie);
	}
}
