package View;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.*;
import Model.Customer;
import Model.BranchManager;
import Model.Admin;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class LoginGUI extends JFrame {

	private JPanel w_pane;
	private JTextField fld_lEposta;
	private JPasswordField fld_lPass;
	private DBConnection conn = new DBConnection();
	
	String code;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginGUI.class.getResource("/View/icon.png")));
		setResizable(false);
		setTitle("CarOfDuty / Giriþ Yap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 500);
		w_pane = new JPanel();
		w_pane.setBackground(UIManager.getColor("activeCaption"));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.setBounds(10, 250, 274, 210);
		w_pane.add(w_tabbedPane);
		
		JPanel w_LoginPanel = new JPanel();
		w_LoginPanel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Giriþ Yap", null, w_LoginPanel, null);
		w_LoginPanel.setLayout(null);
		
		JLabel lbl_eposta = new JLabel("E-posta");
		lbl_eposta.setForeground(new Color(169, 169, 169));
		lbl_eposta.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lbl_eposta.setBounds(10, 11, 249, 22);
		w_LoginPanel.add(lbl_eposta);
		
		JLabel lbl_sifre = new JLabel("Þifre");
		lbl_sifre.setForeground(new Color(169, 169, 169));
		lbl_sifre.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lbl_sifre.setBounds(10, 71, 125, 22);
		w_LoginPanel.add(lbl_sifre);
		
		fld_lEposta = new JTextField();
		fld_lEposta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					login();
			    }
			}
		});
		fld_lEposta.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_lEposta.setForeground(new Color(105, 105, 105));
		fld_lEposta.setBackground(new Color(192, 192, 192));
		fld_lEposta.setBounds(10, 34, 249, 26);
		w_LoginPanel.add(fld_lEposta);
		fld_lEposta.setColumns(10);
		
		fld_lPass = new JPasswordField();
		fld_lPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					login();
			    }
			}
		});
		fld_lPass.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_lPass.setForeground(new Color(105, 105, 105));
		fld_lPass.setBackground(new Color(192, 192, 192));
		fld_lPass.setBounds(10, 94, 249, 26);
		w_LoginPanel.add(fld_lPass);
		
		JButton btn_login = new JButton("Giriþ Yap");
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		btn_login.setForeground(new Color(144, 238, 144));
		btn_login.setBackground(new Color(60, 179, 113));
		btn_login.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btn_login.setBounds(10, 131, 110, 30);
		w_LoginPanel.add(btn_login);
		
		JButton btn_register = new JButton("Kaydol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLocationRelativeTo(null);
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				rGUI.setLocationRelativeTo(null);
				dispose();
			}
		});
		btn_register.setBackground(new Color(65, 105, 225));
		btn_register.setForeground(new Color(135, 206, 235));
		btn_register.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btn_register.setBounds(149, 131, 110, 30);
		w_LoginPanel.add(btn_register);
		
		
		JButton btnForgotMyPassword = new JButton("Þifremi Unuttum");
		btnForgotMyPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				code = verificationCodeGenerator();
				MailSenderGUI msGUI = new MailSenderGUI();
				try {
					msGUI.code = getCodeToFrame();
					msGUI.setVisible(true);
					msGUI.setLocationRelativeTo(null);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnForgotMyPassword.setFont(new Font("Century Gothic", Font.PLAIN, 10));
		btnForgotMyPassword.setBounds(79, 166, 110, 13);
		w_LoginPanel.add(btnForgotMyPassword);
		
		JLabel lblLogin = new JLabel(new ImageIcon(getClass().getResource("login3.gif")));
		lblLogin.setBounds(0, 0, 294, 471);
		w_pane.add(lblLogin);
	}
	
	@SuppressWarnings("deprecation")
	private void login() {
		if(fld_lEposta.getText().length() == 0 || fld_lPass.getText().length() == 0) {
			Object[] options = {"Tamam"};
			JOptionPane.showOptionDialog(rootPane, "Lütfen tüm alanlarý doldurunuz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}else if(!validate(fld_lEposta.getText())){
			Object[] options = {"Tamam"};
			JOptionPane.showOptionDialog(rootPane, "Lütfen e-posta adresinizi doðru formatta giriniz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}else {
			try {
				Connection con = conn.connDb();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM admin UNION ALL SELECT * FROM branchmanagers UNION ALL SELECT * FROM customers");
				ResultSet rs2 = st.executeQuery("SELECT * FROM (SELECT * FROM admin UNION SELECT * FROM branchmanagers UNION SELECT * FROM customers) AS S WHERE S.eposta = '" + fld_lEposta.getText() + "' ");
				String cryptedPassword = Base64.getEncoder().encodeToString(fld_lPass.getText().getBytes());
				boolean value = true;
				while(rs.next()) {
					if(fld_lEposta.getText().equals(rs.getString("eposta")) && cryptedPassword.equals(rs.getString("password"))) {
						
						if(rs.getString("type").equals("yonetici")) {
							Admin admin = new Admin();
							admin.setId(rs.getInt("id"));
							admin.setName(rs.getString("name"));
							admin.setEposta(rs.getString("eposta"));
							admin.setPassword(rs.getString("password"));
							admin.setType(rs.getString("type"));
							AdminGUI aGUI = new AdminGUI(admin);
							aGUI.setVisible(true);
							aGUI.setLocationRelativeTo(null);
							dispose();
							value = false;
						}else if(rs.getString("type").equals("sube muduru")){
							BranchManager subeMuduru = new BranchManager();
							subeMuduru.setId(rs.getInt("id"));
							subeMuduru.setName(rs.getString("name"));
							subeMuduru.setEposta(rs.getString("eposta"));
							subeMuduru.setPassword(rs.getString("password"));
							subeMuduru.setType(rs.getString("type"));
							BranchManagerGUI bmGUI = new BranchManagerGUI(subeMuduru);
							bmGUI.setVisible(true);
							bmGUI.setLocationRelativeTo(null);
							dispose();
							value = false;
						}else if(rs.getString("type").equals("musteri")){
							Customer customer = new Customer();
							customer.setId(rs.getInt("id"));
							customer.setName(rs.getString("name"));
							customer.setEposta(rs.getString("eposta"));
							customer.setPassword(rs.getString("password"));
							customer.setType(rs.getString("type"));
							CustomerGUI cGUI = new CustomerGUI(customer);
							cGUI.lblMyMail.setText(getMyMail());
							cGUI.lblMyPass.setText(getMyPass());
							cGUI.setVisible(true);
							cGUI.setLocationRelativeTo(null);
							dispose();
							value = false;
						}
					}
				}
				if (!rs2.isBeforeFirst()) {
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Bu e-posta hesabý için sistemde bir kayýt bulunamadý!\nLütfen önce kayýt olun!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
		        	value = false;
				}
				if(value) {
					value = false;
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Þifre hatalý!\nBilgilerinizi lütfen gözden geçirin.", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private static String verificationCodeGenerator() {
		Random random = new Random();
		int code = random.nextInt(999999);
		
		return String.format("%06d", code);
	}
	
	public String getCodeToFrame() {
		return code;
	}
	
	public String getMyMail() {
		return fld_lEposta.getText();
	}
	
	@SuppressWarnings("deprecation")
	public String getMyPass() {
		return fld_lPass.getText();
	}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
}
