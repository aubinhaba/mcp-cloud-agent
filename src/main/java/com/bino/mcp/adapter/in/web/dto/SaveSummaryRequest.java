package com.bino.mcp.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record SaveSummaryRequest(
        @NotBlank(message = "Title is required")
        @Size(max = 500, message = "Title must not exceed 500 characters")
        String title,

        @NotBlank(message = "Summary is required")
        @Size(max = 10000, message = "Summary must not exceed 10000 characters")
        String summary
) {
}
