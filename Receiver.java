import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import java.net.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
class gui{

	public JFrame frame;
	private JTextField senderIPField;
	private JTextField senderPortField;
	private JSeparator separator;
	private JLabel lblFileName;
	private JTextField fileNameField;
	private JLabel maxSizeField;
	private JLabel lblSender;
	private JLabel lblPortOfReceiver;
	public JTextField receiverPortField;
	private JLabel receivedLabel;
	
	public gui(){
		initialize();
	}
	
		private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 342, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIpAddressOf = new JLabel("IP Address of Sender:");
		lblIpAddressOf.setBounds(10, 54, 133, 14);
		frame.getContentPane().add(lblIpAddressOf);
		
		senderIPField = new JTextField();
		senderIPField.setBounds(153, 54, 136, 20);
		frame.getContentPane().add(senderIPField);
		senderIPField.setColumns(10);
		
		JLabel lblPortOf = new JLabel("Port # of Sender:");
		lblPortOf.setBounds(10, 82, 120, 14);
		frame.getContentPane().add(lblPortOf);
		
		senderPortField = new JTextField();
		senderPortField.setColumns(10);
		senderPortField.setBounds(153, 82, 136, 20);
		frame.getContentPane().add(senderPortField);
		
		separator = new JSeparator();
		separator.setBounds(10, 142, 279, 2);
		frame.getContentPane().add(separator);
		
		lblFileName = new JLabel("File name: ");
		lblFileName.setBounds(10, 158, 120, 14);
		frame.getContentPane().add(lblFileName);
		
		fileNameField = new JTextField();
		fileNameField.setColumns(10);
		fileNameField.setBounds(153, 155, 136, 20);
		frame.getContentPane().add(fileNameField);
		
		maxSizeField = new JLabel("Current # of received in-order packets");
		maxSizeField.setBounds(10, 186, 193, 14);
		frame.getContentPane().add(maxSizeField);
		
		lblSender = new JLabel("RECEIVER");
		lblSender.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSender.setHorizontalAlignment(SwingConstants.CENTER);
		lblSender.setBounds(100, 11, 103, 31);
		frame.getContentPane().add(lblSender);
		
		lblPortOfReceiver = new JLabel("Port# of Receiver");
		lblPortOfReceiver.setBounds(10, 111, 120, 14);
		frame.getContentPane().add(lblPortOfReceiver);
		
		receiverPortField = new JTextField();
		receiverPortField.setColumns(10);
		receiverPortField.setBounds(153, 111, 136, 20);
		frame.getContentPane().add(receiverPortField);
		
		receivedLabel = new JLabel(" ");
		receivedLabel.setBounds(243, 186, 46, 14);
		frame.getContentPane().add(receivedLabel);
		
		JRadioButton rdbtnReliable = new JRadioButton("RELIABLE");
		rdbtnReliable.setSelected(true);
		rdbtnReliable.setBounds(10, 217, 109, 23);
		frame.getContentPane().add(rdbtnReliable);
		
		JRadioButton rdbtnUnreliable = new JRadioButton("UNRELIABLE");
		rdbtnUnreliable.setBounds(140, 217, 109, 23);
		frame.getContentPane().add(rdbtnUnreliable);
		
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(rdbtnReliable);
		bgroup.add(rdbtnUnreliable);
		
		JButton btnActivateReceiver = new JButton("ACTIVATE RECEIVER");
		btnActivateReceiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				Receiver.socket = new DatagramSocket(Integer.parseInt(receiverPortField.getText()));
				Receiver.senderIP =  InetAddress.getByName(senderIPField.getText());
				Receiver.senderPort = Integer.parseInt(senderPortField.getText());
				Receiver.fileName = fileNameField.getText();
				Receiver.activate();
				}
				catch(Exception e){
					System.out.println("Failed to activate Receiver");
				}
			}
		});
		btnActivateReceiver.setBounds(100, 247, 134, 23);
		frame.getContentPane().add(btnActivateReceiver);
	}
	
}
public class Receiver{
public static gui window;
public static DatagramSocket socket;
public static InetAddress senderIP;
public static int senderPort;
public static String fileName;
	public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}
	public static void activate(){
		System.out.println("Activating receiver");
		int MDS;
		byte [] buffer = new byte[1000];
		try{
		while(true){
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		socket.receive(request);
		String [] arrayMsg = new String(request.getData()).split(" ");
		System.out.println(arrayMsg[0]);
		System.out.println(arrayMsg[1]);
		System.out.println("Message ends here");
		/*
		if(arrayMsg[0].equals("SYNC")){
		MDS = Integer.parseInt(arrayMsg[1]);	
		byte [] sndMsg = ("SYNACK").getBytes();
		DatagramPacket ack = new DatagramPacket(sndMsg,sndMsg.length,senderIP,senderPort);
		socket.send(ack);
		}
		else if(arrayMsg[0].equals("ACK")){
			
			//Call function that will handle receiving transmission
		}
		*/
		}
		
		}
		catch(Exception e){
			System.out.println("HANDSHAKE FAILED");
			System.out.println(e.getMessage());
		}
	}
}