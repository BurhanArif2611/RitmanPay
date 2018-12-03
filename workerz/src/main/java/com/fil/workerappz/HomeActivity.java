package com.fil.workerappz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.HomeGridAdapter;
import com.fil.workerappz.pojo.BalanceListJsonPojo;
import com.fil.workerappz.pojo.HomeDataBean;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.CircleTransform;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.DrawerMenu;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class HomeActivity extends ActionBarActivity {

    @BindView(R.id.menuImageViewHeader)
    ImageView menuImageViewHeader;
    @BindView(R.id.appImageViewHeader)
    ImageView appImageViewHeader;
    @BindView(R.id.notificationImageViewHeader)
    ImageView notificationImageViewHeader;
    @BindView(R.id.homeRecyclerView)
    RecyclerView homeRecyclerView;
    @BindView(R.id.kycImageViewFooter)
    ImageView kycImageViewFooter;
    @BindView(R.id.kycTextViewFooter)
    TextView kycTextViewFooter;
    @BindView(R.id.kycLinearLayoutFooter)
    LinearLayout kycLinearLayoutFooter;
    @BindView(R.id.quickPayImageViewFooter)
    ImageView quickPayImageViewFooter;
    @BindView(R.id.quickPayTextViewFooter)
    TextView quickPayTextViewFooter;
    @BindView(R.id.quickPayLinearLayoutFooter)
    LinearLayout quickPayLinearLayoutFooter;
    @BindView(R.id.beneficiaryImageViewFooter)
    ImageView beneficiaryImageViewFooter;
    @BindView(R.id.beneficiaryTextViewFooter)
    TextView beneficiaryTextViewFooter;
    @BindView(R.id.beneficiaryLinearLayoutFooter)
    LinearLayout beneficiaryLinearLayoutFooter;
    @BindView(R.id.historyImageViewFooter)
    ImageView historyImageViewFooter;
    @BindView(R.id.historyTextViewFooter)
    TextView historyTextViewFooter;
    @BindView(R.id.historyLinearLayoutFooter)
    LinearLayout historyLinearLayoutFooter;
    @BindView(R.id.slideHolderHome)
    SlideHolder slideHolderHome;
    @BindView(R.id.loadMoneyLinearLayoutHome)
    LinearLayout loadMoneyLinearLayoutHome;
    @BindView(R.id.walletTransferLinearLayoutHome)
    LinearLayout walletTransferLinearLayoutHome;
    @BindView(R.id.nameTextViewHomeActivity)
    TextView nameTextViewHomeActivity;
    @BindView(R.id.pointsTextViewHomeActivity)
    TextView pointsTextViewHomeActivity;
    @BindView(R.id.walletAmountTextViewHomeActivity)
    TextView walletAmountTextViewHomeActivity;
    @BindView(R.id.mainHomeActivityLinearLayout)
    LinearLayout mainHomeActivityLinearLayout;
    @BindView(R.id.homeImageViewFooter)
    ImageView homeImageViewFooter;
    @BindView(R.id.homeTextViewFooter)
    TextView homeTextViewFooter;
    @BindView(R.id.homeLinearLayoutFooter)
    LinearLayout homeLinearLayoutFooter;
    @BindView(R.id.homeUserProfileImageView)
    ImageView homeUserProfileImageView;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.homeUserRefreshImageView)
    ImageView homeUserRefreshImageView;
    @BindView(R.id.goodtohavetextview)
    TextView goodtohavetextview;
    @BindView(R.id.textbalance)
    TextView textbalance;
    @BindView(R.id.textviewpointbalance)
    TextView textviewpointbalance;
    @BindView(R.id.textviewpoints)
    TextView textviewpoints;
    @BindView(R.id.textviewloadmoney)
    TextView textviewloadmoney;
    @BindView(R.id.textviewmoneytransfer)
    TextView textviewmoneytransfer;

    private Intent mIntent;
    private final ArrayList<HomeDataBean> homeDataBeans = new ArrayList<>();
    private final ArrayList<BalanceListJsonPojo> balanceListJsonPojos = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private UserListPojo.Data userListPojo;
    private Currency currency;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        ButterKnife.bind(this);

        sessionManager = new SessionManager(HomeActivity.this);
        userListPojo = getUserData();
        if (Constants.bankNextBenificaryCount == 0) {
            Constants.bankNextBenificaryCount = userListPojo.getBankBenificaryCount();
        }

        Constants.bankBenificaryCount = userListPojo.getUserCustomerNo();
        if (Constants.cashBenificaryCount == 0) {
            Constants.cashBenificaryCount = userListPojo.getCashBenificaryCount();
        }
        Constants.beneficiarcount = userListPojo.getBankBenificaryCount();


        if (!Constants.firsttimeautologout) {
            onUserInteraction();
            Constants.firsttimeautologout = true;
        }

//        currency=userListPojo.getCountryCurrencySymbol();


        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {
                goodtohavetextview.setText(datumLable_languages.getGoodToHaveYouBack());
                textbalance.setText(datumLable_languages.getBALANCE());
                textviewpointbalance.setText(datumLable_languages.getPointBalance() + ":");
                textviewpoints.setText(" " + datumLable_languages.getPoints());
                textviewloadmoney.setText(datumLable_languages.getLoadMoney());
                textviewmoneytransfer.setText(datumLable_languages.getMoneyTransfer());
                homeTextViewFooter.setText(datumLable_languages.getHome());
                quickPayTextViewFooter.setText(datumLable_languages.getQuickPay());
                beneficiaryTextViewFooter.setText(datumLable_languages.getBeneficiary());
                historyTextViewFooter.setText(datumLable_languages.getHistory());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
            } else {
                goodtohavetextview.setText(getResources().getString(R.string.good_to_have_you_back));
                textbalance.setText(getResources().getString(R.string.balance));
                textviewpointbalance.setText(getResources().getString(R.string.point_balance));
                textviewpoints.setText(getResources().getString(R.string.points));
                textviewloadmoney.setText(getResources().getString(R.string.load_money));
                textviewmoneytransfer.setText(getResources().getString(R.string.money_transfer));
                homeTextViewFooter.setText(getResources().getString(R.string.home));
                quickPayTextViewFooter.setText(getResources().getString(R.string.quick_pay));
                beneficiaryTextViewFooter.setText(getResources().getString(R.string.beneficiary));
                historyTextViewFooter.setText(getResources().getString(R.string.history));
                nointernetmsg = getResources().getString(R.string.no_internet);
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }


        if (IsNetworkConnection.checkNetworkConnection(this)) {
            updateWalletBalanceJsonCall();
            if (sessionManager.getDeviceToken().length() == 0) {
                updateDeviceTokenJsonCall();
                sessionManager.setDeviceToken(Constants.device_token);
            }
        } else {
            Constants.showMessage(mainHomeActivityLinearLayout, this, nointernetmsg);
        }

        Picasso.with(HomeActivity.this).load(Constants.IMAGE_URL_USER + getUserData().getUserProfilePicture()).placeholder(R.drawable.user_profile_home).error(R.drawable.user_profile_home).transform(new CircleTransform()).into(homeUserProfileImageView);

        homeSelection();
        setBeanData();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void updateWalletBalanceJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userID", "" + getMyUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "balance json " + json);
        Call<List<BalanceListJsonPojo>> call = RestClient.get().getBalanceListJsonCall(json);

        call.enqueue(new Callback<List<BalanceListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<BalanceListJsonPojo>> call, Response<List<BalanceListJsonPojo>> response) {
                balanceListJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    balanceListJsonPojos.addAll(response.body());
                    if (balanceListJsonPojos.get(0).getStatus() == true) {
                        sessionManager.setWalletBalance((float) balanceListJsonPojos.get(0).getData().get(0).getBalance());
                        walletAmountTextViewHomeActivity.setText(sessionManager.userProfileData().getCountryCurrencySymbol() + " " + (float) balanceListJsonPojos.get(0).getData().get(0).getBalance());
//                        DrawerMenu.accountBalanceTextViewDrawerMenu.setText("Balance: " + " " + currency.getCurrencyCode() + " " + balanceListJsonPojos.get(0).getData().get(0).getBalance());
                        DrawerMenu.accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance() + ":" + " " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + balanceListJsonPojos.get(0).getData().get(0).getBalance());


//                        Constants.showMessage(mainHomeActivityLinearLayout, HomeActivity.this, balanceListJsonPojos.get(0).getInfo());
                    } else {

                        walletAmountTextViewHomeActivity.setText(" " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
                        DrawerMenu.accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance() + ":" + " " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
//                        Constants.showMessage(mainHomeActivityLinearLayout, HomeActivity.this, balanceListJsonPojos.get(0).getInfo());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BalanceListJsonPojo>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshProfile();
//        SetUpBroadCast();

        if (slideHolderHome.isOpened()) {
            slideHolderHome.toggle();

        }
    }

    public void SetUpBroadCast() {
        IntentFilter filter = new IntentFilter("");
        LocalBroadcastManager.getInstance(HomeActivity.this).registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (!TextUtils.isEmpty(intent.getStringExtra("UpdatedBalance"))) {
                    if (IsNetworkConnection.checkNetworkConnection(HomeActivity.this)) {
                        updateWalletBalanceJsonCall();
                    }
                }
            }

        }
    };

    private void refreshProfile() {
        userListPojo = getUserData();
        nameTextViewHomeActivity.setText(datumLable_languages.getHi() + " " + userListPojo.getUserFirstName() + ",");
        currency = Currency.getInstance(userListPojo.getCountryCurrencySymbol());
        if (sessionManager.getWalletBalance() != 0) {
            walletAmountTextViewHomeActivity.setText(sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
            DrawerMenu.accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance() + ":" + " " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
        }

    }

    private void setBeanData() {
        homeDataBeans.add(new HomeDataBean(R.drawable.profile_home, datumLable_languages.getProfile()));
        homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_wallet, datumLable_languages.getWallet()));
        homeDataBeans.add(new HomeDataBean(R.drawable.prepaid_card_home, datumLable_languages.getPrepaidCard()));
        homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_mobile, datumLable_languages.getMobileTopup()));
        homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_bill_payment, datumLable_languages.getBillPay()));
//        homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_money_transfer, "Money Transfer"));
        homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_loyalty, datumLable_languages.getLoyaltyPoints()));

        HomeGridAdapter homeGridAdapter = new HomeGridAdapter(HomeActivity.this, homeDataBeans);
        gridLayoutManager = new GridLayoutManager(HomeActivity.this, 3);

        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(3, 1, false, 0);
        homeRecyclerView.addItemDecoration(itemDecoration);

        homeRecyclerView.setLayoutManager(gridLayoutManager);
        homeRecyclerView.setAdapter(homeGridAdapter);
        homeRecyclerView.setHasFixedSize(true);
    }

    @OnClick({R.id.menuImageViewHeader, R.id.notificationImageViewHeader, R.id.kycLinearLayoutFooter, R.id.quickPayLinearLayoutFooter, R.id.beneficiaryLinearLayoutFooter, R.id.historyLinearLayoutFooter, R.id.walletTransferLinearLayoutHome, R.id.loadMoneyLinearLayoutHome, R.id.homeUserProfileImageView, R.id.homeUserRefreshImageView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader:
                slideHolderHome.toggle();
                break;
            case R.id.homeUserRefreshImageView:
//                DrawerMenu.accountBalanceTextViewDrawerMenu.setText("Balance: " + " " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
//                slideHolderHome.toggle();
                updateWalletBalanceJsonCall();
                break;
            case R.id.notificationImageViewHeader:
                mIntent = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(mIntent);
                break;
            case R.id.kycLinearLayoutFooter:
                mIntent = new Intent(HomeActivity.this, UploadYourDocumentActivity.class);
                startActivity(mIntent);
                break;
            case R.id.quickPayLinearLayoutFooter:
                mIntent = new Intent(HomeActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "quick_pay");
                startActivity(mIntent);
                break;
            case R.id.beneficiaryLinearLayoutFooter:
                mIntent = new Intent(HomeActivity.this, SelectBeneficiaryActivity.class);
                mIntent.putExtra("come_from", "");
                startActivity(mIntent);
                break;
            case R.id.historyLinearLayoutFooter:
                mIntent = new Intent(HomeActivity.this, PinVerificationActivity.class);
                mIntent.putExtra("come_from", "history");
                startActivity(mIntent);
                break;
            case R.id.walletTransferLinearLayoutHome:
                mIntent = new Intent(HomeActivity.this, SendMoneyActivity.class);
                startActivity(mIntent);
                break;
            case R.id.loadMoneyLinearLayoutHome:
                mIntent = new Intent(HomeActivity.this, AddMoneyToWalletActivity.class);
                startActivity(mIntent);
                break;
            case R.id.homeUserProfileImageView:
                mIntent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(mIntent);
                break;
        }
    }

    private void homeSelection() {
        homeImageViewFooter.setImageResource(R.drawable.footer_icon_home_selected);
        homeTextViewFooter.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    private void updateDeviceTokenJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userDeviceType", Constants.device_type);
            jsonObject.put("userDeviceID", Constants.device_token);
            jsonObject.put("userID", "" + getMyUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "device token json " + json);
        Call<List<JsonListPojo>> call = RestClient.get().updateDeviceTokenJsonCall(json);

        call.enqueue(new Callback<List<JsonListPojo>>() {
            @Override
            public void onResponse(Call<List<JsonListPojo>> call, Response<List<JsonListPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    CustomLog.d("System out", "" + response.body().get(0).getInfo());
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {

            }
        });
    }

    class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;
        private final int headerNum;

        GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int headerNum) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
            this.headerNum = headerNum;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) - headerNum; // item position

            if (position >= 0) {
                int column = position % spanCount; // item column

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                    outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                    if (position < spanCount) { // top edge
                        outRect.top = spacing;
                    }
                    outRect.bottom = spacing; // item bottom
                } else {
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                    if (position >= spanCount) {
                        outRect.top = spacing; // item top
                    }
                }
            } else {
                outRect.left = 0;
                outRect.right = 0;
                outRect.top = 0;
                outRect.bottom = 0;
            }
        }
    }
}