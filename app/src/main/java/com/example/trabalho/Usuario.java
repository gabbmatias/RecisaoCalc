package com.example.trabalho;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    int usuarioId;
    String unome, email, usuario, senha;

    public Usuario() {
    }

    public Usuario(int usuarioId, String unome, String email, String usuario, String senha) {
        this.usuarioId = usuarioId;
        this.unome = unome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUnome() {
        return unome;
    }

    public void setUnome(String unome) {
        this.unome = unome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return getUsuarioId() + ":" + getUnome();
    }
}
