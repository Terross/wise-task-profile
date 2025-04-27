package ru.leti.wise.task.profile.notification;


import java.time.LocalDateTime;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.stereotype.Component;

@Component
public class MailSender {

    private final String smtpHost = "smtp.wise task.ru" ;
    private final int smtpPort = 25;
    private final String fromAddress = "noreply@wisetask.ru";


    public void sendEmail(String toAddress, String subject, String messageText) {
        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        Session session = Session.getInstance(props);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setText(messageText);
            Transport.send(message);
        } catch (MessagingException e){

        }
        System.out.println(LocalDateTime.now() + "Письмо для" + toAddress + "успешно отправлено!");
    }
}

