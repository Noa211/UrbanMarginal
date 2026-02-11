package vue;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Arene extends JFrame implements Global {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtSaisie;
	private Controle controle;
	private boolean client;
	
	private Son[] lesSons = new Son[SON.length];
	
	private JPanel jpnMurs;
	public JPanel getJpnMurs() {
		return this.jpnMurs;
	}
	public void setJpnMurs(JPanel jpnMurs) {
		this.jpnMurs.add(jpnMurs);
		this.jpnMurs.repaint();
	}
	
	private JPanel jpnJeu;
	public JPanel getJpnJeu() {
		return this.jpnJeu;
	}
	public void setJpnJeu(JPanel jpnJeu) {
		this.jpnJeu.removeAll();
		this.jpnJeu.add(jpnJeu);
		this.jpnJeu.repaint();
		this.contentPane.requestFocus();
	}
	
	private JTextArea txtChat;
	public String getTxtChat() {
		return txtChat.getText();
	}
	public void setTxtChat(String txtChat) {
		this.txtChat.setText(txtChat);
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}

	/**
	 * Create the frame.
	 */
	public Arene(Controle controle, String typeJeu) {
		this.client = typeJeu.equals(CLIENT);
		setTitle("Arena");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setPreferredSize(new Dimension(800, 600 + 25 + 140));
		this.pack();
		contentPane = new JPanel();
		if (this.client) {
			contentPane.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_KeyPressed(e);
				}
			});
		}
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnJeu.setOpaque(false);
		jpnJeu.setLayout(null);
		contentPane.add(jpnJeu);
		
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, LARGEURARENE, HAUTEURARENE);
		jpnMurs.setOpaque(false);
		jpnMurs.setLayout(null);
		contentPane.add(jpnMurs);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 800, 600);
		String chemin = FONDARENE;
		URL resource = getClass().getClassLoader().getResource(chemin);
		lblFond.setIcon(new ImageIcon(resource));
		contentPane.add(lblFond);
		
		if (this.client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					txtSaisie_KeyPressed(e);
				}
			});
			txtSaisie.setBounds(0, 600, 800, 25);
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10);
		}
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, 625, 800, 140);
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		if (this.client) {
			txtChat.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					contentPane_KeyPressed(e);
				}
			});
		}
		txtChat.setEditable(false);
		jspChat.setViewportView(txtChat);
		
		if (client) {
			for (int k=0 ; k<SON.length ; k++) {
				lesSons[k] = new Son(getClass().getClassLoader().getResource(SON[k])) ;
			}
		}
		
		this.controle = controle;
	}
	
	public void ajoutMurs(Object mur) {
		jpnMurs.add((JLabel)mur);
		jpnMurs.repaint();
	}
	
	public void ajoutJLabelJeu(JLabel jLabel) {
		this.jpnJeu.add(jLabel);
		this.jpnJeu.repaint();
	}
	
	public void ajoutTchat(String phrase) {
		this.txtChat.setText(this.txtChat.getText()+phrase+"\r\n");
		this.txtChat.setCaretPosition(this.txtChat.getDocument().getLength());
	}
	
	private void txtSaisie_KeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!this.txtSaisie.getText().equals("")) {
				this.controle.evenementArene(this.txtSaisie.getText());
				this.txtSaisie.setText("");
				this.contentPane.requestFocus();
			}
		}
	}
	
	private void contentPane_KeyPressed(KeyEvent e) {
		int touche = -1;
		switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				touche = e.getKeyCode();
				break;
			case KeyEvent.VK_DOWN:
				touche = e.getKeyCode();
				break;
			case KeyEvent.VK_LEFT:
				touche = e.getKeyCode();
				break;
			case KeyEvent.VK_RIGHT:
				touche = e.getKeyCode();
				break;
			case KeyEvent.VK_SPACE:
				touche = e.getKeyCode();
				break;
		}
		if (touche != -1) {
			controle.evenementArene(touche);
		}
	}
	
	public void joueSon(int numSon) {
		this.lesSons[numSon].play();
	}
}
