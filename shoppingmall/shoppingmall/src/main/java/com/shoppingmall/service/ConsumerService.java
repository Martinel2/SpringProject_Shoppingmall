package com.shoppingmall.service;

import com.shoppingmall.domain.Consumer;
import com.shoppingmall.repository.ConsumerRepository;
import jakarta.transaction.Transactional;

@Transactional
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }

    public Consumer save(Consumer consumer){
        return consumerRepository.save(consumer);
    }
}
