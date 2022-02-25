import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class App implements ActionListener{

    static final String DB_URL = "jdbc:mysql://localhost:3306/Capstone";
    static final String USER = "root"; // username created in mySQL query bbbb
    static final String PASS = "password"; // password created in mySQL query
	private JLabel label = new JLabel(" ");
    private JFrame frame = new JFrame();
	public static JFrame menuFrame = new JFrame();

    public static void main(String[] args) throws SQLException {
		new App();
		//AddEmployees();
	}

	public App() {

        // the clickable button
        JButton enterButton = new JButton("Log In");
        enterButton.addActionListener(this);
        enterButton.setVisible(false);

        // the panel with the button and text
        JPanel panel = new JPanel();
        
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout());
        panel.add(label);
        panel.add(enterButton);
        

        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Phising service");
        
        frame.pack();
        frame.setVisible(true);
    }

	public static void optionsDisplay() {
    	JPanel panel = new JPanel();
        
    	JButton viewEmployees = new JButton("View Employees");
    	//TODO viewEmployees.addActionListener(new actionPerformed()); //need to add event for view employees
    	
    	JButton addEmployees = new JButton("Add Employees");
    	//TODO addEmployees.addActionListener(new actionPerformed()); //need to add event 
    	
    	JButton deleteEmployees = new JButton("Delete Employees");
    	//TODO deleteEmployees.addActionListener(new actionPerformed()); //need to add event 
    	
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout());
        //panel.add(label);
        panel.add(viewEmployees);
        panel.add(addEmployees);
        panel.add(deleteEmployees);


        

        // set up the frame and display it
        menuFrame.add(panel, BorderLayout.CENTER);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setTitle("Phising service");
        
        menuFrame.pack();
        menuFrame.setVisible(true);
    }

	// process the button clicks
	public void actionPerformed(ActionEvent e) {
		//clicks++;
		try {
			frame.setVisible(false);
			AddCompany();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		label.setText("booo");
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
		//TODO: Add did you mean sign in option 
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
	optionsDisplay();
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