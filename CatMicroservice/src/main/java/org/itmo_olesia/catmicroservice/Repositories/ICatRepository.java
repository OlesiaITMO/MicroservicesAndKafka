package org.itmo_olesia.catmicroservice.Repositories;

import org.itmo_olesia.catmicroservice.jpa.Cat;
import org.itmo_olesia.dto.Entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ICatRepository extends JpaRepository<Cat, Long> {
    Optional<Cat> findByName(String name);
    List<Cat> findByBirthday(LocalDate date);
    List<Cat> findByColor(Color color);
    List<Cat> findByBreed(String breed);
}
