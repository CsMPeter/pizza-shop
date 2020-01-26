package org.fasttrackit.pizzashop.steps;

import org.fasttrackit.pizzashop.domain.Customer;
import org.fasttrackit.pizzashop.service.CustomerService;
import org.fasttrackit.pizzashop.transfer.SaveCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@Component
public class CustomerSteps {

    @Autowired
    private CustomerService customerService;

    public Customer createCustomer() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName("Ion" + System.currentTimeMillis());
        request.setLastName("Trif" + System.currentTimeMillis());

        Customer createdCustomer = customerService.createCustomer(request);

        assertThat(createdCustomer, notNullValue());
        assertThat(createdCustomer.getId(), notNullValue());
        assertThat(createdCustomer.getId(), greaterThan(0L));
        assertThat(createdCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(createdCustomer.getLastName(), is(request.getLastName()));

        return createdCustomer;
    }
}
