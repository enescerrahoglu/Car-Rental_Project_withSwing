package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Helper.DBConnection;
import Helper.Helper;
import Model.Branch;
import Model.Customer;
import Model.BranchManager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.SystemColor;

@SuppressWarnings("serial")
public class BranchManagerGUI extends JFrame {
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	static BranchManager branchManager = new BranchManager();
	static Branch branch = new Branch();
	static Customer customer = new Customer();
	
	private JPanel w_contentPane;
	private JTable table_branchCars;
	
	private DefaultTableModel pCarModel = null;
	private Object[] pCarData = null;
	
	private DefaultTableModel rentalModel = null;
	private Object[] rentalData = null;
	
	JTextField fld_dailyPrice;
	private JTable table_allRentals;
	private JTextField fld_carId;
	
	JLabel lbl_image;
	JLabel lblTotalCar;
	JLabel lblTotalRental;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BranchManagerGUI frame = new BranchManagerGUI(branchManager);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("static-access")
	public BranchManagerGUI(BranchManager branchManager) throws SQLException {
		
		//Present Car Model
		pCarModel = new DefaultTableModel();
		Object[] colCar = new Object[7];
		colCar[0] = "Araç ID";
		colCar[1] = "Marka Adý";
		colCar[2] = "Model Adý";
		colCar[3] = "Model Yýlý";
		colCar[4] = "Renk";
		colCar[5] = "Günlük Kira Ücreti";
		colCar[6] = "Kira Durumu";
		st = con.createStatement();
		pCarModel.setColumnIdentifiers(colCar);
		pCarData = new Object[7];
		for (int i = 0; i < branchManager.getBranchPresentCarList(branchManager.getId()).size(); i++) {
			pCarData[0] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId();
			pCarData[1] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getBrandName();
			pCarData[2] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelName();
			pCarData[3] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelYear();
			pCarData[4] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getColor();
			pCarData[5] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getDailyPrice() + " TL";
			pCarData[6] = branchManager.isRental(branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId());
			pCarModel.addRow(pCarData);
		}
		
		//Rentals Model
		rentalModel = new DefaultTableModel();
		Object[] colRental = new Object[7];
		colRental[0] = "Kira ID";
		colRental[1] = "Müþteri ID";
		colRental[2] = "Müþteri Adý";
		colRental[3] = "Araç ID";
		colRental[4] = "Araç Adý";
		colRental[5] = "Kira Baþlangýç Tarihi";
		colRental[6] = "Kira Bitiþ Tarihi";
		st = con.createStatement();
		rentalModel.setColumnIdentifiers(colRental);
		rentalData = new Object[7];
		for (int i = 0; i < branchManager.getAllRentals(branchManager.getId()).size(); i++) {
			rentalData[0] = branchManager.getAllRentals(branchManager.getId()).get(i).getId();
			rentalData[1] = branchManager.getAllRentals(branchManager.getId()).get(i).getCustomerId();
			rentalData[2] = branchManager.getAllRentals(branchManager.getId()).get(i).getCustomerName();
			rentalData[3] = branchManager.getAllRentals(branchManager.getId()).get(i).getCarId();
			rentalData[4] = branchManager.getAllRentals(branchManager.getId()).get(i).getCarName();
			rentalData[5] = branchManager.getAllRentals(branchManager.getId()).get(i).getRentDate();
			rentalData[6] = branchManager.getAllRentals(branchManager.getId()).get(i).getReturnDate();
			rentalModel.addRow(rentalData);
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(BranchManagerGUI.class.getResource("/View/icon.png")));
		setTitle("CarOfDuty");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		w_contentPane = new JPanel();
		w_contentPane.setBackground(UIManager.getColor("activeCaption"));
		w_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_contentPane);
		w_contentPane.setLayout(null);
		
		JLabel lblBMName = new JLabel("Merhaba, " + branchManager.getName());
		lblBMName.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblBMName.setBounds(10, 11, 400, 24);
		w_contentPane.add(lblBMName);
		
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
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 12));
		btnNewButton.setBackground(new Color(138, 43, 226));
		btnNewButton.setBounds(845, 11, 89, 24);
		w_contentPane.add(btnNewButton);
		
		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				try {
					DefaultTableModel clearModel = (DefaultTableModel) table_allRentals.getModel();
					clearModel.setRowCount(0);
					for (int i = 0; i < branchManager.getAllRentals(branchManager.getId()).size(); i++) {
						rentalData[0] = branchManager.getAllRentals(branchManager.getId()).get(i).getId();
						rentalData[1] = branchManager.getAllRentals(branchManager.getId()).get(i).getCustomerId();
						rentalData[2] = branchManager.getAllRentals(branchManager.getId()).get(i).getCustomerName();
						rentalData[3] = branchManager.getAllRentals(branchManager.getId()).get(i).getCarId();
						rentalData[4] = branchManager.getAllRentals(branchManager.getId()).get(i).getCarName();
						rentalData[5] = branchManager.getAllRentals(branchManager.getId()).get(i).getRentDate();
						rentalData[6] = branchManager.getAllRentals(branchManager.getId()).get(i).getReturnDate();
						rentalModel.addRow(rentalData);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		w_tabbedPane.setBounds(10, 46, 924, 514);
		w_contentPane.add(w_tabbedPane);
		
		JPanel panel_presentCar = new JPanel();
		panel_presentCar.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Mevcut Araçlar", null, panel_presentCar, null);
		panel_presentCar.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 899, 254);
		panel_presentCar.add(scrollPane);
		
		lbl_image = new JLabel(new ImageIcon(getClass().getResource("default.png")));
		lbl_image.setBounds(291, 286, 336, 189);
		panel_presentCar.add(lbl_image);


		table_branchCars = new JTable(pCarModel){
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)){
                	c.setBackground(Color.BLACK);
                	c.setForeground(Color.WHITE);
                }
                return c;
            }
			public boolean isCellEditable(int row, int column){  
		          return false;  
			}
		};
		table_branchCars.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_branchCars.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				fld_carId.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 0).toString());
			}
		});
		table_branchCars.getTableHeader().setReorderingAllowed(false);
		table_branchCars.getColumnModel().getColumn(0).setPreferredWidth(75);
		table_branchCars.getColumnModel().getColumn(1).setPreferredWidth(200);
		table_branchCars.getColumnModel().getColumn(2).setPreferredWidth(200);
		table_branchCars.getColumnModel().getColumn(3).setPreferredWidth(200);
		table_branchCars.getColumnModel().getColumn(4).setPreferredWidth(200);
		table_branchCars.getColumnModel().getColumn(5).setPreferredWidth(200);
		table_branchCars.getColumnModel().getColumn(6).setPreferredWidth(200);
		table_branchCars.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_dailyPrice.setText(table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 5).toString().replace(" TL", ""));
					
					String carId = (table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 0).toString());
					
					String sql = "SELECT image FROM cars WHERE id = '" + carId + "' ";
					st = con.createStatement();
					rs = st.executeQuery(sql);
					if(rs.next()) {
						byte[] imagedata = rs.getBytes("image");
						ImageIcon formate = new ImageIcon(imagedata);
						lbl_image.setIcon(formate);
					}
							
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		table_branchCars.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus, int row, int col) {

		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		        
		        String status = (String)table.getModel().getValueAt(row, 6);
		        if ("Kiralandý".equals(status)) {
		            setBackground(Color.GREEN);
		            setForeground(Color.black);
		        } else {
		            setBackground(table.getBackground());
		            setForeground(table.getForeground());
		        }
		        return this;
		    }   
		});
		scrollPane.setViewportView(table_branchCars);

		fld_dailyPrice = new JTextField();
		fld_dailyPrice.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					fld_dailyPrice.setEditable(false);
				}else {
					fld_dailyPrice.setEditable(true);
				}
			}
		});
		fld_dailyPrice.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_dailyPrice.setColumns(10);
		fld_dailyPrice.setBounds(709, 400, 165, 24);
		panel_presentCar.add(fld_dailyPrice);
		
		JLabel lbl_dailyPrice = new JLabel("Günlük Kira Ücreti");
		lbl_dailyPrice.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_dailyPrice.setBounds(709, 379, 200, 20);
		panel_presentCar.add(lbl_dailyPrice);
		
		JLabel lblPriceModel = new JLabel("TL");
		lblPriceModel.setForeground(new Color(34, 139, 34));
		lblPriceModel.setHorizontalAlignment(SwingConstants.CENTER);
		lblPriceModel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblPriceModel.setBounds(874, 400, 35, 24);
		panel_presentCar.add(lblPriceModel);
		
		JButton btn_updatePrice = new JButton("Güncelle");
		btn_updatePrice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_dailyPrice.getText().length() == 0) {
					Helper.showMsg("Fiyatýný güncellemek istediðiniz aracý seçiniz!");
				}else if(Helper.confirm("sure")) {
					String carId = (table_branchCars.getValueAt(table_branchCars.getSelectedRow(), 0).toString());
					try {
						branchManager.updateDailyPrice(Integer.parseInt(carId), fld_dailyPrice.getText());
						Helper.showMsg("success");
						fld_dailyPrice.setText(null);
						fld_carId.setText(null);
						lbl_image.setIcon(new ImageIcon(getClass().getResource("default.png")));
						
						DefaultTableModel clearModel = (DefaultTableModel) table_branchCars.getModel();
						clearModel.setRowCount(0);
						branchManager.count = 0;
						for (int i = 0; i < branchManager.getBranchPresentCarList(branchManager.getId()).size(); i++) {
							pCarData[0] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId();
							pCarData[1] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getBrandName();
							pCarData[2] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelName();
							pCarData[3] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelYear();
							pCarData[4] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getColor();
							pCarData[5] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getDailyPrice() + " TL";
							pCarData[6] = branchManager.isRental(branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId());
							pCarModel.addRow(pCarData);
						}
						table_branchCars.setModel(pCarModel);
						lblTotalCar.setText("Þubedeki Toplam Araç Sayýsý: " + table_branchCars.getRowCount());
						lblTotalRental.setText("Kiralanan Toplam Araç Sayýsý: " + branchManager.count);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updatePrice.setForeground(new Color(50, 205, 50));
		btn_updatePrice.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btn_updatePrice.setBackground(new Color(240, 255, 240));
		btn_updatePrice.setBounds(709, 435, 200, 40);
		panel_presentCar.add(btn_updatePrice);
		
		JButton btn_openRental = new JButton("Kiralanmaya Aç");
		btn_openRental.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_carId.getText().length() == 0) {
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Bir araç seçin!", "Mesaj", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
				}else {
					int id = Integer.parseInt(fld_carId.getText().toString());
					try {
						if(Helper.confirm("sure")) {
							String status = (String)table_branchCars.getModel().getValueAt(table_branchCars.getSelectedRow(), 6);
							if(status == "Kiralandý") {
								branchManager.deleteRental(id);
								Helper.showMsg("success");
								DefaultTableModel clearModel = (DefaultTableModel) table_branchCars.getModel();
								clearModel.setRowCount(0);
								branchManager.count = 0;
								for (int i = 0; i < branchManager.getBranchPresentCarList(branchManager.getId()).size(); i++) {
									pCarData[0] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId();
									pCarData[1] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getBrandName();
									pCarData[2] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelName();
									pCarData[3] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelYear();
									pCarData[4] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getColor();
									pCarData[5] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getDailyPrice() + " TL";
									pCarData[6] = branchManager.isRental(branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId());
									pCarModel.addRow(pCarData);
								}
								table_branchCars.setModel(pCarModel);
								lblTotalCar.setText("Þubedeki Toplam Araç Sayýsý: " + table_branchCars.getRowCount());
								lblTotalRental.setText("Kiralanan Toplam Araç Sayýsý: " + branchManager.count);
								fld_dailyPrice.setText(null);
								fld_carId.setText(null);
								lbl_image.setIcon(new ImageIcon(getClass().getResource("default.png")));
							}else {
								Object[] options = {"Tamam"};
					        	JOptionPane.showOptionDialog(rootPane, "Araç zaten kiralanmaya açýk!", "Hatalý iþlem", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_openRental.setForeground(new Color(30, 144, 255));
		btn_openRental.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btn_openRental.setBackground(new Color(176, 224, 230));
		btn_openRental.setBounds(10, 435, 200, 40);
		panel_presentCar.add(btn_openRental);
		
		fld_carId = new JTextField();
		fld_carId.setEditable(false);
		fld_carId.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_carId.setColumns(10);
		fld_carId.setBounds(80, 400, 130, 24);
		panel_presentCar.add(fld_carId);
		
		JLabel lbl_dailyPrice_1 = new JLabel("Araç ID");
		lbl_dailyPrice_1.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lbl_dailyPrice_1.setBounds(10, 403, 70, 20);
		panel_presentCar.add(lbl_dailyPrice_1);
		
		JButton btn_updateCar = new JButton("");
		btn_updateCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel clearModel = (DefaultTableModel) table_branchCars.getModel();
				clearModel.setRowCount(0);
				branchManager.count = 0;
				try {
					for (int i = 0; i < branchManager.getBranchPresentCarList(branchManager.getId()).size(); i++) {
						pCarData[0] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId();
						pCarData[1] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getBrandName();
						pCarData[2] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelName();
						pCarData[3] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getModelYear();
						pCarData[4] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getColor();
						pCarData[5] = branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getDailyPrice() + " TL";
						pCarData[6] = branchManager.isRental(branchManager.getBranchPresentCarList(branchManager.getId()).get(i).getId());
						pCarModel.addRow(pCarData);
					}
					lblTotalRental.setText("Kiralanan Toplam Araç Sayýsý: " + branchManager.count);
					table_branchCars.setModel(pCarModel);
					lblTotalCar.setText("Þubedeki Toplam Araç Sayýsý: " + table_branchCars.getRowCount());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_updateCar.setIcon(new ImageIcon(BranchManagerGUI.class.getResource("/View/update.png")));
		btn_updateCar.setForeground(new Color(144, 238, 144));
		btn_updateCar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_updateCar.setBackground(SystemColor.menu);
		btn_updateCar.setBounds(10, 276, 24, 24);
		panel_presentCar.add(btn_updateCar);
		
		lblTotalCar = new JLabel("");
		lblTotalCar.setText("Þubedeki Toplam Araç Sayýsý: " + table_branchCars.getRowCount());
		lblTotalCar.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalCar.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblTotalCar.setBounds(637, 276, 272, 20);
		panel_presentCar.add(lblTotalCar);
		
		lblTotalRental = new JLabel("");
		lblTotalRental.setText("Kiralanan Toplam Araç Sayýsý: " + branchManager.count);
		lblTotalRental.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalRental.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblTotalRental.setBounds(637, 307, 272, 20);
		panel_presentCar.add(lblTotalRental);
		
		JPanel panel_rentals = new JPanel();
		panel_rentals.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Tüm Kiralamalar", null, panel_rentals, null);
		panel_rentals.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 899, 464);
		panel_rentals.add(scrollPane_1);
		
		table_allRentals = new JTable(rentalModel){
			public boolean isCellEditable(int row, int column){  
		          return false;  
			}
		};
		table_allRentals.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_allRentals.getTableHeader().setReorderingAllowed(false);
		table_allRentals.getColumnModel().getColumn(0).setPreferredWidth(75);
		table_allRentals.getColumnModel().getColumn(1).setPreferredWidth(75);
		table_allRentals.getColumnModel().getColumn(2).setPreferredWidth(200);
		table_allRentals.getColumnModel().getColumn(3).setPreferredWidth(75);
		table_allRentals.getColumnModel().getColumn(4).setPreferredWidth(200);
		table_allRentals.getColumnModel().getColumn(5).setPreferredWidth(200);
		table_allRentals.getColumnModel().getColumn(6).setPreferredWidth(200);
		
		scrollPane_1.setViewportView(table_allRentals);
		
		String branchName = branchManager.getBranchName(branchManager.getId());
		JLabel lblBranchName = new JLabel( branchName + " Þubesi");
		lblBranchName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBranchName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblBranchName.setBounds(435, 11, 400, 24);
		w_contentPane.add(lblBranchName);
	}
}
