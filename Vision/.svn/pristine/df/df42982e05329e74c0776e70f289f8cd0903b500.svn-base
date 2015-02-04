package androidPort;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTask implements Runnable {

	private final Socket clientSocket;
	
	public ClientTask(Socket socket) {
		this.clientSocket = socket;
	}
	@Override
	public void run() {
		PrintWriter writer = null;
		System.out.println("Client has connected");
		try {
			writer = new PrintWriter(this.clientSocket.getOutputStream());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String cmd = "";
		boolean error = false;
		while( this.clientSocket.isConnected() && !this.clientSocket.isClosed() && !error){
			while( (cmd = Messenger.poll()) == null ){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.print("Sent command " + cmd + " ");
			writer.println(cmd);
			error =writer.checkError();
			System.out.println(" (with error? " + ((error)? "yes )":"no )") );
			writer.flush();
		}
		try{
			this.clientSocket.close();
			System.out.println("Client has disconnected");
		}
		catch (IOException e) {
            e.printStackTrace();
        }

	}

}
