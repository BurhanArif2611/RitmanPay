package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class ChangePinActivity extends ActionBarActivity {

    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.mainChangePinLinearLayout)
    LinearLayout mainChangePinLinearLayout;
    @BindView(R.id.oldPinEditText)
    MaterialEditText oldPinEditText;
    @BindView(R.id.newPinEditText)
    MaterialEditText newPinEditText;
    @BindView(R.id.repeatPinEditText)
    MaterialEditText repeatPinEditText;
    @BindView(R.id.savePinTextView)
    TextView savePinTextView;
    @BindView(R.id.appImageViewHeader1)
    ImageView appImageViewHeader1;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String oldpinmsg, newpinmsg, repeatpinmsg, pimsixdigits, pinnotmatch, nointernetmsg;
    private Intent mIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.change_pin);
        ButterKnife.bind(this);


        try {
            sessionManager = new SessionManager(ChangePinActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader.setText(datumLable_languages.getChangePIN());
                oldPinEditText.setHint(datumLable_languages.getOldWalletPIN());
                oldPinEditText.setFloatingLabelText(datumLable_languages.getOldWalletPIN()+"*");
                newPinEditText.setHint(datumLable_languages.getNewWalletPIN()+"*");
                newPinEditText.setFloatingLabelText(datumLable_languages.getNewWalletPIN()+"*");
                repeatPinEditText.setHint(datumLable_languages.getRepeatNewWalletPIN()+"*");
                repeatPinEditText.setFloatingLabelText(datumLable_languages.getRepeatNewWalletPIN()+"*");
                savePinTextView.setText(datumLable_languages.getSave());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();

            } else {

                titleTextViewViewHeader.setText(getResources().getString(R.string.change_pin));
                oldPinEditText.setHint(getResources().getString(R.string.old_wallet_pin));
                newPinEditText.setHint(getResources().getString(R.string.new_wallet_pin));
                repeatPinEditText.setHint(getResources().getString(R.string.repeat_new_wallet_pin));
                savePinTextView.setText(getResources().getString(R.string.save));
                nointernetmsg = getResources().getString(R.string.no_internet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            oldpinmsg = datumLable_languages_msg.getEnterOldPIN();
            newpinmsg = datumLable_languages_msg.getEnterNewPIN();
            repeatpinmsg = datumLable_languages_msg.getEnterRepeatPIN();
            pimsixdigits = datumLable_languages_msg.getINVALIDPINSIZE();
            pinnotmatch = datumLable_languages_msg.getPINDoesNotMatch();

        } else {

            oldpinmsg = getResources().getString(R.string.Please_Enter_old_pin);
            newpinmsg = getResources().getString(R.string.Please_Enter_new_pin);
            repeatpinmsg = getResources().getString(R.string.Please_Enter_repeat_pin);
            pimsixdigits = getResources().getString(R.string.Pin_six_digits);
            pinnotmatch = getResources().getString(R.string.Pin_does_not_match);

        }
        oldPinEditText.setHint("Old PIN"+"*");
        oldPinEditText.setFloatingLabelText("Old PIN"+"*");
        newPinEditText.setHint("New PIN"+"*");
        newPinEditText.setFloatingLabelText("New PIN"+"*");
        repeatPinEditText.setHint("Repeat New PIN"+"*");
        repeatPinEditText.setFloatingLabelText("Repeat New PIN"+"*");
        appImageViewHeader1.setVisibility(View.VISIBLE);

    }

    private boolean checkValidation() {
        boolean checkFlag = true;
        if (oldPinEditText.getText().toString().length() == 0) {
            Constants.showMessage(mainChangePinLinearLayout, this, oldpinmsg);
            checkFlag = false;
        } else if (newPinEditText.getText().toString().length() == 0) {
            Constants.showMessage(mainChangePinLinearLayout, this, newpinmsg);
            checkFlag = false;
        } else if (repeatPinEditText.getText().toString().length() == 0) {
            Constants.showMessage(mainChangePinLinearLayout, this, repeatpinmsg);
            checkFlag = false;
        } else if (oldPinEditText.getText().toString().length() != 6 || newPinEditText.getText().toString().length() != 6 || repeatPinEditText.getText().toString().length() != 6) {
            Constants.showMessage(mainChangePinLinearLayout, this, pimsixdigits);
            checkFlag = false;
        } else if (newPinEditText.getText().toString().equals(repeatPinEditText.getText().toString()) == false) {
            Constants.showMessage(mainChangePinLinearLayout, this, pinnotmatch);
            checkFlag = false;
        }
        return checkFlag;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @OnClick({R.id.backImageViewHeader, R.id.savePinTextView, R.id.appImageViewHeader1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.appImageViewHeader1:
                mIntent = new Intent(ChangePinActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
                break;
            case R.id.savePinTextView:
                Constants.hideKeyboard(ChangePinActivity.this);
                if (checkValidation() == true) {
                    if (IsNetworkConnection.checkNetworkConnection(ChangePinActivity.this)) {
                        changePinJsonCall();
                    } else {
                        Constants.showMessage(mainChangePinLinearLayout, this, nointernetmsg);
                    }
                }
                break;
        }
    }

    private void changePinJsonCall() {
        Constants.showProgress(ChangePinActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", "" + getMyUserId());
            jsonObject.put("newPin", newPinEditText.getText().toString());
            jsonObject.put("userPin", String.valueOf(oldPinEditText.getText().toString()));
//            jsonObject.put("userPin", getUserData().getUserPin());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "signIn json " + json);
//        Constants.showProgress(ChangePinActivity.this);
        Call<List<JsonListPojo>> call = RestClient.get().changePINJsonCall(json);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {

                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        Constants.closeProgress();
                        Constants.showMessage(mainChangePinLinearLayout, ChangePinActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        UserListPojo.Data data = getUserData();
                        data.setUserPin(newPinEditText.getText().toString());
                        savePinTextView.setEnabled(false);
                        updateUser(data);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
                                finish();

                            }
                        }, 2000);
                    } else {
                        Constants.closeProgress();
                        Constants.showMessage(mainChangePinLinearLayout, ChangePinActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
