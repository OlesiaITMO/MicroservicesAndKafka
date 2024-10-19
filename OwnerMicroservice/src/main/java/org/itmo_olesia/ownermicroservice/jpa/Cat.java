package org.itmo_olesia.ownermicroservice.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Cat", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cat {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthday;
    private String breed;
    private Color color;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany
    @JoinTable(name="cat_friends",
            joinColumns=@JoinColumn(name="cat_id"),
            inverseJoinColumns=@JoinColumn(name="friend_cat_id")
    )
    private List<Cat> friends;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

