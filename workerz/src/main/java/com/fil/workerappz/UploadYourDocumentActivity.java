package com.fil.workerappz;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fil.workerappz.adapter.UploadDocumentListAdapter;
import com.fil.workerappz.pojo.DocumentListCountryWiseJsonPojo;
import com.fil.workerappz.pojo.ImageListPojo;
import com.fil.workerappz.pojo.KYCUploadedDocumentListJsonPojo;
import com.fil.workerappz.pojo.LabelListData;
import com.fil.workerappz.pojo.MessagelistData;
import com.fil.workerappz.pojo.UserListPojo;
import com.fil.workerappz.retrofit.RestApi;
import com.fil.workerappz.retrofit.RestClient;
import com.fil.workerappz.utils.ApiClient;
import com.fil.workerappz.utils.Constants;
import com.fil.workerappz.utils.CustomLog;
import com.fil.workerappz.utils.IsNetworkConnection;
import com.fil.workerappz.utils.SessionManager;
import com.fil.workerappz.utils.SlideHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HS on 24-Feb-18.
 * FIL AHM
 */

public class UploadYourDocumentActivity extends ActionBarActivity {

    private static final int TAKE_PICTURE = 1;
    private static final int SELECT_PICTURE = 2;
    @BindView(R.id.menuImageViewHeader2)
    ImageView menuImageViewHeader2;
    @BindView(R.id.appImageViewHeader2)
    ImageView appImageViewHeader2;
    @BindView(R.id.titleTextViewViewHeader2)
    TextView titleTextViewViewHeader2;
    @BindView(R.id.filterImageViewHeader2)
    ImageView filterImageViewHeader2;
    @BindView(R.id.uploadDocumentRecyclerView)
    RecyclerView uploadDocumentRecyclerView;
    @BindView(R.id.homeImageViewFooter)
    ImageView homeImageViewFooter;
    @BindView(R.id.homeTextViewFooter)
    TextView homeTextViewFooter;
    @BindView(R.id.homeLinearLayoutFooter)
    LinearLayout homeLinearLayoutFooter;
    @BindView(R.id.kycImageViewFooter)
    ImageView kycImageViewFooter;
    @BindView(R.id.kycTextViewFooter)
    TextView kycTextViewFooter;
    @BindView(R.id.kycLinearLayoutFooter)
    LinearLayout kycLinearLayoutFooter;
    @BindView(R.id.quickPayImageViewFooter)
    ImageView quickPayImageViewFooter;
    @BindView(R.id.quickPayTextViewFooter)
    TextView quickPayTextViewFooter;
    @BindView(R.id.quickPayLinearLayoutFooter)
    LinearLayout quickPayLinearLayoutFooter;
    @BindView(R.id.beneficiaryImageViewFooter)
    ImageView beneficiaryImageViewFooter;
    @BindView(R.id.beneficiaryTextViewFooter)
    TextView beneficiaryTextViewFooter;
    @BindView(R.id.beneficiaryLinearLayoutFooter)
    LinearLayout beneficiaryLinearLayoutFooter;
    @BindView(R.id.historyImageViewFooter)
    ImageView historyImageViewFooter;
    @BindView(R.id.historyTextViewFooter)
    TextView historyTextViewFooter;
    @BindView(R.id.historyLinearLayoutFooter)
    LinearLayout historyLinearLayoutFooter;
    @BindView(R.id.slideHolderKYC)
    SlideHolder slideHolderKYC;
    @BindView(R.id.mainUploadYourDocumentLinearLayout)
    LinearLayout mainUploadYourDocumentLinearLayout;
    @BindView(R.id.appLeftImageViewHeader2)
    ImageView appLeftImageViewHeader2;
    @BindView(R.id.skipTextViewViewHeader2)
    TextView skipTextViewViewHeader2;
    @BindView(R.id.myFooterUploadDocument)
    LinearLayout myFooterUploadDocument;
    private UserListPojo.Data userListPojo;
    private final ArrayList<DocumentListCountryWiseJsonPojo.Data> documentListCountryWiseJsonPojos = new ArrayList<>();
    private final ArrayList<KYCUploadedDocumentListJsonPojo> kycUploadedDocumentListJsonPojos = new ArrayList<>();
    private final ArrayList<KYCUploadedDocumentListJsonPojo> tempKycUploadedDocumentListJsonPojos = new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private UploadDocumentListAdapter uploadDocumentListAdapter;
    private Intent mIntent;
    private File file, imgFile;
    private long timeForImageName;
    private String imgName = "";
    private String imgName1 = "";
    private Uri pictureUri = null, myUri;
    private final int docNameId = 0;
    private final int uploadDocForPosition = 0;
    private String comeFrom = "";
    private File compressedImage;
    private LabelListData datumLable_languages = new LabelListData();
    private MessagelistData datumLable_languages_msg = new MessagelistData();
    private SessionManager sessionManager;
    private String nointernetmsg;
    private Uri fileUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.upload_document);
        ButterKnife.bind(this);

        mIntent = getIntent();
        if (mIntent != null) {
            comeFrom = mIntent.getStringExtra("come_from");
        }

        filterImageViewHeader2.setVisibility(View.INVISIBLE);
        if (comeFrom != null) {
//            menuImageViewHeader2.setImageResource(R.drawable.back_btn);
            menuImageViewHeader2.setVisibility(View.INVISIBLE);
            appImageViewHeader2.setVisibility(View.INVISIBLE);
            filterImageViewHeader2.setVisibility(View.GONE);
            myFooterUploadDocument.setVisibility(View.GONE);
            skipTextViewViewHeader2.setVisibility(View.VISIBLE);
        }


        userListPojo = getUserData();

        CustomLog.d("System out", "userID " + getUserData().getUserID());
        CustomLog.d("System out", "countryID " + getUserData().getCountryID());

        try {
            sessionManager = new SessionManager(UploadYourDocumentActivity.this);
            datumLable_languages = sessionManager.getAppLanguageLabel();
            datumLable_languages_msg = sessionManager.getAppLanguageMessage();

            if (datumLable_languages != null) {

                titleTextViewViewHeader2.setText(datumLable_languages.getLegalDocuments());
                nointernetmsg = datumLable_languages.getNoInternetConnectionAvailable();
//                skipTextViewViewHeader2.setText(datumLable_languages.getSkip());

            } else {
                titleTextViewViewHeader2.setText("Upload KYC");
                nointernetmsg = getResources().getString(R.string.no_internet);
//                skipTextViewViewHeader2.setText(getResources().getString(R.string.skip));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (IsNetworkConnection.checkNetworkConnection(this)) {
//            getDocumentListCountryWiseJsonCall();
            kycDocumentListJsonCall();
        } else {
            Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, nointernetmsg);
        }

        kycSelection();

    }

    @OnClick({R.id.menuImageViewHeader2, R.id.skipTextViewViewHeader2, R.id.appImageViewHeader2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.menuImageViewHeader2:
                if (comeFrom != null) {
                    finish();
                } else {
                    slideHolderKYC.toggle();
                }
                break;
            case R.id.appImageViewHeader2:
                mIntent = new Intent(UploadYourDocumentActivity.this, HomeActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);

                break;
            case R.id.skipTextViewViewHeader2:
//                if (Constants.uploaddocument>3)
//                {
//                    Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, "You can Upload maximum Three documents");
//                }
//                else if (Constants.uploaddocument<1)
//                {
//                    Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, "You can Upload minimum Two documents");
//                }
//                else {
                mIntent = new Intent(UploadYourDocumentActivity.this, VerificationActivity.class);
                startActivity(mIntent);
                break;
//                }

        }
    }

    private void getDocumentListCountryWiseJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("languageID", Constants.language_id);
            jsonObject.put("countryID", "" + getUserData().getCountryID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "document list json " + json);
        Constants.showProgress(UploadYourDocumentActivity.this);
        Call<List<DocumentListCountryWiseJsonPojo>> call = RestClient.get().documentListCountryWiseJsonCall(json);

        call.enqueue(new Callback<List<DocumentListCountryWiseJsonPojo>>() {
            @Override
            public void onResponse(Call<List<DocumentListCountryWiseJsonPojo>> call, Response<List<DocumentListCountryWiseJsonPojo>> response) {
                Constants.closeProgress();

                if (response.body() != null && response.body() instanceof ArrayList) {
                    if (response.body().get(0).getStatus() == true) {
                        documentListCountryWiseJsonPojos.addAll(response.body().get(0).getData());
                        tempKycUploadedDocumentListJsonPojos.addAll(kycUploadedDocumentListJsonPojos);


                        if (kycUploadedDocumentListJsonPojos.size() > 0) {
                            CustomLog.d("System out", "uploaddocsize" + kycUploadedDocumentListJsonPojos.get(0).getInfo().size());
                            CustomLog.d("System out", "countrydocsize" + documentListCountryWiseJsonPojos.size());
                            if (kycUploadedDocumentListJsonPojos.get(0).getInfo().size() > 0) {
                                for (int i = 0; i < documentListCountryWiseJsonPojos.size(); i++) {
                                    boolean mFlag = true;
                                    for (int j = 0; j < tempKycUploadedDocumentListJsonPojos.get(0).getInfo().size(); j++) {
                                        if (tempKycUploadedDocumentListJsonPojos.get(0).getInfo().get(j).getKycdocnameID().equalsIgnoreCase(documentListCountryWiseJsonPojos.get(i).getKycdocnameID()) && tempKycUploadedDocumentListJsonPojos.get(0).getInfo().get(j).getKycdoctypeID().equalsIgnoreCase(documentListCountryWiseJsonPojos.get(i).getKycdoctypeID())) {
                                            mFlag = false;
                                        }
                                    }

                                    if (mFlag == true && i < documentListCountryWiseJsonPojos.size()) {
                                        KYCUploadedDocumentListJsonPojo.Info info = new KYCUploadedDocumentListJsonPojo.Info();
                                        info.setUserkycID("");
                                        info.setKycdocnameID(documentListCountryWiseJsonPojos.get(i).getKycdocnameID());
                                        info.setKycdoctypeID(documentListCountryWiseJsonPojos.get(i).getKycdoctypeID());
                                        info.setKycdocnameName(documentListCountryWiseJsonPojos.get(i).getKycdocnameName());
                                        info.setKycdoctypeName(documentListCountryWiseJsonPojos.get(i).getKycdoctypeName());
                                        info.setUserID("");
                                        info.setUserkycImage("");
                                        info.setUserkycStatus("");
                                        info.setUserkycStatusReason("");
                                        info.setUserkycStatusDate("");
                                        info.setUserkycCreatedDate("");
                                        info.setKycstatushistory("");
                                        kycUploadedDocumentListJsonPojos.get(0).getInfo().add(info);
                                    }
                                }
                            }


                        } else {
                            KYCUploadedDocumentListJsonPojo jsonPojo = new KYCUploadedDocumentListJsonPojo();
                            jsonPojo.setStatus(true);
                            List<KYCUploadedDocumentListJsonPojo.Info> infoList = new ArrayList<>();
                            for (int i = 0; i < documentListCountryWiseJsonPojos.size(); i++) {
                                KYCUploadedDocumentListJsonPojo.Info info = new KYCUploadedDocumentListJsonPojo.Info();
                                info.setUserkycID("");
                                info.setKycdocnameID(documentListCountryWiseJsonPojos.get(i).getKycdocnameID());
                                info.setKycdoctypeID(documentListCountryWiseJsonPojos.get(i).getKycdoctypeID());
                                info.setKycdocnameName(documentListCountryWiseJsonPojos.get(i).getKycdocnameName());
                                info.setKycdoctypeName(documentListCountryWiseJsonPojos.get(i).getKycdoctypeName());
                                info.setUserID("");
                                info.setUserkycImage("");
                                info.setUserkycStatus("");
                                info.setUserkycStatusReason("");
                                info.setUserkycStatusDate("");
                                info.setUserkycCreatedDate("");
                                info.setKycstatushistory("");
                                infoList.add(info);
                            }
                            jsonPojo.setInfo(infoList);
                            kycUploadedDocumentListJsonPojos.add(jsonPojo);
                        }
                        if (documentListCountryWiseJsonPojos.size() > 0) {
                            layoutManager = new LinearLayoutManager(UploadYourDocumentActivity.this);
                            uploadDocumentRecyclerView.setLayoutManager(layoutManager);
                            uploadDocumentListAdapter = new UploadDocumentListAdapter(UploadYourDocumentActivity.this, kycUploadedDocumentListJsonPojos.get(0).getInfo(), getMyUserId(), skipTextViewViewHeader2, datumLable_languages, datumLable_languages_msg, mainUploadYourDocumentLinearLayout);
                            uploadDocumentRecyclerView.setAdapter(uploadDocumentListAdapter);
                            uploadDocumentListAdapter.notifyDataSetChanged();
                        }
//                    if (response.body().get(0).getStatus() == true) {
//                        kycDocumentListJsonCall();
//                    }
                    } else {
//                        Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, response.body().get(0).getInfo());
                        Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, datumLable_languages_msg.getMessage(response.body().get(0).getInfo().toString()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<DocumentListCountryWiseJsonPojo>> call, Throwable t) {
                Constants.closeProgress();

            }
        });
    }

    private void kycDocumentListJsonCall() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", "" + getUserData().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.d("System out", "signIn json " + json);
        Constants.showProgress(UploadYourDocumentActivity.this);
        Call<List<KYCUploadedDocumentListJsonPojo>> call = RestClient.get().uploadedKYCDocumentListJsonCall(json);

        call.enqueue(new Callback<List<KYCUploadedDocumentListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<KYCUploadedDocumentListJsonPojo>> call, Response<List<KYCUploadedDocumentListJsonPojo>> response) {
                Constants.closeProgress();

                //kycUploadedDocumentListJsonPojos.get(0).getInfo().get(j).getKycdocnameName().equals(documentListCountryWiseJsonPojos.get(i).getKycdocnameName()) || kycUploadedDocumentListJsonPojos.get(0).getInfo().get(j).getKycdocnameID().equals(documentListCountryWiseJsonPojos.get(i).getKycdocnameID())
                if (response.body() != null && response.body() instanceof ArrayList) {
                    kycUploadedDocumentListJsonPojos.addAll(response.body());
//                    skipTextViewViewHeader2.setVisibility(View.VISIBLE);
                    skipTextViewViewHeader2.setText(datumLable_languages.getSave());

                    getDocumentListCountryWiseJsonCall();


                }

            }

            @Override
            public void onFailure(Call<List<KYCUploadedDocumentListJsonPojo>> call, Throwable t) {
                Constants.closeProgress();
                t.printStackTrace();
                getDocumentListCountryWiseJsonCall();

            }
        });
    }

    public void openGallery() {
        mIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(mIntent, "Select Picture"), SELECT_PICTURE);
    }

    public void startCameraActivity() {
        mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        timeForImageName = System.currentTimeMillis();
        imgName = "img" + timeForImageName + ".jpg";
        file = new File(getExternalFilesDir(null), imgName);
//        fileUri = Constants.getOutputMediaFileUri();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1) {
            mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        } else {
            mIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(UploadYourDocumentActivity.this, getPackageName() + ".provider", file));
        }

        pictureUri = Uri.fromFile(file.getAbsoluteFile());
        startActivityForResult(mIntent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == TAKE_PICTURE) {
                CustomLog.d("System out", "pictureUri " + pictureUri);
                CustomLog.d("System out", "pictureUri " + pictureUri);
                CustomLog.d("System out", "pictureUri " + getExternalFilesDir(null).getParent());
                CustomLog.d("System out", "pictureUri " + getExternalFilesDir(null).getAbsolutePath());
                CustomLog.d("System out", "pictureUri " + getExternalFilesDir(null).getPath());
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(new File(file.getPath()).getAbsolutePath(), options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;
//                try {
//                    compressedImage = new Compressor(this)
//                            .setMaxWidth(640)
//                            .setMaxHeight(480)
//                            .setQuality(75)
//                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                            .compressToFile(file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    imgName1 = "img" + timeForImageName;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String imgStorepath = "";
                imgStorepath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/";
                try {
                    Intent i = new Intent(getApplicationContext(), CropActivity.class);
                    i.putExtra("fileUri", pictureUri.toString());
                    i.putExtra("width", String.valueOf(imageWidth));
                    i.putExtra("height",  String.valueOf(imageHeight));
                    i.putExtra("imgStrPath", file.getPath());
                    i.putExtra("fileName", imgName1);
                    startActivityForResult(i, 525);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if (IsNetworkConnection.checkNetworkConnection(UploadYourDocumentActivity.this)) {
//                    uploadKYCDoc();
//                } else {
//                    Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, nointernetmsg);
//                }
            } else if (requestCode == SELECT_PICTURE) {
                timeForImageName = System.currentTimeMillis();
                imgName = "img" + timeForImageName + ".jpg";
                imgName1 = "img" + timeForImageName;
                pictureUri = data.getData();

                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(pictureUri, projection, null, null, null);
                cursor.moveToFirst();
                CustomLog.d("System out", DatabaseUtils.dumpCursorToString(cursor));
                int columnIndex = cursor.getColumnIndex(projection[0]);
                String picturePath = cursor.getString(columnIndex); // returns null
                cursor.close();

                file = new File(picturePath);
                CustomLog.d("System out", "pictureUri " + pictureUri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(new File(file.getPath()).getAbsolutePath(), options);
                int imageHeight = options.outHeight;
                int imageWidth = options.outWidth;

                CustomLog.d("System out", "width " + imageWidth);
                CustomLog.d("System out", "height " + imageHeight);

//                if (imageWidth < 400 || imageHeight < 700) {
//                    compressedImage = file;
//                } else {
//                    try {
//                        compressedImage = new Compressor(this)
//                                .setMaxWidth(640)
//                                .setMaxHeight(480)
//                                .setQuality(75)
//                                .setCompressFormat(Bitmap.CompressFormat.WEBP)
//                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//                                .compressToFile(file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }

                try {
                    Intent i = new Intent(getApplicationContext(), CropActivity.class);
                    i.putExtra("fileUri", pictureUri.toString());
                    i.putExtra("width", String.valueOf(imageWidth));
                    i.putExtra("height",  String.valueOf(imageHeight));
                    i.putExtra("imgStrPath", file.getPath());
//                    i.putExtra("fileName", imgStorepath);
                    startActivityForResult(i, 525);
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                if (IsNetworkConnection.checkNetworkConnection(UploadYourDocumentActivity.this)) {
//                    uploadKYCDoc();
//                } else {
//                    Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, nointernetmsg);
//                }
            } else if (requestCode == 525) {

                Log.e("!_!525 Data Crop ?>>", data.getStringExtra("myFileName") + "");

                try {
                    imgFile = new File(data.getStringExtra("myFileName"));

                    myUri = Uri.parse(data.getStringExtra("myFileName"));

                    if (imgFile.exists()) {
                        if (IsNetworkConnection.checkNetworkConnection(UploadYourDocumentActivity.this)) {
                            compressedImage = new File(String.valueOf(myUri));
                            uploadKYCDoc();
                        } else {
                            Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, nointernetmsg);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Image is not cropped", Toast.LENGTH_SHORT).show();
                        uploadKYCDoc();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadKYCDoc() {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
//        File file = new File(String.valueOf(this.file));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("FileField", imgName, RequestBody.create(MediaType.parse("image*//*"), compressedImage));
        CustomLog.i("System out", "image body response" + filePart.toString());

        String json = "[{" +
                "\"api_type\":\"" + Constants.api_type + "\"," +
                "\"api_userid\":\"" + getUserData().getUserID() + "\"," +
                "\"api_devicetoken\":\"" + Constants.device_token + "\"," +
                "\"Version\":\"" + Constants.version + "\"" +
                "}]";

        Call<List<ImageListPojo>> call = apiService.uploadAttachment(filePart, RequestBody.create(MediaType.parse("text/plain"), "kycdocs"), RequestBody.create(MediaType.parse("text/plain"), json));

        CustomLog.i("System out", "json " + json);

        Constants.showProgress(UploadYourDocumentActivity.this);

        call.enqueue(new Callback<List<ImageListPojo>>() {
            @Override
            public void onResponse(Call<List<ImageListPojo>> call, Response<List<ImageListPojo>> response) {
                CustomLog.d("System out", "response code " + response.body());
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == true) {
                        CustomLog.d("System out", "response true " + response.body().get(0).getInfo());
                        CustomLog.d("System out", "response true " + response.body().get(0).getFileName());

                        if (Constants.Updateflag) {
                            if (IsNetworkConnection.checkNetworkConnection(UploadYourDocumentActivity.this)) {
                                uploadDocumentListAdapter.updateKYCDOC(imgName, mainUploadYourDocumentLinearLayout);
                            } else {
                                Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, nointernetmsg);
                            }
                        } else {

                            if (IsNetworkConnection.checkNetworkConnection(UploadYourDocumentActivity.this)) {
                                uploadDocumentListAdapter.createKYCDoc(imgName, mainUploadYourDocumentLinearLayout);
                            } else {
                                Constants.showMessage(mainUploadYourDocumentLinearLayout, UploadYourDocumentActivity.this, nointernetmsg);
                            }
                        }

                    } else {
                        CustomLog.d("System out", "response false " + response.body().get(0).getInfo());
                    }
                } else {

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<ImageListPojo>> call, Throwable t) {
                // Log error here since request failed
                CustomLog.e("System out", t.getMessage());
                Constants.closeProgress();
            }
        });
    }

    private void createKYCDoc() {
        RestApi apiService = ApiClient.getClient().create(RestApi.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userID", getUserData().getUserID());
            jsonObject.put("kycdocnameID", docNameId);
            jsonObject.put("userkycImage", imgName);
            jsonObject.put("kycdoctypeID", documentListCountryWiseJsonPojos.get(uploadDocForPosition).getKycdoctypeID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = "[" + jsonObject + "]";
        CustomLog.i("System out", "json " + json);

        Call<List<KYCUploadedDocumentListJsonPojo>> call = apiService.createKYCDocumentJsonCall(json);
        Constants.showProgress(UploadYourDocumentActivity.this);

        call.enqueue(new Callback<List<KYCUploadedDocumentListJsonPojo>>() {
            @Override
            public void onResponse(Call<List<KYCUploadedDocumentListJsonPojo>> call, Response<List<KYCUploadedDocumentListJsonPojo>> response) {
                CustomLog.d("System out", "response code " + response.body());
                if (response.body() != null) {
                    if (response.body().get(0).getStatus() == true) {
                        uploadDocumentListAdapter.notifyDataSetChanged();
                    } else {
                        CustomLog.d("System out", "response false " + response.body().get(0).getInfo());
                    }
                } else {

                }
                Constants.closeProgress();
            }

            @Override
            public void onFailure(Call<List<KYCUploadedDocumentListJsonPojo>> call, Throwable t) {
                // Log error here since request failed
                CustomLog.e("System out", t.getMessage());
                Constants.closeProgress();
            }
        });
    }

    private void kycSelection() {
        kycImageViewFooter.setImageResource(R.drawable.footer_icon_kyc_selected);
        kycTextViewFooter.setTextColor(getResources().getColor(R.color.colorWhite));
    }


}
