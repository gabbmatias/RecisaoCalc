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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


public class FuncionarioView extends AppCompatActivity {

    LocalDatabase db;
    private EditText edtNome;
    private EditText edtSalario;
    private EditText edtFerias;
    private EditText edtDiasTrabalhados;
    private EditText edtDiasAvisoPrevio;
//    private EditText edtAdicional;
    private Button btExcluir;
    private int dbFuncionarioID;
    private TextView textViewRecisao;
    private Funcionario dbFuncionario;
    double salario;
//    double adicional;
    int ferias;
    int diasTrabalhados;
    int diasAvisoPrevio;
    double valorRecisao;
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
        edtDiasAvisoPrevio = findViewById(R.id.edtAvisoPrevio);
//        edtAdicional = findViewById(R.id.edtAdicional);
        btExcluir = findViewById(R.id.btnExcluirFuncionario);
        textViewRecisao = findViewById(R.id.textViewRecisao);
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
        edtDiasAvisoPrevio.setText(Integer.toString(dbFuncionario.getDiasAvisoPrevio()));
        edtDiasTrabalhados.setText(Integer.toString(dbFuncionario.getDiasTrabalhados()));
        textViewRecisao.setText(Double.toString(dbFuncionario.getRecisao()));
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

        String tempSalario = edtSalario.getText().toString();
        salario = Double.parseDouble(tempSalario);

        String tempDiasTrabalhados = edtDiasTrabalhados.getText().toString();
        diasTrabalhados = Integer.parseInt(tempDiasTrabalhados);

        String tempFerias = edtFerias.getText().toString();
        ferias = Integer.parseInt(tempFerias);

        String tempDiasAvisoPrevio = edtDiasAvisoPrevio.getText().toString();
        diasAvisoPrevio = Integer.parseInt(tempDiasAvisoPrevio);

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

        //calculos da recisao
        double valorDiaria = salario / 30;
        int anosTrabalhados = diasTrabalhados/365;
        int mesesTrabalhados = anosTrabalhados * 12;

        //valor do aviso prévio
        double totalAvisoPrevio = diasAvisoPrevio * valorDiaria + (3 * anosTrabalhados);

        //fgts
        double fgts = salario * 0.08;
        double saldoFgts = fgts * mesesTrabalhados;
        double totalFgts = saldoFgts + (saldoFgts * 0.4);

        //salário Proporcional
        double totalSalarioProporcional = (diasTrabalhados % 30) * valorDiaria;

        //ferias
        double valorDiaFerias = (salario + (salario / 3)) / 30;
        double totalFeriasRemanescentes = valorDiaFerias * ferias;

        //décimo terceiro
        double valorMesDecimoTerceiro = salario/12;
        double mesesProporcionais = (diasTrabalhados % 365) / 30;
        double totalDecimoTerceiro = mesesProporcionais * valorMesDecimoTerceiro;

        valorRecisao = totalAvisoPrevio + totalFgts + totalSalarioProporcional + totalFeriasRemanescentes + totalDecimoTerceiro;

        Funcionario novoFuncionario = new Funcionario();
        novoFuncionario.setNome(nome);
        novoFuncionario.setSalario(salario);
        novoFuncionario.setDiasTrabalhados(diasTrabalhados);
        novoFuncionario.setDiasFerias(ferias);
        novoFuncionario.setDiasAvisoPrevio(diasAvisoPrevio);
        novoFuncionario.setRecisao(valorRecisao);
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


    public void calcularRecisao(View view) {

        String tempSalario = edtSalario.getText().toString();
        salario = Double.parseDouble(tempSalario);

        String tempDiasTrabalhados = edtDiasTrabalhados.getText().toString();
        diasTrabalhados = Integer.parseInt(tempDiasTrabalhados);

        String tempFerias = edtFerias.getText().toString();
        ferias = Integer.parseInt(tempFerias);

        String tempDiasAvisoPrevio = edtDiasAvisoPrevio.getText().toString();
        diasAvisoPrevio = Integer.parseInt(tempDiasAvisoPrevio);

        double valorDiaria = salario / 30;
        int anosTrabalhados = diasTrabalhados/365;
        int mesesTrabalhados = anosTrabalhados * 12;

        //valor do aviso prévio
        double totalAvisoPrevio = diasAvisoPrevio * valorDiaria + (3 * anosTrabalhados);

        //fgts
        double fgts = salario * 0.08;
        double saldoFgts = fgts * mesesTrabalhados;
        double totalFgts = saldoFgts + (saldoFgts * 0.4);

        //salário Proporcional
        double totalSalarioProporcional = (diasTrabalhados % 30) * valorDiaria;

        //ferias
        double valorDiaFerias = (salario + (salario / 3)) / 30;
        double totalFeriasRemanescentes = valorDiaFerias * ferias;

        //décimo terceiro
        double valorMesDecimoTerceiro = salario/12;
        double mesesProporcionais = (diasTrabalhados % 365) / 30;
        double totalDecimoTerceiro = mesesProporcionais * valorMesDecimoTerceiro;

        valorRecisao = totalAvisoPrevio + totalFgts + totalSalarioProporcional + totalFeriasRemanescentes + totalDecimoTerceiro;

//        String totalRecisao = Double.toString(valorRecisao)
        String totalRecisao = Double.toString(valorRecisao);
//        String novaRecisão = String.format("%.2f" , totalRecisao);

        textViewRecisao.setText(totalRecisao);

    }
}