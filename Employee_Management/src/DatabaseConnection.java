import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public void login(String name, String pass) throws Exception {
		//System.out.println(name + pass);
		try {
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from " + database + ".login WHERE uname = '"+name+"' AND password = '"+pass+"'");

			//System.out.println(resultSet.getInt("uname"));
			if(resultSet.isBeforeFirst()) {
				System.out.println("Successful login!");
				//this.close();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							String cname = null;
							while (resultSet.next()) {
								cname = resultSet.getString("company_name");
							}
							LoginSuccessful frame = new LoginSuccessful(cname, name, pass);
							frame.setVisible(true);
							frame.setResizable(false);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
			else {
				System.out.println("Login failed!");
				System.out.println("Either uname or password or both incorrect");
				this.close();
			}
		} catch (Exception e) {
			throw e;
		}

	}
	/*
	 * SignUp execute
	 */
	public void signup(String cname, String name, String pass) throws Exception {
		try {
			statement = connect.createStatement();
			resultSet = statement
					.executeQuery("select * from " + database + ".login WHERE uname = '"+name+"' AND password = '"+pass+"'");


			if(resultSet.isBeforeFirst()) {
				System.out.println("Company already exists with this uname");
				this.close();
			}
			else {
				System.out.println("Register Successful!");
				/*
				 * check_create : this variable checks whether table is create successfully or not
				 */
				int check_create = statement.executeUpdate("CREATE TABLE `" + cname + "_employee` (name TEXT(50),id VARCHAR(20),phoneno BIGINT(10),homeadd VARCHAR(100),officeadd VARCHAR(100),salary INT(20),age INT(2))");
				
				if(check_create == 0) {
					System.out.println("Table created successfully Successfully!");
					/*
					 * check_insert : this variable checks whether value added to table login successfully or not
					 */
					int check_insert = statement.executeUpdate("INSERT INTO `login` (company_name, uname, password) VALUES ('"+cname+"','"+name+"','"+pass+"')");
					
					if(check_insert != 0) {
						System.out.println("Values added to table login Successfully!");
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									Login frame = new Login();
									frame.setVisible(true);
									frame.setResizable(false);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
					else {
						System.out.println("Values addition failed!");
					}
				}
				else {
					System.out.println("Table creation failed");
				}
				this.close();
			}
		} catch (Exception e) {
			throw e;
		}

	}
	/*
	 * Display Employee List
	 */
	public String employeeList(String cname, String name, String pass) throws SQLException {
		
		statement = connect.createStatement();
		resultSet = statement
				.executeQuery("select * from `"+cname+"_employee`");

		String str="ename" + "\t" + "id" + "\t" + "phoneno" + "\t" + "homeadd" + "\t" + "officeadd"  + "\t" + "salary" + "\t" + "age" + "\n";
		if(resultSet.isBeforeFirst()) {
			while (resultSet.next()) {
				final String ename = resultSet.getString("name");
				final String id = resultSet.getString("id");
				final String phoneno = resultSet.getString("phoneno");
				final String homeadd = resultSet.getString("homeadd");
				final String officeadd = resultSet.getString("officeadd");
				final int salary = resultSet.getInt("salary");
				final int age = resultSet.getInt("age");
				str += ename + "\t" + id + "\t" + phoneno + "\t" + homeadd + "\t" + officeadd  + "\t" + salary + "\t" + age + "\n";
			}
		}
		return str;
	}
	/*
	 * Add Employee
	 */
	public int add(String cname, String name, String id, String phoneno, String homeadd, String officeadd, String salary, String age) throws SQLException {
		
		statement = connect.createStatement();
		int check_insert=0;
		try {
			check_insert = statement.executeUpdate("INSERT INTO `"+cname+"_employee` (name, id, phoneno, homeadd, officeadd, salary, age) VALUES ('"+name+"','"+id+"','"+phoneno+"','"+homeadd+"','"+officeadd+"','"+salary+"','"+age+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(check_insert != 0) {
			System.out.println("Employee Added Successfully!");
		}
		else {
			System.out.println("Adding Employee Failed!");
		}
		return 0;
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
