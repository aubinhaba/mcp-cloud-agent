package com.bino.mcp.adapter.in.web;

import com.bino.mcp.application.port.ArticlesProvider;
import com.bino.mcp.application.port.SummaryRepository;
import com.bino.mcp.application.service.McpApplicationService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@TestConfiguration
public class McpControllerTestConfig {

    @Bean
    @Primary
    public McpApplicationService mcpApplicationService(SummaryRepository summaryRepository, ArticlesProvider articlesProvider) {
        return new McpApplicationService(summaryRepository, articlesProvider);
    }

    @Bean
    @Primary
    public SummaryRepository summaryRepository() {
        return new SummaryRepository() {
            private final List<com.bino.mcp.domain.Summary> store = new ArrayList<>();

            @Override
            public com.bino.mcp.domain.Summary save(com.bino.mcp.domain.Summary summary) {
                store.add(summary);
                return summary;
            }

            @Override
            public List<com.bino.mcp.domain.Summary> findAll() {
                return List.copyOf(store);
            }
        };
    }

    @Bean
    @Primary
    public ArticlesProvider articlesProvider() {
        return () -> List.of(
                Map.of("id", "1", "title", "Introduction to AWS Lambda", "url", "https://example.com/aws-lambda"),
                Map.of("id", "2", "title", "Spring Boot Cloud-Native", "url", "https://example.com/spring-cloud")
        );
    }
}
