package com.fitLifeBuddy.Util;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.Enum.Status;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.INotificationService;
import com.fitLifeBuddy.Service.IPersonService;
import com.fitLifeBuddy.Service.IPlanService;
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

    @Autowired
    private IPlanService planService;

    @Autowired
    private INotificationService notificationService;

    @Scheduled(cron = "0 0 20 * * ?") // Programado para ejecutarse todos los días a las 8 PM
    public void sendDailyEmails() {
        Date today = new Date();
        try {
            List<Daily> dailies = dailyService.findByDate(today);
            logger.info("Dailies found: {}", dailies.size());
            for (Daily daily : dailies) {
                if (daily.getPlan() != null && daily.getPlan().getPacient() != null) {
                    Person person = daily.getPlan().getPacient().getPerson();
                    if (person != null && person.isAcceptsNotifications()) {
                        logger.info("Sending email to: {}", person.getEmailAddress());
                        sendEmail(person.getEmailAddress(), "Confirmación de Actividades Diarias",
                                buildEmailContent(person.getFullname(), daily.getIdDaily()));

                        // Guardar notificación en la base de datos
                        Notification notification = new Notification();
                        notification.setNameNotification("Confirmación de Actividades Diarias");
                        notification.setDescriptionNotification("Por favor, confirma tus actividades diarias.");
                        notification.setStatus(Status.PENDING);
                        notification.setDate(today);
                        notification.setPerson(person);
                        notificationService.save(notification);
                    } else {
                        logger.warn("Person not found or does not accept notifications for daily id: {}", daily.getIdDaily());
                    }
                } else {
                    logger.warn("Plan or Pacient not found for daily id: {}", daily.getIdDaily());
                }
            }

            // Verificar si algún plan debe ser detenido
            List<Plan> plans = planService.getAll();
            for (Plan plan : plans) {
                checkAndNotifyPlanStopped(plan);
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

    private void checkAndNotifyPlanStopped(Plan plan) {
        try {
            Date today = new Date();
            List<Daily> unfilledDailies = dailyService.findUnfilledDailiesUntilToday(today, Status.UNFILLED, plan.getIdPlan());
            if (unfilledDailies.size() > 3) {
                plan.setStatus(Status.STOPPED);
                planService.save(plan);

                // Enviar correo al nutricionista
                Person nutritionist = plan.getPacient().getNutritionist().getPerson();
                sendEmail(nutritionist.getEmailAddress(), "Plan detenido para paciente",
                        buildPlanStoppedEmailContent(plan.getPacient(), plan));

                // Guardar notificación en la base de datos
                Notification notification = new Notification();
                notification.setNameNotification("Plan detenido");
                notification.setDescriptionNotification("El plan del paciente ha sido detenido por varios días sin confirmar sus dailies.");
                notification.setStatus(Status.PENDING);
                notification.setDate(today);
                notification.setPerson(nutritionist);
                notificationService.save(notification);
            }
        } catch (Exception e) {
            logger.error("Error checking and notifying plan stopped", e);
        }
    }

    private String buildPlanStoppedEmailContent(Pacient pacient, Plan plan) {
        return "<p>Hola,</p>" +
                "<p>El plan del paciente " + pacient.getPerson().getFullname() + " ha sido detenido por varios días sin confirmar sus actividades diarias.</p>" +
                "<p>Por favor, contacta con el paciente para más detalles.</p>" +
                "<p>Saludos,<br/>El equipo de FitLifeBuddy</p>";
    }
}
