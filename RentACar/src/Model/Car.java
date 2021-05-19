package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Car {
	
	int id;
	String brandName, modelName, modelYear, color, dailyPrice, image;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Car() {
		
	}
	
	public Car(int id, String brandName, String modelName, String modelYear, String color, String dailyPrice) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.modelName = modelName;
		this.modelYear = modelYear;
		this.color = color;
		this.dailyPrice = dailyPrice;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}


	public String getDailyPrice() {
		return dailyPrice;
	}

	public void setDailyPrice(String dailyPrice) {
		this.dailyPrice = dailyPrice;
	}
	
	public String image() {
		return dailyPrice;
	}

	public void image(String image) {
		this.image = image;
	}

	Connection con = conn.connDb();
	
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
	
	public boolean addCar(String brandName, String modelName, String modelYear, String color, String dailyPrice) throws SQLException {
		
		String query = "INSERT INTO cars(brandName, modelName, modelYear, color, dailyPrice) VALUES(?,?,?,?,?)";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, brandName);
			preparedStatement.setString(2, modelName);
			preparedStatement.setString(3, modelYear);
			preparedStatement.setString(4, color);
			preparedStatement.setString(5, dailyPrice);
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
	
	public boolean deleteCar(int id) throws SQLException {
		
		String query = "DELETE FROM cars WHERE id = ?";	
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
			key = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(key)
			return true;
		else 
			return false;
	}
}
