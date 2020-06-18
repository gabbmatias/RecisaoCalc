package com.example.trabalho;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class EmpresaList extends AppCompatActivity {

    LocalDatabase db;
    List<Empresa> empresas;
    ListView listViewEmpresas;
    Intent edtIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_empresas);
        db = LocalDatabase.getDatabase(getApplicationContext());
        listViewEmpresas = findViewById(R.id.listViewEmpresas);
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtIntent = new Intent(this, EmpresaView.class);
        preencheEmpresas();
    }

    private void preencheEmpresas() {
        empresas = db.empresaModel().getAll();
        ArrayAdapter<Empresa> empresaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, empresas);
        listViewEmpresas.setAdapter(empresaAdapter);
        listViewEmpresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Empresa marcaselecionada = empresas.get(position);
                edtIntent.putExtra("EMPRESA_SELECIONADA_ID", marcaselecionada.empresaID);
                startActivity(edtIntent);
            }
        });
    }

    public void cadastrarEmpresa(View view) {

        startActivity(edtIntent);

    }

    public void voltar(View view) {

        finish();

    }
}
