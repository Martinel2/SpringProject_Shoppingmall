package com.shoppingmall.service;

import com.shoppingmall.domain.Cart;
import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.CartRepository;
import com.shoppingmall.repository.ProductRepository;
import com.shoppingmall.repository.UserRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Cart addCart(String userId, int productId, int quantity){
        Users user = userRepository.findById(userId);
        Products products = productRepository.findById(productId);
        // 장바구니 정보
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProducts(products);
        cart.setQuantity(quantity);

        //저장
        return cartRepository.addCart(cart);
    }

    public List<Cart> getCart(String userId){
        return cartRepository.getCart(userId);
    }
}
