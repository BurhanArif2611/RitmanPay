package com.fil.workerappz.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.AddBeneficiaryActivity;
import com.fil.workerappz.ForgotPinActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.SelectBeneficiaryViewActivity;
import com.fil.workerappz.SignInActivity;
import com.fil.workerappz.SignUpSubmitActivity;
import com.fil.workerappz.pojo.CountryData;
import com.fil.workerappz.pojo.ModeWiseCountryListJsonPojo;
import com.fil.workerappz.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CountryBeneficiarySelection extends BottomSheetDialogFragment {

    @BindView(R.id.countrySelectionRecyclerView)
    RecyclerView countrySelectionRecyclerView;
    @BindView(R.id.closeCountrySelectionImageView)
    ImageView closeCountrySelectionImageView;
    @BindView(R.id.labelselectcountryTextView)
    TextView labelselectcountryTextView;
    @BindView(R.id.inputSearch)
    EditText inputSearch;
    Unbinder unbinder;
    @BindView(R.id.rootBottomSheet1)
    LinearLayout rootBottomSheet1;
    @BindView(R.id.rootBottomSheet2)
    LinearLayout rootBottomSheet2;


    private List<ModeWiseCountryListJsonPojo.Data> countryListPojos = new ArrayList<>();
    private Activity activity;
    private RecyclerView.LayoutManager layoutManager;
    private CountryBeneficiarySelection.CountryListAdapter countryListAdapter;
    BottomSheetBehavior mBottomSheetBehaviorCallback;


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.country_selection, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);

        mBottomSheetBehaviorCallback = BottomSheetBehavior.from(rootBottomSheet2);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    mBottomSheetBehaviorCallback.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }

        View parent = (View) contentView.getParent();
        parent.setFitsSystemWindows(true);
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(parent);
        contentView.measure(0, 0);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        bottomSheetBehavior.setPeekHeight(screenHeight);

        if (params.getBehavior() instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior)params.getBehavior()).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    mBottomSheetBehaviorCallback.setState(BottomSheetBehavior.STATE_EXPANDED);
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                }
            });
        }

        params.height = screenHeight;
        parent.setLayoutParams(params);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
            countryListJsonCall();
        } else {
//            Constants.showMessage(mainLinearLayoutSignIn, getActivity(), getResources().getString(R.string.no_internet));
        }*/

        countryListPojos = (List<ModeWiseCountryListJsonPojo.Data>) getArguments().getSerializable("country_list");
        countryListJsonCall();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);


        closeCountrySelectionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        layoutManager = new LinearLayoutManager(getActivity());
        countrySelectionRecyclerView.setLayoutManager(layoutManager);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public class CountryListAdapter extends RecyclerView.Adapter<CountryBeneficiarySelection.CountryListAdapter.ViewHolder> implements Filterable {

        private final Activity mContext;
        private List<ModeWiseCountryListJsonPojo.Data> countryListPojos;
        private Currency currency;
        private List<ModeWiseCountryListJsonPojo.Data> countryList;
//        private List<CountryData> countryListFiltered;

        CountryListAdapter(Activity mContext, List<ModeWiseCountryListJsonPojo.Data> countryListPojos) {
            this.mContext = mContext;
            this.countryListPojos = countryListPojos;
            this.countryList = countryListPojos;
        }

        @Override
        public CountryBeneficiarySelection.CountryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_selection_adapter, parent, false);
            return new CountryBeneficiarySelection.CountryListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final CountryBeneficiarySelection.CountryListAdapter.ViewHolder holder, int position) {

                holder.imageViewCountrySelectionAdapter.setVisibility(View.GONE);
                holder.textViewCountrySelectionAdapter.setText(countryListPojos.get(position).getCountryName());

//            else {
//                holder.imageViewCountrySelectionAdapter.setVisibility(View.VISIBLE);
//                holder.textViewCountrySelectionAdapter.setText(new String(Base64.decode(countryListPojos.get(position).getCountryName().trim().getBytes(), Base64.DEFAULT)));
//                Picasso.with(getActivity()).load(Constants.FLAG_URL + countryListPojos.get(position).getCountryFlagImage()).into(holder.imageViewCountrySelectionAdapter);
//            }
            holder.mainCountrySelectionAdapterLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getActivity() instanceof AddBeneficiaryActivity) {
                        AddBeneficiaryActivity forgotPinActivity = (AddBeneficiaryActivity) getActivity();
                        forgotPinActivity.updateCountrySelection(countryListPojos, holder.getAdapterPosition());
                        dismiss();
                    }
                    else if (getActivity() instanceof SelectBeneficiaryViewActivity) {
                        SelectBeneficiaryViewActivity forgotPinActivity = (SelectBeneficiaryViewActivity) getActivity();
                        forgotPinActivity.updateCountrySelection(countryListPojos, holder.getAdapterPosition());
                        dismiss();
                    }
                }
            });

        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        countryListPojos = countryList;
                    } else {
                        List<ModeWiseCountryListJsonPojo.Data> filteredList = new ArrayList<>();
                        for (ModeWiseCountryListJsonPojo.Data row : countryList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if (((row.getCountryName())).toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                        countryListPojos = filteredList;
                    }

                    FilterResults filterResults = new FilterResults();
                    filterResults.values = countryListPojos;
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    countryListPojos = (ArrayList<ModeWiseCountryListJsonPojo.Data>) filterResults.values;
                    notifyDataSetChanged();
                }
            };
        }

        @Override
        public int getItemCount() {
            return countryListPojos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.textViewCountrySelectionAdapter)
            TextView textViewCountrySelectionAdapter;
            @BindView(R.id.mainCountrySelectionAdapterLinearLayout)
            LinearLayout mainCountrySelectionAdapterLinearLayout;
            @BindView(R.id.imageViewCountrySelectionAdapter)
            ImageView imageViewCountrySelectionAdapter;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }

    private void countryListJsonCall() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("languageID", Constants.language_id);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        String json = "[" + jsonObject + "]";
//
//        Call<List<CountryListPojo>> call = RestClient.get().countryListJsonCall(json);
//
//        call.enqueue(new Callback<List<CountryListPojo>>() {
//            @Override
//            public void onResponse(Call<List<CountryListPojo>> call, Response<List<CountryListPojo>> response) {
//
//                if (response.body() != null && response.body() instanceof ArrayList) {
//                    countryListPojos.addAll(response.body());
        if (countryListPojos.size() > 0) {
            countryListAdapter = new CountryBeneficiarySelection.CountryListAdapter(getActivity(), countryListPojos);
            countrySelectionRecyclerView.setAdapter(countryListAdapter);

            inputSearch.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    if (cs.length() > 0) {
                        countryListAdapter.getFilter().filter(cs);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {

                }

                @Override
                public void afterTextChanged(Editable arg0) {

                }
            });
        }

//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<CountryListPojo>> call, Throwable t) {
//
//            }
//        });
    }
}