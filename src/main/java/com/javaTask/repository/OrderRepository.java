package com.javaTask.repository;

import com.javaTask.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "from Order o where o.cartId = :cartId")
    List<Order> getOrdersByCartId(@Param("cartId") Long cartId);
}
