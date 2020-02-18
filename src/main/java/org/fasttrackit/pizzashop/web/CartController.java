package org.fasttrackit.pizzashop.web;

import org.fasttrackit.pizzashop.service.CartService;
import org.fasttrackit.pizzashop.transfer.AddPizzaToCartRequest;
import org.fasttrackit.pizzashop.transfer.CartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PutMapping
    public ResponseEntity addPizzaToCart(@RequestBody @Valid AddPizzaToCartRequest request){
        cartService.addPizzaToCart(request);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity removePizzaFromCart(@RequestBody @Valid AddPizzaToCartRequest request){
        cartService.removePizzaFromCart(request);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartResponse> getCart(@PathVariable long id){

        CartResponse cart = cartService.getCart(id);
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }


}
