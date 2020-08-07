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

public class NewSignupMobile extends Activity {

    EditText mobilesignupb;

    CountryCodePicker ccpsignupb;

    Button continuesighupb;


    String sbankAiqmano,sbankAdob;
    String sccp,smobileno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter_mobile_banking);

        sbankAiqmano =getIntent().getStringExtra("sbankAiqmano");
        sbankAdob =getIntent().getStringExtra("sbankAdob");

        mobilesignupb=findViewById(R.id.mobilesignupb);
        ccpsignupb=findViewById(R.id.ccpsignupb);
        continuesighupb=findViewById(R.id.continuesighupb);

        sccp=ccpsignupb.getSelectedCountryCode().toString();

        smobileno=mobilesignupb.getText().toString();



        continuesighupb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewSignupOTP.class);
                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(smobileno,"smobileno");
                intent.putExtra(sccp,"sccp");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");
                startActivity(intent);

            }
        });





    }
}
