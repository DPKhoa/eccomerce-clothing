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

import java.util.ArrayList;
import java.util.List;


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
                    cart.setItems(new ArrayList<>()); // Đảm bảo khởi tạo
                    user.getCarts().add(cart);
                    return getRepo().save(cart);
                });

    }
    @Transactional
    public CartEntity addItemToCart(Long userId, Long productId, int quantity){
        CartEntity cart = getOrCreateCart(userId);
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found"));
        System.out.println("Cart ID: " + cart.getId() + ", Items size: " + cart.getItems().size());
        // Kiễm tra xem sản phẩm có trong giỏ hàng không ?
        CartItemEntity existingItem = cart.getItems() != null ? cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null) : null;
        if(existingItem != null){
            existingItem.setQuantity(existingItem.getQuantity() + quantity);

        }else {
            CartItemEntity newItem =  CartItemEntity.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(quantity)
                    .priceAtTime((double) product.getPrice())
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
            cart.getItems().remove(item); // Hibernate sẽ xóa item nhờ orphanRemoval
        } else {
            item.setQuantity(quantity); // Cập nhật số lượng
        }
        return getRepo().save(cart); // Đảm bảo lưu lại nếu cần
    }

    @Transactional
    public void removeItemFromCart(Long userId, Long productId) {
        CartEntity cart = getOrCreateCart(userId);
        CartItemEntity item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item not found in cart"));
        cart.getItems().remove(item);
        getRepo().save(cart);
    }

    @Transactional
    public CartEntity getCart(Long userId) {
        List<CartEntity> carts = getRepo().findByUserId(userId);
        return carts.stream().findFirst().orElse(null); // Trả null nếu không có
    }

}
