package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

public class NewSignupVerifing extends Activity {

    private Handler mHandler;
    String sbankAiqmano,sbankAdob;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.verifing_banking);

        sbankAiqmano =getIntent().getStringExtra("sbankAiqmano");
        sbankAdob =getIntent().getStringExtra("sbankAdob");
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacks(this);
                Intent intent=new Intent(getApplicationContext(), NewSignupVerified.class);
                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");
                startActivity(intent);
                finish();
            }
        }, 2000);





    }
}
