package me.matteogiovagnotti.springlamiapizzeria.repositories;

import java.util.Optional;

import me.matteogiovagnotti.springlamiapizzeria.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Optional<User> findByEmail(String email);
}
