package controller;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLHandler {

	private String url;
	
	/**
	 * creates the database
	 * @param dbname name of the database
	 */
	public SQLHandler(String dbname) {
		
		new File(System.getProperty("user.dir") + "/dbs").mkdirs();
		
		url = "jdbc:sqlite:dbs/" + dbname + ".db";
		
		connect();
	}
	
	/**
	 * attempts to connect to the database
	 */
	public void connect() {
		try (Connection conn = DriverManager.getConnection(url)) {		
			if (conn != null) {
				System.out.println("Connected");		
			}		
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}	
	
	/**
	 * creates a table with the specified name
	 * @param name name of table to be created
	 */
	public void createTable(String name) {
		
		String sql = "CREATE TABLE IF NOT EXISTS " + name + " (\n"
				+ "name text PRIMARY KEY,\n"
				+ "description text NOT NULL,\n"
				+ "quantity int real,\n"
				+ "price float(53)\n"
				+ ");";
		
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * creates the customers table
	 */
	public void createCustomersTable() {
		
		String sql = "CREATE TABLE IF NOT EXISTS customers (\n"
				+ "name text PRIMARY KEY,\n"
				+ "street text NOT NULL,\n"
				+ "city text NOT NULL,\n"
				+ "postcode text NOT NULL\n"
				+ ");";
		
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * creates the owners table
	 */
	public void createOwnerTable() {
		
		String sql = "CREATE TABLE IF NOT EXISTS owner (\n"
				+ "name text PRIMARY KEY,\n"
				+ "street text NOT NULL,\n"
				+ "city text NOT NULL,\n"
				+ "postcode text NOT NULL,\n"
				+ "phone text NOT NULL,\n"
				+ "days text NOT NULL\n"
				+ ");";
		
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * creates the counter table
	 */
	public void createCounterTable() {
		
		String sql = "CREATE TABLE IF NOT EXISTS counter (\n"
				+ "countername text PRIMARY KEY, \n"
				+ "count int NOT NULL \n"
				+ ");";
		
		try (Connection conn = DriverManager.getConnection(url);
				 Statement stmt = conn.createStatement()) {
				stmt.execute(sql);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	
	/**
	 * starts the invoice counter row in the counter table
	 */
	public void startInvoiceCounter() {
		String sql = "INSERT INTO counter (countername, count) VALUES ('invoice', '0');";
		
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * updates the invoice counter row in the counter table
	 * @param count the new invoice count to be updated into the row
	 */
	public void updateInvoice(int count) {
		
		String sql = "UPDATE counter SET count = ?"
				+ "WHERE countername = 'invoice'";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
				pstmt.setInt(1, count);;
				pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * queries the database for a given SQL query
	 * @param query the SQL to query the database with
	 * @return an collection containing the results of the query after it has been parsed by resultsParser()
	 */
	public ArrayList<Map<String, Object>> query(String query) {
		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(queryHandler(query));) {
			
			return resultsParser(rs);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return new ArrayList<Map<String,Object>>();
		}
	}
	
	/**
	 * inserts a new customer into the database
	 * @param name customers name
	 * @param street customers street
	 * @param city customers city
	 * @param postcode customers postcode
	 */
	public void insertCustomer(String name, String street, String city, String postcode) {
		String sql = "INSERT INTO customers (name, street, city, postcode) VALUES (?,?,?,?)";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, name);
			pstmt.setString(2, street);
			pstmt.setString(3, city);
			pstmt.setString(4, postcode);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * inserts new business details into the database
	 * @param name owners business name
	 * @param street owners business street
	 * @param city owners business city
	 * @param postcode owners business postcode
	 * @param phone owners business phone number
	 * @param days days until invoices are due at the owners business
	 */
	public void insertOwner(String name, String street, String city, String postcode, String phone, String days) {
		String sql = "INSERT INTO owner (name, street, city, postcode, phone, days) VALUES (?,?,?,?,?,?)";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, name);
			pstmt.setString(2, street);
			pstmt.setString(3, city);
			pstmt.setString(4, postcode);
			pstmt.setString(5, phone);
			pstmt.setString(6, days);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * clears the current business details
	 */
	public void clearOwner() {
		String sql = "DELETE FROM owner";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	
	/**
	 * inserts a new product into the database
	 * @param table which table to insert into
	 * @param name name of the product
	 * @param desc description of the product
	 * @param quantity quantity of the product to insert
	 * @param price price of the product
	 */
	public void insertProduct(String table, String name, String desc, int quantity, double price) {
		String sql = "INSERT INTO " + table + " (name, description, quantity, price) VALUES (?,?,?,?)";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			pstmt.setInt(3, quantity);
			pstmt.setFloat(4, (float) price);
			pstmt.executeUpdate();
				
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * updates a products quantity
	 * @param table the table to update
	 * @param name name of the product to update
	 * @param quantity new quantity to set
	 */
	public void updateProduct(String table, String name, int quantity) {
		String sql = "UPDATE " + table + " SET quantity = ?"
				+ "WHERE name = ?";
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
				pstmt.setInt(1, quantity);
				pstmt.setString(2, name);
				pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * deletes a product from the database
	 * @param table table to delete product from
	 * @param name name of the product to delete
	 */
	public void deleteProduct(String table, String name) {
		String sql = "DELETE FROM " + table + " WHERE name = ?";
		
		try (Connection conn = DriverManager.getConnection(url);
			 PreparedStatement pstmt = conn.prepareStatement(sql);) {
				pstmt.setString(1, name);
				pstmt.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
	}
	
	/**
	 * selects the correct SQL based on the query value given
	 * @param query the database to query
	 * @return returns an SQL string to the query method
	 */
	public String queryHandler(String query) {
		switch (query) {
		case "stock":
			return("SELECT * FROM stock;");
		case "invoice":
			return ("SELECT * FROM invoice;");
		case "customers":
			return ("SELECT * FROM customers;");
		case "owner":
			return ("SELECT * FROM owner;");
		case "counter":
			return ("SELECT * FROM counter");
		default:
			return "";
		}
	}
	
	/**
	 * parses the raw results from the database query into a structured collection
	 * @param rs results set from the database query
	 * @return returns a structured collection of the database query results
	 * @throws SQLException throws if there is a major problem with the SQL syntax
	 */
	public ArrayList<Map<String,Object>> resultsParser(ResultSet rs) throws SQLException {
		ResultSetMetaData md = rs.getMetaData();
		int columns = md.getColumnCount();
		ArrayList<Map<String,Object>> results = new ArrayList<>();
		while (rs.next()) {
			Map<String,Object> row = new HashMap<>(columns);
			for(int i=1; i<=columns; i++) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			results.add(row);
		}
		return results;
	}
	
}


