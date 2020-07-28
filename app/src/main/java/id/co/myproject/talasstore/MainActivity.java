package id.co.myproject.talasstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.talasstore.model.Value;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;
import id.co.myproject.talasstore.view.HomeFragment;
import id.co.myproject.talasstore.view.OrderFragment;
import id.co.myproject.talasstore.view.profil.ProfilFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    public static BottomNavigationView bottomNavigationView;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        frameLayout = findViewById(R.id.frame_home);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        sharedPreferences = getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        String kodemember = sharedPreferences.getString(Helper.KEY_KODE_MEMBER, "");
        boolean loginStatus = sharedPreferences.getBoolean(Helper.KEY_LOGIN_STATUS, false);

        Call<Value> getJumlahPesanan = apiRequest.getJumlahPesananRequest(kodemember);
        getJumlahPesanan.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    bottomNavigationView.getOrCreateBadge(R.id.data_order_nav).setNumber(response.body().getJumlahPesanan());
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        if (loginStatus){
            bottomNavigationView.setVisibility(View.VISIBLE);
        }else {
            bottomNavigationView.setVisibility(View.GONE);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_nav){
                    setFragment(new HomeFragment());
                }else if(item.getItemId() == R.id.data_order_nav){
                    setFragment(new OrderFragment());
                }else if (item.getItemId() == R.id.data_profil){
                    setFragment(new ProfilFragment());
                }

                return true;
            }
        });

        setFragment(new HomeFragment());

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
