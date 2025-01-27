
// package com.company.rns;

// import io.quarkus.hibernate.orm.panache.PanacheEntity;
// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.PrePersist;

// @Entity
// public class NumberEntity extends PanacheEntity {

//     // इसे Long बनाएँ ताकि null हैंडल हो सके
//     @Column(nullable = true) Long value;

//     @PrePersist
//     public void prePersist() {
//         // null होने पर default 0L सेट करें
//         if (value == null) {
//             value = 0L;
//         }
//     }

//     // Getter and Setter methods
//     public Long getValue() {
//         return value;
//     }

//     public void setValue(Long value) { // पहले Integer था, अब Long किया
//         this.value = value;
//     }

// }


package com.company.rns;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class NumberEntity extends PanacheEntity {
    @Column(nullable = false, unique = true)
    public Long value;

    public NumberEntity() {}

    public NumberEntity(Long value) {
        this.value = value;
    }
}
