package Helper;
import java.sql.*;

public class DBConnection {
	Connection c = null;
	
	public DBConnection() {
		
	}
	
	public Connection connDb() {
		try {
			String dbURL1 = "jdbc:mariadb://localhost:3334/rentacar?user=root&password=12345";
			this.c = DriverManager.getConnection(dbURL1);
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return c;
	}
}