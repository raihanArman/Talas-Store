package id.co.myproject.talasstore.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.database.OrderHelper;
import id.co.myproject.talasstore.model.Makanan;
import id.co.myproject.talasstore.util.Helper;

import static id.co.myproject.talasstore.util.Helper.rupiahFormat;
import static id.co.myproject.talasstore.view.PesananFragment.tv_total;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    List<Makanan> makananList = new ArrayList<>();
    Context context;
    OrderHelper orderHelper;
    String idUser;

    public CartAdapter(Context context, OrderHelper orderHelper, String idUser) {
        this.context = context;
        this.orderHelper = orderHelper;
        this.idUser = idUser;
    }

    public void setMenuList(List<Makanan> makananList){
        if (makananList.size() > 0){
            this.makananList.clear();
        }
        this.makananList.addAll(makananList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        if (makananList.size() > 0){
            holder.tv_menu.setText(makananList.get(position).getNamaMakanan());
            holder.tv_harga.setText(rupiahFormat(Integer.valueOf(makananList.get(position).getHargaSatuan())));
            Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR + "makanan/" + makananList.get(position).getGambar()).into(holder.iv_menu);
            holder.btn_quantity.setNumber(makananList.get(position).getQuantity());
            holder.btn_quantity.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    Makanan makanan = makananList.get(position);
                    makanan.setQuantity(String.valueOf(newValue));
                    orderHelper.updateCart(makanan, idUser);
                    int total = 0;
                    List<Makanan> makananList = orderHelper.getAllCart(idUser);
                    for (Makanan item : makananList){
                        total+=(Integer.parseInt(item.getHargaSatuan()))*(Integer.parseInt(item.getQuantity()));
                    }

                    tv_total.setText(rupiahFormat(total));

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return makananList.size();
    }

    public Makanan getItem(int position){
        return makananList.get(position);
    }

    public void removeItem(int position){
        makananList.remove(position);
    }

    public void restoreItem(Makanan item, int position){
        makananList.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public TextView tv_menu, tv_harga;
        public ElegantNumberButton btn_quantity;
        public ImageView iv_menu;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_menu = (ImageView) itemView.findViewById(R.id.iv_menu);
            tv_menu = (TextView) itemView.findViewById(R.id.tv_menu);
            tv_harga = (TextView) itemView.findViewById(R.id.tv_harga);
            btn_quantity = (ElegantNumberButton) itemView.findViewById(R.id.btn_quantity);
            view_background = (RelativeLayout) itemView.findViewById(R.id.view_background);
            view_foreground = (LinearLayout) itemView.findViewById(R.id.view_foreground);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select your option");
            contextMenu.add(0,0,getAdapterPosition(), Helper.DELETE);
        }
    }
}
