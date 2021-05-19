package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import Helper.DBConnection;
import Helper.Helper;
import Model.Branch;
import Model.BranchCar;
import Model.Car;
import Model.Customer;
import Model.Rental;
import Model.Admin;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.TextField;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPasswordField;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class CustomerGUI extends JFrame {
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	Branch branch = new Branch();
	Car car = new Car();
	BranchCar branchCar = new BranchCar();
	Admin admin = new Admin();
	Rental rental = new Rental();
	static Customer customer = new Customer();
	
	JLabel lbl_image;
	

	private JPanel contentPane;
	private JTextField fld_carId;
	private JTable table_branchCars;
	JTable table_branches;
	
	private DefaultTableModel branchModel = null;
	private Object[] branchData = null;
	private DefaultTableModel branchCarModel = null;
	private Object[] branchCarData = null;
	private DefaultTableModel allRentalsModel = null;
	private Object[] allRentalsData = null;
	
	JLabel lblBrand;
	JLabel lblModel;
	JLabel lblModelYear;
	JLabel lblColor;
	JLabel lblDailyPrice;
	JLabel lblBranchName;
	
	TextField textField;
	private JPanel w_myAllRentals;
	private JTable table_myAllRentals;
	
	JLabel lblNotRentals;
	private JPasswordField fldPass2;
	private JPasswordField fldPass1;
	private JPasswordField fldPassAgainForDelete;
	
	JLabel lblMyMail;
	private JLabel lblEMail;
	private JPasswordField fldCurrentPass;
	JLabel lblMyPass;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		Properties props = new Properties();
		props.put("logoString", "CoD");
		AluminiumLookAndFeel.setCurrentTheme(props);
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerGUI frame = new CustomerGUI(customer);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CustomerGUI(Customer customer) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CustomerGUI.class.getResource("/View/icon.png")));
		
		//Branch Model
		branchModel = new DefaultTableModel();
		Object[] colBranch = new Object[2];
		colBranch[0] = "ID";
		colBranch[1] = "Þube Adý";
		branchModel.setColumnIdentifiers(colBranch);
		branchData = new Object[2];
		for (int i = 0; i < branch.getBranchList().size(); i++) {
			branchData[0] = branch.getBranchList().get(i).getId();
			branchData[1] = branch.getBranchList().get(i).getName();
			branchModel.addRow(branchData);
		}
		
		// BranchCar Model
		branchCarModel = new DefaultTableModel();
		Object[] colBranchCar = new Object[6];
		colBranchCar[0] = "Araç ID";
		colBranchCar[1] = "Marka Adý";
		colBranchCar[2] = "Model Adý";
		colBranchCar[3] = "Model Yýlý";
		colBranchCar[4] = "Renk";
		colBranchCar[5] = "Günlük Kira Ücreti";
		branchCarModel.setColumnIdentifiers(colBranchCar);
		branchCarData = new Object[6];
		
		// AllRentals Model
		allRentalsModel = new DefaultTableModel();
		Object[] colRentals = new Object[5];
		colRentals[0] = "Kira ID";
		colRentals[1] = "Araç Adý";
		colRentals[2] = "Þube Adý";
		colRentals[3] = "Kiralanma Tarihi";
		colRentals[4] = "Kira Bitiþ Tarihi";
		allRentalsModel.setColumnIdentifiers(colRentals);
		allRentalsData = new Object[5];
		for (int i = 0; i < rental.getMyAllRentals(customer.getId()).size(); i++) {
			allRentalsData[0] = rental.getMyAllRentals(customer.getId()).get(i).getId();
			allRentalsData[1] = rental.getMyAllRentals(customer.getId()).get(i).getCarName();
			allRentalsData[2] = rental.getMyAllRentals(customer.getId()).get(i).getBranchName();
			allRentalsData[3] = rental.getMyAllRentals(customer.getId()).get(i).getRentDate();
			allRentalsData[4] = rental.getMyAllRentals(customer.getId()).get(i).getReturnDate();
			allRentalsModel.addRow(allRentalsData);
		}
		
		setTitle("CarOfDuty");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("activeCaption"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Merhaba, " + customer.getName());
		lblNewLabel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNewLabel.setBounds(10, 11, 400, 24);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Çýkýþ Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure for exit")) {
					LoginGUI login = new LoginGUI();
					login.setVisible(true);
					login.setLocationRelativeTo(null);
					dispose();
				}
			}
		});
		btnNewButton.setForeground(new Color(216, 191, 216));
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(138, 43, 226));
		btnNewButton.setBounds(845, 11, 89, 24);
		contentPane.add(btnNewButton);
		
		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				try {
					updateRentalsModel(customer.getId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		w_tabbedPane.setBounds(10, 46, 924, 514);
		contentPane.add(w_tabbedPane);
		
		JPanel w_customerPanel = new JPanel();
		w_customerPanel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Kiralama Ýþlemleri", null, w_customerPanel, null);
		w_customerPanel.setLayout(null);
		
		JScrollPane w_scrollBranchCars = new JScrollPane();
		w_scrollBranchCars.setBounds(270, 35, 639, 230);
		w_customerPanel.add(w_scrollBranchCars);
		
		table_branchCars = new JTable(){
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		table_branchCars.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_branchCars.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		table_branchCars.getTableHeader().setReorderingAllowed(false);
		table_branchCars.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_carId.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 0).toString());
					
					String sql = "SELECT image FROM cars WHERE id = '" + fld_carId.getText() + "' ";
					st = con.createStatement();
					rs = st.executeQuery(sql);
					if(rs.next()) {
						byte[] imagedata = rs.getBytes("image");
						ImageIcon formate = new ImageIcon(imagedata);
						lbl_image.setIcon(formate);
						lblBrand.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 1).toString());
						lblModel.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 2).toString());
						lblModelYear.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 3).toString());
						lblColor.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 4).toString());
						lblDailyPrice.setText("Bir günlük kira bedeli: " + table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 5).toString());
						lblBranchName.setText("Bulunduðu Þube: " + table_branches.getValueAt(table_branches.getSelectedRow(), 1).toString());
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		w_scrollBranchCars.setViewportView(table_branchCars);
		
		JLabel lblbranchCarList = new JLabel("Þubedeki Araç Listesi");
		lblbranchCarList.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblbranchCarList.setBounds(270, 11, 639, 20);
		w_customerPanel.add(lblbranchCarList);
		
		JLabel lblSubeSec = new JLabel("Þube Seç");
		lblSubeSec.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblSubeSec.setBounds(10, 11, 250, 20);
		w_customerPanel.add(lblSubeSec);
		
		JScrollPane w_scrollBranches = new JScrollPane();
		w_scrollBranches.setBounds(10, 35, 250, 230);
		w_customerPanel.add(w_scrollBranches);
		
		table_branches = new JTable(branchModel){
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		table_branches.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_branches.getTableHeader().setReorderingAllowed(false);
		table_branches.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_branches.getColumnModel().getColumn(1).setPreferredWidth(200);
		table_branches.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table_branches.getSelectedRow();
				if(selectedRow >= 0) {
					String selectedBranch = table_branches.getModel().getValueAt(selectedRow, 0).toString();
					int selectedBranchId = Integer.parseInt(selectedBranch);
					DefaultTableModel clearModel = (DefaultTableModel) table_branchCars.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i = 0; i < admin.getFreeBranchCarList(selectedBranchId).size(); i++) {
							branchCarData[0] = admin.getFreeBranchCarList(selectedBranchId).get(i).getId();
							branchCarData[1] = admin.getFreeBranchCarList(selectedBranchId).get(i).getBrandName();
							branchCarData[2] = admin.getFreeBranchCarList(selectedBranchId).get(i).getModelName();
							branchCarData[3] = admin.getFreeBranchCarList(selectedBranchId).get(i).getModelYear();
							branchCarData[4] = admin.getFreeBranchCarList(selectedBranchId).get(i).getColor();
							branchCarData[5] = admin.getFreeBranchCarList(selectedBranchId).get(i).getDailyPrice() + " TL";
							branchCarModel.addRow(branchCarData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_branchCars.setModel(branchCarModel);
					lbl_image.setIcon(null);
					lblBrand.setText(null);
					lblModel.setText(null);
					lblModelYear.setText(null);
					lblColor.setText(null);
					lblDailyPrice.setText(null);
					lblBranchName.setText(null);
					fld_carId.setText(null);
				}else {
					Helper.showMsg("Bir þube seçin!");
				}
				
				table_branchCars.getColumnModel().getColumn(0).setPreferredWidth(40);
				table_branchCars.getColumnModel().getColumn(1).setPreferredWidth(100);
				table_branchCars.getColumnModel().getColumn(2).setPreferredWidth(100);
				table_branchCars.getColumnModel().getColumn(3).setPreferredWidth(100);
				table_branchCars.getColumnModel().getColumn(4).setPreferredWidth(100);
				table_branchCars.getColumnModel().getColumn(5).setPreferredWidth(100);
			}
		});
		w_scrollBranches.setViewportView(table_branches);
		
		JLabel lbl_carId = new JLabel("Araç ID");
		lbl_carId.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_carId.setBounds(10, 379, 250, 20);
		w_customerPanel.add(lbl_carId);
		
		fld_carId = new JTextField();
		fld_carId.setEditable(false);
		fld_carId.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_carId.setColumns(10);
		fld_carId.setBounds(10, 400, 250, 24);
		w_customerPanel.add(fld_carId);
		
		JButton btn_rentCar = new JButton("Kirala");
		btn_rentCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_carId.getText().length() == 0) {
					Helper.showMsg("Lütfen kiralama iþlemine geçmeden önce bir araç seçin!");
				}else {
					String selectedCarID = fld_carId.getText();
					RentalGUI rental;
					try {
						rental = new RentalGUI();
						rental.lblCustomerId.setText("Müþteri Id ve Adý: " + customer.getId());
						rental.lblCustomerName.setText(customer.getName());
						rental.lblBranchId.setText("Þube Id ve Adý: " + table_branches.getValueAt(table_branches.getSelectedRow(), 0).toString());
						rental.lblBranchName.setText(table_branches.getValueAt(table_branches.getSelectedRow(), 1).toString());
						rental.lblCarId.setText(selectedCarID);
						rental.lblBrand.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 1).toString());
						rental.lblModel.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 2).toString());
						rental.lblModelYear.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 3).toString());
						rental.lblColor.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 4).toString());
						rental.lblPrice.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 5).toString());
						rental.customerMail = getMyEMail();
						try {
							String sql = "SELECT image FROM cars WHERE id = '" + rental.lblCarId.getText() + "' ";
							st = con.createStatement();
							rs = st.executeQuery(sql);
							if(rs.next()) {
								byte[] imagedata = rs.getBytes("image");
								ImageIcon formate = new ImageIcon(imagedata);
								rental.lbl_carImage.setIcon(formate);
							}
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
						rental.setVisible(true);
						rental.setLocationRelativeTo(null);
						rental.setAlwaysOnTop( true );
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				if(fld_carId.getText().length() != 0) {
					try {
						table_branches.removeRowSelectionInterval(table_branches.getSelectedRow(), 0);
						DefaultTableModel clearModel = (DefaultTableModel) table_branchCars.getModel();
						clearModel.setRowCount(0);
						fld_carId.setText(null);
						lbl_image.setIcon(null);
						lblBrand.setText(null);
						lblBranchName.setText(null);
						lblColor.setText(null);
						lblModel.setText(null);
						lblModelYear.setText(null);
						lblDailyPrice.setText(null);
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});
		btn_rentCar.setForeground(new Color(34, 139, 34));
		btn_rentCar.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btn_rentCar.setBackground(new Color(50, 205, 50));
		btn_rentCar.setBounds(10, 435, 250, 40);
		w_customerPanel.add(btn_rentCar);
		
		lbl_image = new JLabel("");
		lbl_image.setBounds(270, 286, 336, 189);
		w_customerPanel.add(lbl_image);
		
		lblBrand = new JLabel("");
		lblBrand.setForeground(Color.BLACK);
		lblBrand.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBrand.setBounds(616, 286, 265, 25);
		w_customerPanel.add(lblBrand);
		
		lblModel = new JLabel("");
		lblModel.setForeground(Color.BLACK);
		lblModel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblModel.setBounds(616, 317, 265, 25);
		w_customerPanel.add(lblModel);
		
		lblModelYear = new JLabel("");
		lblModelYear.setForeground(Color.BLACK);
		lblModelYear.setFont(new Font("Arial", Font.PLAIN, 16));
		lblModelYear.setBounds(616, 348, 265, 25);
		w_customerPanel.add(lblModelYear);
		
		lblColor = new JLabel("");
		lblColor.setForeground(Color.BLACK);
		lblColor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblColor.setBounds(616, 379, 265, 25);
		w_customerPanel.add(lblColor);
		
		lblDailyPrice = new JLabel("");
		lblDailyPrice.setForeground(Color.BLACK);
		lblDailyPrice.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDailyPrice.setBounds(616, 410, 265, 25);
		w_customerPanel.add(lblDailyPrice);
		
		lblBranchName = new JLabel("");
		lblBranchName.setForeground(Color.BLACK);
		lblBranchName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBranchName.setBounds(616, 441, 265, 25);
		w_customerPanel.add(lblBranchName);
		
		w_myAllRentals = new JPanel();
		w_myAllRentals.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Kiralamalarým", null, w_myAllRentals, null);
		w_myAllRentals.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 899, 420);
		w_myAllRentals.add(scrollPane);
		
		table_myAllRentals = new JTable(allRentalsModel){
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		table_myAllRentals.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_myAllRentals.getColumnModel().getColumn(0).setPreferredWidth(10);
		table_myAllRentals.getColumnModel().getColumn(1).setPreferredWidth(150);
		table_myAllRentals.getColumnModel().getColumn(2).setPreferredWidth(150);
		table_myAllRentals.getColumnModel().getColumn(3).setPreferredWidth(150);
		table_myAllRentals.getColumnModel().getColumn(4).setPreferredWidth(150);
		table_myAllRentals.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table_myAllRentals);
		
		lblNotRentals = new JLabel("");
		lblNotRentals.setHorizontalAlignment(SwingConstants.CENTER);
		lblNotRentals.setForeground(new Color(255, 0, 0));
		lblNotRentals.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNotRentals.setBounds(10, 442, 899, 33);
		w_myAllRentals.add(lblNotRentals);
		
		JPanel w_transactionsPanel = new JPanel();
		w_transactionsPanel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Ýþlemlerim", null, w_transactionsPanel, null);
		w_transactionsPanel.setLayout(null);
		
		fldPass2 = new JPasswordField();
		fldPass2.setForeground(SystemColor.controlDkShadow);
		fldPass2.setFont(new Font("Arial", Font.PLAIN, 16));
		fldPass2.setBackground(Color.LIGHT_GRAY);
		fldPass2.setBounds(322, 195, 274, 26);
		w_transactionsPanel.add(fldPass2);
		
		JLabel lbl_sifreAgain = new JLabel("Yeni Þifre (Tekrar)");
		lbl_sifreAgain.setForeground(SystemColor.controlDkShadow);
		lbl_sifreAgain.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifreAgain.setBounds(322, 174, 274, 21);
		w_transactionsPanel.add(lbl_sifreAgain);
		
		fldPass1 = new JPasswordField();
		fldPass1.setForeground(SystemColor.controlDkShadow);
		fldPass1.setFont(new Font("Arial", Font.PLAIN, 16));
		fldPass1.setBackground(Color.LIGHT_GRAY);
		fldPass1.setBounds(322, 142, 274, 26);
		w_transactionsPanel.add(fldPass1);
		
		JLabel lbl_sifre = new JLabel("Yeni Þifre");
		lbl_sifre.setForeground(SystemColor.controlDkShadow);
		lbl_sifre.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifre.setBounds(322, 121, 274, 21);
		w_transactionsPanel.add(lbl_sifre);
		
		JButton btnResetPassword = new JButton("Parolamý Yenile");
		btnResetPassword.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String pass = String.valueOf(fldPass1.getPassword());
				String passAgain = String.valueOf(fldPass2.getPassword());
				if(fldPass1.getText().length() == 0 || fldPass2.getText().length() == 0 || lblMyMail.getText().length() == 0 || fldCurrentPass.getText().length() == 0) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Lütfen tüm alanlarý doldurunuz!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(!fldCurrentPass.getText().equals(lblMyPass.getText())) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Mevcut þifreniz hatalý!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(pass.length() < 8) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Þifreniz en az 8 karakterden oluþmalýdýr.", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(!pass.equals(passAgain)){
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Tekrar girilen þifre, ilk þifre ile ayný deðil!", "HATA!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					System.out.println(pass + " " + passAgain + " " + lblMyMail.getText());
				}else {
					try {
						boolean control = customer.resetPassword(fldPass1.getText(), lblMyMail.getText());
						if(control) {
							sendMail(lblMyMail.getText(), "Þifreniz Yenilendi", "Yapmýþ olduðunuz deðiþiklikten ötürü CarOfDuty Araç Kiralama Sistemindeki þifreniz güncellenmiþtir.\n\nSaðlýklý günler dileriz...\nCarOfDuty Araç Kiralama Sistemi");
							Helper.showMsg("success");
							lblMyPass.setText(fldPass1.getText());
							fldCurrentPass.setText(null);
							fldPass1.setText(null);
							fldPass2.setText(null);
						}else {
							fldCurrentPass.setText(null);
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
		btnResetPassword.setForeground(Color.CYAN);
		btnResetPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnResetPassword.setBackground(new Color(65, 105, 225));
		btnResetPassword.setBounds(322, 244, 274, 26);
		w_transactionsPanel.add(btnResetPassword);
		
		JLabel lbl_sifreAgain_1 = new JLabel("Mevcut Þifre");
		lbl_sifreAgain_1.setForeground(SystemColor.controlDkShadow);
		lbl_sifreAgain_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifreAgain_1.setBounds(322, 359, 274, 21);
		w_transactionsPanel.add(lbl_sifreAgain_1);
		
		fldPassAgainForDelete = new JPasswordField();
		fldPassAgainForDelete.setForeground(SystemColor.controlDkShadow);
		fldPassAgainForDelete.setFont(new Font("Arial", Font.PLAIN, 16));
		fldPassAgainForDelete.setBackground(Color.LIGHT_GRAY);
		fldPassAgainForDelete.setBounds(322, 380, 274, 26);
		w_transactionsPanel.add(fldPassAgainForDelete);
		
		JButton btnHesabmSil = new JButton("Hesabýmý Sil");
		btnHesabmSil.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if(fldPassAgainForDelete.getText().length() == 0) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Hesabýnýzý silmek için mevcut þifrenizi girmeniz gerekir!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else if(!fldPassAgainForDelete.getText().equals(lblMyPass.getText())) {
					Object[] options = {"Tamam"};
					JOptionPane.showOptionDialog(rootPane, "Mevcut þifreniz hatalý!", "UYARI!", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
				}else {
					if (Helper.confirm("sure")) {
						try {
							customer.deleteAccount(lblMyMail.getText());
							sendMail(lblMyMail.getText(), "Gittiðinize Üzüldük :'/", "CarOfDuty Araç Kiralama Sisteminden hesabýnýz baþarýyla silindi.\n\nSizi yeniden aramýzda görmek dileðiyle, Hoþçakalýn...\nCarOfDuty Araç Kiralama Sistemi");
							Object[] options = {"Teþekkürler"};
							JOptionPane.showOptionDialog(rootPane, "Gittiðinize Üzüldük :'/", "Hoþçakalýn!", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
							dispose();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnHesabmSil.setForeground(new Color(255, 192, 203));
		btnHesabmSil.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnHesabmSil.setBackground(new Color(165, 42, 42));
		btnHesabmSil.setBounds(322, 429, 274, 26);
		w_transactionsPanel.add(btnHesabmSil);
		
		lblMyMail = new JLabel("");
		lblMyMail.setFont(new Font("Arial", Font.PLAIN, 14));
		lblMyMail.setBounds(196, 11, 713, 26);
		w_transactionsPanel.add(lblMyMail);
		
		lblEMail = new JLabel("Kayýtlý E-posta Adresim:   ");
		lblEMail.setFont(new Font("Arial", Font.BOLD, 14));
		lblEMail.setBounds(10, 11, 176, 26);
		w_transactionsPanel.add(lblEMail);
		
		JLabel lbl_sifreAgain_1_1 = new JLabel("Mevcut Þifre");
		lbl_sifreAgain_1_1.setForeground(SystemColor.controlDkShadow);
		lbl_sifreAgain_1_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_sifreAgain_1_1.setBounds(322, 68, 274, 21);
		w_transactionsPanel.add(lbl_sifreAgain_1_1);
		
		fldCurrentPass = new JPasswordField();
		fldCurrentPass.setForeground(SystemColor.controlDkShadow);
		fldCurrentPass.setFont(new Font("Arial", Font.PLAIN, 16));
		fldCurrentPass.setBackground(Color.LIGHT_GRAY);
		fldCurrentPass.setBounds(322, 89, 274, 26);
		w_transactionsPanel.add(fldCurrentPass);
		
		lblMyPass = new JLabel("");
		lblMyPass.setFont(new Font("Arial", Font.PLAIN, 14));
		lblMyPass.setBounds(452, 144, 15, 15);
		w_transactionsPanel.add(lblMyPass);
		
		if(table_myAllRentals.getRowCount() == 0) {
			lblNotRentals.setText("Listelenecek kiralamalarýnýz bulunmamaktadýr.");
		}else {
			lblNotRentals.setForeground(new Color(50, 205, 50));
			lblNotRentals.setText("Kiralamalarýnýz listelenmektedir.");
		}
	}
	
	public void updateRentalsModel(int customerId) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_myAllRentals.getModel();
		clearModel.setRowCount(0);
		for(int i = 0; i < rental.getMyAllRentals(customerId).size(); i++) {
			allRentalsData[0] = rental.getMyAllRentals(customerId).get(i).getId();
			allRentalsData[1] = rental.getMyAllRentals(customerId).get(i).getCarName();
			allRentalsData[2] = rental.getMyAllRentals(customerId).get(i).getBranchName();
			allRentalsData[3] = rental.getMyAllRentals(customerId).get(i).getRentDate();
			allRentalsData[4] = rental.getMyAllRentals(customerId).get(i).getReturnDate();
			allRentalsModel.addRow(allRentalsData);
			
			lblNotRentals.setForeground(new Color(50, 205, 50));
			lblNotRentals.setText("Kiralamalarýnýz listelenmektedir.");
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
	
	public String getMyEMail() {
		return lblMyMail.getText();
	}
}
