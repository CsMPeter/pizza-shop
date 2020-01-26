package org.fasttrackit.pizzashop.service;

import org.fasttrackit.pizzashop.domain.Cart;
import org.fasttrackit.pizzashop.domain.Customer;
import org.fasttrackit.pizzashop.persistence.CartRepository;
import org.fasttrackit.pizzashop.transfer.AddPizzaToCartRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final CustomerService customerService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
    }

    @Transactional
    public void addPizzaToCart(AddPizzaToCartRequest request) {
        Cart cart = cartRepository.findById(request.getCustomerId())
                .orElse(new Cart());

        if (cart.getCustomer() == null) {
            LOGGER.info("New cart will be created. Retrieving customer to map the relationship ",
                    request.getCustomerId());
            Customer customer = customerService.getCustomer(request.getCustomerId());

            cart.setCustomer(customer);
        }

        cartRepository.save(cart);
    }

}
