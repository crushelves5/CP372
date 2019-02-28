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
	private JTextField receiverPortField;
	private JLabel receivedLabel;
	
	public gui(){
		initialize();
	}
	
		private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 342, 300);
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
	}
	
}
public class Receiver{
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