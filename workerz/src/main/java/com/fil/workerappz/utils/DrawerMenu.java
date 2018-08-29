package com.fil.workerappz.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.BillPaymentActivity;
import com.fil.workerappz.HomeActivity;
import com.fil.workerappz.LoyaltyProgramActivity;
import com.fil.workerappz.MobileTopUpActivity;
import com.fil.workerappz.MoreActivity;
import com.fil.workerappz.NotificationActivity;
import com.fil.workerappz.PinVerificationActivity;
import com.fil.workerappz.PrepaidCardActivity;
import com.fil.workerappz.ProfileActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.SendMoneyActivity;
import com.fil.workerappz.SignInActivity;
import com.fil.workerappz.SignUpActivity;
import com.fil.workerappz.TermsAndPrivacyActivity;
import com.fil.workerappz.UploadYourDocumentActivity;
import com.fil.workerappz.pojo.BalanceListJsonPojo;
import com.fil.workerappz.pojo.HomeDataBean;
import com.fil.workerappz.pojo.JsonListPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DrawerMenu extends LinearLayout implements View.OnClickListener {
    public static TextView accountBalanceTextViewDrawerMenu;
    private final ImageView userProfileImageViewDrawerMenu;
    private final ImageView moneyTransferImageViewDrawerMenu;
    private final TextView moneyTransferTextViewDrawerMenu;
    private final LinearLayout moneyTransferLinearLayoutDrawerMenu;
    private final LinearLayout homeLinearLayoutDrawerMenu;
    private final LinearLayout legalDocumentLinearLayoutDrawerMenu;
    private final ImageView mobileRechargeImageViewDrawerMenu;
    private final TextView mobileRechargeTextViewDrawerMenu;
    private final LinearLayout mobileRechargeLinearLayoutDrawerMenu;
    private final ImageView billPaymentImageViewDrawerMenu;
    private final TextView billPaymentTextViewDrawerMenu;
    private final TextView pointdrawermenutextview;
    private final LinearLayout billPaymentLinearLayoutDrawerMenu;
    private final ImageView walletImageViewDrawerMenu;
    private final TextView walletTextViewDrawerMenu;
    private final LinearLayout walletLinearLayoutDrawerMenu;
    private final ImageView loyaltyProgramImageViewDrawerMenu;
    private final TextView loyaltyProgramTextViewDrawerMenu;
    private final LinearLayout loyaltyProgramLinearLayoutDrawerMenu;
    private final ImageView notificationImageViewDrawerMenu;
    private final TextView notificationTextViewDrawerMenu;
    private final LinearLayout moreLinearLayoutMoreActivity;
    private final LinearLayout notificationLinearLayoutDrawerMenu;
    private final LinearLayout profileLinearLayoutDrawerMenu;
    private final LinearLayout prepaiedcardLinearLayoutDrawerMenu;
    private final TextView helpNSupportTextView;
    private final TextView Hometextview;
    private final TextView LegaldocTextview;
    private final TextView pointsTextViewDrawerMenu;
    private final TextView nameTextViewDrawerMenu;
    private final TextView logoutTextViewDrawerMenu;
    private final TextView inviteTextViewDrawerMenu;
    private final TextView TextviewBalancePrepaidCarddrawermenu;
    private final TextView moretextviewdrawermenu;
    private final TextView aboutsettingTextview;

    private Intent mIntent;
    private final Context mContext;
    private final UserListPojo.Data userListPojo;
    private final SessionManager sessionManager;
    private final ArrayList<HomeDataBean> homeDataBeans = new ArrayList<>();
    private Currency currency;
    private String shareString = "";
    private final ArrayList<BalanceListJsonPojo> balanceListJsonPojos = new ArrayList<>();
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();

    @SuppressWarnings("deprecation")
    public DrawerMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drawer_menu, null);
        LinearLayout sliderLinearLayout = view.findViewById(R.id.mainDrawerMenuLinearLayout);
        sliderLinearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        sessionManager = new SessionManager(mContext);
        userListPojo = sessionManager.userProfileData();
        datumLable_languages_msg = sessionManager.getAppLanguageMessage();
//        if (sessionManager.getWalletBalance() == 0) {
//            if (IsNetworkConnection.checkNetworkConnection(getContext())) {
////                updateWalletBalanceJsonCall();
//            } else {
////                Constants.showMessage(m, this, getResources().getString(R.string.no_internet));
//            }
//        }


//        shareString = "I love using WorkerAppz, It's simple and incredible. Sign up with my code " + userListPojo.getUserReferKey() + ". " + "You should try it here. " + datumLable_languages_msg.getANDROIDURL();

        nameTextViewDrawerMenu = view.findViewById(R.id.nameTextViewDrawerMenu);
        accountBalanceTextViewDrawerMenu = view.findViewById(R.id.accountBalanceTextViewDrawerMenu);
        pointsTextViewDrawerMenu = view.findViewById(R.id.pointsTextViewDrawerMenu);

        pointsTextViewDrawerMenu.setText("0");
        nameTextViewDrawerMenu.setText(userListPojo.getUserFirstName() + " " + userListPojo.getUserLastName());
        currency = Currency.getInstance(userListPojo.getCountryCurrencySymbol());


        userProfileImageViewDrawerMenu = view.findViewById(R.id.userProfileImageViewDrawerMenu);
        Picasso.with(mContext).load(Constants.IMAGE_URL_USER + sessionManager.userProfileData().getUserProfilePicture()).placeholder(R.drawable.user_profile_circle).error(R.drawable.user_profile_circle).transform(new CircleTransform()).into(userProfileImageViewDrawerMenu);

        billPaymentLinearLayoutDrawerMenu = view.findViewById(R.id.billPaymentLinearLayoutDrawerMenu);
        mobileRechargeLinearLayoutDrawerMenu = view.findViewById(R.id.mobileRechargeLinearLayoutDrawerMenu);
        loyaltyProgramLinearLayoutDrawerMenu = view.findViewById(R.id.loyaltyProgramLinearLayoutDrawerMenu);
        moneyTransferLinearLayoutDrawerMenu = view.findViewById(R.id.moneyTransferLinearLayoutDrawerMenu);
        homeLinearLayoutDrawerMenu = view.findViewById(R.id.homeLinearLayoutDrawerMenu);
        prepaiedcardLinearLayoutDrawerMenu = view.findViewById(R.id.prepaiedcardLinearLayoutDrawerMenu);
        legalDocumentLinearLayoutDrawerMenu = view.findViewById(R.id.legalDocumentLinearLayoutDrawerMenu);
        notificationLinearLayoutDrawerMenu = view.findViewById(R.id.notificationLinearLayoutDrawerMenu);
        moreLinearLayoutMoreActivity = view.findViewById(R.id.moreLinearLayoutMoreActivity);
        walletLinearLayoutDrawerMenu = view.findViewById(R.id.walletLinearLayoutDrawerMenu);
        profileLinearLayoutDrawerMenu = view.findViewById(R.id.profileLinearLayoutDrawerMenu);

        loyaltyProgramImageViewDrawerMenu = view.findViewById(R.id.loyaltyProgramImageViewDrawerMenu);
        billPaymentImageViewDrawerMenu = view.findViewById(R.id.billPaymentImageViewDrawerMenu);
        mobileRechargeImageViewDrawerMenu = view.findViewById(R.id.mobileRechargeImageViewDrawerMenu);
        moneyTransferImageViewDrawerMenu = view.findViewById(R.id.moneyTransferImageViewDrawerMenu);
        notificationImageViewDrawerMenu = view.findViewById(R.id.notificationImageViewDrawerMenu);
        walletImageViewDrawerMenu = view.findViewById(R.id.walletImageViewDrawerMenu);

        billPaymentTextViewDrawerMenu = view.findViewById(R.id.billPaymentTextViewDrawerMenu);
        pointdrawermenutextview = view.findViewById(R.id.pointdrawermenutextview);
        TextviewBalancePrepaidCarddrawermenu = view.findViewById(R.id.TextviewBalancePrepaidCarddrawermenu);
        LegaldocTextview = view.findViewById(R.id.LegaldocTextview);
        loyaltyProgramTextViewDrawerMenu = view.findViewById(R.id.loyaltyProgramTextViewDrawerMenu);
        mobileRechargeTextViewDrawerMenu = view.findViewById(R.id.mobileRechargeTextViewDrawerMenu);
        moneyTransferTextViewDrawerMenu = view.findViewById(R.id.moneyTransferTextViewDrawerMenu);
        notificationTextViewDrawerMenu = view.findViewById(R.id.notificationTextViewDrawerMenu);
        helpNSupportTextView = view.findViewById(R.id.helpNSupportTextView);
        moretextviewdrawermenu = view.findViewById(R.id.moretextviewdrawermenu);
        aboutsettingTextview = view.findViewById(R.id.aboutsettingTextview);
        Hometextview = view.findViewById(R.id.Hometextview);
        walletTextViewDrawerMenu = view.findViewById(R.id.walletTextViewDrawerMenu);
        logoutTextViewDrawerMenu = view.findViewById(R.id.logoutTextViewDrawerMenu);
        inviteTextViewDrawerMenu = view.findViewById(R.id.inviteTextViewDrawerMenu);

        walletLinearLayoutDrawerMenu.setOnClickListener(this);
        homeLinearLayoutDrawerMenu.setOnClickListener(this);
        legalDocumentLinearLayoutDrawerMenu.setOnClickListener(this);
        notificationLinearLayoutDrawerMenu.setOnClickListener(this);
        moreLinearLayoutMoreActivity.setOnClickListener(this);
        mobileRechargeLinearLayoutDrawerMenu.setOnClickListener(this);
        moneyTransferLinearLayoutDrawerMenu.setOnClickListener(this);
        billPaymentLinearLayoutDrawerMenu.setOnClickListener(this);
        loyaltyProgramLinearLayoutDrawerMenu.setOnClickListener(this);
        profileLinearLayoutDrawerMenu.setOnClickListener(this);
        prepaiedcardLinearLayoutDrawerMenu.setOnClickListener(this);
        helpNSupportTextView.setOnClickListener(this);
        logoutTextViewDrawerMenu.setOnClickListener(this);
        inviteTextViewDrawerMenu.setOnClickListener(this);


        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {
                shareString = datumLable_languages.getILoveUsingWorkerAppz() + datumLable_languages.getSignUpWithMyCode() + userListPojo.getUserReferKey() + ". " + datumLable_languages.getYouShouldTryItHere() + datumLable_languages_msg.getANDROIDURL();
                DrawerMenu.accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance() + ":" + " " + sessionManager.userProfileData().getCountryCurrencySymbol() + " " + sessionManager.getWalletBalance());
                pointdrawermenutextview.setText(datumLable_languages.getPoints() + ":");
                Hometextview.setText(datumLable_languages.getHome());
                LegaldocTextview.setText(datumLable_languages.getLegalDocuments());
                mobileRechargeTextViewDrawerMenu.setText(datumLable_languages.getMobileTopup());
                moneyTransferTextViewDrawerMenu.setText(datumLable_languages.getMoneyTransfer());
                TextviewBalancePrepaidCarddrawermenu.setText(datumLable_languages.getPrepaidCard());
                billPaymentTextViewDrawerMenu.setText(datumLable_languages.getBillPay());
                walletTextViewDrawerMenu.setText(datumLable_languages.getWallet());
                loyaltyProgramTextViewDrawerMenu.setText(datumLable_languages.getLoyaltyPoints());
                notificationTextViewDrawerMenu.setText(datumLable_languages.getNotification());
                moretextviewdrawermenu.setText(datumLable_languages.getMore());
                aboutsettingTextview.setText(datumLable_languages.getAboutSettingsEtc());
                helpNSupportTextView.setText(datumLable_languages.getHelpSupport());
                inviteTextViewDrawerMenu.setText(datumLable_languages.getInvite());
                logoutTextViewDrawerMenu.setText(datumLable_languages.getLogout());
                accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance());

            } else {
                shareString = "I love using WorkerAppz, It's simple and incredible. Sign up with my code " + userListPojo.getUserReferKey() + ". " + "You should try it here. " + userListPojo.getAndroidAppUrl();
                accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance()+": " + " " + currency.getCurrencyCode() + " " + sessionManager.getWalletBalance());
                pointdrawermenutextview.setText(getResources().getString(R.string.points));
                Hometextview.setText(getResources().getString(R.string.home));
                LegaldocTextview.setText(getResources().getString(R.string.legal_documents));
                mobileRechargeTextViewDrawerMenu.setText(getResources().getString(R.string.mobile_recharge));
                moneyTransferTextViewDrawerMenu.setText(getResources().getString(R.string.money_transfer));
                TextviewBalancePrepaidCarddrawermenu.setText(getResources().getString(R.string.prepaid_card));
                billPaymentTextViewDrawerMenu.setText(getResources().getString(R.string.bill_pay));
                walletTextViewDrawerMenu.setText(getResources().getString(R.string.wallet));
                loyaltyProgramTextViewDrawerMenu.setText(getResources().getString(R.string.loyalty_program));
                notificationTextViewDrawerMenu.setText(getResources().getString(R.string.notification));
                moretextviewdrawermenu.setText(getResources().getString(R.string.more));
                aboutsettingTextview.setText(getResources().getString(R.string.about_settings_etc));
                helpNSupportTextView.setText(getResources().getString(R.string.help_support));
                inviteTextViewDrawerMenu.setText(getResources().getString(R.string.invite));
                logoutTextViewDrawerMenu.setText(getResources().getString(R.string.logout));
            }

        } catch (
                Exception e)

        {
            e.printStackTrace();
        }
        this.addView(sliderLinearLayout);
    }


    @Override
    public void onClick(View v) {
        if (v == walletLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, PinVerificationActivity.class);
            mIntent.putExtra("come_from", "wallet_summary");
            mContext.startActivity(mIntent);
        } else if (v == loyaltyProgramLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, LoyaltyProgramActivity.class);
            mIntent.putExtra("come_from", "loyality");
            mContext.startActivity(mIntent);
        } else if (v == moneyTransferLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, SendMoneyActivity.class);
            mContext.startActivity(mIntent);
        } else if (v == mobileRechargeLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, MobileTopUpActivity.class);
            mContext.startActivity(mIntent);
        } else if (v == notificationLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, NotificationActivity.class);
            mContext.startActivity(mIntent);
        } else if (v == moreLinearLayoutMoreActivity) {
            mIntent = new Intent(mContext, MoreActivity.class);
            mContext.startActivity(mIntent);
        } else if (v == billPaymentLinearLayoutDrawerMenu) {
//            homeDataBeans.add(new HomeDataBean(R.drawable.profile_home, "Profile"));
//            homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_wallet, "Wallet"));
//            homeDataBeans.add(new HomeDataBean(R.drawable.prepaid_card_home, "Prepaid Card"));
//            homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_mobile, "Mobile Top-up"));
//            homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_bill_payment, "Bill Pay"));
////        homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_money_transfer, "Money Transfer"));
//            homeDataBeans.add(new HomeDataBean(R.drawable.home_icon_loyalty, "Loyalty Points"));
//            mIntent = new Intent(mContext, BillPaymentActivity.class);
//            mIntent.putExtra("home_bean", homeDataBeans);
//            mIntent.putExtra("home_bean_selection", 0);
//            mContext.startActivity(mIntent);
            mIntent = new Intent(mContext, BillPaymentActivity.class);
            mIntent.putExtra("come_from", "bill");
            mContext.startActivity(mIntent);
        } else if (v == profileLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, ProfileActivity.class);
            mContext.startActivity(mIntent);
        }else if (v == prepaiedcardLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, PrepaidCardActivity.class);
            mIntent.putExtra("come_from", "prepaiedcard");
            mContext.startActivity(mIntent);
        } else if (v == helpNSupportTextView) {
            mIntent = new Intent(mContext, TermsAndPrivacyActivity.class);
            mIntent.putExtra("come_from", "Help");
            mContext.startActivity(mIntent);
        } else if (v == logoutTextViewDrawerMenu) {
            if (IsNetworkConnection.checkNetworkConnection(mContext)) {
                logoutUser();
            }
        } else if (v == inviteTextViewDrawerMenu) {
            invite();
        } else if (v == homeLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, HomeActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(mIntent);
        } else if (v == legalDocumentLinearLayoutDrawerMenu) {
            mIntent = new Intent(mContext, UploadYourDocumentActivity.class);
            mContext.startActivity(mIntent);
        }
    }

    private void invite() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("plain/text");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "WorkerAppz invitation");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
//        mContext.startActivity(Intent.createChooser(shareIntent, "WorkerAppz"));
        mContext.startActivity(Intent.createChooser(shareIntent, datumLable_languages.getWorkerAppz()));
    }

    private void logoutUser() {
        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }

        builder.setTitle(datumLable_languages.getWorkerAppz())
                .setMessage(datumLable_languages.getWantToLogout())
                .setCancelable(false)
                .setPositiveButton(datumLable_languages.getYes(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        updateDeviceTokenJsonCall();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(datumLable_languages.getNo(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.app_icon)
                .show();
    }

    private void updateDeviceTokenJsonCall() {
        Constants.showProgress((Activity) mContext);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userDeviceType", Constants.device_type);
            jsonObject.put("userDeviceID", "");
            jsonObject.put("userID", "" + userListPojo.getUserID());
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
                    if (response.body().get(0).getStatus() == true) {
                        Constants.closeProgress();
                        if (sessionManager.getRememberMe() == true) {
                            sessionManager.setVerify(false);
                        } else {
                            sessionManager.clearUser();
                        }
//                        sessionManager.clearUser();
                        sessionManager.setAppLanguageLabel(datumLable_languages);
                        sessionManager.setAppLanguageMessage(datumLable_languages_msg);

                        Constants.cashBenificaryCount = 0;
                        Constants.bankNextBenificaryCount = 0;
                        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.cancelAll();
                        mIntent = new Intent(mContext, SignUpActivity.class);
                        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(mIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<JsonListPojo>> call, Throwable t) {

            }
        });
    }

    public void updateWalletBalanceJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userID", "" + userListPojo.getUserID());
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
                        accountBalanceTextViewDrawerMenu.setText(datumLable_languages.getBalance()+": "+ " " + currency.getCurrencyCode() + " " + sessionManager.getWalletBalance());


//                        Constants.showMessage(mainHomeActivityLinearLayout, HomeActivity.this, balanceListJsonPojos.get(0).getInfo());
                    } else {
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

}