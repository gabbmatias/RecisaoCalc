package com.example.trabalho;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Funcionario {

    @PrimaryKey(autoGenerate = true)
    int funcionarioID;
    int empresaID;
    String Nome;
    double recisao;

    public Funcionario() {
    }

    public Funcionario(int funcionarioID, int empresaID, String nome, double recisao) {
        this.funcionarioID = funcionarioID;
        this.empresaID = empresaID;
        this.Nome = nome;
        this.recisao = recisao;
    }

    public int get
}
