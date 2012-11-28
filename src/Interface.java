import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.JSONException;

import java.net.InetAddress;

public class Interface extends JFrame {

	/**
	 * Je ne sais pas ce dont il s'agit mais il semblerait qu'on en ait besoin (sinon il y a des warnings).
	 */
	private static final long serialVersionUID = 1L;
	
	private Pod pod;
	
	static JPanel panel, me, them;
	static JTextField postText, addFriend;
	
	public Interface(Pod pod) {
		this.pod = pod;
		initUI();
	}
	
	public final void initUI() {

		panel = new JPanel();
		getContentPane().add(panel);
		/* Afficher d'abord la zone de post, puis les gens */
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		/* Une zone de texte et un bouton pour ajouter un ami */
		addFriend = new JTextField(40);
		addFriend.setMaximumSize(new Dimension(Integer.MAX_VALUE, addFriend.getMinimumSize().height));
		panel.add(addFriend);
		JButton addButton = new JButton("Add");
		addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(addButton);
		
		/* Une zone de texte et un bouton pour poster */
		postText = new JTextField(40);
		postText.setMaximumSize(new Dimension(Integer.MAX_VALUE, postText.getMinimumSize().height));
		panel.add(postText);
		JButton postButton = new JButton("Post");
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String infoFriend = addFriend.getText();
					addFriend.setText("");
					String[] infoFriendTrie = infoFriend.split(":");
					pod.sendAddFriend(infoFriendTrie[0],InetAddress.getByName(infoFriendTrie[1]),Integer.parseInt(infoFriendTrie[2]));
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Erreur ajout d'ami","Erreur",JOptionPane.ERROR_MESSAGE) ;
					System.out.println("Erreur ajout d'ami");//l'ajout n'a pas été demandé de la bonne façon
				}				
			}
		});

		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/* Ajoute le statut */
				String myMsg = postText.getText();
				try {
					pod.sendMessage(myMsg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JLabel label = new JLabel(myMsg);
				postText.setText("");
				me.add(label);
				/* Et redessine */
				panel.validate();
			}
		});

		

		postButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(postButton);

		/* Un panel horizontal pour les gens */
		JPanel people = new JPanel();
		panel.add(people);
		people.setAlignmentX(Component.LEFT_ALIGNMENT);
		/* Les personnes sont affichées de gauche à droite */
		people.setLayout(new GridLayout());

		/* Moi */
		me = new JPanel();
		me.setBorder(new LineBorder(Color.black));
		/* Mes commentaires sont affichés de haut en bas */
		me.setLayout(new BoxLayout(me, BoxLayout.Y_AXIS));
		me.setAlignmentY(Component.TOP_ALIGNMENT);
		JScrollPane meScroll = new JScrollPane(me);
		people.add(meScroll);

		/* Une petite séparation entre moi et lui */
		//people.add(Box.createRigidArea(new Dimension(5,0)));

		/* Un ami */
		them = new JPanel();
		them.setBorder(new LineBorder(Color.black));
		them.setLayout(new BoxLayout(them, BoxLayout.Y_AXIS));
		them.setAlignmentY(Component.TOP_ALIGNMENT);
		JScrollPane themScroll = new JScrollPane(them);
		people.add(themScroll);
		people.add(themScroll);

		/* De la place pour les autres */
		people.add(Box.createHorizontalGlue());

		/* Le reste de l'interface */
		setTitle(pod.getOwner().getName());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void afficherMessage(String line){
		System.out.println("Message = "+ line);
		them.add(new JLabel(line));
		panel.validate();
	}
	
	public static boolean demandeAmi(String name){
		int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous ajouter " + name +" ?", 
				"Demande d'ami", JOptionPane.YES_NO_OPTION);
		return (reponse == JOptionPane.YES_OPTION);
	}
	
	public static void resultatInvitation(String name, Boolean result){
		JOptionPane.showMessageDialog(null, name + " " + (result ? "vous a" : "ne vous a pas") + " accepté");
	}
	
}
