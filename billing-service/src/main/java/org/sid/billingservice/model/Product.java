package org.sid.billingservice.model;

import lombok.Data;

@Data
public class Product {
    private String id;
    private String name;
    private double unitPrice;
    private int quantity;
}
