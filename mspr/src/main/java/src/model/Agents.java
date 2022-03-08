package src.model;

import java.util.ArrayList;

public class Agents extends ArrayList<Agent>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Agent> agents;
	
	public Agents(ArrayList<Agent> agents) {
		super();
		this.agents = agents;
	}

	public Agents() {
		this.agents =  new ArrayList<Agent>();
	}

	public ArrayList<Agent> getAgents() {
		return agents;
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}
	
	
	public String toHtml() {
		String html = "<h2> <span class='titre'>Agents :</span> </h2>"
				+ "<div class='tabulationClass'>";
			
		for (Agent agent : agents) {
			
			html += "<p><a class='link' href='" + agent.getId() + ".html'> <span class='colorBlue'>=></span> " + agent.getName() + "</a></p>";
		}
		
		html += "</div>";

		return html;

	}
}
