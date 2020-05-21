package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ingresar_auto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_auto);

        final EditText txt_marca = (EditText) findViewById(R.id.txt_marca);
        final EditText txt_modelo = (EditText) findViewById(R.id.txt_modelo);
        Button btn_salir = (Button) findViewById(R.id.btn_salir);
        Button btn_guardar = (Button) findViewById(R.id.btn_guardar);


        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salir();
            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Auto auto = new Auto(
                        txt_marca.getText().toString(),
                        txt_modelo.getText().toString()
                );
                try{
                    Call<Void> http_call = API.getAutoService().Post(auto);
                    http_call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(ingresar_auto.this, "Auto almacenado.", Toast.LENGTH_LONG).show();
                            salir();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(ingresar_auto.this, "Error, no se almaceno el auto.", Toast.LENGTH_LONG).show();
                        }
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void salir() {
        Intent intent = new Intent(ingresar_auto.this, MainActivity.class);

        startActivity(intent);
    }
}


