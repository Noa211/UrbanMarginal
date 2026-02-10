package modele;

import java.net.URL;
import java.util.Collection;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import controleur.Global;

/**
 * Gestion de la boule
 *
 */
public class Boule extends Objet implements Global, Runnable{

	/**
	 * instance de JeuServeur pour la communication
	 */
	private JeuServeur jeuServeur ;
	private Joueur attaquant;
	private Collection lesMurs;
	
	/**
	 * Constructeur
	 */
	public Boule(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		super.jLabel = new JLabel();
		super.jLabel.setVisible(false);
		URL resource = getClass().getClassLoader().getResource(BOULE);
		super.jLabel.setIcon(new ImageIcon(resource));
		super.jLabel.setBounds(0, 0, LARGEURBOULE, HAUTEURBOULE);
	}
	
	/**
	 * Tire d'une boule
	 */
	public void tireBoule(Joueur attaquant, Collection<Mur> lesMurs) {
		this.attaquant = attaquant;
		this.lesMurs = lesMurs;
		this.posY = this.attaquant.getPosY() + (HAUTEURPERSO / 2);
		if (this.attaquant.getOrientation() == GAUCHE) {
			this.posX = this.attaquant.getPosX() - LARGEURBOULE - 1;
		} else {
			this.posX = this.attaquant.getPosX() + LARGEURPERSO + 1;
		}
		
		new Thread(this).start();
		
	}

	@Override
	public void run() {
		this.attaquant.affiche(MARCHE, 1);
		super.jLabel.setVisible(true);
		Joueur victime = null;
		int lePas;
		if (attaquant.getOrientation() == GAUCHE) {
			lePas = - UNPAS;
		} else {
			lePas = UNPAS;
		}
		do {
			posX += lePas;
			jLabel.setBounds(posX, posY, LARGEURBOULE, HAUTEURBOULE);
			// envoi de la nouvelle zone de jeu à tous (pour que tous voient la boule avancer)
			this.jeuServeur.envoiJeuATous();
			// récupère la collection actuelle de joueurs
			Collection lesJoueurs = this.jeuServeur.getLesJoueurs();
			// récupération de l'éventuelle victime
			victime = (Joueur)super.toucheCollectionObjets(lesJoueurs);
		} while (posX>=0 && posX<=LARGEURARENE && this.toucheCollectionObjets(lesMurs)==null && victime==null);
		
		if(victime != null && !victime.estMort()) {
			victime.perteVie();
			attaquant.gainVie();
			// joue l'animation de la victime blessée
			for(int k=1 ; k<=NBETAPESTOUCHE ; k++) {
				victime.affiche(TOUCHE, k);
				pause(80, 0);
			}
			// contrôle si la victime est morte
			if(victime.estMort()) {
				// joue l'animation de la mort
				for(int k=1 ; k<=NBETAPESMORT ; k++) {
					victime.affiche(MORT, k);
					pause(80, 0);
				}
			} else {
				// remettrele joueur dans la position de repos (marche)
				victime.affiche(MARCHE, 1);
			}
		}
		// rendre à nouveau la boule invisible
		this.jLabel.setVisible(false);
		// envoyer le nouveau jeu à tous
		this.jeuServeur.envoiJeuATous();
		
	}
	
	/**
	 * fais une pause (bloque le processus) d'une durée précise
	 * @param millis millisecondes
	 * @param nanos nanosecondes
	 */
	private void pause(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException e) {
			System.out.println("erreur pause");
		}
	}

	
}
