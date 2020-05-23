package com.example.api_rest_call;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.text.TextUtils.isEmpty;


public class detalle_auto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_auto);

        Bundle bundle = getIntent().getExtras();

        final String id = bundle.getString("id");

        // Veo que se pasa bien el Id
        //Toast.makeText(detalle_auto.this, id, Toast.LENGTH_LONG).show();

        final EditText text_marca = (EditText) findViewById(R.id.text_marca);
        final EditText text_modelo = (EditText) findViewById(R.id.text_modelo);

        Button btn_volver = (Button) findViewById(R.id.btn_volver);
        Button btn_actualizar = (Button) findViewById(R.id.btn_actualizar);
        Button btn_eliminar = (Button) findViewById(R.id.btn_eliminar);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver();
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text_marca.getText().toString().trim().length() != 0 && text_modelo.getText().toString().trim().length() != 0) {
                    final Auto auto = new Auto(
                            text_marca.getText().toString(),
                            text_modelo.getText().toString()
                    );


                    new AlertDialog.Builder(detalle_auto.this)
                            .setTitle("Actualizar auto")
                            .setMessage("¿Desea actualizar la informacion de este auto?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    actualizar(id, auto);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else
                    new AlertDialog.Builder(detalle_auto.this)
                            .setTitle("Actualizar auto")
                            .setMessage("Debe ingresar la 'marca' y 'modelo' del auto.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(detalle_auto.this)
                        .setTitle("Eliminar auto")
                        .setMessage("¿Desea eliminar el auto seleccionado?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                eliminar(id);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        AutoService autoService = API.getAutoService();

        Call<Auto> http_call = autoService.getAuto(id);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto auto = response.body();

                // Veo que el auto esta vacio!!!
                //Toast.makeText(detalle_auto.this, (CharSequence) auto, Toast.LENGTH_LONG).show();


                TextView text_id = (TextView) findViewById(R.id.text_id);
                if (auto!=null){
                    text_id.setText(auto.getId());
                }
                EditText text_marca = (EditText) findViewById(R.id.text_marca);
                if (auto!=null) {
                    text_marca.setText(auto.getMarca());
                }
                EditText text_modelo = (EditText) findViewById(R.id.text_modelo);
                if (auto!=null) {
                    text_modelo.setText(auto.getModelo());
                }

                Toast.makeText(detalle_auto.this, "Se cargo la informacion del auto.", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(detalle_auto.this, "Error, fallo la llamada a la API", Toast.LENGTH_LONG).show();
            }
        });


    }
    public void volver(){
        Intent intent = new Intent(detalle_auto.this, MainActivity.class);

        Toast.makeText(getApplicationContext(), "Pagina principal.", Toast.LENGTH_LONG).show();

        startActivity(intent);
    }

    public void actualizar(String id, Auto auto){
        AutoService autoService = API.getAutoService();

        Call<Auto> http_update = autoService.Update(id, auto);

        // aca falla?

        http_update.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Toast.makeText(getApplicationContext(), "Se actualizo la informacion el auto.", Toast.LENGTH_LONG).show();
                volver();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(detalle_auto.this, "Error, no se pudo actualizar la informacion del auto.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void eliminar(String id){
        AutoService autoService = API.getAutoService();

        Call<Void> http_delete = autoService.Delete(id);

        http_delete.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Se elimino el auto.", Toast.LENGTH_LONG).show();
                volver();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(detalle_auto.this, "Error, no se pudo eliminar el auto.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
