package vue;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import vue.Arene;
import vue.ChoixJoueur;

public class EntreeJeu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIp;
	
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	
	private void btnStart_clic() {
		this.frmArene = new Arene();
		this.frmArene.setVisible(true);
		this.dispose();
	}
	
	private void btnExit_clic() {
		System.exit(0);
	}
	
	private void btnConnect_clic() {
		this.frmChoixJoueur = new ChoixJoueur();
		this.frmChoixJoueur.setVisible(true);
		this.dispose();
	}

	/**
	 * Create the frame.
	 */
	public EntreeJeu() {
		setTitle("Urban Marginal");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 302, 159);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStart = new JLabel("Start a server :");
		lblStart.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblStart.setBounds(10, 9, 107, 20);
		contentPane.add(lblStart);
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStart_clic();
			}
		});
		btnStart.setBounds(194, 10, 84, 20);
		contentPane.add(btnStart);
		
		JLabel lblServer = new JLabel("Connect an existing server :");
		lblServer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblServer.setBounds(10, 36, 166, 20);
		contentPane.add(lblServer);
		
		JLabel lblIp = new JLabel("IP server :");
		lblIp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblIp.setBounds(10, 66, 62, 20);
		contentPane.add(lblIp);
		
		txtIp = new JTextField();
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(80, 68, 96, 18);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect_clic();
			}
		});
		btnConnect.setBounds(194, 67, 84, 20);
		contentPane.add(btnConnect);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExit_clic();
			}
		});
		btnExit.setBounds(194, 92, 84, 20);
		contentPane.add(btnExit);

	}
}
