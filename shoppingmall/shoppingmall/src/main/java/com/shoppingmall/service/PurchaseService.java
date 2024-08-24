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

    public Purchases addPurchase(Purchases purchases) {
        return purchasesRepository.addPurchases(purchases);
    }

    public List<Purchases> getPurchaseByUserId(String user_id) {
        return purchasesRepository.getPurchasesByUserId(user_id);
    }

    public List<Purchases> getPurchaseWithinOneMonth(String user_id) {
        return purchasesRepository.getPurchaseWithinOneMonth(user_id);
    }

    public List<Purchases> findByUserIdAndOrderId(String user_id, String order_id) {
        return purchasesRepository.findByUserIdAndOrderId(user_id, order_id);
    }

    public List<Purchases> findByProductIdAndOrderId(int product_id, String order_id) {
        return purchasesRepository.findByProductIdAndOrderId(product_id, order_id);
    }

    public Purchases findByThreeId(String user_id, int product_id, String order_id){ return purchasesRepository.findByThreeId(user_id, product_id, order_id); }
}