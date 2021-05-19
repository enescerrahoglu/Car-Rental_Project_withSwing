package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.jtattoo.plaf.aluminium.AluminiumLookAndFeel;

import Helper.DBConnection;
import Helper.Helper;
import Helper.Item;
import Model.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class AdminGUI extends JFrame {
	
	private final JFileChooser openFileChooser;
	@SuppressWarnings("unused")
	private BufferedImage originalBufferedImage;
	String imagePath;
	
	DBConnection conn = new DBConnection();
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;

	static Admin admin = new Admin();

	Branch branch = new Branch();
	BranchManager bManager = new BranchManager();
	Worker worker = new Worker();
	Car car = new Car();
	BranchCar branchCar = new BranchCar();

	private JPanel w_contentPane;
	private JTextField fld_smName;
	private JTextField fld_smEposta;
	private JTextField fld_mudurId;
	private JTable table_mudur;
	private DefaultTableModel mudurModel = null;
	private Object[] mudurData = null;
	private JTextField fld_smPass;
	private JTable table_branch;
	private JTextField fld_branchName;
	private DefaultTableModel branchModel = null;
	private Object[] branchData = null;
	private JPopupMenu branchMenu;
	private JTable table_worker;
	private JPopupMenu workerMenu;
	private JPopupMenu branchCarMenu;
	
	private DefaultTableModel workerModel = null;
	private Object[] workerData = null;
	
	private JTextField fld_brandName;
	private JTextField fld_modelName;
	private JTextField fld_modelYear;
	private JTextField fld_carId;
	private JTable table_car;
	private JTextField fld_dailyPrice;
	
	private DefaultTableModel carModel = null;
	private Object[] carData = null;
	
	private JTextField fld_color;
	
	private DefaultTableModel branchCarModel = null;
	private Object[] branchCarData = null;
	private JTable table_branchCar;

	JComboBox<Item> select_mudur;
	JComboBox<Item> select_car;
	
	JLabel lbl_image;
	private JTextField fld_path;
	
	JLabel lblTotalBranchManager;
	JLabel lblTotalBranch;
	private JTextField fld_search;
	
	JLabel lblResult;
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		Properties props = new Properties();
		props.put("logoString", "CoD");
		AluminiumLookAndFeel.setCurrentTheme(props);
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminGUI frame = new AdminGUI(admin);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdminGUI(Admin yonetici) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminGUI.class.getResource("/View/icon.png")));
		
		openFileChooser = new JFileChooser();
		openFileChooser.setCurrentDirectory(new File("C:\\Users\\enesc\\eclipse-workspace"));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "png", "jpg"));

		//BranchManager Model
		mudurModel = new DefaultTableModel();
		Object[] colMudur = new Object[4];
		colMudur[0] = "ID";
		colMudur[1] = "Ad Soyad";
		colMudur[2] = "E-posta";
		colMudur[3] = "Þifre";
		mudurModel.setColumnIdentifiers(colMudur);
		mudurData = new Object[4];
		for (int i = 0; i < yonetici.getSubeMuduruList().size(); i++) {
			String cryptedPassword = yonetici.getSubeMuduruList().get(i).getPassword();
			mudurData[0] = yonetici.getSubeMuduruList().get(i).getId();
			mudurData[1] = yonetici.getSubeMuduruList().get(i).getName();
			mudurData[2] = yonetici.getSubeMuduruList().get(i).getEposta();
			//mudurData[3] = yonetici.getSubeMuduruList().get(i).getPassword();
			mudurData[3] = new String(Base64.getDecoder().decode(cryptedPassword));
			mudurModel.addRow(mudurData);
		}

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
		
		//Worker Model
		workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[4];
		colWorker[0] = "Görev Id";
		colWorker[1] = "Müdür ID";
		colWorker[2] = "Ad Soyad";
		colWorker[3] = "E-posta";
		workerModel.setColumnIdentifiers(colWorker);
		workerData = new Object[4];
		
		//Car Model
		carModel = new DefaultTableModel();
		Object[] colCar = new Object[6];
		colCar[0] = "Araç ID";
		colCar[1] = "Marka Adý";
		colCar[2] = "Model Adý";
		colCar[3] = "Model Yýlý";
		colCar[4] = "Renk";
		colCar[5] = "Günlük Kira Ücreti";
		carModel.setColumnIdentifiers(colCar);
		carData = new Object[6];
		for (int i = 0; i < yonetici.getCarList().size(); i++) {
			carData[0] = yonetici.getCarList().get(i).getId();
			carData[1] = yonetici.getCarList().get(i).getBrandName();
			carData[2] = yonetici.getCarList().get(i).getModelName();
			carData[3] = yonetici.getCarList().get(i).getModelYear();
			carData[4] = yonetici.getCarList().get(i).getColor();
			carData[5] = yonetici.getCarList().get(i).getDailyPrice() + " TL";
			carModel.addRow(carData);
		}
		
		// BranchCar Model
		branchCarModel = new DefaultTableModel();
		Object[] colBranchCar = new Object[7];
		colBranchCar[0] = "Görev Id";
		colBranchCar[1] = "Araç ID";
		colBranchCar[2] = "Marka Adý";
		colBranchCar[3] = "Model Adý";
		colBranchCar[4] = "Model Yýlý";
		colBranchCar[5] = "Renk";
		colBranchCar[6] = "Günlük Kira Ücreti";
		branchCarModel.setColumnIdentifiers(colBranchCar);
		branchCarData = new Object[7];
		
		setTitle("CarOfDuty");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		w_contentPane = new JPanel();
		w_contentPane.setBackground(UIManager.getColor("activeCaption"));
		w_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(w_contentPane);
		w_contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Merhaba, " + yonetici.getName());
		lblNewLabel.setFont(new Font("Century Gothic", Font.ITALIC, 14));
		lblNewLabel.setBounds(10, 11, 400, 24);
		w_contentPane.add(lblNewLabel);

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
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnNewButton.setForeground(new Color(216, 191, 216));
		btnNewButton.setBackground(new Color(138, 43, 226));
		btnNewButton.setBounds(845, 11, 89, 24);
		w_contentPane.add(btnNewButton);

		JTabbedPane w_tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		w_tabbedPane.setBounds(10, 46, 924, 514);
		w_contentPane.add(w_tabbedPane);

		JPanel w_mudurPanel = new JPanel();
		w_mudurPanel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Müdür Yönetimi", null, w_mudurPanel, null);
		w_mudurPanel.setLayout(null);

		JLabel label1 = new JLabel("Ad Soyad");
		label1.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label1.setBounds(709, 39, 200, 20);
		w_mudurPanel.add(label1);

		fld_smName = new JTextField();
		fld_smName.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_smName.setBounds(709, 57, 200, 24);
		w_mudurPanel.add(fld_smName);
		fld_smName.setColumns(10);

		fld_smEposta = new JTextField();
		fld_smEposta.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_smEposta.setColumns(10);
		fld_smEposta.setBounds(709, 110, 200, 24);
		w_mudurPanel.add(fld_smEposta);

		JLabel label2 = new JLabel("E-posta");
		label2.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label2.setBounds(709, 92, 200, 20);
		w_mudurPanel.add(label2);

		JLabel lbl3 = new JLabel("Þifre");
		lbl3.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl3.setBounds(709, 145, 200, 20);
		w_mudurPanel.add(lbl3);

		JButton btnNewButton_1 = new JButton("Müdür Ekle");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_smName.getText().length() == 0 || fld_smEposta.getText().length() == 0 || fld_smPass.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						boolean control = addMudur(fld_smName.getText(), fld_smEposta.getText(), Base64.getEncoder().encodeToString(fld_smPass.getText().getBytes()));
						if (control) {
							Helper.showMsg("success");
							fld_smName.setText(null);
							fld_smEposta.setText(null);
							fld_smPass.setText(null);
							updateMudurModel();
							lblTotalBranchManager.setText("Toplam Müdür Sayýsý: " + table_mudur.getRowCount());
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setBackground(new Color(240, 255, 240));
		btnNewButton_1.setForeground(new Color(50, 205, 50));
		btnNewButton_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnNewButton_1.setBounds(709, 198, 200, 40);
		w_mudurPanel.add(btnNewButton_1);

		JLabel lblMdrId = new JLabel("Müdür ID");
		lblMdrId.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblMdrId.setBounds(709, 382, 200, 20);
		w_mudurPanel.add(lblMdrId);

		fld_mudurId = new JTextField();
		fld_mudurId.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_mudurId.setColumns(10);
		fld_mudurId.setBounds(709, 400, 200, 24);
		w_mudurPanel.add(fld_mudurId);

		JButton btnNewButton_1_1 = new JButton("Müdür Sil");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_mudurId.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir müdür seçiniz!");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_mudurId.getText());
						try {
							boolean control = deleteMudur(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_mudurId.setText(null);
								updateMudurModel();
								lblTotalBranchManager.setText("Toplam Müdür Sayýsý: " + table_mudur.getRowCount());
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton_1_1.setForeground(new Color(255, 0, 0));
		btnNewButton_1_1.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btnNewButton_1_1.setBackground(new Color(255, 228, 225));
		btnNewButton_1_1.setBounds(709, 435, 200, 40);
		w_mudurPanel.add(btnNewButton_1_1);

		fld_smPass = new JTextField();
		fld_smPass.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_smPass.setColumns(10);
		fld_smPass.setBounds(709, 163, 200, 24);
		w_mudurPanel.add(fld_smPass);

		JScrollPane w_scrollPaneMudur = new JScrollPane();
		w_scrollPaneMudur.setBounds(10, 39, 689, 405);
		w_mudurPanel.add(w_scrollPaneMudur);
		
		table_mudur = new JTable(mudurModel);
		table_mudur.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_mudur.getTableHeader().setReorderingAllowed(false);
		w_scrollPaneMudur.setViewportView(table_mudur);
		
		JLabel lblMML = new JLabel("Mevcut Müdür Listesi");
		lblMML.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblMML.setBounds(10, 11, 689, 28);
		w_mudurPanel.add(lblMML);
		
		lblTotalBranchManager = new JLabel("");
		lblTotalBranchManager.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalBranchManager.setText("Toplam Müdür Sayýsý: " + table_mudur.getRowCount());
		lblTotalBranchManager.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblTotalBranchManager.setBounds(10, 455, 689, 20);
		w_mudurPanel.add(lblTotalBranchManager);
		table_mudur.getColumnModel().getColumn(0).setPreferredWidth(25);
		table_mudur.getColumnModel().getColumn(1).setPreferredWidth(225);
		table_mudur.getColumnModel().getColumn(2).setPreferredWidth(225);
		table_mudur.getColumnModel().getColumn(3).setPreferredWidth(125);

		table_mudur.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_mudurId.setText(table_mudur.getValueAt(table_mudur.getSelectedRow(), 0).toString());
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		table_mudur.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectId = Integer.parseInt(table_mudur.getValueAt(table_mudur.getSelectedRow(), 0).toString());
					String selectName = table_mudur.getValueAt(table_mudur.getSelectedRow(), 1).toString();
					String selectEposta = table_mudur.getValueAt(table_mudur.getSelectedRow(), 2).toString();
					String cryptedPassword = Base64.getEncoder().encodeToString(table_mudur.getValueAt(table_mudur.getSelectedRow(), 3).toString().getBytes());
					try {
						updateMudur(selectId, selectName, selectEposta, cryptedPassword);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JPanel w_carPanel = new JPanel();
		w_carPanel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Araç Yönetimi", null, w_carPanel, null);
		w_carPanel.setLayout(null);
		
		JLabel label_brandName = new JLabel("Marka Adý");
		label_brandName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label_brandName.setBounds(709, 41, 200, 20);
		w_carPanel.add(label_brandName);
		
		fld_brandName = new JTextField();
		fld_brandName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fld_brandName.setText(fld_brandName.getText().toUpperCase());
			}
		});
		fld_brandName.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_brandName.setColumns(10);
		fld_brandName.setBounds(709, 59, 200, 24);
		w_carPanel.add(fld_brandName);
		
		JLabel label_modelName = new JLabel("Model Adý");
		label_modelName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label_modelName.setBounds(709, 89, 200, 20);
		w_carPanel.add(label_modelName);
		
		fld_modelName = new JTextField();
		fld_modelName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()!=KeyEvent.VK_BACK_SPACE) {
					fld_modelName.setText(fld_modelName.getText().substring(0, 1).toUpperCase() + fld_modelName.getText().substring(1));
				}
			}
		});
		fld_modelName.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_modelName.setColumns(10);
		fld_modelName.setBounds(709, 107, 200, 24);
		w_carPanel.add(fld_modelName);
		
		JLabel label_modelYear = new JLabel("Model Yýlý");
		label_modelYear.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label_modelYear.setBounds(709, 137, 200, 20);
		w_carPanel.add(label_modelYear);
		
		fld_modelYear = new JTextField();
		fld_modelYear.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					fld_modelYear.setEditable(false);
				}else {
					fld_modelYear.setEditable(true);
				}
			}
		});
		fld_modelYear.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_modelYear.setColumns(10);
		fld_modelYear.setBounds(709, 155, 200, 24);
		w_carPanel.add(fld_modelYear);
		
		JButton btn_addCar = new JButton("Araç Ekle");
		btn_addCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_brandName.getText().length() == 0 || fld_modelName.getText().length() == 0 || fld_modelYear.getText().length() == 0 || fld_color.getText().length() == 0 || fld_dailyPrice.getText().length() == 0) {
					Helper.showMsg("fill");
				}else if(fld_path.getText().length() == 0){
					Helper.showMsg("Lütfen bir resim seçiniz");
				}else {
					try {
						boolean control = addCar(fld_brandName.getText(), fld_modelName.getText(), fld_modelYear.getText(), fld_color.getText(), fld_dailyPrice.getText(), imagePath);
						if(control) {
							Helper.showMsg("success");
							fld_brandName.setText(null);
							fld_modelName.setText(null);
							fld_modelYear.setText(null);
							fld_color.setText(null);
							fld_dailyPrice.setText(null);
							fld_path.setText(null);
							updateCarModel();
							lblResult.setText("Sonuç: " + table_car.getRowCount());
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addCar.setForeground(new Color(50, 205, 50));
		btn_addCar.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btn_addCar.setBackground(new Color(240, 255, 240));
		btn_addCar.setBounds(709, 321, 200, 40);
		w_carPanel.add(btn_addCar);
		
		JLabel lbl_carId = new JLabel("Araç ID");
		lbl_carId.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_carId.setBounds(709, 382, 200, 20);
		w_carPanel.add(lbl_carId);
		
		fld_carId = new JTextField();
		fld_carId.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_carId.setColumns(10);
		fld_carId.setBounds(709, 400, 200, 24);
		w_carPanel.add(fld_carId);
		
		JButton btn_delCar = new JButton("Araç Sil");
		btn_delCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_carId.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir araç seçiniz!");
				}else {
					if(Helper.confirm("sure")) {
						int selectedId = Integer.parseInt(fld_carId.getText());
						try {
							boolean control = deleteCar(selectedId);
							if(control) {
								Helper.showMsg("success");
								fld_carId.setText(null);
								lbl_image.setIcon(null);
								updateCarModel();
								lblResult.setText("Sonuç: " + table_car.getRowCount());
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btn_delCar.setForeground(Color.RED);
		btn_delCar.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btn_delCar.setBackground(new Color(255, 228, 225));
		btn_delCar.setBounds(709, 435, 200, 40);
		w_carPanel.add(btn_delCar);
		
		JScrollPane w_scrollPaneCar = new JScrollPane();
		w_scrollPaneCar.setBounds(10, 39, 689, 236);
		w_carPanel.add(w_scrollPaneCar);
		
		table_car = new JTable(carModel);
		table_car.setFont(new Font("Tahoma", Font.PLAIN, 13));
		table_car.getTableHeader().setReorderingAllowed(false);
		table_car.getColumnModel().getColumn(0).setPreferredWidth(75);
		table_car.getColumnModel().getColumn(1).setPreferredWidth(200);
		table_car.getColumnModel().getColumn(2).setPreferredWidth(200);
		table_car.getColumnModel().getColumn(3).setPreferredWidth(200);
		table_car.getColumnModel().getColumn(4).setPreferredWidth(200);
		table_car.getColumnModel().getColumn(5).setPreferredWidth(200);
		w_scrollPaneCar.setViewportView(table_car);
		table_car.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_carId.setText(table_car.getValueAt(table_car.getSelectedRow(), 0).toString());
					
					String sql = "SELECT image FROM cars WHERE id = '" + fld_carId.getText() + "' ";
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
		
		table_car.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				if(e.getType() == TableModelEvent.UPDATE) {
					int selectId = Integer.parseInt(table_car.getValueAt(table_car.getSelectedRow(), 0).toString());
					String selectBrandName = table_car.getValueAt(table_car.getSelectedRow(), 1).toString();
					String selectModelName = table_car.getValueAt(table_car.getSelectedRow(), 2).toString();
					String selectModelYear = table_car.getValueAt(table_car.getSelectedRow(), 3).toString();
					String selectColor = table_car.getValueAt(table_car.getSelectedRow(), 4).toString();
					String selectDailyPrice = table_car.getValueAt(table_car.getSelectedRow(), 5).toString().replace(" TL", "");
					try {
						updateCar(selectId, selectBrandName, selectModelName, selectModelYear, selectColor, selectDailyPrice);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		JLabel label_dailyPrice = new JLabel("Günlük Kira Ücreti");
		label_dailyPrice.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label_dailyPrice.setBounds(709, 233, 170, 20);
		w_carPanel.add(label_dailyPrice);
		
		fld_dailyPrice = new JTextField();
		fld_dailyPrice.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				if(Character.isLetter(c)) {
					fld_dailyPrice.setEditable(false);
				}else if(Character.isSpace(c)){
					fld_dailyPrice.setEditable(false);
				}else {
					fld_dailyPrice.setEditable(true);
				}
			}
		});
		fld_dailyPrice.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_dailyPrice.setColumns(10);
		fld_dailyPrice.setBounds(709, 251, 170, 24);
		w_carPanel.add(fld_dailyPrice);
		
		JLabel label_color = new JLabel("Renk");
		label_color.setFont(new Font("Century Gothic", Font.BOLD, 14));
		label_color.setBounds(709, 185, 200, 20);
		w_carPanel.add(label_color);
		
		fld_color = new JTextField();
		fld_color.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()!=KeyEvent.VK_BACK_SPACE) {
					fld_color.setText(fld_color.getText().substring(0, 1).toUpperCase() + fld_color.getText().substring(1));
				}
			}
		});
		fld_color.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_color.setColumns(10);
		fld_color.setBounds(709, 203, 200, 24);
		w_carPanel.add(fld_color);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(10, 286, 336, 189);
		w_carPanel.add(desktopPane);
		
		lbl_image = new JLabel("");
		lbl_image.setIcon(new ImageIcon(getClass().getResource("default.png")));
		lbl_image.setBounds(0, 0, 336, 189);
		desktopPane.add(lbl_image);
		
		JButton btn_selectImage = new JButton("Resim Seç");
		btn_selectImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue = openFileChooser.showOpenDialog(btn_selectImage);
				
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					try {
						originalBufferedImage = ImageIO.read(openFileChooser.getSelectedFile());
						imagePath = openFileChooser.getSelectedFile().getPath();
						fld_path.setText(imagePath);
						
						ImageIcon myImage = new ImageIcon(imagePath);
						Image img = myImage.getImage();
						Image newImage = img.getScaledInstance(lbl_image.getWidth(), lbl_image.getHeight(), Image.SCALE_SMOOTH);
						ImageIcon image = new ImageIcon(newImage);
						lbl_image.setIcon(image);
						
						imagePath = imagePath.replace("\\","\\\\");
					} catch (IOException ioe) {
						JOptionPane.showMessageDialog(null, ioe);
					}
				}else {
					
				}
			}
		});
		btn_selectImage.setBackground(new Color(135, 206, 250));
		btn_selectImage.setForeground(new Color(0, 0, 255));
		btn_selectImage.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btn_selectImage.setBounds(709, 286, 200, 24);
		w_carPanel.add(btn_selectImage);
		
		fld_path = new JTextField();
		fld_path.setEditable(false);
		fld_path.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_path.setBounds(356, 286, 343, 24);
		w_carPanel.add(fld_path);
		fld_path.setColumns(10);
		
		JButton btn_updateImage = new JButton("Resmi Güncelle");
		btn_updateImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fld_carId.getText().length() == 0) {
					Helper.showMsg("Lütfen bir araç seçiniz!");
				}else if(fld_path.getText().length() == 0) {
					Helper.showMsg("Yeni bir resim seçiniz!");
				}else {
					String newImagePath = fld_path.getText();
					int id = Integer.parseInt(fld_carId.getText());
					try {
						yonetici.updateImage(id, newImagePath);
						fld_path.setText(null);
					} catch (FileNotFoundException | SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updateImage.setForeground(new Color(255, 127, 80));
		btn_updateImage.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btn_updateImage.setBackground(new Color(245, 222, 179));
		btn_updateImage.setBounds(356, 321, 150, 24);
		w_carPanel.add(btn_updateImage);
		
		JLabel lblTL = new JLabel("TL");
		lblTL.setForeground(new Color(34, 139, 34));
		lblTL.setHorizontalAlignment(SwingConstants.CENTER);
		lblTL.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblTL.setBounds(879, 251, 26, 24);
		w_carPanel.add(lblTL);
		
		JLabel lblMAL = new JLabel("Mevcut Araç Listesi");
		lblMAL.setFont(new Font("Century Gothic", Font.BOLD, 20));
		lblMAL.setBounds(10, 11, 200, 28);
		w_carPanel.add(lblMAL);
		
		JButton btn_updateCar = new JButton("");
		btn_updateCar.setBounds(885, 11, 24, 24);
		w_carPanel.add(btn_updateCar);
		btn_updateCar.setIcon(new ImageIcon(AdminGUI.class.getResource("/View/update.png")));
		btn_updateCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DefaultTableModel clearModel = (DefaultTableModel) table_car.getModel();
					clearModel.setRowCount(0);
					for (int i = 0; i < yonetici.getCarList().size(); i++) {
						carData[0] = yonetici.getCarList().get(i).getId();
						carData[1] = yonetici.getCarList().get(i).getBrandName();
						carData[2] = yonetici.getCarList().get(i).getModelName();
						carData[3] = yonetici.getCarList().get(i).getModelYear();
						carData[4] = yonetici.getCarList().get(i).getColor();
						carData[5] = yonetici.getCarList().get(i).getDailyPrice() + " TL";
						carModel.addRow(carData);
					}
					table_car.setModel(carModel);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_updateCar.setForeground(new Color(144, 238, 144));
		btn_updateCar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn_updateCar.setBackground(UIManager.getColor("Button.background"));
		
		JLabel lblSearchIcon = new JLabel(new ImageIcon(getClass().getResource("search.png")));
		lblSearchIcon.setBounds(441, 11, 24, 24);
		w_carPanel.add(lblSearchIcon);
		
		fld_search = new JTextField();
		fld_search.setForeground(new Color(255, 140, 0));
		fld_search.setBackground(new Color(250, 240, 230));
		fld_search.setFont(new Font("Arial", Font.PLAIN, 16));
		fld_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				DefaultTableModel table = (DefaultTableModel) table_car.getModel();
				String search = fld_search.getText();
				TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
				table_car.setRowSorter(tr);
				tr.setRowFilter(RowFilter.regexFilter("(?i)" + search));
				lblResult.setText("Sonuç: " + table_car.getRowCount());
			}
		});
		fld_search.setColumns(10);
		fld_search.setBounds(465, 11, 234, 24);
		w_carPanel.add(fld_search);
		
		lblResult = new JLabel("");
		lblResult.setForeground(new Color(255, 0, 0));
		lblResult.setFont(new Font("Arial", Font.PLAIN, 12));
		lblResult.setText("Sonuç: " + table_car.getRowCount());
		lblResult.setBounds(709, 11, 170, 24);
		w_carPanel.add(lblResult);

		JPanel w_branchPanel = new JPanel();
		w_branchPanel.setBackground(UIManager.getColor("activeCaption"));
		w_tabbedPane.addTab("Þube Yönetimi", null, w_branchPanel, null);
		w_branchPanel.setLayout(null);

		JScrollPane w_scrollBranch = new JScrollPane();
		w_scrollBranch.setBounds(370, 34, 285, 185);
		w_branchPanel.add(w_scrollBranch);

		branchMenu = new JPopupMenu();
		JMenuItem updateBranchMenu = new JMenuItem("Güncelle");
		JMenuItem deleteBranchMenu = new JMenuItem("Sil");
		branchMenu.add(updateBranchMenu);
		branchMenu.add(deleteBranchMenu);

		updateBranchMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedId = Integer.parseInt(table_branch.getValueAt(table_branch.getSelectedRow(), 0).toString());
				Branch selectedBranch = branch.getFetch(selectedId);
				UpdateBranchGUI updateGUI = new UpdateBranchGUI(selectedBranch);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.setLocationRelativeTo(null);
				updateGUI.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateBranchModel();
							lblTotalBranch.setText("Toplam Þube Sayýsý: " + table_branch.getRowCount());
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}
		});

		deleteBranchMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (Helper.confirm("sure")) {
					int selectedId = Integer.parseInt(table_branch.getValueAt(table_branch.getSelectedRow(), 0).toString());
					try {
						branch.deleteBranch(selectedId);
						Helper.showMsg("success");
						updateBranchModel();
						select_mudur.removeAllItems();
						select_car.removeAllItems();
						for(int i = 0; i < yonetici.getFreeSubeMuduruList().size(); i++) {
							select_mudur.addItem(new Item(yonetici.getFreeSubeMuduruList().get(i).getId(), yonetici.getFreeSubeMuduruList().get(i).getName()));
						}
						for(int i = 0; i < yonetici.getFreeCarList().size(); i++) {
							select_car.addItem(new Item(yonetici.getFreeCarList().get(i).getId(), yonetici.getFreeCarList().get(i).getBrandName() + " " + yonetici.getFreeCarList().get(i).getModelName() + " / " + yonetici.getFreeCarList().get(i).getModelYear() + " / " + yonetici.getFreeCarList().get(i).getColor()));
						}
						DefaultTableModel clearModel1 = (DefaultTableModel) table_worker.getModel();
						clearModel1.setRowCount(0);
						DefaultTableModel clearModel2 = (DefaultTableModel) table_branchCar.getModel();
						clearModel2.setRowCount(0);
						lblTotalBranch.setText("Toplam Þube Sayýsý: " + table_branch.getRowCount());
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					/*
					 
					try {
						if (branch.deleteBranch(selectedId)) {
							Helper.showMsg("success");
							updateBranchModel();
							select_mudur.removeAllItems();
							select_car.removeAllItems();
							for(int i = 0; i < yonetici.getFreeSubeMuduruList().size(); i++) {
								select_mudur.addItem(new Item(yonetici.getFreeSubeMuduruList().get(i).getId(), yonetici.getFreeSubeMuduruList().get(i).getName()));
							}
							for(int i = 0; i < yonetici.getFreeCarList().size(); i++) {
								select_car.addItem(new Item(yonetici.getFreeCarList().get(i).getId(), yonetici.getFreeCarList().get(i).getBrandName() + " " + yonetici.getFreeCarList().get(i).getModelName() + " / " + yonetici.getFreeCarList().get(i).getModelYear() + " / " + yonetici.getFreeCarList().get(i).getColor()));
							}
							DefaultTableModel clearModel1 = (DefaultTableModel) table_worker.getModel();
							clearModel1.setRowCount(0);
							DefaultTableModel clearModel2 = (DefaultTableModel) table_branchCar.getModel();
							clearModel2.setRowCount(0);
							lblTotalBranch.setText("Toplam Þube Sayýsý: " + table_branch.getRowCount());
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					 */
				}
			}
		});

		table_branch = new JTable(branchModel){
			public boolean isCellEditable(int row, int column){  
		          return false;
			}
		};
		table_branch.setFont(new Font("Arial", Font.PLAIN, 12));
		table_branch.getTableHeader().setReorderingAllowed(false);
		table_branch.getColumnModel().getColumn(0).setPreferredWidth(20);
		table_branch.getColumnModel().getColumn(1).setPreferredWidth(200);
		table_branch.setComponentPopupMenu(branchMenu);
		table_branch.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_branch.rowAtPoint(point);
				table_branch.setRowSelectionInterval(selectedRow, selectedRow);
				
				int selectedBranchRow = table_branch.getSelectedRow();
				if(selectedBranchRow >= 0) {
					String selectedBranch = table_branch.getModel().getValueAt(selectedBranchRow, 0).toString();
					int selectedBranchId = Integer.parseInt(selectedBranch);
					DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
					clearModel.setRowCount(0);
					try {
						for(int i = 0; i < yonetici.getBranchMudurList(selectedBranchId).size(); i++) {
							workerData[0] = yonetici.getWorkerList(selectedBranchId).get(i).getId();
							workerData[1] = yonetici.getBranchMudurList(selectedBranchId).get(i).getId();
							workerData[2] = yonetici.getBranchMudurList(selectedBranchId).get(i).getName();
							workerData[3] = yonetici.getBranchMudurList(selectedBranchId).get(i).getEposta();
							workerModel.addRow(workerData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_worker.setModel(workerModel);
				}else {
					Helper.showMsg("Bir þube seçin!");
				}
				
				int selectedCarRow = table_branch.getSelectedRow();
				if(selectedCarRow >= 0) {
					String selectedBranch = table_branch.getModel().getValueAt(selectedCarRow, 0).toString();
					int selectedBranchId = Integer.parseInt(selectedBranch);
					DefaultTableModel clearModel = (DefaultTableModel) table_branchCar.getModel();
					clearModel.setRowCount(0);
					
					try {
						for(int i = 0; i < yonetici.getBranchAllCarList(selectedBranchId).size(); i++) {
							branchCarData[0] = yonetici.getBranchCarList(selectedBranchId).get(i).getId();
							branchCarData[1] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getId();
							branchCarData[2] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getBrandName();
							branchCarData[3] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getModelName();
							branchCarData[4] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getModelYear();
							branchCarData[5] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getColor();
							branchCarData[6] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getDailyPrice() + " TL";
							branchCarModel.addRow(branchCarData);
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					table_branchCar.setModel(branchCarModel);
				}else {
					Helper.showMsg("Bir þube seçin!");
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				table_worker.getColumnModel().getColumn(0).setPreferredWidth(70);
				table_worker.getColumnModel().getColumn(1).setPreferredWidth(70);
				table_worker.getColumnModel().getColumn(2).setPreferredWidth(125);
				table_worker.getColumnModel().getColumn(3).setPreferredWidth(125);
				
				table_branchCar.getColumnModel().getColumn(0).setPreferredWidth(70);
				table_branchCar.getColumnModel().getColumn(1).setPreferredWidth(70);
				table_branchCar.getColumnModel().getColumn(2).setPreferredWidth(125);
				table_branchCar.getColumnModel().getColumn(3).setPreferredWidth(125);
				table_branchCar.getColumnModel().getColumn(4).setPreferredWidth(125);
				table_branchCar.getColumnModel().getColumn(5).setPreferredWidth(125);
				table_branchCar.getColumnModel().getColumn(6).setPreferredWidth(125);
			}
		});
		w_scrollBranch.setViewportView(table_branch);

		JLabel lbl_branchName = new JLabel("Þube Adý");
		lbl_branchName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_branchName.setBounds(665, 11, 244, 20);
		w_branchPanel.add(lbl_branchName);

		fld_branchName = new JTextField();
		fld_branchName.setFont(new Font("Arial", Font.PLAIN, 12));
		fld_branchName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fld_branchName.setText(fld_branchName.getText().toUpperCase());
			}
		});
		fld_branchName.setColumns(10);
		fld_branchName.setBounds(665, 34, 244, 24);
		w_branchPanel.add(fld_branchName);

		JButton btn_addBranch = new JButton("Þube Ekle");
		btn_addBranch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_branchName.getText().length() == 0) {
					Helper.showMsg("Lütfen yeni þubenin adýný giriniz.");
				} else {
					try {
						if (branch.addBranch(fld_branchName.getText())) {
							Helper.showMsg("success");
							fld_branchName.setText(null);
							updateBranchModel();
							lblTotalBranch.setText("Toplam Þube Sayýsý: " + table_branch.getRowCount());
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addBranch.setForeground(new Color(50, 205, 50));
		btn_addBranch.setFont(new Font("Century Gothic", Font.BOLD, 18));
		btn_addBranch.setBackground(new Color(240, 255, 240));
		btn_addBranch.setBounds(665, 69, 244, 40);
		w_branchPanel.add(btn_addBranch);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(10, 34, 350, 185);
		w_branchPanel.add(w_scrollWorker);
		
		workerMenu = new JPopupMenu();
		JMenuItem deleteWorkerMenu = new JMenuItem("Görevden Al");
		workerMenu.add(deleteWorkerMenu);
		
		deleteWorkerMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					int selectedId = Integer.parseInt(table_worker.getValueAt(table_worker.getSelectedRow(), 0).toString());
					try {
						if(yonetici.deleteWorker(selectedId)) {
							Helper.showMsg("success");
							updateWorkerModel();
							select_mudur.setModel(new DefaultComboBoxModel<Item>());
							for(int i = 0; i < yonetici.getFreeSubeMuduruList().size(); i++) {
								select_mudur.addItem(new Item(yonetici.getFreeSubeMuduruList().get(i).getId(), yonetici.getFreeSubeMuduruList().get(i).getName()));
							}
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		table_worker = new JTable(){
			public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		};
		table_worker.setFont(new Font("Arial", Font.PLAIN, 12));
		table_worker.getTableHeader().setReorderingAllowed(false);
		table_worker.setComponentPopupMenu(workerMenu);
		table_worker.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_worker.rowAtPoint(point);
				table_worker.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		w_scrollWorker.setViewportView(table_worker);
		
		select_mudur = new JComboBox();
		select_mudur.setFont(new Font("Arial", Font.PLAIN, 12));
		select_mudur.setBounds(665, 144, 244, 24);
		for(int i = 0; i < yonetici.getFreeSubeMuduruList().size(); i++) {
			select_mudur.addItem(new Item(yonetici.getFreeSubeMuduruList().get(i).getId(), yonetici.getFreeSubeMuduruList().get(i).getName()));
		}
		select_mudur.addActionListener(e ->{
			JComboBox c = (JComboBox) e.getSource();
			@SuppressWarnings("unused")
			Item item = (Item) c.getSelectedItem();
			//System.out.println(item.getKey() + " : " + item.getValue());
		});
		w_branchPanel.add(select_mudur);
		
		JButton btn_addWorker = new JButton("Þube Müdürü Ata");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_branch.getSelectedRow();
				if(selectedRow >= 0) {
					if(select_mudur.getItemCount() == 0) {
						Object[] options = {"Tamam"};
			        	JOptionPane.showOptionDialog(rootPane, "Yeni müdür ekleyin.", "Müdür bulunamadý!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					}else {
						String selectedBranch = table_branch.getModel().getValueAt(selectedRow, 0).toString();
						int selectedBranchId = Integer.parseInt(selectedBranch);
						Item MudurItem = (Item) select_mudur.getSelectedItem();
						try {
							boolean control = yonetici.addWorker(MudurItem.getKey(), selectedBranchId);
							if(control) {
								Helper.showMsg("success");
								DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
								clearModel.setRowCount(0);
								for(int i = 0; i < yonetici.getBranchMudurList(selectedBranchId).size(); i++) {
									workerData[0] = yonetici.getWorkerList(selectedBranchId).get(i).getId();
									workerData[1] = yonetici.getBranchMudurList(selectedBranchId).get(i).getId();
									workerData[2] = yonetici.getBranchMudurList(selectedBranchId).get(i).getName();
									workerData[3] = yonetici.getBranchMudurList(selectedBranchId).get(i).getEposta();
									workerModel.addRow(workerData);
								}
								table_worker.setModel(workerModel);
								select_mudur.setModel(new DefaultComboBoxModel<Item>());
								for(int i = 0; i < yonetici.getFreeSubeMuduruList().size(); i++) {
									select_mudur.addItem(new Item(yonetici.getFreeSubeMuduruList().get(i).getId(), yonetici.getFreeSubeMuduruList().get(i).getName()));
								}
							}else {
								Helper.showMsg("Bu þubede görevli bir müdür zaten var!");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}else if(table_branch.getModel().getRowCount() == 0) {
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Müdür atanacak þube bulunamadý!", "Þube bulunamadý!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}else {
					Helper.showMsg("Bir þube seçin!");
				}
			}
		});
		btn_addWorker.setForeground(new Color(30, 144, 255));
		btn_addWorker.setFont(new Font("Century Gothic", Font.BOLD, 18));
		btn_addWorker.setBackground(new Color(175, 238, 238));
		btn_addWorker.setBounds(665, 179, 244, 40);
		w_branchPanel.add(btn_addWorker);
		
		JLabel lbl_mudurSec = new JLabel("Müdür Seç");
		lbl_mudurSec.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_mudurSec.setBounds(665, 121, 244, 20);
		w_branchPanel.add(lbl_mudurSec);
		
		JLabel lblMS = new JLabel("Mevcut Þubeler");
		lblMS.setHorizontalAlignment(SwingConstants.CENTER);
		lblMS.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblMS.setBounds(370, 11, 285, 20);
		w_branchPanel.add(lblMS);
		
		JLabel lblSGMH = new JLabel("Þubede Görevli Müdür Hakkýnda");
		lblSGMH.setHorizontalAlignment(SwingConstants.CENTER);
		lblSGMH.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblSGMH.setBounds(10, 11, 350, 20);
		w_branchPanel.add(lblSGMH);
		
		JLabel lbl_aracSec = new JLabel("Araç Seç");
		lbl_aracSec.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_aracSec.setBounds(665, 230, 244, 20);
		w_branchPanel.add(lbl_aracSec);
		
		select_car = new JComboBox();
		select_car.setFont(new Font("Arial", Font.PLAIN, 12));
		select_car.setBounds(665, 253, 244, 24);
		for(int i = 0; i < yonetici.getFreeCarList().size(); i++) {
			select_car.addItem(new Item(yonetici.getFreeCarList().get(i).getId(), yonetici.getFreeCarList().get(i).getBrandName() + " " + yonetici.getFreeCarList().get(i).getModelName() + " / " + yonetici.getFreeCarList().get(i).getModelYear() + " / " + yonetici.getFreeCarList().get(i).getColor()));
		}
		select_car.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			@SuppressWarnings("unused")
			Item item = (Item) c.getSelectedItem();
		});
		w_branchPanel.add(select_car);
		
		JButton btn_addBranchCar = new JButton("Þubeye Araç Ekle");
		btn_addBranchCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table_branch.getSelectedRow();
				if(selectedRow >= 0) {
					if(select_car.getItemCount() == 0) {
						Object[] options = {"Tamam"};
			        	JOptionPane.showOptionDialog(rootPane, "Yeni araç ekleyin.", "Araç bulunamadý!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
					}else {
						String selectedBranch = table_branch.getModel().getValueAt(selectedRow, 0).toString();
						int selectedBranchId = Integer.parseInt(selectedBranch);
						Item CarItem = (Item) select_car.getSelectedItem();
						try {
							boolean control = yonetici.addBranchCar(CarItem.getKey(), selectedBranchId);
							if(control) {
								Helper.showMsg("success");
								DefaultTableModel clearModel = (DefaultTableModel) table_branchCar.getModel();
								clearModel.setRowCount(0);
								for(int i = 0; i < yonetici.getBranchAllCarList(selectedBranchId).size(); i++) {
									branchCarData[0] = yonetici.getBranchCarList(selectedBranchId).get(i).getId();
									branchCarData[1] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getId();
									branchCarData[2] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getBrandName();
									branchCarData[3] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getModelName();
									branchCarData[4] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getModelYear();
									branchCarData[5] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getColor();
									branchCarData[6] = yonetici.getBranchAllCarList(selectedBranchId).get(i).getDailyPrice() + " TL";
									branchCarModel.addRow(branchCarData);
								}
								table_branchCar.setModel(branchCarModel);
								select_car.setModel(new DefaultComboBoxModel<Item>());
								for(int i = 0; i < yonetici.getFreeCarList().size(); i++) {
									select_car.addItem(new Item(yonetici.getFreeCarList().get(i).getId(), yonetici.getFreeCarList().get(i).getBrandName() + " " + yonetici.getFreeCarList().get(i).getModelName() + " / " + yonetici.getFreeCarList().get(i).getModelYear() + " / " + yonetici.getFreeCarList().get(i).getColor()));
								}
							}else {
								Helper.showMsg("Seçilen araç daha önce bir þubeye eklendi!");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}else if(table_branch.getModel().getRowCount() == 0) {
					Object[] options = {"Tamam"};
		        	JOptionPane.showOptionDialog(rootPane, "Araç eklenecek þube bulunamadý!", "Þube bulunamadý!", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
				}else {
					Helper.showMsg("Bir þube seçin!");
				}
			}
		});
		btn_addBranchCar.setForeground(new Color(30, 144, 255));
		btn_addBranchCar.setFont(new Font("Century Gothic", Font.BOLD, 18));
		btn_addBranchCar.setBackground(new Color(175, 238, 238));
		btn_addBranchCar.setBounds(665, 288, 244, 40);
		w_branchPanel.add(btn_addBranchCar);
		
		JLabel lblSAL = new JLabel("Þubedeki Araç Listesi");
		lblSAL.setHorizontalAlignment(SwingConstants.CENTER);
		lblSAL.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblSAL.setBounds(10, 229, 645, 20);
		w_branchPanel.add(lblSAL);
		
		JScrollPane w_scrollBranchCar = new JScrollPane();
		w_scrollBranchCar.setBounds(10, 252, 645, 223);
		w_branchPanel.add(w_scrollBranchCar);
		
		branchCarMenu = new JPopupMenu();
		JMenuItem deleteBranchCarMenu = new JMenuItem("Þubeden Çýkar");
		branchCarMenu.add(deleteBranchCarMenu);
		
		deleteBranchCarMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					int selectedId = Integer.parseInt(table_branchCar.getValueAt(table_branchCar.getSelectedRow(), 0).toString());
					try {
						if(branchCar.deleteBranchCar(selectedId)) {
							Helper.showMsg("success");
							updateBranchCarModel();
							select_car.setModel(new DefaultComboBoxModel<Item>());
							for(int i = 0; i < yonetici.getFreeCarList().size(); i++) {
								select_car.addItem(new Item(yonetici.getFreeCarList().get(i).getId(), yonetici.getFreeCarList().get(i).getBrandName() + " " + yonetici.getFreeCarList().get(i).getModelName() + " / " + yonetici.getFreeCarList().get(i).getModelYear() + " / " + yonetici.getFreeCarList().get(i).getColor()));
							}
						}else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		table_branchCar = new JTable(){
			public boolean isCellEditable(int row, int column){  
		          return false;  
			}
		};
		table_branchCar.setFont(new Font("Arial", Font.PLAIN, 12));
		table_branchCar.getTableHeader().setReorderingAllowed(false);
		table_branchCar.setComponentPopupMenu(branchCarMenu);
		table_branchCar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_branchCar.rowAtPoint(point);
				table_branchCar.setRowSelectionInterval(selectedRow, selectedRow);
			}
		});
		w_scrollBranchCar.setViewportView(table_branchCar);
		
		lblTotalBranch = new JLabel("");
		lblTotalBranch.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalBranch.setText("Toplam Þube Sayýsý: " + table_branch.getRowCount());
		lblTotalBranch.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		lblTotalBranch.setBounds(665, 455, 244, 20);
		w_branchPanel.add(lblTotalBranch);
		
		JLabel lblAdminIcon = new JLabel(new ImageIcon(getClass().getResource("admin.png")));
		lblAdminIcon.setBounds(452, 11, 40, 40);
		w_contentPane.add(lblAdminIcon);
		
		
	}
	
	public boolean addMudur(String name, String eposta, String password) throws SQLException {
		
		String query = "INSERT INTO branchmanagers" + "(name, eposta, password, type) VALUES" + "(?,?,?,?)";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, eposta);
			preparedStatement.setString(3, password);
			preparedStatement.setString(4, "sube muduru");
			preparedStatement.executeUpdate();
			select_mudur.setModel(new DefaultComboBoxModel<Item>());
			for(int i = 0; i < admin.getFreeSubeMuduruList().size(); i++) {
				select_mudur.addItem(new Item(admin.getFreeSubeMuduruList().get(i).getId(), admin.getFreeSubeMuduruList().get(i).getName()));
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else
			return false;
	}
	
	public boolean deleteMudur(int id) throws SQLException {
		
		String query = "DELETE branchmanagers, workers FROM branchmanagers LEFT JOIN workers ON branchmanagers.id = workers.bManagerId WHERE branchmanagers.id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			select_mudur.setModel(new DefaultComboBoxModel<Item>());
			for(int i = 0; i < admin.getFreeSubeMuduruList().size(); i++) {
				select_mudur.addItem(new Item(admin.getFreeSubeMuduruList().get(i).getId(), admin.getFreeSubeMuduruList().get(i).getName()));
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else
			return false;
	}
	
	public boolean updateMudur(int id, String name, String eposta, String password) throws SQLException {
		
		String query = "UPDATE branchmanagers SET name = ?, eposta = ?, password = ? WHERE id = ?";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, eposta);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, id);
			preparedStatement.executeUpdate();
			select_mudur.setModel(new DefaultComboBoxModel<Item>());
			for(int i = 0; i < admin.getFreeSubeMuduruList().size(); i++) {
				select_mudur.addItem(new Item(admin.getFreeSubeMuduruList().get(i).getId(), admin.getFreeSubeMuduruList().get(i).getName()));
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else 
			return false;
	}
	
	public boolean addCar(String brandName, String modelName, String modelYear, String color, String dailyPrice, String path) throws SQLException, FileNotFoundException {
		
		String query = "INSERT INTO cars(brandName, modelName, modelYear, color, dailyPrice, image) VALUES(?,?,?,?,?,?)";	
		boolean key = false;
		File file = new File(path);
		FileInputStream inputStream = new FileInputStream(file);
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, brandName);
			preparedStatement.setString(2, modelName);
			preparedStatement.setString(3, modelYear);
			preparedStatement.setString(4, color);
			preparedStatement.setString(5, dailyPrice);
			preparedStatement.setBlob(6, inputStream);
			preparedStatement.executeUpdate();
			select_car.setModel(new DefaultComboBoxModel<Item>());
			for(int i = 0; i < admin.getFreeCarList().size(); i++) {
				select_car.addItem(new Item(admin.getFreeCarList().get(i).getId(), admin.getFreeCarList().get(i).getBrandName() + " " + admin.getFreeCarList().get(i).getModelName() + " / " + admin.getFreeCarList().get(i).getModelYear() + " / " + admin.getFreeCarList().get(i).getColor()));
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else
			return false;
	}
	
	public boolean deleteCar(int id) throws SQLException {
		
		String query = "DELETE cars, branchcars, rentaltransactions FROM cars LEFT JOIN branchcars ON cars.id = branchcars.carId LEFT JOIN rentaltransactions ON cars.id = rentaltransactions.carId WHERE cars.id = ?";
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			select_car.setModel(new DefaultComboBoxModel<Item>());
			for(int i = 0; i < admin.getFreeCarList().size(); i++) {
				select_car.addItem(new Item(admin.getFreeCarList().get(i).getId(), admin.getFreeCarList().get(i).getBrandName() + " " + admin.getFreeCarList().get(i).getModelName() + " / " + admin.getFreeCarList().get(i).getModelYear() + " / " + admin.getFreeCarList().get(i).getColor()));
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else
			return false;
	}
	
	public boolean updateCar(int id, String brandName, String modelName, String modelYear, String color, String dailyPrice) throws SQLException {
		
		String query = "UPDATE cars SET brandName = ?, modelName = ?, modelYear = ?, color = ?, dailyPrice = ? WHERE id = ?";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, brandName);
			preparedStatement.setString(2, modelName);
			preparedStatement.setString(3, modelYear);
			preparedStatement.setString(4, color);
			preparedStatement.setString(5, dailyPrice);
			preparedStatement.setInt(6, id);
			preparedStatement.executeUpdate();
			select_car.setModel(new DefaultComboBoxModel<Item>());
			for(int i = 0; i < admin.getFreeCarList().size(); i++) {
				select_car.addItem(new Item(admin.getFreeCarList().get(i).getId(), admin.getFreeCarList().get(i).getBrandName() + " " + admin.getFreeCarList().get(i).getModelName() + " / " + admin.getFreeCarList().get(i).getModelYear() + " / " + admin.getFreeCarList().get(i).getColor()));
			}
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else 
			return false;
	}

	public void updateMudurModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_mudur.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < admin.getSubeMuduruList().size(); i++) {
			String cryptedPassword = admin.getSubeMuduruList().get(i).getPassword();
			mudurData[0] = admin.getSubeMuduruList().get(i).getId();
			mudurData[1] = admin.getSubeMuduruList().get(i).getName();
			mudurData[2] = admin.getSubeMuduruList().get(i).getEposta();
			mudurData[3] = new String(Base64.getDecoder().decode(cryptedPassword));
			mudurModel.addRow(mudurData);
		}

	}

	public void updateBranchModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_branch.getModel();
		clearModel.setRowCount(0);

		for (int i = 0; i < branch.getBranchList().size(); i++) {
			branchData[0] = branch.getBranchList().get(i).getId();
			branchData[1] = branch.getBranchList().get(i).getName();
			branchModel.addRow(branchData);
		}

	}
	
	public void updateWorkerModel() throws SQLException {
		
		DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < admin.getWorkerList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).size(); i++) {
			try {
				workerData[0] = admin.getWorkerList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getId();
				workerData[1] = admin.getBranchMudurList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getId();
				workerData[2] = admin.getBranchMudurList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getName();
				workerData[3] = admin.getBranchMudurList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getEposta();
				workerModel.addRow(workerData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		table_worker.setModel(workerModel);
	}
	
	public void updateCarModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_car.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < admin.getCarList().size(); i++) {
			carData[0] = admin.getCarList().get(i).getId();
			carData[1] = admin.getCarList().get(i).getBrandName();
			carData[2] = admin.getCarList().get(i).getModelName();
			carData[3] = admin.getCarList().get(i).getModelYear();
			carData[4] = admin.getCarList().get(i).getColor();
			carData[5] = admin.getCarList().get(i).getDailyPrice() + " TL";
			carModel.addRow(carData);
		}
	}
	
	public void updateBranchCarModel() throws SQLException {
		
		DefaultTableModel clearModel = (DefaultTableModel) table_branchCar.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < admin.getBranchCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).size(); i++) {
			try {
				branchCarData[0] = admin.getBranchCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getId();
				branchCarData[1] = admin.getBranchAllCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getId();
				branchCarData[2] = admin.getBranchAllCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getBrandName();
				branchCarData[3] = admin.getBranchAllCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getModelName();
				branchCarData[4] = admin.getBranchAllCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getModelYear();
				branchCarData[5] = admin.getBranchAllCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getColor();
				branchCarData[6] = admin.getBranchAllCarList(Integer.parseInt(table_branch.getModel().getValueAt(table_branch.getSelectedRow(), 0).toString())).get(i).getDailyPrice() + " TL";
				branchCarModel.addRow(branchCarData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		table_branchCar.setModel(branchCarModel);
	}
}
