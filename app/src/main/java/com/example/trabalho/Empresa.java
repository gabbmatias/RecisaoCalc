package com.example.trabalho;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Empresa {

    @PrimaryKey(autoGenerate = true)
    int empresaID;
    String empresa;

    public Empresa(){

    }

    public Empresa(int empresaID, String empresa){
        this.empresaID = empresaID;
        this.empresa = empresa;
    }

    public int getEmpresaID() {
        return empresaID;
    }

    public void setEmpresaID(int empresaID) {
        this.empresaID = empresaID;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString(){
        return this.empresaID + ": " + this.empresa;
    }
}
