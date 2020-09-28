package student_mgmt_sys;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.xml.bind.DatatypeConverter;

public class Login {

	private JFrame frame;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JLabel loginText;
	private MessageDigest md; 

	public Login() {
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			Main.printError(e);
		}
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 311);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setFont(new Font("Calibri", Font.BOLD, 26));
		usernameLabel.setBounds(96, 11, 127, 33);
		frame.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setFont(new Font("Calibri", Font.BOLD, 26));
		passwordLabel.setBounds(96, 55, 127, 33);
		frame.getContentPane().add(passwordLabel);
		
		usernameField = new JTextField();
		usernameField.setBounds(233, 11, 158, 33);
		frame.getContentPane().add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(233, 55, 158, 33);
		passwordField.setEchoChar('*');
		frame.getContentPane().add(passwordField);
		
		loginText = new JLabel("");
		loginText.setHorizontalAlignment(JLabel.CENTER);
		loginText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		loginText.setBounds(96, 112, 295, 37);
		frame.getContentPane().add(loginText);
		frame.setVisible(true);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.login = tryLogin(usernameField.getText(), new String(passwordField.getPassword()));
				if(!Main.login)
					loginText.setText("Login failed");
				else {
					JOptionPane.showMessageDialog(null, "Login Success!");
					frame.dispose();
					Main.startSystem();
					
				}
					
			}
		});
		loginButton.setFont(new Font("Tahoma", Font.BOLD, 24));
		loginButton.setBounds(96, 216, 295, 45);
		frame.getContentPane().add(loginButton);
	}
	
	public boolean tryLogin(String user, String pass) {
		if(user == null || pass == null || user.equals("") || pass.equals(""))
			return false;
		
		try {
			md.update(pass.getBytes());
			byte[] digset = md.digest();
			String hashed = DatatypeConverter.printHexBinary(digset).toUpperCase();
			boolean b = new Database("users","root","root").checkSystemLogin(user, hashed);
			return b;
		} catch(Exception e) {
			Main.printError(e);
		}
		return false;
	}
}
