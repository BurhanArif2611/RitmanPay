package com.fil.workerappz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.adapter.NotificationListAdapter;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.NotificationListJsonPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

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
 * Created by HS on 26-Feb-18.
 * FIL AHM
 */

public class NotificationActivity extends ActionBarActivity {


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
    @BindView(R.id.notificationRecycleView)
    RecyclerView notificationRecycleView;
    @BindView(R.id.slideNotification)
    SlideHolder slideNotification;
    @BindView(R.id.mainNotificationLinearLayout)
    LinearLayout mainNotificationLinearLayout;
    private LinearLayoutManager layoutManager;
    private final List<NotificationListJsonPojo> notificationListpojo = new ArrayList<>();
    private NotificationListAdapter notificationListAdapter;
    private Intent mIntent;
    private int pageIndex = -1;
    private int pageSize = 10;
    private final int pagingSize = 20;
    private int pastVisibleItems;
    private boolean mIsLoading = false;
    static int y;
    int pageNo = 0;
    int visibleItemCount, totalItemCount, firstVisibleItemPosition;
    boolean isLoading = false;
    boolean isLastpage = false;
    private SessionManager sessionManager;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private String nointernetmsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification);
        ButterKnife.bind(this);


        try {
            sessionManager = new SessionManager(NotificationActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {


                titleTextViewViewHeader2.setText(datumLable_languages.getNotification());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();


            } else {
                titleTextViewViewHeader2.setText(getResources().getString(R.string.notification));
                nointernetmsg=getResources().getString(R.string.no_internet);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        layoutManager = new LinearLayoutManager(NotificationActivity.this);
        notificationRecycleView.setLayoutManager(layoutManager);
        if (IsNetworkConnection.checkNetworkConnection(this)) {
            notificationListJsonCall();
        } else {
            Constants.showMessage(mainNotificationLinearLayout, this,nointernetmsg);
        }

//

//    notificationRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener()

//    {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0) {
//                    visibleItemCount = layoutManager.getChildCount();
//                    totalItemCount = layoutManager.getItemCount();
//                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//
//                    if (mIsLoading) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                            mIsLoading = false;
//                            if (notificationListpojo.size() >= pageSize) {
//                                notificationListJsonCall();
//                                pageSize += pagingSize;
//                            }
//                        }
//                    }
//                }
//            }
//        });
        notificationRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (notificationRecycleView.SCROLL_STATE_IDLE == newState) {
                    // fragProductLl.setVisibility(View.VISIBLE);
                     /*if(y<=0){
                        ((HomeActivity)getActivity()).bottomnavigation.clearAnimation();
                        ((HomeActivity)getActivity()).bottomnavigation.animate().translationY(0).setDuration(200);
                    }
                    else{
                        y=0;
                        ((HomeActivity)getActivity()).bottomnavigation.clearAnimation();

                        ((HomeActivity)getActivity()).bottomnavigation.animate().translationY(getResources().getDimension(R.dimen._50sdp)).setDuration(200);
                    }*/
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                y = dy;
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
                if (!isLoading && !isLastpage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= 20) {

                        isLoading = true;
                        notificationListJsonCall();
                    }
                }
            }
        });
    }
//    private void notificationListJsonCall() {
//
//        Constants.showProgress(NotificationActivity.this);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userID", "" + getUserData().getUserID());
//            jsonObject.put("page", "0");
//            jsonObject.put("pagesize", "20");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String json = "[" + jsonObject + "]";
//        CustomLog.d("System out", "list notification" + json);
////        Constants.showProgress(ProfileActivity.this);
//        Call<List<NotificationListJsonPojo>> call = RestClient.get().notificationListJsonCall(json);
//        call.enqueue(new Callback<List<NotificationListJsonPojo>>() {
//            @Override
//            public void onResponse(Call<List<NotificationListJsonPojo>> call, final Response<List<NotificationListJsonPojo>> response) {
//                if (response.body() != null && response.body() instanceof ArrayList) {
//                    try {
//                        Constants.closeProgress();
//                        if (response.body().get(0).getStatus() == true) {
//                            CustomLog.d("System out", "true");
//                            notificationListpojo.clear();
//                            notificationListpojo.addAll(response.body());
//                            notificationListAdapter = new NotificationListAdapter(NotificationActivity.this, notificationListpojo, getUserData().getUserID());
//                            notificationRecycleView.setAdapter(notificationListAdapter);
//                        } else {
//                            notificationRecycleView.setAdapter(null);
//                            Constants.showMessage(mainNotificationLinearLayout, NotificationActivity.this, response.body().get(0).getInfo());
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//            @Override
//            public void onFailure(Call<List<NotificationListJsonPojo>> call, Throwable t) {
//                Constants.closeProgress();
//                t.printStackTrace();
//            }
//        });
//
//    }


    private void notificationListJsonCall() {

        Constants.showProgress(NotificationActivity.this);
//        pageIndex++;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", "" + getUserData().getUserID());
            jsonObject.put("page", pageNo);
            jsonObject.put("pagesize", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "list notification" + json);
//        Constants.showProgress(ProfileActivity.this);
        Call<List<NotificationListJsonPojo>> call = RestClient.get().notificationListJsonCall(json);
        call.enqueue(new Callback<List<NotificationListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<NotificationListJsonPojo>> call, final Response<List<NotificationListJsonPojo>> response) {
                if (response.body() != null && response.body() instanceof ArrayList) {
                    try {

                        if (response.body() != null && response.body().size() > 0) {
                            isLoading = false;
                            if (response.body().get(0).getStatus() == false) {
                                isLastpage = true;
                                if (notificationListpojo.size() == 0) {
                                    notificationRecycleView.setAdapter(null);
                                    Constants.showMessage(mainNotificationLinearLayout, NotificationActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                                }
                            } else {
                                mIsLoading = true;

                                if (pageNo == 0) {
                                    notificationListpojo.clear();
                                    notificationListpojo.addAll(response.body());
                                    notificationListAdapter = new NotificationListAdapter(NotificationActivity.this, notificationListpojo, getUserData().getUserID(),datumLable_languages_msg,datumLable_languages);
                                    notificationRecycleView.setAdapter(notificationListAdapter);
                                } else {
                                    notificationListpojo.get(0).getData().addAll(response.body().get(0).getData());
                                    notificationListAdapter.notifyDataSetChanged();

                                }

                                pageNo = pageNo + 1;
                                if (notificationListpojo.get(0).getData().size() < 20) {
                                    isLastpage = true;
                                } else {
                                    notificationListAdapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            mIsLoading = false;
                            notificationRecycleView.setAdapter(null);
                            Constants.showMessage(mainNotificationLinearLayout, NotificationActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Constants.closeProgress();
            }


            @Override
            public void onFailure(Call<List<NotificationListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });

    }

    @OnClick({R.id.appImageViewHeader2, R.id.menuImageViewHeader2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.appImageViewHeader2:
                mIntent = new Intent(NotificationActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                break;
            case R.id.menuImageViewHeader2:
                slideNotification.toggle();
                break;
        }
    }
}