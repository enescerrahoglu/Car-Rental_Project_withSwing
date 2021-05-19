package Model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin extends User {
	
	Connection con = conn.connDb();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	Car car = new Car();

	public Admin(int id, String name, String eposta, String password, String type) {
		super(id, name, eposta, password, type);
	}
	
	public Admin() {
		
	}
	
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
	
	public ArrayList<User> getFreeSubeMuduruList() throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT b.*, w.* FROM branchmanagers b LEFT JOIN workers w ON (b.id = w.bManagerId) WHERE b.id IS NULL OR w.bManagerId IS NULL");
			while(rs.next()) {
				obj = new User(rs.getInt("id"), rs.getString("name"), rs.getString("eposta"), rs.getString("password"), rs.getString("type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<User> getBranchMudurList(int branch_id) throws SQLException {
		ArrayList<User> list = new ArrayList<>();
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT b.id, b.name, b.eposta, b.password, b.type FROM workers w LEFT JOIN branchmanagers b ON w.bManagerId = b.id WHERE branchId = " + branch_id);
			while(rs.next()) {
				obj = new User(rs.getInt("b.id"), rs.getString("b.name"), rs.getString("b.eposta"), rs.getString("b.password"), rs.getString("b.type"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	
	public ArrayList<Worker> getWorkerList(int branch_id) throws SQLException {
		ArrayList<Worker> list = new ArrayList<>();
		Worker obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM workers WHERE branchId = " + branch_id);
			while(rs.next()) {
				obj = new Worker();
				obj.setId(rs.getInt("id"));
				obj.setbManagerId(rs.getInt("bManagerId"));
				obj.setBranchId(rs.getInt("branchId"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			con.close();
		}
		
		return list;
	}
	
	public ArrayList<Car> getCarList() throws SQLException {
		ArrayList<Car> list = new ArrayList<>();
		Car obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM cars");
			while(rs.next()) {
				obj = new Car(rs.getInt("id"), rs.getString("brandName"), rs.getString("modelName"), rs.getString("modelYear"), rs.getString("color"), rs.getString("dailyPrice"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Car> getFreeCarList() throws SQLException {
		ArrayList<Car> list = new ArrayList<>();
		Car obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT c.*, bc.* FROM cars c LEFT JOIN branchcars bc ON (c.id = bc.carId) WHERE c.id IS NULL OR bc.carId IS NULL");
			while(rs.next()) {
				obj = new Car(rs.getInt("id"), rs.getString("brandName"), rs.getString("modelName"), rs.getString("modelYear"), rs.getString("color"), rs.getString("dailyPrice"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Car> getBranchAllCarList(int branch_id) throws SQLException {
		ArrayList<Car> list = new ArrayList<>();
		Car obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT c.id, c.brandName, c.modelName, c.modelYear, c.color, c.dailyPrice FROM branchcars bc LEFT JOIN cars c ON bc.carId = c.id WHERE branchId = " + branch_id);
			while(rs.next()) {
				obj = new Car(rs.getInt("c.id"), rs.getString("c.brandName"), rs.getString("c.modelName"), rs.getString("c.modelYear"), rs.getString("c.color"), rs.getString("c.dailyPrice"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<BranchCar> getBranchCarList(int branch_id) throws SQLException {
		ArrayList<BranchCar> list = new ArrayList<>();
		BranchCar obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM branchcars WHERE branchId = " + branch_id);
			while(rs.next()) {
				obj = new BranchCar();
				obj.setId(rs.getInt("id"));
				obj.setCarId(rs.getInt("carId"));
				obj.setBranchId(rs.getInt("branchId"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			st.close();
			rs.close();
			con.close();
		}
		
		return list;
	}
	
	public ArrayList<Car> getFreeBranchCarList(int branch_id) throws SQLException {
		ArrayList<Car> list = new ArrayList<>();
		Car obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT c.* FROM cars c LEFT JOIN branchcars bc ON (c.id = bc.carId) LEFT JOIN rentaltransactions r ON (c.id = r.carId) WHERE r.carId IS NULL AND bc.branchId = " + branch_id);
			while(rs.next()) {
				obj = new Car(rs.getInt("id"), rs.getString("brandName"), rs.getString("modelName"), rs.getString("modelYear"), rs.getString("color"), rs.getString("dailyPrice"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Car> getRentalCarInfo(int rentalCarId) throws SQLException {
		ArrayList<Car> list = new ArrayList<>();
		Car obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM cars WHERE id = " + rentalCarId);
			while(rs.next()) {
				obj = new Car(rs.getInt("id"), rs.getString("brandName"), rs.getString("modelName"), rs.getString("modelYear"), rs.getString("color"), rs.getString("dailyPrice"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean addWorker(int bManagerId, int branchId) throws SQLException {
		
		String query = "INSERT INTO workers(bManagerId, branchId) VALUES(?,?)";

		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT b.* FROM branches b LEFT JOIN workers w ON (b.id = w.branchId) WHERE w.branchId IS NULL AND b.id = " + branchId);
			while(rs.next()) {
				count++;
			}
			if(count == 1) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, bManagerId);
				preparedStatement.setInt(2, branchId);
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
	
	public boolean deleteWorker(int id) throws SQLException {
		
		String query = "DELETE FROM workers WHERE id = ?";	
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
	
	public boolean updateImage(int id, String path) throws SQLException, FileNotFoundException {
		
		String query = "UPDATE cars SET image = ? WHERE id = ?";	
		boolean key = false;
		File file = new File(path);
		FileInputStream inputStream = new FileInputStream(file);
		
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setBlob(1, inputStream);
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
	
	public boolean addBranchCar(int carId, int branchId) throws SQLException {
		
		String query = "INSERT INTO branchcars(carId, branchId) VALUES(?,?)";

		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT c.* FROM cars c LEFT JOIN branchcars bc ON (c.id = bc.carId) WHERE bc.carId IS NULL AND c.id = " + carId);
			while(rs.next()) {
				count++;
			}
			if(count == 1) {
				preparedStatement = con.prepareStatement(query);
				preparedStatement.setInt(1, carId);
				preparedStatement.setInt(2, branchId);
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
