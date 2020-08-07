package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;

public class NewLoginOption extends Activity {

    Button ufinger,umobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.option_login);

        umobile=findViewById(R.id.umobile);
        ufinger=findViewById(R.id.ufinger);

        umobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewMobileLogin.class);
                startActivity(intent);

            }
        });
        ufinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewLoginFScan.class);
                startActivity(intent);
            }
        });



    }
}
