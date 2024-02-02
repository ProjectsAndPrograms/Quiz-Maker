package Client;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class Student extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String rollno;
	
	JPanel panel;
	JPanel beforeReadyPanel;
	JLabel beforeReadyLabel;
	JPanel waitingForStartPanel;
	
	JLabel readyLoading;
	JLabel startLoadinglabel;
	
	JButton readyBtn;
	JButton startBtn;
	JButton exitBtn;
	JButton closeButton;
	JButton configBtn;
	
	JTextField nameField;
	JTextField rollField;
	
	ArrayList<String> questions;
	ArrayList<ArrayList<String>> options;
	ArrayList<String> answers;
	
	JLabel warningLabel;
	
	static int x;
	static int y;
	
	
	ExamPanel exampanel;
	
	boolean serverIsReady = false;
	boolean serverIsReadyToStart = false;
	boolean running = true;
	boolean examIsStarted = true;
	
	
	JLabel saving = new JLabel();
	
	static int  fullWidth;
	static int  fullHeight ;
	
	public Student() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setBounds(100,100,700,480);
		this.setTitle("EXAM TIME");
		this.setIconImage((new ImageIcon("images/q.png")).getImage());
		this.setResizable(true);
		this.addWindowListener(this);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0,0,this.getWidth(),this.getHeight());
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.red, Color.green));
		
		
	// --------------------------------------- CREATING READY PANEL ----------------------------------------- start
		beforeReadyPanel = new JPanel();
		beforeReadyPanel.setLayout(null);
		beforeReadyPanel.setSize(600,500);

		x = (this.getWidth()/2) - (beforeReadyPanel.getWidth()/2);
		y = (panel.getHeight()/2) - (beforeReadyPanel.getHeight()/2) - 20;
		
		beforeReadyPanel.setLocation(x, y);
		
		
		ImageIcon configIcon = new ImageIcon("images/config.png");
		configBtn = new JButton();
		configBtn.setSize(50,50);
		configBtn.setBackground(Color.white);
		configBtn.setFocusable(false);
		configBtn.setToolTipText("Configure database");
		configBtn.setIcon(configIcon);
		configBtn.setLocation(panel.getWidth()-50,panel.getHeight() - 50);
		configBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				configBtn.setEnabled(false);
				new DataBaseEntryDialog(configBtn);
				
			}
		});
		
		
		
		ImageIcon close = new ImageIcon("images/exit.png");
		closeButton = new JButton();
		closeButton.setSize(close.getIconWidth(),close.getIconHeight());
		closeButton.setFocusable(false);
		closeButton.setIcon(close);
		UIManager.put("ToolTip.background", Color.ORANGE);
		UIManager.put("ToolTip.foreground", Color.BLACK);
		UIManager.put("ToolTip.font", new Font("Arial", Font.BOLD, 14));
		closeButton.setToolTipText("<html>exit</html>");
		
		
		closeButton.setBackground(Color.white);
		closeButton.setLocation(panel.getWidth() - closeButton.getWidth(), 0);
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(closeButton);
		
		beforeReadyLabel = new JLabel();
		beforeReadyLabel.setText("Connect to server");
		beforeReadyLabel.setHorizontalAlignment(JLabel.CENTER);
		beforeReadyLabel.setVerticalAlignment(JLabel.CENTER);
		beforeReadyLabel.setHorizontalTextPosition(JLabel.CENTER);
		beforeReadyLabel.setVerticalTextPosition(JLabel.BOTTOM);
		beforeReadyLabel.setFont(new Font("Arial",Font.BOLD,40));
		beforeReadyLabel.setSize(530,100);
		beforeReadyLabel.setLocation((beforeReadyPanel.getWidth()/2) - (beforeReadyLabel.getWidth()/2),  140);

		readyBtn = new JButton("connect");
		readyBtn.setSize(200,50);
		readyBtn.setFont(new Font("verdana",Font.BOLD,20));
		readyBtn.setForeground(Color.red);
		readyBtn.setIconTextGap(15);
		readyBtn.setToolTipText("<html><i>connect when server is ready !</i></html>");
		readyBtn.setLocation((beforeReadyPanel.getWidth()/2) - (readyBtn.getWidth()/2), 250);
		readyBtn.setFocusable(false);
		readyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				panel.remove(configBtn);
				panel.revalidate();
				panel.repaint();
				
				readyBtn.setEnabled(false);
			
				
				IsReady th1 = new IsReady();
				Loading th2 = new Loading(th1);
				
				th1.start();
				th2.start();
				
				
				
			}
		});
	
		
		

		readyLoading = new JLabel();
		readyLoading.setSize(300,100);
		readyLoading.setVerticalTextPosition(JLabel.CENTER);
		readyLoading.setHorizontalTextPosition(JLabel.CENTER);
		readyLoading.setHorizontalAlignment(JLabel.CENTER);
		readyLoading.setVerticalAlignment(JLabel.CENTER);
		readyLoading.setLocation((beforeReadyPanel.getWidth()/2) - (readyLoading.getWidth()/2), 290);
		
		
		beforeReadyPanel.add(readyLoading);
		beforeReadyPanel.add(beforeReadyLabel);
		beforeReadyPanel.add(readyBtn);
		// --------------------------------------- CREATING READY PANEL ----------------------------------------- end
		
		waitingForStartPanel = new JPanel();
		waitingForStartPanel.setBounds(100,100,100,100);
		waitingForStartPanel.setBackground(Color.yellow);
		waitingForStartPanel.setOpaque(true);
		
		
		panel.add(beforeReadyPanel);
		panel.add(configBtn);
		this.add(panel);
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new Student();
	}
	
	class Loading extends Thread{
		
		Thread th;
		
		Loading(Thread th){
			this.th = th;
		}
		
		synchronized public void run() {
			
			while(th.isAlive()) {
				readyLoading.revalidate();
				readyLoading.repaint();
				readyLoading.setIcon(new ImageIcon("images/upload.gif"));
			
				try {
					Thread.sleep(50);
				}catch (InterruptedException e) {
					Const.showError("Error", "x00000343 error");
				}
			}
			
			
			
			readyLoading.setIcon(null);
			if(!serverIsReady && !Const.errorInConnection) {
				showAlert("Warning","Server is not ready yet !");
				panel.add(configBtn);
				panel.revalidate();
				panel.repaint();
			}
			
			
			Const.errorInConnection = false;
			readyBtn.setEnabled(true);
			
			if(serverIsReady) {
				
				panel.remove(closeButton);
				panel.revalidate();
				panel.repaint();
				Student.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
				Student.this.setAlwaysOnTop(true);
			
				fullWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
				fullHeight  = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
				panel.remove(beforeReadyPanel);
				panel.setSize(fullWidth, fullHeight);
			
				
				System.out.println(fullWidth + " " + fullHeight);
				
				panel.add(new BeforeStartPanel());
				panel.revalidate();
				panel.repaint();
		
				try {
					fectchAllData();
				} catch (SQLException | ClassNotFoundException   e) {
					showAlert("connection probelm", "unable to connect to the database");
				}
			}
			
			
			
		}
	}
	public void fectchAllData() throws SQLException, ClassNotFoundException {

		DBclient db = new DBclient();
		questions = db.getAllQuestions();
		answers = db.getAllAnswers();
		options = db.getAllOptions();
		
		
	}
	
	class IsReady extends Thread{

		
		
		@Override
		synchronized public void run() {
			
			try {
				DBclient db = new DBclient();
				
				serverIsReady = db.checkReady();
				
				db.closeConnection();
				
				
			} catch (SQLException | ClassNotFoundException  e) {
				Const.errorInConnection = true;
				showAlert("alert","<html>Internet connection is required !<br>or may your connection database not availabe. </html>");
				
			}
			
				
		}
	}
	
	class StartLoading extends Thread{
		
		Thread th;
		
		StartLoading(Thread th){
			this.th = th;
		}
		
		@Override
		synchronized public void run() {                       
			while(th.isAlive()) {
				startBtn.setEnabled(false);
				startLoadinglabel.setIcon(new ImageIcon("images/progressbar.gif"));
			
				try {
					Thread.sleep(50);
				}catch (InterruptedException e) {
					
				}
				startLoadinglabel.revalidate();
				startLoadinglabel.repaint();
			}
			startLoadinglabel.setIcon(null);
			startLoadinglabel.revalidate();
			startLoadinglabel.repaint();
			
			if(!serverIsReadyToStart) {
				warningLabel.setText("Server is not ready yet !");
				
				if(Const.internetAvailable == false) {
					warningLabel.setText("No internet !");
					
				}
				
				startBtn.setEnabled(true);
				nameField.setEditable(true);
				rollField.setEditable(true);
			}else {
				panel.removeAll();
				panel.add(exampanel = new ExamPanel());
				panel.revalidate();
				panel.repaint();
			}
			
		}
		
	}
	
	
	class IsStart extends Thread{


		@Override
		synchronized public void run() {
			
			try {
				DBclient db = new DBclient();
				
				serverIsReadyToStart = db.checkStart();
				
				db.closeConnection();
				
				Const.internetAvailable = true;
			} catch (SQLException | ClassNotFoundException  e) {
				warningLabel.setText("Internet connection is required !");
				
				startBtn.setEnabled(true);
				Const.internetAvailable = false;
				nameField.setEditable(true);
				rollField.setEditable(true);
				
			}
			
				
		}
	}
	
	class BeforeStartPanel extends JPanel implements ActionListener {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		JLabel nameLabel;
		JLabel rollLabel;
		
		
		
		JLabel nameAlert;
		JLabel rollAlert;
		
		
		JButton exitBtn;
		ImageIcon userIcon;
		JLabel userImageLabel;
		
		Font labelfont = new Font("verdana",Font.PLAIN,30);
		Font textfont = new Font("vardana",Font.PLAIN,20);
		
		BeforeStartPanel(){
			
			
			
			this.setLayout(null);
			
			this.setBounds(0,0,fullWidth,fullHeight);
			
			ImageIcon exitIcon = new ImageIcon("images/exit.png");
			exitBtn = new JButton();
			exitBtn.setSize(exitIcon.getIconWidth(), exitIcon.getIconHeight());
			exitBtn.setBackground(Color.white);
			exitBtn.setIcon(exitIcon);
			exitBtn.setLocation(this.getWidth()-exitIcon.getIconWidth(), 0);
			exitBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			this.add(exitBtn);
			
			JPanel namepanel = new JPanel(); 
			namepanel.setSize(550,50);
			namepanel.setLocation((this.getWidth()/2)-(namepanel.getWidth()/2),(this.getHeight()/2)-(namepanel.getHeight()/2));

			userIcon = new ImageIcon("images/user.png");
			userImageLabel = new JLabel(userIcon);
			userImageLabel.setSize(userIcon.getIconWidth()+50,userIcon.getIconHeight()+50);
			userImageLabel.setBorder(BorderFactory.createLineBorder(Color.blue,2));
			userImageLabel.setLocation(namepanel.getX()+(namepanel.getWidth()/2) - (userImageLabel.getWidth()/2),namepanel.getY()-userImageLabel.getHeight()-50);
			
			nameLabel = new JLabel("Enter name :");
			nameLabel.setFont(labelfont);
			
			nameField = new JTextField();
			nameField.setPreferredSize(new Dimension(300,40));
			nameField.setForeground(Color.blue);
			nameField.setOpaque(true);
			nameField.setFont(textfont);
			nameField.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {}
				
				@Override
				public void keyReleased(KeyEvent e) {
					if(nameField.getText().length() >= 100) {
						warningLabel.setText("name is to huge ! not acceptable %%%%");
					}else {
						warningLabel.setText("");
					}
					nameAlert.setText("");
					
				}
				
				@Override
				public void keyPressed(KeyEvent e) {}
			});
			
			nameAlert = new JLabel();
			nameAlert.setFont(new Font("Arial",Font.ITALIC,14));
			nameAlert.setSize(170,20);
			nameAlert.setForeground(Color.red);
			nameAlert.setLocation(namepanel.getX() + namepanel.getWidth() - nameAlert.getWidth(),namepanel.getY()+namepanel.getHeight());
		
			namepanel.add(nameLabel);
			namepanel.add(nameField);
			
			JPanel rollpanel = new JPanel(); 
			rollpanel.setSize(550,50);
			rollpanel.setLocation(namepanel.getX(),namepanel.getY() + namepanel.getHeight() + 30);

			
			rollLabel = new JLabel("Entrollment :");
			rollLabel.setFont(labelfont);
			
			rollField = new JTextField();
			rollField.setPreferredSize(new Dimension(300,40));
			rollField.setForeground(Color.blue);
			rollField.setOpaque(true);
			rollField.setFont(textfont);
			rollField.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					
					if(rollField.getText().length() > 14) {
						warningLabel.setText("Enterollment must be less than 15 characters !!!");
					}else {
						warningLabel.setText("");
					}
					rollAlert.setText("");
				}
				
				@Override
				public void keyReleased(KeyEvent e) {}
				
				@Override
				public void keyPressed(KeyEvent e) {}
			});
		
			rollAlert = new JLabel();
			rollAlert.setFont(new Font("Arial",Font.ITALIC,14));
			rollAlert.setSize(190,20);
			rollAlert.setForeground(Color.red);
			rollAlert.setLocation(rollpanel.getX() + rollpanel.getWidth() - rollAlert.getWidth()-8,rollpanel.getY()+rollpanel.getHeight());
		
			
			rollpanel.add(rollLabel);
			rollpanel.add(rollField);
			

			JPanel startBtnPanel = new JPanel(); 
			startBtnPanel.setSize(200,100);
			startBtnPanel.setLocation(((this.getWidth()/2) - (startBtnPanel.getWidth()/2)),rollpanel.getY() + rollpanel.getHeight() + 30);

			
			startBtn = new JButton("Start Exam");
			startBtn.setPreferredSize(new Dimension(200,50));
			startBtn.setFocusable(false);
			startBtn.setFont(new Font("Arial",Font.PLAIN,17));
			startBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					
					warningLabel.setText("");
					
					if(nameField.getText().isBlank() || rollField.getText().isBlank()) {
						
						if(nameField.getText().isBlank()) {
							nameAlert.setText("name can't be empty !");							
						}else {
							nameAlert.setText("");
						}
						
						if( rollField.getText().isBlank()) {
							rollAlert.setText("entrollment can't be empty !");
						}else {
							nameAlert.setText("");
						}
					}
					else {
						
						examIsStarted = true;
						
						nameField.setEditable(false);
						rollField.setEditable(false);
						
						nameAlert.setText("");
						rollAlert.setText("");
						
						username = nameField.getText();
						rollno = rollField.getText();
						
						IsStart th1 = new IsStart();
						StartLoading th2 = new StartLoading(th1);
						
						th1.start();
						th2.start();
					}
					
				}
			});
			startBtn.addActionListener(this);
			
			
			startLoadinglabel = new JLabel();
			startLoadinglabel.setSize(100,100);
			startLoadinglabel.setVerticalTextPosition(JLabel.CENTER);
			startLoadinglabel.setHorizontalTextPosition(JLabel.CENTER);
			startLoadinglabel.setHorizontalAlignment(JLabel.CENTER);
			startLoadinglabel.setVerticalAlignment(JLabel.CENTER);
			
			
			startBtnPanel.add(startBtn);
			startBtnPanel.add(startLoadinglabel);
			
			warningLabel = new JLabel();
			warningLabel.setSize(500,40);
			warningLabel.setFont(new Font("Arial", Font.ITALIC|Font.BOLD, 23));
			warningLabel.setForeground(Color.red);
			warningLabel.setHorizontalTextPosition(JLabel.CENTER);
			warningLabel.setVerticalTextPosition(JLabel.CENTER);
			warningLabel.setHorizontalAlignment(JLabel.CENTER);
			warningLabel.setOpaque(true);
			warningLabel.setLocation((this.getWidth()/2)-(warningLabel.getWidth()/2), startBtnPanel.getY() +startBtnPanel.getHeight()  + 30 );
			this.add(warningLabel);
			
			this.add(userImageLabel);
			this.add(namepanel);
			this.add(nameAlert);
			this.add(rollpanel);
			this.add(rollAlert);
			this.add(startBtnPanel);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		
	}
	 // *********************************************** EXAM PANEL ********************************************************start
		
	/// !!!!!!!!!!!!!!!!!! USE RADIO BUTTONS WITH IMAGE RATHER THAN BUTTONS AND TEXTAREA
	
	class ExamPanel extends JPanel implements ActionListener{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		JPanel mainExamPanel;
		
		JLabel nameLabel;
		JLabel rollLabel;
		JPanel name_roll_panel;
		Font labelfont = new Font("Arial",Font.PLAIN,20);
		
		int index = 0;
		int seconds = 10;
		int total_questions = questions.size();
		public static int correct_answers = 0;
		int result;
		String answer;
		
		JTextField textField = new JTextField();
		JTextArea textarea = new JTextArea();
		
		JRadioButton btnA;
		JRadioButton btnB;
		JRadioButton btnC;
		JRadioButton btnD;
		
		ImageIcon optionIcon = new ImageIcon("images/uncheck.png");
		ImageIcon selectedIcon = new ImageIcon("images/selected4.png");
		
		JLabel timeLabel = new JLabel();
		JLabel seconds_left = new JLabel();
	
		
		
		javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				seconds --;
				seconds_left.setText(String.valueOf(seconds));
				
				if(seconds <= 0) {
					displayAnswer();
				}
			}
		});
		
		
		
		ExamPanel(){
			this.setBounds(0,0,fullWidth,fullHeight);
			this.setLayout(null);
			
			nameLabel = new JLabel("<html><b>Name :</b> " + username + "</html>");
			nameLabel.setBounds(0,0,300,35);
			nameLabel.setVerticalTextPosition(JLabel.CENTER);
			nameLabel.setHorizontalAlignment(JLabel.CENTER);
			nameLabel.setFont(labelfont);
			
			rollLabel = new JLabel("<html><b>Entrollment :</b> " + rollno + "</html>");
			rollLabel.setBounds(0,30,300,35);
			rollLabel.setVerticalTextPosition(JLabel.CENTER);
			rollLabel.setHorizontalAlignment(JLabel.CENTER);
			rollLabel.setFont(labelfont);
			
			name_roll_panel = new JPanel();
			name_roll_panel.setLocation(fullWidth-450, 50);
			name_roll_panel.setPreferredSize(new Dimension(400,100));
			name_roll_panel.setBackground(Color.orange);
			name_roll_panel.setOpaque(true);
			name_roll_panel.setLayout(new GridLayout(2,1));
			name_roll_panel.add(nameLabel);
			name_roll_panel.add(rollLabel);
			name_roll_panel.setBorder(BorderFactory.createLineBorder(Color.red, 4));
			
			
			this.add(name_roll_panel);
			
			mainExamPanel = new JPanel();
			mainExamPanel.setSize(this.getWidth()/2,this.getHeight() - 140);
			System.out.println(this.getWidth() + " : ");
//			mainExamPanel.setLocation((this.getWidth()/2)-(mainExamPanel.getWidth()/2) ,
//									(this.getHeight()/2)-(mainExamPanel.getHeight()/2) );
			mainExamPanel.setLocation(150 ,50);
			mainExamPanel.setLayout(new FlowLayout(FlowLayout.LEFT,0,7));
			
			textField.setPreferredSize(new Dimension(mainExamPanel.getWidth(),70));
			textField.setForeground(Color.red);
			textField.setFont(new Font("Ink free", Font.BOLD,30));
			textField.setBorder(BorderFactory.createLineBorder(Color.red, 2));
			textField.setOpaque(true);
			textField.setHorizontalAlignment(JTextField.CENTER);
			textField.setEditable(false);
			
			JLabel spacelabel = new JLabel();
			spacelabel.setPreferredSize(new Dimension(mainExamPanel.getWidth(),30));
			
			textarea.setBounds(0,70,mainExamPanel.getWidth(),100);
			textarea.setMargin(new Insets(100, 0, 100, 0));
			textarea.setLineWrap(true);
			textarea.setWrapStyleWord(true);
			textarea.setForeground(Color.BLACK);
			textarea.setFont(new Font("Verdana",Font.BOLD,25));
			textarea.setBorder(null);
			textarea.setBackground(mainExamPanel.getBackground());
			textarea.setEditable(false);
			
			
			btnA = new JRadioButton();
			btnA.setFont(new Font("verdana",Font.PLAIN,25));
			btnA.setPreferredSize(new Dimension(mainExamPanel.getWidth(),100));
			btnA.setFocusable(false);
			btnA.setForeground(Color.blue);
			btnA.addActionListener(this);
			
			btnB = new JRadioButton();
			btnB.setFont(new Font("verdana",Font.PLAIN,25));
			btnB.setPreferredSize(new Dimension(mainExamPanel.getWidth(),100));
			btnB.setFocusable(false);
			btnB.addActionListener(this);
			btnB.setForeground(Color.blue);
		
			
			btnC = new JRadioButton();
			btnC.setFont(new Font("verdana",Font.PLAIN,25));
			btnC.setPreferredSize(new Dimension(mainExamPanel.getWidth(),100));
			btnC.setFocusable(false);
			btnC.addActionListener(this);
			btnC.setForeground(Color.blue);
			
			btnD = new JRadioButton();
			btnD.setFont(new Font("verdana",Font.PLAIN,25));
			btnD.setPreferredSize(new Dimension(mainExamPanel.getWidth(),100));
			btnD.setFocusable(false);
			btnD.addActionListener(this);
			btnD.setForeground(Color.blue);
			
			ButtonGroup group = new ButtonGroup();
			group.add(btnA);
			group.add(btnB);
			group.add(btnC);
			group.add(btnD);
			
			mainExamPanel.add(textField);
			mainExamPanel.add(spacelabel);
			mainExamPanel.add(textarea);
			mainExamPanel.add(btnA);
			mainExamPanel.add(btnB);
			mainExamPanel.add(btnC);
			mainExamPanel.add(btnD);
			
			seconds_left.setBounds(fullWidth-150,
								   mainExamPanel.getY()+mainExamPanel.getHeight()-100,
								   100,100);
			seconds_left.setBackground(Color.DARK_GRAY);
			seconds_left.setForeground(Color.red);
			seconds_left.setFont(new Font("verdana",Font.PLAIN,60));
			seconds_left.setBorder(BorderFactory.createBevelBorder(1));
			seconds_left.setOpaque(true);
			seconds_left.setHorizontalAlignment(JTextField.CENTER);
			seconds_left.setText(String.valueOf(seconds));
					
			
			timeLabel.setBackground(Color.DARK_GRAY);
			timeLabel.setForeground(Color.red);
			timeLabel.setOpaque(true);
			timeLabel.setHorizontalAlignment(JTextField.CENTER);
			ImageIcon timericon = new ImageIcon("images/timer.gif");
			timeLabel.setIcon(timericon);
			timeLabel.setBorder(BorderFactory.createLineBorder(Color.green, 3));
			timeLabel.setBounds(seconds_left.getX()+20,
								seconds_left.getY()-timericon.getIconHeight()-20,
								timericon.getIconWidth(),
								timericon.getIconHeight());
			
			this.add(mainExamPanel);
			this.add(timeLabel);
			this.add(seconds_left);
			
			
			nextQuestion();
		}

		public void nextQuestion(){
			
			
			if(index < total_questions) {
				
				textarea.setText(questions.get(index));
				textField.setText("Question " + (index + 1));
				
				btnA.setIcon(optionIcon);
				btnB.setIcon(optionIcon);
				btnC.setIcon(optionIcon);
				btnD.setIcon(optionIcon);
				
				btnA.setText(options.get(index).get(0));
				btnB.setText(options.get(index).get(1));
				btnC.setText(options.get(index).get(2));
				btnD.setText(options.get(index).get(3));
				
				
				
				timer.start();
			}
			else {
				
				result();
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getSource() == btnA) {
				btnB.setEnabled(false);
				btnC.setEnabled(false);
				btnD.setEnabled(false);
				answer = options.get(index).get(0);
				btnA.setIcon(selectedIcon);				
				if(answer.equalsIgnoreCase(answers.get(index))) {
					correct_answers ++;
				}
			}
			if(e.getSource() == btnB) {
				btnA.setEnabled(false);
				btnC.setEnabled(false);
				btnD.setEnabled(false);
				answer = options.get(index).get(1);
				btnB.setIcon(selectedIcon);
				if(answer.equalsIgnoreCase(answers.get(index))) {
					correct_answers ++;
				}
			}
			if(e.getSource() == btnC) {
				btnA.setEnabled(false);
				btnB.setEnabled(false);
				btnD.setEnabled(false);
				answer = options.get(index).get(2);
				btnC.setIcon(selectedIcon);
				if(answer.equalsIgnoreCase(answers.get(index))) {
					correct_answers ++;
				}
			}
			if(e.getSource() == btnD) {
				btnA.setEnabled(false);
				btnB.setEnabled(false);
				btnC.setEnabled(false);

				answer = options.get(index).get(3);
				btnD.setIcon(selectedIcon);
				if(answer.equalsIgnoreCase(answers.get(index))) {
					correct_answers ++;
				}
			}
			
			displayAnswer();
		}

		private void displayAnswer() {

				timer.stop();
				javax.swing.Timer pause = new javax.swing.Timer(1000,new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						answer = "";
						seconds = 10 ;
						seconds_left.setText(String.valueOf(seconds));
						
						btnA.setEnabled(true);
						btnB.setEnabled(true);
						btnC.setEnabled(true);
						btnD.setEnabled(true);
						
						index += 1;
						
						nextQuestion();
					}
					
				});
				
				pause.setRepeats(false);
				pause.start();
				
		}
		
		public void result() {
			
			btnA.setEnabled(false);
			btnB.setEnabled(false);
			btnC.setEnabled(false);
			btnD.setEnabled(false);
			
			exampanel.removeAll();
			
			showExamFinished();
			
			exampanel.revalidate();
			exampanel.repaint();
			
			SubmitAnswer th1 = new SubmitAnswer();
			SubmittingBar th2 = new SubmittingBar(th1);
			
			th1.start();
			th2.start();
			
			
			
		}
		public void showExamFinished() {
			
			ImageIcon completedImage = new ImageIcon("images/complete.png");
			JLabel completedLabel = new JLabel();
			completedLabel.setIcon(completedImage);
			
			
			int x = (fullWidth/2) - (completedImage.getIconWidth()/2);
			
			completedLabel.setBounds(x,200,completedImage.getIconWidth(),300);
			completedLabel.setOpaque(true);
			
			saving.setText("Saving...");
			saving.setFont(new Font("verdana", Font.PLAIN,20));
			saving.setHorizontalTextPosition(JLabel.CENTER);
			saving.setVerticalTextPosition(JLabel.TOP);
			saving.setHorizontalAlignment(JLabel.CENTER);
			saving.setSize(650,300);
			saving.setLocation((exampanel.getWidth()/2) - (saving.getWidth()/2), completedLabel.getY() + completedLabel.getHeight() + 50);
			saving.setIcon(new ImageIcon("images/upload.gif"));
			
			
			ImageIcon exiticon = new ImageIcon("images/exit.png");
			exitBtn = new JButton(exiticon);
			exitBtn.setToolTipText("exit");
			exitBtn.setFocusable(false);
			exitBtn.setSize(exiticon.getIconWidth(), exiticon.getIconHeight());
			exitBtn.setBackground(Color.white);
			exitBtn.setLocation(fullWidth - exitBtn.getWidth(),0);
			exitBtn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				
					System.exit(0);
				}
			});
			
		
			exampanel.add(completedLabel);
			
			exampanel.add(saving);
			exampanel.revalidate();
			exampanel.repaint();
			
		}
	}
	
	class SubmitAnswer extends Thread{
		
		@Override
		synchronized public void run() {
			try {
				DBclient db = new DBclient();
				db.submitAnswer(username, rollno,ExamPanel.correct_answers);
				db.closeConnection();
				
				saving.setIcon(null);
				saving.setFont(new Font("verdana", Font.BOLD,25));
				saving.setText("Your answers are successfully submitted.");
				saving.setForeground(Color.green);
				saving.revalidate();
				saving.repaint();
				
			} catch (SQLException | ClassNotFoundException  e) {
//				showAlert("unable to connect database", "answer not submitted successfully");
				
				saving.setIcon(null);
				saving.setForeground(Color.red);
				saving.setText("Error : answers not submitted !");
				
			}
		}
	}
	
	class SubmittingBar extends Thread{
		
		Thread th ;
		
		SubmittingBar(Thread th){
			this.th = th;
		}
		
		@Override
		synchronized public void run() {

			while(th.isAlive()) {
				
			}
			
			
			exampanel.add(exitBtn);
			exampanel.revalidate();
			exampanel.repaint();
			
			running = false;
		}
		
	}
	
	
	 // *********************************************** EXAM PANEL ********************************************************end

	
	//start ------------------------------------- WINDOW LISTENER METHODS ------------------------------------------------
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
	
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
		if(examIsStarted) {
			this.setState(NORMAL);
		}
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	//end ------------------------------------- WINDOW LISTENER METHODS ------------------------------------------------
	public void showAlert(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);;
	}
	
}
