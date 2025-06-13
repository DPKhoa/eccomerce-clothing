package com.app.projectstyleecommerce.service;

import com.app.projectstyleecommerce.entity.CartEntity;

public interface CartService extends CommonService<CartEntity, Long> {
     CartEntity addItemToCart(Long userId, Long productId, int quantity);
     CartEntity updateItemQuantity(Long userId, Long productId, int quantity);
     void removeItemFromCart(Long userId, Long productId);
     CartEntity getCart(Long userId);
}
