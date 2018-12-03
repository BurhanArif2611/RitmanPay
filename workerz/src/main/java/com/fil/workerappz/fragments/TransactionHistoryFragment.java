package com.fil.workerappz.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fil.workerappz.R;
import com.fil.workerappz.adapter.TransactionHistoryAdapter;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.TransactionHistoryListJsonPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class TransactionHistoryFragment extends BaseFragment {

    @BindView(R.id.maintransactionfragmentlinearlayout)
    LinearLayout maintransactionfragmentlinearlayout;
    Unbinder unbinder;
    private int userId;
    private int position;
    static int y;
    int pageNo = 0;
    int visibleItemCount, totalItemCount, firstVisibleItemPosition;
    boolean isLoading = false;
    boolean isLastpage = false;
    private LabelListData datumLable_languages = new LabelListData();
    //    @BindView(R.id.transactionHistoryRecyclerView)
    private RecyclerView transactionHistoryRecyclerView;
    private SessionManager sessionManager;
    private LinearLayoutManager layoutManager;
//    Unbinder unbinder;

    private TransactionHistoryAdapter transactionHistoryAdapter;
    private final ArrayList<TransactionHistoryListJsonPojo> transactionHistoryListJsonPojos = new ArrayList<>();
    private String norecordfoundmsg;
    boolean pagginationFlag = false;

    public TransactionHistoryFragment() {
    }

    public TransactionHistoryFragment newInstance() {
        TransactionHistoryFragment fragment = new TransactionHistoryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.transaction_history, container, false);
//        unbinder = ButterKnife.bind(this, view);
        transactionHistoryRecyclerView = view.findViewById(R.id.transactionHistoryRecyclerView);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public void getTransactionHistory(int position, int userId, final Activity activity) {
        this.position = position;
        this.userId = userId;
        this.activity = activity;

        CustomLog.d("System out", "activity is " + this.activity);
        pageNo = 0;
        layoutManager = new LinearLayoutManager(activity);
        sessionManager = new SessionManager(getActivity());
        transactionHistoryRecyclerView.setLayoutManager(layoutManager);

        try {

            datumLable_languages = sessionManager.getAppLanguageLabel();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages != null) {
            norecordfoundmsg = datumLable_languages.getNoRecordFound();

        } else {

            norecordfoundmsg = getResources().getString(R.string.no_record_found);


        }
        if (IsNetworkConnection.checkNetworkConnection(activity)) {
            getTransactionHistory();
        }

        transactionHistoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (transactionHistoryRecyclerView.SCROLL_STATE_IDLE == newState) {
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
                        pagginationFlag = true;
                        getTransactionHistory();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
        unbinder.unbind();
    }

    private void getTransactionHistory() {
        Date mDate = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userID", userId);
            jsonObject.put("startDate", "");
            jsonObject.put("endDate", sdf.format(mDate));
            jsonObject.put("page", String.valueOf(pageNo));
            jsonObject.put("pagesize", "20");
            jsonObject.put("filter", Constants.filter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "transaction history json " + json);
        Constants.showProgress(activity);
        Call<List<TransactionHistoryListJsonPojo>> call = RestClient.get().walletHistoryJsonCall(json);

        call.enqueue(new Callback<List<TransactionHistoryListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<TransactionHistoryListJsonPojo>> call, Response<List<TransactionHistoryListJsonPojo>> response) {

                if (response.body() != null && response.body() instanceof ArrayList) {
                    isLoading = false;
                    if (response.body().get(0).getStatus() == false) {
                        isLastpage = true;
                        if (transactionHistoryListJsonPojos.size() == 0) {
                            transactionHistoryRecyclerView.setAdapter(null);

                        }
                    } else {

                        if (response.body().get(0).getData().size() == 0 && pageNo == 0) {
                            isLastpage = true;
                            transactionHistoryRecyclerView.setAdapter(null);
                            Constants.showMessage(maintransactionfragmentlinearlayout, activity, norecordfoundmsg);
                        } else {
                            if (pageNo == 0) {
                                transactionHistoryListJsonPojos.clear();
                                transactionHistoryListJsonPojos.addAll(response.body());
                                SessionManager sessionManager;
                                LabelListData datumLable_languages = new LabelListData();
                                sessionManager = new SessionManager(activity);
                                datumLable_languages = sessionManager.getAppLanguageLabel();
                                transactionHistoryAdapter = new TransactionHistoryAdapter(activity, transactionHistoryListJsonPojos, datumLable_languages);
//                            transactionHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                transactionHistoryRecyclerView.setAdapter(transactionHistoryAdapter);
                            } else {
                                if (pagginationFlag) {
                                    transactionHistoryListJsonPojos.get(0).getData().addAll(response.body().get(0).getData());
                                    transactionHistoryAdapter.notifyDataSetChanged();
                                }

                            }
                            pageNo = pageNo + 1;
                            pagginationFlag = false;
                            if (transactionHistoryListJsonPojos.get(0).getData().size() < 20) {
                                isLastpage = true;
                            } else {
                                transactionHistoryAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<TransactionHistoryListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();

            }
        });
    }

    private Activity activity;
}