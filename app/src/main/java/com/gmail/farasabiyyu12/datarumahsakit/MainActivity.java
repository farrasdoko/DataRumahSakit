package com.gmail.farasabiyyu12.datarumahsakit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit.ApiService;
import com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit.InstanceRetrofit;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.DataItem;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseCreateData;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseReadData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //TODO Recyclerview
    RecyclerView recyclerView;

    //TODO popUp
    Dialog popUp;
    EditText inputnama, inputalamat;
    Button btnmasuk;
    //TODO Swipe Refresh
    SwipeRefreshLayout mSwipeRefreshLayout;

    //TODO install stringdoank
    String strnama, stralamat;

    List<DataItem> dataItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getData();
        //TODO bikin data baru

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp = new Dialog(MainActivity.this);
                popUp.setContentView(R.layout.inputdata);
                popUp.show();

                inputnama = popUp.findViewById(R.id.inputnama);
                inputalamat = popUp.findViewById(R.id.inputalamat);

                btnmasuk = popUp.findViewById(R.id.btnInput);
                btnmasuk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        strnama = inputnama.getText().toString();
                        stralamat = inputalamat.getText().toString();

                        if (TextUtils.isEmpty(strnama)){
                            inputnama.setError("Masukkan Dulu");
                        }else if(TextUtils.isEmpty(stralamat)){
                            inputalamat.setError("Masukkan Dulu");
                        }else{
                            masukkanData();
                            popUp.dismiss();
                        }
                    }
                });
            }
        });

        //TODO Swiperefresh Layout
        mSwipeRefreshLayout = findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

            }
        });

    }

    private void getData() {
        ApiService apiService = InstanceRetrofit.getInstance();
        Call<ResponseReadData> readDataCall = apiService.response_read_data();

        readDataCall.enqueue(new Callback<ResponseReadData>() {
            @Override
            public void onResponse(Call<ResponseReadData> call, Response<ResponseReadData> response) {
                Boolean status = response.body().isSuccess();
                if (status) {
                    dataItems = response.body().getData();
                    CustomAdapter adapter = new CustomAdapter(MainActivity.this, dataItems);
                    recyclerView.setAdapter(adapter);
                    mSwipeRefreshLayout.setRefreshing(false);
                }else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseReadData> call, Throwable t) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void masukkanData() {
        final ProgressDialog dialog = ProgressDialog.show(this, "Tunggu Sebentar"
                , "Memasukkan Data");
        ApiService apiService = InstanceRetrofit.getInstance();
        Call<ResponseCreateData> createDataCall = apiService.response_create_data(strnama, stralamat);

        createDataCall.enqueue(new Callback<ResponseCreateData>() {
            @Override
            public void onResponse(Call<ResponseCreateData> call, Response<ResponseCreateData> response) {
                if(response.body().isSuccess()){
                    Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseCreateData> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
