package Server;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class AddQuestionPanel implements ActionListener,KeyListener {

	
	JPanel panel = new JPanel();
	
	JLabel titleLabel;
	JButton cancelBtn;
	JButton addBtn;
	JPanel addPanel;
	
	JLabel questionLabel;
	JLabel answerLabel;
	JScrollPane scorllpane;
	JTextArea question;
	
	JLabel alertLabel;
	
	JTextField[] options = new JTextField[4];	
	JLabel[] optionsLabel = new JLabel[4];
	String[] abcd = {"[A]","[B]","[C]","[D]"}; 
	
	public JComboBox<Object> answers;
	
	int[] posY = {150,205,260,315};
	
	Font questionFont = new Font("Verdana",Font.PLAIN,17);
	
	Font btnFont = new Font("Arial",Font.BOLD,20);
	Border btnBorder = BorderFactory.createLineBorder(Color.orange,3);
	Color btnBackground = Color.darkGray;
	Color btnforground = Color.green;
	
	JLabel loadingImage;
	
	
	AddQuestionPanel(JPanel panel){
		panel.setBackground(new Color(103,124,145));
		panel.setLayout(null);
		
		titleLabel = new JLabel("New Question");
		titleLabel.setIcon(new ImageIcon("images/q.png"));
		titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
		titleLabel.setForeground(Color.green);
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setVerticalAlignment(JLabel.CENTER);
		titleLabel.setIconTextGap(15);
		titleLabel.setHorizontalTextPosition(JLabel.RIGHT);
		titleLabel.setVerticalTextPosition(JLabel.CENTER);
		titleLabel.setBounds(50,40,500,80);
		
	
		
		//start------------------------------- ADD QUESTION PANEL ------------------------------
		
		addPanel = new JPanel();
		addPanel.setLayout(null);
		addPanel.setBounds(75,136,510,405);
		addPanel.setOpaque(false);
		
		questionLabel = new JLabel("Question");
		questionLabel.setBounds(20,25,150,20);
		questionLabel.setFont(btnFont);
		questionLabel.setForeground(Color.yellow);
		
		
		question = new JTextArea();
		question.setFont(questionFont);
		question.setLineWrap(true);
		question.setWrapStyleWord(true);
		
		
		scorllpane = new JScrollPane(question);					
		scorllpane.setBounds(20,55,470,75);
		scorllpane.setViewportBorder(btnBorder);
		
		for(int i = 0; i < 4 ;i++) {
			optionsLabel[i] = new JLabel(abcd[i]);
			optionsLabel[i].setBounds(20,posY[i],35,35);
			optionsLabel[i].setFont(new Font("verdana",Font.BOLD,17));
			optionsLabel[i].setHorizontalAlignment(JLabel.CENTER);
			optionsLabel[i].setForeground(Color.green);

			options[i] = new JTextField();
			options[i].setBounds(60,posY[i],430,35);
			options[i].setFont(new Font("verdana",Font.PLAIN,15));
			options[i].setBackground(Color.white);
			options[i].setForeground(Color.blue);
			
			
			addPanel.add(optionsLabel[i]);
			addPanel.add(options[i]);
		}

		addPanel.add(questionLabel);
		addPanel.add(scorllpane);
		
		//end------------------------------- ADD QUESTION PANEL ------------------------------
		
		
		answerLabel = new JLabel("Answer");
		answerLabel.setBounds(650,161,100,20);
		answerLabel.setFont(btnFont);
		answerLabel.setForeground(Color.yellow);
		
		String[] answerOptions = {"A","B","C","D"}; 
		
		answers = new JComboBox<Object>(answerOptions);
		answers.setLocation(100,100);
		answers.setBounds(650,190,100,30);
		answers.setBackground(Color.green);
		answers.setForeground(Color.black);
		answers.setFont(new Font("Arial",Font.PLAIN,20));
		answers.setOpaque(true);
		answers.setEnabled(true);
		
		
		addBtn = new JButton("Add");
		addBtn.setFocusable(false);
		addBtn.setBorder(btnBorder);
		addBtn.setBackground(btnBackground);
		addBtn.setForeground(btnforground);
		addBtn.setFont(btnFont);
		addBtn.setOpaque(true);
		addBtn.setBounds(650,410,125,45);
		addBtn.setEnabled(true);
		addBtn.addActionListener(this);
		
		
		
		loadingImage = new JLabel();
		loadingImage.setSize(50,50);
		loadingImage.setLocation(addBtn.getX() - 50, addBtn.getY());
		
		
		cancelBtn = new JButton("back");
		cancelBtn.setFocusable(false);
		cancelBtn.setBorder(btnBorder);
		cancelBtn.setBackground(btnBackground);
		cancelBtn.setForeground(btnforground);
		cancelBtn.setFont(btnFont);
		cancelBtn.setOpaque(true);
		cancelBtn.setBounds(650,490,125,45);
		cancelBtn.addActionListener(this);
		
		
		alertLabel = new JLabel();
		alertLabel.setBounds(100,525,500,50);
		alertLabel.setFont(new Font("verdana",Font.PLAIN,16));
		alertLabel.setHorizontalTextPosition(JLabel.RIGHT);
		alertLabel.setVerticalTextPosition(JLabel.CENTER);
		alertLabel.setForeground(Color.orange);
		
		panel.add(alertLabel);
		panel.add(answerLabel);
		panel.add(answers);
		panel.add(addPanel);
		panel.add(titleLabel);
		panel.add(addBtn);
		panel.add(loadingImage);
		panel.add(cancelBtn);
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == cancelBtn) {
			panel.removeAll();
			panel.revalidate();
			panel.repaint();
			
			new QuestionsPanel(panel);
		}
		
	
		
		
		if(e.getSource() == addBtn) {
			
			
			alertLabel.removeAll();
			alertLabel.revalidate();
			alertLabel.repaint();
			
			if(!(question.getText().isBlank()) && 
			   !(options[0].getText().isBlank()) &&
			   !(options[1].getText().isBlank()) &&
			   !(options[2].getText().isBlank()) &&
			   !(options[3].getText().isBlank())) {
				
				
				AddingQuestion th1 = new AddingQuestion();
				LoadingIcon th2 = new LoadingIcon(th1);
				
				th1.start();
				th2.start();
				
				/// pass
				
			}else {
			
				alertLabel.setIcon(new ImageIcon("images/x.png"));
				
				if(question.getText().isBlank()) {alertLabel.setText("<html>Questions field is empty !</html>");}
				if((options[0].getText().isBlank()) || (options[1].getText().isBlank()) || (options[2].getText().isBlank()) || (options[3].getText().isBlank())) {
					alertLabel.setText("<html>All options are <b>required</b> !</html>");
				}
				
			}
		}
		
		
	}

	class LoadingIcon extends Thread{
		
		Thread th;
		
		LoadingIcon(Thread th){
			this.th = th;
		}
		
		@Override
		synchronized public void run() {
			
			loadingImage.setIcon(new ImageIcon("images/progress.gif"));
			while(th.isAlive()) {
				
			}
			loadingImage.setIcon(null);
		}
		
	}
	
	class AddingQuestion extends Thread{
		
		
		@Override
		synchronized public void run() {
			try {
				
				
				
				DB db = new DB();
				db.addQuestion(question.getText(),
						       options[0].getText(), 
						       options[1].getText(), 
						       options[2].getText(), 
						       options[3].getText(), 
						       options[answers.getSelectedIndex()].getText());
				db.closeConnection();
				
				alertLabel.setForeground(Color.green);
				alertLabel.setText("<html>Question inserted <i>successfully </i>!</html>");
				alertLabel.setIcon(new ImageIcon("images/saved.png"));
				
				question.setText("");
				for(int i=0;i<4;i++) {
					options[i].setText("");
				}
				addPanel.revalidate();
				addPanel.repaint();
				
				
			} catch (SQLException | ClassNotFoundException e1) {
				
				alertLabel.setText("<html>something went wrong!<b> Data not inserted!</b></html>");
				alertLabel.setIcon(new ImageIcon("images/x.png"));
				
			}
			
		}
	}
	
	// ----------------------------------- KEY LISTNER METHODS -------------------------------------
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	// ----------------------------------- -------------------- -------------------------------------
	
}


