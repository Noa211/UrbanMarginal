package modele;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;
/**
 * Gestion des joueurs
 *
 */
public class Joueur extends Objet implements Global {
	
	/**
	 * pseudo saisi
	 */
	private String pseudo ;
	public String getPseudo() {
		return this.pseudo;
	}
	/**
	 * n° correspondant au personnage (avatar) pour le fichier correspondant
	 */
	private int numPerso ; 
	/**
	 * instance de JeuServeur pour communiquer avec lui
	 */
	private JeuServeur jeuServeur ;
	/**
	 * numéro d'�tape dans l'animation (de la marche, touché ou mort)
	 */
	private int etape ;
	/**
	 * la boule du joueur
	 */
	private Boule boule ;
	/**
	* vie restante du joueur
	*/
	private int vie ; 
	/**
	* tourné vers la gauche (0) ou vers la droite (1)
	*/
	private int orientation ;
	public int getOrientation() {
		return this.orientation;
	}
	
	private JLabel message;
	
	/**
	 * Constructeur
	 */
	public Joueur(JeuServeur serveur) {
		this.jeuServeur = serveur;
		this.vie = MAXVIE;
		this.etape = 1;
		this.orientation = DROITE;
	}

	/**
	 * Initialisation d'un joueur (pseudo et numéro, calcul de la 1ère position, affichage, création de la boule)
	 */
	public void initPerso(String pseudo, int numPerso, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		this.jLabel = new JLabel();
		this.message = new JLabel();
		this.boule = new Boule(this.jeuServeur);
		this.message.setHorizontalAlignment(SwingConstants.CENTER);
		this.message.setFont(new Font("Dialog", Font.PLAIN, 8));
		this.premierePosition(lesMurs, lesJoueurs);
		this.jeuServeur.ajoutJLabelJeuArene(jLabel);
		this.jeuServeur.ajoutJLabelJeuArene(message);
		this.jeuServeur.ajoutJLabelJeuArene(this.boule.getjLabel());
		this.affiche(MARCHE, etape);
		System.out.println("joueur "+pseudo+" - num perso "+numPerso+" créé");
	}

	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 */
	private void premierePosition(ArrayList<Mur> lesMurs, Collection<Joueur> lesJoueurs) {
		jLabel.setBounds(0, 0, LARGEURPERSO, HAUTEURPERSO);
		do {
			posX = (int) Math.round(Math.random() * (LARGEURARENE - LARGEURPERSO));
			posY = (int) Math.round(Math.random() * (HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE));
		}while(this.toucheJoueur(lesJoueurs) || this.toucheMur(lesMurs));
	}
	
	/**
	 * Affiche le personnage et son message
	 */
	public void affiche(String etat, int etape) {
		super.jLabel.setBounds(posX, posY, LARGEURPERSO, HAUTEURPERSO);
		String chemin = CHEMINPERSONNAGES + PERSO + this.numPerso + etat + etape + "d" + this.orientation + EXTFICHIERPERSO;
		URL resource = getClass().getClassLoader().getResource(chemin);
		super.jLabel.setIcon(new ImageIcon(resource));
		this.message.setBounds(posX-MARGEMESSAGE, posY+HAUTEURPERSO, LARGEURPERSO+MARGEMESSAGE, HAUTEURMESSAGE);
		this.message.setText(pseudo+" : "+vie);
		this.jeuServeur.envoiJeuATous();
	}

	/**
	 * Gère une action reçue et qu'il faut afficher (déplacement, tire de boule...)
	 */
	public void action(int action, Collection<Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		if (!this.estMort()) {
			switch (action) {
				case KeyEvent.VK_UP:
					posY = deplace(posY, action, -UNPAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
					break;
				case KeyEvent.VK_DOWN:
					posY = deplace(posY, action, UNPAS, HAUTEURARENE - HAUTEURPERSO - HAUTEURMESSAGE, lesJoueurs, lesMurs);
					break;
				case KeyEvent.VK_LEFT:
					orientation = GAUCHE;
					posX = deplace(posX, action, -UNPAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
					break;
				case KeyEvent.VK_RIGHT:
					orientation = DROITE;
					posX = deplace(posX, action, UNPAS, LARGEURARENE - LARGEURPERSO, lesJoueurs, lesMurs);
					break;
				case KeyEvent.VK_SPACE:
					if(!this.boule.getjLabel().isVisible()) {
						this.boule.tireBoule(this, lesMurs);
					}
					break;
			}
			this.affiche(MARCHE, this.etape);
		}
	}

	/**
	 * Gère le déplacement du personnage
	 */
	private int deplace(int position, // position de départ
			int action, // gauche, droite, haut, bas
			int lepas, // valeur du déplacement (positif ou négatif)
			int max, // valeur à ne pas dépasser
			Collection<Joueur> lesJoueurs, // les joueurs (pour éviter les collisions)
			ArrayList<Mur> lesMurs) { // les murs (pour éviter les collisions)
		int ancpos = position ;
		position += lepas ;
		position = Math.max(position, 0) ;
		position = Math.min(position,  max) ;
		if (action==KeyEvent.VK_LEFT || action==KeyEvent.VK_RIGHT) {
			posX = position ;
		}else{
			posY = position ;
		}
		// controle s'il y a collision, dans ce cas, le personnage reste sur place
		if (toucheJoueur(lesJoueurs) || toucheMur(lesMurs)) {
			position = ancpos ;
		}
		// passe à l'étape suivante de l'animation de la marche
		etape = (etape % NBETAPESMARCHE) + 1 ;
		return position ;
	}
	/**
	 * Contrôle si le joueur touche un des autres joueurs
	 * @return true si deux joueurs se touchent
	 */
	private Boolean toucheJoueur(Collection<Joueur> lesJoueurs) {
		for(Joueur unJoueur : lesJoueurs) {
			if(!this.equals(unJoueur)) {
				if(this.toucheObjet(unJoueur)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gain de points de vie après avoir touché un joueur
	 */
	public void gainVie() {
		this.vie += GAIN;
		affiche(MARCHE, etape);
	}
	
	/**
	 * Perte de points de vie après avoir été touché 
	 */
	public void perteVie() {
		this.vie = Math.max(0, this.vie - PERTE);
		affiche(MARCHE, etape);
	}
	
	/**
	* Contrôle si le joueur touche un des murs
	* @return true si un joueur touche un mur
	*/
	private Boolean toucheMur(ArrayList<Mur> lesMurs) {
		for(Mur unMur : lesMurs) {
			if(this.toucheObjet(unMur)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * vrai si la vie est à 0
	 * @return true si vie = 0
	 */
	public Boolean estMort() {
		return vie == 0;
	}
	
	/**
	 * Le joueur se déconnecte et disparait
	 */
	public void departJoueur() {
		if (super.jLabel != null) {
			super.jLabel.setVisible(false);
			this.message.setVisible(false);
			this.boule.getjLabel().setVisible(false);
			this.jeuServeur.envoiJeuATous();
		}
	}
	
}
