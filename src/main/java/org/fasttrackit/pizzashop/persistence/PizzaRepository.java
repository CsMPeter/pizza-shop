package org.fasttrackit.pizzashop.persistence;


import org.fasttrackit.pizzashop.domain.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza,Long> {

}
