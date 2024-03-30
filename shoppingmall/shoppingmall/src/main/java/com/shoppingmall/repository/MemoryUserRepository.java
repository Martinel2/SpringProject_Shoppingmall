package com.shoppingmall.repository;

import com.shoppingmall.domain.Level;
import com.shoppingmall.domain.User;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryUserRepository implements UserRepository {
    private Map<Long, User> store = new ConcurrentHashMap<>();
    private static long seq = 0L;

    @Override
    public User add(User user) { //사용자가 잘 들어갔는지 확인용
        user.setNum(++seq);
        store.put(user.getNum(),user);
        return user;
    }

    @Override
    public Level valueOf(int value) {
        switch (value){
            case 1: return Level.BASIC;
            case 2: return Level.NORMAL;
            case 3: return Level.VIP;
            case 4: return Level.VVIP;
            default: throw new AssertionError("Unknown value: " + value);
        }
    }

    @Override
    public Optional<User> findById(String id) {
        return store.values().stream()
                .filter(user -> user.getId().equals(id))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }
}
