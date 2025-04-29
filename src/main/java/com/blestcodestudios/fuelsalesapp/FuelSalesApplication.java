package com.blestcodestudios.fuelsalesapp;

import com.blestcodestudios.fuelsalesapp.model.SalesCalculator;
import com.blestcodestudios.fuelsalesapp.repository.SalesCalculatorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FuelSalesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuelSalesApplication.class, args);
    }

    @Bean
    CommandLineRunner loadSampleData(SalesCalculatorRepository repository) {
        return args -> {
            SalesCalculator sale = new SalesCalculator();
            sale.setStartReadingA(659126.47);
            sale.setEndReadingA(660648.01);
            sale.setStartReadingB(430349.6);
            sale.setEndReadingB(430865.77);
            sale.setPricePerLitre(1.43);

            repository.save(sale);
        };
    }

}
