package com.fitLifeBuddy.Util;

import com.fitLifeBuddy.Entity.Daily;
import com.fitLifeBuddy.Entity.Enum.Status;
import com.fitLifeBuddy.Entity.Person;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.IPersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;

@Service
public class EmailNotificationService {
    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IDailyService dailyService;

    @Autowired
    private IPersonService personService;

    @Scheduled(cron = "0 0 20 * * ?") // Programado para ejecutarse todos los días a las 8 PM
    public void sendDailyEmails() {
        Date today = new Date();
        try {
            List<Daily> dailies = dailyService.findByDate(today);
            logger.info("Dailies found: {}", dailies.size());
            for (Daily daily : dailies) {
                if (daily.getStatus() == Status.UNFILLED) {
                    if (daily.getPlan() != null && daily.getPlan().getPacient() != null) {
                        Person person = daily.getPlan().getPacient().getPerson();
                        if (person != null) {
                            logger.info("Sending email to: {}", person.getEmailAddress());
                            sendEmail(person.getEmailAddress(), "Confirmación de Actividades Diarias",
                                    buildEmailContent(person.getFullname(), daily.getIdDaily()));
                        } else {
                            logger.warn("Person not found for daily id: {}", daily.getIdDaily());
                        }
                    } else {
                        logger.warn("Plan or Pacient not found for daily id: {}", daily.getIdDaily());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error sending daily emails", e);
        }
    }

    private void sendEmail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }

    private String buildEmailContent(String name, Long dailyId) {
        return "<p>Hola " + name + ",</p>" +
                "<p>Recuerda confirmar tus actividades diarias haciendo clic en el siguiente enlace:</p>" +
                "<p><a href=\"http://15.228.8.131:8080/api/dailies/confirm/" + dailyId + "\">Confirmar Daily</a></p>" +
                "<p>Saludos,<br/>El equipo de FitLifeBuddy</p>";
    }
}
