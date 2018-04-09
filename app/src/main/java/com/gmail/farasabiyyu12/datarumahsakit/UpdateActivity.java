package com.gmail.farasabiyyu12.datarumahsakit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit.ApiService;
import com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit.InstanceRetrofit;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseUpdateData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {

    @BindView(R.id.inpnama)
    EditText inpnama;
    @BindView(R.id.inpalamat)
    EditText inpalamat;
    @BindView(R.id.btnInput2)
    Button btnInput2;

    String stralamat, strnama, strid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        inpnama.setText(getIntent().getStringExtra("nama"));
        inpalamat.setText(getIntent().getStringExtra("alamat"));

//        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @OnClick(R.id.btnInput2)
    public void onViewClicked() {
        strid = getIntent().getStringExtra("id");
        strnama = inpnama.getText().toString();
        stralamat = inpalamat.getText().toString();

        final ProgressDialog progress = ProgressDialog.show(this, "Sedang Mengupdate Data"
                , "Mohon Tunggu Sebentar");

        ApiService api = InstanceRetrofit.getInstance();
        Call<ResponseUpdateData> call = api.response_update_data(strid, strnama, stralamat);
        call.enqueue(new Callback<ResponseUpdateData>() {
            @Override
            public void onResponse(Call<ResponseUpdateData> call, Response<ResponseUpdateData> response) {
                Toast.makeText(UpdateActivity.this, ""+response.body().getPesan(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
                progresilang();
            }

            private void progresilang() {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                progress.setCancelable(true);
            }

            @Override
            public void onFailure(Call<ResponseUpdateData> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                progress.dismiss();
                progresilang();
            }
        });
    }
}
