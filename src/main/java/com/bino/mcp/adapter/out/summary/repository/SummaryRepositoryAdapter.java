package com.bino.mcp.adapter.out.summary.repository;

import com.bino.mcp.adapter.out.summary.mapper.SummaryPersistenceMapper;
import com.bino.mcp.application.port.SummaryRepository;
import com.bino.mcp.domain.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SummaryRepositoryAdapter implements SummaryRepository {

    private static final Logger log = LoggerFactory.getLogger(SummaryRepositoryAdapter.class);

    private final SummaryJpaRepository jpaRepository;
    private final SummaryPersistenceMapper mapper;

    public SummaryRepositoryAdapter(SummaryJpaRepository jpaRepository, SummaryPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Summary save(Summary summary) {
        log.debug("Persisting summary: title='{}'", summary.title());
        var entity = mapper.toEntity(summary);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Summary> findAll() {
        log.debug("Fetching all summaries from database");
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }
}
