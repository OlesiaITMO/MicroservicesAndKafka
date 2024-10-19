package org.itmo_olesia.ownermicroservice.Repositories;


import org.itmo_olesia.ownermicroservice.jpa.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByName(String name);
    void deleteByName(String name);
    List<Owner> findByBirthday(LocalDate date);
}
