package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class UsuarioList extends AppCompatActivity {
    LocalDatabase db;
    List<Usuario> usuarios;
    ListView listViewUsuario;
    Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_usuarios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewUsuario = findViewById(R.id.listViewUsuarios);
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, UsuarioView.class);
        preencheUsuarioes();
    }

    private void preencheUsuarioes() {
        usuarios = db.usuarioModel().getAll();
        ArrayAdapter<Usuario> usuariosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usuarios);
        listViewUsuario.setAdapter(usuariosAdapter);
        listViewUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id){
                Usuario usuarioselecionado = usuarios.get(position);
                edtIntent.putExtra("USUARIO_SELECIONADO_ID", usuarioselecionado.usuarioId);
                startActivity(edtIntent);
            }
        });
    }

    public void cadastrarUsuario(View view) {

        startActivity(edtIntent);

    }

    public void voltar(View view) {

        finish();
    }

}