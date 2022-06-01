package com.shareNwork.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;

public class QueryParams {

    private final Map<String, String> params;

    public QueryParams() {
        this.params = new LinkedHashMap<>();
    }

    public QueryParams addParam(String queryParam, String value) {
        params.put(queryParam, value);
        return this;
    }

    public String getParamValue(String queryParam) {
        return params.get(queryParam);
    }

    public String removeParam(String queryParam) {
        return params.remove(queryParam);
    }

    public boolean containsParam(String queryParam) {
        return params.containsKey(queryParam);
    }

    public String asString() {
        StringJoiner joiner = new StringJoiner("&");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            joiner.add(entry.getKey() + "=" + entry.getValue());
        }
        return joiner.toString();
    }
}
