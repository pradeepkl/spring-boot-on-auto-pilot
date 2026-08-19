package com.ekart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ekart.model.LineItem;

public interface LineItemRepository
        extends JpaRepository<LineItem, Long> {
}
