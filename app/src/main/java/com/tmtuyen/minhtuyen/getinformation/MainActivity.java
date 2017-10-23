package com.tmtuyen.minhtuyen.getinformation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkConnection();

    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {

        if (isConnected) {
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyData", 0);
            int userID = pref.getInt("userID", 0);
            if (userID == 0)
                startActivity(new Intent(this, LoginActivity.class));
            else {
                finish();
                startActivity(new Intent(this, Main2Activity.class));
            }
        } else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.linearLayout), "Không có kết nối Internet!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Thử lại!", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkConnection();
                        }
                    });
            View view = snackbar.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snackbar.show();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
