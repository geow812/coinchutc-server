package projet;


import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import jade.core.Profile;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Launcher {
	public static void main(String[] ars)
	{

		// Get a hold on JADE runtime
		Runtime rt = Runtime.instance();
		// Create a default profile
		ProfileImpl p = new ProfileImpl();
		p.setParameter("gui", "true");
		p.setParameter("main", "true");
		// Create a new non-main container, connecting to the default
		// main container (i.e. on this host, port 1099)
		ContainerController cc = rt.createMainContainer(p);
		// Create a new agent, a DummyAgent
		// and pass it a reference to an Object
		Object reference = new Object();
		Object args[] = new Object[1];
		args[0]=reference;
		AgentController dummy = null;
		AgentController dummy2 = null;

		try {
			dummy2 = cc.createNewAgent("Partie1",
					"projet.Partie", null);
			dummy2.start();
			/*
			for (int i=0;i<4;i++)
			{
				dummy = cc.createNewAgent("Joueur"+i,
						"projet.Joueur", null);
				dummy.start(); 

			}*/
			dummy = cc.createNewAgent("login", "projet.LogAgent", null);
			dummy.start();
			dummy = cc.createNewAgent("androidconn", "projet.AndroidConnexionAgent", null);
			dummy.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Fire up the agent
	
	}
}