package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import Helper.DBConnection;

public class Worker {
	
	private int id, bManagerId, branchId;
	
	DBConnection conn = new DBConnection();
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement preparedStatement = null;
	
	public Worker() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getbManagerId() {
		return bManagerId;
	}
	public void setbManagerId(int bManagerId) {
		this.bManagerId = bManagerId;
	}

	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public Worker(int id, int bManagerId, int branchId) {
		super();
		this.id = id;
		this.bManagerId = bManagerId;
		this.branchId = branchId;
	}
}
