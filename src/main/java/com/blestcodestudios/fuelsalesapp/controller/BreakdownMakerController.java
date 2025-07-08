package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.model.BreakdownMaker;
import com.blestcodestudios.fuelsalesapp.model.SalesCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BreakdownMakerController {


    private final SalesCalculatorController salesCalculator;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/showBreakDownForm")
    public String showBreakDownForm() {

        //return "breakdown";
        return "brkdwn";
    }

    @PostMapping("/makeBreakDown")
    public String calculateFuelSales(
            @RequestParam("oneDollar") int oneDollar,
            @RequestParam("twoDollars") int twoDollars,
            @RequestParam("fiveDollars") int fiveDollars,
            @RequestParam("tenDollars") int tenDollars,
            @RequestParam("twentyDollars") int twentyDollars,
            @RequestParam("fiftyDollars") int fiftyDollars,
            @RequestParam("oneHundredDollars") int oneHundredDollars,
            @RequestParam("numberOfOneDollarNotes")int numberOfOneDollarNotes,
            @RequestParam("numberOfTwoDollarNotes")int numberOfTwoDollarNotes,
            @RequestParam("numberOfFiveDollarNotes") int numberOfFiveDollarNotes,
            @RequestParam("numberOfTenDollarNotes") int numberOfTenDollarNotes,
            @RequestParam("numberOfTwentyDollarNotes")int numberOfTwentyDollarNotes,
            @RequestParam("numberOfFiftyDollarNotes") int numberOfFiftyDollarNotes,
            @RequestParam("numberOfOneHundredDollarNotes")int numberOfOneHundredDollarNotes,
            Model model
    ) {

        int totalCashInHand = oneDollar*numberOfOneDollarNotes+
                twoDollars*numberOfTwoDollarNotes+fiveDollars*numberOfFiveDollarNotes
                +tenDollars*numberOfTenDollarNotes+twentyDollars*numberOfTwentyDollarNotes
                +fiftyDollars*numberOfFiftyDollarNotes+oneHundredDollars*numberOfOneHundredDollarNotes;

        int totalForOneDollars = oneDollar*numberOfOneDollarNotes;
        int totalForTwoDollars = twoDollars*numberOfTwoDollarNotes;
        int totalForFiveDollars = fiveDollars*numberOfFiveDollarNotes;
        int totalForTenDollars = tenDollars*numberOfTenDollarNotes;
        int totalForTwentyDollars = twentyDollars*numberOfTwentyDollarNotes;
        int totalForFiftyDollars = fiftyDollars*numberOfFiftyDollarNotes;
        int totalForHundredDollars = oneHundredDollars*numberOfOneHundredDollarNotes;

        BreakdownMaker  breakdownMaker = new BreakdownMaker();

        double netCashSales  = salesCalculator.closingCash;
        int cashDropped = breakdownMaker.getCashDropped();
        double balance = netCashSales - cashDropped;


        model.addAttribute("totalCashInHand", totalCashInHand );
        model.addAttribute("totalForOneDollars", totalForOneDollars );
        model.addAttribute("totalForTwoDollars", totalForTwoDollars );
        model.addAttribute("totalForFiveDollars", totalForFiveDollars );
        model.addAttribute("totalForTenDollars", totalForTenDollars );
        model.addAttribute("totalForTwentyDollars", totalForTwentyDollars );
        model.addAttribute("totalForFiftyDollars", totalForFiftyDollars );
        model.addAttribute("totalForHundredDollars", totalForHundredDollars );
        model.addAttribute("oneDollar", oneDollar );
        model.addAttribute("twoDollars", twoDollars );
        model.addAttribute("fiveDollars", fiveDollars );
        model.addAttribute("tenDollars", tenDollars );
        model.addAttribute("twentyDollars", twentyDollars );
        model.addAttribute("fiftyDollars", fiftyDollars );
        model.addAttribute("oneHundredDollars", oneHundredDollars );
        model.addAttribute("numberOfOneDollarNotes", numberOfOneDollarNotes );
        model.addAttribute("numberOfTwoDollarNotes", numberOfTwoDollarNotes );
        model.addAttribute("numberOfFiveDollarNotes", numberOfFiveDollarNotes );
        model.addAttribute("numberOfTenDollarNotes", numberOfTenDollarNotes );
        model.addAttribute("numberOfTwentyDollarNotes", numberOfTwentyDollarNotes );
        model.addAttribute("numberOfFiftyDollarNotes", numberOfFiftyDollarNotes );
        model.addAttribute("numberOfOneHundredDollarNotes", numberOfOneHundredDollarNotes );
        model.addAttribute("balance", balance );


//        return "breakdown-table";
        return "brkd--result";
    }
}
