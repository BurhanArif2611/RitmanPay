package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.BrowsePlansListAdapter;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.ding.GetProductsList;
import com.fil.workerappz.pojo.ding.GetProvidersList;
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

/**
 * Created by HS on 23-Mar-18.
 * FIL AHM
 */

public class BrowsePlansActivity extends ActionBarActivity {

    @BindView(R.id.closeBrowsePlansImageView)
    ImageView closeBrowsePlansImageView;
    @BindView(R.id.providerNameTextView)
    TextView providerNameTextView;
    @BindView(R.id.browsePlanRecyclerView)
    RecyclerView browsePlanRecyclerView;
    @BindView(R.id.mainBrowsePlansLinearLayout)
    LinearLayout mainBrowsePlansLinearLayout;

    private RecyclerView.LayoutManager layoutManager;
    private BrowsePlansListAdapter browsePlansListAdapter;
    private GetProvidersList.Data providerListData = new GetProvidersList.Data();
    private final ArrayList<GetProductsList> getProductsLists = new ArrayList<>();
    private Intent mIntent;
    private String countryIso = "";
    private String mobileNo = "";
    private SessionManager sessionManager;
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private LabelListData datumLable_languages = new LabelListData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_plans);
        ButterKnife.bind(this);

        mIntent = getIntent();
        if (mIntent != null) {
            mobileNo = mIntent.getStringExtra("mobile_no");
            countryIso = mIntent.getStringExtra("country_iso");
            providerListData = (GetProvidersList.Data) mIntent.getSerializableExtra("provider");
            providerNameTextView.setText(providerListData.getName());
            try {
                sessionManager = new SessionManager(BrowsePlansActivity.this);
                datumLable_languages_msg = sessionManager.getAppLanguageMessage();
                datumLable_languages = sessionManager.getAppLanguageLabel();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            if (IsNetworkConnection.checkNetworkConnection(this)) {
                dingBrowsePlanListJsonCall();
            } else {
                Constants.showMessage(mainBrowsePlansLinearLayout, this, datumLable_languages.getNoInternetConnectionAvailable());
            }
        }
    }

    private void dingBrowsePlanListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(BrowsePlansActivity.this);
        try {
            jsonObject.put("countryIsos", countryIso);
            jsonObject.put("regionCodes", "");
            jsonObject.put("mobNo", mobileNo);
            jsonObject.put("providerCodes", providerListData.getProviderCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<GetProductsList>> call = RestClient.get().getProductsJsonCall(json);

        call.enqueue(new Callback<List<GetProductsList>>() {

            @Override
            public void onResponse(Call<List<GetProductsList>> call, Response<List<GetProductsList>> response) {
                Constants.closeProgress();
                getProductsLists.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    getProductsLists.addAll(response.body());
                    if (getProductsLists.get(0).getStatus() == true) {
                        if (getProductsLists.get(0).getData().size() > 0) {
                            layoutManager = new LinearLayoutManager(BrowsePlansActivity.this);
                            browsePlansListAdapter = new BrowsePlansListAdapter(BrowsePlansActivity.this, getProductsLists,datumLable_languages.getValidity(),datumLable_languages.getReceiveValue());
                            browsePlanRecyclerView.setLayoutManager(layoutManager);
                            browsePlanRecyclerView.setAdapter(browsePlansListAdapter);
                        }
                    } else {
                        Constants.showMessage(mainBrowsePlansLinearLayout, BrowsePlansActivity.this, datumLable_languages_msg.getMessage(getProductsLists.get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GetProductsList>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }


    @OnClick(R.id.closeBrowsePlansImageView)
    public void onClick() {
        finish();
    }
}
