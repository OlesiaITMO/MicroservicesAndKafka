package org.itmo_olesia.presentationmicroservice.jpa;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.itmo_olesia.dto.Role.Role;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
@Data
@Table(name="User", schema = "public")
public class User  {
    @Getter
    @Setter
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Role role;

    @Column(unique = true)
    private String username;

    private String password;
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private Owner owner;


}