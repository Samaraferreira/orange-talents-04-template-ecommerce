package br.com.zup.edu.ecommerce.shared.security;

public class LoginResponse {

    private String accessToken;

    @Deprecated
    public LoginResponse() {}

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
