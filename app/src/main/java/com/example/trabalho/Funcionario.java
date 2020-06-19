package com.example.trabalho;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Empresa.class, parentColumns = "empresaID", childColumns = "empresaID"))
public class Funcionario {

    @PrimaryKey(autoGenerate = true)
    int funcionarioID;
    int empresaID;
    String Nome;
    int diasTrabalhados;
    int diasAvisoPrevio;
    int diasFerias;
    double salario;
    double recisao;

    public Funcionario() {
    }

    public Funcionario(int funcionarioID, int empresaID, String nome, double recisao, int diasTrabalhados, int diasAvisoPrevio, int diasFerias, double salario) {
        this.funcionarioID = funcionarioID;
        this.empresaID = empresaID;
        this.Nome = nome;
        this.diasTrabalhados = diasTrabalhados;
        this.diasAvisoPrevio = diasAvisoPrevio;
        this.diasFerias = diasFerias;
        this.salario = salario;
        this.recisao = recisao;
    }

    public int getFuncionarioID() {
        return funcionarioID;
    }

    public void setFuncionarioID(int funcionarioID) {
        this.funcionarioID = funcionarioID;
    }

    public int getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(int empresaID) {
        this.empresaID = empresaID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public int getDiasTrabalhados() {
        return diasTrabalhados;
    }

    public void setDiasTrabalhados(int diasTrabalhados) {
        this.diasTrabalhados = diasTrabalhados;
    }

    public int getDiasAvisoPrevio() {
        return diasAvisoPrevio;
    }

    public void setDiasAvisoPrevio(int diasAvisoPrevio) {
        this.diasAvisoPrevio = diasAvisoPrevio;
    }

    public int getDiasFerias() {
        return diasFerias;
    }

    public void setDiasFerias(int diasFerias) {
        this.diasFerias = diasFerias;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public double getRecisao() { return recisao; }

    public void setRecisao(double recisao) {
        this.recisao = recisao;
    }

    @Override
    public String toString() {
        return getFuncionarioID() + ": " + getNome();
    }
}
