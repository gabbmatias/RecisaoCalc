package com.example.trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class FuncionarioList extends AppCompatActivity {

    LocalDatabase db;
    List<Funcionario> funcionarios;
    ListView listViewFuncionario;
    Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_funcionarios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewFuncionario = findViewById(R.id.listViewFuncionarios);
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, FuncionarioView.class);
        preencheFuncionarios();
    }

    private void preencheFuncionarios() {
        funcionarios = db.funcionarioModel().getAll();
        ArrayAdapter<Funcionario> funcionarioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, funcionarios);
        listViewFuncionario.setAdapter(funcionarioAdapter);
        listViewFuncionario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id){
                Funcionario funcionarioSelecionado = funcionarios.get(position);
                edtIntent.putExtra("FUNCIONARIO_SELECIONADO_ID", funcionarioSelecionado.funcionarioID);
                startActivity(edtIntent);
            }
        });
    }

    public void cadastrarFuncionario(View view) { startActivity(edtIntent); }

    public void voltar(View view) { finish(); }
}
