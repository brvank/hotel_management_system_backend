package com.project.hms.response_model;

public abstract class Response {
    protected String token;

    public Response(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
