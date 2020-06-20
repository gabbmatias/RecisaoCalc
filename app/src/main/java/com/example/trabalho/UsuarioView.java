package com.example.trabalho;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
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

public class UsuarioView extends AppCompatActivity {

    LocalDatabase db;
    private EditText edtUNome;
    private EditText edtEmail;
    private EditText edtUsuario;
    private EditText edtSenha;
    private EditText getEdtSenhaConfirm;
    private int dbUsuarioID;
    private Usuario dbUsuario;
    private Button btExcluir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_usuarios);
        db = LocalDatabase.getDatabase(getApplicationContext());
        edtUNome = findViewById(R.id.edtUNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);
        getEdtSenhaConfirm = findViewById(R.id.edtSenhaConfim);
        btExcluir = findViewById(R.id.btnExcluirUsuario);
        dbUsuarioID = getIntent().getIntExtra("USUARIO_SELECIONADO_ID", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(dbUsuarioID >= 0){
            preencheUsuario();
        }
        else {
            btExcluir.setVisibility(View.GONE);
        }
    }

    private void preencheUsuario() {
        dbUsuario = db.usuarioModel().get(dbUsuarioID);
        edtUNome.setText(dbUsuario.getUnome());
        edtEmail.setText(dbUsuario.getEmail());
        edtUsuario.setText(dbUsuario.getUsuario());
        edtSenha.setText(dbUsuario.getSenha());
        getEdtSenhaConfirm.setText(dbUsuario.getSenhaconfirm());

    }

    public void salvarUsuario(View view) {
        String unome = edtUNome.getText().toString();
        if(unome.equals("")){
            Toast.makeText(this, "O nome é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = edtEmail.getText().toString();
        if (email.equals("")) {
            Toast.makeText(this, "Informe o e-mail", Toast.LENGTH_SHORT).show();
            return;
        }

        String usuario = edtUsuario.getText().toString();
        if (usuario.equals("")) {
            Toast.makeText(this, "Informe o usuario", Toast.LENGTH_SHORT).show();
            return;
        }

        String senha = edtSenha.getText().toString();
        if (senha.equals("")) {
            Toast.makeText(this, "Informe a senha", Toast.LENGTH_SHORT).show();
            return;
        }

        String senhaconfirm = getEdtSenhaConfirm.getText().toString();
        if(senhaconfirm.equals("")){
            Toast.makeText(this, "Confirme a senha", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!senha.equals(senhaconfirm)){
            Toast.makeText(this, "As senhas devem ser iguais", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUnome(unome);
        novoUsuario.setEmail(email);
        novoUsuario.setUsuario(usuario);
        novoUsuario.setSenha(senha);
        novoUsuario.setSenhaconfirm(senhaconfirm);

        if(dbUsuario != null){
            novoUsuario.setUsuarioId(dbUsuarioID);
            db.usuarioModel().update(novoUsuario);
            Toast.makeText(this, "Usuario atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        } else {
            db.usuarioModel().insertAll(novoUsuario);
            Toast.makeText(this, "Usuario cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void excluirUsuario(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de Usuário")
                .setMessage("Deseja excluir esse usuário?")
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
        db.usuarioModel().delete(dbUsuario);
        Toast.makeText(this, "Usuário excluído com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void voltar(View view) {

        finish();
    }

}