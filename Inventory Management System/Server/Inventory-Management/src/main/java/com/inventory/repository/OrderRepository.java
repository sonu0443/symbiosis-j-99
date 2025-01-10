package com.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inventory.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // You can add custom queries here if needed, for example:

    // Find orders by status (e.g., 'pending' or 'completed')
    List<Order> findByStatus(String status);
    
   
   long countByUserIdAndStatus(Long userId, String status);


   @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND o.status = 'pending'")
   long countPendingOrdersByUserId(Long userId);

   // Count completed orders by user ID
   @Query("SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId AND o.status = 'completed'")
   long countCompletedOrdersByUserId(Long userId);

}
