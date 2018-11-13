package com.fil.workerappz.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.fil.workerappz.MobileTopUpActivity;
import com.fil.workerappz.R;
import com.fil.workerappz.SelectBeneficiaryViewActivity;
import com.fil.workerappz.SignInActivity;
import com.fil.workerappz.SignUpSubmitActivity;
import com.fil.workerappz.pojo.ding.GetCountryList;
import com.fil.workerappz.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DingCountryBottomSheet extends BottomSheetDialogFragment {

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


    private List<GetCountryList.Data> countryListPojos = new ArrayList<>();
    private Activity activity;
    private RecyclerView.LayoutManager layoutManager;
    private DingCountryBottomSheet.CountryListAdapter countryListAdapter;

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.country_selection, null);
        dialog.setContentView(contentView);
        ButterKnife.bind(this, contentView);
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

        countryListPojos = (List<GetCountryList.Data>) getArguments().getSerializable("country_list");
        countryListJsonCall();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(rootBottomSheet2);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
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


    public class CountryListAdapter extends RecyclerView.Adapter<DingCountryBottomSheet.CountryListAdapter.ViewHolder> implements Filterable {

        private final Activity mContext;
        private List<GetCountryList.Data> countryListPojos;
        private Currency currency;
        private List<GetCountryList.Data> countryList;
//        private List<GetCountryList> countryListFiltered;

        CountryListAdapter(Activity mContext, List<GetCountryList.Data> countryListPojos) {
            this.mContext = mContext;
            this.countryListPojos = countryListPojos;
            this.countryList = countryListPojos;
        }

        @Override
        public DingCountryBottomSheet.CountryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_selection_adapter, parent, false);
            return new DingCountryBottomSheet.CountryListAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final DingCountryBottomSheet.CountryListAdapter.ViewHolder holder, int position) {

                holder.imageViewCountrySelectionAdapter.setVisibility(View.GONE);
                holder.textViewCountrySelectionAdapter.setText(countryListPojos.get(position).getCountryName());


            holder.mainCountrySelectionAdapterLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof MobileTopUpActivity) {
                        MobileTopUpActivity signInActivity = (MobileTopUpActivity) getActivity();
                        signInActivity.updateCountrySelection(countryListPojos, holder.getAdapterPosition());
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
                        List<GetCountryList.Data> filteredList = new ArrayList<>();
                        for (GetCountryList.Data row : countryList) {

                            // name match condition. this might differ depending on your requirement
                            // here we are looking for name or phone number match
                            if ((row.getCountryName().trim()).toLowerCase().contains(charString.toLowerCase())) {
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
                    countryListPojos = (ArrayList<GetCountryList.Data>) filterResults.values;
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
            countryListAdapter = new DingCountryBottomSheet.CountryListAdapter(getActivity(), countryListPojos);
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
