package me.matteogiovagnotti.springlamiapizzeria.repositories;

import me.matteogiovagnotti.springlamiapizzeria.models.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoRepository extends JpaRepository<Promo, Integer> {
}
