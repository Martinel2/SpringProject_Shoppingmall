package com.shoppingmall.service;

import com.shoppingmall.domain.Purchases;
import com.shoppingmall.repository.PurchasesRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
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

    public List<Purchases> getPurchaseTerm(String user_id, LocalDateTime start, LocalDateTime end) {
        return purchasesRepository.getPurchaseTerm(user_id, start, end);
    }

    public List<Purchases> getPurchaseTermPlusKeyword(String user_id, LocalDateTime start, LocalDateTime end, String keyword) {
        return purchasesRepository.getPurchaseTermPlusKeyword(user_id, start, end, keyword);
    }


    public List<Purchases> findByUserIdAndOrderId(String user_id, String order_id) {
        return purchasesRepository.findByUserIdAndOrderId(user_id, order_id);
    }

    public List<Purchases> findByProductIdAndOrderId(int product_id, String order_id) {
        return purchasesRepository.findByProductIdAndOrderId(product_id, order_id);
    }

    public Purchases findByThreeId(String user_id, int product_id, String order_id){ return purchasesRepository.findByThreeId(user_id, product_id, order_id); }

    public Purchases findById(int id) { return purchasesRepository.findById(id); }

    public boolean deletePurchase(Purchases purchases) { return purchasesRepository.deletePurchases(purchases); }
}
