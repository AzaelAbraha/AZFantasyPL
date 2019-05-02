package com.example.azfantasypl;

public class User {
    public String username;
    public String email;
    public String password;
    public long score;

    public User() { }

    public User(String username, String email, String password, long score) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public long getScore() {
        return score;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setScore(long score) {
        this.score = score;
    }
}
