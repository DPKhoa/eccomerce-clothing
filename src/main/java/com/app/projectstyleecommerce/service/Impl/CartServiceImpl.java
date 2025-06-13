package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.CartEntity;
import com.app.projectstyleecommerce.entity.CartItemEntity;
import com.app.projectstyleecommerce.entity.ProductEntity;
import com.app.projectstyleecommerce.entity.UserEntity;
import com.app.projectstyleecommerce.repository.CartRepository;
import com.app.projectstyleecommerce.repository.ProductRepository;
import com.app.projectstyleecommerce.repository.UserRepository;
import com.app.projectstyleecommerce.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl extends CommonServiceImpl<CartEntity, Long, CartRepository> implements CartService  {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository repo, UserRepository userRepository, ProductRepository productRepository) {
        super(repo);
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartEntity getOrCreateCart(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<CartEntity> carts = getRepo().findByUserId(userId);
        return carts.stream()
                .findFirst()
                .orElseGet(() -> {
                    CartEntity cart = CartEntity.builder()
                            .user(user)
                            .build();
                    user.getCarts().add(cart);
                    return getRepo().save(cart);
                });


    }
    @Transactional
    public CartEntity addItemToCart(Long userId, Long productId, int quantity){
        CartEntity cart = getOrCreateCart(userId);
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        // Kiễm tra xem sản phẩm có trong giỏ hàng không ?
        CartItemEntity existingItem = cart.getItems().stream().filter(item -> item.getProduct().equals(productId)).findFirst().orElse(null);
        if(existingItem != null){
            existingItem.setQuantity((int) (existingItem.getQuantity() + quantity));

        }else {
            CartItemEntity newItem =  CartItemEntity.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .priceAtTime(String.valueOf(product.getPrice()))
                    .build();
            cart.getItems().add(newItem);
        }
        return getRepo().save(cart);

    }
    @Transactional
    public CartEntity updateItemQuantity(Long userId, Long productId, int quantity) {
        CartEntity cart = getOrCreateCart(userId);
        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        if (quantity <= 0) {
            cart.getItems().remove(item);
            getRepo().delete(item.getCart());
        } else {
            item.setQuantity(quantity);
        }
        return getRepo().save(cart);
    }

    @Transactional
    public void removeItemFromCart(Long userId, Long productId) {
        CartEntity cart = getOrCreateCart(userId);
        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cart.getItems().remove(item);
        getRepo().delete(item.getCart());
        getRepo().save(cart);
    }

    @Transactional
    public CartEntity getCart(Long userId) {
        List<CartEntity> carts = getRepo().findByUserId(userId);
        return (CartEntity) carts;
    }

}
