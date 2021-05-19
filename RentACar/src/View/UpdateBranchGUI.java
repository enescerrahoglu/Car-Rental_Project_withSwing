package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.Helper;
import Model.Branch;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class UpdateBranchGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fld_branchName;
	private static Branch branch;

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdateBranchGUI frame = new UpdateBranchGUI(branch);
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UpdateBranchGUI(Branch branch) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UpdateBranchGUI.class.getResource("/View/icon.png")));
		setTitle("Güncelleme Ekraný");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 165);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("activeCaption"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbl_branchName = new JLabel("Þube Adý");
		lbl_branchName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lbl_branchName.setBounds(10, 25, 264, 20);
		contentPane.add(lbl_branchName);
		
		fld_branchName = new JTextField();
		fld_branchName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				fld_branchName.setText(fld_branchName.getText().toUpperCase());
			}
		});
		fld_branchName.setFont(new Font("Arial", Font.PLAIN, 14));
		fld_branchName.setColumns(10);
		fld_branchName.setBounds(10, 50, 274, 24);
		fld_branchName.setText(branch.getName());
		contentPane.add(fld_branchName);
		
		JButton btn_updateBranch = new JButton("Güncelle");
		btn_updateBranch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Helper.confirm("sure")) {
					try {
						branch.updateBranch(branch.getId(), fld_branchName.getText());
						Helper.showMsg("success");
						dispose();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btn_updateBranch.setForeground(new Color(50, 205, 50));
		btn_updateBranch.setFont(new Font("Century Gothic", Font.BOLD, 20));
		btn_updateBranch.setBackground(new Color(240, 255, 240));
		btn_updateBranch.setBounds(10, 85, 274, 40);
		contentPane.add(btn_updateBranch);
	}
}
