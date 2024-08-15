package com.shoppingmall.service;

import com.shoppingmall.domain.Products;
import com.shoppingmall.domain.Users;
import com.shoppingmall.domain.Wishlist;
import com.shoppingmall.repository.WishlistRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist save(Users user, Products products){
        return wishlistRepository.save(user, products);
    }

    public List<Wishlist> getAllWishlistByUserId(String id){
        return wishlistRepository.getWishlistByUserId(id);
    }
    public List<Wishlist> getAllWishlistByProductId(int id){
        return wishlistRepository.getWishlistByProductId(id);
    }

    public Wishlist findByTwoId(String userId, int productId){
        List<Wishlist> wishlists = wishlistRepository.getWishlistByUserId(userId);
        for(Wishlist w : wishlists){
            Products p = w.getProduct();
            if(p.getId() == productId){
                return w;
            }
        }
        return null;
    }

    public boolean deleteWishlist(int id) { return wishlistRepository.deleteWishlist(id); }
}
