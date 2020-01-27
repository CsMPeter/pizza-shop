package org.fasttrackit.pizzashop.transfer;



import java.util.Set;

public class CartResponse {

    private Long id;
    private Set<PizzaInCartResponse> pizzas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PizzaInCartResponse> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Set<PizzaInCartResponse> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public String toString() {
        return "CartResponse{" +
                "id=" + id +
                ", pizzas=" + pizzas +
                '}';
    }
}
