package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.Border;

public class ServerMenu extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenu main;
	JMenu help;
	
	JMenuItem databaseItem;
	JMenuItem exitItem;
	
	JMenuItem aboutItem;
	JMenuItem helpItem;
	
	Font itemFont = new Font("verdana", Font.PLAIN, 15);
	Font menuFont = new Font("Verdana", Font.BOLD, 17);
	Border menuBorder = BorderFactory.createLineBorder(Color.orange,2);
	Border itemBorder = BorderFactory.createLineBorder(Color.green,2);
	
	
	public ServerMenu(MainRunner admin){
		
		this.setBackground(Color.BLACK);
		
		main = new JMenu("Main ");
		main.setForeground(Color.green);
		main.setFont(menuFont);
		
		
		help = new JMenu("Help ");
		help.setForeground(Color.green);
		help.setFont(menuFont);
		
		databaseItem = new JMenuItem("Database Entry");
		databaseItem.setFont(itemFont);
		databaseItem.setForeground(Color.orange);
		databaseItem.setBackground(Color.DARK_GRAY);
		databaseItem.setBorder(itemBorder);
		databaseItem.setIcon(new ImageIcon("images/database.png"));
		databaseItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				databaseItem.setEnabled(false);
				
				new DataBaseEntryDialog(databaseItem);
			}
		});
		
		
		
		exitItem = new JMenuItem("Exit");
		exitItem.setFont(itemFont);
		exitItem.setForeground(Color.orange);
		exitItem.setBackground(Color.DARK_GRAY);
		exitItem.setBorder(itemBorder);
		exitItem.setIcon(new ImageIcon("images/exit.png"));
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				admin.setVisible(false);
				
				try {
					DB db = new DB();
					db.restoreDefault();
					db.closeConnection();
				} catch (SQLException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		
		aboutItem = new JMenuItem("    About   ");
		aboutItem.setFont(itemFont);
		aboutItem.setForeground(Color.orange);
		aboutItem.setBackground(Color.DARK_GRAY);
		aboutItem.setBorder(itemBorder);
		aboutItem.setIcon(new ImageIcon("images/back.png"));
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutItem.setEnabled(false);
				
				try {
					new About(aboutItem);
				} catch (URISyntaxException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		
		helpItem = new JMenuItem("    Help    ");
		helpItem.setFont(itemFont);
		helpItem.setForeground(Color.orange);
		helpItem.setBackground(Color.DARK_GRAY);
		helpItem.setBorder(itemBorder);
		helpItem.setIcon(new ImageIcon("images/helpIcon.png"));
		helpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				helpItem.setEnabled(false);
				
				new Help(helpItem);
			}
		});
		
		main.add(databaseItem);
		main.addSeparator();
		main.add(exitItem);
		
		help.add(aboutItem);
		help.addSeparator();
		help.add(helpItem);
		
		
		
		this.add(main);
		this.add(help);
		
	}
}
