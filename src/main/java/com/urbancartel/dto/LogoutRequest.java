// LogoutRequest.java
package com.urbancartel.dto;

import com.urbancartel.entity.User;

public class LogoutRequest {
    public com.urbancartel.entity.User User;
    private String refreshToken;

    public LogoutRequest() {
    }

    public LogoutRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public com.urbancartel.entity.User getUser() {
        return User;
    }

    public void setUser(com.urbancartel.entity.User user) {
        User = user;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}