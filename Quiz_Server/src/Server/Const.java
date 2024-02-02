package Server;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Const {

	public static final String password = "EXAM@SERVER"; 
	
	
	protected final static ImageIcon APP_ICON = new ImageIcon("images/result.png");  
	protected final static String APP_TITLE = "Exam Server";
	protected final static int APP_WIDTH = 850;
	protected final static int APP_HEIGHT = 650;
	
	
	public static void showAlert(String title, String msg) {
		
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showError(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE,new ImageIcon("images/cross.png"));
	}
	public static void showInfo(String title, String msg) {
		JOptionPane.showMessageDialog(null, title, msg, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("images/right.png"));
	}
	
}
