package id.co.myproject.talasstore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.ListPesanan;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {

    List<ListPesanan> pesananList = new ArrayList<>();
    Context context;

    public PesananAdapter(Context context) {
        this.context = context;
    }

    public void setPesananList(List<ListPesanan> pesananList){
        this.pesananList.clear();
        this.pesananList.addAll(pesananList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PesananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_pesanan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananAdapter.ViewHolder holder, int position) {
        holder.tvBarang.setText(pesananList.get(position).getNamaMakanan());
        holder.tvSubTotal.setText(pesananList.get(position).getSubTotal());
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvBarang, tvSubTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBarang = itemView.findViewById(R.id.tv_barang);
            tvSubTotal = itemView.findViewById(R.id.tv_sub_total);
        }
    }
}
