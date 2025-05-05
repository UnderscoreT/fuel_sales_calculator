package com.blestcodestudios.fuelsalesapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="breakdown")
public class BreakdownMaker {

    @NotNull(message = "amount is required")
    @Positive
    int oneDollar;
    @NotNull(message = "amount is required")
    @Positive
    int twoDollars;
    @NotNull(message = "amount is required")
    @Positive
    int fiveDollars;
    @NotNull(message = "amount is required")
    @Positive
    int tenDollars;
    @NotNull(message = "amount is required")
    @Positive
    int twentyDollars;
    @NotNull(message = "amount is required")
    @Positive
    int fiftyDollars;
    @NotNull(message = "amount is required")
    @Positive
    int oneHundredDollars;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfOneDollarNotes;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfTwoDollarNotes;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfFiveDollarNotes;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfTenDollarNotes;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfTwentyDollarNotes;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfFiftyDollarNotes;
    @NotNull(message = "amount is required")
    @Positive
    int numberOfHundredDollarNotes;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp()
    private LocalDateTime dateTime;
    @Positive
    @NotNull(message = "amount dropped is required")
    private int cashDropped;

    private int total=calculateTotal();


    private int calculateTotal(){
       return getOneDollars()+getTwoDollars()+getFiveDollars()+getTenDollars()+getFiftyDollars()+
                getTwentyDollars()+getFiftyDollars()+getOneHundredDollars();
    }

    public int getOneDollars() {

        return oneDollar*numberOfOneDollarNotes;
    }
    public int getTwoDollars() {
        return twoDollars*numberOfTwoDollarNotes;
    }
    public int getFiveDollars() {
        return fiveDollars* numberOfFiveDollarNotes;
    }
    public int getTenDollars() {
        return tenDollars* numberOfTenDollarNotes;
    }
    public int getFiftyDollars() {
        return fiftyDollars* numberOfFiftyDollarNotes;
    }
    public int getOneHundredDollars() {
        return oneHundredDollars* numberOfHundredDollarNotes;
    }
    public int getTwentyDollars() {
        return twentyDollars* numberOfTwentyDollarNotes;
    }


}
