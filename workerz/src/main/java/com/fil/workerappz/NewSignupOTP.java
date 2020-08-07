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

public class NewSignupOTP extends Activity {
    PinEntryView otp_signup;
    TextView resendsignup;
    Button confirmsignup;
    String sotp,sbankAiqmano,sbankAdob,sccp,smobileno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.enter_otp_banking);

        otp_signup=findViewById(R.id.otp_signup);
        resendsignup=findViewById(R.id.resendsignup);
        confirmsignup=findViewById(R.id.confirmsignupotp);
        sbankAiqmano =getIntent().getStringExtra("sbankAiqmano");
        sbankAdob =getIntent().getStringExtra("sbankAdob");
        sccp =getIntent().getStringExtra("sccp");
        smobileno =getIntent().getStringExtra("smobileno");

        sotp=otp_signup.getText().toString();



        resendsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        confirmsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent=new Intent(getApplicationContext(),NewSignupCreateProfile.class);
                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(smobileno,"smobileno");
                intent.putExtra(sccp,"sccp");
                intent.putExtra(sotp,"sotp");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");
            startActivity(intent);
            }
        });


    }
}
