import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
	static final String DB_URL = "jdbc:mysql://localhost:3306/capstone";
	static final String USER = "newuser"; // username created in mySQL query
	static final String PASS = "password"; // password created in mySQL query
	private static JFrame frame;
	private static JTextField input;
	private static JLabel id_label;	
	private static JTextField input_id;
	private static JLabel fname_label;	
	private static JTextField input_fname;
	private static JLabel lname_label;	
	private static JTextField input_lname;
	private static JLabel email_label;	
	private static JTextField input_email;
	private static JLabel label;	
	private static JButton button1;
	private static JButton addEmployees;
	private static JButton viewEmployees;
	private static JButton deleteEmployees;
	private static JButton addBttn;
	static String company_name;
	private int width; 
	private int height;
	
	public App(int w, int h) {
		frame = new JFrame();
		label = new JLabel("<html>Welcome! Please enter your company name to login.</html>");
		
		input = new JTextField(10);
		button1 = new JButton("Log In");
		viewEmployees = new JButton("View Employees");
    	
    	addEmployees = new JButton("Add Employees");
    	
    	deleteEmployees = new JButton("Delete Employees");
    	id_label = new JLabel("Enter ID: ");
    	input_id = new JTextField(10);
    	fname_label = new JLabel("Enter first name: ");
    	input_fname = new JTextField(10);
    	lname_label = new JLabel("Enter last name: ");
    	input_lname = new JTextField(10);
    	email_label = new JLabel("Enter email: ");
    	input_email = new JTextField(10);
    	
    	addBttn = new JButton("Add Employee");
		width = w;
		height = h;
	}
	
	public static void AddEmployees() throws SQLException {
		// open a connection
		//addEmployeeDisplay();
		addEmployees.setVisible(false);
		viewEmployees.setVisible(false);
		deleteEmployees.setVisible(false);
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			
			DatabaseMetaData dbm = conn.getMetaData();
			String tblname = company_name + "_employees";
			ResultSet tables = dbm.getTables(null, null, tblname, null);
			
			
			
			String end = "yes";
			if (tables.next()) {
				while (end.equals("yes")) {
					String empId = JOptionPane.showInputDialog("Enter ID");
					String firstName = JOptionPane.showInputDialog("Enter First Name");
					String lastName = JOptionPane.showInputDialog("Enter Last Name");
					String empEmail = JOptionPane.showInputDialog("Enter Email");

					// the mysql insert statement
					String query = " insert into " + company_name + "_employees  (id, fname, lname, email)"
							+ " values (?, ?, ?, ?)";

					// create the mysql insert preparedstatement
					PreparedStatement preparedStmt = conn.prepareStatement(query);
					preparedStmt.setString(1, empId);
					preparedStmt.setString(2, firstName);
					preparedStmt.setString(3, lastName);
					preparedStmt.setString(4, empEmail);
					preparedStmt.execute();
					JOptionPane.showMessageDialog(null, "Employee Has Been Added", "Results",
							JOptionPane.PLAIN_MESSAGE);
					end = JOptionPane.showInputDialog("Would you like to continue?");

				}
			} else {
				//optionsDisplay();
				
			}
			addEmployees.setVisible(true);
			viewEmployees.setVisible(true);
			deleteEmployees.setVisible(true);
		}

	}
	public static void optionsDisplay() {
		//String cname = company_name;
		Container cp = frame.getContentPane();
		FlowLayout flow = new FlowLayout();
		cp.setLayout(flow);
		frame.setSize(640, 480); //same width/height as GUI set up 
        
        cp.add(viewEmployees);
        cp.add(addEmployees);
        cp.add(deleteEmployees);


  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Phising service");
        
        frame.setVisible(true);
    }
	public static void addEmployeeDisplay() {
		Container cp = frame.getContentPane();
		FlowLayout flow = new FlowLayout();
		cp.setLayout(flow);
		frame.setSize(640, 480); //same width/height as GUI set up 
		frame.setTitle("Add Employee");
		cp.add(id_label);
		cp.add(input_id);
		cp.add(fname_label);
		cp.add(input_fname);
		cp.add(lname_label);
		cp.add(input_lname);
		cp.add(email_label);
		cp.add(input_email);
		cp.add(addBttn);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setVisible(true);
	}

	 public static void AddCompany() throws SQLException {
			// open a connection
			try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
					Statement stmt = conn.createStatement();) {

				// --CREATE TABLE TO HOLD COMPANY EMPLOYEE INFO--
				// this is going to act as like "logging in" for the company... lets remember
				// that for the GUI
				// user input of employee name
				//String company_name = JOptionPane.showInputDialog("Enter Company");
				company_name = input.getText();
				// TODO: add error checking logic to see if table already exists etc.

				// TODO: add error checking logic to see if table already exists etc.
				String tblName = company_name + "_employees";
				DatabaseMetaData dbm = conn.getMetaData();
				ResultSet tables = dbm.getTables(null, null, tblName, null);
				if (tables.next()) {
					JOptionPane.showMessageDialog(null, "Company Already Exists", "Results", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Creating Company", "Results", JOptionPane.PLAIN_MESSAGE);
					String sql_table = "CREATE TABLE " + company_name + "_employees " + "(id INTEGER not NULL, "
							+ " fname VARCHAR(255), " + " lname VARCHAR(255), " + " email VARCHAR(255), "
							+ " PRIMARY KEY ( id ))";

					stmt.executeUpdate(sql_table);
					JOptionPane.showMessageDialog(null, "Company successfully created!", "Results", JOptionPane.PLAIN_MESSAGE);
					//optionsDisplay();
				}
				button1.setVisible(false);
				label.setVisible(false);
				input.setVisible(false);
				optionsDisplay();
			}

		}
	public void setUpGUI() {
		Container cp = frame.getContentPane();
		FlowLayout flow = new FlowLayout();
		cp.setLayout(flow);
		frame.setSize(width, height);
		frame.setTitle("Login");
		cp.add(label);
		cp.add(input);		
		cp.add(button1);
		//cp.add(button2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void setUpButtonListeners() {
		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object o = ae.getSource();
				if(o == button1) {
					//String s = input.getText();
					//label.setText(s); //changes label value 
					//input.setText("");
					try {
						AddCompany();
						button1.setVisible(false);
						label.setVisible(false);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("logged in ");
				}
				else if(o == addEmployees) {
					System.out.println("lets add an employee");

					try {
						AddEmployees();
						/*addEmployees.setVisible(false);
						viewEmployees.setVisible(false);
						deleteEmployees.setVisible(false);*/
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addEmployees.setVisible(true);
					viewEmployees.setVisible(true);
					deleteEmployees.setVisible(true);
					System.out.println("employee add ");
				}
				else if (o == viewEmployees) {
					System.out.println("lets view employees");
					try {
						viewEmployees();
					}catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addEmployees.setVisible(true);
					viewEmployees.setVisible(true);
					deleteEmployees.setVisible(true);
					System.out.println("employee viewed ");
				}
			}
			
		};	
		
		button1.addActionListener(buttonListener);
		addEmployees.addActionListener(buttonListener);
		viewEmployees.addActionListener(buttonListener);
		deleteEmployees.addActionListener(buttonListener);

	}
	
	
}
