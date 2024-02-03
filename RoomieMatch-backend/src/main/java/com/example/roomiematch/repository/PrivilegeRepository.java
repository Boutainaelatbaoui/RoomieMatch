package com.example.roomiematch.repository;

import com.example.roomiematch.enums.Permission;
import com.example.roomiematch.model.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByName(Permission name);
}
