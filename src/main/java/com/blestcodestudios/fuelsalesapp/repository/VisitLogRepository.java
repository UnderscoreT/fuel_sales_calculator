package com.blestcodestudios.fuelsalesapp.repository;


import com.blestcodestudios.fuelsalesapp.entity.VisitLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog,Long>,JpaSpecificationExecutor<VisitLog> {
}
