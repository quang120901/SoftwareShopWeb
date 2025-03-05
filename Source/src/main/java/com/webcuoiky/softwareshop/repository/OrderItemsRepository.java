package com.webcuoiky.softwareshop.repository;

import com.webcuoiky.softwareshop.model.Order;
import com.webcuoiky.softwareshop.model.Order_items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<Order_items, Integer> {
    List<Order_items> findByOrder(Order order);

}
