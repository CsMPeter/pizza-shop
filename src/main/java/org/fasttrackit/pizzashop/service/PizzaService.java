package org.fasttrackit.pizzashop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.exception.ResourceNotFoundException;
import org.fasttrackit.pizzashop.persistence.PizzaRepository;
import org.fasttrackit.pizzashop.transfer.GetPizzasRequest;
import org.fasttrackit.pizzashop.transfer.PizzaResponse;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PizzaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PizzaService.class);
    private final PizzaRepository pizzaRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, ObjectMapper objectMapper) {
        this.pizzaRepository = pizzaRepository;
        this.objectMapper = objectMapper;
    }

    public Pizza createPizza(SavePizzaRequest request) {
        LOGGER.info("Creating pizza {}", request);
        Pizza pizza = objectMapper.convertValue(request,Pizza.class);

//        Pizza pizza = new Pizza();
//        pizza.setName(request.getName());
//        pizza.setImageUrl(request.getImageUrl());
//        pizza.setIngredients(request.getIngredients());
//        pizza.setPrice(request.getPrice());
//        pizza.setQuantity(request.getQuantity());

        return pizzaRepository.save(pizza);
    }

    public Pizza getPizza(long id) {
        LOGGER.info("Retrieving pizza {}", id);
        return pizzaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pizza " + id + " does not exist."));
    }

    @Transactional
    public Page<PizzaResponse> getPizzas(GetPizzasRequest request, Pageable pageable){
        LOGGER.info("Retrieving products: {}",request);

        Page<Pizza> pizzas;

        if(request != null && request.getPartialName() != null && request.getMinQuantity() != null)
            pizzas = pizzaRepository.findByNameContainingAndQuantityGreaterThanEqual(
                    request.getPartialName(),request.getMinQuantity(),pageable);
        else if(request != null && request.getPartialName() != null)
            pizzas = pizzaRepository.findByNameContaining(request.getPartialName(),pageable);
        else
            pizzas = pizzaRepository.findAll(pageable);

        List<PizzaResponse> pizzaResponses = new ArrayList<>();

        for(Pizza pizza : pizzas.getContent()){
            PizzaResponse pizzaResponse = new PizzaResponse();
            pizzaResponse.setId(pizza.getId());
            pizzaResponse.setName(pizza.getName());
            pizzaResponse.setPrice(pizza.getPrice());
            pizzaResponse.setIngredients(pizza.getIngredients());
            pizzaResponse.setQuantity(pizza.getQuantity());
            pizzaResponse.setImageUrl(pizza.getImageUrl());

            pizzaResponses.add(pizzaResponse);
        }

        return new PageImpl<>(pizzaResponses,pageable,pizzas.getTotalElements());
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
