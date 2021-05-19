package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Customer;

import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;
import java.awt.SystemColor;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ResetPasswordGUI extends JFrame {

	VerifyCodeGUI vcGUI = new VerifyCodeGUI();
	private JPanel contentPane;
	private JPasswordField fldPass1;
	private JPasswordField fldPass2;
	private Customer customer = new Customer();
	String myMail = vcGUI.getMyMail();

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ResetPasswordGUI frame = new ResetPasswordGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setAlwaysOnTop(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ResetPasswordGUI() {
		vcGUI.stopTimer();
		setIconImage(Toolkit.getDefaultToolkit().getImage(ResetPasswordGUI.class.getResource("/View/icon.png")));
		setTitle("Parola Yenileme");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("activeCaption"));
		panel.setBounds(0, 0, 294, 171);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lbl_sifre = new JLabel("Yeni Þifre");
		lbl_sifre.setForeground(UIManager.getColor("controlDkShadow"));
		lbl_sifre.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifre.setBounds(10, 11, 274, 21);
		panel.add(lbl_sifre);
		
		fldPass1 = new JPasswordField();
		fldPass1.setForeground(SystemColor.controlDkShadow);
		fldPass1.setFont(new Font("Arial", Font.PLAIN, 16));
		fldPass1.setBackground(Color.LIGHT_GRAY);
		fldPass1.setBounds(10, 32, 274, 26);
		panel.add(fldPass1);
		
		JLabel lbl_sifreAgain = new JLabel("Yeni Þifre (Tekrar)");
		lbl_sifreAgain.setForeground(UIManager.getColor("controlDkShadow"));
		lbl_sifreAgain.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifreAgain.setBounds(10, 64, 274, 21);
		panel.add(lbl_sifreAgain);
		
		fldPass2 = new JPasswordField();
		fldPass2.setForeground(SystemColor.controlDkShadow);
		fldPass2.setFont(new Font("Arial", Font.PLAIN, 16));
		fldPass2.setBackground(Color.LIGHT_GRAY);
		fldPass2.setBounds(10, 85, 274, 26);
		panel.add(fldPass2);
		
		JButton btnResetPassword = new JButton("Parolamý Yenile");
		btnResetPassword.setBackground(new Color(65, 105, 225));
		btnResetPassword.setForeground(new Color(0, 255, 255));
		btnResetPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnResetPassword.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String pass = String.valueOf(fldPass1.getPassword());
				String passAgain = String.valueOf(fldPass2.getPassword());
				if(fldPass1.getText().length() == 0 || fldPass2.getText().length() == 0) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Lütfen tüm alanlarý doldurunuz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(pass.length() < 8) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Þifreniz en az 8 karakterden oluþmalýdýr.", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(!pass.equals(passAgain)){
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Tekrar girilen þifre, ilk þifre ile ayný deðil!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					System.out.println(pass + " " + passAgain + " " + myMail);
				}else {
					try {
						boolean control = customer.resetPassword(fldPass1.getText(), myMail);
						if(control) {
							sendMail(myMail, "Þifreniz Yenilendi", "Yapmýþ olduðunuz deðiþiklikten ötürü CarOfDuty Araç Kiralama Sistemindeki þifreniz güncellenmiþtir.\n\nSaðlýklý günler dileriz...\nCarOfDuty Araç Kiralama Sistemi");
							Helper.showMsg("success");
							dispose();
						}else {
							fldPass1.setText(null);
							fldPass2.setText(null);
							Helper.showMsg("Hatalý Ýþlem");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnResetPassword.setBounds(10, 134, 274, 26);
		panel.add(btnResetPassword);
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
}
