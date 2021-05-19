package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.DBConnection;
import Model.Customer;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPasswordField;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SuppressWarnings("serial")
public class RegisterGUI extends JFrame {
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.connDb();
	PreparedStatement preparedStatement = null;
	
	private JPanel contentPane;
	JTextField fld_name;
	JTextField fld_eposta;
	JPasswordField fld_pass;
	JPasswordField fld_passAgain;
	public String code = verificationCodeGenerator();
	Customer customer = new Customer();

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public RegisterGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(RegisterGUI.class.getResource("/View/icon.png")));
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setTitle("CarOfDuty / Kaydol");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 500);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("activeCaption"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.setBounds(10, 153, 274, 307);
		contentPane.add(w_tabbedPane);
		
		JPanel w_panel = new JPanel();
		w_panel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Kaydol", null, w_panel, null);
		w_panel.setLayout(null);
		
		JLabel lbl_name = new JLabel("Ad Soyad");
		lbl_name.setBackground(new Color(192, 192, 192));
		lbl_name.setForeground(new Color(169, 169, 169));
		lbl_name.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_name.setBounds(10, 11, 249, 21);
		w_panel.add(lbl_name);
		
		fld_name = new JTextField();
		fld_name.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						register();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    }
			}
		});
		fld_name.setForeground(new Color(105, 105, 105));
		fld_name.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_name.setColumns(10);
		fld_name.setBackground(new Color(192, 192, 192));
		fld_name.setBounds(10, 32, 249, 26);
		w_panel.add(fld_name);
		
		JLabel lbl_eposta = new JLabel("E-posta");
		lbl_eposta.setForeground(new Color(169, 169, 169));
		lbl_eposta.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_eposta.setBounds(10, 64, 249, 21);
		w_panel.add(lbl_eposta);
		
		fld_eposta = new JTextField();
		fld_eposta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						register();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    }
			}
		});
		fld_eposta.setForeground(new Color(105, 105, 105));
		fld_eposta.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_eposta.setColumns(10);
		fld_eposta.setBackground(new Color(192, 192, 192));
		fld_eposta.setBounds(10, 85, 249, 26);
		w_panel.add(fld_eposta);
		
		JLabel lbl_sifre = new JLabel("Þifre");
		lbl_sifre.setForeground(new Color(169, 169, 169));
		lbl_sifre.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifre.setBounds(10, 117, 249, 21);
		w_panel.add(lbl_sifre);
		
		fld_pass = new JPasswordField();
		fld_pass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						register();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    }
			}
		});
		fld_pass.setForeground(new Color(105, 105, 105));
		fld_pass.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_pass.setBackground(new Color(192, 192, 192));
		fld_pass.setBounds(10, 138, 249, 26);
		w_panel.add(fld_pass);
		
		JButton btn_register = new JButton("Kaydol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					register();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_register.setForeground(new Color(144, 238, 144));
		btn_register.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btn_register.setBackground(new Color(60, 179, 113));
		btn_register.setBounds(10, 228, 110, 30);
		w_panel.add(btn_register);
		
		JButton btn_backto = new JButton("Giriþ Yap");
		btn_backto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login=new LoginGUI();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
				dispose();
			}
		});
		btn_backto.setForeground(new Color(135, 206, 235));
		btn_backto.setFont(new Font("Century Gothic", Font.BOLD, 16));
		btn_backto.setBackground(new Color(65, 105, 225));
		btn_backto.setBounds(149, 228, 110, 30);
		w_panel.add(btn_backto);
		
		JLabel lbl_sifreAgain = new JLabel("Þifre (Tekrar)");
		lbl_sifreAgain.setForeground(new Color(169, 169, 169));
		lbl_sifreAgain.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifreAgain.setBounds(10, 170, 249, 21);
		w_panel.add(lbl_sifreAgain);
		
		fld_passAgain = new JPasswordField();
		fld_passAgain.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER){
					try {
						register();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
			    }
			}
		});
		fld_passAgain.setForeground(new Color(105, 105, 105));
		fld_passAgain.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_passAgain.setBackground(new Color(192, 192, 192));
		fld_passAgain.setBounds(10, 191, 249, 26);
		w_panel.add(fld_passAgain);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(getClass().getResource("login3.gif")));
		lblNewLabel.setBounds(0, 0, 294, 471);
		contentPane.add(lblNewLabel);
		
	}
	
	@SuppressWarnings("deprecation")
	private void register() throws SQLException {
		String pass = String.valueOf(fld_pass.getPassword());
		String passAgain = String.valueOf(fld_passAgain.getPassword());
		if(fld_name.getText().length() == 0 || fld_eposta.getText().length() == 0 || fld_pass.getText().length() == 0 || fld_passAgain.getText().length() == 0) {
			Object[] options = {"Tamam"};
			JOptionPane.showOptionDialog(rootPane, "Lütfen tüm alanlarý doldurunuz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}else if(!validate(fld_eposta.getText())) {
			Object[] options = {"Tamam"};
			JOptionPane.showOptionDialog(rootPane, "Lütfen e-posta adresinizi doðru formatta giriniz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}else if(fld_pass.getText().length() < 8) {
			Object[] options = {"Tamam"};
			JOptionPane.showOptionDialog(rootPane, "Þifreniz en az 8 karakterden oluþmalýdýr.", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}else if(!pass.equals(passAgain)){
			Object[] options = {"Tamam"};
			JOptionPane.showOptionDialog(rootPane, "Tekrar girilen þifre ilk þifre ile ayný deðil!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
			System.out.println(pass + " " + passAgain);
		}else {
			try {
				if(checkOfEposta(fld_eposta.getText()) == true) {
					sendMail(code);
					
					VerifyRegisterCodeGUI verifyRegisterCodeGUI = new VerifyRegisterCodeGUI();
					verifyRegisterCodeGUI.code = getCodeToFrame();
					verifyRegisterCodeGUI.name = getTextFromName();
					verifyRegisterCodeGUI.eposta = getTextFromEposta();
					verifyRegisterCodeGUI.password = getTextFromPassword();
					
					verifyRegisterCodeGUI.setVisible(true);
					verifyRegisterCodeGUI.setLocationRelativeTo(null);
					dispose();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean checkOfEposta(String eposta) throws SQLException {
		int key = 0;
		boolean duplicate = true;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM (SELECT * FROM admin UNION SELECT * FROM branchmanagers UNION SELECT * FROM customers) AS S WHERE S.eposta = '" + eposta + "' ");
			while (rs.next()) {
				duplicate = false;
				Object[] options = {"Tamam"};
				JOptionPane.showOptionDialog(rootPane, "Sistemde bu e-posta adresi için daha önce bir kayýt oluþturuldu!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				break;
			}
			if (duplicate) {
				key = 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1)
			return true;
		else
			return false;
	}
	
	private static String verificationCodeGenerator() {
		Random random = new Random();
		int code = random.nextInt(999999);
		
		return String.format("%06d", code);
	}
	
	private void sendMail(String code) {
		try {
			Properties properties = new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");
			
			Session session = Session.getDefaultInstance(properties, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("carofdutyaks@gmail.com", "my_password");
				}
			});
			
			Message message = new MimeMessage(session);
			message.setSubject("CoD E-posta Doðrulama Kodu");
			message.setContent("Tek kullanýmlýk doðrulama kodunuz: " + code + "\n\nKayýt iþlemine devam edebilmek için bu kodu sistemde belirtilen alana girmelisiniz.\nGüvenlik açýsýndan doðrulama kodunu lütfen kimseyle paylaþmayýnýz!\n\nCarOfDuty Araç Kiralama Sistemi", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress("carofdutyaks@gmail.com"));
			message.setRecipient(RecipientType.TO, new InternetAddress(fld_eposta.getText()));
			message.setSentDate(new Date());
			Transport.send(message);
			
			JOptionPane msg = new JOptionPane("Doðrulama kodu gönderildi. Mail adresinizi kontrol ediniz!", JOptionPane.INFORMATION_MESSAGE);
		    final JDialog dlg = msg.createDialog("BÝLGÝ!");
		    dlg.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		    new Thread(new Runnable() {
		    	@Override
		    	public void run() {
		    		try {
		    			Thread.sleep(5000);
		    		} catch (InterruptedException e) {
		    			e.printStackTrace();
		    		}
		    		dlg.setVisible(false);
		    	}
		    }).start();
		    dlg.setVisible(true);
		    
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
	}
	
	public String getCodeToFrame() {
		return this.code;
	}
	public String getTextFromName() {
		return this.fld_name.getText();
	}
	public String getTextFromEposta() {
		return this.fld_eposta.getText();
	}
	@SuppressWarnings("deprecation")
	public String getTextFromPassword() {
		return this.fld_pass.getText();
	}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
	
}