package org.sid.inventoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Setter @Getter @Builder
public class Product {
    @Id
    private String id;
    private String name;
    private double unitPrice;
    private int quantity;
}
