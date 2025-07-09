package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.dto.SalesSummaryDto;
import com.blestcodestudios.fuelsalesapp.service.PdfGenerator;
import com.lowagie.text.DocumentException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SalesCalculatorController {


    @GetMapping("/terms")
    public String showTerms() {
        return "terms";
    }

    @GetMapping("/privacy")
    public String showPrivacyPolicy() {
        return "privacy";
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
            @RequestParam("startReadingUnleadedB") double startReadingUnleadedB,
            @RequestParam("endReadingUnleadedB") double endReadingUnleadedB,
            @RequestParam("pricePerLitreDiesel") double pricePerLitreDiesel,
            @RequestParam("pricePerLitreBlend") double pricePerLitreBlend,
            @RequestParam("pricePerLitreUnleaded") double pricePerLitreUnleaded,

            @RequestParam(value = "couponLitresDiesel", defaultValue = "0.0") double couponLitresDiesel,
            @RequestParam(value = "couponLitresBlend", defaultValue = "0.0") double couponLitresBlend,
            @RequestParam(value = "couponLitresUnleaded", defaultValue = "0.0") double couponLitresUnleaded,

            @RequestParam(value = "daCardLitresDiesel", defaultValue = "0.0") double daCardLitresDiesel,
            @RequestParam(value = "daCardLitresBlend", defaultValue = "0.0") double daCardLitresBlend,
            @RequestParam(value = "daCardLitresUnleaded", defaultValue = "0.0") double daCardLitresUnleaded,

            @RequestParam(value = "cashDropped", defaultValue = "0.0") double cashDropped, HttpSession session,

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
        double unleadedNetLitres = litresUnleaded - couponLitresUnleaded - daCardLitresUnleaded;

        double totalRevenue = litresBlend * pricePerLitreBlend + litresUnleaded * pricePerLitreUnleaded + litresDiesel * pricePerLitreDiesel;
        double totalRevenueInCash = litresDiesel * pricePerLitreDiesel + litresUnleaded * pricePerLitreUnleaded +
                litresBlend * pricePerLitreBlend - couponLitresBlend * pricePerLitreBlend - couponLitresDiesel * pricePerLitreDiesel
                - couponLitresUnleaded * pricePerLitreUnleaded - daCardLitresBlend * pricePerLitreBlend
                - daCardLitresDiesel * pricePerLitreDiesel - daCardLitresUnleaded * pricePerLitreUnleaded;
        double cashInHand = totalRevenueInCash - cashDropped;



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

        SalesSummaryDto summary = new SalesSummaryDto();
        summary.litresBlend = litresBlend;
        summary.litresDiesel = litresDiesel;
        summary.litresUnleaded = litresUnleaded;
        summary.totalLitres = totalLitres;
        summary.couponLitres = couponLitres;
        summary.daCardLitres = daCardLitres;
        summary.blendNetLitres = blendNetLitres;
        summary.dieselNetLitres = dieselNetLitres;
        summary.unleadedNetLitres = unleadedNetLitres;
        summary.couponLitresBlend = couponLitresBlend;
        summary.couponLitresDiesel = couponLitresDiesel;
        summary.couponLitresUnleaded = couponLitresUnleaded;
        summary.daCardLitresBlend = daCardLitresBlend;
        summary.daCardLitresDiesel = daCardLitresDiesel;
        summary.daCardLitresUnleaded = daCardLitresUnleaded;
        summary.netLitres = netLitres;
        summary.totalRevenue = totalRevenue;
        summary.totalRevenueInCash = totalRevenueInCash;
        summary.cashInHand = cashInHand;
        summary.cashDropped = cashDropped;

        session.setAttribute("salesSummary", summary);

//        return "result";
        return "sales-summary";
    }



    @PostMapping("/summary/pdf")
    public ResponseEntity<byte[]> downloadSalesSummaryPdf(@Autowired PdfGenerator pdfGenerator,HttpSession session) throws DocumentException {
            SalesSummaryDto summary = (SalesSummaryDto) session.getAttribute("salesSummary");

            if (summary == null) {
                return ResponseEntity.badRequest().build(); // Or return an error view/page
            }



            Map<String, Object> data = new HashMap<>();
            data.put("litresBlend", summary.getLitresBlend());
            data.put("litresDiesel", summary.getLitresDiesel());
            data.put("litresUnleaded", summary.getLitresUnleaded());
            data.put("netLitres", summary.getNetLitres());
            data.put("totalLitres", summary.getTotalLitres());
            data.put("couponLitres", summary.getCouponLitres());
            data.put("daCardLitres", summary.getDaCardLitres());
            data.put("blendNetLitres", summary.getBlendNetLitres());
            data.put("dieselNetLitres", summary.getDieselNetLitres());
            data.put("unleadedNetLitres", summary.getUnleadedNetLitres());
            data.put("totalRevenue", summary.getTotalRevenue());
            data.put("totalRevenueInCash", summary.getTotalRevenueInCash());
            data.put("cashInHand", summary.getCashInHand());
            data.put("couponLitresDiesel", summary.getCouponLitresDiesel());
            data.put("couponLitresUnleaded", summary.getCouponLitresUnleaded());
            data.put("couponLitresBlend", summary.getCouponLitresBlend());
            data.put("daCardLitresBlend", summary.getDaCardLitresBlend());
            data.put("daCardLitresDiesel", summary.getDaCardLitresDiesel());
            data.put("daCardLitresUnleaded", summary.getDaCardLitresUnleaded());
            data.put("cashDropped", summary.getCashDropped());




            byte[] pdfBytes = pdfGenerator.generatePdf("sales-summary", data);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"fuel-sales-summary.pdf\"")
                    .body(pdfBytes);
        }



}
