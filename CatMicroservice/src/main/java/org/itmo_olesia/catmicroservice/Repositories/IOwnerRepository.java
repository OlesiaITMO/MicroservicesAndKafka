package org.itmo_olesia.catmicroservice.Repositories;


import org.itmo_olesia.catmicroservice.jpa.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Optional;

@Repository
public interface IOwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByName(String name);
    void deleteByName(String name);
    LinkedList<Owner> findByBirthday(LocalDate date);
}
