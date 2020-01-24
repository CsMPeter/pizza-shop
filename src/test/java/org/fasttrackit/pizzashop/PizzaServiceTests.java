package org.fasttrackit.pizzashop;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.service.PizzaService;
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

	@Test
	public void testCreatePizza_whenValidRequest_thenProductIsSaved() {

		SavePizzaRequest request = new SavePizzaRequest();
		request.setName("Diavola" + System.currentTimeMillis());
		request.setPrice(20.0);
		request.setIngredients("mozarella, tomato sauce, pepperoni");

		Pizza createdPizza = pizzaService.createPizza(request);

		assertThat(createdPizza, notNullValue());
		assertThat(createdPizza.getId(),notNullValue());
		assertThat(createdPizza.getId(),greaterThan(0L));
		assertThat(createdPizza.getName(),is(request.getName()));
		assertThat(createdPizza.getIngredients(),is(request.getIngredients()));
		assertThat(createdPizza.getImageUrl(),is(request.getImageUrl()));
		assertThat(createdPizza.getPrice(),is(request.getPrice()));


	}

	@Test(expected = TransactionSystemException.class)
	public void testCreateProduct_whenInvalidRequest_thenThrowException(){
		SavePizzaRequest request = new SavePizzaRequest();
		//leaving request properties with null values tovalidate the negative flow

		pizzaService.createPizza(request);
	}

}
