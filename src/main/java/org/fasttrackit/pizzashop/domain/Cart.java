package org.fasttrackit.pizzashop.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cart {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Customer customer;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "cart_pizza",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "pizza_id"))
    private Set<Pizza> pizzas = new HashSet<>();

    public void addToCart(Pizza pizza) {
        //adding received pizza to the current cart
        pizzas.add(pizza);

        //adding current cart to the cart set of the received pizza
        pizza.getCarts().add(this);
    }

    public void removeFromCart(Pizza pizza) {
        pizzas.remove(pizza);

        pizza.getCarts().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", customer=" + customer +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        return id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
