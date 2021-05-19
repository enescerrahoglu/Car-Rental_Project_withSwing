package Model;

import Helper.DBConnection;

public class User {
	private int id;
	String name, eposta, password, type;
	
	DBConnection conn = new DBConnection();
	
	
	public User(int id, String name, String eposta, String password, String type) {
		this.id = id;
		this.name = name;
		this.eposta = eposta;
		this.password = password;
		this.type = type;
	}
	
	public User() {
		
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
	public String getEposta() {
		return eposta;
	}
	public void setEposta(String eposta) {
		this.eposta = eposta;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
