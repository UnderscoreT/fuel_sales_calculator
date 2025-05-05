package com.blestcodestudios.fuelsalesapp.service;

import com.blestcodestudios.fuelsalesapp.model.BreakdownMaker;
import com.blestcodestudios.fuelsalesapp.repository.BreakdownMakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BreakdownMakerService {
    private  final BreakdownMakerRepository breakdownMakerRepository;

    public Optional<BreakdownMaker> findById(Integer id) {
        return breakdownMakerRepository.findById(id);
    }
    public List<BreakdownMaker> findAll() {
        return breakdownMakerRepository.findAll();
    }
    public BreakdownMaker save(BreakdownMaker breakdownMaker) {
        return breakdownMakerRepository.save(breakdownMaker);
    }
    public void deleteById(Integer id) {
        breakdownMakerRepository.deleteById(id);
    }
}
