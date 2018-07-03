package com.fil.workerappz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.CMSListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
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

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class TermsAndPrivacyActivity extends ActionBarActivity {


    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.termsAndPrivacyWebView)
    WebView termsAndPrivacyWebView;
    @BindView(R.id.mainLinearLayoutTermsPrivacy)
    LinearLayout mainLinearLayoutTermsPrivacy;
    private Intent mIntent;
    private String comeFrom = "";
    private int cmsPageId = 0;
    private final ArrayList<CMSListPojo> cmsListPojos = new ArrayList<>();
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.terms_privacy);
        ButterKnife.bind(this);
        WebView termsAndPrivacyWebView = findViewById(R.id.termsAndPrivacyWebView);
        mIntent = getIntent();
        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        if (mIntent != null) {
            comeFrom = mIntent.getStringExtra("come_from");
        }
        try {
            sessionManager = new SessionManager(TermsAndPrivacyActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {
                if (comeFrom.equalsIgnoreCase("Privacy")) {
                    titleTextViewViewHeader2.setText(datumLable_languages.getPrivacyPolicy());
                    cmsPageId = 145174;
                } else if (comeFrom.equalsIgnoreCase("About")) {
                    titleTextViewViewHeader2.setText(datumLable_languages.getAbout());
                    cmsPageId = 451263;
                } else if (comeFrom.equalsIgnoreCase("Help")) {
                    titleTextViewViewHeader2.setText(datumLable_languages.getHelpSupport());
                    cmsPageId = 471258;
                } else {
                    titleTextViewViewHeader2.setText(datumLable_languages.getTermsConditions());
                    cmsPageId = 415263;
                }
               nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();;


            } else {
                if (comeFrom.equalsIgnoreCase("Privacy")) {
                    titleTextViewViewHeader2.setText(getResources().getString(R.string.privacy));
                    cmsPageId = 145174;
                } else if (comeFrom.equalsIgnoreCase("About")) {
                    titleTextViewViewHeader2.setText(getResources().getString(R.string.about_us));
                    cmsPageId = 451263;
                } else if (comeFrom.equalsIgnoreCase("Help")) {
                    titleTextViewViewHeader2.setText(getResources().getString(R.string.help_supprt));
                    cmsPageId = 471258;
                } else {
                    titleTextViewViewHeader2.setText(getResources().getString(R.string.terms));
                    cmsPageId = 415263;
                }
                nointernetmsg=getResources().getString(R.string.no_internet);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        if (IsNetworkConnection.checkNetworkConnection(this)) {
            getWebPageUrl();
        } else {
            Constants.showMessage(mainLinearLayoutTermsPrivacy, this,nointernetmsg);
        }
    }

    @OnClick(R.id.menuImageViewHeader2)
    public void onViewClicked() {
        finish();
    }

    private void setLoadWebPage() {
        termsAndPrivacyWebView.getSettings().setJavaScriptEnabled(true); // enable javascript

        termsAndPrivacyWebView.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Constants.showMessage(mainLinearLayoutTermsPrivacy, TermsAndPrivacyActivity.this, description
//                );
            }

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Constants.closeProgress();
            }
        });

        termsAndPrivacyWebView.loadUrl(cmsListPojos.get(0).getData().get(0).getCmspageScoUrl());
    }

    private void getWebPageUrl() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", "1");
            jsonObject.put("cmspageConstantCode", cmsPageId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        Constants.showProgress(TermsAndPrivacyActivity.this);
        Call<List<CMSListPojo>> call = RestClient.get().cmsPageJsonCall(json);

        call.enqueue(new Callback<List<CMSListPojo>>() {
            @Override
            public void onResponse(Call<List<CMSListPojo>> call, Response<List<CMSListPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    cmsListPojos.addAll(response.body());
                    if (cmsListPojos.get(0).getStatus() == true) {
                        setLoadWebPage();
                    } else {
                        Constants.closeProgress();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CMSListPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
