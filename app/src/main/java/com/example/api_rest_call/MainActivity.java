package com.example.api_rest_call;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends ListActivity {

    ListView list;
    ListAdapter adaptador;
    ArrayList<String> autos = new ArrayList<>();
    ArrayList<Auto> lista_autos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autos);

        list = (ListView) findViewById(android.R.id.list);

        list.setAdapter(adaptador);

        this.getListadoVehiculos();

    }


    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        /*Toast.makeText(
                MainActivity.this,
                // 57 list.getItemAtPosition(position).toString(),
                "clicleo posicion" + lista_autos.get(position).getId(),
                Toast.LENGTH_LONG
        ) .show();*/

        Intent intent = new Intent(MainActivity.this, detalle_auto.class);

        // 1 intent.putExtra( "id", lista_autos.get(position).getId());
        // 2 intent.putExtra("id", position);

        intent.putExtra("id", lista_autos.get(position).getId());

        startActivity(intent);

    }

    public void getListadoVehiculos(){

        // Establezco una relacion de mi app con este endpoint:
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Defnimos la interfaz para que utilice la base retrofit de mi aplicacion ()
        AutoService autoService = retrofit.create(AutoService.class);

        Call<List<Auto>> http_call = autoService.getAutos();

        http_call.enqueue(new Callback<List<Auto>>() {
            @Override
            public void onResponse(Call<List<Auto>> call, Response<List<Auto>> response) {
                // Si el servidor responde correctamente puedo hacer uso de la respuesta esperada:
                autos.clear();
                lista_autos.clear();

                for (Auto auto: response.body()){

                    // listo pantalla inicial
                    autos.add(auto.getId() + " - "+ auto.getMarca() + ": " + auto.getModelo());

                    // guardo toda la coleccion
                    lista_autos.add(auto);
                }

                // Aviso al base adapter que cambio mi set de datos.
                // Renderizacion general de mi ListView
                ((BaseAdapter) adaptador).notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Auto>> call, Throwable t) {
                // SI el servidor o la llamada no puede ejecutarse, muestro un mensaje de eror:
                Toast.makeText(MainActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);

            }
        });

    }



}
