package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;
import me.philio.pinentry.PinEntryView;

public class NewLoginOTP extends Activity {

    PinEntryView otplogin;



    TextView resend;


    Button confirmotplogin;

    String sccp,pass,smobileno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.enter_otp_login);

        smobileno= getIntent().getStringExtra("mobileno");
        sccp= getIntent().getStringExtra("ccp");

        otplogin=findViewById(R.id.otplogin);
        resend=findViewById(R.id.resend);
        confirmotplogin=findViewById(R.id.confirmotplogin);

        pass=otplogin.getText().toString();



        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        confirmotplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),SignInActivity.class);
                intent.putExtra(pass,"pass");
                intent.putExtra(sccp,"ccp");
                intent.putExtra(smobileno,"mobileno");
                startActivity(intent);

            }
        });


    }
}
