package com.blestcodestudios.fuelsalesapp;

import com.blestcodestudios.fuelsalesapp.model.SalesCalculator;
import com.blestcodestudios.fuelsalesapp.repository.SalesCalculatorRepository;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableRabbit
public class FuelSalesApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        SpringApplication.run(FuelSalesApplication.class, args);
    }

    @Bean
    CommandLineRunner loadSampleData(SalesCalculatorRepository repository) {
        return args -> {
            SalesCalculator sale = new SalesCalculator();
            sale.setStartReadingBlendA(659126.47);
            sale.setEndReadingBlendA(660648.01);
            sale.setStartReadingBlendB(430349.6);
            sale.setEndReadingBlendB(430865.77);
            sale.setPricePerLitreDiesel(1.43);
            sale.setPricePerLitreBlend(1.43);
            sale.setPricePerLitreUnleaded(1.43);
            sale.setDaCardLitresBlend(1.1);
            sale.setDaCardLitresUnleaded(1.1);
            sale.setDaCardLitresDiesel(1.1);
            sale.setCouponLitresBlend(1.1);
            sale.setCouponLitresUnleaded(1.1);
            sale.setCouponLitresDiesel(1.1);
            sale.setStartReadingDieselA(1000.0);
            sale.setEndReadingDieselA(1500.0);
            sale.setStartReadingDieselB(2000.0);
            sale.setEndReadingDieselB(2500.0);
            sale.setStartReadingUnleadedA(3000.0);
            sale.setEndReadingUnleadedA(3500.0);
            sale.setStartReadingUnleadedB(4000.0);
            sale.setEndReadingUnleadedB(4500.0);
                      repository.save(sale);
        };
    }

}
