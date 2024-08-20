package com.shoppingmall.service;

import com.shoppingmall.domain.Purchases;
import com.shoppingmall.repository.PurchasesRepository;
import jakarta.transaction.Transactional;

import java.util.List;

@Transactional
public class PurchaseService {

    private final PurchasesRepository purchasesRepository;

    public PurchaseService(PurchasesRepository purchasesRepository) {
        this.purchasesRepository = purchasesRepository;
    }

    public Purchases addPurchase(Purchases purchases){
        return purchasesRepository.addPurchases(purchases);
    }

    public List<Purchases> getPurchaseByUserId(String user_id){
        return purchasesRepository.getPurchasesByUserId(user_id);
    }
}
