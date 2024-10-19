package org.itmo_olesia.catmicroservice.jpa;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "Owner", schema = "public")
@Data
public class Owner {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDate birthday;

    @OneToMany(mappedBy = "id")
    private List<Cat> cats;

}

