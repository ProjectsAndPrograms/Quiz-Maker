package Server;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



public class Help extends JDialog implements WindowListener {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JMenuItem btn;
	Help(JMenuItem btn){
		this.btn = btn;
		
	
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Help");
		this.setSize(400,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.addWindowListener(this);
		

		JLabel label = new JLabel();
		label.setText("Help");
		label.setFont(new Font("Arial", Font.BOLD,25));
		label.setIcon(new ImageIcon("images/about.png"));
		label.setHorizontalTextPosition(JLabel.LEFT);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setIconTextGap(8);
		label.setBounds(50,20,200,label.getIcon().getIconHeight()+5);
		
		JPanel panel = new JPanel();
		panel.setBounds(50, 80, 281, 130);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 1));
		
		JLabel line1 = new JLabel("<html>Follow these steps to use Quiz Generator.<hr></html>");
		line1.setFont(new Font("Arial", Font.PLAIN,15));
		line1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel line2 = new JLabel("<html>Step 1 : Fill your database details on both client<br> and server side.</html>");
		JLabel line3 = new JLabel("<html>Step 2 : You can add/remove question and see<br> previous results. </html>");
		JLabel line4 = new JLabel("<html>Step 3 : when server is ready connect the client.</html>");
		JLabel line5 = new JLabel("<html>Step 4 : start the exam.</html>");
		
		panel.add(line1);
		panel.add(line2);
		panel.add(line3);
		panel.add(line4);
		panel.add(line5);
		
		JLabel imgLabel = new JLabel(new ImageIcon("images/persons.png"));
		imgLabel.setBounds(280,175,100,100);
		
		JLabel imgLabel2 = new JLabel(new ImageIcon("images/saved.png"));
		imgLabel2.setBounds(5,181,100,100);
		
		this.add(label);
		this.add(imgLabel2);
		this.add(imgLabel);
		this.add(panel);
		this.setVisible(true);
		
		
	}
	
	public void windowOpened(WindowEvent e) {
		
		
	}

	public void windowClosing(WindowEvent e) {
		
		this.btn.setEnabled(true);
	}

	public void windowClosed(WindowEvent e) {
		
	
	}

	public void windowIconified(WindowEvent e) {
		
		
	}

	public void windowDeiconified(WindowEvent e) {
	
		
	}

	public void windowActivated(WindowEvent e) {
		
		
	}

	public void windowDeactivated(WindowEvent e) {
				
	}


	

	
	
}
