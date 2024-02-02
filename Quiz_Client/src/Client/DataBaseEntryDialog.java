package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DataBaseEntryDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String [] labels = {"Host name :", "Database :", "Username :", "Password :", "Port number :"};
	ArrayList<JTextField> inputs = new ArrayList<>(); 
	JButton commitBtn;
	static JButton configBtn;
	JPanel fullPanel ;
	
	public DataBaseEntryDialog(JButton configButton) {
		
		configBtn = configButton;
		
		if(!DataBaseEntryDialog.PasswordCheck()) {
			
			return;
		}
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Database Entry");
		this.setAlwaysOnTop(true);
		this.setSize(430,450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				configBtn.setEnabled(true);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
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
					DataBaseEntryDialog.this.setAlwaysOnTop(false);
					Const.showError("Warning", "<html>All of those fields are required ! <br> And <b> carefully fill the entries !</b></html>");
					DataBaseEntryDialog.this.setAlwaysOnTop(true);
				}
				
				else {
					DataBaseEntryDialog.this.setAlwaysOnTop(false);
					int ans = JOptionPane.showConfirmDialog(null,"<html><b>Are You sure !</b><br> To change the database.</html>", "Confirm message", JOptionPane.YES_NO_CANCEL_OPTION); 
					DataBaseEntryDialog.this.setAlwaysOnTop(true);
					
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
							DataBaseEntryDialog.this.setAlwaysOnTop(false);
							Const.showError("Error", "0000000x802 error");
							DataBaseEntryDialog.this.setAlwaysOnTop(true);
						}
						DataBaseEntryDialog.this.setAlwaysOnTop(false);
						Const.showInfo("Operation done successfully", "Done");
						DataBaseEntryDialog.this.setAlwaysOnTop(true);
						
						closeDialog();
					}
				}	
			}

		});
		
		fullPanel.add(mainPanel);
		fullPanel.add(commitBtn);
		this.setVisible(true);
	}
	private void closeDialog() {
		configBtn.setEnabled(true);
		this.setVisible(false);
	}
	
	private static  boolean PasswordCheck() {
		String password = Const.password;
		
		String guess = JOptionPane.showInputDialog("Password : ");
		if(guess == null) {
			configBtn.setEnabled(true);
			return false;
		}
		else if(guess.equals(password)) {
			return true;
		}
		else {
			Const.showAlert("Error", "Wrong Password");
			configBtn.setEnabled(true);
			return false;
		}
	}
	
}