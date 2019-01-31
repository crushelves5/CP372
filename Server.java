import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
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

class Note{
     String color;
     String message;
     int coord_x;
	 int coord_y;
     int width;
     int height;
     boolean pinned;

        Note(String color, String message, int corner, int width, int height){
            this.color = color;
            this.message = message;
            this.coord_x = coord_x;
			this.coord_y = coord_y;
            this.width = width;
            this.height = height;
            this.pinned = false;
     }
 }

public class Server{
	
	private static int port;
	private static int board_width;
	private static int board_height;
	private static String default_color;
    private static ArrayList<String> colors = new ArrayList<String>();
    public static LinkedList<Note> noteList = new LinkedList<Note>();
    /**
     * Application method to run the server runs in an infinite loop
     * listening on port 9898.  When a connection is requested, it
     * spawns a new thread to do the servicing and immediately returns
     * to listening.  The server keeps a unique client number for each
     * client that connects just to show interesting logging
     * messages.  It is certainly not necessary to do this.
     */
    public static void main(String[] args) throws Exception {
        System.out.println("The Note Board server is running.");
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


        public void post(String msg[]){
            String color = msg[4];
            String message = msg[5];
            int corner = Integer.parseInt(msg[1]);
            int width = Integer.parseInt(msg[2]);
            int height = Integer.parseInt(msg[3]);
            boolean pinned = false;
            Note newNote = new Note(color, message, corner, width, height);
            noteList.add(newNote);
        }

        public void get(String msg[]){
            
        }

        public void pin(String msg[]){

        }
        public void unpin(String msg[]){

        }
        public void clear(String msg[]){

        }
        public void disconnect(String msg[]){

        }

        public void messageHandler(String msg[]){
            
            if(msg[0].compareTo("POST") == 0){
                post(msg);
                System.out.println("Congrats, post worked so far");
            }else if(msg[0].compareTo("GET") == 0){
                //get(msg);
            }else if(msg[0].compareTo("PIN") == 0){
                //pin(msg);
            }else if(msg[0].compareTo("UNPIN") == 0){
                //unpin(msg);
            }else if(msg[0].compareTo("CLEAR") == 0){
                //clear();
            }else if(msg[0].compareTo("DISCONNECT") == 0){
                //disconnect(msg);
            }
        }

        public void execute(String input){
            String msg[];
            msg = input.split(" ");
            messageHandler(msg);
            return;


        }

        public void run() {
			
            try {
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
//                out.println("Enter a line with only a period to quit\n");

                // Get messages from the client, line by line; return them
                // capitalized
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    System.out.println(input.toUpperCase()); //changed this to print to stdout for testing I/O
                    execute(input);

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

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}

