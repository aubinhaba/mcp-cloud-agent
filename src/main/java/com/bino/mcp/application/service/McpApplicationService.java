package com.bino.mcp.application.service;

import com.bino.mcp.application.port.ArticlesProvider;
import com.bino.mcp.application.port.SummaryRepository;
import com.bino.mcp.domain.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Application service (use cases): orchestration of MCP resources and tools.
 */
@Service
@Transactional(readOnly = true)
public class McpApplicationService {

    private static final Logger log = LoggerFactory.getLogger(McpApplicationService.class);

    private final SummaryRepository summaryRepository;
    private final ArticlesProvider articlesProvider;

    public McpApplicationService(SummaryRepository summaryRepository, ArticlesProvider articlesProvider) {
        this.summaryRepository = summaryRepository;
        this.articlesProvider = articlesProvider;
    }

    public Map<String, Object> listArticles() {
        log.debug("Fetching articles list");
        List<Map<String, String>> articles = articlesProvider.getArticles();
        return Map.of("articles", articles);
    }

    @Transactional
    public Summary saveSummary(Summary summary) {
        log.info("Saving summary: title='{}'", summary.title());
        Summary saved = summaryRepository.save(summary);
        log.debug("Summary saved successfully: {}", saved);
        return saved;
    }

    public List<Summary> listSummaries() {
        log.debug("Fetching all summaries");
        List<Summary> summaries = summaryRepository.findAll();
        log.debug("Number of summaries: {}", summaries.size());
        return summaries;
    }
}
