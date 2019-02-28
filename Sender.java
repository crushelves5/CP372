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
class gui{
	public JFrame frame;
	private JTextField receiverIPField;
	private JTextField receiverPortField;
	private JLabel lblPortOf_1;
	private JTextField senderPortField;
	private JSeparator separator;
	private JLabel lblFileName;
	private JTextField fileNameField;
	private JLabel maxSizeField;
	private JTextField textField;
	private JScrollPane scroll;
	private JLabel lblSender;
	
	public gui(){
		initialize();
	}
	
		private void initialize() {
frame = new JFrame();
		frame.setBounds(100, 100, 342, 478);
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
		
		maxSizeField = new JLabel("Max size of Datagram:");
		maxSizeField.setBounds(10, 186, 133, 14);
		frame.getContentPane().add(maxSizeField);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(153, 186, 136, 20);
		frame.getContentPane().add(textField);
		
		JButton btnNewButton = new JButton("TRANSFER");
		btnNewButton.setBounds(100, 211, 103, 31);
		frame.getContentPane().add(btnNewButton);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		scroll = new JScrollPane(textArea);
		textArea.setBounds(10, 206, 306, 163);
		scroll.setBounds(10,249,306,163);
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