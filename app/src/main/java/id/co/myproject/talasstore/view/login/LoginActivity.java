package id.co.myproject.talasstore.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.talasstore.MainActivity;
import id.co.myproject.talasstore.R;
import id.co.myproject.talasstore.request.ApiRequest;
import id.co.myproject.talasstore.request.RetrofitRequest;
import id.co.myproject.talasstore.util.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import static id.co.myproject.talasstore.util.Helper.KEY_LOGIN_STATUS;


public class LoginActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    //    public static boolean onResetPasswordFragment = false;
    public static boolean setSignUpFragment = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ApiRequest apiRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        frameLayout = findViewById(R.id.frame_login);
    }


    private void updateUI(final boolean isSignedIn) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        boolean loginStatus = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isSignedIn) {
                    if (loginStatus) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }


    @Override
    protected void onStart() {
        super.onStart();
        boolean loginStatus = sharedPreferences.getBoolean(KEY_LOGIN_STATUS, false);
        if (loginStatus){
            updateUI(true);
        }else {
            setFragment(new SignInFragment());
//            if (setSignUpFragment) {
//                setSignUpFragment = false;
//                setFragment(new SignUpFragment());
//            } else {
//                setFragment(new SignInFragment());
//            }
        }
    }
}
