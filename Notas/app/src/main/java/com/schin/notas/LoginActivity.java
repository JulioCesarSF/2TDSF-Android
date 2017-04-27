package com.schin.notas;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.schin.notas.activities.CadastraLoginActivity;
import com.schin.notas.activities.NotasCardActivity;
import com.schin.notas.utils.ArquivoDB;
import com.schin.notas.utils.Chave;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin, btnPrimeiroAcesso;
    private EditText edtEmail, edtSenha;
    private ArquivoDB arquivoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initArquivoDB();
    }

    private void initArquivoDB() {
        arquivoDB = ArquivoDB.getInst(getApplicationContext());
    }

    private void initViews() {
        btnLogin = (Button) findViewById(R.id.btnLogar);
        btnLogin.setOnClickListener(this);

        btnPrimeiroAcesso = (Button) findViewById(R.id.btnPrimeiroAcesso);
        btnPrimeiroAcesso.setOnClickListener(this);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogar:
                logar();
                break;

            case R.id.btnPrimeiroAcesso:
                cadastrar();
                break;
        }
    }

    private void cadastrar() {
        Intent cadastro = new Intent(this, CadastraLoginActivity.class);
        startActivity(cadastro);
        finish();
    }

    private void logar() {
        if (!validarLogin(edtEmail.getText().toString(), edtSenha.getText().toString())) {
            display(getString(R.string.login_invalido));
            return;
        }
        Intent notas = new Intent(this, NotasCardActivity.class);
        startActivity(notas);
        finish();
    }

    public boolean validarLogin(String email, String senha) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(senha)) {
            String prefEmail = arquivoDB.retornarValor(Chave.EMAIL.toString());
            String prefSenha = arquivoDB.retornarValor(Chave.SENHA.toString());
            if (email.equals(prefEmail) && senha.equals(prefSenha)) {
                return true;
            }
        }
        return false;
    }

    public void display(String msg) {
        Snackbar snackbar = Snackbar.make(getCurrentFocus(), msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
