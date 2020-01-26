package org.fasttrackit.pizzashop.transfer;

import javax.validation.constraints.NotNull;

public class AddPizzaToCartRequest {
    
    @NotNull
    private Long pizzaId;
    @NotNull
    private Long customerId;

    public Long getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(Long pizzaId) {
        this.pizzaId = pizzaId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "AddPizzaToCartRequest{" +
                "pizzaId=" + pizzaId +
                ", customerId=" + customerId +
                '}';
    }
}
