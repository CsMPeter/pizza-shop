package org.fasttrackit.pizzashop.steps;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.service.PizzaService;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class PizzaSteps {

    @Autowired
    private PizzaService pizzaService;

    public Pizza createPizza() {
        SavePizzaRequest request = new SavePizzaRequest();
        request.setName("Diavola" + System.currentTimeMillis());
        request.setPrice(20.0);
        request.setIngredients("mozarella, tomato sauce, pepperoni");
        request.setQuantity(1);

        Pizza createdPizza = pizzaService.createPizza(request);

        assertThat(createdPizza, notNullValue());
        assertThat(createdPizza.getId(), notNullValue());
        assertThat(createdPizza.getId(), greaterThan(0L));
        assertThat(createdPizza.getName(), is(request.getName()));
        assertThat(createdPizza.getIngredients(), is(request.getIngredients()));
        assertThat(createdPizza.getImageUrl(), is(request.getImageUrl()));
        assertThat(createdPizza.getPrice(), is(request.getPrice()));
        assertThat(createdPizza.getQuantity(), is(request.getQuantity()));

        return createdPizza;
    }
}
