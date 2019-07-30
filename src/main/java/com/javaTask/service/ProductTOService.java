package com.javaTask.service;

import com.javaTask.model.ProductTO;
import com.javaTask.model.enums.Status;
import com.javaTask.repository.ProductTORepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aleksandr Beryozkin
 */

@Service
public class ProductTOService {

    private final ProductTORepository productTORepository;

    public ProductTOService(ProductTORepository productTORepository) {
        this.productTORepository = productTORepository;
    }

    public List<ProductTO> getAllProductsByCartId(Long cartId) {
        return productTORepository.getAllProductsByCartId(cartId);
    }

    public List<ProductTO> getProductsHistoryByTimeAndUserId(Long userId, long from, long till) {
        return productTORepository.getProductsHistoryByTimeAndUserId(userId, from, till);
    }
}
