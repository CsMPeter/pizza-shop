package org.fasttrackit.pizzashop.persistence;

import org.fasttrackit.pizzashop.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
