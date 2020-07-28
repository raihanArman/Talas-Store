package id.co.myproject.talasstore.adapter;

import android.content.Context;
import android.os.Bundle;
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
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.Kategori;
import id.co.myproject.talasstore.view.home.KategoriMakananFragment;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {

    List<Kategori> kategoriList = new ArrayList<>();
    Context context;

    public KategoriAdapter(Context context) {
        this.context = context;
    }

    public void setKategoriList(List<Kategori> kategoriList){
        this.kategoriList.clear();
        this.kategoriList.addAll(kategoriList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"kategori/"+kategoriList.get(position).getGambarJenisMakanan()).into(holder.ivKategori);
        holder.tvKategori.setText(kategoriList.get(position).getNamaJenisMakanan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KategoriMakananFragment kategoriMakananFragment = new KategoriMakananFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id_kategori", kategoriList.get(position).getKodeJenisMakanan());
                bundle.putString("nama_kategori", kategoriList.get(position).getNamaJenisMakanan());
                kategoriMakananFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, kategoriMakananFragment)
                        .addToBackStack("")
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivKategori;
        TextView tvKategori;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivKategori = itemView.findViewById(R.id.iv_kategori);
            tvKategori = itemView.findViewById(R.id.tv_kategori);
        }
    }
}
