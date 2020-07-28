package id.co.myproject.talasstore.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.model.Pesanan;

import static id.co.myproject.talasstore.util.Helper.rupiahFormat;

public class RiwayatPembayaranAdapter extends RecyclerView.Adapter<RiwayatPembayaranAdapter.ViewHolder> {
    List<Pesanan> pesananList = new ArrayList<>();
    Context context;
    public RiwayatPembayaranAdapter(Context context) {
        this.context = context;
    }

    public void setPesananList(List<Pesanan> zakatHistoryList){
        this.pesananList.clear();
        this.pesananList.addAll(zakatHistoryList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RiwayatPembayaranAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pembayaran, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatPembayaranAdapter.ViewHolder holder, int position) {
        String tanggal = DateFormat.format("dd MMM yyyy", pesananList.get(position).getTanggal()).toString();
        String jam = DateFormat.format("HH:mm", pesananList.get(position).getTanggal()).toString();
        holder.tvJam.setText(jam);
        holder.tvTanggal.setText(tanggal);
        holder.tvJummlahPesanan.setText(pesananList.get(position).getJumlah_pesanan()+" pesanan");
        holder.tvNominal.setText("Bayar : "+rupiahFormat(Integer.parseInt(pesananList.get(position).getUangBayar())));
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTanggal, tvJummlahPesanan, tvNominal, tvJam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvJummlahPesanan = itemView.findViewById(R.id.tv_jumlah_pesanan);
            tvNominal = itemView.findViewById(R.id.tv_bayar);
            tvJam = itemView.findViewById(R.id.tv_jam);
        }
    }
}
