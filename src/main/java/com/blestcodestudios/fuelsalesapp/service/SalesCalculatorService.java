package com.blestcodestudios.fuelsalesapp.service;

import com.blestcodestudios.fuelsalesapp.model.SalesCalculator;
import com.blestcodestudios.fuelsalesapp.repository.SalesCalculatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SalesCalculatorService {
    private final SalesCalculatorRepository repository;

    public Optional<SalesCalculator> findById(Long id) {
        return repository.findById(id);
    }

    public SalesCalculator saveReading(SalesCalculator reading) {
        return repository.save(reading);
    }

    public List<SalesCalculator>  findAllReadings() {
        return repository.findAll();
    }
    public void deleteReading(Long id) {
        repository.deleteById(id);
    }

}
