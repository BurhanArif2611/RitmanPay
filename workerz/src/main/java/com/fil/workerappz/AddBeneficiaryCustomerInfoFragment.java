package com.fil.workerappz;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fil.workerappz.fragments.BaseFragment;
import com.fil.workerappz.pojo.CreateCustomerListJsonPojo;
import com.fil.workerappz.pojo.IdTypeListJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fr.ganfra.materialspinner.MaterialSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FUSION on 26/03/2018.
 */

public class AddBeneficiaryCustomerInfoFragment extends BaseFragment {

    private final ArrayList<IdTypeListJsonPojo> idTypePojos = new ArrayList<>();
    private Calendar myCalendar1 = Calendar.getInstance();
    @BindView(R.id.customerInfoTextView)
    TextView customerInfoTextView;
    @BindView(R.id.idtypeSpinnerAddBeneficiaryCustomerInfo)
    MaterialSpinner idtypeSpinnerAddBeneficiaryCustomerInfo;
    @BindView(R.id.idNumberEditTextAddBeneficiaryCustomerInfo)
    MaterialEditText idNumberEditTextAddBeneficiaryCustomerInfo;
    @BindView(R.id.dateofBirthEditTextAddBeneficiaryCustomerInfo)
    MaterialEditText dateofBirthEditTextAddBeneficiaryCustomerInfo;
    @BindView(R.id.dateofBirthTextViewAddBeneficiaryCustomerInfo)
    TextView dateofBirthTextViewAddBeneficiaryCustomerInfo;
    @BindView(R.id.idDescriptionEditTextAddBeneficiaryCustomerInfo)
    MaterialEditText idDescriptionEditTextAddBeneficiaryCustomerInfo;
    @BindView(R.id.next_textview)
    TextView nextTextview;
    @BindView(R.id.addBeneficiaryCustomerInfoLinearlayout)
    LinearLayout addBeneficiaryCustomerInfoLinearlayout;
    private Unbinder unbinder;
    private String idtype;
    private String comefrom;
    int userId;
    private ArrayList<CreateCustomerListJsonPojo> createCustomerListJsonPojos = new ArrayList<>();
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private SessionManager sessionManager;
    private String idtypemsg, idnumbermsg, valididnumbermsg, dateofbirthmsg,iddescriptionmsg;
    private String nointernetmsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_beneficiary_customer_info_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//
////                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR) - 18);
////                dateSpecified = myCalendar.getTime();
//
//                Calendar minAdultAge = new GregorianCalendar();
//                minAdultAge.add(Calendar.YEAR, -18);
//                if (minAdultAge.before(myCalendar)) {
//
//                }
//                else {
//                    updateLabel();
//                }
//            }
//
//        };
        try {

            sessionManager = new SessionManager(getActivity());
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                idNumberEditTextAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getIdNumber());
                dateofBirthEditTextAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getDateOfBirth());
                idDescriptionEditTextAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getIDDESCRIPTION());
                nextTextview.setHint(datumLable_languages.getAdd());
                customerInfoTextView.setText(datumLable_languages.getCustomerInfo());
                nointernetmsg=datumLable_languages.getNoInternetConnectionAvailable();
            } else {
                idNumberEditTextAddBeneficiaryCustomerInfo.setHint(getResources().getString(R.string.id_number));
                dateofBirthEditTextAddBeneficiaryCustomerInfo.setHint(getResources().getString(R.string.date_of_birth));
                idDescriptionEditTextAddBeneficiaryCustomerInfo.setHint(getResources().getString(R.string.id_description));
                nextTextview.setHint(getResources().getString(R.string.add));
                customerInfoTextView.setText(getResources().getString(R.string.customer_info));
                nointernetmsg = getResources().getString(R.string.no_internet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (datumLable_languages_msg != null) {
            idtypemsg = datumLable_languages_msg.getSelectIdType();
            idnumbermsg = datumLable_languages_msg.getEnterIdNumber();
            valididnumbermsg = datumLable_languages_msg.getEnterValidIdNumber();
            dateofbirthmsg = datumLable_languages_msg.getSelectDateOfBirth();

        } else {
            idtypemsg = getResources().getString(R.string.Please_select_idType);
            idnumbermsg = getResources().getString(R.string.Please_Enter_id_number);
            valididnumbermsg = getResources().getString(R.string.Please_Enter_valid_id_number);
            dateofbirthmsg = getResources().getString(R.string.Please_select_Dob);



        }
        dateofBirthTextViewAddBeneficiaryCustomerInfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

//                new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR) - 18, myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                DataPickerDialog1();
            }
        });
        if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
            idTypeJsonCall();
        } else {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(),nointernetmsg);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comefrom = getArguments().getString("come_from");
        userId = getArguments().getInt("user_id");
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateofBirthEditTextAddBeneficiaryCustomerInfo.setText(sdf.format(myCalendar1.getTime()));

//        dateofbirth = Constants.formatDate(dateofBirthEditTextAddBeneficiaryCustomerInfo.getText().toString(), "dd/MM/yyyy", "dd MM yyyy");

    }

    private void DataPickerDialog1() {
        final Calendar myCalendar = Calendar.getInstance();
        int mYear = myCalendar.get(Calendar.YEAR) - 18;
        int mMonth = myCalendar.get(Calendar.MONTH);
        int mDay = myCalendar.get(Calendar.DAY_OF_MONTH);

        Calendar mincalendar = Calendar.getInstance();
        mincalendar.set(mYear, mMonth, mDay);

        DatePickerDialog dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("year", year + "");
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(myCalendar)) {
                    Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(),nointernetmsg);
                } else {
                    myCalendar1 = myCalendar;
                    updateLabel();
                }
            }
        }, mYear, mMonth, mDay);

        dpd.getDatePicker().setMaxDate(mincalendar.getTimeInMillis());
        dpd.show();

    }

    private void idTypeJsonCall() {
        JSONObject jsonObject = new JSONObject();
        Constants.showProgress(getActivity());
        try {
            jsonObject.put("countryCode", "India");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
//        Constants.showProgress(ProfileActivity.this);
        Call<List<IdTypeListJsonPojo>> call = RestClient.get().getIdTypeJsonCall(json);

        call.enqueue(new Callback<List<IdTypeListJsonPojo>>() {

            @Override
            public void onResponse(Call<List<IdTypeListJsonPojo>> call, Response<List<IdTypeListJsonPojo>> response) {
                Constants.closeProgress();
                idTypePojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    idTypePojos.addAll(response.body());
                    if (idTypePojos.get(0).getStatus() == true) {
                        ArrayList<String> countryList = new ArrayList<>();
                        for (int i = 0; i < idTypePojos.get(0).getData().size(); i++) {
                            Constants.closeProgress();
                            countryList.add(new String(idTypePojos.get(0).getData().get(i).getIDType().trim()));
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, countryList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        idtypeSpinnerAddBeneficiaryCustomerInfo.setAdapter(adapter);

                        idtypeSpinnerAddBeneficiaryCustomerInfo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    idtype = idTypePojos.get(0).getData().get(position).getIDType_ID();
//                                    IDtype_Description = idTypePojos.get(0).getData().get(position).getIDType();
//                                    countryId = idTypePojos.get(0).getData().get(position).getCountryID();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<IdTypeListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }

    @OnClick(R.id.next_textview)
    public void onClick() {
        if (idtypeSpinnerAddBeneficiaryCustomerInfo == null && idtypeSpinnerAddBeneficiaryCustomerInfo.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), idtypemsg);
        } else if (idtypeSpinnerAddBeneficiaryCustomerInfo.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), idtypemsg);

        } else if (idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), idnumbermsg);
        } else if (idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString().length() < 10) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), valididnumbermsg);
        } else if (dateofBirthEditTextAddBeneficiaryCustomerInfo.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(),dateofbirthmsg);
        }
// else if (idDescriptionEditTextAddBeneficiaryCustomerInfo.getText().toString().length() == 0) {
//            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), );
//        }
        else {

            if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
                createCustomerJsonCall();
            } else {
                Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(),nointernetmsg);
            }

        }
    }

    public AddBeneficiaryCustomerInfoFragment newInstance(String bank, int userId) {
        AddBeneficiaryCustomerInfoFragment fragment = new AddBeneficiaryCustomerInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("come_from", bank);
        bundle.putInt("user_id", userId);
        fragment.setArguments(bundle);
        return fragment;
    }


    private void createCustomerJsonCall() {
        Constants.showProgress(getActivity());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", String.valueOf(userId));
            jsonObject.put("userDateOfBirth", Constants.formatDate(dateofBirthEditTextAddBeneficiaryCustomerInfo.getText().toString(), "dd/MM/yyyy", "MM/dd/yyyy"));
            jsonObject.put("IDType", idtype);
            jsonObject.put("IDtype_Description", idDescriptionEditTextAddBeneficiaryCustomerInfo.getText().toString());
            jsonObject.put("IDExpiryDate", "12/31/2099");
            jsonObject.put("IDNumber", String.valueOf(idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "customer json " + json);

        Call<List<CreateCustomerListJsonPojo>> call = RestClient.get().createCustomerJsonCall(json);

        call.enqueue(new Callback<List<CreateCustomerListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<CreateCustomerListJsonPojo>> call, Response<List<CreateCustomerListJsonPojo>> response) {
                createCustomerListJsonPojos.clear();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        createCustomerListJsonPojos.addAll(response.body());
//                        Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), response.body().get(0).getInfo());
                        Constants.closeProgress();
                        String test=datumLable_languages_msg.getMessage("Request successful");
                        String test1=createCustomerListJsonPojos.get(0).getData().get(0).getDescription();
                        if (test.equalsIgnoreCase("")) {
                            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(),test1);
                        } else {
                            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), datumLable_languages_msg.getMessage(createCustomerListJsonPojos.get(0).getData().get(0).getDescription().toString()));
                        }



                        Constants.beneficiarcount++;
                        Constants.bankBenificaryCount = Integer.parseInt(createCustomerListJsonPojos.get(0).getData().get(0).getCustomerNumber());
                        SessionManager sessionManager = new SessionManager(getActivity());
                        sessionManager.setUserCustomerNo(Integer.parseInt(createCustomerListJsonPojos.get(0).getData().get(0).getCustomerNumber()));
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                handler.removeCallbacks(this);
//                                finish();
//                                onRestart();
                                Intent mIntent = new Intent(getActivity(), AddBeneficiaryActivity.class);
                                mIntent.putExtra("come_from", comefrom);
                                mIntent.putExtra("customernumber", createCustomerListJsonPojos.get(0).getData().get(0).getCustomerNumber());
                                mIntent.putExtra("activitytype", "CustomerInfo");
                                startActivity(mIntent);
                                getActivity().finish();
                            }
                        }, 3300);


                    } else {
                        Constants.closeProgress();
                        if (datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()).equalsIgnoreCase("")) {
                            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(),response.body().get(0).getInfo().toString());
                        } else {
                            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                        }

                    }
                }
            }


            @Override
            public void onFailure(Call<List<CreateCustomerListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
            }
        });
    }
}