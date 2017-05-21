import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class SignUp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSignUp = new JLabel("SignUp");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(lblSignUp, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblCompanyName = new JLabel("Company Name");
		lblCompanyName.setBounds(73, 53, 99, 14);
		panel.add(lblCompanyName);
		
		JLabel lblUname = new JLabel("uname");
		lblUname.setBounds(72, 89, 46, 14);
		panel.add(lblUname);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setBounds(73, 129, 79, 14);
		panel.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(221, 50, 106, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(221, 86, 106, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(221, 126, 106, 20);
		panel.add(passwordField);
		
		JButton btnSignup = new JButton("SignUp");
		btnSignup.setBounds(146, 174, 89, 23);
		panel.add(btnSignup);
	}

}
