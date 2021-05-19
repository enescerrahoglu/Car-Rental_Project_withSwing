package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Rental {
	int id, customerId, carId, branchId;
	String customerName, carName, branchName, rentDate, returnDate;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	
	
	public Rental(int id, int customerId, int carId, int branchId, String customerName, String carName,
			String branchName, String rentDate, String returnDate) {
		this.id = id;
		this.customerId = customerId;
		this.carId = carId;
		this.branchId = branchId;
		this.customerName = customerName;
		this.carName = carName;
		this.branchName = branchName;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
	}

	public Rental() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCarName() {
		return carName;
	}

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getRentDate() {
		return rentDate;
	}

	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	
	public ArrayList<Rental> getMyAllRentals(int customerId) throws SQLException {
		ArrayList<Rental> list = new ArrayList<>();
		Rental obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM rentals WHERE customerId = " + customerId);
			while(rs.next()) {
				obj = new Rental();
				obj.setId(rs.getInt("id"));
				obj.setCustomerId(rs.getInt("customerId"));
				obj.setCustomerName(rs.getString("customerName"));
				obj.setCarId(rs.getInt("carId"));
				obj.setCarName(rs.getString("carName"));
				obj.setBranchId(rs.getInt("branchId"));
				obj.setBranchName(rs.getString("branchName"));
				obj.setRentDate(rs.getString("rentDate"));
				obj.setReturnDate(rs.getString("returnDate"));;
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			st.close();
			rs.close();
			con.close();
		}
		
		return list;
	}
	
	
}
