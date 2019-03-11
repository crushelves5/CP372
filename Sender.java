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


class senderGui{
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
	
	public senderGui(){
		initialize();
	}
	
	public boolean handshake(byte[] message,DatagramSocket socket, InetAddress receiver, int portNum, int MDS){
		
		
		try{
		String msg;
		DatagramPacket request = new DatagramPacket(message,message.length, receiver, portNum);
		socket.send(request);
		
		byte [] receiverBuffer = new byte[MDS+6];
		DatagramPacket response = new DatagramPacket(receiverBuffer,receiverBuffer.length);
		socket.receive(response);
		//String reply = new String(response.getData()); //change to strin array
		String[] arrayMsg  = new String(response.getData()).split(" ");
		System.out.println("response was: " + arrayMsg[0].trim());
		if (arrayMsg[0].trim().equals("SYNACK") == false){
			System.out.println("the handshake has failed.");
			textArea.setText("HANDSHAKE FAILED\n");
		}
		else{
			msg = "ACK   ";
			System.out.println("getBytes");
			message = msg.getBytes();
			request = new DatagramPacket(message, message.length, receiver,portNum);
			socket.send(request);
			//textArea.append("HANDSHAKE success"+"\n");
			return true;
		}
		
		}
		
		catch(SocketTimeoutException e){
			//textArea.setText("HANDSHAKE FAILED\n");
			System.out.println("handshake fail occurred");
			//e.printStackTrace();
		}catch(Exception e){
			System.out.println("error occured");
		}
		
		return false;
	}

private int sendHelper(DatagramSocket sock, InetAddress receiverIP, int receiverPort, byte[][] dataArray, int MDS, int index){
	
	try{
		String msg = index + " " + new String(dataArray[index]);
		byte[] toSend = msg.getBytes();
		DatagramPacket sendMsg = new DatagramPacket(toSend, toSend.length, receiverIP, receiverPort);
		sock.send(sendMsg);
		byte[] ack = new byte[4];
		DatagramPacket receiveMsg = new DatagramPacket(ack, ack.length);
		sock.receive(receiveMsg);
		return index++; //returns ack number to send func

	}catch(SocketTimeoutException s){
		System.out.println("timeout occured");
		return sendHelper(sock, receiverIP, receiverPort, dataArray, MDS, index);
	}catch(Exception e){
		System.out.println("error occured");
	}

	return -1; //something went very bad
}

private void send(DatagramSocket sock, InetAddress receiverIP, int receiverPort, byte[][] dataArray, boolean[] acknowledged, int num_seq, int MDS){
	long startTime = System.currentTimeMillis();
	for(int index = 0; index < num_seq; index++){
		int ackReceived = sendHelper(sock, receiverIP, receiverPort, dataArray, MDS, index);
		System.out.println("ACK " + ackReceived + " received.");
	}
	//SEND END OF TRANSMISSION PACKET
	try{
	String msg = "EOT ";
	byte[] toSend =  msg.getBytes();
	DatagramPacket sendMsg = new DatagramPacket(toSend, toSend.length, receiverIP, receiverPort);
	sock.send(sendMsg);
	}catch(Exception a){
		//arbitrary for EOT
	}
	long totalTime = System.currentTimeMillis() - startTime;
	textArea.setText("Transfer took " + totalTime + " ms to transfer.\n");
	
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
				int timeOutVal = Integer.parseInt(timeoutField.getText());
				
				try{
					InetAddress receiverIP = InetAddress.getByName(receiverIPField.getText());
					DatagramSocket sock = new DatagramSocket(senderPortNum);
					sock.setSoTimeout(timeOutVal);
					//sock.connect(receiverIP, receiverPortNum);
					File fd = new File(fileNameField.getText());
					//grab file size in bytes
					int size = (int)(fd.length());
					String handshakeMessage = "SYNC "+MDS+" " + size + " ";
					//convert handshake message to stream of bytes
					byte [] msg = handshakeMessage.getBytes();
					System.out.println("before handshake");
					boolean success = handshake(msg, sock, receiverIP, receiverPortNum, MDS);
					System.out.println("after handshake");
					if (success){
						//START FILE TRANSFER
						long currentTime = System.currentTimeMillis();
						FileInputStream fileIn = new FileInputStream(fd);
						long numBytes = fd.length();
						System.out.println(numBytes);
						int num_packages = (int)(size/MDS);
						if(size%MDS !=0){
							num_packages+=1;
						}
						byte [][] dataArray = new byte[num_packages][MDS];
						boolean [] acknowledged = new boolean[num_packages];

						//initialize acknowledgement array and read file into array
						System.out.println("Converting file into array of bytes");
						for(int i = 0; i < num_packages; i++){
							acknowledged[i] = false;
							fileIn.read(dataArray[i]);
							
						}
				
						//call threaded function to handle sending
						send(sock,receiverIP,receiverPortNum,dataArray, acknowledged, num_packages,MDS);
					}
					else{
						System.out.println("Handshake with Receiver Failed, Cannot begin Transfer");
					}
						
					
					sock.close();
				}catch (Exception h){
					System.out.println("error occured: "  );
					h.printStackTrace();
				}


			}
			
		});

		frame.getContentPane().add(btnNewButton);

		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(10, 296, 306, 163);
		scroll = new JScrollPane(textArea);
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
	public static senderGui window;
	public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new senderGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}


	
}