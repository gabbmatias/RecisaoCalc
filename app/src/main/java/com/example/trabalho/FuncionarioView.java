package com.example.trabalho;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private EditText edtSalario;
    private EditText edtFerias;
    private EditText edtDiasTrabalhados;
    private EditText edtAvisoPrevio;
    private Button btExcluir;
    private int dbFuncionarioID;
    private Funcionario dbFuncionario;
    List<Empresa> empresas;
    Spinner spnEmpresas;
    ArrayAdapter<Empresa> empresasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_funcionarios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        edtNome = findViewById(R.id.edtNome);
        edtSalario = findViewById(R.id.edtSalario);
        edtFerias = findViewById(R.id.edtFerias);
        edtDiasTrabalhados = findViewById(R.id.edtDiasTrabalhados);
        edtAvisoPrevio = findViewById(R.id.edtAvisoPrevio);
        btExcluir = findViewById(R.id.btnExcluirFuncionario);
        spnEmpresas = findViewById(R.id.spnEmpresas);
        dbFuncionarioID = getIntent().getIntExtra("FUNCIONARIO_SELECIONADO_ID", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dbFuncionarioID >= 0){
            preencheFuncionario();
        } else {
            btExcluir.setVisibility(View.GONE);
        }
        preencheEmpresas();
    }

    private void preencheFuncionario() {
        dbFuncionario = db.funcionarioModel().get(dbFuncionarioID);
        edtNome.setText(dbFuncionario.getNome());
        edtSalario.setText(Double.toString(dbFuncionario.getSalario()));
        edtFerias.setText(Integer.toString(dbFuncionario.getDiasFerias()));
        edtAvisoPrevio.setText(Integer.toString(dbFuncionario.getDiasAvisoPrevio()));
        edtDiasTrabalhados.setText(Integer.toString(dbFuncionario.getDiasTrabalhados()));
    }

    private void preencheEmpresas() {
        empresas = db.empresaModel().getAll();
        empresasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empresas);
        spnEmpresas.setAdapter(empresasAdapter);
        if(dbFuncionario != null) {
            spnEmpresas.setSelection(dbFuncionario.getEmpresaID() -1);
        }
    }

    public void salvarFuncionario(View view) {
        String nome = edtNome.getText().toString();

        //captura e casting de salário
        String tempSalario = edtSalario.getText().toString();
        double salario = Double.parseDouble(tempSalario);

        //captura e casting de ferias
        String tempFerias = edtFerias.getText().toString();
        int ferias = Integer.parseInt(tempFerias);

        //captura e casting de dias trabalhados
        String tempDiasTrabalhados = edtDiasTrabalhados.getText().toString();
        int diasTrabalhados = Integer.parseInt(tempDiasTrabalhados);

        //captura e casting de dias de aviso prévio
        String tempAvisoPrevio = edtAvisoPrevio.getText().toString();
        int avisoPrevio = Integer.parseInt(tempAvisoPrevio);

        String novaEmpresa = "";

        if(spnEmpresas.getSelectedItem() != null){
            novaEmpresa = spnEmpresas.getSelectedItem().toString();
        }

        if(nome.equals("")){
            Toast.makeText(this, "O nome é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }

        if(novaEmpresa.equals("")) {
            Toast.makeText(this, "O funcionario precisa de uma empresa.", Toast.LENGTH_SHORT).show();
            return;
        }

        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(nome);
        novoFuncionario.setSalario(salario);
        novoFuncionario.setDiasFerias(ferias);
        novoFuncionario.setDiasTrabalhados(diasTrabalhados);
        novoFuncionario.setDiasAvisoPrevio(avisoPrevio);
        novoFuncionario.setEmpresaID(empresas.get(spnEmpresas.getSelectedItemPosition()).getEmpresaID());

        if(dbFuncionario != null){
            novoFuncionario.setFuncionarioID(dbFuncionarioID);
            db.funcionarioModel().update(novoFuncionario);
            Toast.makeText(this, "Funcionario atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.funcionarioModel().insertAll(novoFuncionario);
            Toast.makeText(this, "Funcionario cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void excluirFuncionario(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Funcionario")
                .setMessage("Deseja excluir esse funcionario?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }

    public void excluir() {
        db.funcionarioModel().delete(dbFuncionario);
        Toast.makeText(this, "Funcionario excluído com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void voltar(View view) {
        finish();
    }

    public void cadastrarEmpresa(View view) {
        startActivity(new Intent(this, EmpresaView.class));
    }

}
