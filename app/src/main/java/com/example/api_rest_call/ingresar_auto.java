package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ingresar_auto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_auto);

        EditText txt_marca = (EditText) findViewById(R.id.txt_marca);
        EditText txt_modelo = (EditText) findViewById(R.id.txt_modelo);
        Button btn_salir = (Button) findViewById(R.id.btn_salir);
        Button btn_guardar = (Button) findViewById(R.id.btn_guardar);


        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ingresar_auto.this, MainActivity.class);

                startActivity(intent);

                //Toast.makeText(getApplicationContext(), "Pagina principal", Toast.LENGTH_LONG);
            }
        });
    }
}


