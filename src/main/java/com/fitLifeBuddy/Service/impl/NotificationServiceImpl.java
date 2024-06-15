package com.fitLifeBuddy.Service.impl;

import com.fitLifeBuddy.Entity.Notification;
import com.fitLifeBuddy.Repository.INotificationRepository;
import com.fitLifeBuddy.Service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class NotificationServiceImpl implements INotificationService {
    @Autowired
    private INotificationRepository notificationRepository;


    @Override
    public List<Notification> findByPersonId(Long idPerson) throws Exception {
        return notificationRepository.findByPersonId(idPerson);
    }

    @Override
    @Transactional
    public Notification save(Notification notification) throws Exception {
        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void delete(Long id) throws Exception {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> getAll() throws Exception {
        return notificationRepository.findAll().stream()
                .sorted((n1, n2) -> n1.getIdNotification().compareTo(n2.getIdNotification()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Notification> getById(Long id) throws Exception {
        return notificationRepository.findById(id);
    }
}
