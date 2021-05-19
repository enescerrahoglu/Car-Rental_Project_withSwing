package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Branch {
	
	private int id;
	private String name;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	Connection con = conn.connDb();
	
	public Branch() {
		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Branch(int id, String name, String eposta, String password) {
		super();
		this.id = id;
		this.name = name;
	}


	public ArrayList<Branch> getBranchList() throws SQLException {
		ArrayList<Branch> list = new ArrayList<>();
		Branch obj;
		Connection con = conn.connDb();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM branches");
			while(rs.next()) {
				obj = new Branch();
				obj.setId(rs.getInt("id"));
				obj.setName(rs.getString("name"));
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
	
	
	public boolean addBranch(String name) throws SQLException {
		
		String query = "INSERT INTO branches" + "(name) VALUES" + "(?)";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
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
	
	public boolean deleteBranch(int id) throws SQLException {
		
		String query = "DELETE branches, workers, branchcars FROM branches LEFT JOIN workers ON branches.id = workers.branchId LEFT JOIN branchcars ON branches.id = branchcars.branchId WHERE branches.id = ?";
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
	
	public boolean updateBranch(int id, String name) throws SQLException {
		
		String query = "UPDATE branches SET name = ? WHERE id = ?";	
		boolean key = false;
		try {
			st = con.createStatement();
			preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, name);
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
	
	public Branch getFetch(int id) {
		Branch b = new Branch();
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM branches WHERE id = " + id);
			while(rs.next()) {
				b.setId(rs.getInt("id"));
				b.setName(rs.getString("name"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	
}
