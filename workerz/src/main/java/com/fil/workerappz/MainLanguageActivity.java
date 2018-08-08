package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainLanguageActivity extends AppCompatActivity {

    @BindView(R.id.languageSpinner)
    MaterialSpinner languageSpinner;
    @BindView(R.id.next_button)
    TextView nextButton;
    @BindView(R.id.mainlanguagelayout)
    LinearLayout mainlanguagelayout;
    private String languageid;
    private final ArrayList<GetLanguageList> languagesListPojo = new ArrayList<>();
    private final ArrayList<LabelListJsonPojo> labelListPojos = new ArrayList<>();
    private final ArrayList<MessageListJsonPojo> messageListPojos = new ArrayList<>();
    private final ArrayList<LabelListData> labelListdataPojos = new ArrayList<>();
    private final ArrayList<MessagelistData> messageListdataPojos = new ArrayList<>();
    private Intent mIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_screen);
        ButterKnife.bind(this);
        if (IsNetworkConnection.checkNetworkConnection(MainLanguageActivity.this)) {
            languageListJsonCall();
        } else {
            Constants.showMessage(mainlanguagelayout, this, getResources().getString(R.string.no_internet));
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (IsNetworkConnection.checkNetworkConnection(MainLanguageActivity.this)) {
                    labelListJsonCall();
                    messageListJsonCall();

                } else {
                                    Constants.showMessage(mainlanguagelayout, MainLanguageActivity.this, getResources().getString(R.string.no_internet));
                }

            }
        });
    }


    private void languageListJsonCall() {
        Constants.showProgress(MainLanguageActivity.this);
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
                        for (int i = 0; i < languagesListPojo.get(0).getData().size(); i++) {
                            languagestringList.add(new String(Base64.decode(languagesListPojo.get(0).getData().get(i).getLanguageName().trim().getBytes(), Base64.DEFAULT)));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainLanguageActivity.this, android.R.layout.simple_spinner_item, languagestringList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        languageSpinner.setAdapter(adapter);

                        for (int i = 0; i < languagesListPojo.get(0).getData().size(); i++) {
                            if (Constants.language_id.equalsIgnoreCase(String.valueOf(languagesListPojo.get(0).getData().get(i).getLanguageID()))) {
                                Constants.language_id = Constants.language_id;
                                languageSpinner.setSelection(i + 1);

                            }
                        }

                        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    languageid = String.valueOf(languagesListPojo.get(0).getData().get(position).getLanguageID());
                                    Constants.language_id = languageid;

                                }

                            }


                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetLanguageList>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void messageListJsonCall() {
        Constants.showProgress(MainLanguageActivity.this);
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
                SessionManager sessionManager = new SessionManager(MainLanguageActivity.this);
                if (response.body() != null && response.body() instanceof ArrayList) {
                    messageListPojos.addAll(response.body());
                    if (messageListPojos.get(0).getStatus() == true) {
                        messageListdataPojos.add(messageListPojos.get(0).getData().get(0));
//                        SugarRecord.save(messageListPojos.get(0).getData());

                        sessionManager.setAppLanguageMessage(messageListdataPojos.get(0));
                        sessionManager.setlanguageselection(true);
                        if (sessionManager.getRememberMe() == true && sessionManager.getVerify() == true) {
                            mIntent = new Intent(MainLanguageActivity.this, HomeActivity.class);
//                    mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                            startActivity(mIntent);
                        } else if (sessionManager.getRememberMe() == true && sessionManager.getVerify() == false) {
                            mIntent = new Intent(MainLanguageActivity.this, SignInActivity.class);
//                    mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                            startActivity(mIntent);
                        } else {
                            mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                            startActivity(mIntent);
                        }

                    } else {
                        sessionManager.setlanguageselection(true);
                        if (sessionManager.getRememberMe() == true && sessionManager.getVerify() == true) {
                            mIntent = new Intent(MainLanguageActivity.this, HomeActivity.class);
//                    mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                            startActivity(mIntent);
                        } else if (sessionManager.getRememberMe() == true && sessionManager.getVerify() == false) {
                            mIntent = new Intent(MainLanguageActivity.this, SignInActivity.class);
//                    mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                            startActivity(mIntent);
                        } else {
                            mIntent = new Intent(MainLanguageActivity.this, SignUpActivity.class);
                            startActivity(mIntent);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MessageListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    private void labelListJsonCall() {
//        Constants.showProgress(MainLanguageActivity.this);
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
//                Constants.closeProgress();
                labelListdataPojos.clear();
                labelListPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    labelListPojos.addAll(response.body());
                    if (labelListPojos.get(0).getStatus() == true) {

//                        SugarRecord.save(labelListPojos.get(0).getData());
                        labelListdataPojos.add(labelListPojos.get(0).getData().get(0));
                        SessionManager sessionManager = new SessionManager(MainLanguageActivity.this);
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

}
