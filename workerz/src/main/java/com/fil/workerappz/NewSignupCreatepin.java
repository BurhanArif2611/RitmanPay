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

public class NewSignupCreatepin extends Activity {

    PinEntryView signuppin;



    Button signuppinconfirm;


    TextView skipsignuppin;

    String sccp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpin_banking);


        signuppin=findViewById(R.id.signuppin);
        signuppinconfirm=findViewById(R.id.signuppinconfirm);
        skipsignuppin=findViewById(R.id.skipsignuppin);

        sccp=signuppin.getText().toString();



        signuppinconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        skipsignuppin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });






    }
}
