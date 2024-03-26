package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Notification;
import com.example.roomiematch.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.recipient = :recipient ORDER BY n.id DESC")
    List<Notification> findByRecipientOrderByidDesc(User recipient);
}
