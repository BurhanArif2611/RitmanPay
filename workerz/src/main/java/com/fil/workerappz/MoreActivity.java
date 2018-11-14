package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 05-Mar-18.
 * FIL AHM
 */

public class MoreActivity extends ActionBarActivity {


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
    @BindView(R.id.aboutLinearLayoutMoreActivity)
    LinearLayout aboutLinearLayoutMoreActivity;
    @BindView(R.id.termsLinearLayoutMoreActivity)
    LinearLayout termsLinearLayoutMoreActivity;
    @BindView(R.id.privacyLinearLayoutMoreActivity)
    LinearLayout privacyLinearLayoutMoreActivity;
    @BindView(R.id.settingsLinearLayoutMoreActivity)
    LinearLayout settingsLinearLayoutMoreActivity;
    @BindView(R.id.slideHolderMore)
    SlideHolder slideHolderMore;
    @BindView(R.id.textviewabout)
    TextView textviewabout;
    @BindView(R.id.textviewterms)
    TextView textviewterms;
    @BindView(R.id.textviewprivacypolicy)
    TextView textviewprivacypolicy;
    @BindView(R.id.textviewsetting)
    TextView textviewsetting;
    private Intent mIntent;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.more);
        ButterKnife.bind(this);


        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        menuImageViewHeader2.setImageResource(R.drawable.back_btn);
        try {
            sessionManager = new SessionManager(MoreActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {


                titleTextViewViewHeader2.setText(datumLable_languages.getMore());
                textviewabout.setText(datumLable_languages.getAbout());
                textviewprivacypolicy.setText(datumLable_languages.getPrivacyPolicy());
                textviewsetting.setText(datumLable_languages.getSettings());
                textviewterms.setText(datumLable_languages.getTermsConditions());


            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.more));
                textviewabout.setText(getResources().getString(R.string.about));
                textviewprivacypolicy.setText(getResources().getString(R.string.privacy));
                textviewsetting.setText(getResources().getString(R.string.settings));
                textviewterms.setText(getResources().getString(R.string.terms));


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.menuImageViewHeader2, R.id.aboutLinearLayoutMoreActivity, R.id.settingsLinearLayoutMoreActivity, R.id.termsLinearLayoutMoreActivity, R.id.privacyLinearLayoutMoreActivity, R.id.appImageViewHeader2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
//                slideHolderMore.toggle();
                finish();
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(MoreActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.aboutLinearLayoutMoreActivity:
                mIntent = new Intent(MoreActivity.this, TermsAndPrivacyActivity.class);
                mIntent.putExtra("come_from", "about");
                startActivity(mIntent);
                break;
            case R.id.settingsLinearLayoutMoreActivity:
                mIntent = new Intent(MoreActivity.this, SettingsActivity.class);
                startActivity(mIntent);
                break;
            case R.id.termsLinearLayoutMoreActivity:
                mIntent = new Intent(MoreActivity.this, TermsAndPrivacyActivity.class);
                mIntent.putExtra("come_from", "terms");
                startActivity(mIntent);
                break;
            case R.id.privacyLinearLayoutMoreActivity:
                mIntent = new Intent(MoreActivity.this, TermsAndPrivacyActivity.class);
                mIntent.putExtra("come_from", "privacy");
                startActivity(mIntent);
                break;
        }
    }

}
