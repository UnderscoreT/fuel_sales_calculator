package com.blestcodestudios.fuelsalesapp.repository;

import com.blestcodestudios.fuelsalesapp.model.SalesCalculator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesCalculatorRepository extends JpaRepository<SalesCalculator, Long> {

}
