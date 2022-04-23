import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;

public class PlayGround {
    public static void main(String[] args) {
        String USER_NAME = "JkelleyAKlein"; // GMail user name (just the part before "@gmail.com")
        String PASSWORD = "JKelleyAKlein1!"; // GMail password
        String from = USER_NAME;
        String pass = PASSWORD;
        // override -> copy & paste that & below it remove the params string, filename &
        // remove the if statement where
        // it says if file exists
        // sendFromGMail(from, pass, to, subject, body, ids);

        ArrayList<String> Emails = new ArrayList<String>();
        Emails.add("pirateshockey@google.com");
        Emails.add("josephboydkelley@google.com");
        for (String i : Emails) {

            sendFromGMail(from, pass, i, "d", "body");
            System.out.println("Message Sent");
        }
    }

    private static void sendFromGMail(String from, String pass, String to, String subject, String body) {

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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(subject);
            String link = "https://sites.google.com/view/capstonejkak/home?utm_source="// + employeeNumber
                    + "&utm_medium=email&utm_campaign="; // get company name;
            // TODO: get company name;
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(body, "text/html");
            MimeBodyPart websiteclick = new MimeBodyPart();
            websiteclick.setContent(link, "text/html");
            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(websiteclick);
            int employeeNumber = 1;
            int bEmployeeNumber = Integer.parseInt(Integer.toBinaryString(employeeNumber));
            // int bEmployeeNumber = 1; //placeholder
            if (bEmployeeNumber % 10 == 1) {
                MimeBodyPart email1 = new MimeBodyPart();
                email1.setContent("1", "text/html");
                // email1.setContent("<img
                // src https://pastepixel.com/image/dtQFdWQzkDDSuRZdQ6da.png\"
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
                // email3.setContent("<img
                // src=\"https://pastepixel.com/image/WNYrTHC57YqUMaTbHYSB.png\" alt=\"\"/>",
                // "text/html");
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

}
