package com.javaTask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author Aleksandr Beryozkin
 */

@Entity
@JsonIgnoreProperties(value = "id")
public class ProductTO {

    @Id
    private Long id;
    private String title;
    private int quantity;
    private double price;
    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductTO productTO = (ProductTO) o;
        return quantity == productTO.quantity &&
                Double.compare(productTO.price, price) == 0 &&
                Objects.equals(title, productTO.title) &&
                Objects.equals(orderId, productTO.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, quantity, price, orderId);
    }
}
