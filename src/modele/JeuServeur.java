package modele;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.swing.JLabel;

import controleur.Controle;
import outils.connexion.Connection;
import controleur.Global;
import modele.Mur;

/**
 * Gestion du jeu côté serveur
 *
 */
public class JeuServeur extends Jeu implements Global {

	/**
	 * Collection de murs
	 */
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	/**
	 * Collection de joueurs
	 */
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();
	public Collection<Joueur> getLesJoueurs() {
		return this.lesJoueurs.values();
	}
	/**
	 * Constructeur
	 */
	public JeuServeur(Controle controle) {
		this.controle = controle;
	}
	
	@Override
	public void connexion(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] message = ((String)info).split(STRINGSEPARE);
		
		switch(message[0]) {
			case PSEUDO:
				controle.evenementJeuServeur(AJOUTPANELMURS, connection);
				String pseudo = message[1];
				Integer numPerso = Integer.parseInt(message[2]);
				this.lesJoueurs.get(connection).initPerso(pseudo, numPerso, this.lesJoueurs.values(), this.lesMurs);
				String premierMessage = "*** "+pseudo+" vient de se connecter ***";
				this.controle.evenementJeuServeur(AJOUTPHRASE, premierMessage);
				break;
			case TCHAT:
				String phrase = message[1];
				phrase = this.lesJoueurs.get(connection).getPseudo()+" > "+phrase;
				this.controle.evenementJeuServeur(AJOUTPHRASE, phrase);
				break;
			case ACTION:
				int action = Integer.parseInt(message[1]);
				this.lesJoueurs.get(connection).action(action, this.lesJoueurs.values(), this.lesMurs);
		}
	}
	
	@Override
	public void deconnexion(Connection connection) {
		this.lesJoueurs.get(connection).departJoueur();
		this.lesJoueurs.remove(connection);
	}

	/**
	 * Envoi d'une information vers tous les clients
	 * fais appel plusieurs fois à l'envoi de la classe Jeu
	 */
	public void envoi(Object info) {
		for (Connection connection : this.lesJoueurs.keySet()) {
			super.envoi(connection, info);
		}
	}

	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for (int i = 0; i < NBMURS; i++) {
			this.lesMurs.add(new Mur());
			this.controle.evenementJeuServeur(AJOUTMUR, lesMurs.get(lesMurs.size()-1).getjLabel());
		}
	}
	
	public void ajoutJLabelJeuArene(JLabel jLabel) {
		this.controle.evenementJeuServeur(AJOUTJLABELJEU, jLabel);
	}
	
	public void envoiJeuATous() {
		for(Connection connection : this.lesJoueurs.keySet()) {
			this.controle.evenementJeuServeur(MODIFPANELJEU, connection);
		}
	}
}
