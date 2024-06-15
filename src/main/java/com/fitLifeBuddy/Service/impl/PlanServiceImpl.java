package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.*;
import com.fitLifeBuddy.Entity.Enum.DietType;
import com.fitLifeBuddy.Entity.Enum.Frecuently;
import com.fitLifeBuddy.Entity.Enum.Status;
import com.fitLifeBuddy.Repository.IPlanRepository;
import com.fitLifeBuddy.Service.IDailyService;
import com.fitLifeBuddy.Service.INotificationService;
import com.fitLifeBuddy.Service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PlanServiceImpl implements IPlanService {
    @Autowired
    private IPlanRepository planRepository;

    @Autowired
    private IDailyService dailyService;

    @Autowired
    private INotificationService notificationService;
    @Autowired
    private JavaMailSenderImpl mailSender;

    @Override
    @Transactional
    public Plan save(Plan plan) throws Exception {
        return planRepository.save(plan);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        planRepository.deleteById(id);
    }

    @Override
    public List<Plan> getAll() throws Exception {
        return planRepository.findAll().stream()
                .sorted((p1, p2) -> p1.getIdPlan().compareTo(p2.getIdPlan()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Plan> getById(Long id) throws Exception {
        return planRepository.findById(id);
    }

    @Override
    public List<Daily> findDailiesByIdPlan(Long idPlan) throws Exception {
        return planRepository.findDailiesByIdPlan(idPlan);
    }

    @Override
    public List<Plan> findByFrecuently(Frecuently frecuently) throws Exception {
        return planRepository.findByFrecuently(frecuently);
    }

    @Override
    public List<Plan> findByDietType(DietType dietType) throws Exception {
        return planRepository.findByDietType(dietType);
    }

    @Override
    public List<Plan> findActivedPlanByPacientId(Long pacientId) throws Exception {
        return planRepository.findActivedPlanByPacientId(pacientId);
    }

    @Transactional
    public void checkAndNotifyPlanStopped(Plan plan) {
        try {
            Date today = new Date();
            List<Daily> unfilledDailies = dailyService.findUnfilledDailiesUntilToday(today, Status.UNFILLED, plan.getIdPlan());
            if (unfilledDailies.size() > 3) {
                plan.setStatus(Status.STOPPED);
                planRepository.save(plan);

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

        }
    }

    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
        }
    }

    private String buildPlanStoppedEmailContent(Pacient pacient, Plan plan) {
        return "<p>Hola,</p>" +
                "<p>El plan del paciente " + pacient.getPerson().getFullname() + " ha sido detenido por varios días sin confirmar sus actividades diarias.</p>" +
                "<p>Por favor, contacta con el paciente para más detalles.</p>" +
                "<p>Saludos,<br/>El equipo de FitLifeBuddy</p>";
    }
}
