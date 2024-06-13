package com.fitLifeBuddy.Service;

import com.fitLifeBuddy.Entity.Notification;

import java.util.List;

public interface INotificationService extends CrudService<Notification> {
    public List<Notification> findByPersonId(Long idPerson) throws Exception;

}
