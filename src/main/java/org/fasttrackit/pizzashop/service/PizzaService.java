package org.fasttrackit.pizzashop.service;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.exception.ResourceNotFoundException;
import org.fasttrackit.pizzashop.persistence.PizzaRepository;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

        return pizzaRepository.save(pizza);
    }

    public Pizza getPizza(long id) {
        LOGGER.info("Retrieving pizza {}", id);
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza " + id + " does not exist."));
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
