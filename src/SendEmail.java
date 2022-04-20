import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.text.html.HTML;

import com.mysql.cj.conf.IntegerPropertyDefinition;
import com.mysql.cj.jdbc.DatabaseMetaData;

import java.util.ArrayList;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SendEmail {

    private static String USER_NAME = "JkelleyAKlein"; // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "JKelleyAKlein1!"; // GMail password

    // override -> copy & paste that & below it remove the params string, filename &
    // remove the if statement where
    // it says if file exists
    public static void SendEmail(HashMap<String, String> EmId) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String subject = "Hello";
        String body = "";
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
        body = Bodies.get(index);
        
        //TODO: loop through hashmap, add emails to the String[] "to"
        //pass sendFromGmail with correct employee Ids
        Iterator hmIterator = EmId.entrySet().iterator();
        String[] to;
        ArrayList<String> toList = new ArrayList<String>();
        ArrayList<String> ids = new ArrayList<String>();
       // int i = 0; 
        while(hmIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry)hmIterator.next();
            String email = ((String)mapElement.getValue());
            toList.add(email);
            // for(int i=0; i < EmId.size(); i++) {

            // }
            //to[i]=email;
            String id = ((String)mapElement.getKey()); 
            ids.add(id);
           // i++;
        }
        to = toList.toArray(new String[0]);

         sendFromGMail(from, pass, to, subject, body, ids);


        // for (String i : Emails) {
        //     String[] to = { i };
        //     sendFromGMail(from, pass, to, subject, body, 1);
        //     System.out.println("Message Sent");
        // }

    }
    // String[] to = { RECIPIENT }; // list of recipient email addresses
    // String subject = "Hello";
    // String body = "Welcome to The Jungle!";
    // ArrayList<String> Emails = new ArrayList<String>();
    // try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
    // Statement stmt = conn.createStatement();) {

    // DatabaseMetaData dbm = (DatabaseMetaData) conn.getMetaData();
    // //TODO: get company name, change from hard coded
    // //String tblname = company_name + "_employees";
    // String tblname = "_employees";
    // String query = " SELECT email FROM " + tblname;
    // ResultSet rs = stmt.executeQuery(query);
    // while(rs.next()) {
    // String email = rs.getString("email");
    // System.out.println(email);
    // Emails.add(email);
    // }
    // }

    // for (String i : Emails) {
    // String[] to = { i };
    // sendFromGMail(from, pass, to, subject, body,"pass");
    // System.out.println("Message Sent");
    // }
    // }
    private static void sendFromGMail(String from, String pass, String[] to, String subject, String body,
            ArrayList<String> employeeNumber) {
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
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            // File file = new File(filename);
            // // if (file.exists()){
            // // DataSource source = new FileDataSource(filename);
            // // message.setDataHandler(new DataHandler(source));
            // // message.setFileName(filename);

            // // }
            // message.setFrom(new InternetAddress("frank@dgmachine.com"));

            // Text

            // so we gotta just get the company name
            String link = "https://sites.google.com/view/capstonejkak/home?utm_source=" + employeeNumber
                    + "&utm_medium=email&utm_campaign="; // get company name;
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html");
            MimeBodyPart websiteclick = new MimeBodyPart();
            websiteclick.setContent(link, "text/html");

            // //Example
            // MimeBodyPart mimeBodyPartWithStyledText = new MimeBodyPart();
            // mimeBodyPartWithStyledText.setContent("<img
            // src=\"https://pastepixel.com/image/wkRkY2fRRUaezXyTphUc.png\"
            // alt=\"\"/>","text/html");
            // multipart.addBodyPart(mimeBodyPart);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(websiteclick);
            //int bEmployeeNumber = Integer.parseInt(Integer.toBinaryString(employeeNumber));
            int bEmployeeNumber = 1; //placeholder
            if (bEmployeeNumber % 10 == 1) {
                MimeBodyPart email1 = new MimeBodyPart();
                email1.setContent("1", "text/html");
                // email1.setContent("<img
                // src=\"https://pastepixel.com/image/dtQFdWQzkDDSuRZdQ6da.png\"
                // alt=\"\"/>","text/html");
                multipart.addBodyPart(email1);
            }
            bEmployeeNumber = bEmployeeNumber / 10;

            if (bEmployeeNumber % 10 == 1) {
                MimeBodyPart email2 = new MimeBodyPart();
                email2.setContent("2", "text/html");
                // email2.setContent("<img
                // src=\"https://pastepixel.com/image/U7HkVsae7MCUtdgTPFg4.png\" alt=\"\"/>",
                // "text/html");
                multipart.addBodyPart(email2);
            }
            bEmployeeNumber = bEmployeeNumber / 10;
            if (bEmployeeNumber % 10 == 1) {
                MimeBodyPart email3 = new MimeBodyPart();
                email3.setContent("3", "text/html");
                // email3.setContent("");
                multipart.addBodyPart(email3);
            }
            bEmployeeNumber = bEmployeeNumber / 10;
            if (bEmployeeNumber % 10 == 1) {
                MimeBodyPart email4 = new MimeBodyPart();
                // email4.setContent("");
                email4.setContent("4", "text/html");
                multipart.addBodyPart(email4);
            }
            bEmployeeNumber = bEmployeeNumber / 10;
            if (bEmployeeNumber % 10 == 1) {
                MimeBodyPart email5 = new MimeBodyPart();
                // email5.setContent("");
                email5.setContent("5", "text/html");
                multipart.addBodyPart(email5);
            }
            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
    public static void sendFromGMail(String from, String pass, String to, String subject, String body) {
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
            message.setRecipients(Message.RecipientType.TO, to);


            message.setSubject(subject);
            //TODO: add report
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html");
            MimeBodyPart websiteclick = new MimeBodyPart();
            

            
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
