package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.OnClick;

public class NewSignupCreateProfile2 extends Activity {

    EditText countrysignup,citysignup,lnamesignup,nationalitysignup;
    String  scountrysignup,scitysignup,slnamesignup2,snationalitysignup,sfnamesignup,smnamesignup,slnamesignup,semailsignup,sotp,sbankAiqmano,sbankAdob,sccp,smobileno;
    Button createnext2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.complete_profile_nonb2);


        sbankAiqmano =getIntent().getStringExtra("sbankAiqmano");
        sbankAdob =getIntent().getStringExtra("sbankAdob");
        sccp =getIntent().getStringExtra("sccp");
        smobileno =getIntent().getStringExtra("smobileno");
        sotp =getIntent().getStringExtra("sotp");
        sfnamesignup =getIntent().getStringExtra("sfnamesignup");
        smnamesignup =getIntent().getStringExtra("smnamesignup");
        slnamesignup =getIntent().getStringExtra("slnamesignup");
        semailsignup =getIntent().getStringExtra("semailsignup");


        countrysignup=findViewById(R.id.countrysignup);
        citysignup=findViewById(R.id.citysignup);
        lnamesignup=findViewById(R.id.addresssignup);
        nationalitysignup=findViewById(R.id.nationalitysignup);
        createnext2=findViewById(R.id.createnext2);
        createnext2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scountrysignup=countrysignup.getText().toString();
                scitysignup=citysignup.getText().toString();
                slnamesignup2=lnamesignup.getText().toString();
                snationalitysignup=nationalitysignup.getText().toString();
                Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);

                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(smobileno,"smobileno");
                intent.putExtra(sccp,"sccp");
                intent.putExtra(sotp,"sotp");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");


                intent.putExtra(sfnamesignup,"sfnamesignup");
                intent.putExtra(smnamesignup,"smnamesignup");
                intent.putExtra(slnamesignup,"slnamesignup");
                intent.putExtra(semailsignup,"semailsignup");

                intent.putExtra(sfnamesignup,"scountrysignup");
                intent.putExtra(smnamesignup,"scitysignup");
                intent.putExtra(slnamesignup,"slnamesignup2");
                intent.putExtra(semailsignup,"snationalitysignup");

                startActivity(intent);

            }
        });





    }
}
