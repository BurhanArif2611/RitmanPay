package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;

public class NewSignupVerified extends Activity {

    private Handler mHandler;
    String sbankAiqmano,sbankAdob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.verified_banking);
        sbankAiqmano =getIntent().getStringExtra("sbankAiqmano");
        sbankAdob =getIntent().getStringExtra("sbankAdob");
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacks(this);
                Intent intent=new Intent(getApplicationContext(), NewSignupMobile.class);
                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");
                startActivity(intent);
                finish();
            }
        }, 2000);





    }
}
