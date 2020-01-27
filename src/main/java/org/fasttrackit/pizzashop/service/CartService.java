package org.fasttrackit.pizzashop.service;

import org.fasttrackit.pizzashop.domain.Cart;
import org.fasttrackit.pizzashop.domain.Customer;
import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.exception.ResourceNotFoundException;
import org.fasttrackit.pizzashop.persistence.CartRepository;
import org.fasttrackit.pizzashop.transfer.AddPizzaToCartRequest;
import org.fasttrackit.pizzashop.transfer.CartResponse;
import org.fasttrackit.pizzashop.transfer.PizzaInCartResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


@Service
public class CartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;

    private final CustomerService customerService;

    private final PizzaService pizzaService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, PizzaService pizzaService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.pizzaService = pizzaService;
    }

    @Transactional
    public void addPizzaToCart(AddPizzaToCartRequest request) {
        LOGGER.info("Adding pizza to cart: ()", request);
        Cart cart = cartRepository.findById(request.getCustomerId())
                .orElse(new Cart());

        if (cart.getCustomer() == null) {
            LOGGER.info("New cart will be created. Retrieving customer to map the relationship ",
                    request.getCustomerId());
            Customer customer = customerService.getCustomer(request.getCustomerId());

            cart.setId(customer.getId());
            cart.setCustomer(customer);
        }

        Pizza pizza = pizzaService.getPizza(request.getPizzaId());
        cart.addToCart(pizza);

        cartRepository.save(cart);
    }

    @Transactional
    public CartResponse getCart(long id){
        LOGGER.info("Retrieving cart {}",id);
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart " + id + " does not exist."));

        CartResponse response = new CartResponse();
        response.setId(cart.getId());

        Set<PizzaInCartResponse> pizzasInCart = new HashSet<>();

        Iterator<Pizza> cartIterator = cart.getPizzas().iterator();

        while(cartIterator.hasNext()){
            Pizza pizza = cartIterator.next();
        //lazy loading fix so  the properties can be loaded
            PizzaInCartResponse pizzaResponse = new PizzaInCartResponse();
            pizzaResponse.setId(pizza.getId());
            pizzaResponse.setName(pizza.getName());
            pizzaResponse.setPrice(pizza.getPrice());

            pizzasInCart.add(pizzaResponse);
        }

        response.setPizzas(pizzasInCart);
        return response;
    }

}
