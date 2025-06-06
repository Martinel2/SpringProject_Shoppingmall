package com.shoppingmall.Coupon.Service;

import com.shoppingmall.Coupon.Domain.Coupon;
import com.shoppingmall.Coupon.Domain.CouponList;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Coupon.Repository.CouponRepository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public int useCoupon(int price, Coupon coupon) { return couponRepository.useCoupon(price, coupon);}

    public LocalDate createdDate(Coupon coupon){
        return couponRepository.createdDate(coupon);
    }


    public LocalDate expiredDate(Coupon coupon){
        return couponRepository.expiredDate(coupon);
    }

    public List<CouponList> findListByUserId(String id){
        return couponRepository.findListByUserId(id);
    }

    public List<CouponList> findListByCouponId(int id){
        return couponRepository.findListByCouponId(id);
    }

    public List<Coupon> makeRealCouponList(List<CouponList> couponList){
        return couponRepository.makeRealCouponList(couponList);
    }

    public Coupon findCouponById(int id){
        return couponRepository.findCouponById(id);
    }

    public boolean saveCoupon(Users user, Coupon coupon){
        CouponList couponList = couponRepository.saveCoupon(user,coupon);
        if(couponList != null) return true;
        return false;
    }

    public List<Coupon> getAllCoupon(){
        return couponRepository.getAllCoupon();
    }

    public List<Coupon> findCouponByLevel(int level) { return couponRepository.findByLevel(level); }

    public boolean deleteFirstCouponList(String user_id, int Coupon_id) {
        CouponList couponList = couponRepository.findFirstCouponList(user_id, Coupon_id);
        return couponRepository.deleteCouponList(couponList);
    }
}
