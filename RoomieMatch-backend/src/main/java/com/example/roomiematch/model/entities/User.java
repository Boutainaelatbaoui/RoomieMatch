package com.example.roomiematch.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.roomiematch.model.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    private String telephone;

    private String bio;

    private double budget;

    private int occupation;

    private int gender;

    private String birthdate;

    private String profilePicture;

    @OneToOne
    @JoinColumn(name = "preference_id")
    private Preference preference;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "current_city_id")
    private City currentCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "desired_city_id")
    private City desiredCity;

    @OneToMany(mappedBy = "user")
    private List<QuestionnaireResponse> questionnaireResponses;

    @OneToMany(mappedBy = "user")
    private List<UserQuestionPreference> userQuestionPreferences;

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName().toString()));

            for (Privilege privilege : role.getPrivileges()) {
                authorities.add(new SimpleGrantedAuthority(privilege.getName().name()));
            }
        }

        System.out.println(authorities);

        return authorities;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
