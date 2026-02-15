package com.bino.mcp.adapter.in.web.dto;

import java.util.List;


public record ArticlesResponse(List<ArticleItem> articles) {

    public record ArticleItem(String id, String title, String url) {
    }
}
