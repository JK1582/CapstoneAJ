import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import com.mysql.cj.jdbc.DatabaseMetaData;

import java.util.ArrayList;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SendEmail {

    private static String USER_NAME = "JkelleyAKlein";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "JKelleyAKlein1!"; // GMail password

    //override -> copy & paste that & below it remove the params string, filename & remove the if statement where 
    //it says if file exists
    public static void main(String[] args) throws SqLException {
        String from = USER_NAME;
        String pass = PASSWORD;
        static final String DB_URL = "jdbc:mysql://localhost:3306/capstone";
        static final String USER = "newuser"; // username created in mySQL query
        static final String PASS = "password"; // password created in mySQL query
      //  String filename = "/Users/joekelley/Documents/CapstoneAJ/src/Mutan#5226.jpg";
       // String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Hello"; 
        String body = "Welcome to The Jungle!";
        ArrayList<String> Emails = new ArrayList<String>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();) {
			
			DatabaseMetaData dbm = (DatabaseMetaData) conn.getMetaData();
			//TODO: get company name, change from hard coded
			//String tblname = company_name + "_employees";
			String tblname =  "_employees";
			String query = " SELECT email FROM " + tblname;
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String email = rs.getString("email");
				System.out.println(email);
				Emails.add(email);
			}
        }	

        
        for (String i : Emails) {
            String[] to = { i };
            sendFromGMail(from, pass, to, subject, body,"pass");
            System.out.println("Message Sent");
        }
    }

    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body,String filename) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
      
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            File file = new File(filename);
            if (file.exists()){
            DataSource source = new FileDataSource(filename);
            message.setDataHandler(new DataHandler(source)); 
            message.setFileName(filename);
            
        }
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
