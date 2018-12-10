package com.fil.workerappz;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
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
import com.fil.workerappz.pojo.SourceOfFundJsonPojo;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    @BindView(R.id.idExpireyDatecustomerEditTextAddBeneficiary)
    MaterialEditText idExpireyDatecustomerEditTextAddBeneficiary;
    @BindView(R.id.idExpireyDatecustomerTextViewAddBeneficiary)
    TextView idExpireyDatecustomerTextViewAddBeneficiary;
    @BindView(R.id.findSourceSpinnerAddBeneficiary)
    MaterialSpinner findSourceSpinnerAddBeneficiary;
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
    private String comefrom, userdateofBirth = "";
    int userId;
    private ArrayList<CreateCustomerListJsonPojo> createCustomerListJsonPojos = new ArrayList<>();
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private SessionManager sessionManager;
    private String idtypemsg, idnumbermsg, valididnumbermsg, dateofbirthmsg, iddescriptionmsg, idexpireydate = "";
    private String nointernetmsg;
    private int idtypemaxlength = 15;
    private int idtypeminlength = 7;
    private ActionBarActivity activity;
    private String fundId="",fundname="";
    private final ArrayList<SourceOfFundJsonPojo.Datum> fundListPojos = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
                idTypeJsonCall();
                incomeListJsonCall();
            } else {
                Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), nointernetmsg);
            }
        } else {
            // fragment is no longer visible
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ActionBarActivity) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

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

                idNumberEditTextAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getIdNumber() + "*");
                idNumberEditTextAddBeneficiaryCustomerInfo.setFloatingLabelText(datumLable_languages.getIdNumber() + "*");
                dateofBirthEditTextAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getDateOfBirth() + "*");
                idDescriptionEditTextAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getIDDESCRIPTION());
                idDescriptionEditTextAddBeneficiaryCustomerInfo.setFloatingLabelText(datumLable_languages.getIDDESCRIPTION());
                idtypeSpinnerAddBeneficiaryCustomerInfo.setHint(datumLable_languages.getIdType() + "*");
                idtypeSpinnerAddBeneficiaryCustomerInfo.setFloatingLabelText(datumLable_languages.getIdType() + "*");
                nextTextview.setHint(datumLable_languages.getAdd());
                customerInfoTextView.setText(datumLable_languages.getCustomerInfo());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
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
        idExpireyDatecustomerEditTextAddBeneficiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.hideKeyboard(getActivity());
//                dateOfBirthDialog();
                DataPickerDialogIdExpireyDate();
            }
        });

        idNumberEditTextAddBeneficiaryCustomerInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if(!charSequence.equals("") ) {
//                    //do your work here
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                OnlyCharacter1(idNumberEditTextAddBeneficiaryCustomerInfo);
            }
        });


        return view;
    }

    private void DataPickerDialogIdExpireyDate() {

        String getfromdate = userdateofBirth;
        String getfrom[] = getfromdate.split("/");


        final Calendar myCalendar = Calendar.getInstance();
        final int mYear = Integer.parseInt(getfrom[2]);
        final int mMonth = Integer.parseInt(getfrom[1]);
        final int mDay = Integer.parseInt(getfrom[0]);

        Calendar mincalendar = Calendar.getInstance();
        mincalendar.set(mYear, mMonth - 1, mDay);
        int themeResId = 2;


        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("year", year + "");
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//                Calendar minAdultAge = new GregorianCalendar();
//                minAdultAge.add(Calendar.YEAR, mYear);
//                minAdultAge.add(Calendar.MONTH, mMonth-1);
//                minAdultAge.add(Calendar.DAY_OF_MONTH, mDay);
////                minAdultAge.add(Calendar.YEAR, -18);
//                if (minAdultAge.before(myCalendar)) {
//                    Constants.showMessage(addBeneficiaryActivityLinearLayout, SelectBeneficiaryViewActivity.this, "Please Enter Valid Date");
//                } else {
                myCalendar1 = myCalendar;
                updateLabelIdExpireyDate();
//                }
            }
        }, mYear, mMonth - 1, mDay);

        dpd.getDatePicker().setMinDate(mincalendar.getTimeInMillis());

        dpd.show();

    }

    private void updateLabelIdExpireyDate() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        idExpireyDatecustomerEditTextAddBeneficiary.setText(sdf.format(myCalendar1.getTime()));

        idexpireydate = Constants.formatDate(idExpireyDatecustomerEditTextAddBeneficiary.getText().toString(), "dd/MM/yyyy", "MM/dd/yyyy");

    }

    public static void OnlyCharacter1(MaterialEditText editText) {
//        if (editText.getText().toString().length() > 0) {
//
//            char x;
//            int[] t = new int[editText.getText().toString()
//                    .length()];
//
//            for (int i = 0; i < editText.getText().toString()
//                    .length(); i++) {
//                x = editText.getText().toString().charAt(i);
//                int z = (int) x;
//                t[i] = z;
//
//                if ((z > 64 && z < 91)
//                        || (z > 96 && z < 123)|| (z > 47 && z < 58) ) {
//
//                }
//                else {
//
//                    editText.setText(editText
//                            .getText()
//                            .toString()
//                            .substring(
//                                    0,
//                                    (editText.getText()
//                                            .toString().length()) - 1));
//                    editText.setSelection(editText
//                            .getText().length());
//                }
//                Log.d("System out", "decimal value of character" + z);
//
//            }
//        }

        if (editText.getText().toString().length() > 0) {

            char x;
            int[] t = new int[editText.getText().toString()
                    .length()];

            for (int i = 0; i < editText.getText().toString()
                    .length(); i++) {
                x = editText.getText().toString().charAt(i);
                int z = (int) x;
                t[i] = z;

                if ((z > 64 && z < 91)
                        || (z > 96 && z < 123) || (z >= 48 && z <= 57)) {

                } else {

                    editText.setText(editText
                            .getText()
                            .toString()
                            .substring(
                                    0,
                                    (editText.getText()
                                            .toString().length()) - 1));
                    editText.setSelection(editText
                            .getText().length());
                }

            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public void onResume() {

        super.onResume();
        activity.onUserInteraction();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comefrom = getArguments().getString("come_from");
        userdateofBirth = getArguments().getString("date_of_birth");
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
        int themeResId = 2;
        DatePickerDialog dpd = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("year", year + "");
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                Calendar minAdultAge = new GregorianCalendar();
                minAdultAge.add(Calendar.YEAR, -18);
                if (minAdultAge.before(myCalendar)) {
                    Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), nointernetmsg);
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
//            jsonObject.put("countryCode", "India");
            jsonObject.put("countryCode", sessionManager.userProfileData().getCountryShortCode());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
//        Constants.showProgress(ProfileActivity.this);
        CustomLog.i("System out", "json " + json);
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
                                    idtype = idTypePojos.get(0).getData().get(position).getIDTypeID();
                                    idtypemaxlength = Integer.parseInt(idTypePojos.get(0).getData().get(position).getMaxLength());
                                    idtypeminlength = Integer.parseInt(idTypePojos.get(0).getData().get(position).getMinLength());
                                    if (idTypePojos.get(0).getData().get(position).getIsNumeric().equals("true") && idTypePojos.get(0).getData().get(position).getIsAlphaNumeric().equals("false")) {
                                        idNumberEditTextAddBeneficiaryCustomerInfo.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    } else if (idTypePojos.get(0).getData().get(position).getIsAlphaNumeric().equals("true") && idTypePojos.get(0).getData().get(position).getIsNumeric().equals("false")) {
                                        idNumberEditTextAddBeneficiaryCustomerInfo.setInputType(InputType.TYPE_CLASS_TEXT);
                                    } else {
                                        idNumberEditTextAddBeneficiaryCustomerInfo.setInputType(InputType.TYPE_CLASS_TEXT);
                                    }
                                    InputFilter[] FilterArray = new InputFilter[1];
                                    FilterArray[0] = new InputFilter.LengthFilter(idtypemaxlength);
                                    idNumberEditTextAddBeneficiaryCustomerInfo.setFilters(FilterArray);

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
        } else if (idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString().length() < idtypeminlength) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), "ID Number should be" + " " + idtypeminlength + " " + "digits");
        } else if (idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString().length() > idtypemaxlength) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), valididnumbermsg);
        }
        else if (idExpireyDatecustomerEditTextAddBeneficiary.getText().toString().length() == 0) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), "Please select Id Expirey Date");
        }
        else if (findSourceSpinnerAddBeneficiary.getSelectedItem() == null) {
            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), "Please select source of fund");
        }
//        else if (idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString().length() < 7) {
//            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), valididnumbermsg);
//        }

//        else if (dateofBirthEditTextAddBeneficiaryCustomerInfo.getText().toString().length() == 0) {
//            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), dateofbirthmsg);
//        }
// else if (idDescriptionEditTextAddBeneficiaryCustomerInfo.getText().toString().length() == 0) {
//            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), );
//        }
        else {

            if (IsNetworkConnection.checkNetworkConnection(getActivity())) {
                createCustomerJsonCall();
            } else {
                Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), nointernetmsg);
            }

        }
    }

    public AddBeneficiaryCustomerInfoFragment newInstance(String bank, int userId, String dateofbirth) {
        AddBeneficiaryCustomerInfoFragment fragment = new AddBeneficiaryCustomerInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("come_from", bank);
        bundle.putInt("user_id", userId);
        bundle.putString("date_of_birth", dateofbirth);
        fragment.setArguments(bundle);
        return fragment;
    }


    private void createCustomerJsonCall() {
        Constants.showProgress(getActivity());

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", String.valueOf(userId));
//            jsonObject.put("userDateOfBirth", Constants.formatDate(dateofBirthEditTextAddBeneficiaryCustomerInfo.getText().toString(), "dd/MM/yyyy", "MM/dd/yyyy"));
            jsonObject.put("userDateOfBirth", "");
            jsonObject.put("IDType", idtype);
            jsonObject.put("IDtype_Description", idDescriptionEditTextAddBeneficiaryCustomerInfo.getText().toString());
//            jsonObject.put("IDExpiryDate", "12/31/2099");
            jsonObject.put("IDExpiryDate", idexpireydate);
            jsonObject.put("IDNumber", String.valueOf(idNumberEditTextAddBeneficiaryCustomerInfo.getText().toString()));
            jsonObject.put("SourceOfFund ",fundId);
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
                        String test = datumLable_languages_msg.getMessage("Request successful");
                        String test1 = createCustomerListJsonPojos.get(0).getData().get(0).getDescription();
                        if (test.equalsIgnoreCase("")) {
                            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), test1);
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
                            Constants.showMessage(addBeneficiaryCustomerInfoLinearlayout, getActivity(), response.body().get(0).getInfo().toString());
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
    private void incomeListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userMobile", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        Constants.showProgress(getActivity());
        CustomLog.d("System out", "relation json " + json);
        Call<List<SourceOfFundJsonPojo>> call = RestClient.get().sourceofFundListJsonCall(json);

        call.enqueue(new Callback<List<SourceOfFundJsonPojo>>() {
            @Override
            public void onResponse(Call<List<SourceOfFundJsonPojo>> call, Response<List<SourceOfFundJsonPojo>> response) {
                Constants.closeProgress();
                if (response.body() != null && response.body() instanceof ArrayList) {
                    fundListPojos.clear();

                    if (response.body().get(0).getStatus() == true) {
                        ArrayList<String> fundList = new ArrayList<>();
                        fundListPojos.addAll(response.body().get(0).getData());
                        for (int i = 0; i < fundListPojos.size(); i++) {
                            fundList.add(fundListPojos.get(i).getSourceName());

//                            if (CountryCodegoogle.equalsIgnoreCase(new String(Base64.decode(fundListPojos.get(i).getCountryName().trim().getBytes(), Base64.DEFAULT)))) {
//                                countryId = fundListPojos.get(i).getCountryID();
//                                break;
//                            }


                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, fundList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        findSourceSpinnerAddBeneficiary.setAdapter(adapter);

                        findSourceSpinnerAddBeneficiary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != -1) {
                                    fundId = fundListPojos.get(position).getID();
                                    fundname = fundListPojos.get(position).getSourceName();


                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } else {
//                        Constants.showMessage(mainLinearLayoutSignUpSubmit, SignUpSubmitActivity.this,"sorry,record not found");


                    }
                }
            }

            @Override
            public void onFailure(Call<List<SourceOfFundJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
            }
        });
    }
}
