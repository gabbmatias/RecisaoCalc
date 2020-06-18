package com.example.trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class FuncionarioView extends AppCompatActivity {

    LocalDatabase db;
    private EditText edtNome;
    private Button btnSalvarFuncionario, btExcluir;
    private int dbFuncionarioID;
    private Funcionario dbFuncionario;
    List<Empresa> empresas;
    Spinner spnEmpresas;
    ArrayAdapter<Empresa> empresaAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_funcionarios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        edtNome = findViewById(R.id.edtNome);
        btExcluir = findViewById(R.id.btnExcluirModelo);
        spnEmpresas = findViewById(R.id.spnEmpresas);
        dbFuncionarioID = getIntent().getIntExtra("FUNCIONARIO_SELECIONADO_ID", -1);
    }

    protected void onResume() {
        super.onResume();
        if (dbFuncionarioID >= 0) {
            preencheFuncionario();
        } else {
            btExcluir.setVisibility(View.GONE);
        }
        preencheEmpresas();
    }

    private void preencheFuncionario() {
        dbFuncionario = db.funcionarioModel().get(dbFuncionarioID);
        edtNome.setText(dbFuncionario.getNome());
    }

    private void preencheEmpresas() {
        empresas = db.empresaModel().getAll();
        empresaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empresas);
        spnEmpresas.setAdapter(empresaAdapter);
        if (dbFuncionario != null) {
            spnEmpresas.setSelection(dbFuncionario.getFuncionarioID() -1);
        }
    }

    public void salvarFuncionario(View view) {
        String novoNome = edtNome.getText().toString();
        String novaEmpresa = "";

        if (spnEmpresas.getSelectedItem() != null) {
            novaEmpresa = spnEmpresas.getSelectedItem().toString();
        }

        if (novoNome.equals("")) {
            Toast.makeText(this, "O nome é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }

        if (novaEmpresa.equals("")) {
            Toast.makeText(this, "O funcionário precisa ser alocado em uma empresa", Toast.LENGTH_SHORT).show();
            return;
        }

        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(novoNome);
        novoFuncionario.setEmpresaID(empresas.get(spnEmpresas.getSelectedItemPosition()).getEmpresaID());

        if (dbFuncionario != null) {
            novoFuncionario.setEmpresaID(dbFuncionarioID);
            db.funcionarioModel().update(novoFuncionario);
            Toast.makeText(this, "Funcionário atualizado com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            db.funcionarioModel().insertAll(novoFuncionario);
            Toast.makeText(this, "Funcionário cadastrado com sucesso", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void excluirFuncionario() {
        db.funcionarioModel().delete(dbFuncionario);
        Toast.makeText(this, "Funcionário excluído com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void voltar(View view) { finish(); }

    public void cadastrarEmpresa(View view) { startActivity(new Intent(this, EmpresaView.class)); }
}