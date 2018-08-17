package com.fil.workerappz;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.widget.LinearLayout;

import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.CountryListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.LabelListJsonPojo;
import com.fil.workerappz.pojo.MessageListJsonPojo;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;
import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 23-Feb-18.
 * FIL AHM
 */

public class SplashActivity extends Activity {

    @BindView(R.id.splashmainlinearlayout)
    LinearLayout splashmainlinearlayout;
    private Handler mHandler;
    private Intent mIntent;
    private CountryData countryData;
    private final ArrayList<CountryListPojo> countryListPojos = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        final SessionManager sessionManager = new SessionManager(SplashActivity.this);
        Constants.device_token = FirebaseInstanceId.getInstance().getToken();
         Constants.language_id_label_msg=sessionManager.getlanguageselection();



        if (IsNetworkConnection.checkNetworkConnection(this)) {
            if (SugarRecord.count(CountryData.class) <= 0) {
                countryListJsonCall();

            }
        }

        getHashKey();

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.removeCallbacks(this);
//                if (sessionManager.getLogin() == false) {
//                    mIntent = new Intent(SplashActivity.this, SignUpActivity.class);
//                    startActivity(mIntent);
//                } else if (sessionManager.getVerify() == false) {
//                    mIntent = new Intent(SplashActivity.this, SignUpActivity.class);
//                    startActivity(mIntent);
//                } else {
//                    mIntent = new Intent(SplashActivity.this, HomeActivity.class);
//                    startActivity(mIntent);
//                }

////                language updated code
//                if (!sessionManager.getLogin()&&sessionManager.getRememberMe() == true) {
//
//                    mIntent = new Intent(SplashActivity.this, MainLanguageActivity.class);
////                    mIntent = new Intent(SplashActivity.this, SignUpActivity.class);
//                    startActivity(mIntent);
//                }
//                else {
                    if (sessionManager.getRememberMe() == true && sessionManager.getVerify() == true) {
                        mIntent = new Intent(SplashActivity.this, HomeActivity.class);
//                    mIntent = new Intent(SplashActivity.this, SignUpActivity.class);
                        startActivity(mIntent);
                    } else if (sessionManager.getRememberMe() == true && sessionManager.getVerify() == false) {
                        mIntent = new Intent(SplashActivity.this, SignInActivity.class);
//                    mIntent = new Intent(SplashActivity.this, SignUpActivity.class);
                        startActivity(mIntent);
                    } else {
                        mIntent = new Intent(SplashActivity.this, MainLanguageActivity.class);
                        startActivity(mIntent);
                    }
//                }
                finish();
            }
        }, 2000);
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.fil.workerappz", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                CustomLog.d("System out", "MY KEY HASH: " + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            CustomLog.e("System out", e.getMessage());
        }
    }

    private void countryListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";

        Call<List<CountryListPojo>> call = RestClient.get().countryListJsonCall(json);

        call.enqueue(new Callback<List<CountryListPojo>>() {
            @Override
            public void onResponse(Call<List<CountryListPojo>> call, Response<List<CountryListPojo>> response) {

                if (response.body() != null && response.body() instanceof ArrayList) {
                    countryListPojos.addAll(response.body());

                    if (countryListPojos.get(0).getStatus() == true) {
                        if (countryListPojos.get(0).getData().size() > 0) {
                            SugarRecord.saveInTx(countryListPojos.get(0).getData());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CountryListPojo>> call, Throwable t) {

            }
        });
    }




}
