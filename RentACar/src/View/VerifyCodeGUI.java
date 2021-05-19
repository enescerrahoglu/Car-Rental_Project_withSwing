package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;

@SuppressWarnings("serial")
public class VerifyCodeGUI extends JFrame {

	MailSenderGUI msGUI = new MailSenderGUI();
	private JPanel contentPane;
	String code;
	String myMail;
	private JLabel lblText2;
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

	public VerifyCodeGUI() {
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
		fldCode.setHorizontalAlignment(SwingConstants.CENTER);
		fldCode.setBackground(new Color(255, 255, 224));
		fldCode.setForeground(new Color(0, 128, 0));
		fldCode.setFont(new Font("Arial", Font.BOLD, 16));
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
					stopTimer();
					dispose();
					ResetPasswordGUI resetPasswordGUI = new ResetPasswordGUI();
					resetPasswordGUI.myMail = getMyMail();
					resetPasswordGUI.setVisible(true);
					resetPasswordGUI.setLocationRelativeTo(null);
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
		
		
		
		lblText2 = new JLabel("Doðrulama Kodu:");
		lblText2.setFont(new Font("Arial", Font.PLAIN, 14));
		lblText2.setBounds(10, 47, 118, 25);
		panel.add(lblText2);
		
	}
	public String getMyMail() {
		return this.myMail;
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
			}
			i--;
		}
	});
	
	public void stopTimer() {
		t.stop();
	}

}
