package org.fasttrackit.pizzashop;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.exception.ResourceNotFoundException;
import org.fasttrackit.pizzashop.service.PizzaService;
import org.fasttrackit.pizzashop.steps.PizzaSteps;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PizzaServiceTests {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private PizzaSteps pizzaSteps;

    @Test
    public void testCreatePizza_whenValidRequest_thenProductIsSaved() {

        pizzaSteps.createPizza();


    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thenThrowException() {
        SavePizzaRequest request = new SavePizzaRequest();
        //leaving request properties with null values to validate the negative flow

        pizzaService.createPizza(request);
    }

    @Test
    public void testGetPizza_whenExistingPizza_thenRetrievePizza() {
        Pizza createdPizza = pizzaSteps.createPizza();

        Pizza retrievedPizza = pizzaService.getPizza(createdPizza.getId());

        assertThat(retrievedPizza, notNullValue());
        assertThat(createdPizza.getId(), is(retrievedPizza.getId()));
        assertThat(createdPizza.getPrice(), is(retrievedPizza.getPrice()));
        assertThat(createdPizza.getIngredients(), is(retrievedPizza.getIngredients()));
        assertThat(createdPizza.getName(), is(retrievedPizza.getName()));
        assertThat(createdPizza.getQuantity(), is(retrievedPizza.getQuantity()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetPizza_whenPizzaDoesNotExist_thenThrowResourceNotFound() {
        pizzaService.getPizza(999999999999999L);
    }

    @Test
    public void testUpdatePizza_whenValidRequest_thenReturnUpdatedPizza() {
        Pizza createdPizza = pizzaSteps.createPizza();

        SavePizzaRequest request = new SavePizzaRequest();
        request.setName(createdPizza.getName() + "updated");
        request.setIngredients(createdPizza.getIngredients() + "updated");
        request.setPrice(createdPizza.getPrice() + 10);
        request.setQuantity(createdPizza.getQuantity() + 10);

        Pizza updatedPizza = pizzaService.updatePizza(createdPizza.getId(), request);

        assertThat(updatedPizza, notNullValue());
       // assertThat(updatedPizza.getId(), is(createdPizza.getId()));
        assertThat(updatedPizza.getPrice(), is(request.getPrice()));
        assertThat(updatedPizza.getIngredients(), is(request.getIngredients()));
        assertThat(updatedPizza.getName(), is(request.getName()));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeletePizza_whenExistingPizza_thenProductIsDeleted(){
        Pizza pizza = pizzaSteps.createPizza();

        pizzaService.deletePizza(pizza.getId());

        pizzaService.getPizza(pizza.getId());
    }




}
