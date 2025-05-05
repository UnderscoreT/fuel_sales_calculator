package com.blestcodestudios.fuelsalesapp.repository;

import com.blestcodestudios.fuelsalesapp.model.BreakdownMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakdownMakerRepository extends JpaRepository<BreakdownMaker, Integer> {
}
