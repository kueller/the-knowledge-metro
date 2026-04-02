package com.glitch.knowledge.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VerifyResponse {
    private int status;
    private String message;
    private Integer origin_id;

    public VerifyResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.origin_id = null;
    }

    public VerifyResponse(int status, String message, int origin_id) {
        this.status = status;
        this.message = message;
        this.origin_id = origin_id;
    }

    @JsonProperty("status")
    public int getStatus() {
        return this.status;
    }

    @JsonProperty("message")
    public String getMessage() {
        return this.message;
    }

    @JsonProperty("origin_id")
    public Integer getOriginId() {
        return this.origin_id;
    }
}
