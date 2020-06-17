package com.example.trabalho;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EmpresaView extends AppCompatActivity {

    LocalDatabase db;
    private EditText edtEmpresa;
    private Button btnSalvar, btnExcluir;
    private int dbEmpresaID;
    private Empresa dbEmpresa;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = LocalDatabase.getDatabase(getApplicationContext());
        edtEmpresa = findViewById(R.id.edtEmpresa);
        btnSalvar = findViewById(R.id.btnSalvarEmpresa);
        btnExcluir = findViewById(R.id.btnExcluirEmpresa);
        dbEmpresaID = getIntent().getIntExtra("EMPRESA_SELECIONADA_ID", -1);
    }

    protected void onResume() {
        super.onResume();
        if (dbEmpresaID >= 0) {
            getDBEmpresa();
        } else {
            btnExcluir.setVisibility(View.GONE);
        }
    }

    private void getDBEmpresa() {
        dbEmpresa = db.empresaModel().get(dbEmpresaID);
        edtEmpresa.setText(dbEmpresa.getEmpresa());
    }

    public void salvarEmpresa() {
        String nomeEmpresa = edtEmpresa.getText().toString();

        if (nomeEmpresa.equals("")) {
            Toast.makeText(this, "O Nome da empresa não pode estar vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        Empresa thisEmpresa = new Empresa();
        thisEmpresa.setEmpresa(nomeEmpresa);

        if (dbEmpresa != null) {
            thisEmpresa.setEmpresaID(dbEmpresaID);
            db.empresaModel().update(thisEmpresa);
            Toast.makeText(this, "Empresa atualizada com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            db.empresaModel().insertAll(thisEmpresa);
            Toast.makeText(this, "Empresa criada com sucesso", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void excluirEmpresa(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exclusão de Empresa")
                .setMessage("Tem certeza que deseja excluir essa empresa?")
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("não", null)
                .show();
    }

    public void excluir(){
        try {
            db.empresaModel().delete(dbEmpresa);
            Toast.makeText(this, "Empresa excluída com sucesso", Toast.LENGTH_SHORT).show();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(this, "Impossível excluir empresa com recisões pendentes", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void voltar() {
        finish();
    }
}
