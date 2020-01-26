package org.fasttrackit.pizzashop.web;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.service.PizzaService;
import org.fasttrackit.pizzashop.transfer.GetPizzasRequest;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @PostMapping
    public ResponseEntity<Pizza> createPizza(@RequestBody @Valid SavePizzaRequest request) {
        Pizza pizza = pizzaService.createPizza(request);
        return new ResponseEntity<>(pizza, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Pizza> getPizza(@PathVariable Long id) {
        Pizza pizza = pizzaService.getPizza(id);
        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Pizza>> getPizzas(GetPizzasRequest request, Pageable pageable) {
        Page<Pizza> pizzas = pizzaService.getPizzas(request, pageable);
        return new ResponseEntity<>(pizzas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pizza> updatePizza(@PathVariable Long id, @RequestBody @Valid SavePizzaRequest request) {
        Pizza pizza = pizzaService.updatePizza(id, request);
        return new ResponseEntity<>(pizza, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePizza(@PathVariable Long id) {
        pizzaService.deletePizza(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
