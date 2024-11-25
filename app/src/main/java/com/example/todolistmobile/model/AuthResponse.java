package com.example.todolistmobile.model;

public class AuthResponse {
    private User user;       // Dados do usuário retornado em caso de sucesso
    private String error;    // Mensagem de erro retornada em caso de falha

    // Getters e Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
