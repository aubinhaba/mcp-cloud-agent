package com.bino.mcp.adapter.in.web;

import com.bino.mcp.adapter.in.web.dto.ArticlesResponse;
import com.bino.mcp.adapter.in.web.dto.SaveSummaryRequest;
import com.bino.mcp.adapter.in.web.dto.SaveSummaryResponse;
import com.bino.mcp.adapter.in.web.dto.SummaryResponse;
import com.bino.mcp.application.service.McpApplicationService;
import com.bino.mcp.domain.Summary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST controller for MCP resources and tools.
 */
@RestController
@RequestMapping
@Tag(name = "MCP API", description = "MCP Endpoints - Resources and Tools")
public class McpController {

    private static final Logger log = LoggerFactory.getLogger(McpController.class);
    private final McpApplicationService mcpApplicationService;

    public McpController(McpApplicationService mcpApplicationService) {
        this.mcpApplicationService = mcpApplicationService;
    }

    @Operation(
            summary = "List articles",
            description = "MCP resource: returns the list of available articles"
    )
    @ApiResponse(responseCode = "200", description = "Articles list retrieved successfully")
    @GetMapping(value = "/resources/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArticlesResponse listArticles() {
        log.info("GET /resources/articles");
        Map<String, Object> result = mcpApplicationService.listArticles();

        @SuppressWarnings("unchecked")
        List<Map<String, String>> articles = (List<Map<String, String>>) result.get("articles");

        List<ArticlesResponse.ArticleItem> items = articles.stream()
                .map(m -> new ArticlesResponse.ArticleItem(m.get("id"), m.get("title"), m.get("url")))
                .toList();

        return new ArticlesResponse(items);
    }

    @Operation(
            summary = "Save a summary",
            description = "MCP tool: saves an article summary to the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Summary saved successfully",
            content = @Content(schema = @Schema(implementation = SaveSummaryResponse.class))
    )
    @ApiResponse(responseCode = "400", description = "Validation error")
    @PostMapping(value = "/tools/saveSummary", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SaveSummaryResponse saveSummary(@Valid @RequestBody SaveSummaryRequest request) {
        log.info("POST /tools/saveSummary - title: '{}'", request.title());

        Summary summary = new Summary(request.title(), request.summary());
        mcpApplicationService.saveSummary(summary);

        return SaveSummaryResponse.success();
    }

    @Operation(
            summary = "List summaries",
            description = "Returns all saved summaries (optional)"
    )
    @ApiResponse(responseCode = "200", description = "Summaries list retrieved successfully")
    @GetMapping(value = "/resources/summaries", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SummaryResponse> listSummaries() {
        log.info("GET /resources/summaries");
        return mcpApplicationService.listSummaries().stream()
                .map(SummaryResponse::fromDomain)
                .toList();
    }
}
