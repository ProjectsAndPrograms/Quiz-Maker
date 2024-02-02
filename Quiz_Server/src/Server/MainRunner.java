package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MainRunner extends JFrame implements ActionListener,WindowListener{

	
	
	// creating object of home
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	ImageIcon back = new ImageIcon("images/back.jpg");
	
	JPanel panel = new JPanel() {
	     /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
	     protected void paintComponent(Graphics g)
	     {
	        super.paintComponent(g);
	        g.drawImage(back.getImage(), 0, 0, null);
	     }
	  };
	
	HomePanel home;
	ImageIcon titleicon = new ImageIcon("images/piramid.png");
	
	JLabel titleLabel;
	
	JButton questionsBtn;
	JButton clientsBtn;
	JButton readyBtn;
	JButton startBtn;
	
	Font btnFont = new Font("Arial",Font.BOLD,20);
	Border btnBorder = BorderFactory.createLineBorder(Color.green,3);
	Color btnBackground = new Color(86,189,201);
	Color btnforground = new Color(255,255,255);
	
	
	MainRunner(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(Const.APP_WIDTH,Const.APP_HEIGHT);
		this.setResizable(false);
		this.setIconImage(Const.APP_ICON.getImage());
		this.setTitle(Const.APP_TITLE);
		this.addWindowListener(this);
		this.setJMenuBar(new ServerMenu(this));
		
		home = new HomePanel(panel);
		home.setPanelSize(this);
		this.add(home.getPanel());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	
// ------------------------------------------------------- MAIN ---------------------------------------------------------------------------	
	public static void main(String[] args) {
		
		new MainRunner();
	}
// --------------------------------------------------------------------------------------------------------------------------------------

	
// **************** ACTION LISTENER *********************************************
	@Override
	public void actionPerformed(ActionEvent e) {

	
		
	}


@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}


@Override
public void windowClosing(WindowEvent e) {

	
	closeWin();
}

public void closeWin() {

	this.setVisible(false);
	
	try {
		DB db = new DB();
		db.restoreDefault();
		db.closeConnection();
	} catch (SQLException | ClassNotFoundException e1) {
		
	}
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
