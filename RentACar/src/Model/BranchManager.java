package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class BranchManager extends User {
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	Connection con = conn.connDb();
	
	public static int count = 0;
	
	public BranchManager(int id, String name, String eposta, String password, String type) {
		super(id, name, eposta, password, type);
	}
	
	public BranchManager() {
		
	}
	
	public ArrayList<Car> getBranchPresentCarList(int bManagerId) throws SQLException {
		ArrayList<Car> list = new ArrayList<>();
		Car obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT c.id, c.brandName, c.modelName, c.modelYear, c.color, c.dailyPrice FROM branchcars bc LEFT JOIN workers w ON bc.branchId = w.branchId LEFT JOIN cars c ON bc.carId = c.id WHERE w.bManagerId = " + bManagerId);
			while(rs.next()) {
				obj = new Car(rs.getInt("c.id"), rs.getString("c.brandName"), rs.getString("c.modelName"), rs.getString("c.modelYear"), rs.getString("c.color"), rs.getString("c.dailyPrice"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Rental> getAllRentals(int bManagerId) throws SQLException {
		ArrayList<Rental> list = new ArrayList<>();
		Rental obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT r.id, r.customerId, r.customerName, r.carId, r.carName, r.branchId, r.branchName, r.rentDate, r.returnDate FROM rentals r LEFT JOIN workers w ON r.branchId = w.branchId WHERE w.bManagerId = " + bManagerId);
			while(rs.next()) {
				obj = new Rental(rs.getInt("id"), rs.getInt("customerId"), rs.getInt("carId"), rs.getInt("branchId"), rs.getString("customerName"), rs.getString("carName"), rs.getString("branchName"), rs.getString("rentDate"), rs.getString("returnDate"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean deleteRental(int carId) throws SQLException {
		
		String query = "DELETE FROM rentaltransactions WHERE carId = ?";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, carId);
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
	
	public Object isRental(int carId) throws SQLException {
		Object obj = null;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT r.carId FROM branchcars bc LEFT JOIN rentaltransactions r ON r.carId = bc.carId LEFT JOIN workers w ON w.branchId = bc.branchId WHERE bc.carId = " + carId);
			while(rs.next()) {
				if(rs.getInt("r.carId") == carId) {
					obj = "Kiralandý";
					count += 1;
				}else {
					obj = "Kiralanmadý";
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	public boolean updateDailyPrice(int id, String dailyPrice) throws SQLException {
		
		String query = "UPDATE cars SET dailyPrice = ? WHERE id = ?";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, dailyPrice);
			preparedStatement.setInt(2, id);
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
	
	public String getBranchName(int bManagerId) {
		String branchName = "";
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT b.name FROM branches b LEFT JOIN workers w ON b.id = w.branchId WHERE w.bManagerId = " + bManagerId);
			while(rs.next()) {
				branchName = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return branchName;
	}
	
	/*
	public ArrayList<User> getSubeMuduruList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM branchmanagers WHERE type = 'sube muduru'");
			while(rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("name"), rs.getString("eposta"), rs.getString("password"), rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
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
		
		String query = "DELETE FROM branchmanagers WHERE id = ?";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
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
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else 
			return false;
	}*/

}
