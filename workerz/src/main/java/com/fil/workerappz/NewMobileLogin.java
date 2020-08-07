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

public class NewMobileLogin extends Activity {

    EditText mobileed;

    CountryCodePicker ccp;

    TextView continues;



    String sccp,smobileno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter_mobile_login);
        mobileed=findViewById(R.id.mobileed);
        ccp=findViewById(R.id.ccp);
        continues=findViewById(R.id.continues);

        sccp=ccp.getSelectedCountryCode().toString();
        smobileno=mobileed.getText().toString();



        continues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getApplicationContext(),NewLoginOTP.class);
                intent.putExtra(smobileno,"mobileno");
                intent.putExtra(sccp,"ccp");
                startActivity(intent);

            }
        });





    }
}
