package org.fasttrackit.pizzashop;

import org.fasttrackit.pizzashop.domain.Customer;
import org.fasttrackit.pizzashop.exception.ResourceNotFoundException;
import org.fasttrackit.pizzashop.service.CustomerService;
import org.fasttrackit.pizzashop.transfer.SaveCustomerRequest;
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
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_whenValidRequest_thenProductIsSaved() {

        createCustomer();


    }

    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thenThrowException() {
        SaveCustomerRequest request = new SaveCustomerRequest();
        //leaving request properties with null values to validate the negative flow

        customerService.createCustomer(request);
    }

    @Test
    public void testGetCustomer_whenExistingCustomer_thenRetrieveCustomer() {
        Customer createdCustomer = createCustomer();

        Customer retrievedCustomer = customerService.getCustomer(createdCustomer.getId());

        assertThat(retrievedCustomer, notNullValue());
        assertThat(createdCustomer.getId(), is(retrievedCustomer.getId()));
        assertThat(createdCustomer.getFirstName(), is(retrievedCustomer.getFirstName()));
        assertThat(createdCustomer.getLastName(), is(retrievedCustomer.getLastName()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetCustomer_whenCustomerDoesNotExist_thenThrowResourceNotFound() {
        customerService.getCustomer(999999999999999L);
    }

    @Test
    public void testUpdateCustomer_whenValidRequest_thenReturnUpdatedCustomer() {
        Customer createdCustomer = createCustomer();

        SaveCustomerRequest request = new SaveCustomerRequest();
        request.setFirstName(createdCustomer.getFirstName() + "updated");
        request.setLastName(createdCustomer.getLastName() + "updated");

        Customer updatedCustomer = customerService.updateCustomer(createdCustomer.getId(), request);

        assertThat(updatedCustomer, notNullValue());
        // assertThat(updatedCustomer.getId(), is(createdCustomer.getId()));
        assertThat(updatedCustomer.getFirstName(), is(request.getFirstName()));
        assertThat(updatedCustomer.getLastName(), is(request.getLastName()));

    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteCustomer_whenExistingCustomer_thenProductIsDeleted() {
        Customer customer = createCustomer();

        customerService.deleteCustomer(customer.getId());

        customerService.getCustomer(customer.getId());
    }

    private Customer createCustomer() {
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
