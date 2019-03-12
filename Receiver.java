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
import java.io.*;
class receiverGui{

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
	public JLabel receivedLabel;
	
	public receiverGui(){
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
		rdbtnReliable.setActionCommand("RELIABLE");
		frame.getContentPane().add(rdbtnReliable);
		
		JRadioButton rdbtnUnreliable = new JRadioButton("UNRELIABLE");
		rdbtnUnreliable.setBounds(140, 217, 109, 23);
		rdbtnUnreliable.setActionCommand("UNRELIABLE");
		frame.getContentPane().add(rdbtnUnreliable);
		
		ButtonGroup bgroup = new ButtonGroup();
		bgroup.add(rdbtnReliable);
		bgroup.add(rdbtnUnreliable);
		
		JButton btnActivateReceiver = new JButton("ACTIVATE RECEIVER");
		btnActivateReceiver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				Receiver.socket = new DatagramSocket(Integer.parseInt(receiverPortField.getText()));
				//Receiver.socket.setSoTimeout(100000);
				Receiver.senderIP =  InetAddress.getByName(senderIPField.getText());
				Receiver.senderPort = Integer.parseInt(senderPortField.getText());
				Receiver.fileName = fileNameField.getText();
				if(bgroup.getSelection().getActionCommand().equals("RELIABLE")){
					Receiver.reliable = true;
				}
				else{
					Receiver.reliable = false;
				}
				
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
public static receiverGui window;
public static DatagramSocket socket;
public static InetAddress senderIP;
public static int senderPort;
public static String fileName;
public static boolean reliable;
	public static void main(String[] args){
			EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new receiverGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

	}
	public static void activate(){
		System.out.println("Activating receiver");
		int MDS = 1000;//default
		int packetNum;
		byte [] buffer = new byte[1000];
		try{
		boolean handshaking = true;
		while(handshaking){
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		socket.receive(request);
		String [] arrayMsg = new String(request.getData()).split(" ");
		System.out.println(arrayMsg[0]);
		//MDS = Integer.parseInt(arrayMsg[1]);

		System.out.println("Message ends here");
		
		if(arrayMsg[0].equals("SYNC")){
		MDS = Integer.parseInt(arrayMsg[1]);	
		packetNum = Integer.parseInt(arrayMsg[2]);
		packetNum = (int)Math.ceil(packetNum/MDS);
		
		byte[][] file = new byte[packetNum][MDS]; 
		
		byte [] sndMsg = ("SYNACK").getBytes();
		DatagramPacket ack = new DatagramPacket(sndMsg,sndMsg.length ,senderIP,senderPort);
		System.out.println("sending SYNACK");
		socket.send(ack);
		}
		else if(arrayMsg[0].equals("ACK")){
			System.out.println("received ACK,Now starting Transmission Thread");
			//Call function that will handle receiving transmission
			handshaking = false;
			new fileTransfer(MDS).start();
			/*
			boolean allPacketsReceived = false;
			while(allPacketsReceived == false){
				//handle receiving all packets and storing them to byte [][] file OR change to func.
				
			}

			*/
		}
		}
		
		
		}
		catch(Exception e){
			System.out.println("HANDSHAKE FAILED");
			System.out.println(e.getMessage());
		}
	}
	
	public static class fileTransfer extends Thread {
		private int MDS;
		public fileTransfer(int MDS){
			this.MDS  = MDS;
		}
			
            public void run() {
                try {
					byte [] buffer = new byte[MDS+4];
					byte [] sndMsg = new byte[4];
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					FileWriter fileWriter = new FileWriter(fileName);
					PrintWriter writer = new PrintWriter(fileWriter);
					int packet_count = 1;
					while(true){
						
						socket.receive(request);
						if(packet_count % 10 != 0 || reliable == true){
						String []arrayMsg = new String(request.getData()).split(" ", 2);
						if(arrayMsg[0].equals("EOT")){
							break;
						}
						else{
						String w = arrayMsg[1].trim().replace('~', '\n').replace('|', ' ');

						writer.print(w);
						
						//System.out.println(new String(arrayMsg[1]).trim());
						sndMsg = arrayMsg[0].getBytes();
						DatagramPacket ack = new DatagramPacket(sndMsg,sndMsg.length ,senderIP,senderPort);
						socket.send(ack);
						}
						window.receivedLabel.setText(""+packet_count);
						
						}
						packet_count++;
					}
					writer.close();
					
					/*
                    if (connected) {
                        int counter = 1;
                        transmitting = true;
                        String packetInfo[] = handshake().split(" ");
                        
                        packetSize = Integer.parseInt(packetInfo[0]);
                        numPackets = Integer.parseInt(packetInfo[1]);
                        
                        byte[][] file = new byte[numPackets][packetSize];
                        acknowledged = new boolean[numPackets];
                        dataArea.setText("");
                        byte[] buffer = new byte[packetSize];
                        DatagramPacket packet = new DatagramPacket(buffer, packetSize);
                        while (transmitting) {
                            
                            counter++;
                            socket.receive(packet);

                            byte[] sequenceNumberByte = Arrays.copyOfRange(buffer, 0, 4);
                            byte[] filePortionByte;
                            int sequenceNumber = java.nio.ByteBuffer.wrap(sequenceNumberByte).getInt();
                            if(sequenceNumber>numPackets || sequenceNumber==-5){
    
                                throw new Exception("Error: File no longer Transfering");
                                
                            }
                            if (sequenceNumber == (numPackets-1)) {
                                filePortionByte = Arrays.copyOfRange(buffer, 4, packet.getLength());
                            } else {
                                filePortionByte = Arrays.copyOfRange(buffer, 4, buffer.length);
                            }
                            if (sequenceNumber == -1) {
                                transmitting = false;
                                FileOutputStream out = new FileOutputStream(fileNameField.getText());
                                for (int i = 0; i < numPackets; i++) {
                                    out.write(file[i]);

                                }
                                dataArea.setText(dataArea.getText() + "File Received\n");
                            } else if (counter % 10 != 0 || reliable) {
                                dataArea.setText(dataArea.getText() + "There were " + (sequenceNumber + 1) + " of "
                                        + numPackets + " packets Received in order\n");
                                        dataArea.setCaretPosition(dataArea.getDocument().getLength());
                                file[sequenceNumber] = filePortionByte;
                                acknowledged[sequenceNumber] = true;

                    
                                DatagramPacket pSend = new DatagramPacket(sequenceNumberByte, 4);
                                socket.send(pSend);

                            }
                        }
                    }
					*/
                } catch (Exception e) {
                    System.out.println(e.getMessage());
					
                }
socket.close();
            }

        
		
    }
}