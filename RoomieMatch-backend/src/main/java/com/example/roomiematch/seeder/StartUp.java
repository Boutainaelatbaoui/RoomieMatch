package com.example.roomiematch.seeder;

import com.example.roomiematch.enums.Permission;
import com.example.roomiematch.enums.RoleName;
import com.example.roomiematch.model.entities.Privilege;
import com.example.roomiematch.model.entities.Role;
import com.example.roomiematch.model.entities.User;
import com.example.roomiematch.repository.PrivilegeRepository;
import com.example.roomiematch.repository.RoleRepository;
import com.example.roomiematch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class StartUp implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StartUp(UserRepository userRepository,
                   RoleRepository roleRepository,
                   PrivilegeRepository privilegeRepository,
                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        Privilege readPrivilege = createPrivilegeIfNotFound(Permission.CAN_READ);
        Privilege updatePrivilege = createPrivilegeIfNotFound(Permission.CAN_UPDATE);
        Privilege deletePrivilege = createPrivilegeIfNotFound(Permission.CAN_DELETE);
        Privilege manageUsersPrivilege = createPrivilegeIfNotFound(Permission.CAN_MANAGE_USERS);
        Privilege updateProfilePrivilege = createPrivilegeIfNotFound(Permission.CAN_UPDATE_PROFILE);
        Privilege deleteProfilePrivilege = createPrivilegeIfNotFound(Permission.CAN_DELETE_PROFILE);

        Role adminRole = createRoleIfNotFound(RoleName.ADMIN, Arrays.asList(readPrivilege, updatePrivilege, updateProfilePrivilege, deletePrivilege, deleteProfilePrivilege));
        Role userRole = createRoleIfNotFound(RoleName.USER, Arrays.asList(readPrivilege, updateProfilePrivilege, deleteProfilePrivilege));
        Role superAdminRole = createRoleIfNotFound(RoleName.MANAGER, Arrays.asList(readPrivilege, updatePrivilege, updateProfilePrivilege, deletePrivilege, deleteProfilePrivilege, manageUsersPrivilege));

        User adminUser = createUser(
                "Admin",
                "User",
                "admin@example.com",
                "adminPassword",
                "adminTel",
                "Admin bio",
                1000.0,
                1,
                1,
                LocalDate.parse("1990-01-01"),
                "adminProfilePicture",
                adminRole);

        userRepository.save(adminUser);

        User regularUser = createUser(
                "Regular",
                "User",
                "user@example.com",
                "userPassword",
                "userTel",
                "Regular bio",
                500.0,
                2,
                2,
                LocalDate.parse("1995-05-05"),
                "userProfilePicture",
                userRole);

        userRepository.save(regularUser);

        User managerUser = createUser(
                "Manager",
                "User",
                "manager@example.com",
                "managerPassword",
                "managerTel",
                "Manager bio",
                800.0,
                3,
                1,
                LocalDate.parse("1985-10-10"),
                "managerProfilePicture",
                superAdminRole);

        userRepository.save(managerUser);

    }

    private Privilege createPrivilegeIfNotFound(Permission name) {
        return privilegeRepository.findByName(name)
                .orElseGet(() -> privilegeRepository.save(Privilege.builder().name(name).build()));
    }

    private Role createRoleIfNotFound(RoleName name, List<Privilege> privileges) {
        return roleRepository.findByName(name)
                .orElseGet(() -> roleRepository.save(Role.builder().name(name).privileges(privileges).build()));
    }

    private User createUser(
            String firstName,
            String lastName,
            String email,
            String password,
            String telephone,
            String bio,
            double budget,
            int occupation,
            int gender,
            LocalDate birthdate,
            String profilePicture,
            Role role) {

        return userRepository.save(User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .telephone(telephone)
                .bio(bio)
                .budget(budget)
                .occupation(occupation)
                .gender(gender)
                .birthdate(birthdate)
                .profilePicture(profilePicture)
                .role(role)
                .build());
    }


}
