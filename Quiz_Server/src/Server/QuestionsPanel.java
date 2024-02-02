package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

class QuestionsPanel implements ActionListener{
	
	JLabel titleLabel;
	JTextArea questionField;
	JScrollPane scrollPane;
	JButton addBtn;
	JButton backBtn;
	JPanel panel;
	JLabel loadingLabel;
	JLabel deleteingLabel;
	
	JButton refreshBtn;
	JButton deleteAllBtn;
	
	Font questionFont = new Font("Verdana",Font.PLAIN,14);
	Border qPanelBorder = BorderFactory.createLineBorder(Color.black,2,true);
	
	Font btnFont = new Font("Arial",Font.BOLD,20);
	Border btnBorder = BorderFactory.createLineBorder(Color.orange,3);
	Color btnBackground = Color.darkGray;
	Color btnforground = Color.green;
	
	QuestionsPanel(JPanel panel){
		panel.setBackground(new Color(103,124,145));
		panel.setLayout(null);
		
		titleLabel = new JLabel("Questions :");
		titleLabel.setIcon(new ImageIcon("images/qa.png"));
		titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
		titleLabel.setForeground(Color.green);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.CENTER);
		titleLabel.setIconTextGap(15);
		titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
		titleLabel.setVerticalTextPosition(JLabel.CENTER);
		titleLabel.setBounds(50,40,400,80);
		
		
		
		questionField = new JTextArea();
		questionField.setBackground(Color.white);
		questionField.setOpaque(true);
		questionField.setBorder(qPanelBorder);
		questionField.setBounds(75,136,510,405);
		questionField.setFont(questionFont);
		questionField.setLayout(null);
		questionField.setLineWrap(true);
		questionField.setWrapStyleWord(true);
		questionField.setEditable(false);
		
		scrollPane = new JScrollPane(questionField);					
		scrollPane.setBounds(75,136,510,405);
		scrollPane.setViewportBorder(qPanelBorder);
		
		
		// refresh button
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(650,135,125,45);
		refreshBtn.setBackground(btnBackground);
		refreshBtn.setForeground(btnforground);
		refreshBtn.setOpaque(true);
		refreshBtn.setBorder(btnBorder);
		refreshBtn.setFont(btnFont);
		refreshBtn.setFocusable(false);
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				questionField.setText("");
				loadQuestions();
				
			}

		});
		
		
		deleteAllBtn = new JButton("Delete All");
		deleteAllBtn.setBounds(650,200,125,45);
		deleteAllBtn.setBackground(btnBackground);
		deleteAllBtn.setForeground(Color.red);
		deleteAllBtn.setOpaque(true);
		deleteAllBtn.setBorder(btnBorder);
		deleteAllBtn.setFont(btnFont);
		deleteAllBtn.setFocusable(false);
		deleteAllBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DeleteingQuestions th1 = new DeleteingQuestions();
				DeleteLoading th2 = new DeleteLoading(th1);
				
				th1.start();
				th2.start();
				
				
			}
		});
		
		deleteingLabel = new JLabel();
		deleteingLabel.setBounds(595,200,50,50);
		deleteingLabel.setHorizontalTextPosition(JLabel.CENTER);
		panel.add(deleteingLabel);
		
		loadingLabel = new JLabel();
		loadingLabel.setBounds(595,135,50,50);
		loadingLabel.setHorizontalTextPosition(JLabel.CENTER);
		panel.add(loadingLabel);
		
		
		addBtn = new JButton("Add");
		addBtn.setFocusable(false);
		addBtn.setBorder(btnBorder);
		addBtn.setBackground(btnBackground);
		addBtn.setForeground(btnforground);
		addBtn.setFont(btnFont);
		addBtn.setOpaque(true);
		addBtn.setBounds(650,410,125,45);
		addBtn.addActionListener(this);
		
		backBtn = new JButton("Back");
		backBtn.setFocusable(false);
		backBtn.setBorder(btnBorder);
		backBtn.setBackground(btnBackground);
		backBtn.setForeground(btnforground);
		backBtn.setFont(btnFont);
		backBtn.setOpaque(true);
		backBtn.setBounds(650,490,125,45);
		backBtn.addActionListener(this);
		
		
		panel.add(titleLabel);
		panel.add(scrollPane);
		panel.add(refreshBtn);
		panel.add(deleteAllBtn);
		panel.add(addBtn);
		panel.add(backBtn);
		this.panel = panel;
	}

	public class DeleteLoading extends Thread{
		Thread th;
		public DeleteLoading(Thread th) {
			this.th = th;
		}
		
		@Override
		synchronized public void run() {
			while(th.isAlive()) {
				
			}
			
			
			deleteingLabel.setIcon(null);
			deleteingLabel.revalidate();
			deleteingLabel.repaint();
			refreshBtn.setEnabled(true);
			addBtn.setEnabled(true);
			backBtn.setEnabled(true);
		}
		
	}
	public class DeleteingQuestions extends Thread {
		@Override
		synchronized public void run() {
			
			deleteingLabel.revalidate();
			deleteingLabel.repaint();
			deleteingLabel.setIcon(new ImageIcon("images/progress.gif"));
			
			try {
				refreshBtn.setEnabled(false);
				loadingLabel.setIcon(null);
				loadingLabel.revalidate();
				loadingLabel.repaint();
				addBtn.setEnabled(false);
				backBtn.setEnabled(false);
				
				DB db = new DB();
				db.deleteAllQuestions();
				db.closeConnection();
			} catch (SQLException | ClassNotFoundException e) {
				Const.showError("Error", "unable to delete ! ");
			}
			
		}
	}
	
	// creating a THREAD to load the question
	public class DataLoading extends Thread{
		
		@Override
		synchronized public void run() {
			try {
			
				DB db = new DB();
				
				ResultSet set = DB.getAllData();
				
				int q_no = 1;
				while(set.next()) {
					
					questionField.append(" " + q_no+". " + set.getString(2) + "\n");
					questionField.append("    [A] "+ set.getString(3) + "\n");
					questionField.append("    [B] "+ set.getString(4) + "\n");
					questionField.append("    [C] "+ set.getString(5) + "\n");
					questionField.append("    [D] "+ set.getString(6) + "\n");
					questionField.append(" ans : " + set.getString(7) + "\n\n");
					
					q_no += 1;
				}
				db.closeConnection();
			} catch (SQLException | ClassNotFoundException e) {
				Const.showAlert("Alert", "Multiple tasks are running so this application doesn't work correctly!");
			}
		}
	}
	public class Loading extends Thread{
		
		Thread th;
		
		
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
					Const.showError("Error", "IO Exception ! This application is not working correctly");
					System.exit(0);
				}
			}
			loadingLabel.setIcon(null);
			loadingLabel.revalidate();
			loadingLabel.repaint();
		}
		
	}
	
	// to load data from database ---------------------------------------------------------------start
	protected void loadQuestions() {

		DataLoading dataloading = new DataLoading();
		Loading loading = new Loading(dataloading);
		
		dataloading.start();
		loading.start();
		

		
		
	}
	// to load data from database ---------------------------------------------------------------end
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backBtn) {
			
			
			
			panel.removeAll();
			panel.revalidate();
			panel.repaint();
			
			new HomePanel(panel);
		
		}
		
		if(e.getSource() == addBtn) {
			panel.removeAll();
			panel.revalidate();
			panel.repaint();
			
			new AddQuestionPanel(panel);
		}
	}
	
}