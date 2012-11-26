public class Start {

	public void main(String[] args){
		Pod pod = new Pod(args[0]);
		ServerThread thread = new ServerThread(pod,Integer.parseInt(args[1]));
		thread.run();
		pod.start();
	}
	
}
