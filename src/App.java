import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import javax.swing.JOptionPane;
public class App {

    static final String DB_URL = "jdbc:mysql://localhost:3306/Capstone";
    static final String USER = "root"; // username created in mySQL query bbbb
    static final String PASS = "password"; // password created in mySQL query

    public static void main(String[] args) throws SQLException {
		AddEmployees();
	}

    public static void AddCompany () throws SQLException {
	 // open a connection
	 try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	 Statement stmt = conn.createStatement();) {

	// --CREATE TABLE TO HOLD COMPANY EMPLOYEE INFO--
	//this is going to act as like "logging in" for the company... lets remember that for the GUI 
	// user input of employee name
	String company_name = JOptionPane.showInputDialog("Enter Company");

	// TODO: add error checking logic to see if table already exists etc.

   //TODO: add error checking logic to see if table already exists etc. 
	String tblName = company_name + "_employees";
	DatabaseMetaData dbm = conn.getMetaData();
	ResultSet tables = dbm.getTables(null, null, tblName, null);
	if(tables.next()) {	
		JOptionPane.showMessageDialog(null, "Company Already Exists", "Results", JOptionPane.PLAIN_MESSAGE );
    }
	else{
		JOptionPane.showMessageDialog(null, "Creating Company", "Results", JOptionPane.PLAIN_MESSAGE );
	String sql_table = "CREATE TABLE " + company_name + "_employees " +
			   "(id INTEGER not NULL, " +
			   " fname VARCHAR(255), " + 
			   " lname VARCHAR(255), " + 
			   " email VARCHAR(255), " + 
			   " PRIMARY KEY ( id ))"; 
	
	stmt.executeUpdate(sql_table);
	JOptionPane.showMessageDialog(null, "Created Company", "Results", JOptionPane.PLAIN_MESSAGE );
	}    			
} 

}
public static void AddEmployees () throws SQLException {
	// open a connection
	try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	Statement stmt = conn.createStatement();) {

   // --CREATE TABLE TO HOLD COMPANY EMPLOYEE INFO--
   //this is going to act as like "logging in" for the company... lets remember that for the GUI 
   //Scanner myObj = new Scanner(System.in);
   String company_name = JOptionPane.showInputDialog("Enter Company");
   //System.out.println("Enter company name: "); // user input of employee name
   //company_name = myObj.nextLine();

   // TODO: add error checking logic to see if table already exists etc.

  //TODO: add error checking logic to see if table already exists etc. 
   String tblName = company_name + "_employees";
   DatabaseMetaData dbm = conn.getMetaData();
   ResultSet tables = dbm.getTables(null, null, tblName, null);
   String end ="yes";
   if(tables.next()) {	
	   while (end.equals("yes")){
	String empId = JOptionPane.showInputDialog("Enter ID");
	String firstName = JOptionPane.showInputDialog("Enter First Name");
	String lastName = JOptionPane.showInputDialog("Enter Last Name");
	String empEmail = JOptionPane.showInputDialog("Enter Email");

	
	
	

	  // the mysql insert statement
	  String query = " insert into " + company_name + "_employees  (id, fname, lname, email)"
		+ " values (?, ?, ?, ?)";

	  // create the mysql insert preparedstatement
	  PreparedStatement preparedStmt = conn.prepareStatement(query);
	  preparedStmt.setString (1, empId);
	  preparedStmt.setString (2, firstName);
	  preparedStmt.setString   (3, lastName);
	  preparedStmt.setString    (4, empEmail);
	  preparedStmt.execute();
	JOptionPane.showMessageDialog(null, "Employee Has Been Added", "Results", JOptionPane.PLAIN_MESSAGE );
	end = JOptionPane.showInputDialog("Would you like to continue?");
	

}
   }
   else {
	JOptionPane.showMessageDialog(null, "Company Does Not Exist", "Results", JOptionPane.PLAIN_MESSAGE );
   
   }    			
} 

}
}