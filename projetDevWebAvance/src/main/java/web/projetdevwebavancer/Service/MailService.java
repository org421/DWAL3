package web.projetdevwebavancer.Service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class MailService {
    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SpringTemplateEngine templateEngine;


    // Sends an email with the specified recipient, subject, and template, optionally including dynamic content from a map of variables
    public void sendMail(String to, String subject, String template, Map<String,Object> option) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        Context context = new Context();
        for (Map.Entry<String,Object> entry : option.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(templateEngine.process(template,context), true);
        mailSender.send(message);
    }

    //
    public void sendMail(String to, String subject, String template) throws MessagingException {
        sendMail(to,subject,template,null);
    }


}
