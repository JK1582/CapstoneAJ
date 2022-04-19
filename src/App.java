import javax.swing.JFrame;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
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
	static final String USER = "root"; // username created in mySQL query
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
	private static JButton generateReport;
	static String company_name;
	private int width;
	private int height;
	static SendEmail sd = new SendEmail();

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
		generateReport = new JButton("Generate Report");
		width = w;
		height = h;
	}

	public static void SendReport() throws SchedulerException {
		// use quartz job scheduler
		JobDetail j = JobBuilder.newJob(ScheduleSend.class).build();
		Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(02).repeatForever()).build();

	}

	public static void SendEmail() throws SQLException {
		String from = "JkelleyAKlein"; // GMail user name (just the part before "@gmail.com")
		String pass = "JKelleyAKlein1!"; // GMail password
		String subject = "Hello";
		// String pixel1 = "<!DOCTYPE html>\n"
		// 		+ "<img src=\"https://script.google.com/macros/s/AKfycbwwV7PU_8KgjKyVYxpqpdy2LjX6iYi_sxo9OVMmd5gCZzEvwus/exec\"\n"
		// 		+ "width =\"1\" height =\"1\">";
		// String pixel2 = "";
		// String pixel3 = "";
		// String pixel4 = "";
		// String pixel5 = "";
		// String pixel6 = "";
		// String body = "";
		String bodyOpt1 = "<p> Hi, <br> A vulnerability has been identified in the Outlook applications that allow an attacker to access confidential emails and files from your account without your knowledge."
				+
				"<br> We would like all of our employees to verify if any of their data has been compromised. </p> <br> <h4> To perform this verification, please use the following link: {{insert tracking link here}}</h4>"
				+
				" <br> <p>Thank you, <br> Human Resources </p>";
		String bodyOpt2 = "<p> Hello, <br> Due to recent activity on your account, we have placed a temporary suspension until you verify your account. Please review your information with us before close of business today. <br>"
				+
				" To verify your information, please click the following link: {{insert tracking link here}} <br> " +
				"For the sake of our company's security, we advise you to avoid sharing your password with anybody. If you have any issues verifying your account, "
				+
				" please contact technical support. <br> Thank you, <br> IT team </p>";
		String bodyOpt3 = "<h1> Password successfully changed </h1> <br> " +
				"<p>Your password for your Microsoft account was successfully changed. <br> If this was you, then you can safely ignore this email. <br>"
				+
				"If this wasn't you, your account has been compromised. Please click the following link to secure your account: {{insert link here}} <br>"
				+
				"Thank you, <br> The Microsoft account team </p>";
		ArrayList<String> Bodies = new ArrayList<String>();
		Bodies.add(bodyOpt1);
		Bodies.add(bodyOpt2);
		Bodies.add(bodyOpt3);
		int index = (int) (Math.random() * Bodies.size());
		//body = Bodies.get(index);
		ArrayList<String> Emails = new ArrayList<String>();
		//ArrayList<String> Ids = new ArrayList<String>();
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {

			DatabaseMetaData dbm = conn.getMetaData();

			String tblname = company_name + "_employees";

			//body = "<h1>This is actual message embedded in HTML tags</h1>";
			String query = " SELECT email FROM " + tblname;
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String email = rs.getString("email");
				Integer id = rs.getInt("id");
				System.out.println(email);
				System.out.println(id);
				Emails.add(email);
				//Ids.add(Integer.toBinaryString(id));
				// HashMap<String, String> a = new HashMap<>();
				// for (int i = 0; i < Ids.size(); i++) {
				// 	a.put(Ids.get(i), Emails.get(i));
				// }
			}

		}
		sd.SendEmail(Emails);
		JOptionPane.showMessageDialog(frame, "Emails Sent, report will go here");
	}

	// private static void sendFromGMail(String from, String pass, String[] to, String subject, String body,
	// 		String filename) {
	// 	Properties props = System.getProperties();
	// 	String host = "smtp.gmail.com";
	// 	props.put("mail.smtp.starttls.enable", "true");
	// 	props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

	// 	props.put("mail.smtp.host", host);
	// 	props.put("mail.smtp.user", from);
	// 	props.put("mail.smtp.password", pass);

	// 	props.put("mail.smtp.port", "587");
	// 	props.put("mail.smtp.auth", "true");
	// 	props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

	// 	javax.mail.Session session = Session.getDefaultInstance(props);
	// 	MimeMessage message = new MimeMessage(session);

	// 	try {
	// 		message.setFrom(new InternetAddress(from));
	// 		InternetAddress[] toAddress = new InternetAddress[to.length];

	// 		// To get the array of addresses
	// 		for (int i = 0; i < to.length; i++) {
	// 			toAddress[i] = new InternetAddress(to[i]);
	// 		}

	// 		for (int i = 0; i < toAddress.length; i++) {
	// 			message.addRecipient(Message.RecipientType.TO, toAddress[i]);
	// 		}

	// 		message.setSubject(subject);
	// 		File file = new File(filename);
	// 		if (file.exists()) {
	// 			DataSource source = new FileDataSource(filename);
	// 			message.setDataHandler(new DataHandler(source));
	// 			message.setFileName(filename);

	// 		}
	// 		message.setContent(body, "text/html");
	// 		Transport transport = session.getTransport("smtp");
	// 		transport.connect(host, from, pass);
	// 		transport.sendMessage(message, message.getAllRecipients());
	// 		transport.close();
	// 	} catch (AddressException ae) {
	// 		ae.printStackTrace();
	// 	} catch (MessagingException me) {
	// 		me.printStackTrace();
	// 	}
	// }

	public static void DeleteEmployee() throws SQLException {
		addEmployees.setVisible(false);
		viewEmployees.setVisible(false);
		deleteEmployees.setVisible(false);
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			DatabaseMetaData dbm = conn.getMetaData();
			String tblname = company_name + "_employees";
			ResultSet tables = dbm.getTables(null, null, tblname, null);
			String end = "yes";
			outerloop: if (tables.next()) {
				System.out.println("entered1");
				while (end.equals("yes")) {
					System.out.println("entered2");
					String empId = JOptionPane.showInputDialog("Enter ID");
					if (empId == null) {
						end = "no";
						break outerloop;

					}
					String sql = "SELECT * FROM " + company_name + "_employees where id=" + empId;
					ResultSet rs = stmt.executeQuery(sql);

					if (rs.next()) {
						System.out.println("entered3");
						System.out.println("Success");
						String query = " delete from " + company_name + "_employees where id=" + empId;

						// create the mysql insert preparedstatement
						PreparedStatement preparedStmt = conn.prepareStatement(query);
						// preparedStmt.setString(1, empId);
						preparedStmt.execute();
						JOptionPane.showMessageDialog(null, "Employee Has Been Removed", "Results",
								JOptionPane.PLAIN_MESSAGE);
						end = JOptionPane.showInputDialog("Would you like to continue?");

					} else {

						System.out.println("entered4");
						JOptionPane.showMessageDialog(null, "Employee Does Not Exist", "Results",
								JOptionPane.YES_NO_OPTION);
						end = JOptionPane.showInputDialog("Would you like to continue?");
						if (end == null) {
							System.out.println("entered5");
							break;
						}
					}
					// the mysql insert statement
					if (end == null) {
						System.out.println("entered6");
						end = "no";
					}

				}
			}

		}
	}

	public static void AddEmployees() throws SQLException {
		// open a connection
		// addEmployeeDisplay();
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
				outerloop: while (end.equals("yes")) {

					String empId = JOptionPane.showInputDialog("Enter ID");
					if (empId == null) {
						System.out.println("entered1");
						end = "no";
						break outerloop;
					}
					String firstName = JOptionPane.showInputDialog("Enter First Name");
					if (firstName == null) {
						System.out.println("entered1");
						end = "no";
						break outerloop;
					}
					String lastName = JOptionPane.showInputDialog("Enter Last Name");
					if (lastName == null) {
						System.out.println("entered1");
						end = "no";
						break outerloop;
					}
					String empEmail = JOptionPane.showInputDialog("Enter Email");
					if (empEmail == null) {
						System.out.println("entered1");
						end = "no";
						break outerloop;
					}

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
					if (end == null) {
						System.out.println("entered2");
						break;
					}

				}
			} else {

				// optionsDisplay();

			}
			addEmployees.setVisible(true);
			viewEmployees.setVisible(true);
			deleteEmployees.setVisible(true);
		}

	}

	public static void ViewEmployees() throws SQLException {
		// open a connection
		// addEmployeeDisplay();
		addEmployees.setVisible(false);
		viewEmployees.setVisible(false);
		deleteEmployees.setVisible(false);
		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {

			DatabaseMetaData dbm = conn.getMetaData();
			String tblname = company_name + "_employees";
			ResultSet tables = dbm.getTables(null, null, tblname, null);

			Statement satmt = conn.createStatement();
			String query = " SELECT * FROM " + company_name + "_employees";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("id   fname   lname   email");
			String data = "";
			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String email = rs.getString("email");
				System.out.println(id + "   " + fname + "    " + lname + "    " + email);
				data += id + "   " + fname + "    " + lname + "    " + email + "\n";
			}
			JOptionPane.showMessageDialog(frame, data);
			addEmployees.setVisible(true);
			viewEmployees.setVisible(true);
			deleteEmployees.setVisible(true);
		}

	}

	public static void optionsDisplay() {
		// String cname = company_name;
		Container cp = frame.getContentPane();
		FlowLayout flow = new FlowLayout();
		cp.setLayout(flow);
		frame.setSize(640, 480); // same width/height as GUI set up

		cp.add(viewEmployees);
		cp.add(addEmployees);
		cp.add(deleteEmployees);
		cp.add(generateReport);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Phising service");

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
			// String company_name = JOptionPane.showInputDialog("Enter Company");
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
				JOptionPane.showMessageDialog(null, "Company successfully created!", "Results",
						JOptionPane.PLAIN_MESSAGE);
				// optionsDisplay();
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
		// cp.add(button2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void setUpButtonListeners() {
		ActionListener buttonListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				Object o = ae.getSource();
				if (o == button1) {
					// String s = input.getText();
					// label.setText(s); //changes label value
					// input.setText("");
					try {
						AddCompany();
						button1.setVisible(false);
						label.setVisible(false);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("logged in ");
				} else if (o == addEmployees) {
					System.out.println("lets add an employee");

					try {
						AddEmployees();
						/*
						 * addEmployees.setVisible(false);
						 * viewEmployees.setVisible(false);
						 * deleteEmployees.setVisible(false);
						 */
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addEmployees.setVisible(true);
					viewEmployees.setVisible(true);
					deleteEmployees.setVisible(true);
					generateReport.setVisible(true);
					System.out.println("employee add ");
				} else if (o == viewEmployees) {
					System.out.println("lets view employees");
					try {
						ViewEmployees();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addEmployees.setVisible(true);
					viewEmployees.setVisible(true);
					deleteEmployees.setVisible(true);
					generateReport.setVisible(true);
					System.out.println("employee viewed ");
				} else if (o == deleteEmployees) {
					System.out.println("delete an employee");
					try {
						DeleteEmployee();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					addEmployees.setVisible(true);
					viewEmployees.setVisible(true);
					deleteEmployees.setVisible(true);
					generateReport.setVisible(true);
					System.out.println("employee deleted ");
				} else if (o == generateReport) {
					System.out.println("generate report");
					try {
						SendEmail();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					addEmployees.setVisible(true);
					viewEmployees.setVisible(true);
					deleteEmployees.setVisible(true);
					generateReport.setVisible(true);
					System.out.println("emails sent ");
				}
			}

		};

		button1.addActionListener(buttonListener);
		addEmployees.addActionListener(buttonListener);
		viewEmployees.addActionListener(buttonListener);
		deleteEmployees.addActionListener(buttonListener);
		generateReport.addActionListener(buttonListener);

	}

}
