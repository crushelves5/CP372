import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JTextField;
class Dashboard {
	/**
	 * Create the Dashboard
	 */
	public JFrame frame;
	public JTextArea responseField;
	public Dashboard() {
		initialize();
	}
		private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnPost = new JButton("POST");
		btnPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnPost.setBounds(65, 11, 89, 23);
		frame.getContentPane().add(btnPost);
		
		JButton btnGet = new JButton("GET");
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnGet.setBounds(65, 45, 89, 23);
		frame.getContentPane().add(btnGet);
		
		JButton btnPin = new JButton("PIN");
		btnPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnPin.setBounds(65, 79, 89, 23);
		frame.getContentPane().add(btnPin);
		
		JButton btnUnpin = new JButton("UNPIN");
		btnUnpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnUnpin.setBounds(65, 113, 89, 23);
		frame.getContentPane().add(btnUnpin);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnClear.setBounds(65, 147, 89, 23);
		frame.getContentPane().add(btnClear);
		
		JLabel lblServerResponse = new JLabel("Server Response");
		lblServerResponse.setBounds(308, 15, 103, 14);
		frame.getContentPane().add(lblServerResponse);
		
		JButton btnDisconnect = new JButton("DISCONNECT");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnDisconnect.setBounds(53, 206, 114, 33);
		frame.getContentPane().add(btnDisconnect);
		
		responseField = new JTextArea();
		responseField.setEditable(false);
		responseField.setBounds(191, 44, 326, 195);
		frame.getContentPane().add(responseField);
	}
}

class Window {

	public JFrame frame;
	private JTextField addressField;
	private JTextField portField;

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 507, 145);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server Address");
		lblNewLabel.setBounds(16, 0, 100, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Port Number");
		lblNewLabel_1.setBounds(16, 48, 100, 23);
		frame.getContentPane().add(lblNewLabel_1);
		
		addressField = new JTextField();
		addressField.setBounds(110, 8, 112, 23);
		frame.getContentPane().add(addressField);
		addressField.setColumns(20);
		
		portField = new JTextField();
		portField.setBounds(110, 46, 112, 23);
		frame.getContentPane().add(portField);
		portField.setColumns(10);
		
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//CONNECT TO SERVER
				String address = addressField.getText();
				int port = Integer.parseInt(portField.getText());
				String success = Client.connect(address,port);
				if(success.equals("SUCCESS")){
					Client.welcome_message();
					
				}
				
			}
		});
		btnNewButton.setBounds(245, 48, 129, 45);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lbllocalhost = new JLabel("(localhost if server is on the same machine)");
		lbllocalhost.setBounds(222, 11, 300, 26);
		frame.getContentPane().add(lbllocalhost);
	}
}



public class Client {
	static String serverAddress;
	static Socket socket;
	static BufferedReader in;
    static PrintWriter out; 
	public static Window connect_window;
    public static void main(String[] args) throws Exception {
	
    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					connect_window = new Window();
					connect_window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		


        Scanner scanner = new Scanner(System.in);
		Scanner message_scan;
        while (true) {
            System.out.println("\nEnter a message to send to the server (DISCONNECT to disconnect):");
            String message = scanner.nextLine();
			message_scan = new Scanner(message);
            if (message.equals("DISCONNECT")) {//disconnect from server and client session ends
			out.println("DISCONNECT");
                break;
            }
			else{
				//Determine if the message is a valid format
				boolean msg_valid = CheckMessage(message_scan);
				if (msg_valid){
					//If valid, send message to server
					out.println(message);
					System.out.println(in.readLine());
					}
				else{System.out.println("Input has invalid form");}
				
			}
			
          // out.println(message);
           //System.out.println(in.readLine());
			
        }
    }
	
	public static String connect(String addrs, int port_num){
		String success = "SUCCESS";
		try{
		serverAddress = addrs;
		socket = new Socket(addrs,port_num);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(Exception e){
			success ="Unable to Connect, Invalid input try again";
		}
		return success;
	}
	
	public static void welcome_message(){
		// Consume and display welcome message, and Available colors from server
		
		    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard window = new Dashboard();
					window.responseField.append(in.readLine()+"\n");
					window.responseField.append(in.readLine()+"\n");
					window.responseField.append(in.readLine()+"\n");
					window.frame.setVisible(true);
					connect_window.frame.setVisible(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
      
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
		finally{
		return valid;
		}
		
	}
}

