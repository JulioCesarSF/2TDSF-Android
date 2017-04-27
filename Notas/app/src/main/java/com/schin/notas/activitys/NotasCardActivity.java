package com.schin.notas.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.schin.notas.LoginActivity;
import com.schin.notas.R;
import com.schin.notas.entity.CloudantResponse;
import com.schin.notas.entity.Row;
import com.schin.notas.utils.CloudantRequestInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotasCardActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRetornar;
    private ArrayList<Row> rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_card);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaNotas();
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

    private void carregaNotas() {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("https://33774666-fc8b-406f-b354-2622d4b74751-bluemix.cloudant.com/fiap-notas/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CloudantRequestInterface api = retrofit.create(CloudantRequestInterface.class);
        api.getAllDocs().enqueue(new Callback<CloudantResponse>() {
            @Override
            public void onResponse(Call<CloudantResponse> call, Response<CloudantResponse> response) {
                CloudantResponse cloudantResponse = response.body();

                rows = new ArrayList<>(Arrays.asList(cloudantResponse.getRows()));

                for (Row item : rows) {
                    Log.d("Nota", item.getDoc().getAssunto());
                }

            }

            @Override
            public void onFailure(Call<CloudantResponse> call, Throwable t) {

            }
        });
    }
}
