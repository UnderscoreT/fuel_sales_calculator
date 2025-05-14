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

    @NotNull(message = "Start reading BlendA is required")
    @PositiveOrZero
    private Double startReadingBlendA;

    @NotNull(message = "Start reading BlendB is required")
    @PositiveOrZero
    private Double startReadingBlendB;

    @NotNull(message = "End reading BlendA is required")
    @PositiveOrZero
    private Double endReadingBlendA;

    @NotNull(message = "End reading BlendB is required")
    @PositiveOrZero
    private Double endReadingBlendB;

    @NotNull(message = "Start reading DieselA is required")
    @PositiveOrZero
    private Double startReadingDieselA;

    @NotNull(message = "Start reading DieselB is required")
    @PositiveOrZero
    private Double startReadingDieselB;

    @NotNull(message = "End reading DieselA is required")
    @PositiveOrZero
    private Double endReadingDieselA;

    @NotNull(message = "End reading DieselB is required")
    @PositiveOrZero
    private Double endReadingDieselB;

    @NotNull(message = "Start reading UnleadedA is required")
    @PositiveOrZero
    private Double startReadingUnleadedA;

    @NotNull(message = "Start reading UnleadedB is required")
    @PositiveOrZero
    private Double startReadingUnleadedB;

    @NotNull(message = "End reading UnleadedA is required")
    @PositiveOrZero
    private Double endReadingUnleadedA;

    @NotNull(message = "End reading UnleadedB is required")
    @PositiveOrZero
    private Double endReadingUnleadedB;


    @NotNull(message = "price for Blend is required")
    private Double pricePerLitreBlend;

    @NotNull(message = "price for Diesel is required")
    private Double pricePerLitreDiesel;

    @NotNull(message = "price for Unleaded is required")
    private Double pricePerLitreUnleaded;


    @NotNull
    @PositiveOrZero
    private Double couponLitresDiesel = 0.0;

    @NotNull
    @PositiveOrZero
    private Double couponLitresBlend = 0.0;

    @NotNull
    @PositiveOrZero
    private Double couponLitresUnleaded = 0.0;

    @NotNull
    @PositiveOrZero
    private Double daCardLitresDiesel = 0.0;

    @NotNull
    @PositiveOrZero
    private Double daCardLitresBlend = 0.0;

    @NotNull
    @PositiveOrZero
    private Double daCardLitresUnleaded = 0.0;


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
