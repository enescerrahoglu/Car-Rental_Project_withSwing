package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class BranchCar {
	
	private int id, carId, branchId;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public BranchCar() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}

	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	public BranchCar(int id, int carId, int branchId) {
		super();
		this.id = id;
		this.carId = carId;
		this.branchId = branchId;
	}

	public ArrayList<BranchCar> getBranchCarList() throws SQLException {
		ArrayList<BranchCar> list = new ArrayList<>();
		BranchCar obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM branchcars");
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
	
	Connection con = conn.connDb();
	
	public boolean deleteBranchCar(int id) throws SQLException {
		
		String query = "DELETE FROM branchcars WHERE id = ?";	
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
	
	public boolean addBranchCar(int carId, int branchId) throws SQLException {
		
		String query = "INSERT INTO branchcars" + "(carId, branchId) VALUES" + "(?,?)";

		boolean key = false;
		int count = 0;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM branchcars WHERE carId = " + carId + " AND branchId = " + branchId);
			while(rs.next()) {
				count++;
			}
			if(count == 0) {
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
