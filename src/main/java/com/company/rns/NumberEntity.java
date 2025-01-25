
package com.company.rns;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;

@Entity
public class NumberEntity extends PanacheEntity {

    @Column(nullable = true) // Allow null temporarily for existing data
 Integer value;

    @PrePersist
    public void prePersist() {
        if (value == null) {
            value = 0; // Default value
        }
    }
    // Getter and Setter methods
    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
