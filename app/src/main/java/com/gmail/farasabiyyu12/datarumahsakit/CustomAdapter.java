package com.gmail.farasabiyyu12.datarumahsakit;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit.ApiService;
import com.gmail.farasabiyyu12.datarumahsakit.ApiRetrofit.InstanceRetrofit;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.DataItem;
import com.gmail.farasabiyyu12.datarumahsakit.ResponseServer.ResponseDeleteData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by farasabiyyuhandoko on 06/04/2018.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    Context context;
    List<DataItem> data;
    Dialog popUp;
    Button btnUpdate, btnDelete;

    public CustomAdapter(Context context, List<DataItem> dataItems) {
        this.context = context;
        this.data = dataItems;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item , parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.ViewHolder holder, final int position) {

        final String strid = data.get(position).getId();

    holder.id.setText(strid);
    holder.nama.setText(data.get(position).getNama());
    holder.alamat.setText(data.get(position).getAlamat());
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            popUp = new Dialog(context);
            popUp.setContentView(R.layout.update_delete);
            popUp.show();

            btnDelete = popUp.findViewById(R.id.btndelete);
            btnUpdate = popUp.findViewById(R.id.btnupdate);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                context.startActivity(new Intent(context, UpdateActivity.class)
                .putExtra("id", strid)
                .putExtra("nama", data.get(position).getNama())
                .putExtra("alamat", data.get(position).getAlamat()));
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog progressDialog = ProgressDialog.show(context,
                            "Menghapus Data", "Mohon Tunggu");
                    ApiService apiService = InstanceRetrofit.getInstance();
                    Call<ResponseDeleteData> call = apiService.response_delete_data(strid);
                    call.enqueue(new Callback<ResponseDeleteData>() {
                        @Override
                        public void onResponse(Call<ResponseDeleteData> call, Response<ResponseDeleteData> response) {
                            Toast.makeText(context, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            popUp.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseDeleteData> call, Throwable t) {
                            Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                            popUp.dismiss();
                        }
                    });
                }
            });
            return true;
        }
    });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, nama, alamat;
        public ViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id);
            nama = itemView.findViewById(R.id.nama);
            alamat = itemView.findViewById(R.id.alamat);
        }
    }
}
