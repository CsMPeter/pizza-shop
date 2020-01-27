package org.fasttrackit.pizzashop;

import org.fasttrackit.pizzashop.domain.Customer;
import org.fasttrackit.pizzashop.service.CartService;
import org.fasttrackit.pizzashop.steps.CustomerSteps;
import org.fasttrackit.pizzashop.transfer.AddPizzaToCartRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartServiceTests {

    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerSteps customerSteps;

    @Test
    public void testAddPizzaToCart_whenNewCartForExistingCustomer_thenCartIsSaved(){

        Customer customer = customerSteps.createCustomer();

        AddPizzaToCartRequest request = new AddPizzaToCartRequest();
        request.setCustomerId(customer.getId());

        cartService.addPizzaToCart(request);
    }
}
