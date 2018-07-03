package com.fil.workerappz.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fil.workerappz.R;
import com.fil.workerappz.adapter.WalletHistoryAdapter;
import com.fil.workerappz.pojo.LabelListData;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class WalletHistoryFragment extends BaseFragment {

    RecyclerView transactionHistoryRecyclerView;
    private int userId;
    private int position;
    private LinearLayoutManager layoutManager;
    static int y;
    int pageNo = 0;
    int visibleItemCount, totalItemCount, firstVisibleItemPosition;
    boolean isLoading = false;
    boolean isLastpage = false;
    private WalletHistoryAdapter walletHistoryAdapter;
    private LabelListData datumLable_languages = new LabelListData();
    private final ArrayList<TransactionHistoryListJsonPojo> transactionHistoryListJsonPojos = new ArrayList<>();
    private boolean pagginationFlag=false;

    public WalletHistoryFragment() {

    }

    public WalletHistoryFragment newInstance() {
        WalletHistoryFragment fragment = new WalletHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.transaction_history, container, false);
        transactionHistoryRecyclerView = view.findViewById(R.id.transactionHistoryRecyclerView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutManager = new LinearLayoutManager(getActivity());
    }

    public void getTransactionHistory(int position, int userId) {
        this.position = position;
        this.userId = userId;
        pageNo = 0;
        layoutManager = new LinearLayoutManager(getActivity());
        transactionHistoryRecyclerView.setLayoutManager(layoutManager);
        if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
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

    private void getTransactionHistory() {
        Date mDate = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("userID", userId);
            jsonObject.put("startDate", "");
            jsonObject.put("endDate", sdf.format(mDate));
            jsonObject.put("filter", Constants.filter);
            jsonObject.put("page", String.valueOf(pageNo));
            jsonObject.put("pagesize", "20");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "wallet json " + json);
        Constants.showProgress(getActivity());
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
                        if (response.body().get(0).getData().size() == 0) {
                            isLastpage = true;
                        } else {
                            if (pageNo == 0) {
                                transactionHistoryListJsonPojos.clear();
                                transactionHistoryListJsonPojos.addAll(response.body());

                                walletHistoryAdapter = new WalletHistoryAdapter(getActivity(), transactionHistoryListJsonPojos);
                                transactionHistoryRecyclerView.setAdapter(walletHistoryAdapter);
                            } else {
                                if (pagginationFlag) {
                                    transactionHistoryListJsonPojos.get(0).getData().addAll(response.body().get(0).getData());
                                    walletHistoryAdapter.notifyDataSetChanged();
                                }
                            }
                            pageNo = pageNo + 1;
                            pagginationFlag = false;
                            if (transactionHistoryListJsonPojos.get(0).getData().size() < 20) {
                                isLastpage = true;
                            } else {
                                walletHistoryAdapter.notifyDataSetChanged();
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
}