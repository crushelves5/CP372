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
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Choice;
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
				Client.openPost();
			}
		});
		btnPost.setBounds(65, 11, 89, 23);
		frame.getContentPane().add(btnPost);
		
		JButton btnGet = new JButton("GET");
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.openGet();
			}
		});
		btnGet.setBounds(65, 45, 89, 23);
		frame.getContentPane().add(btnGet);
		
		JButton btnPin = new JButton("PIN");
		btnPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.openPin();
			}
		});
		btnPin.setBounds(65, 79, 89, 23);
		frame.getContentPane().add(btnPin);
		
		JButton btnUnpin = new JButton("UNPIN");
		btnUnpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.openUnpin();
			}
		});
		btnUnpin.setBounds(65, 113, 89, 23);
		frame.getContentPane().add(btnUnpin);
		
		JButton btnClear = new JButton("CLEAR");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.clear();
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
				Client.disconnect();
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
class Unpin {

	public JFrame frame;
	private JTextField xField;
	private JTextField yField;



	/**
	 * Create the application.
	 */
	public Unpin() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 363, 134);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblXCoordinate = new JLabel("X");
		lblXCoordinate.setBounds(77, 67, 17, 14);
		frame.getContentPane().add(lblXCoordinate);
		
		xField = new JTextField();
		xField.setBounds(49, 36, 60, 20);
		frame.getContentPane().add(xField);
		xField.setColumns(10);
		
		JLabel label = new JLabel(",");
		label.setBounds(110, 50, 17, 14);
		frame.getContentPane().add(label);
		
		yField = new JTextField();
		yField.setColumns(10);
		yField.setBounds(120, 36, 60, 20);
		frame.getContentPane().add(yField);
		
		JLabel lblY = new JLabel("Y");
		lblY.setBounds(148, 67, 17, 14);
		frame.getContentPane().add(lblY);
		
		JButton btnUnpin = new JButton("UNPIN");
		btnUnpin.setBounds(219, 35, 89, 23);
		frame.getContentPane().add(btnUnpin);
				btnUnpin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			Client.unpin(xField.getText(),yField.getText());
				
			}
		});
	}

}
class PinGui {

	public JFrame frame;
	private JTextField xField;
	private JTextField yField;


	/**
	 * Create the application.
	 */
	public PinGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 363, 134);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblXCoordinate = new JLabel("X");
		lblXCoordinate.setBounds(77, 67, 17, 14);
		frame.getContentPane().add(lblXCoordinate);
		
		xField = new JTextField();
		xField.setBounds(49, 36, 60, 20);
		frame.getContentPane().add(xField);
		xField.setColumns(10);
		
		JLabel label = new JLabel(",");
		label.setBounds(110, 50, 17, 14);
		frame.getContentPane().add(label);
		
		yField = new JTextField();
		yField.setColumns(10);
		yField.setBounds(120, 36, 60, 20);
		frame.getContentPane().add(yField);
		
		JLabel lblY = new JLabel("Y");
		lblY.setBounds(148, 67, 17, 14);
		frame.getContentPane().add(lblY);
		
		JButton btnPin = new JButton("PIN");
		btnPin.setBounds(219, 35, 89, 23);
		frame.getContentPane().add(btnPin);
		btnPin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			Client.pin(xField.getText(),yField.getText());
				
			}
		});
	}

}
class Post {

	public JFrame frame;
	public Choice colors;
	private JTextField xField;
	private JTextField yField;
	private JTextField widthField;
	private JTextField heightField;

	/**
	 * Create the application.
	 */
	public Post() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 306, 320);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblXCoordinate = new JLabel("X coordinate");
		lblXCoordinate.setBounds(10, 11, 78, 23);
		frame.getContentPane().add(lblXCoordinate);
		
		xField = new JTextField();
		xField.setBounds(93, 12, 86, 20);
		frame.getContentPane().add(xField);
		xField.setColumns(10);
		
		JLabel lblYCoordinate = new JLabel("Y coordinate");
		lblYCoordinate.setBounds(10, 45, 78, 23);
		frame.getContentPane().add(lblYCoordinate);
		
		yField = new JTextField();
		yField.setColumns(10);
		yField.setBounds(93, 46, 86, 20);
		frame.getContentPane().add(yField);
		
		JLabel lblW = new JLabel("Note Width");
		lblW.setBounds(10, 79, 78, 23);
		frame.getContentPane().add(lblW);
		
		widthField = new JTextField();
		widthField.setColumns(10);
		widthField.setBounds(93, 80, 86, 20);
		frame.getContentPane().add(widthField);
		
		JLabel lblNoteHeight = new JLabel("Note Height");
		lblNoteHeight.setBounds(10, 113, 78, 23);
		frame.getContentPane().add(lblNoteHeight);
		
		heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(93, 114, 86, 20);
		frame.getContentPane().add(heightField);
		
		JLabel lblColor = new JLabel("Color");
		lblColor.setBounds(10, 147, 78, 23);
		frame.getContentPane().add(lblColor);
		
		colors = new Choice();
		colors.setBounds(93, 150, 180, 20);
		frame.getContentPane().add(colors);
		
		JTextField messageField = new JTextField();
		messageField.setBounds(10, 181, 276, 40);
		frame.getContentPane().add(messageField);
		
		JButton btnPost = new JButton("POST");
		btnPost.setBounds(106, 240, 89, 23);
		frame.getContentPane().add(btnPost);
		btnPost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				int coord_x = Integer.parseInt(xField.getText());
				int coord_y = Integer.parseInt(yField.getText());
				int width = Integer.parseInt(widthField.getText());
				int height = Integer.parseInt(heightField.getText());
				
				
				if(coord_x >= 0 && coord_y >= 0 && width > 0 && height >0){
					Client.post("POST "+coord_x+" "+coord_y+" "+width+" "+height+" "+colors.getSelectedItem()+" "+messageField.getText());
				}
				else{
					JOptionPane.showMessageDialog(null,"Invalid Input");
				}
				}
				catch(Exception e){
					JOptionPane.showMessageDialog(null,"Invalid Input");
				}
				
			}
		});
	}
}
class Get {

	public JFrame frame;
	private JTextField colorField;
	private JTextField containsField;
	private JTextField refersField;


	/**
	 * Create the application.
	 */
	public Get() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 436, 194);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblColor = new JLabel("color");
		lblColor.setBounds(42, 28, 68, 14);
		frame.getContentPane().add(lblColor);
		
		colorField = new JTextField();
		colorField.setBounds(109, 25, 131, 20);
		frame.getContentPane().add(colorField);
		colorField.setColumns(10);
		
		JLabel lblContains = new JLabel("contains");
		lblContains.setBounds(42, 69, 68, 14);
		frame.getContentPane().add(lblContains);
		
		containsField = new JTextField();
		containsField.setColumns(10);
		containsField.setBounds(109, 66, 131, 20);
		frame.getContentPane().add(containsField);
		
		JLabel lblRefersto = new JLabel("refersTo");
		lblRefersto.setBounds(42, 109, 46, 14);
		frame.getContentPane().add(lblRefersto);
		
		refersField = new JTextField();
		refersField.setColumns(10);
		refersField.setBounds(109, 106, 131, 20);
		frame.getContentPane().add(refersField);
		
		JButton btnGet = new JButton("GET");
		btnGet.setBounds(288, 28, 89, 39);
		frame.getContentPane().add(btnGet);
		btnGet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String color =colorField.getText();
				String contains = containsField.getText();
				String refersTo = refersField.getText();
				if(color.equals("")){color ="all";}
				if(contains.equals("")){contains="all";}
				if(refersTo.equals("")){refersTo="all";}
				Client.get("GET color "+color+" contains "+contains+" refersTo "+refersTo);
				
			}
		});
		
		JButton btnGetPins = new JButton("GET PINS");
		btnGetPins.setBounds(288, 84, 89, 39);
		frame.getContentPane().add(btnGetPins);
		btnGetPins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.get("GET PINS");
				
			}
		});
	}

}


public class Client {
	static String serverAddress;
	static Socket socket;
	static BufferedReader in;
    static PrintWriter out; 
	public static Window connect_window;
	public static Dashboard dashboard;
	public static PinGui pin_window;
	public static Unpin unpin_window;
	public static Post post_window;
	public static Get get_window;
	public static String [] colors;
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
	
	public static void Errorpane(){
		JOptionPane.showMessageDialog(null,"Invalid Input");
	}
	
	public static void openPin(){
			
		    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pin_window = new PinGui();
					pin_window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void openUnpin(){
				    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					unpin_window = new Unpin();
					unpin_window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void openPost(){
			 EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					post_window = new Post();
					for(int x =0; x< colors.length-1;x++){
						post_window.colors.add(colors[x]);
					}
					post_window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void openGet(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					get_window = new Get();
					get_window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public static void post(String message){
		try{
			out.println(message);
			System.out.println("Sent "+message+" to server");
			dashboard.responseField.append(in.readLine()+"\n");
		}
		catch(Exception e){
			System.out.println("Something went wrong");
		}
	}
	
	public static void get(String message){
		try{
			out.println(message);
			System.out.println("Sent "+message+" to server");
			dashboard.responseField.append(in.readLine()+"\n");
		}
		catch(Exception e){
			System.out.println("Something went wrong");
		}
		
	}
	
	public static void pin(String x, String y){
		try{
		int coord_x = Integer.parseInt(x);
		int coord_y = Integer.parseInt(y);
		
			if(coord_x >= 0 && coord_y >= 0){
				out.println("PIN "+x+" "+y);
				dashboard.responseField.append(in.readLine()+"\n");
			}
			else{
				JOptionPane.showMessageDialog(null, "Valid coordinates are greater than 0");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Invalid Input");
		}
	}
	public static void unpin(String x, String y){
		try{
		int coord_x = Integer.parseInt(x);
		int coord_y = Integer.parseInt(y);
		
			if(coord_x >= 0 && coord_y >= 0){
				out.println("UNPIN "+x+" "+y);
				dashboard.responseField.append(in.readLine()+"\n");
			}
			else{
				JOptionPane.showMessageDialog(null, "Valid coordinates are greater than 0");
			}
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, "Invalid Input");
		}
	}
	
	public static void clear(){
		out.println("CLEAR");
		try{
		dashboard.responseField.append(in.readLine()+"\n");
		}
		catch(Exception e){
			
		}
	}
	public static void disconnect(){
		out.println("DISCONNECT");
		System.exit(0);
	}
	
	public static void welcome_message(){
		// Consume and display welcome message, and Available colors from server
		    EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String colorLine = "";
					dashboard = new Dashboard();
					dashboard.responseField.append(in.readLine()+"\n");
					dashboard.responseField.append(in.readLine()+"\n");
					colorLine= in.readLine();
					dashboard.responseField.append(colorLine+"\n");
					colors = colorLine.split(",");

					dashboard.frame.setVisible(true);
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

