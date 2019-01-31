import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
	
//        System.out.println("Enter the IP address of a machine running the capitalize server:");
        String serverAddress = "localhost";//new Scanner(System.in).nextLine();
        Socket socket = new Socket(serverAddress, 4554);

        // Streams for conversing with server
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Consume and display welcome message from the server
        System.out.println(in.readLine());

        Scanner scanner = new Scanner(System.in);
		Scanner message_scan;
        while (true) {
            System.out.println("\nEnter a message to send to the server (DISCONNECT to disconnect):");
            String message = scanner.nextLine();
			message_scan = new Scanner(message);
            if (message.equals("DISCONNECT")) {//disconnect from server and client session ends
                break;
            }
			else{
				//Determine if the message is a valid format
				boolean msg_valid = CheckMessage(message_scan);
				if (msg_valid){
					//If valid, send message to server
					out.println(message);
					}
				else{System.out.println("Input has invalid form");}
				
			}
			
          // out.println(message);
           System.out.println(in.readLine());
			
        }
    }
	
	public static boolean CheckMessage(Scanner msg_scan){
		boolean valid = false;
		try{
		String type = msg_scan.next();
		int coord_x;
		int coord_y;
		int width;
		int height;
		String color = "";
		String text = "";
		if(type.equals("POST")){
			coord_x = msg_scan.nextInt();
			coord_y = msg_scan.nextInt();
			width = msg_scan.nextInt();
			height = msg_scan.nextInt();
			color = msg_scan.next();
			text = msg_scan.next();
			if(coord_x >= 0 && coord_y >= 0 && width > 0 && height >0 && color != null && text !=null){
				valid = true;
			}
		}
		else if(type.equals("GET")){
			text = msg_scan.next();
			if(text != null){
			valid = true;
			}
		}
		else if(type.equals("PIN")){
			coord_x = msg_scan.nextInt();
			coord_y = msg_scan.nextInt();
			if(coord_x >= 0 && coord_y >= 0){
				valid = true;
			}
		}
		else if(type.equals("UNPIN")){
			coord_x = msg_scan.nextInt();
			coord_y = msg_scan.nextInt();
			if(coord_x >= 0 && coord_y >= 0){
				valid = true;
			}
		}
		else if(type.equals("CLEAR")){
			valid = true;
		}
		}
		catch(Exception e){
			//Errors mean the format is invalid
		}
		return valid;
		
		
	}
}

