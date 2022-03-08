package src.model;

public class Agent {
	private String id;
	private String name;
	private String surname;
	private String mission;
	private String password;
	private String urlPicture;
	private Equipments equipements;
	
	public Agent(String id, String name, String surname, String mission, String password, String urlPicture, Equipments equipements) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.mission = mission;
		this.password = password;
		this.urlPicture = urlPicture;
		this.equipements = equipements;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMission() {
		return mission;
	}

	public void setMission(String mission) {
		this.mission = mission;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrlPicture() {
		return urlPicture;
	}

	public void setUrlPicture(String urlPicture) {
		this.urlPicture = urlPicture;
	}

	public Equipments getEquipements() {
		return equipements;
	}

	public void setEquipements(Equipments equipements) {
		this.equipements = equipements;
	}
	
	public String toHtml() {
		String html = ""
			+ "<div class='topSection'>"
				+ "<div class='identification'><h1>" + this.getName() + " " + this.getSurname() + "</h1></div>"
				+ "<div><img src='" + this.getUrlPicture() + "' alt='Image' width='150px'></div>"
			+ "</div>"
			+ "<h2> <span class='titre'>misSion :</span></h2><div class='tabulationClass'><span class='colorBlue'>=></span> " + this.getMission() + "</div></br>"
			+ "<h2> <span class='titre'>equipementss :</span></h2><div class='tabulationClass'>" + this.getEquipements().toHtml() + "</div></br>";


		return html;
	}
}
