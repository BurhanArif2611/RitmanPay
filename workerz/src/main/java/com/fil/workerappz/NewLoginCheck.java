package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class NewLoginCheck extends Activity {

    Button buttoncreate;

    Button bottonalready;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.new_signup_login);

        buttoncreate=findViewById(R.id.buttonCreate);
        bottonalready=findViewById(R.id.already);

        buttoncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(), NewSignupCreateAcc.class);
                startActivity(intent);

            }
        });
        bottonalready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(getApplicationContext(),NewLoginOption.class);
            startActivity(intent);
            }
        });

    }
}
