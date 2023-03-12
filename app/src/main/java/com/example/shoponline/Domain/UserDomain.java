package com.example.shoponline.Domain;

public class UserDomain {
    private String username;
    private String password;
    private String confirmPassword;

    public UserDomain(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDomain(String username, String password, String confirmPassword) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username.trim();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword.trim();
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isValidPassword() {
        return getPassword().equals(getConfirmPassword());
    }
}
