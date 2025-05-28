package com.shoppingmall.Coupon.Scheduler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;

public class CouponScheduler {

    @PersistenceContext
    private final EntityManager em;

    public CouponScheduler(EntityManager em) {
        this.em = em;
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    @Transactional
    public void updateValidTermsAndDeleteExpiredRows() {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // validTerm이 0보다 큰 경우 1 감소시키기
            em.createQuery("UPDATE Coupon e SET e.validTerm = e.validTerm - 1 WHERE e.validTerm > 0")
                    .executeUpdate();

            // validTerm이 0인 행 삭제하기
            em.createQuery("DELETE FROM Coupon e WHERE e.validTerm <= 0")
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }
}

