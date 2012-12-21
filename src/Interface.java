import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.InetAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Interface extends JFrame {

	/**
	 * Je ne sais pas ce dont il s'agit mais il semblerait qu'on en ait besoin (sinon il y a des warnings).
	 */
	private static final long serialVersionUID = 1L;
	
	private Pod pod;
	
	static JPanel panel, me, them, ami;
	static JTextField postText, addFriend;
	static JComboBox<UserProfile> amiListe;
	
	public Interface(Pod pod) {
		this.pod = pod;
		initUI();
	}
	
	public final void initUI() {
		
		amiListe = new JComboBox<UserProfile>();

		panel = new JPanel();
		getContentPane().add(panel);
		/* Afficher d'abord la zone de post, puis les gens */
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);

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
		JButton postImageButton = new JButton("Post an image.");
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String infoFriend = addFriend.getText();
					addFriend.setText("");
					String[] infoFriendTrie = infoFriend.split(":");
					PodLocation friendLocation = new PodLocation(InetAddress.getByName(infoFriendTrie[0]),
																 Integer.parseInt(infoFriendTrie[1]));
					PodLocation myLocation = new PodLocation(InetAddress.getLocalHost(),pod.getListeningPort());
					if(pod.hasPendingFriend(friendLocation))
						afficherErreur("Inivtation déjà envoyé (en attente de réponse).");
					else if(pod.hasFriend(friendLocation))
						afficherErreur("Ami déjà ajouté.");
					else if(myLocation.equals(friendLocation))
						afficherErreur("Vous ne pouvez pas vous ajoutez.");
					else
						pod.addPendingFriend(friendLocation);
				}catch(Exception e){
					afficherErreur("Erreur ajout d'ami");
					System.out.println("Erreur ajout d'ami");//l'ajout n'a pas été demandé de la bonne façon
				}				
			}
		});

		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/* Ajoute le statut */
				String myMsg = postText.getText();
				Message message = new Message(myMsg, new Date());
				pod.addMessage(message);
				JLabel label = new JLabel(myMsg);
				postText.setText("");
				me.add(label);
				/* Et redessine */
				panel.validate();
			}
		});
		
		postImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				JFileChooser choix = new JFileChooser();
				int retour=choix.showOpenDialog(panel);
				
				if(retour==JFileChooser.APPROVE_OPTION){
					try {			   
					   JLabel imagePanel = new JLabel();
					   ImageIcon image = new ImageIcon(choix.getSelectedFile().getAbsolutePath());
					   imagePanel.setText(null); 
					   imagePanel.setIcon(image);
					   me.add(imagePanel);
					   panel.validate();
					   pod.addImage((new File(choix.getSelectedFile().getAbsolutePath())).toURI().toURL());
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});

		

		postButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(postButton);
		postButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(postImageButton);

		/* Un panel horizontal pour les gens */
		JPanel people = new JPanel();
		panel.add(people);
		people.setAlignmentX(Component.LEFT_ALIGNMENT);
		/* Les personnes sont affichées de gauche à droite */
		people.setLayout(new GridLayout(1,3));

		/* Moi */
		me = new JPanel();
		me.setBackground(Color.WHITE);
		me.setBorder(new LineBorder(Color.black));
		/* Mes commentaires sont affichés de haut en bas */
		me.setLayout(new BoxLayout(me, BoxLayout.Y_AXIS));
		me.setAlignmentY(Component.TOP_ALIGNMENT);
		JScrollPane meScroll = new JScrollPane(me);
		people.add(meScroll);
		me.add(new JLabel("Mes posts :"));
		panel.validate();

		/* Un ami */
		them = new JPanel();
		them.setBackground(Color.WHITE);
		them.setBorder(new LineBorder(Color.black));
		them.setLayout(new BoxLayout(them, BoxLayout.Y_AXIS));
		them.setAlignmentY(Component.TOP_ALIGNMENT);
		JScrollPane themScroll = new JScrollPane(them);
		people.add(themScroll);
		them.add(new JLabel("Les posts de mes amis :"));
		panel.validate();

		ami = new JPanel();
		ami.setBackground(Color.WHITE);
		ami.setBorder(new LineBorder(Color.black));
		ami.setLayout(new BoxLayout(ami, BoxLayout.Y_AXIS));
		ami.setAlignmentY(Component.TOP_ALIGNMENT);
		JScrollPane amiScroll = new JScrollPane(ami);
		people.add(amiScroll);
		ami.add(new JLabel("Mes amis :"));
		ami.add(amiListe);
		
		JButton deleteButton = new JButton("Supprimer");
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					UserProfile oldFriend = (UserProfile) amiListe.getSelectedItem();
					pod.sendCommand(pod.getFriend(oldFriend.getUUID()).getLocation().getAddress(), 
							pod.getFriend(oldFriend.getUUID()).getLocation().getPort(), 
							"DEL", 
							pod.getOwner().toJSON());
					pod.removeFriend(oldFriend.getUUID());
					amiListe.removeItem(oldFriend);
					afficherSuppression(oldFriend.getName(), false);
				} catch (JSONException e) {
					System.out.println("La suppresion a échoué.");
					e.printStackTrace();
				}
			}
		});
		ami.add(deleteButton);
		
		panel.validate();

		/* Le reste de l'interface */
		setTitle(pod.getOwner().getName());
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void afficherAmi(UserProfile newFriend){
		amiListe.addItem(newFriend);
		panel.validate();
	}
	
	public void afficherMessage(String line){
		System.out.println("Message = "+ line);
		them.add(new JLabel(line));
		panel.validate();
	}
	
	public static void supprimerAmi(UserProfile oldFriend){
		amiListe.removeItem(oldFriend);
		amiListe.validate();
		ami.validate();
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
	
	public static void afficherSuppression(String name, Boolean result){
		JOptionPane.showMessageDialog(null, name + " " + (result ? "vous a" : "a bien été") + " supprimé.");
	}
	
	public void afficherErreur(String erreurMsg){
		JOptionPane.showMessageDialog(null, erreurMsg,"Erreur",JOptionPane.ERROR_MESSAGE) ;
	}
	
	public void afficherImage(ImageIcon image){
		JLabel imagePanel = new JLabel();
		imagePanel.setText(null); 
		imagePanel.setIcon(image);
		them.add(imagePanel);
		panel.validate();
	}

	public void close(){
		try {
			
			List<User> f = pod.getFriendList();
			JSONArray t = new JSONArray();
			for(int i = 0 ; i < f.size() ; i++)
				t.put(i, f.get(i).getLocation());
	
			// Create file 
			FileWriter fstream = new FileWriter(pod.getOwner().getName() + ".ami.social ");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(t.toString());
			//Close the output stream
			out.close();
	
			
			Iterator<User> i = f.iterator();
			while(i.hasNext()) {
				User tmp = i.next();
				pod.sendCommand(tmp.getLocation().getAddress(), 
								tmp.getLocation().getPort(), 
								"DEL", 
								pod.getOwner().toJSON());
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){//Catch exception if any
			 System.err.println("Error: " + e.getMessage());
		}
	}
	
}

class CloseWindow extends WindowAdapter {
	
	protected Interface in;
	
	public CloseWindow(Interface i){
		in = i;
	}
	
	public void windowClosing(WindowEvent e) {
		in.close();
	}
	
}
