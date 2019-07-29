package com.javaTask.repository;

import com.javaTask.model.Cart;
import com.javaTask.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select distinct c from Cart c where c.userId = :id and c.status = :status")
    Cart getCartsByUserIdAndOpen(@Param("id") Long id, @Param("status") Status status);
}
