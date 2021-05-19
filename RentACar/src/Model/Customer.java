package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

import Helper.Helper;

public class Customer extends User{
	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.connDb();
	PreparedStatement preparedStatement = null;

	public Customer(int id, String eposta, String password, String name, String type) {
		super(id, eposta, password, name, type);
	}
	
	public Customer() {
	}

	public boolean register(String name, String eposta, String password) throws SQLException {
		int key = 0;
		String query = "INSERT INTO customers " + "(name , eposta, password) VALUES " + "(?,?,?)";
		password = Base64.getEncoder().encodeToString(password.getBytes());
		try {
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, eposta);
			preparedStatement.setString(3, password);
			preparedStatement.executeUpdate();
			key = 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (key == 1)
			return true;
		else
			return false;
	}
	
	public boolean checkOfEmail(String eposta) throws SQLException {
		int key = 0;
		boolean duplicate = true;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM (SELECT * FROM admin UNION SELECT * FROM branchmanagers UNION SELECT * FROM customers) AS S WHERE S.eposta = '" + eposta + "' ");
			while (!rs.next()) {
				duplicate = false;
				Helper.showMsg("Sistemde bu e-posta adresi için bir kayýt bulunmamaktadýr. Lütfen önce kayýt ekranýndan kaydolunuz.");
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
	
	public boolean resetPassword(String password, String eposta) throws SQLException {
		boolean key = false;
		String query = "UPDATE customers SET password = ? WHERE eposta = ?";
		password = Base64.getEncoder().encodeToString(password.getBytes());
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, eposta);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else 
			return false;
	}
	
	public boolean deleteAccount(String eposta) throws SQLException {
		boolean key = false;
		String query = "DELETE customers FROM customers WHERE eposta = ?";
		password = Base64.getEncoder().encodeToString(password.getBytes());
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, eposta);
			preparedStatement.executeUpdate();
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else 
			return false;
	}
	
	public boolean rentCar(int cusomerId, String customerName, int carId, String carName, int branchId, String branchName, String rentDate, String returnDate) throws SQLException {
		
		String query = "INSERT INTO rentals(customerId, customerName, carId, carName, branchId, branchName, rentDate, returnDate) VALUES(?,?,?,?,?,?,?,?)";	
		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT bc.*, r.* FROM branchcars bc LEFT JOIN rentaltransactions r ON (bc.carId = r.carId) WHERE r.carId IS NULL AND bc.carId = " + carId);
			while(rs.next()) {
				count++;
			}
			if(count == 1) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, cusomerId);
				preparedStatement.setString(2, customerName);
				preparedStatement.setInt(3, carId);
				preparedStatement.setString(4, carName);
				preparedStatement.setInt(5, branchId);
				preparedStatement.setString(6, branchName);
				preparedStatement.setString(7, rentDate);
				preparedStatement.setString(8, returnDate);
				preparedStatement.executeUpdate();
				key = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else
			return false;
	}
	
	public boolean addRT(int cusomerId, String customerName, int carId, String carName, int branchId, String branchName, String rentDate, String returnDate) throws SQLException {
		
		String query = "INSERT INTO rentaltransactions(customerId, customerName, carId, carName, branchId, branchName, rentDate, returnDate) VALUES(?,?,?,?,?,?,?,?)";	
		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT bc.*, r.* FROM branchcars bc LEFT JOIN rentaltransactions r ON (bc.carId = r.carId) WHERE r.carId IS NULL AND bc.carId = " + carId);
			while(rs.next()) {
				count++;
			}
			if(count == 1) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, cusomerId);
				preparedStatement.setString(2, customerName);
				preparedStatement.setInt(3, carId);
				preparedStatement.setString(4, carName);
				preparedStatement.setInt(5, branchId);
				preparedStatement.setString(6, branchName);
				preparedStatement.setString(7, rentDate);
				preparedStatement.setString(8, returnDate);
				preparedStatement.executeUpdate();
				key = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else
			return false;
	}
}