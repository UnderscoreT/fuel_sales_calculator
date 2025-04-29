package com.blestcodestudios.fuelsalesapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SalesCalculatorController {

    @GetMapping("/")
    public String showCalculatorForm() {
        return "index";
    }

    @PostMapping("/calculate")
    public String calculateFuelSales(
            @RequestParam("startReadingA") double startReadingA,
            @RequestParam("endReadingA") double endReadingA,
            @RequestParam("startReadingB") double startReadingB,
            @RequestParam("endReadingB") double endReadingB,
            @RequestParam("pricePerLitre") double pricePerLitre,
            Model model
    ) {
        double litresA = endReadingA - startReadingA;
        double litresB = endReadingB - startReadingB;
        double totalLitres = litresA + litresB;
        double totalRevenue = totalLitres * pricePerLitre;

        model.addAttribute("litresA", litresA);
        model.addAttribute("litresB", litresB);
        model.addAttribute("totalLitres", totalLitres);
        model.addAttribute("totalRevenue", totalRevenue);

        return "result"; // Loads result.html
    }
}
