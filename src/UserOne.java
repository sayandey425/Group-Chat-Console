import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class UserOne extends JFrame implements ActionListener, Runnable{
	
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JTextArea a1;
	
	BufferedWriter writer;
	BufferedReader reader;
	
	Boolean typing;

	UserOne(){
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(29,161,242));
		p1.setBounds(0, 0, 450, 70);
		add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/pngwave.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/Octocat.png"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/3dd.png"));
		Image i8 = i7.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		
		
		JLabel l5 = new JLabel(i9);
		l5.setBounds(390, 4, 60, 60);
		p1.add(l5);
		
		ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i12 = new ImageIcon(i11);
		
		JLabel l6 = new JLabel(i12);
		l6.setBounds(350, 20, 35, 30);
		p1.add(l6);
		
		JLabel l3 = new JLabel("Java Developers Group");
		l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 15, 250, 18);
		p1.add(l3);
		
		JLabel l4 = new JLabel("UserSecond,UserThird,UserFourth");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 35, 250, 20);
		p1.add(l4);
		
		Timer t = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if( ! typing) {
					l4.setText("UserSecond,UserThird,UserFourth");
				}
			}
		});
		
		t.setInitialDelay(800);
		
		a1 = new JTextArea();                                   
		a1.setBounds(5, 75, 440, 550);                          
		a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));      
		a1.setEditable(false);                                  
		a1.setLineWrap(true);
		a1.setWrapStyleWord(true);
		add(a1);
		
		t1 = new JTextField();
		t1.setBounds(5, 630, 330, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		add(t1);
		
		t1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				l4.setText("UserOne typing..");
				t.stop();
				typing = true;
			}
			public void keyReleased(KeyEvent ke) {
				typing = false;
				
				if( ! t.isRunning()) {
					t.restart();
				}
			}
			
		});
		
		b1 = new JButton("Send");
		b1.setBounds(342, 630, 100, 40);
		b1.setBackground(new Color(29, 161, 242));
		b1.setForeground(Color.BLUE);
		b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		b1.addActionListener(this);
		add(b1);
		
		getContentPane().setBackground(Color.WHITE);
		setLayout(null);
		setSize(450, 700);
		setLocation(20, 200);
//		setUndecorated(true);
		setVisible(true);
		
		
		try {
			Socket socketClient = new Socket("localhost", 5007);
			 writer = new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())); 
			 reader = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
			
		}catch(Exception e) {
		
		}
		
	}
		
	public void actionPerformed(ActionEvent ae) {
		
		String str = "UserOne:\n"+ t1.getText();
		try {
			writer.write(str);
			writer.write("\r\n");
			writer.flush();
			
		}catch(Exception e) {
			
		}
		t1.setText("");
	}
	
	public void run() {
		 
		try {
			String message = "";
			while((message = reader.readLine()) != null) {
				a1.append(message +"\n");
			}
			
		}catch(Exception e) {
			
		}
	}
	public static void main(String[] args) {
		UserOne one = new UserOne(); 
		Thread t1 = new Thread(one);
		t1.start();
	}

}
