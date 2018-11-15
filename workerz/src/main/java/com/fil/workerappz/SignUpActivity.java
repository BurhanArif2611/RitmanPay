package com.fil.workerappz;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.SessionManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by HS on 23-Feb-18.
 * FIL AHM
 */

public class SignUpActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 100;
    @BindView(R.id.signUpWithEmailMobileTextViewSignUpActivity)
    TextView signUpWithEmailMobileTextViewSignUpActivity;
    @BindView(R.id.signInTextViewSignUpActivity)
    TextView signInTextViewSignUpActivity;
    @BindView(R.id.fbButton)
    LoginButton fbButton;

    @BindView(R.id.alreadyhaveaccounttextview)
    TextView alreadyhaveaccounttextview;
    @BindView(R.id.texdtviewor)
    TextView texdtviewor;
    @BindView(R.id.textviewsigninwithgoogle)
    TextView textviewsigninwithgoogle;

    @BindView(R.id.textviewsigninwithfacebook)
    TextView textviewsigninwithfacebook;
    @BindView(R.id.signInWithGoogle)
    LinearLayout signInWithGoogle;
    @BindView(R.id.signInWithFacebook)
    LinearLayout signInWithFacebook;
    @BindView(R.id.alreadyhaveotptextview)
    TextView alreadyhaveotptextview;
    @BindView(R.id.mainSignUpLinearlayout)
    LinearLayout mainSignUpLinearlayout;

    //G+ Login
    private GoogleApiClient mGoogleApiClient;
    private Intent mIntent;
    private boolean mIntentInProgress;
    // fb login
    private CallbackManager callbackManager;
    private String accessTokenNew = "", email = "", userPin = "", mobilenumber, validmobilenumber;
    private LabelListData datumLable_languages = new LabelListData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.signup_activity);
        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(SignUpActivity.this)
                .enableAutoManage(SignUpActivity.this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        fbLoginCall();

        try {
            SessionManager sessionManager = new SessionManager(SignUpActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();

            if (datumLable_languages != null) {
                signInTextViewSignUpActivity.setText(datumLable_languages.getSignIn());
                signUpWithEmailMobileTextViewSignUpActivity.setText(datumLable_languages.getSignUpWithEmailMobile());
                alreadyhaveaccounttextview.setText(datumLable_languages.getAlerdyHaveAnAccount());
                texdtviewor.setText(datumLable_languages.getOR());
                textviewsigninwithfacebook.setText(datumLable_languages.getSignUpWithFacebook());
                textviewsigninwithgoogle.setText(datumLable_languages.getSignUpWithGoogle());

            } else {
                signInTextViewSignUpActivity.setText(getResources().getString(R.string.sign_in));
                signUpWithEmailMobileTextViewSignUpActivity.setText(getResources().getString(R.string.sign_up_with_email_mobile));
                alreadyhaveaccounttextview.setText(getResources().getString(R.string.already_have_an_account));
                alreadyhaveaccounttextview.setText(getResources().getString(R.string.already_have_an_account));
                textviewsigninwithgoogle.setText(getResources().getString(R.string.sign_in_with_google));
                textviewsigninwithfacebook.setText(getResources().getString(R.string.sign_in_with_facebook));
                texdtviewor.setText(getResources().getString(R.string.or));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mobilenumber = getResources().getString(R.string.Please_Enter_Mobile_number);
        validmobilenumber = getResources().getString(R.string.Please_Enter_valid_Mobile_number);

    }

    @OnClick({R.id.signUpWithEmailMobileTextViewSignUpActivity, R.id.signInTextViewSignUpActivity, R.id.signInWithFacebook, R.id.signInWithGoogle, R.id.alreadyhaveotptextview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.signUpWithEmailMobileTextViewSignUpActivity:
                mIntent = new Intent(SignUpActivity.this, SignUpSubmitActivity.class);
                mIntent.putExtra("email", "");
                mIntent.putExtra("google_id", "");
                mIntent.putExtra("fb_id", "");
                mIntent.putExtra("first_name", "");
                mIntent.putExtra("last_name", "");
                mIntent.putExtra("gender", "");
                startActivity(mIntent);
//                finish();
                break;
            case R.id.signInTextViewSignUpActivity:
                mIntent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(mIntent);
//                finish();
                break;
            case R.id.signInWithGoogle:
                mIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(mIntent, RC_SIGN_IN);
                break;
            case R.id.signInWithFacebook:
                fbButton.performClick();
                break;
            case R.id.alreadyhaveotptextview:
                openOtpDialog();
                break;
        }
    }

    private void openOtpDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.mobile_number_custom_dialog, null);

        final MaterialEditText editText = (MaterialEditText) dialogView.findViewById(R.id.MobileNoEditTextSignUpActivity);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);
       final LinearLayout customDialogLayput = (LinearLayout) dialogView.findViewById(R.id.customDialogLayput);

//        editText.setHint(datumLable_languages.getMobileNumber());
//        editText.setFloatingLabelText(datumLable_languages.getMobileNumber());

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.hideKeyboard(SignUpActivity.this);
                if (editText.getText().toString().length() == 0 || editText.getText().toString().length() == 0) {
                    Constants.showMessage(customDialogLayput, SignUpActivity.this, mobilenumber);
                } else if (editText.getText().toString().length() > 0 && editText.getText().toString().length() < 7) {
                    Constants.hideKeyboard(SignUpActivity.this);
                    Constants.showMessage(customDialogLayput, SignUpActivity.this, validmobilenumber);
                } else if (editText.getText().toString().startsWith("0")) {
                    Constants.hideKeyboard(SignUpActivity.this);
                    Constants.showMessage(customDialogLayput, SignUpActivity.this, validmobilenumber);
                }
                else {
                    dialogBuilder.dismiss();
                    mIntent = new Intent(SignUpActivity.this, VerificationActivity.class);
                    mIntent.putExtra("come_from", editText.getText().toString());
                    startActivity(mIntent);
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String email_google, name_google, id_google, link_google;
            email_google = acct.getEmail();
            name_google = acct.getDisplayName();
            id_google = acct.getId();

            Person person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            CustomLog.d("System out", "google gender " + String.valueOf(person.getGender() + " " + name_google + " " + id_google));
            CustomLog.d("System out", "google " + email_google + " " + id_google + " " + name_google + " " + person.getGender());

            String[] strings = name_google.split(" ");

            mIntent = new Intent(SignUpActivity.this, SignUpSubmitActivity.class);
            mIntent.putExtra("email", email_google);
            mIntent.putExtra("google_id", id_google);
            mIntent.putExtra("fb_id", id_google);
            if (strings.length == 2) {
                mIntent.putExtra("first_name", strings[0]);
                mIntent.putExtra("last_name", strings[1]);
            } else {
                mIntent.putExtra("first_name", name_google);
                mIntent.putExtra("last_name", "");
            }
            String gender = (person.getGender() == 0) ? "Male" : "Female";
            mIntent.putExtra("gender", gender);
            startActivity(mIntent);

        } else {

        }
    }

    private void fbLoginCall() {

        fbButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        fbButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        accessTokenNew = loginResult.getAccessToken().getToken();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        try {
                                            email = object.optString("email");
                                            JSONObject jobj = object.optJSONObject("picture");
                                            JSONObject data = jobj.optJSONObject("data");
                                            LoginManager.getInstance().logOut();

                                            CustomLog.d("System out", "fb " + email + " " + object.optString("id") + " " + object.optString("name") + " " + object.optString("gender"));

                                            String[] strings = object.optString("name").split(" ");
                                            mIntent = new Intent(SignUpActivity.this, SignUpSubmitActivity.class);
                                            mIntent.putExtra("email", email);
                                            mIntent.putExtra("google_id", "");
                                            mIntent.putExtra("fb_id", object.optString("id"));
                                            if (strings.length == 2) {
                                                mIntent.putExtra("first_name", strings[0]);
                                                mIntent.putExtra("last_name", strings[1]);
                                            } else {
                                                mIntent.putExtra("first_name", object.optString("name"));
                                                mIntent.putExtra("last_name", "");
                                            }
                                            String gender = (object.optString("gender").equalsIgnoreCase("male")) ? "Male" : "Female";
                                            mIntent.putExtra("gender", gender);
                                            startActivity(mIntent);
                                            finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday,installed,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!mIntentInProgress && connectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                connectionResult.startResolutionForResult(SignUpActivity.this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
            }

        }
    }

    @Override
    protected void onDestroy() {
        mGoogleApiClient.stopAutoManage(SignUpActivity.this);
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }
}
