package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PaginaInicial extends AppCompatActivity {

    private Button btnRecisoes, btnEmpresas, button, btnUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pagina_inicial);
        btnRecisoes = findViewById(R.id.btnRecisoes);
        btnEmpresas = findViewById(R.id.btnEmpresas);
        button = findViewById(R.id.button);
        btnUsuarios = findViewById(R.id.btnUsuarios);
    }

    public void listarFuncionarios(View view) { startActivity(new Intent(this, FuncionarioList.class)); }

    public void listarEmpresas(View view) { startActivity(new Intent(this, EmpresaList.class)); }

    public void verificarRevolucao(View view) { startActivity(new Intent(this, FuncionarioAltView.class)); }

    public void editarUsuario(View view) { startActivity(new Intent(this, UsuarioList.class)); }

    public void voltar(View view) { finish(); }

}