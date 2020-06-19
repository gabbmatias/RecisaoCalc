package com.example.trabalho;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class FuncionarioAltView extends AppCompatActivity {
    private EditText edtSalario;
    private EditText edtFerias;
    private EditText edtDiasTrabalhados;
    private EditText edtDiasAvisoPrevio;
    private EditText edtRecisaoInformada;
    private TextView textViewRecisao;
    private MediaPlayer mySong;
    double salario;
    int ferias;
    int diasTrabalhados;
    int diasAvisoPrevio;
    double recisaoInformada;
    double valorRecisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_funcionariosalt);
        mySong = MediaPlayer.create(this, R.raw.mysong);
        mySong.start();
        edtSalario = findViewById(R.id.edtSalarioAlt);
        edtFerias = findViewById(R.id.edtFeriasAlt);
        edtDiasTrabalhados = findViewById(R.id.edtDiasTrabalhadosAlt);
        edtDiasAvisoPrevio = findViewById(R.id.edtAvisoPrevioAlt);
        edtRecisaoInformada = findViewById(R.id.edtRecisaoInformada);
        textViewRecisao = findViewById(R.id.textViewRecisaoAlt);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void verificarRecisao(View view) {

        String tempSalario = edtSalario.getText().toString();
        salario = Double.parseDouble(tempSalario);

        String tempDiasTrabalhados = edtDiasTrabalhados.getText().toString();
        diasTrabalhados = Integer.parseInt(tempDiasTrabalhados);

        String tempFerias = edtFerias.getText().toString();
        ferias = Integer.parseInt(tempFerias);

        String tempDiasAvisoPrevio = edtDiasAvisoPrevio.getText().toString();
        diasAvisoPrevio = Integer.parseInt(tempDiasAvisoPrevio);

        String tempRecisaoInformada = edtRecisaoInformada.getText().toString();

        if (tempRecisaoInformada.equals("")) {
            Toast.makeText(this, "Informe sua recisão, camarada", Toast.LENGTH_SHORT).show();
            return;
        }

        recisaoInformada = Double.parseDouble(tempRecisaoInformada);

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
        String totalRecisao = Double.toString(valorRecisao);

        textViewRecisao.setText(totalRecisao);

        if(valorRecisao == recisaoInformada) {
            Toast.makeText(this, "O patrão está a salvo...por hora", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "SE O POVO TUDO PRODUZ, AO POVO TUDO PERTENCE! PEGUE AS TOCHAS", Toast.LENGTH_SHORT).show();
        }


    }

    public void voltar(View view) {
        mySong.release();
        finish();
    }
}
