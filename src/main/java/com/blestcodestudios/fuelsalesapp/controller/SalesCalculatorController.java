package com.blestcodestudios.fuelsalesapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SalesCalculatorController {

    double closingCash ;

    @GetMapping("/")
    public String showCalculatorForm() {
        return "index";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @PostMapping("/calculate")
    public String calculateFuelSales(
            @RequestParam("startReadingBlendA") double startReadingBlendA,
            @RequestParam("endReadingBlendA") double endReadingBlendA,
            @RequestParam("startReadingBlendB") double startReadingBlendB,
            @RequestParam("endReadingBlendB") double endReadingBlendB,
            @RequestParam("startReadingDieselA") double startReadingDieselA,
            @RequestParam("endReadingDieselA") double endReadingDieselA,
            @RequestParam("startReadingDiesel") double startReadingDieselB,
            @RequestParam("endReadingDieselB") double endReadingDieselB,
            @RequestParam("startReadingUnleadedA") double startReadingUnleadedA,
            @RequestParam("endReadingUnleadedA") double endReadingUnleadedA,
            @RequestParam("startReadingUnleadedA") double startReadingUnleadedB,
            @RequestParam("endReadingUnleadedB") double endReadingUnleadedB,
            @RequestParam("pricePerLitre") double pricePerLitre,
            @RequestParam(value = "couponLitres", defaultValue = "0.0") double couponLitres,
            @RequestParam(value = "daCardLitres", defaultValue = "0.0")double daCardLitres,
            @RequestParam(value = "cashDropped", defaultValue = "0.0") double cashDropped,
            Model model
    ) {
        double litresBlendA = endReadingBlendA - startReadingBlendA;
        double litresBlendB = endReadingBlendB - startReadingBlendB;
        double litresDieselA = endReadingDieselA - startReadingDieselA;
        double litresDieselB = endReadingDieselB - startReadingDieselB;
        double litresUnleadedA = endReadingUnleadedA - startReadingUnleadedA;
        double litresUnleadedB = endReadingUnleadedB - startReadingUnleadedB;

        double totalLitres = litresBlendA + litresBlendB + litresDieselA + litresDieselB + litresUnleadedA + litresUnleadedB;

        double netLitres = totalLitres - couponLitres - daCardLitres;
        double totalRevenue = netLitres * pricePerLitre;
        double cashInHand = totalRevenue - cashDropped;
        closingCash = cashInHand;

        double litresBlend = litresBlendA + litresBlendB;
        double litresDiesel = litresDieselA + litresDieselB;
        double litresUnleaded = litresUnleadedA + litresUnleadedB;
        model.addAttribute("litresBlend", litresBlend);
        model.addAttribute("litresDiesel", litresDiesel);
        model.addAttribute("litresUnleaded", litresUnleaded);

        model.addAttribute("totalLitres", totalLitres);
        model.addAttribute("couponLitres", couponLitres);
        model.addAttribute("daCardLitres", daCardLitres);
        model.addAttribute("netLitres", netLitres);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("cashInHand", cashInHand);

        return "result";
    }
}
