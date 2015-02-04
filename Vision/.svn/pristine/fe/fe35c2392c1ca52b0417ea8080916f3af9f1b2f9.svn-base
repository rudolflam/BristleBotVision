package androidPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TestClient {
	public static void main(String[] args) {
		try {
			Socket s = new Socket(InetAddress.getByName("192.168.1.101"), 1337);
			
			BufferedReader reader= new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.println(reader.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
