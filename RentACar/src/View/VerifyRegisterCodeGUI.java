package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Customer;

import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class VerifyRegisterCodeGUI extends JFrame {

	private JPanel contentPane;
	JLabel lblCode;
	private JLabel lblText2;
	
	Customer customer = new Customer();
	RegisterGUI rGUI = new RegisterGUI();
	
	String code = rGUI.getCodeToFrame();
	String name = rGUI.getTextFromName();
	String eposta = rGUI.getTextFromEposta();
	String password = rGUI.getTextFromPassword();
	JLabel lblCountDown;
	int i = 119;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VerifyCodeGUI frame = new VerifyCodeGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VerifyRegisterCodeGUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VerifyCodeGUI.class.getResource("/View/icon.png")));
		setTitle("Kod Doðrulama");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("activeCaption"));
		panel.setBounds(0, 0, 444, 121);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblText1 = new JLabel("Aþaðýdaki text alanýna, e-posta adresinize gelen doðrulama kodunu giriniz.");
		lblText1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblText1.setHorizontalAlignment(SwingConstants.CENTER);
		lblText1.setBounds(10, 11, 424, 25);
		panel.add(lblText1);
		
		JTextField fldCode = new JTextField();
		fldCode.setBackground(new Color(255, 255, 224));
		fldCode.setForeground(new Color(0, 128, 0));
		fldCode.setHorizontalAlignment(SwingConstants.CENTER);
		fldCode.setFont(new Font("Arial", Font.BOLD, 17));
		fldCode.setColumns(10);
		fldCode.setBounds(138, 47, 168, 25);
		panel.add(fldCode);
		
		lblCountDown = new JLabel("");
		lblCountDown.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCountDown.setBounds(316, 87, 118, 23);
		panel.add(lblCountDown);
		
		t.start();
		
		JButton btnDorula = new JButton("DOÐRULA");
		btnDorula.setBackground(new Color(46, 139, 87));
		btnDorula.setForeground(new Color(144, 238, 144));
		btnDorula.setFont(new Font("Century Gothic", Font.BOLD, 12));
		btnDorula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fldCode.getText().equals(code)) {
					boolean control;
					try {
						control = customer.register(name, eposta, password);
						if(control) {
							t.stop();
							sendMail(eposta, "Kayýt Baþarýlý!", "Sizi aramýzda gördüðümüze sevindik!\nCarOfDuty sistemine kaydýnýz baþarýyla gerçekleþmiþtir.\n\nKeyifli günler dileriz...\nCarOfDuty Araç Kiralama Sistemi");
							Helper.showMsg("success");
							LoginGUI login = new LoginGUI();
							login.setVisible(true);
							login.setLocationRelativeTo(null);
							dispose();
						}else {
							rGUI.fld_eposta.setText(null);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}else if(fldCode.getText().length() == 0) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Lütfen önce mail adresinize gelen doðrulama kodunu giriniz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Girilen kod hatalý!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}
			}
		});
		btnDorula.setBounds(138, 87, 168, 23);
		panel.add(btnDorula);
		
		lblCode = new JLabel("");
		lblCode.setBounds(165, 87, 115, 23);
		panel.add(lblCode);
		
		lblText2 = new JLabel("Doðrulama Kodu:");
		lblText2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblText2.setBounds(10, 47, 118, 25);
		panel.add(lblText2);
		
	}
	
	private void sendMail(String eposta, String subject, String content) {
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
			message.setSubject(subject);
			message.setContent(content, "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress("carofdutyaks@gmail.com"));
			message.setRecipient(RecipientType.TO, new InternetAddress(eposta));
			message.setSentDate(new Date());
			Transport.send(message);
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2.getMessage());
		}
	}
	
	Timer t = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			lblCountDown.setText("Kalan Süre: " + String.valueOf(i));
			lblCountDown.setForeground(Color.BLUE);
			if(i <= 9) {
				lblCountDown.setForeground(Color.RED);
			}
			if(lblCountDown.getText().toString().equals("Kalan Süre: 0")) {
				stopTimer();
				Object[] options = {"Tamam"};
				JOptionPane.showOptionDialog(rootPane, "Belirlenen zamanda kod girilmediði için iþlem iptal edildi!", "Zaman Aþýmý!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				dispose();
				RegisterGUI registerGUI = new RegisterGUI();
				registerGUI.setVisible(true);
				registerGUI.setLocationRelativeTo(null);
			}
			i--;
		}
	});
	
	public void stopTimer() {
		t.stop();
	}
}
