package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataBaseEntryDialog extends JDialog implements WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String [] labels = {"Host name :", "Database :", "Username :", "Password :", "Port number :"};
	ArrayList<JTextField> inputs = new ArrayList<>(); 
	JButton commitBtn;
	
	JPanel fullPanel ;
	
	JLabel loading;
	ImageIcon loadingicon;
	
	JMenuItem btn;
	
	public DataBaseEntryDialog(JMenuItem btn) {
		
		this.btn = btn;
		
		if(!DataBaseEntryDialog.PasswordCheck()) {
			
			this.btn.setEnabled(true);
			return;
		}
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Database Entry");
		this.setSize(430,450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(this);
		
		fullPanel = new JPanel();
		fullPanel.setBounds(0,0,this.getWidth(), this.getHeight());
		fullPanel.setLayout(null);
		fullPanel.setBackground(Color.DARK_GRAY);
		fullPanel.setOpaque(true);
		this.add(fullPanel);
		
		JLabel titlelabel = new JLabel("Database Entry - ");
		titlelabel.setFont(new Font("verdana", Font.BOLD | Font.ITALIC, 30));
		titlelabel.setForeground(Color.white);
		titlelabel.setBounds(20,20,350,50);
		fullPanel.add(titlelabel);
		
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0,80,this.getWidth(),250);
		mainPanel.setLayout(new GridLayout(5,1));
		
		for(int i = 0 ; i < 5; i++) {
				
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setSize(this.getWidth(), 50);
			panel.setBackground(Color.DARK_GRAY);
			panel.setOpaque(true);
			
			JLabel label = new JLabel(labels[i]);
			label.setFont(new Font("verdana", Font.BOLD, 17));
			label.setSize(170,50);
			label.setLocation(20,0);
			label.setForeground(Color.orange);
			panel.add(label);
			
			JTextField textField = new JTextField();
			textField.setSize(230,30);
			textField.setFont(new Font("verdana", Font.PLAIN, 15));
			textField.setLocation(panel.getWidth() - textField.getWidth() - 30, 10);
			panel.add(textField);
			
			inputs.add(textField);
			
			mainPanel.add(panel);
		
		}
		
		commitBtn = new JButton("<html><b>Commit<b></html>");
		commitBtn.setFont(new Font("verdana", Font.BOLD, 17));
		commitBtn.setFocusable(false);
		commitBtn.setForeground(Color.green);
		commitBtn.setBackground(Color.black);
		commitBtn.setBorder(BorderFactory.createLineBorder(Color.orange, 3));
		commitBtn.setSize(150,40);
		commitBtn.setLocation(this.getWidth() - commitBtn.getWidth() - 30, this.getHeight() - 100);
		
		commitBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(inputs.get(0).getText().isBlank() ||
				   inputs.get(1).getText().isBlank() ||
				   inputs.get(2).getText().isBlank() ||
//				   inputs.get(3).getText().isBlank() ||
				   inputs.get(4).getText().isBlank() ) {
					Const.showError("Warning", "<html>All of those fields are required ! <br> And <b> carefully fill the entries !</b></html>");
				}
				
				else {
					commitBtn.setEnabled(false);
					int ans = JOptionPane.showConfirmDialog(null,"<html><b>Are You sure !</b><br> To change the database.</html>", "Confirm message", JOptionPane.YES_NO_CANCEL_OPTION); 
					
					if(ans == 0) {
						String data = inputs.get(0).getText() + "\n" +
									  inputs.get(1).getText() + "\n" +
									  inputs.get(2).getText() + "\n" +
									  inputs.get(3).getText() + "\n" +
									  inputs.get(4).getText();
						
						try {
							FileWriter writer = new FileWriter("files/db.txt");
							writer.write(data);
							writer.close();
						} catch (IOException e1) {
							Const.showError("Error", "0000000x802 error");
						}
						
						
						UpdateDatabase th1 = new UpdateDatabase();
						loadingThread th2 = new loadingThread(th1);
						
						
						
						th1.start();
						th2.start();
					}
				}
				
				
			}

		
		});
		
		
		loadingicon = new ImageIcon("images/progress.gif");
		loading = new JLabel();
		loading.setForeground(Color.yellow);
		loading.setSize(200,loadingicon.getIconHeight());
		loading.setLocation(20, commitBtn.getY());
		
		
		
		fullPanel.add(mainPanel);
		fullPanel.add(commitBtn);
		fullPanel.add(loading);
		this.setVisible(true);
	}
	private void closeDialog() {
		this.setVisible(false);
		
	}
	
	class UpdateDatabase extends Thread{
		
		@Override
		synchronized public void run() {
			
			try {
				DB db = new DB();
				db.createTables();
				db.closeConnection();
				
				loading.setIcon(null);
				loading.setText("");
				
				Const.showInfo("Operation done successfully", "Done");

				closeDialog();
			} catch (ClassNotFoundException | SQLException e) {

				loading.setIcon(null);
				loading.setText("");
				
				Const.showError("Error", "Database not found !");
				commitBtn.setEnabled(true);
			}
		}
	}
	
	class loadingThread extends Thread{
		
		Thread th;
		
		loadingThread(Thread th){
			this.th = th;
		}
		
		@Override
		synchronized public void run() {
			
			loading.setText("It will take few seconds...");
			loading.setIcon(loadingicon);
			while(th.isAlive()) {
				
			}
			
			commitBtn.setEnabled(true);
		}
	}
	
	
	
	private static  boolean PasswordCheck() {
		String password = Const.password;
		
		String guess = JOptionPane.showInputDialog("Password : ");
		if(guess == null) {
			return false;
		}
		else if(guess.equals(password)) {
			return true;
		}
		else {
			Const.showAlert("Error", "Wrong Password");
			
			return false;
		}
	}
	
	
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		this.btn.setEnabled(true);
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}