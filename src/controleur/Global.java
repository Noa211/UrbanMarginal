package controleur;

public interface Global {
	/**
	 * N° du port d'écoute du serveur
	 */
	int PORT = 6666;
	/**
	 * Nombre de personnages différents
	 */
	int MAXPERSO = 3;
	/**
	 * Caractère de séparation dans un chemin de fichiers
	 */
	String CHEMINSEPARATOR = "/";
	/**
	 * Chemin du dossier des images de fonds
	 */
	String CHEMINFONDS = "fonds"+CHEMINSEPARATOR;
	/**
	 * Chemin du dossier des images des personnages
	 */
	String CHEMINPERSONNAGES = "personnages"+CHEMINSEPARATOR;
	/**
	 * Chemin de l'image de fond de la vue ChoixJoueur
	 */
	String FONDCHOIX = CHEMINFONDS+"fondchoix.jpg";
	/**
	 * Chemin de l'image de fond de la vue Arene
	 */
	String FONDARENE = CHEMINFONDS+"fondarene.jpg";
	/**
	 * Extension des fichiers des images des personnages
	 */
	String EXTFICHIERPERSO = ".gif";
	/**
	 * Début du nom des images des personnages
	 */
	String PERSO = "perso";
	/**
	 * état marche du personnage
	 */
	String MARCHE = "marche";
	/**
	 * Caractère de séparation dans les chaines transférées
	 */
	String STRINGSEPARE = "~";
	/**
	 * Type de jeu "serveur"
	 */
	String SERVEUR = "serveur";
	/**
	 * Message "connexion" envoyé par la classe Connection
	 */
	String CONNEXION = "connexion";
	/**
	 * Message "réception" envoyé par la classe Connection
	 */
	String RECEPTION = "reception";
	/**
	 * Message "déconnexion" envoyé par la classe Connection
	 */
	String DECONNEXION = "deconnexion";
	/**
	 * Message "pseudo" envoyé pour la création d'un joueur
	 */
	String PSEUDO = "pseudo";
	/**
	 * vie de départ pour tous les joueurs
	 */
	int MAXVIE = 10 ;
	/**
	 * gain de points de vie lors d'une attaque
	 */
	int GAIN = 1 ; 
	/**
	 * perte de points de vie lors d'une attaque
	 */
	int PERTE = 2 ; 
	/**
	 * valeur de la hauteur de l'arene
	 */
	int HAUTEURARENE = 600;
	/**
	 * valeur de la largeur de l'arene
	 */
	int LARGEURARENE = 800;
	/**
	 * valeur de la hauteur du mur
	 */
	int HAUTEURMUR = 35;
	/**
	 * valeur de la largeur du mur
	 */
	int LARGEURMUR = 34;
	
	String CHEMINMURS = "murs"+CHEMINSEPARATOR;
	
	String MUR = CHEMINMURS+"mur.gif";
	
	int NBMURS = 20;
	
	String AJOUTMUR = "ajout mur";
	
	String AJOUTPANELMURS = "ajout panel murs";
	
	int HAUTEURPERSO = 44;
	
	int LARGEURPERSO = 39;
	
	int HAUTEURMESSAGE = 8;
	
	String AJOUTJLABELJEU = "ajout jlabel jeu";
	
	String TOUCHE = "touche";
	
	String MORT = "mort";
	
	int MARGEMESSAGE = 10;
	
	int GAUCHE = 0;
	int DROITE = 1;
	
	String MODIFPANELJEU = "modif panel jeu";
	
	String TCHAT = "tchat";
	
	String AJOUTPHRASE = "ajout phrase";
	
	String MODIFTCHAT = "modif tchat";
	
	String CLIENT = "client";
	
	String ACTION = "action";
	
	int UNPAS = 10;
	
	int NBETAPESMARCHE = 4;
}
