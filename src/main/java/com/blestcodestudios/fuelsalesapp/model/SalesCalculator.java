package com.blestcodestudios.fuelsalesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "price is required")
    private Double pricePerLitre;

    @NotNull
    @PositiveOrZero
    private Double couponLitres = 0.0; // Litres given away via coupon (no revenue)
    @NotNull
    @PositiveOrZero
    private Double daCardLitres = 0.0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Transient
//    private Double totalLitresSold =calculateTotalLitresSold();
//
//    @Transient
//    private Double netLitresSold =calculateNetLitresSold();

//    @PrePersist
//    @PreUpdate
//    public Double calculateTotalLitresSold() {
//        if (startReadingA != null && endReadingA != null &&
//                startReadingB != null && endReadingB != null &&
//                endReadingA >= startReadingA && endReadingB >= startReadingB) {
//
//            double litresA = endReadingA - startReadingA;
//            double litresB = endReadingB - startReadingB;
//            this.totalLitresSold = litresA + litresB;
//
//
//
//
//
//        }return this.totalLitresSold;
//    }
//    public Double calculateNetLitresSold() {
//        this.netLitresSold = this.totalLitresSold - couponLitres -daCardLitres;
//        if (this.netLitresSold < 0) {
//            this.netLitresSold = 0.0; // Prevent negative sales
//        }
//        return netLitresSold;
//    }
}
