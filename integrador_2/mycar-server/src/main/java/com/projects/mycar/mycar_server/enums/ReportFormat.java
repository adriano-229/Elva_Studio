package com.projects.mycar.mycar_server.enums;

import java.util.Arrays;

public enum ReportFormat {
    PDF("pdf", "application/pdf"),
    XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final String queryValue;
    private final String contentType;

    ReportFormat(String queryValue, String contentType) {
        this.queryValue = queryValue;
        this.contentType = contentType;
    }

    public static ReportFormat fromQueryValue(String value) {
        return Arrays.stream(values())
                .filter(format -> format.queryValue.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Formato no soportado: " + value));
    }

    public String getQueryValue() {
        return queryValue;
    }

    public String getContentType() {
        return contentType;
    }

    public String filenameSuffix() {
        return "." + queryValue;
    }
}
