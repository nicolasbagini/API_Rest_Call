package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class detalle_auto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_auto);

        Bundle b = getIntent().getExtras();

        String id = b.getString("id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AutoService autoService = retrofit.create(AutoService.class);

        Call<Auto> http_call = autoService.getAuto(id);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto auto = response.body();

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

                /*Toast.makeText(detalle_auto.this,
                        auto.getMarca(),
                        Toast.LENGTH_LONG).show();*/

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(detalle_auto.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);

            }
        });
    }
}
