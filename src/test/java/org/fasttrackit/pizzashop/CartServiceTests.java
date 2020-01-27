package org.fasttrackit.pizzashop;

import org.fasttrackit.pizzashop.domain.Cart;
import org.fasttrackit.pizzashop.domain.Customer;
import org.fasttrackit.pizzashop.domain.Pizza;
import org.fasttrackit.pizzashop.service.CartService;
import org.fasttrackit.pizzashop.steps.CustomerSteps;
import org.fasttrackit.pizzashop.steps.PizzaSteps;
import org.fasttrackit.pizzashop.transfer.AddPizzaToCartRequest;
import org.fasttrackit.pizzashop.transfer.CartResponse;
import org.fasttrackit.pizzashop.transfer.PizzaInCartResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerSteps customerSteps;

    @Autowired
    private PizzaSteps pizzaSteps;

    @Test
    public void testAddPizzaToCart_whenNewCartForExistingCustomer_thenCartIsSaved(){

        Customer customer = customerSteps.createCustomer();
        Pizza pizza = pizzaSteps.createPizza();

        AddPizzaToCartRequest request = new AddPizzaToCartRequest();
        request.setCustomerId(customer.getId());
        request.setPizzaId(pizza.getId());

        cartService.addPizzaToCart(request);

        CartResponse cart = cartService.getCart(customer.getId());

        assertThat(cart.getId(),is(customer.getId()));

        Iterator<PizzaInCartResponse> iterator = cart.getPizzas().iterator();
        assertThat(iterator.hasNext(),is(true));

        PizzaInCartResponse pizzaFromCart = iterator.next();

        assertThat(pizzaFromCart,notNullValue());
        assertThat(pizzaFromCart.getId(),is(pizza.getId()));
        assertThat(pizzaFromCart.getName(),is(pizza.getName()));
        assertThat(pizzaFromCart.getPrice(),is(pizza.getPrice()));

    }
}
