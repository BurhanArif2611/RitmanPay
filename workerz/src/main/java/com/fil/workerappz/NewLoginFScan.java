package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rilixtech.CountryCodePicker;

import butterknife.BindView;
import butterknife.OnClick;
import me.philio.pinentry.PinEntryView;

public class NewLoginFScan extends Activity {

    ImageView scanfinger;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.scan_finger_login);

            scanfinger=findViewById(R.id.scanfinger);

        scanfinger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NewLoginOption.class);
                startActivity(intent);

            }
        });







    }
}
