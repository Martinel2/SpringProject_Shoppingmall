package com.shoppingmall.service;

import com.shoppingmall.domain.Seller;
import com.shoppingmall.domain.Users;
import com.shoppingmall.repository.SellerRepository;
import jakarta.transaction.Transactional;

@Transactional
public class SellerService {
    private final SellerRepository sellerRepository;


    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller findById(String id){
        return sellerRepository.findById(id);
    }

    public Seller join(Users users, String company_id, String company_name){
        Seller seller = new Seller();
        seller.setUser(users);
        seller.setId(users.getId());
        seller.setCompany_id(company_id);
        seller.setCompany_name(company_name);
        sellerRepository.add(seller);
        return seller;
    }
}
