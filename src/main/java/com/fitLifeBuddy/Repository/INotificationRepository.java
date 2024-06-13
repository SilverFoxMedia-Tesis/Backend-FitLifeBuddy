package com.fitLifeBuddy.Repository;

import com.fitLifeBuddy.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from Notification n where n.person.idPerson = :personId")
    public List<Notification> findByPersonId(@Param("personId")Long idPerson);

}
