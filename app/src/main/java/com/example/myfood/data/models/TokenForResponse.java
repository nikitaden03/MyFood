package com.example.myfood.data.models;

public class TokenForResponse {
    int codeResponse;
    String token;

    public TokenForResponse(int codeResponse, String token) {
        this.codeResponse = codeResponse;
        this.token = token;
    }

    public TokenForResponse() {
    }

    public int getCodeResponse() {
        return codeResponse;
    }

    public void setCodeResponse(int codeResponse) {
        this.codeResponse = codeResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
