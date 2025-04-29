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
    @NotNull(message = "start reading B is required" )
    @Positive
    private Double startReadingB;
    @NotNull(message = "End reading A is required")
    @PositiveOrZero
    private Double endReadingA;
    @NotNull(message = "End reading B is required")
    @Positive
    private Double endReadingB;

    private Double pricePerLitre;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double totalLitresSold;

    @PrePersist
    @PreUpdate
    public void calculateTotalLitresSold() {
        if (startReadingA != null && endReadingA != null &&
                startReadingB != null && endReadingB != null &&
                endReadingA >= startReadingA && endReadingB >= startReadingB) {

            double litresA = endReadingA - startReadingA;
            double litresB = endReadingB - startReadingB;
            this.totalLitresSold = litresA + litresB;
        }
    }
}

