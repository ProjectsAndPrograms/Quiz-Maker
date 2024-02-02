package Client;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Const {

	static String password = "EXAM@CLIENT";
	
	static boolean errorInConnection = false;
	static boolean internetAvailable = true;
	
	public static void showAlert(String title, String msg) {
		
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showError(String title, String msg) {
		JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE,new ImageIcon("images/x.png"));
	}
	public static void showInfo(String title, String msg) {
		JOptionPane.showMessageDialog(null, title, msg, JOptionPane.INFORMATION_MESSAGE,new ImageIcon("images/done.png"));
	}
	
	
}
