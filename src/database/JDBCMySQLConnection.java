package database;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import utils.Logger;


public class JDBCMySQLConnection {
	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	static final String URL = "jdbc:mysql://localhost:51708/attendance";
	static final String USER = "root";//"root";
	static final String PASSWORD = "arpitkh96iotmain";
	
	
	private JDBCMySQLConnection() {
        try {
            //Step 2: Load MySQL Java driver
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
	      //  Logger.log(e);
	        }
    }

	private Connection createConnection() {
		Connection connection = null;
		try {
		//	Logger.log("Connecting"+(connection==null));
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
	//		Logger.log("Connected"+(connection==null));
		} catch (SQLException e) {
		//	Logger.log(e);
			}
		return connection;
	}
	
	
	
	public static Connection getConnection() {
		return instance.createConnection();
	}
}
