package com.ekart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
