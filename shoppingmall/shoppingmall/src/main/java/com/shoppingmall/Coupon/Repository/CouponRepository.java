package com.shoppingmall.Coupon.Repository;

import com.shoppingmall.Coupon.Domain.Coupon;
import com.shoppingmall.Coupon.Domain.CouponList;
import com.shoppingmall.User.Domain.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CouponRepository {

    @PersistenceContext
    private final EntityManager em;

    public CouponRepository(EntityManager em) {
        this.em = em;
    }

    //쿠폰 적용 메소드
    public int useCoupon(int price, Coupon coupon){
        return price*(1-(coupon.getPercent()/100));
    }

    //쿠폰 유효기간 표시 메소드
    public LocalDate createdDate(Coupon coupon){
        LocalDate currentDate = LocalDate.now();
        return currentDate.minusDays(coupon.getValidTerm());
    }

    public LocalDate expiredDate(Coupon coupon){
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(coupon.getValidTerm());
    }

    //소유자id로 쿠폰 리스트 찾기
    public List<CouponList> findListByUserId(String id){
        TypedQuery<CouponList> query = em.createQuery("SELECT u FROM CouponList u WHERE u.user.id = :id", CouponList.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    //쿠폰id로 쿠폰 리스트 찾기
    public List<CouponList> findListByCouponId(int id){
        TypedQuery<CouponList> query = em.createQuery("SELECT u FROM CouponList u WHERE u.coupon.id = :id", CouponList.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Coupon findCouponById(int id){
        return em.find(Coupon.class, id);
    }

    public List<Coupon> makeRealCouponList(List<CouponList> couponList){
        List<Coupon> coupons = new ArrayList<>();
        for (CouponList c: couponList) {
            coupons.add(c.getCoupon());
        }
        return coupons;
    }

    public boolean duplicateCoupon(List<CouponList> couponLists, Coupon coupon){
        for (CouponList c: couponLists) {
            if(c.getCoupon_id() == coupon.getId())
                return true;
        }
        return false;
    }

    public CouponList saveCoupon(Users user, Coupon coupon){
        List<CouponList> list = findListByUserId(user.getId());
        if(duplicateCoupon(list,coupon)) return null;

        CouponList couponList = new CouponList();
        couponList.setUser(user);
        couponList.setCoupon(coupon);
        couponList.setOwn_id(user.getId());
        couponList.setCoupon_id(coupon.getId());
        em.persist(couponList);
        return couponList;
    }

    public List<Coupon> getAllCoupon(){
        String query = "SELECT c FROM Coupon c";
        return em.createQuery(query, Coupon.class).getResultList();
    }



    public List<Coupon> findByLevel(int level){
        TypedQuery<Coupon> query = em.createQuery("SELECT u FROM Coupon u WHERE u.level = :level", Coupon.class);
        query.setParameter("level", level);
        return query.getResultList();
    }

    public CouponList findFirstCouponList(String user_id, int coupon_id){
        TypedQuery<CouponList> query = em.createQuery("SELECT u FROM CouponList u WHERE u.user.id = :user_id AND u.coupon.id = :coupon_id", CouponList.class);
        query.setParameter("user_id", user_id);
        query.setParameter("coupon_id", coupon_id);
        return query.getResultList().get(0);
    }

    public boolean deleteCouponList(CouponList couponList) {
        if (couponList != null) {
            em.remove(couponList); // 엔티티 삭제
            return true;
        }
        return false;
    }
}
