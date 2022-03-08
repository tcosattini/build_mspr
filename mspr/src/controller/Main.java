package src.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.StandardSocketOptions;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import src.model.Agent;
import src.model.Agents;
import src.model.Equipments;

public class Main {
	static String urlRessourcesPath = "resources";

	public static void createAgentsHtml(Agents agents) {
		FileOutputStream fop = null;
		File file;
		String contenu = agents.toHtml();

		try {

			file = new File(urlRessourcesPath + "/view/index.html");

			fop = new FileOutputStream(file);

			// creer le fichier s'il n'existe pas
			if (!file.exists()) {
				file.createNewFile();
			}

			// convertir en donnees binaires
			contenu = "<html><head>"
					+ "<meta charset='utf-8' />"
					+ "<title>MPSR - GO Securi</title>"
					+ "<link href='style.css' rel='stylesheet'>"
					+ "</head>"
					+ "<body>"+contenu+"</body>"
					+ "</html>";
			byte[] contenuEnBytes = contenu.getBytes();

			fop.write(contenuEnBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Agents getAgentsFromApi() {
		FileInputStream fop = null;
		File file;

		int ch;
		String string = "";

		String urlStaff = "https://gitlab.com/sashalarrieu/go-securi-data/-/raw/main/resources/data/staff.txt";
		OkHttp okHttp = new OkHttp();
		CopyOnWriteArrayList<Agent> list = new CopyOnWriteArrayList<>();

		try {
			String res = okHttp.run(urlStaff);

			PrintWriter printWriter = new PrintWriter(urlRessourcesPath + "/data/staff.txt");
			printWriter.print(res);
			printWriter.close();

			file = new File(urlRessourcesPath + "/data/staff.txt");
			fop = new FileInputStream(file);

			// Recuperer les donnees du fichier s'il existe
			if (file.exists()) {

			    while ((ch = fop.read()) != -1) {
			        string += ((char) ch);
			    }

			    String[] listeIdAgents = string.split("\n");

				fop.close();

				ArrayList<Thread> listThread = new ArrayList<>();

			    for (String idAgent : listeIdAgents) {
					Thread thread = new Thread(new Runnable() {
						@Override
						public void run() {
							//Faire la requête ici
							try {
								Agent agent = getAgentFromApi(idAgent);
								createAgentHtml(agent);
								list.add(agent);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});

					thread.start();
					listThread.add(thread);
				}

				for (Thread thread : listThread) {
					thread.join();
				}


			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Agents agents = new Agents();

		for (Agent ag: list) {
			agents.add(ag);
		}
		return agents;
	}


	
	public static void createAgentHtml(Agent agent) {
		FileOutputStream fop = null;
		File file;
		String contenu = agent.toHtml();

		try {

			file = new File(urlRessourcesPath + "/view/"+ agent.getId() + ".html");
			fop = new FileOutputStream(file);


			// creer le fichier s'il n'existe pas
			if (!file.exists()) {
				file.createNewFile();
			}


			// convertir en donnees binaires
			contenu = "<html><head>"
					+ "<meta charset='utf-8' />"
					+ "<title>MPSR - GO Securi</title>"
					+ "<link href='style.css' rel='stylesheet'>"
					+ "</head>"
					+ "<body>"+contenu+"</body>"
					+ "</html>";
			byte[] contenuEnBytes = contenu.getBytes();

			fop.write(contenuEnBytes);
			fop.flush();
			fop.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Agent getAgentFromApi(String idAgent) {

		FileInputStream fop = null;
		File file;
		Agent agent = null;
		int ch;
		String string = "";

		String urlAgent = "https://gitlab.com/sashalarrieu/go-securi-data/-/raw/main/resources/data/agents/" + idAgent + ".txt";
		String urlEquipements = "https://gitlab.com/sashalarrieu/go-securi-data/-/raw/main/resources/data/liste.txt/";
		OkHttp okHttp = new OkHttp();

		try {
			String res = okHttp.run(urlAgent);

			PrintWriter printWriter = new PrintWriter(urlRessourcesPath + "/data/agents/" + idAgent + ".txt");
			printWriter.print(res);
			printWriter.close();


			res = okHttp.run(urlEquipements);

			printWriter = new PrintWriter(urlRessourcesPath + "/data/liste.txt/");
			printWriter.print(res);
			printWriter.close();

			file = new File(urlRessourcesPath + "/data/agents/" + idAgent + ".txt");
			fop = new FileInputStream(file);

			// Recuperer les donnees du fichier s'il existe
			if (file.exists()) {
			    while ((ch = fop.read()) != -1) {
			        string += ((char) ch);
			    }

			    String id = string.split("\n")[0];
			    String name = string.split("\n")[1];
			    String surname = string.split("\n")[2];
			    String mission = string.split("\n")[3];
			    String password = string.split("\n")[4];
			    String urlPicture = "https://gitlab.com/sashalarrieu/go-securi-data/-/raw/main/resources/image/" + id + ".jpg";


			    Equipments equipements = new Equipments();
			    ArrayList <String> liste = new ArrayList<String>();

				String eqs = string.split("\n\n")[1];
				String[] eqsK = eqs.split("\n");
				String[] listeEquipement = res.split("\n");
				ArrayList<String> listeEquipementK = new ArrayList<>();
				ArrayList<String> listeEquipementV = new ArrayList<>();
				ArrayList<String> str = new ArrayList<>();

				for (String equipementKV : listeEquipement) {
					listeEquipementK.add(equipementKV.split(" ")[0]);
				}

				for (String equipementKV : listeEquipement) {
					String value = "";
					String[] listeKV = equipementKV.split(" ");
					for(String keyValue : listeKV){
						if(keyValue != listeKV[0]){
							value += keyValue + " ";
						}
					}
					listeEquipementV.add(value);
				}

				for (int i=0; i<eqsK.length; i++) {
					for (String equipementK : listeEquipementK) {
						if (eqsK[i].equals(equipementK)) {
							str.add(listeEquipementV.get(i));
						}
					}
				}

			    for (String st : str) {
			    	liste.add(st);
				}

			    equipements.setEquipements(liste);

			    agent = new Agent(id, name, surname, mission, password, urlPicture, equipements);

				fop.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return agent;
	}

	public static void main(String[] args) throws IOException {

		Agents agents = new Agents();
		
		agents.setAgents(getAgentsFromApi());
		
		createAgentsHtml(agents);

		System.exit(0);
	}
}