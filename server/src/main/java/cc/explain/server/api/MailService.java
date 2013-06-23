package cc.explain.server.api;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * User: kzimnick
 * Date: 20.06.13
 * Time: 19:07
 */
public class MailService {

    public String host;
    public String from;
    public String pass;

    public MailService(){
        init();
    }

    public void init(){
        host="smtp.live.com";
        from="";
        pass="";
    }



    public void send(String destinationEmail, String subject, String text) throws MessagingException {

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        String[] to = {destinationEmail};

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));

        InternetAddress[] toAddress = new InternetAddress[to.length];

        for( int i=0; i < to.length; i++ ) {
            toAddress[i] = new InternetAddress(to[i]);
        }
        System.out.println(Message.RecipientType.TO);

        for( int i=0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
        }
        message.setSubject(subject);
        message.setText(text);
        Transport transport = session.getTransport("smtp");
        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
