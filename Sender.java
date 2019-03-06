import java.io.*;
import java.net.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.Font;

//my imports
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


class gui{
	public JFrame frame;
	private JTextField receiverIPField;
	private JTextField receiverPortField;
	private JLabel lblPortOf_1;
	private JTextField senderPortField;
	private JSeparator separator;
	private JLabel lblFileName;
	private JTextField fileNameField;
	private JLabel maxSizeLabel;
	private JTextField maxSizeField;
	private JScrollPane scroll;
	private JLabel lblSender;
	private JTextArea textArea;
	private JLabel timeoutLabel;
	private JTextField timeoutField;
	
	public gui(){
		initialize();
	}
	
	public boolean handshake(byte[] message,DatagramSocket socket, InetAddress receiver, int portNum){
		
		boolean success = true;
		try{
		String msg;
		DatagramPacket request = new DatagramPacket(message,message.length, receiver, portNum);
		socket.send(request);
		/*
		byte [] receiverBuffer = new byte[1000];
		DatagramPacket response = new DatagramPacket(receiverBuffer,receiverBuffer.length);
		socket.receive(response);
		String reply = new String(response.getData());
		if (!reply.equals("SYNACK")){
			success = false;
			textArea.setText("HANDSHAKE FAILED\n");
		}
		else{
			msg = "ACK";
			message = msg.getBytes();
			request = new DatagramPacket(message, message.length, receiver,portNum);
			socket.send(request);
			textArea.setText("HANDSHAKE success\n");
		}
		*/
		}
		
		catch(Exception e){
			textArea.setText("HANDSHAKE FAILED\n");
			e.getMessage();
		}
		
		return success;
	}

	
		private void initialize() {
frame = new JFrame();
		frame.setBounds(100, 100, 342, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblIpAddressOf = new JLabel("IP Address of Receiver:");
		lblIpAddressOf.setBounds(10, 54, 133, 14);
		frame.getContentPane().add(lblIpAddressOf);
		
		receiverIPField = new JTextField();
		receiverIPField.setBounds(153, 54, 136, 20);
		frame.getContentPane().add(receiverIPField);
		receiverIPField.setColumns(10);
		
		JLabel lblPortOf = new JLabel("Port # of Receiver:");
		lblPortOf.setBounds(10, 82, 120, 14);
		frame.getContentPane().add(lblPortOf);
		
		receiverPortField = new JTextField();
		receiverPortField.setColumns(10);
		receiverPortField.setBounds(153, 82, 136, 20);
		frame.getContentPane().add(receiverPortField);
		
		lblPortOf_1 = new JLabel("Port # of Sender:");
		lblPortOf_1.setBounds(10, 110, 120, 14);
		frame.getContentPane().add(lblPortOf_1);
		
		senderPortField = new JTextField();
		senderPortField.setColumns(10);
		senderPortField.setBounds(153, 110, 136, 20);
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
		
		maxSizeLabel = new JLabel("Max size of Datagram:");
		maxSizeLabel.setBounds(10, 186, 133, 14);
		frame.getContentPane().add(maxSizeLabel);
		
		maxSizeField = new JTextField();
		maxSizeField.setColumns(10);
		maxSizeField.setBounds(153, 186, 136, 20);
		frame.getContentPane().add(maxSizeField);
		
		timeoutLabel = new JLabel("Timeout (In Milliseconds):");
		timeoutLabel.setBounds(10, 214, 143, 14);
		frame.getContentPane().add(timeoutLabel);
		
		timeoutField = new JTextField();
		timeoutField.setColumns(10);
		timeoutField.setBounds(153, 214, 136, 20);
		frame.getContentPane().add(timeoutField);
		
		JButton btnNewButton = new JButton("TRANSFER");
		btnNewButton.setBounds(100, 254, 103, 31);
		btnNewButton.addActionListener(new ActionListener(){
				//START HANDSHAKE
			public void actionPerformed(ActionEvent e){
				int MDS = Integer.parseInt(maxSizeField.getText());
				String fileName = fileNameField.getText();
				int senderPortNum = Integer.parseInt(senderPortField.getText());
				int receiverPortNum = Integer.parseInt(receiverPortField.getText());
				
				try{
					InetAddress receiverIP = InetAddress.getByName(receiverIPField.getText());
					DatagramSocket sock = new DatagramSocket(senderPortNum);
					//sock.connect(receiverIP, receiverPortNum);
					String handshakeMessage = "SYNC "+MDS+" ";
					//convert handshake message to stream of bytes
					byte [] msg = handshakeMessage.getBytes();
					boolean success = handshake(msg, sock, receiverIP, receiverPortNum);
					/*
					if (success){
						//START FILE TRANSFER
						long currentTime = System.currentTimeMillis();
						File fd = new File(fileName);
						FileInputStream fileIn = new FileInputStream(fd);
						long numBytes = fd.length();
						System.out.println(numBytes);
					}
					*/
					sock.close();
				}catch (Exception h){
					System.out.println(h.getMessage());
				}


			}
			
		});

		frame.getContentPane().add(btnNewButton);

		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scroll = new JScrollPane(textArea);
		textArea.setBounds(10, 206, 306, 163);
		scroll.setBounds(10,296,306,163);
		frame.getContentPane().add(scroll);
		
		lblSender = new JLabel("SENDER");
		lblSender.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSender.setHorizontalAlignment(SwingConstants.CENTER);
		lblSender.setBounds(127, 11, 68, 31);
		frame.getContentPane().add(lblSender);
	}
	
}



public class Sender{
	public static gui window;
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


	
}