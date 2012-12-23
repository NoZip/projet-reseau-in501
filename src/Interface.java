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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.net.InetAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Classe gérant l'interface.
 */
public class Interface extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Pod pod;
	
	static JPanel panel, me, them, ami;
	static JTextField postText, addFriend;
	static JComboBox<UserProfile> amiListe;
	
	/**
	* Création d'une instance de Interface.
	* @param pod Pod auquel est lié l'interface.
	*/
	public Interface(Pod pod) {
		this.pod = pod;
		initUI();
	}
	
	public final void initUI() {
		
		amiListe = new JComboBox<UserProfile>(); //liste déroulante servant à stocker les amis.

		panel = new JPanel(); //panel principal servant à stocker tous les autres éléments.
		getContentPane().add(panel); //On le rajoute à la fenêtre.
		
		/* Défini les caractéristiques du panel principal. */
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
		
		//Action associée au bouton add.
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					String infoFriend = addFriend.getText(); //on récupère le texte
					addFriend.setText("");//on l'enlève
					String[] infoFriendTrie = infoFriend.split(":");//on recupere l'adresse et le port.
					
					//création du podLocation correspondant.
					PodLocation friendLocation = new PodLocation(InetAddress.getByName(infoFriendTrie[0]),
																 Integer.parseInt(infoFriendTrie[1]));
					
					//création du podLocation correspondant à mon adresse.
					PodLocation myLocation = new PodLocation(InetAddress.getLocalHost(),pod.getListeningPort());
					
					//on vérifie si l'adresse est valable. 
					if(pod.hasPendingFriend(friendLocation))
						afficherErreur("Inivtation déjà envoyé (en attente de réponse).");
					else if(pod.hasFriend(friendLocation))
						afficherErreur("Ami déjà ajouté.");
					else if(myLocation.equals(friendLocation))
						afficherErreur("Vous ne pouvez pas vous ajoutez.");
					else
						pod.addPendingFriend(friendLocation);
				}catch(Exception e){
					//l'ajout n'a pas été demandé de la bonne façon
					afficherErreur("Erreur ajout d'ami. \nUsage : w.x.y.z:p avec w.x.y.z adresse ip et p le port.");
				}				
			}
		});

		//action associée au bouton post.
		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String myMsg = postText.getText(); //on récupère le text
				postText.setText("");
				Message message = new Message(myMsg, new Date()); //on crée l'objet message correspondant.
				pod.addMessage(message); //on le rajoute au pod.
				JLabel label = new JLabel(myMsg); //on crée l'objet servant à le stocker dans l'interface.
				me.add(label); //on l'affiche
				panel.validate();
			}
		});
		
		//action associée au bouton post image.
		postImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				JFileChooser choix = new JFileChooser(); //on demande où se trouve l'image;
				int retour=choix.showOpenDialog(panel); //code de retour
				
				if(retour==JFileChooser.APPROVE_OPTION){ //si un fichier a été choisi
					try {			   
					   JLabel imagePanel = new JLabel(); //on crée l'objet stockant l'image.
					   ImageIcon image = new ImageIcon(choix.getSelectedFile().getAbsolutePath()); //on crée l'image
					   imagePanel.setText(null); 
					   imagePanel.setIcon(image);
					   me.add(imagePanel); //on rajoute l'image
					   panel.validate();
					   pod.addImage((new File(choix.getSelectedFile().getAbsolutePath())).toURI().toURL()); //on envoie l'image
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		});

		

		postButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(postButton);
		postImageButton.setAlignmentX(Component.LEFT_ALIGNMENT);
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

		/* Les ami */
		them = new JPanel();
		them.setBackground(Color.WHITE);
		them.setBorder(new LineBorder(Color.black));
		them.setLayout(new BoxLayout(them, BoxLayout.Y_AXIS));
		them.setAlignmentY(Component.TOP_ALIGNMENT);
		JScrollPane themScroll = new JScrollPane(them);
		people.add(themScroll);
		them.add(new JLabel("Les posts de mes amis :"));
		panel.validate();

		//la liste des amis
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
					UserProfile oldFriend = (UserProfile) amiListe.getSelectedItem(); //on récupère l'ami sélectionné dans la liste
					pod.sendCommand(pod.getFriend(oldFriend.getUUID()).getLocation().getAddress(), 
							pod.getFriend(oldFriend.getUUID()).getLocation().getPort(), 
							"DEL", 
							pod.getOwner().toJSON()); //on envoie la commande "DEL"
					pod.removeFriend(oldFriend.getUUID()); //on le retire du pod.
					amiListe.removeItem(oldFriend); //on le retire de l'interface
					afficherSuppression(oldFriend.getName(), false); //on indique la suppression.
				} catch (JSONException e) {
					afficherErreur("Erreur suppression d'ami.");
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
	
	/**
	* Rajouter un ami dans la liste déroulante.
	* @param newFriend Ami à rajouter.
	*/
	public void afficherAmi(UserProfile newFriend){
		amiListe.addItem(newFriend);
		panel.validate();
	}
	
	/**
	* Affiche le message d'un ami.
	* @param line Message à ajouter.
	*/
	public void afficherMessage(String line){
		them.add(new JLabel(line));
		panel.validate();
	}
	
	/**
	* Supprime un ami dans la liste déroulante.
	* @param oldFriend Ami à supprimer.
	*/
	public static void supprimerAmi(UserProfile oldFriend){
		amiListe.removeItem(oldFriend);
		amiListe.validate();
		panel.validate();
	}
	
	/**
	* Affiche une demande d'ami.
	* @param name Nom de l'ami ayant envoyé la demande.
	* @return true si la demande est accepté, false sinon.
	*/
	public static boolean demandeAmi(String name){
		int reponse = JOptionPane.showConfirmDialog(null, "Voulez-vous ajouter " + name +" ?", 
				"Demande d'ami", JOptionPane.YES_NO_OPTION);
		return (reponse == JOptionPane.YES_OPTION);
	}
	
	/**
	 * Affiche le résultat d'une demande d'ami.
	 * @param name Nom de l'ami à qui a été envoyé la demande;
	 * @param result Résultat de la demande.
	 */
	public static void resultatInvitation(String name, Boolean result){
		JOptionPane.showMessageDialog(null, name + " " + (result ? "vous a" : "ne vous a pas") + " accepté");
	}
	
	/**
	 * Indique une suppression.
	 * @param name Nom de l'ami supprimé/qui vous a supprimé.
	 * @param result t si vous avez supprimé, false si on vous a supprimé.
	 */
	public static void afficherSuppression(String name, Boolean result){
		JOptionPane.showMessageDialog(null, name + " " + (result ? "vous a" : "a bien été") + " supprimé.");
	}
	
	/**
	 * Affiche une erreur.
	 * @param erreurMsg Chaîne de charactère décrivant l'erreur.
	 */
	public void afficherErreur(String erreurMsg){
		JOptionPane.showMessageDialog(null, erreurMsg,"Erreur",JOptionPane.ERROR_MESSAGE) ;
	}
	
	/**
	 * Affiche une image reçu.
	 * @param image Image à afficher.
	 */
	public void afficherImage(ImageIcon image){
		JLabel imagePanel = new JLabel();
		imagePanel.setText(null); 
		imagePanel.setIcon(image);
		them.add(imagePanel);
		panel.validate();
	}

	/**
	 * Action à effectuer à la fermeture du programme.
	 */
	public void close(){
		try {
			
			List<User> f = pod.getFriendList(); //récupère la liste d'ami
			
			//on la stocke dans un JSONArray
			JSONArray t = new JSONArray();
			for(int i = 0 ; i < f.size() ; i++)
				t.put(i, f.get(i).getLocation());
			
			//On récupère les amis qui était dans l'ancien fichier stocker la liste d'ami mais qui n'ont pas répondu.
			File oldFile = new File(pod.getOwner().getName() + ".ami.social "); //on récupère le fichier
			if(oldFile.isFile()){ //s'il existe
				try {
					//on récupère son contenu
					FileInputStream fileReader= new FileInputStream(oldFile);
					byte[] tmp = new byte[fileReader.available()];
					fileReader.read(tmp);
					fileReader.close();
					
					JSONArray oldFriend = new JSONArray( new String(tmp) );
					
					for(int i = 0 ; i < oldFriend.length() ; i++) {
						String[] infoFriendTrie = ((String) oldFriend.get(i)).split(":");
						infoFriendTrie[0] = (infoFriendTrie[0].split("/"))[1];
						PodLocation friendLocation = new PodLocation(InetAddress.getByName(infoFriendTrie[0]),
																 	Integer.parseInt(infoFriendTrie[1]));
						if(pod.hasPendingFriend(friendLocation))
							t.put(t.length()+1, friendLocation);	
					}
					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
	
			// On crée le fichier 
			FileWriter fstream = new FileWriter(pod.getOwner().getName() + ".ami.social ");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(t.toString());
			out.close();
	
			//On indique la déconnexion à tous nos amis.
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
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}

/**
 * Permet de mettre en place l'action à l'arrêt du programme.
 */
class CloseWindow extends WindowAdapter {
	
	protected Interface in;
	
	/**
	 * Création de l'objet CloseWindow
	 * @param i L'interface auquel il est lié.
	 */
	public CloseWindow(Interface i){
		in = i;
	}
	
	/**
	 * Action à effectuer lors de l'arrêt du programme.
	 */
	public void windowClosing(WindowEvent e) {
		in.close();
	}
	
}
