package androidPort;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Messenger {
	
	private static Queue<String> cmdQueue = new LinkedList<String>();
	private static ServerSocket socket;
	private final int port ;
	public Messenger(int port) {
		this.port = port;
	}
	
	public void start(){
		ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);
		
		Thread server = new Thread( () ->{
			ServerSocket serverSocket;
			try {
                serverSocket = new ServerSocket(port);
                System.out.println("Waiting for clients to connect...");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    clientProcessingPool.submit(new ClientTask(clientSocket));
                }
            } catch (IOException e) {
                System.err.println("Unable to process client request");
                e.printStackTrace();
            }
		});
		
		server.start();
	}
	
	public static String poll(){
		return cmdQueue.poll();
	}
	
	public static void send(String msg){
		if (cmdQueue.size() < 30) {
			System.out.println("Sending " +msg);
			cmdQueue.offer(msg);
		}
	}
	
	public static void shutdown(){
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
