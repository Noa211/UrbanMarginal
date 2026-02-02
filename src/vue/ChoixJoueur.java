package vue;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import vue.Arene;
import javax.swing.SwingConstants;

public class ChoixJoueur extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtPseudo;
	private JLabel lblPersonnage;
	
	private Arene frmArene;
	private Controle controle;
	
	private int numPerso = 1;
	
	private void lblPrecedent_clic() {
		this.numPerso -= 1;
		if(this.numPerso == 0) {
			this.numPerso = MAXPERSO;
		}
		affichePerso();
	}
	
	private void lblSuivant_clic() {
		this.numPerso += 1;
		if(this.numPerso > MAXPERSO) {
			this.numPerso = 1;
		}
		affichePerso();
	}
	
	private void lblGo_clic() {
		if(this.txtPseudo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "La saisie du pseudo est obligatoire");
			txtPseudo.requestFocus();
		} else {
			controle.evenementChoixJoueur(txtPseudo.getText(), this.numPerso);
		}
	}

	/**
	 * Create the frame.
	 */
	public ChoixJoueur(Controle controle) {
		setTitle("Choice");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(400, 275));
		this.pack();
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(145, 112, 117, 125);
		contentPane.add(lblPersonnage);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(145, 247, 117, 18);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblPrecedent_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblPrecedent.setBounds(69, 141, 25, 51);
		contentPane.add(lblPrecedent);
		
		JLabel lblSuivant = new JLabel("");
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblSuivant.setBounds(302, 141, 25, 51);
		contentPane.add(lblSuivant);
		
		JLabel lblGo = new JLabel("");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				sourisDoigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				sourisNormale();
			}
		});
		lblGo.setBounds(312, 202, 58, 51);
		contentPane.add(lblGo);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		String chemin = FONDCHOIX;
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
		
		this.controle = controle;
		
		affichePerso();
	}
	
	private void affichePerso() {
		String chemin = CHEMINPERSONNAGES+PERSO+numPerso+MARCHE+"1d1"+EXTFICHIERPERSO;
		
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblPersonnage.setIcon(new ImageIcon(resource));
		
	}
	
	private void sourisNormale( ) {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	private void sourisDoigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
}
