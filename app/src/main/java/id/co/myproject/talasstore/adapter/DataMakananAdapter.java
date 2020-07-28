package id.co.myproject.talasstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.view.DetailMakananActivity;

public class DataMakananAdapter extends RecyclerView.Adapter<DataMakananAdapter.ViewHolder> {

    List<Makanan> makananList = new ArrayList<>();
    Context context;

    public DataMakananAdapter(Context context) {
        this.context = context;
    }

    public void setMakananList(List<Makanan> makananList){
        this.makananList.clear();
        this.makananList.addAll(makananList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataMakananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataMakananAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"makanan/"+makananList.get(position).getGambar()).into(holder.ivMenu);
        holder.tvMenu.setText(makananList.get(position).getNamaMakanan());
        holder.tvHarga.setText(makananList.get(position).getHargaSatuan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailMakananActivity.class);
                intent.putExtra("kode_makanan", makananList.get(position).getKodeMakanan());
                intent.putExtra("data_makanan", makananList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivMenu;
        TextView tvMenu, tvHarga;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            tvMenu = itemView.findViewById(R.id.tv_menu);
            tvHarga = itemView.findViewById(R.id.tv_harga);
        }
    }
}
