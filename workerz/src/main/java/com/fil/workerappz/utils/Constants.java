package com.fil.workerappz.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.fil.workerappz.R;
import com.fil.workerappz.pojo.ding.GetProductsList;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HS on 27-Feb-18.
 * FIL AHM
 */

public class Constants {

    public static final boolean ENABLE_LOG = true;
    public static final String FLAG_URL = "http://betaapplication.com/workerzapp/backend/web/uploads/flag/";
//    public static final String FLAG_URL = "http://180.179.114.39/backend/web/uploads/flag/";
//    public static final String FLAG_URL = "https://m.workerappz.com/backend/web/uploads/flag/";
    public static final String IMAGE_URL = "http://betaapplication.com/workerzapp/backend/web/index.php/api/";
//    public static final String IMAGE_URL = "http://180.179.114.39/backend/web/index.php/api/";
//    public static final String IMAGE_URL = "https://m.workerappz.com/backend/web/index.php/api/";
    public static final String IMAGE_URL_KYC = "http://betaapplication.com/workerzapp/backend/web/uploads/kycdocs/";
//    public static final String IMAGE_URL_KYC = "http://180.179.114.39/backend/web/uploads/kycdocs/";
//    public static final String IMAGE_URL_KYC = "https://m.workerappz.com/backend/web/uploads/kycdocs/";
    public static final String IMAGE_URL_USER = "http://betaapplication.com/workerzapp/backend/web/uploads/user/";
//    public static final String IMAGE_URL_USER = "http://180.179.114.39/backend/web/uploads/user/";
//    public static final String IMAGE_URL_USER = "https://m.workerappz.com/backend/web/uploads/user/";
    public static final String api_type = "android";
    public static final String version = "1.0";
    public static final String device_type = "android";
    public static String device_token = "android";
    public static String device_id = "android";
    public static final String user_type = "Individual";
    public static String latitude = "0.0";
    public static String longitude = "0.0";
    public static String language_id = "1";
    public static String language_id_label_msg = "1";

    public static String filter = "all";
    public static int uploaddocument = 0;
    public static int country_selection_position = -1;
    public static int ca = -1;

    public static int bankBenificaryCount = 0;
    public static int cashBenificaryCount = 0;
    public static int bankNextBenificaryCount = 0;
    private static Dialog dialog;
    public static GetProductsList.Data productListData = null;
    public static boolean Updateflag=false;
    public static int beneficiarcount=0;

    public static void showProgress(Activity activity) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = showProgressDialog(activity);
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            CustomLog.e("System out", e.getMessage());
        }
    }

    public static void closeProgress() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static Dialog showProgressDialog(Context c) {
        final Dialog dialog = new Dialog(c, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progressbar);
        return dialog;
    }

    public static boolean validateEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static int specialCharacterCount(String count)
    {
        String SPECIAL_CHARS_REGEX = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]";
//        String SPECIAL_CHARS_REGEX = "[!@#$%^&*()\\[\\]|;',./{}\\\\:\"<>?]";
        int specials = count.split(SPECIAL_CHARS_REGEX, -1).length - 1;
        return specials;
    }

    public static boolean validateSpecialCharacter(String character) {
        Pattern pattern;
        Matcher matcher;
        String EMAIL_PATTERN = "[$&+,:;=\\\\?@#|/'<>.^*()%!-]";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(character);
        return matcher.matches();
    }

    public static void showMessage(View view, Context context, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen._12ssp));
        textView.setMaxLines(2);
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        snackbar.show();
    }

    public static String formatDate(String date, String initDateFormat, String endDateFormat) {

        String parsedDate = "";
        try {
            Date initDate = new SimpleDateFormat(initDateFormat, Locale.getDefault()).parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat, Locale.getDefault());
            parsedDate = formatter.format(initDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }

    public static String formatAmount(String value) {
        DecimalFormat formatter = new DecimalFormat("#,##,##,##,###");
        String yourFormattedString = "";
        try {
            yourFormattedString = formatter.format(Double.parseDouble(value));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return yourFormattedString;
    }

    public static String stringBase64Encode(String string) {
        byte[] mString = Base64.encode(string.getBytes(), Base64.DEFAULT);
        return Arrays.toString(mString);
    }

    public static String stringBase64Decode(String string) {
        try {
            byte[] mString = Base64.decode(string.getBytes("UTF-8"), Base64.DEFAULT);
            return Arrays.toString(mString);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String base64EncodeToString(String string) {
//        Base64.encodeToString(edSearchFeed.getText().toString().getBytes(), Base64.NO_WRAP)
        String mString = Base64.encodeToString(string.getBytes(), Base64.NO_WRAP);
        return mString;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static float findNumericValue(String string) {
        try {
            return Float.parseFloat(string.replaceAll("[^.0-9]", ""));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Dialog showMessageOkCancel(Context context, String message, DialogInterface.OnClickListener okListener) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", okListener);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        android.app.AlertDialog alert = builder.create();
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.show();

        return alert;
    }
}