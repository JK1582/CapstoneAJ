import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduleSend implements Job {
	private static String USER_NAME = "JkelleyAKlein"; // GMail user name (just the part before "@gmail.com")
	private static String PASSWORD = "JKelleyAKlein1!"; // GMail password
	App a = new App();
	SendEmail sd = new SendEmail();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String email = a.ceoEmail;
		String from = USER_NAME;
		String pass = PASSWORD;
		String subject = "Phishing Report";
		String body = ""; // insert report here ?
		sd.sendFromGMail(email, subject, body);
		System.out.println("Scheduler running");
		System.out.println("The time is " + new Date());

	}

}
