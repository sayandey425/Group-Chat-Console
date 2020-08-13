import java.net.*;
import java.util.*;
import java.io.*;

public class ServerForGroup implements Runnable{
	
	Socket socket;
	
	public static Vector client = new Vector();
	
	public ServerForGroup(Socket socket) {
		
		try {
			this.socket = socket;
			
		}catch(Exception e) {
			
		}
	}
		
		public void run() { 
			try {
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				client.add(writer);
				
				while(true) {
					String data = reader.readLine().trim();
					System.out.println("Received " +data);
					
					for(int i=0; i<client.size(); i++) {
						try {
							
							BufferedWriter bw = (BufferedWriter) client.get(i);
							bw.write(data);
							bw.write("\r\n");
							bw.flush();
							
						}catch(Exception e) { }
					}
				} 
			}catch(Exception e) {
				
			}
		}
	
	public static void main(String[] args) throws Exception{
		
		ServerSocket s = new ServerSocket(5007);
		
		while(true) {
			Socket socket = s.accept(); 
			ServerForGroup server = new ServerForGroup(socket);
			
			Thread thread = new Thread(server);
			thread.start();
		}
		
	}
	}


	