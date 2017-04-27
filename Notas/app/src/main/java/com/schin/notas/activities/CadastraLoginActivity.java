package com.schin.notas.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.schin.notas.LoginActivity;
import com.schin.notas.R;
import com.schin.notas.utils.ArquivoDB;
import com.schin.notas.utils.Chave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CadastraLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtNome, edtSobrenome, edtDataNascimento, edtEmail, edtSenha;
    private RadioGroup rg;
    private ArquivoDB arquivoDB;
    private ProgressDialog dialog;

    private final String[] chaves = {
            Chave.NOME.toString(), Chave.SOBRENOME.toString(), Chave.SEXO.toString(),
            Chave.EMAIL.toString(), Chave.DATA_NASCIMENTO.toString(), Chave.SENHA.toString()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastra_login);

        initViews();
        initArquivoDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarDados();
    }

    private void initArquivoDB() {
        this.arquivoDB = ArquivoDB.getInst(getApplication());
    }

    private void initViews() {
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtSobrenome = (EditText)findViewById(R.id.edtSobrenome);
        edtDataNascimento = (EditText)findViewById(R.id.edtDataNascimento);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

        Button btnGravarDados = (Button) findViewById(R.id.btnGravarDados);
        Button btnExcluirDados = (Button) findViewById(R.id.btnExcluirDados);
        Button btnCarregarDados = (Button) findViewById(R.id.btnCarregarDados);
        Button btnVerificarDados = (Button) findViewById(R.id.btnVerificarDados);

        Button btnGravarArquivo = (Button) findViewById(R.id.btnGravarArquivo);
        Button btnLerArquivo = (Button) findViewById(R.id.btnLerArquivo);
        Button btnExcluirArquivo = (Button) findViewById(R.id.btnExcluirArquivo);

        Button btnRetornar = (Button) findViewById(R.id.btnRetornar);

        btnGravarDados.setOnClickListener(this);
        btnExcluirDados.setOnClickListener(this);
        btnCarregarDados.setOnClickListener(this);
        btnVerificarDados.setOnClickListener(this);
        btnGravarArquivo.setOnClickListener(this);
        btnLerArquivo.setOnClickListener(this);
        btnExcluirArquivo.setOnClickListener(this);
        btnRetornar.setOnClickListener(this);

        rg = (RadioGroup)findViewById(R.id.rgSexo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGravarDados:
                gravarDados();
                break;

            case R.id.btnRetornar:
                fechareRetornar();
                break;

            case R.id.btnExcluirDados:
                excluirDados();
                break;

            case R.id.btnCarregarDados:
                carregarDados();
                break;

            case R.id.btnVerificarDados:
                verificarDados();
                break;

            case R.id.btnGravarArquivo:

                break;

            case R.id.btnLerArquivo:

                break;

            case R.id.btnExcluirArquivo:

                break;
        }
    }

    private void verificarDados() {
        HashMap<String, Boolean> l = arquivoDB.verificarTodasChaves(Arrays.asList(chaves));
        int size = new HashSet(l.values()).size();
        if(size == 1 && l.containsValue(true)){
            display(getString(R.string.dados_ok));
        }else if( size < 1 || l.containsValue(false)){
            display(getString(R.string.dados_fail));
        }
    }

    private void excluirDados() {
        try {
            arquivoDB.excluirChaves(Arrays.asList(
                    chaves
            ));
            display(getString(R.string.dados_excluidos));
        } catch (Exception e){
            display(e.getMessage());
        }
    }

    private void gravarDados() {
        if(!validar()) {
            display(getString(R.string.preenche_corretament));
            return;
        }

        progress(getString(R.string.sharedPref), getString(R.string.salvando));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<>();
                map.put(Chave.NOME.toString(), edtNome.getText().toString());
                map.put(Chave.SOBRENOME.toString(), edtSobrenome.getText().toString());
                map.put(Chave.DATA_NASCIMENTO.toString(), edtDataNascimento.getText().toString());
                map.put(Chave.EMAIL.toString(), edtEmail.getText().toString());
                map.put(Chave.SENHA.toString(), edtSenha.getText().toString());

                int sexoId = rg.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton)findViewById(sexoId);
                map.put(Chave.SEXO.toString(), rb.getText().toString());

                arquivoDB.gravarChaves(map);
                dialog.dismiss();
                display(getString(R.string.salvo_sucesso));
            }
        }, 1000);
    }

    private void carregarDados() {
        edtNome.setText(arquivoDB.retornarValor(Chave.NOME.toString()));
        edtSobrenome.setText(arquivoDB.retornarValor(Chave.SOBRENOME.toString()));
        edtEmail.setText(arquivoDB.retornarValor(Chave.EMAIL.toString()));
        edtSenha.setText(arquivoDB.retornarValor(Chave.SENHA.toString()));
        edtDataNascimento.setText(arquivoDB.retornarValor(Chave.DATA_NASCIMENTO.toString()));

        String sexo = arquivoDB.retornarValor(Chave.SEXO.toString());

        int count = rg.getChildCount();
        for(int i  =0; i < count; i++){
            if(rg.getChildAt(i) instanceof RadioButton){
                if(((RadioButton) rg.getChildAt(i)).getText().toString().equals(sexo)){
                    ((RadioButton) rg.getChildAt(i)).setChecked(true);
                }
            }
        }
    }

    private boolean validar(){
        if(Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()
                && !TextUtils.isEmpty(edtNome.getText().toString())
                && !TextUtils.isEmpty(edtSobrenome.getText().toString())
                && !TextUtils.isEmpty(edtSenha.getText().toString())
                && !TextUtils.isEmpty(edtDataNascimento.getText().toString())){
            return true;
        }
        return false;
    }

    private void display(String msg){
        Snackbar snackbar = Snackbar.make(getWindow().getDecorView().getRootView(), msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    private void progress(String title, String msg){
        dialog = ProgressDialog.show(this, title, msg, true);
    }

    private void fechareRetornar() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}
