package com.javaTask.repository;

import com.javaTask.model.ProductTO;
import com.javaTask.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

public interface ProductTORepository extends JpaRepository<ProductTO, Long> {

    @Query(value = "select new ProductTO(p.title, o.quantity, p.price, o.id) from Product p join Order o on p.id=o.productId where o.cartId = :cartId")
    List<ProductTO> getAllProductsByCartId(@Param("cartId") Long cartId);

    @Query(value = "select new ProductTO(p.title, o.quantity, p.price, o.id) from Product p join Order o on p.id=o.productId join Cart c on o.cartId=c.id where c.userId = :userId " +
            "and timestamp between :from and :till")
    List<ProductTO> getProductsHistoryByTimeAndUserId(@Param("userId") Long userId, @Param("from") long from, @Param("till") long till);
}
