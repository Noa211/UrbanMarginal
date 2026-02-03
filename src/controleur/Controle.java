package controleur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JPanel;

import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.AsyncResponse;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.EntreeJeu;
import vue.Arene;
import vue.ChoixJoueur;

public class Controle implements AsyncResponse, Global {
	
	private EntreeJeu frmEntreeJeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Jeu leJeu;

	public static void main(String[] args) {
		
		new Controle();

	}
	
	/**
	 * Constructeur
	 */
	private Controle() {
		this.frmEntreeJeu = new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}
	
	public void evenementEntreeJeu(String info) {
		
		if(info == SERVEUR) {
			this.frmArene = new Arene();
			this.leJeu = new JeuServeur(this);
			((JeuServeur)this.leJeu).constructionMurs();
			this.frmArene.setVisible(true);
			ServeurSocket serveur = new ServeurSocket(this, 6666);
			this.frmEntreeJeu.dispose();
		} else {
			ClientSocket client = new ClientSocket(this, info, PORT);
		}
	}
	
	public void evenementChoixJoueur(String pseudo, int numPerso) {
		this.frmArene.setVisible(true);
		((JeuClient)this.leJeu).envoi((PSEUDO+STRINGSEPARE+pseudo+STRINGSEPARE+numPerso));
		this.frmChoixJoueur.dispose();
	}

	@Override
	public void reception(Connection connection, String ordre, Object info) {
		// TODO Auto-generated method stub
		switch(ordre) {
		
			case CONNEXION:
				if(!(this.leJeu instanceof JeuServeur)) {
					this.frmChoixJoueur = new ChoixJoueur(this);
					this.frmArene = new Arene();
					this.frmChoixJoueur.setVisible(true);
					this.frmEntreeJeu.dispose();
					this.leJeu = new JeuClient(this);
					this.leJeu.connexion(connection);
				} else {
					this.leJeu.connexion(connection);
				}
				break;
				
			case RECEPTION:
				this.leJeu.reception(connection, info);
				break;
				
			case DECONNEXION:
				break;
		}
	}
	
	public void envoi(Connection connection, Object objet) {
		connection.envoi(objet);
	}
	
	public void evenementJeuServeur(String ordre, Object info) {
		if (ordre == AJOUTMUR) {
			frmArene.ajoutMurs(info);
		} else if (ordre == AJOUTPANELMURS) {
			this.leJeu.envoi((Connection)info, this.frmArene.getJpnMurs());
		}
	}
	
	public void evenementJeuClient(String ordre, Object info) {
		if (ordre == AJOUTPANELMURS) {
			this.frmArene.setJpnMurs((JPanel)info);
		}
	}

}
