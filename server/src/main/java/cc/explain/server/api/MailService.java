package cc.explain.server.api;

import cc.explain.server.exception.TechnicalException;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * User: kzimnick
 * Date: 20.06.13
 * Time: 19:07
 */
public class MailService {

    public String host;
    public String from;
    public String pass;
    public String username;

    public MailService(){
        init();
    }

    public void init() {
        try{
            from= (String)InitialContext.doLookup("java:comp/env/fromEmail");
            pass= (String)InitialContext.doLookup("java:comp/env/smtpPass");
            username = (String)InitialContext.doLookup("java:comp/env/smtpUser");
            host = (String)InitialContext.doLookup("java:comp/env/smtpHost");
        }catch(NamingException e){
            throw new TechnicalException(e);
        }
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
        transport.connect(host, username, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
