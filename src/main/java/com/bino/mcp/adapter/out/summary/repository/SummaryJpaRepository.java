package com.bino.mcp.adapter.out.summary.repository;

import com.bino.mcp.adapter.out.summary.entity.SummaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummaryJpaRepository extends JpaRepository<SummaryEntity, Long> {
}
