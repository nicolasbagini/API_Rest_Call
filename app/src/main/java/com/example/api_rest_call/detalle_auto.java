package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class detalle_auto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_auto);

        Bundle bundle = getIntent().getExtras();

        String id = bundle.getString("id");

        // Veo que se pasa bien el Id
        Toast.makeText(detalle_auto.this, id, Toast.LENGTH_LONG).show();

        Button btn_volver = (Button) findViewById(R.id.btn_volver);
        Button btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
        Button btn_eliminar = (Button) findViewById(R.id.btn_eliminar);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

        AutoService autoService = API.getAutoService();

        Call<Auto> http_call = autoService.getAuto(id);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto auto = response.body();

                // Veo que el auto esta vacio!!!
                Toast.makeText(detalle_auto.this, (CharSequence) auto, Toast.LENGTH_LONG).show();


                TextView text_id = (TextView) findViewById(R.id.text_id);
                if (auto!=null){
                    text_id.setText(auto.getId());
                }
                TextView text_marca = (TextView) findViewById(R.id.text_marca);
                if (auto!=null) {
                    text_marca.setText(auto.getMarca());
                }
                TextView text_modelo = (TextView) findViewById(R.id.text_modelo);
                if (auto!=null) {
                    text_modelo.setText(auto.getModelo());
                }

                Toast.makeText(detalle_auto.this, "Se cargo la informacion del auto.", Toast.LENGTH_LONG);

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(detalle_auto.this, "Error, fallo la llamada a la API", Toast.LENGTH_LONG);
            }
        });


    }
    public void volver(){
        Intent intent = new Intent(detalle_auto.this, MainActivity.class);

        Toast.makeText(getApplicationContext(), "Pagina principal.", Toast.LENGTH_LONG);

        startActivity(intent);
    }
}
