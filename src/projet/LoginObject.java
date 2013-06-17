package projet;

public class LoginObject {
	private String login = "";
	private String mdp = "";
	public LoginObject(String login, String mdp) {
		this.setLogin(login);
		this.setMdp(mdp);
	}
	public LoginObject() {}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	

}
