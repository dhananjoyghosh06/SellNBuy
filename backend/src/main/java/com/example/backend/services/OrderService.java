package com.example.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.entity.Order;
import com.example.backend.repository.OrderRepo;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    public boolean createOrder(Long id, Long productId, String username, Long quantity) {
        try{
            if(id == null) throw new RuntimeException("id is not valid");
            if(productId == null) throw new RuntimeException("productId is not valid");
            if(username == null) throw new RuntimeException("username is not valid");
            if(quantity == null){
                quantity = 1L;
            }
            Order newOrder = new Order();
            newOrder.setUserId(id);
            newOrder.setProductId(productId);
            newOrder.setQuantity(quantity);
            newOrder.setUsername(username);

            Order savedOrder = orderRepo.save(newOrder);
            if(savedOrder == null) throw new RuntimeException("user not saved");
            else return true;
        }catch(Exception e){
            throw new RuntimeException("OrderCreation Failed -> " + e.getMessage());
        }
        
    }
}
