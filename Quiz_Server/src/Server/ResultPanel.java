package Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

class ResultPanel implements ActionListener{
	
	JLabel titleLabel;
	JPanel resultBoard;
	JTable table;
	String[][] rowData ;
	String[] columnName = {"Student Name", "Entrollment", "Marks"};
	JScrollPane scrollPane;
	
	JButton backBtn;
	JPanel panel;
	JLabel loadingLabel;
	
	JButton refreshBtn;
	JButton deleteBtn;
	
	Font questionFont = new Font("Verdana",Font.PLAIN,14);
	Border qPanelBorder = BorderFactory.createLineBorder(Color.black,2,true);
	
	Font btnFont = new Font("Arial",Font.BOLD,20);
	Border btnBorder = BorderFactory.createLineBorder(Color.orange,3);
	Color btnBackground = Color.darkGray;
	Color btnforground = Color.green;
	
	ResultPanel(JPanel panel){
		panel.setBackground(new Color(103,124,145));
		panel.setLayout(null);
		
		titleLabel = new JLabel("Results :");
		titleLabel.setIcon(new ImageIcon("images/resulticon.png"));
		titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
		titleLabel.setForeground(Color.green);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.CENTER);
		titleLabel.setIconTextGap(15);
		titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
		titleLabel.setVerticalTextPosition(JLabel.CENTER);
		titleLabel.setBounds(50,30,400,90);
		
		
		resultBoard = new JPanel();
		resultBoard.setLayout(new BorderLayout());
		resultBoard.setBackground(Color.white);
		resultBoard.setOpaque(true);
		resultBoard.setBorder(qPanelBorder);
		resultBoard.setBounds(75,136,510,405);
		resultBoard.setFont(questionFont);
		
	
		
		
		// refresh button
		refreshBtn = new JButton("Refresh");
		refreshBtn.setBounds(650,135,125,45);
		refreshBtn.setBackground(btnBackground);
		refreshBtn.setForeground(btnforground);
		refreshBtn.setOpaque(true);
		refreshBtn.setBorder(btnBorder);
		refreshBtn.setFont(btnFont);
		refreshBtn.setFocusable(false);
		refreshBtn.setEnabled(false);
		refreshBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshBtn.setEnabled(false);
				loadResult();
			
			}

		});
		
		deleteBtn = new JButton("Delete All");
		deleteBtn.setBounds(650,200,125,45);
		deleteBtn.setBackground(btnBackground);
		deleteBtn.setForeground(Color.red);
		deleteBtn.setOpaque(true);
		deleteBtn.setBorder(btnBorder);
		deleteBtn.setFont(btnFont);
		deleteBtn.setFocusable(false);
		deleteBtn.setEnabled(false);
		deleteBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DeleteThread th = new DeleteThread();
				DeleteLoading th2 = new DeleteLoading(th);
				
				th.start();
				th2.start();
				
			}
		});
		
		loadingLabel = new JLabel();
		loadingLabel.setBounds(resultBoard.getX() + resultBoard.getWidth() - 50,resultBoard.getY() - 50,50,50);
		loadingLabel.setHorizontalTextPosition(JLabel.CENTER);
		panel.add(loadingLabel);
		
		
		backBtn = new JButton("Back");
		backBtn.setFocusable(false);
		backBtn.setBorder(btnBorder);
		backBtn.setBackground(btnBackground);
		backBtn.setForeground(btnforground);
		backBtn.setFont(btnFont);
		backBtn.setOpaque(true);
		backBtn.setBounds(650,490,125,45);
		backBtn.addActionListener(this);
		backBtn.setEnabled(false);
		
		
		panel.add(titleLabel);
		panel.add(resultBoard);
		panel.add(refreshBtn);
		panel.add(deleteBtn);
		panel.add(backBtn);
		
		
		this.panel = panel;
	
		loadResult();
		
	}

	public class DeleteThread extends Thread{
		
		@Override
		synchronized public void run() {

			try {
				DB db = new DB();
				db.deleteResult();
				db.closeConnection();
			}catch (SQLException | ClassNotFoundException e) {
				
				Const.showError("Error", "Unable to delete previous result");
			}
		}
		
	}
public class DeleteLoading extends Thread{
		
		Thread th ;
		DeleteLoading(Thread th){
			this.th = th;
		}
	
		@Override
		synchronized public void run() {

			while(th.isAlive()) {
				deleteBtn.setText("");
				deleteBtn.setIcon(new ImageIcon("images/progressbar.gif"));
			}
			deleteBtn.setIcon(null);
			
			resultBoard.remove(scrollPane);
			resultBoard.revalidate();
			resultBoard.repaint();
			
			deleteBtn.setText("Delete");
		}
		
	}
	
	
	// creating a THREAD to load the question
	public class ResultLoading extends Thread{
		
		@Override
		synchronized public void run() {
			try {
			
				DB db = new DB();
				
				rowData = db.getResult();
				
				db.closeConnection();
			} catch (SQLException | ClassNotFoundException e) {
				
				Const.showAlert("error", "some error occure");
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
					
				}
			}
			loadingLabel.setIcon(null);
			loadingLabel.revalidate();
			loadingLabel.repaint();
			
			refreshBtn.setEnabled(true);
			deleteBtn.setEnabled(true);
			backBtn.setEnabled(true);
			
			
			
			table = new JTable(rowData, columnName);
			table.setFont(new Font("verdana", Font.PLAIN, 15));
			table.getTableHeader().setFont(new Font("verdana", Font.BOLD, 15));
			table.setEnabled(false);

			scrollPane = new JScrollPane(table);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			resultBoard.add(scrollPane);
		
			resultBoard.revalidate();
			resultBoard.repaint();
		}
		
	}
	
	// to load data from database ---------------------------------------------------------------start
	protected void loadResult() {

		ResultLoading dataloading = new ResultLoading();
		Loading loading = new Loading(dataloading);
		
		dataloading.start();
		loading.start();
		

		
		
	}
	// to load data from database ---------------------------------------------------------------end
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == backBtn) {
			backBtn.setEnabled(false);
			panel.removeAll();
			panel.revalidate();
			panel.repaint();
			
		 new HomePanel(panel);
		
		 backBtn.setEnabled(true);
		}
		
	
	}
	
}