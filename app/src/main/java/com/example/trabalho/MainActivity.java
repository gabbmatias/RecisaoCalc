package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listarFuncionarios(View view) { startActivity(new Intent(this, FuncionarioList.class)); }

    public void listarEmpresas(View view) { startActivity(new Intent(this, EmpresaList.class)); }
}