package com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit;

import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseCreateData;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseDeleteData;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseReadData;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseUpdateData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 *
 * Created by farasabiyyuhandoko on 06/04/2018.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("create_data.php")
    Call<ResponseCreateData> response_create_data(
        @Field("nama") String nama,
        @Field("alamat") String alamat
    );

    @GET("read_data.php")
    Call<ResponseReadData> response_read_data();

    @FormUrlEncoded
    @POST("update_data.php")
    Call<ResponseUpdateData> response_update_data(
            @Field("vrid") String id,
            @Field("vrnama") String nama,
            @Field("vralamat") String alamat
    );

    @FormUrlEncoded
    @POST("delete_Data.php")
    Call<ResponseDeleteData> response_delete_data(
            @Field("vrid") String id
    );
}
