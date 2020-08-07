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

public class NewLoginPass extends Activity {

    PinEntryView newpin;



    TextView confirmnewpin;


    TextView skipnewpin;

    String sccp,smobileno;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newpin_login);
        newpin=findViewById(R.id.newpin);
        confirmnewpin=findViewById(R.id.confirmnewpin);
        skipnewpin=findViewById(R.id.skipnewpin);

        sccp=newpin.getText().toString();



        newpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        confirmnewpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        skipnewpin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });






    }
}
