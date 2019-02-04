import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Queue;
/**
 * SOURCE:  http://cs.lmu.edu/~ray/notes/javanetexamples/
 *
 * A server program which accepts requests from clients to capitalize strings.
 * When clients connect, a new thread is started to handle an interactive dialog
 * in which the client sends in a string and the server thread sends back the
 * capitalized version of the string.
 *
 * The program is runs in an infinite loop, so shutdown in platform dependent.
 * If you ran it from a console window with the "java" interpreter, Ctrl+C will
 * shut it down.
 */
class Message{
public String message;
public PrintWriter out;
	public Message(String message,PrintWriter out){
		this.message = message;
		this.out = out;
	}
}
class Pin{
    int x;
    int y;
    public Pin(int x, int y){
        this.x = x;
        this.y = y;
    }
}

class Note{
     String color;
     String message;
     int coord_x;
	 int coord_y;
     int width;
     int height;
     boolean pinned;
     public LinkedList<Pin> pins;

        Note(String color, String message, int coord_x,int coord_y, int width, int height){
            this.color = color;
            this.message = message;
            this.coord_x = coord_x;
			this.coord_y = coord_y;
            this.width = width;
            this.height = height;
            this.pinned = false;
            this.pins = new LinkedList<Pin>();	
     }
 }

public class Server{
	
	private static int port;
	private static int board_width;
	private static int board_height;
	private static String default_color;
    private static ArrayList<String> colors = new ArrayList<String>();
    public static LinkedList<Note> noteList = new LinkedList<Note>();
    public static LinkedList<Pin> pinList = new LinkedList<Pin>();
    public static Queue<Message> q = new LinkedList<>();
    
    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {

		try{
		port = Integer.parseInt(args[0]);
		board_width = Integer.parseInt(args[1]);
		board_height = Integer.parseInt(args[2]);
		default_color = args[3];
		colors.add(default_color);
		for (int x = 4; x < args.length;x++){
			colors.add(args[x]);
		}
        System.out.println("The Note Board server is running at port "+port+".");
        int clientNumber = 0;
        ServerSocket listener = new ServerSocket(port);
		/*
        try{
            new RequestHandler().start();
        }catch(Exception e){
            System.out.println("Server request cant initiate");
        }
*/
        try {
            while (true) {
                new Client(listener.accept(), clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }
	catch(Exception e){
		System.out.println("Server specifications invalid");
	}
	}
	

    /**
     * A private thread to handle client requests on a particular
     * socket.  The client terminates the dialogue by sending a single line
     * containing only a period.
     */
/*
    public static class RequestHandler extends Thread{
        

        public void run(){
            
            String return_message;
            Scanner scan_msg;
            try{
                while(true){
					
                    if(q.peek() != null){
                        Message object = q.remove();
                        scan_msg = new Scanner(object.message);
                        return_message = messageHandler(scan_msg);
                        object.out.println(return_message);
                    }
                }
            }catch(Exception e){
                System.out.println("ran into error with requesthandler");
            }
        }
		
		

    }
*/
    private static class Client extends Thread {
        private Socket socket;
        private int clientNumber;

        public Client(Socket socket, int clientNumber) {
            this.socket = socket;
            this.clientNumber = clientNumber;
            log("New connection with client# " + clientNumber + " at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message then repeatedly reading strings
         * and sending back the capitalized version of the string.
         */

        public void run() {
			
            try {
				Scanner scan_msg;
                String return_message;
                
                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				String welcome_note = "Hello, you are client #"+ clientNumber +".\nAvailable note colors\n";
                // Send a welcome message to the client.
				for (int x = 0; x < colors.size();x++){
					welcome_note = welcome_note + colors.get(x)+", ";
				}
				out.println(welcome_note);
				out.println("Board Dimensions: Width: "+board_width+" Height: "+board_height);
//                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line;
                while (true) {
                    String input = in.readLine();
                    if (input.equals("DISCONNECT")) {
						socket.close();
                        break;
                    }
					scan_msg = new Scanner(input);
					return_message = messageHandler(scan_msg);
                    //Message command = new Message(input,out);
                    //q.add(command);
					out.println(return_message);
                

                }
            } catch (IOException e) {
                log("Error handling client# " + clientNumber + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client# " + clientNumber + " closed");
            }
        }
		public String messageHandler(Scanner msg_scan){
            String msg = msg_scan.next();
			String return_message = "";
			//Compare the command then pass in the rest of the message to the appropriate function
            if(msg.equals("POST")){
               return_message = post(msg_scan.nextLine());
            }else if(msg.equals("GET")){
                return_message = get(msg_scan.nextLine());
            }else if(msg.equals("PIN")){
                return_message = pin(msg_scan.nextLine());
            }else if(msg.equals("UNPIN")){
                return_message = unpin(msg_scan.nextLine());
            }else if(msg.equals("CLEAR")){
                return_message =clear();
            }
			
			return return_message;
        }

        public String post(String msg){
			String return_message = "";
			boolean valid = true;
			boolean color_match = false;
			Scanner msg_scan = new Scanner(msg);
			int coord_x = msg_scan.nextInt();
			int coord_y = msg_scan.nextInt();
			int width = msg_scan.nextInt();
			int height = msg_scan.nextInt();
            String color = msg_scan.next();
			String message = msg_scan.nextLine();
			if(coord_x > board_width || coord_y > board_height){
				valid = false;
				return_message = return_message+"Coordinates are outside the board dimension, POST denied ";
			}
			for(int x = 0; x< colors.size();x++){
				if (color.equals(colors.get(x))){
					color_match = true;
				}
			}
			if(!color_match){
				color = default_color;
			}
			if(valid){
            Note newNote = new Note(color, message, coord_x, coord_y, width, height);
            noteList.add(newNote);
			return_message ="POST message success";
            }
            
			return return_message;
			
        }

        public String get(String msg){
            String return_message = "";
            Scanner msg_scan = new Scanner(msg);
            boolean valid = false;
            String color = "";
            String contains1 = "";
            String contains2 = "";
            String refersTo = "";
            boolean notesFound = false;


            while(msg_scan.hasNext()){
                
                String cond = msg_scan.next();
                if(cond.equals("PINS")){
					if(pinList.size() > 0){
                    return_message = "The board has pins at locations:";
                    for(int i = 0; i < pinList.size(); i++){
                        return_message = return_message + " {" + pinList.get(i).x + "," + pinList.get(i).y + "}";
                    }
					}
					else{
						return_message ="There are no pins on the board";
					}
					break;
                }else if(cond.equals("color")){
					valid = true;
                    color = msg_scan.next();
					
                }else if(cond.equals("contains")){
                    contains1 = msg_scan.next();
                    if(!contains1.equals("all")){
                        contains2 = msg_scan.next();
                    }
                }else if(cond.equals("refersTo")){
                    refersTo = msg_scan.next();
					    if(!refersTo.equals("all")){
                       while(msg_scan.hasNext()){
						   refersTo = refersTo+" "+msg_scan.next();
					   }
                    }

                }
			}  
                //handle grabbing correct stuff
                if(valid == true){
                    return_message = return_message + "Query results:~";
                    for(int i = 0; i < noteList.size(); i++){
                        Note current = noteList.get(i);
                        
                            if(color.equals("all") || color.equals(current.color)){
                                if(refersTo.equals("all") || current.message.contains(refersTo)){
									if(contains1.equals("all")) {
                                    return_message = return_message+"Note: " + current.message+"~";
                                    notesFound = true;
									}
									else{
										for (int x = 0; x < current.pins.size(); x++){
											if(Integer.parseInt(contains1)== (current.pins.get(x).x) && Integer.parseInt(contains2)==(current.pins.get(x).y)){
		                                    return_message = return_message+"Note: " + current.message+"~";
											notesFound = true;
											break;
											}
										}
									}
                                }
                            }
                        
                    }
                    if(!notesFound){
                        return_message = return_message + "No notes were found matching the query.";
                    }
                }
            
            return return_message;
        }

        public String pin(String msg){
            String return_message = "";
            Scanner msg_scan = new Scanner(msg);
            boolean valid = true;
            int numNotes = noteList.size();
            int pinCount = 0;
            int coord_x = msg_scan.nextInt();
            int coord_y = msg_scan.nextInt();

            if(coord_x > board_width || coord_y > board_height){
				valid = false;
				return_message = return_message+"Coordinates are outside the board dimension, PIN denied ";
            }
            if(valid){
                Pin newPin = new Pin(coord_x, coord_y);
                pinList.add(newPin);
                return_message = return_message + "Pin placed, ";    
                for(int i = 0; i < numNotes; i++){
                    Note current = noteList.get(i);
                    if(coord_x >= current.coord_x && coord_x <= current.width + current.coord_x){
                        if(coord_y >= current.coord_y && coord_y <= current.height + current.coord_y){
                            current.pinned = true;
                            current.pins.add(newPin);
                            pinCount++;
                        }
                    }
                }
                if(pinCount < 1){
                    return_message = return_message + "no notes were pinned at position (" + coord_x + ", " + coord_y + ").";
                }else{
                    return_message = return_message + "" +pinCount + " message(s) have been pinned."; 
                }
            }

			return return_message;
        }
        public String unpin(String msg){
            String return_message = "";
            Scanner msg_scan = new Scanner(msg);
            int coord_x = msg_scan.nextInt();
            int coord_y = msg_scan.nextInt();
            boolean valid = true;
            boolean unpinned = false;
            int unpinnedCount = 0;

            if(coord_x > board_width || coord_y > board_height){
				valid = false;
				return_message = return_message+"Coordinates are outside the board dimension, UNPIN denied ";
            }

            if(valid){
                for(int i = 0; i < pinList.size(); i++){
                    Pin currentPin = pinList.get(i);
                    if(currentPin.x == coord_x && currentPin.y == coord_y){
                        //remove this pin
                        unpinned = true;
                        unpinnedCount++;
                        for(int j = 0; j < noteList.size(); j++){
                            Note currentNote = noteList.get(j);
                            for(int k = 0; k < currentNote.pins.size(); k++){
                                Pin notePin = currentNote.pins.get(k);
                                if(notePin.x == coord_x && notePin.y == coord_y){
                                    currentNote.pins.remove(k);
                                    
                                    k--; //decrement k if removing pin for LinkedList
                                    if(currentNote.pins.size() == 0){
                                        currentNote.pinned = false;
                                    }
                                }
                            }
                        }
                    pinList.remove(i);
                    i--; // decrement i for linkedlist
                    }
                }
            }

            if(unpinned){
                return_message = return_message + unpinnedCount + " pin(s) were removed.";
            }else{
                return_message = return_message+"No pins were found at this position.";
            }
            return return_message;
        }

        public String clear(){
		String return_message;
		int noteCount = 0;
		int x = 0;
		while(x < noteList.size()){
			if(noteList.get(x).pinned == false){
				noteList.remove(x);
				noteCount++;
			}
			else{
				x++;
			}
		}
		return return_message = noteCount+" note(s) have been cleared";
        }
        


        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }

	}

