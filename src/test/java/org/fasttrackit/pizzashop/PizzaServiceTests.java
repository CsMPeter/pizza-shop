package org.fasttrackit.pizzashop;

import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.service.PizzaService;
import org.fasttrackit.pizzashop.transfer.SavePizzaRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
class PizzaServiceTests {

	@Autowired
	private PizzaService pizzaService;

	@Test
	void testCreatePizza_whenValidRequest_thenProductIsSaved() {

		SavePizzaRequest request = new SavePizzaRequest();
		request.setName("Diavola");
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

}
