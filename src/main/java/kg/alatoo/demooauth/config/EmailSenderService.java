package kg.alatoo.demooauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String toEmail, String subject, String body, Long product){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("addafaidn184@gmail.com");
        message.setTo(toEmail);
        message.setText(body+"\n ur product id: "+ product);
        message.setSubject(subject);
        mailSender.send(message);
    }
}