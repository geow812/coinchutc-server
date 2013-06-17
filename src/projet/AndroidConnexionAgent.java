package projet;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.StringWriter;

import org.codehaus.jackson.map.ObjectMapper;


@SuppressWarnings("serial")
public class AndroidConnexionAgent extends Agent {

	private static final String CHAT_ID = "__chat__";
	public static final String DECONNEXION = "__deconnexion__";
	public static final String QUERYLOGIN = "__query_login__";
	public static final String LOGINSUCCESS = "__login_success__";
	public static final String LOGINFAIL = "__login_fail__";
	public static final String CREER_COMPTE = "__creer_compte__";
	public static final String NEW_PLAYER = "__new_player__";
	public static final String LOG_AGENT_NAME = "login";

	private ACLMessage spokenMsg;
	private String login = "";
	private String mdp = "";

	protected void setup() {

		// Add initial behaviours
		addBehaviour(new ParticipantsManager(this));

		// Initialize the message used to convey spoken sentences
		spokenMsg = new ACLMessage(ACLMessage.INFORM);
		spokenMsg.setConversationId(CHAT_ID);

	}
	
	/**
	 * Inner class ParticipantsManager. This behaviour registers as a chat
	 * participant and keeps the list of participants up to date by managing the
	 * information received from the ChatManager agent.
	 */
	class ParticipantsManager extends CyclicBehaviour {
		private static final long serialVersionUID = -4845730529175649756L;
		private MessageTemplate template;

		ParticipantsManager(Agent a) {
			super(a);
		}

		public void action() {
			//String convId = "C-" + myAgent.getLocalName();
			template = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
			MessageTemplate template2 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM), MessageTemplate.MatchContent(DECONNEXION)),
					template3 = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM_IF), MessageTemplate.MatchConversationId(QUERYLOGIN)),
							template4 = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

			// Receives information about people joining and leaving
			// the chat from the ChatManager agent
			ACLMessage msg = myAgent.receive(template), msg2 = myAgent.receive(template2), msg3 = myAgent.receive(template3), msg4 = myAgent.receive(template4);
			if (msg != null) {
				//query the LogAgent to search an account
				login = msg.getSender().getLocalName().substring(5);
				System.out.println("Login: " + login);
				mdp = msg.getContent();
				ACLMessage queryLogin = new ACLMessage(ACLMessage.QUERY_IF);
				queryLogin.setConversationId(QUERYLOGIN);
				queryLogin.addReceiver(new AID(LOG_AGENT_NAME, AID.ISLOCALNAME));
				LoginObject lo = new LoginObject(login, mdp);
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				try {
					mapper.writeValue(sw, lo);
					queryLogin.setContent(sw.toString());
					System.out.println("Manager sends " + sw.toString());
					myAgent.send(queryLogin);
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
				//Boolean loginSuccess = checkLogin(login, mdp);
				
			} else if (msg2 != null) {
				//AID player = msg2.getSender();
				//participants.remove(player);
				//notifyParticipantsChanged();
				
			}
			else if (msg3 != null) {
				// response from LogAgent if the login succeeded
				String answer = msg3.getContent();
				System.out.println("Manager receives " + answer);
				//System.out.println(LOGINSUCCESS + " " + answer);
				ACLMessage loginReply = new ACLMessage(ACLMessage.INFORM_IF);
				loginReply.setConversationId("Conn-" + login);
				System.out.println("Manager sends to receiver " + "Conn-" + login);
				loginReply.addReceiver(new AID("Conn-" + login, AID.ISLOCALNAME));
				
				if (answer.equalsIgnoreCase(LOGINSUCCESS)) {
					System.out.println("Login succeeded!");
					loginReply.setContent(LOGINSUCCESS);

				}
				else {
					System.out.println("Login failed!");
					loginReply.setContent(LOGINFAIL);
				}
				myAgent.send(loginReply);
			}
			else if (msg4 != null) {
				// request LogAgent to create an account
				login = msg4.getSender().getLocalName().substring(5);
				mdp = msg4.getContent();
				ACLMessage queryCreerCompte = new ACLMessage(ACLMessage.REQUEST);
				queryCreerCompte.setConversationId(CREER_COMPTE);
				queryCreerCompte.addReceiver(new AID(LOG_AGENT_NAME, AID.ISLOCALNAME));
				LoginObject lo = new LoginObject(login, mdp);
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				try {
					mapper.writeValue(sw, lo);
					queryCreerCompte.setContent(sw.toString());
					System.out.println("Manager sends " + sw.toString());
					myAgent.send(queryCreerCompte);
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
}