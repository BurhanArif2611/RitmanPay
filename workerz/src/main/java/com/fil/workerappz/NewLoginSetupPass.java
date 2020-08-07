package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class NewLoginSetupPass extends Activity {

    Button sfinger;

    Button setpin;

    TextView skipn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setup_pass_login);
        sfinger=findViewById(R.id.sfinger);
        setpin=findViewById(R.id.setpin);
        skipn=findViewById(R.id.skipn);

        sfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewLoginPass.class);
                startActivity(intent);

            }
        });
        setpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewLoginPass.class);
                startActivity(intent);
            }
        });
        skipn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewLoginPass.class);
                startActivity(intent);
            }
        });



    }
}
