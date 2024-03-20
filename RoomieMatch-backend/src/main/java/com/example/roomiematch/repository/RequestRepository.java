package com.example.roomiematch.repository;

import com.example.roomiematch.enums.RequestStatus;
import com.example.roomiematch.model.entities.Request;
import com.example.roomiematch.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByRecipientAndStatus(User recipient, RequestStatus status);
    boolean existsBySenderEmailAndRecipientEmail(String senderEmail, String recipientEmail);
    List<Request> findBySender(User sender);
    List<Request> findByRecipient(User recipient);
    List<Request> findBySenderEmailAndStatus(String recipientEmail, RequestStatus status);


}
