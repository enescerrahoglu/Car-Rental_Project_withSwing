package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import Model.Customer;
import Model.BranchManager;
import Model.Admin;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

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
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import Helper.DBConnection;

import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;

import java.awt.event.MouseEvent;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;
import java.awt.event.MouseMotionAdapter;
import com.toedter.components.JSpinField;
import com.toedter.calendar.JYearChooser;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class RentalGUI extends JFrame {
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	Admin admin = new Admin();
	static Customer customer = new Customer();
	CustomerGUI cGUI = new CustomerGUI(customer);
	BranchManager branchManager = new BranchManager();
	
	private JPanel contentPane;
	private JLabel lbl_CarInfo;
	private JLabel lbl_CarIdIs;
	JLabel lblCarId;
	private JLabel lbl_brandIs;
	JLabel lblBrand;
	private JLabel lbl_modelIs;
	JLabel lblModel;
	private JLabel lbl_modelYearIs;
	JLabel lblModelYear;
	private JLabel lbl_colorIs;
	JLabel lblColor;
	private JLabel lbl_colorPriceIs;
	JLabel lblPrice;
	private JDesktopPane desktopPane;
	JLabel lbl_carImage;
	
	JLabel lblCustomerId;
	JLabel lblMessage;
	JLabel lblTotalDays;
	
	JDateChooser select_rent;
	JDateChooser select_return;
	private JLabel lblNewPrice;
	private JLabel lblTotalPrice;
	private JTextField fld_kartAdı;
	private JLabel lblHesapNo;
	private JLabel lblAy;
	private JLabel lblCVV;
	
	JSpinField spinField_month;
	JYearChooser yearChooser;
	private JLabel lblCardType;
	JLabel lblCustomerName;
	JLabel lblBranchName;
	JLabel lblBranchId;
	private JLabel lblkrediKartı;
	
	String customerMail = "";
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		Properties props = new Properties();
		props.put("logoString", "CoD");
		AluminiumLookAndFeel.setCurrentTheme(props);
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalGUI frame = new RentalGUI();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
					frame.setAlwaysOnTop(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public RentalGUI() throws SQLException{
		setIconImage(Toolkit.getDefaultToolkit().getImage(RentalGUI.class.getResource("/View/icon.png")));
		setResizable(false);
		setTitle("CarOfDuty / Kiralama İşlemleri");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 700);
		contentPane = new JPanel();
		contentPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				updateTotalPrice();
			}
		});
		
		contentPane.setBackground(UIManager.getColor("activeCaption"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblCarId = new JLabel("");
		lblCarId.setForeground(new Color(65, 105, 225));
		lblCarId.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblCarId.setBounds(176, 70, 214, 25);
		contentPane.add(lblCarId);
		
		lbl_CarIdIs = new JLabel("Araç ID : ");
		lbl_CarIdIs.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_CarIdIs.setBounds(10, 70, 105, 25);
		contentPane.add(lbl_CarIdIs);
		
		lbl_CarInfo = new JLabel("Araç Bilgileri");
		lbl_CarInfo.setForeground(new Color(65, 105, 225));
		lbl_CarInfo.setFont(new Font("Arial", Font.BOLD, 25));
		lbl_CarInfo.setBounds(10, 11, 162, 48);
		contentPane.add(lbl_CarInfo);
		
		lbl_brandIs = new JLabel("Marka : ");
		lbl_brandIs.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_brandIs.setBounds(10, 101, 105, 25);
		contentPane.add(lbl_brandIs);
		
		lbl_modelIs = new JLabel("Model : ");
		lbl_modelIs.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_modelIs.setBounds(10, 132, 105, 25);
		contentPane.add(lbl_modelIs);
		
		lbl_modelYearIs = new JLabel("Model Yılı : ");
		lbl_modelYearIs.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_modelYearIs.setBounds(10, 163, 105, 25);
		contentPane.add(lbl_modelYearIs);
		
		lbl_colorIs = new JLabel("Renk : ");
		lbl_colorIs.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_colorIs.setBounds(10, 194, 105, 25);
		contentPane.add(lbl_colorIs);
		
		lblBrand = new JLabel("");
		lblBrand.setForeground(new Color(65, 105, 225));
		lblBrand.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblBrand.setBounds(176, 101, 214, 25);
		contentPane.add(lblBrand);
		
		lblModel = new JLabel("");
		lblModel.setForeground(new Color(65, 105, 225));
		lblModel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblModel.setBounds(176, 132, 214, 25);
		contentPane.add(lblModel);
		
		lblModelYear = new JLabel("");
		lblModelYear.setForeground(new Color(65, 105, 225));
		lblModelYear.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblModelYear.setBounds(176, 163, 214, 25);
		contentPane.add(lblModelYear);
		
		lblColor = new JLabel("");
		lblColor.setForeground(new Color(65, 105, 225));
		lblColor.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblColor.setBounds(176, 194, 214, 25);
		contentPane.add(lblColor);
		
		JButton btn_cancel = new JButton("İptal Et");
		btn_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.noButtonText", "Hayır");
				UIManager.put("OptionPane.yesButtonText", "Evet");
				int response = JOptionPane.showConfirmDialog(rootPane, "İptal etmek istediğinize emin misiniz?", "Mesaj!", JOptionPane.YES_NO_OPTION);
				if(response == 0) {
					dispose();
				}
			}
		});
		btn_cancel.setForeground(new Color(165, 42, 42));
		btn_cancel.setFont(new Font("Arial", Font.BOLD, 20));
		btn_cancel.setBackground(new Color(240, 128, 128));
		btn_cancel.setBounds(500, 620, 250, 40);
		contentPane.add(btn_cancel);
		
		
		long t = System.currentTimeMillis();
		Date today = new Date(t);
		
		Date tomorrow = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(tomorrow); 
		c.add(Calendar.DATE, 1);
		tomorrow = c.getTime();

		select_rent = new JDateChooser();
		select_rent.setMinSelectableDate(today);
		select_rent.setDate(today);
		select_rent.setBounds(10, 290, 162, 20);
		contentPane.add(select_rent);
		
		JLabel lbl_rentDate = new JLabel("Kira Başlangıç Tarihi");
		lbl_rentDate.setForeground(new Color(50, 205, 50));
		lbl_rentDate.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lbl_rentDate.setBounds(10, 270, 162, 20);
		contentPane.add(lbl_rentDate);
		
		JLabel lbl_returnDate = new JLabel("Kira Bitiş Tarihi");
		lbl_returnDate.setForeground(new Color(165, 42, 42));
		lbl_returnDate.setFont(new Font("Century Gothic", Font.BOLD, 13));
		lbl_returnDate.setBounds(186, 270, 162, 20);
		contentPane.add(lbl_returnDate);
		
		select_return = new JDateChooser();
		select_return.setMinSelectableDate(tomorrow);
		select_return.setDate(tomorrow);
		select_return.setBounds(186, 290, 162, 20);
		contentPane.add(select_return);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBounds(414, 70, 336, 189);
		contentPane.add(desktopPane);
		
		lbl_carImage = new JLabel("");
		lbl_carImage.setBounds(0, 0, 336, 189);
		desktopPane.add(lbl_carImage);
		
		lbl_colorPriceIs = new JLabel("Günlük Kira Bedeli : ");
		lbl_colorPriceIs.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_colorPriceIs.setBounds(10, 225, 162, 25);
		contentPane.add(lbl_colorPriceIs);
		
		lblPrice = new JLabel("0");
		lblPrice.setForeground(new Color(65, 105, 225));
		lblPrice.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblPrice.setBounds(176, 225, 214, 25);
		contentPane.add(lblPrice);
		
		lblNewPrice = new JLabel("Toplam Kiralama Ücreti : ");
		lblNewPrice.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewPrice.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		lblNewPrice.setBounds(358, 270, 218, 20);
		contentPane.add(lblNewPrice);
		
		lblTotalPrice = new JLabel("");
		lblTotalPrice.setForeground(new Color(255, 0, 0));
		lblTotalPrice.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblTotalPrice.setBounds(576, 270, 174, 20);
		contentPane.add(lblTotalPrice);
		
		JLabel lblKartKisiAdi = new JLabel("Kart Üzerindeki İsim");
		lblKartKisiAdi.setForeground(new Color(176, 224, 230));
		lblKartKisiAdi.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblKartKisiAdi.setBounds(206, 456, 250, 20);
		contentPane.add(lblKartKisiAdi);
		
		fld_kartAdı = new JTextField();
		fld_kartAdı.setFont(new Font("Arial", Font.BOLD, 12));
		fld_kartAdı.setForeground(new Color(0, 0, 139));
		fld_kartAdı.setBackground(new Color(176, 224, 230));
		fld_kartAdı.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				
				if(Character.isLetter(c) || Character.isWhitespace(c) || Character.isISOControl(c)) {
					fld_kartAdı.setEditable(true);
				}else {
					fld_kartAdı.setEditable(false);
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				int position = fld_kartAdı.getCaretPosition();
				fld_kartAdı.setText(fld_kartAdı.getText().toUpperCase());
				fld_kartAdı.setCaretPosition(position);
			}
		});
		fld_kartAdı.setHorizontalAlignment(SwingConstants.LEFT);
		fld_kartAdı.setBounds(206, 477, 250, 20);
		contentPane.add(fld_kartAdı);
		fld_kartAdı.setColumns(10);
		
		lblHesapNo = new JLabel("Hesap Numarası");
		lblHesapNo.setForeground(new Color(176, 224, 230));
		lblHesapNo.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblHesapNo.setBounds(279, 395, 250, 20);
		contentPane.add(lblHesapNo);
		
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("#### #### #### ####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JFormattedTextField fld_hesapNo = new JFormattedTextField(mask);
		fld_hesapNo.setHorizontalAlignment(SwingConstants.CENTER);
		fld_hesapNo.setFont(new Font("Arial", Font.BOLD, 12));
		fld_hesapNo.setForeground(new Color(0, 0, 139));
		fld_hesapNo.setBackground(new Color(176, 224, 230));
		fld_hesapNo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String firstValue = fld_hesapNo.getText().substring(0, 1);
				try {
					if(Integer.parseInt(firstValue) == 4) {
						lblCardType.setIcon(new ImageIcon(getClass().getResource("visa.png")));
						lblMessage.setText(null);
					}else if(Integer.parseInt(firstValue) == 5) {
						lblCardType.setIcon(new ImageIcon(getClass().getResource("mastercard.png")));
						lblMessage.setText(null);
					}else if(Integer.parseInt(firstValue) != 4 || Integer.parseInt(firstValue) != 5){
						lblMessage.setText("Hatalı Hesap Numarası");
						lblCardType.setIcon(null);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				String firstValue = fld_hesapNo.getText().substring(0, 1);
				if(firstValue.isBlank()){
					lblCardType.setIcon(null);
					lblMessage.setText(null);
				}
			}
		});
		fld_hesapNo.setColumns(10);
		fld_hesapNo.setBounds(279, 417, 297, 20);
		contentPane.add(fld_hesapNo);
		
		lblAy = new JLabel("Ay");
		lblAy.setForeground(new Color(176, 224, 230));
		lblAy.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAy.setBounds(206, 517, 30, 20);
		contentPane.add(lblAy);
		
		spinField_month = new JSpinField();
		spinField_month.setValue(1);
		spinField_month.setMinimum(1);
		spinField_month.setMaximum(12);
		spinField_month.getSpinner().setLocation(10, 0);
		spinField_month.setBounds(236, 517, 40, 20);
		contentPane.add(spinField_month);
		
		JLabel lblYil = new JLabel("Yıl");
		lblYil.setForeground(new Color(176, 224, 230));
		lblYil.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblYil.setBounds(295, 517, 30, 20);
		contentPane.add(lblYil);
		
		lblCVV = new JLabel("CVV");
		lblCVV.setForeground(new Color(176, 224, 230));
		lblCVV.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCVV.setBounds(476, 477, 40, 20);
		contentPane.add(lblCVV);

		MaskFormatter maskCVV = null;
		try {
			maskCVV = new MaskFormatter("###");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		JFormattedTextField fld_cvv = new JFormattedTextField(maskCVV);
		fld_cvv.setFont(new Font("Arial", Font.BOLD, 12));
		fld_cvv.setForeground(new Color(0, 0, 139));
		fld_cvv.setBackground(new Color(176, 224, 230));
		fld_cvv.setColumns(10);
		fld_cvv.setBounds(516, 477, 60, 20);
		contentPane.add(fld_cvv);
		
		yearChooser = new JYearChooser();
		yearChooser.setMinimum(Calendar.getInstance().get(Calendar.YEAR));
		yearChooser.setBounds(325, 517, 48, 20);
		contentPane.add(yearChooser);
		
		JButton btnNewButton = new JButton("Onayla");
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton.setBackground(new Color(50, 205, 50));
		btnNewButton.setForeground(new Color(34, 139, 34));
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(fld_hesapNo.getValue() == null || fld_kartAdı.getText().length() == 0 || fld_cvv.getValue() == null) {
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Bigilerinizi kontrol edin!", "Hatalı Bilgi", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}else if(select_rent.getDate() == null || select_return.getDate() == null){
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Lütfen aracı hangi tarihler arasında kiralamak istediğinizi belirtin!", "Eksik Tarih Bilgisi", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(lblCardType.getIcon() == null){
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Hesap Numaranızı kontrol edin!", "Hatalı Bilgi", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}else {
					try {
						if(branchManager.isRental(Integer.parseInt(lblCarId.getText().toString())) == "Kiralanmadı") {
							SimpleDateFormat dFormat = new SimpleDateFormat("dd-MM-yyyy");
							String rentDate = dFormat.format(select_rent.getDate());
							String returnDate = dFormat.format(select_return.getDate());
							
							String customerId = lblCustomerId.getText();
							customerId = customerId.replaceAll("[^0-9]", "");
							String branchId = lblBranchId.getText();
							branchId = branchId.replaceAll("[^0-9]", "");
							customer.rentCar(Integer.parseInt(customerId), lblCustomerName.getText(), Integer.parseInt(lblCarId.getText()), lblBrand.getText() + " " + lblModel.getText(), Integer.parseInt(branchId), lblBranchName.getText(), rentDate, returnDate);
							customer.addRT(Integer.parseInt(customerId), lblCustomerName.getText(), Integer.parseInt(lblCarId.getText()), lblBrand.getText() + " " + lblModel.getText(), Integer.parseInt(branchId), lblBranchName.getText(), rentDate, returnDate);
							Object[] options = {"Tamam"};
				        	JOptionPane.showOptionDialog(rootPane, "Kiralama işlemi başarıyla gerçekleşti.", "Başarılı", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				        	sendMail(customerMail, lblBrand.getText() + " " + lblModel.getText() + " Kiraladınız!", "Kira bitiş tarihi " +  returnDate + " olan " + lblTotalPrice.getText() + " tutarındaki " + lblBranchName.getText() + " şubesinden yaptığınız araç kiralama işlemi başarıyla gerçekleşti!\n\nKira başlangıç tarihi: " + rentDate + "\nKira bitiş tarihi: " + returnDate + "\n\nAraç Hakkında\n\nMarka ve Model: " + lblBrand.getText() + " " + lblModel.getText() + "\nModel Yılı: " + lblModelYear.getText() + "\nRenk: " + lblColor.getText() + "\n\nBizi tercih ettiğiniz için teşekkürler!\nCarOfDuty Araç Kiralama Sistemi" );
				        	System.out.println(fld_kartAdı.getText());
				        	System.out.println(fld_hesapNo.getText());
				        	System.out.println(fld_cvv.getText());
				        	System.out.println(spinField_month.getValue());
				        	System.out.println(yearChooser.getValue());
				        	dispose();
						}else {
							Object[] options = {"Tamam"};
				        	JOptionPane.showOptionDialog(rootPane, "Bu araç az önce kiralandı.", "Hatalı İşlem!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				        	dispose();
						}
					} catch (NumberFormatException | SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(10, 620, 250, 40);
		contentPane.add(btnNewButton);
		
		lblCustomerId = new JLabel("");
		lblCustomerId.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCustomerId.setBounds(414, 11, 174, 20);
		contentPane.add(lblCustomerId);
		
		lblCardType = new JLabel("");
		lblCardType.setBounds(486, 504, 100, 69);
		contentPane.add(lblCardType);
		
		lblCustomerName = new JLabel("");
		lblCustomerName.setBounds(598, 11, 152, 20);
		contentPane.add(lblCustomerName);
		
		lblBranchName = new JLabel("");
		lblBranchName.setBounds(598, 39, 152, 20);
		contentPane.add(lblBranchName);
		
		lblBranchId = new JLabel("");
		lblBranchId.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBranchId.setBounds(414, 39, 174, 20);
		contentPane.add(lblBranchId);
		
		lblMessage = new JLabel("");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(new Color(255, 215, 0));
		lblMessage.setFont(new Font("Arial", Font.PLAIN, 12));
		lblMessage.setBounds(279, 441, 297, 14);
		contentPane.add(lblMessage);
		
		lblkrediKartı = new JLabel(new ImageIcon(getClass().getResource("creditCard.png")));
		lblkrediKartı.setBounds(0, 0, 760, 618);
		contentPane.add(lblkrediKartı);
		
		JLabel lblDays = new JLabel("Kiralanacak Gün Sayısı : ");
		lblDays.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDays.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		lblDays.setBounds(358, 290, 218, 20);
		contentPane.add(lblDays);
		
		lblTotalDays = new JLabel("");
		lblTotalDays.setForeground(Color.RED);
		lblTotalDays.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblTotalDays.setBounds(576, 290, 174, 20);
		contentPane.add(lblTotalDays);
		
	}
	
	
	public void updateTotalPrice(){
		try {
			Date startDate = select_rent.getDate();
	        Date endDate = select_return.getDate();
	        
	        long difference = endDate.getTime() - startDate.getTime();
	        int day = (int)(difference / 1000 / 60 / 60 / 24);
	        
	        String price = lblPrice.getText();
	        String[] _arr = price.split("\\s");
	        price = _arr[0];
	        int newprice = Integer.parseInt(price);
	    	int totalPrice = day * newprice;
	        if(day <= 0) {
	        	Object[] options = {"Tamam"};
	        	JOptionPane.showOptionDialog(rootPane, "Lütfen kira bitiş tarihini ileri bir tarihe seçiniz!", "Tarih Hatası!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	        	select_return.setCalendar(null);
	        }else {
	        	lblTotalPrice.setText(totalPrice + " TL");
	        	lblTotalDays.setText(day + " Gün");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
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
