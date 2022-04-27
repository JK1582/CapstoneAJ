import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduleSend implements Job {
	private static String USER_NAME = "JkelleyAKlein"; // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "JKelleyAKlein1!"; // GMail password
	App gd = new App();
	SendEmail sd = new SendEmail();
	TrackingPixel tp = new TrackingPixel();
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		String email = gd.ceoEmail;
		String from = USER_NAME;
        String pass = PASSWORD;
        String subject = "Phishing Report";
        String body = "<html> Hello, <br>"
        		+ "Here is your requested phishing report. <br> <br>"
        		+ TrackingPixel.reportBody 
        		+ " <br> We suggest forwarding <a href= 'youtube.com/watch?v=WNVTGTrWcvw'> this video </a> to the above employees to prevent them from falling for a legitimate phishing attack in the future. "
        		+ "<br> <br> Stay safe. Stay secure. </html>";
        sd.sendFromGMail(email, subject, body);
		System.out.println("Scheduler running");
		System.out.println("The time is " + new Date());
	}

}
