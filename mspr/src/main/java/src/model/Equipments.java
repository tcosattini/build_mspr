package src.model;

import java.util.ArrayList;
import java.util.Iterator;

public class Equipments extends ArrayList<String> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ArrayList<String> equipements;

	public Equipments(ArrayList<String> equipements) {
		super();
		this.equipements = equipements;
	}

	public Equipments() {
		this.equipements = new ArrayList<String>();
	}

	public ArrayList<String> getEquipements() {
		return equipements;
	}

	public void setEquipements(ArrayList<String> equipements) {
		this.equipements = equipements;
	}
	
	public String toHtml(){
		String html = "";
		
		for (String String : equipements) {
			html += "<p> <span class='colorBlue'>=></span>  " + String.toString() + "</p>";
		}
		
		return html;
  	}
}
