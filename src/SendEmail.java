import java.util.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.io.File;


public class SendEmail {

    private static String USER_NAME = "JkelleyAKlein";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "JKelleyAKlein1!"; // GMail password

    public static void main(String[] args) {
        String from = USER_NAME;
        String pass = PASSWORD;
        String filename = "/Users/joekelley/Documents/CapstoneAJ/src/Mutan#5226.jpg";
       // String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Hello";
        String body = "Welcome to The Jungle!";
        ArrayList<String> Emails = new ArrayList<String>();
        //Emails.add("pirateshockey17@gmai.com");
        Emails.add("JosephBoydKelley@gmai.com");
        //Emails.add("Jk1582@mynsu.nova.edu");
       //Emails.add("MadelineMcgowan974@gmail.com");
        
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
