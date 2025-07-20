package com.blestcodestudios.fuelsalesapp.controller;

import com.blestcodestudios.fuelsalesapp.entity.VisitLog;
import com.blestcodestudios.fuelsalesapp.repository.VisitLogRepository;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/visits")
public class VisitLogController {

    private final VisitLogRepository visitLogRepository;

    public VisitLogController(VisitLogRepository visitLogRepository) {
        this.visitLogRepository = visitLogRepository;
    }

    // ✅ Paginated, sorted, and searchable
    @GetMapping
    public Page<VisitLog> getVisits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String ip
    ) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (ip != null && !ip.isEmpty()) {
            return visitLogRepository
                    .findAll((Specification<VisitLog>) (root, query, cb) -> {
                Predicate predicate = cb.like(root.get("ip"), "%" + ip + "%");
                return predicate;
            }, pageable);
        }

        return visitLogRepository.findAll(pageable);
    }

    // ✅ Stats
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        List<VisitLog> allVisits = visitLogRepository.findAll();

        long totalVisits = allVisits.size();
        long uniqueIps = allVisits.stream().map(VisitLog::getIp).distinct().count();

        Map<String, Long> topIps = allVisits.stream()
                .collect(Collectors.groupingBy(VisitLog::getIp, Collectors.counting()));
        Map<String, Long> topCountries = allVisits.stream()
                .collect(Collectors.groupingBy(VisitLog::getCountry, Collectors.counting()));
        Map<String, Long> topIsps = allVisits.stream()
                .collect(Collectors.groupingBy(VisitLog::getIsp, Collectors.counting()));

        return Map.of(
                "totalVisits", totalVisits,
                "uniqueIps", uniqueIps,
                "topIps", topIps.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(5),
                "topCountries", topCountries.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(5),
                "topIsps", topIsps.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).limit(5)
        );
    }
}
