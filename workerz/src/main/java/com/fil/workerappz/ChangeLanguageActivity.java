package com.fil.workerappz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.GetLanguageList;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.LabelListJsonPojo;
import com.fil.workerappz.pojo.MessageListJsonPojo;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;

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

public class ChangeLanguageActivity extends AppCompatActivity {

    private final ArrayList<LabelListJsonPojo> labelListPojos = new ArrayList<>();
    private final ArrayList<MessageListJsonPojo> messageListPojos = new ArrayList<>();
    private final ArrayList<LabelListData> labelListdataPojos = new ArrayList<>();
    private final ArrayList<MessagelistData> messageListdataPojos = new ArrayList<>();

    private final ArrayList<GetLanguageList> languagesListPojo = new ArrayList<>();
    @BindView(R.id.backImageViewHeader)
    ImageView backImageViewHeader;
    @BindView(R.id.titleTextViewViewHeader)
    TextView titleTextViewViewHeader;
    @BindView(R.id.skipTextViewViewHeader)
    TextView skipTextViewViewHeader;
    @BindView(R.id.languageTextviewchange)
    TextView languageTextviewchange;
    @BindView(R.id.linearlanguagechange)
    LinearLayout linearlanguagechange;
    @BindView(R.id.savelanguageTextView)
    TextView savelanguageTextView;
    @BindView(R.id.mainChangeLanguageLinearLayout)
    LinearLayout mainChangeLanguageLinearLayout;

    private String languageid = "1";
    String[] stockArr = new String[0];
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);
        ButterKnife.bind(this);
        titleTextViewViewHeader.setText(getResources().getString(R.string.change_language));
        dingCountryListJsonCall();
    }

    private void dingCountryListJsonCall() {
        Constants.showProgress(ChangeLanguageActivity.this);
        Call<List<GetLanguageList>> call = RestClient.get().getLanguageistJsonCall();
        call.enqueue(new Callback<List<GetLanguageList>>() {
            @Override
            public void onResponse(Call<List<GetLanguageList>> call, Response<List<GetLanguageList>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    languagesListPojo.clear();
                    languagesListPojo.addAll(response.body());
                    if (languagesListPojo.get(0).getStatus() == true) {
                        final ArrayList<String> languagestringList = new ArrayList<>();
                       stockArr = new String[languagesListPojo.get(0).getData().size()];
                        for (int i = 0; i < languagesListPojo.get(0).getData().size(); i++) {
                            languagestringList.add(new String(Base64.decode(languagesListPojo.get(0).getData().get(i).getLanguageName().trim().getBytes(), Base64.DEFAULT)));
                            stockArr[i] = new String(Base64.decode(languagesListPojo.get(0).getData().get(i).getLanguageName().trim().getBytes(), Base64.DEFAULT));
                        }
                            for (int i1=0;i1<stockArr.length;i1++)
                        {
                            if (Constants.language_id.equalsIgnoreCase(String.valueOf(languagesListPojo.get(0).getData().get(i1).getLanguageID())))
                            {

                                languageTextviewchange.setText(stockArr[i1]);
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetLanguageList>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void opendialog(final String[] stockArr) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ChangeLanguageActivity.this);
        mBuilder.setTitle("Select Language");
        mBuilder.setSingleChoiceItems(stockArr, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                languageTextviewchange.setText(stockArr[i]);
                for (int i1=0;i1<stockArr.length;i1++)
                {
                    if (languageTextviewchange.getText().toString().equalsIgnoreCase(new String(Base64.decode(languagesListPojo.get(0).getData().get(i1).getLanguageName().trim().getBytes(), Base64.DEFAULT))))
                    {
                        Constants.language_id= String.valueOf(languagesListPojo.get(0).getData().get(i1).getLanguageID());
                    }
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    @OnClick({R.id.backImageViewHeader, R.id.savelanguageTextView, R.id.linearlanguagechange})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImageViewHeader:
                finish();
                break;
            case R.id.linearlanguagechange:
                opendialog(stockArr);
                break;
            case R.id.savelanguageTextView:
                Constants.hideKeyboard(ChangeLanguageActivity.this);

                if (IsNetworkConnection.checkNetworkConnection(ChangeLanguageActivity.this)) {
                    labelListJsonCall();
                    messageListJsonCall();

                } else {
                    Constants.showMessage(mainChangeLanguageLinearLayout, this, getResources().getString(R.string.no_internet));
                }

                break;
        }
    }

    private void labelListJsonCall() {
        Constants.showProgress(ChangeLanguageActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "language lIST JSON" + json);

        Call<List<LabelListJsonPojo>> call = RestClient.get().labelListJsonCall(json);
        call.enqueue(new Callback<List<LabelListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<LabelListJsonPojo>> call, Response<List<LabelListJsonPojo>> response) {
                Constants.closeProgress();
                labelListdataPojos.clear();
                labelListPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    labelListPojos.addAll(response.body());
                    if (labelListPojos.get(0).getStatus() == true) {

//                        SugarRecord.save(labelListPojos.get(0).getData());
                        labelListdataPojos.add(labelListPojos.get(0).getData().get(0));
                        SessionManager sessionManager = new SessionManager(ChangeLanguageActivity.this);
                        sessionManager.setAppLanguageLabel(labelListdataPojos.get(0));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LabelListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void messageListJsonCall() {
        Constants.showProgress(ChangeLanguageActivity.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "language lIST JSON" + json);
        Call<List<MessageListJsonPojo>> call = RestClient.get().messageListJsonCall(json);
        call.enqueue(new Callback<List<MessageListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<MessageListJsonPojo>> call, Response<List<MessageListJsonPojo>> response) {
                Constants.closeProgress();
                messageListPojos.clear();
                messageListdataPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    messageListPojos.addAll(response.body());
                    if (messageListPojos.get(0).getStatus() == true) {
                        messageListdataPojos.add(messageListPojos.get(0).getData().get(0));
//                        SugarRecord.save(messageListPojos.get(0).getData());
                        linearlanguagechange.setEnabled(false);
                        SessionManager sessionManager = new SessionManager(ChangeLanguageActivity.this);
                        sessionManager.setAppLanguageMessage(messageListdataPojos.get(0));
                        sessionManager.setlanguageselection(true);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
                                finish();
                                mIntent = new Intent(ChangeLanguageActivity.this, ProfileActivity.class);
//                    mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                                startActivity(mIntent);

                            }
                        }, 2000);

                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessageListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }


}
