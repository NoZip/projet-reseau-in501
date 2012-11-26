public class ServerThread extends Thread {
	
	private Pod pod;
	private int port;
	
	public ServerThread(Pod pod, int port){
		this.pod = pod ;
		this.port = port;
	}
	
	public void run(){
		pod.listen(port);
	}

}