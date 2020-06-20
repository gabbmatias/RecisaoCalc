package com.example.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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

    public void logar(View view) throws InvalidKeySpecException, NoSuchAlgorithmException {

        edtUsuario = findViewById(R.id.edtUsuario);
        edtSenha = findViewById(R.id.edtSenha);

        String usuarioText = edtUsuario.getText().toString();

        String senha = edtSenha.getText().toString();
        Usuario user = db.usuarioModel().get(usuarioText);

        if(user != null){
            String senhaAtual = user.getSenha();

            if(validatePassword(senha, senhaAtual)){
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

    private boolean validatePassword(String senha, String senhaAtual) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = senhaAtual.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for(int i = 0; i<bytes.length ;i++)
        {
            bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}