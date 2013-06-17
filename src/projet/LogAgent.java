package projet;

import jade.core.AID;
import jade.core.Agent;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;

import org.codehaus.jackson.map.ObjectMapper;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class LogAgent extends GuiAgent{

	private String prefix;
	private Model model;
	private Model model2;
	protected static final int LOG_EVENT = 0;
	protected static final int CREER_EVENT = 1;
	
	public static final String DECONNEXION = "__deconnexion__";
	public static final String QUERYLOGIN = "__query_login__";
	public static final String LOGINSUCCESS = "__login_success__";
	public static final String LOGINFAIL = "__login_fail__";
	public static final String CREER_COMPTE = "__creer_compte__";
	
	protected void setup(){
		System.out.println("Hello! Agent "+getAID().getName()+" is ready.");
		LogWindow log = new LogWindow(this);

		try{
			model = ModelFactory.createDefaultModel();
			FileInputStream fis = new FileInputStream("baseDonnees.txt");
			model.read(fis,"http://utc/","N3"); // n3	
			prefix = "http://projet/";
			addBehaviour(new CheckLoginBehaviour(this));
		}
		catch(Exception ex){}
	}
	
	class CheckLoginBehaviour extends CyclicBehaviour {

		public CheckLoginBehaviour(Agent a) {
			super(a);
		}
		
		@Override
		public void action() {
			MessageTemplate template = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.QUERY_IF), 
					MessageTemplate.MatchConversationId(QUERYLOGIN)),
					template2 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.REQUEST), 
							MessageTemplate.MatchConversationId(CREER_COMPTE));
			ACLMessage message = myAgent.receive(template), message2 = myAgent.receive(template2);
			if (message != null) {
				String s = message.getContent();
				System.out.println("KBAgent receives " + s);
				ObjectMapper mapper = new ObjectMapper();
				try {
					LoginObject lo = mapper.readValue(s, LoginObject.class);
					Boolean loginSuccess = checkLogin(lo.getLogin(), lo.getMdp());
					String loginMessage = loginSuccess ? LOGINSUCCESS : LOGINFAIL;
					ACLMessage reply = new ACLMessage(ACLMessage.INFORM_IF);
					reply.addReceiver(new AID("androidconn", AID.ISLOCALNAME));
					reply.setConversationId(QUERYLOGIN);
					reply.setContent(loginMessage);
					System.out.println("KBAgent sends " + loginMessage);
					myAgent.send(reply);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			else if (message2 != null) {
				String s = message2.getContent();
				System.out.println("KBAgent receives " + s);
				ObjectMapper mapper = new ObjectMapper();
				try {
					LoginObject lo = mapper.readValue(s, LoginObject.class);
					Boolean loginSuccess = creerCompte(lo.getLogin(), lo.getMdp());
					String loginMessage = loginSuccess ? LOGINSUCCESS : LOGINFAIL;
					ACLMessage reply = new ACLMessage(ACLMessage.INFORM_IF);
					reply.addReceiver(new AID("androidconn", AID.ISLOCALNAME));
					reply.setConversationId(QUERYLOGIN);
					reply.setContent(loginMessage);
					System.out.println("KBAgent sends " + loginMessage);
					myAgent.send(reply);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				block();
			}
		}
	}
	
	private Boolean checkLogin(String login, String mdp) {
		Boolean loginSucess = false;
		String queryString = 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
						"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
						"PREFIX projet:	 <http://projet/>"+
						"ASK" +
						"WHERE { ?pers foaf:identifiant '"+login+"'. ?pers foaf:mdp '"+mdp+"'}";
		Query query = QueryFactory.create(queryString) ;
		//QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query);

		QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
		loginSucess = qexec.execAsk();

		qexec.close();
		return loginSucess;
	}
	
	private Boolean creerCompte(String login, String mdp) {
		Boolean createAccountSucess = true;
		try{
			//model2 = ModelFactory.createDefaultModel();
			//FileOutputStream fos = new FileOutputStream("Z:\\debutProjet\\baseDonnees.txt");
			//com.hp.hpl.jena.rdf.model.Resource nom = model.createResource(prefix+arg0.getParameter(0));
			
			// Check if login exists
			String queryString = 
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
							"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
							"PREFIX projet:	 <http://projet/>"+
							"ASK" +
							"WHERE { ?pers foaf:identifiant '"+ login +"'}";
			Query query = QueryFactory.create(queryString) ;
			//QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query);

			QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
			createAccountSucess = qexec.execAsk() ? false : true;

			qexec.close();
			
			if (createAccountSucess) {
				String nomFichier = "baseDonnees.txt";
				FileWriter f = new FileWriter(nomFichier,true);
				BufferedWriter output = new BufferedWriter(f);
				output.write("projet:"+login +
					 	"\n a foaf:Person ; "+	    
			    "\n foaf:identifiant \""+login+"\";"+ 
				"\n foaf:mdp \""+mdp+"\".");
				
				output.flush();
				output.close();
			}
			return createAccountSucess;
			//model2.write(fis, "N-TRIPLE");
		}
		catch(Exception ex){return false;}

	}
	
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub

		if (arg0.getType() == CREER_EVENT)
		{
			try{
				//model2 = ModelFactory.createDefaultModel();
				//FileOutputStream fos = new FileOutputStream("Z:\\debutProjet\\baseDonnees.txt");
				//com.hp.hpl.jena.rdf.model.Resource nom = model.createResource(prefix+arg0.getParameter(0));
				String nomFichier = "baseDonnees.txt";
				FileWriter f = new FileWriter(nomFichier,true);
				BufferedWriter output = new BufferedWriter(f);
				output.write("\nprojet:"+arg0.getParameter(0) +
					 	"\n a foaf:Person ; "+	    
			    "\n foaf:identifiant \""+arg0.getParameter(1)+"\";"+ 
				"\n foaf:mdp \""+arg0.getParameter(2)+"\".");
				
				output.flush();
				output.close();
				
				//model2.write(fis, "N-TRIPLE");
			}
			catch(Exception ex){}
		}

		else if (arg0.getType() == LOG_EVENT)
		{
			String login = (String) arg0.getParameter(0);
			String mdp = (String) arg0.getParameter(1);

			String queryString = 
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
							"PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
							"PREFIX projet:	 <http://projet/>"+
							"ASK" +
							"WHERE { ?pers foaf:identifiant '"+login+"'. ?pers foaf:mdp '"+mdp+"'}";
			Query query = QueryFactory.create(queryString) ;
			//QueryExecution qexec = QueryExecutionFactory.sparqlService("http://linkedgeodata.org/sparql", query);

			QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
			if (qexec.execAsk()==true)
			{
				Runtime rt = Runtime.instance();
				ProfileImpl p = new ProfileImpl();
				p.setParameter("gui", "true");
				p.setParameter("main", "false");
				ContainerController cc = rt.createAgentContainer(p);
				AgentController dummy = null;
				try {
					dummy = cc.createNewAgent(login,
							"projet.Joueur", null);
					dummy.start();
				} catch (StaleProxyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//ResultSet results = qexec.execSelect() ;
			//QuerySolution sol = results.next();
			//ResultSetFormatter.out(System.out,results);

			qexec.close();
		}
	}

}
