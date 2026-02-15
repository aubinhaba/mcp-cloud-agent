package com.bino.mcp.adapter.in.web;

import com.bino.mcp.adapter.in.web.dto.SaveSummaryRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = McpController.class)
@Import(McpControllerTestConfig.class)
class McpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listArticles_returnsArticles() throws Exception {
        mockMvc.perform(get("/resources/articles").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articles").isArray())
                .andExpect(jsonPath("$.articles.length()").value(2))
                .andExpect(jsonPath("$.articles[0].title").value("Introduction to AWS Lambda"))
                .andExpect(jsonPath("$.articles[0].id").value("1"));
    }

    @Test
    void saveSummary_withValidRequest_returnsOk() throws Exception {
        SaveSummaryRequest request = new SaveSummaryRequest("Test Title", "Test summary content.");

        mockMvc.perform(post("/tools/saveSummary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Summary saved"));
    }

    @Test
    void saveSummary_withBlankTitle_returnsBadRequest() throws Exception {
        SaveSummaryRequest request = new SaveSummaryRequest("", "Test summary content.");

        mockMvc.perform(post("/tools/saveSummary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation Failed"));
    }

    @Test
    void saveSummary_withBlankSummary_returnsBadRequest() throws Exception {
        SaveSummaryRequest request = new SaveSummaryRequest("Test Title", "   ");

        mockMvc.perform(post("/tools/saveSummary")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Validation Failed"));
    }

    @Test
    void listSummaries_returnsAllSummaries() throws Exception {
        mockMvc.perform(get("/resources/summaries").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
