package id.co.myproject.talasstore.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.ListPesanan;
import id.co.myproject.talasstore.model.Pesanan;
import id.co.myproject.talasstore.request.ApiRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<Pesanan> requestList = new ArrayList<>();
    Context context;
    ApiRequest apiRequest;

    public OrderAdapter(Context context, ApiRequest apiRequest) {
        this.context = context;
        this.apiRequest = apiRequest;
    }

    public void setRequestList(List<Pesanan> requestList){
        this.requestList.clear();
        this.requestList.addAll(requestList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.tvTanggal.setText(DateFormat.format("dd MMM yyyy HH:mm", requestList.get(position).getTanggal()));
        holder.tvTotal.setText("Total "+requestList.get(position).getTotalHarga());
        holder.tvStatus.setText("Status : "+requestList.get(position).getStatus());
        holder.tvJumlahPesanan.setText("Jumlah pesanan : "+requestList.get(position).getJumlah_pesanan());

        PesananAdapter pesananAdapter = new PesananAdapter(context);
        holder.rv_pesanan.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_pesanan.setAdapter(pesananAdapter);

        Call<List<ListPesanan>> pesananCall = apiRequest.getListPesananRequest(requestList.get(position).getIdTransaksi());
        pesananCall.enqueue(new Callback<List<ListPesanan>>() {
            @Override
            public void onResponse(Call<List<ListPesanan>> call, Response<List<ListPesanan>> response) {
                if (response.isSuccessful()) {
                    List<ListPesanan> pesananList = response.body();
                    pesananAdapter.setPesananList(pesananList);
                }
            }

            @Override
            public void onFailure(Call<List<ListPesanan>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//
//        holder.ivArrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                DetailRequestFragment detailRequestFragment = new DetailRequestFragment();
//                Bundle bundle = new Bundle();
//                bundle.putString("id_request", requestList.get(position).getIdRequest());
//                detailRequestFragment.setArguments(bundle);
//                ((MainActivity)view.getContext()).getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.fm_home, detailRequestFragment)
//                        .commit();
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvJumlahPesanan, tvTanggal, tvTotal, tvStatus;
        ImageView ivArrow;
        RecyclerView rv_pesanan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_pesanan = itemView.findViewById(R.id.rv_pesanan);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvTotal = itemView.findViewById(R.id.tv_total);
            tvStatus = itemView.findViewById(R.id.tv_status);
            ivArrow = itemView.findViewById(R.id.iv_arrow);
            tvJumlahPesanan = itemView.findViewById(R.id.tv_jumlah_pesanan);
        }
    }
}
