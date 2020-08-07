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

public class NewSignupCreateProfile extends Activity {

    EditText fnamesignup,mnamesignup,lnamesignup,emailsignup;
    String  sfnamesignup,smnamesignup,slnamesignup,semailsignup,sotp,sbankAiqmano,sbankAdob,sccp,smobileno;
    RadioGroup radiomf;
    Button nextcreate1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.complete_profile_nonb);

        sbankAiqmano =getIntent().getStringExtra("sbankAiqmano");
        sbankAdob =getIntent().getStringExtra("sbankAdob");
        sccp =getIntent().getStringExtra("sccp");
        smobileno =getIntent().getStringExtra("smobileno");
        sotp =getIntent().getStringExtra("sotp");


        fnamesignup=findViewById(R.id.fnamesignup);
        mnamesignup=findViewById(R.id.mnamesignup);
        lnamesignup=findViewById(R.id.lnamesignup);
        emailsignup=findViewById(R.id.emailsignup);
        nextcreate1=findViewById(R.id.nextcreate1);
        radiomf=findViewById(R.id.radiomf);
        nextcreate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfnamesignup=fnamesignup.getText().toString();
                smnamesignup=mnamesignup.getText().toString();
                slnamesignup=lnamesignup.getText().toString();
                semailsignup=emailsignup.getText().toString();
                Intent intent=new Intent(getApplicationContext(),NewSignupCreateProfile2.class);
                intent.putExtra(sbankAdob,"sbankAdob");
                intent.putExtra(smobileno,"smobileno");
                intent.putExtra(sccp,"sccp");
                intent.putExtra(sotp,"sotp");
                intent.putExtra(sbankAiqmano,"sbankAiqmano");


                intent.putExtra(sfnamesignup,"sfnamesignup");
                intent.putExtra(smnamesignup,"smnamesignup");
                intent.putExtra(slnamesignup,"slnamesignup");
                intent.putExtra(semailsignup,"semailsignup");
                startActivity(intent);

            }
        });





    }
}
