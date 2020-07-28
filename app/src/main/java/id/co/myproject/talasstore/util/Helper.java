package id.co.myproject.talasstore.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Helper {
    public static final String DELETE = "Delete";
    public static final String KEY_LOGIN_STATUS = "login_status";
    public static final String KEY_KODE_MEMBER = "kode_member";
    public static final String KEY_LOGIN_SHARED_PREF = "data_member";

    public static String rupiahFormat(int harga){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }
}
