package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.entity.CartEntity;
import com.app.projectstyleecommerce.service.CartService;
import com.app.projectstyleecommerce.util.UrlUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UrlUtil.CART_URL)
public class CartController extends CommonController<CartEntity, Long, CartService> {
    public CartController(CartService service) {
        super(service);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<CartEntity> getCart(@PathVariable Long userId) {
        CartEntity cart = getService().getCart(userId);
        return ResponseEntity.ok(cart);
    }
    @PostMapping("/{userId}/items")
    public ResponseEntity<CartEntity> addItemToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {
        CartEntity updatedCart = getService().addItemToCart(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }


    @PutMapping("/{userId}/items/{productId}")
    public ResponseEntity<CartEntity> updateItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @RequestParam int quantity) {
        CartEntity updatedCart = getService().updateItemQuantity(userId, productId, quantity);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{userId}/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        getService().removeItemFromCart(userId, productId);
        return ResponseEntity.noContent().build();
    }
}
