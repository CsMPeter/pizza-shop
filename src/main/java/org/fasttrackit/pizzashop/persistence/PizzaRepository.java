package org.fasttrackit.pizzashop.persistence;


import org.fasttrackit.pizzashop.domain.Pizza;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza,Long> {

    Page<Pizza> findByNameContaining(String partialName, Pageable pageable);
    Page<Pizza>findByNameContainingAndQuantityGreaterThanEqual(String partialName,int minQuantity,Pageable pageable);
}
