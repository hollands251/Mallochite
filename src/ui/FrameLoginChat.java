
/*
 * Joseph Escober
 */

package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.border.SoftBevelBorder;

import mallochite.DatabaseConnection.DatabaseCrud;

import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;

public class FrameLoginChat extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtUserName, txtPassword;
	private JTextField textField;
	boolean same = false;
	
	FrameUserChat userChat = new FrameUserChat();
	//private JPasswordField txtPassword;	

	/**
	 * Launch the application.
	 */
	public static void newLoginMemberScreen(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameLoginChat frame = new FrameLoginChat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrameLoginChat() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 300, 400, 600);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 100, 0));
		contentPane.setBorder(new LineBorder(new Color(47, 79, 79), 4));
		setContentPane(contentPane);
		setUndecorated(true);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel.setBounds(42, 228, 298, 59);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtUserName = new JTextField();
		txtUserName.setBorder(null);
		txtUserName.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		txtUserName.setText("Username");
		txtUserName.setBounds(10, 10, 278, 39);
		panel.add(txtUserName);
		txtUserName.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(42, 319, 298, 59);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		txtPassword = new JTextField();
		txtPassword.setBorder(null);
		txtPassword.setFont(new Font("Segoe UI Light", Font.PLAIN, 18));
		txtPassword.setText("Password");
		txtPassword.setBounds(10, 10, 238, 39);
		panel_1.add(txtPassword);
		
		//JLabel lblNewLabel = new JLabel("");
		//lblNewLabel.setBackground(Color.WHITE);
		//lblNewLabel.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/res/padlock.png")).getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
		//lblNewLabel.setBounds(251, 10, 37, 46);
		//panel_1.add(lblNewLabel);
		
		
		JPanel pnlBtnLogin = new JPanel();
		pnlBtnLogin.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		pnlBtnLogin.setBackground(new Color(60, 179, 113));
		pnlBtnLogin.setBounds(228, 479, 112, 59);
		contentPane.add(pnlBtnLogin);
		pnlBtnLogin.setLayout(null);
		
		JLabel lblRegister = new JLabel("Register");
		lblRegister.setBounds(21, 10, 81, 39);
		lblRegister.setForeground(Color.WHITE);
		lblRegister.setFont(new Font("Arial", Font.BOLD, 18));
		pnlBtnLogin.add(lblRegister);
		
		
		lblRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FrameRegisterUser.newRegisterMemberScreen(null);
					 FrameLoginChat.this.dispose();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblRegister.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				lblRegister.setForeground(Color.WHITE);
			}
			
		});
		
		JLabel lblNewLabel_2 = new JLabel("allochite");
		lblNewLabel_2.setLocation(new Point(26, 0));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("Arial", Font.BOLD, 34));
		lblNewLabel_2.setBounds(139, 33, 213, 43);
		contentPane.add(lblNewLabel_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(60, 179, 113));
		panel_2.setBounds(0, 86, 400, 20);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setBounds(72, 10, 68, 66);
		contentPane.add(lblNewLabel_3);
		lblNewLabel_3.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/res/image2.jpg")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
		
		JLabel lblClose = new JLabel("X");
		lblClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to close this application?", "confirmation", JOptionPane.YES_NO_OPTION) == 0)
					 FrameLoginChat.this.dispose();
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblClose.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				lblClose.setForeground(Color.WHITE);
			}
			
		});
		lblClose.setForeground(Color.WHITE);
		lblClose.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblClose.setBounds(376, 0, 24, 35);
		contentPane.add(lblClose);
		
		JLabel lblLoginDisplay = new JLabel("Login");
		lblLoginDisplay.setForeground(Color.WHITE);
		lblLoginDisplay.setFont(new Font("Arial", Font.BOLD, 24));
		lblLoginDisplay.setBounds(47, 163, 195, 35);
		contentPane.add(lblLoginDisplay);
		
		JPanel pnlBtnLogin_1 = new JPanel();
		pnlBtnLogin_1.setLayout(null);
		pnlBtnLogin_1.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		pnlBtnLogin_1.setBackground(new Color(60, 179, 113));
		pnlBtnLogin_1.setBounds(47, 479, 112, 59);
		contentPane.add(pnlBtnLogin_1);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setForeground(Color.WHITE);
		lblLogin.setFont(new Font("Arial", Font.BOLD, 18));
		lblLogin.setBounds(31, 10, 71, 39);
		pnlBtnLogin_1.add(lblLogin);
		
		lblLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//FrameLoginChat.this.dispose();
				//FrameUserChat.newRegisterMemberScreen(null);
							 
			       //FrameJScrollPaneDemo.newUserChatScreenDemo(null);	
				
				System.out.println("clicked");
				getLogin();
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblLogin.setForeground(Color.RED);
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				lblLogin.setForeground(Color.BLACK);
			}
		});
		
		
		JLabel lblNewLabel = new JLabel("Already Registered?");
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(48, 441, 159, 28);
		contentPane.add(lblNewLabel);
		
		
		setLocationRelativeTo(null);
	}
	
	
	
	public void getLogin()
	{			
		
		Connection con = DatabaseCrud.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
		try
		{
			String sql = "SELECT * from Registration";
			
		    ps = con.prepareStatement(sql);
            rs = ps.executeQuery();			
            
			while(rs.next())
            {           	   
				String UserName = rs.getString("Username");			
				String Password = rs.getString("Password");	
				

				if (txtUserName.getText().equals(UserName) && txtPassword.getText().equals(Password))
				{
					same = true;
					JOptionPane.showMessageDialog(null, "username and password are correct.","You are logged in",
							JOptionPane.INFORMATION_MESSAGE);
					FrameLoginChat.this.dispose();
					//userChat.newUserChatScreenDemo(null);
					//userChat.getOperation();
					
					
				}
				
				 else 
				 {
					 JOptionPane.showMessageDialog(null, "Please Check Username and Password ");
				 }
				 rs.close();
	             ps.close();
            }
			
			           
			
             	
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
					JOptionPane.ERROR_MESSAGE);
		}finally {
			
		}
	}   
	
	
	
	public void registerUser(String Username, String Password)
	{			
        
        String sql = "INSERT INTO Registration (Username, Password) VALUES(?,?)";
        
		try
		{			
				         
            	Connection con = DatabaseCrud.connect();
	            PreparedStatement ps = null;		         
                
                ps = con.prepareStatement(sql);		  
                          
                    ps.setString(1, Username);
                    ps.setString(2, Password);
                    ps.executeUpdate();  
                
                    System.out.println("User data are successfully saved!:\n");	                                          
                
            }
		   catch (SQLException e)
		     {
                System.out.println(e.toString());
             } 		            
			
           
           
	}   
	public JTextField getTxtUserName() {
		return txtUserName;
	}

	public void setTxtUserName(JTextField txtUserName) {
		this.txtUserName = txtUserName;
	}

	public JTextField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(JTextField txtPassword) {
		this.txtPassword = txtPassword;
	}
	
	
	
}