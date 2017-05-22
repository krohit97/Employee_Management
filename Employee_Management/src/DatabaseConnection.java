import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {

	private Connection connect = null;
	private Statement statement = null;
	@SuppressWarnings("unused")
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	final private String host = "localhost";
	final private String user = "root";
	final private String passwd = "";
	final private String database = "employee_management";
	
	/*
	 * Establishing connection to database
	 */
	public void connectToDB() throws Exception {
		try {
			// This will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");

			// Setup the connection with the DB
			connect = DriverManager.getConnection("jdbc:mysql://" + host + "/"
					+ database + "?" + "user=" + user + "&password=" + passwd);

			System.out.println("Database connection successful");

		} catch (Exception e) {
			System.out.println("Database connection failed!");
			throw e;
		}
	}
	/*
	 * Login execution  
	 */
	public int login(String name, String pass) throws Exception {
		//System.out.println(name + pass);
		try {
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from " + database + ".login WHERE uname = '"+name+"' AND password = '"+pass+"'");


			//System.out.println(resultSet.getInt("uname"));
			if(resultSet.isBeforeFirst()) {
				System.out.println("Successful login!");
				this.close();
				return 0;
			}
			else {
				System.out.println("Successful failed!");
				System.out.println("Either uname or password or both incorrect");
				this.close();
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}

	}
	/*
	 * SignUp execute
	 */
	public int signup(String cname, String name, String pass) throws Exception {
		//System.out.println(name + pass);
		try {
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from " + database + ".login WHERE uname = '"+name+"' AND password = '"+pass+"'");


			//System.out.println(resultSet.getInt("uname"));
			if(resultSet.isBeforeFirst()) {
				System.out.println("Company already exists with this uname");
				this.close();
				return 0;
			}
			else {
				System.out.println("Register Successful!");
				resultSet = statement
						.executeQuery("CREATE TABLE '" + cname + "'_employee ('name TEXT(50)','id VARCHAR(20)','phoneno BIGINT(10)','homeadd VARCHAR(100)','officeadd VARCHAR(100)','salary INT(20)','age INT(2)')");
				
				if(!resultSet.wasNull()) {
					System.out.println("Table created successfully Successfully!");
					resultSet = statement
							.executeQuery("INSERT INTO '"+cname+"'_employee ('name','id','phoneno','homeadd','officeadd','salary','age') VALUES ('sumit kumar','shr15','9712508221','khora colony','greater noida','80000','20')");
					
					if(!resultSet.wasNull()) {
						System.out.println("Values added to table Successfully!");
					}
					else {
						System.out.println("Values addition failed!");
					}
				}
				else {
					System.out.println("Table creation failed");
				}
				this.close();
				return 1;
			}
		} catch (Exception e) {
			throw e;
		}

	}
	/*
	 * Closing database connection
	 */
	public void close() {
		try {
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}
}
