
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App {
static final String DB_URL = "jdbc:mysql://localhost:3306/Capstone";
static final String USER = "root"; //username created in mySQL
static final String PASS = "password"; //password created in mySQL query
public static void main(String[] args) throws SQLException {
//open a connection 
try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS); 
Statement stmt = conn.createStatement();) {

//--CREATE TABLE TO HOLD COMPANY EMPLOYEE INFO--

Scanner myObj = new Scanner(System.in);
String company_name;
System.out.println("Enter company name: "); //user input of employee name
company_name = myObj.nextLine();
//System.out.println(company_name + "_employees");

//TODO: add error checking logic to see if table already exists etc. 

String sql_table = "CREATE TABLE " + company_name + "_employees " +
                  "(id INTEGER not NULL, " +
                  " fname VARCHAR(255), " + 
                  " lname VARCHAR(255), " + 
                  " email VARCHAR(255), " + 
                  " PRIMARY KEY ( id ))"; 

stmt.executeUpdate(sql_table);
System.out.println("Created table in database");

//--ADD IN EMPLOYEE DATA TO TABLE--
System.out.println("Enter id: ");
String empId = myObj.nextLine();
System.out.println("Enter first name: ");
String firstName = myObj.nextLine();
System.out.println("Enter last name: ");
String lastName = myObj.nextLine();
System.out.println("Enter email: ");
String empEmail = myObj.nextLine();


String insert_sql = "insert into " + company_name + "_employees "
+ " (id, fname, lname, email)" + " values (id, fname, lname, email)";
stmt.executeUpdate(insert_sql);
//TODO: should we give user ability to upload csv or stick to one by one entries?
System.out.println("Data inserted.");
}
catch (SQLException e) {
e.printStackTrace();
}
}

}