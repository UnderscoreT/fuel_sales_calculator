package com.blestcodestudios.fuelsalesapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SalesCalculatorController {

    double closingCash ;

    @GetMapping("/")
    public String showCalculatorForm() {
        return "home";
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
            @RequestParam("startReadingDieselB") double startReadingDieselB,
            @RequestParam("endReadingDieselB") double endReadingDieselB,
            @RequestParam("startReadingUnleadedA") double startReadingUnleadedA,
            @RequestParam("endReadingUnleadedA") double endReadingUnleadedA,
            @RequestParam("startReadingUnleadedA") double startReadingUnleadedB,
            @RequestParam("endReadingUnleadedB") double endReadingUnleadedB,
            @RequestParam("pricePerLitreDiesel") double pricePerLitreDiesel,
            @RequestParam("pricePerLitreBlend") double pricePerLitreBlend,
            @RequestParam("pricePerLitreUnleaded") double pricePerLitreUnleaded,

            @RequestParam(value = "couponLitresDiesel", defaultValue = "0.0") double couponLitresDiesel,
            @RequestParam(value = "couponLitresBlend", defaultValue = "0.0") double couponLitresBlend,
            @RequestParam(value = "couponLitresUnleaded", defaultValue = "0.0") double couponLitresUnleaded,

            @RequestParam(value = "daCardLitresDiesel", defaultValue = "0.0")double daCardLitresDiesel,
            @RequestParam(value = "daCardLitresBlend", defaultValue = "0.0")double daCardLitresBlend,
            @RequestParam(value = "daCardLitresUnleaded", defaultValue = "0.0")double daCardLitresUnleaded,

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
        double couponLitres = couponLitresBlend + couponLitresUnleaded + couponLitresDiesel;
        double daCardLitres = daCardLitresBlend + daCardLitresUnleaded + daCardLitresDiesel;

        double netLitres = totalLitres - couponLitres - daCardLitres;
        double litresBlend = litresBlendA + litresBlendB;
        double litresDiesel = litresDieselA + litresDieselB;
        double litresUnleaded = litresUnleadedA + litresUnleadedB;

        double blendNetLitres = litresBlend - couponLitresBlend - daCardLitresBlend;
        double dieselNetLitres = litresDiesel - couponLitresDiesel - daCardLitresDiesel;
        double unleadedNetLitres = litresUnleaded - couponLitresUnleaded -daCardLitresUnleaded;

        double totalRevenue = litresBlend*pricePerLitreBlend+ litresUnleaded*pricePerLitreUnleaded+litresDiesel*pricePerLitreDiesel;
        double totalRevenueInCash = litresDiesel*pricePerLitreDiesel + litresUnleaded*pricePerLitreUnleaded+
                litresBlend*pricePerLitreBlend - couponLitresBlend*pricePerLitreBlend - couponLitresDiesel*pricePerLitreDiesel
                -couponLitresUnleaded*pricePerLitreUnleaded -daCardLitresBlend*pricePerLitreBlend
                -daCardLitresDiesel*pricePerLitreDiesel -daCardLitresUnleaded*pricePerLitreUnleaded;
        double cashInHand = totalRevenueInCash - cashDropped;
        closingCash = cashInHand;


        model.addAttribute("litresBlend", litresBlend);
        model.addAttribute("litresDiesel", litresDiesel);
        model.addAttribute("litresUnleaded", litresUnleaded);

        model.addAttribute("totalLitres", totalLitres);
        model.addAttribute("couponLitres", couponLitres);
        model.addAttribute("daCardLitres", daCardLitres);
        model.addAttribute("couponLitresBlend", couponLitresBlend);
        model.addAttribute("couponLitresDiesel", couponLitresDiesel);
        model.addAttribute("couponLitresUnleaded", couponLitresUnleaded);

        model.addAttribute("blendNetLitres", blendNetLitres);
        model.addAttribute("dieselNetLitres", dieselNetLitres);
        model.addAttribute("unleadedNetLitres", unleadedNetLitres);

        model.addAttribute("daCardLitresBlend", daCardLitresBlend);
        model.addAttribute("daCardLitresUnleaded", daCardLitresUnleaded);

        model.addAttribute("daCardLitresDiesel", daCardLitresDiesel);

        model.addAttribute("netLitres", netLitres);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("totalRevenueInCash", totalRevenueInCash);

        model.addAttribute("cashInHand", cashInHand);

        return "result";
    }
}
