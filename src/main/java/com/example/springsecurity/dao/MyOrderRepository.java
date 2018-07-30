package com.example.springsecurity.dao;

import com.example.springsecurity.entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {

    @Query("select myorder from MyOrder myorder where myorder.user_id=?1")
    List<MyOrder> findByUser_id(Long userId);

    @Query("select myorder from MyOrder myorder where myorder.id=?1")
    Optional<MyOrder> findById(Long id);
}
