package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Customer;

import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@SuppressWarnings("serial")
public class MailSenderGUI extends JFrame {

	private Customer customer = new Customer();
	LoginGUI lGUI = new LoginGUI();
	private JPanel contentPane;
	JTextField fldToMail;
	private JLabel lblText;
	String code = lGUI.getCodeToFrame();
	private JLabel lblEposta;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MailSenderGUI frame = new MailSenderGUI();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MailSenderGUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MailSenderGUI.class.getResource("/View/icon.png")));
		setTitle("CarOfDuty");
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
		
		lblText = new JLabel("Lütfen, sisteme kayýt olurken kullandýðýnýz e-posta adresinizi giriniz.");
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		lblText.setFont(new Font("Arial", Font.PLAIN, 12));
		lblText.setBounds(10, 11, 424, 25);
		panel.add(lblText);
		
		fldToMail = new JTextField();
		fldToMail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(fldToMail.getText().length() == 0) {
						Object[] options = {"Tamam"};
						JOptionPane.showOptionDialog(rootPane, "E-posta adresi kýsmý boþken bu iþlem yapýlamaz!", "Eksik Bilgi!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					}else if(!fldToMail.getText().contains("@") || !fldToMail.getText().contains(".")) {
						Object[] options = {"Tamam"};
						JOptionPane.showOptionDialog(rootPane, "Lütfen e-posta adresinizi doðru formatta giriniz!", "Hatalý Bilgi!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					}else {
						try {
							if(customer.checkOfEmail(fldToMail.getText().toString()) == true) {
								
								sendMail();
								
								VerifyCodeGUI verifyCodeGUI = new VerifyCodeGUI();
								verifyCodeGUI.code = getCodeToFrame();
								verifyCodeGUI.myMail = getMyMail();
								dispose();
								verifyCodeGUI.setVisible(true);
								verifyCodeGUI.setLocationRelativeTo(null);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		fldToMail.setFont(new Font("Century Gothic", Font.BOLD, 14));
		fldToMail.setBounds(130, 47, 304, 25);
		panel.add(fldToMail);
		fldToMail.setColumns(10);
		
		JButton btnSendMail = new JButton("Doðrulama Kodu Gönder");
		btnSendMail.setForeground(new Color(0, 128, 0));
		btnSendMail.setBackground(new Color(144, 238, 144));
		btnSendMail.setFont(new Font("Century Gothic", Font.BOLD, 11));
		btnSendMail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fldToMail.getText().length() == 0) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "E-posta adresi kýsmý boþken bu iþlem yapýlamaz!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}else if(!validate(fldToMail.getText())) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Lütfen e-posta adresinizi doðru formatta giriniz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else {
					try {
						if(customer.checkOfEmail(fldToMail.getText().toString()) == true) {
							
							sendMail();
							
							VerifyCodeGUI verifyCodeGUI = new VerifyCodeGUI();
							verifyCodeGUI.code = getCodeToFrame();
							verifyCodeGUI.myMail = getMyMail();
							dispose();
							verifyCodeGUI.setVisible(true);
							verifyCodeGUI.setLocationRelativeTo(null);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnSendMail.setBounds(138, 87, 168, 23);
		panel.add(btnSendMail);
		
		lblEposta = new JLabel("E-posta adresi : ");
		lblEposta.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEposta.setBounds(10, 47, 110, 25);
		panel.add(lblEposta);
	}
	
	private void sendMail() {
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
			message.setContent("Tek kullanýmlýk doðrulama kodunuz: " + this.code + "\nÞifre yenileme iþlemine devam edebilmek için bu kodu sistemde belirtilen alana girmelisiniz.\nGüvenlik açýsýndan doðrulama kodunu lütfen kimseyle paylaþmayýnýz!", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress("carofdutyaks@gmail.com"));
			message.setRecipient(RecipientType.TO, new InternetAddress(fldToMail.getText()));
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
	public String getMyMail() {
		return this.fldToMail.getText();
	}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
	        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
	        return matcher.find();
	}
}
