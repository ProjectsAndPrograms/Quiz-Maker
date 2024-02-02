package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class HomePanel  implements ActionListener {

	JLabel titleLabel;
	JPanel panel = new JPanel();
	
	JButton questionsBtn;
	JButton resultBtn;
	JButton readyBtn;
	JButton stopBtn;
	JLabel loadingLabel;
	Font btnFont = new Font("Arial",Font.BOLD,20);
	Border btnBorder = BorderFactory.createLineBorder(Color.orange,3);
	Color btnBackground = Color.darkGray;
	Color btnforground = Color.green;
	
	
	
	ImageIcon titleicon = new ImageIcon("images/result.png");
	
	
	
	HomePanel(JPanel panel){
		panel.setBackground(new Color(103,124,145));
		panel.setLayout(null);
		
	
		
		titleLabel = new JLabel("Exam Server");
		titleLabel.setIcon(titleicon);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
		titleLabel.setForeground(Color.green);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.CENTER);
		titleLabel.setIconTextGap(15);
		titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
		titleLabel.setVerticalTextPosition(JLabel.CENTER);
		titleLabel.setBounds(50,40,450,80);
		
		
		questionsBtn = new JButton("Questions");
		questionsBtn.setFocusable(false);
		questionsBtn.setBorder(btnBorder);
		questionsBtn.setBackground(btnBackground);
		questionsBtn.setForeground(btnforground);
		questionsBtn.setFont(btnFont);
		questionsBtn.setOpaque(true);
		questionsBtn.setBounds(600,120,150,45);
		questionsBtn.addActionListener(this);
		
	
		
		resultBtn = new JButton("Result");
		resultBtn.setFocusable(false);
		resultBtn.setBorder(btnBorder);
		resultBtn.setBackground(btnBackground);
		resultBtn.setForeground(btnforground);
		resultBtn.setFont(btnFont);
		resultBtn.setOpaque(true);
		resultBtn.setBounds(600,200,150,45);
		resultBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				panel.removeAll();
				panel.revalidate();
				panel.repaint();
				new ResultPanel(panel);
			}
		});
		
		
		stopBtn = new JButton();
		ImageIcon stop = new ImageIcon("images/stop.png");
		stopBtn.setIcon(stop);
		stopBtn.setFocusable(false);
		stopBtn.setBorder(btnBorder);
		stopBtn.setBackground(btnBackground);
		stopBtn.setForeground(btnforground);
		stopBtn.setFont(btnFont);
		stopBtn.setOpaque(true);
		stopBtn.setBounds(260, 500,45,45 );
		stopBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				stopThread th = new stopThread();
				Loading th2 = new Loading(th);
				
				th.start();
				th2.start();
				
				
			}
		});
		
	
		
		readyBtn = new JButton("Ready");
		readyBtn.setFocusable(false);
		readyBtn.setBorder(btnBorder);
		readyBtn.setBackground(btnBackground);
		readyBtn.setForeground(btnforground);
		readyBtn.setFont(btnFont);
		readyBtn.setOpaque(true);
		readyBtn.setBounds(80,500,150,45);
		readyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				
				
				questionsBtn.setEnabled(false);
				resultBtn.setEnabled(false);
				
				if(Check.ready == false) {
					readyBtn.setEnabled(false);
					getReady();
					
				}
				else {

					readyBtn.setEnabled(false);
					starting();
				}
				
				
			}
		});
		
		
		
	
		
		loadingLabel = new JLabel();
		loadingLabel.setBounds(750 ,readyBtn.getY(),50,50);
		panel.add(loadingLabel);
		
		
		
		panel.add(titleLabel);
		panel.add(questionsBtn);
	
		panel.add(resultBtn);
		panel.add(readyBtn);
	
		
		
		
		panel.revalidate();
		panel.repaint();
	
		this.panel = panel;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == questionsBtn) {
		
			panel.removeAll();
			panel.revalidate();
			panel.repaint();
			
			
			
			new QuestionsPanel(panel);
		}
		
	}
	
	JPanel getPanel() {
		return panel;
	}
	void setPanelSize(JFrame frame) {
		panel.setBounds(0,0,frame.getWidth(),frame.getHeight());
	}

	
	class Loading extends Thread{
		
		Thread th ;

		Loading(Thread th){
			this.th = th;
			
		}
		
		
		@Override
		synchronized public void run() {
			while(th.isAlive()) {
				loadingLabel.revalidate();
				loadingLabel.repaint();
				loadingLabel.setIcon(new ImageIcon("images/progress.gif"));
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					
				}
			}
			loadingLabel.setIcon(null);
			loadingLabel.revalidate();
			loadingLabel.repaint();
		}
		
	}
	
	class stopThread extends Thread{
		
		@Override
		synchronized public void run() {
			try {
				stopBtn.setEnabled(false);
				readyBtn.setEnabled(false);
				
				
				
				DB db = new DB();
				db.restoreDefault();
				db.closeConnection();
				
				
				readyBtn.setText("Ready");
				Check.ready = false;
				readyBtn.setEnabled(true);
	
				panel.remove(stopBtn);
				
				stopBtn.setEnabled(true);
				questionsBtn.setEnabled(true);
				resultBtn.setEnabled(true);
				
				panel.revalidate();
				panel.repaint();
			} catch (SQLException | ClassNotFoundException e1) {
				Const.showError("error", "connection not found");
				stopBtn.setEnabled(true);
			}
		}
	}
	
	class ReadyThread extends Thread{
		
		@Override
		synchronized public void run() {
			
			try {
				
				panel.remove(stopBtn);
				panel.revalidate();
				panel.repaint();
				
				DB db = new DB();
				db.readyToBegin();
				db.startExamOver();
				db.closeConnection();
				
				readyBtn.setText("Start Exam");
				Check.ready = true;
				readyBtn.setEnabled(true);
				

				panel.add(stopBtn);
				panel.revalidate();
				panel.repaint();
				
			} catch (SQLException | ClassNotFoundException e1) {
				Const.showAlert("Unable to ready ", "<html>May be you have no internet<br>or database entries are expired</html>");
				readyBtn.setEnabled(true);
				Check.ready = false;
				
				panel.remove(stopBtn);
				panel.revalidate();
				panel.repaint();
				
				questionsBtn.setEnabled(true);
				resultBtn.setEnabled(true);
				
			}	
		}
	}
	class StartThread extends Thread{
		
		@Override
		synchronized public void run() {
			
			try {
				panel.remove(stopBtn);
				panel.revalidate();
				panel.repaint();
				
				
				DB db = new DB();
				
				db.startExam();
				db.readyTimeOver();
				
				db.closeConnection();
			
				panel.add(stopBtn);
				panel.revalidate();
				panel.repaint();
				
			} catch (SQLException | ClassNotFoundException e) {
				Const.showAlert("Unable to start exam ", "<html>May be you have no internet<br>or database entries are expired</html>");
				readyBtn.setText("Ready");
				readyBtn.setEnabled(true);
				Check.ready = false;
				
				panel.remove(stopBtn);
				panel.revalidate();
				panel.repaint();
				
				questionsBtn.setEnabled(true);
				resultBtn.setEnabled(true);
			}
		}
	}
	
	
	protected void getReady() {
		ReadyThread th1 = new ReadyThread(); 
		Loading th2 = new Loading(th1);
		
		th1.start();
		th2.start();
	}
	protected void starting() {
		StartThread th1 = new StartThread(); 
		Loading th2 = new Loading(th1);
		
		th1.start();
		th2.start();
	}
	
	
}
