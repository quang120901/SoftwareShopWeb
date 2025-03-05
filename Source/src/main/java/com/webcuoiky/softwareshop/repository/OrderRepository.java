package com.webcuoiky.softwareshop.repository;

import com.webcuoiky.softwareshop.model.Order;
import com.webcuoiky.softwareshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
