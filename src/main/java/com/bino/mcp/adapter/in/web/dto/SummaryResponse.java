package com.bino.mcp.adapter.in.web.dto;

import com.bino.mcp.domain.Summary;


public record SummaryResponse(String title, String summary) {

    public static SummaryResponse fromDomain(Summary summary) {
        return new SummaryResponse(summary.title(), summary.summary());
    }
}
