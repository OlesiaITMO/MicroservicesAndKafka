package org.itmo_olesia.ownermicroservice.Repositories;


import org.itmo_olesia.ownermicroservice.jpa.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    void deleteByUsername(String username);
    User getByUsername(String username);
}
