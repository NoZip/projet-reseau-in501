import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

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
		JLabel label;

		panel = new JPanel();
		getContentPane().add(panel);
		/* Afficher d'abord la zone de post, puis les gens */
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		/* Une zone de texte et un bouton pour ajouter un ami */
		addFriend = new JTextField(40);
		addFriend.setMaximumSize(new Dimension(Integer.MAX_VALUE, addFriend.getMinimumSize().height));
		panel.add(addFriend);
		JButton addButton = new JButton("Add");
		
		/* Une zone de texte et un bouton pour poster */
		postText = new JTextField(40);
		postText.setMaximumSize(new Dimension(Integer.MAX_VALUE, postText.getMinimumSize().height));
		panel.add(postText);
		JButton postButton = new JButton("Post");
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String infoFriend = addFriend.getText();
					String[] infoFriendTrie = infoFriend.split(":");
					pod.sendAddFriend(infoFriendTrie[0],InetAddress.getByName(infoFriendTrie[1]),Integer.parseInt(infoFriendTrie[2]));
				}catch(Exception e){
					;//l'ajout n'a pas été demandé de la bonne façon
				}				
			}
		});

		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/* Ajoute le statut */
				JLabel label = new JLabel("new status " + postText.getText());
				postText.setText("");
				me.add(label);
				/* Et redessine */
				panel.validate();
			}
		});

		
		addButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(addButton);
		postButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(postButton);

		/* Un panel horizontal pour les gens */
		JPanel people = new JPanel();
		panel.add(people);
		people.setAlignmentX(Component.LEFT_ALIGNMENT);
		/* Les personnes sont affichÃ©es de gauche Ã  droite */
		people.setLayout(new BoxLayout(people, BoxLayout.X_AXIS));

		/* Moi */
		me = new JPanel();
		me.setBorder(new LineBorder(Color.black));
		/* Mes commentaires sont affichÃ©s de haut en bas */
		me.setLayout(new BoxLayout(me, BoxLayout.Y_AXIS));
		me.setAlignmentY(Component.TOP_ALIGNMENT);
		people.add(me);

		label = new JLabel("my status1");
		me.add(label);
		label = new JLabel("my status2");
		me.add(label);

		/* Une petite sÃ©paration entre moi et lui */
		people.add(Box.createRigidArea(new Dimension(5,0)));

		/* Un ami */
		them = new JPanel();
		them.setBorder(new LineBorder(Color.black));
		them.setLayout(new BoxLayout(them, BoxLayout.Y_AXIS));
		them.setAlignmentY(Component.TOP_ALIGNMENT);
		people.add(them);

		label = new JLabel("his status1");
		them.add(label);
		label = new JLabel("his status2");
		them.add(label);

		/* De la place pour les autres */
		people.add(Box.createHorizontalGlue());

		/* Le reste de l'interface */
		setTitle("Social");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void afficherMessage(String line){
		them.add(new JLabel(line));
		panel.validate();
	}
	
	
}
