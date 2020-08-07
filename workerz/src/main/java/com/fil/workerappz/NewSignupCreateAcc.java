package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;

public class NewSignupCreateAcc extends Activity {

    EditText bankAiqmano,bankAdob;
    String sbankAiqmano,sbankAdob;
    Button bankAnext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_account_banking);

        bankAiqmano=findViewById(R.id.bankAiqmano);
        bankAdob=findViewById(R.id.bankAdob);
        bankAnext=findViewById(R.id.bankAnext);
        bankAnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sbankAiqmano=bankAiqmano.getText().toString();
                sbankAdob=bankAdob.getText().toString();
                Intent intent=new Intent(getApplicationContext(),NewSignupVerifing.class);
                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");
                startActivity(intent);

            }
        });





    }
}
