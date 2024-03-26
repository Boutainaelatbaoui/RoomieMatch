package com.example.roomiematch.repository;

import com.example.roomiematch.model.entities.Preference;
import com.example.roomiematch.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findById(Integer id);
    @Query("SELECT u.gender FROM User u WHERE u.email = ?1")
    int findGenderByEmail(String email);
    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    User findByPreference(Preference preference);
    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN Request r ON (u = r.sender OR u = r.recipient) " +
            "WHERE (r.sender = :connectedUser OR r.recipient = :connectedUser) " +
            "AND r.status = 'ACCEPTED'")
    List<User> findUsersWithAcceptedRequests(@Param("connectedUser") User connectedUser);

}
