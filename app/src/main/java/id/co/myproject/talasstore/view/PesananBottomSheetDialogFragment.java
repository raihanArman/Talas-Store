package id.co.myproject.talasstore.view;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import id.co.myproject.talasstore.BuildConfig;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.database.OrderHelper;
import id.co.myproject.talasstore.model.Makanan;

import static id.co.myproject.talasstore.util.Helper.rupiahFormat;

public class PesananBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private ImageView iv_makanan;
    private TextView tv_makanan, tv_harga;
    private Button btn_pesan;
    OrderHelper orderHelper;
    ElegantNumberButton btn_quantity;
//    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
//
//        @Override
//        public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            setStateText(newState);
//            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss();
//            }
//
//        }
//
//        @Override
//        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            setOffsetText(slideOffset);
//        }
//    };
    private LinearLayoutManager mLinearLayoutManager;
    private Makanan makanan;
    private String idUser;
    public PesananBottomSheetDialogFragment(Makanan makanan, String idUser) {
        this.makanan = makanan;
        this.idUser = idUser;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View contentView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(contentView, savedInstanceState);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.bottom_sheet, null);
        dialog.setContentView(contentView);

        orderHelper = OrderHelper.getINSTANCE(contentView.getContext());
        orderHelper.open();

        iv_makanan = contentView.findViewById(R.id.iv_makanan);
        tv_makanan = contentView.findViewById(R.id.tv_makanan);
        tv_harga = contentView.findViewById(R.id.tv_harga);
        btn_pesan = contentView.findViewById(R.id.btn_pesan);
        btn_quantity = contentView.findViewById(R.id.btn_quantity);
        tv_makanan.setText(makanan.getNamaMakanan());
        tv_harga.setText(rupiahFormat(Integer.valueOf(makanan.getHargaSatuan())));
        Glide.with(contentView.getContext()).load(BuildConfig.BASE_URL_GAMBAR + "makanan/" + makanan.getGambar()).into(iv_makanan);

        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long result = orderHelper.addToCart(idUser, makanan, btn_quantity.getNumber());
                if (result > 0){
                    Toast.makeText(getActivity(), "Berhasil menyimpan", Toast.LENGTH_SHORT).show();
                    HomeFragment.fb_pesan.setCount(orderHelper.getCountCart(idUser));
                    dismiss();
                }else {
                    Toast.makeText(getActivity(), "Gagal menyimpan", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        orderHelper.close();
    }
}