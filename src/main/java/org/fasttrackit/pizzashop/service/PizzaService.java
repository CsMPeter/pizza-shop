package org.fasttrackit.pizzashop.service;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.exception.ResourceNotFoundException;
import org.fasttrackit.pizzashop.persistence.PizzaRepository;
import org.fasttrackit.pizzashop.transfer.GetPizzasRequest;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaService.class);
    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public Pizza createPizza(SavePizzaRequest request) {
        LOGGER.info("Creating pizza {}", request);
        Pizza pizza = new Pizza();
        pizza.setName(request.getName());
        pizza.setImageUrl(request.getImageUrl());
        pizza.setIngredients(request.getIngredients());
        pizza.setPrice(request.getPrice());
        pizza.setQuantity(request.getQuantity());

        return pizzaRepository.save(pizza);
    }

    public Pizza getPizza(long id) {
        LOGGER.info("Retrieving pizza {}", id);
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza " + id + " does not exist."));
    }

    public Page<Pizza> getPizzas(GetPizzasRequest request, Pageable pageable){
        LOGGER.info("Retrieving products: {}",request);
        if(request != null && request.getPartialName() != null && request.getMinQuantity() != null)
            return pizzaRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(),request.getMinQuantity(),pageable);
        else if(request != null && request.getPartialName() != null)
            return pizzaRepository.findByNameContaining(request.getPartialName(),pageable);
        else
            return pizzaRepository.findAll(pageable);
    }

    public Pizza updatePizza(long id, SavePizzaRequest request) {
        LOGGER.info("Updating Pizza {}: {}", id, request);
        Pizza pizza = getPizza(id);
        BeanUtils.copyProperties(request, pizza); //(din unde,pe unde)
        return pizzaRepository.save(pizza);
    }

    public void deletePizza(long id) {
        LOGGER.info("Deleting pizza {}", id);
        pizzaRepository.deleteById(id);
    }



}
