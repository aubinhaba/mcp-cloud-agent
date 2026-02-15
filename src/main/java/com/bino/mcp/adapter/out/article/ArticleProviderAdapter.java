package com.bino.mcp.adapter.out.article;

import com.bino.mcp.application.port.ArticlesProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class ArticleProviderAdapter implements ArticlesProvider {

    @Override
    public List<Map<String, String>> getArticles() {
        return List.of(
                Map.of(
                        "id", "1",
                        "title", "Introduction to AWS Lambda",
                        "url", "https://example.com/aws-lambda"
                ),
                Map.of(
                        "id", "2",
                        "title", "Spring Boot Cloud-Native",
                        "url", "https://example.com/spring-cloud"
                )
        );
    }
}
