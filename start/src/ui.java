

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import javax.swing.Box;
import java.awt.SystemColor;
import java.awt.Panel;
import javax.swing.JTextPane;
import java.awt.Toolkit;
import java.awt.Label;


public class ui extends JFrame implements SerialPortEventListener {
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String DB_URL = "jdbc:mysql://localhost/parking";

	   //  Database credentials
	   static final String USER = "root";
	   static final String PASS = "";
	   static Connection conn = null;
	   static Statement stmt = null;
	JButton btnNewButton_1 = new JButton("A2");
	JButton btnA = new JButton("A3");
	JButton btnB = new JButton("B1");
	JButton button_5 = new JButton("New");
	JButton button = new JButton("New");
	JButton button_6 = new JButton("New Button");
	JButton button_11 = new JButton("New Button");
	JButton button_8 = new JButton("New Button");
	JButton button_9 = new JButton("New Button");
	Label label_2;
	int sen_1,sen_2,sen_3,sen_4,sen_5,sen_6;
	private JPanel contentPane;
    SerialPort serialPort = null;
    int counter=0;
    private static final String PORT_NAMES[] = { 
       // "/dev/tty.usbmodem", // Mac OS X
//        "/dev/usbdev", // Linux
//        "/dev/tty", // Linux
//        "/dev/serial", // Linux
        "COM8", // Windows
    };
    
    private String appName;
    private BufferedReader input;
    private OutputStream output;
    
    private static final int TIME_OUT = 2000; // Port open timeout
    private static final int DATA_RATE = 9600; // Arduino serial port

    public boolean initialize() {
        try {
            CommPortIdentifier portId = null;
            Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

            // Enumerate system ports and try connecting to Arduino over each
            //
            System.out.println( "Trying:");
            while (portId == null && portEnum.hasMoreElements()) {
                // Iterate through your host computer's serial port IDs
                //
                CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
                System.out.println( "   port" + currPortId.getName() );
                for (String portName : PORT_NAMES) {
                    if ( currPortId.getName().equals(portName) 
                      || currPortId.getName().startsWith(portName)) {

                        // Try to connect to the Arduino on this port
                        //
                        // Open serial port
                        serialPort = (SerialPort)currPortId.open(appName, TIME_OUT);
                        portId = currPortId;
                        System.out.println( "Connected on port" + currPortId.getName() );
                        break;
                    }
                }
            }
        
            if (portId == null || serialPort == null) {
                System.out.println("Oops... Could not connect to Arduino");
                return false;
            }
        
            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);

            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

            // Give the Arduino some time
            try { Thread.sleep(2000); } catch (InterruptedException ie) {}
            
            return true;
        }
        catch ( Exception e ) { 
            e.printStackTrace();
        }
        return false;
    }
    
    private void sendData(String data) {
        try {
            System.out.println("Sending data: '" + data +"'");
            
            // open the streams and send the "y" character
            output = serialPort.getOutputStream();
            output.write( data.getBytes() );
        } 
        catch (Exception e) {
            System.err.println(e.toString());
            System.exit(0);
        }
    }

    //
    // This should be called when you stop using the port
    //
    public synchronized void close() {
        if ( serialPort != null ) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    //
    // Handle serial port event
    //
    public synchronized void serialEvent(SerialPortEvent oEvent) {
        //System.out.println("Event received: " + oEvent.toString());
        try {
            switch (oEvent.getEventType() ) {
                case SerialPortEvent.DATA_AVAILABLE: 
                    if ( input == null ) {
                        input = new BufferedReader(
                            new InputStreamReader(
                                    serialPort.getInputStream()));
                       // Thread.sleep(1000);
                    }
                    String delim="|";
                    String inputLine = input.readLine();
                    String[] values = inputLine.split(delim);
                    int empty=0;
                	    sen_1= Integer.parseInt(values[1]);
                	    sen_2= Integer.parseInt(values[3]);
                	    sen_3= Integer.parseInt(values[5]);
                	    sen_4= Integer.parseInt(values[7]);
                	    sen_5= Integer.parseInt(values[9]);
                	    sen_6= Integer.parseInt(values[11]);
                	    System.out.println(sen_1+"|"+sen_2+"|"+sen_3+"|"+sen_4+"|"+sen_5+"|"+sen_6+"|");
                	    if(sen_1==1){
                	    	//btnNewButton.setBackground(Color.RED);
                	    	button_6.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
                	    	
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 1 where sensor_name= 'sensor1' ";
                	    	stmt.executeUpdate(sql);
                      //System.out.println(a);
                	    	//empty++;

                	    }
                	    else{
                	    	//btnNewButton.setBackground(Color.GREEN);
                	    	button_6.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
                	    	
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 9 where sensor_name= 'sensor1' ";
                	    	stmt.executeUpdate(sql);
                	    	empty++;
                	    }
                	    
                	    if(sen_2==2){
                	    	//btnNewButton_1.setBackground(Color.RED);
                	    	btnB.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 2 where sensor_name= 'sensor2' ";
                	    	int a= stmt.executeUpdate(sql);
                	    }
                	    else{
                	    	//btnNewButton_1.setBackground(Color.GREEN);
                	    	btnB.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 9 where sensor_name= 'sensor2' ";
                	    	stmt.executeUpdate(sql);
                	    	empty++;
                	    }
                	    
                	    if(sen_3==3){
                	    	
                	    	//btnA.setBackground(Color.RED);
                	    	button_5.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 3 where sensor_name= 'sensor3' ";
                	    	int a= stmt.executeUpdate(sql);
                	    }
                	    else{
                	    	//btnA.setBackground(Color.GREEN);
                	    	button_5.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 9 where sensor_name= 'sensor3' ";
                	    	stmt.executeUpdate(sql);
                	    	empty++;
                	    }
                	    
                	    if(sen_4==4){
                	    	//btnB.setBackground(Color.RED);
                	    	button_11.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 4 where sensor_name= 'sensor4' ";
                	    	int a= stmt.executeUpdate(sql);
                	    }
                	    else{
                	    	//btnB.setBackground(Color.GREEN);
                	    	button_11.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 9 where sensor_name= 'sensor4' ";
                	    	stmt.executeUpdate(sql);
                	    	empty++;
                	    }
                	    
                	    if(sen_5==5){
                	    	//btnB_1.setBackground(Color.RED);
                	    	button_9.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 5 where sensor_name= 'sensor5' ";
                	    	int a= stmt.executeUpdate(sql);
                	    }
                	    else{
                	    	//btnB_1.setBackground(Color.GREEN);
                	    	button_9.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 9 where sensor_name= 'sensor5' ";
                	    	stmt.executeUpdate(sql);
                	    	empty++;
                	    }
                	    if(sen_6==6){
                	    	//btnB_1.setBackground(Color.RED);
                	    	btnA.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 6 where sensor_name= 'sensor6' ";
                	    	int a= stmt.executeUpdate(sql);
                	    }
                	    else{
                	    	//btnB_1.setBackground(Color.GREEN);
                	    	btnA.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
                	    	String sql = "UPDATE status " +
                                    "SET sensor_data = 9 where sensor_name= 'sensor6' ";
                	    	stmt.executeUpdate(sql);
                	    	empty++;
                	    }
                	 // System.out.println(sen_1);
                	//  System.out.println(sen_2);
                	  //System.out.println(sen_3);
                	  //System.out.println(sen_4);
                	  //System.out.println(sen_5);
              
                    sendData(" "+empty);
                    label_2.setText("Empty Slots : "+empty);
                 
                    
                   
            }
        } 
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public ui() {
    	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Nikhit\\Desktop\\park.jpg"));
    	setBackground(Color.WHITE);
    	setTitle("SMART PARKING");
        appName = getClass().getName();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(20, 20, 1189, 729);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(255, 255, 255));
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		appName = getClass().getName();
		
		Label label_4 = new Label("Under the guidance of Prof. S G Raghavendra Prasad");
		label_4.setFont(new Font("Batang", Font.BOLD, 15));
		label_4.setForeground(Color.WHITE);
		label_4.setBackground(new Color(25, 25, 112));
		label_4.setBounds(10, 670, 506, 23);
		contentPane.add(label_4);
		
		Label label_1 = new Label("Developed by, Nikhit kumar , Abhijith S , Shruti jajoo");
		label_1.setFont(new Font("Berlin Sans FB", Font.BOLD, 15));
		label_1.setForeground(new Color(255, 255, 255));
		label_1.setBackground(new Color(25, 25, 112));
		label_1.setBounds(10, 641, 750, 23);
		contentPane.add(label_1);
		
		Label label = new Label("INTELLIGENT PARKING SYSTEM");
		label.setBackground(new Color(25, 25, 112));
		label.setFont(new Font("Serif", Font.PLAIN, 40));
		label.setBounds(86, 20, 644, 40);
		contentPane.add(label);
		
		JButton button_13 = new JButton("");
		button_13.setForeground(SystemColor.menu);
		button_13.setBackground(new Color(25, 25, 112));
		button_13.setIcon(new ImageIcon("C:\\Users\\Nikhit\\Desktop\\RVCE_New_Logo.png"));
		button_13.setBounds(0, 0, 80, 80);
		contentPane.add(button_13);
		
		Canvas canvas_27 = new Canvas();
		canvas_27.setBackground(Color.YELLOW);
		canvas_27.setBounds(475, 337, 38, 8);
		contentPane.add(canvas_27);
		
		Canvas canvas_26 = new Canvas();
		canvas_26.setBackground(Color.YELLOW);
		canvas_26.setBounds(519, 337, 38, 8);
		contentPane.add(canvas_26);
		
		Canvas canvas_25 = new Canvas();
		canvas_25.setBackground(Color.YELLOW);
		canvas_25.setBounds(571, 337, 38, 8);
		contentPane.add(canvas_25);
		
		Canvas canvas_24 = new Canvas();
		canvas_24.setBackground(Color.YELLOW);
		canvas_24.setBounds(623, 337, 38, 8);
		contentPane.add(canvas_24);
		
		Canvas canvas_23 = new Canvas();
		canvas_23.setBackground(Color.YELLOW);
		canvas_23.setBounds(675, 337, 38, 8);
		contentPane.add(canvas_23);
		
		Canvas canvas_22 = new Canvas();
		canvas_22.setBackground(Color.YELLOW);
		canvas_22.setBounds(724, 337, 38, 8);
		contentPane.add(canvas_22);
		
		Canvas canvas_20 = new Canvas();
		canvas_20.setBackground(Color.YELLOW);
		canvas_20.setBounds(334, 253, 38, 8);
		contentPane.add(canvas_20);
		
		Canvas canvas_19 = new Canvas();
		canvas_19.setBackground(Color.YELLOW);
		canvas_19.setBounds(278, 253, 38, 8);
		contentPane.add(canvas_19);
		
		Canvas canvas_18 = new Canvas();
		canvas_18.setBackground(Color.YELLOW);
		canvas_18.setBounds(216, 253, 38, 8);
		contentPane.add(canvas_18);
		
		Canvas canvas_17 = new Canvas();
		canvas_17.setBackground(Color.YELLOW);
		canvas_17.setBounds(157, 253, 38, 8);
		contentPane.add(canvas_17);
		
		Canvas canvas_16 = new Canvas();
		canvas_16.setBackground(Color.YELLOW);
		canvas_16.setBounds(101, 253, 38, 8);
		contentPane.add(canvas_16);
		
		Canvas canvas_15 = new Canvas();
		canvas_15.setBackground(Color.YELLOW);
		canvas_15.setBounds(387, 518, 38, 8);
		contentPane.add(canvas_15);
		
		Canvas canvas_14 = new Canvas();
		canvas_14.setBackground(Color.YELLOW);
		canvas_14.setBounds(334, 518, 38, 8);
		contentPane.add(canvas_14);
		
		Canvas canvas_13 = new Canvas();
		canvas_13.setBackground(Color.YELLOW);
		canvas_13.setBounds(278, 518, 38, 8);
		contentPane.add(canvas_13);
		
		Canvas canvas_12 = new Canvas();
		canvas_12.setBackground(Color.YELLOW);
		canvas_12.setBounds(216, 518, 38, 8);
		contentPane.add(canvas_12);
		
		Canvas canvas_11 = new Canvas();
		canvas_11.setBackground(Color.YELLOW);
		canvas_11.setBounds(157, 518, 38, 8);
		contentPane.add(canvas_11);
		
		Canvas canvas_10 = new Canvas();
		canvas_10.setBackground(Color.YELLOW);
		canvas_10.setBounds(101, 518, 38, 8);
		contentPane.add(canvas_10);
		
		Canvas canvas_9 = new Canvas();
		canvas_9.setBackground(Color.YELLOW);
		canvas_9.setBounds(417, 103, 8, 50);
		contentPane.add(canvas_9);
		
		Canvas canvas_8 = new Canvas();
		canvas_8.setBackground(Color.YELLOW);
		canvas_8.setBounds(417, 169, 8, 50);
		contentPane.add(canvas_8);
		
		Canvas canvas_7 = new Canvas();
		canvas_7.setBackground(Color.YELLOW);
		canvas_7.setBounds(417, 243, 8, 50);
		contentPane.add(canvas_7);
		
		Canvas canvas_6 = new Canvas();
		canvas_6.setBackground(Color.YELLOW);
		canvas_6.setBounds(417, 316, 8, 50);
		contentPane.add(canvas_6);
		
		Canvas canvas_5 = new Canvas();
		canvas_5.setBackground(Color.YELLOW);
		canvas_5.setBounds(417, 387, 8, 50);
		contentPane.add(canvas_5);
		
		Canvas canvas_4 = new Canvas();
		canvas_4.setBackground(Color.YELLOW);
		canvas_4.setBounds(417, 456, 8, 50);
		contentPane.add(canvas_4);
		
		
		btnA.setBounds(172, 169, 55, 60);
		contentPane.add(btnA);
		
		
		btnB.setBounds(623, 365, 55, 60);
		contentPane.add(btnB);
		
		Canvas canvas = new Canvas();
		canvas.setBackground(Color.DARK_GRAY);
		canvas.setBounds(369, 103, 100, 403);
		contentPane.add(canvas);
		
		Canvas canvas_1 = new Canvas();
		canvas_1.setBounds(463, 315, 299, 44);
		canvas_1.setBackground(Color.DARK_GRAY);
		contentPane.add(canvas_1);
		
		button = new JButton("New button");
		button.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button.setBounds(101, 285, 55, 60);
		contentPane.add(button);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		btnNewButton_3.setBounds(172, 285, 55, 60);
		contentPane.add(btnNewButton_3);
		
		Canvas canvas_2 = new Canvas();
		canvas_2.setBackground(Color.DARK_GRAY);
		canvas_2.setBounds(100, 235, 311, 44);
		contentPane.add(canvas_2);
		
		
		//ImageIcon imageForOne = new ImageIcon(getClass().getResource("parking.jpeg"));
		Icon icon = new ImageIcon ("parking.jpeg");
		JButton back = new JButton(icon);
		
		JButton button_1 = new JButton("New button");
		button_1.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_1.setBounds(705, 249, 55, 60);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("New button");
		button_2.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_2.setBounds(623, 249, 55, 60);
		contentPane.add(button_2);
		
		JButton button_3 = new JButton("New button");
		button_3.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_3.setBounds(705, 365, 55, 60);
		contentPane.add(button_3);
		
		button_5 = new JButton("New button");
		button_5.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		button_5.setBounds(543, 249, 55, 60);
		contentPane.add(button_5);
		
		//JButton button_6 = new JButton("New button");
		button_8.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_8.setBounds(543, 365, 55, 60);
		contentPane.add(button_8);
		
		JButton button_7 = new JButton("New button");
		button_7.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_7.setBounds(475, 249, 55, 60);
		contentPane.add(button_7);
		
		//JButton button_8 = new JButton("New button");
		button_6.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		button_6.setBounds(475, 365, 55, 60);
		contentPane.add(button_6);
		
		button_9 = new JButton("New button");
		button_9.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		button_9.setBounds(239, 285, 55, 60);
		contentPane.add(button_9);
		
		JButton button_10 = new JButton("New button");
		button_10.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_10.setBounds(239, 169, 55, 60);
		contentPane.add(button_10);
		
		button_11 = new JButton("New button");
		button_11.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		button_11.setBounds(308, 169, 55, 60);
		contentPane.add(button_11);
		
		JButton button_12 = new JButton("New button");
		button_12.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_12.setBounds(306, 285, 55, 60);
		contentPane.add(button_12);
		
		btnB.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		btnA.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		
		Canvas canvas_3 = new Canvas();
		canvas_3.setBackground(Color.DARK_GRAY);
		canvas_3.setBounds(74, 477, 395, 90);
		contentPane.add(canvas_3);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setBounds(443, 401, 377, -35);
		contentPane.add(horizontalStrut);
		
		JLabel lblEntry = new JLabel("ENTRY");
		lblEntry.setForeground(Color.WHITE);
		lblEntry.setBounds(10, 449, 38, 14);
		contentPane.add(lblEntry);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fgr.png"));
		btnNewButton.setBounds(888, 110, 48, 40);
		contentPane.add(btnNewButton);
		
		JButton button_4 = new JButton("New button");
		button_4.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		button_4.setBounds(888, 169, 48, 40);
		contentPane.add(button_4);
		
		JLabel lblEmptyParkingSpot = new JLabel("Empty Parking Spot");
		lblEmptyParkingSpot.setForeground(new Color(0, 153, 102));
		lblEmptyParkingSpot.setBackground(Color.DARK_GRAY);
		lblEmptyParkingSpot.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblEmptyParkingSpot.setBounds(953, 110, 120, 37);
		contentPane.add(lblEmptyParkingSpot);
		
		JLabel lblOccupiedParkingSpot = new JLabel("Occupied Parking spot");
		lblOccupiedParkingSpot.setForeground(new Color(255, 0, 0));
		lblOccupiedParkingSpot.setBackground(Color.DARK_GRAY);
		lblOccupiedParkingSpot.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblOccupiedParkingSpot.setBounds(948, 169, 159, 37);
		contentPane.add(lblOccupiedParkingSpot);
		
		Canvas canvas_21 = new Canvas();
		canvas_21.setBackground(Color.YELLOW);
		canvas_21.setBounds(475, 337, 38, 8);
		contentPane.add(canvas_21);
		
		Canvas canvas_28 = new Canvas();
		canvas_28.setForeground(new Color(255, 255, 255));
		canvas_28.setBackground(new Color(25, 25, 112));
		canvas_28.setBounds(0, 0, 1500, 80);
		contentPane.add(canvas_28);
		btnNewButton_1.setBackground(new Color(0, 0, 128));
		btnNewButton_1.setIcon(new ImageIcon("C:\\Users\\ACER\\Desktop\\fr.png"));
		
		
		btnNewButton_1.setBounds(101, 169, 55, 60);
		contentPane.add(btnNewButton_1);
		
		Canvas canvas_29 = new Canvas();
		canvas_29.setBackground(new Color(25, 25, 112));
		canvas_29.setBounds(0, 622, 1500, 128);
		contentPane.add(canvas_29);
		
		label_2 = new Label("Empty Slots : 6");
		label_2.setForeground(new Color(25, 25, 112));
		label_2.setFont(new Font("Serif", Font.BOLD, 20));
		label_2.setBounds(908, 402, 150, 23);
		contentPane.add(label_2);
		
		Label label_3 = new Label("Total Slots : 16");
		label_3.setForeground(new Color(25, 25, 112));
		label_3.setFont(new Font("Serif", Font.BOLD, 20));
		label_3.setBounds(908, 365, 150, 23);
		contentPane.add(label_3);
		
		JLabel lblA = new JLabel("A1");
		lblA.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA.setBounds(114, 151, 25, 16);
		contentPane.add(lblA);
		
		JLabel lblA_1 = new JLabel("A2");
		lblA_1.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_1.setBounds(182, 152, 25, 16);
		contentPane.add(lblA_1);
		
		JLabel lblA_2 = new JLabel("A3");
		lblA_2.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_2.setBounds(252, 152, 25, 16);
		contentPane.add(lblA_2);
		
		JLabel lblA_3 = new JLabel("A4");
		lblA_3.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_3.setBounds(322, 152, 25, 16);
		contentPane.add(lblA_3);
		
		JLabel lblA_4 = new JLabel("A5");
		lblA_4.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_4.setBounds(111, 350, 25, 16);
		contentPane.add(lblA_4);
		
		JLabel lblA_5 = new JLabel("A6");
		lblA_5.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_5.setBounds(182, 350, 25, 16);
		contentPane.add(lblA_5);
		
		JLabel lblA_6 = new JLabel("A7");
		lblA_6.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_6.setBounds(249, 350, 25, 16);
		contentPane.add(lblA_6);
		
		JLabel lblA_7 = new JLabel("A8");
		lblA_7.setFont(new Font("Mangal", Font.BOLD, 14));
		lblA_7.setBounds(316, 350, 25, 16);
		contentPane.add(lblA_7);
		
		JLabel lblB = new JLabel("B1");
		lblB.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB.setBounds(487, 231, 25, 16);
		contentPane.add(lblB);
		
		JLabel lblB_1 = new JLabel("B2");
		lblB_1.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_1.setBounds(555, 232, 25, 16);
		contentPane.add(lblB_1);
		
		JLabel lblB_2 = new JLabel("B3");
		lblB_2.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_2.setBounds(636, 232, 25, 16);
		contentPane.add(lblB_2);
		
		JLabel lblB_3 = new JLabel("B4");
		lblB_3.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_3.setBounds(715, 232, 25, 16);
		contentPane.add(lblB_3);
		
		JLabel lblB_4 = new JLabel("B5");
		lblB_4.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_4.setBounds(491, 431, 25, 16);
		contentPane.add(lblB_4);
		
		JLabel lblB_5 = new JLabel(" B6");
		lblB_5.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_5.setBounds(553, 432, 25, 16);
		contentPane.add(lblB_5);
		
		JLabel lblB_6 = new JLabel(" B7");
		lblB_6.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_6.setBounds(636, 432, 25, 16);
		contentPane.add(lblB_6);
		
		JLabel lblB_7 = new JLabel("B8");
		lblB_7.setFont(new Font("Mangal", Font.BOLD, 14));
		lblB_7.setBounds(724, 432, 25, 16);
		contentPane.add(lblB_7);
    }
    
    public static void main(String[] args) throws Exception {
    	 Class.forName("com.mysql.jdbc.Driver");
    	 try{
         //STEP 3: Open a connection
         System.out.println("Connecting to a selected database...");
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         System.out.println("Connected database successfully...");
         System.out.println("Creating statement...");
         stmt = conn.createStatement();
         
        // rs.close();
      }catch(SQLException se){
         //Handle errors for JDBC
         se.printStackTrace();
      }catch(Exception e){
         //Handle errors for Class.forName
         e.printStackTrace();
      }finally{
         //finally block used to close resources
    /*     try{
            if(stmt!=null)
               conn.close();
         }catch(SQLException se){
         }// do nothing
         try{
            if(conn!=null)
               conn.close();
         }catch(SQLException se){
            se.printStackTrace();
         }//end finally try*/
      }
        ui test = new ui();
        test.setVisible(true);
        if ( test.initialize() ) {
         //   test.sendData("y");
           // try { Thread.sleep(2000); } catch (InterruptedException ie) {}
          
           // test.close();
        }

        // Wait 5 seconds then shutdown
      //  try { Thread.sleep(4000); } catch (InterruptedException ie) {}
    }
}



