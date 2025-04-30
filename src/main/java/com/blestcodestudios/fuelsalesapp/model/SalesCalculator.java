package com.blestcodestudios.fuelsalesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sale_calculator")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesCalculator {

    @NotNull(message = "Start reading A is required")
    @PositiveOrZero
    private Double startReadingA;

    @NotNull(message = "Start reading B is required")
    @PositiveOrZero
    private Double startReadingB;

    @NotNull(message = "End reading A is required")
    @PositiveOrZero
    private Double endReadingA;

    @NotNull(message = "End reading B is required")
    @PositiveOrZero
    private Double endReadingB;

    private Double pricePerLitre;

    @PositiveOrZero
    private Double couponLitres = 0.0; // Litres given away via coupon (no revenue)

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Transient
    private Double totalLitresSold;

    @Transient
    private Double netLitresSold;

    @PrePersist
    @PreUpdate
    public void calculateTotalLitresSold() {
        if (startReadingA != null && endReadingA != null &&
                startReadingB != null && endReadingB != null &&
                endReadingA >= startReadingA && endReadingB >= startReadingB) {

            double litresA = endReadingA - startReadingA;
            double litresB = endReadingB - startReadingB;
            this.totalLitresSold = litresA + litresB;

            if (couponLitres == null) {
                couponLitres = 0.0;
            }

            this.netLitresSold = this.totalLitresSold - couponLitres;
            if (this.netLitresSold < 0) {
                this.netLitresSold = 0.0; // Prevent negative sales
            }
        }
    }
}
