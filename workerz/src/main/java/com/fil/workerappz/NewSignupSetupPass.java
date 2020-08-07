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

public class NewSignupSetupPass extends Activity {

    Button fingersignup,createpinsignup;
    TextView skipsignup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.setup_pass_nonb);

        fingersignup=findViewById(R.id.fingersignup);
        createpinsignup=findViewById(R.id.createpinsignup);
        skipsignup=findViewById(R.id.skipsignup);
        fingersignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        createpinsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),NewSignupCreatepin.class);
                    startActivity(intent);
            }
        });
        skipsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
