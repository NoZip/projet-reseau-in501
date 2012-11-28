public class Start {

	public static void main(String[] args){
		System.out.println("Salut");
		Pod pod = new Pod(args[0],Integer.parseInt(args[1]));
		pod.addService("MSG", new MessageService(pod));
		pod.addService("ADD", new AddService(pod));
		ServerThread thread = new ServerThread(pod);
		thread.start();
		Interface mine = pod.getInterface();
		mine.setVisible(true);
	}
	
}
