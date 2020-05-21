package com.example.api_rest_call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.POST;


public interface AutoService {
    // Ruta pare el READ

    // Definicion de ruta para GET
    String API_ROUTE = "app/api/read";

    // Metodo abstracto para utilizar HTTP.GET
    // @return
    @GET(API_ROUTE)
    Call<List<Auto>> getAutos();


    // Ruta pare el READ{id}

    String API_GET_ID = "app/api/read{id}";

    @GET(API_GET_ID)
    Call<Auto> getAuto(@Path("id") String id);


    // Ruta pare el DELETE{id}

    String API_DELETE = "app/api/delete{id}";

    @DELETE(API_DELETE)
    Call<Void> Delete(@Path("id") String id);


    // Ruta pare el UPDATE{id}

    String API_UPDATE = "app/api/update{id}";

    @PUT(API_UPDATE)
    Call<Auto> Update(@Path("id") String id, @Body Auto auto);


    // Ruta pare el CREATE

    String API_POST= "app/api/create";

    @POST(API_POST)
    Call<Void> Post(@Body Auto auto);


}
