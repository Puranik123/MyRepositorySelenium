package com.qtpselenium.hybrid.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.comparator.LastModifiedFileComparator;  

import com.qtpselenium.hybrid.utility.Constants;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class SendMail  {



	
public static void main(String[] args) throws Exception {
		Properties pr=new Properties();
try {
	FileInputStream ip=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Configuration.properties");
	pr.load(ip);
} catch (FileNotFoundException e) {
	
	e.printStackTrace();
}
//Reports folder for Extent reports	

String ReportFolder=Constants.REPORTS_PATH;

//logic for sorting files based on date
//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
/*FileFilterDateIntervalUtils filter = 
    new FileFilterDateIntervalUtils("2010-01-01", "2020-12-31");*/

File folder =  new File(ReportFolder);
File files[] = folder.listFiles();
Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);






String FileName=files[files.length-1].getName();
System.out.println("Sankalp "+FileName);
String extentFilePath=ReportFolder+FileName;




String[] to={"sankalp.puranik@skillnetinc.com","romesh.sarwagod@skillnetinc.com","sankalp.puranik@gmail.com"};
String[] cc={""};
String[] bcc={""};


//mailing extent reports
sendmail("sankalp.puranik@skillnetinc.com",
        "March1980",
        "smtp.office365.com",
        "587",
        "true",
        "true",
        true,
        "javax.net.ssl.SSLSocketFactory",
        "false",
        to,
        cc,
        bcc,
       "Automation Test Reports - Extent",
        "Please find the reports attached.\n\n Regards\nWebMaster",
        extentFilePath,
        FileName);
String xsltReportPath=ReportFolder+"Report.zip";
//Zip.zipDir(System.getProperty("user.dir")+"\\XSLT_Reports\\", xsltReportPath);

//mailing xslt reports

/*sendmail("sankalp.puranik@skillnetinc.com",
        "March1980",
        "smtp.office365.com",
        "587",
        "true",
        "true",
        true,
       "javax.net.ssl.SSLSocketFactory",
        "false",
        to,
        cc,
        bcc,
       "Automation Test Reports - xslt",
        "Please find the reports attached.\n\n Regards\nWebMaster",
        xsltReportPath,
        "Report.zip");*/
}
	
	


	
	
	
	public static boolean sendmail(final String userName,
    		final String passWord,
    		String host,
    		String port,
    		String starttls,
    		String auth,
    		boolean debug,
    		String socketFactoryClass,
    		String fallback,
    		String[] to,
    		String[] cc,
    		String[] bcc,
    		String subject,
    		String text,
    		String attachmentPath,
    		String attachmentName){
		
		Properties props = new Properties();
		  props.put("mail.smtp.starttls.enable", starttls);
          props.put("mail.smtp.auth",auth);
          props.put("mail.smtp.host", host);
          props.put("mail.smtp.port", port);
		
          try

          {

          	Session session = Session.getInstance(props,
          	          new javax.mail.Authenticator() {
          	            protected PasswordAuthentication getPasswordAuthentication() {
          	                return new PasswordAuthentication(userName, passWord);
          	            }
          	          });

              MimeMessage msg = new MimeMessage(session);

              msg.setText(text);

              msg.setSubject(subject);
              //attachment start
              // create the message part 
             
              Multipart multipart = new MimeMultipart();
              MimeBodyPart messageBodyPart = new MimeBodyPart();
              DataSource source = 
                new FileDataSource(attachmentPath);
              messageBodyPart.setDataHandler(
                new DataHandler(source));
              messageBodyPart.setFileName(attachmentName);
              multipart.addBodyPart(messageBodyPart);
              
              // attachment ends

              // Put parts in message
              msg.setContent(multipart);
              msg.setFrom(new InternetAddress(userName));

                          for(int i=0;i<to.length;i++){

              msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));

                          }

                          /*for(int i=0;i<cc.length;i++){

              msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));

                          }*/

                         /* for(int i=0;i<bcc.length;i++){

              msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));

                          }*/

              msg.saveChanges();

                          Transport transport = session.getTransport("smtp");

                          transport.connect(host, userName, passWord);

                          transport.sendMessage(msg, msg.getAllRecipients());

                          transport.close();

                          return true;

          }

          catch (Exception mex)

          {

              mex.printStackTrace();

                          return false;

          } 
	}
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
