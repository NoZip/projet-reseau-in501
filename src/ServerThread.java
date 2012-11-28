public class ServerThread extends Thread {
	
	private Pod pod;
	
	public ServerThread(Pod pod){
		this.pod = pod ;
	}
	
	public void run(){
		pod.listen();
	}

}