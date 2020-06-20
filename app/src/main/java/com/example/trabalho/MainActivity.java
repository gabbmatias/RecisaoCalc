package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LocalDatabase db;
    private EditText edtUsuario;
    private EditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = LocalDatabase.getDatabase(getApplicationContext());

    }

    public void cadastrarUsuario(View view){ startActivity(new Intent(this, UsuarioView.class));}

    public void logar(View view){

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);

        String usuarioText = edtUsuario.getText().toString();

        String senha = edtSenha.getText().toString();
        Usuario user = db.usuarioModel().get(usuarioText);
        if(user != null){
            String senhaAtual = user.getSenha();
            if(senhaAtual.equals(senha)){
                startActivity(new Intent(this, PaginaInicial.class));
            }
            else {
                Toast.makeText(this, "Senha Inválida", Toast.LENGTH_SHORT).show();
            }
        }
         else{
            Toast.makeText(this, "Usuário Inválido", Toast.LENGTH_SHORT).show();
        }
    }
}