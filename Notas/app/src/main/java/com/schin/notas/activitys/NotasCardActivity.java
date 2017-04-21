package com.schin.notas.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.schin.notas.LoginActivity;
import com.schin.notas.R;

public class NotasCardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRetornar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_card);

        initViews();
    }

    private void initViews() {
        btnRetornar = (Button) findViewById(R.id.btnRetornar);
        btnRetornar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRetornar:
                retornar();
                break;
        }
    }

    private void retornar() {
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
        finish();
    }
}
